/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.stuy;

/**
 *
 * @author abdullahfahad
 */
public class Constants {
    
    /* PWM */
    public static final int LEFT_MOTOR_CHANNEL = 1;
    public static final int RIGHT_MOTOR_CHANNEL = 2;
    public static final int ACQUIRER_ROLLER_CHANNEL = 3;
    public static final int SHOOTER_CHANNEL = 4;
    
    /* SOLENOID */
    public static final int THING_DOWN_CHANNEL = 1;
    public static final int THING_UP_CHANNEL = 2;

    /* GPIO */
    public static final int BALL_SENSOR_CHANNEL = 1;
    public static final int ENCODER_CHANNEL_A = 2;
    public static final int ENCODER_CHANNEL_B = 3;
    
    /* ENCODER */
    public static final int PULSES_PER_REVOLUTION = 360;
    public static final int DEGREES_WINCH_RETRACT = 240;
    public static final int DEGREES_WINCH_RELEASE = 360 - DEGREES_WINCH_RETRACT;
    
    public static final int GAMEPAD_RIGHT_PORT = 1;
    
}
