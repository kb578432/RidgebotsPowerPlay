package org.firstinspires.ftc.teamcode.Hardware;

import com.acmerobotics.dashboard.config.Config;
import com.arcrobotics.ftclib.drivebase.MecanumDrive;
import com.arcrobotics.ftclib.hardware.motors.Motor;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.DeviceNames;

/**
 * The class for all things driving related.
 */
@Config
public class DriveModule {
    /**
     * The diameter of each wheel in inches
     */
    public static final double WHEEL_DIAMETER = 4;

    /**
     * Each motor/wheel of the driving system.
     * <p>
     * FL's encoder ticks are from the FL motor.
     * FR's encoder ticks are from the front dead wheel.
     * BL's encoder ticks are from the left dead wheel.
     * BR's encoder ticks are from the right dead wheel.
     */
    public static Motor fl, fr, bl, br;

    /**
     * Contains a reference to each motor.
     * DO NOT initialize to a new array. Do that in init.
     */
    public static Motor[] motors;

    /**
     * Contains methods for driving.
     * Does NOT contain an accessible reference to each motor (unfortunately).
     */
    public static MecanumDrive mecanumDrive;
    public static OpMode opMode;

    /**
     * Initialize and assign fields.
     */
    public static void init(OpMode opMode) {
        DriveModule.opMode = opMode;
        HardwareMap hardwareMap = opMode.hardwareMap;

        fl = new Motor(hardwareMap, DeviceNames.FL, Motor.GoBILDA.RPM_435);
        fr = new Motor(hardwareMap, DeviceNames.FR, Motor.GoBILDA.RPM_435);
        bl = new Motor(hardwareMap, DeviceNames.BL, Motor.GoBILDA.RPM_435);
        br = new Motor(hardwareMap, DeviceNames.BR, Motor.GoBILDA.RPM_435);
        motors = new Motor[]{fl, fr, bl, br};
        mecanumDrive = new MecanumDrive(fl, fr, bl, br);

        for (Motor motor : motors) {
            // Equivalent to setDirection(Reversed)
            motor.setInverted(true);
            motor.setZeroPowerBehavior(Motor.ZeroPowerBehavior.BRAKE);
        }
        resetMotors();
    }

    // =============================================================================================
    // Motor methods
    // =============================================================================================

    public static void stopMotors() {
        for (Motor motor : motors) {
            motor.stopMotor();
        }
    }

    public static void resetMotors() {
        for (Motor motor : motors) {
            motor.resetEncoder();
        }
    }

    // =============================================================================================
    // Drive methods
    // =============================================================================================

    /**
     * Sets the motors' powers for a single iteration.
     * Invoking this method does not guarantee arrival at a desired destination.
     */
    public static void drive(double x, double y, double rotation) {
        /*
         * Potential solutions:
         * https://gm0.org/en/latest/docs/software/tutorials/mecanum-drive.html
         * https://ftcforum.firstinspires.org/forum/ftc-technology/android-studio/6361-mecanum-wheels-drive-code-example
         *
         * Currently used solution:
         * https://docs.ftclib.org/ftclib/features/drivebases
         */
        mecanumDrive.driveRobotCentric(x, y, rotation);
    }

    public static double MOVE_SPEED = 0.35, ROTATE_SPEED = 0.2;

    public static void driveLeft(double inches) {
        resetMotors();
        while (-xPositionInches() < inches) {
            drive(-MOVE_SPEED, 0, IMU.getCorrection());
        }
        stopMotors();
    }

    public static void driveRight(double inches) {
        resetMotors();
        while (xPositionInches() < inches) {
            drive(MOVE_SPEED, 0, IMU.getCorrection());
        }
        stopMotors();
    }

    public static void driveForward(double inches) {
        resetMotors();
        while (yPositionInches() < inches) {
            drive(0, MOVE_SPEED, IMU.getCorrection());
        }
        stopMotors();
    }

    public static void driveBackwards(double inches) {
        resetMotors();
        while (-yPositionInches() < inches) {
            drive(0, -MOVE_SPEED, IMU.getCorrection());
        }
        stopMotors();
    }

    public static void turnBy(double angleDegrees) {
        IMU.resetAngle();
        while (Math.abs(IMU.getAngle()) < Math.abs(angleDegrees)) {
            double angleSign = angleDegrees < 0 ? -1 : 1;
            drive(0, 0, ROTATE_SPEED * angleSign);
        }
        IMU.resetAngle();
        stopMotors();
    }

    // =============================================================================================
    // Calculation methods
    // =============================================================================================

    /**
     * @return The distance in inches traveled on the y-axis. (Forwards / Backwards)
     */
    public static double yPositionInches() {
        return toInchesMotors(fl.getCurrentPosition());
    }

    /**
     * @return The distance in inches traveled on the x-axis. (Left / Right)
     */
    public static double xPositionInches() {
        return toInchesDeadWheels(fr.getCurrentPosition());
    }

    /**
     * @return `inches` in ticks. Applies only to the motors of the drive train.
     */
    public static double toTicksMotors(double inches) {
        // https://www.vexforum.com/t/converting-units-in-code/59739
        // https://www.reddit.com/r/FTC/comments/78l5o0/how_to_program_encoders/
        double circumference = WHEEL_DIAMETER * Math.PI;
        double inchesPerTick = circumference / fl.getCPR();
        return inches / inchesPerTick;
    }

    /**
     * @return `ticks` in inches. Applies only to the motors of the drive train.
     */
    public static double toInchesMotors(double ticks) {
        double circumference = WHEEL_DIAMETER * Math.PI;
        double inchesPerTick = circumference / fl.getCPR();
        return inchesPerTick * ticks;
    }

    /**
     * @return `inches` in ticks. Applies only to the rev throughbore dead wheels.
     */
    public static double toTicksDeadWheels(double inches) {
        double circumference = (35 / 25.4) * Math.PI;
        double inchesPerTick = circumference / 8192;
        return inches / inchesPerTick;
    }

    /**
     * @return `inches` in ticks. Applies only to the rev throughbore dead wheels.
     */
    public static double toInchesDeadWheels(double ticks) {
        double circumference = (35 / 25.4) * Math.PI;
        double inchesPerTick = circumference / 8192;
        return inchesPerTick * ticks;
    }

    public static void addTelemetryData() {
        Telemetry telemetry = opMode.telemetry;
        telemetry.addLine("fl{ticks:" + fl.getCurrentPosition() + ",power" + fl.get() + "}");
        telemetry.addLine("fr{ticks:" + fr.getCurrentPosition() + ",power" + fr.get() + "}");
        telemetry.addLine("bl{ticks:" + bl.getCurrentPosition() + ",power" + bl.get() + "}");
        telemetry.addLine("br{ticks:" + br.getCurrentPosition() + ",power" + br.get() + "}");
    }
}
