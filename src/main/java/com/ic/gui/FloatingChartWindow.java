package com.ic.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;


public class FloatingChartWindow extends JFrame implements WindowListener {
    /**
     *
     */
    private static final long serialVersionUID = -1383843787553240394L;
    BorderLayout borderLayout1 = new BorderLayout();
    private STVChart chartPanel = null;
    private Container originalContainer = null;

    public FloatingChartWindow() {
        try {
            jbInit();
            this.addWindowListener(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setChartPanel(STVChart pnChart) {
        chartPanel = pnChart;
        originalContainer = pnChart.getParent();
        this.getContentPane().add(pnChart, borderLayout1.CENTER);
    }

    private void jbInit() throws Exception {
        // button1.setLabel("button1");
        this.getContentPane().setLayout(borderLayout1);

    }

    public void windowClosing(WindowEvent e) {
        this.dispose();
        chartPanel.btOpenClose.setVisible(true);
        //originalContainer.setLayout(borderLayout);
        this.originalContainer.add("Chart", chartPanel);

        chartPanel.setBounds(0, 0, originalContainer.getSize().width, originalContainer.getSize().height);

        originalContainer.repaint();
    }

    public void windowOpened(WindowEvent e) {
        System.out.println("closed");
    }

    public void windowClosed(WindowEvent e) {
        System.out.println("closed");
    }

    public void windowIconified(WindowEvent e) {
        System.out.println("iconified");
    }

    public void windowDeiconified(WindowEvent e) {
        System.out.println("deiconified");
    }

    public void windowActivated(WindowEvent e) {
        System.out.println("activated");
    }

    public void windowDeactivated(WindowEvent e) {
        System.out.println("deactivated");
    }


}
