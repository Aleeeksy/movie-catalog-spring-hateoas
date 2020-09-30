package com.example.moviecatalog.models;

import com.example.moviecatalog.controllers.MovieController;
import com.example.moviecatalog.controllers.RootController;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
@RequiredArgsConstructor
public class MovieAssembler implements RepresentationModelAssembler<Movie, EntityModel<Movie>> {

    @Override
    public EntityModel<Movie> toModel(Movie movie) {
        EntityModel<Movie> movieEntityModel = EntityModel.of(movie);
        movieEntityModel.add(linkTo(methodOn(MovieController.class).getMovieById(movie.getId())).withSelfRel());
        movieEntityModel.add(linkTo(methodOn(MovieController.class).getAllMovies()).withRel("movies"));
        return movieEntityModel;
    }

    @Override
    public CollectionModel<EntityModel<Movie>> toCollectionModel(Iterable<? extends Movie> movies) {
        List<EntityModel<Movie>> moviesEntityModels = StreamSupport.stream(movies.spliterator(), false)
                .map(this::toModel)
                .collect(Collectors.toList());
        CollectionModel<EntityModel<Movie>> collectionModel = CollectionModel.of(moviesEntityModels);
        collectionModel.add(linkTo(methodOn(MovieController.class).getAllMovies()).withSelfRel());
        collectionModel.add(linkTo(methodOn(RootController.class).getRoot()).withRel("root"));
        return collectionModel;
    }
}
