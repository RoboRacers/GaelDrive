package com.roboracers.gaeldrive.particles;
import androidx.annotation.NonNull;

import org.apache.commons.math3.linear.RealVector;

/**
 * Elementary class to represent a particle of an arbitrary number of dimensions.
 * Used to represent the probability distribution of a state space.
 */
public class Particle implements Cloneable {

    /**
     * Represents the state of the particle via a vector of arbitrary dimension.
     */
    RealVector state;
    /**
     * The weightage of this particle.
     */
    double weight;
    /**
     * This id of this particle for tracking purposes.
     */
    Integer id;

    public Particle(RealVector state, double weight, Integer id) {
        this.state = state;
        this.weight = weight;
        this.id = id;
    }

    /**
     * Null initializer
     */
    public Particle() {
    }

    /**
     * Set the state of the particle
     * @param newState
     */
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

    @Override
    public String toString() {
        return "Particle{" +
                "state=" + state +
                ", weight=" + weight +
                ", id=" + id +
                '}';
    }

    @NonNull
    public Object clone() {
        try {
            return super.clone();
        } catch (CloneNotSupportedException e) {
            throw new InternalError(e.toString());
        }
    }
}

