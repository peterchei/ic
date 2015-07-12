
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



public class FMEChartA extends Panel
{

  public Panel panelToolbar = new Panel();
  public ChartScreen chartScreen1 = new ChartScreen(20,20,42,25); //the first chartscreen
  public ChartScreen chartScreen2 = new ChartScreen(20,5,42,25); //the second chartscreen
  public ChartScreen chartScreen3 = new ChartScreen(20,5,42,25); //the third chartscreen
  public FButtonBar fbuttonBar = new FButtonBar();   //the button bar
  public FMenuBar fmenuBar = new FMenuBar();
  public FTypeBBar fTypeBBar = new FTypeBBar();         //the menu bar
  public ChartSource chartSource = new ChartSource();
  public FCompareBar fCompareBar = new FCompareBar();
  public FTAMenu fTAMenu1 = new FTAMenu();
  public FImageButton btOpenClose = new FImageButton();
  FMEChartWindow chartWindow = new FMEChartWindow();  // the popup window.

  // the printer object to print the graphic.....
  BasicPrint basicPrinter;


  private int language = FConfig.constEnglish;
  public FImageButton btPrinter = new FImageButton();

  public FMEChartA()
  {
    try
    {
      jbInit();
      //set the reference of chartscreen

      fbuttonBar.setChartScreen(chartScreen1,chartScreen2,chartScreen3);
      fbuttonBar.setMenus(fCompareBar, fmenuBar);
      fTypeBBar.setChartScreen(chartScreen1,chartScreen2,chartScreen3);
      //add ScreenActionListen to the buttonbar;
      chartScreen1.addScreenActionListen(fbuttonBar);
      chartScreen2.addScreenActionListen(fbuttonBar);
      chartScreen3.addScreenActionListen(fbuttonBar);
      fmenuBar.setChartScreen(chartScreen1, chartScreen2, chartScreen3);
      fmenuBar.setChartSource(chartSource);
      fmenuBar.setTAMenu(fTAMenu1);
      fCompareBar.setMenus(fbuttonBar,fmenuBar);
      fCompareBar.setChartScreen(chartScreen1,chartScreen2,chartScreen3);
      fCompareBar.setChartSource(chartSource);
      fTAMenu1.setChartScreen(chartScreen2);
    }
    catch(Exception e)
    {
      e.printStackTrace();
    }
  }

  public void resizeChartScreen(int x,int y, int w, int h)
  {
    if (this.btOpenClose!=null)
    {
      btOpenClose.setBounds(w-23,0,22,22);
    }
    if (this.btPrinter!=null)
    {
      if (btOpenClose.isVisible())
      {
        btPrinter.setBounds(w-44,0,22,22);
      }
      else
      {
        btPrinter.setBounds(w-23,0,22,22);
      }
    }

    int xx = h -52-15;
    xx = xx /3;
    panelToolbar.setBounds(new Rectangle(0, 0, w, 52));
    chartScreen1.setBounds(new Rectangle(1, 52, w, xx+15));
    chartScreen2.setBounds(new Rectangle(1, 52+15+xx,w, xx));
    chartScreen3.setBounds(new Rectangle(1, 52+15+2*xx,w, xx));
    fTypeBBar.initType(52,xx+15,xx,xx);
    chartScreen1.faction.lineRecords.removeAllElements();
    chartScreen2.faction.lineRecords.removeAllElements();
    chartScreen3.faction.lineRecords.removeAllElements();
    chartScreen1.faction.zoomRecords.removeAllElements();
    chartScreen2.faction.zoomRecords.removeAllElements();
    chartScreen3.faction.zoomRecords.removeAllElements();
    chartScreen1.undoZoom();
    chartScreen2.undoZoom();
    chartScreen3.undoZoom();
    chartScreen1.setVisible(true);
    chartScreen2.setVisible(true);
    chartScreen3.setVisible(true);

  }

