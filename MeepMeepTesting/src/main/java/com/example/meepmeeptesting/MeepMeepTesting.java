package com.example.meepmeeptesting;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.noahbres.meepmeep.MeepMeep;
import com.noahbres.meepmeep.core.colorscheme.ColorScheme;
import com.noahbres.meepmeep.core.colorscheme.scheme.ColorSchemeBlueDark;
import com.noahbres.meepmeep.core.colorscheme.scheme.ColorSchemeRedDark;
import com.noahbres.meepmeep.roadrunner.DefaultBotBuilder;
import com.noahbres.meepmeep.roadrunner.entity.RoadRunnerBotEntity;

import java.util.Arrays;
import java.util.List;

public class MeepMeepTesting {
    public static final double TILE = 23.5;
    public static final double NORTH = Math.toRadians(90);
    public static final double EAST = Math.toRadians(0);
    public static final double SOUTH = Math.toRadians(270);
    public static final double WEST = Math.toRadians(180);
    public static final double HEIGHT = 18;
    public static final double WIDTH = 18;
    public static final Vector2d EXTENTS = new Vector2d(WIDTH / 2.0, HEIGHT / 2.0);

    /**
     * @return Pose reflected about the x axis
     */
    public static Pose2d reflectX(Pose2d pose) {
        double heading = Math.toRadians(180) - pose.getHeading();
        return new Pose2d(-pose.getX(), pose.getY(), heading);
    }

    /**
     * @return Pose reflect about the y axis
     */
    public static Pose2d reflectY(Pose2d pose) {
        double heading = -pose.getHeading();
        return new Pose2d(pose.getX(), -pose.getY(), heading);
    }

    /**
     * @return A redLeftPose adjusted with respect to botType.
     * Right: Reflect about the x axis.
     * Blue: Reflect about the y axis.
     */
    public static Pose2d adjust(String botType, Pose2d redLeftPose) {
        Pose2d adjusted = redLeftPose;

        if (botType.contains("Right")) {
            adjusted = reflectX(adjusted);
        }
        if (botType.contains("Blue")) {
            adjusted = reflectY(adjusted);
        }
        return adjusted;
    }

    public static RoadRunnerBotEntity createBot(String botType, MeepMeep meepMeep) {
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
        Pose2d spawnPose = adjust(botType,
                new Pose2d(-1.5 * TILE, -3 * TILE + EXTENTS.getY(), NORTH));

        Pose2d scanPose = adjust(botType,
                new Pose2d(-1.5 * TILE, -1.5 * TILE - EXTENTS.getY(), NORTH));

        Pose2d betweenPose = adjust(botType,
                new Pose2d(-1.5 * TILE, -0.5 * TILE, NORTH));

        Pose2d coneStackPose = adjust(botType,
                new Pose2d(-2.5 * TILE, -0.5 * TILE, WEST));

        Pose2d junctionPose = adjust(botType,
                new Pose2d(-TILE, -0.5 * TILE, NORTH));

        List<Pose2d> signalZonePoses = Arrays.asList(
                new Pose2d(-2.5 * TILE, -1.5 * TILE, NORTH),
                new Pose2d(-1.5 * TILE, -1.5 * TILE, NORTH),
                new Pose2d(-0.5 * TILE, -1.5 * TILE, NORTH)
        );
        signalZonePoses.replaceAll(x -> adjust(botType, x));

        ColorScheme colorScheme = botType.contains("Red")
                ? new ColorSchemeRedDark()
                : new ColorSchemeBlueDark();

        /*
         * In the following trajectory sequence, we use
         * "UNSTABLE_addTemporalMarkerOffset" instead of "addDisplacementMarker"
         *
         * The reasoning is as follows:
         *
         * Imagine a piece of code that reads as such:
         * ```
         * addDisplacementMarker(callback)
         * waitSeconds(seconds)
         * ```
         *
         * "callback" runs after "waitSeconds" has returned
         *
         * Image a piece of code that reads as such:
         * ```
         * UNSTABLE_addTemporalMarkerOffset(offset, callback)
         * waitSeconds(seconds)
         * ```
         *
         * "callback" runs "offset" seconds into "waitSeconds"
         */
        return new DefaultBotBuilder(meepMeep)
                .setConstraints(60, 60, Math.toRadians(180), Math.toRadians(180), 15)
                .setColorScheme(colorScheme)
                .followTrajectorySequence(drive -> drive
                        .trajectorySequenceBuilder(spawnPose)

                        // Drive to the signal cone. Scan the signal cone
                        .lineToLinearHeading(scanPose)
                        .UNSTABLE_addTemporalMarkerOffset(0, () -> {
                            System.out.println(botType + ": Scanning cone");
                        })
                        .waitSeconds(1)

                        // Drive to the high junction. Place cone
                        .lineToLinearHeading(betweenPose)
                        .lineToLinearHeading(junctionPose)
                        .UNSTABLE_addTemporalMarkerOffset(0, () -> {
                            System.out.println(botType + ": Placing cone");
                        })
                        .waitSeconds(1)

                        // Drive to the cone stack. Pick up a cone
                        .lineToLinearHeading(coneStackPose)
                        .UNSTABLE_addTemporalMarkerOffset(0, () -> {
                            System.out.println(botType + ": Picking up cone");
                        })
                        .waitSeconds(1)

                        // Drive to the high junction. Place cone
                        .lineToLinearHeading(junctionPose)
                        .UNSTABLE_addTemporalMarkerOffset(0, () -> {
                            System.out.println(botType + ": Placing cone");
                        })
                        .waitSeconds(1)

                        // Drive to signal zone
                        .lineToLinearHeading(betweenPose)
                        .lineToLinearHeading(signalZonePoses.get(1))

                        .build());
    }

    public static void main(String[] args) {
        MeepMeep meepMeep = new MeepMeep(600);

        RoadRunnerBotEntity redLeftBot = createBot("RedLeft", meepMeep);
        RoadRunnerBotEntity redRightBot = createBot("RedRight", meepMeep);
        RoadRunnerBotEntity blueLeftBot = createBot("BlueLeft", meepMeep);
        RoadRunnerBotEntity blueRightBot = createBot("BlueRight", meepMeep);

        meepMeep.setBackground(MeepMeep.Background.FIELD_POWERPLAY_OFFICIAL)
                .setDarkMode(true)
                .setBackgroundAlpha(0.95f)
                .addEntity(redLeftBot)
                .addEntity(redRightBot)
                .addEntity(blueLeftBot)
                .addEntity(blueRightBot)
                .start();
    }
}