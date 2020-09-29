package com.example.moviecatalog.utils;

import com.example.moviecatalog.controllers.DirectorController;
import com.example.moviecatalog.controllers.MovieController;
import com.example.moviecatalog.models.Director;
import com.example.moviecatalog.models.Movie;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.Link;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

public class LinkHelper {
    private static final String ALL_DIRECTORS = "allDirectors";
    private static final String ALL_MOVIES = "allMovies";

    public static void addSelfDirectorLink(Iterable<Director> directors) {
        directors.forEach(director -> director.addIf(
                !director.hasLink(IanaLinkRelations.SELF),
                () -> getSelfDirectorLink(director.getId()))
        );
    }

    public static void addSelfMovieLink(Movie movie) {
        movie.add(linkTo(methodOn(MovieController.class).getMovieById(movie.getId())).withSelfRel());
    }

    public static Link getAllMoviesLink() {
        return linkTo(methodOn(MovieController.class).getAllMovies()).withRel(LinkHelper.ALL_MOVIES);
    }

    public static Link getAllDirectorsLink() {
        return linkTo(methodOn(DirectorController.class).getAllDirectors()).withRel(LinkHelper.ALL_DIRECTORS);
    }

    public static Link getSelfDirectorLink(String id) {
        return linkTo(methodOn(DirectorController.class).getDirectorById(id)).withSelfRel();
    }
}
