package teletext.apps.ui;

import teletext.apps.core.ChartScreen;
import teletext.apps.core.ChartItem;
import teletext.apps.core.ChartType;
import teletext.apps.core.FAction;
import teletext.apps.core.ScreenActionListener;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class FButtonBar extends TabBar implements ScreenActionListener {

    /**
     *
     */
    private static final long serialVersionUID = -5791728889310760870L;
    public FImageButton btNone = new FImageButton();
    public FImageButton btWatch = new FImageButton();
    public FImageButton btZoomIn = new FImageButton();
    public FImageButton btZoomOut = new FImageButton();
    public FImageButton btMove = new FImageButton();
    public FImageButton btInsertLine = new FImageButton();
    public FImageButton btInsertPLine = new FImageButton();
    public FImageButton btGPartition = new FImageButton();
    public FImageButton btRemoveLine = new FImageButton();
    public FImageButton btClear = new FImageButton();
    public FImageButton btCompare = new FImageButton();
    public FImageButton btSetting = new FImageButton();
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

    public FButtonBar() {
        try {
            jbInit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setLanguage(int tlanguage) {
        language = tlanguage;
        settingWindow1.setLanguage(language);
    }

    private void jbInit() throws Exception {

        btNone.setBackground(Color.gray);
        btNone.setBounds(new Rectangle(9, 2, 22, 22));
        btNone.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                btNone_actionPerformed(e);
            }
        });
        btNone.setLabel("FImageButton1");
        this.setLayout(null);
        btWatch.setBackground(Color.gray);
        btWatch.setBounds(new Rectangle(32, 2, 22, 22));
        btWatch.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                btWatch_actionPerformed(e);
            }
        });
        btWatch.setLabel("FImageButton2");
        btZoomIn.setBackground(Color.gray);
        btZoomIn.setBounds(new Rectangle(55, 2, 22, 22));
        btZoomIn.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                btZoomIn_actionPerformed(e);
            }
        });
        btZoomIn.setLabel("FImageButton3");
        btZoomOut.setBackground(Color.gray);
        btZoomOut.setBounds(new Rectangle(78, 2, 22, 22));
        btZoomOut.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                btZoomOut_actionPerformed(e);
            }
        });
        btZoomOut.setLabel("FImageButton4");
        btMove.setBackground(Color.gray);
        btMove.setBounds(new Rectangle(101, 2, 22, 22));
        btMove.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                btMove_actionPerformed(e);
            }
        });
        btMove.setLabel("FImageButton5");
        btInsertLine.setLabel("FImageButton5");
        btInsertLine.setBackground(Color.gray);
        btInsertLine.setBounds(new Rectangle(124, 2, 22, 22));
        btInsertLine.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                btInsertLine_actionPerformed(e);
            }
        });
        btInsertPLine.setLabel("FImageButton5");
        btInsertPLine.setBackground(Color.gray);
        btInsertPLine.setBounds(new Rectangle(147, 2, 22, 22));
        btInsertPLine.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                btInsertPLine_actionPerformed(e);
            }
        });
        btGPartition.setLabel("FImageButton5");
        btGPartition.setBackground(Color.gray);
        btGPartition.setBounds(new Rectangle(170, 2, 22, 22));
        btGPartition.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                btGPartition_actionPerformed(e);
            }
        });
        btRemoveLine.setLabel("FImageButton5");
        btRemoveLine.setBackground(Color.gray);
        btRemoveLine.setBounds(new Rectangle(193, 2, 22, 22));
        btRemoveLine.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                btRemoveLine_actionPerformed(e);
            }
        });

        btClear.setLabel("FImageButton5");
        btClear.setBackground(Color.gray);
        btClear.setBounds(new Rectangle(216, 2, 22, 22));
        btClear.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                btClear_actionPerformed(e);
            }
        });

        btCompare.setLabel("Compare Button");
        btCompare.setBackground(Color.gray);
        btCompare.setBounds(new Rectangle(239, 2, 22, 22));
        btCompare.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                btCompare_actionPerformed(e);
            }
        });


        btSetting.setLabel("FImageButton5");
        btSetting.setBackground(Color.gray);
        btSetting.setBounds(new Rectangle(263, 2, 22, 22));
        btSetting.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                btSetting_actionPerformed(e);
            }
        });

        this.setBackground(SystemColor.control);
        this.add(btNone, null);
        this.add(btWatch, null);
        this.add(btZoomIn, null);
        this.add(btZoomOut, null);
        this.add(btMove, null);
        this.add(btInsertLine, null);
        this.add(btInsertPLine, null);
        this.add(btGPartition, null);
        this.add(btRemoveLine, null);
        this.add(btClear, null);
        this.add(btCompare, null);
        this.add(btSetting, null);
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
                    lcchart.chartType = ChartType.PERCENTAGE;
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
}
