package com.example.filmservice.service;

import com.example.filmservice.dto.FilmDto;
import com.example.filmservice.mapper.FilmMapper;
import com.example.filmservice.model.Film;
import com.example.filmservice.repository.FilmRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FilmService {

    @Autowired
    private FilmRepository filmRepository;

    @Autowired
    private FilmMapper filmMapper;


    public ResponseEntity<List<FilmDto>> getAllFilms() {
        List<Film> films = filmRepository.findAll();
        List<FilmDto> filmDtos = filmMapper.toFilmDtos(films);
        return ResponseEntity.ok(filmDtos);
    }

    public ResponseEntity<FilmDto> getFilmById(String id) {
        Optional<Film> film = filmRepository.findById(id);
        if (film.isPresent()) {
            FilmDto filmDto = filmMapper.toFilmDto(film.get());
            return ResponseEntity.ok(filmDto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    public ResponseEntity<List<FilmDto>> getFilmsByCategory(String category) {
        List<Film> films = filmRepository.findByCategory(category);
        List<FilmDto> filmDtos = filmMapper.toFilmDtos(films);
        return ResponseEntity.ok(filmDtos);
    }

    public ResponseEntity<FilmDto> saveFilm(FilmDto filmDto) {
        Film film = filmMapper.toFilm(filmDto);
        Film savedFilm = filmRepository.save(film);
        FilmDto savedFilmDto = filmMapper.toFilmDto(savedFilm);
        return ResponseEntity.ok(savedFilmDto);
    }

    public ResponseEntity<Void> deleteFilm(String id) {
        if (filmRepository.existsById(id)) {
            filmRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
