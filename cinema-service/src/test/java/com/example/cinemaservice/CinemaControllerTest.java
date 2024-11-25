package com.example.cinemaservice;

import com.example.cinemaservice.controller.CinemaController;
import com.example.cinemaservice.dto.CinemaDto;
import com.example.cinemaservice.service.CinemaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
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
        CinemaDto cinemaDto = new CinemaDto(1, "Cinema 1", "Location 1", null, null);

        when(cinemaService.addCinema(any(CinemaDto.class)))
                .thenReturn(ResponseEntity.status(201).body(cinemaDto));

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/cinemas")
                        .content("{ \"cinemaName\": \"Cinema 1\", \"location\": \"Location 1\" }")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.cinemaName").value("Cinema 1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.location").value("Location 1"));
    }

    @Test
    public void testFetchAllCinemas() throws Exception {
        List<CinemaDto> cinemas = Arrays.asList(
                new CinemaDto(1, "Cinema 1", "Location 1", null, null),
                new CinemaDto(2, "Cinema 2", "Location 2", null, null)
        );

        when(cinemaService.fetchAllCinemas())
                .thenReturn(ResponseEntity.ok(cinemas));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/cinemas")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()").value(cinemas.size()));
    }

    @Test
    public void testFetchCinemaById() throws Exception {
        CinemaDto cinemaDto = new CinemaDto(1, "Cinema 1", "Location 1", null, null);

        when(cinemaService.fetchCinemaById(1))
                .thenReturn(ResponseEntity.ok(cinemaDto));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/cinemas/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.cinemaName").value("Cinema 1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.location").value("Location 1"));
    }

    @Test
    public void testUpdateCinema() throws Exception {
        when(cinemaService.updateCinema(any(Integer.class), any(CinemaDto.class)))
                .thenReturn(ResponseEntity.ok().build());

        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/cinemas/1")
                        .content("{ \"cinemaName\": \"Updated Cinema\", \"location\": \"Updated Location\" }")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testPatchCinema() throws Exception {
        CinemaDto updatedCinemaDto = new CinemaDto(1, "Updated Cinema", "Updated Location", null, null);

        when(cinemaService.patchCinema(any(Integer.class), any(CinemaDto.class)))
                .thenReturn(ResponseEntity.ok(updatedCinemaDto));

        mockMvc.perform(MockMvcRequestBuilders.patch("/api/v1/cinemas/1")
                        .content("{ \"cinemaName\": \"Updated Cinema\", \"location\": \"Updated Location\" }")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.cinemaName").value("Updated Cinema"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.location").value("Updated Location"));
    }

    @Test
    public void testDeleteCinema() throws Exception {
        when(cinemaService.deleteCinemaById(1))
                .thenReturn(ResponseEntity.ok("Cinema deleted successfully"));

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/cinemas/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("Cinema deleted successfully"));
    }

    @Test
    public void testAddRoomToCinema() throws Exception {
        when(cinemaService.addRoomToCinema(1, 1))
                .thenReturn(ResponseEntity.ok("Room added to cinema successfully"));

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/cinemas/1/rooms/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("Room added to cinema successfully"));
    }

    @Test
    public void testAddEmployeeToCinema() throws Exception {
        when(cinemaService.addEmployeeToCinema(1, 1))
                .thenReturn(ResponseEntity.ok("Employee added to cinema successfully"));

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/cinemas/1/employees/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("Employee added to cinema successfully"));
    }
}
