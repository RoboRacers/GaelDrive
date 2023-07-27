package com.roboracers.gaeldrive;

import com.acmerobotics.roadrunner.geometry.Pose2d;

/**
 * Class full of constants to tune the localization.
 */
public class LocalizationConstants {

    //TODO: Tune Localization Constants
    /**
     * Number of particles in the particle filter. More particles means higher accuracy,
     * but worse performance. Tune to a value you feel right with.
     */
    public static int PARTICLE_COUNT = 20;

    /**
     * The default start pose of the robot. Feel free to change this or just call
     * "drive.setPoseEstimate()" in the start of your autonomous cycle.
     */
    public static Pose2d START_POSE = new Pose2d(0,0,0);

    /**
     * The positional deviation in which the particles are spawned in relation to their X and Y coordinates.
     */
    public static double POSITIONAL_DEVIATION = 0.1;

    /**
     * The rotational deviation in which the particles are spawned in relation to heading.
     */
    public static double ROTATIONAL_DEVIATION = 0.01;

    public static boolean TESTING = false;
}
