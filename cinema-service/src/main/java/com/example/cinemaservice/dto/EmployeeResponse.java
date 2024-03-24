package com.example.cinemaservice.dto;

import com.example.cinemaservice.model.Cinema;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
public class EmployeeResponse {
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
