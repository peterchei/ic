
/**
 * Title:        FME Chart Project for E-finet<p>
 * Description:  <p>
 * Copyright:    Copyright (c) <p>
 * Company:      <p>
 * @author
 * @version 1.0
 */
package fmechart;

import java.awt.*;
import java.awt.event.*;
import java.net.*;
import java.io.*;
import java.util.*;
import java.lang.*;

public class FMenuBar extends FBar implements KeyListener, ChartSourceListener
{

  final String lbArray[][] ={
                                   {"None","\u7121"}                                //0
                                  ,{"Bar","\u68d2\u5f62"}                           //1
                                  ,{"Candle","\u9670\u967d"}                        //2
                                  ,{"Line","\u7dda\u5f62"}
                                  ,{"Plot","\u7e6a\u5716"}
                                  };

  final String lbDurationArray[][] = {                                                            //3
                                   {"Daily","\u65e5\u7dda"}                         //0
                                  ,{"Weekly","\u661f\u671f\u7dda"}                  //1
                                  ,{"Monthly","\u6708\u7dda"}                       //2
                                  ,{"Intraday","\u5373\u5e02"}                      //3
                                };

  final String lbCodeArray[][] = {
                      {"0000","Please Select","Please Select"},
                      {"5001","Hang seng index", "ネ计" },
                      {"5051","HSI Future (current month)", "ネ戳砯计(る)"},
                      {"5052", "HSI Future (next month)", "ネ戳砯计(る)"},
                      {"5002", "HSI finance", "ネ磕计"},
                      {"5003", "HSI-UTILITIES","ネそノ计"},
                      {"5004", "HSI-PROPERTIES","ネ玻计"},
                      {"5005", "HSI-COM & IND", "ネ坝计"},
                      {"5006", "HANG SENG C C I", "ネ膚计"},
                      {"5007", "HANG SENG C E I", "ネ瓣计"},
                      {"5008", "HANG SENG MIDCAP", "ネい计"},
                      {"5009", "HANG SENG 100", "ネκ计"},
                      {"5020", "GEM INDEX", "承穨狾计"},
                      {"2800", "TRACKER FUND", "碔膀"}
                    };

  final String lbMinuteArray[][] = {
                      {"10","10 Min","10 Min"},
                      {"1","1 Min", "1 Min"},
                    };

  final String lbTA1Array[][] = {
                                   {"None","\u7121"}                                      //0
                                  ,{"Simple Moving Average","\u7c21\u55ae\u79fb\u52d5\u5e73\u5747\u7dda"}      //1
                                  ,{"Weighted Moving Average","\u52a0\u6b0a\u79fb\u52d5\u5e73\u5747\u7dda"} //2
                                  ,{"Exponential Moving Average","\u6307\u6578\u79fb\u52d5\u5e73\u5747\u7dda"} //3
                                  ,{"Bollinger Bands","\u4fdd\u6b77\u52a0\u901a\u9053"}
                                  };

  private int language = FConfig.constEnglish;



  public TextField tfCode = new TextField();
  public Button btPlot = new Button();
  public Choice chDuration = new Choice();
  public Choice chChartType = new Choice();
  public Choice chSpChart = new Choice();
  public Choice chMA1 = new Choice();
  public Choice chMinute = new Choice();



  //the reference of chartscreens which this menubar can control it.
  private ChartScreen chartScreen1 = null;
  private ChartScreen chartScreen2 = null;
  private ChartScreen chartScreen3 = null;
  private ChartSource chartSource = null;
//  private ChartUIObject chartUIObject.
  private FTAMenu taMenu = null;


