package org.project.streamingapi.service;

import org.project.streamingapi.exception.*;
import org.project.streamingapi.model.Series;
import org.project.streamingapi.repository.SeriesRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SeriesService {
    private final SeriesRepository seriesRepository;

    public SeriesService(SeriesRepository seriesRepository){
        this.seriesRepository = seriesRepository;
    }

    public Series create(Series series) {
        if (seriesRepository.existsByName(series.getName())) {
            throw new DuplicateResourceException("Film with name '" + series.getName() + "' already exists");
        }
        return seriesRepository.save(series);
    }

    public List<Series> getAll() {
        return seriesRepository.findAll();
    }

    public Series getById(Long id) {
        return seriesRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Film with id " + id + " not found"));
    }

    public Series update(Long id, Series updatedSeries) {
        Series film = getById(id);
        if (updatedSeries.getName() != null
                && !updatedSeries.getName().equals(film.getName())
                && seriesRepository.existsByName(updatedSeries.getName())) {
            throw new DuplicateResourceException("Film with name '" + updatedSeries.getName() + "' already exists");
        }

        film.setName(updatedSeries.getName());
        film.setRating(updatedSeries.getRating());

        return seriesRepository.save(film);
    }

    public void delete(Long id){
        seriesRepository.deleteById(id);
    }
}
