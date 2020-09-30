package com.example.moviecatalog.models;

import com.example.moviecatalog.controllers.DirectorController;
import com.example.moviecatalog.controllers.RootController;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class DirectorAssemblerV2 implements RepresentationModelAssembler<Director, EntityModel<? extends Director>> {

    @Override
    public EntityModel<Director> toModel(Director director) {
        EntityModel<Director> entityModel = EntityModel.of(director);
        entityModel.add(linkTo(methodOn(DirectorController.class).getDirectorById(director.getId())).withSelfRel());
        entityModel.add(linkTo(methodOn(DirectorController.class).getAllDirectors()).withRel("directors"));
        return entityModel;
    }

    @Override
    public CollectionModel<EntityModel<? extends Director>> toCollectionModel(Iterable<? extends Director> directors) {
        List<EntityModel<? extends Director>> directorsEntityModel = StreamSupport.stream(directors.spliterator(), false)
                .map(this::toModel)
                .collect(Collectors.toList());
        CollectionModel<EntityModel<? extends Director>> directorsCollectionModel = CollectionModel.of(directorsEntityModel);
        directorsCollectionModel.add(linkTo(methodOn(DirectorController.class).getAllDirectors()).withSelfRel());
        directorsCollectionModel.add(linkTo(methodOn(RootController.class).getRoot()).withRel("root"));
        return directorsCollectionModel;
    }

///**
// * Version with java 10 var - to improve readability
// */
//    @Override
//    public CollectionModel<EntityModel<? extends Director>> toCollectionModel2(Iterable<? extends Director> directors) {
//        List<EntityModel<? extends Director>> directorsEntityModel = StreamSupport.stream(directors.spliterator(), false)
//                .map(this::toModel)
//                .collect(Collectors.toList());
//        var directorsCollectionModel = CollectionModel.of(directorsEntityModel);
//        directorsCollectionModel.add(linkTo(methodOn(DirectorController.class).getAllDirectors()).withSelfRel());
//        return directorsCollectionModel;
//    }
}
