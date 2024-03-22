package com.example.cinemaservice.dto;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class EmployeeResponse {
    private int id;
    private String name;
    private String surname;
    private String position;
    private String phone;
    private Double salary;
    private int holidaysDays;
    private int availableVacationDays;
}
