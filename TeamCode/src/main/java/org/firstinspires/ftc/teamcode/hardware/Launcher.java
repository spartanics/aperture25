package org.firstinspires.ftc.teamcode.hardware;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.Pose2d;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.CurrentUnit;

public class Launcher {

    private OpMode myOpMode;

//    ElapsedTime rpmtimer = new ElapsedTime();

    private double ddistance;

    private double adjustedVelocity = 3;
    private double adjustedLinear = HardwareConstants.LAUNCH_LINE;

//    private double storedticks = 0;

//    private double rpm = 0;

    double velocity = 0.0;

    public boolean launchReady = false;

    private DcMotorEx flywheel;

    private Servo linear;

    public Launcher(OpMode opmode) { myOpMode = opmode; }



    public void init() {

        flywheel = myOpMode.hardwareMap.get(DcMotorEx.class, "flywheel");

        linear = myOpMode.hardwareMap.get(Servo.class, "linear");

        flywheel.setDirection(DcMotorSimple.Direction.REVERSE);

        flywheel.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        flywheel.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        flywheel.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
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

//        if (rpmtimer.seconds() > 0.2) {
//            rpm = 60 * ((float) flywheel.getCurrentPosition() - storedticks) / 27 * (rpmtimer.seconds());
//            rpmtimer.reset();
//            storedticks = flywheel.getCurrentPosition();
//        }

        if (myOpMode.gamepad1.dpad_right) {
            adjustedVelocity = HardwareConstants.HIGH_LAUNCH_POWER;
            adjustedLinear = HardwareConstants.LAUNCH_LINE;
        } else if (myOpMode.gamepad1.dpad_down) {
            adjustedVelocity = 2.8;
            adjustedLinear = 0.55;
        } else if (myOpMode.gamepad1.dpad_left) {
            adjustedVelocity = 2.6;
            adjustedLinear = 0.55;
        } else if (myOpMode.gamepad1.dpad_up) {
            adjustedVelocity = 3.2;
            adjustedLinear = HardwareConstants.LAUNCH_LINE;
        }

        linear.setPosition(adjustedLinear);

//        adjusted_power = HardwareConstants.LAUNCH_POWER;

        if (myOpMode.gamepad2.right_bumper) {
            flywheel.setVelocity(adjustedVelocity, AngleUnit.RADIANS);
        } else {
            flywheel.setVelocity(0.0);
        }

        if (myOpMode.gamepad2.right_bumper && flywheel.getVelocity(AngleUnit.RADIANS) > adjustedVelocity - 0.1) {
            launchReady = true;
        } else {
            launchReady = false;
        }
    }

    public void sendTelemetry() {
        myOpMode.telemetry.addLine("----LAUNCHER----");
        myOpMode.telemetry.addData("Power", "%.2f", adjustedVelocity);
        myOpMode.telemetry.addData("Raw Velocity", "%.2f", flywheel.getVelocity(AngleUnit.RADIANS));
//        myOpMode.telemetry.addData("Timer", rpmtimer.seconds());
//        myOpMode.telemetry.addData("RPM", "%.2f", rpm);
        myOpMode.telemetry.addData("Current", "%.2f", flywheel.getCurrent(CurrentUnit.AMPS));
        myOpMode.telemetry.addData("Linear Pos", "%.2f", linear.getPosition());
        myOpMode.telemetry.addLine();
//        myOpMode.telemetry.addData("Delta Distance", "%.2f", ddistance);
    }

    public class AutonSendTelemetry implements Action {

        @Override
        public boolean run(@NonNull TelemetryPacket telemetryPacket) {
            myOpMode.telemetry.addLine("----LAUNCHER----");
            myOpMode.telemetry.addData("Power", "%.2f", adjustedVelocity);
            myOpMode.telemetry.addData("Raw Velocity", "%.2f", flywheel.getVelocity(AngleUnit.RADIANS));
//        myOpMode.telemetry.addData("Timer", rpmtimer.seconds());
//        myOpMode.telemetry.addData("RPM", "%.2f", rpm);
            myOpMode.telemetry.addData("Current", "%.2f", flywheel.getCurrent(CurrentUnit.AMPS));
            myOpMode.telemetry.addData("Linear Pos", "%.2f", linear.getPosition());
            myOpMode.telemetry.addLine();
            return true;
        }
    }

    public Action autoSendTelemetry() {
        return new Launcher.AutonSendTelemetry();
    }

    public class AutonListen implements Action {

        @Override
        public boolean run(@NonNull TelemetryPacket telemetryPacket) {
            flywheel.setVelocity(velocity, AngleUnit.RADIANS);
            return true;
        }
    }

    public Action autoListen() {
        return new Launcher.AutonListen();
    }

    public class AutonBigSpinUp implements Action {

        @Override
        public boolean run(@NonNull TelemetryPacket telemetryPacket) {
            velocity = 0.5;
            return false;
        }
    }

    public Action autoBigSpinUp() {
        return new Launcher.AutonBigSpinUp();
    }

    public class AutonSmallSpinUp implements Action {

        @Override
        public boolean run(@NonNull TelemetryPacket telemetryPacket) {
            velocity = 2.8;
            return false;
        }
    }

    public Action autoSmallSpinUp() {
        return new Launcher.AutonSmallSpinUp();
    }

    public class AutonSpinDown implements Action {

        @Override
        public boolean run(@NonNull TelemetryPacket telemetryPacket) {
            velocity = 0;
            return false;
        }
    }

    public Action autoSpinDown() {
        return new Launcher.AutonSpinDown();
    }
}
