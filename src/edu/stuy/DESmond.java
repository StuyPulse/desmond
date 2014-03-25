package edu.stuy;

import edu.stuy.subsystems.*;
import edu.stuy.util.Gamepad;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;

public class DESmond extends IterativeRobot {

    Drivetrain drivetrain;
    //CV cv; // TODO: uncomment when CV works

    Gamepad rightPad = new Gamepad(Constants.GAMEPAD_RIGHT_PORT);
    Gamepad leftPad = new Gamepad(Constants.GAMEPAD_LEFT_PORT);

    SendableChooser autonChooser;

    public void robotInit() {
        drivetrain = Drivetrain.getInstance();
        resetAll();

        // SendableChooser for auton
        autonChooser = new SendableChooser();
        autonChooser.addObject("00 - Do nothing",                               Integer.valueOf(00));

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

        autonChooser.addObject("40 - Drive forward only",                       Integer.valueOf(40));
        SmartDashboard.putData("Autonomous Routine", autonChooser);
    }

    public void autonomousInit() {
        System.out.println("Initialising auton");
        resetAll();
        Integer selection = (Integer) autonChooser.getSelected();
        System.out.println("Selected auton #" + selection.intValue());
        Autonomous.auton(selection.intValue());
    }
    
    public void autonomousPeriodic() {
    }

    public void teleopInit() {
        resetAll();
    }

    public void disabledInit() {
    }
   
    public void teleopPeriodic() {
        drivetrain.tankDrive(rightPad);
    }
    
    public void testPeriodic() {
        // TODO: To be added later
    }

    public void resetAll() {
        drivetrain.reset();
    }
}
