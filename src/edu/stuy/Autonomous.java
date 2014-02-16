package edu.stuy;

import edu.stuy.subsystems.*;
import edu.wpi.first.wpilibj.Timer;

public class Autonomous {

    public static void auton(int x) {
        switch (x) {
            case 0:
                auton0(); // Do nothing
                break;
            case 1:
                auton1(); // CV- one ball, drive forward
                break;
            case 2:
                auton2(); // CV- two balls, drive forward
                break;
            case 3:
                auton3(); // CV- drive forward, low goal
                break;
            case 4:
                auton4(); // Analog light sensor- 1 ball, drive forward
                break;
            case 5:
                auton5(); // Analog light sensor- 2 balls, drive forward
                break;
            case 6:
                auton6(); // Analog light sensor- drive forward, low goal
                break;
            case 7:
                auton7(); // Dumb fire with no CV- shoots one time
                break;
            case 8:
                auton8(); // Dumb fire with no CV- shoots two times
                break;
            case 9:
                auton9(); // Dumb fire with no CV- low goal
                break;
            case 10:
                auton10(); // Just move forward
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
        readyNextBall();
        // There may not be enough time to check if the goal is hot AND shoot both balls
        shootIfHotCV();
        finishLoadingNextBall();
        shoot();
        driveBackward();
    }

    public static void auton3() {
        driveForward(2);
        driveBackward(.5);
        lowShootIfHotCV();
    }

    // Auton sets for using Analog light sensor instead of CV
    public static void auton4() {
        //shootIfHotAnalog();
        driveBackward();
    }

    public static void auton5() {
        readyNextBall();
        // There may not be enough time to check if the goal is hot AND shoot both balls
        //shootIfHotAnalog();
        finishLoadingNextBall();
        shoot();
        driveBackward();
    }

    public static void auton6() {
        driveForward(2);
        driveBackward(.5);
        //lowShootIfHotAnalog();
    }

    // Auton for dumb firing (one ball), without CV/light sensor
    public static void auton7() {
        shoot();
        driveBackward();
    }

    // Auton for dumb firing (two balls), without CV/light sensor
    public static void auton8() {
        readyNextBall();
        shoot();
        finishLoadingNextBall();
        shoot();
        driveBackward();
    }
    
    // One point auton with dumb fire
    public static void auton9() {
        driveForward(2);
        driveBackward(.5);
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
            Timer.delay(4.5);
            shoot();
        }
    }

    public static void lowShootIfHotCV() {
        if (CV.getInstance().isGoalHot()) {
            lowShoot();
        } else {
            Timer.delay(4.5);
            shoot();
        }
    }
    /*
     public static void shootIfHotAnalog() {
     if (Shooter.getInstance().isGoalHot()) {
     shoot();
     } else {
     Timer.delay(4.5);
     shoot();
     }
     }
     */

//    public static void lowShootIfHotAnalog() {
//        if (Shooter.getInstance().isGoalHot()) {
//            lowShoot();
//        } else {
//            Timer.delay(4.5);
//            lowShoot();
//        }
//    }

    // Shoot without CV
    public static void shoot() {
        Shooter.getInstance().fireBall();
    }

    public static void lowShoot() {
        Acquirer.getInstance().rotateDown();
        Acquirer.getInstance().ejectBall();
    }
    
    // Drive forward
    public static void driveBackward() {
        System.out.println("Driving backward at " + System.currentTimeMillis());
        // Delay for a short time in case we just shot a ball
        Timer.delay(Constants.AUTON_DELAY_BETWEEN_SHOOT_AND_DRIVE);
        Acquirer.getInstance().rotateUp();
        // These numbers will require tuning
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
        Timer.delay(time/5);
        Drivetrain.getInstance().tankDrive(0.5, 0.5);
        Timer.delay(time/5);
        Drivetrain.getInstance().tankDrive(0.75, 0.75);
        Timer.delay(time/5);
        Drivetrain.getInstance().tankDrive(0.5, 0.5);
        Timer.delay(time/5);
        Drivetrain.getInstance().tankDrive(0.25, 0.25);
        Timer.delay(time/5);
        Drivetrain.getInstance().tankDrive(0, 0);
    }
    
    public static void driveForward(double time) {
        Drivetrain.getInstance().tankDrive(-0.25, -0.25);
        Timer.delay(time/3);
        Drivetrain.getInstance().tankDrive(-0.5, -0.5);
        Timer.delay(time/3);
        Drivetrain.getInstance().tankDrive(-0.25, -0.25);
        Timer.delay(time/3);
        Drivetrain.getInstance().tankDrive(0, 0);
    }

    public static void readyNextBall() {
        Acquirer.getInstance().rotateDown();
        Acquirer.getInstance().intakeBall();
        Timer.delay(1.0); // Delay should be tuned to ready next ball, but not load until first ball is fired
        Acquirer.getInstance().stopRoller();
    }

    public static void finishLoadingNextBall() {
        if (Shooter.getInstance().isFullyRetracted()) {
            Acquirer.getInstance().intakeBall();
            Timer.delay(1.0); // Delay should be tuned
        }
        Acquirer.getInstance().stopRoller();
    }
}
