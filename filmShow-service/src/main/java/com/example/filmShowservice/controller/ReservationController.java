package com.example.filmShowservice.controller;


import com.example.filmShowservice.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("*")
@RestController
@RequestMapping("/filmShow")
public class ReservationController {

    @Autowired
    private ReservationService reservationService;

    @PostMapping("/reserve/{filmShowId}")
    public ResponseEntity<?> reserveSeats(@PathVariable int filmShowId, @RequestParam int numberOfSeatsToReserve) {
        return reservationService.reserveSeats(filmShowId, numberOfSeatsToReserve);
    }

    @GetMapping("/{filmShowId}/reservations")
    public ResponseEntity<?> getReservationsForFilmShow(@PathVariable int filmShowId) {
        return reservationService.getReservationsForFilmShow(filmShowId);
    }

    @DeleteMapping("/reservations/{reservationId}")
    public ResponseEntity<?> cancelReservation(@PathVariable int reservationId) {
        return reservationService.cancelReservation(reservationId);
    }


}
