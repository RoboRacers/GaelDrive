package com.roboracers.gaeldrive.utils;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.apache.commons.math3.linear.ArrayRealVector;
import org.apache.commons.math3.linear.RealVector;
import org.junit.jupiter.api.Test;

class StatsUtilsTest {

    @Test
    void addGaussianNoiseDoesNotMutateInputVector() throws MismatchedLengthException {
        double[] original = {1.0, 2.0, 3.0};
        RealVector state = new ArrayRealVector(original);
        Deviance deviances = new Deviance(0.1, 0.1, 0.1);

        StatsUtils.addGaussianNoise(state, deviances);

        assertArrayEquals(original, state.toArray(), 0.0,
                "addGaussianNoise must not mutate the vector passed in by the caller");
    }

    @Test
    void addGaussianNoiseReturnsVectorOfSameDimension() throws MismatchedLengthException {
        RealVector state = new ArrayRealVector(new double[]{1.0, 2.0, 3.0});
        Deviance deviances = new Deviance(0.1, 0.1, 0.1);

        RealVector noisy = StatsUtils.addGaussianNoise(state, deviances);

        assertEquals(state.getDimension(), noisy.getDimension());
    }

    @Test
    void addGaussianNoiseThrowsOnLengthMismatch() {
        RealVector state = new ArrayRealVector(new double[]{1.0, 2.0});
        Deviance deviances = new Deviance(0.1, 0.1, 0.1);

        assertThrows(MismatchedLengthException.class, () -> StatsUtils.addGaussianNoise(state, deviances));
    }

    @Test
    void generateGaussianIsRoughlyCenteredOnMean() {
        double mean = 10.0;
        double sum = 0;
        int samples = 20_000;

        for (int i = 0; i < samples; i++) {
            sum += StatsUtils.generateGaussian(1.0, mean);
        }

        double average = sum / samples;
        assertTrue(Math.abs(average - mean) < 0.1,
                "Average of many Gaussian samples should be close to the requested mean, got " + average);
    }

    @Test
    void readingDeltaProbabilityIsHighestWhenReadingsMatch() {
        RealVector v1 = new ArrayRealVector(new double[]{5, 5});
        RealVector v2 = new ArrayRealVector(new double[]{5, 5});
        RealVector v3 = new ArrayRealVector(new double[]{50, 50});

        double matchingProbability = StatsUtils.readingDeltaProbability(v1, v2, 2);
        double distantProbability = StatsUtils.readingDeltaProbability(v1, v3, 2);

        assertTrue(matchingProbability > distantProbability);
    }

    @Test
    void readingDeltaProbabilityFallsBackToTwoDofForUnknownDof() {
        RealVector v1 = new ArrayRealVector(new double[]{1, 1});
        RealVector v2 = new ArrayRealVector(new double[]{2, 2});

        double fallback = StatsUtils.readingDeltaProbability(v1, v2, 99);
        double twoDof = StatsUtils.readingDeltaProbability(v1, v2, 2);

        assertEquals(twoDof, fallback);
    }
}
