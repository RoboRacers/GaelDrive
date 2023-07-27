package com.roboracers.gaeldrive.motion;

import com.roboracers.gaeldrive.utils.Updatable;

import org.apache.commons.math3.linear.RealVector;

/**
 * A model to predict the movement of a robot over a given timeframe.
 */
public interface MotionModel extends Updatable {

    public RealVector getTranslationVector();

}
