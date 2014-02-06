package edu.stuy.subsystems;

import edu.stuy.util.NetworkIO;
import edu.stuy.Constants;
import edu.wpi.first.wpilibj.Timer;

public class CV {

    private static CV instance;
    private static NetworkIO net;

    public CV() {

        Thread t = new Thread(new Runnable() {
            public void run() {
                CV.net = new NetworkIO();
            }
        });
        t.start();
    }

    public static CV getInstance() {
        if (instance == null) {
            instance = new CV();
        }
        return instance;
    }

    // TODO: Implement wrapper for Josh's CV code.
    public boolean isGoalHot() {
        int status = net.getCurrent();
        if (!(status == Constants.CV_I_DONT_KNOW)) {
            Timer.delay(0.5);
            status = net.getCurrent();
        }
        return status==Constants.CV_TARGET_IS_HOT;
    }
}
