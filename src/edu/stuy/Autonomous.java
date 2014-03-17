package edu.stuy;

import edu.stuy.subsystems.*;
import edu.wpi.first.wpilibj.Timer;

public class Autonomous {

    public static void auton(int x) {
        switch (x) {
            case 00:
                auton00(); // Do nothing
                break;

            case 10: // Shooter faces goal
                auton10(); // CV- one ball, drive forward
                break;
            case 11: // Shooter faces goal
                auton11(); // CV- two balls, drive forward
                break;
            case 12: // Acquirer faces goal
                auton12(); // CV- low goal
                break;

            case 20: // Shooter faces goal
                auton20(); // DV- 1 ball, drive forward
                break;
            case 21: // Shooter faces goal
                auton21(); // DV- 2 balls, drive forward
                break;
            case 22: // Acquirer faces goal
                auton22(); // DV- low goal
                break;

            case 30: // Shooter faces goal
                auton30(); // Dumb fire high goal- 1 time
                break;
            case 31: // Shooter faces goal
                auton31(); // Dumb fire high goal- 2 times
                break;
            case 32: // Acquirer faces goal
                auton32(); // Dumb fire low goal
                break;
            case 33: // Shooter faces goal
                auton33(); // Dumb fire high goal - 3 ball
                break;

            case 40: // Shooter faces goal
                auton40(); // Just drive backward
                break;
        }
    }

    // Empty auton for testing purposes
    public static void auton00() {
        // Do nothing
    }

    // Auton set that relies on CV
    public static void auton10() {
        shootIfHotCV();
        driveBackwardForMobilityPoints();
    }

    public static void auton11() {
        shootIfHotCV();
        readyShooter();
        loadNextBall();
        shoot();
        driveBackwardForMobilityPoints();
    }

    public static void auton12() {
        if (!CV.getInstance().isGoalHot()) {
            Timer.delay(Constants.AUTON_TIME_TO_WAIT_FOR_SWITCH_TO_HOT_GOAL);
        }
        driveForward(Constants.AUTON_TIME_TO_DRIVE_18_FEET);
        lowShoot();
    }

    // Auton set for using Analog light sensor instead of CV
    public static void auton20() {
        Shooter.getInstance().enableCameraLight();
        delayForDanCam();
        shootIfHotAnalog();
        driveBackwardForMobilityPoints();
    }

    public static void auton21() {
        Shooter.getInstance().enableCameraLight();
        delayForDanCam();
        shootIfHotAnalog();
        readyShooter();
        loadNextBall();
        shoot();
        driveBackwardForMobilityPoints();
    }

    public static void auton22() {
        Shooter.getInstance().enableCameraLight();
        if (!Shooter.getInstance().isGoalHotDigital()) {
            Timer.delay(Constants.AUTON_TIME_TO_WAIT_FOR_SWITCH_TO_HOT_GOAL);
        }
        driveForward(Constants.AUTON_TIME_TO_DRIVE_18_FEET);
        lowShoot();
    }

    // Auton set for dumb firing
    public static void auton30() {
        extendAndShoot();
        driveBackwardForMobilityPoints();
        readyShooter();
    }

    // One ball is shot hot, another is not
    public static void auton31() {
        extendAndShoot();
        readyShooter();
        loadNextBall();
        Timer.delay(1.5); // This should remain in order to ensure separation
        shoot();
        driveBackwardForMobilityPoints();
        readyShooter();
    }

    // One point auton with dumb fire while acquirer is up
    public static void auton32() {
        driveForward(Constants.AUTON_TIME_TO_DRIVE_18_FEET);
        lowShoot();
    }

    // Auton for just moving forward to get mobility points
    public static void auton40() {
        driveBackwardForMobilityPoints();
    }

    public static void auton33() {
        extendAndShoot();
        readyShooter();
        loadNextBall();
        Timer.delay(1.5); // This should remain in order to ensure separation
        shoot();
        readyShooter();
        loadBallWhileMoving(Constants.AUTON_THREE_BALL_DRIVE_TIME);
        driveBackward(Constants.AUTON_THREE_BALL_DRIVE_TIME);
        rotateDownToClearShooter();
        shoot();
        driveBackwardForMobilityPoints();
    }

    // Wait for CV to say goal is hot and then shoot
    public static void shootIfHotCV() {
        if (CV.getInstance().isGoalHot()) {
            extendAndShoot();
        } else {
            Timer.delay(Constants.AUTON_TIME_TO_WAIT_FOR_SWITCH_TO_HOT_GOAL);
            extendAndShoot();
        }
    }

    public static void lowShootIfHotCV() {
        if (!(CV.getInstance().isGoalHot())) {
            Timer.delay(Constants.AUTON_TIME_TO_WAIT_FOR_SWITCH_TO_HOT_GOAL);
        }
        lowShoot();
    }

