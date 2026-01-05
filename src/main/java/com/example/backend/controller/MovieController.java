package com.example.backend.controller;

import com.example.backend.entity.Movie;
import com.example.backend.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/movie")
public class MovieController {
    @Autowired
    private MovieRepository movieRepository;

    @GetMapping
    public Iterable<Movie> returnAllMovie() {
        return movieRepository.findAll();
    }

    @PostMapping
    public ResponseEntity<Movie> createMovie(@RequestBody Movie movie) {
        Movie createdMovie = movieRepository.save(movie);
        return ResponseEntity.ok(createdMovie);
    }
}
