package ru.ts.missioninfograbber.logic;


import ru.ts.missioninfograbber.entity.BriefingData;
import ru.ts.missioninfograbber.entity.MissionData;

import javafx.collections.ObservableList;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class MissionInfoGrabber {
    private MissionFileReader missionReader = new MissionFileReader();
    private BriefingFileReader briefingReader = new BriefingFileReader();
    private MissionInfoExporter exporter = new MissionInfoExporter();

    public void grab(String rootDirectory, ObservableList pathlist, String exportPath,
                     boolean append, boolean asDataFile) throws IOException {

        if (asDataFile) { // Initially create file and make all next write operations to append
            exporter.initDataFile(exportPath);
            append = true;
        }

        // --- Bulk export of mission info
        for (Object directory : pathlist) {
            if (directory instanceof String) {
                Path missionPath = Paths.get(rootDirectory, (String) directory);

                if (Files.exists(missionPath)) {
                    grabMissionInfo(missionPath.toString(), exportPath, append);

                    if (!append) { // -- Turns off overwrite after first item processed all next items should be appended
                        append = true;
                    }
                }
            }
        }

        if (asDataFile) { // --- Add data file tail data to file
            exporter.finishDataFile(exportPath);
        }
    }

    private void grabMissionInfo(String path, String exportPath, boolean append) throws IOException {
        MissionData missionData = missionReader.read(path);
        BriefingData briefingData = briefingReader.read(path);

        exporter.export(exportPath, missionData, briefingData, append);
    }
}
