package com.example.filmShowservice.model;


import jakarta.persistence.*;
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
    public int numberOfSeats;
    public String clientId;
}
