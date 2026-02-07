package org.project.streamingapi.service;

import org.project.streamingapi.exception.*;
import org.project.streamingapi.model.Film;
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
        if (filmRepository.existsByName(film.getName())) {
            throw new DuplicateResourceException("Film with name '" + film.getName() + "' already exists");
        }
        return filmRepository.save(film);
    }

    public List<Film> getAll() {
        return filmRepository.findAll();
    }

    public Film getById(Long id) {
        return filmRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Film with id " + id + " not found"));
    }

    public Film update(Long id, Film updatedFilm) {
        Film film = getById(id);
        if (updatedFilm.getName() != null
                && !updatedFilm.getName().equals(film.getName())
                && filmRepository.existsByName(updatedFilm.getName())) {
            throw new DuplicateResourceException("Film with name '" + updatedFilm.getName() + "' already exists");
        }

        film.setName(updatedFilm.getName());
        film.setRating(updatedFilm.getRating());
        film.setDuration(updatedFilm.getDuration());

        return filmRepository.save(film);
    }

    public void delete(Long id){
        filmRepository.deleteById(id);
    }
}