  //change language
  public void setLanguage(int tlanguage)
  {
    language = tlanguage;

    int selectedIndex;
    this.btPlot.setLabel(lbArray[4][language]);
    selectedIndex = chDuration.getSelectedIndex();
    //   System.out.println("Select " + select);
    chDuration.removeAll();
    chDuration.addItem(lbDurationArray[0][language]);
    chDuration.addItem(lbDurationArray[1][language]);
    chDuration.addItem(lbDurationArray[2][language]);
    chDuration.addItem(lbDurationArray[3][language]);
    if (selectedIndex >= 0) chDuration.select(selectedIndex);

    selectedIndex = chChartType.getSelectedIndex();
    chChartType.removeAll();
    chChartType.addItem(lbArray[1][language]);
    chChartType.addItem(lbArray[2][language]);
    chChartType.addItem(lbArray[3][language]);
    if (selectedIndex >= 0) chChartType.select(selectedIndex);

    selectedIndex = chSpChart.getSelectedIndex();
    chSpChart.removeAll();
    chSpChart.setBackground(new Color(3,93,3));
    chSpChart.setForeground(Color.white);
    //chRightChart.setFont(new Font("default",0,10));
    for (int i=0;i<lbCodeArray.length; i++)
    {
    chSpChart.addItem(lbCodeArray[i][language+1]);
    }
    if (selectedIndex >= 0) chSpChart.select(selectedIndex);

    selectedIndex = chMA1.getSelectedIndex();
    chMA1.removeAll();
    chMA1.addItem(lbTA1Array[0][language]);
    chMA1.addItem(lbTA1Array[1][language]);
    chMA1.addItem(lbTA1Array[2][language]);
    chMA1.addItem(lbTA1Array[3][language]);
    chMA1.addItem(lbTA1Array[4][language]);
    if (selectedIndex >= 0) chMA1.select(selectedIndex);


    selectedIndex = this.chMinute.getSelectedIndex();
    chMinute.removeAll();
    chMinute.addItem(lbMinuteArray[0][language+1]);
    chMinute.addItem(lbMinuteArray[1][language+1]);
//  chTA1.addItem(lbTA1Array[2][language]);
    if (selectedIndex >= 0) chMinute.select(selectedIndex);


  }



  //set the reference of the chartScrenn such that the menubar can control it.
  public void setChartScreen(ChartScreen cs1, ChartScreen cs2, ChartScreen cs3)
  {
    chartScreen1 = cs1;
    chartScreen2 = cs2;
    chartScreen3 = cs3;
  }

  public void setChartSource(ChartSource cs)
  {
    chartSource = cs;
  }


  public void updateMenu()
  {
    setLanguage(0);
  }

  public FMenuBar()
  {
    try
    {
      jbInit();
      updateMenu();
      chDuration.addItemListener(new ItemListener()
      {
        public void itemStateChanged(ItemEvent e)
        {
          chDuration_itemStateChanged(e);
        }
      });

      chChartType.addItemListener(new ItemListener()
      {
        public void itemStateChanged(ItemEvent e)
        {
          chChartType_itemStateChanged(e);
        }
      });

      this.chSpChart.addItemListener(new ItemListener()
      {
        public void itemStateChanged(ItemEvent e)
        {
           chSpChart_itemStateChanged(e);
        }
      });

      this.chMinute.addItemListener(new ItemListener()
      {
        public void itemStateChanged(ItemEvent e)
        {
          chMinute_itemStateChanged(e);
        }
      });

      this.chMA1.addItemListener(new ItemListener()
      {
        public void itemStateChanged(ItemEvent e)
        {
          chMA1_itemStateChanged(e);
        }
      }
      );


    }
    catch(Exception e)
    {
      e.printStackTrace();
    }
  }

