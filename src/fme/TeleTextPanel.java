/**
 * Title:        <p>
 * Description:  <p>
 * Copyright:    Copyright (c) <p>
 * Company:      <p>
 * @author
 * @version 1.0
 */
package fme;

import java.awt.*;
import java.awt.event.*;
import java.util.Vector;

public class TeleTextPanel extends Panel implements KeyListener {

    private final String lbArray[][] = {{"Nominal", "\u6700\u65b0\u50f9"}
                                        , {"High", "\u6700\u9ad8"}
                                        , {"Low", "\u6700\u4f4e"}
                                        , {"Prev-Close", "\u4e0a\u65e5\u6536\u5e02"}
                                        , {"Avg Price", "\u5e73\u5747\u50f9"}
                                        , {"Volume", "\u6210\u4ea4\u91cf"}
                                        , {"Turnover", "\u6210\u4ea4\u984d"}
                                        , {"Spread", "\u5dee\u50f9"}
                                        , {"Lot Size", "\u8cb7\u8ce3\u55ae\u4f4d"}
                                        , {"Chg", "\u5347\u8dcc"}
                                        , {"%Chg", "\u5347\u8dcc\u6bd4\u7387"}
                                        , {"PE", "\u5e02\u76c8\u7387"}
                                        , {"HSI", "\u6046\u6307"}
                                        , {"YIELD", "\u9031\u606f\u7387"}
                                        , {"HSIF", "\u671f\u6307"}
                                        , {"Buy", "\u8cb7\u5165"}
                                        , {"Sell", "\u8ce3\u51fa"}
                                        , {"Buy Brokers", "\u8cb7\u5165\u7d93\u7d00\u7de8\u865f"}
                                        , {"Sell Brokers", "\u8ce3\u51fa\u7d93\u7d00\u7de8\u865f"}
                                        , {"Go", "\u8f38\u5165"}
                                        , {"Chart", "\u5716\u8868"}
                                        , {"Stock ID", "\u80a1\u7968\u865f\u78bc"}
                                        , {"Last Updated", "\u6700\u5f8c\u66f4\u65b0"}
                                        , {"Log", "\u7d00\u9304"}
                                        , {"Broker", "\u7d93\u7d00"}
    };

    int language;

    FinetExpress finetExpress = null;
    TeleTextSource teletextSource = null;
    TeleTextEngine teletextEngine = null;
    boolean isStandalone = false;
    FTable buyBrokersTable = new FTable(4, 10, 28, 12, "Dialog", 0, 11);
    FTable sellBrokersTable = new FTable(4, 10, 28, 12, "Dialog", 0, 11);
    FTable tradeTable = new FTable(3, 4, new int[]{38, 39, 46}, 18, "Dialog", 0, 12);
    FTable buySTable = new FTable(1, 5, 60, 18, "Dialog", 0, 11);
    FTable sellSTable = new FTable(1, 5, 60, 18, "Dialog", 0, 11);

    TextField tfCompany = new TextField();
    Label lbstBBrokers = new Label();
    Label lbstSBrokers = new Label();
    Panel panel1 = new Panel();
    Panel panel3 = new Panel();
    Panel panel4 = new Panel();
    Label lbask = new Label();
    Label lbbid = new Label();
    Label lbstBuy = new Label();
    Label lbstSell = new Label();
    Label lbstPE = new Label();
    Label lbstYIELD = new Label();
    Label lbstHSI = new Label();
    Label lbstHSIF = new Label();
    Label lbstStockID = new Label();
    TextField tfStock = new TextField();
    Button btGo = new Button();
    Label lbstNominal = new Label();
    Label lbstHigh = new Label();
    Label lbstPrevClose = new Label();
    Label lbstLow = new Label();
    Label lbstTurnover = new Label();
    Label lbstSpread = new Label();
    Label lbstVolume = new Label();
    Label lbstAvgPrice = new Label();
    Label lbstLotSize = new Label();
    Label lbstChg = new Label();
    Label lbstPChg = new Label();
    Label lbPCChg = new Label();
    Label lbChg = new Label();
    Label lbLotSize = new Label();
    Label lbAvgPrice = new Label();
    Label lbVolume = new Label();
    Label lbSpread = new Label();
    Label lbTurnover = new Label();
    Label lbLow = new Label();
    Label lbPrevClose = new Label();
    Label lbHigh = new Label();
    Label lbNominal = new Label();

    Label lbPE = new Label();
    Label lbYIELD = new Label();
    Label lbCode = new Label();
    Label lbName = new Label();
    public Label lbdatetime = new Label();
    Label lbCName = new Label();
    Label lbHSI = new Label();
    Label lbHSIF = new Label();
    Label lbUpdate = new Label();
    Button btLog = new Button();
    Button btBroker = new Button();
    public List lstLog = new List();
    public TextArea textArea1 = new TextArea(" ", 50, 82, TextArea.SCROLLBARS_VERTICAL_ONLY);

