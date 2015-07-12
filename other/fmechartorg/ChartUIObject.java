/**
 * Title:        FME Chart Project for E-finet<p>
 * Description:  it contain one chart Data component and UI component information.
 *  Copyright:    Copyright (c) <p>
 * Company:      <p>
 * @author
 * @version 1.0
 */
package fmechart;

import java.awt.*;
import java.util.Vector;

public class ChartUIObject
{
//constant to specify the type of chart
  public static final int BAR    = 0;
  public static final int CANDLE = 1;
  public static final int LINE   = 2;
  public static final int VOLUME = 3;
  public static final int PERCENTAGE = 4;
//  public static final int MOVINGAVERAGE = 5;
  public static final int SIMPLEMOVINGAVERAGE = 5;
  public static final int WEIGHTEDMOVINGAVERAGE = 6;
  public static final int EXPONENTIALMOVINGAVERAGE = 7;
  public static final int BOLLINGERBAND = 8;

  public static final int RSI = 9;
  public static final int STC = 10;
  public static final int OBV = 11;
  public static final int MACD = 12;
  public static final int WilliamR = 13;

//constant to specify the Axis need to plot or not
  public static final int NONE =0;
  public static final int LEFTAXIS = 1;
  public static final int RIGHTAXIS = 2;

//the Y Axis
  public int axisBar=NONE;

//the UI type of chart
  public int chartType = LINE;

  public ChartData chartData = null;
  public Color firstColor = Color.blue;
  public Color secondColor = Color.red;
  public Color thirdColor = Color.green;







//the key of the chart, it must be an unique ID in the screen.
  public String idKey = "";
  private boolean visable = true;
  private boolean showXaxis = true;


  //the upper bound and lower bound of the bound, this variable is set in the setLeftBound and setRightBound function in the ChartScreen class.
  FBound chartBound;
  //public double upperBound=0.0f;
  //public double lowerBound=10000.0f;
  public double getLowerBound()
  {
    if (chartBound!=null)
    switch (chartType)
    {
      case ChartUIObject.BAR:
      return chartBound.LowerStockBound;
      case ChartUIObject.CANDLE:
      return chartBound.LowerStockBound;
      case ChartUIObject.LINE:
      return chartBound.LowerStockBound;
      case ChartUIObject.VOLUME:
      return chartBound.LowerVolumeBound;
      case ChartUIObject.PERCENTAGE:
      return chartBound.LowerPercentageBound;
      case ChartUIObject.SIMPLEMOVINGAVERAGE:
      return chartBound.LowerStockBound;
      case ChartUIObject.WEIGHTEDMOVINGAVERAGE:
      return chartBound.LowerStockBound;
      case ChartUIObject.EXPONENTIALMOVINGAVERAGE:
      return chartBound.LowerStockBound;
      case ChartUIObject.BOLLINGERBAND:
      return chartBound.LowerStockBound;
      case ChartUIObject.RSI:
      return chartBound.LowerRSIBound;
      case ChartUIObject.MACD:
      return chartBound.LowerMACDBound;
      case ChartUIObject.OBV:
      return chartBound.LowerOBVBound;
      case ChartUIObject.WilliamR:
      return chartBound.LowerWilliamRBound;
      case ChartUIObject.STC:
      return chartBound.LowerSTCBound;


    }
    return 0f;
  }
  public double getUpperBound()
  {

    if (chartBound!=null)
    switch (chartType)
    {
      case ChartUIObject.BAR:
      return chartBound.UpperStockBound;
      case ChartUIObject.CANDLE:
      return chartBound.UpperStockBound;
      case ChartUIObject.LINE:
      return chartBound.UpperStockBound;
      case ChartUIObject.VOLUME:
      return chartBound.UpperVolumeBound;
      case ChartUIObject.PERCENTAGE:
      return chartBound.UpperPercentageBound;
      case ChartUIObject.SIMPLEMOVINGAVERAGE:
      return chartBound.UpperStockBound;
      case ChartUIObject.WEIGHTEDMOVINGAVERAGE:
      return chartBound.UpperStockBound;
      case ChartUIObject.EXPONENTIALMOVINGAVERAGE:
      return chartBound.UpperStockBound;
      case ChartUIObject.BOLLINGERBAND:
      return chartBound.UpperStockBound;
      case ChartUIObject.RSI:
      return chartBound.UpperRSIBound;
      case ChartUIObject.MACD:
      return chartBound.UpperMACDBound;
      case ChartUIObject.OBV:
      return chartBound.UpperOBVBound;
      case ChartUIObject.WilliamR:
      return chartBound.UpperWilliamRBound;
      case ChartUIObject.STC:
      return chartBound.UpperSTCBound;



    }
    return 0f;
  }

  public boolean isShowXaxis()
  {
    return showXaxis;
  }
  public void setShowXaxis(boolean ss)
  {
    showXaxis = ss;
  }
  public void setVisible(boolean isV)
  {
    visable = isV;
  }

  public boolean isVisable()
  {
    return visable;
  }

  public ChartUIObject(ChartData newChartData,String key)
  {
    chartData = newChartData;
    idKey = key;
  }






}