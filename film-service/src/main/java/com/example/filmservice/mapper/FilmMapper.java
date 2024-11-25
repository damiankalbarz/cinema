package com.example.filmservice.mapper;

import com.example.filmservice.dto.FilmDto;
import com.example.filmservice.model.Film;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface FilmMapper {
    FilmDto toFilmDto(Film film);

    Film toFilm(FilmDto filmDto);

    List<FilmDto> toFilmDtos(List<Film> films);
}
