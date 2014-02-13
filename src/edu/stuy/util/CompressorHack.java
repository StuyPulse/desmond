package edu.stuy.util;

import edu.stuy.Constants;
import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Timer;

public class CompressorHack extends Thread {
    
    public static boolean isEnabled;
    private Relay relay;
    private DigitalInput pressureSwitch;
    
    public CompressorHack() {
        relay = new Relay(Constants.COMPRESSOR_RELAY_CHANNEL);
        pressureSwitch = new DigitalInput(Constants.PRESSURE_SWITCH_CHANNEL);
    }   
    public void run() {
        while(true) {
            if (isEnabled) {
                if (pressureSwitch.get()) {
                    relay.set(Relay.Value.kForward);
                    System.out.println("The relay has been set to forward.");
                } else {
                    relay.set(Relay.Value.kOff);
                    System.out.println("The relay has been set to off.");
                }
            }
            Timer.delay(0.5);
        }
    }
}
