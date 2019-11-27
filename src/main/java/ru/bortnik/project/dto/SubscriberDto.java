package ru.bortnik.project.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.*;
import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Data
@StringFieldMatch(
        message = "Workphone and mobilephone prefixes are not equal",
        field = "workphone",
        matchTo = "mobilephone",
        length = 3
)
public class SubscriberDto {

    @NotNull
    @Size(min = 1, max = 19, message = "Lastname length must be in [1; 19]")
    private String lastname;

    @NotNull
    @Size(min = 1, max = 9, message = "Firstname length must be in [1; 9]")
    private String firstname;

    @NotNull
    @Pattern(regexp = "\\d{3}-\\d{3}-\\d{2}-\\d{2}", message = "Workphone format: nnn-ddd-dd-dd")
    private String workphone;

    @NotNull
    @Pattern(regexp = "\\d{3}-\\d{3}-\\d{2}-\\d{2}", message = "Mobilephone format: nnn-ddd-dd-dd")
    private String mobilephone;

    @NotNull
    @Email(regexp = "[^@]{1,29}@{1}.{1,9}")
    private String mail;

    @NotNull
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @PastOrPresent(message = "Birthdate can't be from future")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd.MM.yyyy")
    private LocalDate birthdate;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String work;
}