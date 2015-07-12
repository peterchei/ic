package teletext.apps.core;


import teletext.apps.ui.*;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;


public class MainPanel extends JPanel {
    JTabbedPane jTabbedPane1 = new JTabbedPane();
    BorderLayout borderLayout1 = new BorderLayout();
    public FMEChartA fMEChartA1 = new FMEChartA();
    public Border border1;
    public JPanel jPanel1 = new TopTenPanel();
    public JPanel jPanel2 = new NewsPanel();
    public JPanel jPanel3 = new PortfolioPanel();
    Border border2;
    AssetPanel jpAsset = new AssetPanel();


    public MainPanel() {
        try {
            jbInit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void jbInit() throws Exception {
        border1 = BorderFactory.createEmptyBorder();
        border2 = BorderFactory.createEmptyBorder(5, 0, 5, 0);
        this.setLayout(borderLayout1);
        jTabbedPane1.setTabPlacement(JTabbedPane.BOTTOM);
        jTabbedPane1.setBackground(FConfig.ToolBarColor);
        jTabbedPane1.setEnabled(true);
        jTabbedPane1.setFont(new java.awt.Font("Arial", 0, 10));
        jTabbedPane1.setAlignmentX((float) 0.0);
        jTabbedPane1.setAlignmentY((float) 0.0);
        jTabbedPane1.setBorder(null);
        jTabbedPane1.setDebugGraphicsOptions(0);
        jTabbedPane1.setDoubleBuffered(false);
        jTabbedPane1.setMaximumSize(new Dimension(32767, 32767));
        this.add(jTabbedPane1, BorderLayout.CENTER);
        jTabbedPane1.add(fMEChartA1, "Chart");
        //jTabbedPane1.add(jPanel1, "Top10");
        //jTabbedPane1.add(jPanel2, "News");
        //jTabbedPane1.add(jPanel3, "Portfolio");
        //jTabbedPane1.add(jpAsset, "Asset");

        jTabbedPane1.setSelectedComponent(fMEChartA1);
    }
}
