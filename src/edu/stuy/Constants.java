package edu.stuy;

// A value of -1 or -1.0 means the port has yet to be assigned.
public class Constants {

    /* PWM */
    public static final int RIGHT_MOTOR_CHANNEL = 9;
    public static final int LEFT_MOTOR_CHANNEL = 10;
    public static final int ACQUIRER_ROLLER_CHANNEL = 8;
    public static final int SHOOTER_CHANNEL = 7;

    /* SOLENOID */
    public static final int PISTON_EXTEND_CHANNEL = 2;
    public static final int PISTON_RETRACT_CHANNEL = 3;
    public static final int PRESSURE_SWITCH_CHANNEL = 5;
    public static final int COMPRESSOR_RELAY_CHANNEL = 1;
    public static final int BLOCKER_UP_CHANNEL = 6;
    public static final int BLOCKER_DOWN_CHANNEL = 7;

    /* GPIO */
    public static final int ENCODER_CHANNEL_LEFT_A = 10;
    public static final int ENCODER_CHANNEL_LEFT_B = 11;
    public static final int ENCODER_CHANNEL_RIGHT_A = 1;
    public static final int ENCODER_CHANNEL_RIGHT_B = 2;
    public static final int CATAPULT_RETRACTED_SWITCH_CHANNEL = 7;
    public static final int GOAL_SENSOR_DIGITAL_CHANNEL = 14; // Rear is 13 (acquirer), front is 14 (shooter)

    /* DRIVETRAIN DISTANCE MEASUREMENT CONSTANTS */
    private static final double ENCODER_PULSE_PER_REV = 360.0;
    private static final double GEAR_RATIO = 42.0 / 39.0; // TODO: TO BE CHANGED
    private static final double WHEEL_RADIUS = 2.0; // TODO: TO BE CHANGED
    private static final double CIRCUMFERENCE = 2 * Math.PI * WHEEL_RADIUS;
    public static final double ENCODER_DISTANCE_PER_PULSE = CIRCUMFERENCE / (ENCODER_PULSE_PER_REV * GEAR_RATIO);

    /* GYRO DRIVE STRAIGHT PID VALUES */
    public static final double PVAL_D = .01; // TODO: TO BE CHANGED
    public static final double IVAL_D = .01; // TODO: TO BE CHANGED
    public static final double DVAL_D = .01; // TODO: TO BE CHANGED

    /* ANALOG INPUT */
    public static final int GAMEPAD_RIGHT_PORT = 1;
    public static final int GAMEPAD_LEFT_PORT = 2;
    public static final int GOAL_SENSOR_ANALOG_CHANNEL = 1; // 3 for rear (acquirer), 1 for front (shooter)

    //CV constants
    //Robot and Pi are different endian, so these are the values flipped
    public static final int CV_I_DONT_KNOW = 0;
    public static final int CV_TARGET_IS_HOT = 16777216;
    public static final int CV_TARGET_IS_NOT_HOT = 33554432;
    public static final int CV_TIMEOUT = 3000;
    public static final int CV_SERVER_PORT = 12345;
    public static final String CV_IP = "10.6.94.15";

    public static final int DAN_CAMERA_AUTON_DELAY = 200;

    // SHOOTER constants
    public static final double SHOOTER_GOAL_SENSOR_VOLTAGE = 3.3; // TODO: To be changed later
    public static final long SHOOTER_RETRACT_TIMEOUT = 5000; // Failsafe if limit switch fails
    public static final double SHOOTER_DELAY_FOR_FIRE = 0.5;
    public static final long SHOOTER_FIRE_TIMEOUT = 2000;

    //RELAY outputs
    public static final int CAMERA_RETICLE_SWITCH = 6;

    // AUTON constants
    public static final double AUTON_DELAY_BETWEEN_SHOOT_AND_DRIVE = 0.5;
    public static final double AUTON_TIME_TO_DRIVE_18_FEET = 2.25;
    public static final double AUTON_TIME_TO_LINE_UP_SHOT = 0.75;
    // Makes the delay factor in the time needed to drive + some buffer
    public static final double AUTON_TIME_FOR_CV_TO_WAIT_FOR_SWITCH_TO_HOT_GOAL = 6 - AUTON_TIME_TO_DRIVE_18_FEET + .5;
    public static final double AUTON_TIME_FOR_DV_TO_WAIT_FOR_SWITCH_TO_HOT_GOAL = 6 - AUTON_TIME_TO_DRIVE_18_FEET + 1;
    public static final double AUTON_TIME_TO_EXTEND_ACQUIRER = 1.6;
    public static final double AUTON_TIME_TO_INTAKE_BALL = 1.75;
    public static final double AUTON_THREE_BALL_DRIVE_TIME = 1.0;

}
