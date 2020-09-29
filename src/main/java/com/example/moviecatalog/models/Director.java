package com.example.moviecatalog.models;

import lombok.*;
import org.springframework.hateoas.RepresentationModel;

@Builder
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
public class Director extends RepresentationModel<Director> {
    private String id;
    private String firstname;
    private String lastname;
    private int year;
}
