package com.example.filmservice;

import com.example.filmservice.controller.FilmController;
import com.example.filmservice.model.Film;
import com.example.filmservice.service.FilmService;
import jakarta.validation.ValidationException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FilmControllerTest {

    @Mock
    private FilmService filmService;

    @InjectMocks
    private FilmController filmController;

    @Test
    void getAllFilms() {
        Film film = new Film("1", "Title", "Author", "Category");
        List<Film> films = Collections.singletonList(film);
        when(filmService.getAllFilms()).thenReturn(films);

        ResponseEntity<List<Film>> response = filmController.getAllFilms();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(films, response.getBody());
    }

    @Test
    void getFilmById_existingFilm() {

        Film film = new Film("1", "Title", "Author", "Category");
        when(filmService.getFilmById("1")).thenReturn(Optional.of(film));

        ResponseEntity<Film> response = filmController.getFilmById("1");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(film, response.getBody());
    }

    @Test
    void getFilmById_nonExistingFilm() {
        when(filmService.getFilmById("1")).thenReturn(Optional.empty());

        ResponseEntity<Film> response = filmController.getFilmById("1");

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    void saveFilm_validFilm() {

        Film filmToSave = new Film(null, "Title", "Author", "Category");
        Film savedFilm = new Film("1", "Title", "Author", "Category");
        when(filmService.saveFilm(filmToSave)).thenReturn(savedFilm);

        ResponseEntity<Film> response = filmController.saveFilm(filmToSave);


        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(savedFilm, response.getBody());
    }

    @Test
    void saveFilm_invalidFilm() {
        // Given
        Film filmToSave = new Film(null, null, "Author", "Category");

        // When/Then
        assertThrows(ValidationException.class, () -> filmController.saveFilm(filmToSave));
        verifyNoInteractions(filmService);
    }

    @Test
    void deleteFilm() {

        String filmId = "1";

        ResponseEntity<Void> response = filmController.deleteFilm(filmId);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(filmService, times(1)).deleteFilm(filmId);
    }
}
