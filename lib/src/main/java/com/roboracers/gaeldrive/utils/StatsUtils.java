package com.roboracers.gaeldrive.utils;

import org.apache.commons.math3.distribution.ChiSquaredDistribution;
import org.apache.commons.math3.linear.RealVector;

import java.util.Random;

public class StatsUtils {

    static Random random = new Random();

    static private double STD_DEVIATION = 1;
    static private double MEAN = 0;

    static ChiSquaredDistribution distribution2DOF = new ChiSquaredDistribution(2);
    static ChiSquaredDistribution distribution3DOF = new ChiSquaredDistribution(3);

    public static RealVector addGaussianNoise(RealVector state) {
        int len = state.getDimension();
        for (int i = 0; i < len; i++) {
            state.setEntry(len-1, state.getEntry(len-1) + generateGaussian());
        }
        return state;
    }

    public static RealVector addGaussianNoise2D(RealVector state) {
        if (state.getDimension() != 3) {
            return null;
        } else {
            state.setEntry(0, state.getEntry(0) + generateGaussian(1,0));
            state.setEntry(1, state.getEntry(1) + generateGaussian(1,0));
            state.setEntry(2, state.getEntry(2) + generateGaussian(0.01,0));
        }
        return state;
    }

    public static RealVector addGaussianNoise2D(RealVector state, double positionDeviation, double headingDeviation) {
        if (state.getDimension() != 3) {
            return null;
        } else {
            state.setEntry(0, state.getEntry(0) + generateGaussian(positionDeviation,0));
            state.setEntry(1, state.getEntry(1) + generateGaussian(positionDeviation,0));
            state.setEntry(2, state.getEntry(2) + generateGaussian(headingDeviation,0));
        }
        return state;
    }


    public static double generateGaussian() {
        return random.nextGaussian() * STD_DEVIATION + MEAN;
    }

    public static double generateGaussian(double STD_DEVIATION, double MEAN) {
        return random.nextGaussian() * STD_DEVIATION + MEAN;
    }

    public static void setStdDeviation(double newSTD_DEVIATION) {
        STD_DEVIATION = newSTD_DEVIATION;
    }

    public static void setMEAN(double newMEAN) {
        MEAN = newMEAN;
    }

    public static double readingDeltaProbability(RealVector v1, RealVector v2, int DOF) {

        RealVector readingDelta = v1.subtract(v2);

        double probSensorGivenState = 0;
        if (DOF == 2){
            probSensorGivenState = distribution2DOF.density(readingDelta.getNorm());
        } else if ( DOF == 3 ) {
            probSensorGivenState = distribution3DOF.density(readingDelta.getNorm());
        }

        return probSensorGivenState;
    }

    public static double quickGaussian(long randomBits) {
        long evenChunks = randomBits & EVEN_CHUNKS;
        long oddChunks = (randomBits & ODD_CHUNKS) >>> 5;
        long sum = chunkSum(evenChunks + oddChunks)  - 186; // Mean = 31*12 / 2
        return sum / 31.0;
    }

    private static long chunkSum(long bits) {
        long sum = bits + (bits >>> 40);
        sum += sum >>> 20;
        sum += sum >>> 10;
        sum &= (1<<10)-1;
        return sum;
    }

    static final long EVEN_CHUNKS = 0x7c1f07c1f07c1fL;
    static final long ODD_CHUNKS  = EVEN_CHUNKS << 5;

}
