package ru.bortnik.project.configuration;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.bortnik.project.dto.SubscriberDto;
import ru.bortnik.project.processor.SetWorkProcessor;
import ru.bortnik.project.service.SubscriberRepositoryService;

import static org.apache.camel.LoggingLevel.ERROR;
import static org.apache.camel.LoggingLevel.INFO;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class Route {

    @Value("${app.json-schema}")
    private String jsonSchema;

    private final FolderProperties folders;

    @Bean
    public RouteBuilder routeBuilder() {
        return new RouteBuilder() {
            @Override
            public void configure() throws Exception {
                onException(Exception.class)
                        .maximumRedeliveries(5)
                        .redeliveryDelay(1000)
                        .handled(true)
                        .log(ERROR, log, "${exception.message}: ${exception.stacktrace}");

                // Поиск *.ready-файла
                from(String.format("file://%s?include=.*.ready&delete=true", folders.getWork()))
                        .log(INFO, log, "Flag file found: ${header.CamelFilePath}")

                        // Поиск соотвутствующего *.txt-файла
                        .log(INFO, log, "Load file: ${file:name.noext}.txt")
                        .pollEnrich().simple(String.format("file://%s?fileName=${file:name.noext}.txt&delete=true", folders.getWork()))

                        // Валдиация JSON
                        .log(INFO, log, "Validate file body via JSON-schema")
                        .to(String.format("json-validator:%s", jsonSchema))

                        // Перенос файла в целевую директорию
                        .log(INFO, log, String.format("Move file to %s/${header.CamelFileName}", folders.getData()))
                        .to(String.format("file://%s", folders.getData()))

                        // Преобразование в объект
                        .log(INFO, log, "Convert JSON ${body} to POJO")
                        .convertBodyTo(SubscriberDto.class)

                        // Запрос места работы по имени и фамилии
                        .log(INFO, log, "Query work info for " + "${body.lastname}" + " " + "${body.firstname}")
                        .to("sql: select * from work_place " +
                                "where lastname = :#${body.lastname} and firstname = :#${body.firstname}" +
                                "?outputHeader=workMap")

                        // Заполнение поля work, если возможно
                        .log(INFO, log, "Check subscriber's work info")
                        .choice()
                            .when(simple("${header.workMap.size()} > 0"))
                                .log(INFO, log, "Set work to ${body.lastname} ${body.firstname}")
                                .setHeader("subscriberWork", simple("${header.workMap[0][WORK_PLACE]}; ${header.workMap[0][WORK_ADDRESS]}"))
                                .process(new SetWorkProcessor("subscriberWork"))
                            .otherwise()
                                .log(INFO, log, "Work for ${body.lastname} ${body.firstname} is empty")
                        .end()

                        // Обновление файла в целевой директории
                        .log(INFO, log, String.format("Update file %s/${header.CamelFileName}", folders.getData()))
                        .to(String.format("file://%s", folders.getData()))

                        // Сохранение в БД
                        .log(INFO, log, "Save ${body} to database")
                        .bean(SubscriberRepositoryService.class, "save")

                        .log(INFO, log, "Subscriber processed");
            }
        };
    }

}
