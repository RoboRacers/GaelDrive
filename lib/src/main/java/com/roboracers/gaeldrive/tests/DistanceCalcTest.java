package com.roboracers.gaeldrive.tests;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.roboracers.gaeldrive.distance.FieldDistance;

public class DistanceCalcTest {
    static long loop;
    static long loopTime = 0;

    public static void main(String[] args) {

        loop = System.nanoTime();
        for (int i = 0; i < 2000; i++) {
            double distance = FieldDistance.calculateSimulatedDistance(new Pose2d(0,0,Math.toRadians(180)));
        }
        loopTime = System.nanoTime();
        System.out.println("Time for function call: " + (loopTime-loop)/1000000 + "ms");
    }
}
