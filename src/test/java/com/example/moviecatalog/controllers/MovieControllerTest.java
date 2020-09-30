package com.example.moviecatalog.controllers;

import com.example.moviecatalog.models.Director;
import com.example.moviecatalog.models.Movie;
import com.example.moviecatalog.models.MovieAssembler;
import com.example.moviecatalog.models.Rating;
import com.example.moviecatalog.services.MovieService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.hateoas.*;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MovieControllerTest {
    @Mock
    private MovieService movieService;

    private final MovieAssembler movieAssembler = new MovieAssembler();

    private MovieController movieController;

    @BeforeEach
    void setUp() {
        movieController = new MovieController(movieService, movieAssembler);
    }

    @Test
    void shouldContainsLinksToSelfAndCollection() {
        //given
        when(movieService.getMovieById(1L)).thenReturn(createExampleOptionalMovie(1L));
        //when
        ResponseEntity<EntityModel<Movie>> response = movieController.getMovieById(1L);
        //then
        Links links = response.getBody().getLinks();
        assertThat(links).extracting(Link::getHref)
                .containsExactlyInAnyOrder("/movies","/movies/1");// could be omitted because these links are generated automatically and when we would like to change addresses our will not pass
        assertThat(links).extracting(Link::getRel)
                .containsExactlyInAnyOrder(LinkRelation.of("self"),LinkRelation.of("movies"));
    }

    @Test
    void shouldContainsLinksToSelfAndRoot() {
        //given
        when(movieService.getAllMovies()).thenReturn(List.of(createExampleMovie(1L),
                createExampleMovie(2L),
                createExampleMovie(3L)));
        //when
//        ResponseEntity<CollectionModel<EntityModel<Movie>>> response = movieController.getAllMovies();
        var response = movieController.getAllMovies();
        //then
        Links links = response.getBody().getLinks();
        assertThat(links).extracting(Link::getRel)
                .containsExactlyInAnyOrder(LinkRelation.of("self"), LinkRelation.of("root"));
    }

    private Optional<Movie> createExampleOptionalMovie(Long movieId) {
        return Optional.of(createExampleMovie(movieId));
    }

    private Movie createExampleMovie(Long movieId) {
        return Movie.builder()
                .id(movieId)
                .director(new Director())
                .title("Movie title")
                .rating(Rating.R)
                .build();
    }
}
