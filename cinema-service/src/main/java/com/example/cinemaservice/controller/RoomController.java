package com.example.cinemaservice.controller;

import com.example.cinemaservice.dto.RoomDto;
import com.example.cinemaservice.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/rooms")
public class RoomController {

    @Autowired
    private RoomService roomService;

    @PostMapping
    public ResponseEntity<RoomDto> addRoom(@RequestBody RoomDto roomDto) {
        return roomService.addRoom(roomDto);
    }

    @GetMapping
    public ResponseEntity<List<RoomDto>> fetchAllRooms() {
        return roomService.fetchAllRooms();
    }

    @GetMapping("/{id}")
    public ResponseEntity<RoomDto> fetchRoomById(@PathVariable int id) {
        return roomService.fetchRoomById(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateRoom(@PathVariable int id, @RequestBody RoomDto roomDto) {
        return roomService.updateRoom(id, roomDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteRoom(@PathVariable int id) {
        return roomService.deleteRoomById(id);
    }
}
