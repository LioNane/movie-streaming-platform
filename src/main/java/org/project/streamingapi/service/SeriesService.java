package org.project.streamingapi.service;

import org.project.streamingapi.exception.*;
import org.project.streamingapi.model.Episode;
import org.project.streamingapi.model.Series;
import org.project.streamingapi.patterns.builder.EpisodeBuilder;
import org.project.streamingapi.patterns.factory.ContentFactory;
import org.project.streamingapi.patterns.factory.ContentType;
import org.project.streamingapi.patterns.singleton.InMemoryCache;
import org.project.streamingapi.patterns.singleton.LoggingService;
import org.project.streamingapi.repository.SeriesRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SeriesService {
    private static final String CACHE_KEY_ALL_SERIES = "series_all";

    private final SeriesRepository seriesRepository;
    private final InMemoryCache cache = InMemoryCache.getInstance();
    private final ContentFactory contentFactory = new ContentFactory();

    public SeriesService(SeriesRepository seriesRepository){
        this.seriesRepository = seriesRepository;
    }

    public Series create(Series series) {
        LoggingService.getInstance().info("Creating series: " + series.getName());
        Series toSave = (Series) contentFactory.create(
                ContentType.SERIES,
                series.getName(),
                series.getRating(),
                null
        );

        if (series.getEpisodes() != null && !series.getEpisodes().isEmpty()) {
            for (Episode e : series.getEpisodes()) {
                Episode built = new EpisodeBuilder()
                        .name(e.getName())
                        .duration(e.getDuration())
                        .buildFor(toSave);
                toSave.getEpisodes().add(built);
            }
        }
        if (seriesRepository.existsByName(series.getName())) {
            throw new DuplicateResourceException("Series with name '" + series.getName() + "' already exists");
        }
        Series saved = seriesRepository.save(toSave);
        cache.evict(CACHE_KEY_ALL_SERIES);
        return saved;
    }

    public List<Series> getAll() {
        return cache.getOrCompute(CACHE_KEY_ALL_SERIES, seriesRepository::findAll);
    }

    public Series getById(Long id) {
        return seriesRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Series with id " + id + " not found"));
    }

    public Series update(Long id, Series updatedSeries) {
        LoggingService.getInstance().info("Updating series with id " + id);
        Series series = getById(id);
        if (updatedSeries.getName() != null
                && !updatedSeries.getName().equals(series.getName())
                && seriesRepository.existsByName(updatedSeries.getName())) {
            throw new DuplicateResourceException("Series with name '" + updatedSeries.getName() + "' already exists");
        }

        if (updatedSeries.getName() != null) series.setName(updatedSeries.getName());
        series.setRating(updatedSeries.getRating());

        Series saved = seriesRepository.save(series);
        cache.evict(CACHE_KEY_ALL_SERIES);
        return saved;
    }

    public void delete(Long id){
        LoggingService.getInstance().info("Deleting series with id " + id);
        seriesRepository.deleteById(id);
        cache.evict(CACHE_KEY_ALL_SERIES);
    }
}
