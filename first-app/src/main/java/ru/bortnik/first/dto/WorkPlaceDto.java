package ru.bortnik.first.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@AllArgsConstructor
@RequiredArgsConstructor
@Data
public class WorkPlaceDto {

    private String lastname;
    private String firstname;
    private String workPlace;
    private String workAddress;
}
