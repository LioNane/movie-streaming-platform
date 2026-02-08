package org.project.streamingapi.patterns.builder;

import org.project.streamingapi.exception.InvalidInputException;
import org.project.streamingapi.model.Episode;
import org.project.streamingapi.model.Series;

import java.util.ArrayList;
import java.util.List;

public class SeriesBuilder {

    private String name;
    private double rating;
    private final List<EpisodeDraft> drafts = new ArrayList<>();

    public SeriesBuilder name(String name) {
        this.name = name;
        return this;
    }

    public SeriesBuilder rating(double rating) {
        this.rating = rating;
        return this;
    }

    public SeriesBuilder addEpisode(String episodeName, int duration) {
        this.drafts.add(new EpisodeDraft(episodeName, duration));
        return this;
    }

    public Series build() {
        if (name == null || name.isBlank()) {
            throw new InvalidInputException("Series name is required");
        }

        Series series = new Series(name, rating);

        if (!drafts.isEmpty()) {
            List<Episode> episodes = new ArrayList<>();
            for (EpisodeDraft d : drafts) {
                episodes.add(new EpisodeBuilder()
                        .name(d.name)
                        .duration(d.duration)
                        .buildFor(series));
            }
            series.setEpisodes(episodes);
        }

        return series;
    }

    private record EpisodeDraft(String name, int duration) {}
}
