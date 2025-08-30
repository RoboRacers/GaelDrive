package com.roboracers.gaeldrive.sensors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.roboracers.gaeldrive.utils.MismatchedLengthException;

import org.apache.commons.math3.linear.ArrayRealVector;
import org.apache.commons.math3.linear.RealVector;
import org.junit.jupiter.api.Test;

class PositionalSensorModelTest {

    private static PositionalSensorModel newModel() {
        return new PositionalSensorModel() {
            @Override
            public void update() {
                // no-op for tests
            }
        };
    }

    @Test
    void getSimulatedReadingDoesNotThrowWhenPositionIsUnset() throws Exception {
        PositionalSensorModel model = newModel();
        RealVector state = new ArrayRealVector(new double[]{1, 2, 3});

        assertEquals(state, model.getSimulatedReading(state));
    }

    @Test
    void getSimulatedReadingThrowsOnDimensionMismatch() {
        PositionalSensorModel model = newModel();
        model.position = new ArrayRealVector(new double[]{1, 2, 3});
        RealVector mismatchedState = new ArrayRealVector(new double[]{1, 2});

        assertThrows(MismatchedLengthException.class, () -> model.getSimulatedReading(mismatchedState));
    }

    @Test
    void getSimulatedReadingSucceedsOnMatchingDimensions() throws Exception {
        PositionalSensorModel model = newModel();
        model.position = new ArrayRealVector(new double[]{1, 2, 3});
        RealVector state = new ArrayRealVector(new double[]{4, 5, 6});

        assertEquals(state, model.getSimulatedReading(state));
    }
}
