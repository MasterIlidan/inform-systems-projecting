package org.example.laba3;

import org.example.utils.JSONReader;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

class MainTest {
    JSONObject root;

    @BeforeEach
    public void setUp() throws IOException, ParseException {
        root = JSONReader.getDataFromJSONLaba3("./src/test/java/org/example/laba3/testData.json");
    }

    @Test
    void getPotentialProgramVolume() {
        double expected = 52798.77763927879;
        Main main = new Main("./src/test/java/org/example/laba3/testData.json");
        double actual = main.getPotentialProgramVolume(6060);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void getInternalAndExternalParams() {
        int expected = 6060;
        Main main = new Main("./src/test/java/org/example/laba3/testData.json");
        int actual = main.getInternalAndExternalParams();
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void getPotentialErrorCount() {
        double expected = 607344.4270592607;
        Main main = new Main("./src/test/java/org/example/laba3/testData.json");
        int operands = main.getInternalAndExternalParams();
        double volume = main.getPotentialProgramVolume(operands);
        double actual = main.getPotentialErrorCount(volume);
        Assertions.assertEquals(expected, actual);
    }
}