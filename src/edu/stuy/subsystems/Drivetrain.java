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
    
    double driveStraightSpeed = 0; // TO BE CHANGED
   
    private Gyro gyro;
    private static Drivetrain instance;
    private RobotDrive drivetrain;
    PIDController forwardController;
    PIDController backwardController;
    private Encoder encoder;
    
    private Drivetrain() {
        
        drivetrain = new RobotDrive(Constants.LEFT_MOTOR_CHANNEL, Constants.RIGHT_MOTOR_CHANNEL);
        drivetrain.setSafetyEnabled(false);
        gyro = new Gyro(Constants.GYRO_CHANNEL);
        
        encoder = new Encoder(Constants.ENCODER_CHANNEL_A, Constants.ENCODER_CHANNEL_B);
        encoder.setDistancePerPulse(Constants.ENCODER_DISTANCE_PER_PULSE);
        encoder.start();
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
        tankDrive(gamepad.getLeftY(), gamepad.getRightY());
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
    
    public double getEnc() {
        return encoder.getDistance();
    }

    public void resetEncoders() {
        encoder.reset();
    }

    public double getAvgDistance() {
        return getEnc();
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
