package com.roboracers.gaeldrive.filters;


import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.roboracers.gaeldrive.particles.Particle;
import com.roboracers.gaeldrive.sensors.SensorModel;
import com.roboracers.gaeldrive.tests.TestConstants;
import com.roboracers.gaeldrive.utils.StatsUtils;

import org.apache.commons.math3.distribution.ChiSquaredDistribution;
import org.apache.commons.math3.linear.RealVector;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

/**
 * A filter that uses Monte Carlo methods to find approximate solutions
 * to filtering problems in a non-linear state space.
 */
public abstract class ParticleFilter {

    /**
     * Hashmap that stores all the particles in Integer/Particle pairs.
     */
    ArrayList<Particle> Particles = new ArrayList<>();

    private Random random = new Random();
    int Dimensions;

    ChiSquaredDistribution distribution2DOF = new ChiSquaredDistribution(2);
    ChiSquaredDistribution distribution3DOF = new ChiSquaredDistribution(3);

    /**
     * Add a particle to the internal Hashmap.
     * @param particle
     */
    public void add(Particle particle) {
        Particles.add(particle);
    }

    /**
     * Clear all particles.
     */
    public void clear() {
        Particles.clear();
    }

    /**
     * Return the Hashmap that stores the particles.
     * @return Hashmap of particles
     */
    public ArrayList<Particle> getParticles() {
        return this.Particles;
    }


    /**
     * Translate every particle
     * @param translationVector The translation vector that the other particles will be translated by.
     */
    public void translateParticles (RealVector translationVector) {

        int index = 0;
        // For every particle in our set of Particles
        for (Particle particle: Particles) {
            // Add our translational vector
            particle.setState(particle.getState().add(translationVector));
            // Set the value as our updated particle
            Particles.set(index, particle);
            index ++;
        }
    }

    /**
     * Weigh all the particles in our state space given a set of sensor models.
     * We compared the delta between the actual sensor reading and the
     * sensor value simulated for each one of our sensors.
     * @param models List of models to be used.
     */
    public void weighParticles(List<SensorModel> models) {

        // For every particle in our state space
        int index = 0;
        for (Particle particle: Particles) {

            double cumulativeWeight = 0;
            double cumulativeWeightModifier = 0;

            // For every sensor model that we are considering
            for (SensorModel model: models) {

                // Get both the actual and simulated reading
                RealVector simulatedSensorValue = model.getSimulatedReading(particle.getState());
                RealVector actualSensorValue = model.getActualReading();

                double probability = StatsUtils.readingDeltaProbability(actualSensorValue, simulatedSensorValue, model.getDOF());

                // Add the probability multiplied by the weight of the model.
                cumulativeWeight += probability * model.getWeightModifier();
                // Add the weight of this sensor model to the overall weight modifier
                cumulativeWeightModifier += model.getWeightModifier();

            }

            // Calculate the average weights of all the sensors and assign it to the particle
            particle.setWeight(cumulativeWeight/cumulativeWeightModifier);

            // Add the particle with the updated weight back into our particle set.
            Particles.set(index, particle);
            index ++;
        }
    }

    /**
     *
     * @param models List of models to be used.
     */
    public void weighParticlesBayesian(List<SensorModel> models) {
        //TODO: Implement Fully Bayesian Weighting

        // For every particle in our state space
        int index = 0;
        for (Particle particle: Particles) {

            double cumalativeWeight = 0;
            double cumalativeWeightModifer = 0;

            // For every sensor model that we are considering
            for (SensorModel model: models) {

                // Get both the actual and simulated reading
                RealVector simulatedSensorValue = model.getSimulatedReading(particle.getState());
                RealVector actualSensorValue = model.getActualReading();

                double probability = StatsUtils.readingDeltaProbability(actualSensorValue, simulatedSensorValue, model.getDOF());

                // Add the probability multiplied by the weight of the model.
                cumalativeWeight += probability * model.getWeightModifier();
                // Add the weight of this sensor model to the overall weight modifier
                cumalativeWeightModifer += model.getWeightModifier();

            }

            // Calculate the average weights of all the sensors and assign it to the particle
            particle.setWeight((cumalativeWeight/cumalativeWeightModifer)*particle.getWeight());

            // Add the particle with the updated weight back into our particle set.
            Particles.set(index, particle);
            index ++;
        }
    }

    /**
     * Systematic resampling for the particle filter.
     */
    public void resampleParticles() {
        int numParticles = Particles.size();
        ArrayList<Particle> newParticles = new ArrayList<>(numParticles);

        double totalWeight = 0.0;
        for (Particle particle : Particles) {
            totalWeight += particle.getWeight(); // Replace with your weight retrieval logic
        }

        double stepSize = totalWeight / numParticles;
        double position = random.nextDouble() * stepSize;

        int index = 0;
        double cumulativeWeight = Particles.get(0).getWeight();
        for (int i = 0; i < numParticles; i++) {
            while (position > cumulativeWeight && index < numParticles - 1) {
                index++;
                cumulativeWeight += Particles.get(index).getWeight();
            }

            Particle particle = Particles.get(index);
            particle.setWeight(1.0);
            if (TestConstants.ADD_NOISE) {
                newParticles.add(sampleFromParticle(particle));
            } else {
                newParticles.add(particle);
            }

            position += stepSize;
        }

        Particles = newParticles;
    }


    /**
     * Gets the particle with the highest weight.
     * @return Particle of the highest weighted particle.
     */
    public Particle getBestParticle () {

        double highestWeight = 0;
        Particle bestParticle = new Particle();

        // Loop through all weights and get highest weight
        for (Particle particle : Particles) {
            double particleWeight = particle.getWeight();
            if (particleWeight > highestWeight) {
                bestParticle = particle;
                highestWeight = particleWeight;
            }
        }
        return bestParticle;
    }

    /**
     * Get a random particle from the particle set. Used for debugging.
     * @return
     */
    public Particle getRandomParticle() {
        int range = Particles.size();
        return Particles.get(ThreadLocalRandom.current().nextInt(0, range));
    }


    public abstract void initializeParticles(int numParticles, Pose2d startingLocation, double xMin, double xMax, double yMin, double yMax, double headingMin, double headingMax);

    public abstract void initializeParticles(int numParticles, Pose2d startingLocation);

    /**
     * Resampling from a single particle. Take the original particle and add gaussian noise to it.
     * @param initialParticle The starting particle
     * @return The resampled particle
     */
    protected abstract Particle sampleFromParticle(Particle initialParticle);
}
