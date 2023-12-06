package com.roboracers.gaeldrive.tests;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.roboracers.gaeldrive.filters.ParticleFilter2d;
import com.roboracers.gaeldrive.sensors.SensorModel;
import com.roboracers.gaeldrive.sensors.TestDistanceSensorModel;
import com.roboracers.gaeldrive.utils.Updatable;

import java.util.ArrayList;
import java.util.List;

public class WeightingUnitTest {
    static long loop;
    static long loopTime = 0;

    static ParticleFilter2d filter = new ParticleFilter2d();
    static List<SensorModel> models = new ArrayList<>();
    public static void main(String[] args) {

        System.out.println("* * * * * * * * * * * *");
        System.out.println("Unit Test Started!");
        loop = System.nanoTime();

        Pose2d pose1 =  new Pose2d(0,0, 0);
        Pose2d pose2 = new Pose2d(0,0,Math.toRadians(-90));
        Pose2d pose3 = new Pose2d(0,0,Math.toRadians(90));

        models.add(new TestDistanceSensorModel(51, pose1));
        models.add(new TestDistanceSensorModel(51, pose2));
        models.add(new TestDistanceSensorModel(51, pose3));

        for (Updatable model: models
             ) {
            model.update();
        }

        System.out.println("Actual Sensor Reading: " + models.get(0).getActualReading() + ", Relative Sensor Location " + pose1);
        System.out.println("Actual Sensor Reading: " + models.get(1).getActualReading() + ", Relative Sensor Location: " + pose2);
        System.out.println("Actual Sensor Reading: " + models.get(2).getActualReading() + ", Relative Sensor Location: " + pose3);
        filter.initializeParticles(2000, new Pose2d(0, 0,Math.toRadians(45)));
        filter.weighParticles(models);
        loopTime = System.nanoTime();

        System.out.println("Best Particle Pose: " + filter.getBestPose());
        System.out.println("Best Particle Weight: " + filter.getBestParticle().getWeight());
        System.out.println("Time for function call: " + (loopTime-loop)/1000000 + "ms");

        System.out.println("Unit Test Ended!");
        System.out.println("* * * * * * * * * * * *");
    }
}
