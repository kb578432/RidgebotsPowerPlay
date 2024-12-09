package org.firstinspires.ftc.teamcode.Test;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Hardware.Claw;
import org.firstinspires.ftc.teamcode.Input;

@TeleOp(group = "Test")
public class TestClaw extends OpMode {
    @Override
    public void init() {
        Claw.init(this);
    }

    @Override
    public void loop() {
        Input.handleGrasp(gamepad1);

        Claw.addTelemetryData();
        telemetry.update();
    }
}
