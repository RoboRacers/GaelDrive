package com.roboracers.gaeldrive.tests;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.roboracers.gaeldrive.filters.ParticleFilter2d;
import com.roboracers.gaeldrive.sensors.SensorModel;
import com.roboracers.gaeldrive.sensors.TestDistanceSensorModel;
import com.roboracers.gaeldrive.utils.Updatable;

import java.util.ArrayList;
import java.util.List;

public class WeightingandResamplingBenchmark {
    static long loop;
    static long loopTime = 0;

    static ParticleFilter2d filter = new ParticleFilter2d(-72,72,-72,72,-0.001,0.001);
    static List<SensorModel> models = new ArrayList<>();

    public static void main(String[] args) throws Exception {

        System.out.println("* * * * * * * * * * * *");
        System.out.println("Benchmark Started!");

        Pose2d pose1 =  new Pose2d(0,0, 0);
        Pose2d pose2 = new Pose2d(0,0,Math.toRadians(-90));
        Pose2d pose3 = new Pose2d(0,0,Math.toRadians(90));

        models.add(new TestDistanceSensorModel(51, pose1));
        models.add(new TestDistanceSensorModel(51, pose2));
        models.add(new TestDistanceSensorModel(51, pose3));

        for (Updatable model: models) {
            model.update();
        }

        // Start Resampling
        loop = System.nanoTime();
        filter.initializeParticles(100, new Pose2d(0, 0,Math.toRadians(45)));
        loopTime = System.nanoTime();

        System.out.println("Time for initialization function call: " + (loopTime-loop)/1000000 + "ms");

        // Start Weighting
        loop = System.nanoTime();
        filter.weighParticles(models);
        loopTime = System.nanoTime();

        System.out.println("Time for weighting function call: " + (loopTime-loop)/1000000 + "ms");

        // Start Resampling
        loop = System.nanoTime();
        filter.resampleParticles(new double[] {0.5,0.5,0.01});
        loopTime = System.nanoTime();

        System.out.println("Time for resampling function call: " + (loopTime-loop)/1000000 + "ms");

        System.out.println("Benchmark Ended!");
        System.out.println("* * * * * * * * * * * *");
    }
}
