package ru.ts.missioninfograbber.logic;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class MIENodeFormatterTest {
    private final boolean noisyOutput = false;
    private final MissionInfoExporter MIE = new MissionInfoExporter();
    private final String expectedNode;
    private final String key;
    private final String value;
    private final boolean quoted;
    private final boolean isLast;

    @Parameterized.Parameters
    public static Collection formatData() {
        return Arrays.asList(new Object[][] {
                {"\n\tSTR_NODE_KEY: \"My string node value\",", "STR_NODE_KEY", "My string node value", true, false}
                , {"\n\tSTR_NODE_KEY: \"My string node value\"", "STR_NODE_KEY", "My string node value", true, true}
                , {"\n\tARRAY_NODE_KEY: [\"Val1\",\"Val2\"]", "ARRAY_NODE_KEY", "[\"Val1\",\"Val2\"]", false, true}
                , {"\n\tNUMBER_NODE_KEY: 26", "NUMBER_NODE_KEY", "26", false, true}
        });
    }

    public MIENodeFormatterTest(String expectedNode, String key, String value, boolean quoted, boolean isLast) {
        this.expectedNode = expectedNode;
        this.key = key;
        this.value = value;
        this.quoted = quoted;
        this.isLast = isLast;
    }

    @Test
    public void formatNodeText() {
        String node = MIE.formatNode(key, value, quoted, isLast);
        if (noisyOutput) {
            System.out.println(expectedNode);
            System.out.println(node);
        }

        assertEquals("Node text not matches!", expectedNode, node);
    }
}
