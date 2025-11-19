package org.firstinspires.ftc.teamcode.hardware;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

public class Chamber {

    private OpMode myOpMode;

    double power = 0;

    private ElapsedTime swapCD = new ElapsedTime();

    private ElapsedTime dropCD = new ElapsedTime();

    private CRServo left_lift;
    private CRServo right_lift;

    private Servo swap;
    private CRServo drop;

    public Chamber(OpMode opmode) { myOpMode = opmode; }

    boolean swapDirection = false;

    public void init() {
        left_lift = myOpMode.hardwareMap.get(CRServo.class, "liftLeft");
        right_lift = myOpMode.hardwareMap.get(CRServo.class, "liftRight");

        left_lift.setDirection(DcMotorSimple.Direction.FORWARD);
        right_lift.setDirection(DcMotorSimple.Direction.REVERSE);

        swap = myOpMode.hardwareMap.get(Servo.class, "swap");
        drop = myOpMode.hardwareMap.get(CRServo.class, "drop");
    }

    public void listen() {
//        if (myOpMode.gamepad2.left_bumper) {
//            left_bumper = 1.0;
//        } else {
//            left_bumper = 0.0;
//        }
//        if (myOpMode.gamepad2.right_bumper) {
//            right_bumper = 1.0;
//        } else {
//            right_bumper = 0.0;
//        }

        power = -myOpMode.gamepad2.right_stick_y;
        if (myOpMode.gamepad2.y && swapCD.seconds() > 0.2 && swapDirection) {
            swap.setPosition(0);
            swapDirection = !swapDirection;
            swapCD.reset();
        } else if (myOpMode.gamepad2.y && swapCD.seconds() > 0.2 && !swapDirection) {
            swap.setPosition(1);
            swapDirection = !swapDirection;
            swapCD.reset();
        }
        left_lift.setPower(power);
        right_lift.setPower(power);

        if (myOpMode.gamepad2.b) {
            drop.setPower(1);
        } else if (myOpMode.gamepad2.a) {
            drop.setPower(-1);
        } else {
            drop.setPower(0);
        }
    }

    public void sendTelemetry() {
        myOpMode.telemetry.addLine("----CHAMBER----");
        myOpMode.telemetry.addData("Lift Power", "%.2f", power);
        myOpMode.telemetry.addData("Swap Direction", swapDirection);
        myOpMode.telemetry.addLine();
    }

    public class AutonChamberUp implements Action {

        @Override
        public boolean run(@NonNull TelemetryPacket telemetryPacket) {
            left_lift.setPower(1);
            right_lift.setPower(1);
            return false;
        }
    }

    public Action autoChamberUp() {
        return new Chamber.AutonChamberUp();
    }

    public class AutonChamberDown implements Action {

        @Override
        public boolean run(@NonNull TelemetryPacket telemetryPacket) {
            left_lift.setPower(0);
            right_lift.setPower(0);
            return false;
        }
    }

    public Action autoChamberDown() {
        return new Chamber.AutonChamberDown();
    }
}
