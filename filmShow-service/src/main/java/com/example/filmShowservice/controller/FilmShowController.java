package com.example.filmShowservice.controller;

import com.example.filmShowservice.model.FilmShow;
import com.example.filmShowservice.service.FilmShowService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("*")
@RestController
@RequestMapping("/filmShow")
public class FilmShowController {
    @Autowired
    private FilmShowService filmShowService;

    @GetMapping("/{id}")
    public ResponseEntity<?> fetchFilmShowById(@PathVariable int id){
        return filmShowService.fetchFilmShowById(id);
    }

    @PostMapping
    public ResponseEntity<?> createFilmShow(@Valid @RequestBody FilmShow filmShow){
        return filmShowService.create(filmShow);
    }

    @GetMapping()
    public ResponseEntity<?> fetchFilmShow(){
        return filmShowService.fetchFilmShow();
    }

    @GetMapping("/detail")
    public ResponseEntity<?> getAllFilmShow(){
        return filmShowService.getAllFilmShows();
    }

    @PostMapping("/{filmShowId}/reserve")
    public ResponseEntity<?> reserveSeats(@Valid @PathVariable int filmShowId,@Valid @RequestParam int numberOfSeatsToReserve) {
        return filmShowService.reserveSeats(filmShowId, numberOfSeatsToReserve);
    }


}
