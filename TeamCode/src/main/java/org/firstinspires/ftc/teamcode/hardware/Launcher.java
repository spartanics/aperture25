package org.firstinspires.ftc.teamcode.hardware;

import android.drm.DrmStore;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
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

    private double ddistance;

    private double adjusted_power;

    double power = 0.0;

    private DcMotorEx flywheel;

    public Launcher(OpMode opmode) { myOpMode = opmode; }

    public void init() {
        flywheel = myOpMode.hardwareMap.get(DcMotorEx.class, "flywheel");

        flywheel.setDirection(DcMotorSimple.Direction.FORWARD);
    }

    public void listen(Pose2d pose) {
        double dx = pose.position.x - (-51.3);
        double dy = pose.position.y - (52.3);

        ddistance = (Math.sqrt(Math.pow(dx, 2) + Math.pow(dy, 2)));

//        adjusted_power = -0.897519 + 0.350997 * Math.log(ddistance);

        if (myOpMode.gamepad1.right_trigger > 0.2) {
            adjusted_power = 0.5;
        } else if (myOpMode.gamepad1.left_trigger > 0.2) {
            adjusted_power = 0.6;
        } else {
            adjusted_power = 0.7;
        }

        if (myOpMode.gamepad2.right_bumper) {
            power = adjusted_power;
        } else {
            power = 0.0;
        }

        flywheel.setPower(power);
    }

    public void sendTelemetry() {
        myOpMode.telemetry.addLine("----LAUNCHER----");
        myOpMode.telemetry.addData("Power", "%.2f", adjusted_power);
        myOpMode.telemetry.addData("Delta Distance", "%.2f", ddistance);
        myOpMode.telemetry.addLine();
    }

    public class AutonListen implements  Action {

        @Override
        public boolean run(@NonNull TelemetryPacket telemetryPacket) {
            left_launch.setPower(leftPower);
            right_launch.setPower(rightPower);
            return true;
        }
    }

    public Action autonListen() {
        return new Launcher.AutonListen();
    }

    public class AutonSpinUp implements Action {

        @Override
        public boolean run(@NonNull TelemetryPacket telemetryPacket) {
            leftPower = 0.8;
            rightPower = 0.8;
            return false;
        }
    }

    public Action autonSpinUp() {
        return new Launcher.AutonSpinUp();
    }

    public class AutonSpinDown implements Action {

        @Override
        public boolean run(@NonNull TelemetryPacket telemetryPacket) {
            leftPower = 0;
            rightPower = 0;
            return false;
        }
    }

    public Action autonSpinDown() {
        return new Launcher.AutonSpinDown();
    }
}
