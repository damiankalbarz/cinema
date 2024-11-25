package com.example.cinemaservice.mapper;

import com.example.cinemaservice.dto.EmployeeDto;
import com.example.cinemaservice.model.Employee;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface EmployeeMapper {

    EmployeeDto toEmployeeDto(Employee employee);

    Employee toEmployee(EmployeeDto employeeDto);

    List<EmployeeDto> toEmployeeDtos(List<Employee> employees);
}
