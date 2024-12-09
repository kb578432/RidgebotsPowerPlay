package org.firstinspires.ftc.teamcode.Test;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Hardware.DriveModule;
import org.firstinspires.ftc.teamcode.Hardware.IMU;

@Config
@Autonomous(group = "Test")
public class TestDrive extends LinearOpMode {
    public static double DISTANCE = 12;

    @Override
    public void runOpMode() throws InterruptedException {
        DriveModule.init(this);
        IMU.init(this);

        waitForStart();

        while (opModeIsActive()) {
            DriveModule.addTelemetryData();
            IMU.addTelemetryData();
            telemetry.update();

            if (gamepad1.dpad_down) {
                DriveModule.driveBackwards(DISTANCE);
            } else if (gamepad1.dpad_up) {
                DriveModule.driveForward(DISTANCE);
            } else if (gamepad1.dpad_left) {
                DriveModule.driveLeft(DISTANCE);
            } else if (gamepad1.dpad_right) {
                DriveModule.driveRight(DISTANCE);
            } else if (gamepad1.left_bumper) {
                DriveModule.turnBy(-90);
            } else if (gamepad1.right_bumper) {
                DriveModule.turnBy(90);
            }
        }
    }
}
