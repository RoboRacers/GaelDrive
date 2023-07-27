package com.roboracers.gaeldrive.particles;


import org.apache.commons.math3.linear.RealVector;

/**
 * Elementary class to represent a particle of an arbitrary amount of dimensions.
 */
public class Particle {

    /**
     * Represents the state of the particle via a vector of arbitrary dimension.
     */
    RealVector state;
    /**
     * The weightage of this particle.
     */
    public double weight;
    /**
     * This id of this particle for tracking purposes.
     */
    Integer id;


    public void setState(RealVector newState) {
        state = newState;
    }

    /**
     * Return the state of the particle.
     * @return
     */
    public RealVector getState() {
        return this.state;
    }

    /**
     * Returns the weight of the particle.
     * @return the weight of the particle.
     */
    public double getWeight(){
        return this.weight;
    }

    /**
     * Sets the weight of this particle to the given value.
     * @param weight the intended weight of the particle.
     */
    public void setWeight(Double weight) {
        this.weight = weight;
    }

    /**
     * Gets the id of the particle.
     * @return The ID of the particle.
     */
    public Integer getId() { return this.id; }

    /**
     * Get the shape (dimensions) of the state of this particle.
     * @return Dimensions of this particle's state vector.
     */
    public int getDimensions() {
        return this.state.getDimension();
    }
}
