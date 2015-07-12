/**
 * Title:        FME Chart Project for E-finet<p>
 * Description:  Object used to connect to server and create ChartData object
 * Copyright:    Copyright (c) <p>
 * Company:      <p>
 * @author
 * @version 1.0
 */
package fmechart;


import java.net.*;
import java.io.*;
import java.util.*;
import java.applet.*;
import java.lang.Math;
import java.lang.*;

public class ChartSource extends Thread
{


  private String ServerAddress = "203.161.232.88";
  private final String dailyInterface = "/FMEQuoteBase/newchart/getDaily.php";
  private final String weeklyInterface = "/FMEQuoteBase/newchart/getWeekly.php";
  private final String monthlyInterface = "/FMEQuoteBase/newchart/getMonthly.php";
  private final String intradayInterface = "/FMEQuoteBase/newchart/getIntra.php";


/*
  private String ServerAddress = "202.122.204.8";
  private final String dailyInterface = "/khchei8/fme/getDaily.php3";
  private final String weeklyInterface = "/khchei8/fme/getWeekly.php3";
  private final String monthlyInterface = "/khchei8/fme/getMonthly.php3";
  private final String intradayInterface = "/khchei8/fme/getIntra.php3";
*/
// http://203.161.232.88/FMEQuoteBase/newchart/getWeekly.php?code=5&data_num=9


  public final int STATE_UNINIT  = 0;
  public final int STATE_STARTED = 1;
  public final int STATE_SLEEP = 2;

  private boolean isNewCommand = false;
  private int engineState = STATE_UNINIT;
  private final int refreshSecond = 1;//5*60; //how long to be refresh for intraday chart.

  private FCommand fCommand = null;


  public void SetServerAddress(String sadd)
  {
    ServerAddress = sadd;
  }
  public void enable()
  {
    if (engineState == STATE_UNINIT )
    {
      this.start();   // start the thread
    }
    else if (engineState == STATE_STARTED)
    {
      this.notify();  // resume the thread
    }
    else if (engineState == STATE_SLEEP)
    {
      this.notify();
    }
  }

  public void disable()
  {

  }

  public void run()
  {
      int count =0;
      while (true)
      {

     //   System.out.println("ABC");
        try {
        if (fCommand!=null)
        {
          //process the FCommand the fire result to the ChartSource Listener
          FCommand currentCommand = fCommand;
          ChartData cdata =null;
          switch (fCommand.dType)
          {
            case FCommand.DAILY:
              fCommand = null;
//              System.out.println("getting Daily Data");
              if (isNewCommand)
              {
                isNewCommand = false;
                cdata= getDailyData(currentCommand);
                if (cdata!=null)
                {
                  System.out.println("fire event");
                  currentCommand.reference.OnReceivedChartData(currentCommand,cdata);
                }
                else
                {
                  currentCommand.reference.OnReceivedError(currentCommand);
                }
              }
              break;

            case FCommand.WEEKLY:
              fCommand = null;
//              System.out.println("getting weekly Data");
              if (isNewCommand)
              {
                isNewCommand = false;
                cdata= getWeeklyData(currentCommand);
  //              System.out.println("null weekly Data");
                if (cdata!=null)
                {
                  System.out.println("fire event");
                  currentCommand.reference.OnReceivedChartData(currentCommand,cdata);
                }
                else
                {
                  System.out.println("error");
                  currentCommand.reference.OnReceivedError(currentCommand);
                }
              }
              break;

            case FCommand.MONTHLY:

              fCommand = null;
              if (isNewCommand)
              {
                isNewCommand = false;
                System.out.println("getting Monthly Data");
                cdata= getMonthlyData(currentCommand);
                if (cdata!=null)
                {
                  System.out.println("fire event");
                  currentCommand.reference.OnReceivedChartData(currentCommand,cdata);
                }
                else
                {
                  currentCommand.reference.OnReceivedError(currentCommand);
                }
                if (cdata!=null)
                {
                  System.out.println("fire event");
                  currentCommand.reference.OnReceivedChartData(currentCommand,cdata);
                }
                else
                {
                  currentCommand.reference.OnReceivedError(currentCommand);
                }
              }
              break;

            case FCommand.INTRADAILY:
//            fCommand = null;
              isNewCommand = false;
              System.out.println("getting Intraday data");
              cdata = getIntradayData(currentCommand);
              if (cdata!=null)
              {
    //            System.out.println("fire event");
                currentCommand.reference.OnReceivedChartData(currentCommand,cdata);
              }
              else
              {
                currentCommand.reference.OnReceivedError(currentCommand);
              }
            break;


          }

        }
        else {
      //   System.out.println("None");
        }


        synchronized (this)
        {
          for (int i =0;i<refreshSecond;i++)
          {
            wait(1000);
            if (isNewCommand) break;
          }
        }
        }
        catch (InterruptedException e)
        {
          fCommand = null;
          isNewCommand = false;
        }
      }
  }

