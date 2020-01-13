package ru.ts.missioninfograbber.logic;

import org.junit.Test;
import ru.ts.missioninfograbber.entity.BriefingData;

import java.io.File;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class BFRTest {
    private final boolean noisyOutput = false;
    private final BriefingFileReader BFR = new BriefingFileReader();
    private final ClassLoader classLoader = getClass().getClassLoader();

    // Briefing
    private final String emptyBriefingDataExpected = "&lt;no data&gt;";
    private final String validBriefingPath = "co13-business-fall.Takistan";//"CO18_MGSV_42.ProvingGrounds_PMC";
    private final String emptyBriefingPath = "co21-succesive-patrol-1c.Kunduz";
    private final ArrayList<String> trashSubstrings = new ArrayList<>();
    {
        trashSubstrings.add("#define");
        trashSubstrings.add("BRIEFING");
        trashSubstrings.add("TOPIC(");
        trashSubstrings.add("END");
        trashSubstrings.add("ADD_TOPICS");
        trashSubstrings.add("\"\"");
    }

    // Tags
    private final String validTagsBriefingPath = "co22_Operation_Pink_Citadel_1B.abramia";
    private final String validTagsBriefingExpected = "[\"SPECOPS\",\"INFANTRY\"]";
    private final String emptyTagsBriefingPath = "co21-succesive-patrol-1c.Kunduz";
    private final String noTagsBriefingPath = "CO18_MGSV_42.ProvingGrounds_PMC";
    private final String emptyTagsBriefingExpected = "[]";

    @Test
    public void readNegativeTest() {
        BriefingData briefingData = BFR.read("$#%!");
        if (noisyOutput) { System.out.println("readNegativeTest :: " + briefingData.getText()); }

        assertEquals("Failed result doesn't match!", emptyBriefingDataExpected, briefingData.getText());
    }

    @Test
    public void readValidTest() {
        String filepath = getResourcePath(validBriefingPath);
        BriefingData briefingData = BFR.read(filepath);
        if (noisyOutput) {  System.out.println("readValidTest :: " + briefingData.getText()); }

        assertNotEquals(emptyBriefingDataExpected, briefingData.getText());
    }

    @Test
    public void readEmptyBriefingFile() {
        String filepath = getResourcePath(emptyBriefingPath);
        BriefingData briefingData = BFR.read(filepath);
        if (noisyOutput) {
            System.out.println("readEmptyBriefingFile :: File read!");
            System.out.println(briefingData.getText());
        }

        assertEquals("File is not empty!", emptyBriefingDataExpected, briefingData.getText());
    }

    @Test
    public void readOutputTest() {
        String filepath = getResourcePath(validBriefingPath);
        BriefingData briefingData = BFR.read(filepath);
        if (noisyOutput) {  System.out.println("readOutputTest :: File read!"); }

        String text = briefingData.getText();

        // Check for trash substrings
        trashSubstrings.forEach(s -> assertEquals("Briefing text contains [" + s + "]",-1, text.indexOf(s)));
    }

    @Test
    public void readNonEmptyTags() {
        String filepath = getResourcePath(validTagsBriefingPath);
        BriefingData briefingData = BFR.read(filepath);

        assertEquals("Tags are not the same!", validTagsBriefingExpected, briefingData.getTags() );
    }

    @Test
    public void readEmptyTags() {
        String filepath = getResourcePath(emptyTagsBriefingPath);
        BriefingData briefingData = BFR.read(filepath);

        assertEquals("Tags are not empty!", emptyTagsBriefingExpected, briefingData.getTags() );
    }

    @Test
    public void readAbsentTagsTest() {
        String filepath = getResourcePath(noTagsBriefingPath);
        BriefingData briefingData = BFR.read(filepath);

        assertEquals("Tags are not empty!", emptyTagsBriefingExpected, briefingData.getTags() );
    }

    @Test
    public void readTagsNegativeTest() {
        BriefingData briefingData = BFR.read("$#%!");
        if (noisyOutput) { System.out.println("readTagsNegativeTest :: " + briefingData.getTags()); }

        assertEquals("Tags are not empty!", emptyTagsBriefingExpected, briefingData.getTags());
    }

    private String getResourcePath(String resourceName) {
        return (new File(classLoader.getResource(resourceName).getFile())).getAbsolutePath();
    }
}