    public TeleTextPanel() {
        try {
            jbInit();
            // init the Tables Color.
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 4; j++) {
                    tradeTable.lbItem[i][j].orgFgColor = Color.black;
                    tradeTable.lbItem[i][j].spFgColor = Color.black;
                    tradeTable.lbItem[i][j].orgBgColor = Color.white;
                    tradeTable.lbItem[i][j].spBgColor = Color.yellow;
                    //tradeTable.lbItem[i][j].orgBgColor = Color.yellow;
                }

            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void SetMainApplet(FinetExpress fe) {
        this.finetExpress = fe;
    }

    public void SetEngine(TeleTextEngine Te) {
        teletextEngine = Te;
    }

    // Set the DataSource
    public void SetSource(TeleTextSource ptt) {
        teletextSource = ptt;
    }

    public void setCaptionForeground(Color c) {
        panel1.setForeground(c);
        lbName.setForeground(c);
        lbCode.setForeground(c);
        lbdatetime.setForeground(c);
        lbUpdate.setForeground(c);
        lbCName.setForeground(c);
        lbstStockID.setForeground(c);
    }

    public void setCaptionBackground(Color c) {

        panel1.setBackground(c);
        lbName.setBackground(c);
        lbCode.setBackground(c);
        lbdatetime.setBackground(c);
        lbUpdate.setBackground(c);
        lbCName.setBackground(c);
        lbstStockID.setBackground(c);
    }

    public void setInfoBackground(Color c) {
        lbstHigh.setBackground(c);
        lbstPrevClose.setBackground(c);
        lbstLow.setBackground(c);
        lbstTurnover.setBackground(c);
        lbstSpread.setBackground(c);
        lbstVolume.setBackground(c);
        lbstAvgPrice.setBackground(c);
        lbstLotSize.setBackground(c);
        lbstChg.setBackground(c);
        lbstPChg.setBackground(c);
        lbPCChg.setBackground(c);
        lbChg.setBackground(c);
        lbLotSize.setBackground(c);
        lbAvgPrice.setBackground(c);
        lbVolume.setBackground(c);
        lbSpread.setBackground(c);
        lbTurnover.setBackground(c);
        lbLow.setBackground(c);
        lbPrevClose.setBackground(c);
        lbHigh.setBackground(c);
        this.setBackground(c);
    }

// this function reports some error message in the stats bar.
    public void Report(String sstring) {
        finetExpress.showStatus(sstring);
    }
// this function is used to report Server, state = 0 (RUNING) state = 1 (down a moment)
    public void ReportServerState(int sstate) {
        // server OK
        if (sstate == 0) {
            finetExpress.showStatus("Running..........");
        }
        // server Down
        else if (sstate == 1) {
            finetExpress.showStatus("Server busy and may be down a moment");
        }
    }

// this function will be called when the data need update........
    public void UpdateInformation() throws Exception {
        if (teletextSource == null) {
            //System.out.println("No TeletextSource defined");
            return;
        }

        lbCode.setText(teletextSource.Code);
        lbName.setText(teletextSource.Name);
        lbCName.setText(teletextSource.CName);
        lbNominal.setText(teletextSource.Nominal);
        lbHigh.setText(teletextSource.High);
        lbLow.setText(teletextSource.Low);
        lbPrevClose.setText(teletextSource.PrevClose);
        lbAvgPrice.setText(FormatData.FormatString2(teletextSource.AvgPrice, 3));
        //System.out.println(teletextSource.AvgPrice);
        lbVolume.setText(FormatData.FormatString2(teletextSource.Volume, 1));
        lbTurnover.setText(FormatData.FormatStringI(teletextSource.Turnover, 8));
        lbSpread.setText(teletextSource.LowerSpread + "/" + teletextSource.UpperSpread);
        lbLotSize.setText(teletextSource.LotSize);
        //teletextSource.Chg
        lbChg.setText(teletextSource.Chg);
        lbPCChg.setText(teletextSource.PecentChg + "%");

        //the Top right Data
        lbPE.setText(teletextSource.PE);
        lbYIELD.setText(teletextSource.Yield);

        lbHSI.setText(teletextSource.HSI);
        lbHSIF.setText(teletextSource.HSIF);

        //
        //lbYield.="XXX";
        //HSI="XXX";
        //HSIF="XXX";
        //

        //Main Sell and buy
        lbask.setText(teletextSource.currask);
        lbbid.setText(teletextSource.currbid);

        // Sell Summary
        for (int i = 0; i < 5; i++) {
            sellSTable.lbItem[0][i].setText(FormatData.FormatString2(teletextSource.iask[i], 1) + "(" + FormatData.GetInteger(teletextSource.nask[i]) + ")");
        }

        // Buy Summary
        for (int i = 0; i < 5; i++) {
            buySTable.lbItem[0][i].setText(FormatData.FormatString2(teletextSource.ibid[i], 1) + "(" + FormatData.GetInteger(teletextSource.nbid[i]) + ")");
        }

        // trade activity summary
        for (int i = 0; i < 4; i++) {
            tradeTable.lbItem[0][i].setText(teletextSource.tact[i]);
            tradeTable.lbItem[1][i].setText(FormatData.FormatString2(teletextSource.nact[i], 1));
            tradeTable.lbItem[2][i].setText(teletextSource.act[i]);
        }
        lbdatetime.setText(teletextSource.date + " " + teletextSource.time);

        UpdateBrokersInfo();
    }


    public void UpdateBrokersInfo() throws Exception {

        Vector oldbuyBrokers = new Vector();
        Vector oldsellBrokers = new Vector();

        //store the old brokers Table to the Vector collection
        for (int i = 0; i < 4; i++)
            for (int j = 0; j < 10; j++) {
                if (buyBrokersTable.lbItem[i][j].getText().indexOf("-") == -1) oldbuyBrokers.addElement(buyBrokersTable.lbItem[i][j].getText());
                if (sellBrokersTable.lbItem[i][j].getText().indexOf("-") == -1) oldsellBrokers.addElement(this.sellBrokersTable.lbItem[i][j].getText());
            }


        for (int i = 0; i < 4; i++)  // 4 columns
        {
            for (int j = 0; j < 10; j++) // 10 rows
            {
                // Update the BuyBroker table
                if (teletextSource.BuyBroker[i][j].indexOf("-") >= 0) {
                    //When it is '-xs' item.
                    buyBrokersTable.lbItem[i][j].setText(teletextSource.BuyBroker[i][j], Color.red, Color.black);
                } else {
                    if (oldbuyBrokers.indexOf(teletextSource.BuyBroker[i][j]) >= 0) {
                        //if the brokers already in the queue
                        buyBrokersTable.lbItem[i][j].setText(teletextSource.BuyBroker[i][j]);
                    } else {
                        //if it is a new broker
                        buyBrokersTable.lbItem[i][j].setText(teletextSource.BuyBroker[i][j], Color.yellow, Color.black);
                    }
                }  //end updating the BuyBroker table

                // Update the Sellbroker table
                if (teletextSource.SellBroker[i][j].indexOf("-") >= 0) {
                    String temp = "+" + teletextSource.SellBroker[i][j].substring(1);
                    sellBrokersTable.lbItem[i][j].setText(temp, Color.red, Color.black);
                } else {
                    if (oldsellBrokers.indexOf(teletextSource.SellBroker[i][j]) >= 0) {
                        sellBrokersTable.lbItem[i][j].setText(teletextSource.SellBroker[i][j]);
                    } else {
                        sellBrokersTable.lbItem[i][j].setText(teletextSource.SellBroker[i][j], Color.yellow, Color.black);
                    }
                }// end updating the sellbroker table
            } // end for loop of 10 rows
        } // end for loop of 4 columns
    } // end method UpdateBrokerInfo()


    private void jbInit() throws Exception {
        this.setLayout(null);
        this.setBounds(new Rectangle(700, 500));
        buyBrokersTable.setBounds(new Rectangle(260, 125, 117, 133));
        sellBrokersTable.setBounds(new Rectangle(378, 125, 117, 133));
        buyBrokersTable.setBorderColor(Color.white, Color.black);
        buyBrokersTable.setFontColor(Color.blue, Color.black);
        sellBrokersTable.setBorderColor(Color.white, Color.black);
        sellBrokersTable.setFontColor(Color.red, Color.black);
        tfCompany.setBackground(Color.white);
        tfCompany.setBounds(new Rectangle(259, 258, 236, 17));
        tfCompany.setFont(new java.awt.Font("Dialog", 0, 12));
        tfCompany.setEditable(false);
        lbstBBrokers.setBackground(Color.blue);
        lbstBBrokers.setBounds(new Rectangle(260, 98, 117, 25));
        lbstBBrokers.setFont(new java.awt.Font("Serif", 1, 16));
        lbstBBrokers.setForeground(Color.white);
        lbstBBrokers.setAlignment(1);
        lbstBBrokers.setText("Buy Brokers");
        lbstSBrokers.setBackground(Color.red);
        lbstSBrokers.setBounds(new Rectangle(378, 98, 117, 25));
        lbstSBrokers.setFont(new java.awt.Font("Serif", 1, 16));
        lbstSBrokers.setForeground(Color.white);
        lbstSBrokers.setAlignment(1);
        lbstSBrokers.setText("Sell Brokers");
        panel1.setBackground(Color.black);
        panel1.setBounds(new Rectangle(0, 0, 500, 40));
        panel1.setLayout(null);
        panel3.setBackground(Color.blue);
        panel3.setBounds(new Rectangle(128, 129, 62, 48));
        panel3.setLayout(null);
        panel4.setBackground(Color.red);
        panel4.setBounds(new Rectangle(193, 129, 62, 48));
        panel4.setLayout(null);
        lbask.setBackground(Color.white);
        lbask.setBounds(new Rectangle(7, 24, 48, 19));
        lbask.setFont(new java.awt.Font("Dialog", 1, 12));
        lbask.setAlignment(1);
        lbask.setText("0000");
        lbbid.setBackground(Color.white);
        lbbid.setBounds(new Rectangle(5, 24, 48, 19));
        lbbid.setFont(new java.awt.Font("Dialog", 1, 12));
        lbbid.setAlignment(1);
        lbbid.setText("0000");
        lbstBuy.setBackground(Color.blue);
        lbstBuy.setBounds(new Rectangle(8, 1, 48, 23));
        lbstBuy.setFont(new java.awt.Font("Serif", 1, 13));
        lbstBuy.setForeground(Color.white);
        lbstBuy.setAlignment(1);
        lbstBuy.setText("Buy");
        lbstSell.setBounds(new Rectangle(7, 1, 48, 23));
        lbstSell.setFont(new java.awt.Font("Serif", 1, 13));
        lbstSell.setForeground(Color.white);
        lbstSell.setAlignment(1);
        lbstSell.setText("Sell");
        lbstPE.setBackground(Color.white);
        lbstPE.setBounds(new Rectangle(308, 50, 41, 20));
        lbstPE.setFont(new java.awt.Font("Serif", 1, 12));
        lbstPE.setText("PE:");
        lbstYIELD.setBackground(Color.white);
        lbstYIELD.setBounds(new Rectangle(308, 71, 49, 20));
        lbstYIELD.setFont(new java.awt.Font("Serif", 1, 12));
        lbstYIELD.setText("YIELD:");
        lbstHSI.setBackground(Color.white);
        lbstHSI.setBounds(new Rectangle(389, 50, 40, 20));
        lbstHSI.setFont(new java.awt.Font("Serif", 1, 12));
        lbstHSI.setText("HSI:");
        lbstHSIF.setBackground(Color.white);
        lbstHSIF.setBounds(new Rectangle(390, 71, 40, 20));
        lbstHSIF.setFont(new java.awt.Font("Serif", 1, 12));
        lbstHSIF.setText("HSIF:");
        lbstStockID.setBounds(new Rectangle(309, 1, 64, 23));
        lbstStockID.setFont(new java.awt.Font("Dialog", 1, 14));
        lbstStockID.setForeground(Color.white);
        lbstStockID.setText("StockID");
        tfStock.setBackground(Color.white);
        tfStock.setBounds(new Rectangle(385, 1, 67, 17));
        tfStock.setFont(new java.awt.Font("Dialog", 1, 12));

        btGo.setBackground(SystemColor.control);
        btGo.setBounds(new Rectangle(453, 1, 46, 18));
        btGo.setLabel("GO");
        btGo.setFont(new java.awt.Font("Dialog", 1, 14));
        btGo.addActionListener(new java.awt.event.ActionListener() {

            public void actionPerformed(ActionEvent e) {
                btGo_actionPerformed(e);
            }
        });
        lbstNominal.setBackground(Color.cyan);
        lbstNominal.setBounds(new Rectangle(2, 48, 59, 19));
        lbstNominal.setFont(new java.awt.Font("Serif", 1, 12));
        lbstNominal.setText("Nominal");
        lbstHigh.setBackground(SystemColor.control);
        lbstHigh.setBounds(new Rectangle(2, 68, 35, 19));
        lbstHigh.setFont(new java.awt.Font("Serif", 1, 12));
        lbstHigh.setText("High");
        lbstPrevClose.setBackground(SystemColor.control);
        lbstPrevClose.setBounds(new Rectangle(2, 108, 67, 19));
        lbstPrevClose.setFont(new java.awt.Font("Serif", 1, 12));
        lbstPrevClose.setText("Prev-Close");
        lbstLow.setBackground(SystemColor.control);
        lbstLow.setBounds(new Rectangle(2, 88, 34, 19));
        lbstLow.setFont(new java.awt.Font("Serif", 1, 12));
        lbstLow.setText("Low");
        lbstTurnover.setText("Turnover");
        lbstTurnover.setBounds(new Rectangle(2, 168, 57, 19));
        lbstTurnover.setFont(new java.awt.Font("Serif", 1, 12));
        lbstTurnover.setBackground(SystemColor.control);
        lbstSpread.setText("Spread");
        lbstSpread.setBounds(new Rectangle(2, 188, 42, 19));
        lbstSpread.setFont(new java.awt.Font("Serif", 1, 12));
        lbstSpread.setBackground(SystemColor.control);
        lbstVolume.setText("Volume");
        lbstVolume.setBounds(new Rectangle(2, 148, 48, 19));
        lbstVolume.setFont(new java.awt.Font("Serif", 1, 12));
        lbstVolume.setBackground(SystemColor.control);
        lbstAvgPrice.setText("Avg Price");
        lbstAvgPrice.setBounds(new Rectangle(2, 128, 57, 19));
        lbstAvgPrice.setFont(new java.awt.Font("Serif", 1, 12));
        lbstAvgPrice.setBackground(SystemColor.control);
        lbstLotSize.setBackground(SystemColor.control);
        lbstLotSize.setBounds(new Rectangle(2, 208, 60, 19));
        lbstLotSize.setFont(new java.awt.Font("Serif", 1, 12));
        lbstLotSize.setText("Lot Size");
        lbstChg.setBackground(SystemColor.control);
        lbstChg.setBounds(new Rectangle(2, 228, 53, 19));
        lbstChg.setFont(new java.awt.Font("Serif", 1, 12));
        lbstChg.setText("Chg");
        lbstPChg.setBackground(SystemColor.control);
        lbstPChg.setBounds(new Rectangle(2, 248, 54, 19));
        lbstPChg.setFont(new java.awt.Font("Serif", 1, 12));
        lbstPChg.setText("%Chg");
        lbPCChg.setText("0000");
        lbPCChg.setFont(new java.awt.Font("Serif", 1, 12));
        lbPCChg.setAlignment(2);
        lbPCChg.setBounds(new Rectangle(56, 248, 68, 19));
        lbPCChg.setBackground(SystemColor.control);
        lbChg.setText("0000");
        lbChg.setFont(new java.awt.Font("Serif", 1, 12));
        lbChg.setAlignment(2);
        lbChg.setBounds(new Rectangle(56, 228, 68, 19));
        lbChg.setBackground(SystemColor.control);
        lbLotSize.setText("0000");
        lbLotSize.setFont(new java.awt.Font("Serif", 1, 12));
        lbLotSize.setAlignment(2);
        lbLotSize.setBounds(new Rectangle(63, 208, 61, 19));
        lbLotSize.setBackground(SystemColor.control);
        lbAvgPrice.setBackground(SystemColor.control);
        lbAvgPrice.setFont(new java.awt.Font("Serif", 1, 12));
        lbAvgPrice.setAlignment(2);
        lbAvgPrice.setBounds(new Rectangle(60, 128, 64, 19));
        lbAvgPrice.setText("0000");
        lbVolume.setBackground(SystemColor.control);
        lbVolume.setFont(new java.awt.Font("Serif", 1, 12));
        lbVolume.setAlignment(2);
        lbVolume.setBounds(new Rectangle(51, 148, 73, 19));
        lbVolume.setText("0000");
        lbSpread.setBackground(SystemColor.control);
        lbSpread.setFont(new java.awt.Font("Serif", 1, 12));
        lbSpread.setAlignment(2);
        lbSpread.setBounds(new Rectangle(45, 188, 79, 19));
        lbSpread.setText("0000");
        lbTurnover.setBackground(SystemColor.control);
        lbTurnover.setFont(new java.awt.Font("Serif", 1, 12));
        lbTurnover.setAlignment(2);
        lbTurnover.setBounds(new Rectangle(60, 168, 64, 19));
        lbTurnover.setText("0000");
        lbLow.setText("0000");
        lbLow.setFont(new java.awt.Font("Serif", 1, 12));
        lbLow.setAlignment(2);
        lbLow.setBounds(new Rectangle(37, 88, 87, 19));
        lbLow.setBackground(SystemColor.control);
        lbPrevClose.setText("0000");
        lbPrevClose.setFont(new java.awt.Font("Serif", 1, 12));
        lbPrevClose.setAlignment(2);
        lbPrevClose.setBounds(new Rectangle(70, 108, 54, 19));
        lbPrevClose.setBackground(SystemColor.control);
        lbHigh.setText("0000");
        lbHigh.setFont(new java.awt.Font("Serif", 1, 12));
        lbHigh.setAlignment(2);
        lbHigh.setBounds(new Rectangle(38, 68, 86, 19));
        lbHigh.setBackground(SystemColor.control);
        lbNominal.setText("0000");
        lbNominal.setFont(new java.awt.Font("Serif", 1, 12));
        lbNominal.setAlignment(2);
        lbNominal.setBounds(new Rectangle(61, 48, 63, 19));
        lbNominal.setBackground(Color.cyan);
        tradeTable.setBackground(Color.gray);
        tradeTable.setBounds(new Rectangle(128, 48, 127, 77));
        this.setBackground(SystemColor.control);
        lbPE.setBackground(Color.white);
        lbPE.setBounds(new Rectangle(348, 50, 41, 20));
        lbPE.setFont(new java.awt.Font("Serif", 1, 12));
        lbPE.setForeground(Color.blue);
        lbPE.setAlignment(2);
        lbPE.setText("###");
        lbYIELD.setBackground(Color.white);
        lbYIELD.setBounds(new Rectangle(356, 71, 34, 20));
        lbYIELD.setFont(new java.awt.Font("Serif", 1, 12));
        lbYIELD.setForeground(Color.blue);
        lbYIELD.setAlignment(2);
        lbYIELD.setText("###");
        buySTable.setBackground(Color.gray);
        buySTable.setBounds(new Rectangle(128, 179, 62, 96));
        buySTable.setFontColor(Color.blue, Color.black);
        sellSTable.setBackground(Color.gray);
        sellSTable.setBounds(new Rectangle(193, 179, 62, 96));
        sellSTable.setFontColor(Color.red, Color.black);
        lbCode.setBackground(Color.black);
        lbCode.setBounds(new Rectangle(1, 1, 34, 23));
        lbCode.setFont(new java.awt.Font("Dialog", 1, 15));
        lbCode.setForeground(Color.white);
        lbCode.setAlignment(1);
        lbCode.setText("####");
        lbName.setBackground(Color.black);
        lbName.setBounds(new Rectangle(36, 1, 127, 23));
        lbName.setFont(new java.awt.Font("Dialog", 1, 13));
        lbName.setForeground(Color.white);
        lbName.setText("####");

        lbdatetime.setBackground(Color.black);
        lbdatetime.setBounds(new Rectangle(109, 25, 154, 14));
        lbdatetime.setFont(new java.awt.Font("Serif", 0, 12));
        lbdatetime.setForeground(Color.white);
        //lbdatetime.setText("Last Update:");
        //lbdatetime.setText("\u6700\u5f8c\u66f4\u65b0:");
        lbCName.setBackground(Color.black);
        lbCName.setBounds(new Rectangle(175, 1, 148, 23));
        lbCName.setFont(new java.awt.Font("Dialog", 1, 14));
        lbCName.setForeground(Color.white);
        lbCName.setText("####");
        lbHSI.setBackground(Color.white);
        lbHSI.setBounds(new Rectangle(429, 50, 71, 20));
        lbHSI.setFont(new java.awt.Font("Serif", 1, 12));
        lbHSI.setForeground(Color.blue);
        lbHSI.setText("#####");
        lbHSIF.setBackground(Color.white);
        lbHSIF.setBounds(new Rectangle(429, 71, 71, 20));
        lbHSIF.setFont(new java.awt.Font("Serif", 1, 12));
        lbHSIF.setForeground(Color.blue);
        lbHSIF.setText("#####");

        lbUpdate.setForeground(Color.white);
        lbUpdate.setFont(new java.awt.Font("Serif", 0, 12));
        lbUpdate.setBounds(new Rectangle(0, 25, 109, 14));
        lbUpdate.setBackground(Color.black);
        btLog.setBounds(new Rectangle(260, 50, 46, 20));
        btLog.setVisible(true);
        btLog.setLabel("Log");
        btLog.setFont(new java.awt.Font("Dialog", 0, 12));
        btLog.addActionListener(new java.awt.event.ActionListener() {

            public void actionPerformed(ActionEvent e) {
                btLog_actionPerformed(e);
            }
        });
        btBroker.setBounds(new Rectangle(260, 71, 46, 20));
        btBroker.setVisible(true);
        btBroker.setLabel("Broker");
        btBroker.setFont(new java.awt.Font("Dialog", 0, 12));
        btBroker.addActionListener(new java.awt.event.ActionListener() {

            public void actionPerformed(ActionEvent e) {
                btBroker_actionPerformed(e);
            }
        });
        lstLog.setBackground(Color.white);
        lstLog.setBounds(new Rectangle(128, 48, 127, 228));
        lstLog.setFont(new Font("Courier", 0, 9));
        textArea1.setBackground(SystemColor.controlLtHighlight);
        textArea1.setBounds(new Rectangle(0, 40, 500, 240));
        textArea1.setFont(new Font("Dialog", 0, 11));
        textArea1.setEditable(false);
        textArea1.setVisible(false);
        this.add(textArea1, null);
        this.add(lbstPChg, null);
        this.add(lbstNominal, null);
        this.add(lbstHigh, null);
        this.add(lbstLow, null);
        this.add(lbstPrevClose, null);
        this.add(lbstAvgPrice, null);
        this.add(lbstVolume, null);
        this.add(lbstTurnover, null);
        this.add(lbstSpread, null);
        this.add(lbstLotSize, null);
        this.add(lbstChg, null);
        this.add(lbNominal, null);
        this.add(lbPCChg, null);
        this.add(lbChg, null);
        this.add(lbLotSize, null);
        this.add(lbAvgPrice, null);
        this.add(lbVolume, null);
        this.add(lbSpread, null);
        this.add(lbTurnover, null);
        this.add(lbLow, null);
        this.add(lbPrevClose, null);
        this.add(lbHigh, null);
        this.add(tradeTable, null);
        this.add(panel1, null);
        panel1.add(lbCode, null);
        panel1.add(lbName, null);
        panel1.add(lbdatetime, null);
        panel1.add(btGo, null);
        panel1.add(tfStock, null);
        panel1.add(lbstStockID, null);
        panel1.add(lbCName, null);
        panel1.add(lbUpdate, null);
        this.add(buySTable, null);
        this.add(panel3, null);
        panel3.add(lbask, null);
        panel3.add(lbstBuy, null);
        this.add(tfCompany, null);
        this.add(buyBrokersTable, null);
        this.add(sellBrokersTable, null);
        this.add(lbstSBrokers, null);
        this.add(lbstBBrokers, null);
        this.add(panel4, null);
        panel4.add(lbbid, null);
        panel4.add(lbstSell, null);
        this.add(sellSTable, null);
        this.add(lstLog, null);
        lstLog.setVisible(false);

        this.add(lbstYIELD, null);
        this.add(lbstPE, null);
        this.add(lbPE, null);
        this.add(lbYIELD, null);
        this.add(btLog, null);
        this.add(btBroker, null);
        this.add(lbstHSIF, null);
        this.add(lbHSIF, null);
        this.add(lbstHSI, null);
        this.add(lbHSI, null);
// add the key lister for the textbox
        this.tfStock.addKeyListener(this);

/// add the mouse event lister for the broker table's item
        MouseMotionAdapter brokerAdapter = new MouseMotionAdapter() {
            public void mouseMoved(MouseEvent e) {
                tb_actionPeformed(e);
            }
        };

        MouseAdapter clickAdapter = new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                brokersTable_mouseClicked(e);
            }
        };

