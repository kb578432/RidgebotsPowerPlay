package org.firstinspires.ftc.teamcode.TeleOp;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.DeviceNames;
import org.firstinspires.ftc.teamcode.Hardware.ColorSensor4262;
import org.firstinspires.ftc.teamcode.Input;
import org.firstinspires.ftc.teamcode.Main;

@TeleOp
public class PlanC extends OpMode {
    // Make this static so we can access it from other classes.
    public static DcMotor motor1;
    public static DcMotor motor2;
    public static DcMotor lift;
    public static double UP_SPEED = 0.5;
    public static double DOWN_SPEED = 0.5;
    public static double MANUAL_SPEED = 0.5;

    // Before running init make sure arm is down
    @Override
    public void init() {
        Main.initAll(this);
        // motor1 = hardwareMap.get(DcMotor.class, DeviceNames.PLAN_C1);
        motor2 = hardwareMap.get(DcMotor.class, DeviceNames.PLAN_C2);
        lift = hardwareMap.get(DcMotor.class, DeviceNames.LIFT);

        // motor1.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER); // The down position now corresponds to 0 encoder ticks.
        // motor1.setMode(DcMotor.RunMode.RUN_USING_ENCODER); // Necessary for presets
        // motor1.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE); // Braking doesn't work because of torque and gravity.

        motor2.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER); // The down position now corresponds to 0 encoder ticks.
        motor2.setMode(DcMotor.RunMode.RUN_USING_ENCODER); // Necessary for presets
        motor2.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE); // Braking doesn't work because of torque and gravity.

        lift.setTargetPosition(0);
        lift.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        lift.setPower(0.2);
        lift.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        lift.setDirection(DcMotor.Direction.REVERSE);
    }

    @Override
    public void loop() {
        Input.handleDrive(gamepad1); // Same as before. should work.
        Input.handleGrasp(gamepad2); // Same as before. should work.

        double y = -gamepad2.left_stick_y; // y needs to be inverted for the robot to work intuitively.

        telemetry.addData("gamepad2.dpad_up", gamepad2.dpad_up);
        telemetry.addData("gamepad2.dpad_down", gamepad2.dpad_down);
        telemetry.addData("gamepad 2 right stick y", y);
        // telemetry.addData("encoder 1 ticks", motor1.getCurrentPosition());
        telemetry.addData("joint motor 2 power", motor2.getPower());
        telemetry.addData("joint motor 2 encoder ticks", motor2.getCurrentPosition());
        telemetry.addData("lift motor power", lift.getPower());
        telemetry.addData("lift motor encoder ticks", lift.getCurrentPosition());
        ColorSensor4262.addTelemetryData();

        // This might be redundant. keep just in case.
        // motor1.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        // motor1.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        motor2.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        motor2.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        lift.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        if (gamepad2.left_trigger >= 0.5) { // if left trigger is pressed
            // motor1.setPower(0.01); // move up by 1 percent speed
            motor2.setPower(0.01);
        } else {
            // motor1.setPower(y * 0.3); // move arm with respect to left stick y at 30 percent speed
            motor2.setPower(y * 0.3);
        }


        if (gamepad2.dpad_up) {
            lift.setTargetPosition(-4125);
            lift.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            lift.setPower(UP_SPEED);
        } else if (gamepad2.dpad_down) {
            lift.setTargetPosition(0);
            lift.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            lift.setPower(DOWN_SPEED);
        } else if (Math.abs(gamepad2.right_stick_y) > 0.05) {
            lift.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            lift.setPower(gamepad2.right_stick_y * MANUAL_SPEED);
        } else if (!lift.isBusy()){
            lift.setPower(0);
        }
    }
}