  private void chDuration_itemStateChanged(ItemEvent e)
  {
    //System.out.println(chDuration.getSelectedItem());
    String dtype = chDuration.getSelectedItem();
    int selectedIndex  =  chMinute.getSelectedIndex();                  //get the selected Index
    int intervals =  Integer.parseInt(lbMinuteArray[selectedIndex][0]); // get the intervals

    /*
    if (dtype == lbDurationArray[3][0] || dtype == lbDurationArray[3][1])
    {
      chChartType.setVisible(false);
    }
    else
    {
      chChartType.setVisible(true);
    }
    */

    // if it is daily chart.
    if (dtype == lbDurationArray[0][0] || dtype == lbDurationArray[0][1])
    {
      this.chMinute.setVisible(false);
      this.chMA1.setVisible(true);
      ChartUIObject lchart = chartScreen1.getLeftChart();
      if (chartScreen1.getLeftChart()!=null)
      {
        int Code = lchart.chartData.Code;
        FCommand fc = new FCommand(Code,FCommand.TYPE_DOWNLOAD_LEFT_CHART,chDuration.getSelectedIndex(),"LMain1", 500,intervals,false,this);
        chartSource.addCommand(fc);
        chartScreen1.setScreenState(ChartScreen.LOADING);
        chartScreen2.setScreenState(ChartScreen.LOADING);
        chartScreen3.setScreenState(ChartScreen.LOADING);
      }
    }

    // if it is weekly chart.
    if (dtype == lbDurationArray[1][0] || dtype == lbDurationArray[1][1])
    {
      this.chMA1.setVisible(true);
      this.chMinute.setVisible(false);
      ChartUIObject lchart = chartScreen1.getLeftChart();
      if (chartScreen1.getLeftChart()!=null)
      {
        int Code = lchart.chartData.Code;
        FCommand fc = new FCommand(Code,FCommand.TYPE_DOWNLOAD_LEFT_CHART,chDuration.getSelectedIndex(),"LMain1", 500,intervals,false,this);
        chartSource.addCommand(fc);
        chartScreen1.setScreenState(ChartScreen.LOADING);
        chartScreen2.setScreenState(ChartScreen.LOADING);
        chartScreen3.setScreenState(ChartScreen.LOADING);
      }
    }

    // if it is monthly chart
    if (dtype == lbDurationArray[2][0] || dtype == lbDurationArray[2][1])
    {
      this.chMA1.setVisible(true);
      this.chMinute.setVisible(false);
      ChartUIObject lchart = chartScreen1.getLeftChart();
      if (chartScreen1.getLeftChart()!=null)
      {
        int Code = lchart.chartData.Code;
        FCommand fc = new FCommand(Code,FCommand.TYPE_DOWNLOAD_LEFT_CHART,chDuration.getSelectedIndex(),"LMain1", 500,intervals,false,this);
        chartSource.addCommand(fc);
        chartScreen1.setScreenState(ChartScreen.LOADING);
        chartScreen2.setScreenState(ChartScreen.LOADING);
        chartScreen3.setScreenState(ChartScreen.LOADING);
      }
    }

    // if it is intraday chart
    if (dtype == lbDurationArray[3][0] || dtype == lbDurationArray[3][1])
    {
      this.chMA1.setVisible(false);
      this.chMinute.setVisible(true);
      ChartUIObject lchart = chartScreen1.getLeftChart();
      if (chartScreen1.getLeftChart()!=null)
      {
        int Code = lchart.chartData.Code;
        FCommand fc = new FCommand(Code,FCommand.TYPE_DOWNLOAD_LEFT_CHART,chDuration.getSelectedIndex(),"LMain1", 500,intervals,false,this);
        chartSource.addCommand(fc);
        chartScreen1.setScreenState(ChartScreen.LOADING);
        chartScreen2.setScreenState(ChartScreen.LOADING);
        chartScreen3.setScreenState(ChartScreen.LOADING);
      }
    }

    this.setEnable(false);
  }

  private void chMinute_itemStateChanged(ItemEvent e)
  {
    //String dtype = chMinute.getSelectedItem();
    int selectedIndex  =  chMinute.getSelectedIndex();                  //get the selected Index
    int intervals =  Integer.parseInt(lbMinuteArray[selectedIndex][0]); // get the intervals

    ChartUIObject lchart = chartScreen1.getLeftChart();
    if (chartScreen1.getLeftChart()!=null)
    {
      FCommand fc = new FCommand(lchart.chartData.Code,FCommand.TYPE_DOWNLOAD_LEFT_CHART,chDuration.getSelectedIndex(),"LMain1", 500,intervals,false,this);
      chartSource.addCommand(fc);
      chartScreen1.setScreenState(ChartScreen.LOADING);
      chartScreen2.setScreenState(ChartScreen.LOADING);
      chartScreen3.setScreenState(ChartScreen.LOADING);
    }
     this.setEnable(false);

  }

  private void chSpChart_itemStateChanged(ItemEvent e)
  {
    //System.out.println("Right Chart");
    String dtype = chSpChart.getSelectedItem();
    if (dtype == lbCodeArray[0][1] || dtype == lbCodeArray[0][2])
    {
//      ChartUIObject cchart = (ChartUIObject) chartScreen1.getChart("LMain1");
//     cchart.setVisible(false);
//      chartScreen1.updateBaseScreen();
      chartScreen1.repaint();
      return;
    }
    else
    {
      String sCode ="";
      for (int i=1;i<lbCodeArray.length;i++)
      {
        if (dtype == lbCodeArray[i][1] || dtype == lbCodeArray[i][2])
        {
          sCode = lbCodeArray[i][0];
          break;
        }
      }
      if (sCode !="")
      {
        int Code = Integer.parseInt(sCode);
        //System.out.println(this.chDuration.getSelectedIndex());
        FCommand fc = new FCommand(Code,FCommand.TYPE_DOWNLOAD_LEFT_CHART,chDuration.getSelectedIndex(),"LMain1", 500,10,false,this);
        chartSource.addCommand(fc);
        chartScreen1.setScreenState(ChartScreen.LOADING);
        chartScreen2.setScreenState(ChartScreen.LOADING);
        chartScreen3.setScreenState(ChartScreen.LOADING);
      }
    }
    this.setEnable(false);
  }

