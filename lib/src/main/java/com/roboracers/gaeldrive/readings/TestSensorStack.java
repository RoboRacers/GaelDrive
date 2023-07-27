package com.roboracers.gaeldrive.readings;

import com.acmerobotics.roadrunner.geometry.Pose2d;

import org.jetbrains.annotations.TestOnly;

import java.util.ArrayList;
import java.util.List;

/**
 * Class to store and load sensor values used by the MCL (Monte Carlo Localizer)
 */
public class TestSensorStack implements SensorStack {
    /* Flags for user to change */
    static List<Pose2d> path = new ArrayList<>();

    public static Pose2d mockPoseEstimate = new Pose2d(0,0,0);
    static int iterator = 0;


    static public void init() {
        path.add(new Pose2d(0,0,0));
        path.add(new Pose2d(5,0,0));
        path.add(new Pose2d(10,0,0));
        path.add(new Pose2d(15,0,0));
        path.add(new Pose2d(20,0,0));
        path.add(new Pose2d(20,5,Math.toRadians(90)));
        path.add(new Pose2d(20,10,Math.toRadians(90)));
        path.add(new Pose2d(20,15,Math.toRadians(90)));
        path.add(new Pose2d(20,20,Math.toRadians(90)));
        path.add(new Pose2d(15,20,Math.toRadians(180)));
        path.add(new Pose2d(10,20,Math.toRadians(180)));
        path.add(new Pose2d(5,20,Math.toRadians(180)));
        path.add(new Pose2d(0,20,Math.toRadians(180)));
        path.add(new Pose2d(0,15,Math.toRadians(270)));
        path.add(new Pose2d(0,10,Math.toRadians(270)));
        path.add(new Pose2d(0,5,Math.toRadians(270)));
        path.add(new Pose2d(0,0,Math.toRadians(270)));
    }

    public static void update(){
        mockPoseEstimate = path.get(iterator);
        iterator++;
    }
}
