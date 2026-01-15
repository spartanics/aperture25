package org.firstinspires.ftc.teamcode.hardware;

import android.drm.DrmStore;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
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

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.CurrentUnit;
import org.firstinspires.ftc.robotcore.internal.opmode.TelemetryImpl;

public class Launcher {

    private OpMode myOpMode;

    private double ddistance;

    private double adjusted_power = 0.7;

    double power = 0.0;

    private DcMotorEx flywheel;

    private Servo linear;

    public Launcher(OpMode opmode) { myOpMode = opmode; }



    public void init() {
        flywheel = myOpMode.hardwareMap.get(DcMotorEx.class, "flywheel");

        linear = myOpMode.hardwareMap.get(Servo.class, "linear");

        flywheel.setDirection(DcMotorSimple.Direction.REVERSE);

        flywheel.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        flywheel.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }

    //done done done
    //done done done

    public void listen(Pose2d pose) {
//        double dx = (-51.3) - pose.position.x;
//        double dy = (52.3) - pose.position.y;
//
//        ddistance = (Math.sqrt(Math.pow(dx, 2) + Math.pow(dy, 2)));

//        adjusted_power = -0.897519 + 0.350997 * Math.log(ddistance);

//        if (myOpMode.gamepad1.right_trigger > 0.2) {
//            adjusted_power = 0.5;
//        } else if (myOpMode.gamepad1.left_trigger > 0.2) {
//            adjusted_power = 0.6;
//        } else {
//            adjusted_power = 0.7;
//        }
//

        if (myOpMode.gamepad1.dpad_up) {
            adjusted_power = HardwareConstants.HIGH_LAUNCH_POWER;
        } else if (myOpMode.gamepad1.dpad_down) {
            adjusted_power = HardwareConstants.LOW_LAUNCH_POWER;
        } else if (myOpMode.gamepad1.dpad_left) {
            adjusted_power = 0.85;
        } else if (myOpMode.gamepad1.dpad_right) {
            adjusted_power = 0.95;
        }

        linear.setPosition(HardwareConstants.LAUNCH_LINE);

        adjusted_power = HardwareConstants.HIGH_LAUNCH_POWER;

//        adjusted_power = HardwareConstants.LAUNCH_POWER;

        if (myOpMode.gamepad2.right_bumper) {
            flywheel.setPower(adjusted_power);
        } else {
            flywheel.setPower(0.0);
        }
    }

    public void sendTelemetry() {
        myOpMode.telemetry.addLine("----LAUNCHER----");
        myOpMode.telemetry.addData("Power", "%.2f", adjusted_power);
        myOpMode.telemetry.addData("Current", "%.2f", flywheel.getCurrent(CurrentUnit.AMPS));
        myOpMode.telemetry.addData("Linear Pos", "%.2f", linear.getPosition());
        myOpMode.telemetry.addLine();
//        myOpMode.telemetry.addData("Delta Distance", "%.2f", ddistance);
    }

    public class AutonListen implements  Action {

        @Override
        public boolean run(@NonNull TelemetryPacket telemetryPacket) {
            flywheel.setPower(power);
            return true;
        }
    }

    public Action autonListen() {
        return new Launcher.AutonListen();
    }

    public class AutonSpinUp implements Action {

        @Override
        public boolean run(@NonNull TelemetryPacket telemetryPacket) {
            flywheel.setPower(0.6);
            return false;
        }
    }

    public Action autonSpinUp() {
        return new Launcher.AutonSpinUp();
    }

    public class AutonSpinDown implements Action {

        @Override
        public boolean run(@NonNull TelemetryPacket telemetryPacket) {
            flywheel.setPower(0);
            return false;
        }
    }

    public Action autonSpinDown() {
        return new Launcher.AutonSpinDown();
    }
}
