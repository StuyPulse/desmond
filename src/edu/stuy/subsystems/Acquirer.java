/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.stuy.subsystems;

import edu.stuy.Constants;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Talon;

/**
 *
 * @author abdullahfahad
 */
public class Acquirer {
    
    private static Acquirer instance;
    private Solenoid thingDown;
    private Solenoid thingUp;
    private Talon roller;
    private DigitalInput ballSensor;
 
    public Acquirer() {
        thingDown = new Solenoid(Constants.THING_DOWN_CHANNEL);
        thingUp = new Solenoid(Constants.THING_UP_CHANNEL);
        roller = new Talon(Constants.ACQUIRER_ROLLER_CHANNEL);
        ballSensor = new DigitalInput(Constants.BALL_SENSOR_CHANNEL);
    }
    
    public static Acquirer getInstance() {
        if (instance == null) {
            instance = new Acquirer();
        }
        return instance;
    }
    
    public void rotateDown() {
        thingDown.set(true);
        thingUp.set(false);
    }
    
    public void rotateUp() {
        thingDown.set(false);
        thingUp.set(true);
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
    
    public boolean hasBall() {
        return ballSensor.get();
    }
    
    
}
