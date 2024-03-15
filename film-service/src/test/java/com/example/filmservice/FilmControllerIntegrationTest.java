package com.example.filmservice;

import com.example.filmservice.model.Film;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/*
@SpringBootTest
@AutoConfigureMockMvc
class FilmControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void getAllFilms() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/film"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").isArray());
    }

    @Test
    void getFilmById_existingFilm() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/film/{id}", "65e8b483654fbc1b37aca803"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists());
    }

    @Test
    void getFilmById_nonExistingFilm() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/film/{id}", 999))
                .andExpect(status().isNotFound());
    }

    @Test
    void saveFilm_validFilm() throws Exception {
        String filmJson = "{ \"title\": \"Test Title\", \"author\": \"Test Author\", \"category\": \"Test Category\" }";
        mockMvc.perform(MockMvcRequestBuilders.post("/film")
                .contentType(MediaType.APPLICATION_JSON)
                .content(filmJson))
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists());
    }

    @Test
    void saveFilm_invalidFilm() throws Exception {
        String invalidFilmJson = "{ \"author\": \"Test Author\", \"category\": \"Test Category\" }";
        mockMvc.perform(MockMvcRequestBuilders.post("/film")
                .contentType(MediaType.APPLICATION_JSON)
                .content(invalidFilmJson))
                .andExpect(status().isBadRequest());
    }

    @Test
    void deleteFilm() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/film/{id}", 1))
                .andExpect(status().isNoContent());
    }
}
*/