    public static void shootIfHotAnalog() {
        if (Shooter.getInstance().isGoalHotDigital()) {
            Timer.delay(Constants.AUTON_TIME_TO_WAIT_FOR_SWITCH_TO_HOT_GOAL);
        }
        shoot();
    }

    public static void lowShootIfHotAnalog() {
        if (Shooter.getInstance().isGoalHotDigital()) {
            Timer.delay(Constants.AUTON_TIME_TO_WAIT_FOR_SWITCH_TO_HOT_GOAL);
        }
        lowShoot();
    }

    // Shoot without CV
    public static void extendAndShoot() {
        Acquirer.getInstance().rotateDown();
        Timer.delay(Constants.AUTON_TIME_TO_EXTEND_ACQUIRER);
        Shooter.getInstance().fireBall();
    }

    public static void shoot() {
        Shooter.getInstance().fireBall();
    }

    public static void readyShooter() {
        double startTime = System.currentTimeMillis();
        Shooter.getInstance().initiateWinch();
        while (!Shooter.getInstance().isFullyRetracted() && (System.currentTimeMillis() - startTime) < Constants.SHOOTER_RETRACT_TIMEOUT) {
            Shooter.getInstance().retractWinch();
        }
        Shooter.getInstance().stopWinch();
    }

    public static void lowShoot() {
        Acquirer.getInstance().ejectBall();
    }

    public static void driveBackwardForMobilityPoints() {
        // Delay for a short time in case we just shot a ball
        Timer.delay(Constants.AUTON_DELAY_BETWEEN_SHOOT_AND_DRIVE);
        Acquirer.getInstance().rotateUp();
        Drivetrain.getInstance().tankDrive(0.25, 0.25);
        Timer.delay(0.25);
        Drivetrain.getInstance().tankDrive(0.5, 0.5);
        Timer.delay(0.25);
        Drivetrain.getInstance().tankDrive(0.75, 0.75);
        Timer.delay(0.25);
        Drivetrain.getInstance().tankDrive(1, 1);
        Timer.delay(0.25);
        Drivetrain.getInstance().tankDrive(0.5, 0.5);
        Timer.delay(0.25);
        Drivetrain.getInstance().tankDrive(0, 0);
    }

    public static void driveBackward(double time) {
        Drivetrain.getInstance().tankDrive(0.25, 0.25);
        Timer.delay(time / 10);
        Drivetrain.getInstance().tankDrive(0.5, 0.5);
        Timer.delay(time / 5);
        Drivetrain.getInstance().tankDrive(0.75, 0.75);
        Timer.delay((time / 5) * 2);
        Drivetrain.getInstance().tankDrive(0.5, 0.5);
        Timer.delay(time / 5);
        Drivetrain.getInstance().tankDrive(0.25, 0.25);
        Timer.delay(time / 10);
        Drivetrain.getInstance().tankDrive(0, 0);
    }

    public static void driveForward(double time) {
        double partial = time / 2;
        Drivetrain.getInstance().tankDrive(-0.25, -0.25);
        Timer.delay(partial / 6);
        Drivetrain.getInstance().tankDrive(-0.5, -0.5);
        Timer.delay(partial / 6);
        Drivetrain.getInstance().tankDrive(-0.75, -0.75);
        Timer.delay(partial / 6);
        Drivetrain.getInstance().tankDrive(-1, -1);
        Timer.delay(time - partial);
        Drivetrain.getInstance().tankDrive(-0.75, -0.75);
        Timer.delay(partial / 6);
        Drivetrain.getInstance().tankDrive(-0.50, -0.50);
        Timer.delay(partial / 6);
        Drivetrain.getInstance().tankDrive(-0.25, -0.25);
        Timer.delay(partial / 6);
        Drivetrain.getInstance().tankDrive(0, 0);
    }

    public static void rotateDownToClearShooter() {
        Acquirer.getInstance().rotateDown();
    }

    public static void loadBallWhileMoving(double time) {
        Acquirer.getInstance().intakeBall();
        driveForward(time);
        Acquirer.getInstance().stopRoller();
        Acquirer.getInstance().rotateUp();
    }

    public static void loadNextBall() {
        Acquirer.getInstance().rotateDown();
        Acquirer.getInstance().intakeBall();
        Timer.delay(Constants.AUTON_TIME_TO_INTAKE_BALL); // TODO: Delay should be tuned
        Acquirer.getInstance().stopRoller();
    }

    // Accomodate for when the reflexite is rotating
    public static void delayForDanCam() {
        Timer.delay(Constants.DAN_CAMERA_AUTON_DELAY);
    }
}
