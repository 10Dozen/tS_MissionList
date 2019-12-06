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
    private final String dataFilePre = "var missionsInfo = [\n";
    private final String dataFilePost = "\n];";
    private int written = 0;
    private boolean isWritingDataFile = false;

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

    public void export(String exportPath, MissionData missionData, BriefingData briefingData,
                       boolean append) throws IOException {
        StringBuilder jsonBuilder = formatExportData(missionData, briefingData);

        writeFoTile(exportPath, jsonBuilder, append, false);
        written++;
    }

    public void initDataFile(String exportPath) throws IOException {
        isWritingDataFile = true;
        StringBuilder sb = new StringBuilder(dataFilePre);
        writeFoTile(exportPath, sb, false, true);
        written++;
    }

    public void finishDataFile(String exportPath) throws IOException {
        StringBuilder sb = new StringBuilder(dataFilePost);
        writeFoTile(exportPath, sb, true, true);
        written++;
    }

    private StringBuilder formatExportData(MissionData mission, BriefingData briefing) {
        StringBuilder jsonFormatted = new StringBuilder();
        jsonFormatted.append("{")
                .append(formatNonStringNode(ExportFields.ID.getName(), generateHash(mission.getFilename())))
                .append(formatStringNode(ExportFields.FILENAME.getName(), mission.getFilename()))
                .append(formatStringNode(ExportFields.TITLE.getName(), mission.getTitle()))
                .append(formatNonStringNode(ExportFields.SLOTS.getName(), mission.getSlots()))
                .append(formatStringNode(ExportFields.TERRAIN.getName(), mission.getTerrain()))
                .append(formatStringNode(ExportFields.TAGS.getName(), "[]"))
                .append(formatStringNode(ExportFields.OVERVIEW.getName(), mission.getOverview()))
                .append(formatStringNode(ExportFields.BRIEFING.getName(), briefing.getText()))
                .append(formatNode(ExportFields.MAPSHOT.getName(), "", true, true))
                .append("\n}");

        return jsonFormatted;
    }

    private String formatNode(String key, String value, boolean quoted, boolean isLast) {
        nodeBuilder.setLength(0);

        nodeBuilder.append("\n\t").append(key).append(": ");

        if (quoted) {
            nodeBuilder.append("\"").append(value).append("\"");
        } else {
            nodeBuilder.append(value);
        }

        if (!isLast) {
            nodeBuilder.append(",");
        }

        return nodeBuilder.toString();
    }

    private String formatStringNode(String key, String value) {
        return formatNode(key, value, true, false);
    }

    private String formatNonStringNode(String key, String value) {
        return formatNode(key, value, false,false);
    }

    private String generateHash(String value) {
        int hash = 7;
        for (int i = 0; i < value.length(); i++) {
            hash = hash * 31 + value.charAt(i);
        }

        return "" + Math.abs(hash);
    }

    private void writeFoTile(String exportPath, StringBuilder missionInfo,
                             boolean append, boolean skipComma) throws IOException {
        StandardOpenOption option = StandardOpenOption.CREATE;
        Path outputFile = Paths.get(exportPath);
        boolean addLeadingComma = false;

        if (Files.exists(outputFile)) {
            if (append) {
                option = StandardOpenOption.APPEND;

                if (skipComma) {
                    addLeadingComma = false;
                } else {
                    if (isWritingDataFile && written < 2) {
                        addLeadingComma = false;
                    } else {
                        addLeadingComma = true;
                    }
                }
            } else {
                Files.deleteIfExists(outputFile);
            }
        }

        try (
                BufferedWriter writer =
                        Files.newBufferedWriter(outputFile, Charset.forName("UTF-8"), option)
        ) {

            if (addLeadingComma) {
                writer.write("\n,");
            }
            writer.write(missionInfo.toString());
        }
    }
}
