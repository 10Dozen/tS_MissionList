package ru.ts.missionlist.grabber.logic;

import ru.ts.missionlist.grabber.entity.BriefingData;
import ru.ts.missionlist.grabber.entity.MissionData;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class MissionInfoExporter {
    private final StringBuilder nodeBuilder = new StringBuilder();

    public void export(String exportPath, MissionData missionData, BriefingData briefingData, boolean append) throws IOException {
        StringBuilder jsonBuilder = formatExportData(missionData, briefingData);

        writeFoTile(exportPath, jsonBuilder, append);
    }

    private enum ExportFields {
        FILENAME("filename")
        , TITLE("title")
        , SLOTS("player_count")
        , TERRAIN("terrain")
        , OVERVIEW("overview")
        , BRIEFING("briefing")
        , ID("id")
        , MAPSHOT("map_shot")
        , TAGS("tags");

        private String name;
        ExportFields(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }

    private StringBuilder formatExportData(MissionData mission, BriefingData briefing) {
        StringBuilder jsonFormatted = new StringBuilder();
        jsonFormatted.append("{")
                .append(formatNonStringNode(ExportFields.ID.getName(), generateHash(mission.getFilename())))
                .append(formatNode(ExportFields.FILENAME.getName(), mission.getFilename()))
                .append(formatNode(ExportFields.TITLE.getName(), mission.getTitle()))
                .append(formatNonStringNode(ExportFields.SLOTS.getName(), mission.getSlots()))
                .append(formatNode(ExportFields.TERRAIN.getName(), mission.getTerrain()))
                .append(formatNonStringNode(ExportFields.TAGS.getName(), "[]"))
                .append(formatNode(ExportFields.OVERVIEW.getName(), mission.getOverview()))
                .append(formatNode(ExportFields.BRIEFING.getName(), briefing.getText()))
                .append(formatNode(ExportFields.MAPSHOT.getName(), "", true))
                .append("}");

        return jsonFormatted;
    }

    private String formatNode(String key, String value) {
        return formatNode(key, value, false);
    }

    private String formatNode(String key, String value, boolean isLast) {
        nodeBuilder.setLength(0);

        nodeBuilder.append(key)
                .append(": \"")
                .append(value)
                .append("\"");

        if (!isLast) {
            nodeBuilder.append(",");
        }

        return nodeBuilder.toString();
    }

    private String formatNonStringNode(String key, String value) {
        return formatNonStringNode(key, value, false);
    }

    private String formatNonStringNode(String key, String value, boolean isLast) {
        nodeBuilder.setLength(0);
        nodeBuilder.append(key).append(": ").append(value);

        if (!isLast) {
            nodeBuilder.append(",");
        }

        return nodeBuilder.toString();
    }

    private String generateHash(String value) {
        int hash = 7;
        for (int i = 0; i < value.length(); i++) {
            hash = hash * 31 + value.charAt(i);
        }

        return "" + Math.abs(hash);
    }

    private void writeFoTile(String exportPath, StringBuilder missionInfo, boolean append) throws IOException {
        StandardOpenOption option = StandardOpenOption.CREATE;
        Path outputFile = Paths.get(exportPath);
        boolean addLeadingComma = false;

        if (Files.exists(outputFile)) {
            if (append) {
                option = StandardOpenOption.APPEND;
                addLeadingComma = true;
            } else {
                Files.deleteIfExists(outputFile);
            }
        }

        try (
                BufferedWriter writer =
                        Files.newBufferedWriter(outputFile, Charset.forName("UTF-8"), option)
        ) {

            if (addLeadingComma) {
                writer.write(", ");
            }
            writer.write(missionInfo.toString());
        }
    }
}
