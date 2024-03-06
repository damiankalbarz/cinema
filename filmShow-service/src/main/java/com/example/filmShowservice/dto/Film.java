package com.example.filmShowservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Film{
    private String id;
    private String title;
    private String author;
    private String category;
}