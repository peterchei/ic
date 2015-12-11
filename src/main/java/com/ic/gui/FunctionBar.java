package com.ic.gui;

import com.ic.core.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class FunctionBar extends JPanel implements ScreenActionListener {

    private static final long serialVersionUID = -5791728889310760870L;
    private ImageButton btNone = new ImageButton();
    private ImageButton btWatch = new ImageButton();
    private ImageButton btZoomIn = new ImageButton();
    private ImageButton btZoomOut = new ImageButton();
    private ImageButton btMove = new ImageButton();
    private ImageButton btInsertLine = new ImageButton();
    private ImageButton btInsertPLine = new ImageButton();
    private ImageButton btGPartition = new ImageButton();
    private ImageButton btRemoveLine = new ImageButton();
    private ImageButton btClear = new ImageButton();
    private ImageButton btCompare = new ImageButton();
    private ImageButton btSetting = new ImageButton();
    // the reference of bars
    private MenuBar fMenuBar = null;
    private CompareBar fCompareBar = null;
    //the reference of chartscreens which this buttonbar can control it.
    private ChartScreen chartScreen1 = null;
    private ChartScreen chartScreen2 = null;
    private ChartScreen chartScreen3 = null;
    private ArrayList<ChartScreen> screens = new ArrayList<ChartScreen>();
    private int language = FConfig.constEnglish;
    private SettingDialog settingWindow1 = new SettingDialog();

    public FunctionBar() {
        try {
            jbInit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setMenus(CompareBar fc, MenuBar fm) {
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

        //getBtNone().setBackground(Color.gray);
        getBtNone().setBounds(new Rectangle(9, 2, FConfig.BUTTON_SIZE, FConfig.BUTTON_SIZE));
        getBtNone().addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                btNone_actionPerformed(e);
            }
        });
        getBtNone().setLabel("FImageButton1");
        this.setLayout(null);
        //getBtWatch().setBackground(Color.gray);
        getBtWatch().setBounds(new Rectangle(9 + (FConfig.BUTTON_SIZE + 1) * 1, 2, FConfig.BUTTON_SIZE, FConfig.BUTTON_SIZE));
        getBtWatch().addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                btWatch_actionPerformed(e);
            }
        });
        getBtWatch().setLabel("FImageButton2");
        //getBtZoomIn().setBackground(Color.gray);
        getBtZoomIn().setBounds(new Rectangle(9 + (FConfig.BUTTON_SIZE + 1) * 2, 2, FConfig.BUTTON_SIZE, FConfig.BUTTON_SIZE));
        getBtZoomIn().addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                btZoomIn_actionPerformed(e);
            }
        });
        getBtZoomIn().setLabel("FImageButton3");
        //getBtZoomOut().setBackground(Color.gray);
        getBtZoomOut().setBounds(new Rectangle(9 + (FConfig.BUTTON_SIZE + 1) * 3, 2, FConfig.BUTTON_SIZE, FConfig.BUTTON_SIZE));
        getBtZoomOut().addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                btZoomOut_actionPerformed(e);
            }
        });
        getBtZoomOut().setLabel("FImageButton4");
        //getBtMove().setBackground(Color.gray);
        getBtMove().setBounds(new Rectangle(9 + (FConfig.BUTTON_SIZE + 1) * 4, 2, FConfig.BUTTON_SIZE, FConfig.BUTTON_SIZE));
        getBtMove().addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                btMove_actionPerformed(e);
            }
        });
        getBtMove().setLabel("FImageButton5");
        getBtInsertLine().setLabel("FImageButton5");
        //getBtInsertLine().setBackground(Color.gray);
        getBtInsertLine().setBounds(new Rectangle(9 + (FConfig.BUTTON_SIZE + 1) * 5, 2, FConfig.BUTTON_SIZE, FConfig.BUTTON_SIZE));
        getBtInsertLine().addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                btInsertLine_actionPerformed(e);
            }
        });
        getBtInsertPLine().setLabel("FImageButton5");
        //getBtInsertPLine().setBackground(Color.gray);
        getBtInsertPLine().setBounds(new Rectangle(9 + (FConfig.BUTTON_SIZE + 1) * 6, 2, FConfig.BUTTON_SIZE, FConfig.BUTTON_SIZE));
        getBtInsertPLine().addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                btInsertPLine_actionPerformed(e);
            }
        });
        getBtGPartition().setLabel("FImageButton5");
        //getBtGPartition().setBackground(Color.gray);
        getBtGPartition().setBounds(new Rectangle(9 + (FConfig.BUTTON_SIZE + 1) * 7, 2, FConfig.BUTTON_SIZE, FConfig.BUTTON_SIZE));
        getBtGPartition().addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                btGPartition_actionPerformed(e);
            }
        });
        getBtRemoveLine().setLabel("FImageButton5");
        //getBtRemoveLine().setBackground(Color.gray);
        getBtRemoveLine().setBounds(new Rectangle(9 + (FConfig.BUTTON_SIZE + 1) * 8, 2, FConfig.BUTTON_SIZE, FConfig.BUTTON_SIZE));
        getBtRemoveLine().addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                btRemoveLine_actionPerformed(e);
            }
        });

        getBtClear().setLabel("FImageButton5");
        //getBtClear().setBackground(Color.gray);
        getBtClear().setBounds(new Rectangle(9 + (FConfig.BUTTON_SIZE + 1) * 9, 2, FConfig.BUTTON_SIZE, FConfig.BUTTON_SIZE));
        getBtClear().addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                btClear_actionPerformed(e);
            }
        });

        getBtCompare().setLabel("Compare Button");
        //getBtCompare().setBackground(Color.gray);
        getBtCompare().setBounds(new Rectangle(9 + (FConfig.BUTTON_SIZE + 1) * 10, 2, FConfig.BUTTON_SIZE, FConfig.BUTTON_SIZE));
        getBtCompare().addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                btCompare_actionPerformed(e);
            }
        });


        getBtSetting().setLabel("FImageButton5");
        //getBtSetting().setBackground(Color.gray);
        getBtSetting().setBounds(new Rectangle(9 + (FConfig.BUTTON_SIZE + 1) * 11, 2, FConfig.BUTTON_SIZE, FConfig.BUTTON_SIZE));
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
                screen.getAction().setActionType(ActionCommand.Type.NONEACTION);
            }
        }

    }

    void btWatch_actionPerformed(ActionEvent e) {
        for (ChartScreen screen : screens) {
            if (screen != null) {
                screen.getAction().setActionType(ActionCommand.Type.WATCH);
            }
        }
    }

    void btZoomIn_actionPerformed(ActionEvent e) {
        if (chartScreen1 != null) {
            chartScreen1.getAction().setActionType(ActionCommand.Type.ZOOMIN);
        }

        if (chartScreen2 != null) {
            chartScreen2.getAction().setActionType(ActionCommand.Type.NONEACTION);
        }

        if (chartScreen3 != null) {
            chartScreen3.getAction().setActionType(ActionCommand.Type.NONEACTION);
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
                screen.getAction().setActionType(ActionCommand.Type.MOVECHART);
            }
        }

    }

    void btInsertLine_actionPerformed(ActionEvent e) {
        for (ChartScreen screen : screens) {
            if (screen != null) {
                screen.getAction().setActionType(ActionCommand.Type.INSERTLINE);
            }
        }
    }

    void btInsertPLine_actionPerformed(ActionEvent e) {
        for (ChartScreen screen : screens) {
            if (screen != null) {
                screen.getAction().setActionType(ActionCommand.Type.INSERTPARALLELLINE);
            }
        }
    }

    void btGPartition_actionPerformed(ActionEvent e) {
        for (ChartScreen screen : screens) {
            if (screen != null) {
                screen.getAction().setActionType(ActionCommand.Type.GOLDENPARTITION);
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
                    chartScreen1.getAction().getLineRecords().removeAllElements();
                    chartScreen1.getAction().setGoldenPartitionLine(null);
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

    public void OnZoomCompleted(Object actionChartScreen, int startIndex, int endIndex) {
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
                screen.getAction().getLineRecords().removeAllElements();
                screen.getAction().setGoldenPartitionLine(null);
                screen.repaint();
            }
        }
    }

    public ImageButton getBtNone() {
        return btNone;
    }

    public ImageButton getBtWatch() {
        return btWatch;
    }

    public ImageButton getBtZoomIn() {
        return btZoomIn;
    }

    public ImageButton getBtZoomOut() {
        return btZoomOut;
    }

    public ImageButton getBtMove() {
        return btMove;
    }

    public ImageButton getBtInsertLine() {
        return btInsertLine;
    }

    public ImageButton getBtInsertPLine() {
        return btInsertPLine;
    }

    public ImageButton getBtGPartition() {
        return btGPartition;
    }

    public ImageButton getBtRemoveLine() {
        return btRemoveLine;
    }

    public ImageButton getBtClear() {
        return btClear;
    }

    public ImageButton getBtCompare() {
        return btCompare;
    }

    public ImageButton getBtSetting() {
        return btSetting;
    }
}
