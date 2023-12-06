package com.roboracers.gaeldrive.tests;

public abstract class TestTemplate {

    static TestTemplate This;

    public void init(TestTemplate This) {
        this.This = This;
    }


    public abstract void test();

    public static void main(String[] args) {

        This.test();
    }
}
