package org.firstinspires.ftc.teamcode.Auto;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.Hardware.ColorSensor4262;
import org.firstinspires.ftc.teamcode.DeviceNames;
import org.firstinspires.ftc.teamcode.Hardware.Claw;
import org.firstinspires.ftc.teamcode.Hardware.IMU;
import org.firstinspires.ftc.teamcode.Hardware.Lift;
import org.firstinspires.ftc.teamcode.Main;
import org.firstinspires.ftc.teamcode.Hardware.DriveModule;
import org.firstinspires.ftc.teamcode.TeleOp.PlanC;

@Autonomous
@Config
public class LeftAuto extends LinearOpMode {
    public static double TILE = 23.5;

    @Override
    public void runOpMode() throws InterruptedException {
        /*
        Main.initAll(this);
        IMU.init(this);

        DcMotor jointMotor = hardwareMap.get(DcMotor.class, DeviceNames.PLAN_C2);
        jointMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        jointMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        jointMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        // DcMotor liftMotor = hardwareMap.get(DcMotor.class, DeviceNames.LIFT);
        // liftMotor.setTargetPosition(0);
        // liftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        // liftMotor.setPower(0.5);
        // liftMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        // liftMotor.setDirection(DcMotor.Direction.REVERSE);

        waitForStart();

        Claw.grab();
        sleep(1000);
        jointMotor.setPower(0.3);
        // liftMotor.setTargetPosition(-4125);
        // liftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        // liftMotor.setPower(0.5);
        Lift.runToTop();

        // Wait for the arm to come up.
        sleep(1000);

        DriveModule.driveForward(21);

        sleep(1000);
        String maxColor = ColorSensor4262.getMaxColor();
        telemetry.addData("Max Color", maxColor);
        telemetry.update();

        sleep(1000);
        DriveModule.driveForward(49);
        // DriveModule.driveBackwards(TILE);
        DriveModule.turnBy(-90);
        DriveModule.driveForward(2);

        sleep(1000);
        Claw.release();

        Lift.runToBottom();
        while (Lift.motor.isBusy()) {
            idle();
        }
        */

        Main.initAll(this);
        IMU.init(this);

        DcMotor jointMotor = hardwareMap.get(DcMotor.class, DeviceNames.PLAN_C2);
        jointMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        jointMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        jointMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        // DcMotor liftMotor = hardwareMap.get(DcMotor.class, DeviceNames.LIFT);
        // liftMotor.setTargetPosition(0);
        // liftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        // liftMotor.setPower(0.5);
        // liftMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        // liftMotor.setDirection(DcMotor.Direction.REVERSE);

        waitForStart();

        Claw.grab();
        sleep(1000);
        jointMotor.setPower(0.3);
        // liftMotor.setTargetPosition(-4125);
        // liftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        // liftMotor.setPower(0.5);
        Lift.runToTop();

        // Wait for the arm to come up.
        sleep(1000);

        DriveModule.driveForward(21);

        sleep(1000);
        String maxColor = ColorSensor4262.getMaxColor();
        telemetry.addData("Max Color", maxColor);
        telemetry.update();

        sleep(1000);
        DriveModule.driveForward(12);

        sleep(1000);
        DriveModule.driveLeft(33);

        sleep(1000);
        Claw.release();

        sleep(1000);
        // liftMotor.setTargetPosition(0);
        Lift.runToBottom();
        DriveModule.driveBackwards(3);

        if ("RED".equals(maxColor)) {
            DriveModule.driveRight(10);
        } else if ("GREEN".equals(maxColor)) {
            DriveModule.driveRight(33.5);
        } else if ("BLUE".equals(maxColor)) {
            DriveModule.driveRight(57);
        }
        while (Lift.motor.isBusy()) {
            idle();
        }
    }

