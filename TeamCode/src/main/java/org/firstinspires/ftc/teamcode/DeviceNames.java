package org.firstinspires.ftc.teamcode;

import com.acmerobotics.dashboard.config.Config;

/**
 * Device names assigned in the driver station.
 * Used during variable initialization.
 * <p>
 * The reason for this class is to have all device names in one location.
 * If we ever need to change device names we are able to do so easily.
 * <p>
 * The naming convention for device names will be UpperCamelCase
 */
@Config
public class DeviceNames {
    public static final String[] JOINT_MOTORS = {"JointMotor0", "JointMotor1"};
    public static final String JOINT_SERVO = "PitchServo";
    public static final String GRASP_SERVO = "GraspServo";
    public static final String YAW_SERVO = "YawServo";
    public static final String COLOR_SENSOR = "ColorSensor";
    public static final String FL = "FL";
    public static final String FR = "FR";
    public static final String BL = "BL";
    public static final String BR = "BR";
    public static final String PLAN_C1 = "PlanC1";
    public static final String PLAN_C2 = "PlanC2";
    public static final String LIFT = "Lift";
}
