package edu.stuy.subsystems;

import edu.stuy.Constants;
import edu.stuy.Autonomous;
import edu.stuy.util.Gamepad;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Drivetrain {

    double driveStraightSpeed = 0; // TODO: To be implemented during driver tuning

    private static Drivetrain instance;
    private RobotDrive drivetrain;
    PIDController forwardController;
    PIDController backwardController;

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
        double right = gamepad.getRightY();
        double left = gamepad.getLeftY();
        if (gamepad.getRightBumper()) {
            right *= 0.9;
        }
        if (gamepad.getLeftBumper()) {
            left *= 0.9;
        }
        if (gamepad.getBottomButton()) {
            Autonomous.driveForward(1);
        }
        if (gamepad.getRightButton()) {
            Autonomous.driveForward(2);
        }
        if (gamepad.getLeftButton()) {
            Autonomous.driveForward(3);
        }
        if (gamepad.getTopButton()) {
            Autonomous.driveForward(4);
        }
        tankDrive(left, right);
    }

    public void reset() {
        tankDrive(0.0, 0.0);
    }

    public void dashboardPIDUpdate() {
        SmartDashboard.putNumber("Drivetrain P", Constants.PVAL_D);
        SmartDashboard.putNumber("Drivetrain I", Constants.IVAL_D);
        SmartDashboard.putNumber("Drivetrain D", Constants.DVAL_D);

        double tp, ti, td;
        tp = SmartDashboard.getNumber("Drivetrain P");
        ti = SmartDashboard.getNumber("Drivetrain I");
        td = SmartDashboard.getNumber("Drivetrain D");

        forwardController.setPID(tp, ti, td);
        backwardController.setPID(tp, ti, td);
    }
}
