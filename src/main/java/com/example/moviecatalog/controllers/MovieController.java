package com.example.moviecatalog.controllers;

import com.example.moviecatalog.models.Movie;
import com.example.moviecatalog.models.MovieAssembler;
import com.example.moviecatalog.services.MovieService;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/movies")
@RequiredArgsConstructor
public class MovieController {

    private final MovieService movieService;
    private final MovieAssembler movieAssembler;

    @GetMapping
    public ResponseEntity<CollectionModel<EntityModel<Movie>>> getAllMovies() {
        List<Movie> allMovies = movieService.getAllMovies();
        CollectionModel<EntityModel<Movie>> entityModels = movieAssembler.toCollectionModel(allMovies);
        return ResponseEntity.ok(entityModels);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<EntityModel<Movie>> getMovieById(@PathVariable("id") Long id) {
        Optional<Movie> movieById = movieService.getMovieById(id);
        return movieById.map(movie -> {
            EntityModel<Movie> entityModel = movieAssembler.toModel(movie);
            return ResponseEntity.ok(entityModel);
        }).orElse(ResponseEntity.notFound().build());
    }
}
