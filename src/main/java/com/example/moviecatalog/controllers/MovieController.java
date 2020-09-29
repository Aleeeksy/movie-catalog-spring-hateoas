package com.example.moviecatalog.controllers;

import com.example.moviecatalog.models.Movie;
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
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

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
        movies.forEach(movie -> movie.add(linkTo(MovieController.class).slash(movie.getId()).withSelfRel()));
        Link allMoviesLink = linkTo(methodOn(MovieController.class).getAllMovies()).withRel("allMovies");
        return ResponseEntity.ok(CollectionModel.of(movies, allMoviesLink));
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<EntityModel<Movie>> getMovieById(@PathVariable("id") String id) {
        return movieService.getMovieById(id)
                .map(movie -> {
                    Link selfLink = linkTo(MovieController.class).slash(movie.getId()).withSelfRel();
                    List<Link> directorsLinks = prepareDirectorsLinks(movie);
                    Link allMoviesLink = linkTo(methodOn(MovieController.class).getAllMovies()).withRel("allMovies");

                    movie.add(selfLink, allMoviesLink);
                    movie.add(directorsLinks);
                    return ResponseEntity.ok(EntityModel.of(movie));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    private List<Link> prepareDirectorsLinks(Movie movie) {
        return movie.getDirectors()
                .stream()
                .map(director -> linkTo(methodOn(DirectorController.class).getDirectorById(director.getId())).withRel("directors"))
                .collect(Collectors.toList());
    }
}
