package com.example.filmservice.model;


import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "films")
public class Film {
    @Id
    private String id;
    @NotBlank(message = "Tytuł nie może być pusty")
    private String title;

    @NotBlank(message = "Autor nie może być pusty")
    private String author;

    @NotBlank(message = "Kategoria nie może być pusta")
    private String category;

    //private byte[] image;
}
