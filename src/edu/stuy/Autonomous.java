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
        }
    }

    // Empty auton for testing purposes
    public static void auton0() {
        // Do nothing
    }

    // Auton set that relies on CV
    public static void auton1() {
        shootIfHotCV();
        driveBackward();
    }

    public static void auton2() {
        startLoadingNextBall();
        shootIfHotCV();
        finishLoadingNextBall();
        shoot();
        driveBackward();
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
        //shootIfHotAnalog(); // TODO: uncomment me
        driveBackward();
    }

    public static void auton5() {
        startLoadingNextBall();
        //shootIfHotAnalog(); // TODO: uncomment me
        finishLoadingNextBall();
        shoot();
        driveBackward();
    }

    public static void auton6() {
//        if (!Shooter.getInstance().isGoalHot()) { // TODO: uncomment me
//            Timer.delay(Constants.AUTON_TIME_TO_WAIT_FOR_SWITCH_TO_HOT_GOAL);
//        }
        driveForward(Constants.AUTON_TIME_TO_DRIVE_18_FEET);
        lowShoot();
    }

    // Auton set for dumb firing
    public static void auton7() {
        shoot();
        driveBackward();
    }

    public static void auton8() {
        startLoadingNextBall();
        shoot();
        finishLoadingNextBall();
        shoot();
        driveBackward();
    }

    // One point auton with dumb fire while acquirer is up
    public static void auton9() {
        driveForward(Constants.AUTON_TIME_TO_DRIVE_18_FEET);
        lowShoot();
    }

    // Auton for just moving forward to get mobility points
    public static void auton10() {
        driveBackward();
    }

    // Wait for CV to say goal is hot and then shoot
    public static void shootIfHotCV() {
        if (CV.getInstance().isGoalHot()) {
            shoot();
        } else {
            Timer.delay(Constants.AUTON_TIME_TO_WAIT_FOR_SWITCH_TO_HOT_GOAL);
            shoot();
        }
    }

    public static void lowShootIfHotCV() {
        if (CV.getInstance().isGoalHot()) {
            lowShoot();
        } else {
            Timer.delay(Constants.AUTON_TIME_TO_WAIT_FOR_SWITCH_TO_HOT_GOAL);
            shoot();
        }
    }

    public static void lowShootAcquirerUpIfHotCV() {
        if (CV.getInstance().isGoalHot()) {
            lowShoot();
        } else {
            Timer.delay(Constants.AUTON_TIME_TO_WAIT_FOR_SWITCH_TO_HOT_GOAL);
            shoot();
        }
    }

    // TODO: uncomment these
//    public static void shootIfHotAnalog() {
//        if (Shooter.getInstance().isGoalHot()) {
//            shoot();
//        } else {
//            Timer.delay(Constants.AUTON_TIME_TO_WAIT_FOR_SWITCH_TO_HOT_GOAL);
//            shoot();
//        }
//    }
//    public static void lowShootIfHotAnalog() {
//        if (Shooter.getInstance().isGoalHot()) {
//            lowShoot();
//        } else {
//            Timer.delay(Constants.AUTON_TIME_TO_WAIT_FOR_SWITCH_TO_HOT_GOAL);
//            lowShoot();
//        }
//    }
    
    // Shoot without CV
    public static void shoot() {
        Shooter.getInstance().fireBall();
    }

    public static void lowShoot() {
        Acquirer.getInstance().ejectBall();
    }

    public static void driveBackward() {
        // Delay for a short time in case we just shot a ball
        Timer.delay(Constants.AUTON_DELAY_BETWEEN_SHOOT_AND_DRIVE);
        Acquirer.getInstance().rotateUp();
        // TODO: These numbers will require tuning
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

    public static void startLoadingNextBall() {
        Acquirer.getInstance().rotateDown();
        Acquirer.getInstance().intakeBall();
        Timer.delay(1.0); // TODO: Delay should be tuned to ready next ball, but not load until first ball is fired
        Acquirer.getInstance().stopRoller();
    }

    public static void finishLoadingNextBall() {
        if (Shooter.getInstance().isFullyRetracted()) {
            Acquirer.getInstance().intakeBall();
            Timer.delay(1.0); // TODO: Delay should be tuned
        }
        Acquirer.getInstance().stopRoller();
    }
}
