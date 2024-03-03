package com.example.cinemaservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping(value = "/cinema")
public class CinemaController {
    @Autowired
    private CinemaService cinemaService;

    @PostMapping
    public Cinema addCinema(@RequestBody Cinema cinema){
        return cinemaService.addCinema(cinema);
    }

    @GetMapping
    public List<Cinema> fatchCinemas(){
        return cinemaService.fetchCinema();
    }
}
