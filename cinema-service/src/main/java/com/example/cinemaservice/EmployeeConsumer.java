package com.example.cinemaservice;

import com.example.cinemaservice.dto.EmployeeResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class EmployeeConsumer {

    @RabbitListener(queues = "${rabbitmq.queue}")
    public void receiveMessage(byte[] messageBytes) {
        ObjectMapper objectMapper = new ObjectMapper();
        EmployeeResponse employee = null;
        try {
            employee = objectMapper.readValue(messageBytes, EmployeeResponse.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Nowy pracownik dodany do kina: " + employee.getName() + " " + employee.getSurname());
    }

}