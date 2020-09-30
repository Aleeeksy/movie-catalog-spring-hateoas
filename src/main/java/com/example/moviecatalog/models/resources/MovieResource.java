package com.example.moviecatalog.models.resources;

import com.example.moviecatalog.models.Rating;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.springframework.hateoas.RepresentationModel;

import java.util.Set;

@Builder
@Getter
@EqualsAndHashCode(callSuper = false)
public class MovieResource extends RepresentationModel<MovieResource> {
    private final String id;
    private final String title;
    private final int year;
    private final Rating rating;
    private final Set<DirectorResource> directors;
}
