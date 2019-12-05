package ru.ts.missionlist.grabber.entity;

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
}
