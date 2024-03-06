package com.example.filmShowservice.service;

import com.example.filmShowservice.dto.Cinema;
import com.example.filmShowservice.dto.Film;
import com.example.filmShowservice.dto.FilmShowResponse;
import com.example.filmShowservice.model.FilmShow;
import com.example.filmShowservice.repository.FilmShowRepositry;
import jakarta.servlet.http.PushBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

@Service
public class FilmShowService {

    @Autowired
    private FilmShowRepositry filmShowRepositry;

    @Autowired
    RestTemplate restTemplate;

    public ResponseEntity<?> create(FilmShow filmShow){
        try{
            return new ResponseEntity<FilmShow>(filmShowRepositry.save(filmShow), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    public ResponseEntity<?> fetchFilmShowById(int id){
        Optional<FilmShow> filmShow = filmShowRepositry.findById(id);
        if(filmShow.isPresent()){
            Cinema cinema = restTemplate.getForObject("http://CINEMA-SERVICE/cinema/" + filmShow.get().getCinemaId(), Cinema.class);
            Film film = restTemplate.getForObject("http://FILM-SERVICE/film/"+filmShow.get().getFilmId(), Film.class);
            FilmShowResponse filmShowResponse = new FilmShowResponse(
                    filmShow.get().getId(),
                    filmShow.get().getDateTime(),
                    cinema,
                    film
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
}