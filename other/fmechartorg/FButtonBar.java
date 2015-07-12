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

public class FButtonBar extends FBar implements ScreenActionListener
{
  public FImageButton btNone     = new FImageButton();
  public FImageButton btWatch    = new FImageButton();
  public FImageButton btZoomIn   = new FImageButton();
  public FImageButton btZoomOut  = new FImageButton();
  public FImageButton btMove     = new FImageButton();
  public FImageButton btInsertLine   = new FImageButton();
  public FImageButton btInsertPLine  = new FImageButton();
  public FImageButton btGPartition   = new FImageButton();
  public FImageButton btRemoveLine   = new FImageButton();
  public FImageButton btClear        = new FImageButton();
  public FImageButton btCompare      = new FImageButton();
  public FImageButton btSetting      = new FImageButton();

  // the reference of bars
  private FMenuBar fMenuBar = null;
  private FCompareBar fCompareBar = null;


  //the reference of chartscreens which this buttonbar can control it.
  private ChartScreen chartScreen1 = null;
  private ChartScreen chartScreen2 = null;
  private ChartScreen chartScreen3 = null;
  private int language = FConfig.constEnglish;
  private FSettingDialog settingWindow1 = new FSettingDialog();

  public void setMenus(FCompareBar fc, FMenuBar fm)
  {
    fCompareBar = fc;
    fMenuBar = fm;
  }
  //set the reference of the chartScrenn such that the button bar can control it.
  public void setChartScreen(ChartScreen cs1, ChartScreen cs2, ChartScreen cs3)
  {
    chartScreen1 = cs1;
    chartScreen2 = cs2;
    chartScreen3 = cs3;
  }

  public FButtonBar()
  {
    try
    {
      jbInit();
    }
    catch(Exception e)
    {
      e.printStackTrace();
    }
  }


  public void setLanguage(int tlanguage)
  {
    language = tlanguage;
    settingWindow1.setLanguage(language);
  }


