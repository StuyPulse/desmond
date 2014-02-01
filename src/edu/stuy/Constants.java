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
    public static final int LEFT_MOTOR_CHANNEL = 2;
    public static final int RIGHT_MOTOR_CHANNEL = 1;
    public static final int ACQUIRER_ROLLER_CHANNEL = 3;
    public static final int SHOOTER_CHANNEL = 4;
    
    /* SOLENOID */
    public static final int THING_DOWN_CHANNEL = 1;
    public static final int THING_UP_CHANNEL = 2;

    /* GPIO */
    public static final int BALL_SENSOR_CHANNEL = 1;
    public static final int ENCODER_CHANNEL_LEFT_A = 2;
    public static final int ENCODER_CHANNEL_LEFT_B = 3;
    public static final int ENCODER_CHANNEL_RIGHT_A = 4;
    public static final int ENCODER_CHANNEL_RIGHT_B = 5;
    
    /* ENCODER */
    public static final int PULSES_PER_REVOLUTION = 250; // TO BE CHANGED
    public static final int DEGREES_WINCH_RETRACT = 240;
    public static final int DEGREES_WINCH_RELEASE = 360 - DEGREES_WINCH_RETRACT;
    
    /* DRIVETRAIN DISTANCE MEASUREMENT CONSTANTS */
    private static final double ENCODER_PULSE_PER_REV = 360.0;
    private static final double GEAR_RATIO = 42.0 / 39.0; // TO BE CHANGED
    private static final double WHEEL_RADIUS = 2.0; // TO BE CHANGED
    private static final double CIRCUMFERENCE = 2 * Math.PI * WHEEL_RADIUS;
    public static final double ENCODER_DISTANCE_PER_PULSE = CIRCUMFERENCE / (ENCODER_PULSE_PER_REV * GEAR_RATIO);
    
    /* GYRO DRIVE STRAIGHT PID VALUES */
    public static final double PVAL_D = .01; // TO BE CHANGED
    public static final double IVAL_D = .01; // TO BE CHANGED
    public static final double DVAL_D = .01; // TO BE CHANGED
    
    /* ANALOG INPUT */
    public static final int GYRO_CHANNEL = 1;
            
    public static final int GAMEPAD_RIGHT_PORT = 1;
    public static final int GAMEPAD_LEFT_PORT = 1; 
    
    //CV constants
    public static final int CV_I_DONT_KNOW = 0;
    public static final int CV_TARGET_IS_HOT = 1;
    public static final int CV_TARGET_ISNOT_HOT = 2;
    public static final int CV_TIMEOUT = 3000;
    public static final int CV_SERVER_PORT = 6940;
    public static final String CV_IP = "10.6.94.14";
    
    // SHOOTER constants
    public static final double SHOOTER_WINCH_SPEED_ONE = 0.1;
    public static final double SHOOTER_WINCH_SPEED_TWO = 0.0;
    
}
