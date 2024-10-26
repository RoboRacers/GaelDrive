package com.roboracers.gaeldrive.utils;

/**
 * Thrown when particle or sensor model weights are unusable for the
 * requested operation - for example all sensor weight modifiers being
 * zero, or a particle set's total weight being zero or negative when
 * resampling.
 */
public class InvalidWeightException extends Exception {
    public InvalidWeightException(String errorMessage) {
        super(errorMessage);
    }
}
