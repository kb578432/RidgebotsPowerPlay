package org.firstinspires.ftc.teamcode.Test;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.Hardware.Claw;
import org.firstinspires.ftc.teamcode.Hardware.ColorSensor4262;
import org.firstinspires.ftc.teamcode.Input;
import org.firstinspires.ftc.teamcode.Main;
import org.firstinspires.ftc.teamcode.Hardware.DriveModule;
import org.firstinspires.ftc.teamcode.Hardware.Arm;

@TeleOp(group = "Test")
public class TestOp extends OpMode {
    @Override
    public void init() {
        Main.initAll(this);
    }

    @Override
    public void loop() {
        Input.handleDrive(gamepad1);
        Input.handleArmHeightManually(gamepad2);
        Input.handleGrasp(gamepad2);

        if (gamepad2.right_bumper) {
            // Set motor encoder ticks to 0 at the minimum height
            // Arm.jointMotor1.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            Arm.jointMotor2.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        }

        Arm.addTelemetryData();
        ColorSensor4262.addTelemetryData();
        telemetry.addData("Grasp servo position", Claw.graspServo.getPosition());
        telemetry.update();
    }

    @Override
    public void stop() {
        DriveModule.stopMotors();
        DriveModule.resetMotors();
    }
}
