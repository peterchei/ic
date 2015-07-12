package teletext.apps.ui;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import java.awt.*;


public class DealInputPanel extends JPanel {
    BorderLayout borderLayout1 = new BorderLayout();
    TitledBorder titledBorder1;
    Border border1;
    JPanel jPanel1 = new JPanel();
    JButton jbQuote = new JButton();
    JTextField jTextField2 = new JTextField();
    Border border2;
    Border border3;
    JLabel lbCode = new JLabel();
    JLabel lbName = new JLabel();
    JTextField jTextField3 = new JTextField();
    JTextField jTextField4 = new JTextField();
    JTextField jTextField5 = new JTextField();
    JTextField jTextField6 = new JTextField();
    Border border4;
    TitledBorder titledBorder2;
    JLabel lbQuantity = new JLabel();
    JLabel lbPrice = new JLabel();
    JLabel lbAmount = new JLabel();
    JButton jbBuy = new JButton();
    JButton jbCanel = new JButton();

    public DealInputPanel() {
        try {
            jbInit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void jbInit() throws Exception {
        titledBorder1 = new TitledBorder("");
        border1 = BorderFactory.createCompoundBorder(BorderFactory.createEtchedBorder(Color.white, new Color(148, 145, 140)), BorderFactory.createEmptyBorder(0, 5, 0, 5));
        border2 = BorderFactory.createLineBorder(Color.white, 1);
        border3 = BorderFactory.createCompoundBorder(BorderFactory.createEtchedBorder(Color.white, new Color(148, 145, 140)), BorderFactory.createEmptyBorder(0, 5, 0, 5));
        border4 = BorderFactory.createLineBorder(Color.black, 2);
        titledBorder2 = new TitledBorder(BorderFactory.createLineBorder(Color.black, 1), "Deal Input");
        this.setBackground(FConfig.ChatBackground);
        this.setOpaque(true);
        this.setLayout(borderLayout1);
        jbQuote.setBackground(FConfig.ScreenBackground);
        jbQuote.setBounds(new Rectangle(205, 29, 47, 20));
        jbQuote.setFont(new java.awt.Font("Arial", 0, 12));
        jbQuote.setBorder(border3);
        jbQuote.setActionCommand("jButton1");
        jbQuote.setBorderPainted(true);
        jbQuote.setText("Quote");
        jTextField2.setEnabled(true);
        jTextField2.setBorder(BorderFactory.createLineBorder(Color.black));
        jTextField2.setSelectionStart(0);
        jTextField2.setText("");
        jTextField2.setColumns(5);
        jTextField2.setBounds(new Rectangle(130, 119, 68, 21));
        lbCode.setForeground(Color.black);
        lbCode.setText("Code");
        lbCode.setBounds(new Rectangle(64, 29, 44, 18));
        jPanel1.setLayout(null);
        lbName.setToolTipText("");
        lbName.setText("Name");
        lbName.setBounds(new Rectangle(64, 58, 51, 18));
        jTextField3.setBorder(BorderFactory.createLineBorder(Color.black));
        jTextField3.setText("");
        jTextField3.setBounds(new Rectangle(130, 150, 100, 21));
        jTextField4.setBounds(new Rectangle(130, 88, 67, 21));
        jTextField4.setColumns(5);
        jTextField4.setText("");
        jTextField4.setSelectionStart(0);
        jTextField4.setBorder(BorderFactory.createLineBorder(Color.black));
        jTextField4.setEnabled(true);
        jTextField5.setEnabled(true);
        jTextField5.setBorder(BorderFactory.createLineBorder(Color.black));
        jTextField5.setSelectionStart(0);
        jTextField5.setText("");
        jTextField5.setColumns(5);
        jTextField5.setBounds(new Rectangle(130, 57, 181, 21));
        jTextField6.setEnabled(true);
        jTextField6.setBorder(BorderFactory.createLineBorder(Color.black));
        jTextField6.setSelectionStart(0);
        jTextField6.setText("");
        jTextField6.setColumns(5);
        jTextField6.setBounds(new Rectangle(130, 28, 68, 21));
        jPanel1.setBackground(FConfig.ScreenBackground);
        jPanel1.setBorder(titledBorder2);
        jPanel1.setDoubleBuffered(true);
        lbQuantity.setBounds(new Rectangle(64, 119, 54, 18));
        lbQuantity.setText("Quantity");
        lbQuantity.setToolTipText("");
        lbPrice.setBounds(new Rectangle(64, 87, 50, 18));
        lbPrice.setText("Price");
        lbPrice.setToolTipText("");
        lbAmount.setBounds(new Rectangle(64, 153, 54, 18));
        lbAmount.setText("Amount");
        lbAmount.setToolTipText("");
        jbBuy.setBackground(FConfig.ScreenBackground);
        jbBuy.setBounds(new Rectangle(122, 182, 47, 22));
        jbBuy.setBorder(border1);
        jbBuy.setText("Buy");
        jbCanel.setText("Cancel");
        jbCanel.setBorder(border1);
        jbCanel.setBackground(FConfig.ScreenBackground);
        jbCanel.setBounds(new Rectangle(180, 182, 57, 22));
        jPanel1.add(jTextField6, null);
        jPanel1.add(jTextField5, null);
        jPanel1.add(jTextField4, null);
        jPanel1.add(jTextField2, null);
        jPanel1.add(jTextField3, null);
        jPanel1.add(jbQuote, null);
        jPanel1.add(lbPrice, null);
        jPanel1.add(lbName, null);
        jPanel1.add(lbCode, null);
        jPanel1.add(lbAmount, null);
        jPanel1.add(lbQuantity, null);
        this.add(jPanel1, BorderLayout.CENTER);
        jPanel1.add(jbBuy, null);
        jPanel1.add(jbCanel, null);
    }
}
