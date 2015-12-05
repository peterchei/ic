package com.ic.gui;

import com.ic.core.ChartScreen;
import com.ic.core.FConfig;
import com.ic.util.BasicPrint;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FMEChartA extends JPanel {

    private static final long serialVersionUID = -280148111733446555L;
    public JPanel panelToolbar = new JPanel();
    public ChartScreen chartScreen1 = new ChartScreen(20, 20, 42, 25); //the first chartscreen
    public ChartScreen chartScreen2 = new ChartScreen(20, 5, 42, 25); //the second chartscreen
    public ChartScreen chartScreen3 = new ChartScreen(15, 5, 42, 25); //the third chartscreen
    public FButtonBar fbuttonBar = new FButtonBar();   //the button bar
    public FMenuBar fmenuBar = new FMenuBar();//the menu bar
    public FCompareBar fCompareBar = new FCompareBar();
    public FTAMenu fTAMenu1 = new FTAMenu();
    public FImageButton btOpenClose = new FImageButton();
    public FMEChartWindow chartWindow = new FMEChartWindow();  // the popup window.
    // the printer object to print the graphic.....
    public BasicPrint basicPrinter;
    public FImageButton btPrinter = new FImageButton();
    public ChartOptionBar chartOptionBar1 = new ChartOptionBar();
    private int language = FConfig.constEnglish;

    public FMEChartA() {
        try {
            jbInit();
            //set the reference of chartscreen
            fbuttonBar.setChartScreen(chartScreen1, chartScreen2, chartScreen3);
            fbuttonBar.setMenus(fCompareBar, fmenuBar);
            chartOptionBar1.setChartScreen(chartScreen1, chartScreen2, chartScreen3);
            //add ScreenActionListen to the buttonbar;

            fmenuBar.setChartScreen(chartScreen1, chartScreen2, chartScreen3);
            //fmenuBar.setChartSource(chartSource);
            fmenuBar.setTAMenu(fTAMenu1);
            fCompareBar.setMenus(fbuttonBar, fmenuBar);
            fCompareBar.setChartScreen(chartScreen1, chartScreen2, chartScreen3);
            // fCompareBar.setChartSource(chartSource);
            fTAMenu1.setChartScreen(chartScreen2);


            chartScreen1.addScreenActionListen(fbuttonBar);
            chartScreen2.addScreenActionListen(fbuttonBar);
            chartScreen3.addScreenActionListen(fbuttonBar);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void resizeChartScreen(int x, int y, int w, int h) {


        //System.out.print("FMEChartA resized.");
        if (this.btOpenClose != null) {
            btOpenClose.setBounds(w - 23, 0, 22, 22);
        }

        if (this.btPrinter != null) {
            if (btOpenClose.isVisible()) {
                btPrinter.setBounds(w - 44, 0, 22, 22);
            } else {
                btPrinter.setBounds(w - 23, 0, 22, 22);
            }
        }

        int xx = h - 52 - 50;
        xx = xx / 4;

        int x1 = (int) (xx * 1.5d + 50);
        int x2 = (int) (xx * 1.5d);
        int x3 = xx;


        if (!chartScreen1.isVisible()) {
            System.out.println("s1 is zero");
            if (chartScreen2.isVisible()) {
                x2 = x2 + x1;
                x1 = 0;
            } else if (chartScreen3.isVisible()) {
                x3 = x3 + x1;
                x1 = 0;
            } else {
                x1 = 0;
                System.out.println("s1 is zero");
            }
        }

        if (!chartScreen2.isVisible()) {
            if (chartScreen1.isVisible()) {
                x1 = x1 + x2;
                x2 = 0;
            } else if (chartScreen3.isVisible()) {
                x3 = x3 + x2;
                x2 = 0;
            } else {
                x2 = 0;
            }

        }

        if (!chartScreen3.isVisible()) {
            if (chartScreen1.isVisible()) {
                x1 = x1 + x3;
                x3 = 0;
            } else if (chartScreen2.isVisible()) {
                x2 = x2 + x3;
                x3 = 0;
            } else {
                x3 = 0;
            }

        }

        panelToolbar.setBounds(new Rectangle(0, 0, w, 52));
        chartScreen1.setBounds(new Rectangle(1, 52, w, x1));
        chartScreen2.setBounds(new Rectangle(1, 52 + x1, w, x2));
        chartScreen3.setBounds(new Rectangle(1, 52 + x1 + x2, w, x3));

        chartScreen1.getFaction().lineRecords.removeAllElements();
        chartScreen2.getFaction().lineRecords.removeAllElements();
        chartScreen3.getFaction().lineRecords.removeAllElements();
        chartScreen1.getFaction().zoomRecords.removeAllElements();
        chartScreen2.getFaction().zoomRecords.removeAllElements();
        chartScreen3.getFaction().zoomRecords.removeAllElements();
        chartScreen1.undoZoom();
        chartScreen2.undoZoom();
        chartScreen3.undoZoom();

    }

    @Override
    public void setBounds(int x, int y, int w, int h) {
        super.setBounds(x, y, w, h);
        resizeChartScreen(x, y, w, h);
    }

    private void jbInit() throws Exception {

        this.setLayout(null);
        panelToolbar.setBackground(FConfig.ToolBarColor);
        panelToolbar.setLayout(null);

        //chartScreen1.setBounds(new Rectangle(1, 53, 500, 178));
        //chartScreen2.setBounds(new Rectangle(1, 231, 500, 125));
        //chartScreen3.setBounds(new Rectangle(1, 356, 500, 125));


        int x = 400 - 52 - 50;
        x = x / 3;

        int x1 = x + 50;
        int x2 = x;
        int x3 = x;

        if (!chartScreen1.isVisible()) {
            x2 = x2 + x1;
        }

        if (!chartScreen2.isVisible()) {
            x1 = x1 + x2;
            x2 = 0;
        }

        if (!chartScreen3.isVisible()) {
            x1 = x1 + x3;
            x3 = 0;
        }


        panelToolbar.setBounds(new Rectangle(0, 0, 1024, 52));
        chartScreen1.setBounds(new Rectangle(1, 53, 600, x1));
        chartScreen2.setBounds(new Rectangle(1, 53 + x1, 600, x2));
        chartScreen3.setBounds(new Rectangle(1, 53 + x1 + x2, 600, x3));

        fbuttonBar.setBackground(FConfig.ToolBarColor);
        fbuttonBar.setBounds(new Rectangle(1, 26, 286, 26));
        fbuttonBar.setLayout(null);
        fmenuBar.setBackground(FConfig.ToolBarColor);
        fmenuBar.setBounds(new Rectangle(1, 0, 295, 26));

        fCompareBar.setBounds(new Rectangle(1, 0, 436, 26));
        fCompareBar.setVisible(false);
        fCompareBar.setBackground(FConfig.ToolBarColor);
        fTAMenu1.setBackground(FConfig.ToolBarColor);
        fTAMenu1.setBounds(new Rectangle(288, 27, 114, 24));
        btOpenClose.setBounds(new Rectangle(571, 0, 22, 22));
        btOpenClose.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                btOpenClose_actionPerformed(e);
            }
        });
        btOpenClose.setLabel("fImageButton1");
        btOpenClose.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btOpenClose_actionPerformed(e);
            }
        });
        btPrinter.setBounds(new Rectangle(548, 0, 23, 22));
        btPrinter.setLabel("fImageButton1");
        btPrinter.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                btPrinter_actionPerformed(e);
            }
        });
        chartOptionBar1.setPreferredSize(new Dimension(1, 10));
        chartOptionBar1.setBounds(new Rectangle(299, 1, 215, 25));
        chartOptionBar1.setBackground(FConfig.ToolBarColor);
        this.add(chartScreen1, null);
        this.add(panelToolbar, null);
        panelToolbar.add(fmenuBar, null);
        panelToolbar.add(fbuttonBar, null);
        panelToolbar.add(fCompareBar, null);
        panelToolbar.add(btOpenClose, null);
        panelToolbar.add(btPrinter, null);
        panelToolbar.add(chartOptionBar1, null);
        panelToolbar.add(fTAMenu1, null);

        this.add(chartScreen2, null);
        this.add(chartScreen3, null);

    }

    void btOpenClose_actionPerformed(ActionEvent e) {

        btOpenClose.setVisible(false);
        chartWindow.setChartPanel(this);
        chartWindow.setBounds(0, 0, 800, 600);
        chartWindow.setResizable(true);
        chartWindow.setTitle("FME Chart Window");
        chartWindow.show();

    }

    //change the language
    public void SetLanguage(int tlanguage) {
        language = tlanguage;
        chartScreen1.setLanguage(language);
        chartScreen2.setLanguage(language);
        chartScreen3.setLanguage(language);
        fmenuBar.setLanguage(language);
        fCompareBar.setLanguage(language);
        fTAMenu1.setLanguage(language);
        fbuttonBar.setLanguage(language);
    }

    void btPrinter_actionPerformed(ActionEvent e) {
        try {
            //create printer object and connect to the printer
            basicPrinter = new BasicPrint();
            if (basicPrinter.initPrinter(chartScreen1, chartScreen2, chartScreen3) == true) {
                basicPrinter.startPrint();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
