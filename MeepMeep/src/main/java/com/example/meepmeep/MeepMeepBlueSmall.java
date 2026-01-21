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
        myBot.runAction(myBot.getDrive().actionBuilder(new Pose2d(58, -12, Math.toRadians(20)))
                .setReversed(true)
                .splineToLinearHeading(new Pose2d(new Vector2d(-10, -5), Math.PI / 4), Math.PI)
//                .afterTime(0, launcher.autoSmallSpinUp())
//                .waitSeconds(2)
//                .afterTime(0, chamber.autoLaunch())
//                .waitSeconds(1)
//                .afterTime(0, chamber.autoCycle())
//                .waitSeconds(1)
//                .afterTime(0, launcher.autoSpinDown())
//                .waitSeconds(5)
                .setReversed(false)
                .splineToLinearHeading(new Pose2d(new Vector2d(36, -27), Math.toRadians(270)), Math.toRadians(-90))
                // intake mechs
//                .afterTime(0, intake.autoIntakeStart())
                .splineToConstantHeading(new Pose2d(new Vector2d(36, -58), Math.toRadians(180)).component1(), Math.toRadians(-90))
//                .afterTime(0, intake.autoIntakeStop())
                // sorting mechs
                .setReversed(true)
                .splineToLinearHeading(new Pose2d(new Vector2d(-10, -5), Math.PI / 4), Math.PI)
                // launch mech
//                .afterTime(0, launcher.autoSmallSpinUp())
//                .waitSeconds(0.5)
//                .afterTime(0, chamber.autoLaunch())
//                .waitSeconds(1)
//                .afterTime(0, launcher.autoSpinDown())
//                .waitSeconds(0.2)
                .setReversed(true)
                .splineToLinearHeading(new Pose2d(new Vector2d(11, -27), Math.toRadians(270)), Math.toRadians(-90))
//                intake mech
//                .afterTime(0, intake.autoIntakeStart())
                .splineToConstantHeading(new Pose2d(new Vector2d(11, -58), Math.toRadians(180)).component1(), Math.toRadians(-90))
//                .afterTime(0, intake.autoIntakeStop())
//                sorting mech
                .setReversed(true)
                .splineToLinearHeading(new Pose2d(new Vector2d(-10, -5), Math.PI / 4), Math.PI)
                //launching mech
//                .afterTime(0.5, launcher.autoSmallSpinUp())
//                .afterTime(1, chamber.autoLaunch())
//                .afterTime(0.2, launcher.autoSpinDown())
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