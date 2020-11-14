package ru.ts.missioninfograbber.logic;


import ru.ts.missioninfograbber.entity.MissionData;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MissionFileReader {
    private final Pattern briefingNamePattern = Pattern.compile("briefingName=\"(.*)\"");
    private final Pattern overviewTextPattern = Pattern.compile("overviewText=\"(.*)\"");
    private final Pattern maxPlayersPattern = Pattern.compile("maxPlayers=(.*);");
    private final Pattern slotsPrefixPattern = Pattern.compile("((co|CO)_?(\\d+))");

    private enum Details {
        TITLE, OVERVIEW, SLOTS
    }

    public MissionData read(String path) throws IOException {
        String filename = new File(path).getName();
        String[] missionFileData = parseMissionFilename(filename);

        Path missionFile = Paths.get(path).resolve("mission.sqm");
        Map<Details, String> details = Files.lines(missionFile)
                .map(e -> {
                    Matcher titleMatcher = briefingNamePattern.matcher(e);
                    Matcher overviewMatcher = overviewTextPattern.matcher(e);
                    Matcher slotsMatcher = maxPlayersPattern.matcher(e);

                    if (titleMatcher.find()) {
                        return new AbstractMap.SimpleEntry<>(Details.TITLE, titleMatcher.group(1));
                    } else if (overviewMatcher.find()) {
                        return new AbstractMap.SimpleEntry<>(Details.OVERVIEW, overviewMatcher.group(1));
                    } else if (slotsMatcher.find()) {
                        return new AbstractMap.SimpleEntry<>(Details.SLOTS, slotsMatcher.group(1));
                    } else {
                        return null;
                    }
                })
                .filter(Objects::nonNull)
                .collect(TreeMap::new, (m,c) -> m.put(c.getKey(), c.getValue()), (m, u) -> {});

        // --- Verify that data read or assign defaults
        String title = details.getOrDefault(Details.TITLE, "");
        if (title.isEmpty()) {
            title = missionFileData[0];
        }

        String slotCount = details.getOrDefault(Details.SLOTS, "");
        if (slotCount.isEmpty() || slotCount.chars().noneMatch(Character::isDigit)) {
            slotCount = missionFileData[1];
        }

        return new MissionData(
                title
                , details.getOrDefault(Details.OVERVIEW, "")
                , filename
                , missionFileData[2]
                , slotCount);
    }

    protected String[] parseMissionFilename(String filename) {
        String[] filenameParts = filename.split("\\.");

        String fullname = filenameParts[0];

        // --- Get slots count from "...COxx..." part of given filename
        Matcher slotsMatcher = slotsPrefixPattern.matcher(fullname);

        String slotsCount = "";
        if (slotsMatcher.find()) {
            slotsCount = slotsMatcher.group(3);
        }

        // --- Get terrain name
        String terrain = filenameParts[filenameParts.length - 1];

        return new String[] {
                fullname
                , slotsCount
                , makeLeadingCharUppercase(terrain)
        };
    }

    protected String makeLeadingCharUppercase(String s) {
        return (s.substring(0, 1).toUpperCase() + s.substring(1));
    }
}
