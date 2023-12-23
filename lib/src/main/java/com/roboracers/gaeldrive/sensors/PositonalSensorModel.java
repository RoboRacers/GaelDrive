package com.roboracers.gaeldrive.sensors;

import org.apache.commons.math3.linear.ArrayRealVector;
import org.apache.commons.math3.linear.RealVector;

/**
 * Simple positional sensor.
 */
public abstract class PositonalSensorModel implements SensorModel {

    public ArrayRealVector position;

    public double weight;
    public int DOF = 2;

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
        return position;
    }

    /**
     * Returns the expected vectorized sensor reading from a particular state.
     * @param state the state of the particle
     * @return Simulated sensor value
     */
    @Override
    public RealVector getSimulatedReading(RealVector state) throws Exception {
        assert position == null || state.getDimension() == position.getDimension();
        return state;
    }


}
