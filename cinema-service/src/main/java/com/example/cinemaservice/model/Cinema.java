package com.example.cinemaservice.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Data
@Table(name = "cinema")
public class Cinema {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "cinema_id")
    private int id;
    @NotBlank(message = "Nazwa kina nie może być pusta")
    private String cinemaName;

    @NotBlank(message = "Lokalizacja kina nie może być pusta")
    private String location;


    @OneToMany(mappedBy = "cinema", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<Room> roomList= new ArrayList<>();

    @OneToMany(mappedBy = "cinema", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<Employee> employeeList = new ArrayList<>();

}