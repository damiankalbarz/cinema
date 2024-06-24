package com.example.filmShowservice.service;

import com.example.filmShowservice.controller.ReservationController;
import com.example.filmShowservice.dto.Client;
import com.example.filmShowservice.dto.ReservationResponse;
import com.example.filmShowservice.model.FilmShow;
import com.example.filmShowservice.model.Reservation;
import com.example.filmShowservice.repository.FilmShowRepositry;
import com.example.filmShowservice.repository.ReservationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ReservationService {
    @Autowired
    ReservationRepository reservationRepository;
    @Autowired
    RestTemplate restTemplate;

    @Autowired
    private FilmShowRepositry filmShowRepositry;

    private static final Logger logger = LoggerFactory.getLogger(ReservationController.class);


    public ResponseEntity<?> reserveSeats(int filmShowId, int numberOfSeatsToReserve, String clientId) {
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
                reservation.setClientId(clientId);
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
                List<ReservationResponse> reservationResponses = reservations.stream()
                        .map(this::mapToReservationResponse)
                        .collect(Collectors.toList());

                return new ResponseEntity<>(reservationResponses, HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Brak rezerwacji na ten seans filmowy.", HttpStatus.NOT_FOUND);
            }
        } else {
            return new ResponseEntity<>("Seans filmowy nie znaleziony.", HttpStatus.NOT_FOUND);
        }

    }

    private ReservationResponse mapToReservationResponse(Reservation reservation) {
        Client client = null;
        try {
            client = restTemplate.getForObject("http://CLIENT-SERVICE/client/" + reservation.getClientId(), Client.class);
        } catch (HttpClientErrorException e) {
            if (e.getStatusCode() == HttpStatus.NOT_FOUND) {
                logger.error("Klient nie znaleziony dla ID: " + reservation.getClientId());
            } else {
                throw e;
            }
        }

        return new ReservationResponse(
                reservation.getId(),
                reservation.getFilmShow(),
                reservation.getNumberOfSeats(),
                client
        );
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

    public ResponseEntity<?> getReservationById(int reservationId) {
        Optional<Reservation> optionalReservation = reservationRepository.findById(reservationId);

        if (optionalReservation.isPresent()) {
            try {
                Client client = restTemplate.getForObject("http://CLIENT-SERVICE/client/" + optionalReservation.get().getClientId(), Client.class);

                ReservationResponse reservationResponse = new ReservationResponse(
                        optionalReservation.get().getId(),
                        optionalReservation.get().getFilmShow(),
                        optionalReservation.get().getNumberOfSeats(),
                        client
                );

                return new ResponseEntity<>(reservationResponse, HttpStatus.OK);
            } catch (HttpServerErrorException e) {
                logger.error("Error fetching client information: {}", e.getMessage());
                return new ResponseEntity<>("Error fetching client information from CLIENT-SERVICE.", HttpStatus.INTERNAL_SERVER_ERROR);
            } catch (Exception e) {
                logger.error("Unexpected error: {}", e.getMessage());
                return new ResponseEntity<>("Unexpected error occurred.", HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } else {
            return new ResponseEntity<>("Reservation not found.", HttpStatus.NOT_FOUND);
        }
    }

}
