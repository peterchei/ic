/**
 * Title:        FME Chart Project for E-finet<p>
 * Description:  <p>
 * Copyright:    Copyright (c) <p>
 * Company:      <p>
 * @author
 * @version 1.0
**/

package fmechart;

import java.awt.event.*;
import java.awt.*;
import java.awt.image.*;
import java.util.Vector;

public class ChartScreen extends Panel implements MouseListener, MouseMotionListener
{

  private final String lbArray[][] ={
                                   {"None","\u7121"}                                      //0
                                  ,{"Simple Moving Average","\u7c21\u55ae\u79fb\u52d5\u5e73\u5747\u7dda"}      //1
                                  ,{"Weighted Moving Average","\u52a0\u6b0a\u79fb\u52d5\u5e73\u5747\u7dda"} //2
                                  ,{"Exponential Moving Average","\u6307\u6578\u79fb\u52d5\u5e73\u5747\u7dda"} //3
                                  ,{"Bollinger Bands","\u4fdd\u6b77\u52a0\u901a\u9053"}  //4
                                  ,{"Open","\u958b\u5e02"}                        //5
                                  ,{"Close","\u6536\u5e02"}                      //6
                                  ,{"High","\u6700\u9ad8"}                        //7
                                  ,{"Low","\u6700\u4f4e"}                          //8
                                  ,{"Volume","\u6210\u4ea4\u91cf"}                    //9
                                  ,{"RSI","RSI"}                          //10
                                  ,{"STC","STC"}                          //11
                                  ,{"EMA","EMA"}                          //12
                                  ,{"WMA","WMA"}                          //13
                                  ,{"SMA","SMA"}                          //14
                                  ,{"Date","\u671f\u9593"}                        //15
                                  ,{"Time","\u671f\u9593"}                        //16
                                  ,{"Relative Strength Index","\u76f8\u5c0d\ufffd\u5f31\u6307\u6578"}       //17
                                  ,{"Stochastics","\u96a8\u6a5f\u6307\u6578"}       //18
                                  ,{"On Balance Volume","\u6210\u4ea4\u91cf\u5e73 \u6307\u6578"}       //19
                                  ,{"Moving Average Convergence Divergence","\u79fb\u52d5\u5e73\u5747\u7dda\u532f\u805a\u80cc\u99b3\u6307\u6a19"}     //20
                                  //,{"MACD","\u79fb\u52d5\u5e73\u5747\u7dda\u532f\u805a\u80cc\u99b3\u6307\u6a19"}     //20
                                  ,{"William %R","\u5a01\u5ec9\u6307\u6a19"}  //21
                                  ,{"Devation","Devation"} //22

                                  };
///Here is some color variable
  Color backgroundColor = Color.white;
  public static final int NONE = 0;
  public static final int LOADING = 1;
  public static final int STARTED = 2;

  private int screenState = STARTED;
  private boolean isUpdatingBaseScreen = false; // check isUpdatingBaseScreen or not.
  private Color gridColor;
  private int language = FConfig.constEnglish;

  public void setScreenState(int ss)
  {
    screenState = ss;
    repaint();
  }
  public int getScreenState()
  {
    return screenState;
  }

  public void setLanguage(int tlanguage)
  {
    language = tlanguage;
  }


///The space of top, left, right and bottom in pixels.
  private int topSpace = 30;
  private int leftSpace = 38;
  private int rightSpace = 38;
  private int bottomSpace = 15;
////

//The maximun number of point that the chart have....
  private int maxNumberOfChartPoint=0;

//The screenImage for drawing....
//  protected BufferedImage screenImage = null; //the chart image
//  protected BufferedImage allscreenImage = null; //the full screen image
  protected Image screenImage = null; //the chart image
  protected Image allscreenImage = null; //the full screen image


//Define of Resolution and display information
  private final int minResolution = 3;
  private int resolution  = 3; //must be < 4 pixels per point.
  private int startDisplayIndex = 0;
  private int endDisplayIndex = 0;

  private ScreenActionListener screenActionListener = null;

  //Action Object to record and repersent all the action.
  FAction faction = new FAction();
  //A list of add ChartUIObject needed to plot to this screen.....
  private Vector chartObjects = new Vector();



  //get the chart given the key.
  public ChartUIObject getChart(String key)
  {
    for (int i=0;i<chartObjects.size();i++)
    {
      ChartUIObject pchart = (ChartUIObject) chartObjects.elementAt(i);
      if (pchart.idKey.equals(key))
      {
        return pchart;
      }
    }
    return null;
  }

  //add a chart to this screen
  public  boolean addChart(ChartUIObject newChart)
  {
    int tempNumb = 0;
    for (int i=0;i<chartObjects.size();i++)
    {
      ChartUIObject cchart = (ChartUIObject) chartObjects.elementAt(i);
      if (cchart.idKey == newChart.idKey )
      {
        chartObjects.removeElement(cchart);
      }
    }
    chartObjects.addElement(newChart);
    // get the max Number Of Chart Point......

    for (int i=0 ; i< this.chartObjects.size() ; i++)
    {
      ChartUIObject cchart = (ChartUIObject) chartObjects.elementAt(i);
      if (cchart.chartData.data.size() > tempNumb) tempNumb = cchart.chartData.data.size();
    }
    maxNumberOfChartPoint = tempNumb;

    return true;
  }

  //get the LowerBound and UpperBound of the L or R chart
  private FBound getAxisBound(int axisBarType)
  {
  //  System.out.println("Entering getAxisBound ********************************************************************");
//    System.out.println("MAX: " + maxNumberOfChartPoint + "Start: " + startDisplayIndex + " End : " + endDisplayIndex);
    double bStockMax = -1000000f;
    double bStockMin =  1000000f;

    float bPercentageMax = 0;
    float bPercentageMin = 100;


    float bMACDMax = -1000000f;
    float bMACDMin = 1000000f;

    float bOBVMax = -10000f;
    float bOBVMin = 100000f;


    double bVolumeMax = 0;
    double bVolumeMin = 0;




    for (int i=0;i<chartObjects.size();i++)
    {
      ChartUIObject cchart = (ChartUIObject) chartObjects.elementAt(i);
      if (cchart.axisBar == axisBarType )
      {
        if (cchart.chartType == ChartType.BAR || cchart.chartType == ChartType.CANDLE || cchart.chartType == ChartType.LINE )
        {
            double Max = cchart.chartData.getMaximum(startDisplayIndex, endDisplayIndex ,"STOCK");
            if (Max > bStockMax) bStockMax = Max;
            double Min = cchart.chartData.getMinimum(startDisplayIndex, endDisplayIndex ,"STOCK");
            if (Min < bStockMin)  bStockMin = Min;
        }

        if (cchart.chartType == ChartType.VOLUME)
        {
            double Max = cchart.chartData.getMaximumVolume(startDisplayIndex, endDisplayIndex);
            if (Max > bVolumeMax) bVolumeMax = Max;
        }
     }

       if (cchart.chartType == ChartType.PERCENTAGE)
       {
            float Max = (float) cchart.chartData.getMaximum(startDisplayIndex, endDisplayIndex ,"PERCENTAGE");
            if (Max > bPercentageMax) bPercentageMax = Max;
            float Min = (float) cchart.chartData.getMinimum(startDisplayIndex, endDisplayIndex ,"PERCENTAGE");
            if (Min < bPercentageMin)  bPercentageMin = Min;
       }

       if (cchart.chartType == ChartType.SIMPLEMOVINGAVERAGE || cchart.chartType == ChartType.WEIGHTEDMOVINGAVERAGE || cchart.chartType == ChartType.EXPONENTIALMOVINGAVERAGE)
       {
            double Max = cchart.chartData.getMaximum(startDisplayIndex, endDisplayIndex ,"STOCK");
            if (Max > bStockMax) bStockMax = Max;
            double Min = cchart.chartData.getMinimum(startDisplayIndex, endDisplayIndex ,"STOCK");
            if (Min < bStockMin)  bStockMin = Min;
       }

       if (cchart.chartType == ChartType.BOLLINGERBAND)
       {
            double Max = cchart.chartData.getMaximum(startDisplayIndex, endDisplayIndex ,"STOCK");
            if (Max > bStockMax) bStockMax = Max;
            double Min = cchart.chartData.getMinimum(startDisplayIndex, endDisplayIndex ,"STOCK");
            if (Min < bStockMin)  bStockMin = Min;
       }


       if (cchart.chartType == ChartType.MACD)
       {
            double Max = cchart.chartData.getMaximum(startDisplayIndex, endDisplayIndex ,"MACD");
            if (Max > bStockMax) bMACDMax = (float) Max;
            double Min = cchart.chartData.getMinimum(startDisplayIndex, endDisplayIndex ,"MACD");
            if (Min < bStockMin)  bMACDMin = (float) Min;
       }

       if (cchart.chartType == ChartType.OBV )
       {
            double Max = cchart.chartData.getMaximum(startDisplayIndex, endDisplayIndex ,"OBV");
            if (Max > bStockMax) bOBVMax = (float) Max;
            double Min = cchart.chartData.getMinimum(startDisplayIndex, endDisplayIndex ,"OBV");
            if (Min < bStockMin)  bOBVMin = (float) Min;
       }
   }

    FBound newBound = new FBound();

    double ss =  (bStockMax - bStockMin) *0.1f;
//    double ss = 0.01;
    newBound.LowerStockBound = bStockMin-ss;
    newBound.UpperStockBound = bStockMax +ss;
    newBound.LowerVolumeBound = 0;
    newBound.UpperVolumeBound = bVolumeMax  +5;
    ss =  (bPercentageMax - bPercentageMin) *0.1f;
    newBound.LowerPercentageBound = (float) bPercentageMin - (float)ss;
    newBound.UpperPercentageBound = (float) bPercentageMax + (float)ss;
    ss =  (bMACDMax - bMACDMin) *0.1f;
    newBound.LowerMACDBound = (float) bMACDMin - (float) ss;
    newBound.UpperMACDBound = (float) bMACDMax + (float) ss;
    newBound.LowerOBVBound = (int) bOBVMin;
    newBound.UpperOBVBound = (int) bOBVMax;

    for (int i=0;i<chartObjects.size();i++)
    {
      ChartUIObject cchart = (ChartUIObject) chartObjects.elementAt(i);
      if (cchart.axisBar == axisBarType)
      {
         cchart.chartBound  = newBound;
      }
      if (cchart.chartType == ChartType.PERCENTAGE && axisBarType == ChartType.LEFTAXIS )
      {
        cchart.chartBound = newBound;
      }


    }
    return newBound;
 }


  //function to initScreen, it should be called once in applet.start function in order to fix UI bug.
  public void initScreen()
  {
     if (screenImage == null)
     {
        screenImage = createImage(getSize().width,getSize().height);
     }
     if (allscreenImage == null)
     {
        allscreenImage = createImage(getSize().width,getSize().height);
     }
  }

  public int getStartDisplayIndex()
  {
    return startDisplayIndex;
  }

  public int getEndDisplayIndex()
  {
    return endDisplayIndex;
  }


  //remove a chart from this screen given the name
  public boolean removeChart(String key)
  {
    for (int i=0;i<chartObjects.size();i++)
    {
      ChartUIObject chartobj = (ChartUIObject) chartObjects.elementAt(i);
      if (chartobj.idKey.equals(key))
      {
        chartObjects.removeElementAt(i);
        return true;
      }
    }

    return false;
  }


  //remove a chart from this screen given the name
  public boolean removeChart(int code)
  {
    for (int i=0;i<chartObjects.size();i++)
    {
      ChartUIObject chartobj = (ChartUIObject) chartObjects.elementAt(i);
      if (chartobj.chartData.Code == code)
      {
        chartObjects.removeElementAt(i);
      }
    }

    return true;
  }
  public void removeChartsByType(int cType)
  {
    for (int i=0;i<chartObjects.size();i++)
    {
      ChartUIObject chartobj = (ChartUIObject) chartObjects.elementAt(i);
//      System.out.println("HELLO");
      if (chartobj.chartType  == cType)
      {
        chartObjects.removeElementAt(i);
       // System.out.println("Remove a line");
        i=-1;
      }
    }
  }

  //determine the x,y is within the chartRegion or not.
  boolean isWithinChartRegion(int x, int y)
  {
    if (x>=leftSpace && x<= (getXAxisWidth()+leftSpace+2))
    {
      if (y>=topSpace && y <= (getYAxisWidth()+topSpace))  return true;
    }
    return false;
  }


// return the width of X Axis in pixels
  int getXAxisWidth()
  {
    return getSize().width-leftSpace-rightSpace;
  }

// return the height of Y Axis in pixels.
  int getYAxisWidth()
  {
//    getSize().height
    return getSize().height - topSpace - bottomSpace;
  }


  public ChartScreen(int TOPSpace, int BOTTOMSpace, int LEFTSpace, int RIGHTSpace)
  {
    topSpace = TOPSpace;
    bottomSpace = BOTTOMSpace;
    leftSpace = LEFTSpace;
    rightSpace = RIGHTSpace;
    //ChartScreen();
    try
    {
      //Enable Mouse Event Listeners.
      addMouseListener(this);
      addMouseMotionListener(this);
      //init the ui component
      jbInit();


    }
    catch(Exception e)
    {
      e.printStackTrace();
    }

  }

  public ChartScreen()
  {
    try
    {
      //Enable Mouse Event Listeners.
      addMouseListener(this);
      addMouseMotionListener(this);
      //init the ui component
      jbInit();
    }
    catch(Exception e)
    {
      e.printStackTrace();
    }
  }

//jbInit is used to init UI components and is mainly generated by the Jbuilder.
  private void jbInit() throws Exception
  {
    this.setBackground(backgroundColor);
    this.setLayout(null);
  }


