package com.example.moviecatalog.assemblers;

import com.example.moviecatalog.controllers.MovieController;
import com.example.moviecatalog.models.Movie;
import com.example.moviecatalog.models.representations.MovieRepresentation;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class MovieRepresentationAssembler implements RepresentationModelAssembler<Movie, MovieRepresentation> {
    private final DirectorRepresentationAssembler directorRepresentationAssembler;

    public MovieRepresentationAssembler(DirectorRepresentationAssembler directorRepresentationAssembler) {
        this.directorRepresentationAssembler = directorRepresentationAssembler;
    }

    @Override
    public MovieRepresentation toModel(Movie entity) {
        MovieRepresentation movieRepresentation = MovieRepresentation.builder()
                .id(entity.getId())
                .rating(entity.getRating())
                .title(entity.getTitle())
                .year(entity.getYear())
                .directors(entity.getDirectors().stream().map(directorRepresentationAssembler::toModel).collect(Collectors.toSet()))
                .build();

        movieRepresentation.add(linkTo(methodOn(MovieController.class).getMovieById(movieRepresentation.getId())).withSelfRel());

        return movieRepresentation;
    }
}
