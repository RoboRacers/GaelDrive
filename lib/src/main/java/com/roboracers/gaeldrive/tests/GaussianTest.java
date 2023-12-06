package com.roboracers.gaeldrive.tests;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.roboracers.gaeldrive.particles.Particle;
import com.roboracers.gaeldrive.particles.Particle2d;
import com.roboracers.gaeldrive.utils.StatsUtils;

import java.util.Random;

public class GaussianTest {

    static long loop = 0;
    static long loopTime = 0;
    static boolean quick = false;
    static Random random = new Random();

    public static void main(String[] args) {
        Particle initial = new Particle2d(new Pose2d(0,0,0), 1, 0);

        Particle newParticle = new Particle(StatsUtils.addGaussianNoise2D(initial.getState(), 0.001, 0.005), 0, 0);
        System.out.println(newParticle);
    }
}
