package edu.stuy.subsystems;

import edu.stuy.Constants;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.AnalogChannel;
import edu.stuy.util.Gamepad;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Talon;

public class Shooter {

    private boolean retracting = false;
    private AnalogChannel goalSensor;
    private Talon chooChoo;
    //private Encoder winchEncoder;
    private static Shooter instance;
    private DigitalInput ballSensor;
    private DigitalInput ballCenteredSwitch;
    private DigitalInput catapultRetractedSwitch;
    private long startTime = System.currentTimeMillis();

    private Shooter() {
        goalSensor = new AnalogChannel(Constants.GOAL_SENSOR_CHANNEL);
        chooChoo = new Talon(Constants.SHOOTER_CHANNEL);
        /*winchEncoder = new Encoder(Constants.WINCH_ENCODER_CHANNEL_A, Constants.WINCH_ENCODER_CHANNEL_B);
         winchEncoder.start();
         winchEncoder.reset(); */
        ballSensor = new DigitalInput(Constants.BALL_SENSOR_CHANNEL);
        ballCenteredSwitch = new DigitalInput(Constants.BALL_CENTERED_SWITCH_CHANNEL);
        catapultRetractedSwitch = new DigitalInput(Constants.CATAPULT_RETRACTED_SWITCH_CHANNEL);
    }

    public static Shooter getInstance() {
        if (instance == null) {
            instance = new Shooter();
        }
        return instance;
    }

    public void reset() {
        retractWinchIfCan();
    }

    public void fireBall() {
        if (isFullyRetracted() && !retracting) {
            chooChoo.set(1.0);
            Timer.delay(Constants.SHOOTER_DELAY_FOR_FIRE);
            chooChoo.set(0.0);
        }
    }

    public void retractWinchIfCan() {
        if (retracting && !isFullyRetracted() && (System.currentTimeMillis() - startTime) < Constants.SHOOTER_RETRACT_TIMEOUT) {
            chooChoo.set(1.0);
        }
        else {
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
        return ballSensor.get();
    }

    public boolean isBallCentered() {
        return !ballCenteredSwitch.get(); //closed switch is false
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
        double voltage = goalSensor.getAverageVoltage();
        return (voltage >= Constants.SHOOTER_GOAL_SENSOR_VOLTAGE);
    }

    public void manualGamepadControl(Gamepad gamepad) {
        if (gamepad.getRightBumper()) {
            fireBall();
        } else if (gamepad.getStartButton()) {
            stopWinch();
        } else if (gamepad.getLeftBumper()) {
            initiateWinch();
        } else if (gamepad.getLeftY() > 0) {
            chooChoo.set(gamepad.getLeftY());
        } else if (gamepad.getLeftY() <= 0) {
            chooChoo.set(0);
        }
        retractWinchIfCan();
    }

    public void testChooChoo(Gamepad gamepad) {
        if (gamepad.getLeftButton()) {
            chooChoo.set(1);
        } else if (gamepad.getBottomButton()) {
            chooChoo.set(0);
        }
    }
}
