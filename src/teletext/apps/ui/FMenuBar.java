package teletext.apps.ui;

import teletext.apps.core.AxisType;
import teletext.apps.core.ChartScreen;
import teletext.apps.core.ChartItem;
import teletext.apps.core.ChartType;
import teletext.data.ChartData;
import teletext.data.ChartDataService;
import teletext.data.ChartDataServiceListener;
import teletext.data.FCommand;
import teletext.util.FormatUtil;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.*;

public class FMenuBar extends TabBar implements KeyListener, ChartDataServiceListener {

    final String lbArray[][] = {
        {"None", "\u7121"} //0
        , {"Bar", "\u68d2\u5f62"} //1
        , {"Candle", "\u9670\u967d"} //2
        , {"Line", "\u7dda\u5f62"}, {"Go", "\u7e6a\u5716"}
    };
    final String lbDurationArray[][] = {//3
        {"Daily", "\u65e5\u7dda"} //0
        , {"Weekly", "\u661f\u671f\u7dda"} //1
        , {"Monthly", "\u6708\u7dda"} //2
        , {"Intraday", "\u5373\u5e02"} //3
    };
    final String lbMinuteArray[][] = {
        {"10", "10 Min", "10 Min"},
        {"1", "1 Min", "1 Min"},};
    final String lbTA1Array[][] = {
        {"None", "\u7121"} //0
        , {"Simple Moving Average", "\u7c21\u55ae\u79fb\u52d5\u5e73\u5747\u7dda"} //1
        , {"Weighted Moving Average", "\u52a0\u6b0a\u79fb\u52d5\u5e73\u5747\u7dda"} //2
        , {"Exponential Moving Average", "\u6307\u6578\u79fb\u52d5\u5e73\u5747\u7dda"} //3
        , {"Bollinger Bands", "\u4fdd\u6b77\u52a0\u901a\u9053"}
    };
    private int language = FConfig.constEnglish;
    JTextField tfCode = new JTextField();
    public JComboBox chDuration = new JComboBox();
    JComboBox chChartType = new JComboBox();
    JComboBox chMA1 = new JComboBox();
    JComboBox chMinute = new JComboBox();
    //the reference of chartscreens which this menubar can control it.
    private ChartScreen chartScreen1 = null;
    private ChartScreen chartScreen2 = null;
    private ChartScreen chartScreen3 = null;
    private FTAMenu taMenu = null;
    Border border1;

    //change language
    public void setLanguage(int tlanguage) {
        language = tlanguage;

        int selectedIndex;

        // btPlot.setLabel(lbArray[4][language]);

        selectedIndex = chDuration.getSelectedIndex();
        chDuration.removeAllItems();
        System.out.println("added -- chDuration");
        chDuration.addItem(lbDurationArray[0][language]);
        //chDuration.addItem(lbDurationArray[1][language]);
        //chDuration.addItem(lbDurationArray[2][language]);
        //chDuration.addItem(lbDurationArray[3][language]);
        System.out.println("completed added -- chDuration");
        if (selectedIndex >= 0) {
            chDuration.setSelectedIndex(selectedIndex);
        }

        selectedIndex = chChartType.getSelectedIndex();
        chChartType.removeAllItems();
        chChartType.addItem(lbArray[1][language]);
        chChartType.addItem(lbArray[2][language]);
        chChartType.addItem(lbArray[3][language]);
        if (selectedIndex >= 0) {
            chChartType.setSelectedIndex(selectedIndex);
        }


        selectedIndex = chMA1.getSelectedIndex();
        chMA1.removeAllItems();
        chMA1.addItem(lbTA1Array[0][language]);
        chMA1.addItem(lbTA1Array[1][language]);
        chMA1.addItem(lbTA1Array[2][language]);
        chMA1.addItem(lbTA1Array[3][language]);
        chMA1.addItem(lbTA1Array[4][language]);
        if (selectedIndex >= 0) {
            chMA1.setSelectedIndex(selectedIndex);
        }


        selectedIndex = this.chMinute.getSelectedIndex();
        chMinute.removeAll();
        chMinute.addItem(lbMinuteArray[0][language + 1]);
        chMinute.addItem(lbMinuteArray[1][language + 1]);
        if (selectedIndex >= 0) {
            chMinute.setSelectedIndex(selectedIndex);
        }


    }

