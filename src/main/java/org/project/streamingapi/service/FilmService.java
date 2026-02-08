package org.project.streamingapi.service;

import org.project.streamingapi.exception.*;
import org.project.streamingapi.model.Film;
import org.project.streamingapi.patterns.builder.FilmBuilder;
import org.project.streamingapi.patterns.singleton.LoggingService;
import org.project.streamingapi.repository.FilmRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FilmService {
    private final FilmRepository filmRepository;

    public FilmService(FilmRepository filmRepository){
        this.filmRepository = filmRepository;
    }

    public Film create(Film film) {
        LoggingService.getInstance().info("Creating film: " + film.getName());

        Film toSave = new FilmBuilder()
                .name(film.getName())
                .duration(film.getDuration())
                .rating(film.getRating())
                .build();
        if (filmRepository.existsByName(toSave.getName())) {
            throw new DuplicateResourceException("Film with name '" + film.getName() + "' already exists");
        }
        return filmRepository.save(toSave);
    }

    public List<Film> getAll() {
        return filmRepository.findAll();
    }

    public Film getById(Long id) {
        return filmRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Film with id " + id + " not found"));
    }

    public Film update(Long id, Film updatedFilm) {
        LoggingService.getInstance().info("Updating film with id " + id);
        Film film = getById(id);
        if (updatedFilm.getName() != null
                && !updatedFilm.getName().equals(film.getName())
                && filmRepository.existsByName(updatedFilm.getName())) {
            throw new DuplicateResourceException("Film with name '" + updatedFilm.getName() + "' already exists");
        }

        if (updatedFilm.getName() != null) film.setName(updatedFilm.getName());
        film.setRating(updatedFilm.getRating());
        film.setDuration(updatedFilm.getDuration());

        return filmRepository.save(film);
    }

    public void delete(Long id){
        LoggingService.getInstance().info("Deleting film with id " + id);
        filmRepository.deleteById(id);
    }
}
