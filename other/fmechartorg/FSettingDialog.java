package fmechart;

import java.awt.*;
import java.awt.event.*;

/**
 * Title:        FME Chart Project for E-finet
 * Description:
 * Copyright:    Copyright (c)
 * Company:
 * @author
 * @version 1.0
*/

public class FSettingDialog extends Frame implements WindowListener
{
  Label label1 = new Label();
  TextField tfSMA1 = new TextField();
  Label label3 = new Label();
  TextField tfSMA2 = new TextField();
  Label label5 = new Label();
  TextField tfSMA3 = new TextField();
  Label lbSMA = new Label();
  Label lbWMA = new Label();
  Label label6 = new Label();
  TextField tfWMA1 = new TextField();
  Label label7 = new Label();
  TextField tfWMA2 = new TextField();
  Label label8 = new Label();
  TextField tfWMA3 = new TextField();
  Button btSMAApply = new Button();
  Button btWMAApply = new Button();
  Label lbEMA = new Label();
  Label label10 = new Label();
  TextField tfEMA1 = new TextField();
  Label label11 = new Label();
  TextField tfEMA2 = new TextField();
  TextField tfEMA3 = new TextField();
  Button btEMAApply = new Button();
  Label label12 = new Label();
  Label lbBollinger = new Label();
  Label label14 = new Label();
  TextField tfBB = new TextField();
  Label label15 = new Label();
  TextField tfDevation = new TextField();
  Button btBollingerApply = new Button();
  Button btCancel = new Button();
  Button btOK = new Button();
  Button btHelp = new Button();


  // the reference of chart and the chartscreen
  private String TAChartName = new String("TA1Chart"); // the chart Name (id) that this setting window can control
  private ChartScreen chartScreen = null;

  private int language = FConfig.constEnglish;

  private String lbArray[][] = {  {"OK","\u78ba\u5b9a"}          //0
                          ,{"Apply","\u5957\u7528"}    //1
                          ,{"Cancel","\u95dc\u9589"}  //2
                          ,{"Help","\u6307\u5f15"}      //3
                          ,{"Simple Moving Average","\u7c21\u55ae\u79fb\u52d5\u5e73\u5747\u7dda"}      //4
                          ,{"Weighted Moving Average","\u52a0\u6b0a\u79fb\u52d5\u5e73\u5747\u7dda"} //5
                          ,{"Exponential Moving Average","\u6307\u6578\u79fb\u52d5\u5e73\u5747\u7dda"} //6
                          ,{"Bollinger Bands","\u4fdd\u6b77\u52a0\u901a\u9053"}  //7
                        };

  public FSettingDialog()
  {
    try
    {
      jbInit();
      addWindowListener(this);
    }
    catch(Exception e)
    {
      e.printStackTrace();
    }
  }


