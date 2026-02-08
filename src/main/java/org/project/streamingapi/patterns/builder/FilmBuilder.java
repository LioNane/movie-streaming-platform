package org.project.streamingapi.patterns.builder;

import org.project.streamingapi.exception.InvalidInputException;
import org.project.streamingapi.model.Film;

public class FilmBuilder {

    private String name;
    private double rating;
    private int duration;

    public FilmBuilder name(String name) {
        this.name = name;
        return this;
    }

    public FilmBuilder rating(double rating) {
        this.rating = rating;
        return this;
    }

    public FilmBuilder duration(int duration) {
        this.duration = duration;
        return this;
    }

    public Film build() {
        if (name == null || name.isBlank()) {
            throw new InvalidInputException("Film name is required");
        }
        if (duration <= 0) {
            throw new InvalidInputException("Film duration must be > 0");
        }
        return new Film(name, duration, rating);
    }
}

