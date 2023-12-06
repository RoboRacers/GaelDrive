package com.roboracers.gaeldrive.sensors;

import com.roboracers.gaeldrive.motion.MotionModel;
import com.roboracers.gaeldrive.utils.Updatable;

import java.util.ArrayList;
import java.util.List;

public abstract class SensorStack {

    public static MotionModel motionModel;
    public static List<SensorModel> sensorModels = new ArrayList<>();

    /**
     * Run on update on all the sensor values.
     */
    public static void update() {
        // Update our motion model
        motionModel.update();
        // Update all sensor models
        for (Updatable model: sensorModels) {
            model.update();
        }
    }

    public static List<SensorModel> getSensorModels () {
        return sensorModels;
    }

    public static void addSensorModel (SensorModel model) {
        sensorModels.add(model);
    }
}