  public void setBounds(int x,int y, int w, int h)
  {

    Image newImage1 = null;
    Image newImage2 = null;
    try {
    if (screenImage != null  && (screenImage.getHeight(this) != h || screenImage.getWidth(this)!=w) )
    {
      //System.out.println("creating image 1");
      newImage1 = createImage(w, h);
      //screenImage = createImage(w, h);
      screenImage = newImage1;
    }
    if (allscreenImage != null && (allscreenImage.getHeight(this) != h || allscreenImage.getWidth(this)!=w) )
    {
      //System.out.println("create image 2");
      newImage2 = createImage(w, h);
      //allscreenImage = createImage(w, h);
      allscreenImage = newImage2;
    }


    super.setBounds(x,y,w,h);
    }
    catch (Exception ee)
    {
      System.out.println("Error when resize, No memory!");
    }





  }

  public boolean zoom(int startIndex, int endIndex)
  {
    //System.out.println("Zoom:" + startIndex + " : " + endIndex);
  //first, determine the new resolution.....
    int numberOfpoints = endIndex - startIndex + 1;
    float fpixelsperpoint = (float) (getXAxisWidth()-2)/numberOfpoints;
    resolution = (int )(fpixelsperpoint + 0.01);
 //   System.out.println(fpixelsperpoint + " :  " + resolution);

    startDisplayIndex = startIndex;
    endDisplayIndex = endIndex;


//  update the Screen and notify to listener
    this.updateBaseScreen();

    //remove lines because of when using percentage..........
    ChartUIObject ccchart = getLeftChart();
    if (ccchart!=null && ccchart.chartType == ChartType.PERCENTAGE )
    {
      faction.lineRecords.removeAllElements();
      faction.goldenPartitionLine = null;
    }
    this.repaint();
    if (screenActionListener != null)
    {
      screenActionListener.OnZoomCompleted(this,startDisplayIndex,endDisplayIndex);
    }
    return true;
  }

//  The function is used to plot the chart  ....................................
  public void update(Graphics g)
  {
    paint(g);
  }

  public synchronized void paint(Graphics gg)
  {
    if (isUpdatingBaseScreen) return;
   // System.out.println("Thread ID" + "this.painting " + Thread.currentThread() );
     if (screenImage == null)
     {
        screenImage = createImage(getSize().width,this.getSize().height);
     }
     if (allscreenImage == null)
     {
        allscreenImage = createImage(getSize().width,this.getSize().height);
     }
//     allscreenImage = createImage(w,h);

     if (this.screenState == LOADING )
     {
  //      System.out.println(">>Loading");
        plotLoading(gg);
        return;
     }
      //super.paint(gg);
      //this.updateBaseScreen();
      Graphics g = allscreenImage.getGraphics();

      g.drawImage(screenImage,0,0,getSize().width,getSize().height,this);
      g.setColor(Color.black);



// draw lines to the screen ///////////////////////////////////////////////////////////////////////////
     // System.out.println(faction.lineRecords.size());
      for (int i=0;i<faction.lineRecords.size();i++)
      {
        FLine fline = (FLine) faction.lineRecords.elementAt(i);
       //System.out.println(fline.point1.x + ":" +  fline.point1.y + " :" + fline.point2.x + " : " + fline.point2.y);
        if (fline.isFixedLine)
        {
          g.setColor(Color.black);
          g.drawLine(fline.point1.x, fline.point1.y, fline.point2.x ,fline.point2.y);
        }
        else
        {
          g.setColor(Color.black);
          int x1 = getScreenXPositionFromPoint(fline.index1);
          int x2 = getScreenXPositionFromPoint(fline.index2);
          int y1,y2;
          ChartUIObject cchart = getLeftChart();
          if (cchart != null)
          {
            double Max = cchart.getUpperBound();
            double Min = cchart.getLowerBound();
            y1 = getScreenYPosition(fline.value1,Max,Min);
            y2 = getScreenYPosition(fline.value2,Max,Min);
            g.drawLine(x1,y1,x2,y2);
          }
        }
      }
///////////////////////////////////////////////////////////////////////////////////////////

      // if we are not process GOLDENPAERTITION and we have one need to draw
      if (faction.currentActionType != FAction.GOLDENPARTITION || faction.actionProcessing == false)
      if (faction.goldenPartitionLine != null)
      {
        int MaxY=0, MinY=0;
        int x1, x2;
        if (faction.goldenPartitionLine.isFixedLine)
        {
          MaxY = Math.max(faction.goldenPartitionLine.point1.y,faction.goldenPartitionLine.point2.y);
          MinY = Math.min(faction.goldenPartitionLine.point1.y,faction.goldenPartitionLine.point2.y);
//          g.drawLine(fline.point1.x, fline.point1.y, fline.point2.x ,fline.point2.y);
          x1 = faction.goldenPartitionLine.point1.x;
          x2 = faction.goldenPartitionLine.point2.x;

        }
        else
        {
  //      g.setColor(Color.black);
          x1 = getScreenXPositionFromPoint(faction.goldenPartitionLine.index1);
          x2 = getScreenXPositionFromPoint(faction.goldenPartitionLine.index2);
          int y1,y2;
          ChartUIObject cchart = getLeftChart();
          if (cchart != null)
          {
            double Max = cchart.getUpperBound();
            double Min = cchart.getLowerBound();
            y1 = getScreenYPosition(faction.goldenPartitionLine.value1,Max,Min);
            y2 = getScreenYPosition(faction.goldenPartitionLine.value2,Max,Min);
            MaxY = Math.max(y1,y2);
            MinY = Math.min(y1,y2);
//            g.drawLine(x1,y1,x2,y2);
          }
        }

          int Y618 = (int) (MaxY - (MaxY-MinY)*0.618f);
          int Y50  = (int) (MaxY - (MaxY-MinY)*0.50f);
          int Y382 = (int) (MaxY - (MaxY-MinY)*0.382);

          g.drawString("0%",x1 + 2,MaxY-1);
          g.drawString("100%",x1 + 2,MinY-1);
          g.drawLine(x1,MaxY, leftSpace + getXAxisWidth(),MaxY);
          g.drawLine(x1,MinY, leftSpace + getXAxisWidth(),MinY);
          g.setColor(Color.red);
          drawDotLine(g,x1,Y618, leftSpace + getXAxisWidth(),Y618);
          drawDotLine(g,x1,Y50, leftSpace + getXAxisWidth(),Y50);
          drawDotLine(g,x1,Y382, leftSpace + getXAxisWidth(),Y382);
          g.setColor(Color.darkGray);
          g.drawString("61.8%",x1 + 2,Y618-1);
          g.drawString("50%",x1 + 2,Y50-1);
          g.drawString("38.2%",x1 + 2,Y382-1);

          ChartUIObject cchart = (ChartUIObject) getLeftChart();
          if (cchart!=null)
          {
            double UpperBound = cchart.getUpperBound();
            double LowerBound = cchart.getLowerBound();
            float v100 = (float) getYValueFromScreen(MinY,UpperBound,LowerBound);
            float v618 = (float) getYValueFromScreen(Y618,UpperBound,LowerBound);
            float v500 = (float) getYValueFromScreen(Y50,UpperBound,LowerBound);
            float v382 = (float) getYValueFromScreen(Y382,UpperBound,LowerBound);
            float v000 = (float) getYValueFromScreen(MaxY,UpperBound,LowerBound);
            g.setColor(cchart.firstColor);
            if (cchart.chartType == ChartType.PERCENTAGE)
            {
              g.drawString(FFormater.formatData2(v100)+"%",x1 + 40,MinY-1);
              g.drawString(FFormater.formatData2(v618)+"%",x1 + 40,Y618-1);
              g.drawString(FFormater.formatData2(v500)+"%",x1 + 40,Y50-1);
              g.drawString(FFormater.formatData2(v382)+"%",x1 + 40,Y382-1);
              g.drawString(FFormater.formatData2(v000)+"%",x1 + 40,MaxY-1);
           }
           else
           {
              g.drawString(FFormater.formatData2(v100),x1 + 40,MinY-1);
              g.drawString(FFormater.formatData2(v618),x1 + 40,Y618-1);
              g.drawString(FFormater.formatData2(v500),x1 + 40,Y50-1);
              g.drawString(FFormater.formatData2(v382),x1 + 40,Y382-1);
              g.drawString(FFormater.formatData2(v000),x1 + 40,MaxY-1);
           }
          }
          cchart = (ChartUIObject) getRightChart();
          if (cchart!=null)
          {
            double UpperBound = cchart.getUpperBound();
            double LowerBound = cchart.getLowerBound();
            float v100 = (float) getYValueFromScreen(MinY,UpperBound,LowerBound);
            float v618 = (float) getYValueFromScreen(Y618,UpperBound,LowerBound);
            float v500 = (float) getYValueFromScreen(Y50,UpperBound,LowerBound);
            float v382 = (float) getYValueFromScreen(Y382,UpperBound,LowerBound);
            float v000 = (float) getYValueFromScreen(MaxY,UpperBound,LowerBound);
            g.setColor(cchart.firstColor);

            g.drawString(FFormater.formatData2(v100),x1 + 80,MinY-1);
            g.drawString(FFormater.formatData2(v618),x1 + 80,Y618-1);
            g.drawString(FFormater.formatData2(v500),x1 + 80,Y50-1);
            g.drawString(FFormater.formatData2(v382),x1 + 80,Y382-1);
            g.drawString(FFormater.formatData2(v000),x1 + 80,MaxY-1);
          }
      }


/// process current action ..................................
      switch (faction.currentActionType)
      {
////----------------------Response to Zoom Action ------------------------------------------////////
        case FAction.ZOOMIN:
        if (faction.actionProcessing == true)
        {
          Point opoint = new Point();
          Point epoint = new Point();
          opoint.x = Math.min(faction.pressMpoint.x,faction.currentMpoint.x);
          opoint.y = Math.min(faction.pressMpoint.y,faction.currentMpoint.y);
          epoint.x = Math.max(faction.pressMpoint.x,faction.currentMpoint.x);
          epoint.y = Math.max(faction.pressMpoint.y,faction.currentMpoint.y);

          if (opoint.y < topSpace) opoint.y = topSpace + 1;
          if (opoint.x < leftSpace) opoint.x = leftSpace +1;
          if (epoint.y > topSpace + getYAxisWidth()) epoint.y = topSpace + getYAxisWidth()-1;
          if (epoint.x > leftSpace + getXAxisWidth()) epoint.x = leftSpace + getXAxisWidth()-1;
          g.drawRect(opoint.x,opoint.y,Math.abs(epoint.x-opoint.x),Math.abs(epoint.y-opoint.y));
        }
        break;

////----------------------Response to Watch Action -----------------------------------------///////
        case FAction.WATCH:
        if (faction.actionProcessing == true &&  isWithinChartRegion(faction.currentMpoint.x,faction.currentMpoint.y))
        {
          ChartUIObject cchart = (ChartUIObject) getLeftChart();
          if (cchart != null )
//          if (cchart.chartType == ChartType.LINE ||  cchart.chartType == ChartType.CANDLE || cchart.chartType == ChartType.BAR  || cchart.chartType == ChartType.VOLUME || cchart.chartType == ChartType.MACD)
//          {
            drawWatchAction();
//          }
        }

        break;
////----------------------Response to INSERTLINE Action ------------------------------------------////////
        case FAction.INSERTLINE:
        //System.out.println(faction.actionProcessing);
        if (faction.actionProcessing == true)
        {
           g.drawLine(faction.currentMpoint.x,faction.currentMpoint.y, faction.pressMpoint.x, faction.pressMpoint.y );
        }
        break;
////----------------------Response to Insert Parallel Line Action ------------------------------------------////////
        case FAction.INSERTPARALLELLINE:
//          if (faction.actionProcessing == true &&  isWithinChartRegion(faction.currentMpoint.x,faction.currentMpoint.y))
        if (faction.actionProcessing == true)
        {
             FLine fline = (FLine) faction.lineRecords.lastElement();
             Point rpoint = new Point(0,0);
             if (fline.point1.x<fline.point2.x)
             {
              rpoint.x = fline.point1.x;
              rpoint.y = fline.point1.y;
             }
             else
             {
              rpoint.x = fline.point2.x;
              rpoint.y = fline.point2.y;
             }
             int dx = + faction.currentMpoint.x -rpoint.x ;
             int dy =  + faction.currentMpoint.y -rpoint.y;

             g.drawLine(fline.point1.x +dx, fline.point1.y + dy, fline.point2.x +dx, fline.point2.y + dy );
          }
        break;

////----------------------Response to Golden partition Action ------------------------------------------////////
      case FAction.GOLDENPARTITION:
        //if (faction.actionProcessing == true &&  isWithinChartRegion(faction.currentMpoint.x,faction.currentMpoint.y))
        if (faction.actionProcessing == true )
        {
          int MaxY = Math.max(faction.pressMpoint.y,faction.currentMpoint.y);
          int MinY = Math.min(faction.pressMpoint.y,faction.currentMpoint.y);

          int Y618 = (int) (MaxY - (MaxY-MinY)*0.618f);
          int Y50  = (int) (MaxY - (MaxY-MinY)*0.50f);
          int Y382 = (int) (MaxY - (MaxY-MinY)*0.382);
          g.drawString("100%",faction.pressMpoint.x + 2,MinY-1);
          g.drawLine(faction.pressMpoint.x,MaxY, leftSpace + getXAxisWidth(),MaxY);
          g.drawString("0%",faction.pressMpoint.x + 2,MaxY-1);
          g.drawLine(faction.pressMpoint.x,MinY, leftSpace + getXAxisWidth(),MinY);
          g.setColor(Color.red);
          drawDotLine(g,faction.pressMpoint.x,Y618, leftSpace + getXAxisWidth(),Y618);
          drawDotLine(g,faction.pressMpoint.x,Y50, leftSpace + getXAxisWidth(),Y50);
          drawDotLine(g,faction.pressMpoint.x,Y382, leftSpace + getXAxisWidth(),Y382);
          g.setColor(Color.darkGray);
          g.drawString("38.2%",faction.pressMpoint.x + 2,Y382-1);
          g.drawString("50%",faction.pressMpoint.x + 2,Y50-1);
          g.drawString("61.8%",faction.pressMpoint.x + 2,Y618-1);

          ChartUIObject cchart = (ChartUIObject) getLeftChart();
          if (cchart!=null)
          {
            double UpperBound = cchart.getUpperBound();
            double LowerBound = cchart.getLowerBound();
            float v100 = (float) getYValueFromScreen(MinY,UpperBound,LowerBound);
            float v618 = (float) getYValueFromScreen(Y618,UpperBound,LowerBound);
            float v500 = (float) getYValueFromScreen(Y50,UpperBound,LowerBound);
            float v382 = (float) getYValueFromScreen(Y382,UpperBound,LowerBound);
            float v000 = (float) getYValueFromScreen(MaxY,UpperBound,LowerBound);
            g.setColor(cchart.firstColor);
            if (cchart.chartType == ChartType.PERCENTAGE )
            {
              g.drawString(FFormater.formatData2(v100)+"%",faction.pressMpoint.x+40,MinY-1);
              g.drawString(FFormater.formatData2(v618)+"%",faction.pressMpoint.x+40,Y618-1);
              g.drawString(FFormater.formatData2(v500)+"%",faction.pressMpoint.x+40,Y50-1);
              g.drawString(FFormater.formatData2(v382)+"%",faction.pressMpoint.x+40,Y382-1);
              g.drawString(FFormater.formatData2(v000)+"%",faction.pressMpoint.x+40,MaxY-1);
            }
            else
            {
              g.drawString(FFormater.formatData2(v100),faction.pressMpoint.x+40,MinY-1);
              g.drawString(FFormater.formatData2(v618),faction.pressMpoint.x+40,Y618-1);
              g.drawString(FFormater.formatData2(v500),faction.pressMpoint.x+40,Y50-1);
              g.drawString(FFormater.formatData2(v382),faction.pressMpoint.x+40,Y382-1);
              g.drawString(FFormater.formatData2(v000),faction.pressMpoint.x+40,MaxY-1);
            }
          }

          cchart = (ChartUIObject) getRightChart();
          if (cchart!=null)
          {
            double UpperBound = cchart.getUpperBound();
            double LowerBound = cchart.getLowerBound();
            float v100 = (float) getYValueFromScreen(MinY,UpperBound,LowerBound);
            float v618 = (float) getYValueFromScreen(Y618,UpperBound,LowerBound);
            float v500 = (float) getYValueFromScreen(Y50,UpperBound,LowerBound);
            float v382 = (float) getYValueFromScreen(Y382,UpperBound,LowerBound);
            float v000 = (float) getYValueFromScreen(MaxY,UpperBound,LowerBound);
            g.setColor(cchart.firstColor);
            if (cchart.chartType == ChartType.PERCENTAGE )
            {
              g.drawString(FFormater.formatData2(v100)+"%",faction.pressMpoint.x+80,MinY-1);
              g.drawString(FFormater.formatData2(v618)+"%",faction.pressMpoint.x+80,Y618-1);
              g.drawString(FFormater.formatData2(v500)+"%",faction.pressMpoint.x+80,Y50-1);
              g.drawString(FFormater.formatData2(v382)+"%",faction.pressMpoint.x+80,Y382-1);
              g.drawString(FFormater.formatData2(v000)+"%",faction.pressMpoint.x+80,MaxY-1);
            }
            else
            {
              g.drawString(FFormater.formatData2(v100),faction.pressMpoint.x+80,MinY-1);
              g.drawString(FFormater.formatData2(v618),faction.pressMpoint.x+80,Y618-1);
              g.drawString(FFormater.formatData2(v500),faction.pressMpoint.x+80,Y50-1);
              g.drawString(FFormater.formatData2(v382),faction.pressMpoint.x+80,Y382-1);
              g.drawString(FFormater.formatData2(v000),faction.pressMpoint.x+80,MaxY-1);
            }
          }
       }
      break;
////////////////////////////////////////////////////////////////////////////////////////////

      }
//////plot the Axis/////////////////////////////////////////////////////////////////////////
      plotAxis(true);
//////plot Y Axis //////////////////////////////////////////////////////////////////////////
 //     System.out.println("drawCompareTable");
      drawCompareTable();
 //     System.out.println(" End drawCompareTable");
/////draw the buffered image to the screen /////////////////////////////////////////////////
      gg.drawImage(allscreenImage,0,0,getSize().width,getSize().height,this);
  }

