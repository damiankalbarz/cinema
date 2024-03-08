package com.example.filmShowservice.dto;

import lombok.Data;
import org.springframework.data.annotation.Id;

@Data
public class Client {
    private String id;
    private String name;
    private String surname;
    private int age;
    private String gender;
    private Integer cinemaId;
}
