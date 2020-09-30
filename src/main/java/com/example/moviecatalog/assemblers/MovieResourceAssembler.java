package com.example.moviecatalog.assemblers;

import com.example.moviecatalog.controllers.MovieController;
import com.example.moviecatalog.models.Movie;
import com.example.moviecatalog.models.resources.MovieResource;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class MovieResourceAssembler implements RepresentationModelAssembler<Movie, MovieResource> {
    private final DirectorResourceAssembler directorResourceAssembler;

    public MovieResourceAssembler(DirectorResourceAssembler directorResourceAssembler) {
        this.directorResourceAssembler = directorResourceAssembler;
    }

    @Override
    public MovieResource toModel(Movie entity) {
        MovieResource movieResource = MovieResource.builder()
                .id(entity.getId())
                .rating(entity.getRating())
                .title(entity.getTitle())
                .year(entity.getYear())
                .directors(entity.getDirectors().stream().map(directorResourceAssembler::toModel).collect(Collectors.toSet()))
                .build();

        movieResource.add(linkTo(methodOn(MovieController.class).getMovieById(movieResource.getId())).withSelfRel());

        return movieResource;
    }
}
