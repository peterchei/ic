package com.ic.app;

import com.ic.core.ChartScreen;
import com.ic.core.FConfig;
import com.ic.data.ChartDataService;
import com.ic.data.CommandType;
import com.ic.data.RequestCommand;
import com.ic.gui.STVChart;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
* Main entry point
*
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


        JPanel glassPanel = new JPanel();
        ImageIcon icon1 = new ImageIcon(getClass().getResource("/UpLeftArrow.png"));
        ImageIcon icon2 = new ImageIcon(getClass().getResource("/UpRightArrow.png"));
        ImageIcon icon3 = new ImageIcon(getClass().getResource("/DownLeftArrow.png"));
        ImageIcon icon4 = new ImageIcon(getClass().getResource("/DownRightArrow.png"));


        JLabel jLabel1 = new javax.swing.JLabel();
        JLabel jLabel2 = new javax.swing.JLabel();
        JLabel jLabel3 = new javax.swing.JLabel();
        JLabel jLabel4 = new javax.swing.JLabel();



        jLabel1.setIcon(icon1);
        jLabel2.setIcon(icon2);
        jLabel3.setIcon(icon3);
        jLabel4.setIcon(icon4);

        glassPanel.setLayout(new java.awt.GridLayout(2,2));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel1.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        glassPanel.add(jLabel1);

        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel2.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        glassPanel.add(jLabel2);

        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel3.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        glassPanel.add(jLabel3);

        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel4.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        glassPanel.add(jLabel4);


        setGlassPane(glassPanel);

        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                super.componentResized(e);
                if (Main2.this.getBounds().width <= 400 || Main2.this.getBounds().height <=400) {
                    getGlassPane().setVisible(true);
                } else {
                    getGlassPane().setVisible(false);
                }
            }
        });
        //jp.setVisible(true);

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
        RequestCommand fc = new RequestCommand(2800, RequestCommand.TYPE_DOWNLOAD_LEFT_CHART,
                (CommandType) coreChart.fmenuBar.chDuration.getSelectedItem(), "LMain1", 500, 1, false, coreChart.fmenuBar);

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