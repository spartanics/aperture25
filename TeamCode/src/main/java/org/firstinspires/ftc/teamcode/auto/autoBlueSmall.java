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
import org.firstinspires.ftc.teamcode.hardware.Intake;
import org.firstinspires.ftc.teamcode.hardware.Launcher;


@Config
@Autonomous(name = "Auto Blue Small", group = "Autonomous")
public class autoBlueSmall extends LinearOpMode {
    Pose2d startPose;
    MecanumDrive drive;

    Chamber chamber = new Chamber(this);
    Launcher launcher = new Launcher(this);
    Intake intake = new Intake(this);

//this auton cannot scan and sort, these features need to be implemented before a auton is complete

    @Override
    public void runOpMode() throws InterruptedException {
        startPose = new Pose2d(58, -12, Math.toRadians(0));
        drive = new MecanumDrive(hardwareMap, startPose);
        TrajectoryActionBuilder build = drive.actionBuilder(startPose)
                // scan
                // launch & sort mechs
                .setReversed(true)
                .splineToLinearHeading(new Pose2d(new Vector2d(-13, -15),  7 * Math.PI / 32), Math.PI)
                .afterTime(0, launcher.autoSpinUp())
                .waitSeconds(3.5)
                .afterTime(0, chamber.autoLaunch())
                .waitSeconds(0.45)
                .afterTime(0, chamber.autoCycle())
                .waitSeconds(1.25)
                .afterTime(0, chamber.autoLaunch())
                .waitSeconds(0.45)
                .afterTime(0, chamber.autoCycle())
                .waitSeconds(1.2)
                .afterTime(0, chamber.autoLaunch())
                .waitSeconds(0.45)
                .afterTime(0, launcher.autoSpinDown())
                .setReversed(false)
                .splineToLinearHeading(new Pose2d(new Vector2d(30, -38), Math.toRadians(270)), Math.toRadians(-90))
                // intake mechs
                .afterTime(0, intake.autoIntakeStart())
                .waitSeconds(0.2)
                .afterTime(0, intake.autoIntakeStop())
                .waitSeconds(0.2)
                .splineToConstantHeading(new Pose2d(new Vector2d(30, -42), Math.toRadians(180)).component1(), Math.toRadians(-90))
                .waitSeconds(0.2)
                .afterTime(0, chamber.autoCycle())
                .waitSeconds(0.2)
                .afterTime(0, intake.autoIntakeStart())
                .splineToConstantHeading(new Pose2d(new Vector2d(30, -44), Math.toRadians(180)).component1(), Math.toRadians(-90))
                .waitSeconds(0.5)
                .afterTime(0, chamber.autoCycle())
                .splineToConstantHeading(new Pose2d(new Vector2d(30, -47), Math.toRadians(180)).component1(), Math.toRadians(-90))
                .afterTime(0, intake.autoIntakeStop())
                // sorting mechs
//                .setReversed(true)
//                .splineToLinearHeading(new Pose2d(new Vector2d(-10, -5), Math.PI / 4), Math.PI)
                // launch mech
//                .afterTime(0, launcher.autoSmallSpinUp())
//                .waitSeconds(0.5)
//                .afterTime(0, chamber.autoLaunch())
//                .waitSeconds(1)
//                .afterTime(0, launcher.autoSpinDown())
//                .waitSeconds(0.2)
//                .setReversed(true)
//                .splineToLinearHeading(new Pose2d(new Vector2d(11, -27), Math.toRadians(270)), Math.toRadians(-90))
//                intake mech
//                .afterTime(0, intake.autoIntakeStart())
//                .splineToConstantHeading(new Pose2d(new Vector2d(11, -58), Math.toRadians(180)).component1(), Math.toRadians(-90))
//                .afterTime(0, intake.autoIntakeStop())
//                sorting mech
//                .setReversed(true)
//                .splineToLinearHeading(new Pose2d(new Vector2d(-10, -5), Math.PI / 4), Math.PI)
                //launching mech
//                .afterTime(0.5, launcher.autoSmallSpinUp())
//                .afterTime(1, chamber.autoLaunch())
//                .afterTime(0.2, launcher.autoSpinDown())
//                .setReversed(true)
//                .splineToLinearHeading(new Pose2d(new Vector2d(-11, -30), Math.toRadians(270)), Math.toRadians(-90))

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
        intake.init();




        waitForStart();

        Actions.runBlocking(new ParallelAction(
                launcher.autoListen(),
                chamber.autoListen(),
                intake.autoListen(),
                build.build()
        ));


        launcher.sendTelemetry();
        telemetry.update();

    }
}

