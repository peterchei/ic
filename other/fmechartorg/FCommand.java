
/**
 * Title:        FME Chart Project for E-finet<p>
 * Description:  <p>
 * Copyright:    Copyright (c) <p>
 * Company:      <p>
 * @author
 * @version 1.0
 */
package fmechart;

// the command for the engine to download data,
public class FCommand
{

  public static final int DAILY = 0;
  public static final int WEEKLY = 1;
  public static final int MONTHLY = 2;
  public static final int INTRADAILY = 3;

  public static final int TYPE_DOWNLOAD_RIGHT_CHART = 0;
  public static final int TYPE_DOWNLOAD_LEFT_CHART = 2;
  public static final int TYPE_DOWNLOAD_TA_CHART = 3;


  int actionType = TYPE_DOWNLOAD_RIGHT_CHART; // the action of the FCommand
  int dType = DAILY;     //the type of the chart
  String sKey = "RMain1";       //the key of the chart used to id the chart.
  int Code;                     //the code to download
  int numberOfPoint = 250;      //Number of point to download
  int intradayIntervals = 1;    //for intraday only
  boolean isFillEmptyPoints = false;

  ChartSourceListener reference; // the reference to the object that create this command.

  public FCommand(int sCode, int atype, int type, String key, int num, int intervals, boolean fillEmptyPoints,  ChartSourceListener re)
  {
    Code = sCode;
    actionType = atype;
    dType = type;
    sKey = key;
    numberOfPoint = num;
    intradayIntervals = intervals;
    reference = re;
    isFillEmptyPoints = fillEmptyPoints;
  }
}