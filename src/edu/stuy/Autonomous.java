package edu.stuy;

import edu.stuy.subsystems.*;
import edu.wpi.first.wpilibj.Timer;

public class Autonomous {

    public static void auton(int x) {
        switch (x) {
            case 00:
                auton00(); // Do nothing
                break;
            case 01:
                auton01(); // Open acquirer
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
            case 34: // Shooter faces goal
                auton34(); // Dumb fire high goal - 2 ball, drag
                break;

            case 40: // Shooter faces goal
                auton40(); // Just drive backward
                break;
            case 41: // Shooter faces goal
                auton41(); // Just drive backward twice as far
                break;

            case 50: // Shooter faces goal
                auton50(); // Encoder fire high goal - 1 ball
                break;
            case 51: // Shooter faces goal
                auton51(); // Encoder fire high goal - 2 ball, drag
                break;
        }
    }

    // Empty auton for testing purposes
    public static void auton00() {
        // Do nothing
    }
    
    // Open acquirer
    public static void auton01() {
        Acquirer.getInstance().rotateDown();
    }

    // Auton set that relies on CV
    public static void auton10() {
        Acquirer.getInstance().rotateDown();
        shootIfHotCV();
    }

    public static void auton11() {
        Acquirer.getInstance().rotateDown();
        shootIfHotCV();
        readyShooter();
        loadNextBall();
        driveBackwardToLineUpShot();
        readyAndShoot();
    }

    public static void auton12() {
        if (!CV.getInstance().isGoalHot()) {
            Timer.delay(Constants.AUTON_TIME_FOR_CV_TO_WAIT_FOR_SWITCH_TO_HOT_GOAL);
        }
        driveForward(Constants.AUTON_TIME_TO_DRIVE_18_FEET);
        lowShoot();
    }

    // Auton set for using Analog light sensor instead of CV
    public static void auton20() {
        Shooter.getInstance().enableCameraLight();
        Acquirer.getInstance().rotateDown();
        delayForDanCam();
        shootIfHotAnalog();
    }

    public static void auton21() {
        Shooter.getInstance().enableCameraLight();
        Acquirer.getInstance().rotateDown();
        delayForDanCam();
        shootIfHotAnalog();
        readyShooter();
        loadNextBall();
        driveBackwardToLineUpShot();
        readyAndShoot();
    }

    public static void auton22() {
        Shooter.getInstance().enableCameraLight();
        Acquirer.getInstance().rotateDown();
        if (!Shooter.getInstance().isGoalHotDigital()) {
            Timer.delay(Constants.AUTON_TIME_FOR_DV_TO_WAIT_FOR_SWITCH_TO_HOT_GOAL);
        }
        driveForward(Constants.AUTON_TIME_TO_DRIVE_18_FEET);
        lowShoot();
    }

    // Auton set for dumb firing
    public static void auton30() {
        driveBackwardToLineUpShot();
        extendAndSettleAndShoot();
        readyShooter();
    }

    // One ball is shot hot, another is not
    public static void auton31() {
        driveBackwardToLineUpShot();
        extendAndSettleAndShoot();
        loadBallWhileMoving(Constants.AUTON_TIME_TO_LINE_UP_SHOT); // Reverse direction and pick up second ball
        driveBackwardToLineUpShot();
        Timer.delay(1.5);
        readyShooter();
        extendAndSettleAndShoot();
    }

    // One point auton with dumb fire while acquirer is up
    public static void auton32() {
        driveForward(Constants.AUTON_TIME_TO_DRIVE_18_FEET);
        lowShoot();
    }

    public static void auton33() {
        extendAndSettleAndShoot();
        readyShooter();
        loadNextBall();
        driveBackwardToLineUpShot();
        Timer.delay(1.5); // This should remain in order to ensure separation
        readyAndShoot();
        readyShooter();
        loadBallWhileMoving(Constants.AUTON_THREE_BALL_DRIVE_TIME + Constants.AUTON_TIME_TO_LINE_UP_SHOT);
        driveBackward(Constants.AUTON_THREE_BALL_DRIVE_TIME + Constants.AUTON_TIME_TO_LINE_UP_SHOT);
        rotateDownToClearShooter();
        readyAndShoot();
    }

	// two ball auton where the second ball is dragged with the acquirer
    public static void auton34() {
	Acquirer.getInstance().rotateDown();
        Timer.delay(Constants.AUTON_TIME_TO_EXTEND_ACQUIRER);
	Acquirer.getInstance().intakeHalfSpeed();
	driveBackwardAndDragToLineUpShot();
	Acquirer.getInstance().stopRoller(); // prevent second ball from interfering with
        Timer.delay(Constants.SHOOTER_DELAY_FOR_BALL_SETTLE);
	Shooter.getInstance().fireBall();
        Timer.delay(Constants.SHOOTER_DELAY_FOR_FIRE);
        readyShooter();
	loadNextBall();
        Timer.delay(Constants.SHOOTER_DELAY_FOR_BALL_SETTLE);
	Shooter.getInstance().fireBallPastBackdrive();
        Shooter.getInstance().initiateWinch();
    }

