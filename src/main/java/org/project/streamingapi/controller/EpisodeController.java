package org.project.streamingapi.controller;

import org.project.streamingapi.model.Episode;
import org.project.streamingapi.service.EpisodeService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/episodes/")
public class EpisodeController {
    private final EpisodeService episodeService;

    public EpisodeController(EpisodeService episodeService) {
        this.episodeService = episodeService;
    }

    public Episode create(@PathVariable Long seriesId, @RequestBody Episode episode) {
        return episodeService.create(seriesId, episode);
    }

    @GetMapping
    public List<Episode> getAll() {
        return episodeService.getAll();
    }

    @GetMapping("/{id}")
    public Episode getById(@PathVariable Long id) {
        return episodeService.getById(id);
    }

    @PutMapping("/{id}")
    public Episode update(@PathVariable Long id, @RequestBody Episode episode) {
        return episodeService.update(id, episode);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        episodeService.delete(id);
    }
}
