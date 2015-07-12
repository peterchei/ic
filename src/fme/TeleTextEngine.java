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
import java.util.Date;
import java.util.NoSuchElementException;
import java.util.StringTokenizer;

public class TeleTextEngine
        extends Thread {
//The Cgi Interfaces:
    private final String BrokersInterface = "broker1.txt"; //the brokers file location.
    private final String HSIInterface = "HSIInfo.dat"; //Store the HSI index information.

//Finet Express
    private FinetExpress finetExpress;

//THE server address.
    private String ServerAddress;
    private TeleTextSource teletextSource;
    private TeleTextPanel ui;
    private int mInterval;
    private String oldCode = "0001";

//the EngineState
    private final int uninited = 0;
    private final int inited = 1;
    private final int started = 2;
    private final int stopped = 3;
    private final int ended = 4;
    private int EngineState = 0; //0-uninited, 1-inited, 2-started, 3-stopped, 4-Ended
    private boolean codeChanged = true; //this is true when the code is changed
    private int gCode = 1;
    private int count = 0;

    public TeleTextEngine(FinetExpress finet) {
        finetExpress = finet;
    }

    public void SetDataSouce(TeleTextSource DS) {
        teletextSource = DS;
    }

    // Change the fme code
    public boolean chgStock(String ccode) throws Exception {
        ccode = "0000" + ccode;
        ccode = ccode.substring(ccode.length() - 4);
        if (!ccode.equals(teletextSource.Code)) {
            oldCode = teletextSource.Code; //The Engine record the old stock old first in case the new code is not find and replace it..
            teletextSource.Code = ccode;
            return true;
        }
        return false;
    }

    // Require the stock with code cccode
    public void viewStock(String ccode) {
        String OldCode = teletextSource.Code;
        try {
            if (chgStock(ccode)) {
                getData();
                ui.UpdateInformation();
                ui.ReportServerState(0);
            }
        } catch (NoSuchElementException no) {
            teletextSource.Code = OldCode;
        } catch (NullPointerException nu) {
            teletextSource.Code = OldCode;
        } catch (StringIndexOutOfBoundsException siobe) {
            teletextSource.Code = OldCode;
            ui.Report("Invalid Stock Code");
            ui.lbdatetime.setText("Invalid Stock Code");
        } catch (Exception e) {
            teletextSource.Code = OldCode;
            e.printStackTrace();
        }

    }

    public void getData() throws Exception {
        if (gCode != Integer.parseInt(teletextSource.Code)) {
            gCode = Integer.parseInt(teletextSource.Code);
            this.codeChanged = true; //means that the User select a new code.
        }
        GetStockData(gCode);

//      System.out.println("Codechange = " + codeChanged);
        if (codeChanged == true) {
            GetBrokerQueueData(gCode);
            GetGeneralInfo(gCode);
            if (ui.lstLog.isVisible()) {
                GetLog();
            }
        } else if (count > 3) {
            GetBrokerQueueData(gCode);
            GetHSI();
            GetGeneralInfo(gCode);
            count %= 3;
        } else {
            count++;
        }
    }

    public void run() {
        // Thread Initialization
        boolean brokerInfoReady = false;
        while (!brokerInfoReady) {
            try {
                GetPage();
                getData(); // get .i / .q / index.txt / .s from the internet
                GetHSI();
                ui.UpdateInformation();
                System.out.println("Loading Broker Information");
                LoadBrokerInfo();
                brokerInfoReady = true;
                System.out.println("Loading Brokers OK");
            } catch (NullPointerException nu) {
            } catch (Exception ex) {
                ui.Report("TeletextEngine::Run " + ex.getMessage());
                ex.printStackTrace();
                try { // sleep 2 sec to load the broker info again
                    sleep(2000);
                } catch (NullPointerException nu) {
                } catch (InterruptedException exception) {
                }

            }
        }

        while (EngineState != ended) {
            try {
                //synchronized(this) is used for enable and disable methods.
                synchronized (this) { //it is used to supend and resume the run() thread..
                    getData(); // get .i / .q / index.txt / .s from the internet
//          GetHSI();
                    ui.UpdateInformation();
                    ui.ReportServerState(0);
                    codeChanged = false;
                    sleep(500);
                    if (EngineState == 3) {
                        //System.out.println("TeletextEngine Stopped");
                        wait();
                    }
                }
            } catch (InterruptedException exception) {
            } catch (NoSuchElementException no) {
                ui.Report("Invalid Stock Code");
                ui.lbdatetime.setText("Invalid Stock Code");
            }
                    /*      catch (NullPointerException nu) {}
                          catch (StringIndexOutOfBoundsException siobe) {}
                     */ catch (Exception ex) {
                ui.Report("TeletextEngine::Run " + ex.getMessage());
                System.out.println("Exception");
                ex.printStackTrace();
                //ui.ReportServerState(1); // some problem in retrieveing data.
                try {
                    sleep(5000);
                } catch (InterruptedException exception) {
                }
            }
        }
    }

    public void Enable() {

        //System.out.println(EngineState);
        if (EngineState == uninited) {
            EngineState = inited;
        }

        if (EngineState == uninited || EngineState == inited) {

            try {
                start();
            } catch (Exception ex) {
                ui.Report(ex.getMessage());
            }
            EngineState = started;
            return;
        } else if (EngineState == started) {
            return;
        } else if (EngineState == stopped) {
            synchronized (this) {
                this.notifyAll(); // wake up the run();
            }
            EngineState = started;
            return;
        }
    }

    public void Disable() {
        if (EngineState == started) {
            EngineState = stopped;
        }
        if (EngineState == inited) {
            EngineState = stopped;
        }
    }

    //This function load the broker's Company Name to the Applet
    private boolean LoadBrokerInfo() throws Exception {
        String srcAddr = FinetExpress.getScriptBase() + BrokersInterface;

        URL Finet;
        Finet = new URL(srcAddr);
        URLConnection FinetConnection = Finet.openConnection();
        BufferedReader DS = new BufferedReader(new InputStreamReader(
                FinetConnection.getInputStream()));
        String RawData = DS.readLine();
        StringTokenizer tokens = new StringTokenizer(RawData);

        String inputLine = "";

        while (DS.ready()) {
            inputLine = DS.readLine();
            tokens = new StringTokenizer(inputLine);
            teletextSource.BrokerQueue.addElement(tokens.nextToken(";").trim());
            teletextSource.BrokerQueue.addElement(tokens.nextToken(";").trim());
            teletextSource.BrokerQueue.addElement(tokens.nextToken(";").trim());
        }
        System.out.println(inputLine);
        Date abc = new java.util.Date();
        System.out.println(abc.getTime());
        finetExpress.brokerPanel1.setComboBox();
        abc = new java.util.Date();
        System.out.println(abc.getTime());

        DS.close();
        this.EngineState = inited;

        return true;
    }

    // get .i file
    private boolean GetStockData(int iCode) throws NullPointerException,
            Exception {
        String srcAddr = "0000";
        srcAddr = srcAddr + iCode + ".i";
        srcAddr = srcAddr.substring(srcAddr.length() - 6);
        String RawData = RealTimeStock.getRawString(srcAddr, finetExpress.passcode);

        StringTokenizer tokens = new StringTokenizer(RawData);

        tokens.nextToken(";");
        teletextSource.date = tokens.nextToken(";");
        teletextSource.time = tokens.nextToken(";").substring(0, 5);
        teletextSource.currbid = tokens.nextToken(";");
        teletextSource.currask = tokens.nextToken(";");
        teletextSource.open = tokens.nextToken(";");
        teletextSource.High = tokens.nextToken(";");
        teletextSource.Low = tokens.nextToken(";");

        double avg = (Float.valueOf(teletextSource.High).floatValue() +
                Float.valueOf(teletextSource.Low).floatValue()) * 0.5;
        teletextSource.AvgPrice = String.valueOf(avg);

        teletextSource.Nominal = tokens.nextToken(";");
        teletextSource.Chg = tokens.nextToken(";");
        teletextSource.PecentChg = tokens.nextToken(";");
        teletextSource.Volume = tokens.nextToken(";");
        ;
        teletextSource.Turnover = tokens.nextToken(";");
        teletextSource.Yield = tokens.nextToken(";");
        ;
        teletextSource.PE = tokens.nextToken(";");
        teletextSource.premium = tokens.nextToken(";");
        teletextSource.Wration = tokens.nextToken(";");
        teletextSource.yrhigh = tokens.nextToken(";");
        teletextSource.yrlow = tokens.nextToken(";");

        for (int i = 0; i < 5; i++) {
            teletextSource.ask[i] = tokens.nextToken(";");

        }
        for (int i = 0; i < 5; i++) {
            teletextSource.iask[i] = tokens.nextToken(";");

        }
        for (int i = 0; i < 5; i++) {
            teletextSource.nask[i] = tokens.nextToken(";");

            // buy summary,
        }
        for (int i = 0; i < 5; i++) {
            teletextSource.bid[i] = tokens.nextToken(";");

        }
        for (int i = 0; i < 5; i++) {
            teletextSource.ibid[i] = tokens.nextToken(";");

        }
        for (int i = 0; i < 5; i++) {
            teletextSource.nbid[i] = tokens.nextToken(";");

            //trade activity summary
        }
        int noTrade = 4;
        for (int i = 0; i < noTrade; i++) {
            teletextSource.act[noTrade - 1 - i] = tokens.nextToken(";");

        }
        tokens.nextToken(";");

        for (int i = 0; i < noTrade; i++) {
            teletextSource.nact[noTrade - 1 - i] = tokens.nextToken(";");

        }
        tokens.nextToken(";");

        for (int i = 0; i < noTrade; i++) {
            teletextSource.tact[noTrade - 1 - i] = tokens.nextToken(";");

        }
        teletextSource.LowerSpread = tokens.nextToken(";");
        teletextSource.UpperSpread = tokens.nextToken(";");
        tokens.nextToken(";");
        teletextSource.PrevClose = tokens.nextToken(";");

        return true;
    }

    // This function get the Broker Queue Data (.q file)
    private boolean GetBrokerQueueData(int iCode) throws NullPointerException,
            Exception {
        String srcAddr = "0000";
        srcAddr = srcAddr + iCode + ".q";
        srcAddr = srcAddr.substring(srcAddr.length() - 6);
        String RawData = RealTimeStock.getRawString(srcAddr, finetExpress.passcode);
        StringTokenizer tokens = new StringTokenizer(RawData);

        for (int j = 0; j < 10; j++) {
            String rowString = tokens.nextToken(";");
            //System.out.println(rowString);
            teletextSource.SellBroker[0][j] = rowString.substring(0, 5).trim();
            teletextSource.SellBroker[1][j] = rowString.substring(5, 10).trim();
            teletextSource.SellBroker[2][j] = rowString.substring(10, 15).trim();
            teletextSource.SellBroker[3][j] = rowString.substring(15, 20).trim();
        }
        for (int j = 0; j < 10; j++) {
            String rowString = tokens.nextToken(";");
            //System.out.println(rowString);
            teletextSource.BuyBroker[0][j] = rowString.substring(0, 5).trim();
            teletextSource.BuyBroker[1][j] = rowString.substring(5, 10).trim();
            teletextSource.BuyBroker[2][j] = rowString.substring(10, 15).trim();
            teletextSource.BuyBroker[3][j] = rowString.substring(15, 20).trim();
        }
        return true;
    }

    private boolean GetHSI() throws NullPointerException, Exception {
        BufferedReader DS = RealTimeStock.getStream(HSIInterface,
                finetExpress.passcode);

        String RawData = DS.readLine();
        StringTokenizer tokens = new StringTokenizer(RawData);

        tokens.nextToken(";");
        teletextSource.HSI = FormatData.roundOff(tokens.nextToken(";"));
        String strTemp = tokens.nextToken(";");
//      System.out.println(strTemp);
        if (Float.valueOf(strTemp).floatValue() > 0) {
            teletextSource.HSI = teletextSource.HSI + " +" +
                    FormatData.roundOff(strTemp);
        } else {
            teletextSource.HSI = teletextSource.HSI + " " +
                    FormatData.roundOff(strTemp);
//      System.out.println(teletextSource.HSI);

        }
        RawData = DS.readLine();
        tokens = new StringTokenizer(RawData);

        tokens.nextToken(";");
        teletextSource.HSIF = FormatData.roundOff(tokens.nextToken(";"));
//      System.out.println(teletextSource.HSIF);
        strTemp = tokens.nextToken(";");

        if (Float.valueOf(strTemp).floatValue() > 0) {
            teletextSource.HSIF = teletextSource.HSIF + " +" +
                    FormatData.roundOff(strTemp);
        } else {
            teletextSource.HSIF = teletextSource.HSIF + " " +
                    FormatData.roundOff(strTemp);

//      System.out.println(teletextSource.HSIF);

        }
        return true;
    }

    public boolean GetLog() {
        String srcAddr = "0000";
        srcAddr = srcAddr + teletextSource.Code + ".t";
        srcAddr = srcAddr.substring(srcAddr.length() - 6);

        try {
            BufferedReader DS = RealTimeStock.getStream(srcAddr,
                    finetExpress.passcode);

            String RawData, logItem, sTime, sPrice, sVol;
            StringTokenizer tokens;
            ui.lstLog.removeAll();
            while (DS.ready()) {
                RawData = DS.readLine();
//         System.out.println(RawData);
                tokens = new StringTokenizer(RawData);
                tokens.nextToken(" ");

                sTime = RawData.substring(0, 6);
                sPrice = "       " + (tokens.nextToken(" "));
                sPrice = sPrice.substring(sPrice.length() - 8);
                sVol = "      " + FormatData.FormatString2(tokens.nextToken(" "), 1);
                sVol = sVol.substring(sVol.length() - 7);
                logItem = sTime + sPrice + sVol;
                ui.lstLog.add(logItem);

            }

            DS.close();
        } catch (Exception e) {
            System.out.println("GetLog exception");
        }
        return true;
    }

    public boolean GetPage() {
        String srcAddr = "0781";

        try {
            BufferedReader DS = RealTimeStock.getStream(srcAddr,
                    finetExpress.passcode);

            String RawData, logItem, sTime, sPrice, sVol;
            while (DS.ready()) {
                RawData = DS.readLine().substring(1);
                System.out.println(RawData);
                ui.textArea1.append(RawData + "\n");
            }
            DS.close();
            System.out.println(ui.textArea1.getRows() + " + " +
                    ui.textArea1.getColumns());

        } catch (Exception e) {
            System.out.println("GetPage exception");
        }
        return true;
    }

    private boolean GetGeneralInfo(int iCode) throws NullPointerException,
            Exception {
//      System.out.println("Getting .s");
        String srcAddr = "0000";
        srcAddr = srcAddr + iCode + ".s";
        srcAddr = srcAddr.substring(srcAddr.length() - 6);
        String RawData = RealTimeStock.getRawString(srcAddr, finetExpress.passcode);
        StringTokenizer tokens = new StringTokenizer(RawData);
        tokens.nextToken(",");
        teletextSource.Name = tokens.nextToken(",");
        teletextSource.CName = tokens.nextToken(",");
        tokens.nextToken(",");
        teletextSource.LotSize = tokens.nextToken(",");
        teletextSource.numshare = tokens.nextToken(",");
        return true;
    }

    public void SetDisplay(TeleTextPanel display) {
        ui = display;
    }

    public void SetServerAddress(String addr) {
        this.ServerAddress = addr;
    }
}
