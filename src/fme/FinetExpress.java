//Title:
//Version:     1.0
//Copyright:   Copyright (c)
//Author:
//Company:
//Description:
package fme;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.applet.Applet;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.net.URL;

public class FinetExpress extends Applet implements Runnable {


//Constants ------------------------------------------------------------->
    private final String lbArray[][] = {{"TeleText", "\u5831\u50f9\u8868"}
                                        , {"Top 10", "\u5341\u5927\u6d3b\u8e8d\u6e2f\u80a1"}
                                        , {"News", "\u65b0\u805e"}
                                        , {"Commentary", "\u8a55\u8ad6"}
                                        , {"Stock Watch", "\u80a1\u7968\u8ffd\u8e64"}
                                        , {"Chart", "\u80a1\u7968\u5716"}
                                        , {"\u4e2d\u6587", "English"}
    };

    public static final int constEnglish = 0;
    public static final int constChinese = 1;

    public static final int teletextDisplay = 0;
    public static final int top10Display = 1;
    public static final int newsDisplay = 2;
    public static final int commentaryDisplay = 3;
    public static final int stockwatchDisplay = 4;
    public static final int chartDisplay = 5;
    public static final int brokerDisplay = 6;


//////----------------end of constants --------------------------------////

    // specify the language the user select
    public int Language = constEnglish;
    public static String passcode;

    //private static String ScriptBase;
    public static String ScriptBase;
    private static String serverAddr;
    private static String strCompany, strUserid;


// Authenicate
    public Authenicate authenicate;

    public int displayPanel = teletextDisplay;

    //
//  public FUser user;

    //CaptionBackgroundColor
    Color CaptionBackgroundColor;
    Color InfoBackgroundColor;

    //Create the Source and Engine pair for TeleText
    TeleTextSource teletextSource;
    TeleTextEngine teletextEngine;

    // create a broker screen, by kevin
    BrokerSource brokerSource;
    BrokerEngine brokerEngine;

    //Create the Source and Engine pair for News for english

    NewsSource enewsSource;
    NewsEngine enewsEngine;

    //Create the Source and Engine pair for News for chinese
    NewsSource cnewsSource;
    NewsEngine cnewsEngine;

    //Create the Source and Engine pair for Commentary for english
    CommentarySource ecommentarySource;
    CommentaryEngine ecommentaryEngine;

    //Create the Source and Engine pair for Commentary for chinese
    CommentarySource ccommentarySource;
    CommentaryEngine ccommentaryEngine;

    //Create the Source and Engine pair for Top10
    Top10Source top10Source;
    Top10Engine top10Engine;

    //Create the Source and Engine pair for StockWatch
    StockWatchSource stockwatchSource;
    StockWatchEngine stockwatchEngine;

    //Create the chartFrame
    ChartFrame chartframe;

    boolean isStandalone = false;
    Panel pnMain;
    JButton btTeleText;
    JButton btTop;
    JButton btNews;
    JButton btCommentary;
    JButton btStockWatch;
    JButton btChart;
    JButton btLanguage;

    CardLayout pnMainCardLayout;

//Here is the panel of each functions
//  LoginPanel loginPanel1;
    MultiLoginPanel multiloginpanel;
    TeleTextPanel teletextPanel1;

    NewsPanel eNewsPanel1;    //the english news panel
    NewsPanel cNewsPanel1;    //the chinese news panel

    CommentaryPanel ecommentaryPanel1; //the english commentary panel
    CommentaryPanel ccommentaryPanel1; //the chinese commentary panel

    StockWatchPanel stockWatchPanel1;
    Top10Panel top10Panel1;

    public BrokerPanel brokerPanel1;
    TitledBorder titledBorder1;// by kevin

    //Get ScriptBase
    public static String getScriptBase() {
        return ScriptBase;
    }

    public static String getCompany() {
        return strCompany;
    }

    public static String getUserid() {
        return strUserid;
    }

    //Get a parameter value
    public String getParameter(String key, String def) {
        return isStandalone ? System.getProperty(key, def) :
                (getParameter(key) != null ? getParameter(key) : def);
    }

    public double getScaleUpFactor() {
        return new Double(getParameter("SCALEUPFACTOR")).doubleValue();
    }

