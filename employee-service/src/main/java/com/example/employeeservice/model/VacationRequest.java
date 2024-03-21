package com.example.employeeservice.model;

import jakarta.persistence.Entity;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.Date;

@Data
public class VacationRequest {
    @NotNull(message = "Data rozpoczęcia urlopu jest wymagana")
    @Future(message = "Data rozpoczęcia urlopu musi być w przyszłości")
    private Date startDate;

    @NotNull(message = "Data zakończenia urlopu jest wymagana")
    @Future(message = "Data zakończenia urlopu musi być w przyszłości")
    private Date endDate;

    @NotNull(message = "ID pracownika jest wymagane")
    private Integer employeeId;
}
