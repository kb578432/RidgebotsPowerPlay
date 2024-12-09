package org.firstinspires.ftc.teamcode.Test;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.HardwareDevice;

/**
 * Adds the connection info and names of all recognized hardware devices of hardwareMap to telemetry.
 * This OpMode is a method of asserting proper hardwareMap behavior by eliminating any factors not related to hardwareMap.
 */
@TeleOp(group = "Test")
public class TestHardwareMap extends OpMode {
    @Override
    public void init() {

        for (HardwareDevice hardwareDevice : hardwareMap) {
            // `getConnectionInfo` implementations vary by HardwareDevice.
            // By and large, `getConnectionInfo` returns the port number and controller if applicable.
            //
            // `getDeviceName` returns the device manufacturer and name.
            telemetry.addData(
                    hardwareDevice.getConnectionInfo(),
                    hardwareDevice.getDeviceName()
            );
        }
        telemetry.update();
    }

    @Override
    public void loop() {

    }
}
