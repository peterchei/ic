// Title:       FME Chart Project for E-finet
// Version:     1.0
// Copyright:   Copyright (c)
// Author:
// Company:
// Description:


package fmechart;

import java.awt.*;
import java.awt.event.*;
import java.applet.*;


public class TemplateApplet extends Applet
{
  boolean isStandalone = false;
  ChartScreen chartScreen1 = new ChartScreen();
  ChartSource chartSource1 = new ChartSource();
  Button jButton1 = new Button();
  Button button1 = new Button();
  Button button2 = new Button();
  Button button3 = new Button();
  ChartScreen chartScreen2 = new ChartScreen();
  ChartScreen chartScreen3 = new ChartScreen();
  ChartScreen chartScreen4 = new ChartScreen();
  TextField tfsindex = new TextField();
  TextField tfeindex = new TextField();


  //Get a parameter value
  public String getParameter(String key, String def)
  {
    return isStandalone ? System.getProperty(key, def) :
      (getParameter(key) != null ? getParameter(key) : def);
  }

  //Construct the applet

  public TemplateApplet()
  {
  }

  //Initialize the applet
  public void init()
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

  //Component initialization
  private void jbInit() throws Exception
  {
    this.setLayout(null);
    chartScreen1.setBounds(new Rectangle(2, 90, 632, 94));
    chartScreen1.setForeground(Color.white);
    jButton1.setLabel("jButton1");
    jButton1.addActionListener(new java.awt.event.ActionListener()
    {

      public void actionPerformed(ActionEvent e)
      {
        jButton1_actionPerformed(e);
      }
    });
    jButton1.setBounds(new Rectangle(61, 409, 76, 24));
    jButton1.addActionListener(new java.awt.event.ActionListener()
    {

      public void actionPerformed(ActionEvent e)
      {
        jButton1_actionPerformed(e);
      }
    });
    button1.setBounds(new Rectangle(4, 408, 53, 26));
    button1.setLabel("GetData");
    button1.addActionListener(new java.awt.event.ActionListener()
    {

      public void actionPerformed(ActionEvent e)
      {
        button1_actionPerformed(e);
      }
    });

    button2.setBounds(new Rectangle(140, 409, 66, 24));
    button2.setLabel("Zoom");
    button2.addActionListener(new java.awt.event.ActionListener()
    {

      public void actionPerformed(ActionEvent e)
      {
        button2_actionPerformed(e);
      }
    });
    button2.addActionListener(new java.awt.event.ActionListener()
    {

      public void actionPerformed(ActionEvent e)
      {
        button2_actionPerformed(e);
      }
    });
    button3.setBounds(new Rectangle(426, 409, 71, 24));
    button3.setActionCommand("");
    button3.setLabel("Clear All");
    button3.addActionListener(new java.awt.event.ActionListener()
    {

      public void actionPerformed(ActionEvent e)
      {
        button3_actionPerformed(e);
      }
    });
    button3.addActionListener(new java.awt.event.ActionListener()
    {

      public void actionPerformed(ActionEvent e)
      {
        button3_actionPerformed(e);
      }
    });
    button3.addActionListener(new java.awt.event.ActionListener()
    {

      public void actionPerformed(ActionEvent e)
      {
        button3_actionPerformed(e);
      }
    });
    chartScreen2.setBounds(new Rectangle(3, 185, 632, 99));
    chartScreen2.setLayout(null);
    chartScreen3.setBounds(new Rectangle(3, 285, 632, 93));
    chartScreen4.setBounds(new Rectangle(3, 379, 631, 24));
    tfsindex.setBounds(new Rectangle(280, 410, 70, 22));
    tfsindex.setText("0");
    tfeindex.setBounds(new Rectangle(352, 410, 72, 22));
    tfeindex.setText("100");
    button4.setBounds(new Rectangle(641, 215, 95, 29));
    button4.setLabel("UNZoom");
    button4.addActionListener(new java.awt.event.ActionListener()
    {

      public void actionPerformed(ActionEvent e)
      {
        button4_actionPerformed(e);
      }
    });
    button5.setBounds(new Rectangle(641, 245, 94, 30));
    button5.setLabel("DeleteLine");
    button5.addActionListener(new java.awt.event.ActionListener()
    {

      public void actionPerformed(ActionEvent e)
      {
        button5_actionPerformed(e);
      }
    });
    line.setBounds(new Rectangle(641, 6, 95, 27));
    line.setLabel("line");
    line.addActionListener(new java.awt.event.ActionListener()
    {

      public void actionPerformed(ActionEvent e)
      {
        line_actionPerformed(e);
      }
    });
    button7.setBounds(new Rectangle(641, 35, 95, 27));
    button7.setLabel("p-line");
    button7.addActionListener(new java.awt.event.ActionListener()
    {

      public void actionPerformed(ActionEvent e)
      {
        button7_actionPerformed(e);
      }
    });
    button8.setBounds(new Rectangle(641, 65, 95, 28));
    button8.setLabel("zoom");
    button8.addActionListener(new java.awt.event.ActionListener()
    {

      public void actionPerformed(ActionEvent e)
      {
        button8_actionPerformed(e);
      }
    });
    button6.setBounds(new Rectangle(641, 95, 96, 30));
    button6.setLabel("G-partition");
    button6.addActionListener(new java.awt.event.ActionListener()
    {

      public void actionPerformed(ActionEvent e)
      {
        button6_actionPerformed(e);
      }
    });
    button9.setBounds(new Rectangle(641, 126, 96, 28));
    button9.setLabel("Watch");
    button9.addActionListener(new java.awt.event.ActionListener()
    {

      public void actionPerformed(ActionEvent e)
      {
        button9_actionPerformed(e);
      }
    });
    button10.setBounds(new Rectangle(641, 185, 95, 29));
    button10.setLabel("None");
    button10.addActionListener(new java.awt.event.ActionListener()
    {

      public void actionPerformed(ActionEvent e)
      {
        button10_actionPerformed(e);
      }
    });
    button11.setBounds(new Rectangle(641, 276, 93, 30));
    button11.setLabel("Delete G-partitin");
    button11.addActionListener(new java.awt.event.ActionListener()
    {

      public void actionPerformed(ActionEvent e)
      {
        button11_actionPerformed(e);
      }
    });
    button12.setBounds(new Rectangle(640, 155, 43, 29));
    button12.setLabel("Move");
    button12.addActionListener(new java.awt.event.ActionListener()
    {

      public void actionPerformed(ActionEvent e)
      {
        button12_actionPerformed(e);
      }
    });
    button13.setBounds(new Rectangle(683, 157, 26, 25));
    button13.setActionCommand("");
    button13.setLabel("<");
    button13.addActionListener(new java.awt.event.ActionListener()
    {

      public void actionPerformed(ActionEvent e)
      {
        button13_actionPerformed(e);
      }
    });
    button14.setBounds(new Rectangle(710, 157, 26, 25));
    button14.setLabel(">");
    button14.addActionListener(new java.awt.event.ActionListener()
    {

      public void actionPerformed(ActionEvent e)
      {
        button14_actionPerformed(e);
      }
    });
    fIButton1.setBounds(new Rectangle(642, 317, 34, 34));
    fIButton1.setLabel("fIButton1");
    fButtonBar1.setBackground(SystemColor.inactiveCaption);
    fButtonBar1.setBounds(new Rectangle(0, 26, 242, 25));
    fButtonBar2.setBackground(SystemColor.inactiveCaption);
    fButtonBar2.setBounds(new Rectangle(0, 1, 623, 26));
    this.setBackground(new Color(212, 198, 200));
    fIButton2.setBounds(new Rectangle(97, 49, 102, 32));
    fIButton2.setLabel("fIButton2");
    this.add(button1, null);
    this.add(jButton1, null);
    this.add(button2, null);
    this.add(tfsindex, null);
    this.add(tfeindex, null);
    this.add(line, null);
    this.add(button7, null);
    this.add(button8, null);
    this.add(button6, null);
    this.add(button9, null);
    this.add(button3, null);
    this.add(button10, null);
    this.add(button4, null);
    this.add(button5, null);
    this.add(button11, null);
    this.add(button12, null);
    this.add(button13, null);
    this.add(button14, null);
    this.add(fIButton1, null);
    this.add(fButtonBar2, null);
    this.add(fButtonBar1, null);
    this.add(chartScreen1, null);
    this.add(chartScreen2, null);
    this.add(chartScreen3, null);
    this.add(chartScreen4, null);
    this.add(fIButton2, null);
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

  void jButton1_actionPerformed(ActionEvent e)
  {
      Toolkit tk = Toolkit.getDefaultToolkit();
      Dimension dd = tk.getBestCursorSize(10,10);
      //System.out.println("width:" + dd.width + " :" + dd.height );
      //Image cursorImage = createImage(dd.width,dd.height);

      Image cursorImage = getImage(getDocumentBase(),"cross1.gif");
      fIButton1.offImage = cursorImage;
      if (cursorImage==null)
      {
        //System.out.println("None");

      }


      //Graphics g = cursorImage.getGraphics();
      //g.setXORMode(Color.white);
      //g.setColor(Color.white);
      //g.fillRect(0,0,32,32);
      //g.setColor(Color.black);
      //g.drawLine(dd.width/2,0,dd.width/2,dd.height);
      //g.drawLine(0,dd.height/2,dd.width ,dd.height/2);
     // cursorImage = createImage(32,32);

      Cursor cc = tk.createCustomCursor(cursorImage,new Point(dd.width/2,dd.height/2),"MyCursor");
     // fIButton1.offImage = cursorImage;
/*

      Cursor cc;

      cc = new Cursor(Cursor.HAND_CURSOR);
      cc = new Cursor(Cursor.MOVE_CURSOR);
      cc = new Cursor(Cursor.TEXT_CURSOR);
      cc = new Cursor(Cursor.CROSSHAIR_CURSOR);
      cc = new Cursor(Cursor.E_RESIZE_CURSOR);
      cc = new Cursor(Cursor.CROSSHAIR_CURSOR);
      cc = new Cursor(Cursor.W_RESIZE_CURSOR);
*/


      chartScreen1.setCursor(cc);

      chartScreen1.repaint();
      chartScreen2.repaint();
      chartScreen3.repaint();
      chartScreen4.repaint();
  }
Image cursorImage;
  Button button4 = new Button();
  Button button5 = new Button();
  Button line = new Button();
  Button button7 = new Button();
  Button button8 = new Button();
  Button button6 = new Button();
  Button button9 = new Button();
  Button button10 = new Button();
  Button button11 = new Button();
  Button button12 = new Button();
  Button button13 = new Button();
  Button button14 = new Button();
  FIButton fIButton1 = new FIButton();
  FButtonBar fButtonBar1 = new FButtonBar();
  FMenuBar fButtonBar2 = new FMenuBar();
  FIButton fIButton2 = new FIButton();
  void button1_actionPerformed(ActionEvent e)
  {
    cursorImage = getImage(getDocumentBase(),"cross1.gif");
    ChartData mydata = chartSource1.getTestData(000,chartScreen1.getMaxNumberOfPoint());

    ChartUIObject mychart1 = new ChartUIObject(mydata,"LMain1");
    ChartUIObject mychart2 = new ChartUIObject(mydata,"LMain2");
    ChartUIObject mychart3 = new ChartUIObject(mydata,"LMain3");
    ChartUIObject mychart4 = new ChartUIObject(mydata,"LMain4");

    mychart1.chartType = ChartUIObject.BAR;
    mychart2.chartType = ChartUIObject.CANDLE;
    mychart3.chartType = ChartUIObject.LINE;
    mychart4.chartType = ChartUIObject.VOLUME;

    chartScreen1.addChart(mychart1);
    chartScreen2.addChart(mychart2);
    chartScreen3.addChart(mychart3);
    chartScreen4.addChart(mychart4);

    chartScreen1.zoom(0,chartScreen1.getMaxNumberOfPoint()-1);
    chartScreen2.zoom(0,chartScreen2.getMaxNumberOfPoint()-1);
    chartScreen3.zoom(0,chartScreen3.getMaxNumberOfPoint()-1);
    chartScreen4.zoom(0,chartScreen4.getMaxNumberOfPoint()-1);
  }

  void button2_actionPerformed(ActionEvent e)
  {
    int sIndex = Integer.parseInt(tfsindex.getText());
    int eIndex = Integer.parseInt(tfeindex.getText());
    chartScreen1.zoom( sIndex,eIndex);
    chartScreen2.zoom( sIndex,eIndex);
    chartScreen3.zoom( sIndex,eIndex);
    chartScreen4.zoom( sIndex,eIndex);


  }

  void button3_actionPerformed(ActionEvent e)
  {
  //  chartScreen1.action()
    chartScreen1.removeAllCharts();
    chartScreen1.faction.lineRecords.clear();
    chartScreen1.faction.goldenPartitionLine = null;
    chartScreen1.faction.zoomRecords.clear();
    chartScreen1.repaint();

    chartScreen2.removeAllCharts();
    chartScreen2.faction.lineRecords.clear();
    chartScreen2.faction.goldenPartitionLine = null;
    chartScreen2.faction.zoomRecords.clear();
    chartScreen2.repaint();

    chartScreen3.removeAllCharts();
    chartScreen3.faction.lineRecords.clear();
    chartScreen3.faction.goldenPartitionLine = null;
    chartScreen3.faction.zoomRecords.clear();
    chartScreen3.repaint();

    chartScreen4.removeAllCharts();
    chartScreen4.faction.lineRecords.clear();
    chartScreen4.faction.zoomRecords.clear();
    chartScreen4.faction.goldenPartitionLine = null;
    chartScreen4.repaint();

  }

  void button4_actionPerformed(ActionEvent e)
  {
    chartScreen1.undoZoom();

  }

  void button5_actionPerformed(ActionEvent e)
  {
   chartScreen1.undoInsertLine();
  }

  void line_actionPerformed(ActionEvent e)
  {
   chartScreen1.faction.currentActionType = FAction.INSERTLINE;
  }

  void button7_actionPerformed(ActionEvent e)
  {
    chartScreen1.faction.currentActionType = FAction.INSERTPARALLELLINE;
  }

  void button6_actionPerformed(ActionEvent e)
  {
   chartScreen1.faction.currentActionType = FAction.GOLDENPARTITION;
  }

  void button9_actionPerformed(ActionEvent e)
  {
   chartScreen1.faction.currentActionType = FAction.WATCH;
  }

  void button10_actionPerformed(ActionEvent e)
  {
      chartScreen1.faction.currentActionType = FAction.NONEACTION;
  }

  void button8_actionPerformed(ActionEvent e)
  {
   chartScreen1.faction.currentActionType = FAction.ZOOMIN;
  }

  void button11_actionPerformed(ActionEvent e)
  {
   chartScreen1.faction.goldenPartitionLine = null;
   chartScreen1.repaint();
  }

  void button12_actionPerformed(ActionEvent e)
  {
    chartScreen1.faction.currentActionType = FAction.MOVECHART;

  }

  void button13_actionPerformed(ActionEvent e)
  {
    chartScreen1.moveLeft();

  }

  void button14_actionPerformed(ActionEvent e)
  {
    chartScreen1.moveRight();

  }


}