package ru.bortnik.camel.configuration;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "app.camel")
@Getter
@Setter
public class CamelProperties {

    private String sourceDirectory;
    private String targetDirectory;
    private String jsonSchema;
}
