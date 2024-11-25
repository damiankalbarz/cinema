package com.example.filmservice.controller;

import com.example.filmservice.dto.FilmDto;
import com.example.filmservice.model.Film;
import com.example.filmservice.repository.FilmRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class FilmControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private FilmRepository filmRepository;

    @BeforeEach
    void setUp() {
        filmRepository.deleteAll();
        filmRepository.save(new Film("1", "Inception", "Christopher Nolan", "Sci-Fi"));
        filmRepository.save(new Film("2", "The Godfather", "Francis Ford Coppola", "Crime"));
    }

    @Test
    void shouldGetAllFilms() throws Exception {
        mockMvc.perform(get("/api/v1/film")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].title", is("Inception")))
                .andExpect(jsonPath("$[1].author", is("Francis Ford Coppola")));
    }

    @Test
    void shouldGetFilmById() throws Exception {
        mockMvc.perform(get("/api/v1/film/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title", is("Inception")))
                .andExpect(jsonPath("$.category", is("Sci-Fi")));
    }

    @Test
    void shouldReturnNotFoundWhenFilmDoesNotExist() throws Exception {
        mockMvc.perform(get("/api/v1/film/99")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldCreateFilm() throws Exception {
        String newFilm = """
                {
                    "title": "Interstellar",
                    "author": "Christopher Nolan",
                    "category": "Sci-Fi"
                }
                """;

        mockMvc.perform(post("/api/v1/film")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(newFilm))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title", is("Interstellar")))
                .andExpect(jsonPath("$.author", is("Christopher Nolan")))
                .andExpect(jsonPath("$.category", is("Sci-Fi")));
    }

    @Test
    void shouldDeleteFilm() throws Exception {
        mockMvc.perform(delete("/api/v1/film/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        mockMvc.perform(get("/api/v1/film/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldReturnNotFoundWhenDeletingNonExistentFilm() throws Exception {
        mockMvc.perform(delete("/api/v1/film/99")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}
