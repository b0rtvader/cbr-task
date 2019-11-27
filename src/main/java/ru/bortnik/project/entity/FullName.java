package ru.bortnik.project.entity;

import lombok.Data;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@Data
public class FullName implements Serializable {

    private String lastname;
    private String firstname;
}
