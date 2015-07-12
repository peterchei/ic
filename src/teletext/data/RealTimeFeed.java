/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package teletext.data;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Hung
 */
public class RealTimeFeed implements Runnable {

    private static RealTimeFeed rtf = new RealTimeFeed();
    private ArrayList<FCommand> realTimeQuotesCommand = null;
    private ArrayList<FCommand> currentCommands = null;

    public static synchronized RealTimeFeed getInstance() {
        return rtf;
    }

    private RealTimeFeed() {
        new Thread(this).start();
    }

    public void stopFeed() {
        currentCommands =  null;
    }

    public void startFeed(ArrayList<FCommand> commands) {
        realTimeQuotesCommand = commands;
    }

    public void run() {
        Logger.getLogger(RealTimeFeed.class.getName()).log(Level.SEVERE, "RealTimeFeed started.");
        while (true) {

            if (currentCommands != null && currentCommands.size() > 0) {
            for (FCommand fc : currentCommands) {
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
