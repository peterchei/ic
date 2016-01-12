package com.ic.gui;

import com.ic.core.ChartScreen;
import com.ic.core.FConfig;
import com.ic.util.BasicPrint;
import com.ic.util.CopyImageToClipBoard;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class STVChart extends JPanel {

    private static final long serialVersionUID = -280148111733446555L;
    public JPanel panelToolbar = new JPanel();
    public ChartScreen chartScreen1 = new ChartScreen(20, 35, 42, 15); //the first chartscreen
    public ChartScreen chartScreen2 = new ChartScreen(15, 5, 42, 15); //the second chartscreen
    public ChartScreen chartScreen3 = new ChartScreen(15, 5, 42, 15); //the third chartscreen
    //public FunctionBar fbuttonBar = new FunctionBar();   //the button bar
    public FunctionPanel functionPanel = new FunctionPanel();
    public MenuBar fmenuBar = new MenuBar();//the menu bar
    public CompareBar fCompareBar = new CompareBar();
    public TAMenu fTAMenu1 = new TAMenu();
   // public ImageButton btOpenClose = new ImageButton();
   // public FloatingChartWindow chartWindow = new FloatingChartWindow();  // the popup window.
    // the printer object to print the graphic.....
    private BasicPrint basicPrinter;
    private ImageButton btPrinter = new ImageButton();
    public ImageButton btFacebookShare = new ImageButton();

    public ChartOptionBar chartOptionBar1 = new ChartOptionBar();
    private int language = FConfig.constEnglish;

    public STVChart() {
        try {
            jbInit();



            chartOptionBar1.setChartScreen(chartScreen1, chartScreen2, chartScreen3);
            fmenuBar.setChartScreen(chartScreen1, chartScreen2, chartScreen3);
            fmenuBar.setTAMenu(fTAMenu1);
            fCompareBar.setMenus(functionPanel, fmenuBar);
            fCompareBar.setChartScreen(chartScreen1, chartScreen2, chartScreen3);
            fTAMenu1.setChartScreen(chartScreen2);


            functionPanel.setChartScreen(chartScreen1, chartScreen2, chartScreen3);
            functionPanel.setMenus(fCompareBar, fmenuBar);

            chartScreen1.addScreenActionListen(functionPanel);
            chartScreen2.addScreenActionListen(functionPanel);
            chartScreen3.addScreenActionListen(functionPanel);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void resizeChartScreen(int x, int y, int w, int h) {


        btFacebookShare.setBounds(w - (FConfig.BUTTON_SIZE) * 1, 0, FConfig.BUTTON_SIZE, FConfig.BUTTON_SIZE);
        getBtPrinter().setBounds(w - (FConfig.BUTTON_SIZE) * 2, 0, FConfig.BUTTON_SIZE, FConfig.BUTTON_SIZE);
        chartOptionBar1.setBounds(w - (FConfig.BUTTON_SIZE) * 4, 0, FConfig.BUTTON_SIZE * 2, FConfig.BUTTON_SIZE);


        int xx = h - (FConfig.BUTTON_SIZE + 2);
        xx = xx / 4;

        int x1 = (int) (xx * 1.8);
        int x2 = (int) (xx * 1.2);
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

        fmenuBar.setBounds(new Rectangle(0, 0, 450, FConfig.BUTTON_SIZE));

        panelToolbar.setBounds(new Rectangle(0, 0, w, (FConfig.BUTTON_SIZE + 2)));
        chartScreen1.setBounds(new Rectangle(FConfig.BUTTON_SIZE, (FConfig.BUTTON_SIZE + 2), w - FConfig.BUTTON_SIZE, x1));
        chartScreen2.setBounds(new Rectangle(FConfig.BUTTON_SIZE, (FConfig.BUTTON_SIZE + 2) + x1, w - FConfig.BUTTON_SIZE, x2));
        chartScreen3.setBounds(new Rectangle(FConfig.BUTTON_SIZE, (FConfig.BUTTON_SIZE + 2) + x1 + x2, w - FConfig.BUTTON_SIZE, x3));

        chartScreen1.getActionCommand().getLineRecords().removeAllElements();
        chartScreen2.getActionCommand().getLineRecords().removeAllElements();
        chartScreen3.getActionCommand().getLineRecords().removeAllElements();
        chartScreen1.getActionCommand().getZoomRecords().removeAllElements();
        chartScreen2.getActionCommand().getZoomRecords().removeAllElements();
        chartScreen3.getActionCommand().getZoomRecords().removeAllElements();
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
        setBackground(FConfig.ScreenBackground);
        this.setLayout(null);
        panelToolbar.setBackground(FConfig.ToolBarColor);
        panelToolbar.setLayout(null);

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

        panelToolbar.setBounds(new Rectangle(0, 0, 1024, (FConfig.BUTTON_SIZE) * 2));
        chartScreen1.setBounds(new Rectangle(1, (FConfig.BUTTON_SIZE + 2), 600, x1));
        chartScreen2.setBounds(new Rectangle(1, (FConfig.BUTTON_SIZE + 2) + x1, 600, x2));
        chartScreen3.setBounds(new Rectangle(1, (FConfig.BUTTON_SIZE + 2) + x1 + x2, 600, x3));

        fmenuBar.setBackground(FConfig.ToolBarColor);
        fmenuBar.setBounds(new Rectangle(0, 0, 400, FConfig.BUTTON_SIZE));
        functionPanel.setLayout(null);
        functionPanel.setBounds(new Rectangle(0, FConfig.BUTTON_SIZE, FConfig.BUTTON_SIZE, 9 + (FConfig.BUTTON_SIZE + 1) * 16));

        fCompareBar.setBounds(new Rectangle(1, 0, 436, FConfig.BUTTON_SIZE + 2));
        fCompareBar.setVisible(false);
        fCompareBar.setBackground(FConfig.ToolBarColor);
        fTAMenu1.setBackground(FConfig.ToolBarColor);
        fTAMenu1.setBounds(new Rectangle(450, 0, FConfig.BUTTON_SIZE + 100, FConfig.BUTTON_SIZE + 2));
        btFacebookShare.setBounds(new Rectangle(571, 0, FConfig.BUTTON_SIZE + 2, FConfig.BUTTON_SIZE + 2));
        btFacebookShare.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                btFacebookShare_actionPerformed(e);
            }
        });

        getBtPrinter().setBounds(new Rectangle(548, 0, FConfig.BUTTON_SIZE, FConfig.BUTTON_SIZE));
        getBtPrinter().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    //create printer object and connect to the printer
                    setBasicPrinter(new BasicPrint());
                    if (getBasicPrinter().initPrinter(chartScreen1, chartScreen2, chartScreen3) == true) {
                        getBasicPrinter().startPrint();
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });


        chartOptionBar1.setBounds(571 - (FConfig.BUTTON_SIZE) * 4, 0, FConfig.BUTTON_SIZE * 2, FConfig.BUTTON_SIZE);

        this.add(chartScreen1, null);
        this.add(panelToolbar, null);
        panelToolbar.add(fmenuBar, null);
        panelToolbar.add(fCompareBar, null);
        panelToolbar.add(btFacebookShare, null);
        panelToolbar.add(getBtPrinter(), null);
        panelToolbar.add(chartOptionBar1, null);
        panelToolbar.add(fTAMenu1, null);
        this.add(functionPanel, null);
        this.add(chartScreen2, null);
        this.add(chartScreen3, null);
    }

    void btFacebookShare_actionPerformed(ActionEvent e) {

        try {
            if (Desktop.isDesktopSupported()) {
                new CopyImageToClipBoard(chartScreen1.getAllscreenImage());
                Desktop.getDesktop().browse(new URI("http://www.facebook.com"));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    //change the language
    public void setLanguage(int tlanguage) {
        language = tlanguage;
      //  chartScreen1.setLanguage(language);
        //chartScreen2.setLanguage(language);
       // chartScreen3.setLanguage(language);

    }


    public ImageButton getBtFacebookShare() {
        return btFacebookShare;
    }

    public void setBtFacebookShare(ImageButton btFacebookShare) {
        this.btFacebookShare = btFacebookShare;
    }

    public BasicPrint getBasicPrinter() {
        return basicPrinter;
    }

    public void setBasicPrinter(BasicPrint basicPrinter) {
        this.basicPrinter = basicPrinter;
    }

    public ImageButton getBtPrinter() {
        return btPrinter;
    }

    public void setBtPrinter(ImageButton btPrinter) {
        this.btPrinter = btPrinter;
    }
}
