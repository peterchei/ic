package fmechart;

/**
 * Title:        FME Chart Project for E-finet
 * Description:
 * Copyright:    Copyright (c)
 * Company:
 * @author
 * @version 1.0
 */

class FTAPoint
{
  boolean isValid =  false;
// for SMA and WMA, EMA
  float MA1=0f;
  float MA2=0f;
  float MA3=0f;

// for EMA
//  float EMA = 0f;

// for Bollinger's Band
  float UB = 0f;
  float LB = 0f;

// for RSI
  float RSI = 0f;

// for STC
  float K = 0f;
  float D = 0f;

// for William's %R
  float R = 0f;

// for OBV
  double OBV = 0f; //in K unit

// for MACD
  float MACD1 =0f;
  float MACD2 = 0f;
  float MACDdiff = 0f;

// for RMI
  float RMI = 0f;

}