package edu.stuy;

import edu.stuy.subsystems.*;
import edu.stuy.util.Gamepad;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;

public class DESmond extends IterativeRobot {

    Shooter shooter;
    Drivetrain drivetrain;
    Acquirer acquirer;
    //CV cv; // TODO: uncomment when CV works

    Gamepad rightPad = new Gamepad(Constants.GAMEPAD_RIGHT_PORT);
    Gamepad leftPad = new Gamepad(Constants.GAMEPAD_LEFT_PORT);

    SendableChooser autonChooser;

    public void robotInit() {
        shooter = Shooter.getInstance();
        drivetrain = Drivetrain.getInstance();
        acquirer = Acquirer.getInstance();
        //cv = CV.getInstance(); // TODO: uncomment when CV works
        resetAll();

        // SendableChooser for auton
        autonChooser = new SendableChooser();
        autonChooser.addObject("0 - Do nothing", Integer.valueOf(0));
        autonChooser.addObject("1 - Wait for hot goal using CV, shoot, and drive forward", Integer.valueOf(1));
        autonChooser.addObject("2 - Wait for hot goal using CV, shoot first ball, intake second ball, shoot second ball, drive forward", Integer.valueOf(2));
        autonChooser.addObject("3 - Wait for hot goal using CV, drive forward, fire into low goal", Integer.valueOf(3));
        autonChooser.addObject("4 - Wait for hot goal using analog, shoot, and drive forward", Integer.valueOf(4));
        autonChooser.addObject("5 - Wait for hot goal using analog, shoot first ball, intake second ball, shoot second ball, drive forward", Integer.valueOf(5));
        autonChooser.addObject("6 - Wait for hot goal using analog, drive forward, fire into low goal", Integer.valueOf(6));
        autonChooser.addObject("7 - Dumb fire one ball, drive forward", Integer.valueOf(7));
        autonChooser.addObject("8 - Dumb fire one ball, intake second ball, shoot second ball, drive forward", Integer.valueOf(8));
        autonChooser.addObject("9 - Dumb fire low goal", Integer.valueOf(9));
        autonChooser.addObject("10 - Drive forward only (away from acquirer)", Integer.valueOf(10));
        autonChooser.addDefault("11 - dumb fire one ball, intake second ball, shoot second ball, drive backwards, etc.", Integer.valueOf(11));
        SmartDashboard.putData("Autonomous routine", autonChooser);
    }

    public void autonomousInit() {
        System.out.println("Initialising auton");
        resetAll();
        Integer selection = (Integer) autonChooser.getSelected();
        System.out.println("Selected auton #" + selection.intValue());
        Autonomous.auton(selection.intValue());
    }

    // This function is called periodically during autonomous
    public void autonomousPeriodic() {
        //SmartDashboard.putBoolean("Goal hot?",cv.isGoalHot()); // TODO: uncomment when CV works
    }

    public void teleopInit() {
        resetAll();
        
    }

    public void disabledInit() {
    }

    // This function is called periodically during operator control
    // TODO: uncomment some of these when CV/analog works
    public void teleopPeriodic() {
        SmartDashboard.putNumber("Dan's - Goal Voltage", shooter.getGoalVoltage());
        SmartDashboard.putBoolean("Dan's - Goal Hot (Analog)", shooter.isGoalHotAnalog());
        SmartDashboard.putBoolean("Dan's - Goal Hot (Digital)?", shooter.isGoalHotDigital());
        //SmartDashboard.putBoolean("Pi connected?", cv.isPiConnected());
        //SmartDashboard.putBoolean("CV - Goal Hot?", cv.isGoalHot());
        SmartDashboard.putNumber("Left Encoder Distance", drivetrain.getLeftEnc());
        SmartDashboard.putNumber("Right Encoder Distance", drivetrain.getRightEnc());
        SmartDashboard.putBoolean("Ready to shoot?", shooter.isFullyRetracted());
        //SmartDashboard.putBoolean("Camera Light - On?", cv.getLightValue());
        acquirer.manualGamepadControl(leftPad);
        shooter.manualGamepadControl(leftPad);
        drivetrain.tankDrive(rightPad);
        //cv.setCameraLight(true);
    }

    // This function is called periodically during test mode
    public void testPeriodic() {
        // TODO: To be added later
    }

    public void resetAll() {
        shooter.reset();
        drivetrain.reset();
        acquirer.reset();
        // NOTE: CV does not have a reset() method
    }
}
