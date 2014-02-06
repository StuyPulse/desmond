package edu.stuy;

import edu.stuy.subsystems.*;
import edu.wpi.first.wpilibj.Timer;

public class Autonomous {
    
    public static void auton(int x) {
        switch(x) {
            case 0:
                auton0(); // Do nothing
                break;
            case 1:
                auton1(); // CV
                break;
            case 2:
                auton2(); // CV
                break;
            case 3:
                auton3(); // Analog light sensor 
                break;
            case 4:
                auton4(); // Analog light sensor 
                break;
            case 5:
                auton5(); // Dumb fire
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
        driveForward();
    }
    
    public static void auton2() {
        readyNextBall();
        // There may not be enough time to check if the goal is hot AND shoot both balls
        shootIfHotCV();
        finishLoadingNextBall();
        shoot();
        driveForward();
    }
    
    // Auton sets for using Analog light sensor instead of CV
    public static void auton3() {
        shootIfHotAnalog();
        driveForward();
    }
    
    public static void auton4() {
        readyNextBall();
        // There may not be enough time to check if the goal is hot AND shoot both balls
        shootIfHotAnalog();
        finishLoadingNextBall();
        shoot();
        driveForward();
    }

    // Auton for dumb firing, without CV/light sensor
    public static void auton5() {
        readyNextBall();
        shoot();
        finishLoadingNextBall();
        shoot();
        driveForward();
    }
    
    
    // Wait for CV to say goal is hot and then shoot
    public static void shootIfHotCV() {
        if (CV.getInstance().isGoalHot()) {
            shoot();
        }
        else {
            Timer.delay(5.5);
            shoot();
        }
    }
    
    public static void shootIfHotAnalog() {
        if (Shooter.getInstance().isGoalHot()) {
            shoot();
        } else {
            Timer.delay(5.5);
            shoot();
        }
    }
    
    // Shoot without CV
    public static void shoot() {
        Shooter.getInstance().releaseWinch();
        Timer.delay(0.5);
        Shooter.getInstance().retractWinch();
    }
    
    // Drive forward
    public static void driveForward() {
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
    
    public static void readyNextBall() {
        Acquirer.getInstance().rotateDown();
        Acquirer.getInstance().intakeBall();
        Timer.delay(1.0); // Delay should be tuned to ready next ball, but not load until first ball is fired
        Acquirer.getInstance().stopRoller();
    }
    
    public static void finishLoadingNextBall() {
        if (!Shooter.getInstance().hasBall()) { // Finish loading next ball
            Acquirer.getInstance().intakeBall();
            Timer.delay(1.0); // Delay should be tuned
        }
        Acquirer.getInstance().stopRoller();
    }
}
