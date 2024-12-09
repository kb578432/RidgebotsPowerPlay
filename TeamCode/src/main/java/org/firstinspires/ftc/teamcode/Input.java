package org.firstinspires.ftc.teamcode;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.arcrobotics.ftclib.geometry.Translation2d;
import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.teamcode.Hardware.Arm;
import org.firstinspires.ftc.teamcode.Hardware.Claw;
import org.firstinspires.ftc.teamcode.Hardware.DriveModule;

/**
 * The reason for this class is to have input behaviours (strategies) in one location.
 * If we ever need to change input behaviours we are able to do so easily.
 */
@Config
public class Input {
    /**
     * The robot moves this percent of its max speed when right bumper is pressed.
     */
    public static double SLOWDOWN_SCALE = 0.5;
    /**
     * The trigger must be held by this much for an input to be registered.
     */
    public static double TRIGGER_DEADZONE = 0.5;
    public static double SLOW_DRIVE_SPEED = 0.25;
    public static double FAST_DRIVE_SPEED = 0.5;

    // =============================================================================================
    // Input behaviours
    // =============================================================================================

    /**
     * Set arm height level to ...
     * <p> A: 0
     * <p> X: 1
     * <p> B: 2
     * <p> Y: 3
     */
    public static void handleArmHeightByPreset(Gamepad gamepad) {
        int heightPreset = gamepad.a ? 0 : gamepad.x ? 1 : gamepad.b ? 2 : gamepad.y ? 3 : -1;
    }

    /**
     * Rotate manually ...
     * <p> Left-stick-y: Joint 0
     * <p> Right-stick-y: Joint 1
     * <p> D-Pad-y: Joint 2 / claw pitch
     */
    public static void handleArmHeightManually(Gamepad gamepad) {
        double jointPower = getDPad(gamepad).getY();
        Arm.setJointPower(jointPower);
    }

    /**
     * The claw grasp is clenched while the right trigger is held.
     */
    public static void handleGrasp(Gamepad gamepad) {
        if (gamepad.right_trigger > TRIGGER_DEADZONE) {
            Claw.grab();

        } else {
            Claw.release();
        }
    }

    /**
     * <p> Left-stick: Position.
     * <p> Right-stick-x: Rotation.
     * <p> Right bumper: 50% speed.
     * <p> No bumper: 100% speed.
     */
    public static void handleDrive(Gamepad gamepad) {
        /*
         * The driving will be inverted if the right motors are not inverted.
         *
         * If speed is 1, the robot travels at full speed.
         */
        double xin;
        double yin;
        if (gamepad.dpad_right) {
            xin = 0.5;
        } else if (gamepad.dpad_left) {
            xin = -0.5;
        } else {
            xin = gamepad.left_stick_x * 0.5;
        }
        if (gamepad.dpad_up) {
            yin = -0.5;
        } else if (gamepad.dpad_down) {
            yin = 0.5;
        } else {
            yin = gamepad.left_stick_y * 0.5;
        }
        double turnin = gamepad.right_stick_x * 0.5;
        double speed = gamepad.right_bumper ? SLOWDOWN_SCALE : 1;
        double turn = turnin * speed;
        double x = xin * speed;
        double y = yin * speed;

        // double dPadX = getDPad(gamepad).getX();
        // double dPadY = getDPad(gamepad).getY();
        // double leftX = gamepad.left_stick_x;
        // double leftY = gamepad.left_stick_y;

        // double speed = gamepad.right_bumper ? SLOW_DRIVE_SPEED : FAST_DRIVE_SPEED;
        // double turn = gamepad.right_stick_x * speed;
        // double x = (dPadX != 0 ? dPadX : leftX) * speed;
        // double y = (dPadY != 0 ? dPadY : leftY) * speed;

        DriveModule.drive(x, -y, turn);
    }

    // =============================================================================================
    // Input values
    // =============================================================================================

    /**
     * Inspired by the `GetAxisRaw` method in the Unity framework.
     * https://docs.unity3d.com/ScriptReference/Input.GetAxisRaw.html
     *
     * @param deadZone The minimum absolute value `input` must have in the range [0, 1].
     * @return The raw int value of `input`. I.e:
     * <p> 0 if `input` is in the dead zone,
     * <p> 1 if `input` is > 0,
     * <p> -1 if `input` is < 0.
     */
    public static int getRaw(double input, double deadZone) {
        // If `input` belongs to the range (-deadZone, deadZone).
        if (Math.abs(input) < deadZone) {
            return 0;
        }
        if (input > 0) {
            return 1;
        }
        return -1;
    }

    /**
     * @return A Vector2d representing the DPad inputs of `gamepad`.
     * <p> X = DPad left: -1, DPad right: 1, both or neither: 0
     * <p> Y = Down DPad: -1, DPad up: 1, both or neither: 0
     */
    public static Vector2d getDPad(Gamepad gamepad) {
        double x = (gamepad.dpad_right ? 1 : 0) - (gamepad.dpad_left ? 1 : 0);
        double y = (gamepad.dpad_up ? 1 : 0) - (gamepad.dpad_down ? 1 : 0);
        return new Vector2d(x, y);
    }

    /**
     * @return Left bumper: -1, right bumper: 1, both or neither: 0
     */
    public static double getBumperX(Gamepad gamepad) {
        return (gamepad.right_bumper ? 1 : 0) - (gamepad.left_bumper ? 1 : 0);
    }
}
