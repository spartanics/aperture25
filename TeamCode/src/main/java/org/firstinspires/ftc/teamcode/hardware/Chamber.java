package org.firstinspires.ftc.teamcode.hardware;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.arcrobotics.ftclib.controller.PIDFController;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
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

    private int spinPos;
    double target = 0;

    public Chamber(OpMode opmode) { myOpMode = opmode; }

    public double spinRadiansToTicks(double rad) {
        return rad * (534 / (Math.PI * 2));
    }

    public void init() {
        spindex = myOpMode.hardwareMap.get(DcMotorEx.class, "spindexer");
        spindex.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        spindex.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        //spindex.setDirection(DcMotorSimple.Direction.FORWARD);
        spin1 = myOpMode.hardwareMap.get(Servo.class, "spin1");

        spin2 = myOpMode.hardwareMap.get(Servo.class, "spin2");

        spin3 = myOpMode.hardwareMap.get(Servo.class, "spin3");


    }

    public void listen() {

        if (myOpMode.gamepad2.x) {
            target = 0;
        } else if (myOpMode.gamepad2.a) {
            target = 1;
        } else if (myOpMode.gamepad2.b) {
            target = 2;
        }

        spindex.setPower(spinPidf.calculate(spindex.getCurrentPosition(), spinRadiansToTicks((target * 2 * Math.PI) / 3)));

        if (myOpMode.gamepad2.y) {
            swapCD.reset();
            switch ((int) target) {
                case 0:
                    spin1.setPosition(0.5);
                case 1:
                    spin3.setPosition(0.5);
                case 2:
                    spin2.setPosition(0.5);
            }
        }

        if (myOpMode.gamepad2.dpad_up) {
            spin1.setPosition(0);
            spin2.setPosition(0);
            spin3.setPosition(0);
        }

        spinPidf.setPIDF(HardwareConstants.kP, HardwareConstants.kI, HardwareConstants.kD, HardwareConstants.kF);

//        double output = spinPidf.calculate(spindex.getCurrentPosition(), );

//        spindex.setPower(output);
    }

    public void sendTelemetry() {
        myOpMode.telemetry.addLine("----CHAMBER----");
        myOpMode.telemetry.addData("Lift Power", "%.2f", power);
        myOpMode.telemetry.addData("Spindex Position", "%d", spindex.getCurrentPosition());
        myOpMode.telemetry.addData("Target Point", "%.2f", spinRadiansToTicks((target * 2 * Math.PI) / 3));
        myOpMode.telemetry.addLine();
    }

    public class AutonChamberUp implements Action {

        @Override
        public boolean run(@NonNull TelemetryPacket telemetryPacket) {
            //left_lift.setPower(1);
            //right_lift.setPower(1);
            return false;
        }
    }

    public Action autoChamberUp() {
        return new Chamber.AutonChamberUp();
    }

    public class AutonChamberDown implements Action {

        @Override
        public boolean run(@NonNull TelemetryPacket telemetryPacket) {
            //left_lift.setPower(0);
            //right_lift.setPower(0);
            return false;
        }
    }

    public Action autoChamberDown() {
        return new Chamber.AutonChamberDown();
    }
}
