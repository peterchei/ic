package com.ic.gui;

import com.ic.core.*;
import com.ic.data.*;
import com.ic.util.FormatUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Vector;

public class CompareBar extends JPanel implements KeyListener, ChartDataServiceCallback {

    private static final long serialVersionUID = -5350551016952212042L;
    final String lbCodeArray[][] = {
            {"0001", "Please Select", "Please Select"},
            {"5001", "Hang seng index", "嚙踝蕭肏嚙踝蕭"},
            {"5051", "HSI Future (current month)", "嚙踝蕭芫嚙踝蕭f嚙踝蕭嚙�嚙磐嚙踝蕭)"},
            {"5052", "HSI Future (next month)", "嚙踝蕭芫嚙踝蕭f嚙踝蕭嚙�嚙磊嚙踝蕭)"},
            {"5002", "HSI finance", "嚙踝蕭耵嚙踝蕭蘆悗嚙踝蕭"},
            {"5003", "HSI-UTILITIES", "嚙踝蕭秅嚙踝蕭峈悗嚙踝蕭"},
            {"5004", "HSI-PROPERTIES", "嚙踝蕭穻a嚙踝蕭嚙踝蕭嚙"},
            {"5005", "HSI-COM & IND", "嚙踝蕭秅u嚙諉股恬蕭"},
            {"5006", "HANG SENG C C I", "嚙踝蕭肮嚙踝蕭w嚙踝蕭"},
            {"5007", "HANG SENG C E I", "嚙踝蕭肭嚙踝蕭嚙踝蕭"},
            {"5008", "HANG SENG MIDCAP", "嚙踝蕭秅嚙踝蕭嚙踝蕭悗嚙踝蕭"},
            {"5009", "HANG SENG 100", "嚙踝蕭秅@嚙褊恬蕭"},
            {"5020", "GEM INDEX", "嚙請業嚙瞌嚙踝蕭"},
            {"2800", "TRACKER FUND", "嚙調富嚙踝蕭"}
    };
    private final Color lineColor[] = {
            FConfig.LINE_COLOR_0,
            FConfig.LINE_COLOR_1,
            FConfig.LINE_COLOR_2,
            FConfig.LINE_COLOR_3,
            FConfig.LINE_COLOR_4,
            FConfig.LINE_COLOR_5,
            FConfig.LINE_COLOR_6,
            FConfig.LINE_COLOR_7,
            FConfig.LINE_COLOR_8,
            FConfig.LINE_COLOR_9
    };
    private final String lbArray[][] = {
            {"Add", "\u52a0\u5165"},
            {"Remove", "\u79fb\u9664"},
            {"Close", "\u95dc\u9589"}
    };
    JTextField tfCode = new JTextField();

    JComboBox<String> chACode = new JComboBox<String>();

    private ImageButton addButton = new ImageButton();
    private ImageButton removeButton = new ImageButton();
    private ImageButton closeButton = new ImageButton();
    private Vector<ChartItem> pcCharts = new Vector<ChartItem>();


    private ChartScreen chartScreen1 = null;
    private ChartScreen chartScreen2 = null;
    private ChartScreen chartScreen3 = null;
    private FunctionPanel fButtonBar = null;
    private MenuBar fMenuBar = null;

    private int language = FConfig.constEnglish;


    public CompareBar() {
        try {
            jbInit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setMenus(FunctionPanel fb, MenuBar fm) {
        fButtonBar = fb;
        fMenuBar = fm;
    }

    //set the reference of the chartScrenn such that the button bar can control it.
    public void setChartScreen(ChartScreen cs1, ChartScreen cs2, ChartScreen cs3) {
        chartScreen1 = cs1;
        chartScreen2 = cs2;
        chartScreen3 = cs3;
    }

    private void jbInit() throws Exception {
        tfCode.setBackground(Color.white);
        tfCode.setFont(new Font("Dialog", 0, 18));
        tfCode.setBorder(BorderFactory.createLineBorder(Color.black));
        tfCode.setBounds(new Rectangle(0, 0, FConfig.BUTTON_SIZE * 2, FConfig.BUTTON_SIZE));
        this.setLayout(null);
        getAddButton().setBounds(new Rectangle(FConfig.BUTTON_SIZE * 2 + 1, 0, FConfig.BUTTON_SIZE, FConfig.BUTTON_SIZE));
        getAddButton().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btAddChart_actionPerformed(e);
            }
        });


