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

    private static final ChartType TA_CHART_TYPE_LIST[] = {ChartType.RSI, ChartType.STC, ChartType.OBV, ChartType.WILLIAM_R};

    private static final long serialVersionUID = -4589991589319998628L;
    private JComboBox<ChartType> chChartType = new JComboBox<ChartType>(TA_CHART_TYPE_LIST);
    private ChartScreen chartScreen = null;

    public TAMenu() {
        init();
    }

    public void setChartScreen(ChartScreen cs) {
        chartScreen = cs;
    }

    private void init() {
        this.setLayout(null);
        chChartType.setFont(new Font("Dialog", 0, 18));
        chChartType.setBorder(BorderFactory.createLineBorder(Color.black));
        chChartType.setBounds(new Rectangle(0, 0, 100, FConfig.BUTTON_SIZE));
        add(chChartType, null);
        chChartType.setBackground(Color.white);
        chChartType.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                chChartType_itemStateChanged(e);
            }
        });
    }

    protected void chChartType_itemStateChanged(ItemEvent e) {

        if (chartScreen == null) return;

        ChartItem chartItem = chartScreen.getLeftChart();
        ChartType cType = (ChartType) chChartType.getSelectedItem();

        if (cType == null || chartItem == null) return;

        switch (cType) {
            case RSI:
                chartItem.setChartType(ChartType.RSI);
                chartItem.getChartData().calculateRSI(chartItem.getChartData().getfTAconfig().RSIPeriod);
                break;
            case STC:
                chartItem.setChartType(ChartType.STC);
                chartItem.getChartData().calculateSTC(chartItem.getChartData().getfTAconfig().STCKPeriod,
                        chartItem.getChartData().getfTAconfig().STCDPeriod);
                break;
            case OBV:
                chartItem.setChartType(ChartType.OBV);
                break;
            case MACD:
                chartItem.setChartType(ChartType.MACD);
                chartItem.getChartData().calculateMACD(chartItem.getChartData().getfTAconfig().MACDLEMA,
                        chartItem.getChartData().getfTAconfig().MACDSEMA, chartItem.getChartData().getfTAconfig().MACDAEMA);
                break;
            case WILLIAM_R:
                chartItem.setChartType(ChartType.WILLIAM_R);
                chartItem.getChartData().calculateWilliamR(chartItem.getChartData().getfTAconfig().WilliamPeriod);
                break;

        }
        chartScreen.updateBaseScreen();
        chartScreen.repaint();
    }


}

