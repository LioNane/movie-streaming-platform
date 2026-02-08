package org.project.streamingapi.patterns.builder;

import org.project.streamingapi.exception.InvalidInputException;
import org.project.streamingapi.model.Episode;
import org.project.streamingapi.model.Series;

public class EpisodeBuilder {

    private String name;
    private int duration;

    public EpisodeBuilder name(String name) {
        this.name = name;
        return this;
    }

    public EpisodeBuilder duration(int duration) {
        this.duration = duration;
        return this;
    }

    public Episode buildFor(Series series) {
        if (series == null) {
            throw new InvalidInputException("Series is required for episode");
        }
        if (name == null || name.isBlank()) {
            throw new InvalidInputException("Episode name is required");
        }
        if (duration <= 0) {
            throw new InvalidInputException("Episode duration must be > 0");
        }
        return new Episode(name, duration, series);
    }
}

