package com.roboracers.gaeldrive.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.apache.commons.math3.linear.RealVector;
import org.junit.jupiter.api.Test;

class VectorUtilsTest {

    @Test
    void crossProduct2dOfPerpendicularUnitVectorsIsOne() {
        RealVector x = VectorUtils.create3DVector(1, 0, 0).getSubVector(0, 2);
        RealVector y = VectorUtils.create3DVector(0, 1, 0).getSubVector(0, 2);

        assertEquals(1.0, VectorUtils.crossProduct2d(x, y), 1e-9);
    }

    @Test
    void crossProduct2dIsAnticommutative() {
        RealVector a = VectorUtils.create3DVector(3, 4, 0).getSubVector(0, 2);
        RealVector b = VectorUtils.create3DVector(-1, 2, 0).getSubVector(0, 2);

        assertEquals(-VectorUtils.crossProduct2d(a, b), VectorUtils.crossProduct2d(b, a), 1e-9);
    }

    @Test
    void crossProduct2dOfParallelVectorsIsZero() {
        RealVector a = VectorUtils.create3DVector(2, 4, 0).getSubVector(0, 2);
        RealVector b = VectorUtils.create3DVector(1, 2, 0).getSubVector(0, 2);

        assertEquals(0.0, VectorUtils.crossProduct2d(a, b), 1e-9);
    }

    @Test
    void create3DVectorPreservesComponentsInOrder() {
        RealVector vector = VectorUtils.create3DVector(1.5, -2.5, 3.5);

        assertEquals(3, vector.getDimension());
        assertEquals(1.5, vector.getEntry(0));
        assertEquals(-2.5, vector.getEntry(1));
        assertEquals(3.5, vector.getEntry(2));
    }
}
