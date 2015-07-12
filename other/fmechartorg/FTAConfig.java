package fmechart;

/**
 * Title:        FME Chart Project for E-finet
 * Description:
 * Copyright:    Copyright (c)
 * Company:
 * @author
 * @version 1.0
 */

public class FTAConfig
{
// the config setting of TA Chart ......
// for simple moving average..........
  public int SMAN1 = 10;
  public int SMAN2 = 20;
  public int SMAN3 = 30;

// for Weighted Moving Average
  public int WMAN1 = 10;
  public int WMAN2 = 20;
  public int WMAN3 = 30;

// for EMA
  public int EMA1 = 10;
  public int EMA2 = 20;
  public int EMA3 = 30;

// for Bolinger's Band
  public int bbN = 20;
  public float bbDevation = 2.0f;


// for RSI
  public int RSIPeriod = 14;

// for STC
  public int STCKPeriod = 9;
  public int STCDPeriod = 3;

// for William's %R
  public int WilliamPeriod = 14;

// for MACD
  public int MACDLEMA = 26;
  public int MACDSEMA = 12;
  public int MACDAEMA = 9;


}