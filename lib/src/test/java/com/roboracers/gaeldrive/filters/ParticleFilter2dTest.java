package com.roboracers.gaeldrive.filters;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.roboracers.gaeldrive.particles.Particle;
import com.roboracers.gaeldrive.utils.VectorUtils;

import org.apache.commons.math3.linear.RealVector;
import org.junit.jupiter.api.Test;

class ParticleFilter2dTest {

    @Test
    void initializeParticlesWithDefaultBoundSeedsUniformWeights() {
        ParticleFilter2d filter = new ParticleFilter2d();
        RealVector origin = VectorUtils.create3DVector(0, 0, 0);

        filter.initializeParticles(25, origin);

        assertEquals(25, filter.getParticles().size());
        for (Particle particle : filter.getParticles()) {
            assertEquals(1.0, particle.getWeight());
            assertEquals(3, particle.getDimensions());
        }
    }

    @Test
    void initializeParticlesWithCustomBoundSeedsUniformWeights() {
        ParticleFilter2d filter = new ParticleFilter2d();
        RealVector origin = VectorUtils.create3DVector(0, 0, 0);
        ParticleFilter2d.Bound bound = new ParticleFilter2d.Bound(-1, 1, -1, 1, -0.1, 0.1);

        filter.initializeParticles(25, origin, bound);

        for (Particle particle : filter.getParticles()) {
            assertEquals(1.0, particle.getWeight());
        }
    }

    @Test
    void initializeParticlesRespectsBounds() {
        ParticleFilter2d filter = new ParticleFilter2d();
        RealVector origin = VectorUtils.create3DVector(10, 10, 0);
        ParticleFilter2d.Bound bound = new ParticleFilter2d.Bound(-0.5, 0.5, -0.5, 0.5, -0.05, 0.05);

        filter.initializeParticles(100, origin, bound);

        for (Particle particle : filter.getParticles()) {
            RealVector state = particle.getState();
            assertTrue(state.getEntry(0) >= 9.5 && state.getEntry(0) <= 10.5);
            assertTrue(state.getEntry(1) >= 9.5 && state.getEntry(1) <= 10.5);
            assertTrue(state.getEntry(2) >= -0.05 && state.getEntry(2) <= 0.05);
        }
    }

    @Test
    void resampleParticlesConvenienceOverloadPreservesParticleCount() throws Exception {
        ParticleFilter2d filter = new ParticleFilter2d();
        RealVector origin = VectorUtils.create3DVector(0, 0, 0);
        filter.initializeParticles(30, origin);

        filter.resampleParticles();

        assertEquals(30, filter.getParticles().size());
    }
}
