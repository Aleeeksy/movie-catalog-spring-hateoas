package com.example.moviecatalog.models;

import com.example.moviecatalog.controllers.DirectorController;
import com.example.moviecatalog.controllers.RootController;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.SimpleRepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class DirectorAssembler implements SimpleRepresentationModelAssembler<Director> {

    @Override
    public void addLinks(EntityModel<Director> resource) {
        Long directorId = resource.getContent().getId();
        resource.add(linkTo(methodOn(DirectorController.class).getDirectorById(directorId)).withSelfRel());
        resource.add(linkTo(methodOn(DirectorController.class).getDirectorMovies(directorId)).withRel("director_movies"));
        resource.add(linkTo(methodOn(DirectorController.class).getAllDirectors()).withRel("directors"));
    }

    @Override
    public void addLinks(CollectionModel<EntityModel<Director>> resources) {
        resources.add(linkTo(methodOn(DirectorController.class).getAllDirectors()).withSelfRel());
        resources.add(linkTo(methodOn(RootController.class).getRoot()).withRel("root"));
    }
}
