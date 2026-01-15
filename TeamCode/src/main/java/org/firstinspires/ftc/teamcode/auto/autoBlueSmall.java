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
@Autonomous(name = "Auto Blue Base", group = "Autonomous")
public class autoBlueSmall extends LinearOpMode {
    Pose2d startPose;
    MecanumDrive drive;

    Chamber chamber = new Chamber(this);
    Launcher launcher = new Launcher(this);
    Intake intake = new Intake(this);

//this auton cannot scan and sort, these features need to be implemented before a auton is complete

    @Override
    public void runOpMode() throws InterruptedException {
        startPose = new Pose2d(58, -12, Math.toRadians(20));
        drive = new MecanumDrive(hardwareMap, startPose);
        TrajectoryActionBuilder build = drive.actionBuilder(startPose)
                // scan
                // launch & sort mechs
                .afterTime(0.5, launcher.autoSmallSpinUp())
                .afterTime(1, chamber.autoLaunch())
                .afterTime(0.2, launcher.autoSpinDown())
                .splineToLinearHeading(new Pose2d(new Vector2d(36, -27), Math.toRadians(270)), Math.toRadians(-90))
                // intake mechs
                .afterTime(0, intake.autoIntakeStart())
                .splineToConstantHeading(new Pose2d(new Vector2d(36, -58), Math.toRadians(180)).component1(), Math.toRadians(-90))
                .afterTime(0, intake.autoIntakeStop())
                // sorting mechs
                .setReversed(true)
                .splineToLinearHeading(new Pose2d(new Vector2d(58, -7.5), Math.toRadians(30)), Math.toRadians(60))
                // launch mech
                .afterTime(0.5, launcher.autoSmallSpinUp())
                .afterTime(1, chamber.autoLaunch())
                .afterTime(0.2, launcher.autoSpinDown())
                .setReversed(true)
                .splineToLinearHeading(new Pose2d(new Vector2d(11, -27), Math.toRadians(270)), Math.toRadians(-90))
//                intake mech
                .afterTime(0, intake.autoIntakeStart())
                .splineToConstantHeading(new Pose2d(new Vector2d(11, -58), Math.toRadians(180)).component1(), Math.toRadians(-90))
                .afterTime(0, intake.autoIntakeStop())
//                sorting mech
                .setReversed(true)
                .splineToLinearHeading(new Pose2d(new Vector2d(58, -7.5), Math.toRadians(30)), Math.toRadians(60))
                //launching mech
                .afterTime(0.5, launcher.autoSmallSpinUp())
                .afterTime(1, chamber.autoLaunch())
                .afterTime(0.2, launcher.autoSpinDown())
                .setReversed(true)
                .splineToLinearHeading(new Pose2d(new Vector2d(-11, -30), Math.toRadians(270)), Math.toRadians(-90))
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

    }
}

