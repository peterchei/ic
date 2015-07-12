package teletext.apps.ui;

import teletext.apps.core.PieChartScreen;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import java.awt.*;

public class AssetPanel extends JPanel {
    PieChartScreen pieChartScreen1 = new PieChartScreen();
    Border border1;
    JPanel jPanel1 = new JPanel();
    BorderLayout borderLayout1 = new BorderLayout();
    Border border2;
    JButton jbStock = new JButton();
    JButton jbGeneral1 = new JButton();
    JButton jbDetails = new JButton();
    JButton jbSaving = new JButton();
    JPanel jpCenter = new JPanel();
    BorderLayout borderLayout2 = new BorderLayout();
    JPanel jpHeader = new JPanel();
    JLabel lbAssetHeader = new JLabel();
    FlowLayout flowLayout1 = new FlowLayout(FlowLayout.LEFT);
    Border border3;
    BorderLayout borderLayout3 = new BorderLayout();
    JPanel jpBotton = new JPanel();
    Border border4;
    Border border5;
    Border border6;
    Border border7;

    public AssetPanel() {
        try {
            jbInit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void jbInit() throws Exception {

        border2 = BorderFactory.createCompoundBorder(new EtchedBorder(EtchedBorder.RAISED, Color.white, Color.gray), BorderFactory.createEmptyBorder(10, 30, 10, 30));
        border3 = BorderFactory.createEmptyBorder(10, 0, 6, 0);
        border4 = BorderFactory.createCompoundBorder(BorderFactory.createEtchedBorder(new Color(255, 255, 208), new Color(177, 161, 102)), BorderFactory.createEmptyBorder(0, 5, 0, 5));
        border5 = BorderFactory.createCompoundBorder(BorderFactory.createEtchedBorder(new Color(255, 255, 208), new Color(177, 161, 102)), BorderFactory.createEmptyBorder(0, 5, 0, 5));
        border6 = BorderFactory.createCompoundBorder(BorderFactory.createEtchedBorder(new Color(255, 255, 208), new Color(177, 161, 102)), BorderFactory.createEmptyBorder(0, 5, 0, 5));
        border7 = BorderFactory.createCompoundBorder(BorderFactory.createEtchedBorder(new Color(255, 255, 208), new Color(177, 161, 102)), BorderFactory.createEmptyBorder(0, 5, 0, 5));
        this.setLayout(borderLayout2);
        pieChartScreen1.setBackground(FConfig.ScreenBackground);
        pieChartScreen1.setBorder(border1);
        this.setBackground(new Color(255, 255, 120));
        this.setForeground(Color.lightGray);
        this.setAlignmentY((float) 0.5);
        jPanel1.setBackground(new Color(255, 255, 150));
        jPanel1.setBorder(border2);
        jPanel1.setLayout(borderLayout1);
        jbStock.setBackground(Color.orange);
        jbStock.setFont(new java.awt.Font("Arial", 0, 11));
        jbStock.setBorder(border5);
        jbStock.setActionCommand("General");
        jbStock.setText("Stock");
        jbStock.setBackground(FConfig.ScreenBackground);
        jbGeneral1.setText("General");
        jbGeneral1.setBorder(border4);
        jbGeneral1.setFont(new java.awt.Font("Arial", 0, 11));
        jbGeneral1.setBackground(Color.orange);
        jbGeneral1.setBackground(FConfig.ScreenBackground);
        jbDetails.setText("Details");
        jbDetails.setBorder(border7);
        jbDetails.setFont(new java.awt.Font("Arial", 0, 11));
        jbDetails.setBackground(FConfig.ScreenBackground);
        jbSaving.setText("Saving");
        jbSaving.setBorder(border6);
        jbSaving.setFont(new java.awt.Font("Arial", 0, 11));
        jbSaving.setBackground(new Color(254, 230, 146));

        lbAssetHeader.setFont(new java.awt.Font("Arial", 0, 18));
        lbAssetHeader.setMaximumSize(new Dimension(34, 17));
        lbAssetHeader.setHorizontalAlignment(SwingConstants.LEFT);
        lbAssetHeader.setText("Asset Summary");
        jpHeader.setLayout(flowLayout1);
        jpHeader.setBackground(FConfig.ToolBarColor);
        jpHeader.setBorder(border3);
        jpCenter.setLayout(borderLayout3);
        jpCenter.setBackground(FConfig.ScreenBackground);
        jpBotton.setBackground(FConfig.ToolBarColor);
        jpCenter.add(jPanel1, BorderLayout.CENTER);
        jPanel1.add(pieChartScreen1, BorderLayout.CENTER);
        jpCenter.add(jpBotton, BorderLayout.SOUTH);
        jpBotton.add(jbGeneral1, null);
        jpBotton.add(jbStock, null);
        jpBotton.add(jbSaving, null);
        jpBotton.add(jbDetails, null);
        jpBotton.setBackground(FConfig.ToolBarColor);
        this.add(jpHeader, BorderLayout.NORTH);
        jpHeader.add(lbAssetHeader, null);
        this.add(jpCenter, BorderLayout.CENTER);
    }
}