    public void multilogin() throws Exception {
        stopEngines();
        this.btChart.disable();
        this.btCommentary.disable();
        this.btLanguage.disable();
        this.btNews.disable();
        this.btTeleText.disable();
        this.btStockWatch.disable();
        this.btTop.disable();
        pnMainCardLayout.show(pnMain, "multiloginpanel");
    }

    public void startEngines() {
        if (this.btTeleText.isEnabled())
            this.btTeleText_actionPerformed(null);
        else if (this.btChart.isEnabled())
            this.btChart_actionPerformed(null);
        else if (this.btTop.isEnabled())
            this.btTop_actionPerformed(null);
        else if (this.btNews.isEnabled())
            this.btNews_actionPerformed(null);
        else if (this.btCommentary.isEnabled())
            this.btCommentary_actionPerformed(null);
        else if (this.btStockWatch.isEnabled())
            this.btStockWatch_actionPerformed(null);
    }

    public void stopEngines() throws Exception {
        this.teletextEngine.Disable();
        this.enewsEngine.Disable();
        this.cnewsEngine.Disable();
        this.ecommentaryEngine.Disable();
        this.ccommentaryEngine.Disable();
        this.stockwatchEngine.Disable();
        this.top10Engine.Disable();
        this.authenicate.Disable();
    }

    //Construct the applet
    public FinetExpress() {
        Language = constEnglish;
        displayPanel = teletextDisplay;
    }

    public void setBackground(Color cColor, Color bgColor) throws Exception {
        ecommentaryPanel1.setCaptionBackground(cColor);
        ccommentaryPanel1.setCaptionBackground(cColor);
        stockWatchPanel1.setCaptionBackground(cColor);
        teletextPanel1.setCaptionBackground(cColor);
        eNewsPanel1.setCaptionBackground(cColor);
        cNewsPanel1.setCaptionBackground(cColor);
        top10Panel1.setCaptionBackground(cColor);
        chartframe.setCaptionBackground(cColor);
        brokerPanel1.setCaptionBackground(cColor);

        ecommentaryPanel1.setInfoBackground(bgColor);
        ccommentaryPanel1.setInfoBackground(bgColor);
        stockWatchPanel1.setInfoBackground(bgColor);
        teletextPanel1.setInfoBackground(bgColor);
        eNewsPanel1.setInfoBackground(bgColor);
        cNewsPanel1.setInfoBackground(bgColor);
        top10Panel1.setInfoBackground(bgColor);
        chartframe.setInfoBackground(bgColor);
        brokerPanel1.setInfoBackground(bgColor);
    }

