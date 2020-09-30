package com.example.moviecatalog.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.LinkRelation;
import org.springframework.hateoas.Links;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;

class RootControllerTest {

    private final RootController rootController = new RootController();

    @Test
    void shouldHaveLinksToSelfAndResourcesLists() {
        //when
        ResponseEntity<RepresentationModel<?>> response = rootController.getRoot();
        //then
        Links links = response.getBody().getLinks();
        assertThat(links).extracting(Link::getHref).contains("/", "/directors", "/movies");
        assertThat(links).extracting(Link::getRel)
                .containsExactlyInAnyOrder(LinkRelation.of("self"), LinkRelation.of("movies"), LinkRelation.of("directors"));
    }
}
