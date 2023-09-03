package com.roboracers.gaeldrive.filters;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.roboracers.gaeldrive.particles.Particle;
import com.roboracers.gaeldrive.particles.Particle2d;
import com.roboracers.gaeldrive.utils.PoseUtils;
import com.roboracers.gaeldrive.utils.StatsUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Specialized Particle Filter class for 2d robot localization.
 * Note that this is technically a 3d (x,y,Heading) particle filter.
 */
public class ParticleFilter2d extends ParticleFilter {

    private double xMin = 0.01;
    private double xMax = 0.01;
    private double yMin = 0.01;
    private double yMax = 0.01;
    private double headingMin = 0.001;
    private double headingMax = 0.001;

    /**
     * Quick initialization of a Particle Filter with default covariances
     */
    public ParticleFilter2d() {
        super.Dimensions = 2;
    }

    /**
     * Thorough initialization of a Particle Filter with custom covariances
     * @param xMin Minimum x deviation
     * @param xMax Maximum x deviation
     * @param yMin Minimum y deviation
     * @param yMax Maximum y deviation
     * @param headingMin Minimum heading deviation
     * @param headingMax Maximum heading deviation
     */
    public ParticleFilter2d(double xMin, double xMax, double yMin, double yMax, double headingMin, double headingMax) {
        this.xMin = xMin;
        this.xMax = xMax;
        this.yMin = yMin;
        this.yMax = yMax;
        this.headingMin = headingMin;
        this.headingMax = headingMax;
    }

    /**
     * Initialize the Particle set
     * @param numParticles Number of particles
     * @param startingLocation origin of the particles
     * @param xMin Minimum x deviation
     * @param xMax Maximum x deviation
     * @param yMin Minimum y deviation
     * @param yMax Maximum y deviation
     * @param headingMin Minimum heading deviation
     * @param headingMax Maximum heading deviation
     */
    @Override
    public void initializeParticles(int numParticles, Pose2d startingLocation, double xMin, double xMax, double yMin, double yMax, double headingMin, double headingMax) {

        for(int i=0; i < numParticles; i++ ) {
            // Generate random deviances TODO: Make a more mathematical resampling system
            double xDeviation = ThreadLocalRandom.current().nextDouble(xMin, xMax);
            double yDeviation = ThreadLocalRandom.current().nextDouble(yMin, yMax);
            double headingDeviation = ThreadLocalRandom.current().nextDouble(headingMin, headingMax);


            // Create the new pose
            Pose2d addedPose = new Pose2d(  startingLocation.getX() + xDeviation,
                                            startingLocation.getY() + yDeviation,
                                            startingLocation.getHeading() + headingDeviation);

            // Add the given particle back into the particle set
            add(new Particle2d(addedPose, 0, i));
        }

    }

    /**
     * Initialize the particle set
     * @param numParticles The number of particles
     * @param startingLocation The origin of the particles
     */
    @Override
    public void initializeParticles(int numParticles, Pose2d startingLocation) {

        for(int i=0; i < numParticles; i++ ) {
            // Generate random deviances TODO: Make a more mathematical resampling system
            double xDeviation = ThreadLocalRandom.current().nextDouble(xMin, xMax);
            double yDeviation = ThreadLocalRandom.current().nextDouble(yMin, yMax);
            double headingDeviation = ThreadLocalRandom.current().nextDouble(headingMin, headingMax);

            // Create the new pose
            Pose2d addedPose = new Pose2d(  startingLocation.getX() + xDeviation,
                    startingLocation.getY() + yDeviation,
                    startingLocation.getHeading() + headingDeviation);

            // Add the given particle back into the particle set
            add(new Particle2d(addedPose, 0, i));
        }

    }

    /**
     * Set particle initialization covariances
     * @param xMin Minimum x deviation
     * @param xMax Maximum x deviation
     * @param yMin Minimum y deviation
     * @param yMax Maximum y deviation
     * @param headingMin Minimum heading deviation
     * @param headingMax Maximum heading deviation
     */
    public void setCovariances (double xMin, double xMax, double yMin, double yMax, double headingMin, double headingMax) {
        this.xMin = xMin;
        this.xMax = xMax;
        this.yMin = yMin;
        this.yMax = yMax;
        this.headingMin = headingMin;
        this.headingMax = headingMax;
    }

    /**
     * Gets the best pose of the best particle in our particle set
     * @return Best pose
     */
    public Pose2d getBestPose () {
        return PoseUtils.vectorToPose(getBestParticle().getState());
    }

    public List<Pose2d> getParticlePoses (){
        List<Pose2d> poses = new ArrayList<>();
        ArrayList<Particle> particles = getParticles();

        for (Particle particle : particles) {
            poses.add(PoseUtils.vectorToPose(particle.getState()));
        }

        return poses;
    }

    @Override
    protected Particle sampleFromParticle(Particle initialParticle) {
        return new Particle(
                StatsUtils.addGaussianNoise2D(initialParticle.getState(), 0.5, 0.005),
                1,
                1
        );
    }

}
