/**
 * Title:        <p>
 * Description:  <p>
 * Copyright:    Copyright (c) <p>
 * Company:      <p>
 * @author
 * @version 1.0
 */
package fme;

import java.applet.AudioClip;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class StockWatchPanel extends Panel implements KeyListener {

    private boolean blnState = false;
    private final String lbArray[][] = {{"Stock Watch", "\u80a1\u7968\u8ffd\u8e64"}
                                        , {"Add", "\u52a0\u5165"}
                                        , {"Save", "\u5132\u5b58"}
                                        , {"Remove", "\u79fb\u9664"}
                                        , {"Price Alert", "\u5230\u50f9\u63d0\u793a"}
                                        , {"Code", "\u80a1\u865f"}
                                        , {"Name", "\u540d\u7a31"}
                                        , {"Last", "\u6700\u65b0\u50f9"}
                                        , {"Change", "\u5347\u8dcc"}
                                        , {"%Chg", "\u5347\u8dcc\u6bd4\u7387"}
                                        , {"High", "\u6700\u9ad8"}
                                        , {"Low", "\u6700\u4f4e"}
                                        , {"Volume", "\u6210\u4ea4\u91cf"}
                                        , {"Lower Alert", "\u4e0b\u8b66\u6212\u4f4d"}
                                        , {"Upper Alert", "\u4e0a\u8b66\u6212\u4f4d"}
                                        , {"Clear", "\u6e05\u9664"}
                                        , {"Done", "\u78ba\u5b9a"}
                                        , {"Finet Stock Watch", "\u80a1\u7968\u8ffd\u8e64"}
                                        , {"Price Alert Reached!!", "\u5230\u9054\u8b66\u6212\u4f4d!!"}
                                        , {"Sound Alert", "\u8072\u97f3\u63d0\u793a"}
                                        , {"Turnover", "\u6210\u4ea4\u984d"}
    };
    int language;

//audio Clip plays when the price reach our target.
    public AudioClip alertClip = null;
    private boolean psound = true;

//the parent window....
    private FinetExpress finetExpress;
//for the stock....


    private StockWatchEngine stockwatchEngine;
    private StockWatchSource stockwatchSource;
    Panel panel1 = new Panel();
    Label lbstTitle = new Label();
//  FTable stockTable = new FTable(8,7,new int[]{52,110,52,52,52,52,55,55}, 18,"default" ,0,12);
    FTable stockTable = new FTable(9, 8, new int[]{40, 110, 52, 42, 52, 42, 45, 47, 50}, 18, "default", 0, 12);
    Button btAddCode = new Button();
    Label lbstCode = new Label();
    Label lbstName = new Label();
    Label lbstLast = new Label();
    Label lbstChange = new Label();
    Label lbstPChg = new Label();
    Label lbstHigh = new Label();
    Label lbstLow = new Label();
    Label lbstVolume = new Label();
    Label lbstTurnover = new Label();
    Choice chRmCode = new Choice();
    Button btRemove = new Button();
    TextField tfcode = new TextField();
    TextField tflowerbar = new TextField();
    Button btEditAlert = new Button();
    Panel panelLow = new Panel();
    Panel panelHigh = new Panel();
    Label lbLowPrice = new Label();
    GridLayout gridLayout1 = new GridLayout();
    TextField tfLowPrice = new TextField();
    Label lbHighPrice = new Label();
    GridLayout gridLayout2 = new GridLayout();
    TextField tfHighPrice = new TextField();
    Button btDone = new Button();
    Button btClear = new Button();
    Button btSave = new Button();
    Checkbox cbSound = new Checkbox();
    Label lblSound = new Label();
    final int maxcount = 8;

    public StockWatchPanel() {
        try {
            jbInit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setCaptionBackground(Color c) {
        panel1.setBackground(c);
        lbstTitle.setBackground(c);
        cbSound.setBackground(c);
        lblSound.setBackground(c);
    }

    public void setInfoBackground(Color c) {
        this.setBackground(c);
    }

    private void jbInit() throws Exception {
        this.setLayout(null);
        panel1.setBackground(Color.black);
        panel1.setBounds(new Rectangle(0, 0, 500, 33));
        panel1.setLayout(null);
        lbstTitle.setBounds(new Rectangle(0, 8, 265, 24));
        lbstTitle.setFont(new java.awt.Font("Dialog", 1, 20));
        lbstTitle.setForeground(Color.white);
        lbstTitle.setText("Stock Watch");
        stockTable.setBackground(Color.black);
        stockTable.setBounds(new Rectangle(5, 105, 491, 154));
        btAddCode.setBounds(new Rectangle(83, 35, 83, 23));
        btAddCode.setLabel("Add");
        btAddCode.setFont(new java.awt.Font("Dialog", 1, 12));
        btAddCode.addActionListener(new java.awt.event.ActionListener() {

            public void actionPerformed(ActionEvent e) {
                btAddCode_actionPerformed(e);
            }
        });
        lbstCode.setBackground(Color.black);
//    lbstCode.setBounds(new Rectangle(6, 85, 54, 20));
        lbstCode.setBounds(new Rectangle(5, 85, 41, 20));
        lbstCode.setFont(new java.awt.Font("Dialog", 1, 12));
        lbstCode.setForeground(Color.white);
        lbstCode.setAlignment(1);
        lbstCode.setText("Code");
        lbstName.setText("Name");
        lbstName.setForeground(Color.white);
        lbstName.setAlignment(1);
//    lbstName.setBounds(new Rectangle(59, 85, 111, 20));
        lbstName.setBounds(new Rectangle(46, 85, 111, 20));
        lbstName.setFont(new java.awt.Font("Dialog", 1, 12));
        lbstName.setBackground(Color.black);
        lbstLast.setText("Last");
        lbstLast.setForeground(Color.white);
        lbstLast.setAlignment(1);
//    lbstLast.setBounds(new Rectangle(169, 85, 54, 20));
        lbstLast.setBounds(new Rectangle(157, 85, 53, 20));
        lbstLast.setFont(new java.awt.Font("Dialog", 1, 12));
        lbstLast.setBackground(Color.black);
        lbstChange.setText("Change");
        lbstChange.setForeground(Color.white);
        lbstChange.setAlignment(1);
//    lbstChange.setBounds(new Rectangle(222, 85, 54, 20));
        lbstChange.setBounds(new Rectangle(210, 85, 43, 20));
        lbstChange.setFont(new java.awt.Font("Dialog", 1, 12));
        lbstChange.setBackground(Color.black);
        lbstPChg.setText("%Chg");
        lbstPChg.setForeground(Color.white);
        lbstPChg.setAlignment(1);
//    lbstPChg.setBounds(new Rectangle(276, 85, 54, 20));
        lbstPChg.setBounds(new Rectangle(253, 85, 53, 20));
        lbstPChg.setFont(new java.awt.Font("Dialog", 1, 12));
        lbstPChg.setBackground(Color.black);
        lbstHigh.setText("High");
        lbstHigh.setForeground(Color.white);
        lbstHigh.setAlignment(1);
//    lbstHigh.setBounds(new Rectangle(328, 85, 54, 20));
        lbstHigh.setBounds(new Rectangle(306, 85, 43, 20));
        lbstHigh.setFont(new java.awt.Font("Dialog", 1, 12));
        lbstHigh.setBackground(Color.black);
        lbstLow.setText("Low");
        lbstLow.setForeground(Color.white);
        lbstLow.setAlignment(1);
//    lbstLow.setBounds(new Rectangle(381, 85, 57, 20));
        lbstLow.setBounds(new Rectangle(349, 85, 46, 20));
        lbstLow.setFont(new java.awt.Font("Dialog", 1, 12));
        lbstLow.setBackground(Color.black);
        lbstVolume.setText("Vol.");
        lbstVolume.setForeground(Color.white);
        lbstVolume.setAlignment(1);
//    lbstVolume.setBounds(new Rectangle(438, 85, 58, 20));
        lbstVolume.setBounds(new Rectangle(395, 85, 48, 20));
        lbstVolume.setFont(new java.awt.Font("Dialog", 1, 12));
        lbstVolume.setBackground(Color.black);

        lbstTurnover.setText("Turnover");
        lbstTurnover.setAlignment(1);
        lbstTurnover.setFont(new java.awt.Font("Dialog", 1, 12));
        lbstTurnover.setForeground(Color.white);
//    lbstTurnover.setBounds(new Rectangle(442, 60, 53, 23));
        lbstTurnover.setBounds(new Rectangle(442, 85, 54, 20));
        lbstTurnover.setBackground(Color.black);

        chRmCode.setBounds(new Rectangle(5, 59, 76, 20));
        chRmCode.setFont(new java.awt.Font("Dialog", 1, 12));
        chRmCode.addItemListener(new java.awt.event.ItemListener() {

            public void itemStateChanged(ItemEvent e) {
                chRmCode_itemStateChanged(e);
            }
        });
        btRemove.setBounds(new Rectangle(83, 60, 83, 22));
        btRemove.setFont(new java.awt.Font("Dialog", 1, 12));
        btRemove.setLabel("Remove");
        btRemove.addActionListener(new java.awt.event.ActionListener() {

            public void actionPerformed(ActionEvent e) {
                btRemove_actionPerformed(e);
            }
        });
        tfcode.setBackground(Color.white);
        tfcode.setBounds(new Rectangle(5, 35, 76, 23));
        tfcode.setFont(new java.awt.Font("Dialog", 1, 12));
        //add key listener
        tfcode.addKeyListener(this);

        tflowerbar.setBackground(SystemColor.controlText);
        tflowerbar.setBounds(new Rectangle(-1, 260, 500, 19));
        tflowerbar.setFont(new java.awt.Font("Dialog", 1, 12));
        tflowerbar.setForeground(Color.cyan);
        tflowerbar.setText("Finet Stock Watch");
        btEditAlert.setBounds(new Rectangle(169, 60, 89, 22));
        btEditAlert.setFont(new java.awt.Font("Dialog", 1, 12));
        btEditAlert.setLabel("Edit Alert");
        btEditAlert.addActionListener(new java.awt.event.ActionListener() {

            public void actionPerformed(ActionEvent e) {
                btEditAlert_actionPerformed(e);
            }
        });
        double factor = Utilities.GetFactor();
        panelLow.setBackground(Color.orange);
        panelLow.setBounds(new Rectangle(259, 35, 201, 23));
        panelLow.setVisible(false);
        panelLow.setLayout(gridLayout1);
        panelHigh.setBackground(Color.orange);
        panelHigh.setBounds(new Rectangle(259, 60, 201, 21));
        panelHigh.setVisible(false);
        panelHigh.setLayout(gridLayout2);
        lbLowPrice.setFont(new java.awt.Font("Serif", 1, (int) Math.round(12 * factor)));
        lbLowPrice.setText("Lower Price");
        lbHighPrice.setFont(new java.awt.Font("Serif", 1, (int) Math.round(12 * factor)));
        lbHighPrice.setText("Upper Price");
        tfLowPrice.setBackground(Color.white);
        tfLowPrice.setFont(new java.awt.Font("Dialog", 1, (int) Math.round(12 * factor)));
        tfHighPrice.setBackground(Color.white);
        tfHighPrice.setFont(new java.awt.Font("Dialog", 1, (int) Math.round(12 * factor)));
        btDone.setBackground(SystemColor.control);
        btDone.setLabel("Done");
        btDone.setFont(new java.awt.Font("Dialog", 1, (int) Math.round(12 * factor)));
        btDone.addActionListener(new java.awt.event.ActionListener() {

            public void actionPerformed(ActionEvent e) {
                btDone_actionPerformed(e);
            }
        });
        btClear.setBackground(SystemColor.control);
        btClear.setLabel("Clear");
        btClear.setFont(new java.awt.Font("Dialog", 1, (int) Math.round(12 * factor)));
        btClear.addActionListener(new java.awt.event.ActionListener() {

            public void actionPerformed(ActionEvent e) {
                btClear_actionPerformed(e);
            }
        });
        btSave.setBounds(new Rectangle(169, 35, 89, 23));
        btSave.setLabel("Save");
        btSave.setFont(new java.awt.Font("Dialog", 1, 12));
        btSave.setEnabled(false);
        btSave.addActionListener(new java.awt.event.ActionListener() {

            public void actionPerformed(ActionEvent e) {
                btSave_actionPerformed(e);
            }
        });
        cbSound.setBackground(Color.black);
        cbSound.setBounds(new Rectangle(411, 0, 20, 20));
        cbSound.setForeground(Color.cyan);
        cbSound.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                cbSound_itemStateChanged(e);
            }
        });
//    cbSound.setLabel("Sound Alert");
//    cbSound.setFont(new java.awt.Font("Dialog", 0, 11));

        lblSound.setBounds(new Rectangle(431, 0, 79, 20));
        lblSound.setBackground(Color.black);
        lblSound.setForeground(Color.cyan);
        lblSound.setText("Sound Alert");
        lblSound.setFont(new java.awt.Font("Dialog", 0, 11));

        this.add(panel1, null);
        panel1.add(lbstTitle, null);
        panel1.add(cbSound, null);
        panel1.add(lblSound, null);
        this.add(btAddCode, null);
        this.add(chRmCode, null);
        this.add(btRemove, null);
        this.add(lbstCode, null);
        this.add(lbstName, null);
        this.add(lbstLast, null);
        this.add(lbstChange, null);
        this.add(lbstPChg, null);
        this.add(lbstHigh, null);
        this.add(lbstLow, null);
        this.add(lbstTurnover, null);
        this.add(lbstVolume, null);
        this.add(tfcode, null);
        this.add(btEditAlert, null);
        this.add(btSave, null);
        this.add(panelLow, null);
        panelLow.add(lbHighPrice, null);
        panelLow.add(tfHighPrice, null);
        panelLow.add(btClear, null);
        this.add(panelHigh, null);
        panelHigh.add(lbLowPrice, null);
        panelHigh.add(tfLowPrice, null);
        panelHigh.add(btDone, null);
        this.add(stockTable, null);
        this.add(tflowerbar, null);
    }

    void SetSource(StockWatchSource sws) {
        stockwatchSource = sws;
    }

    void SetEngine(StockWatchEngine swe) {
        stockwatchEngine = swe;
    }

    public void UpdateRmCodeList() throws Exception {
        this.chRmCode.removeAll();

        for (int i = 0; i < stockwatchSource.MyStock.size(); i++) {
            Item cItem = (Item) stockwatchSource.MyStock.elementAt(i);
            chRmCode.add(cItem.Code);
        }

    }

    void UpdateInformation() throws Exception {

        //Check the Codes
        psound = cbSound.getState();
        if (stockwatchEngine.Alert == true) {
            tflowerbar.setText(lbArray[18][language]);
            if (alertClip != null && psound == true) alertClip.play();
        } else {
            tflowerbar.setText(lbArray[17][language]);
            if (alertClip != null) alertClip.stop();
        }

        // get back all selected item.
        int NumberOfStocks = stockwatchSource.MyStock.size();
        Item currentItems[] = new Item[NumberOfStocks];
        for (int i = 0; i < NumberOfStocks; i++) {
            currentItems[i] = (Item) stockwatchSource.MyStock.elementAt(i);// .get(i);
        }


        for (int i = 0; i < maxcount; i++) {
            if (i < NumberOfStocks) {
                stockTable.lbItem[0][i].setText(currentItems[i].Code);
                if (language == FinetExpress.constEnglish)
                    stockTable.lbItem[1][i].setText(currentItems[i].Name);
                else
                    stockTable.lbItem[1][i].setText(currentItems[i].cName);
                stockTable.lbItem[1][i].setAlignment(0);
                stockTable.lbItem[2][i].setText(currentItems[i].Last);
                stockTable.lbItem[3][i].setText(currentItems[i].Change);
                stockTable.lbItem[4][i].setText(currentItems[i].PCChg);
                stockTable.lbItem[5][i].setText(currentItems[i].High);
                stockTable.lbItem[6][i].setText(currentItems[i].Low);
                stockTable.lbItem[7][i].setText(FormatData.FormatString2(currentItems[i].Volume, 1));
                stockTable.lbItem[8][i].setText(FormatData.FormatString2(currentItems[i].Turnover, 1));

                // set the background to red color if the code need Alert.
                if (currentItems[i].Alert == true) {
                    //System.out.println("Alert this Item " + i);
                    stockTable.lbItem[0][i].setBackground(Color.red);
                    stockTable.lbItem[2][i].setBackground(Color.red);
                } else {
                    //System.out.println("[" + currentItems[i].AlertHigh + "]");
                }
            } else {
                stockTable.lbItem[0][i].setText("");
                stockTable.lbItem[1][i].setText("");
                stockTable.lbItem[1][i].setAlignment(0);
                stockTable.lbItem[2][i].setText("");
                stockTable.lbItem[3][i].setText("");
                stockTable.lbItem[4][i].setText("");
                stockTable.lbItem[5][i].setText("");
                stockTable.lbItem[6][i].setText("");
                stockTable.lbItem[7][i].setText("");
                stockTable.lbItem[8][i].setText("");
            }
//      toplistTable.lbItem[7][i].setText(FormatData.FormatString(currentItems[i].Turnover,5));
        }


    }

    void btAddCode_actionPerformed(ActionEvent e) {
        btSave.setEnabled(true);
        try {
            String aCode = this.tfcode.getText();
            if (aCode.length() > 4) return;
            if (chRmCode.getItemCount() >= maxcount) {
                tfcode.setText("");
                Report("Warning! Max 8 stocks can store.");
                return;
            }
            int tempi = Integer.parseInt(aCode);
            aCode = "0000" + aCode;
            aCode = aCode.substring(aCode.length() - 4);

            for (int i = 0; i < chRmCode.getItemCount(); i++) {
                if (Integer.parseInt(aCode) == Integer.parseInt(chRmCode.getItem(i))) {
                    tfcode.setText("");
                    return;
                }
            }
            if (stockwatchEngine.AddStock(Integer.parseInt(aCode)))
                this.chRmCode.add(aCode);
            tfcode.setText("");

        } catch (NumberFormatException ex) {
            tfcode.setText("");
        } catch (Exception ex) {
            ex.printStackTrace();
            tfcode.setText("");
        }
    }

    void btRemove_actionPerformed(ActionEvent e) {
        btSave.setEnabled(true);
        try {
            String rmCode = this.chRmCode.getSelectedItem();
            if (rmCode == null) return;
            stockwatchEngine.RemoveStock(Integer.parseInt(rmCode));
            this.chRmCode.remove(rmCode);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    void btEditAlert_actionPerformed(ActionEvent e) {
        //  this.chRmCode
        //   this.tfLowPrie
        try {
            String rmCode = this.chRmCode.getSelectedItem();
            if (rmCode == null) return;
            this.panelLow.setVisible(true);
            this.panelHigh.setVisible(true);
            EditAlert();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void EditAlert() throws Exception {
        String rmCode = this.chRmCode.getSelectedItem();
        if (rmCode == null) return;
        int NumberOfStocks = stockwatchSource.MyStock.size();
// find the select item in MyStock list ///////////////////////////////
        Item selectedItem = new Item();
        ;
        for (int i = 0; i < NumberOfStocks; i++) {
            selectedItem = (Item) stockwatchSource.MyStock.elementAt(i);// .get(i);
            if (selectedItem.Code.equals(rmCode)) break;
        }
/////////////////////////////////////////////////////////////////////////////
        this.tfLowPrice.setText(selectedItem.AlertLow);
        this.tfHighPrice.setText(selectedItem.AlertHigh);
    }

    void btDone_actionPerformed(ActionEvent e) {
        btSave.setEnabled(true);
        try {
            String rmCode = this.chRmCode.getSelectedItem();
            if (rmCode == null) return;

            int NumberOfStocks = stockwatchSource.MyStock.size();
            // find the select item in MyStock list //////////////////////////////////
            Item selectedItem = new Item();
            ;
            for (int i = 0; i < NumberOfStocks; i++) {
                selectedItem = (Item) stockwatchSource.MyStock.elementAt(i);// .get(i);
                if (selectedItem.Code.equals(rmCode)) break;
            }
            //////////////////////////////////////////////////////////////////////////
            selectedItem.AlertHigh = tfHighPrice.getText();
            selectedItem.AlertLow = tfLowPrice.getText();
            this.panelHigh.setVisible(false);
            this.panelLow.setVisible(false);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    void chRmCode_itemStateChanged(ItemEvent e) {
        try {
            this.EditAlert();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    void btClear_actionPerformed(ActionEvent e) {
        btSave.setEnabled(true);
        try {
            this.tfHighPrice.setText("");
            this.tfLowPrice.setText("");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    void btSave_actionPerformed(ActionEvent e) {
        btSave.setEnabled(false);
        try {
            Report("Saving the profile...........");
            this.stockwatchEngine.SaveUserProfile();
            Report("Done");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    // this function is used to report Server, state = 0 (RUNING) state = 1 (down a moment)
    public void ReportServerState(int sstate) {
        // server OK
        if (sstate == 0) {
            Report("Server State: Running");
        }
        // server Down
        else if (sstate == 1) {
            Report("Server State: Server busy and may be down for a moment");
        }
    }

    void Report(String rp) {
        finetExpress.showStatus(rp);
    }

    public void SetMainApplet(FinetExpress fe) throws Exception {
        this.finetExpress = fe;
    }

    public void SetLanguage(int tlanguage) throws Exception {
        language = tlanguage;

        if (language == FinetExpress.constEnglish) {
            this.lbstTitle.setText(lbArray[0][0]);
            this.btAddCode.setLabel(lbArray[1][0]);
            this.btSave.setLabel(lbArray[2][0]);
            this.btRemove.setLabel(lbArray[3][0]);
            this.btEditAlert.setLabel(lbArray[4][0]);
            this.lbstCode.setText(lbArray[5][0]);    //,{"Code",""}
            this.lbstName.setText(lbArray[6][0]);    //,{"Name",""}
            this.lbstLast.setText(lbArray[7][0]);    //,{"Last",""}
            this.lbstChange.setText(lbArray[8][0]);  //,{"Change",""}
            this.lbstPChg.setText(lbArray[9][0]);    //,{"%Chg",""}
            this.lbstHigh.setText(lbArray[10][0]);   //,{"High",""}
            this.lbstLow.setText(lbArray[11][0]);    //,{"Low",""}
            this.lbstVolume.setText(lbArray[12][0]); //,{"Volume",""}
            this.lbstTurnover.setText(lbArray[20][0]); //,{"Volume",""}
            this.lbLowPrice.setText(lbArray[13][0]); //,{"Lower price","下限"}
            this.lbHighPrice.setText(lbArray[14][0]);// ,{"Upper price","上限"}
            this.btClear.setLabel(lbArray[15][0]);//  ,{"Clear","清除"}
            this.btDone.setLabel(lbArray[16][0]);//  ,{"Done","確定"}
//        this.tflowerbar.setLabel(lbArray[18][0]); // price alert reached
//        this.cbSound.setLabel(lbArray[19][0]); // sound alert
            this.lblSound.setText(lbArray[19][0]); // sound alert

        } else if (language == FinetExpress.constChinese) {
            this.lbstTitle.setText(lbArray[0][1]);
            this.btAddCode.setLabel(lbArray[1][1]);
            this.btSave.setLabel(lbArray[2][1]);
            this.btRemove.setLabel(lbArray[3][1]);
            this.btEditAlert.setLabel(lbArray[4][1]);
            this.lbstCode.setText(lbArray[5][1]);    //,{"Code",""}
            this.lbstName.setText(lbArray[6][1]);    //,{"Name",""}
            this.lbstLast.setText(lbArray[7][1]);    //,{"Last",""}
            this.lbstChange.setText(lbArray[8][1]);  //,{"Change",""}
            this.lbstPChg.setText(lbArray[9][1]);    //,{"%Chg",""}
            this.lbstHigh.setText(lbArray[10][1]);   //,{"High",""}
            this.lbstLow.setText(lbArray[11][1]);    //,{"Low",""}
            this.lbstVolume.setText(lbArray[12][1]); //,{"Volume",""}
            this.lbLowPrice.setText(lbArray[13][1]); //,{"Lower price","下限"}
            this.lbHighPrice.setText(lbArray[14][1]);// ,{"Upper price","上限"}
            this.btClear.setLabel(lbArray[15][1]);//  ,{"Clear","清除"}
            this.btDone.setLabel(lbArray[16][1]);//  ,{"Done","確定"}
//        this.tflowerbar.setLabel(lbArray[18][1]); // price alert reached
//        this.cbSound.setLabel(lbArray[19][1]); //Alert sound
            this.lblSound.setText(lbArray[19][1]); //Alert sound
            this.lbstTurnover.setText(lbArray[20][1]); //,{"Volume",""}

        }
        this.UpdateInformation();
    }

    void cbSound_itemStateChanged(ItemEvent e) {
        //System.out.print(cbSound.getState());
        //Set the play sound flat to ture or false
        psound = cbSound.getState();
    }


    public void keyTyped(KeyEvent e) {
        //System.out.println("keytyped" + e);
    }

    /*** Invoked when a key has been pressed.*/
    public void keyPressed(KeyEvent e) {
        //System.out.println("keyevent" + e);
    }

    /*** Invoked when a key has been released.*/

    public boolean getState() {
        return blnState;
    }

    public void Enable() {
        blnState = true;
        btSave.setEnabled(false);
        stockwatchEngine.refreshData();

    }

    public void Disable() {
        blnState = false;
        if (btSave.isEnabled()) {
            this.btSave_actionPerformed(null);
            System.out.println("Saving stock watch");
        }
    }


    public void keyReleased(KeyEvent e) {
        // when enter is pressed
        try {
            if (e.getKeyCode() == 10) {
                this.btAddCode_actionPerformed(null);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