  public ChartSource()
  {
  }


  public synchronized void  addCommand(FCommand fC)
  {
//    synchronized ()
//    {
      isNewCommand = true;
      fCommand = fC;
      this.notify();
//    }
  }

  public ChartData getIntradayData(FCommand fc)
  {
    int Code = fc.Code;
    int NumberOfPoints = fc.numberOfPoint;
    int intervals = fc.intradayIntervals;
    if (intervals !=1 && intervals !=10)
    {
      intervals = 10;
    }
//    fc.intradayIntervals=1;
    String srcAddr = "http://" + ServerAddress + intradayInterface+"?code="+ Code + "&min=" + intervals;
    System.out.println(srcAddr);
    ChartData newChartData = new ChartData();
    newChartData.Code = Code;
    newChartData.dataType = ChartData.INTRADAY;
    newChartData.intradayInterval = intervals;  // record the intervals 1,5,or 10

    try
    {
      URL Finet;
      Finet = new URL(srcAddr);

      URLConnection FinetConnection =  Finet.openConnection();
      BufferedReader  DS = new BufferedReader(new InputStreamReader(FinetConnection.getInputStream()));

      String RawData = DS.readLine();   //retrieve number Of points it retrieve and its name
      StringTokenizer htokens = new StringTokenizer(RawData);

      int m_NumberOfPoints = Integer.parseInt(htokens.nextToken(";"));
      newChartData.EName = htokens.nextToken(";");
      newChartData.CName = htokens.nextToken(";");
      System.out.println("NumberOfPoints: " + m_NumberOfPoints + " Ename " + newChartData.EName + " CName " + newChartData.CName );

      if (m_NumberOfPoints ==0 )
      {
        return null;
      }


      Vector rawPoints = new Vector();
      for (int i=0;i<m_NumberOfPoints;i++)
      {
        String tempDateTime;
        String tempHigh;
        String tempLow;
        String tempOpen;
        String tempClose;
        String tempVol;

        FPoint fpoint = new FPoint();
        RawData = DS.readLine();   //retrieve a point line
//        System.out.println(RawData);
        StringTokenizer tokens = new StringTokenizer(RawData);
        tempDateTime = tokens.nextToken(";");
        tempOpen = tokens.nextToken(";");
        tempHigh = tokens.nextToken(";");
        tempLow = tokens.nextToken(";");
        tempClose = tokens.nextToken(";");
        tempVol = tokens.nextToken(";");
//      System.out.println(tempDateTime + " " + tempHigh + " " + tempLow + " " + tempOpen + " " + tempClose + " " + tempVol);
        //System.out.println(RawData);
        fpoint.Open = Float.valueOf(tempOpen).floatValue();
        fpoint.Close  = Float.valueOf(tempClose).floatValue();
        fpoint.Maximum  = Float.valueOf(tempHigh).floatValue();
        fpoint.Minimum = Float.valueOf(tempLow).floatValue();
        fpoint.Volume = Integer.parseInt(tempVol);
        fpoint.Hour   = FFormater.getHour(tempDateTime);
        fpoint.Minute = FFormater.getMinute(tempDateTime);

        //to fix some bugs about the data....
        if (fpoint.Minimum == 0.00f) fpoint.Minimum = fpoint.Close;
        if (fpoint.Close == 0.00f) fpoint.Close = fpoint.Maximum;


        //////////////////////////////////////////////////////////////////////////////////////////////////

        rawPoints.addElement(fpoint);
      }


    //System.out.println("Intraday pass 1");

      // the starting time of each date; 10:00
      int currentHour = 10;
      int currentMinute = 00;
      int tempH,tempM;
      int i = rawPoints.size()-1;
      while (i >= 0 )
      {
        FPoint fpoint  = (FPoint) rawPoints.elementAt(i);
        int timeStamp = fpoint.Hour * 60 + fpoint.Minute;
        int currentTimeStamp = currentHour * 60 + currentMinute;
       // System.out.println("Time:" + fpoint.Hour + ":0" + fpoint.Minute + "current: " + currentHour +":0" + currentMinute );
        if (timeStamp < currentTimeStamp)
        {
          //System.out.println("case 1 : " + i + " : " +  timeStamp + " : " + currentTimeStamp);
          FPoint pp = (FPoint) rawPoints.elementAt(i);
          //System.out.println(i+ " pp :" + pp.Hour + ":" +pp.Minute );
          if (!fc.isFillEmptyPoints || newChartData.data.size() < NumberOfPoints)
          newChartData.data.addElement(rawPoints.elementAt(i));
          i--;
        }
        else if (timeStamp == currentTimeStamp)
        {
       //   System.out.println("case 2 : " + i + " : " +  timeStamp + " : " + currentTimeStamp);
          if (!fc.isFillEmptyPoints || newChartData.data.size() < NumberOfPoints)
          newChartData.data.addElement(rawPoints.elementAt(i));
          tempH = FFormater.getNextHour(currentHour, currentMinute, fc.intradayIntervals);
          tempM = FFormater.getNextMinute(currentHour, currentMinute, fc.intradayIntervals);
          currentHour = tempH;
          currentMinute = tempM;
          i--;
        }
        else if (timeStamp > currentTimeStamp)
        {
       //   System.out.println("case 3 : " + i + " : " +  timeStamp + " : " + currentTimeStamp);
          FPoint newPoint = new FPoint();
          newPoint.isValid = false;
          newPoint.Hour = currentHour;
          newPoint.Minute = currentMinute;
//          int ss = currentHour * 60 + currentMinute;
          if (currentTimeStamp <= (12*60+30) || currentTimeStamp >=(14*60+30))
          {
            if (!fc.isFillEmptyPoints || newChartData.data.size() < NumberOfPoints)
            newChartData.data.addElement(newPoint);
          }
          tempH = FFormater.getNextHour(currentHour, currentMinute, fc.intradayIntervals);
          tempM = FFormater.getNextMinute(currentHour, currentMinute, fc.intradayIntervals);
          currentHour = tempH;
          currentMinute = tempM;
        }

      }
      //StringTokenizer tokens = new StringTokenizer(RawData);
      // add "empty" point at the end..
   // System.out.println("Intraday pass 2");

      if (fc.isFillEmptyPoints)
      {
        if (NumberOfPoints > newChartData.data.size())
        {
          int addcount = NumberOfPoints - newChartData.data.size();
          for (int j=0;j<addcount;j++)
          {
            FPoint fpoint = new FPoint();
            fpoint.isValid = false;
            newChartData.data.addElement(fpoint);
          }
        }
        else if (NumberOfPoints < newChartData.data.size())
        {
          int deletecount = NumberOfPoints - newChartData.data.size();
          for (int k=0 ; k<deletecount; k++ )
          newChartData.data.removeElementAt(newChartData.data.size()-1);
        }

      }

    for (int k=1;k< newChartData.data.size();k++)
    {
      FPoint fpoint1  = (FPoint) newChartData.data.elementAt(k-1);
      FPoint fpoint2  = (FPoint) newChartData.data.elementAt(k);
      if (!fpoint2.isValid && fpoint1.isValid)
      {
        fpoint2.Close = fpoint1.Close;
        fpoint2.Maximum = fpoint2.Close;
        fpoint2.Minimum = fpoint2.Close;
        fpoint2.Open = fpoint2.Close;
        fpoint2.Volume = 0;
        fpoint2.isValid = true;
      }
    }

    for (int k=newChartData.data.size()-2;k>=0;k--)
    {
      FPoint fpoint1  = (FPoint) newChartData.data.elementAt(k+1);
      FPoint fpoint2  = (FPoint) newChartData.data.elementAt(k);
      if (!fpoint2.isValid && fpoint1.isValid)
      {
        fpoint2.Close = fpoint1.Close;
        fpoint2.Maximum = fpoint2.Close;
        fpoint2.Minimum = fpoint2.Close;
        fpoint2.Open = fpoint2.Close;
        fpoint2.Volume = 0;
        fpoint2.isValid = true;
      }
    }

  for (int k=0;k<newChartData.data.size();k++)
  {
    FPoint fpoint  = (FPoint) newChartData.data.elementAt(k);
    //System.out.println("Point " +  fpoint.Close + " : " + fpoint.Open  );
  }
   // System.out.println("Intraday pass 3 : " + newChartData.data.size());


    }
    catch (Exception exception)
    {
     // exception.printStackTrace();
      System.out.println("Error when download profile");
      return null;
    }
    //System.out.println("Length : " + newChartData.data.size());
    return newChartData;
  }

