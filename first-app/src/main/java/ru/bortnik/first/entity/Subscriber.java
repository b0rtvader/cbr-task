package ru.bortnik.first.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import java.time.LocalDate;

@Entity
@IdClass(FullName.class)
@AllArgsConstructor
@RequiredArgsConstructor
@Getter
@Setter
public class Subscriber {

    @Id
    private String lastname;
    @Id
    private String firstname;
    private String workphone;
    private String mobilephone;
    private String mail;
    private LocalDate birthdate;
    private String work;
}
