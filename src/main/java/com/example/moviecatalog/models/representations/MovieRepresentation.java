package com.example.moviecatalog.models.representations;

import com.example.moviecatalog.models.Rating;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.springframework.hateoas.RepresentationModel;

import java.util.Set;

@Builder
@Getter
@EqualsAndHashCode(callSuper = false)
public class MovieRepresentation extends RepresentationModel<MovieRepresentation> {
    private final String id;
    private final String title;
    private final int year;
    private final Rating rating;
    private final Set<DirectorRepresentation> directors;
}
