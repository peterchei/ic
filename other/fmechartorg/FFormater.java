package fmechart;

import java.math.*;
import java.lang.*;

public class FFormater
{

//return 0001 given 1
  public static String getCode(int code)
  {
    String cc = "0000" + code;
    cc = cc.substring(cc.length()-4);
    return cc;
  }

  public static int getHour(String dateTime)
  {
    int a = dateTime.indexOf(":");
    String tempString = dateTime.substring(0,a);
    int Hour = Integer.parseInt(tempString);
    return Hour;
  }

  public static int getMinute(String dateTime)
  {
    int a = dateTime.indexOf(":");
    String tempString  = dateTime.substring(a+1,a+3);
    int Minute = Integer.parseInt(tempString);
    //System.out.println("Minute:" + Minute);
    return Minute;
  }
  public static int getYear(String date)
  {
    int a = date.indexOf("-");
    int b = date.lastIndexOf("-");
    String tempString = date.substring(0,a);
//    System.out.println("Year: " + tempString);
    int Year = Integer.parseInt(tempString);
    return Year;
  }
  public static int getMonth(String date)
  {
    int a = date.indexOf("-");
    int b = date.lastIndexOf("-");
//    System.out.println("a:"+ a + " b:" + b );
    String tempString = date.substring(a+1,b);
//    System.out.println("Month: " + tempString);
    int Month = Integer.parseInt(tempString);
    return Month;
  }
  public static int getDay(String date)
  {
    int a = date.indexOf("-");
    int b = date.lastIndexOf("-");
//  System.out.println("a:"+ a + " b:" + b );
    String tempString = date.substring(b+1);
//    System.out.println("Day: " + tempString);
    int Day = Integer.parseInt(tempString);
    return Day;
  }

  public static String formatOBV(double value)
  {
    int ivalue = 0;
    double absValue = Math.abs(value);
    if (absValue <1000 && absValue >= 0)
    {
      ivalue = Math.round((float)value);
      return String.valueOf(ivalue) + "K";
    }
    else  if (absValue < 1000000 && absValue >=1000)
    {
      ivalue = Math.round((float)(value/1000f));
      return String.valueOf(ivalue)+"M";
    }
    else if (absValue >= 1000000)
    {
      ivalue = Math.round((float)(value/1000000f));
      return String.valueOf(ivalue)+"B";
    }
    return "X";
  }
  public static String formatInteger(double value)
  {
    int ivalue = 0;
    if (value <1000 && value >= 0)
    {
      ivalue = Math.round((float)value);
      return String.valueOf(ivalue);
    }
    else  if (value < 1000000 && value >=1000)
    {
      ivalue = Math.round((float)(value/1000f));
      return String.valueOf(ivalue)+"K";
    }
    else if (value >= 1000000)
    {
      ivalue = Math.round((float)(value/1000000f));
      return String.valueOf(ivalue)+"M";
    }
    return "X";
  }



  // format double to 0.xx format
   public static String formatData3(double value)
   {
  //  System.out.println("Double : " + value);
    long ivalue = Math.round((float)value*1000f);
    //long ivalue = (long)((float)value*100f+0.5f);
    float fvalue = ivalue/1000f;
    String Svalue = String.valueOf(fvalue);
    if (Svalue.indexOf(".")>0)
    {
        Svalue = Svalue + "0000000";
        String m_result = Svalue.substring(0,Svalue.indexOf(".")+4);
        //System.out.println("String : " + m_result);
        return m_result;
    }
    else
    {
      return Svalue+".000";
    }
   // return "XXX";
  }

  public static boolean isFloat(String ss)
  {
    //System.out.println(ss);
    try {
      float ivalue = Float.valueOf(ss).floatValue();
      String iss = String.valueOf(ivalue);
      //System.out.println("FFFFFF " + iss + " : " + ss);
      return true;

    }
    catch (Exception e){
      return false;
    }
  }

  public static boolean isNumerical(String ss)
  {
    //System.out.println(ss);
    try {
      int ivalue = Integer.parseInt(ss);
//      String iss = String.valueOf(ivalue);
      return true;
      //System.out.println(iss + " : " + ss);
     // if (iss.equals(ss)) {
       // System.out.println(true);

     //   return true;
    //  } else
    //  {


    //    return false;
   //   }
    }
    catch (Exception e){
      return false;
    }
  }
  // format double to 0.xx format
  public static String formatData2(double value)
  {
  //  System.out.println("Double : " + value);
    long ivalue = Math.round((float)value*100f);
    //long ivalue = (long)((float)value*100f+0.5f);
    float fvalue = ivalue/100f;
    String Svalue = String.valueOf(fvalue);
    if (Svalue.indexOf(".")>0)
    {
        Svalue = Svalue + "0000000";
        String m_result = Svalue.substring(0,Svalue.indexOf(".")+3);
   //     System.out.println("String : " + m_result);
        return m_result;
    }
    return "XXX";
  }
  public static String formatTime(int mHour, int mMinute)
  {
    String ho,mi;
    String mtime;
    ho = "000" + mHour;
    mi = "000" + mMinute;
    mtime = ho.substring(ho.length()-2) + ":"+ mi.substring(mi.length()-2);
    return mtime;
  }

  public static int getNextHour(int currentHour, int currentMinute, int intervals)
  {
     // System.out.println("intervals : " + intervals);
      currentMinute = currentMinute  + intervals;
      if (currentMinute >= 60 )
      {
        currentHour =  (currentHour + 1)%24;
      }
     // System.out.println("Hour : " + currentHour);
      return currentHour;

  }

  public static int getNextMinute(int currentHour, int currentMinute, int intervals)
  {
     // System.out.println("intervals : " + intervals);
      currentMinute = (currentMinute  + intervals)%60;
     // System.out.println("Minute:" + currentMinute);
      return currentMinute;
  }


}