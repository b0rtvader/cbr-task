package ru.bortnik.camel;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

import static org.springframework.boot.WebApplicationType.*;

@SpringBootApplication
public class CamelApplication {

    public static void main(String[] args) throws Exception {
        new SpringApplicationBuilder(CamelApplication.class)
                .web(NONE)
                .run(args);
        Thread.sleep(Long.MAX_VALUE);
    }
}
