package com.example.filmShowservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FilmShowResponse {
    private int id;
    private LocalDateTime dateTime;
    private Cinema cinema;
    private Film film;
}
