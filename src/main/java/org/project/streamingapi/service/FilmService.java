package org.project.streamingapi.service;

import org.project.streamingapi.exception.*;
import org.project.streamingapi.model.Film;
import org.project.streamingapi.patterns.builder.FilmBuilder;
import org.project.streamingapi.patterns.singleton.InMemoryCache;
import org.project.streamingapi.patterns.singleton.LoggingService;
import org.project.streamingapi.repository.FilmRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FilmService {
    private static final String CACHE_KEY_ALL_FILMS = "films_all";

    private final FilmRepository filmRepository;
    private final InMemoryCache cache = InMemoryCache.getInstance();

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
        Film saved = filmRepository.save(toSave);
        cache.evict(CACHE_KEY_ALL_FILMS);
        return saved;
    }

    public List<Film> getAll() {
        return cache.getOrCompute(CACHE_KEY_ALL_FILMS, filmRepository::findAll);
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

        Film saved = filmRepository.save(film);
        cache.evict(CACHE_KEY_ALL_FILMS);
        return saved;
    }

    public void delete(Long id){
        LoggingService.getInstance().info("Deleting film with id " + id);
        filmRepository.deleteById(id);
        cache.evict(CACHE_KEY_ALL_FILMS);
    }
}