  private void plotWatchTable(ChartUIObject cchart, int x, int y)
  {
      Graphics g = allscreenImage.getGraphics();
      if (cchart != null)
      {
        int index = getPointIndexFromScreen(faction.currentMpoint.x);
        FPoint fpoint = (FPoint) cchart.chartData.data.elementAt(index);
        FTAPoint fTApoint = null;
        if (cchart.chartData.TAdata.size() > index){
        fTApoint = (FTAPoint) cchart.chartData.TAdata.elementAt(index);
        }


        String sDate="";
        // for date

        if (cchart.chartData.dataType == ChartData.DAILY )
        {
          sDate = fpoint.Day + "-" + fpoint.Month + "-" +fpoint.Year ;
          sDate = lbArray[15][language] +": " + sDate;
        }
        else if (cchart.chartData.dataType == ChartData.WEEKLY || cchart.chartData.dataType == ChartData.MONTHLY)
        {
          sDate = fpoint.Day + "-" + fpoint.Month + "-" +fpoint.Year+ " to " +fpoint.lDay +"-"+fpoint.lMonth+"-"+fpoint.lYear;
          sDate = lbArray[15][language] +": " +sDate;
        }
        else if (cchart.chartData.dataType == ChartData.INTRADAY)
        {
          sDate = FFormater.formatTime(fpoint.Hour,fpoint.Minute);
          //sDate = fpoint.Hour +":"+ fpoint.Minute;
          sDate = lbArray[16][language] +": " + sDate;
        }

        switch (cchart.chartType)
        {
          case ChartType.PERCENTAGE:

          break;

          case ChartType.MACD:
            g.setColor(new Color(240,240,240));
            g.fill3DRect(x,y,175,50,true);
            g.setColor(cchart.firstColor);
            g.drawString("MACD1: " + FFormater.formatData3(fTApoint.MACD1) , x+1,y+12);
            g.drawString("MACD2: " +FFormater.formatData3(fTApoint.MACD2) , x+1,y+24);
            g.drawString("Diff: " + FFormater.formatData3(fTApoint.MACDdiff), x+1,y+36);
            g.drawString(sDate , x+1 , y+ 48);
          break;

          case ChartType.RSI:
            g.setColor(new Color(240,240,240));
            g.fill3DRect(x,y,175,28,true);
            g.setColor(cchart.firstColor);
            g.drawString("RSI: " + FFormater.formatData3(fTApoint.RSI) , x+1,y+12);
            //sDate = fpoint.Day + "-" + fpoint.Month + "-" +fpoint.Year ;
            g.drawString(sDate , x+1 , y+ 24);
          break;

          case ChartType.STC:
            g.setColor(new Color(240,240,240));
            g.fill3DRect(x,y,175,38,true);
            g.setColor(cchart.firstColor);
            g.drawString("%K: " + FFormater.formatData3(fTApoint.K) , x+1,y+12);
            g.drawString("%D: " + FFormater.formatData3(fTApoint.D) , x+1,y+24);

//            sDate = fpoint.Day + "-" + fpoint.Month + "-" +fpoint.Year ;
            g.drawString(sDate , x+1 , y+ 36);
          break;

          case ChartType.OBV :
            g.setColor(new Color(240,240,240));
            g.fill3DRect(x,y,175,28,true);
            g.setColor(cchart.firstColor);
            g.drawString("OBV: " + FFormater.formatData3(fTApoint.OBV), x+1,y+12);
  //          sDate = fpoint.Day + "-" + fpoint.Month + "-" +fpoint.Year ;
            g.drawString(sDate , x+1 , y+ 24);
          break;

          case ChartType.WilliamR:
            g.setColor(new Color(240,240,240));
            g.fill3DRect(x,y,175,28,true);
            g.setColor(cchart.firstColor);
            g.drawString("William %R: " + FFormater.formatData3(fTApoint.R), x+1,y+12);
            //sDate = fpoint.Day + "-" + fpoint.Month + "-" +fpoint.Year ;
            g.drawString(sDate , x+1 , y+ 24);
          break;

          case ChartType.VOLUME:
            g.setColor(new Color(240,240,240));
            g.fill3DRect(x,y,175,28,true);
            g.setColor(cchart.firstColor);
            g.drawString(lbArray[9][language]+": " + fpoint.Volume , x+1,y+12);
//            sDate = fpoint.Day + "-" + fpoint.Month + "-" +fpoint.Year ;
            g.drawString(sDate , x+1 , y+ 24);
          break;



          case ChartType.CANDLE:
          case ChartType.LINE:
          case ChartType.BAR:
            g.setColor(new Color(240,240,240));
            g.fill3DRect(x,y,175,50,true);
            g.setColor(cchart.firstColor);
            g.drawString(lbArray[5][language]+": " + FFormater.formatData3(fpoint.Open) + " "+lbArray[6][language]+": " + FFormater.formatData3(fpoint.Close) ,x+1,y+12);
            g.drawString(lbArray[7][language]+": " + FFormater.formatData3(fpoint.Maximum) + " "+lbArray[8][language]+": " + FFormater.formatData3(fpoint.Minimum),x+1,y+24);
            g.drawString(lbArray[9][language]+": " + fpoint.Volume , x+1,y+36);
//            sDate = fpoint.Day + "-" + fpoint.Month + "-" +fpoint.Year ;
            g.drawString(sDate , x+1 , y+ 48);
          break;
        }

      }

  }
  private void drawWatchAction()
  {

      int indexPoint = getPointIndexFromScreen(faction.currentMpoint.x);
      if (indexPoint < startDisplayIndex || indexPoint > endDisplayIndex) return;
      ChartUIObject leftChart = getLeftChart();
      ChartUIObject rightChart =  getRightChart();
      if (leftChart != null && leftChart.isVisable())
      {
        if (faction.currentMpoint.x > getSize().width/2)
        {
          plotWatchTable(leftChart,leftSpace + 5,topSpace);
        } else
        {
          plotWatchTable(leftChart,getSize().width - rightSpace - 170,topSpace);
        }
      }
      if (rightChart != null && rightChart.isVisable())
      {
        if (faction.currentMpoint.x > getSize().width/2)
        {
          plotWatchTable(rightChart,leftSpace + 5,topSpace + 60);
        }
        else
        {
          plotWatchTable(rightChart,getSize().width - rightSpace - 180,topSpace+60);
        }
     }
  }



  // Plot function:::
  public synchronized void updateBaseScreen()
  {
    if (this.screenImage == null) return;
    if (this.allscreenImage == null) return;
    //System.out.println("Entering updateBaseScreen");
    isUpdatingBaseScreen = true;

    try {
        //System.out.println("UpdateBaseScreen: " + startDisplayIndex  + " : " +endDisplayIndex);
        clearScreen();

        //System.out.println("updating % data");
        //update the percentage data
        for (int i=0;i<chartObjects.size();i++)
        {
          ChartUIObject currentChart = (ChartUIObject) chartObjects.elementAt(i);
          if (currentChart.isVisable() && currentChart.chartType == ChartType.PERCENTAGE)
          {
            currentChart.chartData.calculatePercentage(startDisplayIndex);
          }

           if (currentChart.isVisable() && currentChart.chartType == ChartType.OBV)
          {
            currentChart.chartData.calculateOBV(startDisplayIndex,endDisplayIndex);
          }

        }
        //System.out.println("end updating");

        FBound fb = getAxisBound(ChartType.LEFTAXIS);
        getAxisBound(ChartType.RIGHTAXIS);
        getAxisBound(ChartType.NONE);

        // plot the grid line
        plotAxis(false);
      //  System.out.println("end plotAxis");
        for (int i=0;i<chartObjects.size();i++)
        {
        //  System.out.println("loop loop , " + chartObjects.size());
          ChartUIObject currentChart = (ChartUIObject) chartObjects.elementAt(i);
          if (currentChart.isVisable())
          {
            plotChart(currentChart);
          }
        }
    }
    catch (Exception ee)
    {
      System.out.println("error when updating screen");
      ee.printStackTrace();
    }

  //   System.out.println("end lopp loop");
    isUpdatingBaseScreen = false;
  //  System.out.println("leaving updateBaseScreen");
 }

 private void plotLoading(Graphics g)
 {
    g.setColor(new Color(240,240,240));
    g.fill3DRect(getSize().width/2-40,getSize().height/2-20,80,40,true);
    g.setColor(Color.blue );
    g.drawString("Loading...",getSize().width/2-30,getSize().height/2+4);
    g.setColor(Color.black);
    g.drawLine(leftSpace,topSpace,leftSpace,topSpace+getYAxisWidth());
    g.drawLine(leftSpace,topSpace+getYAxisWidth(),leftSpace+getXAxisWidth(),topSpace+getYAxisWidth());
    g.drawLine(leftSpace+getXAxisWidth(),topSpace,leftSpace+getXAxisWidth(),topSpace+getYAxisWidth());


 }

