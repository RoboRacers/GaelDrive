package com.roboracers.gaeldrive.sensors;

import org.apache.commons.math3.linear.ArrayRealVector;
import org.apache.commons.math3.linear.RealVector;
import org.jetbrains.annotations.TestOnly;

import java.util.concurrent.ThreadLocalRandom;

public class TestSensorModel implements SensorModel {

    public TestSensorModel() {

    }

    @Override
    public double getWeightModifier() {
        return 1;
    }

    @Override
    public RealVector getActualReading() {
        return new ArrayRealVector(new double[] {10});
    }

    @Override
    public RealVector getSimulatedReading(RealVector state) {
        return new ArrayRealVector(new double[] {ThreadLocalRandom.current().nextDouble(0, 20)});
    }

    @Override
    public void update() {

    }
}
