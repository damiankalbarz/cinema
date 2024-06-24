package com.example.filmservice.controller;

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
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/film")
public class FilmController {
    @Autowired
    private FilmService filmService;

    @GetMapping
    public ResponseEntity<List<Film>> getAllFilms() {
        List<Film> films = filmService.getAllFilms();
        return new ResponseEntity<>(films, HttpStatus.OK);
    }


    @GetMapping("/category/{category}")
    public ResponseEntity<List<Film>> getFilmsByCategory(@PathVariable String category) {
        List<Film> films = filmService.getByCategory(category);
        return new ResponseEntity<>(films, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Film> getFilmById(@PathVariable String id) {
        Optional<Film> film = filmService.getFilmById(id);
        return film.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/auth")
    public ResponseEntity<Film> saveFilm(@Valid @RequestBody Film film) {
        Film savedFilm = filmService.saveFilm(film);
        return new ResponseEntity<>(savedFilm, HttpStatus.CREATED);
    }

    @DeleteMapping("/auth/{id}")
    public ResponseEntity<Void> deleteFilm(@PathVariable String id) {
        filmService.deleteFilm(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
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
