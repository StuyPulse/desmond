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

    /* GPIO */
    public static final int BALL_SENSOR_CHANNEL = -1;
    public static final int ENCODER_CHANNEL_LEFT_A = 10;
    public static final int ENCODER_CHANNEL_LEFT_B = 11;
    public static final int ENCODER_CHANNEL_RIGHT_A = 1;
    public static final int ENCODER_CHANNEL_RIGHT_B = 2;
    public static final int BALL_CENTERED_SWITCH_CHANNEL = -1;
    //public static final int WINCH_ENCODER_CHANNEL_A = 7;
    //public static final int WINCH_ENCODER_CHANNEL_B = 8;
    public static final int CATAPULT_RETRACTED_SWITCH_CHANNEL = -1;

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
    // Temporary. Port assignments will be supplied by Dan later. 
    public static final int GYRO_CHANNEL = -1;
    public static final int GOAL_SENSOR_CHANNEL = -1;
    public static final int GAMEPAD_RIGHT_PORT = 1;
    public static final int GAMEPAD_LEFT_PORT = 2;

    //CV constants
    //Robot and Pi are different endian, so these are the values flipped
    public static final int CV_I_DONT_KNOW = 0;
    public static final int CV_TARGET_IS_HOT = 16777216;
    public static final int CV_TARGET_IS_NOT_HOT = 33554432;
    public static final int CV_TIMEOUT = 3000;
    public static final int CV_SERVER_PORT = 12345;
    public static final String CV_IP = "10.6.94.10";

    // SHOOTER constants
    public static final double SHOOTER_WINCH_SPEED_ONE = 0.1;
    public static final double SHOOTER_WINCH_SPEED_TWO = 0.0;
    public static final double SHOOTER_GOAL_SENSOR_VOLTAGE = 4.0; // To be changed later
    public static final long SHOOTER_RETRACT_TIMEOUT = 5000; // Failsafe if something goes wrong
    public static final double SHOOTER_DELAY_FOR_FIRE = 0.25;

}
