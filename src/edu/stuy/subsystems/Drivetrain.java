/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.stuy.subsystems;

import edu.stuy.Constants;
import edu.stuy.util.Gamepad;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Gyro;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
/**
 *
 * @author abdullahfahad, r4, danny
 */

public class Drivetrain {
    
    double driveStraightSpeed = 0; // TODO: TO BE CHANGED
   
    private Gyro gyro;
    private static Drivetrain instance;
    private RobotDrive drivetrain;
    PIDController forwardController;
    PIDController backwardController;
    private Encoder encoderRight;
    private Encoder encoderLeft;
    
    private Drivetrain() {
        
        drivetrain = new RobotDrive(Constants.LEFT_MOTOR_CHANNEL, Constants.RIGHT_MOTOR_CHANNEL);
        drivetrain.setSafetyEnabled(false);
        gyro = new Gyro(Constants.GYRO_CHANNEL);
        
        encoderRight = new Encoder(Constants.ENCODER_CHANNEL_RIGHT_A, Constants.ENCODER_CHANNEL_RIGHT_B);
        encoderRight.setDistancePerPulse(Constants.ENCODER_DISTANCE_PER_PULSE);
        encoderLeft = new Encoder(Constants.ENCODER_CHANNEL_LEFT_A, Constants.ENCODER_CHANNEL_LEFT_B);
        encoderLeft.setDistancePerPulse(Constants.ENCODER_DISTANCE_PER_PULSE);
        encoderRight.start();
        encoderLeft.start();
        encoderRight.reset();
        encoderLeft.reset();
    }
    
    public static Drivetrain getInstance() {
        if (instance == null) {
            instance = new Drivetrain();
        }   
        return instance;
    }
    
    public void tankDrive(double leftValue, double rightValue) {
        drivetrain.tankDrive(leftValue, rightValue);
    }
    
    public void tankDrive(Gamepad gamepad) {
        double right = gamepad.getRightY();
        double left = gamepad.getLeftY();
        if (gamepad.getRightBumper()) {
            right *= 0.9;
        }
        if (gamepad.getLeftBumper()) {
            left *= 0.9;
        }
        tankDrive(left, right);
    }
    
    public void reset() {
        tankDrive(0.0, 0.0);
        gyroReset();
    }

    public double getAngle() {
        return gyro.getAngle();
    }

    public void gyroReset() {
        gyro.reset();
    }
    
    public double getLeftEnc() {
        return encoderLeft.getDistance();
    }
    
    public double getRightEnc() {
        return encoderRight.getDistance();
    }

    public void resetEncoders() {
        encoderRight.reset();
        encoderLeft.reset();
    }

    public double getAvgDistance() {
        return (getLeftEnc() + getRightEnc()) / 2;
    }
    
    public void dashboardPIDUpdate() {
        SmartDashboard.putNumber("Drivetrain P", Constants.PVAL_D);
        SmartDashboard.putNumber("Drivetrain I", Constants.IVAL_D);
        SmartDashboard.putNumber("Drivetrain D", Constants.DVAL_D);
        
        double tp,ti,td;
        tp = SmartDashboard.getNumber("Drivetrain P");
        ti = SmartDashboard.getNumber("Drivetrain I");
        td = SmartDashboard.getNumber("Drivetrain D");
        
        forwardController.setPID(tp, ti, td);
        backwardController.setPID(tp, ti, td);
    }
}
