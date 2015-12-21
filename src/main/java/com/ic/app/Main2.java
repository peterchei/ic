package com.ic.app;

import com.ic.core.ChartScreen;
import com.ic.core.FConfig;
import com.ic.data.ChartDataService;
import com.ic.data.RequestCommand;
import com.ic.gui.STVChart;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

/**
 * Created by peter on 12/13/2015.
 */
public class Main2 extends JFrame implements WindowListener {

    BorderLayout borderLayout1 = new BorderLayout();
    private STVChart chartPanel = null;
    private Container originalContainer = null;

    public static void main(String args[]) {
        Main2 chartWindow = new Main2();
        chartWindow.setVisible(true);
        chartWindow.setBounds(0, 0, 1280, 960);
        chartWindow.setResizable(true);
        chartWindow.setTitle("IC");
    }

    public Main2() {
        try {
            configure();
            STVChart coreChart = new STVChart();


            coreChart.fTAMenu1.btSetting.setButtonImage(new ImageIcon(getClass().getResource("/setting.png")).getImage());
            //coreChart.chartOptionBar1.btStock.setButtonImage(new ImageIcon(getClass().getResource("/chartTypeA.png")).getImage());
            coreChart.chartOptionBar1.btTA.setButtonImage(new ImageIcon(getClass().getResource("/chartTypeA.png")).getImage());
            coreChart.chartOptionBar1.btVolume.setButtonImage(new ImageIcon(getClass().getResource("/chartTypeC.png")).getImage());
            coreChart.btOpenClose.setButtonImage(new ImageIcon(getClass().getResource("/close.png")).getImage());
            coreChart.btOpenClose.setVisible(false);
            coreChart.btPrinter.setButtonImage(new ImageIcon(getClass().getResource("/print.png")).getImage());
            coreChart.fCompareBar.getCloseButton().setButtonImage(new ImageIcon(getClass().getResource("/close.png")).getImage());
            coreChart.fCompareBar.getAddButton().setButtonImage(new ImageIcon(getClass().getResource("/add.png")).getImage());
            coreChart.fCompareBar.getRemoveButton().setButtonImage(new ImageIcon(getClass().getResource("/minus.png")).getImage());

            // Init the chart screen
            coreChart.chartScreen1.initScreen();
            coreChart.chartScreen2.initScreen();
            coreChart.chartScreen3.initScreen();


            ChartDataService.getInstance().enable();
            this.setIconImage(new ImageIcon(getClass().getResource("/ICChart.png")).getImage());

            // add a chart data
            RequestCommand fc = new RequestCommand(2800, RequestCommand.TYPE_DOWNLOAD_LEFT_CHART, (RequestCommand.CommandType) coreChart.fmenuBar.chDuration.getSelectedItem(), "LMain1", 500, 1, false, coreChart.fmenuBar);
            ChartDataService.getInstance().addCommand(fc);
            coreChart.chartScreen1.setScreenState(ChartScreen.LOADING);
            coreChart.chartScreen2.setScreenState(ChartScreen.LOADING);
            coreChart.chartScreen3.setScreenState(ChartScreen.LOADING);


            // change the language by calling set language function
            coreChart.setLanguage(FConfig.constEnglish);
            this.setChartPanel(coreChart);
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

    private void configure() throws Exception {
        this.getContentPane().setLayout(borderLayout1);
    }

    public void windowClosing(WindowEvent e) {
        this.dispose();
        System.exit(0);
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