    //set the reference of the chartScrenn such that the menubar can control it.
    public void setChartScreen(ChartScreen cs1, ChartScreen cs2, ChartScreen cs3) {
        chartScreen1 = cs1;
        chartScreen2 = cs2;
        chartScreen3 = cs3;
    }

    public void updateMenu() {
        setLanguage(0);
    }

    public FMenuBar() {
        try {
            jbInit();
            updateMenu();
            chDuration.addItemListener(new ItemListener() {

                public void itemStateChanged(ItemEvent e) {
                    chDuration_itemStateChanged(e);
                }
            });

            chChartType.addItemListener(new ItemListener() {

                public void itemStateChanged(ItemEvent e) {
                    chChartType_itemStateChanged(e);
                }
            });


            this.chMinute.addItemListener(new ItemListener() {

                public void itemStateChanged(ItemEvent e) {
                    chMinute_itemStateChanged(e);
                }
            });

            this.chMA1.addItemListener(new ItemListener() {

                public void itemStateChanged(ItemEvent e) {
                    chMA1_itemStateChanged(e);
                }
            });


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void chDuration_itemStateChanged(ItemEvent e) {

        String dtype = (String) chDuration.getSelectedItem();
        int selectedIndex = chMinute.getSelectedIndex();                  //get the selected Index
        int intervals = Integer.parseInt(lbMinuteArray[selectedIndex][0]); // get the intervals

        // if it is daily chart.
        if (dtype == lbDurationArray[0][0] || dtype == lbDurationArray[0][1]) {
            this.chMinute.setVisible(false);
            this.chMA1.setVisible(true);
            ChartItem lchart = chartScreen1.getLeftChart();
            if (chartScreen1.getLeftChart() != null) {
                int Code = lchart.chartData.Code;
                FCommand fc = new FCommand(Code, FCommand.TYPE_DOWNLOAD_LEFT_CHART, chDuration.getSelectedIndex(), "LMain1", 500, intervals, false, this);
                ChartDataService.getInstance().addCommand(fc);
                chartScreen1.setScreenState(ChartScreen.LOADING);
                chartScreen2.setScreenState(ChartScreen.LOADING);
                chartScreen3.setScreenState(ChartScreen.LOADING);
            }
        }

        // if it is weekly chart.
        if (dtype == lbDurationArray[1][0] || dtype == lbDurationArray[1][1]) {
            this.chMA1.setVisible(true);
            this.chMinute.setVisible(false);
            ChartItem lchart = chartScreen1.getLeftChart();
            if (chartScreen1.getLeftChart() != null) {
                int Code = lchart.chartData.Code;
                FCommand fc = new FCommand(Code, FCommand.TYPE_DOWNLOAD_LEFT_CHART, chDuration.getSelectedIndex(), "LMain1", 500, intervals, false, this);
                ChartDataService.getInstance().addCommand(fc);
                chartScreen1.setScreenState(ChartScreen.LOADING);
                chartScreen2.setScreenState(ChartScreen.LOADING);
                chartScreen3.setScreenState(ChartScreen.LOADING);
            }
        }

        // if it is monthly chart
        if (dtype == lbDurationArray[2][0] || dtype == lbDurationArray[2][1]) {
            this.chMA1.setVisible(true);
            this.chMinute.setVisible(false);
            ChartItem lchart = chartScreen1.getLeftChart();
            if (chartScreen1.getLeftChart() != null) {
                int Code = lchart.chartData.Code;
                FCommand fc = new FCommand(Code, FCommand.TYPE_DOWNLOAD_LEFT_CHART, chDuration.getSelectedIndex(), "LMain1", 500, intervals, false, this);
                ChartDataService.getInstance().addCommand(fc);
                chartScreen1.setScreenState(ChartScreen.LOADING);
                chartScreen2.setScreenState(ChartScreen.LOADING);
                chartScreen3.setScreenState(ChartScreen.LOADING);
            }
        }

        // if it is intraday chart
        if (dtype == lbDurationArray[3][0] || dtype == lbDurationArray[3][1]) {
            this.chMA1.setVisible(false);
            this.chMinute.setVisible(true);
            ChartItem lchart = chartScreen1.getLeftChart();
            if (chartScreen1.getLeftChart() != null) {
                int Code = lchart.chartData.Code;
                this.chChartType.setSelectedIndex(0);
                FCommand fc = new FCommand(Code, FCommand.TYPE_DOWNLOAD_LEFT_CHART, chDuration.getSelectedIndex(), "LMain1", 500, intervals, false, this);
                ChartDataService.getInstance().addCommand(fc);
                chartScreen1.setScreenState(ChartScreen.LOADING);
                chartScreen2.setScreenState(ChartScreen.LOADING);
                chartScreen3.setScreenState(ChartScreen.LOADING);
            }
        }

        this.setEnable(false);
    }

    private void chMinute_itemStateChanged(ItemEvent e) {
        //String dtype = chMinute.getSelectedItem();
        int selectedIndex = chMinute.getSelectedIndex();                  //get the selected Index
        int intervals = Integer.parseInt(lbMinuteArray[selectedIndex][0]); // get the intervals

        ChartItem lchart = chartScreen1.getLeftChart();
        if (chartScreen1.getLeftChart() != null) {
            FCommand fc = new FCommand(lchart.chartData.Code, FCommand.TYPE_DOWNLOAD_LEFT_CHART, chDuration.getSelectedIndex(), "LMain1", 500, intervals, false, this);
            ChartDataService.getInstance().addCommand(fc);
            chartScreen1.setScreenState(ChartScreen.LOADING);
            chartScreen2.setScreenState(ChartScreen.LOADING);
            chartScreen3.setScreenState(ChartScreen.LOADING);
        }
        this.setEnable(false);

    }

    private void chMA1_itemStateChanged(ItemEvent e) {
        String ctype = (String) chMA1.getSelectedItem();

        if (ctype == lbTA1Array[0][0] || ctype == lbTA1Array[0][1]) {
            ChartItem taChart = chartScreen1.getChart("TA1Chart");
            if (taChart == null) {
                System.out.println("No TA1Chart");
                return;
            }
            taChart.setVisible(false);
            chartScreen1.updateBaseScreen();
            chartScreen1.repaint();

        } else if (ctype == lbTA1Array[1][0] || ctype == lbTA1Array[1][1]) {
            ChartItem taChart = chartScreen1.getChart("TA1Chart");
            if (taChart == null) {
                System.out.println("No TA1Chart");
                return;
            }

            taChart.chartType = ChartType.SIMPLE_MOVING_AVERAGE;
            taChart.axisBar = AxisType.NONE;
            taChart.chartData.calculateMovingAverage(taChart.chartData.fTAconfig.SMAN1, taChart.chartData.fTAconfig.SMAN2, taChart.chartData.fTAconfig.SMAN3);
            taChart.setVisible(true);
            taChart.setShowXaxis(false);
            chartScreen1.updateBaseScreen();
            chartScreen1.repaint();
        } else if (ctype == lbTA1Array[2][0] || ctype == lbTA1Array[2][1]) {
            ChartItem taChart = chartScreen1.getChart("TA1Chart");
            if (taChart == null) {
                return;
            }

            taChart.chartType = ChartType.WEIGHTED_MOVING_AVERAGE;
            taChart.axisBar = AxisType.NONE;
            taChart.chartData.calculateWeightedMovingAverage(taChart.chartData.fTAconfig.WMAN1, taChart.chartData.fTAconfig.WMAN2, taChart.chartData.fTAconfig.WMAN3);
            taChart.setVisible(true);
            taChart.setShowXaxis(false);
            chartScreen1.updateBaseScreen();
            chartScreen1.repaint();
        } else if (ctype == lbTA1Array[3][0] || ctype == lbTA1Array[3][1]) {
            ChartItem taChart = chartScreen1.getChart("TA1Chart");
            if (taChart == null) {
                return;
            }
            taChart.chartType = ChartType.EXPONENTIAL_MOVING_AVERAGE;
            taChart.axisBar = AxisType.NONE;
            taChart.chartData.calculateExponentialMovingAverage(taChart.chartData.fTAconfig.EMA1, taChart.chartData.fTAconfig.EMA2, taChart.chartData.fTAconfig.EMA3);
            taChart.setVisible(true);
            taChart.setShowXaxis(false);
            chartScreen1.updateBaseScreen();
            chartScreen1.repaint();
        } else if (ctype == lbTA1Array[4][0] || ctype == lbTA1Array[4][1]) {
            ChartItem taChart = chartScreen1.getChart("TA1Chart");
            if (taChart == null) {
                return;
            }

            taChart.chartType = ChartType.BOLLINGERBAND;
            taChart.axisBar = AxisType.NONE;
            taChart.chartData.calculateBollingerBand(taChart.chartData.fTAconfig.bbN, taChart.chartData.fTAconfig.bbDevation);
            taChart.setVisible(true);
            taChart.setShowXaxis(false);
            chartScreen1.updateBaseScreen();
            chartScreen1.repaint();

        }


    }

    private void chChartType_itemStateChanged(ItemEvent e) {
        String ctype = (String) chChartType.getSelectedItem();
        if (ctype == lbArray[1][0] || ctype == lbArray[1][1]) {
            ChartItem cchart = (ChartItem) chartScreen1.getChart("LMain1");
            if (cchart != null) {
                cchart.chartType = ChartType.BAR;
            }

            ChartItem cRchart = (ChartItem) chartScreen1.getChart("LMain1");
            if (cRchart != null) {
                cRchart.chartType = ChartType.BAR;
            }
        } else if (ctype == lbArray[2][0] || ctype == lbArray[2][1]) {
            ChartItem cchart = (ChartItem) chartScreen1.getChart("LMain1");
            if (cchart != null) {
                cchart.chartType = ChartType.CANDLE;
            }

            ChartItem cRchart = (ChartItem) chartScreen1.getChart("LMain1");
            if (cRchart != null) {
                cRchart.chartType = ChartType.CANDLE;
            }

        } else if (ctype == lbArray[3][0] || ctype == lbArray[3][1]) {
            ChartItem cchart = (ChartItem) chartScreen1.getChart("LMain1");
            if (cchart != null) {
                cchart.chartType = ChartType.LINE;
            }

            ChartItem cRchart = (ChartItem) chartScreen1.getChart("LMain1");
            if (cRchart != null) {
                cRchart.chartType = ChartType.LINE;
            }

        }
        chartScreen1.updateBaseScreen();
        chartScreen1.repaint();
    }

    private void jbInit() throws Exception {
        border1 = BorderFactory.createEtchedBorder(Color.white, Color.black);
        tfCode.setFont(new Font("Dialog", 0, 14));
        tfCode.setBorder(BorderFactory.createLineBorder(Color.black));
        tfCode.setCaretPosition(0);
        tfCode.setColumns(4);
        tfCode.setBounds(new Rectangle(9, 4, 50, 19));
        tfCode.setBackground(Color.white);
        tfCode.addKeyListener(this);   //add a key listener
        this.setLayout(null);
        chDuration.setBackground(Color.white);
        chDuration.setFont(new Font("Dialog", 0, 10));
        chDuration.setAutoscrolls(true);
        chDuration.setBorder(BorderFactory.createLineBorder(Color.black));
        chDuration.setBounds(new Rectangle(62, 4, 54, 19));
        chChartType.setBackground(Color.white);
        chChartType.setFont(new Font("Dialog", 0, 10));
        chChartType.setAutoscrolls(true);
        chChartType.setBorder(BorderFactory.createLineBorder(Color.black));
        chChartType.setBounds(new Rectangle(225, 4, 63, 19));
        chMA1.setFont(new Font("Dialog", 0, 10));
        chMA1.setAutoscrolls(true);
        chMA1.setBackground(Color.white);
        chMA1.setBorder(BorderFactory.createLineBorder(Color.black));
        chMA1.setBounds(new Rectangle(120, 4, 101, 19));
        chMinute.setVisible(false);
        chMinute.setBounds(new Rectangle(415, 0, 83, 26));
        this.setFont(new Font("Dialog", 0, 10));
        this.setDebugGraphicsOptions(0);
        this.add(chMinute, null);
        this.add(tfCode, null);
        this.add(chDuration, null);
        this.add(chMA1, null);
        this.add(chChartType, null);
    }

    /*** Invoked when a key has been pressed.*/
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == 10) {
            btPlot_actionPerformed(null);
        }
    }

