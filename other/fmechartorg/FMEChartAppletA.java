
//Title:       FME Chart Project for E-finet
//Version:     1.0
//Copyright:   Copyright (c)
//Author:
//Company:
//Description:
//package temp;
package fmechart;

import java.awt.*;
import java.awt.event.*;
import java.applet.*;
//import fmechart.*;

public class FMEChartAppletA extends Applet
{
  boolean isStandalone = false;
  Panel tempPanel = new Panel();
  FMEChartA fmeChartA = new FMEChartA();
  java.awt.CardLayout cardlayout = new CardLayout();
  BorderLayout borderLayout1 = new BorderLayout();
  BorderLayout borderLayout2 = new BorderLayout();

  //Get a parameter value
  public String getParameter(String key, String def)
  {
    return isStandalone ? System.getProperty(key, def) :
      (getParameter(key) != null ? getParameter(key) : def);
  }

  //Construct the applet
  public FMEChartAppletA()
  {
  }

  //Initialize the applet
  public void init()
  {
    try
    {
     // fmeChartA = new FMEChartA();
      jbInit();
      //load the button images


    }
    catch(Exception e)
    {
      e.printStackTrace();
    }
  }

  //Component initialization
  private void jbInit() throws Exception
  {
  // add the chart panel to the Applet.....
    this.setLayout(cardlayout);
    this.add("panelName",tempPanel);
    tempPanel.setLayout(borderLayout2);
    tempPanel.setBackground(Color.white);
    tempPanel.add(fmeChartA, BorderLayout.CENTER);
  }

  //Start the applet
  public void start()
  {

  // load image from URL
    fmeChartA.fbuttonBar.btNone.offImage = getImage(getDocumentBase(), "mouse.gif");
    fmeChartA.fbuttonBar.btWatch.offImage = getImage(getDocumentBase(), "watch.gif");
    fmeChartA.fbuttonBar.btZoomIn.offImage = getImage(getDocumentBase(), "zoomin.gif");
    fmeChartA.fbuttonBar.btZoomOut.offImage = getImage(getDocumentBase(),"zoomout.gif");
    fmeChartA.fbuttonBar.btMove.offImage = getImage(getDocumentBase(), "move.gif");
    fmeChartA.fbuttonBar.btInsertLine.offImage = getImage(getDocumentBase(),"insertline.gif");
    fmeChartA.fbuttonBar.btInsertPLine.offImage = getImage(getDocumentBase(),"parallelline.gif");
    fmeChartA.fbuttonBar.btGPartition.offImage = getImage(getDocumentBase(),"goldenpartition.gif");
    fmeChartA.fbuttonBar.btRemoveLine.offImage = getImage(getDocumentBase(),"removeline.gif");
    fmeChartA.fbuttonBar.btClear.offImage = getImage(getDocumentBase(),"clear.gif");
    fmeChartA.fbuttonBar.btCompare.offImage = getImage(getDocumentBase(),"compare.gif");
    fmeChartA.fbuttonBar.btSetting.offImage = getImage(getDocumentBase(),"setting.gif");
    fmeChartA.fTAMenu1.btSetting.offImage =  getImage(getDocumentBase(),"setting.gif");
    fmeChartA.fTypeBBar.btTypeA.offImage = getImage(getDocumentBase(),"TypeA.gif");
    fmeChartA.fTypeBBar.btTypeB.offImage = getImage(getDocumentBase(),"TypeB.gif");
    fmeChartA.fTypeBBar.btTypeC.offImage = getImage(getDocumentBase(),"TypeC.gif");
    fmeChartA.fTypeBBar.btTypeD.offImage = getImage(getDocumentBase(),"TypeD.gif");
    fmeChartA.btOpenClose.offImage = getImage(getDocumentBase(),"open.gif");
    fmeChartA.btPrinter.offImage = getImage(getDocumentBase(),"printer.gif");


 // Init the chart screen
    this.fmeChartA.chartScreen1.initScreen();
    this.fmeChartA.chartScreen2.initScreen();
    this.fmeChartA.chartScreen3.initScreen();



    // set the server address and enable the chartSource
//    fmeChartA.chartSource.setServerAddress("202.122.204.8");
//    fmeChartA.chartSource.setServerAddress("203.161.232.88");

    fmeChartA.chartSource.enable();

    // add a chart data
    FCommand fc = new FCommand(0001,FCommand.TYPE_DOWNLOAD_LEFT_CHART,fmeChartA.fmenuBar.chDuration.getSelectedIndex(),"LMain1", 500,1,false,fmeChartA.fmenuBar);
    fmeChartA.chartSource.addCommand(fc);
    fmeChartA.chartScreen1.setScreenState(ChartScreen.LOADING);
    fmeChartA.chartScreen2.setScreenState(ChartScreen.LOADING);
    fmeChartA.chartScreen3.setScreenState(ChartScreen.LOADING);
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////

// change the language by calling set language function
    //fmeChartA.setLanguage(FConfig.constEnglish);
    fmeChartA.setLanguage(FConfig.constChinese);

  }

  //Stop the applet
  public void stop()
  {
  }

  //Destroy the applet
  public void destroy()
  {
  }

  //Get Applet information
  public String getAppletInfo()
  {
    return "Applet Information";
  }

  //Get parameter info
  public String[][] getParameterInfo()
  {
    return null;
  }
}