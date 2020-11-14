package ru.ts.missioninfograbber.logic;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(Parameterized.class)
public class OPGTest {
    private final OverviewPictureGrabber OPG = new OverviewPictureGrabber();
    private final boolean noisyOutput = false;
    private final String resourseName;
    private final String expectedImageInfo;
    private String resourceFilepath;
    private Path tmpDir;

    @Parameterized.Parameters
    public static Collection missionFilenames() {
        Object[][] params = new Object[][]{
                {
                        "CO16-Wastelands-1B.ProvingGrounds_PMC"
                        ,"imgs\\CO16-Wastelands-1B.ProvingGrounds_PMC_overview.jpg"
                }
                , {
                        "co21-succesive-patrol-1b.Kunduz"
                        , "imgs\\co21-succesive-patrol-1b.Kunduz_overview.jpg"
                }
                , {
                        "co21-succesive-patrol-1c.Kunduz"
                        , "imgs\\co21-succesive-patrol-1c.Kunduz_overview.jpg"
                }
                , {
                        "co22_Operation_Pink_Citadel_1B.abramia"
                        , ""
                }
        };

        return Arrays.asList(params);
    }

    public OPGTest(String resourseName, String expectedImageInfo) {
        this.resourseName = resourseName;
        this.expectedImageInfo = expectedImageInfo;
    }

    @Before
    public void setUp() throws Exception {
        ClassLoader classLoader = getClass().getClassLoader();
        resourceFilepath = (new File(classLoader.getResource(resourseName).getFile())).getAbsolutePath();
        tmpDir = Files.createTempDirectory(Paths.get(resourceFilepath), "imgsTest");
    }

    @Test
    public void exportTest() throws IOException {
        if (noisyOutput) {
            System.out.println("Testing mission: " + resourceFilepath);
            System.out.println("Temp dir: " + tmpDir);
        }

        String imageInfo = OPG.getOverviewImage(resourceFilepath, tmpDir.toString());
        assertEquals("Overview image info is invalid!", expectedImageInfo, imageInfo);

        File copiedFile = new File(tmpDir.resolve(imageInfo).toString());
        if (noisyOutput) {
            System.out.println("Copied file path: " + tmpDir.resolve(imageInfo).toString());
        }
        assertTrue("Overview image file is not copied!", copiedFile.exists());
    }

    @After
    public void tearDown() throws Exception {
        Files.walk(tmpDir).sorted(Comparator.reverseOrder()).map(Path::toFile).forEach(File::delete);
    }
}
