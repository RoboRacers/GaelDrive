package com.roboracers.gaeldrive.distance;

import static org.apache.commons.math3.util.FastMath.abs;
import static org.apache.commons.math3.util.FastMath.cos;
import static org.apache.commons.math3.util.FastMath.sin;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.roboracers.gaeldrive.utils.VectorUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Classes to represent field obstacles in code and to calculate distances from objects to obstacles.
 * Used in DistanceSensorModel implementations.
 */
public class FieldDistance {

    /**
     * List of field geometry consisting of lines.
     */
    public static List<Vector2d[]> FieldGeometry = new ArrayList<>();

    static  {
        // Geometry Added Here

        FieldGeometry.add(new Vector2d[] {new Vector2d(-72, 72), new Vector2d(72, 72)});
        FieldGeometry.add(new Vector2d[] {new Vector2d(72, 72), new Vector2d(72, -72)});
        FieldGeometry.add(new Vector2d[] {new Vector2d(72, -72), new Vector2d(-72, -72)});
        FieldGeometry.add(new Vector2d[] {new Vector2d(-72, -72), new Vector2d(-72, 72)});

        /*
        addGeometery(new Vector2d[] {new Vector2d(-72, 72), new Vector2d(72, 72)});
        addGeometery(new Vector2d[] {new Vector2d(72, 72), new Vector2d(72, -12)});
        addGeometery(new Vector2d[] {new Vector2d(72, -12), new Vector2d(-72, -12)});
        addGeometery(new Vector2d[] {new Vector2d(-72, -12), new Vector2d(-72, 72)});
         */
    }

    /** Add your own custom geometry to the field.
     * @param line Array of two Vector2d entries.
     */
    public static void addGeometery(Vector2d[] line) {
        FieldGeometry.add(line);
    }

    /**
     * Calculates a simulated distance reading given an origin point.
     * @param origin the origin of the distance reading
     * @return simulated distance from the sensor to the nearest field geometry.
     */
    public static double calculateSimulatedDistance(Pose2d origin) {

        List<Double> distances = new ArrayList<>();

        for(Vector2d[] line : FieldGeometry) { //TODO: Point of improvement - currently checks every line, more time copmplexity
            double distance = getRayToLineSegmentIntersection(origin, line[0], line[1]);
            if (distance != -1 ) {
                distances.add(distance);
            }
        }

        if (distances.isEmpty() == false) {
            return Collections.min(distances);
        }
        return 0;
    }

    /**
     * Find the intersection between a ray and line.
     * @param pose
     * @param point1
     * @param point2
     * @return Distance between ray and line
     */
    public static double getRayToLineSegmentIntersection(Pose2d pose, Vector2d point1, Vector2d point2) {

        Vector2d rayOrigin = new Vector2d(pose.getX(), pose.getY());
        Vector2d v1 = rayOrigin.minus(point1);
        Vector2d v2 = point2.minus(point1);
        Vector2d v3 = new Vector2d(-sin(pose.getHeading()), cos(pose.getHeading()));
        double dot = v2.dot(v3);

        if (abs(dot) < 0.000001) {
            return -1;
        }

        double t1 = VectorUtils.CrossProduct2d(VectorUtils.Vector2dToVector(v2), VectorUtils.Vector2dToVector(v1))/dot;
        double t2 = (v1.dot(v3)) / dot;

        if (t1 >= 0.0 && (t2 >= 0.0 && t2 <= 1.0)) {
            return t1;
        }

        return -1;
    }

    public static double getRayToLineSegmentIntersection2(Pose2d pose, Vector2d point1, Vector2d point2) {

        Vector2d rayOrigin = new Vector2d(pose.getX(), pose.getY());
        Vector2d v1 = rayOrigin.minus(point1);
        Vector2d v2 = point2.minus(point1);
        Vector2d v3 = new Vector2d(-sin(pose.getHeading()), cos(pose.getHeading()));
        double dot = v2.dot(v3);

        if (abs(dot) < 0.000001) {
            return -1;
        }

        double t1 = VectorUtils.CrossProduct2d(VectorUtils.Vector2dToVector(v2), VectorUtils.Vector2dToVector(v1))/dot;
        double t2 = (v1.dot(v3)) / dot;

        if (t1 >= 0.0 && (t2 >= 0.0 && t2 <= 1.0)) {
            return t1;
        }

        return -1;
    }

}
