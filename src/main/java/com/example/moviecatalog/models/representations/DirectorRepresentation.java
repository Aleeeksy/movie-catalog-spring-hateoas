package com.example.moviecatalog.models.representations;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.springframework.hateoas.RepresentationModel;

@Builder
@Getter
@EqualsAndHashCode(callSuper = false)
public class DirectorRepresentation extends RepresentationModel<DirectorRepresentation> {
    private final String id;
    private final String firstname;
    private final String lastname;
    private final int year;
}
