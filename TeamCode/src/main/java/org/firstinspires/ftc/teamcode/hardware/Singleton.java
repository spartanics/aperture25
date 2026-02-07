package org.firstinspires.ftc.teamcode.hardware;

import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.Vector2d;

import org.firstinspires.ftc.teamcode.MecanumDrive;

public class Singleton {
    public static boolean launchReady1 = false;
    public static boolean launchReady2 = false;
    public static int target = 0;
    public static MecanumDrive drive;
    public static Pose2d storedpose;
}
