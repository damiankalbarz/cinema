package com.example.cinemaservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CinemaDto {
    private int id;
    private String cinemaName;
    private String location;
    private List<RoomDto> roomList;
    private List<EmployeeDto> employeeList;
}
