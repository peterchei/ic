package teletext.apps.ui;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.ActionEvent;

public class TopTenPanel
        extends JPanel {

    TopTenModel model = new TopTenModel();
    BorderLayout borderLayout1 = new BorderLayout();
    JPanel jPanel1 = new JPanel();
    JPanel jPanel2 = new JPanel();
    JTable jTable1 = new JTable();
    BorderLayout borderLayout2 = new BorderLayout();
    JButton jbGain = new JButton();
    JButton jbVolume = new JButton();
    JButton jbTurnover = new JButton();
    JButton jbFall = new JButton();
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
    Border border9;
    TitledBorder titledBorder2;
    Border border10;
    Border border11;
    Border border12;

    public TopTenPanel() {
        try {
            jbInit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String args[]) {

        JFrame frame = new JFrame();
        frame.getContentPane().add(new TopTenPanel());
        frame.pack();
        frame.setVisible(true);
    }

    private void jbInit() throws Exception {
        border1 = BorderFactory.createEmptyBorder(10, 10, 10, 10);
        border2 = BorderFactory.createEmptyBorder(5, 10, 10, 6);
        border3 = BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.black, 2),
                BorderFactory.createEmptyBorder(0, 10, 0, 10));
        titledBorder10 = new TitledBorder(BorderFactory.createEtchedBorder(
                Color.white, new Color(148, 145, 140)), "");
        border4 = BorderFactory.createCompoundBorder(BorderFactory.createEtchedBorder(Color.white, new Color(148, 145, 140)),
                BorderFactory.createEmptyBorder(10, 10, 10, 10));
        titledBorder1 = new TitledBorder("");
        border5 = BorderFactory.createCompoundBorder(BorderFactory.createEtchedBorder(new Color(255, 255, 208),
                new Color(177, 161, 102)),
                BorderFactory.createEmptyBorder(0, 3, 0, 3));
        border6 = BorderFactory.createCompoundBorder(BorderFactory.createEtchedBorder(new Color(255, 255, 208),
                new Color(177, 161, 102)),
                BorderFactory.createEmptyBorder(0, 3, 0, 3));
        border7 = BorderFactory.createCompoundBorder(BorderFactory.createEtchedBorder(new Color(255, 255, 208),
                new Color(177, 161, 102)),
                BorderFactory.createEmptyBorder(0, 3, 0, 3));
        border8 = BorderFactory.createCompoundBorder(BorderFactory.createEtchedBorder(new Color(255, 255, 208),
                new Color(177, 161, 102)),
                BorderFactory.createEmptyBorder(0, 3, 0, 3));
        border9 = BorderFactory.createLineBorder(new Color(255, 255, 208), 1);
        titledBorder2 = new TitledBorder("");
        border10 = BorderFactory.createCompoundBorder(BorderFactory.createEtchedBorder(new Color(255, 255, 212), new Color(144, 112, 102)), BorderFactory.createEmptyBorder(0, 5, 0, 5));
        border11 = BorderFactory.createCompoundBorder(new EtchedBorder(EtchedBorder.RAISED, new Color(255, 255, 208), new Color(144, 112, 102)), BorderFactory.createEmptyBorder(0, 5, 0, 5));
        border12 = new EtchedBorder(EtchedBorder.RAISED, Color.white, new Color(148, 145, 140));
        this.setLayout(borderLayout1);
        jPanel2.setLayout(borderLayout2);
        jbGain.setBackground(new Color(254, 230, 146));
        jbGain.setBorder(border10);
        jbGain.setVerifyInputWhenFocusTarget(true);
        jbGain.setText("Gain");
        jbGain.addActionListener(new TopTenPanel_jbGain_actionAdapter(this));
        jbVolume.setBackground(new Color(254, 230, 146));
        jbVolume.setBorder(border10);
        jbVolume.setText("Volume");
        jbVolume.addActionListener(new TopTenPanel_jbVolume_actionAdapter(this));
        jbTurnover.setBackground(new Color(254, 230, 146));
        jbTurnover.setAlignmentX((float) 0.0);
        jbTurnover.setBorder(border10);
        jbTurnover.setText("Turnover");
        jbTurnover.addActionListener(new TopTenPanel_jbTurnover_actionAdapter(this));
        jbFall.setBackground(new Color(254, 230, 146));
        jbFall.setBorder(border10);
        jbFall.setActionCommand("jButton4");
        jbFall.setBorderPainted(true);
        jbFall.setContentAreaFilled(true);
        jbFall.setText("Fall");
        jbFall.addActionListener(new TopTenPanel_jbFall_actionAdapter(this));
        jPanel1.setBackground(new Color(234, 181, 21));
        jPanel1.setBorder(border2);
        jPanel1.setMaximumSize(new Dimension(208, 29));
        jPanel1.setLayout(flowLayout1);
        jTable1.setBackground(Color.white);
        jTable1.setSelectionBackground(Color.darkGray);
        jPanel2.setBackground(new Color(254, 230, 146));
        jPanel2.setBorder(border4);
        flowLayout1.setAlignment(FlowLayout.LEFT);
        // jScrollPane1.setViewport(null);
        jScrollPane1.getViewport().setBackground(new Color(254, 230, 146));
        jScrollPane1.setBorder(border12);
        jScrollPane1.setDoubleBuffered(false);
        this.setBackground(Color.lightGray);
        this.add(jPanel1, BorderLayout.NORTH);
        jPanel1.add(jbVolume, null);
        jPanel1.add(jbGain, null);
        jPanel1.add(jbTurnover, null);
        jPanel1.add(jbFall, null);
        this.add(jPanel2, BorderLayout.CENTER);
        jTable1.setBorder(border10);
        jTable1.setModel(model);

        JTableHeader header = new JTableHeader();
        header.setBackground(new Color(234, 181, 21));
        header.setBorder(border10);

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

    void jbVolume_actionPerformed(ActionEvent e) {
    }

    void jbGain_actionPerformed(ActionEvent e) {
    }

    void jbTurnover_actionPerformed(ActionEvent e) {
    }

    void jbFall_actionPerformed(ActionEvent e) {
    }
}

class TopTenPanel_jbVolume_actionAdapter
        implements java.awt.event.ActionListener {

    TopTenPanel adaptee;

    TopTenPanel_jbVolume_actionAdapter(TopTenPanel adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.jbVolume_actionPerformed(e);
    }
}

class TopTenPanel_jbGain_actionAdapter
        implements java.awt.event.ActionListener {

    TopTenPanel adaptee;

    TopTenPanel_jbGain_actionAdapter(TopTenPanel adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.jbGain_actionPerformed(e);
    }
}

class TopTenPanel_jbTurnover_actionAdapter
        implements java.awt.event.ActionListener {

    TopTenPanel adaptee;

    TopTenPanel_jbTurnover_actionAdapter(TopTenPanel adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.jbTurnover_actionPerformed(e);
    }
}

class TopTenPanel_jbFall_actionAdapter
        implements java.awt.event.ActionListener {

    TopTenPanel adaptee;

    TopTenPanel_jbFall_actionAdapter(TopTenPanel adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.jbFall_actionPerformed(e);
    }
}
