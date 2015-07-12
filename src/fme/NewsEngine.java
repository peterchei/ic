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

public class NewsEngine
        extends Thread {
    //  The Interfaces for chinese;
    private final String CBodyInterface = "news/story.php"; //retrieve the body
    private final String CHeadersInterface = "news/latest.php"; //get the lastest headers
    private final String CHeadlinesInterface = "news/headline.php"; //get headers given the range
    private final String CNewNewsInterface = "news/nof.php"; //get the newest header ID.

    //  The Interfaces for English;
    private final String EBodyInterface = "news/story_e.php";
    private final String EHeadersInterface = "news/latest_e.php";
    private final String EHeadlinesInterface = "news/headline_e.php";
    private final String ENewNewsInterface = "news/nof_e.php";

    //////////////////////////////////////////////

    //  The active cgi interfaces......./////////////////
    private String BodyInterface;
    private String HeadersInterface;
    private String HeadlinesInterface;
    private String NewNewsInterface;

    private int HeaderID;
    private int newsIndex;

//THE server address.
    private String ServerAddress;

//the EngineState
    private final int uninited = 0;
    private final int inited = 1;
    private final int started = 2;
    private final int stopped = 3;
    private final int ended = 4;

    private NewsSource newsSource = null;
    private NewsPanel ui = null;
    private int EngineState = uninited;

    public void SetDataSource(NewsSource nS) {
        newsSource = nS;
    }

    public void SetDisplay(NewsPanel np) {
        ui = np;
    }

    public void Enable() {
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

    public void Disable() {
        if (EngineState == started) {
            EngineState = stopped;
        }
    }

    public NewsEngine(int language) {
        if (language == FinetExpress.constEnglish) {
            BodyInterface = EBodyInterface;
            HeadersInterface = EHeadersInterface;
            HeadlinesInterface = EHeadlinesInterface;
            NewNewsInterface = ENewNewsInterface;
        } else if (language == FinetExpress.constChinese) {
            BodyInterface = CBodyInterface;
            HeadersInterface = CHeadersInterface;
            HeadlinesInterface = CHeadlinesInterface;
            NewNewsInterface = CNewNewsInterface;
        }
    }

    public void run() {
        boolean ready = false;
        while (!ready) {
            try {
                ui.Report("News Initializing.........");
                GetHeaders(10);
                ui.Report("");
                ui.UpdateInformation();
                ui.Report("News Done.");
                ready = true;
            } catch (Exception ex) {
//        ui.Report("NewsEngine::Run Initialization" + ex.getMessage());
                ex.printStackTrace();
                try {
                    sleep(5000);
                } catch (InterruptedException ie) {
                    System.out.println("interrupted");
                }
            }
        }

        while (EngineState != ended) {
            try {
                sleep(30000);
                UpdateNews();
                synchronized (this) {
                    if (EngineState == 3) {
                        wait();
                    }
                }
            } catch (InterruptedException ex) {
                System.out.println("interrupted");
            } catch (NumberFormatException ex) {
                System.out.println("numeric");
            } catch (Exception ex) {
                ui.Report("NewsEngine::Run " + ex.getMessage());
                ex.printStackTrace();
            }
        }
    }

    private void GetHeaders(int NumberOfHeaders) throws Exception {
        try {
            //System.out.println("GetHeader!");
            String srcAddr = new String();

            srcAddr = FinetExpress.getScriptBase() + this.HeadersInterface +
                    "?number=" + NumberOfHeaders;
            System.out.println(srcAddr);

            URL Finet;
            Finet = new URL(srcAddr);
            URLConnection FinetConnection = Finet.openConnection();
            BufferedReader DS = new BufferedReader(new InputStreamReader(
                    FinetConnection.getInputStream()));

            newsSource.NumberOfHeaders = 0;

            for (int i = NumberOfHeaders; i > 0; i--) {
                String tempString;
                tempString = DS.readLine();
                newsSource.newsObjects[i -
                        1].Date = tempString.substring(0, tempString.indexOf(";"));
                tempString = DS.readLine();
                newsSource.newsObjects[i -
                        1].Time = tempString.substring(0, tempString.indexOf(";"));
                tempString = DS.readLine();
                newsSource.newsObjects[i -
                        1].ID = Integer.parseInt(tempString.
                        substring(0, tempString.indexOf(";")));
                tempString = DS.readLine();
                newsSource.newsObjects[i -
                        1].Type = tempString.substring(0, tempString.indexOf(";"));
                tempString = DS.readLine();
                newsSource.newsObjects[i -
                        1].Header = tempString.substring(0, tempString.indexOf(";"));
                newsSource.NumberOfHeaders++;
                DS.readLine();
            }
        } catch (StringIndexOutOfBoundsException se) {
            System.out.println("StringIndexOutOfBoundsException");
        }
    }

    private void UpdateNews() throws Exception {
        int lastID, oldlastID;
        if (newsSource.NumberOfHeaders > 0) {
            oldlastID = newsSource.newsObjects[newsSource.NumberOfHeaders - 1].ID;
        } else {
            oldlastID = -1;
        }

        String srcAddr = new String();
        srcAddr = FinetExpress.getScriptBase() + this.NewNewsInterface;

        URL Finet;
        Finet = new URL(srcAddr);
        URLConnection FinetConnection = Finet.openConnection();
        BufferedReader DS = new BufferedReader(new InputStreamReader(
                FinetConnection.getInputStream()));
        String s = DS.readLine();

        DS.close();
        lastID = Integer.parseInt(s);
        if (lastID > oldlastID) {
            System.out.println("Need to get more news " + lastID + " " + oldlastID);
            GetMoreHeaders(lastID, oldlastID + 1);
        }
    }

    private void GetMoreHeaders(int ToID, int FromID) throws Exception {
        for (int i = FromID; i <= ToID; i++) {
            try {
                //System.out.println("GetHeade " + i);
                String srcAddr = new String();
                srcAddr = FinetExpress.getScriptBase() + this.HeadlinesInterface +
                        "?code=" + i;
                URL Finet;
                Finet = new URL(srcAddr);
                System.out.println("url: " + Finet);
                URLConnection FinetConnection = Finet.openConnection();
                BufferedReader DS = new BufferedReader(new InputStreamReader(
                        FinetConnection.getInputStream()));

                //Receive the data
                String tempDate, tempTime, tempType, tempHeader;
                int tempID;
//        DS.readLine();
                tempDate = DS.readLine();
                tempTime = DS.readLine();
                tempType = DS.readLine();
                tempHeader = DS.readLine();

                //add the Header to the source.
                newsSource.newsObjects[newsSource.NumberOfHeaders].Date = tempDate.
                        substring(0, tempDate.indexOf(";"));
                ;
                newsSource.newsObjects[newsSource.NumberOfHeaders].Time = tempTime.
                        substring(0, tempTime.indexOf(";"));
                ;
                newsSource.newsObjects[newsSource.NumberOfHeaders].ID = i;
                newsSource.newsObjects[newsSource.NumberOfHeaders].Type = tempType.
                        substring(0, tempType.indexOf(";"));
                ;
                newsSource.newsObjects[newsSource.NumberOfHeaders].Header = tempHeader.
                        substring(0, tempHeader.indexOf(";"));
                ;
                newsSource.NumberOfHeaders++;
            } catch (Exception ex) {
                System.out.println("Exception in getting more headers");
            }
            ui.UpdateInformation();
        }
    }

    public void GetBody(int HeaderID, int newsIndex) throws Exception {
        //System.out.println("GetHeader!");
        String srcAddr = new String();
        srcAddr = FinetExpress.getScriptBase() + this.BodyInterface + "?code=" +
                HeaderID;

        //System.out.println(srcAddr);
        URL Finet;
        Finet = new URL(srcAddr);
        URLConnection FinetConnection = Finet.openConnection();
        BufferedReader DS = new BufferedReader(new InputStreamReader(
                FinetConnection.getInputStream()));

        String tempDate = DS.readLine();
        String tempTime = DS.readLine();
        String tempType = DS.readLine();
        String tempBody = "";
        String tempString;
        tempString = DS.readLine();

        while (tempString != null) {
            // delete the element ';' //////////////////////////////////
            tempString = tempString.trim();
            //System.out.println("[" + tempString + "]");
            if (tempString.length() > 2) {
                if (tempString.charAt(tempString.length() - 1) == ';') {
                    tempString = tempString.substring(0, tempString.length() - 1);
                    ////////////////////////////////////////////////////////////
                }
            }
            tempBody = tempBody + tempString + "\n";
            tempString = DS.readLine();
        }
        //tempString.replace(';',' ');
        newsSource.newsObjects[newsIndex].Body = tempBody;
    }

    public void SetServerAddress(String addr) {
        this.ServerAddress = addr;
    }
}