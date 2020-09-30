package com.example.moviecatalog;

import com.example.moviecatalog.models.Director;
import com.example.moviecatalog.models.Movie;
import com.example.moviecatalog.models.Rating;
import com.example.moviecatalog.repositories.DirectorRepository;
import com.example.moviecatalog.repositories.MovieRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class DatabaseLoader {

    @Bean
    public CommandLineRunner init(DirectorRepository directorRepository, MovieRepository movieRepository) {
        return args -> {
            Director sergio = new Director("Sergio", "Leone", 1929);
            Director steven = new Director("Steven", "Spielberg", 1946);
            Director christopher = new Director("Christopher", "Nolan", 1970);
            directorRepository.save(sergio);
            directorRepository.save(steven);
            directorRepository.save(christopher);

            movieRepository.save(new Movie("Once Upon a Time in the West",1968, Rating.PG_13, sergio));
            movieRepository.save(new Movie("Once Upon a Time in America",1984, Rating.R, sergio));
            movieRepository.save(new Movie("The Lost World: Jurassic Park",1984, Rating.PG_13, steven));
            movieRepository.save(new Movie("Raiders of the Lost Ark",1981, Rating.PG, steven));
            movieRepository.save(new Movie("Catch Me If You Can",2002, Rating.PG_13, steven));
            movieRepository.save(new Movie("Batman Begins",2005, Rating.PG_13, christopher));
            movieRepository.save(new Movie("The Dark Knight",2008, Rating.PG_13, christopher));
            movieRepository.save(new Movie("The Dark Knight Rises",2012, Rating.PG_13, christopher));
        };
    }
}