    //Initialize the applet
    public void init() {
        //set the server address to the engines
        //String serveraddr = this.getDocumentBase().getHost();
        try {
            strUserid = getParameter("USERID");
            strCompany = getParameter("COMPANY");
            ScriptBase = getParameter("SCRIPTBASE");

//      ScriptBase = getScriptBase();             // Get Script Base
            Utilities.SetFactor(getScaleUpFactor());  // Set UI Scale Up Factor

            URL urlScriptBase = new URL(ScriptBase);
            serverAddr = urlScriptBase.getHost();

            //init the panal and engine
            pnMain = new Panel();
            btTeleText = new JButton();
            btTop = new JButton();
            btNews = new JButton();
            btCommentary = new JButton();
            btStockWatch = new JButton();
            btLanguage = new JButton();
            btChart = new JButton();

            pnMainCardLayout = new CardLayout();

            //Here is the panel of each functions
            multiloginpanel = new MultiLoginPanel();
            teletextPanel1 = new TeleTextPanel();
            brokerPanel1 = new BrokerPanel();

            eNewsPanel1 = new NewsPanel();    //the english news panel
            cNewsPanel1 = new NewsPanel();    //the chinese news panel

            ecommentaryPanel1 = new CommentaryPanel(); //the english commentary panel
            ccommentaryPanel1 = new CommentaryPanel(); //the chinese commentary panel

            stockWatchPanel1 = new StockWatchPanel();
            top10Panel1 = new Top10Panel();

            this.teletextSource = new TeleTextSource();
            this.teletextEngine = new TeleTextEngine(this);
            this.teletextEngine.SetDataSouce(teletextSource);
            this.teletextEngine.SetDisplay(this.teletextPanel1);
            // this.teletextEngine.SetServerAddress(this.getDocumentBase().getHost());
            this.teletextPanel1.SetSource(teletextSource);
            this.teletextPanel1.SetEngine(teletextEngine);
            this.teletextPanel1.SetMainApplet(this);

            this.brokerSource = new BrokerSource();
            this.brokerEngine = new BrokerEngine();
            this.brokerEngine.SetDataSouce(brokerSource, teletextSource);
            this.brokerEngine.SetDisplay(this.brokerPanel1);
//      this.brokerEngine.SetServerAddress(this.getDocumentBase().getHost());
            this.brokerPanel1.SetSource(brokerSource);
            this.brokerPanel1.SetEngine(brokerEngine);
            this.brokerPanel1.SetMainApplet(this);



// init the English News engine, source and panel
            this.enewsSource = new NewsSource();
            this.enewsEngine = new NewsEngine(constEnglish);
            this.enewsEngine.SetDataSource(enewsSource);
            this.enewsEngine.SetDisplay(this.eNewsPanel1);
            this.eNewsPanel1.SetSource(enewsSource);
            this.eNewsPanel1.SetEngine(enewsEngine);
            this.eNewsPanel1.SetMainApplet(this);

// init the Chinese News engine, source and panel
            this.cnewsSource = new NewsSource();
            this.cnewsEngine = new NewsEngine(constChinese);
            this.cnewsEngine.SetDataSource(cnewsSource);
            this.cnewsEngine.SetDisplay(this.cNewsPanel1);
            this.cNewsPanel1.SetSource(cnewsSource);
            this.cNewsPanel1.SetEngine(cnewsEngine);
            this.cNewsPanel1.SetMainApplet(this);

// init the english commentary engine, source and panel
            this.ecommentarySource = new CommentarySource();
            this.ecommentaryEngine = new CommentaryEngine(constEnglish);
            this.ecommentaryEngine.SetDataSource(ecommentarySource);
            this.ecommentaryEngine.SetDisplay(ecommentaryPanel1);
            this.ecommentaryPanel1.SetSource(ecommentarySource);
            this.ecommentaryPanel1.SetEngine(ecommentaryEngine);
            this.ecommentaryPanel1.SetMainApplet(this);


// init the english commentary engine, source and panel
            this.ccommentarySource = new CommentarySource();
            this.ccommentaryEngine = new CommentaryEngine(constChinese);
            this.ccommentaryEngine.SetDataSource(ccommentarySource);
            this.ccommentaryEngine.SetDisplay(ccommentaryPanel1);
            this.ccommentaryPanel1.SetSource(ccommentarySource);
            this.ccommentaryPanel1.SetEngine(ccommentaryEngine);
            this.ccommentaryPanel1.SetMainApplet(this);

            this.top10Source = new Top10Source();
            this.top10Engine = new Top10Engine();
            this.top10Engine.SetDataSource(top10Source);
            this.top10Engine.SetDisplay(top10Panel1);
            this.top10Panel1.SetSource(top10Source);
            this.top10Panel1.SetEngine(top10Engine);
            this.top10Panel1.SetMainApplet(this);

            this.stockwatchSource = new StockWatchSource();
            this.stockwatchEngine = new StockWatchEngine(this);
            this.stockwatchEngine.SetDataSource(stockwatchSource);
            this.stockwatchEngine.SetDisplay(stockWatchPanel1);
            this.stockWatchPanel1.SetSource(stockwatchSource);
            this.stockWatchPanel1.SetEngine(stockwatchEngine);
            this.stockWatchPanel1.SetMainApplet(this);

//      this.loginPanel1.SetApplet(this);
            this.chartframe = new ChartFrame();

//      String serveraddr = "210.177.56.148";
            ChartScreen.SetServerAddress(serverAddr);

            //System.out.println(serveraddr);
            this.teletextEngine.SetServerAddress(serverAddr);
            this.enewsEngine.SetServerAddress(serverAddr);
            this.cnewsEngine.SetServerAddress(serverAddr);
            this.ecommentaryEngine.SetServerAddress(serverAddr);
            this.ccommentaryEngine.SetServerAddress(serverAddr);
            this.stockwatchEngine.SetServerAddress(serverAddr);
            this.top10Engine.SetServerAddress(serverAddr);
            this.chartframe.SetServerAddress(serverAddr);

            //load the audio clip for alert.
            stockWatchPanel1.alertClip = this.getAudioClip(this.getCodeBase(), "fme/0.au");
            //System.out.println(getDocumentBase());

            //set the user for the stockwatch....
//      FUser user = new FUser();

//      stockwatchEngine.SetUser(user);

            CaptionBackgroundColor = Utilities.ColorExtractor(this.getParameter("CAPTIONCOLOR"));
            InfoBackgroundColor = Utilities.ColorExtractor(this.getParameter("BGCOLOR"));
            this.setBackground(CaptionBackgroundColor, InfoBackgroundColor);
            try {
                jbInit();

                //init the default language
                Language = new Integer(this.getParameter("LANGUAGE")).intValue(); // getDefaultLanguage
                this.SetLanguage(Language);
                teletextPanel1.SetLanguage(Language);
                top10Panel1.SetLanguage(Language);
                stockWatchPanel1.SetLanguage(Language);
                ecommentaryPanel1.SetLanguage(constEnglish);
                ccommentaryPanel1.SetLanguage(constChinese);
                eNewsPanel1.SetLanguage(constEnglish);
                cNewsPanel1.SetLanguage(constChinese);
                brokerPanel1.SetLanguage(Language);
                chartframe.SetLanguage(Language);
                chartframe.chartScreen1.SetLanguage(Language);

                //set the UI layout...
                int scheme = new Integer(this.getParameter("SCHEME")).intValue(); // getDefaultLanguage

                btTeleText.setEnabled((scheme & 1) == 1);
                btChart.setEnabled((scheme & 2) == 2);
                btTop.setEnabled((scheme & 4) == 4);
                btNews.setEnabled((scheme & 8) == 8);
                btCommentary.setEnabled((scheme & 16) == 16);
                btStockWatch.setEnabled((scheme & 32) == 32);
                btLanguage.setEnabled(true);
            } catch (NumberFormatException ne) {
            } catch (Exception e) {
                e.printStackTrace();
                this.showStatus("jbinit(): " + e.getMessage());
            }
            // Authenicate Services
            authenicate = new Authenicate(this);
            authenicate.start();
//      startEngines();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    //Component initialization, add all the panel to the main applet
    private void jbInit() throws Exception {

        titledBorder1 = new TitledBorder("");
        this.setLayout(null);
        pnMain.setBackground(SystemColor.control);
        pnMain.setBounds(new Rectangle(1, 24, 499, 280));
        pnMain.setLayout(pnMainCardLayout);
        btTeleText.setBackground(SystemColor.control);
        btTeleText.setBounds(new Rectangle(1, 1, 68, 23));
        btTeleText.setLabel("TeleText");
        btTeleText.setFont(new java.awt.Font("Dialog", 1, 12));
        btTeleText.addActionListener(new java.awt.event.ActionListener() {

            public void actionPerformed(ActionEvent e) {
                btTeleText_actionPerformed(e);
            }
        });


        btTeleText.addActionListener(new java.awt.event.ActionListener() {

            public void actionPerformed(ActionEvent e) {
                btTeleText_actionPerformed(e);
            }
        });
        btTop.setBackground(SystemColor.control);
        btTop.setFont(new java.awt.Font("Dialog", 0, 12));
        btTop.setBounds(new Rectangle(127, 1, 80, 23));
        btTop.setLabel("Top10");
        btTop.addActionListener(new java.awt.event.ActionListener() {

            public void actionPerformed(ActionEvent e) {
                btTop_actionPerformed(e);
            }
        });
        btNews.setBackground(SystemColor.control);
        btNews.setBounds(new Rectangle(207, 1, 67, 23));
        btNews.setFont(new java.awt.Font("Dialog", 0, 12));
        btNews.setLabel("News");
        btNews.addActionListener(new java.awt.event.ActionListener() {

            public void actionPerformed(ActionEvent e) {
                btNews_actionPerformed(e);
            }
        });
        btCommentary.setBackground(SystemColor.control);
        btCommentary.setFont(new java.awt.Font("Dialog", 0, 12));

        btCommentary.setDebugGraphicsOptions(0);
        btCommentary.setBorderPainted(true);
        btCommentary.setBounds(new Rectangle(274, 1, 80, 23));
        btCommentary.setLabel("Commentary");
        btCommentary.addActionListener(new java.awt.event.ActionListener() {

            public void actionPerformed(ActionEvent e) {
                btCommentary_actionPerformed(e);
            }
        });
        btStockWatch.setBackground(SystemColor.control);
        btStockWatch.setFont(new java.awt.Font("Dialog", 0, 12));
        btStockWatch.setBounds(new Rectangle(354, 1, 75, 23));
        btStockWatch.setLabel("Stock Watch");
        btStockWatch.addActionListener(new java.awt.event.ActionListener() {

            public void actionPerformed(ActionEvent e) {
                btStockWatch_actionPerformed(e);
            }
        });
        btLanguage.setBackground(SystemColor.control);
        btLanguage.setFont(new java.awt.Font("Dialog", 0, 12));
        btLanguage.setBounds(new Rectangle(429, 1, 71, 23));
        btLanguage.setLabel("Chinese");
        btLanguage.addActionListener(new java.awt.event.ActionListener() {

            public void actionPerformed(ActionEvent e) {
                btLanguage_actionPerformed(e);
            }
        });
//    loginPanel1.setFont(new java.awt.Font("Dialog", 1, 12));
        this.setBackground(SystemColor.control);
        btChart.setBackground(SystemColor.control);
        btChart.setFont(new java.awt.Font("Dialog", 0, 12));
        btChart.setBounds(new Rectangle(69, 1, 58, 23));
        btChart.setLabel("Chart");
        btChart.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btChart_actionPerformed(e);
            }
        });
        this.add(btTeleText, null);
// add main blank panel which contain all the panel as cards
        this.add(pnMain, null);
        //pnMain.add(loginPanel1, "loginPanel");
        pnMain.add(teletextPanel1, "teleTextPanel");
        pnMain.add(brokerPanel1, "brokerPanel");

