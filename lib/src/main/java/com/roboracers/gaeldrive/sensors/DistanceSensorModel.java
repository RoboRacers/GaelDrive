package com.roboracers.gaeldrive.sensors;

import com.roboracers.gaeldrive.distance.FieldDistance;
import com.roboracers.gaeldrive.utils.PoseUtils;

import org.apache.commons.math3.linear.ArrayRealVector;
import org.apache.commons.math3.linear.RealVector;

public abstract class DistanceSensorModel implements SensorModel {

    public double rawReading;

    // Config variables
    public double weight;
    public int DOF = 2;


    // These values may not be the same as the manufacturer specified minimum and max distances. Tune them to your liking.
    public double minDistance;
    public double maxDistance;
    public RealVector location;

    /**
     * Gets the weight modifier assigned to this model.
     * @return Weight modifier
     */
    @Override
    public double getWeightModifier() {
        return weight;
    }

    /**
     * @return Degrees of freedom of the sensor model
     */
    @Override
    public int getDOF() {
        return DOF;
    }

    /**
     * Returns a vectorized version of the reading.
     * @return
     */
    @Override
    public RealVector getActualReading() {
        RealVector reading = new ArrayRealVector(new double[] {rawReading});
        return reading;
    }

    /**
     * Returns the expected vectorized sensor reading from a particular state.
     * @param state the state of the particle
     * @return Simulated sensor value
     */
    @Override
    public RealVector getSimulatedReading(RealVector state) {
        double simulatedDistance = FieldDistance.calculateSimulatedDistance(PoseUtils.vectorToPose(state.add(location)));
        // Limiting Readings to min and max distances
        if (simulatedDistance < minDistance) {
            simulatedDistance = minDistance;
        } else if (simulatedDistance > maxDistance) {
            simulatedDistance = maxDistance;
        }
        return new ArrayRealVector(new double[] {simulatedDistance});
    }

    /**
     * Gets the raw reading from the sensor.
     * @return Distance sensor reading
     */
    public double getRawReading () {
        return rawReading;
    }

}
