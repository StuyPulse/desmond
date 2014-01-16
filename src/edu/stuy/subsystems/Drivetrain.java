/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.stuy.subsystems;

import edu.stuy.Constants;
import edu.stuy.util.Gamepad;
import edu.wpi.first.wpilibj.Gyro;
import edu.wpi.first.wpilibj.RobotDrive;
/**
 *
 * @author abdullahfahad
 */
public class Drivetrain {
    private Gyro gyro;
    private static Drivetrain instance;
    private RobotDrive drivetrain;
    
    private Drivetrain() {
        drivetrain = new RobotDrive(Constants.LEFT_MOTOR_CHANNEL, Constants.RIGHT_MOTOR_CHANNEL);
        drivetrain.setSafetyEnabled(false);
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
    public double getRotationRate(){
        return gyro.getRate();
    }
    
    
    
    
}
