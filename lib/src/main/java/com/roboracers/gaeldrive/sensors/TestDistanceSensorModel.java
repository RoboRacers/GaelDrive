package com.roboracers.gaeldrive.sensors;

import com.acmerobotics.roadrunner.geometry.Pose2d;


/**
 * Class to process the data from a distance sensor to be suitable for MCL.
 * You can create your own models for whatever sensor, just use the SensorModel interface.
 * Use the FieldDistance class for distance calculations (Included in GD for your use).
 */
public class TestDistanceSensorModel extends DistanceSensorModel {

    /**
     * Constructor for a distance sensor.
     *
     *
     */
    public TestDistanceSensorModel(double dist, Pose2d location) {
        this.location = null;
        this.rawReading = dist;
        this.minDistance = 5;
        this.maxDistance = 100;
        this.weight = 1;
    }

    @Override
    public void update() {

    }


}
