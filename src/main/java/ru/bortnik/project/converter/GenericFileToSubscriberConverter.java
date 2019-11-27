package ru.bortnik.project.converter;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.apache.camel.Converter;
import org.apache.camel.TypeConverters;
import org.apache.camel.component.file.GenericFile;
import org.springframework.stereotype.Component;
import ru.bortnik.project.dto.SubscriberDto;

import java.io.File;
import java.io.IOException;

@Component
@RequiredArgsConstructor
public class GenericFileToSubscriberConverter implements TypeConverters {

    private final ObjectMapper objectMapper;

    @Converter
    public SubscriberDto convert(GenericFile genericFile) throws IOException {
        return objectMapper.readValue((File) genericFile.getBody(), SubscriberDto.class);
    }
}
