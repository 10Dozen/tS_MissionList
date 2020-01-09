package ru.ts.missioninfograbber.logic;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(Parameterized.class)
public class MissionFileReaderParserTest {
    private final MissionFileReader MFR = new MissionFileReader();
    private final String filename;
    private final String[] expectedResult;

    @Parameterized.Parameters
    public static Collection missionFilenames() {
        return Arrays.asList(new Object[][] {
                {"CO16_Night_Draws_Near_1A.DYA"
                        , new String[] {"CO16_Night_Draws_Near_1A", "16", "DYA"}}
                ,{"CO18_Panjshir_Waltz_1B.lythium"
                    , new String[] {"CO18_Panjshir_Waltz_1B", "18", "Lythium"}}
                ,{"co19-king-of-the-hill.Chernarus"
                    , new String[] {"co19-king-of-the-hill", "19", "Chernarus"}}
                ,{"co23_operation_WolfsWailing_1B.ProvingGrounds_PMC"
                    , new String[] {"co23_operation_WolfsWailing_1B", "23", "ProvingGrounds_PMC"}}
                ,{"CO_32_Hot_Potato_1c.Altis"
                    , new String[] {"CO_32_Hot_Potato_1c", "32", "Altis"}}
                ,{"BA18-Malden-Missile-Crisis-1C.Malden"
                    , new String[] {"BA18-Malden-Missile-Crisis-1C", "", "Malden"}}
                ,{"CO_Tiger_Siege_1c.dingor"
                    , new String[] {"CO_Tiger_Siege_1c", "", "Dingor"}}
                ,{"UN_injustice_1b.Chernarus_Summer"
                    , new String[] {"UN_injustice_1b", "", "Chernarus_Summer"}}
        });
    }

    public MissionFileReaderParserTest(String inputName, String[] expectedResult) {
        this.filename = inputName;
        this.expectedResult = expectedResult;
    }

    @Test
    public void parseMissionFilenameTest() {
        String[] results = MFR.parseMissionFilename(filename);

        assertTrue(results.length == 3);
        assertEquals("Full name is not equal!", expectedResult[0], results[0]);
        assertEquals("Slot count is invalid!", expectedResult[1], results[1]);
        assertEquals("Terrain name is invalid!", expectedResult[2], results[2]);
    }
}
