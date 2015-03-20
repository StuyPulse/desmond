package edu.stuy.subsystems;

import edu.stuy.Constants;
import edu.wpi.first.wpilibj.Timer;
import edu.stuy.util.Gamepad;
import edu.wpi.first.wpilibj.AnalogChannel;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.Relay;

public class Shooter {

    private boolean retracting = false;
    private boolean reticleToggling, reticleWasToggled;
    private static Shooter instance;
    private DigitalInput catapultRetractedSwitch;
    private Talon chooChoo;
    private long startTime = System.currentTimeMillis();
    private AnalogChannel goalSensorAnalog; // Dan's magic CV
    private DigitalInput goalSensorDigital;
    private Relay cameraReticleSwitch; // For the light alternation

    private Shooter() {
        goalSensorAnalog = new AnalogChannel(Constants.GOAL_SENSOR_ANALOG_CHANNEL);
        goalSensorDigital = new DigitalInput(Constants.GOAL_SENSOR_DIGITAL_CHANNEL);
        chooChoo = new Talon(Constants.SHOOTER_CHANNEL);
        catapultRetractedSwitch = new DigitalInput(Constants.CATAPULT_RETRACTED_SWITCH_CHANNEL);
        cameraReticleSwitch = new Relay(Constants.CAMERA_RETICLE_SWITCH);
    }

    public static Shooter getInstance() {
        if (instance == null) {
            instance = new Shooter();
        }
        return instance;
    }

    public void reset() {
        enableReticleLight();
    }

    public void fireBall() {
        chooChoo.set(-1.0);
        long startTime = System.currentTimeMillis();
        while (isFullyRetracted() && System.currentTimeMillis() - startTime < Constants.SHOOTER_FIRE_TIMEOUT);
        chooChoo.set(0.0);
    }

    public void fireBallAndRetract() {
        fireBall();
        initiateWinch();
    }

    public void fireBallPastBackdrive() {
        initiateWinch();
        while (!isFullyRetracted() && (System.currentTimeMillis() - startTime < Constants.SHOOTER_RETRACT_TIMEOUT)) {
            chooChoo.set(-1.0); // This MUST be negative!
        }
        stopWinch();
        fireBall();
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

    public void enableCameraLight() {
        cameraReticleSwitch.set(Relay.Value.kForward);
    }

    public void enableReticleLight() {
        cameraReticleSwitch.set(Relay.Value.kReverse);
    }

    public void disableCameraAndReticleLights() {
        cameraReticleSwitch.set(Relay.Value.kOff);
    }

    public void toggleReticle() {
        if (cameraReticleSwitch.get() == Relay.Value.kReverse) {
            disableCameraAndReticleLights();
        } else {
            enableReticleLight();
        }
    }

    public boolean isStillRetracting() {
        return retracting;
    }

    public boolean isFullyRetracted() {
        return !catapultRetractedSwitch.get(); //closed switch is false
    }

    public double getGoalVoltage() {
        return goalSensorAnalog.getAverageVoltage();
    }

    public boolean isGoalHotAnalog() {
        return getGoalVoltage() <= Constants.SHOOTER_GOAL_SENSOR_VOLTAGE; // Sensor is active when low
    }

    public boolean isGoalHotDigital() {
        return goalSensorDigital.get();
    }

    public void manualGamepadControl(Gamepad gamepad) {
        if (gamepad.getBottomButton()) { // The A button retracts the catapult
            initiateWinch();
        }
        if (gamepad.getLeftButton()) { // The X button fires the catapult
            fireBall();
        } else if (gamepad.getStartButton()) {
            stopWinch();
        }

        if (gamepad.getDPadDown()) {
            chooChoo.set(.5); 
        } else if (gamepad.getDPadDown() && !retracting) {
            chooChoo.set(0);
        }

        reticleWasToggled = reticleToggling;
        reticleToggling = gamepad.getSelectButton();

        // if there is a reticle toggle request
        if (reticleToggling && !reticleWasToggled) {
            toggleReticle();
        }

        // if there is a retract request
        if (retracting) {
            retractWinch();
        }
    }
}
