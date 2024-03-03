package com.example.cinemaservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CinemaService {
    @Autowired
    private CinemaRepository cinemaRepository;

    public Cinema addCinema(Cinema cinema){
        return cinemaRepository.save(cinema);
    }
    public List<Cinema> fetchCinema(){
        return cinemaRepository.findAll();
    }


}
