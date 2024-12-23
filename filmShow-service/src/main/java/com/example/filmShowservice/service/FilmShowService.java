package com.example.filmShowservice.service;

import com.example.filmShowservice.dto.Cinema;
import com.example.filmShowservice.dto.Film;
import com.example.filmShowservice.dto.FilmShowResponse;
import com.example.filmShowservice.dto.Room;
import com.example.filmShowservice.model.FilmShow;
import com.example.filmShowservice.repository.FilmShowRepositry;
import jakarta.servlet.http.PushBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FilmShowService {
    private static final Logger logger = LoggerFactory.getLogger(FilmShowService.class);


    @Autowired
    private FilmShowRepositry filmShowRepositry;

    @Autowired
    RestTemplate restTemplate;

    public ResponseEntity<?> create(FilmShow filmShow){
        try{
            Room room = restTemplate.getForObject("http://CINEMA-SERVICE/api/v1/cinema/room/"+filmShow.getRoomId(), Room.class);
            if (room != null) {
                filmShow.setAvailableSeats(room.getSeats());
            }
            else {
                return new ResponseEntity<>("Room not found.", HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<FilmShow>(filmShowRepositry.save(filmShow), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    public ResponseEntity<?> fetchFilmShowById(int id){
        Optional<FilmShow> filmShow = filmShowRepositry.findById(id);
        if(filmShow.isPresent()){
            Cinema cinema = restTemplate.getForObject("http://CINEMA-SERVICE/api/v1/cinema/" + filmShow.get().getCinemaId(), Cinema.class);
            Film film = restTemplate.getForObject("http://FILM-SERVICE/api/v1/film/"+filmShow.get().getFilmId(), Film.class);
            Room room = restTemplate.getForObject("http://CINEMA-SERVICE/api/v1/cinema/room/"+filmShow.get().getRoomId(), Room.class);
            FilmShowResponse filmShowResponse = new FilmShowResponse(
                    filmShow.get().getId(),
                    filmShow.get().getDateTime(),
                    cinema,
                    film,
                    room,
                    filmShow.get().getAvailableSeats()
            );

            return new ResponseEntity<>(filmShowResponse,HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>("No film show found", HttpStatus.NOT_FOUND);
        }

    }

    public ResponseEntity<?> fetchFilmShow(){
        List<FilmShow> filmShows = filmShowRepositry.findAll();
        if(filmShows.size()>0){
            return new ResponseEntity<List<FilmShow>>(filmShows, HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>("No film shows",HttpStatus.NOT_FOUND);

        }

    }

    public ResponseEntity<?> getAllFilmShows() {
        List<FilmShow> filmShows = filmShowRepositry.findAll();

        if (!filmShows.isEmpty()) {
            List<FilmShowResponse> filmShowResponses = filmShows.stream()
                    .map(this::mapToFilmShowResponse)
                    .collect(Collectors.toList());

            return new ResponseEntity<>(filmShowResponses, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("No film shows found", HttpStatus.NOT_FOUND);
        }
    }

    private FilmShowResponse mapToFilmShowResponse(FilmShow filmShow) {

        Cinema cinema = null;
        Film film = null;
        Room room = null;


        try {
            cinema = restTemplate.getForObject("http://CINEMA-SERVICE/api/v1//cinema/" + filmShow.getCinemaId(), Cinema.class);
        } catch (HttpClientErrorException e) {
            if (e.getStatusCode() == HttpStatus.NOT_FOUND) {
                logger.error("Cinema not found for ID: " + filmShow.getCinemaId());
            } else {
                throw e;
            }
        }

        try {
            film = restTemplate.getForObject("http://FILM-SERVICE/api/v1/film/" + filmShow.getFilmId(), Film.class);
        } catch (HttpClientErrorException e) {
            if (e.getStatusCode() == HttpStatus.NOT_FOUND) {
                logger.error("Film not found for ID: " + filmShow.getFilmId());
            } else {
                throw e;
            }
        }

        try {
            room = restTemplate.getForObject("http://CINEMA-SERVICE/api/v1/cinema/room/" + filmShow.getRoomId(), Room.class);
        } catch (HttpClientErrorException e) {
            if (e.getStatusCode() == HttpStatus.NOT_FOUND) {
                logger.error("Room not found for ID: " + filmShow.getRoomId());
            } else {
                throw e;
            }
        }
        return new FilmShowResponse(
                filmShow.getId(),
                filmShow.getDateTime(),
                cinema,
                film,
                room,
                filmShow.getAvailableSeats()
        );
    }

    public ResponseEntity<?> reserveSeats(int filmShowId, int numberOfSeatsToReserve) {
        Optional<FilmShow> optionalFilmShow = filmShowRepositry.findById(filmShowId);

        if (optionalFilmShow.isPresent()) {
            FilmShow filmShow = optionalFilmShow.get();
            int availableSeats = filmShow.getAvailableSeats();

            if (numberOfSeatsToReserve <= availableSeats) {
                filmShow.setAvailableSeats(availableSeats - numberOfSeatsToReserve);
                filmShowRepositry.save(filmShow);

                return new ResponseEntity<>("Seats reserved successfully.", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Not enough available seats.", HttpStatus.BAD_REQUEST);
            }
        } else {
            return new ResponseEntity<>("Film show not found.", HttpStatus.NOT_FOUND);
        }
    }

}
