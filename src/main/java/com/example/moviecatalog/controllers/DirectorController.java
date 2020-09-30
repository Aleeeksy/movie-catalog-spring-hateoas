package com.example.moviecatalog.controllers;

import com.example.moviecatalog.assemblers.DirectorRepresentationAssembler;
import com.example.moviecatalog.assemblers.MovieRepresentationAssembler;
import com.example.moviecatalog.models.representations.DirectorRepresentation;
import com.example.moviecatalog.models.representations.MovieRepresentation;
import com.example.moviecatalog.services.DirectorService;
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
@RequestMapping(value = "/directors")
public class DirectorController {
    private static final String DIRECTOR_MOVIES = "directorMovies";
    private static final String ALL_DIRECTORS = "allDirectors";
    private static final String ALL_MOVIES = "allMovies";

    private final DirectorService directorService;
    private final MovieService movieService;
    private final DirectorRepresentationAssembler directorRepresentationAssembler;
    private final MovieRepresentationAssembler movieRepresentationAssembler;

    public DirectorController(DirectorService directorService, MovieService movieService,
                              DirectorRepresentationAssembler directorRepresentationAssembler,
                              MovieRepresentationAssembler movieRepresentationAssembler) {
        this.directorService = directorService;
        this.movieService = movieService;
        this.directorRepresentationAssembler = directorRepresentationAssembler;
        this.movieRepresentationAssembler = movieRepresentationAssembler;
    }

    @GetMapping
    public ResponseEntity<CollectionModel<DirectorRepresentation>> getAllDirectors() {
        return ResponseEntity.ok(directorRepresentationAssembler.toCollectionModel(directorService.getAllDirectors()));
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<DirectorRepresentation> getDirectorById(@PathVariable("id") String id) {
        return directorService.getDirectorById(id)
                .map(director -> {
                    DirectorRepresentation directorRepresentation = directorRepresentationAssembler.toModel(director)
                            .add(linkTo(methodOn(DirectorController.class)
                                    .getDirectorMovies(director.getId())).withRel(DIRECTOR_MOVIES))
                            .add(linkTo(methodOn(DirectorController.class).getAllDirectors()).withRel(ALL_DIRECTORS));
                    return ResponseEntity.ok(directorRepresentation);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping(value = "/{id}/movies")
    public ResponseEntity<CollectionModel<MovieRepresentation>> getDirectorMovies(@PathVariable("id") String id) {
        CollectionModel<MovieRepresentation> directorMovies = movieRepresentationAssembler.toCollectionModel(movieService.getMoviesByDirectorId(id))
                .add(linkTo(methodOn(DirectorController.class).getDirectorMovies(id)).withSelfRel())
                .add(linkTo(methodOn(MovieController.class).getAllMovies()).withRel(ALL_MOVIES));
        return ResponseEntity.ok(directorMovies);
    }
}