  public ChartData getDailyData(FCommand fc)
  {

    int Code = fc.Code;
    int NumberOfPoints = fc.numberOfPoint;

 // boolean iss = true;
 // if (iss)
 // return getTestData(Code,NumberOfPoints);

  //System.out.println("Value" + Integer.parseInt("454.fg"));
    String srcAddr = "http://" + ServerAddress + dailyInterface+"?code="+ Code + "&data_num=" + NumberOfPoints;// + "&startdate=2000-11-1";
    System.out.println(srcAddr);
    ChartData newChartData = new ChartData();
    newChartData.Code = Code;
    newChartData.dataType = ChartData.DAILY;

    try
    {
      URL Finet;
      Finet = new URL(srcAddr);

      URLConnection FinetConnection =  Finet.openConnection();
      BufferedReader  DS = new BufferedReader(new InputStreamReader(FinetConnection.getInputStream()));

      String RawData = DS.readLine();   //retrieve number Of points it retrieve and its name
      StringTokenizer htokens = new StringTokenizer(RawData);

      int m_NumberOfPoints = Integer.parseInt(htokens.nextToken(";"));
      newChartData.EName = htokens.nextToken(";");
      newChartData.CName = htokens.nextToken(";");
      System.out.println("NumberOfPoints: " + m_NumberOfPoints + " Ename " + newChartData.EName + " CName " + newChartData.CName );

      if (m_NumberOfPoints <10 )
      {
        return null;
      }


      Vector rawPoints = new Vector();
      for (int i=0;i<m_NumberOfPoints;i++)
      {
        String tempDate;
        String tempHigh;
        String tempLow;
        String tempOpen;
        String tempClose;
        String tempVol;

        FPoint fpoint = new FPoint();
        RawData = DS.readLine();   //retrieve a point line
//        System.out.println(RawData);
        StringTokenizer tokens = new StringTokenizer(RawData);
        tempDate = tokens.nextToken(";");
        tempHigh = tokens.nextToken(";");
        tempLow = tokens.nextToken(";");
        tempOpen = tokens.nextToken(";");
        tempClose = tokens.nextToken(";");
        tempVol = tokens.nextToken(";");
        //System.out.println(tempDate + " " + tempHigh + " " + tempLow + " " + tempOpen + " " + tempClose + " " + tempVol);
        fpoint.Open = Float.valueOf(tempOpen).floatValue();
        fpoint.Close  = Float.valueOf(tempClose).floatValue();
        fpoint.Maximum  = Float.valueOf(tempHigh).floatValue();
        fpoint.Minimum = Float.valueOf(tempLow).floatValue();
        fpoint.Volume = Integer.parseInt(tempVol);
        fpoint.Year = FFormater.getYear(tempDate);
        fpoint.Month = FFormater.getMonth(tempDate);
        fpoint.Day = FFormater.getDay(tempDate);
        rawPoints.addElement(fpoint);
      }

      // add "empty" point at the end..
      if (fc.isFillEmptyPoints)
      if (NumberOfPoints > m_NumberOfPoints)
      {
        for (int j=0;j<NumberOfPoints-m_NumberOfPoints;j++)
        {
          FPoint fpoint = new FPoint();
          fpoint.isValid = false;
          rawPoints.addElement(fpoint);
        }
      }


      for (int i=rawPoints.size()-1; i>=0; i--)
      {
        newChartData.data.addElement(rawPoints.elementAt(i));
      }
      // StringTokenizer tokens = new StringTokenizer(RawData);

    }
    catch (Exception exception)
    {
      System.out.println("Error when download profile");
      return null;
    }

    return newChartData;
  }



