package com.example.cinemaservice.service;

import com.example.cinemaservice.EmployeeConsumer;
import com.example.cinemaservice.dto.EmployeeResponse;
import com.example.cinemaservice.model.Cinema;
import com.example.cinemaservice.model.Employee;
import com.example.cinemaservice.model.Room;
import com.example.cinemaservice.repository.CinemaRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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
        Cinema cinema = cinemaRepository.findById(id).orElse(null);
        if (cinema == null) {
            throw new EntityNotFoundException("Cinema not found with ID: " + id);
        }
        //System.out.println(cinema);
        cinema.addRoom(room);

        return cinemaRepository.save(cinema);
    }

/*
    @Transactional
    public Cinema addEmployeeToCinema(Employee employee, int id){
        Cinema cinema = cinemaRepository.findById(id).orElse(null);
        System.out.println(cinema);
        if (cinema == null) {
            throw new EntityNotFoundException("Cinema not found with ID: " + employee.getCinema().getId());
        }
        cinema.addEmployee(employee);
        System.out.println(employee);
        System.out.println(cinema);
        return  cinemaRepository.save(cinema);
    }
*/
    //@Transactional
    public void addEmployeeToCinema(Employee employee, int cinemaId) {
        Optional<Cinema> optionalCinema = cinemaRepository.findById(cinemaId);
        if (optionalCinema.isPresent()) {
            Cinema cinema = optionalCinema.get();
            cinema.addEmployee(employee); // Dodanie pracownika do kina
            cinemaRepository.save(cinema); // Zapisanie zmian w bazie danych
        } else {
            throw new EmptyResultDataAccessException(1);
        }
    }


}
