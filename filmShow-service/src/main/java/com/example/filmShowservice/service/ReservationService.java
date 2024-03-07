package com.example.filmShowservice.service;

import com.example.filmShowservice.model.FilmShow;
import com.example.filmShowservice.model.Reservation;
import com.example.filmShowservice.repository.FilmShowRepositry;
import com.example.filmShowservice.repository.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

@Service
public class ReservationService {
    @Autowired
    ReservationRepository reservationRepository;
    @Autowired
    RestTemplate restTemplate;

    @Autowired
    private FilmShowRepositry filmShowRepositry;

    public ResponseEntity<?> reserveSeats(int filmShowId, int numberOfSeatsToReserve) {
        Optional<FilmShow> optionalFilmShow = filmShowRepositry.findById(filmShowId);

        if (optionalFilmShow.isPresent()) {
            FilmShow filmShow = optionalFilmShow.get();
            int availableSeats = filmShow.getAvailableSeats();

            if (numberOfSeatsToReserve <= availableSeats) {
                // Możesz dokonać rezerwacji miejsc
                filmShow.setAvailableSeats(availableSeats - numberOfSeatsToReserve);
                filmShowRepositry.save(filmShow);

                // Zapisz rezerwację
                Reservation reservation = new Reservation();
                reservation.setFilmShow(filmShow);
                reservation.setNumberOfSeats(numberOfSeatsToReserve);
                reservationRepository.save(reservation);

                return new ResponseEntity<>("Seats reserved successfully.", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Not enough available seats.", HttpStatus.BAD_REQUEST);
            }
        } else {
            return new ResponseEntity<>("Film show not found.", HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<?> getReservationsForFilmShow(int filmShowId) {
        Optional<FilmShow> optionalFilmShow = filmShowRepositry.findById(filmShowId);

        if (optionalFilmShow.isPresent()) {
            FilmShow filmShow = optionalFilmShow.get();
            List<Reservation> reservations = filmShow.getReservations();

            if (!reservations.isEmpty()) {
                return new ResponseEntity<>(reservations, HttpStatus.OK);
            } else {
                return new ResponseEntity<>("No reservations for this film show.", HttpStatus.NOT_FOUND);
            }
        } else {
            return new ResponseEntity<>("Film show not found.", HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<?> cancelReservation(int reservationId) {
        Optional<Reservation> optionalReservation = reservationRepository.findById(reservationId);

        if (optionalReservation.isPresent()) {
            Reservation reservation = optionalReservation.get();
            FilmShow filmShow = reservation.getFilmShow();

            // Przywróć dostępne miejsca
            filmShow.setAvailableSeats(filmShow.getAvailableSeats() + reservation.getNumberOfSeats());
            filmShowRepositry.save(filmShow);

            // Usuń rezerwację
            reservationRepository.deleteById(reservationId);

            return new ResponseEntity<>("Reservation canceled successfully.", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Reservation not found.", HttpStatus.NOT_FOUND);
        }
    }

}
