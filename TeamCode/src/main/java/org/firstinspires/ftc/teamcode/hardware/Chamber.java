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

    private DcMotorEx spindex;


    private Servo spin1;
    private Servo spin2;
    private Servo spin3;

    //double flap1 = 0.0;

    public Chamber(OpMode opmode) { myOpMode = opmode; }


    public void init() {
        spindex = myOpMode.hardwareMap.get(DcMotorEx.class, "spindexer");

        //spindex.setDirection(DcMotorSimple.Direction.FORWARD);
        spin1 = myOpMode.hardwareMap.get(Servo.class, "spin1");


        spin2 = myOpMode.hardwareMap.get(Servo.class, "spin2");
        spin3 = myOpMode.hardwareMap.get(Servo.class, "spin3");


    }

    public void listen() {

        spin1.setPosition(0);
        spin2.setPosition(0);
        spin3.setPosition(0);

        if(myOpMode.gamepad2.a){
            //flap1 = 0.9;
            spin2.setPosition(0.7);
        }else if (myOpMode.gamepad2.y) {
            //flap1 = 0.1;
            spin2.setPosition(0.0);
        }
        //power = -myOpMode.gamepad2.right_stick_y;
        //spindex.setPower(power);


    }

    public void sendTelemetry() {
        myOpMode.telemetry.addLine("----CHAMBER----");
        myOpMode.telemetry.addData("Lift Power", "%.2f", power);
        //myOpMode.telemetry.addLine("----flappy----");
        //myOpMode.telemetry.addData("Flap", "%.2f", spin1);
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
