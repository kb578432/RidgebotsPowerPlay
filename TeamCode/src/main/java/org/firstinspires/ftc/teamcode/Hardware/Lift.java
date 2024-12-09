package org.firstinspires.ftc.teamcode.Hardware;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.DeviceNames;

@Config
public class Lift {
    public static int BOTTOM_TICKS = 0, TOP_TICKS = -3650;
    public static double SPEED = 0.85;
    public static OpMode opMode;
    public static DcMotor motor;

    public static void init(OpMode opMode) {
        Lift.opMode = opMode;

        motor = opMode.hardwareMap.get(DcMotor.class, DeviceNames.LIFT);
        motor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        motor.setDirection(DcMotor.Direction.REVERSE);
    }

    public static void runToBottom() {
        motor.setTargetPosition(BOTTOM_TICKS);
        motor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motor.setPower(SPEED);
    }

    public static void runToTop() {
        motor.setTargetPosition(TOP_TICKS);
        motor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motor.setPower(SPEED);
    }

    public static void addTelemetryData() {
        Telemetry telemetry = opMode.telemetry;
        telemetry.addData("Lift Motor Ticks", motor.getCurrentPosition());
        telemetry.addData("Lift Motor Power", motor.getPower());
    }
}
