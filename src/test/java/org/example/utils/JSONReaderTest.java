package org.example.utils;

import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;

class JSONReaderTest {

    @Test
    void getDataFromJSON() throws IOException, ParseException {
        HashMap<Integer, Integer> expected = new HashMap<>();
        expected.put(1, 1);
        expected.put(2, 5);
        expected.put(3, 13);

        HashMap<Integer, Integer> readData = JSONReader.getDataFromJSON("./src/test/java/org/example/utils/testData.json");

        for (Integer key : readData.keySet()) {
            System.out.println(key + " : " + readData.get(key));
        }
        assertEquals(expected, readData);

    }
}