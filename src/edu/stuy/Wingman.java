/*----------------------------------------------------------------------------*/
/* Copyright (c) FIRST 2008. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package edu.stuy;


import edu.stuy.subsystems.Acquirer;
import edu.stuy.subsystems.Drivetrain;
import edu.stuy.subsystems.Shooter;
import edu.stuy.util.Gamepad;
import edu.wpi.first.wpilibj.IterativeRobot;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Wingman extends IterativeRobot {
    
    /* SUBSYSTEMS */
    Acquirer acquirer;
    Drivetrain drivetrain;
    Shooter shooter;
    
    /* CONTROLLERS */
    Gamepad driverPad;
    Gamepad operatorPad;
    
    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    public void robotInit() {
        acquirer = Acquirer.getInstance();
        drivetrain = Drivetrain.getInstance();
        shooter = Shooter.getInstance();
        
        driverPad = new Gamepad(Constants.DRIVER_PAD_PORT);
        operatorPad = new Gamepad(Constants.OPERATOR_PAD_PORT);
    }

    /**
     * This function is called periodically during autonomous
     */
    public void autonomousPeriodic() {

    }

    /**
     * This function is called periodically during operator control
     */
    public void teleopPeriodic() {
        
    }
    
    /**
     * This function is called periodically during test mode
     */
    public void testPeriodic() {
    
    }
    
}