 private void plotPercentageChart(ChartUIObject currentChart)
 {
    double Max = currentChart.chartBound.UpperPercentageBound;
    double Min = currentChart.chartBound.LowerPercentageBound;


    Graphics g = screenImage.getGraphics();
    g.setColor(currentChart.firstColor);

    FPoint fpoint1 = null;
    FPoint fpoint2 = null;
    int lastValidPoint = 0;
    for (int i=startDisplayIndex+1; i<=endDisplayIndex; i++)
    {
      //System.out.println("pp: " + i);
      fpoint1= (FPoint) currentChart.chartData.data.elementAt(i);
      //System.out.println("fpoint1: " + fpoint1.isValid);
      fpoint2 = (FPoint) currentChart.chartData.data.elementAt(i-1);
      if (fpoint2.isValid)
      {
        lastValidPoint = i-1;
      }
      else
      {
        fpoint2 = (FPoint) currentChart.chartData.data.elementAt(lastValidPoint);
      }

      //System.out.println("fpoint2: " + fpoint2.Maximum);

      if (fpoint1!=null && fpoint2!=null && fpoint1.isValid && fpoint2.isValid )
      try {
      int x1 = this.getScreenXPositionFromPoint(i);
      int x2 = getScreenXPositionFromPoint(lastValidPoint);
      int y1 = this.getScreenYPosition(fpoint1.percent,Max,Min);
      int y2 = getScreenYPosition(fpoint2.percent,Max,Min);
      //System.out.println("x1:" + x1 + " y1: "+ y1 + " x2: " + x2 + " y2: " + y2);
      g.drawLine(x1,y1,x2,y2);
      lastValidPoint = i;
      //g.drawLine(this.getScreenXPositionFromPoint(i),this.getScreenYPosition(fpoint1.Maximum,Max,Min),getScreenXPositionFromPoint(i+1),getScreenYPosition(fpoint2.Maximum,Max,Min));
      }
      catch (Exception ee)
      {

      }

    }

 }

 private void plotVolumeChart(ChartUIObject currentChart)
 {
   //System.out.println("plotVolumeChart");

   double Max = currentChart.chartBound.UpperVolumeBound;// .UpperStockBound;
   double Min = currentChart.chartBound.LowerVolumeBound;// .LowerStockBound;
   Max = Math.max(Max,1);

   Graphics g = screenImage.getGraphics();
   g.setColor(currentChart.firstColor);

    int hWidth=1;
    if (resolution >= 3 && resolution <=4)
    {
      hWidth = 1;
    }
    else if (resolution >=5 && resolution <=6)
    {
      hWidth = 2;
    }
    else if (resolution >=7 && resolution <=8)
    {
      hWidth = 3;
    }
    else if (resolution >=9 && resolution <=10)
    {
      hWidth = 4;
    }
    else if (resolution >=11)
    {
      hWidth = 5;
    }

    for (int i=startDisplayIndex; i<=endDisplayIndex; i++)
    {
      FPoint fpoint= (FPoint) currentChart.chartData.data.elementAt(i);
      g.drawRect(getScreenXPositionFromPoint(i),getScreenYPosition(fpoint.Volume,Max,Min),hWidth,getScreenYPosition(0,Max,Min)-getScreenYPosition(fpoint.Volume,Max,Min));
    }
}

 // plot the CandleChart given the chart
 private void plotCandleChart(ChartUIObject currentChart)
 {
   // System.out.print("plotCandleChart:)");
   // System.out.print(startDisplayIndex + ":" + endDisplayIndex + ":");
    double Max = currentChart.chartBound.UpperStockBound;
    double Min = currentChart.chartBound.LowerStockBound;

    Graphics g = screenImage.getGraphics();
    g.setColor(currentChart.firstColor);


    int CandleWidth=1;
    if (resolution >= 3 && resolution <=4)
    {
      CandleWidth = 1;
    }
    else if (resolution >=5 && resolution <=6)
    {
      CandleWidth = 2;
    }
    else if (resolution >=7 && resolution <=8)
    {
      CandleWidth = 3;
    }
    else if (resolution >=9 && resolution <=10)
    {
      CandleWidth = 4;
    }
    else if (resolution >=11)
    {
      CandleWidth = 5;
    }

    for (int i=startDisplayIndex; i<=endDisplayIndex; i++)
    {
      FPoint fpoint= (FPoint) currentChart.chartData.data.elementAt(i);
      if (fpoint.isValid)
      if (fpoint.Open > fpoint.Close)
      {
         g.setColor(FConfig.CandleColorDown);
         g.drawLine(this.getScreenXPositionFromPoint(i),this.getScreenYPosition(fpoint.Maximum,Max,Min),getScreenXPositionFromPoint(i),getScreenYPosition(fpoint.Open,Max,Min));
         g.fillRect(this.getScreenXPositionFromPoint(i)-CandleWidth,this.getScreenYPosition(fpoint.Open,Max,Min),CandleWidth*2+1, getScreenYPosition(fpoint.Close,Max,Min)-getScreenYPosition(fpoint.Open,Max,Min));
         g.drawLine(this.getScreenXPositionFromPoint(i),this.getScreenYPosition(fpoint.Close,Max,Min),getScreenXPositionFromPoint(i),getScreenYPosition(fpoint.Minimum,Max,Min));
         if (fpoint.Close == fpoint.Open )
         {
          g.drawLine(this.getScreenXPositionFromPoint(i)-CandleWidth,this.getScreenYPosition(fpoint.Open,Max,Min),this.getScreenXPositionFromPoint(i)+CandleWidth,getScreenYPosition(fpoint.Close,Max,Min));
         }
      }
      else
      {
         g.setColor(FConfig.CandleColorUp);
         g.drawLine(this.getScreenXPositionFromPoint(i),this.getScreenYPosition(fpoint.Maximum,Max,Min),getScreenXPositionFromPoint(i),getScreenYPosition(fpoint.Close,Max,Min));
         g.drawRect(this.getScreenXPositionFromPoint(i)-CandleWidth,this.getScreenYPosition(fpoint.Close,Max,Min),CandleWidth*2, getScreenYPosition(fpoint.Open,Max,Min)-getScreenYPosition(fpoint.Close,Max,Min));
         g.drawLine(this.getScreenXPositionFromPoint(i),this.getScreenYPosition(fpoint.Open,Max,Min),getScreenXPositionFromPoint(i),getScreenYPosition(fpoint.Minimum,Max,Min));
         if (fpoint.Close == fpoint.Open )
         {
          g.drawLine(this.getScreenXPositionFromPoint(i)-CandleWidth,this.getScreenYPosition(fpoint.Open,Max,Min),this.getScreenXPositionFromPoint(i)+CandleWidth,getScreenYPosition(fpoint.Close,Max,Min));
         }
      }
    }
 }
 private void plotBarChart(ChartUIObject currentChart)
 {

  //  System.out.print("plotLineChart:)");
   // System.out.print(startDisplayIndex + ":" + endDisplayIndex + ":--------------------------------------------------------------");
    double Max = currentChart.chartBound.UpperStockBound;
    double Min = currentChart.chartBound.LowerStockBound;

    Graphics g = screenImage.getGraphics();
    g.setColor(FConfig.BarColor);


    int BarWidth=1;
    if (resolution >= 3 && resolution <=4)
    {
      BarWidth = 1;
    }
    else if (resolution >=5 && resolution <=6)
    {
      BarWidth = 2;
    }
    else if (resolution >=7 && resolution <=8)
    {
      BarWidth = 3;
    }
    else if (resolution >=9 && resolution <=10)
    {
      BarWidth = 4;
    }
    else if (resolution >=11)
    {
      BarWidth = 5;
    }


    for (int i=startDisplayIndex; i<=endDisplayIndex; i++)
    {
      FPoint fpoint= (FPoint) currentChart.chartData.data.elementAt(i);
      if (fpoint.isValid)
      {
        g.drawLine(this.getScreenXPositionFromPoint(i),this.getScreenYPosition(fpoint.Maximum,Max,Min),getScreenXPositionFromPoint(i),getScreenYPosition(fpoint.Minimum,Max,Min));
        g.drawLine(this.getScreenXPositionFromPoint(i)-BarWidth,this.getScreenYPosition(fpoint.Open,Max,Min),getScreenXPositionFromPoint(i),getScreenYPosition(fpoint.Open,Max,Min));
        g.drawLine(this.getScreenXPositionFromPoint(i),this.getScreenYPosition(fpoint.Close,Max,Min),getScreenXPositionFromPoint(i)+BarWidth,getScreenYPosition(fpoint.Close,Max,Min));
      }

    }
 }


 private void plotBollingerBand(ChartUIObject currentChart)
 {

   // System.out.println("Line : " + startDisplayIndex + " : " + endDisplayIndex);
    double Max = currentChart.chartBound.UpperStockBound;
    double Min = currentChart.chartBound.LowerStockBound;

    int N = currentChart.chartData.fTAconfig.bbN;

 //   System.out.println(Max + " : " + Min + "MAING");

    Graphics g = screenImage.getGraphics();
    g.setColor(currentChart.firstColor);

    FTAPoint fTApoint1 = null;
    FTAPoint fTApoint2 = null;

    for (int i=startDisplayIndex+1; i<=endDisplayIndex; i++)
    {
      //System.out.println("pp: " + i);
      fTApoint1=  (FTAPoint) currentChart.chartData.TAdata.elementAt(i);
      fTApoint2 = (FTAPoint) currentChart.chartData.TAdata.elementAt(i-1);

      if (fTApoint1!=null && fTApoint2!=null && fTApoint1.isValid && fTApoint2.isValid )
      try {
      //System.out.println("pp: " + i +" ---------------------");
      int y1, y2;
      int x1 = this.getScreenXPositionFromPoint(i);
      int x2 = this.getScreenXPositionFromPoint(i-1);

      //plot UB line
      g.setColor(FConfig.BollingerBandColor);
      y1 = this.getScreenYPosition(fTApoint1.UB,Max,Min);
      y2 = getScreenYPosition(fTApoint2.UB,Max,Min);
   //   System.out.println(x1 + " : " + y1 + " : " + x2 + " : " + y2 + " plot");
      if (i > N )
      g.drawLine(x1,y1,x2,y2);

      //plot LB  line
      g.setColor(FConfig.BollingerBandColor);
      y1 = this.getScreenYPosition(fTApoint1.LB,Max,Min);
      y2 = getScreenYPosition(fTApoint2.LB,Max,Min);
      if (i > N )
      g.drawLine(x1,y1,x2,y2);

      }
      catch (Exception ee)
      {

      }

    }

 }

 private void plotOBV(ChartUIObject currentChart)
 {
    // System.out.println("Line : " + startDisplayIndex + " : " + endDisplayIndex);
    double Max = currentChart.chartBound.UpperOBVBound;
    double Min = currentChart.chartBound.LowerOBVBound;

    Graphics g = screenImage.getGraphics();
    g.setColor(currentChart.firstColor);

    FTAPoint fTApoint1 = null;
    FTAPoint fTApoint2 = null;
    int lastValidPoint = 0;

    g.setColor(FConfig.OBVColor);
    for (int i=startDisplayIndex+1; i<=endDisplayIndex; i++)
    {
      //System.out.println("pp: " + i);
      fTApoint1= (FTAPoint) currentChart.chartData.TAdata.elementAt(i);
      //System.out.println("fpoint1: " + fpoint1.isValid);
      fTApoint2 = (FTAPoint) currentChart.chartData.TAdata.elementAt(i-1);

      if (fTApoint1!=null && fTApoint2!=null && fTApoint1.isValid && fTApoint2.isValid )
      try {

      int x1 = this.getScreenXPositionFromPoint(i);
      int x2 = this.getScreenXPositionFromPoint(i-1);

      //plot OBV
      int y1 = this.getScreenYPosition(fTApoint1.OBV,Max,Min);
      int y2 = getScreenYPosition(fTApoint2.OBV,Max,Min);
      //System.out.println("x1:" + x1 + " y1: "+ y1 + " x2: " + x2 + " y2: " + y2);
      g.drawLine(x1,y1,x2,y2);

      }
      catch (Exception ee)
      {

      }
    }
 }


 private void plotMACD(ChartUIObject currentChart)
 {
    // System.out.println("Line : " + startDisplayIndex + " : " + endDisplayIndex);
    double Max = currentChart.chartBound.UpperMACDBound;
    double Min = currentChart.chartBound.LowerMACDBound;

    Graphics g = screenImage.getGraphics();
    g.setColor(currentChart.firstColor);

    FTAPoint fTApoint1 = null;
    FTAPoint fTApoint2 = null;
    int lastValidPoint = 0;
    for (int i=startDisplayIndex+1; i<=endDisplayIndex; i++)
    {
      //System.out.println("pp: " + i);
      fTApoint1= (FTAPoint) currentChart.chartData.TAdata.elementAt(i);
      //System.out.println("fpoint1: " + fpoint1.isValid);
      fTApoint2 = (FTAPoint) currentChart.chartData.TAdata.elementAt(i-1);

      if (fTApoint1!=null && fTApoint2!=null && fTApoint1.isValid && fTApoint2.isValid )
      try {

      int x1 = this.getScreenXPositionFromPoint(i);
      int x2 = this.getScreenXPositionFromPoint(i-1);

      //plot MACD1
      int y1 = this.getScreenYPosition(fTApoint1.MACD1,Max,Min);
      int y2 = getScreenYPosition(fTApoint2.MACD1,Max,Min);
      //System.out.println("x1:" + x1 + " y1: "+ y1 + " x2: " + x2 + " y2: " + y2);
      g.setColor(FConfig.MACDColor1);
      g.drawLine(x1,y1,x2,y2);

      //plot MACD2
      y1 = this.getScreenYPosition(fTApoint1.MACD2 ,Max,Min);
      y2 = getScreenYPosition(fTApoint2.MACD2 ,Max,Min);
      //System.out.println("x1:" + x1 + " y1: "+ y1 + " x2: " + x2 + " y2: " + y2);
      g.setColor(FConfig.MACDColor2);
      g.drawLine(x1,y1,x2,y2);


      y1 = this.getScreenYPosition(fTApoint1.MACDdiff, Max,Min);
      y2 = this.getScreenYPosition(0, Max,Min);
      g.setColor(FConfig.MACDColor3);
      g.drawLine(x1,y1,x1,y2);
      //g.drawLine(this.getScreenXPositionFromPoint(i),this.getScreenYPosition(fpoint1.Maximum,Max,Min),getScreenXPositionFromPoint(i+1),getScreenYPosition(fpoint2.Maximum,Max,Min));
      }
      catch (Exception ee)
      {

      }
    }
 }


