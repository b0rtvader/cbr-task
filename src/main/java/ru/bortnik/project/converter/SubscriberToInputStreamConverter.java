package ru.bortnik.project.converter;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.apache.camel.Converter;
import org.apache.camel.TypeConverters;
import org.springframework.stereotype.Component;
import ru.bortnik.project.dto.SubscriberDto;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

@Component
@RequiredArgsConstructor
public class SubscriberToInputStreamConverter implements TypeConverters {

    private final ObjectMapper objectMapper;

    @Converter
    public InputStream convert(SubscriberDto dto) throws IOException {
        return new ByteArrayInputStream(objectMapper.writeValueAsBytes(dto));
    }
}
