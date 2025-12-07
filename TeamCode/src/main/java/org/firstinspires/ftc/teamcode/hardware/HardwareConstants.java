package org.firstinspires.ftc.teamcode.hardware;

import com.acmerobotics.dashboard.config.Config;

@Config
public class HardwareConstants {
    public static double LAUNCH_POWER = 0.8;

    // SPINDEXER PIDF
    public static double kP = 0.006;
    public static double kI = 0.0003;
    public static double kD = 0.0003;
    public static double kF = 0;

    // TUNING/TESTING ONLY
    public static double testSetpoint = 0;
}