    /*** Invoked when a key has been released.*/
    public void keyReleased(KeyEvent e) {
    }

    public void keyTyped(KeyEvent e) {
    }

    void btPlot_actionPerformed(ActionEvent e) {
        int selectedIndex = chMinute.getSelectedIndex();                 //get the selected Index
        int intervals = Integer.parseInt(lbMinuteArray[selectedIndex][0]); // get the intervals

        String cc = tfCode.getText();
        if (!FormatUtil.isNumerical(cc)) {
            this.tfCode.setText("");
            return;
        }
        int Code = Integer.parseInt(cc);
        this.tfCode.setText("");

        setEnable(false);
        chartScreen1.setScreenState(ChartScreen.LOADING);
        chartScreen2.setScreenState(ChartScreen.LOADING);
        chartScreen3.setScreenState(ChartScreen.LOADING);

        FCommand fc = new FCommand(Code, FCommand.TYPE_DOWNLOAD_LEFT_CHART, chDuration.getSelectedIndex(), "LMain1", 500, intervals, false, this);
        ChartDataService.getInstance().addCommand(fc);
    }

    public void setEnable(boolean b) {
        this.tfCode.setEditable(b);
        this.chDuration.setEnabled(b);
        this.chChartType.setEnabled(b);
        this.chMA1.setEnabled(b);
        this.chMinute.setEnabled(b);
    }