 private void plotWilliamR(ChartUIObject currentChart)
 {
    // System.out.println("Line : " + startDisplayIndex + " : " + endDisplayIndex);
    double Max = currentChart.chartBound.UpperWilliamRBound;
    double Min = currentChart.chartBound.LowerWilliamRBound;

    Graphics g = screenImage.getGraphics();
    g.setColor(currentChart.firstColor);

    FTAPoint fTApoint1 = null;
    FTAPoint fTApoint2 = null;
    int lastValidPoint = 0;
    for (int i=startDisplayIndex+1; i<=endDisplayIndex; i++)
    {
      //System.out.println("pp: " + i);
      fTApoint1= (FTAPoint) currentChart.chartData.TAdata.elementAt(i);
      //System.out.println("fpoint1: " + fpoint1.isValid);
      fTApoint2 = (FTAPoint) currentChart.chartData.TAdata.elementAt(i-1);

      if (fTApoint1!=null && fTApoint2!=null && fTApoint1.isValid && fTApoint2.isValid )
      try {

      int x1 = this.getScreenXPositionFromPoint(i);
      int x2 = this.getScreenXPositionFromPoint(i-1);

      //plot %R
      int y1 = this.getScreenYPosition(fTApoint1.R,Max,Min);
      int y2 = getScreenYPosition(fTApoint2.R,Max,Min);
      //System.out.println("x1:" + x1 + " y1: "+ y1 + " x2: " + x2 + " y2: " + y2);
      g.setColor(FConfig.WilliamRColor);
      g.drawLine(x1,y1,x2,y2);
      //g.drawLine(this.getScreenXPositionFromPoint(i),this.getScreenYPosition(fpoint1.Maximum,Max,Min),getScreenXPositionFromPoint(i+1),getScreenYPosition(fpoint2.Maximum,Max,Min));
      }
      catch (Exception ee)
      {

      }
    }

    g.setColor(Color.red);
    int x1 = this.getScreenXPositionFromPoint(startDisplayIndex);
    int x2 = getScreenXPositionFromPoint(endDisplayIndex);
    int y1 = this.getScreenYPosition(50,Max,Min);
    //this.drawDotLine(g,x1,y1,x2,y1);
    g.drawLine(x1,y1,x2,y1);


 }





 private void plotSTC(ChartUIObject currentChart)
 {
    // System.out.println("Line : " + startDisplayIndex + " : " + endDisplayIndex);
    double Max = currentChart.chartBound.UpperSTCBound;
    double Min = currentChart.chartBound.LowerSTCBound;

    Graphics g = screenImage.getGraphics();
    g.setColor(currentChart.firstColor);

    FTAPoint fTApoint1 = null;
    FTAPoint fTApoint2 = null;
    int lastValidPoint = 0;
    for (int i=startDisplayIndex+1; i<=endDisplayIndex; i++)
    {
      //System.out.println("pp: " + i);
      fTApoint1= (FTAPoint) currentChart.chartData.TAdata.elementAt(i);
      //System.out.println("fpoint1: " + fpoint1.isValid);
      fTApoint2 = (FTAPoint) currentChart.chartData.TAdata.elementAt(i-1);

      if (fTApoint1!=null && fTApoint2!=null && fTApoint1.isValid && fTApoint2.isValid )
      try {

      int x1 = this.getScreenXPositionFromPoint(i);
      int x2 = this.getScreenXPositionFromPoint(i-1);

      //plot %K
      int y1 = this.getScreenYPosition(fTApoint1.K,Max,Min);
      int y2 = getScreenYPosition(fTApoint2.K,Max,Min);
      //System.out.println("x1:" + x1 + " y1: "+ y1 + " x2: " + x2 + " y2: " + y2);
      g.setColor(FConfig.STCColorK);
      g.drawLine(x1,y1,x2,y2);

      //plot %D
      y1 = this.getScreenYPosition(fTApoint1.D ,Max,Min);
      y2 = getScreenYPosition(fTApoint2.D ,Max,Min);
      //System.out.println("x1:" + x1 + " y1: "+ y1 + " x2: " + x2 + " y2: " + y2);
      g.setColor(FConfig.STCColorD);
      g.drawLine(x1,y1,x2,y2);

      //g.drawLine(this.getScreenXPositionFromPoint(i),this.getScreenYPosition(fpoint1.Maximum,Max,Min),getScreenXPositionFromPoint(i+1),getScreenYPosition(fpoint2.Maximum,Max,Min));
      }
      catch (Exception ee)
      {

      }
    }

    g.setColor(Color.red);
    int x1 = this.getScreenXPositionFromPoint(startDisplayIndex);
    int x2 = getScreenXPositionFromPoint(endDisplayIndex);
    int y1 = this.getScreenYPosition(80,Max,Min);
    int y2 = getScreenYPosition(20,Max,Min);
//    this.drawDotLine(g,x1,y1,x2,y1);
//    this.drawDotLine(g,x1,y2,x2,y2);
    g.drawLine(x1,y1,x2,y1);
    g.drawLine(x1,y2,x2,y2);

 }

 private void plotRSI(ChartUIObject currentChart)
 {
    // System.out.println("Line : " + startDisplayIndex + " : " + endDisplayIndex);
    double Max = currentChart.chartBound.UpperRSIBound;
    double Min = currentChart.chartBound.LowerRSIBound;

    Graphics g = screenImage.getGraphics();
    g.setColor(currentChart.firstColor);

    FTAPoint fTApoint1 = null;
    FTAPoint fTApoint2 = null;
    int lastValidPoint = 0;

    for (int i=startDisplayIndex+1; i<=endDisplayIndex; i++)
    {
//      System.out.println(i + " : " + startDisplayIndex + " : " + endDisplayIndex + " : " +  currentChart.chartData.TAdata.size());
      fTApoint1= (FTAPoint) currentChart.chartData.TAdata.elementAt(i);
      fTApoint2 = (FTAPoint) currentChart.chartData.TAdata.elementAt(i-1);
      if (fTApoint2.isValid){
        lastValidPoint = i-1;
      } else {
        fTApoint2 = (FTAPoint) currentChart.chartData.TAdata.elementAt(lastValidPoint);
      }
      // plot the RSI line
      if (fTApoint1!=null && fTApoint2!=null && fTApoint1.isValid && fTApoint2.isValid )
      try {
          int x1 = this.getScreenXPositionFromPoint(i);
          int x2 = getScreenXPositionFromPoint(lastValidPoint);
          int y1 = this.getScreenYPosition(fTApoint1.RSI,Max,Min);
          int y2 = getScreenYPosition(fTApoint2.RSI,Max,Min);
          g.setColor(FConfig.RSIColor);
          g.drawLine(x1,y1,x2,y2);
          lastValidPoint = i;
      }
      catch (Exception ee)
      {

      }
    }
      // plot the parellel line.
      g.setColor(Color.red);
      int x1 = this.getScreenXPositionFromPoint(startDisplayIndex);
      int x2 = getScreenXPositionFromPoint(endDisplayIndex);
      int y1 = this.getScreenYPosition(70,Max,Min);
      int y2 = getScreenYPosition(30,Max,Min);
      g.drawLine(x1,y1,x2,y1);
      g.drawLine(x1,y2,x2,y2);
      //      this.drawDotLine(g,x1,y1,x2,y1);
      //      this.drawDotLine(g,x1,y2,x2,y2);

 }

 private void plotMovingAverage(ChartUIObject currentChart)
 {

   // System.out.println("Line : " + startDisplayIndex + " : " + endDisplayIndex);
    double Max = currentChart.chartBound.UpperStockBound;
    double Min = currentChart.chartBound.LowerStockBound;
    int N1,N2,N3;

    if (currentChart.chartType == ChartType.SIMPLEMOVINGAVERAGE)
    {
      N1 = currentChart.chartData.fTAconfig.SMAN1;
      N2 = currentChart.chartData.fTAconfig.SMAN2;
      N3 = currentChart.chartData.fTAconfig.SMAN3;

    }
    else if (currentChart.chartType == ChartType.WEIGHTEDMOVINGAVERAGE )
    {
      N1 = currentChart.chartData.fTAconfig.WMAN1;
      N2 = currentChart.chartData.fTAconfig.WMAN2;
      N3 = currentChart.chartData.fTAconfig.WMAN3;
    }
    else if (currentChart.chartType == ChartType.EXPONENTIALMOVINGAVERAGE )
    {
      N1 = 0;
      N2 = 0;
      N3 = 0;
    }
    else
    {
      N1 =0;N2=0;N3=0;
    }

 //   System.out.println(Max + " : " + Min + "MAING");

    Graphics g = screenImage.getGraphics();
    g.setColor(currentChart.firstColor);

    FTAPoint fTApoint1 = null;
    FTAPoint fTApoint2 = null;

    for (int i=startDisplayIndex+1; i<=endDisplayIndex; i++)
    {
      //System.out.println("pp: " + i);
      fTApoint1=  (FTAPoint) currentChart.chartData.TAdata.elementAt(i);
      fTApoint2 = (FTAPoint) currentChart.chartData.TAdata.elementAt(i-1);


      if (fTApoint1!=null && fTApoint2!=null && fTApoint1.isValid && fTApoint2.isValid )
      try {
      //System.out.println("pp: " + i +" ---------------------");
      int y1, y2;
      int x1 = this.getScreenXPositionFromPoint(i);
      int x2 = this.getScreenXPositionFromPoint(i-1);

      //plot MA1 line
      g.setColor(FConfig.MAColor1);
      y1 = this.getScreenYPosition(fTApoint1.MA1,Max,Min);
      y2 = getScreenYPosition(fTApoint2.MA1,Max,Min);
   //   System.out.println(x1 + " : " + y1 + " : " + x2 + " : " + y2 + " plot");
      if (i > N1 )
      g.drawLine(x1,y1,x2,y2);

      //plot MA2 line
      g.setColor(FConfig.MAColor2);
      y1 = this.getScreenYPosition(fTApoint1.MA2,Max,Min);
      y2 = getScreenYPosition(fTApoint2.MA2,Max,Min);
      if (i > N2 )
      g.drawLine(x1,y1,x2,y2);

      //plot MA3 line
      g.setColor(FConfig.MAColor3);
      y1 = this.getScreenYPosition(fTApoint1.MA3,Max,Min);
      y2 = getScreenYPosition(fTApoint2.MA3,Max,Min);
      if (i > N3 )
      g.drawLine(x1,y1,x2,y2);

      }
      catch (Exception ee)
      {

      }

    }

 }
 private void plotLineChart(ChartUIObject currentChart)
 {

  // System.out.println("Line : " + startDisplayIndex + " : " + endDisplayIndex);
    double Max = currentChart.chartBound.UpperStockBound;
    double Min = currentChart.chartBound.LowerStockBound;

    Graphics g = screenImage.getGraphics();
    g.setColor(FConfig.LineColor);

    FPoint fpoint1 = null;
    FPoint fpoint2 = null;
    int lastValidPoint = 0;
    for (int i=startDisplayIndex+1; i<=endDisplayIndex; i++)
    {
      //System.out.println("pp: " + i);
      fpoint1= (FPoint) currentChart.chartData.data.elementAt(i);
      //System.out.println("fpoint1: " + fpoint1.isValid);
      fpoint2 = (FPoint) currentChart.chartData.data.elementAt(i-1);
      if (fpoint2.isValid)
      {
        lastValidPoint = i-1;
      }
      else
      {
        fpoint2 = (FPoint) currentChart.chartData.data.elementAt(lastValidPoint);
      }

      //System.out.println("fpoint2: " + fpoint2.Maximum);

      if (fpoint1!=null && fpoint2!=null && fpoint1.isValid && fpoint2.isValid )
      try {
      int x1 = this.getScreenXPositionFromPoint(i);
      int x2 = getScreenXPositionFromPoint(lastValidPoint);
      int y1 = this.getScreenYPosition(fpoint1.Close,Max,Min);
      int y2 = getScreenYPosition(fpoint2.Close,Max,Min);
      //System.out.println("x1:" + x1 + " y1: "+ y1 + " x2: " + x2 + " y2: " + y2);
      g.drawLine(x1,y1,x2,y2);
      lastValidPoint = i;
      //g.drawLine(this.getScreenXPositionFromPoint(i),this.getScreenYPosition(fpoint1.Maximum,Max,Min),getScreenXPositionFromPoint(i+1),getScreenYPosition(fpoint2.Maximum,Max,Min));
      }
      catch (Exception ee)
      {

      }

    }

 }

