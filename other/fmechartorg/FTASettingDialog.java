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

public class FTASettingDialog extends Frame implements WindowListener
{
  Label label1 = new Label();
  TextField tfRSI = new TextField();
  Label lbRSI = new Label();
  Label lbSTC = new Label();
  Label label6 = new Label();
  TextField tfSTCK = new TextField();
  Label label7 = new Label();
  TextField tfSTCD = new TextField();
  Button btRSIApply = new Button();
  Button btSTCApply = new Button();
  Label lbMACD = new Label();
  Label label10 = new Label();
  TextField tfELMA = new TextField();
  Label label11 = new Label();
  TextField tfESMA = new TextField();
  TextField tfEAMA = new TextField();
  Button btMACDApply = new Button();
  Label label12 = new Label();
  Label lbWilliam = new Label();
  Label label14 = new Label();
  TextField tfWR = new TextField();
  Button btWilliamApply = new Button();
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
                          ,{"Relative Strength Index","\u76f8\u5c0d\ufffd\u5f31\u6307\u6578"}       //4
                          ,{"Stochastics","\u96a8\u6a5f\u6307\u6578"}       //5
                          ,{"Moving Average Convergence Divergence","\u79fb\u52d5\u5e73\u5747\u7dda\u532f\u805a\u80cc\u99b3\u6307\u6a19"}     //6
                          ,{"William %R","\u5a01\u5ec9\u6307\u6a19"}  //7

                        };


  public FTASettingDialog()
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

  public void setLanguage(int tlanguage)
  {
    language = tlanguage;
    this.btOK.setLabel(lbArray[0][language]);
    this.btMACDApply.setLabel(lbArray[1][language]);
    this.btRSIApply.setLabel(lbArray[1][language]);
    this.btWilliamApply.setLabel(lbArray[1][language]);
    this.btSTCApply.setLabel(lbArray[1][language]);
    this.btCancel.setLabel(lbArray[2][language]);
    this.btHelp.setLabel(lbArray[3][language]);

    this.lbRSI.setText(lbArray[4][language] + "(RSI)");
    this.lbSTC.setText(lbArray[5][language] + "(STC)");
    this.lbMACD.setText(lbArray[6][language] + "(MACD)");
    this.lbWilliam.setText(lbArray[7][language]);
  }
  private void jbInit() throws Exception
  {
    label1.setBounds(new Rectangle(41, 64, 37, 25));
    label1.setAlignment(2);
    label1.setText("MA1");
    this.setLayout(null);
    lbRSI.setBounds(new Rectangle(41, 38, 171, 25));
    lbRSI.setText("Relative Strength Index, RSI");
    lbSTC.setBounds(new Rectangle(41, 101, 201, 25));
    lbSTC.setText("Stochastic, STC");
    label6.setBounds(new Rectangle(41, 127, 37, 25));
    label6.setAlignment(2);
    label6.setText("%K");
    tfRSI.setBackground(Color.white);
    tfRSI.setBounds(new Rectangle(78, 63, 38, 27));
    tfRSI.setText("XX");
    tfSTCK.setBackground(Color.white);
    tfSTCK.setBounds(new Rectangle(78, 126, 38, 27));
    tfSTCK.setText("XX");
    label7.setBounds(new Rectangle(124, 127, 29, 25));
    label7.setAlignment(2);
    label7.setText("%D");
    tfSTCD.setBackground(Color.white);
    tfSTCD.setBounds(new Rectangle(153, 126, 38, 27));
    tfSTCD.setText("XX");
    btRSIApply.setBackground(Color.orange);
    btRSIApply.setBounds(new Rectangle(273, 64, 43, 25));
    btRSIApply.setLabel("Apply");
    btRSIApply.addActionListener(new java.awt.event.ActionListener()
    {

      public void actionPerformed(ActionEvent e)
      {
        btRSIApply_actionPerformed(e);
      }
    });
    btRSIApply.addActionListener(new java.awt.event.ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        btRSIApply_actionPerformed(e);
      }
    });
    btSTCApply.setBackground(Color.orange);
    btSTCApply.setBounds(new Rectangle(273, 127, 43, 25));
    btSTCApply.setLabel("Apply");
    btSTCApply.addActionListener(new java.awt.event.ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        btSTCApply_actionPerformed(e);
      }
    });
    lbMACD.setBounds(new Rectangle(41, 164, 286, 25));
    lbMACD.setText("Moving Average Convergence Divergence, MACD");
    label10.setBounds(new Rectangle(41, 190, 37, 25));
    label10.setAlignment(2);
    label10.setText("MA1");
    tfELMA.setBackground(Color.white);
    tfELMA.setBounds(new Rectangle(78, 189, 38, 27));
    tfELMA.setText("XX");
    label11.setBounds(new Rectangle(124, 190, 29, 25));
    label11.setAlignment(2);
    label11.setText("MA2");
    tfESMA.setBackground(Color.white);
    tfESMA.setBounds(new Rectangle(153, 189, 38, 27));
    tfESMA.setText("XX");
    tfEAMA.setBackground(Color.white);
    tfEAMA.setBounds(new Rectangle(232, 189, 38, 27));
    tfEAMA.setText("XX");
    btMACDApply.setBackground(Color.orange);
    btMACDApply.setBounds(new Rectangle(273, 190, 43, 25));
    btMACDApply.setLabel("Apply");
    btMACDApply.addActionListener(new java.awt.event.ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        btMACDApply_actionPerformed(e);
      }
    });
    label12.setBounds(new Rectangle(203, 190, 27, 25));
    label12.setAlignment(2);
    label12.setText("MA3");
    this.setBackground(FConfig.DialogColor);
    lbWilliam.setBounds(new Rectangle(41, 227, 323, 25));
    lbWilliam.setText("William\'s %R");
    label14.setBounds(new Rectangle(41, 253, 37, 25));
    label14.setAlignment(2);
    label14.setText("%R");
    tfWR.setBackground(Color.white);
    tfWR.setBounds(new Rectangle(78, 252, 38, 27));
    tfWR.setText("XX");
    btWilliamApply.setBackground(Color.orange);
    btWilliamApply.setBounds(new Rectangle(273, 253, 43, 25));
    btWilliamApply.setLabel("Apply");
    btWilliamApply.addActionListener(new java.awt.event.ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        btWilliamApply_actionPerformed(e);
      }
    });
    btCancel.setBackground(Color.orange);
    btCancel.setBounds(new Rectangle(160, 291, 50, 25));
    btCancel.setLabel("Cancel");
    btCancel.addActionListener(new java.awt.event.ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        btCancel_actionPerformed(e);
      }
    });
    btOK.setBackground(Color.orange);
    btOK.setBounds(new Rectangle(55, 291, 50, 25));
    btOK.setLabel("OK");
    btOK.addActionListener(new java.awt.event.ActionListener()
    {

      public void actionPerformed(ActionEvent e)
      {
        btOK_actionPerformed(e);
      }
    });
    btOK.addActionListener(new java.awt.event.ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        btOK_actionPerformed(e);
      }
    });
    btHelp.setBackground(Color.orange);
    btHelp.setBounds(new Rectangle(270, 291, 50, 25));
    btHelp.setLabel("Help");
    btHelp.addActionListener(new java.awt.event.ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        btHelp_actionPerformed(e);
      }
    });
    this.add(lbWilliam, null);
    this.add(label14, null);
    this.add(tfWR, null);
    this.add(tfESMA, null);
    this.add(tfSTCD, null);
    this.add(tfEAMA, null);
    this.add(btSTCApply, null);
    this.add(btMACDApply, null);
    this.add(btRSIApply, null);
    this.add(label1, null);
    this.add(tfRSI, null);
    this.add(lbRSI, null);
    this.add(lbSTC, null);
    this.add(label6, null);
    this.add(lbMACD, null);
    this.add(label10, null);
    this.add(tfSTCK, null);
    this.add(tfELMA, null);
    this.add(label11, null);
    this.add(label7, null);
    this.add(label12, null);
    this.add(btCancel, null);
    this.add(btOK, null);
    this.add(btHelp, null);
    this.add(btWilliamApply, null);
  }

  void btOK_actionPerformed(ActionEvent e)
  {
    this.btWilliamApply_actionPerformed(null);
    this.btMACDApply_actionPerformed(null);
    this.btRSIApply_actionPerformed(null);
    this.btSTCApply_actionPerformed(null);
    this.dispose();
  }

  void btCancel_actionPerformed(ActionEvent e)
  {

    this.dispose();
  }

  void btHelp_actionPerformed(ActionEvent e)
  {

  }

  void btRSIApply_actionPerformed(ActionEvent e)
  {
    if (!FFormater.isNumerical(tfRSI.getText()))
    {
      updateSetting();
      return;
    }
    ChartUIObject taChart = chartScreen.getChart(TAChartName);
    if (taChart == null) return;

    int RSI = Integer.parseInt(tfRSI.getText());

    taChart.chartData.fTAconfig.RSIPeriod = RSI;
    if (taChart.chartType == ChartUIObject.RSI)
    {
      taChart.chartData.calculateRSI(taChart.chartData.fTAconfig.RSIPeriod);
      chartScreen.updateBaseScreen();
      chartScreen.repaint();
    }


  }

  void btMACDApply_actionPerformed(ActionEvent e)
  {
    if (!FFormater.isNumerical(tfEAMA.getText()) || !FFormater.isNumerical(this.tfESMA.getText() ) || !FFormater.isNumerical(this.tfELMA.getText() ))
    {
      updateSetting();
      return;
    }
    ChartUIObject taChart = chartScreen.getChart(TAChartName);
    if (taChart == null) return;

    int LMA = Integer.parseInt(this.tfELMA.getText());
    int SMA = Integer.parseInt(this.tfESMA.getText());
    int AMA = Integer.parseInt(this.tfEAMA.getText());

    taChart.chartData.fTAconfig.MACDAEMA = AMA;
    taChart.chartData.fTAconfig.MACDLEMA = LMA;
    taChart.chartData.fTAconfig.MACDSEMA = SMA;

    if (taChart.chartType == ChartUIObject.MACD )
    {
      taChart.chartData.calculateMACD(LMA,SMA,AMA);
      chartScreen.updateBaseScreen();
      chartScreen.repaint();
    }



  }

  void btSTCApply_actionPerformed(ActionEvent e)
  {
    if (!FFormater.isNumerical(tfSTCK.getText()) || !FFormater.isNumerical(this.tfSTCD.getText() ) )
    {
      updateSetting();
      return;
    }
    ChartUIObject taChart = chartScreen.getChart(TAChartName);
    if (taChart == null) return;

    int K = Integer.parseInt(this.tfSTCK.getText());
    int D = Integer.parseInt(this.tfSTCD.getText());

    taChart.chartData.fTAconfig.STCDPeriod  = D;
    taChart.chartData.fTAconfig.STCKPeriod  = K;
    if (taChart.chartType == ChartUIObject.STC)
    {
      taChart.chartData.calculateSTC(K,D);
      chartScreen.updateBaseScreen();
      chartScreen.repaint();
    }
  }

  void btWilliamApply_actionPerformed(ActionEvent e)
  {
    if (!FFormater.isNumerical(tfWR.getText()))
    {
      updateSetting();
      return;
    }
    ChartUIObject taChart = chartScreen.getChart(TAChartName);
    if (taChart == null) return;

    int R = Integer.parseInt(tfWR.getText());

    taChart.chartData.fTAconfig.WilliamPeriod= R;
    if (taChart.chartType == ChartUIObject.WilliamR)
    {
      taChart.chartData.calculateWilliamR(R);
      chartScreen.updateBaseScreen();
      chartScreen.repaint();
    }
  }


  void updateSetting()
  {
    ChartUIObject currentChart  = chartScreen.getChart(TAChartName);
    if (currentChart == null)
    {
      System.out.println("NO chart found in setting");
      return;
    }

    this.tfRSI.setText(String.valueOf(currentChart.chartData.fTAconfig.RSIPeriod));
    this.tfSTCK.setText(String.valueOf(currentChart.chartData.fTAconfig.STCKPeriod));
    this.tfSTCD.setText(String.valueOf(currentChart.chartData.fTAconfig.STCDPeriod));
    this.tfWR.setText(String.valueOf(currentChart.chartData.fTAconfig.WilliamPeriod));
    this.tfEAMA.setText(String.valueOf(currentChart.chartData.fTAconfig.MACDAEMA));
    this.tfELMA.setText(String.valueOf(currentChart.chartData.fTAconfig.MACDLEMA));
    this.tfESMA.setText(String.valueOf(currentChart.chartData.fTAconfig.MACDSEMA));
  }

  void setChartScreen(ChartScreen cs)
  {
    chartScreen = cs;
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





  public void windowClosed(WindowEvent e){}
  public void windowIconified(WindowEvent e){}
  public void windowDeiconified(WindowEvent e){}
  public void windowActivated(WindowEvent e){}
  public void windowDeactivated(WindowEvent e){}


}