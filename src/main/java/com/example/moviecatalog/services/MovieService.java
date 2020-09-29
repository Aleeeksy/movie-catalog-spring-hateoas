package com.example.moviecatalog.services;

import com.example.moviecatalog.models.Movie;

import java.util.List;
import java.util.Optional;

public interface MovieService {
    List<Movie> getAllMovies();

    Optional<Movie> getMovieById(String id);

    List<Movie> getMoviesByDirectorId(String directorId);
}
