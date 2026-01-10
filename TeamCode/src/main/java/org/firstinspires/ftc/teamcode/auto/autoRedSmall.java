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



    @Override
    public void runOpMode() throws InterruptedException {
        startPose = new Pose2d(60, 11, Math.toRadians(155));
        drive = new MecanumDrive(hardwareMap, startPose);
        TrajectoryActionBuilder build = drive.actionBuilder(startPose)
                //.afterTime(3, launcher.autonSpinUp())
                //.afterTime(2, chamber.autoChamberUp())
                //.afterTime(3, launcher.autonSpinDown())
                .splineToLinearHeading(new Pose2d(new Vector2d(12, 25), Math.toRadians(-270)), Math.toRadians(-230))
                //.afterTime(2, chamber.autoChamberDown())
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





        //PoseStorage.storedPose = drive.pose;






        launcher.init();
        chamber.init();




        waitForStart();

        Actions.runBlocking(new ParallelAction(
                launcher.autonListen(),
                launcher.autonSpinUp(),
                launcher.autonSpinDown(),
//                chamber.autoChamberUp(),
//                chamber.autoChamberDown(),
                build.build()
        ));

    }
}

