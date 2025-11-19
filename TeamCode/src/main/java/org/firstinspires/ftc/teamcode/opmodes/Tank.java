package org.firstinspires.ftc.teamcode.opmodes;
import com.acmerobotics.roadrunner.Pose2d;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.teamcode.MecanumDrive;
import org.firstinspires.ftc.teamcode.hardware.Chamber;
import org.firstinspires.ftc.teamcode.hardware.Intake;
import org.firstinspires.ftc.teamcode.hardware.Launcher;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.util.ElapsedTime;



// I AM not DOCTOR IVO ROBOTNIK! :(
@TeleOp(name = "Tank", group = "OpModes")
public class Tank extends OpMode {

    // Insert whatever initialization your own code does

    int max;
    DcMotorEx leftFront;
    DcMotorEx leftBack;
    DcMotorEx rightBack;
    DcMotorEx rightFront;

    private Intake intake = new Intake(this);
    private Chamber chamber = new Chamber(this);
    private Launcher launcher = new Launcher(this);

    MecanumDrive drive;

    Pose2d poseEstimate;
    double speed;

    ElapsedTime speedTimer = new ElapsedTime();

    @Override
    public void init() {
        max = 1;

        drive = new MecanumDrive(hardwareMap, new Pose2d(0, 0, 0));
        leftFront = hardwareMap.get(DcMotorEx.class, "front_left_drive");
        leftBack = hardwareMap.get(DcMotorEx.class, "back_left_drive");
        rightBack = hardwareMap.get(DcMotorEx.class, "back_right_drive");
        rightFront = hardwareMap.get(DcMotorEx.class, "front_right_drive");

        intake.init();
        chamber.init();
        launcher.init();
        speed = 1;
    }

    @Override
    public void loop() {
        drive.updatePoseEstimate();
        poseEstimate = drive.localizer.getPose();

        float Lx = -gamepad1.left_stick_y;
        float Rx = -gamepad1.right_stick_y;
        leftFront.setPower(Lx * max * speed);
        leftBack.setPower(Lx * max * speed);
        rightFront.setPower(Rx * max * speed);
        rightBack.setPower(Rx * max * speed);


        if (gamepad1.left_bumper && gamepad1.right_bumper) {
            ;
        } else if (gamepad1.left_bumper) {
            leftFront.setPower(-max);
            leftBack.setPower(max);
            rightFront.setPower(max);
            rightBack.setPower(-max);
        } else if (gamepad1.right_bumper) {
            leftFront.setPower(max);
            leftBack.setPower(-max);
            rightFront.setPower(-max);
            rightBack.setPower(max);
        }

        if (gamepad1.dpad_right && speed < 1 && speedTimer.seconds() > 0.25) {
            speedTimer.reset();
            speed += 0.25;
        } else if (gamepad1.dpad_left && speed > 0.25 && speedTimer.seconds() > 0.25) {
            speedTimer.reset();
            speed -= 0.25;
        }

        launcher.sendPose(drive.localizer.getPose());

        intake.listen();
        chamber.listen();
        launcher.listen();

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
