package com.example.cinemaservice;

import com.example.cinemaservice.controller.CinemaController;
import com.example.cinemaservice.model.Cinema;
import com.example.cinemaservice.model.Room;
import com.example.cinemaservice.service.CinemaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class CinemaControllerTest {

    @Mock
    private CinemaService cinemaService;

    @InjectMocks
    private CinemaController cinemaController;

    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(cinemaController).build();
    }

    @Test
    public void testAddCinema() throws Exception {
        Cinema cinema = new Cinema(1, "Cinema 1", "Location 1",null,null);

        when(cinemaService.addCinema(any(Cinema.class))).thenReturn(cinema);

        mockMvc.perform(MockMvcRequestBuilders.post("/cinema")
                .content("{ \"cinemaName\": \"Cinema 1\", \"location\": \"Location 1\" }")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.cinemaName").value("Cinema 1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.location").value("Location 1"));
    }

    @Test
    public void testFetchCinemas() throws Exception {
        List<Cinema> cinemas = new ArrayList<>();
        cinemas.add(new Cinema(1, "Cinema 1", "Location 1",null,null));
        cinemas.add(new Cinema(2, "Cinema 2", "Location 2",null,null));

        when(cinemaService.fetchCinema()).thenReturn(cinemas);

        mockMvc.perform(MockMvcRequestBuilders.get("/cinema")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()").value(cinemas.size()));
    }

    @Test
    public void testFetchCinemaById() throws Exception {
        Cinema cinema = new Cinema(1, "Cinema 1", "Location 1", null, null);

        when(cinemaService.fetchCinemaById(1)).thenReturn(cinema);

        mockMvc.perform(MockMvcRequestBuilders.get("/cinema/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.cinemaName").value("Cinema 1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.location").value("Location 1"));
    }

    @Test
    public void testAddRoomToCinema() throws Exception {
        Cinema cinema = new Cinema(1, "Cinema 1", "Location 1",null, null);
        Room room = new Room();
        room.setId(1);
        //room.setRoomName("Room 1");

        when(cinemaService.addRoomToCinema(1, room)).thenReturn(cinema);

        mockMvc.perform(MockMvcRequestBuilders.post("/cinema/room")
                .content("{ \"roomName\": \"Room 1\" }")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.cinemaName").value("Cinema 1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.location").value("Location 1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.roomList[0].roomName").value("Room 1"));
    }
}
