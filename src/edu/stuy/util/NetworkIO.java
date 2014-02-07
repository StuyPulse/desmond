package edu.stuy.util;

import edu.stuy.Constants;
import java.io.*;
import javax.microedition.io.*;

/**
 * This class sends info to the robot from the pi returns 1 of 3 values, the
 * constant CV_I_DONT_KNOW when we need to wait longer for an answer, the
 * constant CV_TARGET_IS_HOT when the goal is lit, the constant
 * CV_TARGET_ISNOT_HOT when goal is not lit
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
            System.out.println("First thing, creating socket");
            SocketConnection requestSocket = (SocketConnection) Connector.open("socket://" + Constants.CV_IP + ":" + Constants.CV_SERVER_PORT);
            System.out.println("Setting socket options.");
            requestSocket.setSocketOption(SocketConnection.LINGER, 5);
            System.out.println("Opening input stream.");
            in = new DataInputStream(requestSocket.openInputStream());
            System.out.println("Opening output stream.");
            out = new DataOutputStream(requestSocket.openOutputStream());
            System.out.println("Flushing output stream.");
            out.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void run() {
        try {
            if (in.available() > 0) {
                System.out.println("Available: " + in.available());
                mostRecentOut = in.readInt();
                System.out.println("Out: " + mostRecentOut);
                sendMessage("blerp");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void sendMessage(String msg) {
        try {
            out.write(msg.getBytes());
            out.flush();
        } catch (IOException ioException) {
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
