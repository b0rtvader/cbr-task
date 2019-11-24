package ru.bortnik.camel.configuration;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.bortnik.camel.dto.SubscriberDto;
import ru.bortnik.camel.processor.SetWorkProcessor;
import ru.bortnik.camel.service.SubscriberService;

import static org.apache.camel.LoggingLevel.*;

@Configuration
@EnableConfigurationProperties(CamelProperties.class)
@RequiredArgsConstructor
@Slf4j
public class Route {

    private final CamelProperties camelProperties;

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

                from(String.format("file://%s?include=.*.ready&delete=true", camelProperties.getSourceDirectory()))
                        .log(INFO, log, "Flag file found: ${header.CamelFilePath}")

                        .log(INFO, log, "Load file: ${file:name.noext}.txt")
                        .pollEnrich().simple(String.format("file://%s?fileName=${file:name.noext}.txt&delete=true", camelProperties.getSourceDirectory()))

                        .log(INFO, log, "Validate file body via JSON-schema")
                        .to(String.format("json-validator:%s", camelProperties.getJsonSchema()))

                        .log(INFO, log, String.format("Move file to %s/${header.CamelFileName}", camelProperties.getTargetDirectory()))
                        .to(String.format("file://%s", camelProperties.getTargetDirectory()))

                        .log(INFO, log, "Convert JSON ${body} to POJO")
                        .convertBodyTo(SubscriberDto.class)

                        .log(INFO, log, "Query work info for " + "${body.lastname}" + " " + "${body.firstname}")
                        .to("sql: select * from work_place " +
                                "where lastname = :#${body.lastname} and firstname = :#${body.firstname}" +
                                "?outputHeader=workMap")

                        .log(INFO, log, "Check subscriber's work info")
                        .choice()
                            .when(simple("${header.workMap.size()} > 0"))
                                .log(INFO, log, "Set work to ${body.lastname} ${body.firstname}")
                                .setHeader("subscriberWork", simple("${header.workMap[0][WORK_PLACE]}; ${header.workMap[0][WORK_ADDRESS]}"))
                                .process(new SetWorkProcessor("subscriberWork"))
                            .otherwise()
                                .log(INFO, log, "Work for ${body.lastname} ${body.firstname} is empty")
                        .end()

                        .log(INFO, log, String.format("Update file %s/${header.CamelFileName}", camelProperties.getTargetDirectory()))
                        .to(String.format("file://%s", camelProperties.getTargetDirectory()))

                        .log(INFO, log, "Save ${body} to database")
                        .bean(SubscriberService.class, "save")

                        .log(INFO, log, "Subscriber processed");
            }
        };
    }

}
