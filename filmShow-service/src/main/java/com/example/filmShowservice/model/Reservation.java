package com.example.filmShowservice.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public int id;
    @ManyToOne
    @JoinColumn(name = "film_show_id")
    private FilmShow filmShow;

    @Min(value = 1, message = "Liczba miejsc musi być większa niż zero")
    public int numberOfSeats;

    @NotBlank(message = "Id klienta nie może być puste")
    public String clientId;
}
