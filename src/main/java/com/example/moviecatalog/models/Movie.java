package com.example.moviecatalog.models;

import lombok.*;
import org.springframework.hateoas.RepresentationModel;

import javax.persistence.*;
import java.util.Set;

@Entity
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Movie {
    @Id
    @GeneratedValue
    private Long id;
    private String title;
    private int year;
    private Rating rating;

    @ManyToOne
    private Director director;

    public Movie(String title, int year, Rating rating, Director director) {
        this.title = title;
        this.year = year;
        this.rating = rating;
        this.director = director;
    }
}
