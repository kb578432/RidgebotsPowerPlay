package org.firstinspires.ftc.teamcode.TeleOp;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.DeviceNames;
import org.firstinspires.ftc.teamcode.Hardware.Lift;
import org.firstinspires.ftc.teamcode.Input;
import org.firstinspires.ftc.teamcode.Main;
import org.firstinspires.ftc.teamcode.Hardware.DriveModule;

@TeleOp
@Config
public class TeleOp4262 extends OpMode {
    public static DcMotor jointMotor;
    public static double SPEED = 0.65;

    @Override
    public void init() {
        Main.initAll(this);
        jointMotor = hardwareMap.get(DcMotor.class, DeviceNames.PLAN_C2);

        jointMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        jointMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        jointMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }

    @Override
    public void loop() {
        Input.handleDrive(gamepad1);
        Input.handleGrasp(gamepad2);

        if (gamepad2.left_trigger > 0.5) {
            jointMotor.setPower(0.01);

        } else {
            jointMotor.setPower(-gamepad2.left_stick_y * SPEED);
        }

        boolean isLeftStickUp = gamepad2.left_stick_y < 0;
        boolean isLiftAtTop = Lift.motor.getCurrentPosition() < Lift.TOP_TICKS;
        if (gamepad2.dpad_up) {
            Lift.runToTop();

        } else if (gamepad2.dpad_down) {
            Lift.runToBottom();

        } else if ((Math.abs(gamepad2.right_stick_y) > 0.05) && !(isLeftStickUp && isLiftAtTop)) {
            Lift.motor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            Lift.motor.setPower(gamepad2.right_stick_y * Lift.SPEED);

        } else if (gamepad2.y) {
            Lift.motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        } else if (!Lift.motor.isBusy()) {
            Lift.motor.setPower(0);
        }
        Lift.addTelemetryData();
        telemetry.addLine("Y: Reset the lift motor's encoder.");
        telemetry.update();
    }
}