  private void jbInit() throws Exception
  {
    label1.setBounds(new Rectangle(41, 64, 37, 25));
    label1.setAlignment(2);
    label1.setText("MA1");
    this.setLayout(null);
    label3.setBounds(new Rectangle(120, 64, 31, 25));
    label3.setAlignment(2);
    label3.setText("MA2");
    label5.setBounds(new Rectangle(202, 64, 30, 25));
    label5.setAlignment(2);
    label5.setText("MA3");
    lbSMA.setBounds(new Rectangle(41, 38, 171, 25));
    lbSMA.setText("Simple Moving Average ,SMA");
    lbWMA.setBounds(new Rectangle(41, 101, 201, 25));
    lbWMA.setText("Weighted Moving Average, WMA");
    label6.setBounds(new Rectangle(41, 127, 37, 25));
    label6.setAlignment(2);
    label6.setText("MA1");
    tfSMA1.setBackground(Color.white);
    tfSMA1.setBounds(new Rectangle(78, 63, 38, 27));
    tfSMA1.setText("XX");
    tfSMA2.setBackground(Color.white);
    tfSMA2.setBounds(new Rectangle(153, 63, 38, 27));
    tfSMA2.setText("XX");
    tfSMA3.setBackground(Color.white);
    tfSMA3.setBounds(new Rectangle(232, 63, 38, 27));
    tfSMA3.setText("XX");
    tfWMA1.setBackground(Color.white);
    tfWMA1.setBounds(new Rectangle(78, 126, 38, 27));
    tfWMA1.setText("XX");
    label7.setBounds(new Rectangle(124, 127, 29, 25));
    label7.setAlignment(2);
    label7.setText("MA2");
    tfWMA2.setBackground(Color.white);
    tfWMA2.setBounds(new Rectangle(153, 126, 38, 27));
    tfWMA2.setText("XX");
    label8.setBounds(new Rectangle(205, 127, 29, 25));
    label8.setAlignment(1);
    label8.setText("MA3");
    tfWMA3.setBackground(Color.white);
    tfWMA3.setBounds(new Rectangle(232, 126, 38, 27));
    tfWMA3.setText("XX");
    btSMAApply.setBackground(Color.orange);
    btSMAApply.setBounds(new Rectangle(273, 64, 43, 25));
    btSMAApply.setLabel("Apply");
    btSMAApply.addActionListener(new java.awt.event.ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        btSMAApply_actionPerformed(e);
      }
    });
    btWMAApply.setBackground(Color.orange);
    btWMAApply.setBounds(new Rectangle(273, 127, 43, 25));
    btWMAApply.setLabel("Apply");
    btWMAApply.addActionListener(new java.awt.event.ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        btWMAApply_actionPerformed(e);
      }
    });
    lbEMA.setBounds(new Rectangle(41, 164, 200, 25));
    lbEMA.setText("Exponential Moving Average, EMA");
    label10.setBounds(new Rectangle(41, 190, 37, 25));
    label10.setAlignment(2);
    label10.setText("MA1");
    tfEMA1.setBackground(Color.white);
    tfEMA1.setBounds(new Rectangle(78, 189, 38, 27));
    tfEMA1.setText("XX");
    label11.setBounds(new Rectangle(124, 190, 29, 25));
    label11.setAlignment(2);
    label11.setText("MA2");
    tfEMA2.setBackground(Color.white);
    tfEMA2.setBounds(new Rectangle(153, 189, 38, 27));
    tfEMA2.setText("XX");
    tfEMA3.setBackground(Color.white);
    tfEMA3.setBounds(new Rectangle(232, 189, 38, 27));
    tfEMA3.setText("XX");
    btEMAApply.setBackground(Color.orange);
    btEMAApply.setBounds(new Rectangle(273, 190, 43, 25));
    btEMAApply.setLabel("Apply");
    btEMAApply.addActionListener(new java.awt.event.ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        btEMAApply_actionPerformed(e);
      }
    });
    label12.setBounds(new Rectangle(203, 190, 27, 25));
    label12.setAlignment(2);
    label12.setText("MA3");
    this.setBackground(FConfig.DialogColor);
    lbBollinger.setBounds(new Rectangle(41, 227, 104, 25));
    lbBollinger.setText("Bollinger\'s Band");
    label14.setBounds(new Rectangle(41, 253, 37, 25));
    label14.setAlignment(2);
    label14.setText("MA1");
    tfBB.setBackground(Color.white);
    tfBB.setBounds(new Rectangle(78, 252, 38, 27));
    tfBB.setText("XX");
    label15.setBounds(new Rectangle(132, 253, 57, 25));
    label15.setAlignment(2);
    label15.setText("Deviation");
    tfDevation.setBackground(Color.white);
    tfDevation.setBounds(new Rectangle(191, 252, 38, 27));
    tfDevation.setText("XX");
    btBollingerApply.setBackground(Color.orange);
    btBollingerApply.setBounds(new Rectangle(250, 253, 43, 25));
    btBollingerApply.setLabel("Apply");
    btBollingerApply.addActionListener(new java.awt.event.ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        btBollingerApply_actionPerformed(e);
      }
    });
    btCancel.setBackground(Color.orange);
    btCancel.setBounds(new Rectangle(163, 293, 50, 25));
    btCancel.setLabel("Cancel");
    btCancel.addActionListener(new java.awt.event.ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        btCancel_actionPerformed(e);
      }
    });
    btOK.setBackground(Color.orange);
    btOK.setBounds(new Rectangle(58, 293, 50, 25));
    btOK.setLabel("OK");
    btOK.addActionListener(new java.awt.event.ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        btOK_actionPerformed(e);
      }
    });
    btHelp.setBackground(Color.orange);
    btHelp.setBounds(new Rectangle(273, 293, 50, 25));
    btHelp.setLabel("Help");
    btHelp.addActionListener(new java.awt.event.ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        btHelp_actionPerformed(e);
      }
    });
    this.add(lbBollinger, null);
    this.add(label14, null);
    this.add(tfBB, null);
    this.add(tfEMA2, null);
    this.add(tfWMA2, null);
    this.add(tfSMA2, null);
    this.add(tfDevation, null);
    this.add(tfEMA3, null);
    this.add(tfSMA3, null);
    this.add(tfWMA3, null);
    this.add(label8, null);
    this.add(btWMAApply, null);
    this.add(btEMAApply, null);
    this.add(btSMAApply, null);
    this.add(label15, null);
    this.add(btBollingerApply, null);
    this.add(label1, null);
    this.add(tfSMA1, null);
    this.add(lbSMA, null);
    this.add(lbWMA, null);
    this.add(label6, null);
    this.add(lbEMA, null);
    this.add(label10, null);
    this.add(tfWMA1, null);
    this.add(tfEMA1, null);
    this.add(btOK, null);
    this.add(btHelp, null);
    this.add(label11, null);
    this.add(label7, null);
    this.add(label3, null);
    this.add(label5, null);
    this.add(label12, null);
    this.add(btCancel, null);
  }

  void btWMAApply_actionPerformed(ActionEvent e)
  {

    if ( !(FFormater.isNumerical(tfWMA1.getText()) && FFormater.isNumerical(tfWMA2.getText())  && FFormater.isNumerical(tfWMA3.getText())) )
    {
      System.out.println("SMAAPPLy ERROR");
      this.updateSetting();
      return;
    }
    ChartUIObject taChart = chartScreen.getChart(TAChartName);
    if (taChart == null) return;
    int N1, N2, N3;
    N1 = Integer.parseInt(tfWMA1.getText());
    N2 = Integer.parseInt(tfWMA2.getText());
    N3 = Integer.parseInt(tfWMA3.getText());

// data vaildation.
    if (N1<=0 || N2 <=0 || N3<=0 || N1 > taChart.chartData.data.size() ||  N2 > taChart.chartData.data.size() ||  N3 > taChart.chartData.data.size() )
    {
      this.updateSetting();
      return;
    }
//////////////////////////////////////////////////////////////////////////////////////////////////

    taChart.chartData.fTAconfig.WMAN1 = N1;
    taChart.chartData.fTAconfig.WMAN2 = N2;
    taChart.chartData.fTAconfig.WMAN3 = N3;
    if (taChart.chartType == ChartUIObject.WEIGHTEDMOVINGAVERAGE)
    taChart.chartData.calculateWeightedMovingAverage(N1,N2,N3);
    chartScreen.updateBaseScreen();
    chartScreen.repaint();
  }

  void btOK_actionPerformed(ActionEvent e)
  {
    this.btBollingerApply_actionPerformed(null);
    this.btEMAApply_actionPerformed(null);
    this.btSMAApply_actionPerformed(null);
    this.btWMAApply_actionPerformed(null);
    this.dispose();
  }

  void btCancel_actionPerformed(ActionEvent e)
  {

    this.dispose();
  }

  void btHelp_actionPerformed(ActionEvent e)
  {

  }

  void btSMAApply_actionPerformed(ActionEvent e)
  {

    if ( !(FFormater.isNumerical(tfSMA1.getText()) && FFormater.isNumerical(tfSMA2.getText())  && FFormater.isNumerical(tfSMA3.getText())) )
    {
      System.out.println("SMAAPPLy ERROR");
      this.updateSetting();
      return;
    }
    ChartUIObject taChart = chartScreen.getChart(TAChartName);
    if (taChart == null) return;
    int N1, N2, N3;
    N1 = Integer.parseInt(tfSMA1.getText());
    N2 = Integer.parseInt(tfSMA2.getText());
    N3 = Integer.parseInt(tfSMA3.getText());

// data vaildation.
    if (N1<=0 || N2 <=0 || N3<=0 || N1 > taChart.chartData.data.size() ||  N2 > taChart.chartData.data.size() ||  N3 > taChart.chartData.data.size() )
    {
      this.updateSetting();
      return;
    }
//////////////////////////////////////////////////////////////////////////////////////////////////

    taChart.chartData.fTAconfig.SMAN1 = N1;
    taChart.chartData.fTAconfig.SMAN2 = N2;
    taChart.chartData.fTAconfig.SMAN3 = N3;
    if (taChart.chartType == ChartUIObject.SIMPLEMOVINGAVERAGE)
    taChart.chartData.calculateMovingAverage(N1,N2,N3);
    chartScreen.updateBaseScreen();
    chartScreen.repaint();
  }



  void btEMAApply_actionPerformed(ActionEvent e)
  {

    if ( !(FFormater.isFloat(tfEMA1.getText()) && FFormater.isFloat(tfEMA2.getText())  && FFormater.isFloat(tfEMA3.getText())) )
    {
      System.out.println("SMAAPPLy ERROR");
      this.updateSetting();
      return;
    }
    ChartUIObject taChart = chartScreen.getChart(TAChartName);
    if (taChart == null) return;
    int N1, N2, N3;
    N1 = Integer.parseInt(tfEMA1.getText());
    N2 = Integer.parseInt(tfEMA2.getText());
    N3 = Integer.parseInt(tfEMA3.getText());

// data vaildation.
    if (N1<=0 || N2 <=0 || N3<=0 || N1 > taChart.chartData.data.size() ||  N2 > taChart.chartData.data.size() ||  N3 > taChart.chartData.data.size() )
    {
      this.updateSetting();
      return;
    }
///////
//////////////////////////////////////////////////////////////////////////////////////////////////

    System.out.println("OK EMA");
    taChart.chartData.fTAconfig.EMA1 = N1;
    taChart.chartData.fTAconfig.EMA2 = N2;
    taChart.chartData.fTAconfig.EMA3 = N3;
    if (taChart.chartType == ChartUIObject.EXPONENTIALMOVINGAVERAGE)
    taChart.chartData.calculateExponentialMovingAverage(N1,N2,N3);
//  taChart.setVisible(true);
    chartScreen.updateBaseScreen();
    chartScreen.repaint();

  }

  void btBollingerApply_actionPerformed(ActionEvent e)
  {
    if ( !(FFormater.isNumerical(tfBB.getText()) && FFormater.isFloat(this.tfDevation.getText())))
    {
      this.updateSetting();
      return;
    }
    ChartUIObject taChart = chartScreen.getChart(TAChartName);
    if (taChart == null) return;
    int bb;
    float dd;
    bb = Integer.parseInt(tfBB.getText());
    dd = Float.valueOf(tfDevation.getText()).floatValue();

// data vaildation.
    if (bb<=0 || bb > taChart.chartData.data.size() || dd <=0 || dd >=100)
    {
      this.updateSetting();
      return;
    }
//////////////////////////////////////////////////////////////////////////////////////////////////


    taChart.chartData.fTAconfig.bbN = bb;
    taChart.chartData.fTAconfig.bbDevation = dd;
    if (taChart.chartType == ChartUIObject.BOLLINGERBAND)
    taChart.chartData.calculateBollingerBand(bb,dd);
    chartScreen.updateBaseScreen();
    chartScreen.repaint();
  }


  void updateSetting()
  {
    ChartUIObject currentChart  = chartScreen.getChart(TAChartName);
    if (currentChart == null)
    {
      System.out.println("NO chart found in setting");
      return;
    }

    this.tfSMA1.setText(String.valueOf(currentChart.chartData.fTAconfig.SMAN1));
    this.tfSMA2.setText(String.valueOf(currentChart.chartData.fTAconfig.SMAN2));
    this.tfSMA3.setText(String.valueOf(currentChart.chartData.fTAconfig.SMAN3));
    this.tfWMA1.setText(String.valueOf(currentChart.chartData.fTAconfig.WMAN1));
    this.tfWMA2.setText(String.valueOf(currentChart.chartData.fTAconfig.WMAN2));
    this.tfWMA3.setText(String.valueOf(currentChart.chartData.fTAconfig.WMAN3));
    this.tfEMA1.setText(String.valueOf(currentChart.chartData.fTAconfig.EMA1));
    this.tfEMA2.setText(String.valueOf(currentChart.chartData.fTAconfig.EMA2));
    this.tfEMA3.setText(String.valueOf(currentChart.chartData.fTAconfig.EMA3));
    this.tfBB.setText(String.valueOf(currentChart.chartData.fTAconfig.bbN));
    this.tfDevation.setText(String.valueOf(currentChart.chartData.fTAconfig.bbDevation));
  }

  void setChartScreen(ChartScreen cs)
  {
    chartScreen = cs;
  }


  public void setLanguage(int tlanguage)
  {
    language = tlanguage;
    this.btOK.setLabel(lbArray[0][language]);
    this.btSMAApply.setLabel(lbArray[1][language]);
    this.btWMAApply.setLabel(lbArray[1][language]);
    this.btEMAApply.setLabel(lbArray[1][language]);
    this.btBollingerApply.setLabel(lbArray[1][language]);
    this.btCancel.setLabel(lbArray[2][language]);
    this.btHelp.setLabel(lbArray[3][language]);

    this.lbSMA.setText(lbArray[4][language] + "(SMA)");
    this.lbWMA.setText(lbArray[5][language] + "(WMA)");
    this.lbEMA.setText(lbArray[6][language] + "(EMA)");
    this.lbBollinger.setText(lbArray[7][language]);
  }

  public void setTAChartName(String tachartName)
  {
    TAChartName = tachartName;
  }
  public void windowOpened(WindowEvent e)
  {
    System.out.println("WindowOpened");
  }

  public void windowClosing(WindowEvent e){
  this.dispose();
  }
  public void windowClosed(WindowEvent e){System.out.println("closed");}
  public void windowIconified(WindowEvent e){System.out.println("iconified");}
  public void windowDeiconified(WindowEvent e){System.out.println("deiconified");}
  public void windowActivated(WindowEvent e){System.out.println("activated");}
  public void windowDeactivated(WindowEvent e){System.out.println("deactivated");}



}