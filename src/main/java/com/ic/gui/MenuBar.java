package com.ic.gui;

import com.ic.core.*;
import com.ic.data.*;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.*;

public class MenuBar extends JPanel implements KeyListener, ChartDataServiceCallback {

  final String[][] lbDurationArray = { // 3
    {"Daily", "\u65e5\u7dda"} // 0
    , {"Weekly", "\u661f\u671f\u7dda"} // 1
    , {"Monthly", "\u6708\u7dda"} // 2
    , {"Intraday", "\u5373\u5e02"} // 3
  };
  final String[][] lbMinuteArray = {{"10", "10 Min", "10 Min"}, {"1", "1 Min", "1 Min"},};

  final String[][] lbTA1Array = {{"None", "\u7121"} // 0
    , {"Simple Moving Average", "\u7c21\u55ae\u79fb\u52d5\u5e73\u5747\u7dda"} // 1
    , {"Weighted Moving Average", "\u52a0\u6b0a\u79fb\u52d5\u5e73\u5747\u7dda"} // 2
    , {"Exponential Moving Average", "\u6307\u6578\u79fb\u52d5\u5e73\u5747\u7dda"} // 3
    , {"Bollinger Bands", "\u4fdd\u6b77\u52a0\u901a\u9053"}};

  public JComboBox<CommandType> chDuration = new JComboBox<CommandType>();
  JTextField tfCode = new JTextField();
  JComboBox<ChartType> chChartType = new JComboBox<ChartType>();
  JComboBox chMA1 = new JComboBox();
  JComboBox chMinute = new JComboBox();
  Border border1;
  private int language = FConfig.constEnglish;
  // the reference of chartscreens which this menubar can control it.
  private ChartScreen chartScreen1 = null;
  private ChartScreen chartScreen2 = null;
  private ChartScreen chartScreen3 = null;
  private TAMenu taMenu = null;

  public MenuBar() {

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

  }

