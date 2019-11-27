package ru.bortnik.project.configuration;

import com.fasterxml.jackson.databind.JsonNode;
import com.github.fge.jackson.JsonLoader;
import com.github.fge.jsonschema.core.exceptions.ProcessingException;
import com.github.fge.jsonschema.main.JsonSchema;
import com.github.fge.jsonschema.main.JsonSchemaFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

@Configuration
public class SchemaConfiguration {

    @Value("${app.json-schema}")
    private String schema;

    @Bean
    public JsonSchema jsonScjema() throws IOException, ProcessingException {
        JsonNode node = JsonLoader.fromResource(schema);
        JsonSchemaFactory factory = JsonSchemaFactory.byDefault();
        return  factory.getJsonSchema(node);
    }

}
