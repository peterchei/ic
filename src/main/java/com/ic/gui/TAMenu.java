package com.ic.gui;

import com.ic.core.ChartItem;
import com.ic.core.ChartScreen;
import com.ic.core.ChartType;
import com.ic.core.FConfig;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;


public class TAMenu extends JPanel {

    static final String TAchartType[] = {"RSI", "STC", "OBV", "MACD", "William %R"};

    private static final long serialVersionUID = -4589991589319998628L;
    public JComboBox chChartType = new JComboBox();
    private ChartScreen chartScreen = null;

    public TAMenu() {
        try {
            init();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setChartScreen(ChartScreen cs) {
        chartScreen = cs;
    }

    private void init() throws Exception {
        this.setLayout(null);
        chChartType.setFont(new Font("Dialog", 0, 18));
        chChartType.setBorder(BorderFactory.createLineBorder(Color.black));
        chChartType.setBounds(new Rectangle(0, 0, 100, FConfig.BUTTON_SIZE));
        add(chChartType, null);
        chChartType.setBackground(Color.white);

        for (String item:TAchartType) {
            chChartType.addItem(item);
        }
        chChartType.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                chChartType_itemStateChanged(e);
            }
        });


    }

    public void chChartType_itemStateChanged(ItemEvent e) {

        if (chartScreen == null) return;
        ChartItem lchart = chartScreen.getLeftChart(); // The TA chart....
        if (lchart == null) return;
        String cType = (String) chChartType.getSelectedItem(); // get the ITEM we select.

        if (cType == null) {
            return;
        } else if (cType.equals(TAchartType[0])) {
            lchart.setChartType(ChartType.RSI);
            lchart.getChartData().calculateRSI(lchart.getChartData().getfTAconfig().RSIPeriod);
        } else if (cType.equals(TAchartType[1])) {
            lchart.setChartType(ChartType.STC);
            lchart.getChartData().calculateSTC(lchart.getChartData().getfTAconfig().STCKPeriod, lchart.getChartData().getfTAconfig().STCDPeriod);
        } else if (cType.equals(TAchartType[2])) {
            lchart.setChartType(ChartType.OBV);
        } else if (cType.equals(TAchartType[3])) {
            lchart.setChartType(ChartType.MACD);
            lchart.getChartData().calculateMACD(lchart.getChartData().getfTAconfig().MACDLEMA, lchart.getChartData().getfTAconfig().MACDSEMA, lchart.getChartData().getfTAconfig().MACDAEMA);
        } else if (cType.equals(TAchartType[4])) {
            lchart.setChartType(ChartType.WILLIAM_R);
            lchart.getChartData().calculateWilliamR(lchart.getChartData().getfTAconfig().WilliamPeriod);
        }
        chartScreen.updateBaseScreen();
        chartScreen.repaint();

    }


}