  // change language
  public void setLanguage(int tlanguage) {
    language = tlanguage;

    int selectedIndex;

    selectedIndex = chDuration.getSelectedIndex();
    chDuration.removeAllItems();
    // System.out.println("added -- chDuration");
    chDuration.addItem(CommandType.DAILY);
    // System.out.println("completed added -- chDuration");
    if (selectedIndex >= 0) {
      chDuration.setSelectedIndex(selectedIndex);
    }

    selectedIndex = chChartType.getSelectedIndex();
    chChartType.removeAllItems();
    chChartType.addItem(ChartType.LINE);
    chChartType.addItem(ChartType.CANDLE);
    chChartType.addItem(ChartType.BAR);
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

  // set the reference of the chartScrenn such that the menubar can control
  // it.
  public void setChartScreen(ChartScreen cs1, ChartScreen cs2, ChartScreen cs3) {
    chartScreen1 = cs1;
    chartScreen2 = cs2;
    chartScreen3 = cs3;
  }

  public void updateMenu() {
    setLanguage(0);
  }

  private void chDuration_itemStateChanged(ItemEvent e) {

    CommandType dtype = (CommandType) chDuration.getSelectedItem();
    int selectedIndex = chMinute.getSelectedIndex(); // get the selected
    // Index
    int intervals = Integer.parseInt(lbMinuteArray[selectedIndex][0]); // get
    // the
    // intervals

    // if it is daily chart.
    if (dtype == CommandType.DAILY) {
      this.chMinute.setVisible(false);
      this.chMA1.setVisible(true);
      ChartItem lchart = chartScreen1.getLeftChart();
      if (chartScreen1.getLeftChart() != null) {
        String Code = String.valueOf(lchart.getChartData().getCode());
        RequestCommand fc = new RequestCommand(Code, RequestCommand.TYPE_DOWNLOAD_LEFT_CHART,
          (CommandType) chDuration.getSelectedItem(), "LMain1", 500, intervals, false, this);
        ChartDataService.getInstance().addCommand(fc);
        chartScreen1.setScreenState(ChartScreen.LOADING);
        chartScreen2.setScreenState(ChartScreen.LOADING);
        chartScreen3.setScreenState(ChartScreen.LOADING);
      }
    }

    // if it is weekly chart.
    if (dtype == CommandType.WEEKLY) {
      this.chMA1.setVisible(true);
      this.chMinute.setVisible(false);
      ChartItem lchart = chartScreen1.getLeftChart();
      if (chartScreen1.getLeftChart() != null) {
        String Code = String.valueOf(lchart.getChartData().getCode());
        RequestCommand fc = new RequestCommand(Code, RequestCommand.TYPE_DOWNLOAD_LEFT_CHART,
          (CommandType) chDuration.getSelectedItem(), "LMain1", 500, intervals, false, this);
        ChartDataService.getInstance().addCommand(fc);
        chartScreen1.setScreenState(ChartScreen.LOADING);
        chartScreen2.setScreenState(ChartScreen.LOADING);
        chartScreen3.setScreenState(ChartScreen.LOADING);
      }
    }

    // if it is monthly chart
    if (dtype == CommandType.MONTHLY) {
      this.chMA1.setVisible(true);
      this.chMinute.setVisible(false);
      ChartItem lchart = chartScreen1.getLeftChart();
      if (chartScreen1.getLeftChart() != null) {
        String Code = String.valueOf(lchart.getChartData().getCode());
        RequestCommand fc = new RequestCommand(Code, RequestCommand.TYPE_DOWNLOAD_LEFT_CHART,
          (CommandType) chDuration.getSelectedItem(), "LMain1", 500, intervals, false, this);
        ChartDataService.getInstance().addCommand(fc);
        chartScreen1.setScreenState(ChartScreen.LOADING);
        chartScreen2.setScreenState(ChartScreen.LOADING);
        chartScreen3.setScreenState(ChartScreen.LOADING);
      }
    }

    // if it is intraday chart
    if (dtype == CommandType.INTRADAY) {
      this.chMA1.setVisible(false);
      this.chMinute.setVisible(true);
      ChartItem lchart = chartScreen1.getLeftChart();
      if (chartScreen1.getLeftChart() != null) {
        String Code = String.valueOf(lchart.getChartData().getCode());
        this.chChartType.setSelectedIndex(0);
        RequestCommand fc = new RequestCommand(Code, RequestCommand.TYPE_DOWNLOAD_LEFT_CHART,
          (CommandType) chDuration.getSelectedItem(), "LMain1", 500, intervals, false, this);
        ChartDataService.getInstance().addCommand(fc);
        chartScreen1.setScreenState(ChartScreen.LOADING);
        chartScreen2.setScreenState(ChartScreen.LOADING);
        chartScreen3.setScreenState(ChartScreen.LOADING);
      }
    }

    this.setEnable(false);
  }

  private void chMinute_itemStateChanged(ItemEvent e) {
    // String dtype = chMinute.getSelectedItem();
    int selectedIndex = chMinute.getSelectedIndex(); // get the selected
    // Index
    int intervals = Integer.parseInt(lbMinuteArray[selectedIndex][0]); // get
    // the
    // intervals

    ChartItem lchart = chartScreen1.getLeftChart();
    if (chartScreen1.getLeftChart() != null) {
      RequestCommand fc = new RequestCommand(String.valueOf(lchart.getChartData().getCode()),
        RequestCommand.TYPE_DOWNLOAD_LEFT_CHART, (CommandType) chDuration.getSelectedItem(), "LMain1", 500,
        intervals, false, this);
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

      taChart.setChartType(ChartType.SIMPLE_MOVING_AVERAGE);
      taChart.setAxisBar(AxisType.NONE);
      taChart.getChartData().calculateMovingAverage(taChart.getChartData().getfTAconfig().SMAN1,
        taChart.getChartData().getfTAconfig().SMAN2, taChart.getChartData().getfTAconfig().SMAN3);
      taChart.setVisible(true);
      taChart.setShowXaxis(false);
      chartScreen1.updateBaseScreen();
      chartScreen1.repaint();
    } else if (ctype == lbTA1Array[2][0] || ctype == lbTA1Array[2][1]) {
      ChartItem taChart = chartScreen1.getChart("TA1Chart");
      if (taChart == null) {
        return;
      }

      taChart.setChartType(ChartType.WEIGHTED_MOVING_AVERAGE);
      taChart.setAxisBar(AxisType.NONE);
      taChart.getChartData().calculateWeightedMovingAverage(taChart.getChartData().getfTAconfig().WMAN1,
        taChart.getChartData().getfTAconfig().WMAN2, taChart.getChartData().getfTAconfig().WMAN3);
      taChart.setVisible(true);
      taChart.setShowXaxis(false);
      chartScreen1.updateBaseScreen();
      chartScreen1.repaint();
    } else if (ctype == lbTA1Array[3][0] || ctype == lbTA1Array[3][1]) {
      ChartItem taChart = chartScreen1.getChart("TA1Chart");
      if (taChart == null) {
        return;
      }
      taChart.setChartType(ChartType.EXPONENTIAL_MOVING_AVERAGE);
      taChart.setAxisBar(AxisType.NONE);
      taChart.getChartData().calculateExponentialMovingAverage(taChart.getChartData().getfTAconfig().EMA1,
        taChart.getChartData().getfTAconfig().EMA2, taChart.getChartData().getfTAconfig().EMA3);
      taChart.setVisible(true);
      taChart.setShowXaxis(false);
      chartScreen1.updateBaseScreen();
      chartScreen1.repaint();
    } else if (ctype == lbTA1Array[4][0] || ctype == lbTA1Array[4][1]) {
      ChartItem taChart = chartScreen1.getChart("TA1Chart");
      if (taChart == null) {
        return;
      }

      taChart.setChartType(ChartType.BOLLINGERBAND);
      taChart.setAxisBar(AxisType.NONE);
      taChart.getChartData().calculateBollingerBand(taChart.getChartData().getfTAconfig().bbN,
        taChart.getChartData().getfTAconfig().bbDevation);
      taChart.setVisible(true);
      taChart.setShowXaxis(false);
      chartScreen1.updateBaseScreen();
      chartScreen1.repaint();

    }

  }

  private void chChartType_itemStateChanged(ItemEvent e) {
    ChartType ctype = (ChartType) chChartType.getSelectedItem();
    if (ctype == ChartType.LINE || ctype == ChartType.BAR || ctype == ChartType.CANDLE) {
      ChartItem cchart = chartScreen1.getChart("LMain1");
      if (cchart != null) {
        cchart.setChartType(ctype);
      }
      ChartItem cRchart = chartScreen1.getChart("LMain1");
      if (cRchart != null) {
        cRchart.setChartType(ctype);
      }
    }
    chartScreen1.updateBaseScreen();
    chartScreen1.repaint();
  }

  private void jbInit() {
    border1 = BorderFactory.createEtchedBorder(Color.white, Color.black);
    tfCode.setFont(new Font("Dialog", 0, 18));
    tfCode.setBorder(BorderFactory.createLineBorder(Color.black));
    tfCode.setCaretPosition(0);
    tfCode.setColumns(4);

    tfCode.setBackground(Color.white);
    tfCode.addKeyListener(this); // add a key listener
    this.setLayout(null);
    chDuration.setBackground(Color.white);
    chDuration.setFont(new Font("Dialog", 0, 18));
    chDuration.setAutoscrolls(true);
    chDuration.setBorder(BorderFactory.createLineBorder(Color.black));

    chChartType.setBackground(Color.white);
    chChartType.setFont(new Font("Dialog", 0, 18));
    chChartType.setAutoscrolls(true);
    chChartType.setBorder(BorderFactory.createLineBorder(Color.black));

    chMA1.setFont(new Font("Dialog", 0, 18));
    chMA1.setAutoscrolls(true);
    chMA1.setBackground(Color.white);
    chMA1.setBorder(BorderFactory.createLineBorder(Color.black));

    chMinute.setVisible(false);

    tfCode.setBounds(new Rectangle(0, 0, 60, FConfig.BUTTON_SIZE));
    // chDuration.setBounds(new Rectangle(60, 0, 100, FConfig.BUTTON_SIZE));
    chMA1.setBounds(new Rectangle(60, 0, 200, FConfig.BUTTON_SIZE));
    chChartType.setBounds(new Rectangle(260, 0, 80, FConfig.BUTTON_SIZE));

    this.setFont(new Font("Dialog", 0, 18));
    this.setDebugGraphicsOptions(0);
    this.add(chMinute, null);
    this.add(tfCode, null);
    this.add(chDuration, null);
    this.add(chMA1, null);
    this.add(chChartType, null);
  }

  /***
   * Invoked when a key has been pressed.
   */
  public void keyPressed(KeyEvent e) {
    if (e.getKeyCode() == 10) {
      btPlot_actionPerformed(null);
    }
  }

  public void keyReleased(KeyEvent e) {
  }

  public void keyTyped(KeyEvent e) {
  }

  void btPlot_actionPerformed(ActionEvent e) {
    int selectedIndex = chMinute.getSelectedIndex(); // get the selected
    // Index
    int intervals = Integer.parseInt(lbMinuteArray[selectedIndex][0]); // get
    // the
    // intervals

    String cc = tfCode.getText();

    String Code = cc.trim();
    this.tfCode.setText("");

    setEnable(false);
    chartScreen1.setScreenState(ChartScreen.LOADING);
    chartScreen2.setScreenState(ChartScreen.LOADING);
    chartScreen3.setScreenState(ChartScreen.LOADING);

    RequestCommand fc = new RequestCommand(Code, RequestCommand.TYPE_DOWNLOAD_LEFT_CHART,
      (CommandType) chDuration.getSelectedItem(), "LMain1", 500, intervals, false, this);
    ChartDataService.getInstance().addCommand(fc);
  }

  public void setEnable(boolean b) {
    this.tfCode.setEditable(b);
    this.chDuration.setEnabled(b);
    this.chChartType.setEnabled(b);
    this.chMA1.setEnabled(b);
    this.chMinute.setEnabled(b);
  }

  public void OnReceivedError(RequestCommand fc) {
    chartScreen1.setScreenState(ChartScreen.STARTED);
    chartScreen2.setScreenState(ChartScreen.STARTED);
    chartScreen3.setScreenState(ChartScreen.STARTED);
    chartScreen1.repaint();
    chartScreen2.repaint();
    chartScreen3.repaint();
    this.setEnable(true);
  }

  public void setTAMenu(TAMenu menu) {
    taMenu = menu;
  }

  public void OnReceivedChartData(RequestCommand fc, Object result) {

    ChartData chartData = (ChartData) result;
    ChartItem cl = chartScreen1.getLeftChart();

    if (cl != null && ChartType.PERCENTAGE.equals(cl.getChartType()))
      return;

    ChartData mydata = chartData;
    ChartData mydata2 = new ChartData();
    mydata2.setData(mydata.getData());
    mydata2.dataInterval = mydata.dataInterval;
    mydata2.setName(mydata.getName());
    mydata2.setCode(mydata.getCode());

    ChartItem mychart1 = new ChartItem(mydata, "LMain1");
    ChartItem mychart2 = new ChartItem(mydata2, "TA1Chart");
    ChartItem mychart3 = new ChartItem(mydata, "LMain3");
    ChartItem mychart4 = new ChartItem(mydata, "TA1Chart");

    mychart1.setChartType((ChartType) chChartType.getSelectedItem());

    mychart1.setAxisBar(AxisType.LEFTAXIS);
    mychart2.setAxisBar(AxisType.LEFTAXIS);
    mychart3.setChartType(ChartType.VOLUME);
    mychart3.setAxisBar(AxisType.LEFTAXIS);
    mychart3.setFirstColor(new Color(50, 100, 50));
    mychart4.setChartType(ChartType.LINE);
    mychart4.setAxisBar(AxisType.NONE);
    mychart4.setVisible(false);

    mychart2.setChartType(ChartType.VOLUME);

    chartScreen1.removeAllCharts();
    this.chMA1.setSelectedIndex(0); // select None chart for TA1
    chartScreen2.removeAllCharts();
    chartScreen3.removeAllCharts();

    chartScreen1.addChart(mychart1);
    chartScreen2.addChart(mychart2);
    chartScreen3.addChart(mychart3);
    chartScreen1.addChart(mychart4);

    chartScreen1.getAction().getZoomRecords().removeAllElements();
    chartScreen2.getAction().getZoomRecords().removeAllElements();
    chartScreen3.getAction().getZoomRecords().removeAllElements();

    chartScreen1.getAction().getLineRecords().removeAllElements();
    chartScreen2.getAction().getLineRecords().removeAllElements();
    chartScreen3.getAction().getLineRecords().removeAllElements();

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
    chartScreen1.flipLoading();
    chartScreen2.flipLoading();
    chartScreen3.flipLoading();
  }
}
