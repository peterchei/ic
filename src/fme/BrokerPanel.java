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
import java.awt.event.ItemEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Enumeration;
import java.util.StringTokenizer;
import java.util.Vector;

public class BrokerPanel extends Panel implements KeyListener {
    private final String lbArray[][] = {{"Broker Search", "\u7d93\u7d00\u641c\u5c0b"}
                                        , {"Broker ID", "\u7d93\u7d00\u865f\u78bc"}
                                        , {"Broker Bid", "\u7d93\u7d00\u8cb7\u76e4"}
                                        , {"Broker Ask", "\u7d93\u7d00\u8ce3\u76e4"}
                                        , {"Go", "\u8f38\u5165"}
    };
    int language;
    String stBroker;
    boolean blnSameBroker = false;  // check if updateinformation is triggered from lstRelatedBrokers;
    public Vector CodeNumber = new Vector();
    Panel panel1 = new Panel();

    Label lbstBrokerID = new Label();
    TextField tfBroker = new TextField();
    Button btGo = new Button();
    Label lbstTitle = new Label();
    List lstBid = new List();
    List lstAsk = new List();
    List lstCBid = new List();
    List lstCAsk = new List();
    Label lbstSBrokers = new Label();
    Label lbstBBrokers = new Label();

    FinetExpress finetExpress = null;
    BrokerSource brokerSource = null;
    BrokerEngine brokerEngine = null;

    Label lbBrokerID = new Label();
    Label lbBrokerName = new Label();
    Label lbCBrokerName = new Label();

    Choice choBrokerName = new Choice();
    Choice choCBrokerName = new Choice();
    List lstRelatedBrokers = new List();

    public BrokerPanel() {
        try {
            jbInit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void jbInit() throws Exception {
        this.setLayout(null);
        lstBid.setBackground(Color.white);
        lstAsk.setBackground(Color.white);
        lstCBid.setBackground(Color.white);
        lstCAsk.setBackground(Color.white);
        panel1.setBackground(new Color(0, 132, 231));
        panel1.setBounds(new Rectangle(-1, 0, 498, 44));
        panel1.setLayout(null);
        lbstBrokerID.setBounds(new Rectangle(276, 4, 91, 23));
        lbstBrokerID.setFont(new java.awt.Font("Dialog", 1, 14));
        lbstBrokerID.setForeground(Color.white);
        lbstBrokerID.setText("Broker ID");
        tfBroker.setBackground(Color.white);
        tfBroker.setBounds(new Rectangle(374, 5, 67, 17));
        tfBroker.setFont(new java.awt.Font("Dialog", 1, 12));
        this.tfBroker.addKeyListener(this);

        btGo.setBackground(SystemColor.control);
        btGo.setBounds(new Rectangle(444, 4, 46, 18));
        btGo.setLabel("GO");
        btGo.setFont(new java.awt.Font("Dialog", 1, 14));
        btGo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btGo_actionPerformed(e);
            }
        });
        lbstTitle.setBounds(new Rectangle(4, 9, 114, 24));
        lbstTitle.setFont(new java.awt.Font("Dialog", 1, 16));
        lbstTitle.setForeground(Color.white);
        lbstTitle.setText("Broker Search");

