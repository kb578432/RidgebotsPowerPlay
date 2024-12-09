package org.firstinspires.ftc.teamcode.Auto;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.Hardware.Arm;
import org.firstinspires.ftc.teamcode.Hardware.Claw;
import org.firstinspires.ftc.teamcode.Hardware.ColorSensor4262;
import org.firstinspires.ftc.teamcode.Main;
import org.firstinspires.ftc.teamcode.RoadRunner.Drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.RoadRunner.TrajectorySequence.TrajectorySequence;

/**
 * Auto is an abstract class. It will never be selected from the driver station.
 * Rather, it is to be the parent class for OpModes corresponding to each possible spawn point.
 * <p>
 * A child class should initialize `botType` in `runOpMode`.
 * It should then invoke `super.runOpMode`
 */
@Autonomous
public abstract class RoadRunnerAuto extends LinearOpMode {
    public final double TILE = 23.5;
    public final double NORTH = Math.toRadians(90);
    public final double EAST = Math.toRadians(0);
    public final double SOUTH = Math.toRadians(270);
    public final double WEST = Math.toRadians(180);
    public final double HEIGHT = 18;
    public final double WIDTH = 18;
    public final Vector2d EXTENTS = new Vector2d(WIDTH / 2.0, HEIGHT / 2.0);

    public String botType, maxColor;

    public void runOpMode() {
        Main.initAll(this);
        SampleMecanumDrive drive = new SampleMecanumDrive(hardwareMap);

        /*
         * Due to the symmetry of the field,
         * the poses of a trajectory sequence for any spawn
         * (blue left, blue right, red left, red right)
         * are transformations of each other.
         *
         * Let the set of red left poses be the reference for every other trajectory sequence.
         * We shall construct trajectory sequences by reflecting red left poses appropriately.
         *
         * In constructing a trajectory sequence for a blue spawn,
         * it is appropriate to reflect the red left poses about the x axis.
         *
         * In constructing a trajectory sequence for a right spawn,
         * it is appropriate to reflect the red left poses about the y axis.
         */
        Pose2d spawnPose = adjust(new Pose2d(-1.5 * TILE, -3 * TILE + EXTENTS.getY(), NORTH));
        Pose2d scanPose = adjust(new Pose2d(-1.5 * TILE, -1.5 * TILE - EXTENTS.getY(), NORTH));
        Pose2d betweenPose = adjust(new Pose2d(-1.5 * TILE, -0.5 * TILE, NORTH));
        Pose2d coneStackPose = adjust(new Pose2d(-2.5 * TILE, -0.5 * TILE, WEST));
        Pose2d junctionPose = adjust(new Pose2d(-TILE, -0.5 * TILE, NORTH));

        drive.setPoseEstimate(spawnPose);

        TrajectorySequence trajectorySequence = drive
                .trajectorySequenceBuilder(spawnPose)

                // Grab the cone. Raise the arm
                .UNSTABLE_addTemporalMarkerOffset(0, () -> {
                    Claw.grab();
                    sleep(1000);
                    Arm.setHeightPreset(3);
                    sleep(1000);
                })

                // Drive to the signal cone. Scan the signal cone
                .lineToLinearHeading(scanPose)
                .UNSTABLE_addTemporalMarkerOffset(0, () -> {
                    sleep(1000);
                    maxColor = ColorSensor4262.getMaxColor();
                    sleep(1000);
                })

                // Drive to the high junction. Place cone
                .lineToLinearHeading(betweenPose)
                .lineToLinearHeading(junctionPose)
                .UNSTABLE_addTemporalMarkerOffset(0, () -> {
                    Claw.release();
                    sleep(1000);
                })

                // Drive to the cone stack. Pick up a cone
                .lineToLinearHeading(coneStackPose)
                .UNSTABLE_addTemporalMarkerOffset(0, () -> {
                    Arm.setHeightPreset(0);
                    sleep(1000);
                    Claw.grab();
                    sleep(1000);
                    Arm.setHeightPreset(3);
                })

                // Drive to the high junction. Place cone
                .lineToLinearHeading(junctionPose)
                .UNSTABLE_addTemporalMarkerOffset(0, () -> {
                    Claw.release();
                    sleep(1000);
                })

                .lineToLinearHeading(betweenPose)
                .build();

        waitForStart();

        drive.followTrajectorySequence(trajectorySequence);

        if (maxColor.equals("RED")) {
            drive.followTrajectory(drive
                    .trajectoryBuilder(trajectorySequence.end())
                    .strafeLeft(TILE)
                    .build());
        }
        if (maxColor.equals("BLUE")) {
            drive.followTrajectory(drive
                    .trajectoryBuilder(trajectorySequence.end())
                    .strafeRight(TILE)
                    .build());
        }
    }

    /**
     * @return Pose reflected about the x axis
     */
    public Pose2d reflectX(Pose2d pose) {
        double heading = Math.toRadians(180) - pose.getHeading();
        return new Pose2d(-pose.getX(), pose.getY(), heading);
    }

    /**
     * @return Pose reflect about the y axis
     */
    public Pose2d reflectY(Pose2d pose) {
        double heading = -pose.getHeading();
        return new Pose2d(pose.getX(), -pose.getY(), heading);
    }

    /**
     * @return A redLeftPose adjusted with respect to botType.
     * Right: Reflect about the x axis.
     * Blue: Reflect about the y axis.
     */
    public Pose2d adjust(Pose2d redLeftPose) {
        Pose2d adjusted = redLeftPose;

        if (botType.contains("Right")) {
            adjusted = reflectX(adjusted);
        }
        if (botType.contains("Blue")) {
            adjusted = reflectY(adjusted);
        }
        return adjusted;
    }
}
