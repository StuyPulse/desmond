package edu.stuy.subsystems;

import edu.stuy.Constants;
import edu.stuy.util.Gamepad;
import edu.wpi.first.wpilibj.Solenoid;

public class Blocker {

    private static Blocker instance;
    private Solenoid pistonUp;
    private Solenoid pistonDown;

    public Blocker() {
        pistonUp = new Solenoid(Constants.BLOCKER_UP_CHANNEL);
        pistonDown = new Solenoid(Constants.BLOCKER_DOWN_CHANNEL);
    }

    public static Blocker getInstance() {
        if (instance == null) {
            instance = new Blocker();
        }
        return instance;
    }

    public void bringUp() {
        pistonUp.set(true);
        pistonDown.set(false);
    }

    public void bringDown() {
        pistonUp.set(false);
        pistonDown.set(true);
    }
    
    public void reset() {
        bringDown();
    }

    public void manualGamepadControl(Gamepad gamepad) {
        // Blocker roller controls
        if (gamepad.getRightBumper()) {
            bringUp();
        } else if (gamepad.getLeftBumper()) {
            bringDown();
        }
    }
}
