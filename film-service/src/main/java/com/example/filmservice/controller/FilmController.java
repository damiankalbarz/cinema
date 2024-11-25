package com.example.filmservice.controller;

import com.example.filmservice.dto.FilmDto;
import com.example.filmservice.model.Film;
import com.example.filmservice.service.FilmService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.*;



@RestController
@RequestMapping("/api/v1/film")
public class FilmController {

    @Autowired
    private FilmService filmService;

    @GetMapping
    public ResponseEntity<List<FilmDto>> getAllFilms() {
        return filmService.getAllFilms();
    }

    @GetMapping("/{id}")
    public ResponseEntity<FilmDto> getFilmById(@PathVariable String id) {
        return filmService.getFilmById(id);
    }

    @GetMapping("/category/{category}")
    public ResponseEntity<List<FilmDto>> getFilmsByCategory(@PathVariable String category) {
        return filmService.getFilmsByCategory(category);
    }

    @PostMapping
    public ResponseEntity<FilmDto> createFilm(@Valid @RequestBody FilmDto filmDto) {
        return filmService.saveFilm(filmDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFilm(@PathVariable String id) {
        return filmService.deleteFilm(id);
    }

    /*
    @GetMapping(value = "/{id}/image/stream", produces = MediaType.IMAGE_JPEG_VALUE)
    public void getFilmImageAsStream(@PathVariable String id, HttpServletResponse response) throws IOException {
        Optional<Film> optionalFilm = filmService.getFilmById(id);
        if (optionalFilm.isEmpty() || optionalFilm.get().getImage() == null) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        }
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        response.getOutputStream().write(optionalFilm.get().getImage());
    }
    */


}
