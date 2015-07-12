package teletext.apps.ui;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import java.awt.*;
import java.awt.event.ActionEvent;

public class PortfolioPanel
        extends JPanel {
    JPanel jPanel1 = new JPanel();
    BorderLayout borderLayout1 = new BorderLayout();
    Border border1;
    JButton jButton1 = new JButton();
    JButton jButton2 = new JButton();
    FlowLayout flowLayout1 = new FlowLayout();
    Border border2;
    Border border3;
    JPanel jPanel2 = new JPanel();
    Border border4;
    Border border5;
    Border border6;
    JPanel jPanel3 = new JPanel();
    JPanel jPanel4 = new JPanel();
    BorderLayout borderLayout3 = new BorderLayout();
    Border border7;
    Border border8;
    Border border9;
    JTable jTable1 = new JTable();
    JTable jTable2 = new JTable(3, 5);
    Border border10;
    Border border11;
    Border border12;
    BorderLayout borderLayout2 = new BorderLayout();
    JScrollPane jScrollPane1 = new JScrollPane();
    GridBagLayout gridBagLayout1 = new GridBagLayout();
    Border border13;
    Border border14;

    public PortfolioPanel() {
        try {
            jbInit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void jbInit() throws Exception {

        border1 = BorderFactory.createCompoundBorder(new EtchedBorder(
                EtchedBorder.RAISED, Color.yellow, new Color(178, 140, 0)),
                BorderFactory.createEmptyBorder(10, 0, 10, 0));
        border2 = BorderFactory.createCompoundBorder(BorderFactory.
                createEtchedBorder(Color.white, new Color(148, 145, 140)),
                BorderFactory.createEmptyBorder(0, 5, 0, 5));
        border3 = BorderFactory.createCompoundBorder(BorderFactory.
                createEtchedBorder(Color.white, new Color(148, 145, 140)),
                BorderFactory.createEmptyBorder(0, 5, 0, 5));
        border4 = BorderFactory.createEmptyBorder(10, 10, 10, 10);
        border5 = BorderFactory.createEmptyBorder(5, 10, 10, 6);
        border6 = BorderFactory.createCompoundBorder(new EtchedBorder(
                EtchedBorder.RAISED, Color.white, new Color(148, 145, 140)),
                BorderFactory.createEmptyBorder(10, 0, 10, 0));
        border7 = BorderFactory.createEmptyBorder(20, 0, 20, 0);
        border8 = BorderFactory.createEmptyBorder(5, 5, 5, 5);
        border9 = BorderFactory.createEmptyBorder(5, 5, 5, 5);
        border10 = BorderFactory.createEtchedBorder(Color.white,
                new Color(178, 178, 178));
        border11 = BorderFactory.createCompoundBorder(new EtchedBorder(
                EtchedBorder.RAISED, SystemColor.activeCaptionBorder,
                new Color(0, 178, 0)), BorderFactory.createEmptyBorder(10, 5, 10, 5));
        border12 = BorderFactory.createCompoundBorder(new EtchedBorder(
                EtchedBorder.RAISED, Color.white, new Color(178, 178, 178)),
                BorderFactory.createEmptyBorder(10, 5, 10, 5));
        border13 = BorderFactory.createCompoundBorder(BorderFactory.createEtchedBorder(new Color(255, 255, 208), new Color(177, 161, 102)), BorderFactory.createEmptyBorder(0, 5, 0, 5));
        border14 = BorderFactory.createCompoundBorder(BorderFactory.createEtchedBorder(new Color(255, 255, 208), new Color(177, 161, 102)), BorderFactory.createEmptyBorder(0, 5, 0, 5));
        this.setBackground(new Color(234, 181, 21));
        this.setMaximumSize(new Dimension(32767, 32767));
        this.setLayout(borderLayout1);
        jPanel1.setBackground(new Color(234, 181, 21));
        jPanel1.setBorder(border5);
        jPanel1.setLayout(flowLayout1);
        jButton1.setBackground(new Color(254, 230, 146));
        jButton1.setBorder(border13);
        jButton1.setText("Buy");
        jButton1.addActionListener(new PortfolioPanel_jButton1_actionAdapter(this));
        jButton2.setBackground(new Color(254, 230, 146));
        jButton2.setBorder(border14);
        jButton2.setText("Sell");
        jButton2.addActionListener(new PortfolioPanel_jButton2_actionAdapter(this));
        jPanel2.setLayout(borderLayout2);
        jPanel4.setBackground(new Color(255, 217, 140));
        jPanel4.setBorder(border12);
        jPanel4.setLayout(gridBagLayout1);
        jPanel3.setBackground(SystemColor.activeCaptionBorder);
        jPanel3.setBorder(border11);
        jPanel3.setLayout(borderLayout3);
        jPanel2.setBorder(null);
        jPanel2.setMaximumSize(new Dimension(2147483647, 2147483647));
        jTable2.setBackground(Color.lightGray);
        jTable2.setEnabled(false);
        jTable2.setBorder(BorderFactory.createLineBorder(Color.black));
        jTable2.setMinimumSize(new Dimension(75, 32));
        jTable2.setShowHorizontalLines(true);
        jTable1.setFont(new java.awt.Font("Dialog", 0, 10));
        jTable1.setBorder(border10);
        this.add(jPanel1, BorderLayout.NORTH);
        jPanel1.add(jButton1, null);
        jPanel1.add(jButton2, null);
        this.add(jPanel2, BorderLayout.CENTER);
        jPanel2.add(jPanel3, BorderLayout.CENTER);
        jPanel3.add(jScrollPane1, BorderLayout.CENTER);
        jScrollPane1.getViewport().add(jTable1, null);
        jPanel2.add(jPanel4, BorderLayout.SOUTH);
        jPanel4.add(jTable2, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0
                , GridBagConstraints.CENTER, GridBagConstraints.NONE,
                new Insets(0, 0, 0, 0), 0, 0));
        jPanel2.setBackground(new Color(254, 230, 146));
        flowLayout1.setAlignment(FlowLayout.LEFT);

        jTable1.setModel(new PortfolioModel());
        setColumnNames();
    }

    protected void setColumnNames() {
        jTable2.setValueAt("Settled", 1, 0);
        jTable2.setValueAt("Outstanding", 2, 0);
        jTable2.setValueAt("Cost", 0, 1);
        jTable2.setValueAt("NPV", 0, 2);
        jTable2.setValueAt("P&L", 0, 3);
        jTable2.setValueAt("P&L(%)", 0, 4);


    }

    void jButton1_actionPerformed(ActionEvent e) {
        JPanel dealPanel = new DealInputPanel();
        JFrame frame = new JFrame();
        frame.getContentPane().add(dealPanel);
        frame.setSize(355, 250);
        frame.setResizable(false);
        frame.show();
    }

    void jButton2_actionPerformed(ActionEvent e) {
        JPanel dealPanel = new DealSellPanel();
        JFrame frame = new JFrame();
        frame.getContentPane().add(dealPanel);
        frame.setSize(355, 250);
        frame.setResizable(false);
        frame.show();

    }

}

class PortfolioPanel_jButton1_actionAdapter implements java.awt.event.ActionListener {
    PortfolioPanel adaptee;

    PortfolioPanel_jButton1_actionAdapter(PortfolioPanel adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.jButton1_actionPerformed(e);
    }
}

class PortfolioPanel_jButton2_actionAdapter implements java.awt.event.ActionListener {
    PortfolioPanel adaptee;

    PortfolioPanel_jButton2_actionAdapter(PortfolioPanel adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.jButton2_actionPerformed(e);
    }
}
