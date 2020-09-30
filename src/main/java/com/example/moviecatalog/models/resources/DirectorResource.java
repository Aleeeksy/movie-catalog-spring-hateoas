package com.example.moviecatalog.models.resources;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.springframework.hateoas.RepresentationModel;

@Builder
@Getter
@EqualsAndHashCode(callSuper = false)
public class DirectorResource extends RepresentationModel<DirectorResource> {
    private final String id;
    private final String firstname;
    private final String lastname;
    private final int year;
}
