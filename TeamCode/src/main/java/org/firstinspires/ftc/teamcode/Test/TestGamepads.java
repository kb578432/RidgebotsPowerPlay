package org.firstinspires.ftc.teamcode.Test;

import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Input;

import java.util.HashMap;

/**
 * Continuously adds the currently pressed buttons/triggers/joysticks and their values to telemetry.
 * This OpMode is a method of asserting proper gamepad/Input class behavior by eliminating any factors not related to the gamepads or Input class.
 */
@TeleOp(group = "Test")
public class TestGamepads extends OpMode {
    public double deadZone = 0.5;
    public GamepadEx gamepadEx1;
    public GamepadEx gamepadEx2;

    @Override
    public void init() {
        gamepadEx1 = new GamepadEx(gamepad1);
        gamepadEx2 = new GamepadEx(gamepad2);
    }

    @Override
    public void loop() {
        addTelemetryData(gamepadEx1);
        addTelemetryData(gamepadEx2);
        telemetry.update();
    }

    /**
     * Adds the currently pressed buttons/triggers/joysticks and their values to telemetry.
     *
     * @param gamepadEx The gamepad to read from and add data to telemetry regarding.
     */
    public void addTelemetryData(GamepadEx gamepadEx) {
        gamepadEx.readButtons();
        String gamepadName = "gamepad" + gamepadEx.gamepad.getGamepadId();

        addButtonTelemetryData(gamepadEx, gamepadName);
        addTriggerTelemetryData(gamepadEx, gamepadName);
        addJoystickTelemetryData(gamepadEx, gamepadName);
        telemetry.addData(
                "Input.getDPad(" + gamepadName + ")",
                Input.getDPad(gamepadEx.gamepad)
        );
        telemetry.addData(
                "Input.getBumperX(" + gamepadName + ")",
                Input.getBumperX(gamepadEx.gamepad)
        );
    }

    /**
     * Adds the currently pressed joysticks and their values to telemetry.
     */
    public void addJoystickTelemetryData(GamepadEx gamepadEx, String gamepadName) {
        HashMap<String, Double> joystickMap = new HashMap<>();
        joystickMap.put("leftX", gamepadEx.getLeftX());
        joystickMap.put("leftY", gamepadEx.getLeftY());
        joystickMap.put("rightX", gamepadEx.getRightX());
        joystickMap.put("rightY", gamepadEx.getRightY());

        for (String joystickKey : joystickMap.keySet()) {
            double joystickValue = joystickMap.get(joystickKey);

            if (joystickValue != 0) {
                String joystickName = gamepadName + "." + joystickKey;

                telemetry.addData(joystickName, joystickValue);
                telemetry.addData(
                        "Input.getRaw(" + joystickName + "," + deadZone + ")",
                        Input.getRaw(joystickValue, deadZone)
                );
            }
        }
    }

    /**
     * Adds the currently pressed triggers and their values to telemetry.
     */
    public void addTriggerTelemetryData(GamepadEx gamepadEx, String gamepadName) {
        for (GamepadKeys.Trigger trigger : GamepadKeys.Trigger.values()) {
            double triggerValue = gamepadEx.getTrigger(trigger);

            if (triggerValue != 0) {
                String triggerName = gamepadName + "." + trigger.name();

                telemetry.addData(triggerName, triggerValue);
                telemetry.addData(
                        "Input.getRaw(" + triggerName + "," + deadZone + ")",
                        Input.getRaw(triggerValue, deadZone)
                );
            }
        }
    }

    /**
     * Adds the currently pressed buttons to telemetry.
     */
    public void addButtonTelemetryData(GamepadEx gamepadEx, String gamepadName) {
        for (GamepadKeys.Button button : GamepadKeys.Button.values()) {
            if (gamepadEx.getButton(button)) {
                telemetry.addLine(gamepadName + "." + button.name());
            }
        }
    }
}
