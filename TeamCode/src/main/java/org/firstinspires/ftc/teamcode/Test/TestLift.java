package org.firstinspires.ftc.teamcode.Test;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.Hardware.Lift;

@TeleOp(group = "Test")
public class TestLift extends OpMode {
    @Override
    public void init() {
        Lift.init(this);
    }

    @Override
    public void loop() {
        if (Math.abs(gamepad1.left_stick_y) > 0.05) {
            Lift.motor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            Lift.motor.setPower(gamepad1.left_stick_y * Lift.SPEED);
        } else if (gamepad1.dpad_up) {
            Lift.runToTop();
        } else if (gamepad1.dpad_down) {
            Lift.runToBottom();
        } else if (gamepad1.y) {
            Lift.motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        } else if (!Lift.motor.isBusy()) {
            Lift.motor.setPower(0);
        }
        Lift.addTelemetryData();
        telemetry.addLine("Left-Stick-Y: Set lift motor power manually.");
        telemetry.addLine("D-Pad-Up: Run the lift motor to the top.");
        telemetry.addLine("D-Pad-Down: Run the lift motor to the bottom.");
        telemetry.addLine("Y: Reset the lift motor's encoder.");
        telemetry.update();
    }
}
