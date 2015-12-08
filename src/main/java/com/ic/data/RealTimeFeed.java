/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ic.data;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Hung
 */
public class RealTimeFeed implements Runnable {

    private static RealTimeFeed rtf = new RealTimeFeed();
    private ArrayList<RequestCommand> realTimeQuotesCommand = null;
    private ArrayList<RequestCommand> currentCommands = null;

    private RealTimeFeed() {
        new Thread(this).start();
    }

    public static synchronized RealTimeFeed getInstance() {
        return rtf;
    }

    public void stopFeed() {
        currentCommands = null;
    }

    public void startFeed(ArrayList<RequestCommand> commands) {
        realTimeQuotesCommand = commands;
    }

    public void run() {
        Logger.getLogger(RealTimeFeed.class.getName()).log(Level.SEVERE, "RealTimeFeed started.");
        while (true) {

            if (currentCommands != null && currentCommands.size() > 0) {
                for (RequestCommand fc : currentCommands) {
                    fc.execute();
                }
            }
            if (realTimeQuotesCommand != null) {
                currentCommands = realTimeQuotesCommand;
                realTimeQuotesCommand = null;
            } else {
                try {
                    Thread.sleep(6000);
                } catch (InterruptedException ex) {
                    Logger.getLogger(RealTimeFeed.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
}
