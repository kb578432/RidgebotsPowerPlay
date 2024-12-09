package org.firstinspires.ftc.teamcode.Test;


import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.Hardware.IMU;

@Autonomous
public class TestIMU extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        IMU.init(this);

        waitForStart();

        while (opModeIsActive()) {
            IMU.addTelemetryData();
            telemetry.update();
        }
    }
}

