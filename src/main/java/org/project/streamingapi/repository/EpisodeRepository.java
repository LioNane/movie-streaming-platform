package org.project.streamingapi.repository;

import org.project.streamingapi.model.Episode;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EpisodeRepository extends JpaRepository<Episode, Long> {
    List<Episode> findBySeriesId(Long seriesId);
    boolean existsBySeriesIdAndName(Long seriesId, String name);
}