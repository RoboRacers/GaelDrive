package com.roboracers.gaeldrive.filters;

import com.roboracers.gaeldrive.particles.Particle;

import org.apache.commons.math3.linear.ArrayRealVector;
import org.apache.commons.math3.linear.RealVector;

import java.util.concurrent.ThreadLocalRandom;

/**
 * Specialized Particle Filter class for 2d robot localization.
 * Note that this is technically a 3d (x,y,Heading) particle filter.
 */
public class ParticleFilter2d extends ParticleFilter {

    public Bound bound;
    public double[] resampleDeviances = {0.1, 0.1, 0.01};

    /**
     * Quick initialization of a Particle Filter with default covariances
     */
    public ParticleFilter2d() {
        super.Dimensions = 3;
    }

    /**
     * Thorough initialization of a Particle Filter with custom covariances
     * @param bound bounds for particle initialization
     */
    public ParticleFilter2d(Bound bound) {
        super.Dimensions = 3;
        this.bound = bound;
    }

    /**
     * Initialization with initilization bound and resampling deviances.
     * @param bound
     * @param resampleDeviances
     */
    public ParticleFilter2d(Bound bound, double[] resampleDeviances) {
        super.Dimensions = 3;
        this.bound = bound;
        this.resampleDeviances = resampleDeviances;
    }

    public static class Bound {
        private double xMin;
        private double xMax;
        private double yMin;
        private double yMax;
        private double headingMin;
        private double headingMax;

        public Bound(double xMin, double xMax, double yMin, double yMax, double headingMin, double headingMax) {
            this.xMin = xMin;
            this.xMax = xMax;
            this.yMin = yMin;
            this.yMax = yMax;
            this.headingMin = headingMin;
            this.headingMax = headingMax;
        }
    }

    /**
     * Initialize the Particle set
     * @param numParticles Number of particles
     * @param startingLocation origin of the particles
     * @param bound the bounds of initialization
     */
    public void initializeParticles(int numParticles, RealVector startingLocation, Bound bound) {

        for(int i=0; i < numParticles; i++ ) {
            // Generate random deviances
            double xDeviation = ThreadLocalRandom.current().nextDouble(bound.xMin, bound.xMax);
            double yDeviation = ThreadLocalRandom.current().nextDouble(bound.yMin, bound.yMax);
            double headingDeviation = ThreadLocalRandom.current().nextDouble(bound.headingMin, bound.headingMax);


            ArrayRealVector addedPose = new ArrayRealVector(new double[]{
                    startingLocation.getEntry(0) + xDeviation,
                    startingLocation.getEntry(1) + yDeviation,
                    startingLocation.getEntry(2) + headingDeviation
            });

            // Add the given particle back into the particle set
            super.add(new Particle(addedPose, 0, i));
        }

    }

    /**
     * Initialize the particle set
     * @param numParticles The number of particles
     * @param startingLocation The origin of the particles
     */
    public void initializeParticles(int numParticles, RealVector startingLocation) {

        for(int i=0; i < numParticles; i++ ) {
            // Generate random deviances TODO: Make a more mathematical resampling system
            double xDeviation = ThreadLocalRandom.current().nextDouble(bound.xMin, bound.xMax);
            double yDeviation = ThreadLocalRandom.current().nextDouble(bound.yMin, bound.yMax);
            double headingDeviation = ThreadLocalRandom.current().nextDouble(bound.headingMin, bound.headingMax);

            // Create the new vector
            ArrayRealVector addedPose = new ArrayRealVector(new double[]{
                    startingLocation.getEntry(0) + xDeviation,
                    startingLocation.getEntry(1) + yDeviation,
                    startingLocation.getEntry(2) + headingDeviation
            });

            // Add the given particle back into the particle set
            super.add(new Particle(addedPose, 0, i));
        }

    }

    public void resampleParticles() throws Exception {
        super.resampleParticles(this.resampleDeviances);
    }


}
