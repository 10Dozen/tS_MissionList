package ru.ts.missionlist.grabber.logic;

import javafx.collections.ObservableList;
import ru.ts.missionlist.grabber.entity.BriefingData;
import ru.ts.missionlist.grabber.entity.MissionData;

import java.io.File;
import java.io.IOException;

public class MissionInfoGrabber {
    private MissionFileReader missionReader = new MissionFileReader();
    private BriefingReader briefingReader = new BriefingReader();
    private MissionInfoExporter exporter = new MissionInfoExporter();

    public void grab(String rootDirectory, ObservableList pathlist, String exportPath, boolean append) throws IOException {
        // --- Bulk export
        for (Object o : pathlist) {
            if (o instanceof String) {
                String path = rootDirectory + File.separator + (String) o;

                grabMissionInfo(path, exportPath, append);

                // -- Turns off overwrite after first item processed
                //    all next items should be appended to overwritten file
                if (!append) {
                    append = true;
                }
            }
        }
    }

    private void grabMissionInfo(String path, String exportPath, boolean append) throws IOException {
        MissionData missionData = missionReader.read(path);
        BriefingData briefingData = briefingReader.read(path);

        exporter.export(exportPath, missionData, briefingData, append);
    }

}
