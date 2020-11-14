package ru.ts.missioninfograbber.logic;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import ru.ts.missioninfograbber.entity.MissionData;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class MFRReadTest {
    private final MissionFileReader MFR = new MissionFileReader();
    private final String resourseName;
    private final MissionData expectedMissionData;
    private final boolean noisyOutput = false;

    @Parameterized.Parameters
    public static Collection missionFilenames() {

        /*
          return new MissionData(
                    title
                    , details.get(Details.OVERVIEW)
                    , filename
                    , missionFileData[2]
                    , slotCount
            );
         */
        Object[][] params = new Object[][]{
            {
                /* mission.sqm contains all data */
                "CO16-Wastelands-1B.ProvingGrounds_PMC"
                , new MissionData(
                    "CO16 Wastelands (1B)"
                    ,"Takistan Army patrols wastelands"
                    ,"CO16-Wastelands-1B.ProvingGrounds_PMC"
                    ,"ProvingGrounds_PMC"
                    ,"16")
            }
            , {
                /* mission.sqm dosen't contain any data */
                "CO18_MGSV_42.ProvingGrounds_PMC"
                , new MissionData(
                    "CO18_MGSV_42"
                    ,""
                    ,"CO18_MGSV_42.ProvingGrounds_PMC"
                    ,"ProvingGrounds_PMC"
                    ,"18")
            }
            , {
                /* mission.sqm contains some data */
                "co21-succesive-patrol-1b.Kunduz"
                , new MissionData(
                    "CO21 Successive Patrol (1B)"
                    ,""
                    ,"co21-succesive-patrol-1b.Kunduz"
                    ,"Kunduz"
                    ,"21")
            }
            , {
                /* mission.sqm contains some data */
                "co21-succesive-patrol-1c.Kunduz"
                , new MissionData(
                    "co21-succesive-patrol-1c"
                    ,"Patrol operation of US Army troops"
                    ,"co21-succesive-patrol-1c.Kunduz"
                    ,"Kunduz"
                    ,"26")
            }
        };

        return Arrays.asList(params);
    }

    public MFRReadTest(String resourseName, MissionData expectedMissionData) {
        this.resourseName = resourseName;
        this.expectedMissionData = expectedMissionData;
    }

    @Test
    public void readTest() throws IOException {
        ClassLoader classLoader = getClass().getClassLoader();
        String filepath = (new File(classLoader.getResource(resourseName).getFile())).getAbsolutePath();

        if (noisyOutput) { System.out.println("Testing mission: " + filepath); }
        MissionData missionData = MFR.read(filepath);

        if (noisyOutput) {
            System.out.println("Mission Data read!");
            System.out.println(missionData);
            System.out.println(expectedMissionData);
        }

        assertEquals("Title not equal", expectedMissionData.getTitle(), missionData.getTitle());
        assertEquals("Slots not equal", expectedMissionData.getSlots(), missionData.getSlots());
        assertEquals("Terrain not equal", expectedMissionData.getTerrain(), missionData.getTerrain());
        assertEquals("Filename not equal", expectedMissionData.getFilename(), missionData.getFilename());
        assertEquals("Overview not equal", expectedMissionData.getOverview(), missionData.getOverview());
        assertEquals("Mission Data is not equal", expectedMissionData, missionData);
    }
}
