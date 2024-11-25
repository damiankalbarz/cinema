package com.example.cinemaservice.service;

import com.example.cinemaservice.dto.RoomDto;
import com.example.cinemaservice.mapper.RoomMapper;
import com.example.cinemaservice.model.Room;
import com.example.cinemaservice.repository.RoomRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoomService {
    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private RoomMapper roomMapper;

    // Dodanie pokoju (Room)
    public ResponseEntity<RoomDto> addRoom(RoomDto roomDto) {
        Room room = roomMapper.toRoom(roomDto);
        Room savedRoom = roomRepository.save(room);
        return new ResponseEntity<>(roomMapper.toRoomDto(savedRoom), HttpStatus.CREATED);
    }

    // Pobranie wszystkich pokoi
    public ResponseEntity<List<RoomDto>> fetchAllRooms() {
        List<Room> rooms = roomRepository.findAll();
        List<RoomDto> roomDtos = roomMapper.toRoomDtos(rooms);
        return new ResponseEntity<>(roomDtos, HttpStatus.OK);
    }

    // Pobranie pokoju po ID
    public ResponseEntity<RoomDto> fetchRoomById(int id) {
        Room room = roomRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Room not found with ID: " + id));
        RoomDto roomDto = roomMapper.toRoomDto(room);
        return new ResponseEntity<>(roomDto, HttpStatus.OK);
    }

    // Aktualizacja pokoju
    public ResponseEntity<Void> updateRoom(int roomId, RoomDto roomDto) {
        Room existingRoom = roomRepository.findById(roomId)
                .orElseThrow(() -> new EntityNotFoundException("Room not found with ID: " + roomId));
        Room updatedRoom = roomMapper.toRoom(roomDto);
        updatedRoom.setId(existingRoom.getId()); // Utrzymanie ID istniejącego pokoju
        roomRepository.save(updatedRoom);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // Usunięcie pokoju
    public ResponseEntity<String> deleteRoomById(int id) {
        if (!roomRepository.existsById(id)) {
            throw new EntityNotFoundException("Room not found with ID: " + id);
        }
        roomRepository.deleteById(id);
        return new ResponseEntity<>("Room deleted successfully", HttpStatus.OK);
    }
}
