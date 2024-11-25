package com.example.cinemaservice.controller;

import com.example.cinemaservice.dto.CinemaDto;
import com.example.cinemaservice.service.CinemaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/cinemas")
public class CinemaController {

    @Autowired
    private CinemaService cinemaService;

    @PostMapping
    public ResponseEntity<CinemaDto> addCinema(@RequestBody CinemaDto cinemaDto) {
        return cinemaService.addCinema(cinemaDto);
    }

    @GetMapping
    public ResponseEntity<List<CinemaDto>> fetchAllCinemas() {
        return cinemaService.fetchAllCinemas();
    }

    @GetMapping("/{id}")
    public ResponseEntity<CinemaDto> fetchCinemaById(@PathVariable int id) {
        return cinemaService.fetchCinemaById(id);
    }

    @PutMapping("/{cinemaId}")
    public ResponseEntity<Void> updateCinema(@PathVariable int cinemaId, @RequestBody CinemaDto cinemaDto) {
        return cinemaService.updateCinema(cinemaId, cinemaDto);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<CinemaDto> patchCinema(@PathVariable int id, @RequestBody CinemaDto cinemaDto) {
        return cinemaService.patchCinema(id, cinemaDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCinema(@PathVariable int id) {
        return cinemaService.deleteCinemaById(id);
    }

    @PostMapping("/{cinemaId}/rooms/{roomId}")
    public ResponseEntity<String> addRoomToCinema(@PathVariable int cinemaId, @PathVariable int roomId) {
        return cinemaService.addRoomToCinema(cinemaId, roomId);
    }

    @PostMapping("/{cinemaId}/employees/{employeeId}")
    public ResponseEntity<String> addEmployeeToCinema(@PathVariable int cinemaId, @PathVariable int employeeId) {
        return cinemaService.addEmployeeToCinema(cinemaId, employeeId);
    }
}
