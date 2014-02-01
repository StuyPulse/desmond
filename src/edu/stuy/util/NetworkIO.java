/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.stuy.util;

import edu.stuy.Constants;
import java.io.*;
import javax.microedition.io.*;

/**
 * This class sends info to the robot from the pi
 * returns 1 of 3 values, the constant CV_I_DONT_KNOW when we need to wait longer for an answer, the constant CV_TARGET_IS_HOT when the goal is lit, the constant CV_TARGET_ISNOT_HOT when goal is not lit
 * 
 */
public class NetworkIO {

    private SocketConnection requestSocket;
    private DataOutputStream out;
    private DataInputStream in;
    private String message;
    private int mostRecentOut;

    public NetworkIO() {
        mostRecentOut = Constants.CV_I_DONT_KNOW;
        try {
            SocketConnection requestSocket = (SocketConnection) Connector.open("socket://" + Constants.CV_IP + ":" + Constants.CV_SERVER_PORT);
            requestSocket.setSocketOption(SocketConnection.LINGER, 5);
            in = new DataInputStream(requestSocket.openInputStream());
            out = new DataOutputStream(requestSocket.openOutputStream());
            out.flush();
        } catch (Exception e) {
        }
    }

    public void run() {
        try {
            int output;
            if (in.available() > 0) {
                output = in.readInt();
            }
            output = mostRecentOut;
        
        message = "" + output;
        mostRecentOut = Integer.parseInt(message);
    }
    catch (Exception e

    
    

) {
        }
    }
    
    void sendMessage(String msg) {
        try {
            out.write(msg.getBytes());
            out.flush();
        } catch(IOException ioException) {
        }
    }
    
    private int getMostRecent() {
        return mostRecentOut;
    }
    
    public int getCurrent() {
        run();
        return getMostRecent();
    }
}

