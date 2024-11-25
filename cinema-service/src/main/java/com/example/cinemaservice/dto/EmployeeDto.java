package com.example.cinemaservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeDto {
    private int id;
    private int cinemaId;
    private String name;
    private String surname;
    private String position;
    private String phone;
    private Double salary;
    private int holidaysDays;
    private int availableVacationDays;
}
