/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.stuy.subsystems;

import edu.stuy.Constants;
import edu.wpi.first.wpilibj.AnalogChannel;
import edu.stuy.util.Gamepad;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Talon;

/**
 *
 * @author abdullahfahad
 */
public class Shooter {
    public static final double POSITION_ONE = 0.1;
    public static final double POSITION_TWO = 0.0;
    
     private AnalogChannel goalSensor;
     private Talon shootingWinch;
     //private Encoder winchEncoder;
     private static Shooter instance;
     private DigitalInput ballSensor;
     private DigitalInput ballCenteredSwitch;
     private DigitalInput catapultRetractedSwitch;

     
     private Shooter() {
         goalSensor = new AnalogChannel(Constants.GOAL_SENSOR_CHANNEL);
         shootingWinch = new Talon(Constants.SHOOTER_CHANNEL);
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
         retractWinch();
     }
     
     public void releaseWinch() {
         if (readyToShoot()) {
            //int angle = getAngle();
            //if(angle <= (Constants.DEGREES_WINCH_RELEASE/2)) {
            shootingWinch.set(Constants.SHOOTER_WINCH_SPEED_ONE);
         } else {
            shootingWinch.set(Constants.SHOOTER_WINCH_SPEED_TWO);
         }
     }
     
     public void retractWinch() {
         //int angle = getAngle();
         //if (angle >= Constants.DEGREES_WINCH_RETRACT/2) {
         if(isFullyRetracted()){
            shootingWinch.set(Constants.SHOOTER_WINCH_SPEED_ONE);
         } else {
            shootingWinch.set(Constants.SHOOTER_WINCH_SPEED_TWO);
         }
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
         if (gamepad.getTopButton()) {
             releaseWinch();
         }
         else if (gamepad.getBottomButton()) {
             retractWinch();
         }
     }
}
