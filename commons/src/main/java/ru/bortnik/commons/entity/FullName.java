package ru.bortnik.commons.entity;

import lombok.*;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@Data
public class FullName implements Serializable {

    private String firstName;
    private String secondName;
}