        java.awt.event.ActionListener lstAskBid_Listener = new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                lstBidAsk_actionPerformed(e);
            }
        };

        lstBid.setBounds(new Rectangle(166, 74, 155, 187));
        lstBid.setForeground(Color.black);
        lstBid.addActionListener(lstAskBid_Listener);
        lstBid.setFont(new java.awt.Font("Dialog", 0, 11));

        lstCBid.setBounds(new Rectangle(166, 74, 155, 187));
        lstCBid.setForeground(Color.black);
        lstCBid.setFont(new java.awt.Font("Dialog", 0, 11));
        lstCBid.addActionListener(lstAskBid_Listener);

        lstAsk.setBounds(new Rectangle(329, 74, 155, 187));
        lstAsk.setForeground(Color.black);
        lstAsk.addActionListener(lstAskBid_Listener);
        lstAsk.setFont(new java.awt.Font("Dialog", 0, 11));

        lstCAsk.setBounds(new Rectangle(329, 74, 155, 187));
        lstCAsk.setForeground(Color.black);
        lstCAsk.addActionListener(lstAskBid_Listener);
        lstCAsk.setFont(new java.awt.Font("Dialog", 0, 11));

        lbstSBrokers.setBackground(Color.red);
        lbstSBrokers.setBounds(new Rectangle(330, 48, 155, 26));
        lbstSBrokers.setFont(new java.awt.Font("Serif", 1, 16));
        lbstSBrokers.setForeground(Color.white);
        lbstSBrokers.setAlignment(1);
        lbstSBrokers.setText("Broker Ask");
        lbstBBrokers.setBackground(Color.blue);
        lbstBBrokers.setBounds(new Rectangle(166, 48, 155, 26));
        lbstBBrokers.setFont(new java.awt.Font("Serif", 1, 16));
        lbstBBrokers.setForeground(Color.white);
        lbstBBrokers.setAlignment(1);
        lbstBBrokers.setText("Broker Bid");
        this.setBackground(SystemColor.info);
        lbBrokerID.setFont(new java.awt.Font("Dialog", 1, 12));
        lbBrokerID.setForeground(Color.black);
        lbBrokerID.setText("");
        lbBrokerID.setBounds(new Rectangle(14, 49, 33, 19));
        lbBrokerName.setFont(new java.awt.Font("Dialog", 1, 12));
        lbBrokerName.setForeground(Color.black);
        lbBrokerName.setText("");
        lbBrokerName.setBounds(new Rectangle(14, 64, 134, 27));
        lbCBrokerName.setFont(new java.awt.Font("Dialog", 1, 12));
        lbCBrokerName.setForeground(Color.black);
        lbCBrokerName.setText("");
        lbCBrokerName.setBounds(new Rectangle(14, 64, 134, 27));

        java.awt.event.ItemListener BrokerName_Listener = new java.awt.event.ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                choBrokerName_itemStateChanged(e);
            }
        };

        choBrokerName.setBackground(Color.white);
        choBrokerName.setBounds(new Rectangle(14, 100, 130, 26));
        choBrokerName.addItemListener(BrokerName_Listener);
        choBrokerName.setVisible(true);
        choBrokerName.setFont(new java.awt.Font("Dialog", 0, 10));

        choCBrokerName.setBackground(choBrokerName.getBackground());
        choCBrokerName.setBounds(new Rectangle(14, 100, 134, 26));
        choCBrokerName.addItemListener(BrokerName_Listener);
        choCBrokerName.setVisible(true);
        choCBrokerName.setFont(new java.awt.Font("Dialog", 0, 10));

        lstRelatedBrokers.setBackground(SystemColor.controlLtHighlight);
        lstRelatedBrokers.setBounds(new Rectangle(15, 130, 130, 135));
        lstRelatedBrokers.setFont(new java.awt.Font("Dialog", 0, 10));
        lstRelatedBrokers.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                lstRelatedBrokers_itemStateChanged(e);
            }
        });
        this.add(panel1, null);
        panel1.add(lbstTitle, null);
        panel1.add(tfBroker, null);
        panel1.add(btGo, null);
        panel1.add(lbstBrokerID, null);
        this.add(lbstBBrokers, null);
        this.add(lbstSBrokers, null);
        this.add(lstBid, null);
        this.add(lstAsk, null);
        this.add(lstCBid, null);
        this.add(lstCAsk, null);
