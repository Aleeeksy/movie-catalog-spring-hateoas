package com.example.moviecatalog.services;

import com.example.moviecatalog.models.Movie;
import com.example.moviecatalog.repositories.MovieRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MovieService {
    private final MovieRepository movieRepository;

    public MovieService(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    public List<Movie> getAllMovies() {
        return movieRepository.getAllMovies();
    }

    public Optional<Movie> getMovieById(String id) {
        return movieRepository.getMovieById(id);
    }

    public List<Movie> getMoviesByDirectorId(String directorId) {
        return movieRepository.getMoviesByDirectorId(directorId);
    }
}
