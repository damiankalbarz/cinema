package com.example.cinemaservice.service;


import com.example.cinemaservice.model.Room;
import com.example.cinemaservice.repository.CinemaRepository;
import com.example.cinemaservice.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoomService {
    @Autowired
    private RoomRepository roomRepository;

    public Room addRoom(Room room){ return roomRepository.save(room);}

    public List<Room> fetchRoom() { return roomRepository.findAll();}

    public Room fetchRoomById(int id){return  roomRepository.findById(id).orElse(null);}




}