  public ChartData getWeeklyData(FCommand fc)
  {
    int Code = fc.Code;
    int NumberOfPoints = fc.numberOfPoint;
 // boolean iss = true;
 // if (iss)
 // return getTestData(123,NumberOfPoints);
    //System.out.println("Value" + Integer.parseInt("454.fg"));
    String srcAddr = "http://" + ServerAddress + weeklyInterface+"?code="+ Code + "&data_num=" + NumberOfPoints;// + "&startdate=2000-11-1";
   // System.out.println(srcAddr);
    ChartData newChartData = new ChartData();
    newChartData.Code = Code;
    newChartData.dataType = ChartData.WEEKLY;

    try
    {
      URL Finet;
      Finet = new URL(srcAddr);

      URLConnection FinetConnection =  Finet.openConnection();
      BufferedReader  DS = new BufferedReader(new InputStreamReader(FinetConnection.getInputStream()));

      String RawData = DS.readLine();   //retrieve number Of points it retrieve and its name
      StringTokenizer htokens = new StringTokenizer(RawData);

      int m_NumberOfPoints = Integer.parseInt(htokens.nextToken(";"));
      newChartData.EName = htokens.nextToken(";");
      newChartData.CName = htokens.nextToken(";");
     // System.out.println("NumberOfPoints: " + m_NumberOfPoints + " Ename " + newChartData.EName + " CName " + newChartData.CName );

      if (m_NumberOfPoints <10 )
      {
        return null;
      }


      Vector rawPoints = new Vector();
      for (int i=0;i<m_NumberOfPoints;i++)
      {
        String tempfirstDate;
        String templastDate;
        String tempHigh;
        String tempLow;
        String tempOpen;
        String tempClose;
        String tempVol;

        FPoint fpoint = new FPoint();
        RawData = DS.readLine();   //retrieve a point line
       // System.out.println(RawData);
        StringTokenizer tokens = new StringTokenizer(RawData);
        tempfirstDate = tokens.nextToken(";");
        templastDate = tokens.nextToken(";");
        tempHigh = tokens.nextToken(";");
        tempLow = tokens.nextToken(";");
        tempOpen = tokens.nextToken(";");
        tempClose = tokens.nextToken(";");
        tempVol = tokens.nextToken(";");
//        System.out.println(tempfirstDate + " " + tempHigh + " " + tempLow + " " + tempOpen + " " + tempClose + " " + tempVol);
        fpoint.Open = Float.valueOf(tempOpen).floatValue();
        fpoint.Close  = Float.valueOf(tempClose).floatValue();
        fpoint.Maximum  = Float.valueOf(tempHigh).floatValue();
        fpoint.Minimum = Float.valueOf(tempLow).floatValue();
        fpoint.Volume = Integer.parseInt(tempVol);
        fpoint.Year = FFormater.getYear(tempfirstDate);
        fpoint.Month = FFormater.getMonth(tempfirstDate);
        fpoint.Day = FFormater.getDay(tempfirstDate);
        fpoint.lYear = FFormater.getYear(templastDate);
        fpoint.lMonth = FFormater.getMonth(templastDate);
        fpoint.lDay = FFormater.getDay(templastDate);

        rawPoints.addElement(fpoint);

      }

      // add "empty" point at the end..
      if (fc.isFillEmptyPoints)
      if (NumberOfPoints > m_NumberOfPoints)
      {
        for (int j=0;j<NumberOfPoints-m_NumberOfPoints;j++)
        {
          FPoint fpoint = new FPoint();
          fpoint.isValid = false;
          rawPoints.addElement(fpoint);
        }
      }

      for (int i=rawPoints.size()-1; i>=0; i--)
      {
        newChartData.data.addElement(rawPoints.elementAt(i));
      }
      // StringTokenizer tokens = new StringTokenizer(RawData);

    }
    catch (Exception exception)
    {
      System.out.println("Error when download profile" + exception.toString() );

      return null;
    }

    return newChartData;
  }




