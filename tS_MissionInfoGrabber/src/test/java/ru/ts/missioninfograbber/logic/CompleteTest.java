package ru.ts.missioninfograbber.logic;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)

@Suite.SuiteClasses({
        MissionFileReaderTestSuite.class
        , BriefingFileReaderTestSuite.class
        , MissionInfoExportTestSuite.class
})

public class CompleteTest {
}
