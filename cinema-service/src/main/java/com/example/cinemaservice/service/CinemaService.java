package com.example.cinemaservice.service;

import com.example.cinemaservice.dto.CinemaDto;
import com.example.cinemaservice.mapper.CinemaMapper;
import com.example.cinemaservice.model.Cinema;
import com.example.cinemaservice.model.Employee;
import com.example.cinemaservice.model.Room;
import com.example.cinemaservice.repository.CinemaRepository;
import com.example.cinemaservice.repository.EmployeeRepository;
import com.example.cinemaservice.repository.RoomRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CinemaService {
    @Autowired
    private CinemaRepository cinemaRepository;

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private CinemaMapper cinemaMapper;


    public ResponseEntity<CinemaDto> addCinema(CinemaDto cinemaDto) {
        Cinema cinema = cinemaMapper.toCinema(cinemaDto);
        Cinema savedCinema = cinemaRepository.save(cinema);
        return new ResponseEntity<>(cinemaDto, HttpStatus.CREATED);
    }

    public ResponseEntity<List<CinemaDto>> fetchAllCinemas() {
        List<Cinema> cinemas = cinemaRepository.findAll();
        List<CinemaDto> cinemaDtos = cinemaMapper.toCinemaDtos(cinemas);
        return new ResponseEntity<>(cinemaDtos, HttpStatus.OK);
    }

    public ResponseEntity<CinemaDto> fetchCinemaById(int id) {
        Cinema cinema = cinemaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Cinema not found with ID: " + id));
        CinemaDto cinemaDto = cinemaMapper.toCinemaDto(cinema);
        return new ResponseEntity<>(cinemaDto, HttpStatus.OK);
    }

    public ResponseEntity<Void> updateCinema(int cinemaId, CinemaDto cinemaDto) {
        Cinema existingCinema = cinemaRepository.findById(cinemaId)
                .orElseThrow(() -> new EntityNotFoundException("Cinema not found with ID: " + cinemaId));
        Cinema updatedCinema = cinemaMapper.toCinema(cinemaDto);
        cinemaMapper.updateCinema(existingCinema, updatedCinema);
        cinemaRepository.save(existingCinema);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    public ResponseEntity<CinemaDto> patchCinema(int id, CinemaDto cinemaDto) {
        Cinema cinema = cinemaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cinema not found with ID: " + id));

        if (cinemaDto.getCinemaName() != null) {
            cinema.setCinemaName(cinemaDto.getCinemaName());
        }
        if (cinemaDto.getLocation() != null) {
            cinema.setLocation(cinemaDto.getLocation());
        }

        Cinema updatedCinema = cinemaRepository.save(cinema);
        CinemaDto updatedCinemaDto = cinemaMapper.toCinemaDto(updatedCinema);
        return new ResponseEntity<>(updatedCinemaDto, HttpStatus.OK);
    }


    public ResponseEntity<String> deleteCinemaById(int id) {
        if (!cinemaRepository.existsById(id)) {
            throw new EntityNotFoundException("Cinema not found with ID: " + id);
        }
        cinemaRepository.deleteById(id);
        return new ResponseEntity<>("Cinema deleted successfully", HttpStatus.OK);
    }



    public ResponseEntity<String> addRoomToCinema(int cinemaId, int roomId) {
        Cinema cinema = cinemaRepository.findById(cinemaId)
                .orElseThrow(() -> new EntityNotFoundException("Cinema not found with ID: " + cinemaId));

        Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> new EntityNotFoundException("Room not found with ID: " + roomId));

        if (!cinema.getRoomList().contains(room)) {
            cinema.getRoomList().add(room);
            room.setCinema(cinema);
        }

        cinemaRepository.save(cinema);
        return new ResponseEntity<>("Room added to cinema successfully", HttpStatus.OK);
    }

    public ResponseEntity<String> addEmployeeToCinema(int cinemaId, int employeeId) {
        Cinema cinema = cinemaRepository.findById(cinemaId)
                .orElseThrow(() -> new EntityNotFoundException("Cinema not found with ID: " + cinemaId));

        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new EntityNotFoundException("Employee not found with ID: " + employeeId));

        if (!cinema.getEmployeeList().contains(employee)) {
            cinema.getEmployeeList().add(employee);
            employee.setCinema(cinema);
        }
        cinemaRepository.save(cinema);

        return new ResponseEntity<>("Employee added to cinema successfully", HttpStatus.OK);
    }





}
