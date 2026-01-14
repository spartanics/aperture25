package org.firstinspires.ftc.teamcode.hardware;

import com.acmerobotics.dashboard.config.Config;

@Config
public class HardwareConstants {
    public static double HIGH_LAUNCH_POWER = 1;
    public static double LOW_LAUNCH_POWER = 0.9;
    public static double LAUNCH_LINE = 0.6;

    // SPINDEXER PIDF
    public static double kP = 0.006;
    public static double kI = 0.0003;
    public static double kD = 0.0003;
    public static double kF = 0;

    // TUNING/TESTING ONLY
    public static double testSetpoint = 0;
}