  public void setBounds(int x,int y, int w, int h)
  {

    super.setBounds(x,y,w,h);
    resizeChartScreen(x,y,w,h);

  }
  private void jbInit() throws Exception
  {

    this.setLayout(null);
    panelToolbar.setBackground(FConfig.ToolBarColor);
    panelToolbar.setLayout(null);


    chartScreen1.setBounds(new Rectangle(1, 53, 500, 178));
    chartScreen2.setBounds(new Rectangle(1, 231,500, 125));
    chartScreen3.setBounds(new Rectangle(1, 356, 500, 125));


    int x = 400 -52-15;
    x = x /3;
    panelToolbar.setBounds(new Rectangle(0, 0, 1024, 52));
    chartScreen1.setBounds(new Rectangle(1, 53, 600,x+15));
    chartScreen2.setBounds(new Rectangle(1, 53+15+x,600,x));
    chartScreen3.setBounds(new Rectangle(1, 53+15+2*x,600,x));

    fbuttonBar.setBackground(FConfig.ToolBarColor);
    fbuttonBar.setBounds(new Rectangle(1, 26, 286, 26));
    fbuttonBar.setLayout(null);
    fmenuBar.setBackground(FConfig.ToolBarColor);
    fmenuBar.setBounds(new Rectangle(1, 0, 436, 26));

    fTypeBBar.setBackground(FConfig.ToolBarColor);
    fTypeBBar.setBounds(new Rectangle(398, 26, 106, 26));
    fCompareBar.setBounds(new Rectangle(1, 0, 436, 26));
    fCompareBar.setVisible(false);
    fTAMenu1.setBackground(FConfig.ToolBarColor);
    fTAMenu1.setBounds(new Rectangle(285, 27, 114, 24));
    btOpenClose.setBounds(new Rectangle(571, 0, 22, 22));
    btOpenClose.setLabel("fImageButton1");
    btOpenClose.addActionListener(new java.awt.event.ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        btOpenClose_actionPerformed(e);
      }
    });
    btPrinter.setBounds(new Rectangle(548, 0, 23, 22));
    btPrinter.setLabel("fImageButton1");
    btPrinter.addActionListener(new java.awt.event.ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        btPrinter_actionPerformed(e);
      }
    });
    this.add(chartScreen1, null);
    this.add(panelToolbar, null);
    panelToolbar.add(fmenuBar, null);
    panelToolbar.add(fbuttonBar, null);
    panelToolbar.add(fCompareBar, null);
    panelToolbar.add(btOpenClose, null);
    panelToolbar.add(fTAMenu1, null);
    panelToolbar.add(fTypeBBar, null);
    panelToolbar.add(btPrinter, null);

    this.add(chartScreen2, null);
    this.add(chartScreen3, null);

  }

  void btOpenClose_actionPerformed(ActionEvent e)
  {
/*
   if (language == FConfig.constChinese )
   {
    setLanguage(FConfig.constEnglish);
   }
   else
   {
    setLanguage(FConfig.constChinese);
   }
  */
    btOpenClose.setVisible(false);
    chartWindow.setChartPanel(this);
    chartWindow.setBounds(0,0,800,600);
    chartWindow.setResizable(true);
    chartWindow.setTitle("FME Chart Window");
    chartWindow.show();

  }


  //change the language
  public void setLanguage(int tlanguage)
  {
    language = tlanguage;
    chartScreen1.setLanguage(language);
    chartScreen2.setLanguage(language);
    chartScreen3.setLanguage(language);
    fmenuBar.setLanguage(language);
    fCompareBar.setLanguage(language);
    fTAMenu1.setLanguage(language);
    fbuttonBar.setLanguage(language);
  }

  void btPrinter_actionPerformed(ActionEvent e)
  {
    try {
    //create printer object and connect to the printer
    basicPrinter = new BasicPrint();
    if (basicPrinter.initPrinter(chartScreen1,chartScreen2,chartScreen3)==true)
    {
      System.out.println("OK");
      //start print
      basicPrinter.startPrint();
    }
    }
    catch (Exception ex)
    {
      ex.printStackTrace();

      System.out.println("Can not access the printers");
    }
  }


}