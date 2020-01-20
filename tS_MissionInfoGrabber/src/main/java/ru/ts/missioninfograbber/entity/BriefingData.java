package ru.ts.missioninfograbber.entity;

public class BriefingData {
    private final String text;
    private final String tags;

    public BriefingData() {
        this("","");
    }

    public BriefingData(String text, String tags) {
        if (text.isEmpty()) {
            text = "&lt;no data&gt;";
        }
        if (tags.isEmpty()) {
            tags = "[]";
        }
        this.text = text;
        this.tags = tags;
    }

    public String getText() {
        return text;
    }

    public String getTags() {
        return tags;
    }
}
