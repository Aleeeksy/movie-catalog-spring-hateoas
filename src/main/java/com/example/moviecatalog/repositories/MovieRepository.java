package com.example.moviecatalog.repositories;

import com.example.moviecatalog.models.Movie;

import java.util.List;
import java.util.Optional;

public interface MovieRepository {
    List<Movie> getAllMovies();

    Optional<Movie> getMovieById(String id);

    List<Movie> getMoviesByDirectorId(String directorId);
}
