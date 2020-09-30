package com.example.moviecatalog.services;

import com.example.moviecatalog.models.Director;
import com.example.moviecatalog.repositories.DirectorRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DirectorService {
    private final DirectorRepository directorRepository;

    public DirectorService(DirectorRepository directorRepository) {
        this.directorRepository = directorRepository;
    }

    public List<Director> getAllDirectors() {
        return directorRepository.getAllDirectors();
    }

    public Optional<Director> getDirectorById(String id) {
        return directorRepository.getDirectorById(id);
    }
}
