package com.roboracers.gaeldrive.readings;

import org.firstinspires.ftc.teamcode.modules.gaeldrive.sensors.SensorModel;

import java.util.List;

public interface SensorStack {

    /**
     * Run on update on all the sensor values.
     */
    public static void update() {

    }

    public static List<SensorModel> getSensorModels () {
        return null;
    }
}
