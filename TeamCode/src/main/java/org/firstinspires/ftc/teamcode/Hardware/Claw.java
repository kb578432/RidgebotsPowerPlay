package org.firstinspires.ftc.teamcode.Hardware;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.DeviceNames;

@Config
public class Claw {
    public static Servo graspServo;
    public static OpMode opMode;
    public static int OPEN_POSITION = 1, CLOSED_POSITION = 0;

    public static void init(OpMode opMode) {
        Claw.opMode = opMode;
        HardwareMap hardwareMap = opMode.hardwareMap;

        graspServo = hardwareMap.get(Servo.class, DeviceNames.GRASP_SERVO);
    }

    public static void grab() {
        graspServo.setPosition(CLOSED_POSITION);
    }

    public static void release() {
        graspServo.setPosition(OPEN_POSITION);
    }

    public static void addTelemetryData() {
        Telemetry telemetry = opMode.telemetry;
        telemetry.addData("Claw.graspServo.position", graspServo.getPosition());
    }
}
