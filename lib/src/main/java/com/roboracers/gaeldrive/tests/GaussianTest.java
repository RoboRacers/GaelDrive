package com.roboracers.gaeldrive.tests;

import com.roboracers.gaeldrive.utils.StatsUtils;

import java.util.Random;

public class GaussianTest {

    static long loop = 0;
    static long loopTime = 0;
    static boolean quick = false;
    static Random random = new Random();

    public static void main(String[] args) {
        for (int i = 0; i < 100; i++) {
            loop = System.nanoTime();
            if (quick) {
                System.out.println(StatsUtils.quickGaussian(random.nextLong()));
            } else {
                System.out.println(random.nextGaussian());
            }

            loopTime = System.nanoTime();

        }
        System.out.println("Time for function call: " + (loopTime-loop) + " ns");
    }
}
