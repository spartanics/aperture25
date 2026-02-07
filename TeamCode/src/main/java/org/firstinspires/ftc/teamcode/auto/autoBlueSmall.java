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
                .setReversed(true)
                .splineToLinearHeading(new Pose2d(new Vector2d(-13, -15),  8 * Math.PI / 32), Math.PI)
                .afterTime(0, launcher.autoSpinUp())
                .waitSeconds(3)
                .afterTime(0, chamber.autoLaunch())
                .waitSeconds(0.45)
                .afterTime(0, chamber.autoCycle())
                .waitSeconds(1)
                .afterTime(0, chamber.autoLaunch())
                .waitSeconds(0.45)
                .afterTime(0, chamber.autoCycle())
                .waitSeconds(1)
                .afterTime(0, chamber.autoLaunch())
                .waitSeconds(0.45)
                .afterTime(0, launcher.autoSpinDown())
                .waitSeconds(0.1)

                //intake sequence
                .afterTime(0, intake.autoIntakeStart())
                .setReversed(false)
                .splineToLinearHeading(new Pose2d(new Vector2d(30, -37), Math.toRadians(270)), Math.toRadians(-90))
                // intake mechs
//intake
                .waitSeconds(0.5)
                .splineToConstantHeading(new Pose2d(new Vector2d(30, -42), Math.toRadians(-180)).component1(), Math.toRadians(-90))
                .waitSeconds(0.9) //0.3
                .afterTime(0, chamber.autoCycle())
                .waitSeconds(0.8) //0.3
                .splineToConstantHeading(new Pose2d(new Vector2d(30, -45), Math.toRadians(-180)).component1(), Math.toRadians(-90))
                .waitSeconds(0.9)
                .afterTime(0, chamber.autoCycle())
                .waitSeconds(0.8) //0.2
                .splineToConstantHeading(new Pose2d(new Vector2d(30, -49), Math.toRadians(-180)).component1(), Math.toRadians(-90))
                .waitSeconds(0.2)
                .afterTime(0, intake.autoIntakeStop())
                //launch 2
                .setReversed(true)
                .splineToLinearHeading(new Pose2d(new Vector2d(-13, -15),  8 * Math.PI / 32), Math.PI)

                .afterTime(0, launcher.autoSpinUp())
                .waitSeconds(3)
                .afterTime(0, chamber.autoLaunch())
                .waitSeconds(0.45)
                .afterTime(0, chamber.autoCycle())
                .waitSeconds(1)
                .afterTime(0, chamber.autoLaunch())
                .waitSeconds(0.45)
                .afterTime(0, chamber.autoCycle())
                .waitSeconds(1)
                .afterTime(0, chamber.autoLaunch())
                .waitSeconds(0.45)
                .afterTime(0, launcher.autoSpinDown())

                // leave to aline:
                .splineToLinearHeading(new Pose2d(new Vector2d(9, -34), Math.toRadians(270)), Math.toRadians(-90))
//testing in progress

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

