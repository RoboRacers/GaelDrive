package com.roboracers.gaeldrive.utils;

public class Deviance {

    public double[] values;
    public final int length;

    public Deviance(double[] values) {
        this.values = values;
        length = values.length;
    }

    public Deviance(double v1, double v2, double v3) {
        this.values = new double[] {v1,v2,v3};
        this.length = values.length;
    }
}