  private void chMA1_itemStateChanged(ItemEvent e)
  {
    String ctype = chMA1.getSelectedItem();

  //  System.out.println("TA...." +ctype);
    if (ctype == lbTA1Array[0][0] || ctype == lbTA1Array[0][1])
    {
      ChartUIObject taChart =  chartScreen1.getChart("TA1Chart");
      if (taChart == null)
      {
        System.out.println("No TA1Chart");
        return;
      }
      taChart.setVisible(false);// .removeChart("TA1Chart");
      chartScreen1.updateBaseScreen();
      chartScreen1.repaint();
//    System.out.println("TA....");

    }
    else if (ctype == lbTA1Array[1][0] || ctype == lbTA1Array[1][1])
    {
      ChartUIObject taChart =  chartScreen1.getChart("TA1Chart");
      if (taChart == null)
      {
        System.out.println("No TA1Chart");
        return;
      }

      taChart.chartType = ChartUIObject.SIMPLEMOVINGAVERAGE;
      taChart.axisBar  = ChartUIObject.NONE;
      taChart.chartData.calculateMovingAverage(taChart.chartData.fTAconfig.SMAN1,taChart.chartData.fTAconfig.SMAN2,taChart.chartData.fTAconfig.SMAN3);
      taChart.setVisible(true);
      taChart.setShowXaxis(false);
      chartScreen1.updateBaseScreen();
      chartScreen1.repaint();
    }
    else if (ctype == lbTA1Array[2][0] || ctype == lbTA1Array[2][1])
    {
     // System.out.println("TTAAA");
      ChartUIObject taChart =  chartScreen1.getChart("TA1Chart");
      if (taChart == null) return;

      taChart.chartType = ChartUIObject.WEIGHTEDMOVINGAVERAGE;
      taChart.axisBar  = ChartUIObject.NONE;
      taChart.chartData.calculateWeightedMovingAverage(taChart.chartData.fTAconfig.WMAN1,taChart.chartData.fTAconfig.WMAN2,taChart.chartData.fTAconfig.WMAN3);
      taChart.setVisible(true);
      taChart.setShowXaxis(false);
      chartScreen1.updateBaseScreen();
      chartScreen1.repaint();
    }
    else if (ctype == lbTA1Array[3][0] || ctype == lbTA1Array[3][1])
    {
      ChartUIObject taChart =  chartScreen1.getChart("TA1Chart");
      if (taChart == null) return;
      taChart.chartType = ChartUIObject.EXPONENTIALMOVINGAVERAGE;
      taChart.axisBar  = ChartUIObject.NONE;
      taChart.chartData.calculateExponentialMovingAverage(taChart.chartData.fTAconfig.EMA1,taChart.chartData.fTAconfig.EMA2 ,taChart.chartData.fTAconfig.EMA3 );
      taChart.setVisible(true);
      taChart.setShowXaxis(false);
      chartScreen1.updateBaseScreen();
      chartScreen1.repaint();
    }
    else if (ctype == lbTA1Array[4][0] || ctype == lbTA1Array[4][1])
    {
      ChartUIObject taChart =  chartScreen1.getChart("TA1Chart");
      if (taChart == null) return;

/*
      ChartUIObject lcchart =  chartScreen1.getLeftChart();
      if (lcchart == null) return;
      ChartUIObject taChart  = new ChartUIObject(lcchart.chartData,"TA1Chart");*/
      taChart.chartType = ChartUIObject.BOLLINGERBAND;
      taChart.axisBar  = ChartUIObject.NONE;
      taChart.chartData.calculateBollingerBand(taChart.chartData.fTAconfig.bbN,taChart.chartData.fTAconfig.bbDevation);
      taChart.setVisible(true);
      taChart.setShowXaxis(false);
      chartScreen1.updateBaseScreen();
      chartScreen1.repaint();

    }







  }

