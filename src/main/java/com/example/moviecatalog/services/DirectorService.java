package com.example.moviecatalog.services;

import com.example.moviecatalog.models.Director;

import java.util.List;
import java.util.Optional;

public interface DirectorService {
    List<Director> getAllDirectors();

    Optional<Director> getDirectorById(String id);
}
