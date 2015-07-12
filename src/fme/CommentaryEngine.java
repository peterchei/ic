/**
 * Title:        The class of commentary Engine,
 * Description:  This class is used to retrieve the data from URL for commentary and provide interface to access commentary news
 * Copyright:    Copyright (c) <p>
 * Company:      <p>
 * @author:
 * @version 1.0
 */
package fme;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class CommentaryEngine
        extends Thread {
    //  The Interfaces for chinese;
    private final String CBodyInterface = "mktcomm/story.php"; // retrieve the body
    private final String CHeadersInterface = "mktcomm/latest.php"; // get the lastest headers
    private final String CHeadlinesInterface = "mktcomm/headline.php"; // get headers given the range
    private final String CNewCommentaryInterface = "mktcomm/nof.php"; // get the newest header ID.

    //  The Interfaces for English;
    private final String EBodyInterface = "mktcomm/story_e.php"; // retrieve the body
    private final String EHeadersInterface = "mktcomm/latest_e.php"; // get the lastest headers
    private final String EHeadlinesInterface = "mktcomm/headline_e.php"; // get headers given the range
    private final String ENewCommentaryInterface = "mktcomm/nof_e.php"; // get the newest header ID.

    //  The active cgi interfaces......./////////////////
    private String BodyInterface;
    private String HeadersInterface;
    private String HeadlinesInterface;
    private String NewCommentaryInterface;

//THE server address.
    private String ServerAddress;

//the EngineState
    private final int uninited = 0;
    private final int inited = 1;
    private final int started = 2;
    private final int stopped = 3;
    private final int ended = 4;

    private CommentarySource newsSource = null;
    private CommentaryPanel ui = null;
    private int EngineState = uninited;

    public void SetDataSource(CommentarySource nS) {
        newsSource = nS;
    }

    public void SetDisplay(CommentaryPanel np) {
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

    public CommentaryEngine(int language) {
        if (language == FinetExpress.constEnglish) {
            BodyInterface = EBodyInterface;
            HeadersInterface = EHeadersInterface;
            HeadlinesInterface = EHeadlinesInterface;
            NewCommentaryInterface = ENewCommentaryInterface;
        } else if (language == FinetExpress.constChinese) {
            BodyInterface = CBodyInterface;
            HeadersInterface = CHeadersInterface;
            HeadlinesInterface = CHeadlinesInterface;
            NewCommentaryInterface = CNewCommentaryInterface;
        }
    }

    public void run() {
        boolean ready = false;
        while (!ready) {
            try {
                ui.Report("CommentaryEngine Loading..........");
                GetHeaders(10);
                ui.Report("");
                ui.UpdateInformation();
                ui.Report("CommentaryEngine Initialization Done.");
                ready = true;
            } catch (Exception ex) {
//         ui.Report("CommentaryEngine::Run Initialization" + ex.getMessage());
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
                sleep(60000);
                UpdateNews();
                synchronized (this) {
                    if (EngineState == stopped) {
                        wait();
                    }
                }
            } catch (InterruptedException ie) {
            } catch (Exception ex) {
//           ui.Report("CommentaryEngine::Run " + ex.getMessage());
                ex.printStackTrace();
            }
        }
    }

    private void GetHeaders(int NumberOfHeaders) throws Exception {
        try {
            //System.out.println("GetHeader!");
            String srcAddr = new String();
            srcAddr = FinetExpress.getScriptBase() + HeadersInterface + "?number=" +
                    NumberOfHeaders;

            System.out.println(srcAddr);
            URL Finet;
            Finet = new URL(srcAddr);
            URLConnection FinetConnection = Finet.openConnection();
            BufferedReader DS = new BufferedReader(new InputStreamReader(
                    FinetConnection.getInputStream()));
            newsSource.NumberOfHeaders = 0;
            for (int i = NumberOfHeaders; i > 0; i--) {

                int keyIndex = 0;
                String tempString;

                tempString = DS.readLine();
                keyIndex = tempString.indexOf(";");
                if (keyIndex == -1) {
                    keyIndex = tempString.length();
                }
                newsSource.newsObjects[i - 1].Date = tempString.substring(0, keyIndex);

                tempString = DS.readLine();
                keyIndex = tempString.indexOf(";");
                if (keyIndex == -1) {
                    keyIndex = tempString.length();
                }
                newsSource.newsObjects[i - 1].Time = tempString.substring(0, keyIndex);

                tempString = DS.readLine();
                keyIndex = tempString.indexOf(";");
                if (keyIndex == -1) {
                    keyIndex = tempString.length();
                }
                newsSource.newsObjects[i - 1].ID = tempString.substring(0, keyIndex);

                tempString = DS.readLine();
                keyIndex = tempString.indexOf(";");
                if (keyIndex == -1) {
                    keyIndex = tempString.length();
                }
                newsSource.newsObjects[i - 1].Type = tempString.substring(0, keyIndex);

                tempString = DS.readLine();
                keyIndex = tempString.indexOf(";");
                if (keyIndex == -1) {
                    keyIndex = tempString.length();
                }
                newsSource.newsObjects[i - 1].Header = tempString.substring(0, keyIndex);
                newsSource.NumberOfHeaders++;
                DS.readLine();
                //System.out.println(newsSource.newsObjects[i-1].Date);
                //System.out.println(newsSource.newsObjects[i-1].Time);
                //System.out.println(newsSource.newsObjects[i-1].ID);
                //System.out.println(newsSource.newsObjects[i-1].Type);
                //System.out.println(newsSource.newsObjects[i-1].Header);
            } // end for
        } catch (StringIndexOutOfBoundsException se) {
            System.out.println("StringIndexOutOfBoundsException");
        }
    }

    private void UpdateNews() throws Exception {

        String lastID, oldlastID;
        if (newsSource.NumberOfHeaders > 0) {
            oldlastID = newsSource.newsObjects[newsSource.NumberOfHeaders - 1].ID;
        } else {
            oldlastID = "-1";
        }
        //System.out.println(oldlastID);

        //System.out.println("CheckUpdate");
        String srcAddr = new String();
        srcAddr = FinetExpress.getScriptBase() + this.NewCommentaryInterface;
        ////System.out.println(srcAddr);

        URL Finet;
        Finet = new URL(srcAddr);

        URLConnection FinetConnection = Finet.openConnection();
        BufferedReader DS = new BufferedReader(new InputStreamReader(
                FinetConnection.getInputStream()));
        lastID = DS.readLine();

        DS.close();

        if (!lastID.equalsIgnoreCase(oldlastID)) {
            System.out.println("Need to get more news " + "[" + lastID + "] [" +
                    oldlastID + "]");
            GetMoreHeaders(lastID);
        } else {
            System.out.println("The news is up-to-date " + "[" + lastID + "] [" +
                    oldlastID + "]");
        }
    }

    private void GetMoreHeaders(String NewHeaderID) throws Exception {
        //System.out.println("GetHeaders " + NewHeaderID);
        String srcAddr = new String();
        srcAddr = FinetExpress.getScriptBase() + this.HeadlinesInterface + "?code=" +
                NewHeaderID;
        URL Finet;
        Finet = new URL(srcAddr);
        URLConnection FinetConnection = Finet.openConnection();
        BufferedReader DS = new BufferedReader(new InputStreamReader(
                FinetConnection.getInputStream()));

//Receive the data
        String tempDate, tempTime, tempType, tempHeader;
        int tempID;
//      DS.readLine();
        tempDate = DS.readLine();
        System.out.println("date:" + tempDate);
        tempTime = DS.readLine();
        System.out.println("date:" + tempTime);
        tempType = DS.readLine();
        System.out.println("date:" + tempType);
        tempHeader = DS.readLine();
        System.out.println("date:" + tempHeader);

        //add the Header to the source.
        newsSource.newsObjects[newsSource.NumberOfHeaders].Date = tempDate.
                substring(0, tempDate.length() - 1);
        newsSource.newsObjects[newsSource.NumberOfHeaders].Time = tempTime.
                substring(0, tempTime.length() - 1);
        newsSource.newsObjects[newsSource.NumberOfHeaders].ID = NewHeaderID;
        newsSource.newsObjects[newsSource.NumberOfHeaders].Type = tempType.
                substring(0, tempTime.length() - 1);
        newsSource.newsObjects[newsSource.NumberOfHeaders].Header = tempHeader.
                substring(0, tempHeader.length() - 1);
        newsSource.NumberOfHeaders++;
        ui.UpdateInformation();
    }

    public void GetBody(String HeaderID, int newsIndex) throws Exception {
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

        DS.readLine();
        String tempDate = DS.readLine();
        String tempTime = DS.readLine();
        String tempType = DS.readLine();

        String tempBody = "";
        String tempString;
        tempString = DS.readLine();
        while (tempString != null) {
            // delete the element ';' //////////////////////////////////
            if (tempString.length() > 2) {
                if (tempString.charAt(tempString.length() - 1) == ';') {
                    tempString = tempString.substring(0, tempString.length() - 1);
                    ////////////////////////////////////////////////////////////

                    //System.out.println(tempString.substring(1,4));
                }
            }
            if (tempString.length() > 5 && tempString.substring(1, 5).equals("FACE")) {
                tempBody = tempBody + " " + tempString.substring(12);
                //System.out.println("find["+tempString+"]");
            } else {
                if (tempString.indexOf("New Page") > 0) {
                    //the line is "New Page Line" , we can cancel it.
                } else if (tempString.length() > 1) {
                    tempBody = tempBody + tempString + "\n";
                }
            }
            tempString = DS.readLine();
        }
        newsSource.newsObjects[newsIndex].Body = tempBody;
    }

    public void SetServerAddress(String addr) {
        this.ServerAddress = addr;
    }
}