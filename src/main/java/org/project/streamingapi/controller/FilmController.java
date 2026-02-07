package org.project.streamingapi.controller;

import org.project.streamingapi.model.Film;
import org.project.streamingapi.service.FilmService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/films/")
public class FilmController {
    private final FilmService filmService;

    public FilmController(FilmService filmService){
        this.filmService = filmService;
    }

    @PostMapping
    public Film create(@RequestBody Film film){
        return filmService.create(film);
    }

    @GetMapping
    public List<Film> getAll() {
        return filmService.getAll();
    }

    @GetMapping("/{id}")
    public Film getById(@PathVariable Long id) {
        return filmService.getById(id);
    }

    @PutMapping("/{id}")
    public Film update(@PathVariable Long id, @RequestBody Film film) {
        return filmService.update(id, film);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        filmService.delete(id);
    }
}
