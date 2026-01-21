package org.firstinspires.ftc.teamcode.hardware;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.arcrobotics.ftclib.controller.PIDFController;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

public class Chamber {


    private OpMode myOpMode;

    double power = 0;

    private ElapsedTime swapCD = new ElapsedTime();

    private DcMotorEx spindex;

    PIDFController spinPidf = new PIDFController(HardwareConstants.kP, HardwareConstants.kI, HardwareConstants.kD, HardwareConstants.kF);

    private Servo spin1;
    private Servo spin2;
    private Servo spin3;

    private Servo rgb;

    double originalPos;
    double rotAmount;
    double targetPos;

    public boolean launchReady = false;
    public boolean intakeRun = false;

    double deltaTicks = 0.0;
    double deltaTarget = 0.0;

    boolean stillOffset = false;

    private int spinPos;
    double target = 0;

    public Chamber(OpMode opmode) { myOpMode = opmode; }

    public double spinRadiansToTicks(double rad) {
        return rad * (537.6 / (Math.PI * 2));
    }

    public void init() {
        spindex = myOpMode.hardwareMap.get(DcMotorEx.class, "spindexer");
        spindex.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        spindex.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        //spindex.setDirection(DcMotorSimple.Direction.FORWARD);
        spin1 = myOpMode.hardwareMap.get(Servo.class, "spin1");

        spin2 = myOpMode.hardwareMap.get(Servo.class, "spin2");

        spin3 = myOpMode.hardwareMap.get(Servo.class, "spin3");

        rgb = myOpMode.hardwareMap.get(Servo.class, "rgb");
    }

    public void listen() {

//        if (myOpMode.gamepad2.x) {
//            target = 0;
//        } else if (myOpMode.gamepad2.a) {
//            target = 1;
//        } else if (myOpMode.gamepad2.b) {
//            target = 2;
//        }

        originalPos = spinRadiansToTicks((target * 2 * Math.PI) / 3);

        if (myOpMode.gamepad2.x && swapCD.seconds() > 0.4) {
            targetPos += spinRadiansToTicks((2 * Math.PI) / 3);

            target++;
            target %= 3;

            swapCD.reset();
        }

        if (myOpMode.gamepad2.a && swapCD.seconds() > 0.4) {
            targetPos += spinRadiansToTicks((4 * Math.PI) / 3);

            target += 2;
            target %= 3;

            swapCD.reset();
        }

        rotAmount = Math.floor(spindex.getCurrentPosition() / spinRadiansToTicks(2 * Math.PI));
//
//        if (myOpMode.gamepad2.x || myOpMode.gamepad2.a || myOpMode.gamepad2.b) {
//            if (rotAmount != 0 ) {
//                targetPos += originalPos;
//            }
//            else {
//                targetPos = originalPos;
//            }
//        }
//
//        targetPos += myOpMode.gamepad2.right_stick_y * 5;


        if (myOpMode.gamepad2.back) {
            spindex.setPower(-myOpMode.gamepad2.right_stick_y / 10);
            deltaTarget = targetPos + (spindex.getCurrentPosition() - deltaTicks);
        } else {
            spindex.setPower(spinPidf.calculate(spindex.getCurrentPosition(), targetPos));
            deltaTicks = spindex.getCurrentPosition();
            if (deltaTarget != 0) {
                targetPos = deltaTarget;
                deltaTarget = 0;
            }
        }

        if (myOpMode.gamepad2.y || launchReady) {
            swapCD.reset();
            if (target == 0) {
                spin3.setPosition(0.3);
            } else if (target == 1) {
                spin2.setPosition(0.3);
            } else if (target == 2) {
                spin1.setPosition(0.3);
            }
        }

        if (swapCD.seconds() > 0.35) {
            spin1.setPosition(0);
            spin2.setPosition(0);
            spin3.setPosition(0);
        }

        if (intakeRun) {
            rgb.setPosition(1);
        } else {
            rgb.setPosition(0);
        }

        spinPidf.setPIDF(HardwareConstants.kP, HardwareConstants.kI, HardwareConstants.kD, HardwareConstants.kF);

//        double output = spinPidf.calculate(spindex.getCurrentPosition(), );

//        spindex.setPower(output);
    }

    public void sendTelemetry() {
        myOpMode.telemetry.addLine("----CHAMBER----");
        myOpMode.telemetry.addData("Power", "%.2f", power);
        myOpMode.telemetry.addData("Spindex Position", "%d", spindex.getCurrentPosition());
//        myOpMode.telemetry.addData("Orig. Calc", "%.2f", originalPos);
//        myOpMode.telemetry.addData("Amount Rotations", "%.2f", rotAmount);
        myOpMode.telemetry.addData("Target Point", "%.2f", targetPos);
        myOpMode.telemetry.addData("Target", "%.2f", target);
        myOpMode.telemetry.addData("Delta Target", "%.2f", deltaTarget);
        myOpMode.telemetry.addLine();
    }



    public class AutonListen implements Action {

        @Override
        public boolean run(@NonNull TelemetryPacket telemetryPacket) {
            spindex.setPower(spinPidf.calculate(spindex.getCurrentPosition(), targetPos));

            if (swapCD.seconds() > 0.35) {
                spin1.setPosition(0);
                spin2.setPosition(0);
                spin3.setPosition(0);
            }
            return true;
        }
    }

    public Action autoListen() {
        return new Chamber.AutonListen();
    }

    public class AutonCycle implements Action {

        @Override
        public boolean run(@NonNull TelemetryPacket telemetryPacket) {
            if (target >= 2) {
                target = 0;
            } else {
                target++;
            }
            targetPos += spinRadiansToTicks((2 * Math.PI) / 3);
            return false;
        }
    }

    public Action autoCycle() {
        return new Chamber.AutonCycle();
    }

    public class AutonCycleTwice implements Action {

        @Override
        public boolean run(@NonNull TelemetryPacket telemetryPacket) {
            if (target == 1) {
                target = 0;
            } else if (target == 2) {
                target = 1;
            }  else {
                target = 2;
            }
            targetPos += spinRadiansToTicks((4 * Math.PI) / 3);
            return false;
        }
    }

    public Action autoCycleTwice() {
        return new Chamber.AutonCycleTwice();
    }

    public class AutonLaunch implements Action {

        @Override
        public boolean run(@NonNull TelemetryPacket telemetryPacket) {
            swapCD.reset();
            if (target == 0) {
                spin3.setPosition(0.4);
            } else if (target == 1) {
                spin2.setPosition(0.4);
            } else if (target == 2) {
                spin1.setPosition(0.4);
            }
            return false;
        }
    }

    public Action autoLaunch() {
        return new Chamber.AutonLaunch();
    }
//hello
}
