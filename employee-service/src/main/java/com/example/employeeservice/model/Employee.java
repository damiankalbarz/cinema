package com.example.employeeservice.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @NotNull(message = "cinemaId jest wymagane")
    private int cinemaId;

    @NotBlank(message = "Imię jest wymagane")
    private String name;

    @NotBlank(message = "Nazwisko jest wymagane")
    private String surname;

    @NotBlank(message = "Stanowisko jest wymagane")
    private String position;

    @Pattern(regexp = "\\d{9}", message = "Numer telefonu musi składać się z 9 cyfr")
    private String phone;

    @NotNull(message = "Wynagrodzenie nie może być puste")
    @Positive(message = "Wynagrodzenie musi być dodatnie")
    private Double salary;

    @Min(value = 0, message = "Liczba dni urlopu nie może być ujemna")
    @Max(value = 30, message = "Liczba dni urlopu nie może przekroczyć 30")
    private int holidaysDays;

    private int availableVacationDays;

    public double calculateBonus() {
        if ("Manager".equalsIgnoreCase(position)) {
            return salary *= 1.3; // Increase salary by 30% for managers
        } else {
            return salary *= 1.15; // Increase salary by 15% for regular employees
        }
    }

}
