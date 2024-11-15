package com.roboracers.gaeldrive.filters;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.roboracers.gaeldrive.sensors.SensorModel;
import com.roboracers.gaeldrive.utils.Deviance;
import com.roboracers.gaeldrive.utils.EmptyParticleSetException;
import com.roboracers.gaeldrive.utils.InvalidWeightException;
import com.roboracers.gaeldrive.utils.VectorUtils;

import org.apache.commons.math3.linear.RealVector;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

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

    @Test
    void getBestParticleThrowsOnEmptyParticleSet() {
        ParticleFilter filter = new ParticleFilter(3);

        assertThrows(EmptyParticleSetException.class, filter::getBestParticle);
    }

    @Test
    void getRandomParticleThrowsOnEmptyParticleSet() {
        ParticleFilter filter = new ParticleFilter(3);

        assertThrows(EmptyParticleSetException.class, filter::getRandomParticle);
    }

    @Test
    void resampleParticlesThrowsOnEmptyParticleSet() {
        ParticleFilter filter = new ParticleFilter(3);

        assertThrows(EmptyParticleSetException.class,
                () -> filter.resampleParticles(new Deviance(0.1, 0.1, 0.1)));
    }

    @Test
    void resampleParticlesThrowsWhenTotalWeightIsZero() {
        ParticleFilter filter = new ParticleFilter(3);
        RealVector origin = VectorUtils.create3DVector(0, 0, 0);
        filter.initializeParticles(10, origin, new double[]{-1, 1, -1, 1, -1, 1});
        for (int i = 0; i < 10; i++) {
            filter.getParticle(i).setWeight(0.0);
        }

        assertThrows(InvalidWeightException.class,
                () -> filter.resampleParticles(new Deviance(0.1, 0.1, 0.1)));
    }

    @Test
    void weighParticlesThrowsWhenAllSensorWeightModifiersAreZero() {
        ParticleFilter filter = new ParticleFilter(3);
        RealVector origin = VectorUtils.create3DVector(0, 0, 0);
        filter.initializeParticles(5, origin, new double[]{-1, 1, -1, 1, -1, 1});

        SensorModel zeroWeightSensor = new SensorModel() {
            @Override
            public double getWeightModifier() {
                return 0;
            }

            @Override
            public RealVector getActualReading() {
                return origin;
            }

            @Override
            public RealVector getSimulatedReading(RealVector state) {
                return state;
            }

            @Override
            public int getDOF() {
                return 3;
            }

            @Override
            public void update() {
            }
        };

        List<SensorModel> sensors = Collections.singletonList(zeroWeightSensor);

        assertThrows(InvalidWeightException.class, () -> filter.weighParticles(sensors));
    }
}
