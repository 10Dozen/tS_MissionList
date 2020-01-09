package ru.ts.missioninfograbber.entity;

import java.util.Objects;

public class MissionData {
    private final String title;
    private final String overview;
    private final String filename;
    private final String terrain;
    private final String slots;

    public MissionData(String title, String overview, String filename, String terrain, String slots) {
        this.title = title;
        this.overview = overview;
        this.filename = filename;
        this.terrain = terrain;

        if (slots.isEmpty()) {
            slots = "0";
        }
        this.slots = slots;
    }

    public String getTitle() {
        return title;
    }

    public String getOverview() {
        return overview;
    }

    public String getFilename() {
        return filename;
    }

    public String getTerrain() {
        return terrain;
    }

    public String getSlots() {
        return slots;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MissionData that = (MissionData) o;
        return Objects.equals(title, that.title) &&
                Objects.equals(overview, that.overview) &&
                Objects.equals(filename, that.filename) &&
                Objects.equals(terrain, that.terrain) &&
                Objects.equals(slots, that.slots);
    }

    @Override
    public String toString() {
        return "MissionData{" +
                "title='" + title + '\'' +
                ", overview='" + overview + '\'' +
                ", filename='" + filename + '\'' +
                ", terrain='" + terrain + '\'' +
                ", slots='" + slots + '\'' +
                '}';
    }
}