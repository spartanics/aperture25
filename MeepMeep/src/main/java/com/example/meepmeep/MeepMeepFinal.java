package com.example.meepmeep;

import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.Vector2d;
import com.noahbres.meepmeep.MeepMeep;
import com.noahbres.meepmeep.roadrunner.DefaultBotBuilder;
import com.noahbres.meepmeep.roadrunner.entity.RoadRunnerBotEntity;

public class MeepMeepFinal {
    public static void main(String[] args) {
        MeepMeep meepMeep = new MeepMeep(800);

        RoadRunnerBotEntity myBot = new DefaultBotBuilder(meepMeep)
                // Set bot constraints: maxVel, maxAccel, maxAngVel, maxAngAccel, track width
                .setConstraints(60, 60, Math.toRadians(180), Math.toRadians(180), 15)
                .build();

        myBot.runAction(myBot.getDrive().actionBuilder(new Pose2d(60, 11, Math.toRadians(-145)))
                        .waitSeconds(5)
                        //.splineTo(new Pose2d(new Vector2d(35, -22), Math.toRadians(135)).component1(), Math.toRadians(90))
                        .splineToLinearHeading(new Pose2d(new Vector2d(36, -29), Math.toRadians(270)), Math.toRadians(90))
                        .waitSeconds(3)
                        .splineToConstantHeading(new Pose2d(new Vector2d(60, 11), Math.toRadians(180)).component1(), Math.toRadians(135))
                        .turnTo(500)
                        .waitSeconds(5)
                        //.splineToLinearHeading(new Pose2d(new Vector2d(10, -20), Math.toRadians(180)), Math.toRadians(135))
                        .splineTo(new Pose2d(new Vector2d(10, -26), Math.toRadians(0)).component1(), Math.toRadians(0))
                        .turnTo(300)
                .build());

        meepMeep.setBackground(MeepMeep.Background.FIELD_DECODE_JUICE_DARK)
                .setDarkMode(true)
                .setBackgroundAlpha(0.95f)
                .addEntity(myBot)
                .start();
    }
}