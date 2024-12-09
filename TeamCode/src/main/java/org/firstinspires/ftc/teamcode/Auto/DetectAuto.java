package org.firstinspires.ftc.teamcode.Auto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.Hardware.ColorSensor4262;
import org.firstinspires.ftc.teamcode.DeviceNames;
import org.firstinspires.ftc.teamcode.Hardware.Claw;
import org.firstinspires.ftc.teamcode.Hardware.IMU;
import org.firstinspires.ftc.teamcode.Hardware.Lift;
import org.firstinspires.ftc.teamcode.Main;
import org.firstinspires.ftc.teamcode.Hardware.DriveModule;
import org.firstinspires.ftc.teamcode.TeleOp.PlanC;

@Autonomous
public class DetectAuto extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        Main.initAll(this);
        IMU.init(this);

        DcMotor jointMotor = hardwareMap.get(DcMotor.class, DeviceNames.PLAN_C2);
        jointMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        jointMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        jointMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        waitForStart();

        Claw.grab();
        sleep(1000);
        jointMotor.setPower(0.3);
        Lift.runToTop();

        // Wait for the arm to come up.
        sleep(1000);

        DriveModule.driveForward(21);

        sleep(1000);
        String maxColor = ColorSensor4262.getMaxColor();
        telemetry.addData("Max Color", maxColor);
        telemetry.update();

        sleep(1000);
        DriveModule.driveForward(13);

        if ("RED".equals(maxColor)) {
            DriveModule.driveLeft(20);
        } else if ("BLUE".equals(maxColor)) {
            DriveModule.driveRight(20);
        }
        while (Lift.motor.isBusy()) {
            idle();
        }
    }
}
