package com.roboracers.gaeldrive.sensors;

import com.roboracers.gaeldrive.utils.Updatable;

import org.apache.commons.math3.linear.RealVector;

public interface SensorModel extends Updatable {

    public double getWeightModifier();

    public RealVector getActualReading();

    public RealVector getSimulatedReading(RealVector state);



}
