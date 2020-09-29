package com.example.moviecatalog.controllers;

import com.example.moviecatalog.models.Movie;
import com.example.moviecatalog.services.MovieService;
import com.example.moviecatalog.utils.LinkHelper;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/movies")
public class MovieController {
    private final MovieService movieService;

    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    @GetMapping
    public ResponseEntity<CollectionModel<Movie>> getAllMovies() {
        List<Movie> movies = movieService.getAllMovies();
        movies.forEach(movie -> {
            LinkHelper.addSelfMovieLink(movie);
            LinkHelper.addSelfDirectorLink(movie.getDirectors());
        });
        return ResponseEntity.ok(CollectionModel.of(movies, LinkHelper.getAllMoviesLink()));
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<EntityModel<Movie>> getMovieById(@PathVariable("id") String id) {
        return movieService.getMovieById(id)
                .map(movie -> {
                    LinkHelper.addSelfMovieLink(movie);
                    LinkHelper.addSelfDirectorLink(movie.getDirectors());
                    return ResponseEntity.ok(EntityModel.of(movie, LinkHelper.getAllMoviesLink()));
                })
                .orElse(ResponseEntity.notFound().build());
    }
}
