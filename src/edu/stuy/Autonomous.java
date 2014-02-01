/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.stuy;
import edu.stuy.subsystems.*;
import edu.wpi.first.wpilibj.Timer;

/**
 *
 * @author abdullahfahad
 */
public class Autonomous {
    
    public void auton(int x){
        switch(x){
            case 0:
                auton0();
                break;
            case 1:
                auton1();
                break;
            case 2:
                auton2();
                break;
            case 3:
                auton3();
                break;
            case 4:
                auton4();
                break;
        }
    }
    
    public void auton1(){
        //Bring acquisition forward, out of way of catapult
        Acquirer.getInstance().rotateDown();
        //-If CV says the goal is hot we fire
        if(CV.getInstance().isGoalHot()) {
            Shooter.getInstance().releaseWinch();
        } else {
            //-Otherwise we wait till after the 5 second mark to fire, no need to use CV in the second half to tell if the goal is hot
            Timer.delay(5.0);
            Shooter.getInstance().releaseWinch();
        }
        Shooter.getInstance().retractWinch();
        Acquirer.getInstance().rotateUp();
        //-Drive forward into scoring zone for 5 extra points while retracting the catapult.
        Drivetrain.getInstance().tankDrive(1, 1); //placeholder values
        Timer.delay(1.0);
        Drivetrain.getInstance().tankDrive(0, 0);
    }
    
    public void auton2() {
        Acquirer.getInstance().rotateDown();
        Acquirer.getInstance().intakeBall();
        Timer.delay(1.0); // Delay should be tuned to ready next ball, but not load until first ball is fired
        Acquirer.getInstance().stopRoller();
        // There may not be enough time to check if the goal is hot AND shoot both balls
        if (CV.getInstance().isGoalHot()) {
            Shooter.getInstance().releaseWinch();
        }
        else {
            Timer.delay(5.0);
            Shooter.getInstance().releaseWinch();
        }
        Timer.delay(0.5);
        Shooter.getInstance().retractWinch();
        if (!Shooter.getInstance().hasBall()) {
            Acquirer.getInstance().intakeBall();
            Timer.delay(1.0);
        }
        Acquirer.getInstance().stopRoller();
        Shooter.getInstance().releaseWinch();
        Timer.delay(0.5);
        Shooter.getInstance().retractWinch();
        Drivetrain.getInstance().tankDrive(1, 1);
        Timer.delay(1.0);
        Drivetrain.getInstance().tankDrive(0, 0);
    }
    
    public void auton3() {
        Acquirer.getInstance().rotateDown();
        Acquirer.getInstance().intakeBall();
        Timer.delay(1.0); // Delay should be tuned to ready next ball, but not load until first ball is fired
        Acquirer.getInstance().stopRoller();
        Shooter.getInstance().releaseWinch();
        Timer.delay(0.5);
        Shooter.getInstance().retractWinch();
        if (!Shooter.getInstance().hasBall()) {
            Acquirer.getInstance().intakeBall();
            Timer.delay(1.0);
        }
        Acquirer.getInstance().stopRoller();
        Shooter.getInstance().releaseWinch();
        Timer.delay(0.5);
        Shooter.getInstance().retractWinch();
        Drivetrain.getInstance().tankDrive(1, 1);
        Timer.delay(1.0);
        Drivetrain.getInstance().tankDrive(0, 0);
    }
    
    public void auton4() {
        
    }
    
    public void auton0() {
        //do nothing
    }
    
}