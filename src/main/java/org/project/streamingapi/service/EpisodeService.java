package org.project.streamingapi.service;

import org.project.streamingapi.exception.*;
import org.project.streamingapi.patterns.builder.EpisodeBuilder;
import org.project.streamingapi.patterns.singleton.LoggingService;
import org.project.streamingapi.repository.*;
import org.project.streamingapi.model.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EpisodeService {
    private final EpisodeRepository episodeRepository;
    private final SeriesRepository seriesRepository;

    public EpisodeService(EpisodeRepository episodeRepository, SeriesRepository seriesRepository) {
        this.episodeRepository = episodeRepository;
        this.seriesRepository = seriesRepository;
    }

    public Episode create(Long seriesId, Episode episode) {
        LoggingService.getInstance().info("Creating episode: " + episode.getName() + " with seriesId =" + seriesId);
        Series series = seriesRepository.findById(seriesId)
                .orElseThrow(() -> new ResourceNotFoundException("Series with id " + seriesId + " not found"));

        if (episodeRepository.existsBySeriesIdAndName(seriesId, episode.getName())) {
            throw new DuplicateResourceException("Episode with name '" + episode.getName() + "' already exists in series " + seriesId);
        }
        Episode toSave = new EpisodeBuilder()
                .name(episode.getName())
                .duration(episode.getDuration())
                .buildFor(series);

        return episodeRepository.save(toSave);
    }

    public List<Episode> getAll() {
        return episodeRepository.findAll();
    }

    public Episode getById(Long id) {
        return episodeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Episode with id " + id + " not found"));
    }

    public Episode update(Long id, Episode updatedEpisode) {
        LoggingService.getInstance().info("Updating episode with id =" + id);
        Episode episode = getById(id);
        Long seriesId = episode.getSeries().getId();
        if (updatedEpisode.getName() != null
                && !updatedEpisode.getName().equals(episode.getName())
                && episodeRepository.existsBySeriesIdAndName(seriesId, updatedEpisode.getName())) {
            throw new DuplicateResourceException(
                    "Episode with name '" + updatedEpisode.getName() + "' already exists in series " + seriesId
            );
        }

        if (updatedEpisode.getName() != null) episode.setName(updatedEpisode.getName());
        episode.setDuration(updatedEpisode.getDuration());

        return episodeRepository.save(episode);
    }

    public void delete(Long id){
        LoggingService.getInstance().info("Deleting episode with id =" + id);
        episodeRepository.deleteById(id);
    }
}
