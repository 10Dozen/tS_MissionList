package ru.ts.missioninfograbber.logic;

import ru.ts.missioninfograbber.entity.BriefingData;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BriefingFileReader {
    private final Pattern topicStartPattern = Pattern.compile("TOPIC\\(\"(.*)\"\\)");
    private final Pattern tagsPattern = Pattern.compile("TAGS\\((\\[.*\\])\\)");
    private final String topicStartTag = "TOPIC";
    private final String topicEndTag = "END";

    public BriefingData read(String path) {
        Path briefingFile = Paths.get(path).resolve("dzn_tSFramework\\Modules\\Briefing\\tSF_briefing.sqf");

        List<String> lines = null;
        try {
            lines = Files.readAllLines(briefingFile);
        } catch (IOException e) {
            return new BriefingData();
        }

        StringBuilder briefingBuffer = parseBriefingText(lines);
        String tags = parseTagsInfo(lines);

        return new BriefingData(briefingBuffer.toString(), tags);
    }

    private StringBuilder parseBriefingText(List<String> lines) {
        StringBuilder textBuffer = new StringBuilder();

        boolean topicStarted = false;
        for (String line : lines) {
            line = line.trim();

            if (topicStarted) {
                if (line.startsWith(topicEndTag)) {
                    topicStarted = false;
                } else {
                    String trimmedLine = trimQuotesFromLine(line);
                    textBuffer.append(trimmedLine);
                }
            } else {
                if (line.startsWith(topicStartTag)) {
                    topicStarted = true;
                    Matcher m = topicStartPattern.matcher(line);

                    if (m.find()) {
                        textBuffer.append("<br /><br />" + m.group(1) + "<br />");
                    }
                }
            }
        }

        return textBuffer;
    }

    private String parseTagsInfo(List<String> lines) {
        for (String line : lines) {
            Matcher m = tagsPattern.matcher(line);
            if (m.find()) {
                return m.group(1);
            }
        }

        return "[]";
    }

    protected String trimQuotesFromLine(String line) {
        // Trim lines from leading and tailing quotes
        line = line.replaceAll("\"\"", "'");
        String trimmedLine = null;

        if (line.startsWith("\"")) {
            trimmedLine = line.substring(1);

            if (trimmedLine.endsWith("\"")) {
                trimmedLine = trimmedLine.substring(0, trimmedLine.length() -1);
            }
        } else if (line.endsWith("\"")) {
            trimmedLine = line.substring(0, line.length() - 1);
        } else {
            trimmedLine = line;
        }

        return trimmedLine;
    }
}
