package org.project.streamingapi.service;

import org.project.streamingapi.exception.*;
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
        Series series = seriesRepository.findById(seriesId)
                .orElseThrow(() -> new ResourceNotFoundException("Series with id " + seriesId + " not found"));

        if (episodeRepository.existsBySeriesIdAndName(seriesId, episode.getName())) {
            throw new DuplicateResourceException("Episode with name '" + episode.getName() + "' already exists in series " + seriesId);
        }

        episode.setSeries(series);
        return episodeRepository.save(episode);
    }

    public List<Episode> getAll() {
        return episodeRepository.findAll();
    }

    public Episode getById(Long id) {
        return episodeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Episode with id " + id + " not found"));
    }

    public Episode update(Long id, Episode updatedEpisode) {
        Episode episode = getById(id);
        Long seriesId = episode.getSeries().getId();
        if (updatedEpisode.getName() != null
                && !updatedEpisode.getName().equals(episode.getName())
                && episodeRepository.existsBySeriesIdAndName(seriesId, updatedEpisode.getName())) {
            throw new DuplicateResourceException(
                    "Episode with name '" + updatedEpisode.getName() + "' already exists in series " + seriesId
            );
        }

        episode.setName(updatedEpisode.getName());
        episode.setDuration(updatedEpisode.getDuration());

        return episodeRepository.save(episode);
    }

    public void delete(Long id){
        episodeRepository.deleteById(id);
    }
}
