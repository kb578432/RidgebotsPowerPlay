package org.firstinspires.ftc.teamcode.Test;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Hardware.ColorSensor4262;

/**
 * Continuously adds the color sensor's observations and derived max color to telemetry.
 * This OpMode is a method of asserting proper color sensor behavior by eliminating any factors not related to the color sensor.
 */
@TeleOp(group = "Test")
public class TestColorSensor extends OpMode {
    @Override
    public void init() {
        ColorSensor4262.init(this);
    }

    @Override
    public void loop() {
        ColorSensor4262.addTelemetryData();
        telemetry.update();
    }
}
