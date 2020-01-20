package ru.ts.missioninfograbber.logic;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)

@Suite.SuiteClasses({
        MFRParserTest.class
        , MFRBasicTest.class
        , MFRReadTest.class
})

public class MissionFileReaderTestSuite {
}
