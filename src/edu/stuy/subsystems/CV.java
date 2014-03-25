package edu.stuy.subsystems;

import edu.stuy.Constants;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj.Timer;

public class CV {

    private static CV instance;
    private Relay cameraLight;
    private DigitalInput cvGoalSensor;

    public CV() {
        cameraLight = new Relay(Constants.CAMERA_RETICLE_SWITCH);
        System.out.println("CV constructor start");
        cvGoalSensor = new DigitalInput(Constants.GOAL_SENSOR_DIGITAL_CHANNEL);
        System.out.println("CV constructor end");
    }

    public static CV getInstance() {
        if (instance == null) {
            instance = new CV();
        }
        return instance;
    }

    public boolean isGoalHot() {
        return cvGoalSensor.get();
    }

    public boolean getLightValue() {
        if (cameraLight.get() == Relay.Value.kOff) {
            return false;
        } else {
            return true;
        }
    }

    public void setCameraLight(boolean on) {
        Relay.Value currVal = cameraLight.get();
        if (currVal != Relay.Value.kOn && on) { // Turn camera light on
            cameraLight.set(Relay.Value.kForward);
        } else { // Turn camera light off 
            cameraLight.set(Relay.Value.kOff);
        }
    }
}