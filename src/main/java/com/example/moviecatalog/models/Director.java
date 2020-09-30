package com.example.moviecatalog.models;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
public class Director {
    private String id;
    private String firstname;
    private String lastname;
    private int year;
}
