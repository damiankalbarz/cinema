package com.example.filmShowservice.dto;


import com.example.filmShowservice.model.FilmShow;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReservationResponse {
    public int id;
    private FilmShow filmShow;
    public int numberOfSeats;
    public Client client;
}
