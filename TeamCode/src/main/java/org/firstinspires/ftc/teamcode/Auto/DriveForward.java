package org.firstinspires.ftc.teamcode.Auto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.Main;
import org.firstinspires.ftc.teamcode.Hardware.DriveModule;

@Autonomous
public class DriveForward extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        Main.initAll(this);
        waitForStart();
        DriveModule.resetMotors();
        while (DriveModule.fl.getCurrentPosition() < 718.65) { // 718 is temporary to remove toTicks as a source for error. 718 ticks is about 23 inches
            DriveModule.drive(0, 0.5, 0);
        }
    }
}