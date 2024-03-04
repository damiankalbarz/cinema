package com.example.clientservice.dto;


import com.example.clientservice.dto.Cinema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClientResponse {
    private String id;
    private String name;
    private String surname;
    private int age;
    private String gender;
    private Cinema cinema;
}
