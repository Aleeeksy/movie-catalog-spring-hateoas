package com.example.moviecatalog.repositories;

import com.example.moviecatalog.models.Director;

import java.util.List;
import java.util.Optional;

public interface DirectorRepository {
    List<Director> getAllDirectors();

    Optional<Director> getDirectorById(String id);
}
