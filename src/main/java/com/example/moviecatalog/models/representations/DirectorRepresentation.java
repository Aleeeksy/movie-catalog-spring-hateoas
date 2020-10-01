package com.example.moviecatalog.models.representations;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

@Builder
@Getter
@EqualsAndHashCode(callSuper = false)
@Relation(itemRelation = "director", collectionRelation = "directors")
public class DirectorRepresentation extends RepresentationModel<DirectorRepresentation> {
    private final String id;
    private final String firstname;
    private final String lastname;
    private final int year;
}
