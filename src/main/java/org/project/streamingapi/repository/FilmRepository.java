package org.project.streamingapi.repository;

import org.project.streamingapi.model.Film;
import org.springframework.data.jpa.repository.JpaRepository;


public interface FilmRepository extends JpaRepository<Film, Long> {
    boolean existsByName(String name);
}