  private void chChartType_itemStateChanged(ItemEvent e)
  {
    //System.out.println(chChartType.getSelectedItem());
    String ctype = chChartType.getSelectedItem();
    if (ctype == lbArray[1][0] || ctype == lbArray[1][1])
    {
      ChartUIObject cchart = (ChartUIObject) chartScreen1.getChart("LMain1");
      if (cchart!=null)
      {
     //   System.out.println("changing...");
        cchart.chartType = ChartUIObject.BAR;
      }

      ChartUIObject cRchart = (ChartUIObject) chartScreen1.getChart("LMain1");
      if (cRchart!=null)
      {
     //   System.out.println("changing...");
        cRchart.chartType = ChartUIObject.BAR;
      }
    }
    else if (ctype == lbArray[2][0] || ctype == lbArray[2][1])
    {
      ChartUIObject cchart = (ChartUIObject) chartScreen1.getChart("LMain1");
      if (cchart!=null)
      {
      //  System.out.println("changing...");
        cchart.chartType = ChartUIObject.CANDLE;
      }

      ChartUIObject cRchart = (ChartUIObject) chartScreen1.getChart("LMain1");
      if (cRchart!=null)
      {
     //   System.out.println("changing...");
        cRchart.chartType = ChartUIObject.CANDLE;
      }

    }
    else if (ctype == lbArray[3][0] || ctype == lbArray[3][1])
    {
      ChartUIObject cchart = (ChartUIObject) chartScreen1.getChart("LMain1");
      if (cchart!=null)
      {
      //  System.out.println("changing...");
        cchart.chartType = ChartUIObject.LINE ;
      }

      ChartUIObject cRchart = (ChartUIObject) chartScreen1.getChart("LMain1");
      if (cRchart!=null)
      {
     //   System.out.println("changing...");
        cRchart.chartType = ChartUIObject.LINE;
      }

    }
    chartScreen1.updateBaseScreen();
    chartScreen1.repaint();
  }

