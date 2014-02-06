package edu.stuy.subsystems;

import edu.stuy.Constants;
import edu.stuy.util.Gamepad;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Talon;

public class Acquirer {
    
    private static Acquirer instance;
    private Solenoid pistonExtend;
    private Solenoid pistonRetract;
    private Talon roller;
 
    public Acquirer() {
        pistonExtend = new Solenoid(Constants.THING_DOWN_CHANNEL);
        pistonRetract = new Solenoid(Constants.THING_UP_CHANNEL);
        roller = new Talon(Constants.ACQUIRER_ROLLER_CHANNEL);
    }
    
    public static Acquirer getInstance() {
        if (instance == null) {
            instance = new Acquirer();
        }
        return instance;
    }
    
    public void rotateDown() {
        pistonExtend.set(true);
        pistonRetract.set(false);
    }
    
    public void rotateUp() {
        pistonExtend.set(false);
        pistonRetract.set(true);
    }
    
    public void intakeBall() {
        roller.set(-1);
    }
    
    public void ejectBall() {
        roller.set(1);
    }
    
    public void stopRoller() {
        roller.set(0);
    }
    
    public void reset() {
        rotateUp();
        stopRoller();
    }
    
    public void manualGamepadControl(Gamepad gamepad) {
        if (gamepad.getDPadLeft()) {
            ejectBall();
        }
        else if (gamepad.getDPadRight()) {
            intakeBall();
        }
        else if (gamepad.getDPadDown()) {
            rotateDown();
        }
        else if (gamepad.getDPadUp()) {
            rotateUp();
        }
    }
    
}
