package ru.bortnik.project.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;

@Entity
@IdClass(FullName.class)
@Setter
@Getter
public class WorkPlace {

    @Id
    private String lastname;
    @Id
    private String firstname;
    private String workPlace;
    private String workAddress;
}
