package com.example.moviecatalog.controllers;

import com.example.moviecatalog.assemblers.MovieRepresentationAssembler;
import com.example.moviecatalog.models.representations.MovieRepresentation;
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
    private final MovieRepresentationAssembler movieRepresentationAssembler;

    public MovieController(MovieService movieService, MovieRepresentationAssembler movieRepresentationAssembler) {
        this.movieService = movieService;
        this.movieRepresentationAssembler = movieRepresentationAssembler;
    }

    @GetMapping
    public ResponseEntity<CollectionModel<MovieRepresentation>> getAllMovies() {
        CollectionModel<MovieRepresentation> movieRepresentations = movieRepresentationAssembler.toCollectionModel(movieService.getAllMovies());

        movieRepresentations.add(linkTo(methodOn(MovieController.class).getAllMovies()).withSelfRel());

        return ResponseEntity.ok(movieRepresentations);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<MovieRepresentation> getMovieById(@PathVariable("id") String id) {
        return movieService.getMovieById(id)
                .map(movie -> ResponseEntity.ok(movieRepresentationAssembler.toModel(movie)))
                .orElse(ResponseEntity.notFound().build());
    }
}
