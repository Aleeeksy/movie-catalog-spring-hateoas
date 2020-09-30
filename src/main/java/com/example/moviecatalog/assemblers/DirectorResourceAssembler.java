package com.example.moviecatalog.assemblers;

import com.example.moviecatalog.controllers.DirectorController;
import com.example.moviecatalog.controllers.MovieController;
import com.example.moviecatalog.models.Director;
import com.example.moviecatalog.models.resources.DirectorResource;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class DirectorResourceAssembler implements RepresentationModelAssembler<Director, DirectorResource> {
    @Override
    public DirectorResource toModel(Director entity) {
        DirectorResource directorResource = DirectorResource.builder()
                .id(entity.getId())
                .firstname(entity.getFirstname())
                .lastname(entity.getLastname())
                .year(entity.getYear())
                .build();

        directorResource.add(linkTo(methodOn(DirectorController.class).getDirectorById(directorResource.getId())).withSelfRel());

        return directorResource;
    }

    @Override
    public CollectionModel<DirectorResource> toCollectionModel(Iterable<? extends Director> entities) {
        CollectionModel<DirectorResource> directorResources = RepresentationModelAssembler.super.toCollectionModel(entities);

        directorResources.add(linkTo(methodOn(MovieController.class).getAllMovies()).withSelfRel());

        return directorResources;
    }
}
