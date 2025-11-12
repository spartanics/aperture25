package org.firstinspires.ftc.teamcode.hardware;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

public class Launcher {

    private OpMode myOpMode;

    double rightPower = 0.0;
    double leftPower = 0.0;

    double curvePos = 0.0;

    private ElapsedTime swapCD = new ElapsedTime();

    private DcMotorEx left_launch;
    private DcMotorEx right_launch;

    private Servo curve;

    public Launcher(OpMode opmode) { myOpMode = opmode; }

    public void init() {
        left_launch = myOpMode.hardwareMap.get(DcMotorEx.class, "launchLeft");
        right_launch = myOpMode.hardwareMap.get(DcMotorEx.class, "launchRight");

        left_launch.setDirection(DcMotorSimple.Direction.FORWARD);
        right_launch.setDirection(DcMotorSimple.Direction.REVERSE);

        curve = myOpMode.hardwareMap.get(Servo.class, "curve");
    }

    public void listen() {
        if (myOpMode.gamepad2.right_bumper) {
            rightPower = 1.0;
        } else {
            rightPower = 0.0;
        }

        if (myOpMode.gamepad2.left_bumper) {
            leftPower = 1.0;
        } else {
            leftPower = 0.0;
        }

        if (myOpMode.gamepad2.a) {
            curvePos += 0.1;
        } else if (myOpMode.gamepad2.b) {
            curvePos -= 0.1;
        }

        left_launch.setPower(leftPower);
        right_launch.setPower(rightPower);

        curve.setPosition(curvePos);
    }

    public void sendTelemetry() {
        myOpMode.telemetry.addLine("----LAUNCHER----");
        myOpMode.telemetry.addData("Right Power", "%.2f", rightPower);
        myOpMode.telemetry.addData("Left Power", "%.2f", leftPower);
        myOpMode.telemetry.addData("Curve Position", curvePos);
        myOpMode.telemetry.addLine();
    }
}
