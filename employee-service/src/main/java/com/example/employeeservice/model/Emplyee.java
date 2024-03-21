package com.example.employeeservice.model;

import lombok.Data;

@Data
@Entity
public class Emplyee {
    private int id;
    private String name;
    private String surname;
    private String position;
    private String phone;
    private Double salary;
    private int holidaysDays;

}
