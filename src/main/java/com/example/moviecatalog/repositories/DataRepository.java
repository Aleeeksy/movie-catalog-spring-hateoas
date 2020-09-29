package com.example.moviecatalog.repositories;

import com.example.moviecatalog.models.Director;
import com.example.moviecatalog.models.Movie;
import com.example.moviecatalog.models.Rating;
import org.springframework.stereotype.Repository;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Repository
public class DataRepository implements MovieRepository, DirectorRepository {
    private List<Movie> movies;
    private List<Director> directors;

    private void initDirectors() {
        directors = new LinkedList<>();

        directors.add(Director.builder()
                .id("D0001")
                .firstname("Sergio")
                .lastname("Leone")
                .year(1929)
                .build());

        directors.add(Director.builder()
                .id("D0002")
                .firstname("Steven")
                .lastname("Spielberg")
                .year(1946)
                .build());

        directors.add(Director.builder()
                .id("D0003")
                .firstname("Christopher")
                .lastname("Nolan")
                .year(1970)
                .build());
    }

    private void initMovies() {
        initDirectors();
        movies = new LinkedList<>();

        movies.add(Movie.builder()
                .id("M0001")
                .title("Once Upon a Time in the West")
                .year(1968)
                .directors(Set.of(directors.get(0), directors.get(1)))
                .rating(Rating.PG_13)
                .build()
        );

        movies.add(Movie.builder()
                .id("M0002")
                .title("Once Upon a Time in America")
                .year(1984)
                .directors(Set.of(directors.get(0)))
                .rating(Rating.R)
                .build()
        );

        movies.add(Movie.builder()
                .id("M0003")
                .title("The Lost World: Jurassic Park")
                .year(1997)
                .directors(Set.of(directors.get(1)))
                .rating(Rating.PG_13)
                .build()
        );

        movies.add(Movie.builder()
                .id("M0004")
                .title("Raiders of the Lost Ark")
                .year(1981)
                .directors(Set.of(directors.get(1)))
                .rating(Rating.PG)
                .build()
        );

        movies.add(Movie.builder()
                .id("M0005")
                .title("Catch Me If You Can")
                .year(2002)
                .directors(Set.of(directors.get(1)))
                .rating(Rating.PG_13)
                .build()
        );

        movies.add(Movie.builder()
                .id("M0006")
                .title("Batman Begins")
                .year(2005)
                .directors(Set.of(directors.get(2)))
                .rating(Rating.PG_13)
                .build()
        );

        movies.add(Movie.builder()
                .id("M0007")
                .title("The Dark Knight")
                .year(2008)
                .directors(Set.of(directors.get(2)))
                .rating(Rating.PG_13)
                .build()
        );

        movies.add(Movie.builder()
                .id("M0008")
                .title("The Dark Knight Rises")
                .year(2012)
                .directors(Set.of(directors.get(2)))
                .rating(Rating.PG_13)
                .build()
        );
    }

    private List<Movie> getMovies() {
        initMovies();
        return movies;
    }

    private List<Director> getDirectors() {
        initDirectors();
        return directors;
    }

    @Override
    public List<Movie> getAllMovies() {
        return getMovies();
    }

    @Override
    public Optional<Movie> getMovieById(String id) {
        return getMovies().stream().filter(movie -> movie.getId().equals(id)).findFirst();
    }

    @Override
    public List<Movie> getMoviesByDirectorId(String directorId) {
        return getMovies().stream()
                .filter(movie -> movie.getDirectors().stream()
                        .anyMatch(director -> director.getId().equals(directorId))
                )
                .collect(Collectors.toList());
    }

    @Override
    public List<Director> getAllDirectors() {
        return getDirectors();
    }

    @Override
    public Optional<Director> getDirectorById(String id) {
        return getDirectors().stream().filter(director -> director.getId().equals(id)).findFirst();
    }
}
