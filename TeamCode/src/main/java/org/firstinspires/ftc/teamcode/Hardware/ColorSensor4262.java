package org.firstinspires.ftc.teamcode.Hardware;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.DeviceNames;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class ColorSensor4262 {
    public static ColorSensor colorSensor;
    public static final String RED = "RED", GREEN = "GREEN", BLUE = "BLUE";
    public static OpMode opMode;

    public static void init(OpMode opMode) {
        ColorSensor4262.opMode = opMode;
        HardwareMap hardwareMap = opMode.hardwareMap;

        colorSensor = hardwareMap.get(ColorSensor.class, DeviceNames.COLOR_SENSOR);
        colorSensor.enableLed(true);
    }

    /**
     * @return The max color as a string.
     */
    public static String getMaxColor() {
        HashMap<Integer, String> colorSensorMap = new HashMap<>();
        colorSensorMap.put(colorSensor.red(), RED);
        colorSensorMap.put(colorSensor.green(), GREEN);
        colorSensorMap.put(colorSensor.blue(), BLUE);

        int maxColor = Collections.max(colorSensorMap.keySet());
        return colorSensorMap.get(maxColor);
    }

    public static void addTelemetryData() {
        Telemetry telemetry = opMode.telemetry;
        telemetry.addData(RED, colorSensor.red());
        telemetry.addData(GREEN, colorSensor.green());
        telemetry.addData(BLUE, colorSensor.blue());
        telemetry.addData("Max color", getMaxColor());
    }
}
