package com.example.cinemaservice.controller;

import com.example.cinemaservice.model.Cinema;
import com.example.cinemaservice.model.Room;
import com.example.cinemaservice.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping(value = "/cinema/room")
public class RoomController {
    @Autowired
    RoomService roomService;

    @GetMapping("/all")
    public List<Room> fetchRooms(){return roomService.fetchRoom();}
}