  private void jbInit() throws Exception
  {

    btNone.setBackground(Color.gray);
    btNone.setBounds(new Rectangle(9, 2, 22, 22));
    btNone.addActionListener(new java.awt.event.ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        btNone_actionPerformed(e);
      }
    });
    btNone.setLabel("FImageButton1");
    this.setLayout(null);
    btWatch.setBackground(Color.gray);
    btWatch.setBounds(new Rectangle(32, 2, 22, 22));
    btWatch.addActionListener(new java.awt.event.ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        btWatch_actionPerformed(e);
      }
    });
    btWatch.setLabel("FImageButton2");
    btZoomIn.setBackground(Color.gray);
    btZoomIn.setBounds(new Rectangle(55, 2, 22, 22));
    btZoomIn.addActionListener(new java.awt.event.ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        btZoomIn_actionPerformed(e);
      }
    });
    btZoomIn.setLabel("FImageButton3");
    btZoomOut.setBackground(Color.gray);
    btZoomOut.setBounds(new Rectangle(78, 2, 22, 22));
    btZoomOut.addActionListener(new java.awt.event.ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        btZoomOut_actionPerformed(e);
      }
    });
    btZoomOut.setLabel("FImageButton4");
    btMove.setBackground(Color.gray);
    btMove.setBounds(new Rectangle(101, 2, 22, 22));
    btMove.addActionListener(new java.awt.event.ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        btMove_actionPerformed(e);
      }
    });
    btMove.setLabel("FImageButton5");
    btInsertLine.setLabel("FImageButton5");
    btInsertLine.setBackground(Color.gray);
    btInsertLine.setBounds(new Rectangle(124, 2, 22, 22));
    btInsertLine.addActionListener(new java.awt.event.ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        btInsertLine_actionPerformed(e);
      }
    });
    btInsertPLine.setLabel("FImageButton5");
    btInsertPLine.setBackground(Color.gray);
    btInsertPLine.setBounds(new Rectangle(147, 2, 22, 22));
    btInsertPLine.addActionListener(new java.awt.event.ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        btInsertPLine_actionPerformed(e);
      }
    });
    btGPartition.setLabel("FImageButton5");
    btGPartition.setBackground(Color.gray);
    btGPartition.setBounds(new Rectangle(170, 2, 22, 22));
    btGPartition.addActionListener(new java.awt.event.ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        btGPartition_actionPerformed(e);
      }
    });
    btRemoveLine.setLabel("FImageButton5");
    btRemoveLine.setBackground(Color.gray);
    btRemoveLine.setBounds(new Rectangle(193, 2, 22, 22));
    btRemoveLine.addActionListener(new java.awt.event.ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        btRemoveLine_actionPerformed(e);
      }
    });

    btClear.setLabel("FImageButton5");
    btClear.setBackground(Color.gray);
    btClear.setBounds(new Rectangle(216, 2, 22, 22));
    btClear.addActionListener(new java.awt.event.ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        btClear_actionPerformed(e);
      }
    });

    btCompare.setLabel("Compare Button");
    btCompare.setBackground(Color.gray);
    btCompare.setBounds(new Rectangle(239, 2, 22, 22));
    btCompare.addActionListener(new java.awt.event.ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        btCompare_actionPerformed(e);
      }
    });


    btSetting.setLabel("FImageButton5");
    btSetting.setBackground(Color.gray);
    btSetting.setBounds(new Rectangle(263, 2, 22, 22));
    btSetting.addActionListener(new java.awt.event.ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        btSetting_actionPerformed(e);
      }
    });

    this.setBackground(SystemColor.control);
    this.add(btNone, null);
    this.add(btWatch, null);
    this.add(btZoomIn, null);
    this.add(btZoomOut, null);
    this.add(btMove, null);
    this.add(btInsertLine, null);
    this.add(btInsertPLine, null);
    this.add(btGPartition, null);
    this.add(btRemoveLine, null);
    this.add(btClear,null);
    this.add(btCompare, null);
    this.add(btSetting, null);
  }



  void btNone_actionPerformed(ActionEvent e)
  {
    if (chartScreen1 != null){
      chartScreen1.faction.currentActionType = FAction.NONEACTION;
    }

    if (chartScreen2 != null){
      chartScreen2.faction.currentActionType = FAction.NONEACTION;
    }

    if (chartScreen3 != null){
      chartScreen3.faction.currentActionType = FAction.NONEACTION;
    }
  }

  void btWatch_actionPerformed(ActionEvent e)
  {
    if (chartScreen1 != null){
      chartScreen1.faction.currentActionType = FAction.WATCH;
    }

    if (chartScreen2 != null){
      chartScreen2.faction.currentActionType = FAction.WATCH;
    }

    if (chartScreen3 != null){
      chartScreen3.faction.currentActionType = FAction.WATCH;
    }
  }

  void btZoomIn_actionPerformed(ActionEvent e)
  {
    if (chartScreen1 != null){
      chartScreen1.faction.currentActionType = FAction.ZOOMIN;
    }

    if (chartScreen2 != null){
      chartScreen2.faction.currentActionType = FAction.NONEACTION;
    }

    if (chartScreen3 != null){
      chartScreen3.faction.currentActionType = FAction.NONEACTION;
    }
  }

  void btZoomOut_actionPerformed(ActionEvent e)
  {
    if (chartScreen1 != null){
      chartScreen1.undoZoom();
      int startIndex = chartScreen1.getStartDisplayIndex();
      int endIndex = chartScreen1.getEndDisplayIndex();
      chartScreen2.zoom(startIndex, endIndex);
      chartScreen3.zoom(startIndex, endIndex);

    }
/*
    if (chartScreen2 != null){
      chartScreen2.undoZoom();
    }

    if (chartScreen3 != null){
      chartScreen3.undoZoom();
    }
*/
  }

  void btMove_actionPerformed(ActionEvent e)
  {
    if (chartScreen1 != null){
      chartScreen1.faction.currentActionType = FAction.MOVECHART;
    }

    if (chartScreen2 != null){
      chartScreen2.faction.currentActionType = FAction.MOVECHART;
    }

    if (chartScreen3 != null){
      chartScreen3.faction.currentActionType = FAction.MOVECHART;
    }
  }

  void btInsertLine_actionPerformed(ActionEvent e)
  {
    if (chartScreen1 != null){
      chartScreen1.faction.currentActionType = FAction.INSERTLINE;
    }

    if (chartScreen2 != null){
      chartScreen2.faction.currentActionType = FAction.INSERTLINE;
    }

    if (chartScreen3 != null){
      chartScreen3.faction.currentActionType = FAction.INSERTLINE;
    }
  }

  void btInsertPLine_actionPerformed(ActionEvent e)
  {
    if (chartScreen1 != null){
      chartScreen1.faction.currentActionType = FAction.INSERTPARALLELLINE;
    }

    if (chartScreen2 != null){
      chartScreen2.faction.currentActionType = FAction.INSERTPARALLELLINE;
    }

    if (chartScreen3 != null){
      chartScreen3.faction.currentActionType = FAction.INSERTPARALLELLINE;
    }
  }

  void btGPartition_actionPerformed(ActionEvent e)
  {
    if (chartScreen1 != null){
      chartScreen1.faction.currentActionType = FAction.GOLDENPARTITION;
    }

    if (chartScreen2 != null){
      chartScreen2.faction.currentActionType = FAction.GOLDENPARTITION;
    }

    if (chartScreen3 != null){
      chartScreen3.faction.currentActionType = FAction.GOLDENPARTITION;
    }
  }

  void btRemoveLine_actionPerformed(ActionEvent e)
  {

    if (chartScreen1 != null){
      chartScreen1.undoInsertLine();
    }

    if (chartScreen2 != null){
      chartScreen2.undoInsertLine();
    }

    if (chartScreen3 != null){
      chartScreen3.undoInsertLine();
    }

  }


  void btCompare_actionPerformed(ActionEvent e)
  {
    if (fCompareBar !=null && fMenuBar != null)
    {
      ChartUIObject taChart = chartScreen1.getChart("TA1Chart");
      if (taChart!=null) taChart.setVisible(false);              // set TA chart to be invisible when in comparing state.
      fCompareBar.setVisible(true);
      fMenuBar.setVisible(false);
      if (chartScreen1!=null)
      {
        ChartUIObject lcchart = chartScreen1.getLeftChart();
        if (lcchart != null)
        {
          lcchart.chartType = ChartUIObject.PERCENTAGE;
          chartScreen1.faction.lineRecords.removeAllElements();
          chartScreen1.faction.goldenPartitionLine = null;
          chartScreen1.updateBaseScreen();
          chartScreen1.repaint();
        }

      }
    }
  }

  void btSetting_actionPerformed(ActionEvent e)
  {

  //  settingWindow1 = new FSettingDialog();
    settingWindow1.setChartScreen(chartScreen1); // tell the setting window that which chartScreen he are using.
    settingWindow1.setTAChartName("TA1Chart");  // tell the setting window that which chart he can control
    settingWindow1.updateSetting();
    settingWindow1.show();
    settingWindow1.setTitle("FME Main Chart Setting Window");
    settingWindow1.setResizable(false);
    settingWindow1.setBounds(0,0,400,400);
//    settingWindow1.pack();


  }

   public void OnZoomCompleted(ChartScreen actionChartScreen, int startIndex, int endIndex)
   {
      if (actionChartScreen != chartScreen1)
      {
        if (startIndex != chartScreen1.getStartDisplayIndex() || endIndex !=chartScreen1.getEndDisplayIndex())
        chartScreen1.zoom(startIndex,endIndex);
      }

      if (actionChartScreen != chartScreen2)
      {
        if (startIndex != chartScreen2.getStartDisplayIndex() || endIndex !=chartScreen2.getEndDisplayIndex())
        chartScreen2.zoom(startIndex,endIndex);
      }

      if (actionChartScreen != chartScreen3)
      {
        if (startIndex != chartScreen3.getStartDisplayIndex() || endIndex !=chartScreen3.getEndDisplayIndex())
        chartScreen3.zoom(startIndex,endIndex);
      }

   }

  void btClear_actionPerformed(ActionEvent e)
  {
    if (chartScreen1 != null){
      chartScreen1.faction.lineRecords.removeAllElements();
      chartScreen1.faction.goldenPartitionLine = null;
      chartScreen1.repaint();
    }

    if (chartScreen2 != null){
      chartScreen2.faction.lineRecords.removeAllElements();
      chartScreen2.faction.goldenPartitionLine = null;
      chartScreen2.repaint();
    }

    if (chartScreen3 != null){
      chartScreen3.faction.lineRecords.removeAllElements();
      chartScreen3.faction.goldenPartitionLine = null;
      chartScreen3.repaint();
    }
  }

}