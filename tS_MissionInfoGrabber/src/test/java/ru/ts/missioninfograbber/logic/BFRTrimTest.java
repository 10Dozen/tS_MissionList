package ru.ts.missioninfograbber.logic;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class BFRTrimTest {
    private final BriefingFileReader BFR = new BriefingFileReader();
    private final String line;
    private final String expectedTrimmedLine;

    @Parameterized.Parameters
    public static Collection linesToTrim() {
        return Arrays.asList(new Object[][] {
                {"\"От 2 отделений до взвода инсургентов.\"","От 2 отделений до взвода инсургентов."}
                ,{"\"Согласно плану командира отделения.<br /><br />\"", "Согласно плану командира отделения.<br /><br />"}
                ,{"<br />1'1 - 9 чел.", "<br />1'1 - 9 чел."}
                ,{"<br />1'6 - 4 чел.\"", "<br />1'6 - 4 чел."}
                ,{"\"Элитные силы Outer Heaven,", "Элитные силы Outer Heaven,"}
                ,{"\"Освободить захваченных горных инженеров и сопроводить их к \"\"Insertion Point\"\"\"","Освободить захваченных горных инженеров и сопроводить их к 'Insertion Point'"}
                ,{"<br />Second line with \" + _sqfVar + \" inside","<br />Second line with \\\" + _sqfVar + \\\" inside"}
                ,{"\"<br />Quoted line with \" + _sqfVar + \" inside\"","<br />Quoted line with \\\" + _sqfVar + \\\" inside"}
        });
    }

    public BFRTrimTest(String line, String expectedTrimmedLine) {
        this.line = line;
        this.expectedTrimmedLine = expectedTrimmedLine;
    }

    @Test
    public void trimLineTest() {
        String trimmedLine = BFR.escapeQuotesFromLine(line);
        assertEquals(expectedTrimmedLine, trimmedLine);
    }
}
