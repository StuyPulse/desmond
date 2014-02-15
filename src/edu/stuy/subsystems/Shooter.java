package edu.stuy.subsystems;

import edu.stuy.Constants;
import edu.wpi.first.wpilibj.Timer;
import edu.stuy.util.Gamepad;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Talon;

public class Shooter {

    private boolean retracting = false;
    private boolean automaticRetract = false;
    private static Shooter instance;
    private DigitalInput catapultRetractedSwitch;
    private Talon chooChoo;
    private long startTime = System.currentTimeMillis();
    //private AnalogChannel goalSensor; // Dan's magic CV
    //private Encoder winchEncoder;
    //private DigitalInput ballSensor;
    //private DigitalInput ballCenteredSwitch;

    private Shooter() {
        //goalSensor = new AnalogChannel(Constants.GOAL_SENSOR_CHANNEL);
        chooChoo = new Talon(Constants.SHOOTER_CHANNEL);
        /*winchEncoder = new Encoder(Constants.WINCH_ENCODER_CHANNEL_A, Constants.WINCH_ENCODER_CHANNEL_B);
         winchEncoder.start();
         winchEncoder.reset(); */
        //ballSensor = new DigitalInput(Constants.BALL_SENSOR_CHANNEL);
        //ballCenteredSwitch = new DigitalInput(Constants.BALL_CENTERED_SWITCH_CHANNEL);
        catapultRetractedSwitch = new DigitalInput(Constants.CATAPULT_RETRACTED_SWITCH_CHANNEL);
    }

    public static Shooter getInstance() {
        if (instance == null) {
            instance = new Shooter();
        }
        return instance;
    }

    public void reset() {
        retractWinch();
    }

    public void fireBall() {
        if (isFullyRetracted() && !retracting) {
            chooChoo.set(-1.0);
            Timer.delay(Constants.SHOOTER_DELAY_FOR_FIRE);
            chooChoo.set(0.0);
            Timer.delay(Constants.SHOOTER_DELAY_FOR_RETRACT);
            initiateWinch();
        }
    }

    public void retractWinch() {
        if (!isFullyRetracted() && (System.currentTimeMillis() - startTime) < Constants.SHOOTER_RETRACT_TIMEOUT) {
            chooChoo.set(-1.0); // This MUST be negative!
        } else {
            stopWinch();
        }
    }

    public void initiateWinch() {
        retracting = true;
        startTime = System.currentTimeMillis();
    }

    public void stopWinch() {
        chooChoo.set(0);
        retracting = false;
    }
    /*
     public int getAngle() {
     double pulses = winchEncoder.get() % Constants.PULSES_PER_REVOLUTION;
     double angle = pulses / Constants.PULSES_PER_REVOLUTION * 360;
     return (int) angle;
     }
     */

    public boolean hasBall() {
        return true;//ballSensor.get();
    }

    public boolean isStillRetracting() {
        return retracting;
    }

    public boolean isBallCentered() {
        return true;
//!ballCenteredSwitch.get(); //closed switch is false
    }

    public boolean isFullyRetracted() {
        return !catapultRetractedSwitch.get(); //closed switch is false
    }

    public boolean readyToShoot() {
        /*int angle = getAngle();
         return (angle < 340 && angle > 380-Constants.DEGREES_WINCH_RETRACT);
         */
        return isFullyRetracted();
    }

    public boolean isGoalHot() {
        //double voltage = goalSensor.getAverageVoltage();
        return true;//(voltage >= Constants.SHOOTER_GOAL_SENSOR_VOLTAGE);
    }

    public void manualGamepadControl(Gamepad gamepad) { 
        if (gamepad.getLeftBumper()) {
            initiateWinch();
            System.out.println("Choo choo initiated.");
        }
        
        if (gamepad.getRightBumper()) {
            fireBall();
            System.out.println("Firing ball.");
        } else if (gamepad.getStartButton()) {
            stopWinch();
            System.out.println("Winch manually stopped.");
        }
        
        if (gamepad.getLeftY() > 0) {
            chooChoo.set(-gamepad.getLeftY()); // The analog stick Y increases as it is pulled downwards
            System.out.println(gamepad.getLeftY());
            System.out.println("Running choo choo with analog.");
        } else if (gamepad.getLeftY() <= 0 && !retracting) {
            chooChoo.set(0);
            System.out.println("Choo choo stopped because of analog sticks.");
        }
        
        // if there is a retract request
        if (retracting) {
            retractWinch();
        }
    }
}