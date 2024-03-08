package com.example.filmShowservice.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import java.time.LocalDateTime;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class FilmShow {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @NotNull(message = "Data i czas nie mogą być puste")
    private LocalDateTime dateTime;

    @NotNull(message = "Id kina nie może być puste")
    private Integer cinemaId;

    @NotNull(message = "Id filmu nie może być puste")
    private String filmId;

    @NotNull(message = "Id sali nie może być puste")
    private Integer roomId;

    @Min(value = 0, message = "Liczba dostępnych miejsc nie może być ujemna")
    private Integer availableSeats;

    @JsonIgnore
    @OneToMany(mappedBy = "filmShow", cascade = CascadeType.ALL)
    private List<Reservation> reservations;

}