 // only parallel or vertical line can be plotted.
 private void drawDotLine(Graphics g, int x1, int y1, int x2, int y2)
 {
//    System.out.println("drawDowLine :" + x1 + ":"+ y1 + ":" + x2 +":" + y2);
    if (x1==x2)
    {
      int p1,p2;
      p1=Math.min(y1,y2);
      p2=Math.max(y1,y2);
      //System.out.println("drawLine :" +p1 + ":"+ p2);
      for (int i=p1;i<=p2-5;i=i+5)
      {
        //g.setColor(Color.black );
        g.drawLine(x1,i,x1,i+2);
      }
    }
    else if (y1==y2)
    {
      int p1,p2;
      p1=Math.min(x1,x2);
      p2=Math.max(x1,x2);
      for (int i=p1;i<=p2-5;i=i+5)
      {
        g.drawLine(i,y1,i+2,y1);
      }
    }
 }

 private void plotXAxis(ChartUIObject currentChart, boolean isLabel)
 {
     // System.out.println("Entering plotXAxis");
  if (gridColor==null)  gridColor = new Color(200,200,200);
  int dpoint = endDisplayIndex;// this.getMaxNumberOfDisplayPointInCurrentResolution()+startDisplayIndex;
  //System.out.println("StartDisplayIndex:" + startDisplayIndex);
  Graphics g = allscreenImage.getGraphics();
  Graphics gg = screenImage.getGraphics();

  // when the resolution is small, set the font size to some.
  if (resolution <=4) {
    g.setFont(new Font("default",0,10));
    gg.setFont(new Font("default",0,10));
  }



//  if (currentChart.chartData.dataType == ChartData.DAILY || currentChart == getLeftChart()){
  if (currentChart.isShowXaxis())
  {
    if (currentChart.chartData.dataType == ChartData.DAILY  ){
    for (int i = startDisplayIndex;i<dpoint;i++)
    {
        //System.out.println("ADFA");
        int j = Math.max(i-1,startDisplayIndex);
        FPoint fpoint = (FPoint) currentChart.chartData.data.elementAt(i);
        FPoint fpoint2 = (FPoint) currentChart.chartData.data.elementAt(j);
        if (fpoint.Month != fpoint2.Month)
        {
          if (!isLabel)
          {
            gg.setColor(gridColor);
            drawDotLine(gg,getScreenXPositionFromPoint(i),topSpace,getScreenXPositionFromPoint(i),topSpace + getYAxisWidth());
            continue;
          }

          g.setColor(Color.black);
          g.drawLine(getScreenXPositionFromPoint(i),topSpace + getYAxisWidth(),getScreenXPositionFromPoint(i),topSpace + getYAxisWidth()+2);
      //  g.setColor(currentChart.firstColor);
          String dateMY = fpoint.Month + "-" + fpoint.Year;
          if (bottomSpace >8)
          g.drawString(dateMY,getScreenXPositionFromPoint(i),topSpace+getYAxisWidth()+11);
  //      g.drawString(""+fpoint.Year,getScreenXPositionFromPoint(i),topSpace+getYAxisWidth()+20);
        }
    }
    }

    if (currentChart.chartData.dataType == ChartData.WEEKLY){
    for (int i = startDisplayIndex;i<dpoint;i++)
    {
        //System.out.println("ADFA");
        int j = Math.max(i-1,startDisplayIndex);
        FPoint fpoint = (FPoint) currentChart.chartData.data.elementAt(i);
        FPoint fpoint2 = (FPoint) currentChart.chartData.data.elementAt(j);
        if (fpoint.Month != fpoint2.Month && fpoint.Month%3==1)
        {
          if (!isLabel)
          {
            gg.setColor(gridColor);
            drawDotLine(gg,getScreenXPositionFromPoint(i),topSpace,getScreenXPositionFromPoint(i),topSpace + getYAxisWidth());
            continue;
          }

          g.setColor(Color.black);
          g.drawLine(getScreenXPositionFromPoint(i),topSpace + getYAxisWidth(),getScreenXPositionFromPoint(i),topSpace + getYAxisWidth()+4);
         // g.setColor(currentChart.firstColor);
          String dateMY = fpoint.Month + "-" + fpoint.Year;
          if (bottomSpace >8)
          g.drawString(dateMY,getScreenXPositionFromPoint(i),topSpace+getYAxisWidth()+11);

  //        g.drawString(""+fpoint.Year,getScreenXPositionFromPoint(i),topSpace+getYAxisWidth()+20);
        }
    }
    }

    if (currentChart.chartData.dataType == ChartData.MONTHLY){
    for (int i = startDisplayIndex;i<dpoint;i++)
    {
        //System.out.println("ADFA");
        int j = Math.max(i-1,startDisplayIndex);
        FPoint fpoint = (FPoint) currentChart.chartData.data.elementAt(i);
        FPoint fpoint2 = (FPoint) currentChart.chartData.data.elementAt(j);
        if (fpoint.Year != fpoint2.Year )
        {
          if (!isLabel)
          {
            gg.setColor(gridColor);
            drawDotLine(gg,getScreenXPositionFromPoint(i),topSpace,getScreenXPositionFromPoint(i),topSpace + getYAxisWidth());
            continue;
          }

          g.setColor(Color.black);
          g.drawLine(getScreenXPositionFromPoint(i),topSpace + getYAxisWidth(),getScreenXPositionFromPoint(i),topSpace + getYAxisWidth()+4);
      //    g.setColor(currentChart.firstColor);
          String dateY = String.valueOf(fpoint.Year);
          if (bottomSpace >8)
          g.drawString(dateY,getScreenXPositionFromPoint(i),topSpace+getYAxisWidth()+11);
  //      g.drawString(""+fpoint.Year,getScreenXPositionFromPoint(i),topSpace+getYAxisWidth()+20);
        }
    }
    }

    if (currentChart.chartData.dataType == ChartData.INTRADAY){
    for (int i = startDisplayIndex;i<dpoint;i++)
    {
        //System.out.println("ADFA");
        int j = Math.max(i-1,startDisplayIndex);
        FPoint fpoint = (FPoint) currentChart.chartData.data.elementAt(i);
//        FPoint fpoint2 = (FPoint) currentChart.chartData.data.elementAt(j);
        if (fpoint.Minute == 0 && fpoint.Hour!=0)
        {
          if (!isLabel)
          {
            gg.setColor(gridColor);
            drawDotLine(gg,getScreenXPositionFromPoint(i),topSpace,getScreenXPositionFromPoint(i),topSpace + getYAxisWidth());
            continue;
          }

          g.setColor(Color.black);
          g.drawLine(getScreenXPositionFromPoint(i),topSpace + getYAxisWidth(),getScreenXPositionFromPoint(i),topSpace + getYAxisWidth()+4);
         // g.setColor(currentChart.firstColor);

          String dateTime = fpoint.Hour + ":00";
          if (bottomSpace >8)
          g.drawString(dateTime,getScreenXPositionFromPoint(i),topSpace+getYAxisWidth()+11);

        }
    }
    }


  }

 }


