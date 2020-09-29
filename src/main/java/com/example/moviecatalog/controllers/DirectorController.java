package com.example.moviecatalog.controllers;

import com.example.moviecatalog.models.Director;
import com.example.moviecatalog.models.Movie;
import com.example.moviecatalog.services.DirectorService;
import com.example.moviecatalog.services.MovieService;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping(value = "/directors")
public class DirectorController {
    private final DirectorService directorService;
    private final MovieService movieService;

    public DirectorController(DirectorService directorService, MovieService movieService) {
        this.directorService = directorService;
        this.movieService = movieService;
    }

    @GetMapping
    public ResponseEntity<CollectionModel<Director>> getAllDirectors() {
        List<Director> directors = directorService.getAllDirectors();
        directors.forEach(director -> director.add(linkTo(DirectorController.class).slash(director.getId()).withSelfRel()));
        Link allDirectorsLink = linkTo(methodOn(DirectorController.class).getAllDirectors()).withRel("allDirectors");
        return ResponseEntity.ok(CollectionModel.of(directors, allDirectorsLink));
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<EntityModel<Director>> getDirectorById(@PathVariable("id") String id) {
        return directorService.getDirectorById(id)
                .map(director -> {
                    Link selfLink = linkTo(DirectorController.class).slash(director.getId()).withSelfRel();
                    Link directorMoviesLink = linkTo(methodOn(DirectorController.class).getDirectorMovies(director.getId())).withRel("directorMovies");
                    Link allDirectors = linkTo(methodOn(DirectorController.class).getAllDirectors()).withRel("allDirectors");
                    director.add(selfLink,directorMoviesLink, allDirectors);
                    return ResponseEntity.ok(EntityModel.of(director));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping(value = "/{id}/movies")
    public ResponseEntity<CollectionModel<Movie>> getDirectorMovies(@PathVariable("id") String id) {
        List<Movie> directorMovies = movieService.getMoviesByDirectorId(id);
        Link selfLink = linkTo(methodOn(DirectorController.class).getDirectorMovies(id)).withSelfRel();
        Link directorLink = linkTo(methodOn(DirectorController.class).getDirectorById(id)).withRel("director");
        Link allDirectors = linkTo(methodOn(DirectorController.class).getAllDirectors()).withRel("allDirectors");
        Link allMovies = linkTo(methodOn(MovieController.class).getAllMovies()).withRel("allMovies");
        return ResponseEntity.ok(CollectionModel.of(directorMovies, selfLink, directorLink, allDirectors, allMovies));
    }
}
