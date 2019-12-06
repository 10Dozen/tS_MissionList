package ru.ts.missionlist.grabber.logic;

import javafx.collections.ObservableList;
import ru.ts.missionlist.grabber.entity.BriefingData;
import ru.ts.missionlist.grabber.entity.MissionData;

import java.io.IOException;
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
                String path = (Paths.get(rootDirectory, (String) directory)).toString();

                grabMissionInfo(path, exportPath, append);

                if (!append) { // -- Turns off overwrite after first item processed all next items should be appended
                    append = true;
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
