package com.example.filmservice.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FilmDto {
    private String id;
    @NotBlank(message = "Tytuł nie może być pusty")
    private String title;

    @NotBlank(message = "Autor nie może być pusty")
    private String author;

    @NotBlank(message = "Kategoria nie może być pusta")
    private String category;
}
