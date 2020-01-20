package ru.ts.missioninfograbber.logic;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import ru.ts.missioninfograbber.entity.BriefingData;
import ru.ts.missioninfograbber.entity.MissionData;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class MIEDataFormatterTest {
    private final boolean noisyOutput = false;
    private final MissionInfoExporter MIE = new MissionInfoExporter();
    private final ClassLoader classLoader = getClass().getClassLoader();

    private final String expectedResourceDirectory;
    private final String expectedResource;
    private final MissionData missionData;
    private final BriefingData briefingData;

    @Parameterized.Parameters
    public static Collection inputData() {
        List<Object[]> data = new ArrayList<>();
        data.add(new Object[] {
           "TestDataForExport"
           ,"ValidFormattedData.txt"
           , new MissionData("Valid Mission Title", "Valid overview", "valid_filename.terrain","Terrain", "69")
           , new BriefingData("Some valid briefing text here<br /><br />All is valid. 'Quotes' too!", "[\"SPECOPS\",\"INFANTRY\"]")
        });

        data.add(new Object[] {
            "TestDataForExport"
            ,"ValidFormattedData_2.txt"
            , new MissionData("valid_filename.terrain", "", "valid_filename.terrain","Terrain", "")
            , new BriefingData()
        });

        return data;
    }

    public MIEDataFormatterTest(String expectedResourceDirectory, String expectedResource
            , MissionData missionData, BriefingData briefingData) {
        this.expectedResourceDirectory = expectedResourceDirectory;
        this.expectedResource = expectedResource;
        this.missionData = missionData;
        this.briefingData = briefingData;
    }

    @Test
    public void formatData() throws IOException {
        StringBuilder sb = MIE.formatExportData(missionData, briefingData);
        String actualData = sb.toString();

        if (noisyOutput) {
            System.out.println("Format data:");
            System.out.println(actualData);
        }

        String expectedData = getResourceContent();
        if (noisyOutput) {
            System.out.println("Expected:");
            System.out.println(expectedData);
            System.out.println("---");
        }

        assertEquals("Data is not equal!", expectedData, actualData);
    }

    private String getResourceContent() throws IOException {
        String path = (new File(classLoader.getResource(expectedResourceDirectory).getFile())).getAbsolutePath();
        Path resoursePath = Paths.get(path).resolve(expectedResource);
        if (noisyOutput) {
            System.out.println("Resouce path: " + resoursePath);
        }

        String contents = new String(Files.readAllBytes(resoursePath), StandardCharsets.UTF_8);

        // Remove line feed if source file have it
        return contents.replaceAll(String.valueOf((char) 13), "");
    }

}
