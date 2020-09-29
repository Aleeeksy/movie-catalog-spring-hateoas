package com.example.moviecatalog.services;

import com.example.moviecatalog.models.Director;
import com.example.moviecatalog.repositories.DirectorRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DirectorServiceImplementation implements DirectorService {
    private final DirectorRepository directorRepository;

    public DirectorServiceImplementation(DirectorRepository directorRepository) {
        this.directorRepository = directorRepository;
    }

    @Override
    public List<Director> getAllDirectors() {
        return directorRepository.getAllDirectors();
    }

    @Override
    public Optional<Director> getDirectorById(String id) {
        return directorRepository.getDirectorById(id);
    }
}