        pnMain.add(eNewsPanel1, "eNewsPanel");        //the English news panel
        pnMain.add(cNewsPanel1, "cNewsPanel");        //the Chinese news panel

        pnMain.add(ecommentaryPanel1, "ecommentaryPanel"); //the english commentary panel
        pnMain.add(ccommentaryPanel1, "ccommentaryPanel"); //the chinese commentary panel


        pnMain.add(stockWatchPanel1, "stockWatchPanel");
        pnMain.add(top10Panel1, "top10Panel");
        pnMain.add(chartframe, "chart");

        pnMain.add(multiloginpanel, "multiloginpanel");

// add button.
        this.add(btLanguage, null);
        this.add(btStockWatch, null);
        this.add(btCommentary, null);
        this.add(btNews, null);
        this.add(btTop, null);
        this.add(btChart, null);

    }

    //Start the applet
    public void start() {
        //MyThread = new Thread(this,"HELLO");
        //MyThread.start();
        try {
            this.startEngines();
            teletextEngine.chgStock(this.getParameter("DEFAULTCODE"));
        } catch (Exception ex) {
            showStatus("FinetExpress::start" + ex.getMessage());
            ex.printStackTrace();
        }

    }

    //Stop the applet
    public void stop() {
        try {
            this.stopEngines();
        } catch (Exception ex) {
            showStatus("FinetExpress::stop" + ex.getMessage());
        }
    }

    //Destroy the applet
    public void destroy() {
        try {
            stopEngines();
        } catch (Exception ex) {
            showStatus("FinetExpress::destroy" + ex.getMessage());
        }
    }

    //Get Applet information
    public String getAppletInfo() {
        return "Applet Information";
    }

    //Get parameter info
    public String[][] getParameterInfo() {
        return null;
    }

    private void htButton(JButton bt) {
        this.btChart.setFont(new java.awt.Font("Dialog", 0, 12));
        this.btTop.setFont(new java.awt.Font("Dialog", 0, 12));
        this.btCommentary.setFont(new java.awt.Font("Dialog", 0, 12));
        this.btNews.setFont(new java.awt.Font("Dialog", 0, 12));
        this.btStockWatch.setFont(new java.awt.Font("Dialog", 0, 12));
        this.btTeleText.setFont(new java.awt.Font("Dialog", 0, 12));

        bt.setFont(new java.awt.Font("Dialog", 1, 12));
    }

    public void btTeleText_actionPerformed(ActionEvent e) {
        htButton(btTeleText);
        try {
            pnMainCardLayout.show(pnMain, "teleTextPanel");

            displayPanel = this.teletextDisplay;

            // First Panel
            // initialization
            teletextEngine.Enable();
            enewsEngine.Disable();
            cnewsEngine.Disable();
            ecommentaryEngine.Disable();
            ccommentaryEngine.Disable();
            top10Engine.Disable();
            stockWatchPanel1.Disable();
        } catch (Exception ex) {
            showStatus("FinetExpress::btTeleText_actionPerformed " + ex.getMessage());
        }
    }

    void btTop_actionPerformed(ActionEvent e) {
        htButton(btTop);
        try {
            pnMainCardLayout.show(pnMain, "top10Panel");

            displayPanel = this.top10Display;

            teletextEngine.Disable();
            enewsEngine.Disable();
            cnewsEngine.Disable();
            ecommentaryEngine.Disable();
            top10Engine.Enable();
            stockWatchPanel1.Disable();
        } catch (Exception ex) {
            showStatus("FinetExpress::btTop_actionPerformed " + ex.getMessage());
        }

    }

    void btNews_actionPerformed(ActionEvent e) {
        htButton(btNews);
        try {
            //Display the news panel and enable it.
            if (Language == constEnglish) {
                pnMainCardLayout.show(pnMain, "eNewsPanel");
                enewsEngine.Enable();
                cnewsEngine.Disable();
            } else if (Language == constChinese) {
                pnMainCardLayout.show(pnMain, "cNewsPanel");
                cnewsEngine.Enable();
                enewsEngine.Disable();
            }

            displayPanel = this.newsDisplay;
            teletextEngine.Disable();
            ecommentaryEngine.Disable();
            ccommentaryEngine.Disable();
            top10Engine.Disable();
            stockWatchPanel1.Disable();
        } catch (Exception ex) {
            showStatus("FinetExpress::btNews_actionPerformed " + ex.getMessage());
        }
    }

    void btCommentary_actionPerformed(ActionEvent e) {
        htButton(btCommentary);
        try {
            //Display the commentary panel and enable it.
            if (Language == constEnglish) {
                pnMainCardLayout.show(pnMain, "ecommentaryPanel");
                ecommentaryEngine.Enable();
                ccommentaryEngine.Disable();
            } else if (Language == constChinese) {
                pnMainCardLayout.show(pnMain, "ccommentaryPanel");
                ecommentaryEngine.Disable();
                ccommentaryEngine.Enable();
            }

            displayPanel = this.commentaryDisplay;

            teletextEngine.Disable();
            enewsEngine.Disable();
            cnewsEngine.Disable();
            top10Engine.Disable();
            stockWatchPanel1.Disable();
        } catch (Exception ex) {
            showStatus("FinetExpress::btCommentary_actionPerformed " + ex.getMessage());
        }
    }

    //Click the chart button on the applet.
    void btChart_actionPerformed(ActionEvent e) {
        htButton(btChart);
        try {
            pnMainCardLayout.show(pnMain, "chart");
            chartframe.lbCode.setText(teletextSource.Code + "  " + teletextSource.Name);
            chartframe.chartScreen1.updateCode(teletextSource.Code);

            teletextEngine.Disable();
            enewsEngine.Disable();
            cnewsEngine.Disable();
            ecommentaryEngine.Disable();
            ccommentaryEngine.Disable();
            top10Engine.Disable();
            stockWatchPanel1.Disable();
        } catch (Exception ex) {
            showStatus("FinetExpress::btChart_actionPerformed " + ex.getMessage());
        }
    }

    void btStockWatch_actionPerformed(ActionEvent e) {
        htButton(btStockWatch);
        try {
            pnMainCardLayout.show(pnMain, "stockWatchPanel");
            displayPanel = this.stockwatchDisplay;
            stockwatchEngine.Enable();
            teletextEngine.Disable();
            enewsEngine.Disable();
            cnewsEngine.Disable();
            ecommentaryEngine.Disable();
            ccommentaryEngine.Disable();
            top10Engine.Disable();
            stockWatchPanel1.Enable();
        } catch (Exception ex) {
            showStatus("FinetExpress::btStockWatch_actionPerformed " + ex.getMessage());
        }

    }

    void btLanguage_actionPerformed(ActionEvent e) {
        try {

            if (Language == constEnglish) {
                Language = constChinese;
                this.btLanguage.setLabel("English");
                if (displayPanel == this.newsDisplay) {
                    pnMainCardLayout.show(pnMain, "cNewsPanel");
                    this.cnewsEngine.Enable();
                    this.enewsEngine.Disable();
                } else if (displayPanel == this.commentaryDisplay) {
                    pnMainCardLayout.show(pnMain, "ccommentaryPanel");
                    this.ccommentaryEngine.Enable();
                    this.ecommentaryEngine.Disable();
                }
            } else if (Language == constChinese) {
                Language = constEnglish;
                this.btLanguage.setLabel("Chinese");
                if (displayPanel == this.newsDisplay) {
                    pnMainCardLayout.show(pnMain, "eNewsPanel");
                    this.cnewsEngine.Disable();
                    this.enewsEngine.Enable();
                } else if (displayPanel == this.commentaryDisplay) {
                    pnMainCardLayout.show(pnMain, "ecommentaryPanel");
                    this.ccommentaryEngine.Disable();
                    this.ecommentaryEngine.Enable();
                }
            }
            teletextPanel1.SetLanguage(Language);
            brokerPanel1.SetLanguage(Language);
            top10Panel1.SetLanguage(Language);
            stockWatchPanel1.SetLanguage(Language);
            chartframe.SetLanguage(Language);
            this.SetLanguage(Language);
            chartframe.chartScreen1.SetLanguage(Language);
        } catch (Exception ex) {
            showStatus("FinetExpress::btLanguage_actionPerformed " + ex.getMessage());
            System.out.println(ex.getMessage());
        }
    }

    public void run() {
        System.out.println("Thread Runned");
    }

    public void showBroker(String ccode) {
        try {
            pnMainCardLayout.show(pnMain, "brokerPanel");
            displayPanel = this.brokerDisplay;
            if (ccode != null)
                brokerEngine.viewBroker(ccode);
//      brokerEngine.Enable();
            teletextEngine.Disable();
            enewsEngine.Disable();
            cnewsEngine.Disable();
            ecommentaryEngine.Disable();
            ccommentaryEngine.Disable();
            top10Engine.Disable();
        } catch (Exception ex) {
            showStatus("FinetExpress::showBroker " + ex.getMessage());
        }

    }

    public void SetLanguage(int tlanguage) {
        try {
            Language = tlanguage;
            if (Language == FinetExpress.constEnglish) {
                this.btTeleText.setLabel(lbArray[0][0]);
                this.btTop.setLabel(lbArray[1][0]);
                this.btNews.setLabel(lbArray[2][0]);
                this.btCommentary.setLabel(lbArray[3][0]);
                this.btStockWatch.setLabel(lbArray[4][0]);
                this.btChart.setLabel(lbArray[5][0]);
                this.btLanguage.setLabel(lbArray[6][0]);
            } else if (Language == FinetExpress.constChinese) {
                this.btTeleText.setLabel(lbArray[0][1]);
                this.btTop.setLabel(lbArray[1][1]);
                this.btNews.setLabel(lbArray[2][1]);
                this.btCommentary.setLabel(lbArray[3][1]);
                this.btStockWatch.setLabel(lbArray[4][1]);
                this.btChart.setLabel(lbArray[5][1]);
                this.btLanguage.setLabel(lbArray[6][1]);
            }
        } catch (Exception ex) {
            showStatus("FinetExpress::SetLanguage " + ex.getMessage());
        }
    }
}
