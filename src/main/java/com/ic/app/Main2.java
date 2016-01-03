package com.ic.app;

import com.ic.core.ChartScreen;
import com.ic.core.FConfig;
import com.ic.data.ChartDataService;
import com.ic.data.CommandType;
import com.ic.data.RequestCommand;
import com.ic.gui.STVChart;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by peter on 12/13/2015.
 */
public class Main2 extends JFrame implements WindowListener {

    public static Logger log = Logger.getLogger(Main2.class.getName());

    private BorderLayout borderLayout1 = new BorderLayout();
    private STVChart chartPanel = null;
    private Container originalContainer = null;

    public static void main(String args[]) {
        Controller.getInstance().lanuch();
    }

    public void lanuch() {
        setVisible(true);
        setBounds(0, 0, 1280, 960);
        setResizable(true);
        setTitle("IC");
    }

    public Main2() {
        try {
            configure();
        } catch (Exception e) {
            log.warning(e.getMessage());
            log.log(Level.WARNING, "Fatal error when launching IC.", e);
            System.exit(1);
        }
    }

    public void setChartPanel(STVChart pnChart) {
        chartPanel = pnChart;
        originalContainer = pnChart.getParent();
        this.getContentPane().add(pnChart, BorderLayout.CENTER);
    }

    private void configure() throws Exception {
        this.getContentPane().setLayout(borderLayout1);

        STVChart coreChart = new STVChart();

        coreChart.chartOptionBar1.btTA.setButtonImage(new ImageIcon(getClass().getResource("/chartTypeA.png")).getImage());
        coreChart.chartOptionBar1.btVolume.setButtonImage(new ImageIcon(getClass().getResource("/chartTypeC.png")).getImage());
        coreChart.btFacebookShare.setButtonImage(new ImageIcon(getClass().getResource("/facebook.png")).getImage());
        coreChart.getBtPrinter().setButtonImage(new ImageIcon(getClass().getResource("/print.png")).getImage());
        coreChart.fCompareBar.getCloseButton().setButtonImage(new ImageIcon(getClass().getResource("/close.png")).getImage());
        coreChart.fCompareBar.getAddButton().setButtonImage(new ImageIcon(getClass().getResource("/add.png")).getImage());
        coreChart.fCompareBar.getRemoveButton().setButtonImage(new ImageIcon(getClass().getResource("/minus.png")).getImage());
        setIconImage(new ImageIcon(getClass().getResource("/ICChart.png")).getImage());


        // Init the chart screen
        coreChart.chartScreen1.initScreen();
        coreChart.chartScreen2.initScreen();
        coreChart.chartScreen3.initScreen();

        ChartDataService.getInstance().enable();

        // add a chart data
        RequestCommand fc = new RequestCommand(2800, RequestCommand.TYPE_DOWNLOAD_LEFT_CHART, (CommandType) coreChart.fmenuBar.chDuration.getSelectedItem(), "LMain1", 500, 1, false, coreChart.fmenuBar);
        ChartDataService.getInstance().addCommand(fc);
        coreChart.chartScreen1.setScreenState(ChartScreen.LOADING);
        coreChart.chartScreen2.setScreenState(ChartScreen.LOADING);
        coreChart.chartScreen3.setScreenState(ChartScreen.LOADING);

        // change the language by calling set language function
        coreChart.setLanguage(FConfig.constEnglish);
        this.setChartPanel(coreChart);
        this.addWindowListener(this);
    }

    public void windowClosing(WindowEvent e) {
        this.dispose();
        System.exit(0);
    }

    public void windowOpened(WindowEvent e) {
        log.finest("closed");
    }

    public void windowClosed(WindowEvent e) {
        log.finest("closed");
    }

    public void windowIconified(WindowEvent e) {
        log.finest("iconified");
    }

    public void windowDeiconified(WindowEvent e) {
        log.finest("deiconified");
    }

    public void windowActivated(WindowEvent e) {
        log.finest("activated");
    }

    public void windowDeactivated(WindowEvent e) {
        log.finest("deactivated");
    }


}