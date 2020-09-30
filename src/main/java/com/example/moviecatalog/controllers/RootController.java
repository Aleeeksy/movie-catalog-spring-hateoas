package com.example.moviecatalog.controllers;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class RootController {
    @GetMapping("/")
    public ResponseEntity<RepresentationModel<?>> getRoot() {
        RepresentationModel<?> representationModel = new RepresentationModel<>();
        representationModel.add(linkTo(methodOn(RootController.class).getRoot()).withSelfRel());
        representationModel.add(linkTo(methodOn(DirectorController.class).getAllDirectors()).withRel("directors"));
        representationModel.add(linkTo(methodOn(MovieController.class).getAllMovies()).withRel("movies"));
        return ResponseEntity.ok(representationModel);
    }
}
