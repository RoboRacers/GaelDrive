package com.roboracers.gaeldrive.filters;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.roboracers.gaeldrive.utils.VectorUtils;

import org.apache.commons.math3.linear.RealVector;
import org.junit.jupiter.api.Test;

class ParticleFilterTest {

    @Test
    void initializeParticlesThrowsWhenDimensionsUnset() {
        ParticleFilter filter = new ParticleFilter();
        RealVector origin = VectorUtils.create3DVector(0, 0, 0);

        assertThrows(IllegalStateException.class,
                () -> filter.initializeParticles(10, origin, new double[]{-1, 1, -1, 1, -1, 1}));
    }

    @Test
    void constructorRejectsNonPositiveDimensions() {
        assertThrows(IllegalArgumentException.class, () -> new ParticleFilter(0));
        assertThrows(IllegalArgumentException.class, () -> new ParticleFilter(-1));
    }

    @Test
    void initializeParticlesRejectsMismatchedConstraintsLength() {
        ParticleFilter filter = new ParticleFilter(3);
        RealVector origin = VectorUtils.create3DVector(0, 0, 0);

        assertThrows(IllegalArgumentException.class,
                () -> filter.initializeParticles(10, origin, new double[]{-1, 1}));
    }

    @Test
    void initializeParticlesPopulatesRequestedCountWithCorrectDimensions() {
        ParticleFilter filter = new ParticleFilter(3);
        RealVector origin = VectorUtils.create3DVector(0, 0, 0);

        filter.initializeParticles(50, origin, new double[]{-1, 1, -1, 1, -0.1, 0.1});

        assertEquals(50, filter.getParticles().size());
        assertEquals(3, filter.getParticle(0).getDimensions());
    }
}
