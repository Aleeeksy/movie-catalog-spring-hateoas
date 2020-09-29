package com.example.moviecatalog.services;

import com.example.moviecatalog.models.Movie;
import com.example.moviecatalog.repositories.MovieRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MovieServiceImplementation implements MovieService {
    private final MovieRepository movieRepository;

    public MovieServiceImplementation(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    @Override
    public List<Movie> getAllMovies() {
        return movieRepository.getAllMovies();
    }

    @Override
    public Optional<Movie> getMovieById(String id) {
        return movieRepository.getMovieById(id);
    }

    @Override
    public List<Movie> getMoviesByDirectorId(String directorId) {
        return movieRepository.getMoviesByDirectorId(directorId);
    }
}
