package com.example.moviecatalog.assemblers;

import com.example.moviecatalog.controllers.DirectorController;
import com.example.moviecatalog.models.Director;
import com.example.moviecatalog.models.representations.DirectorRepresentation;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class DirectorRepresentationAssembler implements RepresentationModelAssembler<Director, DirectorRepresentation> {
    @Override
    public DirectorRepresentation toModel(Director entity) {
        DirectorRepresentation directorRepresentation = DirectorRepresentation.builder()
                .id(entity.getId())
                .firstname(entity.getFirstname())
                .lastname(entity.getLastname())
                .year(entity.getYear())
                .build();

        directorRepresentation.add(linkTo(methodOn(DirectorController.class).getDirectorById(directorRepresentation.getId())).withSelfRel());

        return directorRepresentation;
    }

    @Override
    public CollectionModel<DirectorRepresentation> toCollectionModel(Iterable<? extends Director> entities) {
        CollectionModel<DirectorRepresentation> directorRepresentations = RepresentationModelAssembler.super.toCollectionModel(entities);

        directorRepresentations.add(linkTo(methodOn(DirectorController.class).getAllDirectors()).withSelfRel());

        return directorRepresentations;
    }
}
