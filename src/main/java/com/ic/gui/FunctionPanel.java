package com.ic.gui;

import com.ic.core.*;
import com.ic.util.CopyImageToClipBoard;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * Created by peter on 12/11/2015.
 */
public class FunctionPanel extends JPanel implements ScreenActionListener {

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
    private ImageButton btCapture = new ImageButton();
    private ImageButton btEdit = new ImageButton();

    private int numberOfButtons =0;
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

    public FunctionPanel() {
        try {
            initComponents();
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

    private void addButton(Component component) {

        component.setBounds(0, (FConfig.BUTTON_SIZE + 1) * numberOfButtons, FConfig.BUTTON_SIZE, FConfig.BUTTON_SIZE);
        this.add(component, null);
        numberOfButtons ++;
    }

    private void initComponents() throws Exception {

        getBtNone().setButtonImage(new ImageIcon(getClass().getResource("/cursor.png")).getImage());
        getBtWatch().setButtonImage(new ImageIcon(getClass().getResource("/watch.png")).getImage());
        getBtZoomIn().setButtonImage(new ImageIcon(getClass().getResource("/zoomin.png")).getImage());
        getBtZoomOut().setButtonImage(new ImageIcon(getClass().getResource("/zoomout.png")).getImage());
        getBtMove().setButtonImage(new ImageIcon(getClass().getResource("/move.png")).getImage());
        getBtInsertLine().setButtonImage(new ImageIcon(getClass().getResource("/line.png")).getImage());
        getBtInsertPLine().setButtonImage(new ImageIcon(getClass().getResource("/parallelline.png")).getImage());
        getBtGPartition().setButtonImage(new ImageIcon(getClass().getResource("/goldenline.png")).getImage());
        getBtRemoveLine().setButtonImage(new ImageIcon(getClass().getResource("/undo.png")).getImage());
        getBtClear().setButtonImage(new ImageIcon(getClass().getResource("/clean.png")).getImage());
        getBtSetting().setButtonImage(new ImageIcon(getClass().getResource("/setting.png")).getImage());
        getBtCapture().setButtonImage(new ImageIcon(getClass().getResource("/capture.png")).getImage());
        getBtCompare().setButtonImage(new ImageIcon(getClass().getResource("/percentage.png")).getImage());
        getBtEdit().setButtonImage(new ImageIcon(getClass().getResource("/editText.png")).getImage());


        getBtNone().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btNone_actionPerformed(e);
            }
        });
        getBtWatch().addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                btWatch_actionPerformed(e);
            }
        });
        getBtZoomIn().addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                btZoomIn_actionPerformed(e);
            }
        });
        getBtZoomOut().addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                btZoomOut_actionPerformed(e);
            }
        });
        getBtMove().addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                btMove_actionPerformed(e);
            }
        });
        getBtInsertLine().addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                btInsertLine_actionPerformed(e);
            }
        });
        getBtInsertPLine().addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                btInsertPLine_actionPerformed(e);
            }
        });
        getBtGPartition().addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                btGPartition_actionPerformed(e);
            }
        });
        getBtRemoveLine().addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                btRemoveLine_actionPerformed(e);
            }
        });
        getBtClear().addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                btClear_actionPerformed(e);
            }
        });
        getBtCompare().addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                btCompare_actionPerformed(e);
            }
        });
        getBtCapture().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btCapture_actionPerformed(e);
            }
        });
        getBtSetting().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btSetting_actionPerformed(e);
            }
        });
        getBtEdit().addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                btEdit_actionPerformed(e);
            }
        });

        this.addButton(getBtNone());
        this.addButton(getBtEdit());
        this.addButton(getBtWatch());
        this.addButton(getBtZoomIn());
        this.addButton(getBtZoomOut());
        this.addButton(getBtMove());
        this.addButton(getBtInsertLine());
        this.addButton(getBtInsertPLine());
        this.addButton(getBtGPartition());
        this.addButton(getBtRemoveLine());
        this.addButton(getBtClear());
        this.addButton(getBtCompare());
        this.addButton(getBtCapture());
        this.addButton(getBtSetting());

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

    void btEdit_actionPerformed(ActionEvent e) {
        for (ChartScreen screen : screens) {
            if (screen != null) {
                screen.getAction().setActionType(ActionCommand.Type.EDITTEXT);
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

    void btCapture_actionPerformed(ActionEvent e) {

        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                int height = 0;
                int width = screens.get(0).getWidth();

                for (ChartScreen screen : screens) {
                    if (screen.isVisible()) height = height + screen.getHeight();
                }
                Image image = createImage(width, height);
                Graphics g = image.getGraphics();

                int y = 0;
                for (ChartScreen screen : screens) {

                    if (screen.isVisible()) {
                        g.drawImage(screen.getAllscreenImage(), 0, y, screen.getWidth(), screen.getHeight(), null);
                        y = y + screen.getHeight();
                    }
                }
                new CopyImageToClipBoard(image);

            }
        });


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

    public ImageButton getBtCapture() {
        return btCapture;
    }

    public void setBtCapture(ImageButton btCapture) {
        this.btCapture = btCapture;
    }

    public ImageButton getBtEdit() {
        return btEdit;
    }

    public void setBtEdit(ImageButton btEdit) {
        this.btEdit = btEdit;
    }
}
