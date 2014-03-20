package edu.stuy;

import edu.stuy.subsystems.*;
import edu.wpi.first.wpilibj.Timer;

public class Autonomous {

    public static void auton(int x) {
        switch (x) {
            case 0:
                auton0(); // Do nothing
                break;
            case 1: // Shooter faces goal
                auton1(); // CV- one ball, drive forward
                break;
            case 2: // Shooter faces goal
                auton2(); // CV- two balls, drive forward
                break;
            case 3: // Acquirer faces goal
                auton3(); // CV- low goal
                break;
            case 4: // Shooter faces goal
                auton4(); // Analog light sensor- 1 ball, drive forward
                break;
            case 5: // Shooter faces goal
                auton5(); // Analog light sensor- 2 balls, drive forward
                break;
            case 6: // Acquirer faces goal
                auton6(); // Analog light sensor- low goal
                break;
            case 7: // Shooter faces goal
                auton7(); // Dumb fire high goal- 1 time
                break;
            case 8: // Shooter faces goal
                auton8(); // Dumb fire high goal- 2 times
                break;
            case 9: // Acquirer faces goal
                auton9(); // Dumb fire low goal
                break;
            case 10: // Shooter faces goal
                auton10(); // Just drive backward
                break;
            case 11: // Shooter faces goal
                auton11(); // Dumb fire high goal - 3 ball
                break;
        }
    }

    // Empty auton for testing purposes
    public static void auton0() {
        // Do nothing
    }

    // Auton set that relies on CV
    public static void auton1() {
        shootIfHotCV();
        driveBackwardForMobilityPoints();
    }

    public static void auton2() {
        shootIfHotCV();
        readyShooter();
        loadNextBall();
        shoot();
        driveBackwardForMobilityPoints();
    }

    public static void auton3() {
        if (!CV.getInstance().isGoalHot()) {
            Timer.delay(Constants.AUTON_TIME_TO_WAIT_FOR_SWITCH_TO_HOT_GOAL);
        }
        driveForward(Constants.AUTON_TIME_TO_DRIVE_18_FEET);
        lowShoot();
    }

    // Auton set for using Analog light sensor instead of CV
    public static void auton4() {
        delayForDanCam();
        shootIfHotAnalog();
        driveBackwardForMobilityPoints();
    }

    public static void auton5() {
        delayForDanCam();
        shootIfHotAnalog();
        readyShooter();
        loadNextBall();
        shoot();
        driveBackwardForMobilityPoints();
    }

    public static void auton6() {
        if (!Shooter.getInstance().isGoalHotDigital()) {
            Timer.delay(Constants.AUTON_TIME_TO_WAIT_FOR_SWITCH_TO_HOT_GOAL);
        }
        driveForward(Constants.AUTON_TIME_TO_DRIVE_18_FEET);
        lowShoot();
    }

    // Auton set for dumb firing
    public static void auton7() {
        extendAndShoot();
        driveBackwardForMobilityPoints();
        readyShooter();
    }

    // One ball is shot hot, another is not
    public static void auton8() {
        extendAndShoot();
        readyShooter();
        loadNextBall();
        Timer.delay(1.5); // This should remain in order to ensure separation
        shoot();
        driveBackwardForMobilityPoints();
        readyShooter();
    }

    // One point auton with dumb fire while acquirer is up
    public static void auton9() {
        driveForward(Constants.AUTON_TIME_TO_DRIVE_18_FEET);
        lowShoot();
    }

    // Auton for just moving forward to get mobility points
    public static void auton10() {
        driveBackwardForMobilityPoints();
    }

    public static void auton11() {
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
        Shooter.getInstance().fireBallAndRetract();
    }

    public static void shoot() {
        Shooter.getInstance().fireBallAndRetract();
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