 private void drawCompareTable()
 {
  ChartUIObject lcchart = getLeftChart();
  if (lcchart != null && lcchart.chartType == ChartType.PERCENTAGE)
  {
    //get the number of Percentage chart.....
    int count = 0;
    for (int i =0 ;i < chartObjects.size();i++)
    {
      ChartUIObject cchart = (ChartUIObject) chartObjects.elementAt(i);
      if (cchart.chartType == ChartType.PERCENTAGE )
      {
        count++;
      }
    }
    // draw the table.
    int x1 = getSize().width - rightSpace - 150;
    int y1 = topSpace + 5 ;
    int ww = 145;
    int hh = 14* count + 6;
    if (faction.currentMpoint.x >  x1 && faction.currentMpoint.x < (x1+ww) && faction.currentMpoint.y >y1 && faction.currentMpoint.y < (y1+hh))
    {
      return;
    }
    Graphics gg = allscreenImage.getGraphics();
    gg.setColor(new Color(240,240,240));
    gg.fill3DRect(x1,y1,ww, hh,true);
    int lcount =1;
    for (int i =0 ;i < chartObjects.size();i++)
    {
      ChartUIObject cchart = (ChartUIObject) chartObjects.elementAt(i);
      if (cchart.chartType == ChartType.PERCENTAGE )
      {
        gg.setColor(cchart.firstColor);
        gg.setFont(new Font("",0,10));
        String Name = cchart.chartData.EName;
        if (language == FConfig.constChinese)
        {
          Name = cchart.chartData.CName;
        }
        String slabel = FFormater.getCode(cchart.chartData.Code) + " " +  Name;
        gg.drawString(slabel,getSize().width - rightSpace - 150 + 5, topSpace + 5 + 3 + lcount*14);
        lcount ++;
      }
    }


  }

 }
 // draw the chart name .
 private void drawLabel(ChartUIObject currentChart)
 {

    Graphics g = allscreenImage.getGraphics();
    if (currentChart.axisBar == ChartType.LEFTAXIS )
    {
      if (currentChart.chartType == ChartType.BAR || currentChart.chartType == ChartType.LINE || currentChart.chartType == ChartType.CANDLE )
      {
        g.setColor(currentChart.firstColor);
        String cname="";
        if (language == FConfig.constEnglish )
        {
          cname = currentChart.chartData.EName;
        }
        else if (language == FConfig.constChinese )
        {
          cname = currentChart.chartData.CName;
        }
        g.drawString(FFormater.getCode(currentChart.chartData.Code) + " " + cname,leftSpace,18);
      }
    }
    if (currentChart.chartType == ChartType.VOLUME)
    {
        g.drawString(FFormater.getCode(currentChart.chartData.Code) + " "+lbArray[9][language] ,leftSpace,18);
    }
    else if (currentChart.chartType == ChartType.MACD )
    {
//        g.drawString(FFormater.getCode(currentChart.chartData.Code) + " "+ lbArray[20][language],leftSpace,18);
        String tempString;
        tempString = FFormater.getCode(currentChart.chartData.Code) + " "+ lbArray[20][language];
        tempString = tempString + "("+ currentChart.chartData.fTAconfig.MACDLEMA +"-"+ currentChart.chartData.fTAconfig.MACDSEMA +")";
//MACD1:EMA("+ currentChart.chartData.fTAconfig.MACDLEMA +"-"+ currentChart.chartData.fTAconfig.MACDSEMA +")";
        g.setColor(FConfig.MACDColor1);
        g.drawString(tempString ,leftSpace,18);
        tempString = "EMA("+ currentChart.chartData.fTAconfig.MACDAEMA +")";
//        tempString = "MACD2:EMA("+ currentChart.chartData.fTAconfig.MACDAEMA +")";
        g.setColor(FConfig.MACDColor2);
        g.drawString(tempString ,leftSpace + 300,18);
        tempString = "DIFF";
        g.setColor(FConfig.MACDColor3);
        g.drawString(tempString ,leftSpace + 350,18);
    }
    else if (currentChart.chartType == ChartType.EXPONENTIALMOVINGAVERAGE)
    {
        String tempString;
        g.setColor(FConfig.MAColor1);
        tempString = "EMA("+ currentChart.chartData.fTAconfig.EMA1 +")";
        g.drawString(tempString ,leftSpace + 160,18);
        tempString = "EMA("+ currentChart.chartData.fTAconfig.EMA2 +")";
        g.setColor(FConfig.MAColor2);
        g.drawString(tempString ,leftSpace + 210,18);
        tempString = "EMA("+ currentChart.chartData.fTAconfig.EMA3 +")";
        g.setColor(FConfig.MAColor3);
        g.drawString(tempString ,leftSpace + 260,18);
    }
    else if (currentChart.chartType == ChartType.SIMPLEMOVINGAVERAGE)
    {
        String tempString;
        g.setColor(FConfig.MAColor1);
        tempString = "SMA("+ currentChart.chartData.fTAconfig.SMAN1 +")";
        g.drawString(tempString ,leftSpace + 160,18);
        tempString = "SMA("+ currentChart.chartData.fTAconfig.SMAN2 +")";
        g.setColor(FConfig.MAColor2);
        g.drawString(tempString ,leftSpace + 210,18);
        tempString = "SMA("+ currentChart.chartData.fTAconfig.SMAN3 +")";
        g.setColor(FConfig.MAColor3);
        g.drawString(tempString ,leftSpace + 260,18);
    }
    else if (currentChart.chartType == ChartType.WEIGHTEDMOVINGAVERAGE)
    {
        String tempString;
        g.setColor(FConfig.MAColor1);
        tempString = "WMA("+ currentChart.chartData.fTAconfig.WMAN1 +")";
        g.drawString(tempString ,leftSpace + 160,18);
        tempString = "WMA("+ currentChart.chartData.fTAconfig.WMAN2 +")";
        g.setColor(FConfig.MAColor2);
        g.drawString(tempString ,leftSpace + 210,18);
        tempString = "WMA("+ currentChart.chartData.fTAconfig.WMAN3 +")";
        g.setColor(FConfig.MAColor3);
        g.drawString(tempString ,leftSpace + 260,18);
    }
    else if (currentChart.chartType == ChartType.BOLLINGERBAND)
    {
        String tempString;
        g.setColor(FConfig.BollingerBandColor);
        tempString = "SMA("+ currentChart.chartData.fTAconfig.bbN +")";
        tempString = tempString + "  " + lbArray[22][language] + "("+ currentChart.chartData.fTAconfig.bbDevation +")";
        g.drawString(tempString ,leftSpace + 160,18);
    }
    else if (currentChart.chartType == ChartType.RSI)
    {
        g.drawString(FFormater.getCode(currentChart.chartData.Code) + " "+lbArray[17][language],leftSpace,18);
        String tempString;
        tempString = "RSI("+ currentChart.chartData.fTAconfig.RSIPeriod +")";
        g.setColor(FConfig.RSIColor);
        g.drawString(tempString ,leftSpace + 160,18);


    }
    else if (currentChart.chartType == ChartType.OBV )
    {
        g.drawString(FFormater.getCode(currentChart.chartData.Code) + " "+lbArray[19][language],leftSpace,18);
    }
    else if (currentChart.chartType == ChartType.STC)
    {
        g.drawString(FFormater.getCode(currentChart.chartData.Code) + " "+lbArray[18][language],leftSpace,18);
        String tempString;
        tempString = "%K("+ currentChart.chartData.fTAconfig.STCKPeriod+")";
        g.setColor(FConfig.STCColorK);
        g.drawString(tempString ,leftSpace + 100,18);
        tempString = "%D("+ currentChart.chartData.fTAconfig.STCDPeriod+")";
        g.setColor(FConfig.STCColorD);
        g.drawString(tempString ,leftSpace + 150,18);
    }
    else if (currentChart.chartType == ChartType.WilliamR)
    {
        String tempString = FFormater.getCode(currentChart.chartData.Code) + " "+lbArray[21][language];
        tempString = tempString +"("+ currentChart.chartData.fTAconfig.WilliamPeriod+")";
        g.setColor(FConfig.WilliamRColor );
        g.drawString(tempString ,leftSpace,18);
    }



 }
 //plot the Left YAxis
 private synchronized void plotYAxis(ChartUIObject currentChart, boolean isLabel)
 {
 //  System.out.println("Entering plotYAxis");
    if (currentChart == null) return;
    if (currentChart.chartBound == null) return;
//    System.out.println("null");
    if (gridColor==null)  gridColor = new Color(200,200,200);
    double Max=0;
    double Min=0;

    //calculate the actual bound in the Yaxis.
    Max = currentChart.getUpperBound();
    Min = currentChart.getLowerBound();


    //System.out.println("DD : " + (Max-Min) + " : " + ((int)(Max-Min)*10)/10f);
    float div = (float)(Max-Min)/3f;
    float ddiv = div;
    if (div <1 && div >= 0.1)
    {
      int tempd = Math.round((div*10));
      ddiv = (tempd)/10f;
    //System.out.println("div: " + ddiv + " : " + div);
    }
    else if (div<0.1 && div >=0.01)
    {
      int tempd = Math.round(div*100);
      ddiv = (tempd)/100f;
      //System.out.println("div: " + ddiv + " : " + div);
    }
    else if (div<10 && div >=1)
    {
      int tempd = (int)(div);
      ddiv = tempd;
      //System.out.println("div: " + ddiv + " : " + div);
    }
    else if (div<100 && div >=10)
    {
      int tempd = (int)(div);
      ddiv = tempd;
      //System.out.println("div: " + ddiv + " : " + div);
    }
    else if (div<1000 && div >=100)
    {
      int tempd = (int)(div/10);
      tempd = (int) (tempd *10);
      ddiv = tempd;
      //System.out.println("div: " + ddiv + " : " + div);
    }
    else if (div >=1000)
    {
      int tempd = (int)(div/1000);
      tempd = (int) (tempd *1000);
      ddiv = tempd;
      //System.out.println("div: " + ddiv + " : " + div);
    }



    int tempi =  (int)((float)Min/ddiv);
    float fmin = (tempi*ddiv);
    //System.out.println("Min: " + Min + " : " + fmin + " : " + tempi);
    double sg;
    sg = (tempi)*ddiv; //the first grid line value;
  //  System.out.println("sg  : " + sg + " div :" + ddiv + " Max :"  + Max);

    //if ((Max/ddiv)>100) return;
// it is used to fix the bug that ddiv == 0, it occur where the server data error.
    if ( (ddiv < 0.0001f) || ((sg + 20*ddiv)< Max) )
    {
    //  System.out.println("PlotY error");
      return;
    }

    //if it is TA chart then 0 to 100 axis
    if (currentChart.chartType == ChartType.RSI || currentChart.chartType == ChartType.STC || currentChart.chartType == ChartType.WilliamR)
    {
      sg = 0.0f;
      ddiv = 20f;
    }

    Graphics g = allscreenImage.getGraphics();
    Graphics gg = screenImage.getGraphics();

    // if the screen is small, reduce the font size
    if (this.getSize().height < 150)
    {
      g.setFont(new Font("",0,10));
      gg.setFont(new Font("",0,10));
    }
    else
    {
      g.setFont(new Font("",0,12));
      gg.setFont(new Font("",0,12));

    }


    if (currentChart.axisBar == ChartType.LEFTAXIS )
    {
      for (;sg<=Max;sg=sg+ddiv)
      {
        //System.out.println("sg  : " + sg + "******************************************************");
        int ypos = getScreenYPosition(sg,Max,Min);
        if (sg < Min || sg > Max) continue;
  //    g.setPaintMode();
        if (!isLabel)
        {
          gg.setColor(gridColor);
          drawDotLine(gg,leftSpace,ypos,leftSpace+getXAxisWidth(),ypos);
          continue;
        }

        g.setColor(Color.black);
        g.drawLine(leftSpace,ypos,leftSpace-3,ypos);
        g.setColor(currentChart.firstColor);
        String ss;
        if (currentChart.chartType  == ChartType.VOLUME )
        {
          ss = FFormater.formatInteger(sg);
        }
        else if (currentChart.chartType == ChartType.PERCENTAGE )
        {
          ss = FFormater.formatInteger(sg) + "%";
        }
        else if (currentChart.chartType == ChartType.RSI || currentChart.chartType == ChartType.STC || currentChart.chartType == ChartType.WilliamR)
        {
          ss = String.valueOf((int)(sg+0.5));
        }
        else if (currentChart.chartType == ChartType.OBV)
        {
          ss = FFormater.formatOBV(sg);
        }
        else
        {
          if (sg >1000 ){
            //ss = String.valueOf((int)(sg+0.5));
            ss = FFormater.formatInteger(sg+0.5);

          }
          else {
            ss = FFormater.formatData2(sg);}
       }
       g.setColor(Color.black);
       g.drawString(ss,2,ypos+3);
       }
    }
    else if (currentChart.axisBar == ChartType.RIGHTAXIS )
    {
      for (;sg<Max;sg=sg+ddiv)
      {
        if (sg < Min || sg > Max) continue;
        int ypos = getScreenYPosition(sg,Max,Min);
        g.setColor(Color.black);
        g.drawLine(leftSpace+getXAxisWidth(),ypos,leftSpace+getXAxisWidth()+3,ypos);
        String ss = FFormater.formatData2(sg);
        g.setColor(currentChart.firstColor);
        g.drawString(ss,leftSpace+getXAxisWidth()+6,ypos+3);
     }
   }
  // System.out.println("Leaving plotYAxis");
}

 // switch to different type of chart to plot
 private  void plotChart(ChartUIObject currentChart)
 {
   // System.out.println("Chart Type: &&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&77");
  // System.out.println(currentChart.chartType);
    switch (currentChart.chartType)
    {
      case ChartType.LINE:
        plotLineChart(currentChart);
      break;

      case ChartType.BAR:
        plotBarChart(currentChart);
      break;

      case ChartType.CANDLE:
        plotCandleChart(currentChart);

      break;

      case ChartType.VOLUME:
        plotVolumeChart(currentChart);
      break;

      case ChartType.PERCENTAGE:
        //System.out.println("plot %");
        plotPercentageChart(currentChart);
      break;

      case ChartType.SIMPLEMOVINGAVERAGE:
        //System.out.println("ploting MOVINGAVERGAE");
        plotMovingAverage(currentChart);
      break;

      case ChartType.WEIGHTEDMOVINGAVERAGE:
        //System.out.println("ploting MOVINGAVERGAE");
        plotMovingAverage(currentChart);
      break;

      case ChartType.EXPONENTIALMOVINGAVERAGE:
        plotMovingAverage(currentChart);
      break;

      case ChartType.BOLLINGERBAND:
        plotBollingerBand(currentChart);
      break;

      case ChartType.RSI:
        plotRSI(currentChart);
      break;

      case ChartType.MACD:
        plotMACD(currentChart);
      break;

      case ChartType.WilliamR:
        plotWilliamR(currentChart);
      break;

      case ChartType.OBV:
        plotOBV(currentChart);
      break;

      case ChartType.STC:
        plotSTC(currentChart);
      break;


    }

  }

//clear the Screen
  private boolean clearScreen()
  {
   //System.out.println("Entering ClearScreen");
    Graphics screenG = screenImage.getGraphics();
    screenG.setColor(Color.white);
    screenG.fillRect(0,0,getSize().width,getSize().height);
    return true;
  }

//remove all charts.
  public void removeAllCharts()
  {
    //System.out.println("Entering removeAllCharts");
    this.chartObjects.removeAllElements();
    updateBaseScreen();
  }

  //plot the Axis
  private  synchronized boolean plotAxis(boolean isLabel)
  {
  //System.out.println("Entering plotAxis");
  /*
  if (isLabel || !isLabel)
  {
  return true;
  }
  */
    Graphics screenG = allscreenImage.getGraphics();
    screenG.setColor(Color.black);
    screenG.drawLine(leftSpace,topSpace,leftSpace,topSpace+getYAxisWidth());
    screenG.drawLine(leftSpace,topSpace+getYAxisWidth(),leftSpace+getXAxisWidth(),topSpace+getYAxisWidth());
    screenG.drawLine(leftSpace+getXAxisWidth(),topSpace,leftSpace+getXAxisWidth(),topSpace+getYAxisWidth());
    screenG.setColor(this.backgroundColor);
    screenG.fillRect(0,0,leftSpace-1,getSize().height);
    screenG.fillRect(0,0,getSize().width ,topSpace-1);
    screenG.fillRect(getSize().width - rightSpace +1,0,rightSpace-1,getSize().height);
    screenG.fillRect(0,getSize().height-bottomSpace+1,getSize().width, bottomSpace);

    try {
    for (int i=0;i<chartObjects.size();i++)
    {
      ChartUIObject currentChart = (ChartUIObject) chartObjects.elementAt(i);
      //System.out.println("chartID: " + i);
      if (currentChart.isVisable())
      {
        plotXAxis(currentChart,isLabel);
        //System.out.println("plot X ok");
        plotYAxis(currentChart,isLabel);
       // System.out.println("plot Y ok");
        drawLabel(currentChart);
        //System.out.println("D");
      }
    }
    }
    catch (Exception e)
    {
       e.printStackTrace();
    }
    return true;
  }


  //get the Maximum Number of point that the screen can display.
  private int getMaxNumberOfDisplayPoint()
  {
    //because minimum pixels per point.
    return (getXAxisWidth()-2)/minResolution-2;
  }

  //get the Maximum Number of display point that the screen can display in the current resoluation
  private int getMaxNumberOfDisplayPointInCurrentResolution()
  {
    return (getXAxisWidth()-2)/resolution;
  }

  // retrieve the (central) point position in the screen
  public int getScreenXPositionFromPoint(int pointIndex)
  {
 //   System.out.println("Enter XX");
    int pos= leftSpace + 1 + (pointIndex-startDisplayIndex)*resolution + resolution/2;
//  System.out.println("Leave XX " + pos);
    return pos;
  }



  // retrieve index from screenPos (Xaxis)
  public int getPointIndexFromScreen(int screenPos)
  {
   // System.out.println("Enter YY");
    int pos = startDisplayIndex + (screenPos - leftSpace -1)/resolution;

   // System.out.println("Leave YY " + pos);
      return pos;
  }

  // retrieve the Abract Y position given the value and the range.....
  public int getScreenYPosition(double value, double MaxValue, double MinValue)
  {
    MinValue = MinValue - 0.0001f;

  //  System.out.println("y :"+ value +" Max : " + MaxValue + " " + MinValue);
    double dpos = (double)getYAxisWidth()-((double)(value-MinValue)/((double)MaxValue-MinValue)*getYAxisWidth())+topSpace;
  //  System.out.println("dpos: " + dpos);
    int pos = (int) Math.round(dpos);
    return pos;
  }

  public double getYValueFromScreen(int y, double MaxValue, double MinValue)
  {
 //   System.out.println("y :"+ y +" Max : " + MaxValue + " " + MinValue);
    double dValue =  ((double)(getYAxisWidth()-y+topSpace)/getYAxisWidth())*(MaxValue-MinValue)+MinValue;
    return dValue;
  }

  public void setSpace(int ileftSpace,int irightSpace,int itopSpace,int ibottomSpace)
  {
    leftSpace = ileftSpace;
    rightSpace = irightSpace;
    topSpace = itopSpace;
    bottomSpace = ibottomSpace;
  }


  //// The response of mouse action //////////////////////////////////////////////
  public void mouseDragged(MouseEvent e)
  {

     //if (faction.currentActionType != FAction.INSERTLINE && faction.currentActionType != FAction.ZOOMIN  )

     if (faction.currentActionType == FAction.WATCH || faction.currentActionType ==FAction.MOVECHART)
     if (!isWithinChartRegion(e.getX(),e.getY()))
     {
      faction.actionProcessing = false;
      repaint();
      return;
     }


    switch (faction.currentActionType)
    {
      case FAction.ZOOMIN:
       faction.currentMpoint.x = e.getPoint().x;
       faction.currentMpoint.y = e.getPoint().y;
       repaint();
      break;

      case FAction.INSERTLINE:
      // System.out.println("L MouseDragged");
       faction.currentMpoint.x = e.getPoint().x;
       faction.currentMpoint.y = e.getPoint().y;
       repaint();
      break;

     case FAction.GOLDENPARTITION:
     //  System.out.println("G MouseDragged");
       faction.currentMpoint.x = e.getPoint().x;
       faction.currentMpoint.y = e.getPoint().y;
       repaint();
      break;

     case FAction.MOVECHART:
      if (e.getPoint().x > faction.currentMpoint.x+5)
      {
  //      System.out.println("Move Left");
        this.moveLeft();
        faction.currentMpoint.x = e.getPoint().x;
        faction.currentMpoint.y = e.getPoint().y;
      }
      else if (e.getPoint().x < faction.currentMpoint.x-5)
      {
   //     System.out.println("Move Right");
        this.moveRight();
        faction.currentMpoint.x = e.getPoint().x;
        faction.currentMpoint.y = e.getPoint().y;
      }
     break;

    }

 }

