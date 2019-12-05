package ru.ts.missionlist.grabber.logic;

import ru.ts.missionlist.grabber.entity.MissionData;

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

    private class MissionDetails implements Comparable<MissionDetails> {
        public Details type;
        public String value;

        public MissionDetails(Details type, String value) {
            this.type = type;
            this.value = value;
        }

        @Override
        public int compareTo(MissionDetails o) {
            if (o == null) return 1;
            return this.type.compareTo(o.type);
        }
    }

    private enum Details {
        TITLE, OVERVIEW, SLOTS
    }

    public MissionData read(String path) throws IOException {
        String[] split = path.split("\\\\");
        String filename = split[split.length - 1];

        String[] filenameParts = filename.split("\\.");
        String terrain = filenameParts[filenameParts.length - 1];
        terrain = terrain.substring(0, 1).toUpperCase() + terrain.substring(1); // Make first symbol uppercase

        Path missionFile = Paths.get(path).resolve("mission.sqm");
        Map<Details, String> details = Files.lines(missionFile)
                .map(e -> {
                    Matcher titleMatcher = briefingNamePattern.matcher(e);
                    Matcher overviewMatcher = overviewTextPattern.matcher(e);
                    Matcher slotsMatcher = maxPlayersPattern.matcher(e);

                    if (titleMatcher.find()) {
                        return new MissionDetails(Details.TITLE, titleMatcher.group(1));
                    } else if (overviewMatcher.find()) {
                        return new MissionDetails(Details.OVERVIEW, overviewMatcher.group(1));
                    } else if (slotsMatcher.find()) {
                        return new MissionDetails(Details.SLOTS, slotsMatcher.group(1));
                    } else {
                        return null;
                    }
                })
                .filter(Objects::nonNull)
                .collect(TreeMap::new, (m,c) -> m.put(c.type, c.value), (m, u) -> {});

        return new MissionData(
                details.get(Details.TITLE)
                , details.get(Details.OVERVIEW)
                , filename
                , terrain
                , details.get(Details.SLOTS)
        );
    }
}