        chACode.setFont(new Font("Dialog", 0, 18));
        chACode.setBorder(BorderFactory.createLineBorder(Color.black));
        chACode.setBounds(new Rectangle(FConfig.BUTTON_SIZE * 4 + 1, 0, FConfig.BUTTON_SIZE * 2, FConfig.BUTTON_SIZE));
        getRemoveButton().setBounds(new Rectangle(FConfig.BUTTON_SIZE * 6 + 1, 0, FConfig.BUTTON_SIZE, FConfig.BUTTON_SIZE));
        getRemoveButton().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btRemove_actionPerformed(e);
            }
        });


        getCloseButton().setBounds(new Rectangle(FConfig.BUTTON_SIZE * 8 + 1, 0, FConfig.BUTTON_SIZE, FConfig.BUTTON_SIZE));
        getCloseButton().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btClose_actionPerformed(e);
            }
        });
        this.add(tfCode, null);
        this.add(getAddButton(), null);
        this.add(getRemoveButton(), null);
        this.add(chACode, null);
        this.add(getCloseButton(), null);
        tfCode.addKeyListener(this);
    }

    void btClose_actionPerformed(ActionEvent e) {
        if (fMenuBar != null && chartScreen1 != null) {
            ChartItem taChart = chartScreen1.getChart("TA1Chart");
            if (taChart != null) {
                String chTa = (String) fMenuBar.chMA1.getSelectedItem();

                if (chTa == fMenuBar.lbTA1Array[0][0] || chTa == fMenuBar.lbTA1Array[0][1]) {
                    taChart.setVisible(false);
                } else {
                    taChart.setVisible(true);              // set TA chart to be visible return to normal state.
                }
            }
            this.setVisible(false);
            fMenuBar.setVisible(true);
            chACode.removeAllItems();
            ChartItem cchart = chartScreen1.getLeftChart();
            if (cchart != null) {
                ChartType chartType = (ChartType) fMenuBar.chChartType.getSelectedItem();
                cchart.setChartType(chartType);
            }
        }
        pcCharts.removeAllElements();
        chartScreen1.getAction().getLineRecords().removeAllElements();
        chartScreen1.getAction().setGoldenPartitionLine(null);
        chartScreen1.removeChartsByType(ChartType.PERCENTAGE);
        chartScreen1.updateBaseScreen();
        chartScreen1.repaint();

    }


    /***
     * Invoked when a key has been pressed.
     */
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == 10) {
            btAddChart_actionPerformed(null);
        }
    }

    /***
     * Invoked when a key has been released.
     */
    public void keyReleased(KeyEvent e) {
    }

    public void keyTyped(KeyEvent e) {
    }

    void btAddChart_actionPerformed(ActionEvent e) {

        if (fMenuBar == null || chartScreen1.getLeftChart() == null) return;
        String cc = tfCode.getText();

        if (!FormatUtil.isNumber(cc)) {
            this.tfCode.setText("");
            return;
        }

        int Code = Integer.parseInt(cc);
        this.tfCode.setText("");
        if (Code != chartScreen1.getLeftChart().getChartData().getCode()) {
            int intervals = chartScreen1.getLeftChart().getChartData().getIntradayInterval();
            int NumberOfPoints = chartScreen1.getLeftChart().getChartData().getData().size();

            RequestCommand fc = new RequestCommand(Code, RequestCommand.TYPE_DOWNLOAD_LEFT_CHART,
                    (CommandType) fMenuBar.chDuration.getSelectedItem(), String.valueOf(Code), NumberOfPoints, intervals, true, this);

            ChartDataService.getInstance().addCommand(fc);
            chartScreen1.setScreenState(ChartScreen.LOADING);
        }


    }

    public void OnReceivedError(RequestCommand fc) {
        chartScreen1.setScreenState(ChartScreen.STARTED);
    }

    public void OnReceivedChartData(RequestCommand fc, Object result) {

        ChartData chartData = (ChartData) result;
        if (chartScreen1.getChart(String.valueOf(fc.getCode())) != null) {
            chartScreen1.setScreenState(ChartScreen.STARTED);
            return;
        }

        ChartData mydata = chartData;
        ChartItem mychart1 = new ChartItem(mydata, String.valueOf(fc.getCode()));
        mychart1.setAxisBar(AxisType.NONE);
        mychart1.setChartType(ChartType.PERCENTAGE);
        mychart1.setShowXaxis(false);
       // System.out.println("XXXX:" + (pcCharts.size() ) % lineColor.length);
        mychart1.setFirstColor(lineColor[pcCharts.size() % lineColor.length]);

        chartScreen1.addChart(mychart1);
        chartScreen1.getAction().getZoomRecords().removeAllElements();
        chartScreen1.getAction().getLineRecords().removeAllElements();
        chartScreen1.updateBaseScreen();
        chartScreen1.setScreenState(ChartScreen.STARTED);
        pcCharts.addElement(mychart1);
        chACode.removeAllItems();


        for (ChartItem item : pcCharts) {
            chACode.addItem(String.valueOf(item.getChartData().getCode()));
        }


    }

    void btRemove_actionPerformed(ActionEvent e) {
        if (chACode.getItemCount() == 0) return;
        pcCharts.removeElementAt(chACode.getSelectedIndex());
        chartScreen1.removeChart(Integer.parseInt((String) chACode.getSelectedItem()));
        chACode.removeAllItems();
        for (ChartItem item : pcCharts) {
            chACode.addItem(String.valueOf(item.getChartData().getCode()));
        }
        chartScreen1.updateBaseScreen();
        chartScreen1.repaint();
    }

    public void OnProgress(int percent) {
        // throw new UnsupportedOperationException("Not supported yet.");
    }

    public ImageButton getCloseButton() {
        return closeButton;
    }

    public void setCloseButton(ImageButton closeButton) {
        this.closeButton = closeButton;
    }

    public ImageButton getAddButton() {
        return addButton;
    }

    public void setAddButton(ImageButton addButton) {
        this.addButton = addButton;
    }

    public ImageButton getRemoveButton() {
        return removeButton;
    }

    public void setRemoveButton(ImageButton removeButton) {
        this.removeButton = removeButton;
    }
}
