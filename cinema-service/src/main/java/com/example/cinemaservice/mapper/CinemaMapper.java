package com.example.cinemaservice.mapper;

import com.example.cinemaservice.dto.CinemaDto;
import com.example.cinemaservice.model.Cinema;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring", uses = {RoomMapper.class, EmployeeMapper.class})
public interface CinemaMapper {

    CinemaDto toCinemaDto(Cinema cinema);

    Cinema toCinema(CinemaDto cinemaDto);

    List<CinemaDto> toCinemaDtos(List<Cinema> cinemas);

    void updateCinema(@MappingTarget Cinema target, Cinema source);
}
