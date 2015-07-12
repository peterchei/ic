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
import java.util.StringTokenizer;

public class Top10Engine
        extends Thread {
//The interfaces of Top10:
    //  The Interfaces for chinese;
    private final String Top10GainInterface = "top10/gain.txt"; //Retrieve Top 10 gain list
    private final String Top10LossInterface = "top10/loss.txt"; //Retrieve Top 10 loss list
    private final String Top10VolumeInterface = "top10/vol.txt"; //Retrieve Top 10 volume list
    private final String Top10TurnoverInterface = "top10/turn.txt"; //Retrieve Top 10 turnover list

//  private final String StockInterface = "/stock/";                      //the dir of stocks..

//THE server address.
    private String ServerAddress;

    //the EngineState
    private final int uninited = 0;
    private final int inited = 1;
    private final int started = 2;
    private final int stopped = 3;
    private final int ended = 4;

    public int EngineState = uninited;
    private Top10Panel ui;
    private Top10Source top10Source;

    private boolean changeMark = false;

    public Top10Engine() {
    }

    public void SetDisplay(Top10Panel np) {
        ui = np;
    }

    public void SetDataSource(Top10Source t10S) {
        top10Source = t10S;
    }

    public void Enable() throws Exception {
        //System.out.println(EngineState);
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

    public void SetTop10Type(int sTop10) throws Exception {
        top10Source.Type = sTop10;
        changeMark = true;
        synchronized (this) {
            this.notifyAll();
        }
    }

    public void run() {
        long timeStamp = System.currentTimeMillis();
        try {
            ui.Report("Top 10 Engine Loading..........");
            GetTopList(3);
            GetTopList(2);
            GetTopList(1);
            GetTopList(0);
            ui.Report("");
        } catch (Exception ex) {
            ui.Report("Top10Engine::Run Initialization: " + ex.getMessage());
            ex.printStackTrace();
        }

        while (EngineState != ended) {
            try {
                // the system updata the top list every 1 mins
                if ((System.currentTimeMillis() - timeStamp) > 60000) {
                    //System.out.println(System.currentTimeMillis()-timeStamp);
                    GetTopList(top10Source.Type);
                    timeStamp = System.currentTimeMillis();
                }
                ui.UpdateInformation();
                ui.ReportServerState(0);
                sleep(4000);
                synchronized (this) {
                    if (EngineState == 3) {
                        wait();
                    }
                }
            } catch (InterruptedException exception) {
            } catch (Exception ex) {
//        ui.Report("Top10Engine::Run "+ ex.getMessage());
                ex.printStackTrace();
            }
        }

    }

    private boolean GetTopList(int Type) throws Exception {
        //System.out.println("GetTop10");
        String srcAddr = new String();
        Item currentItems[] = new Item[10];

        if (Type == Top10Source.typeTop10Gain) {
            srcAddr = FinetExpress.getScriptBase() + this.Top10GainInterface;
//          System.out.println(srcAddr);

            currentItems = top10Source.TopGain;
        } else if (Type == Top10Source.typeTop10Lose) {
            srcAddr = FinetExpress.getScriptBase() + this.Top10LossInterface;
//          System.out.println(srcAddr);
            currentItems = top10Source.TopLose;
        } else if (Type == Top10Source.typeTop10Volume) {
            srcAddr = FinetExpress.getScriptBase() + this.Top10VolumeInterface;
//          System.out.println(srcAddr);
            currentItems = top10Source.TopVolume;
        } else if (Type == Top10Source.typeTup10Turnover) {
            srcAddr = FinetExpress.getScriptBase() + this.Top10TurnoverInterface;
//          System.out.println(srcAddr);
            currentItems = top10Source.TopTurnover;
        }

        URL Finet;
        Finet = new URL(srcAddr);
//          System.out.println(srcAddr);
        URLConnection FinetConnection = Finet.openConnection();
        BufferedReader DS = new BufferedReader(new InputStreamReader(
                FinetConnection.getInputStream()));

        // retrieve Top10 Company Code and Name
        int i = 0;
        String fullDataString = new String();
        String tempString = DS.readLine();
        while (tempString != null) {
//          System.out.println(tempString);

            StringTokenizer tokens = new StringTokenizer(tempString);
            currentItems[i].Code = "0000" + tokens.nextToken("\t");
            currentItems[i].Code = currentItems[i].Code.substring(currentItems[i].
                    Code.length() - 4);

            currentItems[i].Name = tokens.nextToken("\t");
            currentItems[i].cName = tokens.nextToken("\t");
            // init the other items
            currentItems[i].Last = tokens.nextToken("\t");
            currentItems[i].prevClose = tokens.nextToken("\t");
            currentItems[i].Change = tokens.nextToken("\t");
            currentItems[i].PCChg = tokens.nextToken("\t");
            currentItems[i].High = tokens.nextToken("\t");
            currentItems[i].Low = tokens.nextToken("\t");
            currentItems[i].Volume = tokens.nextToken("\t");
            currentItems[i].Open = tokens.nextToken("\t");
            currentItems[i].Turnover = tokens.nextToken("\t");
            i++;
            if (i == 10) {
                break;
            }
            tempString = DS.readLine();
        }
        DS.close();
        return true;

    }

    public void SetServerAddress(String addr) {
        this.ServerAddress = addr;
    }

}