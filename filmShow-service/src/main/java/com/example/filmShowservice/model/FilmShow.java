package com.example.filmShowservice.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
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
    private LocalDateTime dateTime;
    private Integer cinemaId;
    private String filmId;
    private Integer roomId;
    private Integer availableSeats;

    @JsonIgnore
    @OneToMany(mappedBy = "filmShow", cascade = CascadeType.ALL)
    private List<Reservation> reservations;

}
