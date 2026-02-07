package org.project.streamingapi.controller;

import org.project.streamingapi.model.Series;
import org.project.streamingapi.service.SeriesService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/series/")
public class SeriesController {
    private final SeriesService seriesService;

    public SeriesController(SeriesService seriesService){
        this.seriesService = seriesService;
    }

    @PostMapping
    public Series create(@RequestBody Series series){
        return seriesService.create(series);
    }

    @GetMapping
    public List<Series> getAll() {
        return seriesService.getAll();
    }

    @GetMapping("/{id}")
    public Series getById(@PathVariable Long id) {
        return seriesService.getById(id);
    }

    @PutMapping("/{id}")
    public Series update(@PathVariable Long id, @RequestBody Series series) {
        return seriesService.update(id, series);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        seriesService.delete(id);
    }
}
