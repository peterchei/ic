/**
 * Title:        <p>
 * Description:  <p>
 * Copyright:    Copyright (c) <p>
 * Company:      <p>
 * @author
 * @version 1.0
 */
package fme;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.NoSuchElementException;
import java.util.StringTokenizer;

public class BrokerEngine
        extends Thread {
    private final int uninited = 0;
    private final int inited = 1;
    private final int started = 2;
    private final int stopped = 3;
    private final int ended = 4;

    private int EngineState = 0; //0-uninited, 1-inited, 2-started, 3-stopped, 4-Ended
    private boolean brokerChanged = true; //this is true when the code is changed

    private final String BrokerInterface = "broker1.php"; //the brokers file location.

//Finet Express
    private FinetExpress finetExpress;

//THE server address.
    public TeleTextSource teletextSource;
    private String ServerAddress;
    private BrokerSource brokerSource;
    private BrokerPanel ui;
    private String oldCode = "0001";
    public int counttok, counttok2;

    public BrokerEngine() {
    }

    public void SetDataSouce(BrokerSource DS, TeleTextSource TS) {
        brokerSource = DS;
        teletextSource = TS;
    }

    public void SetDisplay(BrokerPanel display) {
        ui = display;
    }

    public void SetServerAddress(String addr) {
        this.ServerAddress = addr;
    }

    public void viewBroker(String ccode) {
        try {
            if (chgBroker(ccode)) {
                ccode = "0000" + ccode;
                ccode = ccode.substring(ccode.length() - 4);
                int iCode = Integer.parseInt(ccode) / 10;
                String ciCode = String.valueOf(iCode);
                brokerSource.cBrokerName = teletextSource.BrokerQueue.elementAt(
                        teletextSource.BrokerQueue.indexOf(ciCode) + 1).toString();
                brokerSource.brokerName = teletextSource.BrokerQueue.elementAt(
                        teletextSource.BrokerQueue.indexOf(ciCode) + 2).toString();
                this.LoadBrokerInfo(ccode);
                ui.UpdateInformation();
            }
        } catch (NoSuchElementException no) {
            ui.Report("Invalid Broker ");
        } catch (NullPointerException nu) {
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void Enable() throws Exception {
        if (EngineState == uninited || EngineState == inited) {
            try {
                start();
            } catch (java.lang.IllegalThreadStateException eee) {
                //System.out.println(eee.getMessage() );
            }
            EngineState = started;
            return;
        } else if (EngineState == started) {
            return;
        } else if (EngineState == stopped) {
            synchronized (this) {
                this.notifyAll();
            }
            EngineState = started;
            return;
        }
    }

    public void Disable() throws Exception {
        if (EngineState == started) {
            EngineState = stopped;
        }
    }

    public boolean chgBroker(String ccode) throws Exception {
        ccode = "0000" + ccode;
        ccode = ccode.substring(ccode.length() - 4);
        if (!ccode.equals(BrokerSource.Code)) {
            oldCode = BrokerSource.Code; //The Engine record the old stock old first in case the new code is not find and replace it..
            BrokerSource.Code = ccode;
            return true;
        }
        return false;
    }

    public boolean LoadBrokerInfo(String code) throws Exception {
        String srcAddr = FinetExpress.getScriptBase() + BrokerInterface + "?code=" +
                code;
        System.out.println(srcAddr);
        URL Finet;
        Finet = new URL(srcAddr);
        URLConnection FinetConnection = Finet.openConnection();
        BufferedReader DS = new BufferedReader(new InputStreamReader(
                FinetConnection.getInputStream()));

        StringTokenizer tokens;
        String RawData;

        this.brokerSource.RelatedBrokers.removeAllElements();
        RawData = DS.readLine();
        tokens = new StringTokenizer(RawData, ";");
        tokens.nextToken();
        RawData = tokens.nextToken();
        tokens = new StringTokenizer(RawData, ".");
        while (tokens.hasMoreTokens()) {
            this.brokerSource.RelatedBrokers.addElement(tokens.nextToken());
        }

        this.brokerSource.BrokerAsk.removeAllElements();
        this.brokerSource.BrokerBid.removeAllElements();

        while (DS.ready()) {
            RawData = DS.readLine();

            if (RawData.trim().length() == 0) {
                continue;
            }

            tokens = new StringTokenizer(RawData, ";");

            if (tokens.countTokens() == 1) {
                continue;
            }

            if (RawData.charAt(0) == '+') {
                while (tokens.hasMoreTokens()) {
                    this.brokerSource.BrokerAsk.addElement(tokens.nextToken(";"));
                }
            } else {
                while (tokens.hasMoreTokens()) {
                    this.brokerSource.BrokerBid.addElement(tokens.nextToken(";"));
                }
            }
        }

        DS.close();

        return true;
    }
}
