package com.example.moviecatalog.controllers;

import com.example.moviecatalog.assemblers.MovieResourceAssembler;
import com.example.moviecatalog.models.resources.MovieResource;
import com.example.moviecatalog.services.MovieService;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping(value = "/movies")
public class MovieController {
    private final MovieService movieService;
    private final MovieResourceAssembler movieResourceAssembler;

    public MovieController(MovieService movieService, MovieResourceAssembler movieResourceAssembler) {
        this.movieService = movieService;
        this.movieResourceAssembler = movieResourceAssembler;
    }

    @GetMapping
    public ResponseEntity<CollectionModel<MovieResource>> getAllMovies() {
        CollectionModel<MovieResource> movieResources = movieResourceAssembler.toCollectionModel(movieService.getAllMovies());

        movieResources.add(linkTo(methodOn(MovieController.class).getAllMovies()).withSelfRel());

        return ResponseEntity.ok(movieResources);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<MovieResource> getMovieById(@PathVariable("id") String id) {
        return movieService.getMovieById(id)
                .map(movie -> ResponseEntity.ok(movieResourceAssembler.toModel(movie)))
                .orElse(ResponseEntity.notFound().build());
    }
}
