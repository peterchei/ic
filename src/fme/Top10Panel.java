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
import java.awt.event.ActionEvent;

public class Top10Panel extends Panel {
    private final String lbArray[][] = {{"Top 10 Stock", "\u5341\u5927\u6d3b\u8e8d\u6e2f\u80a1"}
                                        , {"Top10 Gainers", "\u5341\u5927\u5347\u5e45"}
                                        , {"Top10 Losers", "\u5341\u5927\u8dcc\u5e45"}
                                        , {"Top10 Volume", "\u5341\u5927\u6210\u4ea4\u91cf"}
                                        , {"Top10 Turnover", "\u5341\u5927\u6210\u4ea4\u984d"}
                                        //  ,{"Code","\u80a1\u7968\u865f\u78bc"}
                                        , {"Code", "\u80a1\u865f"}
                                        , {"Name", "\u540d\u7a31"}
                                        , {"Last", "\u6700\u65b0\u50f9"}
                                        , {"Change", "\u5347\u8dcc"}
                                        , {"%Chg", "\u5347\u8dcc\u6bd4\u7387"}
                                        , {"High", "\u6700\u9ad8"}
                                        , {"Low", "\u6700\u4f4e"}
                                        , {"Volume", "\u6210\u4ea4\u91cf"}
                                        , {"Turnover", "\u6210\u4ea4\u984d"}
    };
    private int language;


    Button btTopLose = new Button();
    Button btTopVolume = new Button();
    Button btTopTurnover = new Button();
    FTable toplistTable = new FTable(9, 10, new int[]{40, 110, 52, 42, 52, 42, 45, 47, 50}, 18, "default", 0, 12);
    Label lbstCode = new Label();
    Label lbstName = new Label();
    Label lbstLast = new Label();
    Label lbstChange = new Label();
    Label lbstPChg = new Label();
    Label lbstHigh = new Label();
    Label lbstLow = new Label();
    Label lbstVolume = new Label();
    Label lbstTurnover = new Label();
    Button btTopGain = new Button();
    FinetExpress finetExpress;


    private Top10Source top10Source;
    private Top10Engine top10Engine;

