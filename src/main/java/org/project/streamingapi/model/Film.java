package org.project.streamingapi.model;

import jakarta.persistence.*;

@Entity
@Table(name = "films")
public class Film extends Content{

    @Column(nullable = false)
    private int duration;

    public Film() {
    }

    public Film(String name, int duration, double rating) {
        super(name, rating);
        this.duration = duration;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }
}