//    this.add(choCBrokerName, null);
//    this.add(choBrokerName, null);
        this.add(lstRelatedBrokers, null);
        this.add(lbBrokerID, null);
        this.add(lbCBrokerName, null);
        this.add(lbBrokerName, null);
    }

    public void SetMainApplet(FinetExpress fe) {
        this.finetExpress = fe;
    }

    public void SetEngine(BrokerEngine Te) {
        brokerEngine = Te;
    }

    public void setCaptionForeground(Color c) {
        panel1.setForeground(c);
    }

    public void setCaptionBackground(Color c) {
        panel1.setBackground(c);
    }

    public void setInfoBackground(Color c) {
        this.setBackground(c);
    }

    void btGo_actionPerformed(ActionEvent e) {
        //Check the code in the text field is correct or not
        try {
            int ccode = Integer.parseInt(tfBroker.getText());
            if (ccode > 9999 || ccode < 0) {
                tfBroker.setText("");
                return;
            }
            String cCode = this.tfBroker.getText();

            if (cCode.length() > 4 || cCode.length() == 0) {
                tfBroker.setText("");
                return;
            }

            brokerEngine.viewBroker(cCode);

            tfBroker.setText("");
        } catch (Exception ex) {
            tfBroker.setText("");
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

    void Report(String rp) {
        finetExpress.showStatus(rp);
    }

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

    public void SetSource(BrokerSource ptt) {
        brokerSource = ptt;
    }

    public void UpdateInformation() throws Exception {
        lbBrokerID.setText(brokerSource.Code);
        lbBrokerName.setText(brokerSource.brokerName);
        lbCBrokerName.setText(brokerSource.cBrokerName);

        lstBid.removeAll();
        lstAsk.removeAll();
        lstCBid.removeAll();
        lstCAsk.removeAll();

        StringTokenizer token;
        String sItem;
        for (Enumeration e = brokerSource.BrokerBid.elements(); e.hasMoreElements();) {
            token = new StringTokenizer(e.nextElement().toString(), "|");
            sItem = token.nextToken();
            if (token.hasMoreTokens()) {
                lstBid.add(sItem + " " + token.nextToken());
                lstCBid.add(sItem + " " + token.nextToken());
            } else {
                lstBid.add(sItem);
                lstCBid.add(sItem);
            }
        }
        for (Enumeration e = brokerSource.BrokerAsk.elements(); e.hasMoreElements();) {
            token = new StringTokenizer(e.nextElement().toString(), "|");
            sItem = token.nextToken();
            if (token.hasMoreTokens()) {
                lstAsk.add(sItem + " " + token.nextToken());
                lstCAsk.add(sItem + " " + token.nextToken());
            } else {
                lstAsk.add(sItem);
                lstCAsk.add(sItem);
            }
        }
        if (choBrokerName.getItemCount() == 0 || choCBrokerName.getItemCount() == 0)
            this.setComboBox();

        if (this.brokerSource.Code != null) {
            String sTemp = this.brokerSource.Code.substring(0, 3) + "0";
            int CodeIndex = getCodeIndex(sTemp);
            this.choBrokerName.select(CodeIndex);
            this.choCBrokerName.select(CodeIndex);
        }

        if (blnSameBroker == false) {
            lstRelatedBrokers.removeAll();
            for (Enumeration e = brokerSource.RelatedBrokers.elements(); e.hasMoreElements();)
                lstRelatedBrokers.add(e.nextElement().toString());
        }
        blnSameBroker = false;

    }

    public void setComboBox() {
        String sItem;
        choBrokerName.removeAll();
        choCBrokerName.removeAll();
        for (Enumeration e = brokerEngine.teletextSource.BrokerQueue.elements(); e.hasMoreElements();) {
            sItem = e.nextElement().toString();
            sItem = "000" + sItem;
            sItem = sItem.substring(sItem.length() - 3) + "0";
            addItem(e.nextElement().toString(), sItem);
            addItem(e.nextElement().toString(), sItem);
        }
        this.add(choCBrokerName, null);
        this.add(choBrokerName, null);
        try {
            this.SetLanguage(language);
        } catch (Exception e) {
        }
    }

    public void SetLanguage(int tlanguage) throws Exception {
        language = tlanguage;

        if (language == FinetExpress.constEnglish) {
            this.choCBrokerName.setVisible(false);
            this.choBrokerName.setVisible(true);
            this.lstCAsk.setVisible(false);
            this.lstAsk.setVisible(true);
            this.lstCBid.setVisible(false);
            this.lstBid.setVisible(true);
            this.lbstTitle.setText(lbArray[0][0]);
            this.lbstBrokerID.setText(lbArray[1][0]);
            this.lbstBBrokers.setText(lbArray[2][0]);
            this.lbstSBrokers.setText(lbArray[3][0]);
            this.btGo.setLabel(lbArray[4][0]);
            this.lbBrokerName.setVisible(true);
            this.lbCBrokerName.setVisible(false);

        } else if (language == FinetExpress.constChinese) {
            this.choCBrokerName.setVisible(true);
            this.choBrokerName.setVisible(false);
            this.lstCAsk.setVisible(true);
            this.lstAsk.setVisible(false);
            this.lstCBid.setVisible(true);
            this.lstBid.setVisible(false);
            this.lbstTitle.setText(lbArray[0][1]);
            this.lbstBrokerID.setText(lbArray[1][1]);
            this.lbstBBrokers.setText(lbArray[2][1]);
            this.lbstSBrokers.setText(lbArray[3][1]);
            this.btGo.setLabel(lbArray[4][1]);
            this.lbBrokerName.setVisible(false);
            this.lbCBrokerName.setVisible(true);
        }
//     this.UpdateInformation();
    }

    void choBrokerName_itemStateChanged(ItemEvent e) {
        Choice choTemp = (Choice) e.getSource();
        int index = choTemp.getSelectedIndex();
        String sCode = CodeNumber.elementAt(index).toString();
        brokerEngine.viewBroker(sCode);
    }

    void lstRelatedBrokers_itemStateChanged(ItemEvent e) {
        blnSameBroker = true;
        int index = lstRelatedBrokers.getSelectedIndex();
        String sCode = lstRelatedBrokers.getItem(index).trim();
        brokerEngine.viewBroker(sCode);
    }

    void lstBidAsk_actionPerformed(ActionEvent e) {
        try {
            List tempList = (List) e.getSource();
            int index = tempList.getSelectedIndex();
            String sCode = tempList.getItem(index).substring(0, 4);
            if (sCode.charAt(0) == '-' || sCode.charAt(0) == '+')
                return;
            this.brokerEngine.teletextSource.Code = sCode;
            finetExpress.btTeleText_actionPerformed(null);
        } catch (Exception ex) {
            finetExpress.showStatus("FinetExpress::btTeleText_actionPerformed " + ex.getMessage());
        }
    }

    public void addItem(String Item, String Code) {
        choBrokerName.addItem(Item);
        this.CodeNumber.addElement(Code);
    }

    public void removeAll() {
        super.removeAll();
        this.CodeNumber.removeAllElements();
    }

    public int getCodeIndex(String Code) {
        return this.CodeNumber.indexOf(Code);
    }
}