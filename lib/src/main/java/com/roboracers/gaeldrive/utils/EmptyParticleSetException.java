package com.roboracers.gaeldrive.utils;

/**
 * Thrown when an operation that requires at least one particle
 * (e.g. resampling, or fetching the best/random particle) is
 * attempted on an empty particle set.
 */
public class EmptyParticleSetException extends Exception {
    public EmptyParticleSetException(String errorMessage) {
        super(errorMessage);
    }
}
