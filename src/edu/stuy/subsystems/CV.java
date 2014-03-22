package edu.stuy.subsystems;

import edu.stuy.util.NetworkIO;
import edu.stuy.Constants;
import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj.Timer;

public class CV {

    private static CV instance;
    private static NetworkIO net;
    private Relay cameraLight;

    public CV() {
        cameraLight = new Relay(Constants.CAMERA_RETICLE_SWITCH);
        System.out.println("CV constructor start");

        Thread t = new Thread(new Runnable() {
            public void run() {
                System.out.println("Instantiating net now!");
                CV.net = new NetworkIO();
                System.out.println("I FINISHED INSTANTIATING NET!!");
            }
        });
        t.start();
        System.out.println("CV constructor end");
    }

    public static CV getInstance() {
        if (instance == null) {
            instance = new CV();
        }
        return instance;
    }

    // TODO: Implement wrapper for Josh's CV code.
    public boolean isGoalHot() {
        if (net == null) {
            return false;
        }
        int status = net.getCurrent();
        if (!(status == Constants.CV_I_DONT_KNOW)) {
            Timer.delay(0.5);
            status = net.getCurrent();
        }
        return status == Constants.CV_TARGET_IS_HOT;
    }

    public boolean isPiConnected() {
        return net != null && net.getConnected();
    }

    public boolean getLightValue() {
        if (cameraLight.get() == Relay.Value.kOff) {
            return false;
        } else {
            return true;
        }
    }

    public void resetLight() {
        cameraLight.free();
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