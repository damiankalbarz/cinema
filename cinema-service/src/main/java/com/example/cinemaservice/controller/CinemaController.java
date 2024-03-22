package com.example.cinemaservice.controller;

import com.example.cinemaservice.model.Cinema;
import com.example.cinemaservice.model.Room;
import com.example.cinemaservice.service.CinemaService;
import jakarta.validation.Valid;
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
    public Cinema addCinema(@Valid @RequestBody Cinema cinema){
        return cinemaService.addCinema(cinema);
    }

    @GetMapping
    public List<Cinema> fetchCinemas(){
        return cinemaService.fetchCinema();
    }

    @GetMapping("/{id}")
    public Cinema fetchCinemaById(@PathVariable int id){
        return cinemaService.fetchCinemaById(id);
    }

    @PostMapping("/room")
    public Cinema addRoomToCinema(@PathVariable int cinemaId,@Valid @RequestBody Room room)
    {
        return cinemaService.addRoomToCinema(cinemaId,room);
    }



}
