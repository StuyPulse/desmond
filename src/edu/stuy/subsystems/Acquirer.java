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
    public Compressor compressor;

    public Acquirer() {
        pistonExtend = new Solenoid(Constants.PISTON_EXTEND_CHANNEL);
        pistonRetract = new Solenoid(Constants.PISTON_RETRACT_CHANNEL);
        roller = new Talon(Constants.ACQUIRER_ROLLER_CHANNEL);
        compressor = new Compressor(Constants.PRESSURE_SWITCH_CHANNEL, Constants.COMPRESSOR_RELAY_CHANNEL);
        compressor.start();
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
        // Acquirer frame controls
        if (Math.abs(gamepad.getLeftY()) >= 0.1) {
            roller.set(gamepad.getLeftY());
            System.out.println("Acquirer being controlled with analog." + gamepad.getRightY());
        } else if (gamepad.getDPadDown()) {
            ejectBall();
            System.out.println("Acquirer being controlled with button. Ejecting.");
        } else if (gamepad.getDPadUp()) {
            intakeBall();
            System.out.println("Acquirer being controlled with button. Intaking.");
        } else {
            stopRoller();
            System.out.println("Acquirer not being controlled by anything.");
        }

        // Acquirer roller controls
        if (gamepad.getTopButton()) {
            rotateDown();
        } else if (gamepad.getLeftButton()) {
            rotateUp();
        }
    }
}
