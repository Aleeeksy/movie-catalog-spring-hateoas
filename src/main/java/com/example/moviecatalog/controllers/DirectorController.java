package com.example.moviecatalog.controllers;

import com.example.moviecatalog.assemblers.DirectorResourceAssembler;
import com.example.moviecatalog.assemblers.MovieResourceAssembler;
import com.example.moviecatalog.models.resources.DirectorResource;
import com.example.moviecatalog.models.resources.MovieResource;
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
    private final DirectorResourceAssembler directorResourceAssembler;
    private final MovieResourceAssembler movieResourceAssembler;

    public DirectorController(DirectorService directorService, MovieService movieService,
                              DirectorResourceAssembler directorResourceAssembler,
                              MovieResourceAssembler movieResourceAssembler) {
        this.directorService = directorService;
        this.movieService = movieService;
        this.directorResourceAssembler = directorResourceAssembler;
        this.movieResourceAssembler = movieResourceAssembler;
    }

    @GetMapping
    public ResponseEntity<CollectionModel<DirectorResource>> getAllDirectors() {
        return ResponseEntity.ok(directorResourceAssembler.toCollectionModel(directorService.getAllDirectors()));
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<DirectorResource> getDirectorById(@PathVariable("id") String id) {
        return directorService.getDirectorById(id)
                .map(director -> {
                    DirectorResource directorResource = directorResourceAssembler.toModel(director)
                            .add(linkTo(methodOn(DirectorController.class)
                                    .getDirectorMovies(director.getId())).withRel(DIRECTOR_MOVIES))
                            .add(linkTo(methodOn(DirectorController.class).getAllDirectors()).withRel(ALL_DIRECTORS));
                    return ResponseEntity.ok(directorResource);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping(value = "/{id}/movies")
    public ResponseEntity<CollectionModel<MovieResource>> getDirectorMovies(@PathVariable("id") String id) {
        CollectionModel<MovieResource> directorMovies = movieResourceAssembler.toCollectionModel(movieService.getMoviesByDirectorId(id))
                .add(linkTo(methodOn(DirectorController.class).getDirectorMovies(id)).withSelfRel())
                .add(linkTo(methodOn(MovieController.class).getAllMovies()).withRel(ALL_MOVIES));
        return ResponseEntity.ok(directorMovies);
    }
}
