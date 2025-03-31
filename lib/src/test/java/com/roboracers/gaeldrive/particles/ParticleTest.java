package com.roboracers.gaeldrive.particles;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertSame;

import com.roboracers.gaeldrive.utils.VectorUtils;

import org.apache.commons.math3.linear.RealVector;
import org.junit.jupiter.api.Test;

class ParticleTest {

    @Test
    void constructorSetsStateWeightAndId() {
        RealVector state = VectorUtils.create3DVector(1, 2, 3);

        Particle particle = new Particle(state, 0.5, 7);

        assertSame(state, particle.getState());
        assertEquals(0.5, particle.getWeight());
        assertEquals(7, particle.getId());
        assertEquals(3, particle.getDimensions());
    }

    @Test
    void settersUpdateStateAndWeight() {
        Particle particle = new Particle(VectorUtils.create3DVector(0, 0, 0), 1, 0);
        RealVector newState = VectorUtils.create3DVector(9, 9, 9);

        particle.setState(newState);
        particle.setWeight(0.75);

        assertSame(newState, particle.getState());
        assertEquals(0.75, particle.getWeight());
    }

    @Test
    void toStringIncludesStateWeightAndId() {
        Particle particle = new Particle(VectorUtils.create3DVector(1, 2, 3), 0.5, 42);

        String description = particle.toString();

        assertNotNull(description);
        assertEquals(true, description.contains("42"));
        assertEquals(true, description.contains("0.5"));
    }

    @Test
    void cloneProducesADistinctShallowCopy() {
        Particle particle = new Particle(VectorUtils.create3DVector(1, 2, 3), 0.5, 1);

        Particle clone = (Particle) particle.clone();

        assertNotSame(particle, clone);
        assertEquals(particle.getWeight(), clone.getWeight());
        assertEquals(particle.getId(), clone.getId());
        // Cloning is shallow: the state vector reference is shared.
        assertSame(particle.getState(), clone.getState());
    }
}
