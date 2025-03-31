package com.roboracers.gaeldrive.utils;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class DevianceTest {

    @Test
    void threeArgConstructorPacksValuesInOrder() {
        Deviance deviance = new Deviance(0.1, 0.2, 0.3);

        assertEquals(3, deviance.length);
        assertArrayEquals(new double[]{0.1, 0.2, 0.3}, deviance.values);
    }

    @Test
    void arrayConstructorTracksLengthOfArbitraryArrays() {
        double[] values = {1.0, 2.0, 3.0, 4.0, 5.0};

        Deviance deviance = new Deviance(values);

        assertEquals(values.length, deviance.length);
        assertArrayEquals(values, deviance.values);
    }
}
