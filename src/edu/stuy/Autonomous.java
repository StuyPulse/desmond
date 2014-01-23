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
        Acquirer.getInstance().rotateUp();
        //-If CV says the goal is hot we fire
        if(CV.getInstance().isGoalHot()) {
            Shooter.getInstance().releaseWinch();
        } else {
            //-Otherwise we wait till after the 5 second mark to fire, no need to use CV in the second half to tell if the goal is hot
            Timer.delay(5.0);
            Shooter.getInstance().releaseWinch();
        }
        //-Drive forward into scoring zone for 5 extra points while retracting the catapult.
        Drivetrain.getInstance().tankDrive(1, 1); //placeholder values
        Timer.delay(2.0);
        Shooter.getInstance().retractWinch();
        Drivetrain.getInstance().tankDrive(0, 0);
    }
    
    public void auton2() {
    
    }
    
    public void auton3() {
        
    }
    
    public void auton4() {
        
    }
    
    public void auton0() {
        //do nothing
    }
    
}