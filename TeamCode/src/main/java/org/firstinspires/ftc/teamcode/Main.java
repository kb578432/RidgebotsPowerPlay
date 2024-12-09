package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.teamcode.Hardware.Arm;
import org.firstinspires.ftc.teamcode.Hardware.Claw;
import org.firstinspires.ftc.teamcode.Hardware.ColorSensor4262;
import org.firstinspires.ftc.teamcode.Hardware.DriveModule;
import org.firstinspires.ftc.teamcode.Hardware.Lift;

/**
 * `Main` contains methods that are not appropriate in any of the more specified classes.
 * Its methods affect the system much more generally rather than affecting one specific component.
 */
public class Main {
    /**
     * Runs the `init` method of every Hardware class.
     * <p>
     * `IMU.init` is not ran since it requires a `LinearOpMode` as an argument.
     */
    public static void initAll(OpMode opMode) {
        Arm.init(opMode);
        Claw.init(opMode);
        DriveModule.init(opMode);
        ColorSensor4262.init(opMode);
        Lift.init(opMode);
    }
}
