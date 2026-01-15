package org.firstinspires.ftc.teamcode.auto;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.roadrunner.ParallelAction;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.TrajectoryActionBuilder;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.MecanumDrive;
import org.firstinspires.ftc.teamcode.hardware.Chamber;
import org.firstinspires.ftc.teamcode.hardware.Launcher;


@Config
@Autonomous(name = "Auto Red Base", group = "Autonomous")
public class autoRedSmall extends LinearOpMode {
    Pose2d startPose;
    MecanumDrive drive;

    Chamber chamber = new Chamber(this);
    Launcher launcher = new Launcher(this);

//i suck at commenting -crishna kala
    @Override
    public void runOpMode() throws InterruptedException {
        startPose = new Pose2d(56, 10, Math.toRadians(-20));
        drive = new MecanumDrive(hardwareMap, startPose);
        TrajectoryActionBuilder build = drive.actionBuilder(startPose)
                .setReversed(true)
                .splineToLinearHeading(new Pose2d(new Vector2d(36, 27), Math.toRadians(90)), Math.toRadians(90))
                .splineToConstantHeading(new Pose2d(new Vector2d(36, 56), Math.toRadians(180)).component1(), Math.toRadians(-90))
                .setReversed(true)
                .splineToLinearHeading(new Pose2d(new Vector2d(56, 10), Math.toRadians(-20)), Math.toRadians(60))
                .setReversed(true)
                .splineToLinearHeading(new Pose2d(new Vector2d(12, 27), Math.toRadians(90)), Math.toRadians(90))
                .splineToConstantHeading(new Pose2d(new Vector2d(12, 55), Math.toRadians(180)).component1(), Math.toRadians(-90))
                .setReversed(true)
                .splineToLinearHeading(new Pose2d(new Vector2d(56, 10), Math.toRadians(-20)), Math.toRadians(60))
                .setReversed(true)
                .splineToLinearHeading(new Pose2d(new Vector2d(-10, 30), Math.toRadians(90)), Math.toRadians(180))

                ;
//        TrajectoryActionBuilder flipped = new TrajectoryActionBuilder(build,
//                new TrajectoryBuilderParams(pose -> new Pose2dDual<>(
//                        pose.position.x, pose.position.y.unaryMinus(), pose.heading.inverse()),
//                1,
//                lastPoseUnmapped: Pose2d,
//                lastPose: Pose2d,
//                lastTangent: Rotation2d,
//                ms:List<MarkerFactory>,
//        cont: (Action) -> Action);
// psst hey

//hi


        //PoseStorage.storedPose = drive.pose;






        launcher.init();
        chamber.init();




        waitForStart();

        Actions.runBlocking(new ParallelAction(
                launcher.autoListen(),
//                chamber.autoChamberUp(),
//                chamber.autoChamberDown(),
                build.build()
        ));

    }
}

