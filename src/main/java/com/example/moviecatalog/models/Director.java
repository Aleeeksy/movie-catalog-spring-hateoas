package com.example.moviecatalog.models;

import lombok.*;
import net.minidev.json.annotate.JsonIgnore;
import org.springframework.hateoas.RepresentationModel;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.Set;

@Entity
@Builder
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Director {
    @Id
    @GeneratedValue
    @Getter
    private Long id;
    @Getter
    private String firstname;
    @Getter
    private String lastname;
    @Getter
    private int year;

    @JsonIgnore
    @OneToMany(mappedBy = "director")
    private Set<Movie> movies;

    public Director(String firstname, String lastname, int year) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.year = year;
    }
}
