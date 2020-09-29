package com.example.moviecatalog.models;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

import java.util.Set;

@Builder
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
public class Movie extends RepresentationModel<Movie> {
    private String id;
    private String title;
    private int year;
    private Rating rating;
    private Set<Director> directors;
}
