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
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.net.ConnectException;
import java.util.StringTokenizer;

public class ChartFrame extends Panel implements KeyListener {

    private final String lbArray[][] = {{"Stock ID", "\u80a1\u7968\u865f\u78bc"}
                                        , {"Stock Chart", "\u80a1\u7968\u5716"}
                                        , {"Go", "\u8f38\u5165"}};

    ChartScreen chartScreen1 = new ChartScreen("0001");
    private String ServerAddress = "";
    private int language;
    Panel panel1 = new Panel();
    Label lbTitle = new Label();
    Label lbCode = new Label();
    TextField tfStock = new TextField();
    Button btChart = new Button();
    Label label1 = new Label();

    public ChartFrame() {
        try {
            jbInit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setCaptionBackground(Color c) {
        panel1.setBackground(c);
        lbCode.setBackground(c);
        lbTitle.setBackground(c);
        label1.setBackground(c);
    }

    public void setInfoBackground(Color c) {
        this.setBackground(c);
    }

    private void jbInit() throws Exception {
//    this.setSize(new Dimension(500, 281));
        this.setSize(500, 281);
        this.setLayout(null);
        chartScreen1.setBounds(new Rectangle(0, 40, 500, 241));

        panel1.setBackground(Color.black);
        panel1.setBounds(new Rectangle(1, 0, 500, 40));
        panel1.setLayout(null);
        lbTitle.setBackground(Color.black);
        lbTitle.setBounds(new Rectangle(0, 16, 164, 24));
        lbTitle.setFont(new java.awt.Font("Dialog", 1, 20));
        lbTitle.setForeground(Color.white);
        lbTitle.setText("Stock Chart");
        lbCode.setBackground(Color.black);
        lbCode.setBounds(new Rectangle(170, 20, 271, 18));
        lbCode.setFont(new java.awt.Font("Serif", 1, 14));
        lbCode.setForeground(Color.white);
        lbCode.setText("Code and Company");

        tfStock.setBackground(Color.white);
        tfStock.setBounds(new Rectangle(392, 1, 60, 17));
        tfStock.setFont(new java.awt.Font("Dialog", 0, 10));
        tfStock.addKeyListener(this);
        btChart.setBackground(SystemColor.control);
        btChart.setBounds(new Rectangle(453, 2, 44, 17));
        btChart.setLabel("Go");
        btChart.setFont(new java.awt.Font("Dialog", 1, 14));
        btChart.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btChart_actionPerformed(e);
            }
        });
        label1.setBounds(new Rectangle(325, 1, 61, 19));
        label1.setFont(new java.awt.Font("Dialog", 1, 12));
        label1.setForeground(Color.white);
        label1.setAlignment(2);
        label1.setText("Stock ID");
        this.add(chartScreen1);
        this.add(panel1, null);
        panel1.add(lbTitle, null);
        panel1.add(btChart, null);
        panel1.add(lbCode, null);
        panel1.add(tfStock, null);
        panel1.add(label1, null);
    }

    void btChart_actionPerformed(ActionEvent e) {
        try {
            int ccode = Integer.parseInt(tfStock.getText());
            if (ccode > 9999 || ccode < 0) {
                tfStock.setText("");
                return;
            }
        } catch (Exception ee) {
            tfStock.setText("");
            return;
        }

        String stStock = this.tfStock.getText();
        if (stStock.length() > 4 || stStock.length() == 0) {
            tfStock.setText("");
            return;
        }
        if (GetGeneralInfo(Integer.parseInt(tfStock.getText()))) {
            this.chartScreen1.updateCode(stStock);
            tfStock.setText("");
        } else {
            tfStock.setText("");
        }
    }

    private boolean GetGeneralInfo(int iCode) {
        try {
            String srcAddr = "0000" + iCode + ".s";
            srcAddr = srcAddr.substring(srcAddr.length() - 6);
/*      srcAddr = "http://" + ServerAddress +"/stock/"+srcAddr;

      URL Finet = new URL(srcAddr);

      URLConnection FinetConnection =  Finet.openConnection();

      BufferedReader  DS = new BufferedReader(new InputStreamReader(FinetConnection.getInputStream()));

      String RawData = DS.readLine();
*/
            String RawData = RealTimeStock.getRawString(srcAddr, FinetExpress.passcode);
            StringTokenizer tokens = new StringTokenizer(RawData);
            tokens.nextToken(",");
            String tempName = tokens.nextToken(",");

            if (tempName != null) {
                String ccodename = "0000" + iCode;
                ccodename = ccodename.substring(ccodename.length() - 4) + " " + tempName;
                lbCode.setText(ccodename);
            }
//      DS.close();
        } catch (ConnectException exception) {
            return false;
        } catch (IOException exception) {
            return false;
        } catch (NullPointerException exception) {
            return false;
        } catch (Exception exception) {
            return false;
        }
        return true;
    }

    public void SetServerAddress(String addr) {
        this.ServerAddress = addr;
    }

    public void keyTyped(KeyEvent e) {
    }

    public void keyPressed(KeyEvent e) {
    }

    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == 10) {
            btChart_actionPerformed(null);
        }
    }

    public void SetLanguage(int tlanguage) throws Exception {
        language = tlanguage;

        if (language == FinetExpress.constEnglish) {
            this.label1.setText(lbArray[0][0]); //,{"Sell Brokers",""}};
            this.lbTitle.setText(lbArray[1][0]); //,{"Sell Brokers",""}};
            this.btChart.setLabel(lbArray[2][0]);
        } else if (language == FinetExpress.constChinese) {
            this.label1.setText(lbArray[0][1]);   //Normial
            this.lbTitle.setText(lbArray[1][1]);   //Normial
            this.btChart.setLabel(lbArray[2][1]);
        }
    }

}