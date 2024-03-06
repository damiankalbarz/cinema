package com.example.filmShowservice.repository;

import com.example.filmShowservice.model.FilmShow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FilmShowRepositry extends JpaRepository<FilmShow, Integer> {
}
