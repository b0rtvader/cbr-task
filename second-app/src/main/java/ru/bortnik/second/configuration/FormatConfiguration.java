package ru.bortnik.second.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.time.format.DateTimeFormatter;

@Configuration
public class FormatConfiguration {

    @Value("${app.datetime-format:ddMMyy_HHmm}")
    private String format;

    @Bean
    public DateTimeFormatter formatter() {
        return DateTimeFormatter.ofPattern(format);
    }
}
