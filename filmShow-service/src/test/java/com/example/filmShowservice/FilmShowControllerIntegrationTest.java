package com.example.filmShowservice;

import com.example.filmShowservice.model.FilmShow;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDateTime;

@SpringBootTest
@AutoConfigureMockMvc
class FilmShowControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void fetchFilmShowById() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/filmShow/{id}", 1))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void createFilmShow() throws Exception {
        FilmShow filmShow = new FilmShow();
        filmShow.setDateTime(LocalDateTime.now());
        filmShow.setCinemaId(0);
        filmShow.setFilmId("65e8b483654fbc1b37aca803");
        filmShow.setRoomId(1);

        mockMvc.perform(MockMvcRequestBuilders.post("/filmShow")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(filmShow)))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void fetchFilmShow() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/filmShow"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void getAllFilmShow() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/filmShow/detail"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void reserveSeats() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/filmShow/{filmShowId}/reserve", 52)
                .param("numberOfSeatsToReserve", "1"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }


}
