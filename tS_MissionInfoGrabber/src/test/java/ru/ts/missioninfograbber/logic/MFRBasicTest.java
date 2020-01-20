package ru.ts.missioninfograbber.logic;

import org.junit.Test;
import ru.ts.missioninfograbber.entity.MissionData;

import java.io.IOException;

import static org.junit.Assert.*;

public class MFRBasicTest {
    private final MissionFileReader MFR = new MissionFileReader();

    @Test
    public void makeLeadingCharUppercaseTest() {
        assertEquals("Name", MFR.makeLeadingCharUppercase("name"));
        assertEquals("Name", MFR.makeLeadingCharUppercase("Name"));
        assertEquals("1name", MFR.makeLeadingCharUppercase("1name"));
        assertEquals("Имя миссии", MFR.makeLeadingCharUppercase("имя миссии"));
    }

    @Test(expected = IOException.class)
    public void readWrongFile() throws IOException {
        MissionData read = MFR.read("%!$@$%");
    }
}