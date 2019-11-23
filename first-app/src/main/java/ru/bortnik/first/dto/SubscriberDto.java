package ru.bortnik.first.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class SubscriberDto {

    private String lastname;
    private String firstname;
    private String workphone;
    private String mobilephone;
    private String mail;
    private LocalDate birthdate;
    private String work;
}
