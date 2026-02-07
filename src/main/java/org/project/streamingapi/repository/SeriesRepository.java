package org.project.streamingapi.repository;

import org.project.streamingapi.model.Series;
import org.springframework.data.jpa.repository.JpaRepository;


public interface SeriesRepository extends JpaRepository<Series, Long> {
    boolean existsByName(String name);
}
