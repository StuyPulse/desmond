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
    public static final int ENCODER_CHANNEL_A_LEFT = 2;
    public static final int ENCODER_CHANNEL_B_LEFT = 3;
    public static final int ENCODER_CHANNEL_A_RIGHT = 4;
    public static final int ENCODER_CHANNEL_B_RIGHT = 5;
    
    /* ENCODER */
    public static final int PULSES_PER_REVOLUTION = 360;
    public static final int DEGREES_WINCH_RETRACT = 240;
    public static final int DEGREES_WINCH_RELEASE = 360 - DEGREES_WINCH_RETRACT;
    
    /* DRIVETRAIN DISTANCE MEASUREMENT CONSTANTS */
    private static final double ENCODER_PULSE_PER_REV = 360.;
    private static final double GEAR_RATIO = 42.0 / 39.0; // TO BE CHANGED
    private static final double WHEEL_RADIUS = 2.0; // TO BE CHANGED
    public static final double ENCODER_DISTANCE_PER_PULSE = 1 / (ENCODER_PULSE_PER_REV * GEAR_RATIO / ( 2 * Math.PI * WHEEL_RADIUS));
    
    /* GYRO DRIVE STRAIGHT PID VALUES */
    public static final double PVAL_D = .01; // TO BE CHANGED
    public static final double IVAL_D = .01; // TO BE CHANGED
    public static final double DVAL_D = .01; // TO BE CHANGED
    
    /* ANALOG INPUT */
    public static final int GYRO_CHANNEL = 1;
            
    public static final int GAMEPAD_RIGHT_PORT = 1;
    
}
