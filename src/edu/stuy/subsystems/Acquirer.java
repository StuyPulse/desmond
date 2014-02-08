package edu.stuy.subsystems;

import edu.stuy.Constants;
import edu.stuy.util.Gamepad;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Talon;

public class Acquirer {
    
    private static Acquirer instance;
    private Solenoid pistonExtend;
    private Solenoid pistonRetract;
    private Talon roller;
    private Compressor compressor;
 
    public Acquirer() {
        pistonExtend = new Solenoid(Constants.PISTON_EXTEND_CHANNEL);
        pistonRetract = new Solenoid(Constants.PISTON_RETRACT_CHANNEL);
        roller = new Talon(Constants.ACQUIRER_ROLLER_CHANNEL);
        compressor = new Compressor(Constants.PRESSURE_SWITCH_CHANNEL, Constants.COMPRESSOR_RELAY_CHANNEL);
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
    
    public boolean isEnoughPressure() {
        return compressor.getPressureSwitchValue();
    }
    
    public void startCompressor() {
        if (isEnoughPressure()) {
            compressor.stop();
        }
        else {
            compressor.start();
        }
    }
    
    public void manualGamepadControl(Gamepad gamepad) {
        if (gamepad.getDPadDown()) {
            ejectBall();
        }
        else if (gamepad.getDPadUp()) {
            intakeBall();
        }
        else if (gamepad.getTopButton()) {
            rotateDown();
        }
        else if (gamepad.getLeftButton()) {
            rotateUp();
        }
        else {
            stopRoller();
        }
    }
    
}
