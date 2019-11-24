package ru.bortnik.camel.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.*;
import java.time.LocalDate;

@Data
public class SubscriberDto {

    private String lastname;
    private String firstname;
    private String workphone;
    private String mobilephone;
    private String mail;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd.MM.yyyy")
    private LocalDate birthdate;
    private String work;
}
