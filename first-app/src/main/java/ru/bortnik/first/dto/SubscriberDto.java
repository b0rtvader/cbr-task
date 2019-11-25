package ru.bortnik.first.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;

@AllArgsConstructor
@RequiredArgsConstructor
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
