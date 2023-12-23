package com.roboracers.gaeldrive.utils;

import org.apache.commons.math3.linear.ArrayRealVector;
import org.apache.commons.math3.linear.RealVector;

public class VectorUtils {

    /**
     * Takes the cross product of two 2d vectors. Returns a scalar. Not commutative.
     * @param t1 First Vector
     * @param t2 Second Vector
     * @return Scalar
     */
    public static double CrossProduct2d (RealVector t1, RealVector t2) {
        return t1.getEntry(0)*t2.getEntry(1)-t1.getEntry(1)*t2.getEntry(0);
    }

    /**
     * Create a vector with 3 elements, most commonly used for localization algorithms.
     * @param x
     * @param y
     * @param heading
     * @return 3 Element vector in the format <x, y, heading>
     */
    public static RealVector create3DVector (double x, double y, double heading) {
        return  new ArrayRealVector(new double[] {x, y, heading});
    }

 }
