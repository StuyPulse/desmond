/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.stuy.subsystems;

import edu.stuy.Constants;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Talon;

/**
 *
 * @author abdullahfahad
 */
public class Shooter {
     Talon shootingWinch;
     Encoder winchEncoder;
     static Shooter instance;
     private Shooter(){
         shootingWinch = new Talon(Constants.SHOOTER_CHANNEL);
         winchEncoder = new Encoder(Constants.ENCODER_CHANNEL_A, Constants.ENCODER_CHANNEL_B);
         winchEncoder.start();
         winchEncoder.reset(); 
     }
     public static Shooter getInstance() {
        if (instance == null) {
            instance = new Shooter();
        }   
        return instance;
    }
     public void releaseWinch(){
         if(readyToShoot())
         {
            int angle = getAngle();
            if(angle <= (Constants.DEGREES_WINCH_RELEASE/2))
            {
               shootingWinch.set(0.1);
            }
            else
            {
               shootingWinch.set(0.0);
            }
         }
     }
     public void retractWinch(){
     
         int angle = getAngle();
         if(angle >=(Constants.DEGREES_WINCH_RETRACT/2))
         {
            shootingWinch.set(0.1);
         }
         else
         {
            shootingWinch.set(0.0);
         }
         
     }
     public int getAngle(){
         return (int)((winchEncoder.get() % Constants.PULSES_PER_REVOLUTION)/
               Constants.PULSES_PER_REVOLUTION * 360);
     }

     public boolean readyToShoot(){
         int angle = getAngle();
         return (angle < 340 && angle > 380-Constants.DEGREES_WINCH_RETRACT);
     }
}
