package org.firstinspires.ftc.teamcode.opmodes;
import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.PoseVelocity2d;
import com.acmerobotics.roadrunner.Vector2d;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.teamcode.MecanumDrive;
import org.firstinspires.ftc.teamcode.hardware.Chamber;
import org.firstinspires.ftc.teamcode.hardware.Intake;
import org.firstinspires.ftc.teamcode.hardware.Launcher;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;


// I AM DOCTOR IVO ROBOTNIK! >:D
@TeleOp(name = "Headless", group = "OpModes")
public class Headless extends OpMode {

    // Insert whatever initialization your own code does
    private Intake intake = new Intake(this);
    private Chamber chamber = new Chamber(this);
    private Launcher launcher = new Launcher(this);

    MecanumDrive drive;

    boolean pressed_a = false;

    // Read pose
    Pose2d poseEstimate;
    Vector2d input;
    double headlessHeading;

    private void gamepadToMovement() {
        float xDir = -gamepad1.left_stick_x;
        float yDir = -gamepad1.left_stick_y;


        double transXDir = xDir * Math.cos(drive.localizer.getPose().heading.toDouble() - headlessHeading) - yDir * Math.sin(drive.localizer.getPose().heading.toDouble() - headlessHeading);
        double transYDir = xDir * Math.sin(drive.localizer.getPose().heading.toDouble() - headlessHeading) + yDir * Math.cos(drive.localizer.getPose().heading.toDouble() - headlessHeading);

        input = new Vector2d(
                transYDir,
                transXDir
        );
    }

// Pass in the rotated input + right stick value for rotation
// Rotation is not part of the rotated input thus must be passed in separately



    @Override
    public void init() {
        telemetry = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());

        intake.init();
        chamber.init();
        launcher.init();


        drive = new MecanumDrive(hardwareMap, new Pose2d(60, 60, Math.toRadians(270)));
        headlessHeading = 0;

        pressed_a = false;
    }

    @Override
    public void loop() {
        drive.updatePoseEstimate();
        poseEstimate = drive.localizer.getPose();


        if (gamepad1.right_bumper) {
            headlessHeading = drive.localizer.getPose().heading.toDouble();
        }


        gamepadToMovement();
        if (!gamepad1.left_bumper) {
            drive.setDrivePowers(
                    new PoseVelocity2d(
                            input, -gamepad1.right_stick_x
                    )
            );
        } else {
            if (!gamepad1.dpad_up && !gamepad1.dpad_down && !gamepad1.dpad_left && !gamepad1.dpad_right) {
                drive.setDrivePowers(
                        new PoseVelocity2d(
                                new Vector2d(0, 0), 0
                        )
                );
            } else if(gamepad1.dpad_up) {
                drive.setDrivePowers(
                        new PoseVelocity2d(
                                new Vector2d(0.5, 0), 0
                        )
                );
            } else if(gamepad1.dpad_down) {
                drive.setDrivePowers(
                        new PoseVelocity2d(
                                new Vector2d(-0.5, 0), 0
                        )
                );
            } else if(gamepad1.dpad_left) {
                drive.setDrivePowers(
                        new PoseVelocity2d(
                                new Vector2d(0, 0.5), 0
                        )
                );
            } else if(gamepad1.dpad_right) {
                drive.setDrivePowers(
                        new PoseVelocity2d(
                                new Vector2d(0, -0.5), 0
                        )
                );
            }

        }

        chamber.launchReady = launcher.launchReady;
        chamber.intakeRun = intake.intakeRun;

        intake.listen();
        chamber.listen();
        launcher.listen(drive.localizer.getPose());

        intake.sendTelemetry();
        chamber.sendTelemetry();
        launcher.sendTelemetry();

        updateTelemetry(telemetry);

    }

    @Override
    public void init_loop(){}
    @Override
    public void start(){    }
    @Override
    public void stop(){}

}
