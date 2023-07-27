package com.roboracers.gaeldrive.filters;

import com.acmerobotics.roadrunner.geometry.Pose2d;

import org.firstinspires.ftc.teamcode.modules.gaeldrive.LocalizationConstants;
import org.firstinspires.ftc.teamcode.modules.gaeldrive.particles.Particle;
import org.firstinspires.ftc.teamcode.modules.gaeldrive.particles.Particle2d;
import org.firstinspires.ftc.teamcode.modules.gaeldrive.utils.PoseUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Specialized Particle Filter class for 2d robot localization.
 */
public class ParticleFilter2d extends ParticleFilter {

    public ParticleFilter2d() {
    }

    public void initializeParticles(int numParticles, Pose2d startingLocation) {

        // Deviation Threshold for spawning new particles
        double min = -LocalizationConstants.POSITIONAL_DEVIATION;
        double max = LocalizationConstants.POSITIONAL_DEVIATION;
        double heading_min = -LocalizationConstants.ROTATIONAL_DEVIATION;
        double heading_max = LocalizationConstants.ROTATIONAL_DEVIATION;

        for(int i=0; i < numParticles; i++ ) {
            // Generate random deviances TODO: Make a more mathematical resampling system
            double deviation1 = ThreadLocalRandom.current().nextDouble(min, max);
            double deviation2 = ThreadLocalRandom.current().nextDouble(min, max);
            double deviation3 = ThreadLocalRandom.current().nextDouble(heading_min, heading_max);


            // Create the new pose
            Pose2d addedPose = new Pose2d(  startingLocation.getX() + deviation1,
                                            startingLocation.getY() + deviation2,
                                            startingLocation.getHeading() + deviation3);

            add(new Particle2d(addedPose, 0, i));

        }

    }


    public Pose2d getBestPose () {
        Pose2d bestPose = PoseUtils.vectorToPose(getBestParticle().getState());
        return bestPose;
    }

    public List<Pose2d> getParticlePoses (){
        List<Pose2d> poses = new ArrayList<Pose2d>();
        HashMap<Integer, Particle> particles = getParticles();

        for (Map.Entry<Integer,Particle> particle2dEntry : particles.entrySet()) {
            poses.add(PoseUtils.vectorToPose(particle2dEntry.getValue().getState()));
        }

        return poses;
    }


}