  public void mouseMoved(MouseEvent e)
  {
   if (!isWithinChartRegion(e.getX(),e.getY()))
   {
      faction.actionProcessing = false;

   }
//save the current Mouse position in the faction object
     faction.currentMpoint.x = e.getX();
     faction.currentMpoint.y = e.getY();

     switch (faction.currentActionType)
     {
      case FAction.INSERTPARALLELLINE:
        if (faction.lineRecords.size() == 0)
        {
          faction.actionProcessing = false;
        }
        else
        {
          faction.actionProcessing = true;
          repaint();
        }
      break;

      case FAction.WATCH:
        faction.actionProcessing = true;
        repaint();
      break;

      case FAction.NONEACTION:
        faction.actionProcessing = false;
        repaint();
      break;


      default:
        //System.out.println("Default");
        faction.actionProcessing = false;
      break;
      }

  }

  public void mousePressed(MouseEvent e)
  {
    //System.out.println("mousePressed");


    if (!isWithinChartRegion(e.getX(),e.getY()))
    {
      faction.actionProcessing = false;
      return;
    }

    switch (faction.currentActionType)
    {
      case FAction.ZOOMIN:
        faction.actionProcessing = true;
        faction.pressMpoint.x = e.getPoint().x;
        faction.pressMpoint.y = e.getPoint().y;
      break;

      case FAction.INSERTLINE:
        faction.actionProcessing = true;
        faction.pressMpoint.x = e.getPoint().x;
        faction.pressMpoint.y = e.getPoint().y;
      break;

      case FAction.GOLDENPARTITION:
        faction.actionProcessing = true;
        faction.pressMpoint.x = e.getPoint().x;
        faction.pressMpoint.y = e.getPoint().y;
      break;

      case FAction.MOVECHART:
        faction.actionProcessing = true;
        faction.pressMpoint.x = e.getPoint().x;
        faction.pressMpoint.y = e.getPoint().y;
        faction.currentMpoint.x = e.getPoint().x;
        faction.currentMpoint.y = e.getPoint().y;
      break;


    }

  }


  public void mouseReleased(MouseEvent e)
  {
  // System.out.println("mouseReleased");
   if (faction.currentActionType == FAction.WATCH || faction.currentActionType ==FAction.MOVECHART)
   if (!isWithinChartRegion(e.getX(),e.getY()))
   {
      faction.actionProcessing = false;
      return;
   }
    FLine fline;
    switch (faction.currentActionType)
    {

///// Handle Zoom in action ///////////////////////////////////////////////////////////////////
      case FAction.ZOOMIN:
       //faction.mouseFlag = false;
       faction.actionProcessing = false;
       faction.releaseMpoint.x = e.getPoint().x;
       faction.releaseMpoint.y = e.getPoint().y;
       int index1 = getPointIndexFromScreen(faction.releaseMpoint.x);
       int index2 = getPointIndexFromScreen(faction.pressMpoint.x);

       if (Math.abs(index1-index2)>5)
       {
         int startIndex = Math.min(index1,index2);
         int endIndex = Math.max(index1,index2);
         if (endIndex > endDisplayIndex){
          endIndex  = endDisplayIndex ;
         }
         if (startIndex < startDisplayIndex){
          startIndex = startDisplayIndex ;
         }
         //record the previous position;
         faction.zoomRecords.addElement(new Point(startDisplayIndex,endDisplayIndex));
         //change the zoom
         zoom(startIndex,endIndex);
       }
       else
       {
        repaint();
       }
       break;

///// Handle insert line action //////////////////////////////////////////////////////////////////
     case FAction.INSERTLINE:
      faction.actionProcessing = false;
      faction.releaseMpoint.x = e.getPoint().x;
      faction.releaseMpoint.y = e.getPoint().y;
      if (FLine.isFixedLine)
      {
        fline = new FLine(faction.pressMpoint.x,faction.pressMpoint.y,faction.releaseMpoint.x,faction.releaseMpoint.y );
        faction.lineRecords.addElement(fline);
      }
      else
      {
        int x1 = getPointIndexFromScreen(faction.pressMpoint.x);
        int x2 = getPointIndexFromScreen(faction.releaseMpoint.x);
        float y1,y2;
        ChartUIObject cchart = this.getLeftChart();
        if (cchart!=null)
        {
          float Min = (float) cchart.getLowerBound();
          float Max = (float) cchart.getUpperBound();
          y1 = (float) getYValueFromScreen(faction.pressMpoint.y,Max, Min);
          y2 = (float) getYValueFromScreen(faction.releaseMpoint.y,Max,Min);
          fline = new FLine(x1,y1,x2,y2);
          fline.point1.x = faction.pressMpoint.x;
          fline.point1.y = faction.pressMpoint.y;
          fline.point2.x = faction.releaseMpoint.x;
          fline.point2.y = faction.releaseMpoint.y;
         // System.out.println("y1: " + fline.point1.y + " y2: " + fline.point2.y   );
          faction.lineRecords.addElement(fline);
        }
       }
     repaint();
     break;

     case FAction.GOLDENPARTITION:
      faction.actionProcessing = false;
      faction.releaseMpoint.x = e.getPoint().x;
      faction.releaseMpoint.y = e.getPoint().y;

      if (FLine.isFixedLine)
      {
        fline = new FLine(faction.pressMpoint.x,faction.pressMpoint.y,faction.releaseMpoint.x,faction.releaseMpoint.y );
        faction.goldenPartitionLine = fline;
      }
      else
      {
        int x1 = getPointIndexFromScreen(faction.pressMpoint.x);
        int x2 = getPointIndexFromScreen(faction.releaseMpoint.x);
        float y1,y2;
        ChartUIObject cchart = this.getLeftChart();
        if (cchart!=null)
        {
          float Min = (float) cchart.getLowerBound();
          float Max = (float) cchart.getUpperBound();
          y1 = (float) getYValueFromScreen(faction.pressMpoint.y,Max, Min);
          y2 = (float) getYValueFromScreen(faction.releaseMpoint.y,Max,Min);
          fline = new FLine(x1,y1,x2,y2);
          fline.point1.x = faction.pressMpoint.x;
          fline.point1.y = faction.pressMpoint.y;
          fline.point2.x = faction.releaseMpoint.x;
          fline.point2.y = faction.releaseMpoint.y;
         // System.out.println("y1: " + fline.point1.y + " y2: " + fline.point2.y   );
          faction.goldenPartitionLine = fline;
//        faction.lineRecords.addElement(fline);
        }
       }

      if (Math.abs(faction.pressMpoint.y-faction.releaseMpoint.y) <5)
      {
        faction.goldenPartitionLine = null;
      }

     repaint();
      /*
      fline = new FLine(faction.pressMpoint.x,faction.pressMpoint.y,faction.releaseMpoint.x,faction.releaseMpoint.y );
      if (Math.abs(faction.pressMpoint.y-faction.releaseMpoint.y) <5)
      {
        faction.goldenPartitionLine = null;
      } else
      {
        faction.goldenPartitionLine = fline;
      }
      //System.out.println("OK");

      repaint();
      */
     break;

// handle move chart action -- disable it when release the button .//////////////////////////////
     case FAction.MOVECHART:
      faction.actionProcessing = false;
     break;

    }
  }

  public void mouseEntered(MouseEvent e)
  {
  //  System.out.println("MouseEnter");
  repaint();
  }

  public void mouseExited(MouseEvent e)
  {
   // System.out.println("mouseExited");
    switch (faction.currentActionType)
    {

// still perform the action.
      case FAction.GOLDENPARTITION:
      case FAction.INSERTPARALLELLINE:
      case FAction.INSERTLINE:
      case FAction.ZOOMIN:
      break;

// cancel the action.
      default:
        faction.actionProcessing = false;
        repaint();
      break;
    }
  }

  public void mouseClicked(MouseEvent e)
  {
    //System.out.println("Mouse paramString: " + e.paramString());
//    System.out.println("Mouse  BM3     " + e.mo.BUTTON3_MASK);



    //System.out.println("mouseClick");
    if (!isWithinChartRegion(e.getX(),e.getY()))
    {
      faction.actionProcessing = false;
      return;
    }
    switch (faction.currentActionType)
    {
      case FAction.ZOOMIN:
        String mparamString = e.paramString();
        //if right click mouse
        if (mparamString.indexOf("mods=4")>0)
        {
          this.undoZoom();
        }
      break;

//// when mouse click insert a parallel line into action.lineRecords.///////////////////////////////////
      case FAction.INSERTPARALLELLINE:
      if (!isWithinChartRegion(e.getX(),e.getY())) return;

        FLine fline = (FLine) faction.lineRecords.lastElement();
        Point rpoint = new Point(0,0);
        if (fline.point1.x<fline.point2.x)
        {
          rpoint.x = fline.point1.x;
          rpoint.y = fline.point1.y;
        }
        else
        {
          rpoint.x = fline.point2.x;
          rpoint.y = fline.point2.y;
        }
          int dx = + faction.currentMpoint.x -rpoint.x ;
          int dy =  + faction.currentMpoint.y -rpoint.y;
          if (FLine.isFixedLine)
          {
            FLine newFline = new FLine(fline.point1.x+dx,fline.point1.y+dy, fline.point2.x+dx,fline.point2.y+dy);
            faction.lineRecords.addElement(newFline);
          }
          else
          {

            int x1 = getPointIndexFromScreen(fline.point1.x+dx);
            int x2 = getPointIndexFromScreen(fline.point2.x+dx);
            float y1,y2;
            ChartUIObject cchart = this.getLeftChart();
            if (cchart!=null)
            {
              float Min = (float) cchart.getLowerBound();
              float Max = (float) cchart.getUpperBound();
              y1 = (float) getYValueFromScreen(fline.point1.y+dy,Max, Min);
              y2 = (float) getYValueFromScreen(fline.point2.y+dy,Max,Min);
              FLine newfline = new FLine(x1,y1,x2,y2);
              newfline.point1.x = fline.point1.x+dx;
              newfline.point1.y = fline.point1.y+dy;
              newfline.point2.x = fline.point2.x+dx;
              newfline.point2.y = fline.point2.y+dy;
              faction.lineRecords.addElement(newfline);
            }

          }
          repaint();
        break;
    }
  }


  //  End: response of mouse action  .............................................

  //move chart to left
  public void moveLeft()
  {
    if ( (startDisplayIndex -1)>=0)
    {
      int startIndex = startDisplayIndex -1;
      int endIndex = endDisplayIndex -1;
      zoom(startIndex, endIndex);
      this.updateBaseScreen();
      this.repaint();
    }
  }

  //move chart to right
  public void moveRight()
  {
    //ChartUIObject cchart = getLeftChart();
    if ( (endDisplayIndex +1)<maxNumberOfChartPoint)
//    if ( (endDisplayIndex +1)<getMaxNumberOfPoint())
    {
      int startIndex = startDisplayIndex +1;
      int endIndex = endDisplayIndex +1;
      zoom(startIndex, endIndex);
      this.updateBaseScreen();
      this.repaint();
    }
  }

  public ChartUIObject getLeftChart()
  {
    for (int i=0;i<chartObjects.size();i++)
    {
      ChartUIObject cchart = (ChartUIObject) chartObjects.elementAt(i);
      if (cchart.axisBar == ChartType.LEFTAXIS )
      {
        return cchart;
      }
    }
    return null;
  }

  public ChartUIObject getRightChart()
  {
    for (int i=0;i<chartObjects.size();i++)
    {
      ChartUIObject cchart = (ChartUIObject) chartObjects.elementAt(i);
      if (cchart.axisBar == ChartType.RIGHTAXIS )
      {
        return cchart;
      }
    }
    return null;
  }

  public void undoInsertLine()
  {
    if (faction.lineRecords.size()>0)
    {
      FLine fline = (FLine) faction.lineRecords.lastElement();
      faction.lineRecords.removeElement(fline);
    }
    repaint();

  }

  //undo Zoom
  public Point undoZoom()
  {
    int endIndex = maxNumberOfChartPoint-1;
    int startIndex = Math.max(0,endIndex - getMaxNumberOfDisplayPoint());
//    System.out.println(startIndex + " : " + endIndex);
    //ozoom.x means start index, ozoom.y means endindex.
//    Point ozoom = new Point(startIndex,endIndex);
    Point ozoom = new Point(startIndex,endIndex);
    if (faction.zoomRecords.size()>0)
    {
      ozoom = (Point) faction.zoomRecords.lastElement();
      faction.zoomRecords.removeElement(ozoom);
      zoom(ozoom.x,ozoom.y);
//      repaint();
      return ozoom;
    }
    else if (faction.zoomRecords.size()==0)
    {
      //System.out.println("realMax: " + realMax );
      zoom(ozoom.x,ozoom.y);
//      repaint();
      return ozoom;
    }
    return ozoom;
  }

  public void addScreenActionListen(ScreenActionListener ss)
  {
    screenActionListener = ss;
  }

}