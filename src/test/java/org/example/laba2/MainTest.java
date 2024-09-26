package org.example.laba2;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class MainTest {

/*    @Test
    void main() {
    }

    @Test
    void getGeneralCountOfErrors() {
    }*/

    @Test
    void getSigmaResult() {
        int expected = 19;
        Main main = new Main("./src/test/java/org/example/utils/testData.json");
        ArrayList<Integer> iValues = new ArrayList<>();
        iValues.add(1);
        iValues.add(2);
        iValues.add(3);
        int actual = main.getSigmaResult(iValues);
        assertEquals(expected, actual);
    }
}