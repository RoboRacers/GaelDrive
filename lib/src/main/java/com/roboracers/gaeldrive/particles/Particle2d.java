package com.roboracers.gaeldrive.particles;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.roboracers.gaeldrive.utils.PoseUtils;


/**
 * Extension of the Particle class, intended for used with a 2d robot localization system.
 * Note that the state of this particle is actually 3 dimensions (x,y, heading).
 * @see Particle
 */
public class Particle2d extends Particle {

    public Particle2d(Pose2d pose2d, double weight, Integer id) {
        this.state = PoseUtils.poseToVector(pose2d);
        this.weight = weight;
        this.id = id;
    }


    public void setPose(Pose2d newPose){
        state = PoseUtils.poseToVector(newPose);
    }
    public Pose2d getPose(){
        return PoseUtils.vectorToPose(state);
    }

}
