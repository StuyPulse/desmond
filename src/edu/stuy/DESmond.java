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

    Gamepad gamepad = new Gamepad(Constants.GAMEPAD_PORT);

    SendableChooser autonChooser;

    public void robotInit() {
        shooter = Shooter.getInstance();
        drivetrain = Drivetrain.getInstance();
        acquirer = Acquirer.getInstance();
        //cv = CV.getInstance(); // TODO: uncomment when CV works
        resetAll();

        // SendableChooser for auton
        autonChooser = new SendableChooser();
        autonChooser.addObject("00 - Do nothing",                               Integer.valueOf(00));
        autonChooser.addObject("01 - Open acquirer",                            Integer.valueOf(01));

        autonChooser.addObject("10 - CV one ball and drive forward",            Integer.valueOf(10));
        autonChooser.addObject("11 - CV two ball and drive forward",            Integer.valueOf(11));
        autonChooser.addObject("12 - CV low goal",                              Integer.valueOf(12));

        autonChooser.addObject("20 - Light sensor one ball and drive forward",  Integer.valueOf(20));
        autonChooser.addObject("21 - Light sensor two ball and drive forward",  Integer.valueOf(21));
        autonChooser.addObject("22 - Light sensor low goal",                    Integer.valueOf(22));

        autonChooser.addObject("30 - Dumb fire one ball, drive forward",        Integer.valueOf(30));
        autonChooser.addDefault("31 - Dumb fire two ball, drive forward",       Integer.valueOf(31));
        autonChooser.addObject("32 - Dumb fire low goal",                       Integer.valueOf(32));
        autonChooser.addObject("33 - Dumb fire three ball and drive forward",   Integer.valueOf(33));
        autonChooser.addObject("34 - Dumb fire two ball dragging one",          Integer.valueOf(34));

        autonChooser.addObject("40 - Drive forward only",                       Integer.valueOf(40));
        autonChooser.addObject("41 - Drive forward doubly far",                 Integer.valueOf(41));
        SmartDashboard.putData("Autonomous Routine", autonChooser);
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
        SmartDashboard.putBoolean("Dan's - Goal Hot (Digital)?", shooter.isGoalHotDigital());
        //SmartDashboard.putBoolean("Pi connected?", cv.isPiConnected());
        //SmartDashboard.putBoolean("CV - Goal Hot?", cv.isGoalHot());
        SmartDashboard.putNumber("Left Encoder Distance", drivetrain.getLeftEnc());
        SmartDashboard.putNumber("Right Encoder Distance", drivetrain.getRightEnc());
        SmartDashboard.putBoolean("Ready to shoot?", shooter.isFullyRetracted());
        //SmartDashboard.putBoolean("Camera Light - On?", cv.getLightValue());
        acquirer.manualGamepadControl(gamepad);
        shooter.manualGamepadControl(gamepad);
        drivetrain.tankDrive(gamepad);
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
