package com.roboracers.gaeldrive.motion;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.roboracers.gaeldrive.utils.Updatable;

import org.apache.commons.math3.linear.RealVector;

/**
 * A model to predict the movement of a robot over a given timeframe.
 */
public interface MotionModel extends Updatable {

    /**
     * Get the predictive translation in the form of a vector.
     * @return Translation Vector
     */
    public RealVector getTranslationVector();

    /**
     * Get the raw estimate of the predictive model
     * @return Pose Estimate
     */
    public Pose2d getRawEstimate();

}