    public Top10Panel() {
        try {
            jbInit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void SetMainApplet(FinetExpress fe) throws Exception {
        this.finetExpress = fe;
    }

    public void setCaptionBackground(Color c) throws Exception {
//        panel1.setBackground(c);
    }

    public void setInfoBackground(Color c) throws Exception {
        this.setBackground(c);
    }

    private void jbInit() throws Exception {
        this.setLayout(null);
        btTopLose.setBackground(SystemColor.control);
        btTopLose.setBounds(new Rectangle(194, 37, 100, 23));
        btTopLose.setLabel("Top10 Losers");
        btTopLose.setFont(new java.awt.Font("Dialog", 0, 11));

        btTopLose.addActionListener(new java.awt.event.ActionListener() {

            public void actionPerformed(ActionEvent e) {
                btTopLose_actionPerformed(e);
            }
        });
        btTopVolume.setBackground(SystemColor.control);
        btTopVolume.setBounds(new Rectangle(294, 37, 100, 23));
        btTopVolume.setLabel("Top10 Volume");
        btTopVolume.setFont(new java.awt.Font("Dialog", 0, 11));
        btTopVolume.addActionListener(new java.awt.event.ActionListener() {

            public void actionPerformed(ActionEvent e) {
                btTopVolume_actionPerformed(e);
            }
        });
        btTopTurnover.setBackground(SystemColor.control);
        btTopTurnover.setBounds(new Rectangle(394, 37, 100, 23));
        btTopTurnover.setFont(new java.awt.Font("Dialog", 1, 12));
        btTopTurnover.setLabel("Top 10 Turnover");
        btTopTurnover.addActionListener(new java.awt.event.ActionListener() {

            public void actionPerformed(ActionEvent e) {
                btTopTurnover_actionPerformed(e);
            }
        });
        toplistTable.setBackground(Color.black);
        toplistTable.setBounds(new Rectangle(9, 81, 490, 193));
        lbstCode.setBackground(Color.black);
        lbstCode.setBounds(new Rectangle(5, 60, 41, 23));
        lbstCode.setFont(new java.awt.Font("Dialog", 1, 12));
        lbstCode.setForeground(Color.white);
        lbstCode.setAlignment(1);
        lbstCode.setText("Code");
        lbstName.setText("Name");
        lbstName.setAlignment(1);
        lbstName.setFont(new java.awt.Font("Dialog", 1, 12));
        lbstName.setForeground(Color.white);
        lbstName.setBounds(new Rectangle(46, 60, 111, 23));
        lbstName.setBackground(Color.black);
        lbstLast.setText("Last");
        lbstLast.setAlignment(1);
        lbstLast.setFont(new java.awt.Font("Dialog", 1, 12));
        lbstLast.setForeground(Color.white);
        lbstLast.setBounds(new Rectangle(157, 60, 53, 23));
        lbstLast.setBackground(Color.black);
        lbstChange.setText("Change");
        lbstChange.setAlignment(1);
        lbstChange.setFont(new java.awt.Font("Dialog", 1, 12));
        lbstChange.setForeground(Color.white);
        lbstChange.setBounds(new Rectangle(210, 60, 43, 23));
        lbstChange.setBackground(Color.black);
        lbstPChg.setText("%Chg");
        lbstPChg.setAlignment(1);
        lbstPChg.setFont(new java.awt.Font("Dialog", 1, 12));
        lbstPChg.setForeground(Color.white);
        lbstPChg.setBounds(new Rectangle(253, 60, 53, 23));
        lbstPChg.setBackground(Color.black);
        lbstHigh.setText("High");
        lbstHigh.setAlignment(1);
        lbstHigh.setFont(new java.awt.Font("Dialog", 1, 12));
        lbstHigh.setForeground(Color.white);
        lbstHigh.setBounds(new Rectangle(306, 60, 43, 23));
        lbstHigh.setBackground(Color.black);
        lbstLow.setText("Low");
        lbstLow.setAlignment(1);
        lbstLow.setFont(new java.awt.Font("Dialog", 1, 12));
        lbstLow.setForeground(Color.white);
        lbstLow.setBounds(new Rectangle(349, 60, 46, 23));
        lbstLow.setBackground(Color.black);
        lbstVolume.setText("Volume");
        lbstVolume.setAlignment(1);
        lbstVolume.setFont(new java.awt.Font("Dialog", 1, 12));
        lbstVolume.setForeground(Color.white);
        lbstVolume.setBounds(new Rectangle(395, 60, 48, 23));
        lbstVolume.setBackground(Color.black);

        lbstTurnover.setText("Turnover");
        lbstTurnover.setAlignment(1);
        lbstTurnover.setFont(new java.awt.Font("Dialog", 1, 12));
        lbstTurnover.setForeground(Color.white);
        lbstTurnover.setBounds(new Rectangle(442, 60, 53, 23));
        lbstTurnover.setBackground(Color.black);


        btTopGain.setBackground(SystemColor.control);
        btTopGain.setBounds(new Rectangle(94, 37, 100, 23));
        btTopGain.setLabel("Top10 Gainers");
        btTopGain.setFont(new java.awt.Font("Dialog", 0, 11));
        btTopGain.addActionListener(new java.awt.event.ActionListener() {

            public void actionPerformed(ActionEvent e) {
                btTopGain_actionPerformed(e);
            }
        });
        this.setBackground(SystemColor.control);
        this.add(lbstName, null);
        this.add(lbstChange, null);
        this.add(lbstLast, null);
        this.add(lbstPChg, null);
        this.add(lbstHigh, null);
        this.add(lbstLow, null);
        this.add(lbstCode, null);
        this.add(lbstVolume, null);
        this.add(lbstTurnover, null);
        this.add(btTopTurnover, null);
        this.add(btTopVolume, null);
        this.add(btTopLose, null);
        this.add(btTopGain, null);
        this.add(toplistTable, null);

        //set the Company name label format
        for (int i = 0; i < 10; i++) {
            toplistTable.lbItem[1][i].setAlignment(0);
        }
    }

    void UpdateInformation() throws Exception {

        Item currentItems[] = new Item[10];
        ;
        if (top10Source.Type == top10Source.typeTop10Gain) {
            currentItems = top10Source.TopGain;
            btTopGain.setFont(new java.awt.Font("Dialog", 1, 12));

        } else if (top10Source.Type == top10Source.typeTop10Lose) {
            currentItems = top10Source.TopLose;

            btTopLose.setFont(new java.awt.Font("Dialog", 1, 12));


        } else if (top10Source.Type == top10Source.typeTop10Volume) {
            currentItems = top10Source.TopVolume;

            btTopVolume.setFont(new java.awt.Font("Dialog", 1, 12));
        } else if (top10Source.Type == top10Source.typeTup10Turnover) {
            currentItems = top10Source.TopTurnover;
            btTopTurnover.setFont(new java.awt.Font("Dialog", 1, 12));
        }

        for (int i = 0; i < 10; i++) {
            toplistTable.lbItem[0][i].setText(currentItems[i].Code);

            if (language == FinetExpress.constEnglish)
                toplistTable.lbItem[1][i].setText(currentItems[i].Name);
            else if (language == FinetExpress.constChinese)
                toplistTable.lbItem[1][i].setText(currentItems[i].cName);
            toplistTable.lbItem[2][i].setText(currentItems[i].Last);
            toplistTable.lbItem[3][i].setText(currentItems[i].Change);
            toplistTable.lbItem[4][i].setText(currentItems[i].PCChg);
            toplistTable.lbItem[5][i].setText(currentItems[i].High);
            toplistTable.lbItem[6][i].setText(currentItems[i].Low);
            toplistTable.lbItem[7][i].setText(FormatData.FormatString2(currentItems[i].Volume, 1));
            toplistTable.lbItem[8][i].setText(FormatData.FormatString2(currentItems[i].Turnover, 1));
//      toplistTable.lbItem[7][i].setText(FormatData.FormatString(currentItems[i].Turnover,5));
        }

    }

    void SetSource(Top10Source ts) throws Exception {
        top10Source = ts;
    }

    void SetEngine(Top10Engine te) throws Exception {
        top10Engine = te;
    }

    void btTopGain_actionPerformed(ActionEvent e) {
        try {
            top10Engine.SetTop10Type(Top10Source.typeTop10Gain);

            //update the button label
            btTopGain.setFont(new java.awt.Font("Dialog", 1, 12));
            btTopVolume.setFont(new java.awt.Font("Dialog", 0, 12));
            btTopLose.setFont(new java.awt.Font("Dialog", 0, 12));
            btTopTurnover.setFont(new java.awt.Font("Dialog", 0, 12));
            UpdateInformation();
        } catch (Exception ex) {
            Report("FinetExpress::btTopGain_actionPerformed " + ex.getMessage());
        }
    }

    void btTopLose_actionPerformed(ActionEvent e) {
        try {
            top10Engine.SetTop10Type(Top10Source.typeTop10Lose);
            btTopGain.setFont(new java.awt.Font("Dialog", 0, 12));
            btTopVolume.setFont(new java.awt.Font("Dialog", 0, 12));
            btTopLose.setFont(new java.awt.Font("Dialog", 1, 12));
            btTopTurnover.setFont(new java.awt.Font("Dialog", 0, 12));
            UpdateInformation();
        } catch (Exception ex) {
            Report("FinetExpress::btTopLose_actionPerformed " + ex.getMessage());
        }
    }

    void btTopVolume_actionPerformed(ActionEvent e) {
        try {
            top10Engine.SetTop10Type(Top10Source.typeTop10Volume);
            btTopGain.setFont(new java.awt.Font("Dialog", 0, 12));
            btTopVolume.setFont(new java.awt.Font("Dialog", 1, 12));
            btTopLose.setFont(new java.awt.Font("Dialog", 0, 12));
            btTopTurnover.setFont(new java.awt.Font("Dialog", 0, 12));
            UpdateInformation();
        } catch (Exception ex) {
            Report("FinetExpress::btTopVolume_actionPerformed " + ex.getMessage());
        }
    }

    void btTopTurnover_actionPerformed(ActionEvent e) {
        try {
            top10Engine.SetTop10Type(Top10Source.typeTup10Turnover);
            btTopGain.setFont(new java.awt.Font("Dialog", 0, 12));
            btTopVolume.setFont(new java.awt.Font("Dialog", 0, 12));
            btTopLose.setFont(new java.awt.Font("Dialog", 0, 12));
            btTopTurnover.setFont(new java.awt.Font("Dialog", 1, 12));
            UpdateInformation();
        } catch (Exception ex) {
            Report("FinetExpress::btTopTurnover_actionPerformed " + ex.getMessage());
        }
    }


    // this function is used to report Server, state = 0 (RUNING) state = 1 (down a moment)
    public void ReportServerState(int sstate) {
        // server OK
        if (sstate == 0) {
            Report("Running.........");
        }
        // server Down
        else if (sstate == 1) {
            Report("Server busy and may be down for a moment");
        }
    }


    public void Report(String rp) {
        finetExpress.showStatus(rp);
    }

    public void SetLanguage(int tlanguage) throws Exception {
        language = tlanguage;

        if (language == FinetExpress.constEnglish) {
//            this.lbstTitle.setText(lbArray[0][0]);   // {"Top 10 Stock",""}
            this.btTopGain.setLabel(lbArray[1][0]);  //,{"Top10 Gain",""}
            this.btTopLose.setLabel(lbArray[2][0]);  //,{"Top10 Lose",""}
            this.btTopVolume.setLabel(lbArray[3][0]);//,{"Top10 Volume",""}
            this.btTopTurnover.setLabel(lbArray[4][0]);//,{"Top10 Turnover",""}
            this.lbstCode.setText(lbArray[5][0]);    //,{"Code",""}
            this.lbstName.setText(lbArray[6][0]);    //,{"Name",""}
            this.lbstLast.setText(lbArray[7][0]);    //,{"Last",""}
            this.lbstChange.setText(lbArray[8][0]);  //,{"Change",""}
            this.lbstPChg.setText(lbArray[9][0]);    //,{"%Chg",""}
            this.lbstHigh.setText(lbArray[10][0]);   //,{"High",""}
            this.lbstLow.setText(lbArray[11][0]);    //,{"Low",""}
            this.lbstVolume.setText(lbArray[12][0]); //,{"Volume",""}
            this.lbstTurnover.setText(lbArray[13][0]); //,{"Turnover",""}
        } else if (language == FinetExpress.constChinese) {
//            this.lbstTitle.setText(lbArray[0][1]);   // {"Top 10 Stock",""}
            this.btTopGain.setLabel(lbArray[1][1]);  //,{"Top10 Gain",""}
            this.btTopLose.setLabel(lbArray[2][1]);  //,{"Top10 Lose",""}
            this.btTopVolume.setLabel(lbArray[3][1]);//,{"Top10 Volume",""}
            this.btTopTurnover.setLabel(lbArray[4][1]);//,{"Top10 Turnover",""}
            this.lbstCode.setText(lbArray[5][1]);    //,{"Code",""}
            this.lbstName.setText(lbArray[6][1]);    //,{"Name",""}
            this.lbstLast.setText(lbArray[7][1]);    //,{"Last",""}
            this.lbstChange.setText(lbArray[8][1]);  //,{"Change",""}
            this.lbstPChg.setText(lbArray[9][1]);    //,{"%Chg",""}
            this.lbstHigh.setText(lbArray[10][1]);   //,{"High",""}
            this.lbstLow.setText(lbArray[11][1]);    //,{"Low",""}
            this.lbstVolume.setText(lbArray[12][1]); //,{"Volume",""}
            this.lbstTurnover.setText(lbArray[13][1]); //,{"Turnover",""}
        }

        this.UpdateInformation();
    }
}
