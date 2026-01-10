package com.example.meepmeep;

import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.Vector2d;
import com.noahbres.meepmeep.MeepMeep;
import com.noahbres.meepmeep.roadrunner.DefaultBotBuilder;
import com.noahbres.meepmeep.roadrunner.entity.RoadRunnerBotEntity;

public class MeepMeepBlueSmall {
    public static void main(String[] args) {
        MeepMeep meepMeep = new MeepMeep(800);

        RoadRunnerBotEntity myBot = new DefaultBotBuilder(meepMeep)
                // Set bot constraints: maxVel, maxAccel, maxAngVel, maxAngAccel, track width
                .setConstraints(60, 60, Math.toRadians(180), Math.toRadians(180), 15)
                .build();
//launch from big triangle if possible
        myBot.runAction(myBot.getDrive().actionBuilder(new Pose2d(56, 10, Math.toRadians(30)))
                .waitSeconds(2)
                .setReversed(true)
                .splineToLinearHeading(new Pose2d(new Vector2d(35, -27), Math.toRadians(270)), Math.toRadians(-90))
                .splineToConstantHeading(new Pose2d(new Vector2d(35, -58), Math.toRadians(180)).component1(), Math.toRadians(-90))
                .setReversed(true)
                .splineToLinearHeading(new Pose2d(new Vector2d(56, 10), Math.toRadians(30)), Math.toRadians(60))
                .setReversed(true)
                .splineToLinearHeading(new Pose2d(new Vector2d(11, -27), Math.toRadians(270)), Math.toRadians(-90))
                .splineToConstantHeading(new Pose2d(new Vector2d(11, -58), Math.toRadians(180)).component1(), Math.toRadians(-90))
                .setReversed(true)
                .splineToLinearHeading(new Pose2d(new Vector2d(56, 10), Math.toRadians(30)), Math.toRadians(60))
                .setReversed(true)
                .splineToLinearHeading(new Pose2d(new Vector2d(-11, -30), Math.toRadians(270)), Math.toRadians(-90))
                .build());

        meepMeep.setBackground(MeepMeep.Background.FIELD_DECODE_JUICE_DARK)
                .setDarkMode(true)
                .setBackgroundAlpha(0.95f)
                .addEntity(myBot)
                .start();
    }
}