package com.example.cinemaservice.service;

import com.example.cinemaservice.model.Cinema;
import com.example.cinemaservice.model.Room;
import com.example.cinemaservice.repository.CinemaRepository;
import jakarta.persistence.EntityNotFoundException;
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

    public Cinema fetchCinemaById(int id){
        return cinemaRepository.findById(id).orElse(null);
    }

    public Cinema addRoomToCinema(int id, Room room) {
        Cinema cinema = cinemaRepository.findById(id).orElse(null);;
        if (cinema == null) {
            // Handle the case where the cinema is not found based on the given ID.
            // You may throw an exception, return null, or take appropriate action.
            throw new EntityNotFoundException("Cinema not found with ID: " + id);
        }
        System.out.println(cinema);
        cinema.addRoom(room);

        return cinemaRepository.save(cinema);
    }
}
