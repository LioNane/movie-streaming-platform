package org.project.streamingapi.patterns.factory;

import org.project.streamingapi.exception.InvalidInputException;
import org.project.streamingapi.model.Content;
import org.project.streamingapi.model.Film;
import org.project.streamingapi.model.Series;

public class ContentFactory {

    public Content create(ContentType type, String name, double rating, Integer durationOrNull) {
        if (type == null) {
            throw new InvalidInputException("Content type must not be null");
        }
        if (name == null || name.isBlank()) {
            throw new InvalidInputException("Name must not be blank");
        }

        return switch (type) {
            case FILM -> {
                if (durationOrNull == null || durationOrNull <= 0) {
                    throw new InvalidInputException("Film duration must be > 0");
                }
                yield new Film(name, durationOrNull, rating);
            }
            case SERIES -> new Series(name, rating);
        };
    }
}