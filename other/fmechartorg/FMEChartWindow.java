package fmechart;

import java.awt.*;

/**
 * Title:        FME Chart Project for E-finet
 * Description:
 * Copyright:    Copyright (c)
 * Company:
 * @author
 * @version 1.0
 */


import java.awt.*;
import java.awt.event.*;


public class FMEChartWindow extends Frame implements WindowListener
{
  private FMEChartA chartPanel = null;
  private Container originalContainer = null;

  BorderLayout borderLayout1 = new BorderLayout();

  public void setChartPanel(FMEChartA pnChart)
  {
    chartPanel = pnChart;
    originalContainer = pnChart.getParent();
    this.add(pnChart,borderLayout1.CENTER);
  }

  public FMEChartWindow()
  {
    try
    {
      jbInit();
      this.addWindowListener(this);
    }
    catch(Exception e)
    {
      e.printStackTrace();
    }
  }

  private void jbInit() throws Exception
  {
   // button1.setLabel("button1");
    this.setLayout(borderLayout1);

  }

  public void windowClosing(WindowEvent e){
  this.dispose();
  chartPanel.btOpenClose.setVisible(true);
  //originalContainer.setLayout(borderLayout);
  this.originalContainer.add(chartPanel);
  chartPanel.setBounds(0,0,originalContainer.getSize().width,originalContainer.getSize().height);
  originalContainer.repaint();
  }
  public void windowOpened(WindowEvent e){System.out.println("closed");}

  public void windowClosed(WindowEvent e){System.out.println("closed");}
  public void windowIconified(WindowEvent e){System.out.println("iconified");}
  public void windowDeiconified(WindowEvent e){System.out.println("deiconified");}
  public void windowActivated(WindowEvent e){System.out.println("activated");}
  public void windowDeactivated(WindowEvent e){System.out.println("deactivated");}






}