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
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.NoSuchElementException;
import java.util.StringTokenizer;

// Save

public class StockWatchEngine
        extends Thread {

// Finet Express
    private FinetExpress finetExpress;

// SaveStockWatchCodes.php
    String SaveStockWatchCodes = "script/SaveStockWatchCodes.php";
    String LoadStockWatchCodes = "script/LoadStockWatchCodes.php";
//  private final String StockInterface = "/stock/";
//THE server address.
    private String ServerAddress;
    boolean Alert = false;

    //the EngineState
    private final int uninited = 0;
    private final int inited = 1;
    private final int started = 2;
    private final int stopped = 3;
    private final int ended = 4;

    public int EngineState = uninited;
    private StockWatchPanel ui;
    private StockWatchSource stockwatchSource;

    public StockWatchEngine(FinetExpress finet) {
        finetExpress = finet;
    }

    public boolean CheckAlert() throws Exception {

        boolean Flag = false;
        //get back all select item.
        int NumberOfStocks = stockwatchSource.MyStock.size();
        Item currentItems[] = new Item[NumberOfStocks];
        for (int i = 0; i < NumberOfStocks; i++) {
            currentItems[i] = (Item) stockwatchSource.MyStock.elementAt(i); // .get(i);
        }

        for (int i = 0; i < NumberOfStocks; i++) {
            //System.out.println("Check alert");
            currentItems[i].Alert = false;
            if (!currentItems[i].AlertHigh.equals("")) {
                if (Float.valueOf(currentItems[i].Last).floatValue() >=
                        Float.valueOf(currentItems[i].AlertHigh).floatValue()) {
                    //the price reach our target
                    currentItems[i].Alert = true;
                    Flag = true;
                }
            }

            if (!currentItems[i].AlertLow.equals("")) {
                if (Float.valueOf(currentItems[i].Last).floatValue() <=
                        Float.valueOf(currentItems[i].AlertLow).floatValue()) {
                    //the price reach our target
                    currentItems[i].Alert = true;
                    Flag = true;
                }
            }
        }
        Alert = Flag;
        return Flag; //return true if there are any alert item.
    }

    public void SetDisplay(StockWatchPanel np) {
        ui = np;
    }

    public void SetDataSource(StockWatchSource t10S) {
        stockwatchSource = t10S;
    }

    public boolean AddStock(int aCode) throws Exception {
        Item newCode = new Item();
        newCode.Code = String.valueOf(aCode);
//    stockwatchSource.MyStock.addElement(newCode);

        try {
            //System.out.println("["+citem.Code+"]");
            this.GetCodeDetails(newCode);
//      newCode.Name = GetCodeName(newCode.Code);
            GetCodeName(newCode, newCode.Code);
            CheckAlert();
            stockwatchSource.MyStock.addElement(newCode);
            ui.UpdateInformation();
            return true;
        } catch (NumberFormatException fe) {
            ui.Report("Number Format exception");
            return false;
//      RemoveStock((new Integer(newCode.Code)).intValue());
        } catch (FileNotFoundException fe) {
            ui.Report("Invalid Stock Code");
            return false;
//      RemoveStock((new Integer(newCode.Code)).intValue());
        } catch (NoSuchElementException ex) {
            System.out.println("Add stock: No Such Element");
            return false;
        }

    }

    public void RemoveStock(int aCode) throws Exception {
        for (int i = 0; i < stockwatchSource.MyStock.size(); i++) {
            Item citem = (Item) stockwatchSource.MyStock.elementAt(i); // .get(i);
            if (Integer.parseInt(citem.Code) == aCode) {
                stockwatchSource.MyStock.removeElement(citem);
                break;
            }
        }
        ui.UpdateInformation();

    }

    public void Enable() {
        //System.out.println(EngineState);
        try {
            ui.Report("Stock Watch Loading User Stock Watch ...");
            LoadUserProfile();
            ui.Report("Stock Watch Load User Profile Done ...");
            ui.Enable();
        } catch (NumberFormatException ex) {
            ex.printStackTrace();
            ui.Report("NumberFormatException ...");
        } catch (Exception ex) {
            ex.printStackTrace();
            ui.Report("Stock Watch failed to load User Profile Done ...");
        }

        try {
            ui.UpdateRmCodeList();

            if (EngineState == uninited || EngineState == inited) {
                try {
                    start();
                } catch (java.lang.IllegalThreadStateException iee) {
                    System.out.println(iee.getMessage());
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
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void Disable() {
        if (EngineState == started) {
            EngineState = stopped;
        }
        System.out.println("Saving stock watch 1");
        ui.Disable();
    }

    public void LoadUserProfile() throws Exception {

        if (stockwatchSource.MyStock.size() > 0) {
            return;
        }

        String srcAddr = FinetExpress.getScriptBase() + LoadStockWatchCodes;
        String parameter = "?company=" + FinetExpress.getCompany() + "&userid=" +
                FinetExpress.getUserid();
        URL Finet;
        Finet = new URL(srcAddr + parameter);

        URLConnection FinetConnection = Finet.openConnection();
        BufferedReader DS = new BufferedReader(new InputStreamReader(
                FinetConnection.getInputStream()));

        String RawData = DS.readLine();
        DS.close();

        StringTokenizer tokens = new StringTokenizer(RawData);

        String tempString;
        tempString = tokens.nextToken("_");

//      if (tempString.toLowerCase().trim().equals("<br>"))     tempString = "0";  // error, exit

        int NumberOfStocks;

        try {
            NumberOfStocks = Integer.parseInt(tempString);
            //System.out.println("Number Of stock is :" + NumberOfStocks);
        } catch (NumberFormatException ex) {
            NumberOfStocks = 0;
        }

        stockwatchSource.MyStock.removeAllElements();
        //System.out.println("Clear : " + stockwatchSource.MyStock.size());

        for (int i = 0; i < NumberOfStocks; i++) {
            try {
                tempString = tokens.nextToken("_");
                Integer.parseInt(tempString);
            } catch (NumberFormatException ex) {
                return;
            }

            Item newCode = new Item();
//        newCode.Code = tokens.nextToken("_");
            newCode.Code = tempString;

            tempString = tokens.nextToken("_");
            if (!tempString.equals("X")) {
                newCode.AlertHigh = tempString;

            }
            tempString = tokens.nextToken("_");
            if (!tempString.equals("X")) {
                newCode.AlertLow = tempString;

            }
            stockwatchSource.MyStock.addElement(newCode);
        }
    }

    public void SaveUserProfile() throws Exception {
        //Create the DataString to upload........../////////////////////////////////
        ui.Report("Stock Watch Save User Profile ...");
        String DataString = new String("");
        DataString = DataString + stockwatchSource.MyStock.size();
        for (int i = 0; i < stockwatchSource.MyStock.size(); i++) {
            Item citem = (Item) stockwatchSource.MyStock.elementAt(i); // .get(i);
            String dcode = citem.Code;
            String dAlertHigh = citem.AlertHigh;
            String dAlertLow = citem.AlertLow;
            if (dAlertHigh.equals("")) {
                dAlertHigh = "X";
            }
            if (dAlertLow.equals("")) {
                dAlertLow = "X";
            }
            DataString = DataString + "_" + dcode + "_" + dAlertHigh + "_" +
                    dAlertLow;
        }
//    System.out.println(DataString);
        System.out.println("mystock.size() " + stockwatchSource.MyStock.size());

        //Upload the User profile///////////////////////////////////////////////////
        String srcAddr = FinetExpress.getScriptBase() + SaveStockWatchCodes;
        String parameter = "?company=" + FinetExpress.getCompany() + "&userid=" +
                FinetExpress.getUserid();
        parameter = parameter + "&stockwatch=" + DataString;
        URL Finet;
        Finet = new URL(srcAddr + parameter);
        URLConnection FinetConnection = Finet.openConnection();
        BufferedReader DS = new BufferedReader(new InputStreamReader(
                FinetConnection.getInputStream()));
        String r = DS.readLine();
        DS.close();
        ui.Report("Stock Watch Save User Profile Done.");
    }

    public void run() {
        while (EngineState != ended) {
            try {
                //System.out.println("Number Of Code added: " + stockwatchSource.MyStock.size());
                refreshData();
                try {
                    if (ui.getState()) {
                        sleep(4000);
                    } else {
                        sleep(40000);
                    }
                    synchronized (this) {
                        if (EngineState == 3) {
                            wait();
                        }
                    }
                } catch (InterruptedException ie) {
                }
            } // end try
            catch (Exception ex) {
//        ui.Report("StockWatchEngine::Run " + ex.getMessage());
                ex.printStackTrace();
            }
        } // end while
    }

    public void refreshData() {
        try {
            if (stockwatchSource.MyStock.size() > 0) {
                for (int i = 0; i < stockwatchSource.MyStock.size(); i++) {
                    Item citem = (Item) stockwatchSource.MyStock.elementAt(i); // .get(i);
                    try {
                        //System.out.println("["+citem.Code+"]");
                        this.GetCodeDetails(citem);
                        if (citem.Name == "") {
//                citem.Name = GetCodeName(citem.Code);
                            GetCodeName(citem, citem.Code);
                        }
                    } // end try
                    catch (FileNotFoundException fe) {
                        ui.Report("Invalid Stock Code");
                        RemoveStock((new Integer(citem.Code)).intValue());
                    } catch (NoSuchElementException ex) {
                        System.out.println("run: No Such Element");
                        RemoveStock((new Integer(citem.Code)).intValue());
                    }
                } // end for
            } // end if
            CheckAlert();
            ui.UpdateInformation();
        } catch (Exception ex) {
//        ui.Report("StockWatchEngine::Run " + ex.getMessage());
            ex.printStackTrace();
        }

    }

//  private String GetCodeName(String scode) throws Exception
    private void GetCodeName(Item itmInput, String scode) throws
            NoSuchElementException, Exception {
//    try
        {
            String srcAddr = scode + ".s";
            String RawData = RealTimeStock.getRawString(srcAddr,
                    finetExpress.passcode);
            StringTokenizer tokens = new StringTokenizer(RawData);
            tokens.nextToken(","); //retrieve the code
//      return tokens.nextToken(",");     //retrieve the Name
            itmInput.Name = tokens.nextToken(","); //retrieve the Name
            itmInput.cName = tokens.nextToken(","); //retrieve the Name
        }
//    catch (NoSuchElementException ex)
        {
//      System.out.println("No Such Element");
        }
    }

    private void GetCodeDetails(Item codeItem) throws NoSuchElementException,
            Exception {
        String srcAddr = "0000" + codeItem.Code + ".i";
        srcAddr = srcAddr.substring(srcAddr.length() - 6);
        String RawData = RealTimeStock.getRawString(srcAddr, FinetExpress.passcode);

        StringTokenizer tokens = new StringTokenizer(RawData);
        String tempString;

        codeItem.Code = tokens.nextToken(";");
        tempString = tokens.nextToken(";");
        tempString = tokens.nextToken(";");
        tempString = tokens.nextToken(";");
        tempString = tokens.nextToken(";");
        tempString = tokens.nextToken(";");
        codeItem.High = tokens.nextToken(";");
        codeItem.Low = tokens.nextToken(";");
        codeItem.Last = tokens.nextToken(";");
        codeItem.Change = tokens.nextToken(";");
        codeItem.PCChg = tokens.nextToken(";");
        codeItem.Volume = tokens.nextToken(";");
        codeItem.Turnover = tokens.nextToken(";");
        tempString = tokens.nextToken(";");
        tempString = tokens.nextToken(";");
        tempString = tokens.nextToken(";");
        tempString = tokens.nextToken(";");
        tempString = tokens.nextToken(";");
        tempString = tokens.nextToken(";");
    }

    public void SetServerAddress(String addr) {
        this.ServerAddress = addr;
    }
}