package com.example.cinemaservice;

import com.example.cinemaservice.dto.CinemaDto;
import com.example.cinemaservice.dto.EmployeeDto;
import com.example.cinemaservice.model.Cinema;
import com.example.cinemaservice.model.Employee;
import com.example.cinemaservice.service.CinemaService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.io.IOException;
/*
@Component
public class EmployeeConsumer {


    @Autowired
    CinemaService cinemaService;
    @RabbitListener(queues = "${rabbitmq.queue}")
    public void receiveMessage(byte[] messageBytes) {
        ObjectMapper objectMapper = new ObjectMapper();
        EmployeeDto employeeResponse = null;
        try {
            employeeResponse = objectMapper.readValue(messageBytes, EmployeeDto.class);

            //employee.setCinema(cinema);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        ResponseEntity<CinemaDto> cinema = cinemaService.fetchCinemaById(employeeResponse.getCinemaId());
        System.out.println(cinema);
        Employee employee = Employee.builder().cinema(cinema)
                .name(employeeResponse.getName())
                .surname(employeeResponse.getSurname())
                .position(employeeResponse.getPosition())
                .build();

        if (employee != null) { // Sprawdzenie, czy obiekt employee nie jest null
            cinemaService.addEmployeeToCinema(employee.getId(),employeeResponse.getCinemaId());
            System.out.println("Nowy pracownik dodany do kina: " + employee.getName() + " " + employee.getSurname());
        } else {
            System.out.println("Błąd: Otrzymany obiekt EmployeeResponse jest null.");
        }

        //cinemaService.addEmployeeToCinema(employee);
        System.out.print(employeeResponse);
        System.out.print(employee);
    }

}*/