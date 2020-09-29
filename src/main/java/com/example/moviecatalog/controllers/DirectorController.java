package com.example.moviecatalog.controllers;

import com.example.moviecatalog.models.Director;
import com.example.moviecatalog.models.Movie;
import com.example.moviecatalog.services.DirectorService;
import com.example.moviecatalog.services.MovieService;
import com.example.moviecatalog.utils.LinkHelper;
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
    private static final String DIRECTOR_MOVIES = "directorMovies";

    private final DirectorService directorService;
    private final MovieService movieService;

    public DirectorController(DirectorService directorService, MovieService movieService) {
        this.directorService = directorService;
        this.movieService = movieService;
    }

    @GetMapping
    public ResponseEntity<CollectionModel<Director>> getAllDirectors() {
        List<Director> directors = directorService.getAllDirectors();
        LinkHelper.addSelfDirectorLink(directors);
        return ResponseEntity.ok(CollectionModel.of(directors, LinkHelper.getAllDirectorsLink()));
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<EntityModel<Director>> getDirectorById(@PathVariable("id") String id) {
        return directorService.getDirectorById(id)
                .map(director -> {
                    Link directorMoviesLink = linkTo(methodOn(DirectorController.class).getDirectorMovies(director.getId())).withRel(DIRECTOR_MOVIES);
                    director.add(LinkHelper.getSelfDirectorLink(director.getId()), directorMoviesLink, LinkHelper.getAllDirectorsLink());
                    return ResponseEntity.ok(EntityModel.of(director));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping(value = "/{id}/movies")
    public ResponseEntity<CollectionModel<Movie>> getDirectorMovies(@PathVariable("id") String id) {
        List<Movie> directorMovies = movieService.getMoviesByDirectorId(id);
        Link selfLink = linkTo(methodOn(DirectorController.class).getDirectorMovies(id)).withSelfRel();
        addSelfMovieLinks(directorMovies);
        addDirectorsLinks(directorMovies);
        return ResponseEntity.ok(CollectionModel.of(directorMovies, selfLink, LinkHelper.getAllDirectorsLink(), LinkHelper.getAllMoviesLink()));
    }

    private void addSelfMovieLinks(List<Movie> movies) {
        movies.forEach(LinkHelper::addSelfMovieLink);
    }

    private void addDirectorsLinks(List<Movie> movies) {
        movies.forEach(movie -> LinkHelper.addSelfDirectorLink(movie.getDirectors()));
    }
}