  public ChartData getMonthlyData(FCommand fc)
  {




    int Code = fc.Code;
    int NumberOfPoints = fc.numberOfPoint;
 //   boolean iss = true;
 // if (iss)
 // return getTestData(Code,NumberOfPoints);

    String srcAddr = "http://" + ServerAddress + monthlyInterface+"?code="+ Code + "&data_num=" + NumberOfPoints;// + "&startdate=2000-11-1";
    System.out.println(srcAddr);
    ChartData newChartData = new ChartData();
    newChartData.Code = Code;
    newChartData.dataType = ChartData.MONTHLY;

    try
    {
      URL Finet;
      Finet = new URL(srcAddr);

      URLConnection FinetConnection =  Finet.openConnection();
      BufferedReader  DS = new BufferedReader(new InputStreamReader(FinetConnection.getInputStream()));

      String RawData = DS.readLine();   //retrieve number Of points it retrieve and its name
      StringTokenizer htokens = new StringTokenizer(RawData);

      int m_NumberOfPoints = Integer.parseInt(htokens.nextToken(";"));
      newChartData.EName = htokens.nextToken(";");
      newChartData.CName = htokens.nextToken(";");
     // System.out.println("NumberOfPoints: " + m_NumberOfPoints + " Ename " + newChartData.EName + " CName " + newChartData.CName );

      if (m_NumberOfPoints <10 )
      {
        return null;
      }


      Vector rawPoints = new Vector();
      for (int i=0;i<m_NumberOfPoints;i++)
      {
        String tempfirstDate;
        String templastDate;
        String tempHigh;
        String tempLow;
        String tempOpen;
        String tempClose;
        String tempVol;

        FPoint fpoint = new FPoint();
        RawData = DS.readLine();   //retrieve a point line
       // System.out.println(RawData);
        StringTokenizer tokens = new StringTokenizer(RawData);
        tempfirstDate = tokens.nextToken(";");
        templastDate = tokens.nextToken(";");
        tempHigh = tokens.nextToken(";");
        tempLow = tokens.nextToken(";");
        tempOpen = tokens.nextToken(";");
        tempClose = tokens.nextToken(";");
        tempVol = tokens.nextToken(";");
//      System.out.println(tempfirstDate + " " + tempHigh + " " + tempLow + " " + tempOpen + " " + tempClose + " " + tempVol);
        fpoint.Open = Float.valueOf(tempOpen).floatValue();
        fpoint.Close  = Float.valueOf(tempClose).floatValue();
        fpoint.Maximum  = Float.valueOf(tempHigh).floatValue();
        fpoint.Minimum = Float.valueOf(tempLow).floatValue();
        fpoint.Volume = Integer.parseInt(tempVol);
        fpoint.Year = FFormater.getYear(tempfirstDate);
        fpoint.Month = FFormater.getMonth(tempfirstDate);
        fpoint.Day = FFormater.getDay(tempfirstDate);
        fpoint.lYear = FFormater.getYear(templastDate);
        fpoint.lMonth = FFormater.getMonth(templastDate);
        fpoint.lDay = FFormater.getDay(templastDate);

        rawPoints.addElement(fpoint);

      }

      // add "empty" point at the end..
       if (fc.isFillEmptyPoints)
      if (NumberOfPoints > m_NumberOfPoints)
      {
        for (int j=0;j<NumberOfPoints-m_NumberOfPoints;j++)
        {
          FPoint fpoint = new FPoint();
          fpoint.isValid = false;
          rawPoints.addElement(fpoint);
        }
      }

      for (int i=rawPoints.size()-1; i>=0; i--)
      {
        newChartData.data.addElement(rawPoints.elementAt(i));
      }
      // StringTokenizer tokens = new StringTokenizer(RawData);

    }
    catch (Exception exception)
    {
      System.out.println("Error when download profile" + exception.toString() );

      return null;
    }

    return newChartData;
}