    /*
    public static double AUTO_SPEED = 0.4;
    public static double ROTATION_CORRECTION = 0.03;
    public static DcMotor lift;

    public double forwardPosition() {
        double y1 = -DriveModule.br.getCurrentPosition();
        double y2 = -DriveModule.bl.getCurrentPosition();
        return (y1 + y2) / 2;
    }
    public double sidewaysPosition() {
        return DriveModule.fr.getCurrentPosition();
    }


    @Override
    public void runOpMode() throws InterruptedException {

        Main.initAll(this);

        // Todo move to its own init function
        PlanC.motor2 = hardwareMap.get(DcMotor.class, DeviceNames.PLAN_C2);
        PlanC.motor2.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        PlanC.motor2.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        PlanC.motor2.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        lift = hardwareMap.get(DcMotor.class, DeviceNames.LIFT);
        lift.setTargetPosition(0);
        lift.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        lift.setPower(0.5);
        lift.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        lift.setDirection(DcMotor.Direction.REVERSE);


        waitForStart();

        // Nothing can move before start.
        Claw.grab();
        sleep(500);
        PlanC.motor2.setPower(0.3);
        lift.setTargetPosition(-250);
        lift.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        lift.setPower(0.5);

        // Wait for the arm to come up.
        sleep(1000);

        // Drive forward 500 ticks at 30 percent speed.
        DriveModule.resetMotors(); // must reset to keep track of our position.
        while (forwardPosition() < DriveModule.toTicksDeadWheels(17)) {
            DriveModule.drive(0, AUTO_SPEED*0.9, 0);
            telemetry.addData("fl Ticks: ", DriveModule.fl.getCurrentPosition());
            telemetry.addData("fr Ticks: ", DriveModule.fr.getCurrentPosition());
            telemetry.addData("bl Ticks: ", DriveModule.bl.getCurrentPosition());
            telemetry.addData("br Ticks: ", DriveModule.br.getCurrentPosition());
            telemetry.update();
            ColorSensor4262.addTelemetryData();
            DriveModule.addTelemetryData();
        }
        DriveModule.stopMotors();

        sleep(2000);

        String color = ColorSensor4262.getMaxColor();

        DriveModule.resetMotors();
        while (forwardPosition() < DriveModule.toTicksDeadWheels(7.875)) {
            DriveModule.drive(0, AUTO_SPEED, 0);
            telemetry.addData("Detected color: ", color);
            telemetry.update();
        }
        DriveModule.stopMotors();

        sleep(100);

        DriveModule.resetMotors();
        while (sidewaysPosition() < DriveModule.toTicksDeadWheels(9.675)) {
            DriveModule.drive(AUTO_SPEED, 0, ROTATION_CORRECTION);
        }
        DriveModule.stopMotors();

        sleep(1500);

        Claw.release();

        sleep(500);

//        DriveModule.resetMotors();
//        while (forwardPosition() > DriveModule.toTicksThroughbore(-1)) {
//            DriveModule.drive(0, -AUTO_SPEED, 0);
//        }
//        DriveModule.stopMotors();


        DriveModule.resetMotors(); // must reset to keep track of our position.
        if (color.equals("RED")) {
            while (sidewaysPosition() > DriveModule.toTicksDeadWheels(-32)) {
                DriveModule.drive(-AUTO_SPEED, 0, -ROTATION_CORRECTION*1.1); // Drive left for 750 ticks at 30 percent speed
                ColorSensor4262.addTelemetryData();
            }
        } else if (color.equals("BLUE")) {
            while (sidewaysPosition() < DriveModule.toTicksDeadWheels(10)) {
                DriveModule.drive(AUTO_SPEED, 0, ROTATION_CORRECTION); // Drive right for 750 ticks at 30 percent
                ColorSensor4262.addTelemetryData();
            }
        } else if (color.equals("GREEN")) {
            while (sidewaysPosition() > DriveModule.toTicksDeadWheels(-10.5)) {
                DriveModule.drive(-AUTO_SPEED, 0, -ROTATION_CORRECTION);
            }
        }
        DriveModule.stopMotors();
        Claw.grab();
        PlanC.motor2.setPower(-0.2);
        sleep(100);
        Claw.grab();
        lift.setTargetPosition(0);
        lift.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        lift.setPower(1);
        sleep(4000);

    }
    */
}
