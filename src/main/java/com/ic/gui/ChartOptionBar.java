package com.ic.gui;

import com.ic.core.ChartScreen;
import com.ic.core.FConfig;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ChartOptionBar extends JPanel {

    public ImageButton btTA = new ImageButton();
    public ImageButton btVolume = new ImageButton();

    private ChartScreen chartScreen1 = null;
    private ChartScreen chartScreen2 = null;
    private ChartScreen chartScreen3 = null;

    public ChartOptionBar() {
        try {
            jbInit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void jbInit() throws Exception {
        this.setLayout(null);
        btTA.setBounds(new Rectangle(FConfig.BUTTON_SIZE + 1, 2, FConfig.BUTTON_SIZE, FConfig.BUTTON_SIZE));
        btTA.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                btTA_actionPerformed(e);
            }
        });
        btVolume.setBounds(new Rectangle((FConfig.BUTTON_SIZE + 1) * 2, 2, FConfig.BUTTON_SIZE, FConfig.BUTTON_SIZE));
        btVolume.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                btVolume_actionPerformed(e);
            }
        });
        this.add(btTA, null);
        this.add(btVolume, null);
    }


    void btTA_actionPerformed(ActionEvent e) {
        chartScreen2.setVisible(!chartScreen2.isVisible());
    }

    void btVolume_actionPerformed(ActionEvent e) {
        chartScreen3.setVisible(!chartScreen3.isVisible());

    }

    //set the reference of the chartScrenn such that the button bar can control it.
    public void setChartScreen(ChartScreen cs1, ChartScreen cs2, ChartScreen cs3) {
        chartScreen1 = cs1;
        chartScreen2 = cs2;
        chartScreen3 = cs3;
    }


}