  // for test only......
  public static ChartData getTestData(int Code, int NumberOfPoints)
  {

    ChartData newChartData = new ChartData();

    newChartData.Code = Code;

    FPoint oldfpoint = new FPoint();
    newChartData.dataType = ChartData.WEEKLY;
    newChartData.EName = "ABC COMPANY";
    newChartData.CName = "Chinese Name";

    for (int i=0;i<NumberOfPoints;i++)
    {
      FPoint fpoint = new FPoint();

      fpoint.Current =(float)( oldfpoint.Current + (Math.random()-0.5f)*3);
      fpoint.Close = (float)( fpoint.Current + (Math.random()-0.5f)*3);
      fpoint.Open = (float)( fpoint.Current  + (Math.random()-0.5f)*3);
      fpoint.Maximum = (float)( oldfpoint.Maximum + (Math.random()-0.5f));
      fpoint.Minimum = (float)( oldfpoint.Minimum + (Math.random()-0.5f));

      if (fpoint.Maximum< fpoint.Minimum)
      {
        float tempValue = fpoint.Maximum;
        fpoint.Maximum = fpoint.Minimum ;
        fpoint.Minimum = tempValue;
      }

      if (fpoint.Close < fpoint.Minimum || fpoint.Close > fpoint.Maximum )
      {
        fpoint.Close = (fpoint.Minimum + fpoint.Maximum )/2.0f;
      }

      if (fpoint.Open < fpoint.Minimum || fpoint.Open > fpoint.Maximum )
      {
        fpoint.Open = (fpoint.Minimum + fpoint.Maximum )/2.0f;
      }

      if (fpoint.Current < fpoint.Minimum || fpoint.Current > fpoint.Maximum )
      {
        fpoint.Current = (fpoint.Minimum + fpoint.Maximum )/2.0f;
      }
      fpoint.Volume = (int)(fpoint.Current*fpoint.Current);

      oldfpoint = fpoint;
      newChartData.data.addElement(fpoint);
    }

    return newChartData;
  }

}

