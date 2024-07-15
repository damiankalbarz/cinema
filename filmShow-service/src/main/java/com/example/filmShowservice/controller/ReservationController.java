package com.example.filmShowservice.controller;


import com.example.filmShowservice.service.ReservationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/filmShow")
public class ReservationController {

    @Autowired
    private ReservationService reservationService;

    @PostMapping("/reserve/{filmShowId}/{clientId}")
    public ResponseEntity<?> reserveSeats(@PathVariable int filmShowId, @Valid @PathVariable String clientId,@Valid @RequestParam int numberOfSeatsToReserve) {
        return reservationService.reserveSeats(filmShowId, numberOfSeatsToReserve,clientId);
    }

    @GetMapping("/{filmShowId}/reservations")
    public ResponseEntity<?> getReservationsForFilmShow(@PathVariable int filmShowId) {
        return reservationService.getReservationsForFilmShow(filmShowId);
    }

    @GetMapping("/reservations/{reservationId}")
    public ResponseEntity<?> getReservationById(@PathVariable int reservationId) {
        return reservationService.getReservationById(reservationId);
    }

    @DeleteMapping("/reservations/{reservationId}")
    public ResponseEntity<?> cancelReservation(@PathVariable int reservationId) {
        return reservationService.cancelReservation(reservationId);
    }



}