    // Auton for just moving forward to get mobility points
    public static void auton40() {
        driveBackwardForMobilityPoints();
    }

    // Auton for moving doubly far if we're donating the ball for 2-ball auton
    public static void auton41() {
        driveBackwardForMobilityPoints();
        driveBackwardForMobilityPoints();
    }

    // Auton set that relies on the encoders
    public static void auton50() {
        driveBackwardToSweetSpotUsingEncoders();
        extendAndSettleAndShoot();
        readyShooter();
    }

    // two ball auton where the second ball is dragged with the acquirer
    public static void auton51() {
	Acquirer.getInstance().rotateDown();
        Timer.delay(Constants.AUTON_TIME_TO_EXTEND_ACQUIRER);
	Acquirer.getInstance().intakeHalfSpeed();
	driveBackwardToSweetSpotUsingEncoders();
	Acquirer.getInstance().stopRoller(); // prevent second ball from interfering with
        Timer.delay(Constants.SHOOTER_DELAY_FOR_BALL_SETTLE);
	Shooter.getInstance().fireBall();
        Timer.delay(Constants.SHOOTER_DELAY_FOR_FIRE);
        readyShooter();
	loadNextBall();
        Timer.delay(Constants.SHOOTER_DELAY_FOR_BALL_SETTLE);
	Shooter.getInstance().fireBallPastBackdrive();
        Shooter.getInstance().initiateWinch();
    }




    // Wait for CV to say goal is hot and then shoot
    public static void shootIfHotCV() {
        if (CV.getInstance().isGoalHot()) {
            readyAndShoot();
        } else {
            Timer.delay(Constants.AUTON_TIME_FOR_CV_TO_WAIT_FOR_SWITCH_TO_HOT_GOAL);
            readyAndShoot();
        }
    }

    public static void lowShootIfHotCV() {
        if (!(CV.getInstance().isGoalHot())) {
            Timer.delay(Constants.AUTON_TIME_FOR_CV_TO_WAIT_FOR_SWITCH_TO_HOT_GOAL);
        }
        lowShoot();
    }

    public static void shootIfHotAnalog() {
        if (Shooter.getInstance().isGoalHotDigital()) {
            Timer.delay(Constants.AUTON_TIME_FOR_DV_TO_WAIT_FOR_SWITCH_TO_HOT_GOAL);
        }
        readyAndShoot();
    }

    public static void lowShootIfHotAnalog() {
        if (Shooter.getInstance().isGoalHotDigital()) {
            Timer.delay(Constants.AUTON_TIME_FOR_DV_TO_WAIT_FOR_SWITCH_TO_HOT_GOAL);
        }
        lowShoot();
    }

    // Shoot without CV
    public static void extendAndShoot() {
        Acquirer.getInstance().rotateDown();
        Timer.delay(Constants.AUTON_TIME_TO_EXTEND_ACQUIRER);
        readyAndShoot();
    }
    
    public static void close() {
        Acquirer.getInstance().rotateUp();
        Timer.delay(Constants.AUTON_TIME_TO_EXTEND_ACQUIRER);
    }

    public static void extendAndSettleAndShoot() {
        Acquirer.getInstance().rotateDown();
        Timer.delay(Constants.AUTON_TIME_TO_EXTEND_ACQUIRER);
        Timer.delay(Constants.SHOOTER_DELAY_FOR_BALL_SETTLE);
        readyAndShoot();
    }

    public static void readyAndShoot() {
        readyShooter();
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

    public static void driveBackwardToLineUpShot() {
        driveBackward(Constants.AUTON_TIME_TO_LINE_UP_SHOT);
    }

    public static void driveBackwardAndDragToLineUpShot() {
        driveBackward(Constants.AUTON_TIME_TO_LINE_UP_DRAG_SHOT);
    }

    public static void driveBackwardToSweetSpotUsingEncoders() {
        double inchesToTravel = Constants.AUTON_FEET_TO_SWEET_SPOT * 12;
        double initialLocus = Drivetrain.getInstance().getLeftEnc();
        boolean finishedTraveling = false;
        while (!finishedTraveling) {
            double inchesTraveled = Math.abs(Drivetrain.getInstance().getLeftEnc() - initialLocus);
            finishedTraveling = inchesTraveled >= inchesToTravel;
            Drivetrain.getInstance().tankDrive(0.1, 0.1);
        }
        Drivetrain.getInstance().tankDrive(0.0, 0.0);
    }

    public static void driveForwardToUnlineUpShot() {
        driveForward(Constants.AUTON_TIME_TO_LINE_UP_SHOT);
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
        close();
        Acquirer.getInstance().stopRoller();
    }

    // Accomodate for when the reflexite is rotating
    public static void delayForDanCam() {
        Timer.delay(Constants.DAN_CAMERA_AUTON_DELAY);
    }
}
