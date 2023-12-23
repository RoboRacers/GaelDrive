package com.roboracers.gaeldrive.utils;


import org.apache.commons.math3.distribution.ChiSquaredDistribution;
import org.apache.commons.math3.linear.ArrayRealVector;
import org.apache.commons.math3.linear.RealVector;

import java.util.Random;

public class StatsUtils {

    static Random random = new Random();

    static private double STD_DEVIATION = 1;
    static private double MEAN = 0;

    static ChiSquaredDistribution distribution2DOF = new ChiSquaredDistribution(2);
    static ChiSquaredDistribution distribution3DOF = new ChiSquaredDistribution(3);

    /**
     * Adds Gaussian noise to a vector of arbitrary length.
     * @param state
     * @param deviances
     * @return Vector with noise
     * @throws MismatchedLengthException
     */
    public static RealVector addGaussianNoise(RealVector state, double[] deviances) throws MismatchedLengthException {

        int len = state.getDimension();

        if (deviances.length != len) {
            throw new MismatchedLengthException("Mismatched Length for resampling deviances");
        }

        for (int i = 0; i < len; i++) {
            state.setEntry(i, generateGaussian(deviances[i], state.getEntry(i)));
        }

        return new ArrayRealVector(state);

    }

    public static double generateGaussian() {
        return random.nextGaussian() * STD_DEVIATION + MEAN;
    }

    public static double generateGaussian(double STD_DEVIATION, double MEAN) {
        return random.nextGaussian() * STD_DEVIATION + MEAN;
    }

    public static double generateGaussian(double STD_DEVIATION) {
        return random.nextGaussian() * STD_DEVIATION + MEAN;
    }

    /* Set defaults */

    public static void setStdDeviation(double newSTD_DEVIATION) {
        STD_DEVIATION = newSTD_DEVIATION;
    }

    public static void setMEAN(double newMEAN) {
        MEAN = newMEAN;
    }

    public static double readingDeltaProbability(RealVector v1, RealVector v2, int DOF) {

        RealVector readingDelta = v1.subtract(v2);

        if (DOF == 2){
            return distribution2DOF.density(readingDelta.getNorm());
        } else if ( DOF == 3 ) {
            return distribution3DOF.density(readingDelta.getNorm());
        } else {
            return distribution2DOF.density(readingDelta.getNorm());
        }

    }
}
