package com.example.moviecatalog.controllers;

import com.example.moviecatalog.models.*;
import com.example.moviecatalog.services.DirectorService;
import com.example.moviecatalog.services.MovieService;
import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor
@RequestMapping(value = "/directors")
public class DirectorController {
    private final DirectorService directorService;
    private final MovieService movieService;
    private final DirectorAssembler directorAssembler;
    private final MovieAssembler movieAssembler;

    @GetMapping
    public ResponseEntity<?> getAllDirectors() {
        List<Director> directors = directorService.getAllDirectors();
        return ResponseEntity.ok(directorAssembler.toCollectionModel(directors));
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<?> getDirectorById(@PathVariable("id") Long id) {
        return directorService.getDirectorById(id)
                .map(director -> ResponseEntity.ok(directorAssembler.toModel(director)))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping(value = "/{id}/movies")
    public ResponseEntity<?> getDirectorMovies(@PathVariable("id") Long id) {
        List<Movie> directorMovies = movieService.getMoviesByDirectorId(id);
        CollectionModel<EntityModel<Movie>> directorMoviesCollectionModel = movieAssembler.toCollectionModel(directorMovies);
        directorMoviesCollectionModel.add(linkTo(methodOn(DirectorController.class).getDirectorById(id)).withRel("director"));
        return ResponseEntity.ok(directorMoviesCollectionModel);
    }

}