    public void OnReceivedError(FCommand fc) {
        chartScreen1.setScreenState(ChartScreen.STARTED);
        chartScreen2.setScreenState(ChartScreen.STARTED);
        chartScreen3.setScreenState(ChartScreen.STARTED);
        chartScreen1.repaint();
        chartScreen2.repaint();
        chartScreen3.repaint();
        this.setEnable(true);
    }

    public void setTAMenu(FTAMenu ftam) {
        taMenu = ftam;
    }

    // public void OnReceivedChartData(FCommand fc, ChartData chartData) {
    public void OnReceivedChartData(FCommand fc, Object result) {

        ChartData chartData = (ChartData) result;
        ChartItem cl = chartScreen1.getLeftChart();
        if (cl != null) {
            if (cl.chartType == ChartType.PERCENTAGE) {
                return;
            }
        }

        ChartData mydata = chartData;
        ChartData mydata2 = new ChartData();
        mydata2.data = mydata.data;
        mydata2.dataType = mydata.dataType;
        mydata2.EName = mydata.EName;
        mydata2.CName = mydata.CName;
        mydata2.Code = mydata.Code;

        ChartItem mychart1 = new ChartItem(mydata, "LMain1");
        ChartItem mychart2 = new ChartItem(mydata2, "TA1Chart");
        ChartItem mychart3 = new ChartItem(mydata, "LMain3");
        ChartItem mychart4 = new ChartItem(mydata, "TA1Chart");

        // mychart1.chartType = chChartType.getSelectedIndex();

        switch (chChartType.getSelectedIndex()) {

            case 0:
                mychart1.chartType = ChartType.BAR;
                break;
            case 1:
                mychart1.chartType = ChartType.CANDLE;
                break;
            case 2:
                mychart1.chartType = ChartType.LINE;
                break;
            case 3:
                mychart1.chartType = ChartType.BAR;
                break;
        }

        mychart1.axisBar = AxisType.LEFTAXIS;
        mychart2.axisBar = AxisType.LEFTAXIS;
        mychart3.chartType = ChartType.VOLUME;
        mychart3.axisBar = AxisType.LEFTAXIS;
        mychart3.firstColor = new Color(50, 100, 50);
        mychart4.chartType = ChartType.LINE;
        mychart4.axisBar = AxisType.NONE;
        mychart4.setVisible(false);

        mychart2.chartType = ChartType.VOLUME;

        chartScreen1.removeAllCharts();
        this.chMA1.setSelectedIndex(0); // select None chart for TA1
        chartScreen2.removeAllCharts();
        chartScreen3.removeAllCharts();

        chartScreen1.addChart(mychart1);
        chartScreen2.addChart(mychart2);
        chartScreen3.addChart(mychart3);
        chartScreen1.addChart(mychart4);

        chartScreen1.getAction().zoomRecords.removeAllElements();
        chartScreen2.getAction().zoomRecords.removeAllElements();
        chartScreen3.getAction().zoomRecords.removeAllElements();

        chartScreen1.getAction().lineRecords.removeAllElements();
        chartScreen2.getAction().lineRecords.removeAllElements();
        chartScreen3.getAction().lineRecords.removeAllElements();

        // update tachart and calculate.........
        chartScreen1.undoZoom();
        chartScreen2.undoZoom();
        chartScreen3.undoZoom();

        if (taMenu != null) {
            taMenu.chChartType_itemStateChanged(null);
        }

        chartScreen1.setScreenState(ChartScreen.STARTED);
        chartScreen2.setScreenState(ChartScreen.STARTED);
        chartScreen3.setScreenState(ChartScreen.STARTED);

        this.setEnable(true);
    }

    public void OnProgress(int percent) {
        //throw new UnsupportedOperationException("Not supported yet.");
        chartScreen1.flipLoading();
        chartScreen2.flipLoading();
        chartScreen3.flipLoading();


    }
}
