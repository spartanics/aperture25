package org.firstinspires.ftc.teamcode.hardware;

import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class Intake {

    private OpMode myOpMode;

    double left_trigger;
    double right_trigger;
    double power;

    private DcMotorEx intake;

    public Intake(OpMode opmode) { myOpMode = opmode; }

    public void init() {
        intake = myOpMode.hardwareMap.get(DcMotorEx.class, "intake");
    }

    //done done done
    //done done done

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

        left_trigger = myOpMode.gamepad2.left_trigger;
        right_trigger = myOpMode.gamepad2.right_trigger;

        power = -left_trigger + right_trigger;

        intake.setPower(power);
    }

    public void sendTelemetry() {
        myOpMode.telemetry.addLine("----INTAKE----");
        myOpMode.telemetry.addData("Power", "%.2f", power);
        myOpMode.telemetry.addLine();
    }
}
