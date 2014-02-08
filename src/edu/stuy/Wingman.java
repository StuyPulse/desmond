package edu.stuy;

import edu.stuy.subsystems.*;
import edu.stuy.util.Gamepad;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;

public class Wingman extends IterativeRobot {

    Shooter shooter;
    Drivetrain drivetrain;
    Acquirer acquirer;
    CV cv;
    
    Gamepad rightPad = new Gamepad(Constants.GAMEPAD_RIGHT_PORT);
    Gamepad leftPad = new Gamepad(Constants.GAMEPAD_LEFT_PORT);
    
    SendableChooser autonChooser;
    
    public void robotInit() {
        shooter = Shooter.getInstance();
        drivetrain = Drivetrain.getInstance();
        acquirer = Acquirer.getInstance();
        cv = CV.getInstance();
        resetAll();
        

        // SendableChooser for auton
        autonChooser = new SendableChooser();
        autonChooser.addDefault("1 - Wait for hot goal, shoot, and drive forward", Integer.valueOf(1));
        autonChooser.addObject("2 - Wait for hot goal, shoot first ball, intake second ball, shoot second ball, drive forward", Integer.valueOf(2));
        autonChooser.addObject("3 - Shoot first ball, intake second ball, shoot second ball, drive forward", Integer.valueOf(3));
        autonChooser.addObject("0 - Do nothing", Integer.valueOf(0));
        SmartDashboard.putData("Autonomous routine", autonChooser);
    }

    public void autonomousInit() {
        resetAll();
        Integer selection = (Integer) autonChooser.getSelected();
        Autonomous.auton(selection.intValue());
    }
    
    public void teleopInit() {
        resetAll();
    }
    
    // This function is called periodically during autonomous
    public void autonomousPeriodic() {
        SmartDashboard.putBoolean("Goal hot?",cv.isGoalHot());
    }

    // This function is called periodically during operator control
    public void teleopPeriodic() {
        //SmartDashboard.putNumber("Angle", shooter.getAngle());
        SmartDashboard.putBoolean("Shooting?",shooter.readyToShoot());
        acquirer.manualGamepadControl(leftPad);
        shooter.manualGamepadControl(leftPad);
        drivetrain.tankDrive(rightPad);
    }
    
    // This function is called periodically during test mode
    public void testPeriodic() {
        // To be added later    
    }
    
    public void resetAll() {
        shooter.reset();
        drivetrain.reset();
        acquirer.reset();
        // Note: CV does not have a reset() method
    }
}
