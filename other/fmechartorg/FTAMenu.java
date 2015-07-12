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

public class FTAMenu extends FBar
{

  static final String lbArray[][] = {  //{"None","\u7121"}     //0
                                       {"RSI","\u76f8\u5c0d\ufffd\u5f31\u6307\u6578"}       //0
                                      ,{"STC","\u96a8\u6a5f\u6307\u6578"}       //1
                                      ,{"OBV","\u6210\u4ea4\u91cf\u5e73 \u6307\u6578"}       //2
                                      ,{"MACD","\u79fb\u52d5\u5e73\u5747\u7dda\u532f\u805a\u80cc\u99b3\u6307\u6a19"}     //3
                                      ,{"William %R","\u5a01\u5ec9\u6307\u6a19"}  //4
                                      };



  Choice chChartType = new Choice();
  public FImageButton btSetting = new FImageButton();

  private int language = FConfig.constEnglish;
  private ChartScreen chartScreen = null;
  private FTASettingDialog TASetting = new FTASettingDialog();

  public void setChartScreen(ChartScreen cs)
  {
    chartScreen = cs;
  }

  public FTAMenu()
  {
    try
    {
      jbInit();
      this.chChartType.addItemListener(new ItemListener()
      {
        public void itemStateChanged(ItemEvent e)
        {
           chChartType_itemStateChanged(e);
        }
      });
      updateMenu();
    }
    catch(Exception e)
    {
      e.printStackTrace();
    }
  }
  private void jbInit() throws Exception
  {
    this.setLayout(null);
    chChartType.setFont(new java.awt.Font("Dialog", 0, 10));
    chChartType.setBounds(new Rectangle(8, 1, 82, 20));
    btSetting.setBounds(new Rectangle(91, 1, 22, 22));
    btSetting.setLabel("fImageButton1");
    btSetting.addActionListener(new java.awt.event.ActionListener()
    {

      public void actionPerformed(ActionEvent e)
      {
        btSetting_actionPerformed(e);
      }
    });
    btSetting.addActionListener(new java.awt.event.ActionListener()
    {

      public void actionPerformed(ActionEvent e)
      {
        btSetting_actionPerformed(e);
      }
    });
    this.add(chChartType, null);
    this.add(btSetting, null);
  }

  public void updateMenu()
  {
    setLanguage(language);
  }
  //change language
  public void setLanguage(int tlanguage)
  {
    language = tlanguage;

    int selectedIndex;

    selectedIndex = chChartType.getSelectedIndex();
    //   System.out.println("Select " + select);
    chChartType.removeAll();
    for (int i=0; i < lbArray.length ; i ++)
    chChartType.addItem(lbArray[i][language]);
/*
    chChartType.addItem(lbArray[1][language]);
    chChartType.addItem(lbArray[2][language]);
    chChartType.addItem(lbArray[3][language]);
    chChartType.addItem(lbArray[4][language]);
//    chChartType.addItem(lbArray[5][language]);
*/
    if (selectedIndex >= 0) chChartType.select(selectedIndex);
    TASetting.setLanguage(language);

  }

  public void chChartType_itemStateChanged(ItemEvent e)
  {

    if (chartScreen == null) return;
    ChartUIObject lchart = chartScreen.getLeftChart(); // The TA chart....
    if (lchart == null) return;

    String cType = chChartType.getSelectedItem(); // get the ITEM we select.
   System.out.println("item selected : " + cType);
/*
      static final String lbArray[][] = {  {"None","None"}
                                      ,{"RSI","RSI"}
                                      ,{"STC","STC"}
                                      ,{"OBV","OBV"}
                                      ,{"MACD","MACD"}
                                      ,{"William %R","William %R"}
                                      };
*/
    //below : process the ITEM

    if (cType == lbArray[0][0] || cType == lbArray [0][1] )
    {
      //System.out.println("RSI Selected");
      lchart.chartType = ChartUIObject.RSI;
      lchart.chartData.calculateRSI(lchart.chartData.fTAconfig.RSIPeriod);
    }
    else if (cType == lbArray[1][0] || cType == lbArray [1][1] )
    {
      //System.out.println("STC selected");
      lchart.chartType = ChartUIObject.STC;
      lchart.chartData.calculateSTC(lchart.chartData.fTAconfig.STCKPeriod,lchart.chartData.fTAconfig.STCDPeriod);
    }
    else if (cType == lbArray[2][0] || cType == lbArray [2][1] )
    {
      lchart.chartType = ChartUIObject.OBV;
     // lchart.chartData.calculateOBV();
    }
    else if (cType == lbArray[3][0] || cType == lbArray [3][1] )
    {
      lchart.chartType = ChartUIObject.MACD;
      lchart.chartData.calculateMACD(lchart.chartData.fTAconfig.MACDLEMA,lchart.chartData.fTAconfig.MACDSEMA,lchart.chartData.fTAconfig.MACDAEMA);
    }
    else if (cType == lbArray[4][0] || cType == lbArray [4][1] )
    {
      lchart.chartType = ChartUIObject.WilliamR;
      lchart.chartData.calculateWilliamR(lchart.chartData.fTAconfig.WilliamPeriod);
    }
    chartScreen.updateBaseScreen();
    chartScreen.repaint();

  }

  void btSetting_actionPerformed(ActionEvent e)
  {
//      System.out.println("click");
    TASetting.setChartScreen(chartScreen);
    TASetting.setTAChartName("TA1Chart");  // tell the setting window that which chart he can control
    TASetting.updateSetting();
    TASetting.show();
    TASetting.setTitle("FME TA Chart Setting Window");
    TASetting.setResizable(false);
    TASetting.setBounds(0,0,400,400);
  }
}