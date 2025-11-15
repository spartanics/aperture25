package org.firstinspires.ftc.teamcode.hardware;

import android.drm.DrmStore;

import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.Rotation2d;
import com.acmerobotics.roadrunner.Vector2d;
import com.arcrobotics.ftclib.kotlin.extensions.geometry.Vector2dExtKt;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

public class Launcher {

    private OpMode myOpMode;

    private Pose2d pose;

    private double ddistance;

    private double adjusted_power;

    double leftPower = 0.0;

    double rightPower = 0.0;

    private ElapsedTime swapCD = new ElapsedTime();

    private DcMotorEx left_launch;
    private DcMotorEx right_launch;

    public Launcher(OpMode opmode) { myOpMode = opmode; }

    public void init() {
        left_launch = myOpMode.hardwareMap.get(DcMotorEx.class, "launchLeft");
        right_launch = myOpMode.hardwareMap.get(DcMotorEx.class, "launchRight");

        left_launch.setDirection(DcMotorSimple.Direction.FORWARD);
        right_launch.setDirection(DcMotorSimple.Direction.REVERSE);
    }

    public void sendPose(Pose2d currPose) {
        pose = currPose;
    }

    public void listen() {
        double dx = pose.position.x - (-51.3);
        double dy = pose.position.y - (52.3);

        ddistance = (Math.sqrt(Math.pow(dx, 2) + Math.pow(dy, 2)));

//        adjusted_power = -0.897519 + 0.350997 * Math.log(ddistance);

        if (myOpMode.gamepad1.right_trigger > 0.2) {
            adjusted_power = 0.67;
        } else if (myOpMode.gamepad1.left_trigger > 0.2) {
            adjusted_power = 0.8;
        }

        if (myOpMode.gamepad2.right_bumper) {
            rightPower = adjusted_power;
        } else {
            rightPower = 0.0;
        }

        if (myOpMode.gamepad2.left_bumper) {
            leftPower = adjusted_power;
        } else {
            leftPower = 0.0;
        }

        left_launch.setPower(leftPower);
        right_launch.setPower(rightPower);

    }

    public void sendTelemetry() {
        myOpMode.telemetry.addLine("----LAUNCHER----");
        myOpMode.telemetry.addData("Power", "%.2f", adjusted_power);
        myOpMode.telemetry.addData("Delta Distance", "%.2f", ddistance);
        myOpMode.telemetry.addLine();
    }
}
