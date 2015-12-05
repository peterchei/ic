package com.ic.gui;

import com.ic.core.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class FButtonBar extends TabBar implements ScreenActionListener {

    private static final long serialVersionUID = -5791728889310760870L;
    private FImageButton btNone = new FImageButton();
    private FImageButton btWatch = new FImageButton();
    private FImageButton btZoomIn = new FImageButton();
    private FImageButton btZoomOut = new FImageButton();
    private FImageButton btMove = new FImageButton();
    private FImageButton btInsertLine = new FImageButton();
    private FImageButton btInsertPLine = new FImageButton();
    private FImageButton btGPartition = new FImageButton();
    private FImageButton btRemoveLine = new FImageButton();
    private FImageButton btClear = new FImageButton();
    private FImageButton btCompare = new FImageButton();
    private FImageButton btSetting = new FImageButton();
    // the reference of bars
    private FMenuBar fMenuBar = null;
    private FCompareBar fCompareBar = null;
    //the reference of chartscreens which this buttonbar can control it.
    private ChartScreen chartScreen1 = null;
    private ChartScreen chartScreen2 = null;
    private ChartScreen chartScreen3 = null;
    private ArrayList<ChartScreen> screens = new ArrayList<ChartScreen>();
    private int language = FConfig.constEnglish;
    private FSettingDialog settingWindow1 = new FSettingDialog();

    public FButtonBar() {
        try {
            jbInit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setMenus(FCompareBar fc, FMenuBar fm) {
        fCompareBar = fc;
        fMenuBar = fm;
    }

    //set the reference of the chartScrenn such that the button bar can control it.
    public void setChartScreen(ChartScreen cs1, ChartScreen cs2, ChartScreen cs3) {
        chartScreen1 = cs1;
        chartScreen2 = cs2;
        chartScreen3 = cs3;
        screens.add(cs1);
        screens.add(cs2);
        screens.add(cs3);
    }

    public void setLanguage(int tlanguage) {
        language = tlanguage;
        settingWindow1.setLanguage(language);
    }

    private void jbInit() throws Exception {

        getBtNone().setBackground(Color.gray);
        getBtNone().setBounds(new Rectangle(9, 2, 22, 22));
        getBtNone().addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                btNone_actionPerformed(e);
            }
        });
        getBtNone().setLabel("FImageButton1");
        this.setLayout(null);
        getBtWatch().setBackground(Color.gray);
        getBtWatch().setBounds(new Rectangle(32, 2, 22, 22));
        getBtWatch().addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                btWatch_actionPerformed(e);
            }
        });
        getBtWatch().setLabel("FImageButton2");
        getBtZoomIn().setBackground(Color.gray);
        getBtZoomIn().setBounds(new Rectangle(55, 2, 22, 22));
        getBtZoomIn().addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                btZoomIn_actionPerformed(e);
            }
        });
        getBtZoomIn().setLabel("FImageButton3");
        getBtZoomOut().setBackground(Color.gray);
        getBtZoomOut().setBounds(new Rectangle(78, 2, 22, 22));
        getBtZoomOut().addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                btZoomOut_actionPerformed(e);
            }
        });
        getBtZoomOut().setLabel("FImageButton4");
        getBtMove().setBackground(Color.gray);
        getBtMove().setBounds(new Rectangle(101, 2, 22, 22));
        getBtMove().addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                btMove_actionPerformed(e);
            }
        });
        getBtMove().setLabel("FImageButton5");
        getBtInsertLine().setLabel("FImageButton5");
        getBtInsertLine().setBackground(Color.gray);
        getBtInsertLine().setBounds(new Rectangle(124, 2, 22, 22));
        getBtInsertLine().addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                btInsertLine_actionPerformed(e);
            }
        });
        getBtInsertPLine().setLabel("FImageButton5");
        getBtInsertPLine().setBackground(Color.gray);
        getBtInsertPLine().setBounds(new Rectangle(147, 2, 22, 22));
        getBtInsertPLine().addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                btInsertPLine_actionPerformed(e);
            }
        });
        getBtGPartition().setLabel("FImageButton5");
        getBtGPartition().setBackground(Color.gray);
        getBtGPartition().setBounds(new Rectangle(170, 2, 22, 22));
        getBtGPartition().addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                btGPartition_actionPerformed(e);
            }
        });
        getBtRemoveLine().setLabel("FImageButton5");
        getBtRemoveLine().setBackground(Color.gray);
        getBtRemoveLine().setBounds(new Rectangle(193, 2, 22, 22));
        getBtRemoveLine().addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                btRemoveLine_actionPerformed(e);
            }
        });

        getBtClear().setLabel("FImageButton5");
        getBtClear().setBackground(Color.gray);
        getBtClear().setBounds(new Rectangle(216, 2, 22, 22));
        getBtClear().addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                btClear_actionPerformed(e);
            }
        });

        getBtCompare().setLabel("Compare Button");
        getBtCompare().setBackground(Color.gray);
        getBtCompare().setBounds(new Rectangle(239, 2, 22, 22));
        getBtCompare().addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                btCompare_actionPerformed(e);
            }
        });


        getBtSetting().setLabel("FImageButton5");
        getBtSetting().setBackground(Color.gray);
        getBtSetting().setBounds(new Rectangle(263, 2, 22, 22));
        getBtSetting().addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                btSetting_actionPerformed(e);
            }
        });

        this.setBackground(SystemColor.control);
        this.add(getBtNone(), null);
        this.add(getBtWatch(), null);
        this.add(getBtZoomIn(), null);
        this.add(getBtZoomOut(), null);
        this.add(getBtMove(), null);
        this.add(getBtInsertLine(), null);
        this.add(getBtInsertPLine(), null);
        this.add(getBtGPartition(), null);
        this.add(getBtRemoveLine(), null);
        this.add(getBtClear(), null);
        this.add(getBtCompare(), null);
        this.add(getBtSetting(), null);
    }

    void btNone_actionPerformed(ActionEvent e) {

        for (ChartScreen screen : screens) {
            if (screen != null) {
                screen.getAction().currentActionType = FAction.Type.NONEACTION;
            }
        }

    }

    void btWatch_actionPerformed(ActionEvent e) {
        for (ChartScreen screen : screens) {
            if (screen != null) {
                screen.getAction().currentActionType = FAction.Type.WATCH;
            }
        }
    }

    void btZoomIn_actionPerformed(ActionEvent e) {
        if (chartScreen1 != null) {
            chartScreen1.getAction().currentActionType = FAction.Type.ZOOMIN;
        }

        if (chartScreen2 != null) {
            chartScreen2.getAction().currentActionType = FAction.Type.NONEACTION;
        }

        if (chartScreen3 != null) {
            chartScreen3.getAction().currentActionType = FAction.Type.NONEACTION;
        }
    }

    void btZoomOut_actionPerformed(ActionEvent e) {


        if (chartScreen1 != null) {
            chartScreen1.undoZoom();
            int startIndex = chartScreen1.getStartDisplayIndex();
            int endIndex = chartScreen1.getEndDisplayIndex();
            chartScreen2.zoom(startIndex, endIndex);
            chartScreen3.zoom(startIndex, endIndex);

        }
    }

    void btMove_actionPerformed(ActionEvent e) {

        for (ChartScreen screen : screens) {
            if (screen != null) {
                screen.getAction().currentActionType = FAction.Type.MOVECHART;
            }
        }

    }

    void btInsertLine_actionPerformed(ActionEvent e) {

        for (ChartScreen screen : screens) {
            if (screen != null) {
                screen.getAction().currentActionType = FAction.Type.INSERTLINE;
            }
        }

    }

    void btInsertPLine_actionPerformed(ActionEvent e) {

        for (ChartScreen screen : screens) {
            if (screen != null) {
                screen.getAction().currentActionType = FAction.Type.INSERTPARALLELLINE;
            }
        }

    }

    void btGPartition_actionPerformed(ActionEvent e) {

        for (ChartScreen screen : screens) {
            if (screen != null) {
                screen.getAction().currentActionType = FAction.Type.GOLDENPARTITION;
            }
        }


    }

    void btRemoveLine_actionPerformed(ActionEvent e) {

        for (ChartScreen screen : screens) {
            if (screen != null) {
                screen.undoInsertLine();
            }
        }

    }

    void btCompare_actionPerformed(ActionEvent e) {
        if (fCompareBar != null && fMenuBar != null) {
            ChartItem taChart = chartScreen1.getChart("TA1Chart");
            if (taChart != null) {
                taChart.setVisible(false);            // set TA chart to be invisible when in comparing state.
            }
            fCompareBar.setVisible(true);
            // fCompareBar.chACode.setVisible(true);
            //fCompareBar.chSpChart.setVisible(false);

            fMenuBar.setVisible(false);
            if (chartScreen1 != null) {
                ChartItem lcchart = chartScreen1.getLeftChart();
                if (lcchart != null) {
                    lcchart.setChartType(ChartType.PERCENTAGE);
                    chartScreen1.getAction().lineRecords.removeAllElements();
                    chartScreen1.getAction().goldenPartitionLine = null;
                    chartScreen1.updateBaseScreen();
                    chartScreen1.repaint();
                }

            }
        }
    }

    void btSetting_actionPerformed(ActionEvent e) {
        settingWindow1.setChartScreen(chartScreen1); // tell the setting window that which chartScreen he are using.
        settingWindow1.setTAChartName("TA1Chart");  // tell the setting window that which chart he can control
        settingWindow1.updateSetting();
        settingWindow1.setTitle("FME Main Chart Setting Window");
        settingWindow1.setResizable(false);
        settingWindow1.setBounds(0, 0, 380, 335);
        settingWindow1.setVisible(true);
        settingWindow1.show();
    }

    public void OnZoomCompleted(ChartScreen actionChartScreen, int startIndex, int endIndex) {

        for (ChartScreen screen : screens) {
            if (screen != null) {
                if (startIndex != screen.getStartDisplayIndex() || endIndex != screen.getEndDisplayIndex()) {
                    screen.zoom(startIndex, endIndex);
                }
            }
        }

    }

    void btClear_actionPerformed(ActionEvent e) {

        for (ChartScreen screen : screens) {
            if (screen != null) {
                screen.getAction().lineRecords.removeAllElements();
                screen.getAction().goldenPartitionLine = null;
                screen.repaint();
            }
        }


    }

    public FImageButton getBtNone() {
        return btNone;
    }

    public FImageButton getBtWatch() {
        return btWatch;
    }

    public FImageButton getBtZoomIn() {
        return btZoomIn;
    }

    public FImageButton getBtZoomOut() {
        return btZoomOut;
    }

    public FImageButton getBtMove() {
        return btMove;
    }

    public FImageButton getBtInsertLine() {
        return btInsertLine;
    }

    public FImageButton getBtInsertPLine() {
        return btInsertPLine;
    }

    public FImageButton getBtGPartition() {
        return btGPartition;
    }

    public FImageButton getBtRemoveLine() {
        return btRemoveLine;
    }

    public FImageButton getBtClear() {
        return btClear;
    }

    public FImageButton getBtCompare() {
        return btCompare;
    }

    public FImageButton getBtSetting() {
        return btSetting;
    }
}
