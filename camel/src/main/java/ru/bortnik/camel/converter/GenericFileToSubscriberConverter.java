package ru.bortnik.camel.converter;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.apache.camel.Converter;
import org.apache.camel.TypeConverters;
import org.apache.camel.component.file.GenericFile;
import org.springframework.stereotype.Component;
import ru.bortnik.camel.dto.SubscriberDto;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

@Component
@RequiredArgsConstructor
public class GenericFileToSubscriberConverter implements TypeConverters {

    private final ObjectMapper objectMapper;

    @Converter
    public SubscriberDto convert(GenericFile genericFile) throws IOException {
        return objectMapper.readValue((File) genericFile.getBody(), SubscriberDto.class);
    }
}