        for (int i = 0; i < 4; i++)
            for (int j = 0; j < 10; j++) {
                buyBrokersTable.lbItem[i][j].addMouseMotionListener(brokerAdapter);
                sellBrokersTable.lbItem[i][j].addMouseMotionListener(brokerAdapter);
                buyBrokersTable.lbItem[i][j].addMouseListener(clickAdapter);
                sellBrokersTable.lbItem[i][j].addMouseListener(clickAdapter);
            }
    }

    public void SetLanguage(int tlanguage) throws Exception {
        language = tlanguage;

        if (language == FinetExpress.constEnglish) {
            this.lbstNominal.setText(lbArray[0][0]);   //Normial
            this.lbstHigh.setText(lbArray[1][0]);      //high
            this.lbstLow.setText(lbArray[2][0]);      //Low
            this.lbstPrevClose.setText(lbArray[3][0]); //{"Prev-Close",""}
            this.lbstAvgPrice.setText(lbArray[4][0]);  //{"Avg Price",""}
            this.lbstVolume.setText(lbArray[5][0]);   //{"Volume",""}
            this.lbstTurnover.setText(lbArray[6][0]);  //,{"Turnover",""}
            this.lbstSpread.setText(lbArray[7][0]);    //,{"Spread",""}
            this.lbstLotSize.setText(lbArray[8][0]);   //,{"Lot Size",""}
            this.lbstChg.setText(lbArray[9][0]);       //,{"Chg",""}
            this.lbstPChg.setText(lbArray[10][0]);     //,{"%Chg",""}
            this.lbstPE.setText(lbArray[11][0]);       //,{"PE",""}
            this.lbstHSI.setText(lbArray[12][0]);      //,{"HSI",""}
            this.lbstYIELD.setText(lbArray[13][0]);    //,{"YIELD",""}
            this.lbstHSIF.setText(lbArray[14][0]);     //,{"HSIF",""}
            this.lbstBuy.setText(lbArray[15][0]);      //,{"Buy",""}
            this.lbstSell.setText(lbArray[16][0]);     //,{"Sell",""}
            this.lbstBBrokers.setText(lbArray[17][0]); //,{"Buy Brokers",""}
            this.lbstSBrokers.setText(lbArray[18][0]); //,{"Sell Brokers",""}};
            this.btGo.setLabel(lbArray[19][0]);        //,"Go"
            this.lbstStockID.setText(lbArray[21][0]);  //,Stock ID
            this.lbUpdate.setText(lbArray[22][0]);  //,"Last Updated"
            this.btLog.setLabel(lbArray[23][0]);   // , log
            this.btBroker.setLabel(lbArray[24][0]); // , Broker
        } else if (language == FinetExpress.constChinese) {
            this.lbstNominal.setText(lbArray[0][1]);   //Normial
            this.lbstHigh.setText(lbArray[1][1]);      //high
            this.lbstLow.setText(lbArray[2][1]);      //Low
            this.lbstPrevClose.setText(lbArray[3][1]); //{"Prev-Close",""}
            this.lbstAvgPrice.setText(lbArray[4][1]);  //{"Avg Price",""}
            this.lbstVolume.setText(lbArray[5][1]);   //{"Volume",""}
            this.lbstTurnover.setText(lbArray[6][1]);  //,{"Turnover",""}
            this.lbstSpread.setText(lbArray[7][1]);    //,{"Spread",""}
            this.lbstLotSize.setText(lbArray[8][1]);   //,{"Lot Size",""}
            this.lbstChg.setText(lbArray[9][1]);       //,{"Chg",""}
            this.lbstPChg.setText(lbArray[10][1]);     //,{"%Chg",""}
            this.lbstPE.setText(lbArray[11][1]);       //,{"PE",""}
            this.lbstHSI.setText(lbArray[12][1]);      //,{"HSI",""}
            this.lbstYIELD.setText(lbArray[13][1]);    //,{"YIELD",""}
            this.lbstHSIF.setText(lbArray[14][1]);     //,{"HSIF",""}
            this.lbstBuy.setText(lbArray[15][1]);      //,{"Buy",""}
            this.lbstSell.setText(lbArray[16][1]);     //,{"Sell",""}
            this.lbstBBrokers.setText(lbArray[17][1]); //,{"Buy Brokers",""}
            this.lbstSBrokers.setText(lbArray[18][1]); //,{"Sell Brokers",""}};
            this.btGo.setLabel(lbArray[19][1]);        //,"Go"
            this.lbstStockID.setText(lbArray[21][1]);  //,Stock ID
            this.lbUpdate.setText(lbArray[22][1]);  //,"Last Updated"
            this.btLog.setLabel(lbArray[23][1]);   // , log
            this.btBroker.setLabel(lbArray[24][1]); // , Broker
        }
    }


    void tb_actionPeformed(MouseEvent e) {
        try {
            FLabel clickedLabel = (FLabel) e.getComponent();
            //System.out.println(clickedLabel.getText());
            String cCode = clickedLabel.getText();
            if (cCode.indexOf("-") >= 0 || cCode.indexOf("+") >= 0 || cCode.equals("")) return;
            int iCode = Integer.parseInt(cCode) / 10;
            String ciCode = String.valueOf(iCode);

            if (language == FinetExpress.constChinese)
                cCode = cCode + " : " + teletextSource.BrokerQueue.elementAt(teletextSource.BrokerQueue.indexOf(ciCode) + 1).toString();
            else
                cCode = cCode + " : " + teletextSource.BrokerQueue.elementAt(teletextSource.BrokerQueue.indexOf(ciCode) + 2).toString();

            this.tfCompany.setText(cCode);
        } catch (Exception ex) {
            Report("TeletextPanel " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    void btGo_actionPerformed(ActionEvent e) {
        //Check the code in the text field is correct or not
        try {
            int ccode = Integer.parseInt(tfStock.getText());
            if (ccode > 9999 || ccode < 0) {
                tfStock.setText("");
                return;
            }
            String stStock = this.tfStock.getText();
            if (stStock.length() > 4 || stStock.length() == 0) {
                tfStock.setText("");
                return;
            }
            teletextEngine.viewStock(stStock);
            tfStock.setText("");
        } catch (Exception ex) {
            tfStock.setText("");
            return;
        }
    }

    public void keyPressed(KeyEvent e) {
    }

    public void keyTyped(KeyEvent e) {
    }

    /*** Invoked when a key has been released.*/
    public void keyReleased(KeyEvent e) {
        // when ENTER is pressed, call the "GO" button
        if (e.getKeyCode() == 10) {
            btGo_actionPerformed(null);
        }
    }

    void brokersTable_mouseClicked(MouseEvent e) {
        if (e.getClickCount() < 2) return;
//    finetExpress.showBroker();
        try {
            FLabel clickedLabel = (FLabel) e.getComponent();
            //System.out.println(clickedLabel.getText());
            String cCode = clickedLabel.getText();
            if (cCode.indexOf("-") >= 0 || cCode.indexOf("+") >= 0 || cCode.equals("")) return;
            finetExpress.showBroker(cCode);
        } catch (Exception ex) {
            Report("TeletextPanel " + ex.getMessage());
            ex.printStackTrace();
        }

    }

    void btLog_actionPerformed(ActionEvent e) {
        if (lstLog.isVisible()) {
            lstLog.setVisible(false);
            tradeTable.setVisible(true);
            panel3.setVisible(true);
            panel4.setVisible(true);
            sellSTable.setVisible(true);
            buySTable.setVisible(true);
        } else {
            teletextEngine.GetLog();
            lstLog.setVisible(true);
            tradeTable.setVisible(false);
            panel3.setVisible(false);
            panel4.setVisible(false);
            sellSTable.setVisible(false);
            buySTable.setVisible(false);
        }

    }

    void btBroker_actionPerformed(ActionEvent e) {
        finetExpress.showBroker(null);
    }
}

