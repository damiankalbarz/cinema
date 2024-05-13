package com.example.buffetservice.model;

import jakarta.persistence.*;
import lombok.Data;



@Entity
@Data
@Table(name = "companies")
public class Company {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String phone;
    private String email;
    private String accountNumber;
}