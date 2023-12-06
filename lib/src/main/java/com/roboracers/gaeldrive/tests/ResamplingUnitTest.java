package com.roboracers.gaeldrive.tests;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.roboracers.gaeldrive.filters.ParticleFilter2d;
import com.roboracers.gaeldrive.sensors.SensorModel;

import java.util.ArrayList;
import java.util.List;

public class ResamplingUnitTest {
    static long loop;
    static long loopTime = 0;

    static ParticleFilter2d filter = new ParticleFilter2d(-72,72,-72,72,-0.001,0.001);
    static List<SensorModel> models = new ArrayList<>();

    static double[] resamplingDeviances = {0.5,0.5,0.01};


    public static void main(String[] args) throws Exception {

        System.out.println("* * * * * * * * * * * *");
        System.out.println("Unit Test Started!");
        loop = System.nanoTime();


        filter.initializeParticles(20, new Pose2d(0, 0,Math.toRadians(45)));

        for (int i = 0; i < 19; i++) {
            System.out.println(
                    filter.getParticle(i)
            );
        }

        // Start Resampling
        filter.resampleParticles(resamplingDeviances);

        loopTime = System.nanoTime();

        /*

        System.out.println("After Resampling: ");
        for (int i = 0; i < 19; i++) {
            System.out.println(
                    filter.getParticle(i)
            );
        }

         */

        // System.out.println("Random Resampled Particle: " + PoseUtils.vectorToPose(filter.getRandomParticle().getState()));
        // System.out.println("Time for resampling function call: " + (loopTime-loop)/1000000 + "ms");

        /*
        for (int i = 0; i < 100; i++) {
            filter.resampleParticles();
            filter.weighParticles(models);
        }

         */

        System.out.println("Unit Test Ended!");
        System.out.println("* * * * * * * * * * * *");
    }
}
