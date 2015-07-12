package fmechart;

import java.awt.*;
import java.awt.event.*;
import java.util.Vector;

public class FCompareBar extends FBar implements KeyListener, ChartSourceListener
{
  TextField tfCode  = new TextField();
  Button btAddChart = new Button();
  Button btRemove   = new Button();
  Choice chACode    = new Choice();
  Button btClose    = new Button();

  private Vector pcCharts = new Vector();

  private final Color lineColor[] = {
                        new Color(1,255,255),
                        new Color(1,255,37),
                        new Color(228,225,1),
                        new Color(225,132,1),
                        new Color(225,132,1),
                        new Color(255,1,67),
                        new Color(255,1,228),
                        new Color(191,1,255),
                        new Color(1,49,255),
                        };


  private final String lbArray[][] = {
                      {"Add","\u52a0\u5165"},
                      {"Remove", "\u79fb\u9664"},
                      {"Close" , "\u95dc\u9589"}
                    };

  final String lbCodeArray[][] = {
                      {"0001","Please Select","Please Select"},
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

  //the reference of chartscreens which this buttonbar can control it.
  private ChartScreen chartScreen1 = null;
  private ChartScreen chartScreen2 = null;
  private ChartScreen chartScreen3 = null;
  private FButtonBar fButtonBar = null;
  private FMenuBar fMenuBar= null;
  private ChartSource chartSource = null;
  private int language = FConfig.constEnglish;
  Choice chSpChart = new Choice();

  public void setMenus(FButtonBar fb, FMenuBar fm)
  {
    fButtonBar = fb;
    fMenuBar = fm;
  }


  public void setLanguage(int tlanguage)
  {
    language = tlanguage;
    btAddChart.setLabel(lbArray[0][language]);
    btRemove.setLabel(lbArray[1][language]);
    btClose.setLabel(lbArray[2][language]);

    int selectedIndex = chSpChart.getSelectedIndex();
    chSpChart.removeAll();
    chSpChart.setBackground(new Color(3,93,3));
    chSpChart.setForeground(Color.white);
    //chRightChart.setFont(new Font("default",0,10));
    for (int i=0;i<lbCodeArray.length; i++)
    {
      chSpChart.addItem(lbCodeArray[i][language+1]);
    }
    if (selectedIndex >= 0) chSpChart.select(selectedIndex);

  }

  //set the reference of the chartScrenn such that the button bar can control it.
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

  public FCompareBar()
  {
    try
    {
      jbInit();
      this.chSpChart.addItemListener(new ItemListener()
      {
        public void itemStateChanged(ItemEvent e)
        {
           chSpChart_itemStateChanged(e);
        }
      });
      setLanguage(language);
    }
    catch(Exception e)
    {
      e.printStackTrace();
    }
  }
  private void jbInit() throws Exception
  {
    tfCode.setBackground(Color.white);
    tfCode.setBounds(new Rectangle(10, 3, 56, 20));
    this.setLayout(null);
    btAddChart.setFont(new java.awt.Font("Dialog", 0, 10));
    btAddChart.setLabel("Add");
    btAddChart.setBounds(new Rectangle(69, 3, 55, 20));
    btAddChart.addActionListener(new java.awt.event.ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        btAddChart_actionPerformed(e);
      }
    });
    btRemove.setFont(new java.awt.Font("Dialog", 0, 10));
    btRemove.setLabel("Remove");
    btRemove.setBounds(new Rectangle(125, 3, 66, 20));
    btRemove.addActionListener(new java.awt.event.ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        btRemove_actionPerformed(e);
      }
    });
    chACode.setFont(new java.awt.Font("Dialog", 0, 10));
    chACode.setBounds(new Rectangle(192, 3, 76, 22));
    btClose.setFont(new java.awt.Font("Dialog", 0, 10));
    btClose.setLabel("Close");
    btClose.setBounds(new Rectangle(270, 3, 55, 20));
    btClose.addActionListener(new java.awt.event.ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        btClose_actionPerformed(e);
      }
    });
    chSpChart.setFont(new java.awt.Font("Dialog", 0, 10));
    chSpChart.setBounds(new Rectangle(327, 3, 103, 21));
    this.add(tfCode, null);
    this.add(btAddChart, null);
    this.add(btRemove, null);
    this.add(chACode, null);
    this.add(btClose, null);
    this.add(chSpChart, null);
    tfCode.addKeyListener(this);
  }

  void btClose_actionPerformed(ActionEvent e)
  {
    if (fMenuBar!=null && chartScreen1!= null)
    {
      ChartUIObject taChart = chartScreen1.getChart("TA1Chart");
      if (taChart!=null)
      {
        String chTa = fMenuBar.chMA1.getSelectedItem();

        //if TA chart is invisible......
        if (chTa == fMenuBar.lbTA1Array[0][0] || chTa == fMenuBar.lbTA1Array[0][1])
        {
          taChart.setVisible(false);
        } else {
          taChart.setVisible(true);              // set TA chart to be visible return to normal state.
        }
      }

      this.setVisible(false);
      fMenuBar.setVisible(true);
      chACode.removeAll();
      ChartUIObject cchart = chartScreen1.getLeftChart();
      if (cchart != null)
      {
        cchart.chartType = fMenuBar.chChartType.getSelectedIndex();
      }
      pcCharts.removeAllElements();
      chartScreen1.faction.lineRecords.removeAllElements();
      chartScreen1.faction.goldenPartitionLine = null;

      chartScreen1.removeChartsByType(ChartUIObject.PERCENTAGE);
      chartScreen1.updateBaseScreen();
      chartScreen1.repaint();




    }
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
   //   System.out.println("Enter");
       btAddChart_actionPerformed(null);
    }

  }
  /*** Invoked when a key has been released.*/
  public void keyReleased(KeyEvent e){}
  public void keyTyped(KeyEvent e){}

  void btAddChart_actionPerformed(ActionEvent e)
  {
    System.out.println("add action");
//    System.out.println(fMenuBar.chDuration.getSelectedIndex());

    if (fMenuBar == null || chartSource == null || chartScreen1.getLeftChart()== null) return;

    String cc = tfCode.getText();

    //System.out.println("HELLO");
    if (!FFormater.isNumerical(cc))
    {
       this.tfCode.setText("");
       return;
    }

    int Code = Integer.parseInt(cc);
    this.tfCode.setText("");
    //System.out.println(fMenuBar.chDuration.getSelectedIndex());
    if (Code != chartScreen1.getLeftChart().chartData.Code)
    {
      int intervals = chartScreen1.getLeftChart().chartData.intradayInterval;
      //System.out.println("Intervals : " + intervals);
      int NumberOfPoints = chartScreen1.getLeftChart().chartData.data.size();
      FCommand fc = new FCommand(Code,FCommand.TYPE_DOWNLOAD_LEFT_CHART,fMenuBar.chDuration.getSelectedIndex(),String.valueOf(Code), NumberOfPoints,intervals,true,this);
      chartSource.addCommand(fc);
      chartScreen1.setScreenState(ChartScreen.LOADING);
    }



  }

  public void OnReceivedError(FCommand fc)
  {
    System.out.println("Error");
    chartScreen1.setScreenState(ChartScreen.STARTED);
  //  chartScreen2.setScreenState(ChartScreen.STARTED);
  //  chartScreen3.setScreenState(ChartScreen.STARTED);
  //  chartScreen1.repaint();
  //  chartScreen2.repaint();
  //  chartScreen3.repaint();
  }
  public void OnReceivedChartData(FCommand fc, ChartData chartData)
  {

    if ( chartScreen1.getChart(String.valueOf(fc.Code)) !=null)
    {
      chartScreen1.setScreenState(ChartScreen.STARTED);
      return;
    }


    ChartData mydata = chartData;

    ChartUIObject mychart1 = new ChartUIObject(mydata,String.valueOf(fc.Code));
    mychart1.axisBar = ChartUIObject.NONE;
    mychart1.chartType = ChartUIObject.PERCENTAGE ;
    mychart1.setShowXaxis(false);
    mychart1.firstColor = lineColor[pcCharts.size()%lineColor.length];


    chartScreen1.addChart(mychart1);
    chartScreen1.faction.zoomRecords.removeAllElements();
    chartScreen1.faction.lineRecords.removeAllElements();
    chartScreen1.updateBaseScreen();
    chartScreen1.setScreenState(ChartScreen.STARTED);
    pcCharts.addElement(mychart1);
    chACode.removeAll();
    for (int i=0;i<pcCharts.size();i++)
    {
      ChartUIObject cchart = (ChartUIObject) pcCharts.elementAt(i);
      chACode.add(String.valueOf(cchart.chartData.Code));
    }
    chSpChart.select(0);

  }

  void btRemove_actionPerformed(ActionEvent e)
  {
      if (chACode.getItemCount() == 0 ) return;
      pcCharts.removeElementAt(chACode.getSelectedIndex());
      chartScreen1.removeChart(Integer.parseInt(chACode.getSelectedItem()));
      chACode.removeAll();
      for (int i=0;i<pcCharts.size();i++)
      {
      ChartUIObject cchart = (ChartUIObject) pcCharts.elementAt(i);
      chACode.add(String.valueOf(cchart.chartData.Code));
      }
      chartScreen1.updateBaseScreen();
      chartScreen1.repaint();

//    chartScreen1.removeAllCharts();

  }

  private void chSpChart_itemStateChanged(ItemEvent e)
  {
    //System.out.println("Right Chart");
    String dtype = chSpChart.getSelectedItem();
    if (dtype == lbCodeArray[0][1] || dtype == lbCodeArray[0][2])
    {
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
        // add the sp chart to compare chart.
        int Code = Integer.parseInt(sCode);
        this.tfCode.setText(sCode);
        this.btAddChart_actionPerformed(null);
      }
    }
  }

}