  private void jbInit() throws Exception
  {
    tfCode.setBounds(new Rectangle(8, 2, 56, 23));
    tfCode.setFont(new java.awt.Font("Dialog", 0, 14));
    tfCode.setBackground(Color.white);
    tfCode.addKeyListener(this);   //add a key listener
    this.setLayout(null);
    btPlot.setLabel("Plot");
    btPlot.setBounds(new Rectangle(67, 2, 42, 22));
    btPlot.addActionListener(new java.awt.event.ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        btPlot_actionPerformed(e);
      }
    });
    btPlot.setFont(new java.awt.Font("Dialog", 0, 10));
    chDuration.setBounds(new Rectangle(111, 2, 58, 22));
    chDuration.setFont(new java.awt.Font("Dialog", 0, 10));
    chChartType.setBounds(new Rectangle(170, 2, 52, 22));
    chChartType.setFont(new java.awt.Font("Dialog", 0, 10));
    chSpChart.setBounds(new Rectangle(223, 2, 110, 22));
    chSpChart.setFont(new java.awt.Font("Dialog", 0, 10));
    chMA1.setBounds(new Rectangle(333, 2, 94, 22));
    chMA1.setFont(new java.awt.Font("Dialog", 0, 10));
    chMinute.setVisible(false);
    chMinute.setBounds(new Rectangle(333, 2, 61, 22));
    this.setFont(new java.awt.Font("Dialog", 0, 10));
    this.add(tfCode, null);
    this.add(btPlot, null);
    this.add(chDuration, null);
    this.add(chMinute, null);
    this.add(chChartType, null);
    this.add(chSpChart, null);
    this.add(chMA1, null);
  }

  /*** Invoked when a key has been pressed.*/
  public void keyPressed(KeyEvent e)
  {
    //System.out.println("keyevent" + e);
    //System.out.println("keytyped");
    // System.out.println(e.getKeyCode());
    //e.setKeyChar('a');
    if (e.getKeyCode()== 10 )
    {
      //System.out.println("Enter");
      btPlot_actionPerformed(null);
    }

  }
  /*** Invoked when a key has been released.*/
  public void keyReleased(KeyEvent e){}
  public void keyTyped(KeyEvent e){}

  void btPlot_actionPerformed(ActionEvent e)
  {
  //  System.out.println("Action: " + this.chDuration.getSelectedIndex());
    int selectedIndex  =  chMinute.getSelectedIndex();                  //get the selected Index
    int intervals =  Integer.parseInt(lbMinuteArray[selectedIndex][0]); // get the intervals

    String cc = tfCode.getText();
    if (!FFormater.isNumerical(cc))
    {
       this.tfCode.setText("");
       return;
    }
    int Code = Integer.parseInt(cc);
    this.tfCode.setText("");

    FCommand fc = new FCommand(Code,FCommand.TYPE_DOWNLOAD_LEFT_CHART,chDuration.getSelectedIndex(),"LMain1", 500,intervals,false,this);
    chartSource.addCommand(fc);
    this.setEnable(false);
    chartScreen1.setScreenState(ChartScreen.LOADING);
    chartScreen2.setScreenState(ChartScreen.LOADING);
    chartScreen3.setScreenState(ChartScreen.LOADING);



  }

  public void setEnable(boolean b)
  {
    this.tfCode.setEditable(b);
    this.chDuration.setEnabled(b);
    this.chChartType.setEnabled(b);
    this.chSpChart.setEnabled(b);
    this.btPlot.setEnabled(b);
    this.chMA1.setEnabled(b);
    this.chMinute.setEnabled(b);
  }
  public void OnReceivedError(FCommand fc)
  {
 //   System.out.println("Error");
    chartScreen1.setScreenState(ChartScreen.STARTED);
    chartScreen2.setScreenState(ChartScreen.STARTED);
    chartScreen3.setScreenState(ChartScreen.STARTED);
    chartScreen1.repaint();
    chartScreen2.repaint();
    chartScreen3.repaint();
    this.setEnable(true);
  }


  public void setTAMenu(FTAMenu ftam)
  {
    taMenu = ftam;
  }


  public void OnReceivedChartData(FCommand fc, ChartData chartData)
  {

    ChartUIObject cl = chartScreen1.getLeftChart();
    if (cl!=null)
    {
      if (cl.chartType == ChartUIObject.PERCENTAGE )
      return;
    }

 // System.out.println("OnReceive Chart Data");
    ChartData mydata = chartData;
    ChartData mydata2 = new ChartData();
    mydata2.data = mydata.data;
    mydata2.dataType = mydata.dataType;
    mydata2.EName = mydata.EName;
    mydata2.CName = mydata.CName;
    mydata2.Code = mydata.Code;

    ChartUIObject mychart1 = new ChartUIObject(mydata,"LMain1");
    ChartUIObject mychart2 = new ChartUIObject(mydata2,"TA1Chart");
    ChartUIObject mychart3 = new ChartUIObject(mydata,"LMain3");
    ChartUIObject mychart4 = new ChartUIObject(mydata,"TA1Chart");

    // if it is intraday, plot line chart by default because it is nice.
    if (mychart1.chartData.dataType == ChartData.INTRADAY )
    {
      mychart1.chartType = ChartUIObject.LINE;
      chChartType.select(ChartUIObject.LINE);
    }
    else
    {
      mychart1.chartType = chChartType.getSelectedIndex();
    }

    mychart1.axisBar = ChartUIObject.LEFTAXIS;
    mychart2.axisBar = ChartUIObject.LEFTAXIS;
    mychart3.chartType = ChartUIObject.VOLUME;
    mychart3.axisBar = ChartUIObject.LEFTAXIS;
    mychart3.firstColor = new Color(50,100,50);
    mychart4.chartType = ChartUIObject.LINE;
    mychart4.axisBar = ChartUIObject.NONE;
    mychart4.setVisible(false);

    mychart2.chartType = ChartUIObject.VOLUME;
//  mychart2.chartData.calculateWilliamR(18);

    chartScreen1.removeAllCharts();
    this.chMA1.select(0); // select None chart for TA1
    chartScreen2.removeAllCharts();
    chartScreen3.removeAllCharts();

    chartScreen1.addChart(mychart1);
    chartScreen2.addChart(mychart2);
    chartScreen3.addChart(mychart3);
    chartScreen1.addChart(mychart4);

    chartScreen1.faction.zoomRecords.removeAllElements();
    chartScreen2.faction.zoomRecords.removeAllElements();
    chartScreen3.faction.zoomRecords.removeAllElements();

    chartScreen1.faction.lineRecords.removeAllElements();
    chartScreen2.faction.lineRecords.removeAllElements();
    chartScreen3.faction.lineRecords.removeAllElements();

    // update tachart and calculate.........
    chartScreen1.undoZoom();
    chartScreen2.undoZoom();
    chartScreen3.undoZoom();

    if (taMenu!=null)
    {
      taMenu.chChartType_itemStateChanged(null);
    }

    chartScreen1.setScreenState(ChartScreen.STARTED);
    chartScreen2.setScreenState(ChartScreen.STARTED);
    chartScreen3.setScreenState(ChartScreen.STARTED);

    this.setEnable(true);
  }

}