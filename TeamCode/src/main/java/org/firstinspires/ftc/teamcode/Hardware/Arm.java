package org.firstinspires.ftc.teamcode.Hardware;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.DeviceNames;

@Config
public class Arm {
    public static OpMode opMode;

    public static DcMotor jointMotor1;
    public static DcMotor jointMotor2;
    public static DcMotor liftMotor;
    public static DcMotor[] motors;

    public static Servo graspServo;
    public static double motorSpeed;
    public static double jointSpeed = 0.3;

    public static void init(OpMode opMode) {
        Arm.opMode = opMode;
        HardwareMap hardwareMap = opMode.hardwareMap;

        graspServo = hardwareMap.get(Servo.class, DeviceNames.GRASP_SERVO);

        jointMotor1 = hardwareMap.get(DcMotor.class, DeviceNames.PLAN_C1);
        jointMotor2 = hardwareMap.get(DcMotor.class, DeviceNames.PLAN_C2);
        liftMotor = hardwareMap.get(DcMotor.class, DeviceNames.LIFT);
        motors = new DcMotor[]{jointMotor1, jointMotor2, liftMotor};

        for (DcMotor motor : motors) {
            motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            motor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            motor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        }
        liftMotor.setDirection(DcMotor.Direction.REVERSE);
    }

    public static void setJointPower(double jointPower) {
        jointMotor1.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        jointMotor2.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        jointMotor1.setPower(jointPower * jointSpeed);
        jointMotor2.setPower(jointPower * jointSpeed);
    }

    public static void setHeightPreset(int preset) {
        // TODO populate with encoder ticks
        int[][] encoderTicksPresets = {
                {0, 0, 0},
                {0, 0, 0},
                {0, 0, 0}
        };
        int[] targetPositions = encoderTicksPresets[preset];

        for (int i = 0; i < 3; i++) {
            motors[i].setMode(DcMotor.RunMode.RUN_TO_POSITION);
            motors[i].setTargetPosition(targetPositions[i]);
            motors[i].setPower(motorSpeed);
        }
    }

    public static void addTelemetryData() {
        Telemetry telemetry = opMode.telemetry;
        telemetry.addLine("jointMotor1{ticks:" + jointMotor1.getCurrentPosition() + ",power:" + jointMotor1.getPower() + "}");
        telemetry.addLine("jointMotor2{ticks:" + jointMotor2.getCurrentPosition() + ",power:" + jointMotor1.getPower() + "}");
        telemetry.addLine("liftMotor{ticks:" + liftMotor.getCurrentPosition() + ",power" + liftMotor.getPower() + "}");
    }
}
