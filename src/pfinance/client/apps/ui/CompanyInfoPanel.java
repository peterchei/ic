package pfinance.client.apps.ui;

import teletext.apps.ui.TopTenModel;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.ActionEvent;

public class CompanyInfoPanel
        extends JPanel {
    TopTenModel model = new TopTenModel();
    BorderLayout borderLayout1 = new BorderLayout();
    JPanel jPanel1 = new JPanel();
    JPanel jPanel2 = new JPanel();
    JTable jTable1 = new JTable();
    BorderLayout borderLayout2 = new BorderLayout();
    JButton jbAddStock = new JButton();
    Border border1;
    Border border2;
    Border border3;
    TitledBorder titledBorder10;
    Border border4;
    FlowLayout flowLayout1 = new FlowLayout();
    JScrollPane jScrollPane1 = new JScrollPane();
    TitledBorder titledBorder1;
    Border border5;
    Border border6;
    Border border7;
    Border border8;
    JTextField jTextField1 = new JTextField();
    Border border9;
    Border border10;

    public CompanyInfoPanel() {
        try {
            jbInit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String args[]) {

        JFrame frame = new JFrame();
        frame.getContentPane().add(new CompanyInfoPanel());
        frame.pack();
        frame.setVisible(true);
    }

    private void jbInit() throws Exception {
        border1 = BorderFactory.createEmptyBorder(10, 10, 10, 10);
        border2 = BorderFactory.createEmptyBorder(5, 10, 5, 6);
        border3 = BorderFactory.createCompoundBorder(BorderFactory.
                createLineBorder(Color.black, 2),
                BorderFactory.createEmptyBorder(0, 10, 0, 10));
        titledBorder10 = new TitledBorder(BorderFactory.createEtchedBorder(
                Color.white, new Color(148, 145, 140)), "");
        border4 = BorderFactory.createCompoundBorder(titledBorder10,
                BorderFactory.createEmptyBorder(10, 10, 0, 10));
        titledBorder1 = new TitledBorder("");
        border5 = BorderFactory.createCompoundBorder(BorderFactory.
                createEtchedBorder(new Color(255, 255, 208),
                        new Color(177, 161, 102)),
                BorderFactory.createEmptyBorder(0, 3, 0, 3));
        border6 = BorderFactory.createCompoundBorder(BorderFactory.
                createEtchedBorder(new Color(255, 255, 208),
                        new Color(177, 161, 102)),
                BorderFactory.createEmptyBorder(0, 3, 0, 3));
        border7 = BorderFactory.createCompoundBorder(BorderFactory.
                createEtchedBorder(new Color(255, 255, 208),
                        new Color(177, 161, 102)),
                BorderFactory.createEmptyBorder(0, 3, 0, 3));
        border8 = BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.black, 1), BorderFactory.createEmptyBorder(0, 6, 0, 6));
        border9 = BorderFactory.createLineBorder(Color.darkGray, 1);
        border10 = new EtchedBorder(EtchedBorder.RAISED, Color.white, new Color(177, 161, 102));
        this.setLayout(borderLayout1);
        jPanel2.setLayout(borderLayout2);
        jbAddStock.setBackground(new Color(254, 230, 146));
        jbAddStock.setFont(new java.awt.Font("Default", 0, 11));
        jbAddStock.setBorder(border10);
        jbAddStock.setText("Add");
        jPanel1.setBackground(new Color(234, 181, 21));
        jPanel1.setBorder(border2);
        jPanel1.setMaximumSize(new Dimension(208, 29));
        jPanel1.setLayout(flowLayout1);
        jTable1.setBackground(Color.white);
        jPanel2.setBackground(new Color(254, 230, 146));
        jPanel2.setBorder(border4);
        flowLayout1.setAlignment(FlowLayout.LEFT);
        // jScrollPane1.setViewport(null);
        jScrollPane1.getViewport().setBackground(new Color(254, 230, 146));
        jScrollPane1.setBorder(BorderFactory.createEtchedBorder());
        this.setBackground(Color.lightGray);
        jTextField1.setBorder(border9);
        jTextField1.setMinimumSize(new Dimension(30, 23));
        jTextField1.setCaretPosition(0);
        jTextField1.setText("");
        jTextField1.setColumns(5);
        jTextField1.addActionListener(new CompanyInfoPanel_jTextField1_actionAdapter(this));
        this.add(jPanel1, BorderLayout.NORTH);
        jPanel1.add(jTextField1, null);
        jPanel1.add(jbAddStock, null);
        this.add(jPanel2, BorderLayout.CENTER);
        jTable1.setBorder(BorderFactory.createEtchedBorder());
        jTable1.setModel(model);

        JTableHeader header = new JTableHeader();
        header.setBackground(new Color(234, 181, 21));

        header.setVisible(true);
        header.setColumnModel(jTable1.getColumnModel());
        jTable1.setTableHeader(header);

        jPanel2.add(jScrollPane1, BorderLayout.CENTER);
        jScrollPane1.getViewport().add(jTable1, null);
        jScrollPane1.getVerticalScrollBar().setBackground(new Color(254, 230,
                146));
        jScrollPane1.getVerticalScrollBar().setForeground(new Color(234, 181,
                21));

    }

    void jTextField1_actionPerformed(ActionEvent e) {

    }


}

class CompanyInfoPanel_jTextField1_actionAdapter implements java.awt.event.ActionListener {
    CompanyInfoPanel adaptee;

    CompanyInfoPanel_jTextField1_actionAdapter(CompanyInfoPanel adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.jTextField1_actionPerformed(e);
    }
}
