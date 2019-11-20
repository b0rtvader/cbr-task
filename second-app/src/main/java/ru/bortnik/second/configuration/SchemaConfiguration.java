package ru.bortnik.second.configuration;

import org.everit.json.schema.Schema;
import org.everit.json.schema.loader.SchemaLoader;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SchemaConfiguration {

    @Value("${app.json-schema}")
    private String schema;

    @Bean
    public Schema jsonSchema() {
        JSONObject schemaObject = new JSONObject(new JSONTokener(SchemaConfiguration.class.getResourceAsStream(schema)));
        return SchemaLoader.load(schemaObject);
    }
}
