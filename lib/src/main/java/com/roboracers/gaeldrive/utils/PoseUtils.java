package com.roboracers.gaeldrive.utils;

import com.acmerobotics.roadrunner.geometry.Pose2d;

import org.apache.commons.math3.linear.ArrayRealVector;
import org.apache.commons.math3.linear.RealVector;

public class PoseUtils {

    /**
     * Util method to convert a Pose2d object to a RealVector object.
     *
     * @param pose A Pose2d Object
     * @return A RealVector Object
     */
    public static RealVector poseToVector(Pose2d pose) {
        return new ArrayRealVector(new double[] {pose.getX(), pose.getY(), pose.getHeading()});
    }

    /**
     * Util method to convert a RealVector object to a Pose2d object.
     *
     * @param vector A RealVector object
     * @return A Pose2d object
     */
    public static Pose2d vectorToPose(RealVector vector) {
        return new Pose2d(vector.getEntry(0), vector.getEntry(1), vector.getEntry(2));
    }

}
