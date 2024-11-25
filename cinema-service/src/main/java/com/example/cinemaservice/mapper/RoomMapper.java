package com.example.cinemaservice.mapper;

import com.example.cinemaservice.dto.RoomDto;
import com.example.cinemaservice.model.Room;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RoomMapper {

    RoomDto toRoomDto(Room room);

    Room toRoom(RoomDto roomDto);

    List<RoomDto> toRoomDtos(List<Room> rooms);
}
