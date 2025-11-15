package com.ic.data;

// the command for the engine to download data,

import com.ic.core.FConfig;
import com.ic.data.provider.DataSourceFactory;
import com.ic.data.provider.DataSourceProvider;
import com.ic.util.FormatUtil;
import org.json.JSONObject;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;

import static com.ic.data.CommandType.DAILY;

public class RequestCommand {

  private static final Logger log = Logger.getLogger(RequestCommand.class.getName());

  // Static attributes.
  public static final int TYPE_DOWNLOAD_RIGHT_CHART = 0;
  public static final int TYPE_DOWNLOAD_LEFT_CHART = 2;

  private final String dailyInterface = "newchart/getDaily.php";
  private final String weeklyInterface = "newchart/getWeekly.php";
  private final String monthlyInterface = "newchart/getMonthly.php";
  private final String intradayInterface = "newchart/getIntra.php";
  private final String code;                                   // the code to download
  private int actionType = TYPE_DOWNLOAD_RIGHT_CHART; // the action of the RequestCommand
  private CommandType dType = DAILY;                  // the type of the chart
  private String sKey = "RMain1";                     // the key of the chart used to id the chart.
  private int numberOfPoint = 100;                    // Number of point to download
  private int intradayInterval = 1;                   // for intraday only
  private boolean isFillEmptyPoints = false;
  private ChartDataServiceCallback reference;          // the reference to the object that create this command.

  public RequestCommand(String sCode, int atype, CommandType type, String key, int num, int intervals, boolean fillEmptyPoints, ChartDataServiceCallback re) {
    code = sCode;
    actionType = atype;
    dType = type;
    sKey = key;
    numberOfPoint = num;
    intradayInterval = intervals;
    reference = re;
    isFillEmptyPoints = fillEmptyPoints;
  }

  public RequestCommand(String sCode, CommandType type, ChartDataServiceCallback re) {
    code = sCode;
    dType = type;
    reference = re;
  }

  // for test only......
  public static ChartData getTestData(String Code, int NumberOfPoints) {

    ChartData newChartData = new ChartData();

    newChartData.setCode(Code);

    StockData oldfpoint = new StockData();
    newChartData.dataInterval = DataInterval.WEEKLY;
    newChartData.setName("Chinese Name");

    for (int i = 0; i < NumberOfPoints; i++) {
      StockData fpoint = new StockData();

      fpoint.setCurrent((float) (oldfpoint.getCurrent() + (Math.random() - 0.5f) * 3));
      fpoint.setClose((float) (fpoint.getCurrent() + (Math.random() - 0.5f) * 3));
      fpoint.setOpen((float) (fpoint.getCurrent() + (Math.random() - 0.5f) * 3));
      fpoint.setMaximum((float) (oldfpoint.getMaximum() + (Math.random() - 0.5f)));
      fpoint.setMinimum((float) (oldfpoint.getMinimum() + (Math.random() - 0.5f)));

      if (fpoint.getMaximum() < fpoint.getMinimum()) {
        float tempValue = fpoint.getMaximum();
        fpoint.setMaximum(fpoint.getMinimum());
        fpoint.setMinimum(tempValue);
      }

      if (fpoint.getClose() < fpoint.getMinimum() || fpoint.getClose() > fpoint.getMaximum()) {
        fpoint.setClose((fpoint.getMinimum() + fpoint.getMaximum()) / 2.0f);
      }

      if (fpoint.getOpen() < fpoint.getMinimum() || fpoint.getOpen() > fpoint.getMaximum()) {
        fpoint.setOpen((fpoint.getMinimum() + fpoint.getMaximum()) / 2.0f);
      }

      if (fpoint.getCurrent() < fpoint.getMinimum() || fpoint.getCurrent() > fpoint.getMaximum()) {
        fpoint.setCurrent((fpoint.getMinimum() + fpoint.getMaximum()) / 2.0f);
      }
      fpoint.setVolume((int) (fpoint.getCurrent() * fpoint.getCurrent()));

      oldfpoint = fpoint;
      newChartData.getData().add(fpoint);
    }

    return newChartData;
  }

  public int getIntadayInterval() {
    return intradayInterval;
  }

  public ChartDataServiceCallback getListener() {
    return reference;
  }

  public void setListener(ChartDataServiceCallback ref) {
    reference = ref;
  }

  public CommandType getChartType() {
    return dType;
  }

  public int getActionType() {
    return actionType;
  }

  public String getCode() {
    return code;
  }

  public boolean isFillEmptyPoints() {
    return isFillEmptyPoints;
  }

  public int getNumberOfPoint() {
    return numberOfPoint;
  }

  public void execute() {

    RequestCommand currentCommand = this;
    ChartData cdata = null;
    switch (getChartType()) {
      case DAILY:
        //cdata = getTestData(1,100);
        cdata = getYahooDailyData();
        if (cdata != null) {
          getListener().OnReceivedChartData(currentCommand, cdata);
        } else {
          getListener().OnReceivedError(currentCommand);
        }
        break;
      case WEEKLY:
        cdata = getWeeklyData(currentCommand);
        if (cdata != null) {
          getListener().OnReceivedChartData(currentCommand, cdata);
        } else {
          //System.out.println("error");
          getListener().OnReceivedError(currentCommand);
        }
        break;
      case MONTHLY:
        cdata = getMonthlyData(currentCommand);
        if (cdata != null) {
          getListener().OnReceivedChartData(currentCommand, cdata);
        } else {
          getListener().OnReceivedError(currentCommand);
        }
        if (cdata != null) {
          getListener().OnReceivedChartData(currentCommand, cdata);
        } else {
          getListener().OnReceivedError(currentCommand);
        }
        break;
      case INTRADAY:
        cdata = getIntradayData(currentCommand);
        if (cdata != null) {
          getListener().OnReceivedChartData(currentCommand, cdata);
        } else {
          getListener().OnReceivedError(currentCommand);
        }
        break;
      case REALTIME_QUOTE:
        String price = getReadTimeQuote(currentCommand.getCode());
        getListener().OnReceivedChartData(currentCommand, price);
        break;
    }
  }

  public ChartData getIntradayData(RequestCommand fc) {

    String Code = fc.getCode();
    int NumberOfPoints = fc.getNumberOfPoint();
    int intervals = fc.getIntadayInterval();
    if (intervals != 1 && intervals != 10) {
      intervals = 10;
    }

    String srcAddr = "http://2103.161.232.72/FMEQuoteBase/" + intradayInterface + "?code=" + Code + "&min=" + intervals;

    ChartData newChartData = new ChartData();
    newChartData.setCode(Code);
    newChartData.dataInterval = DataInterval.INTRADAY;
    newChartData.setIntradayInterval(intervals);  // record the intervals 1,5,or 10

    try {
      URL Finet;
      Finet = new URL(srcAddr);

      URLConnection FinetConnection = Finet.openConnection();
      BufferedReader DS = new BufferedReader(new InputStreamReader(FinetConnection.getInputStream()));

      String RawData = DS.readLine();   //retrieve number Of points it retrieve and its name
      StringTokenizer htokens = new StringTokenizer(RawData);

      int m_NumberOfPoints = Integer.parseInt(htokens.nextToken(";"));
      newChartData.setName(htokens.nextToken(";"));
      // newChartData.setName(htokens.nextToken(";"));
      // System.out.println("NumberOfPoints: " + m_NumberOfPoints + " Ename " + newChartData.getEName() + " CName " + newChartData.getCName());

      if (m_NumberOfPoints == 0) {
        return null;
      }


      List<StockData> rawPoints = new ArrayList<StockData>(1000);
      for (int i = 0; i < m_NumberOfPoints; i++) {
        String tempDateTime;
        String tempHigh;
        String tempLow;
        String tempOpen;
        String tempClose;
        String tempVol;

        StockData fpoint = new StockData();
        RawData = DS.readLine();   //retrieve a point line
        StringTokenizer tokens = new StringTokenizer(RawData);
        tempDateTime = tokens.nextToken(";");
        tempOpen = tokens.nextToken(";");
        tempHigh = tokens.nextToken(";");
        tempLow = tokens.nextToken(";");
        tempClose = tokens.nextToken(";");
        tempVol = tokens.nextToken(";");
        fpoint.setOpen(Float.valueOf(tempOpen).floatValue());
        fpoint.setClose(Float.valueOf(tempClose).floatValue());
        fpoint.setMaximum(Float.valueOf(tempHigh).floatValue());
        fpoint.setMinimum(Float.valueOf(tempLow).floatValue());
        fpoint.setVolume(Integer.parseInt(tempVol));
        fpoint.setHour(FormatUtil.getHour(tempDateTime));
        fpoint.setMinute(FormatUtil.getMinute(tempDateTime));

        //to fix some bugs about the data....
        if (fpoint.getMinimum() == 0.00f) {
          fpoint.setMinimum(fpoint.getClose());
        }
        if (fpoint.getClose() == 0.00f) {
          fpoint.setClose(fpoint.getMaximum());
        }

        rawPoints.add(fpoint);
      }


      //System.out.println("Intraday pass 1");

      // the starting time of each date; 10:00
      int currentHour = 10;
      int currentMinute = 00;
      int tempH, tempM;
      int i = rawPoints.size() - 1;
      while (i >= 0) {
        StockData fpoint = rawPoints.get(i);
        int timeStamp = fpoint.getHour() * 60 + fpoint.getMinute();
        int currentTimeStamp = currentHour * 60 + currentMinute;
        if (timeStamp < currentTimeStamp) {
          StockData pp = rawPoints.get(i);
          if (!fc.isFillEmptyPoints() || newChartData.getData().size() < NumberOfPoints) {
            newChartData.getData().add(rawPoints.get(i));
          }
          i--;
        } else if (timeStamp == currentTimeStamp) {
          if (!fc.isFillEmptyPoints() || newChartData.getData().size() < NumberOfPoints) {
            newChartData.getData().add(rawPoints.get(i));
          }
          tempH = FormatUtil.getNextHour(currentHour, currentMinute, fc.getIntadayInterval());
          tempM = FormatUtil.getNextMinute(currentHour, currentMinute, fc.getIntadayInterval());
          currentHour = tempH;
          currentMinute = tempM;
          i--;
        } else if (timeStamp > currentTimeStamp) {
          StockData newPoint = new StockData();
          newPoint.setValid(false);
          newPoint.setHour(currentHour);
          newPoint.setMinute(currentMinute);
          if (currentTimeStamp <= (12 * 60 + 30) || currentTimeStamp >= (14 * 60 + 30)) {
            if (!fc.isFillEmptyPoints() || newChartData.getData().size() < NumberOfPoints) {
              newChartData.getData().add(newPoint);
            }
          }
          tempH = FormatUtil.getNextHour(currentHour, currentMinute, fc.getIntadayInterval());
          tempM = FormatUtil.getNextMinute(currentHour, currentMinute, fc.getIntadayInterval());
          currentHour = tempH;
          currentMinute = tempM;
        }

      }

      // add "empty" point at the end..
      // System.out.println("Intraday pass 2");
      if (fc.isFillEmptyPoints()) {
        if (NumberOfPoints > newChartData.getData().size()) {
          int addcount = NumberOfPoints - newChartData.getData().size();
          for (int j = 0; j < addcount; j++) {
            StockData fpoint = new StockData();
            fpoint.setValid(false);
            newChartData.getData().add(fpoint);
          }
        } else if (NumberOfPoints < newChartData.getData().size()) {
          int deletecount = NumberOfPoints - newChartData.getData().size();
          for (int k = 0; k < deletecount; k++) {
            newChartData.getData().remove(newChartData.getData().size() - 1);
          }
        }

      }

      for (int k = 1; k < newChartData.getData().size(); k++) {
        StockData fpoint1 = newChartData.getData().get(k - 1);
        StockData fpoint2 = newChartData.getData().get(k);
        if (!fpoint2.isValid() && fpoint1.isValid()) {
          fpoint2.setClose(fpoint1.getClose());
          fpoint2.setMaximum(fpoint2.getClose());
          fpoint2.setMinimum(fpoint2.getClose());
          fpoint2.setOpen(fpoint2.getClose());
          fpoint2.setVolume(0);
          fpoint2.setValid(true);
          fpoint2.setIntraDayMarked(true); // it is a point without data in intraday.
        }
      }

      for (int k = newChartData.getData().size() - 2; k >= 0; k--) {
        StockData fpoint1 = newChartData.getData().get(k + 1);
        StockData fpoint2 = newChartData.getData().get(k);
        if (!fpoint2.isValid() && fpoint1.isValid()) {
          fpoint2.setClose(fpoint1.getClose());
          fpoint2.setMaximum(fpoint2.getClose());
          fpoint2.setMinimum(fpoint2.getClose());
          fpoint2.setOpen(fpoint2.getClose());
          fpoint2.setVolume(0);
          fpoint2.setValid(true);
          fpoint2.setIntraDayMarked(true); // it is a point without data in intraday.
        }


      }

      for (int k = 0; k < newChartData.getData().size(); k++) {
        StockData fpoint = newChartData.getData().get(k);
      }

    } catch (Exception exception) {
      //System.out.println("Error when download profile");
      exception.printStackTrace();
      return null;
    }
    return newChartData;
  }

  public ChartData getDailyData(RequestCommand fc) {

    String Code = fc.getCode();
    int NumberOfPoints = fc.getNumberOfPoint();

    String srcAddr = "??/" + dailyInterface + "?code=" + Code + "&data_num=" + NumberOfPoints;// + "&startdate=2000-11-1";

    ChartData newChartData = new ChartData();
    newChartData.setCode(Code);
    newChartData.dataInterval = DataInterval.DAILY;

    try {
      URL Finet;
      Finet = new URL(srcAddr);

      URLConnection FinetConnection = Finet.openConnection();
      BufferedReader DS = new BufferedReader(new InputStreamReader(FinetConnection.getInputStream()));

      String RawData = DS.readLine();   //retrieve number Of points it retrieve and its name
      StringTokenizer htokens = new StringTokenizer(RawData);

      int m_NumberOfPoints = Integer.parseInt(htokens.nextToken(";"));
      newChartData.setName(htokens.nextToken(";"));
      newChartData.setName(htokens.nextToken(";"));

      if (m_NumberOfPoints < 10) {
        return null;
      }


      List<StockData> rawPoints = new ArrayList<StockData>(1000);
      for (int i = 0; i < m_NumberOfPoints; i++) {
        String tempDate;
        String tempHigh;
        String tempLow;
        String tempOpen;
        String tempClose;
        String tempVol;

        StockData fpoint = new StockData();
        RawData = DS.readLine();   //retrieve a point line
        StringTokenizer tokens = new StringTokenizer(RawData);
        tempDate = tokens.nextToken(";");
        tempHigh = tokens.nextToken(";");
        tempLow = tokens.nextToken(";");
        tempOpen = tokens.nextToken(";");
        tempClose = tokens.nextToken(";");
        tempVol = tokens.nextToken(";");
        fpoint.setOpen(Float.valueOf(tempOpen).floatValue());
        fpoint.setClose(Float.valueOf(tempClose).floatValue());
        fpoint.setMaximum(Float.valueOf(tempHigh).floatValue());
        fpoint.setMinimum(Float.valueOf(tempLow).floatValue());
        fpoint.setVolume(Integer.parseInt(tempVol));
        //fpoint.Year = FormatUtil.getYear(tempDate);
        //fpoint.Month = FormatUtil.getMonth(tempDate);
        //fpoint.Day = FormatUtil.getDay(tempDate);
        fpoint.setDate(FormatUtil.getDateFrom(tempDate));

        if (fpoint.getOpen() == 0) {
          fpoint.setOpen(fpoint.getClose());
          fpoint.setMaximum(fpoint.getClose());
          fpoint.setMinimum(fpoint.getClose());

        }

        rawPoints.add(fpoint);
      }

      // add "empty" point at the end..
      if (fc.isFillEmptyPoints()) {
        if (NumberOfPoints > m_NumberOfPoints) {
          for (int j = 0; j < NumberOfPoints - m_NumberOfPoints; j++) {
            StockData fpoint = new StockData();
            fpoint.setValid(false);
            rawPoints.add(fpoint);
          }
        }
      }
      for (int i = rawPoints.size() - 1; i >= 0; i--) {
        newChartData.getData().add(rawPoints.get(i));
      }

    } catch (Exception exception) {
      exception.printStackTrace();
      return null;
    }

    return newChartData;
  }

  public ChartData getWeeklyData(RequestCommand fc) {
    String Code = fc.getCode();
    int NumberOfPoints = fc.getNumberOfPoint();

    String srcAddr = "http://??/" + weeklyInterface + "?code=" + Code + "&data_num=" + NumberOfPoints;// + "&startdate=2000-11-1";

    ChartData newChartData = new ChartData();
    newChartData.setCode(Code);
    newChartData.dataInterval = DataInterval.WEEKLY;

    try {
      URL Finet;
      Finet = new URL(srcAddr);

      URLConnection FinetConnection = Finet.openConnection();
      BufferedReader DS = new BufferedReader(new InputStreamReader(FinetConnection.getInputStream()));

      String RawData = DS.readLine();   //retrieve number Of points it retrieve and its name
      StringTokenizer htokens = new StringTokenizer(RawData);

      int m_NumberOfPoints = Integer.parseInt(htokens.nextToken(";"));
      newChartData.setName(htokens.nextToken(";"));
      newChartData.setName(htokens.nextToken(";"));

      if (m_NumberOfPoints < 10) {
        return null;
      }


      List<StockData> rawPoints = new ArrayList<StockData>(1000);
      for (int i = 0; i < m_NumberOfPoints; i++) {
        String tempfirstDate;
        String templastDate;
        String tempHigh;
        String tempLow;
        String tempOpen;
        String tempClose;
        String tempVol;

        StockData fpoint = new StockData();
        RawData = DS.readLine();   //retrieve a point line

        StringTokenizer tokens = new StringTokenizer(RawData);
        tempfirstDate = tokens.nextToken(";");
        templastDate = tokens.nextToken(";");
        tempHigh = tokens.nextToken(";");
        tempLow = tokens.nextToken(";");
        tempOpen = tokens.nextToken(";");
        tempClose = tokens.nextToken(";");
        tempVol = tokens.nextToken(";");
        fpoint.setOpen(Float.valueOf(tempOpen).floatValue());
        fpoint.setClose(Float.valueOf(tempClose).floatValue());
        fpoint.setMaximum(Float.valueOf(tempHigh).floatValue());
        fpoint.setMinimum(Float.valueOf(tempLow).floatValue());
        fpoint.setVolume(Integer.parseInt(tempVol));

        fpoint.setDate(FormatUtil.getDateFrom(tempfirstDate));
        fpoint.setlYear(FormatUtil.getYear(templastDate));
        fpoint.setlMonth(FormatUtil.getMonth(templastDate));
        fpoint.setlDay(FormatUtil.getDay(templastDate));

        rawPoints.add(fpoint);

      }
      if (fc.isFillEmptyPoints()) {
        if (NumberOfPoints > m_NumberOfPoints) {
          for (int j = 0; j < NumberOfPoints - m_NumberOfPoints; j++) {
            StockData fpoint = new StockData();
            fpoint.setValid(false);
            rawPoints.add(fpoint);
          }
        }
      }

      for (int i = rawPoints.size() - 1; i >= 0; i--) {
        newChartData.getData().add(rawPoints.get(i));
      }

    } catch (Exception exception) {
      //System.out.println("Error when download profile" + exception.toString());
      exception.printStackTrace();
      return null;
    }

    return newChartData;
  }

  public ChartData getMonthlyData(RequestCommand fc) {

    String Code = fc.getCode();
    int NumberOfPoints = fc.getNumberOfPoint();

    String srcAddr = "http://??/" + monthlyInterface + "?code=" + Code + "&data_num=" + NumberOfPoints;// + "&startdate=2000-11-1";
    ChartData newChartData = new ChartData();
    newChartData.setCode(Code);
    newChartData.dataInterval = DataInterval.MONTHLY;

    try {
      URL Finet;
      Finet = new URL(srcAddr);

      URLConnection FinetConnection = Finet.openConnection();
      BufferedReader DS = new BufferedReader(new InputStreamReader(FinetConnection.getInputStream()));

      String RawData = DS.readLine();   //retrieve number Of points it retrieve and its name
      StringTokenizer htokens = new StringTokenizer(RawData);

      int m_NumberOfPoints = Integer.parseInt(htokens.nextToken(";"));
      newChartData.setName(htokens.nextToken(";"));
      newChartData.setName(htokens.nextToken(";"));

      if (m_NumberOfPoints < 10) {
        return null;
      }

      List<StockData> rawPoints = new
        ArrayList<StockData>(1000);
      for (int i = 0; i < m_NumberOfPoints; i++) {
        String tempfirstDate;
        String templastDate;
        String tempHigh;
        String tempLow;
        String tempOpen;
        String tempClose;
        String tempVol;

        StockData fpoint = new StockData();
        RawData = DS.readLine();   //retrieve a point line
        StringTokenizer tokens = new StringTokenizer(RawData);
        tempfirstDate = tokens.nextToken(";");
        templastDate = tokens.nextToken(";");
        tempHigh = tokens.nextToken(";");
        tempLow = tokens.nextToken(";");
        tempOpen = tokens.nextToken(";");
        tempClose = tokens.nextToken(";");
        tempVol = tokens.nextToken(";");
        fpoint.setOpen(Float.valueOf(tempOpen).floatValue());
        fpoint.setClose(Float.valueOf(tempClose).floatValue());
        fpoint.setMaximum(Float.valueOf(tempHigh).floatValue());
        fpoint.setMinimum(Float.valueOf(tempLow).floatValue());
        fpoint.setVolume(Integer.parseInt(tempVol));
        //fpoint.Year = FormatUtil.getYear(tempfirstDate);
        //fpoint.Month = FormatUtil.getMonth(tempfirstDate);
        //fpoint.Day = FormatUtil.getDay(tempfirstDate);
        fpoint.setDate(FormatUtil.getDateFrom(tempfirstDate));
        fpoint.setlYear(FormatUtil.getYear(templastDate));
        fpoint.setlMonth(FormatUtil.getMonth(templastDate));
        fpoint.setlDay(FormatUtil.getDay(templastDate));

        rawPoints.add(fpoint);

      }

      // add "empty" point at the end..
      if (fc.isFillEmptyPoints()) {
        if (NumberOfPoints > m_NumberOfPoints) {
          for (int j = 0; j < NumberOfPoints - m_NumberOfPoints; j++) {
            StockData fpoint = new StockData();
            fpoint.setValid(false);
            rawPoints.add(fpoint);
          }
        }
      }

      for (int i = rawPoints.size() - 1; i >= 0; i--) {
        newChartData.getData().add(rawPoints.get(i));
      }

    } catch (Exception exception) {
      //System.out.println("Error when download profile" + exception.toString());
      exception.printStackTrace();
      return null;
    }

    return newChartData;
  }

  public String getReadTimeQuote(String codeStr) {
    int code = Integer.parseInt(codeStr);
    try {
      String symbol = "00000" + code;
      symbol = symbol.substring(symbol.length() - 4);
      URL url = new URL("http://www.aastocks.com/tc/LTP/RTQuote.aspx?&symbol=" + symbol);
      // System.out.println(url.getRef());
      InputStream in = url.openStream();
      InputStreamReader ii = new InputStreamReader(in, StandardCharsets.UTF_8);
      //System.out.println(ii.getEncoding());
      BufferedReader reader = new BufferedReader(ii);
      String line = reader.readLine();
      String strPrice = "N/A";
      while (line != null) {
        // System.out.println(line);
        if (line.contains("<td>現價</td>")) {
          reader.readLine();//
          reader.readLine();//
          reader.readLine();//
          line = reader.readLine();//

          int startIndex = line.indexOf("\">");
          int endIndex = line.indexOf("</span>");
          strPrice = line.substring(startIndex + 2, endIndex);

          //System.out.println(strPrice);
          break;
        }
        line = reader.readLine();
      }
      return strPrice;
    } catch (IOException ex) {
      Logger.getLogger(RequestCommand.class.getName()).log(Level.SEVERE, null, ex);
      return "N/A";
    }

  }

  public ChartData getYahooDailyData() {
    String code = getCode();
    int NumberOfPoints = getNumberOfPoint();

    String strCode;
    try {
      int numCode = Integer.parseInt(code);
      strCode = "0000" + numCode + ".HK";
      strCode = strCode.substring(strCode.length() - 7);
    } catch (NumberFormatException e) {
      strCode = code;
    }

    log.info("Fetching daily data for symbol: " + strCode + " using provider: " + FConfig.DATA_SOURCE_PROVIDER);

    ChartData newChartData = new ChartData();
    newChartData.setCode(code);
    newChartData.dataInterval = DataInterval.DAILY;

    try {
      // Create provider based on configuration
      String apiKey = FConfig.getApiKeyForCurrentProvider();
      DataSourceProvider provider = DataSourceFactory.createProvider(FConfig.DATA_SOURCE_PROVIDER, apiKey);

      log.info("Using data provider: " + provider.getProviderName());

      // Fetch data from provider
      JSONObject json = provider.fetchDailyData(strCode, "1d");

      // Parse response based on provider type
      if (FConfig.DATA_SOURCE_PROVIDER == DataSourceFactory.ProviderType.ALPHA_VANTAGE) {
        return parseAlphaVantageResponse(json, newChartData, NumberOfPoints);
      } else if (FConfig.DATA_SOURCE_PROVIDER == DataSourceFactory.ProviderType.YAHOO_FINANCE) {
        return parseYahooFinanceResponse(json, newChartData, NumberOfPoints);
      } else {
        log.warning("Unsupported provider, trying Alpha Vantage format");
        return parseAlphaVantageResponse(json, newChartData, NumberOfPoints);
      }

    } catch (Exception exception) {
      log.log(Level.SEVERE, "Error fetching data for " + strCode, exception);
      return null;
    }
  }

  /**
   * Parse Alpha Vantage JSON response
   */
  private ChartData parseAlphaVantageResponse(JSONObject json, ChartData newChartData, int NumberOfPoints) {
    if (!json.has("Time Series (Daily)")) {
      log.warning("No data available in Alpha Vantage response");
      if (json.has("Information")) {
        log.warning("API Message: " + json.getString("Information"));
      }
      return null;
    }

    JSONObject timeSeries = json.getJSONObject("Time Series (Daily)");
    newChartData.setName(StockInfoStore.getInstance().getStockName(newChartData.getCode()));

    List<String> dates = new ArrayList<>(timeSeries.keySet());
    dates.sort((a, b) -> b.compareTo(a)); // Sort descending (newest first)

    List<StockData> rawPoints = new ArrayList<>();
    int m_NumberOfPoints = 0;

    for (String date : dates) {
      if (m_NumberOfPoints >= NumberOfPoints) break;
      m_NumberOfPoints++;

      JSONObject dayData = timeSeries.getJSONObject(date);
      StockData fpoint = new StockData();

      float open = Float.parseFloat(dayData.getString("1. open"));
      float high = Float.parseFloat(dayData.getString("2. high"));
      float low = Float.parseFloat(dayData.getString("3. low"));
      float close = Float.parseFloat(dayData.getString("4. close"));
      int volume = Integer.parseInt(dayData.getString("5. volume"));

      fpoint.setOpen(open);
      fpoint.setClose(close);
      fpoint.setMaximum(high);
      fpoint.setMinimum(low);
      fpoint.setVolume(volume);
      fpoint.setDate(FormatUtil.getDateFrom(date));

      if (fpoint.getOpen() == 0) {
        fpoint.setOpen(fpoint.getClose());
        fpoint.setMaximum(fpoint.getClose());
        fpoint.setMinimum(fpoint.getClose());
      }

      rawPoints.add(fpoint);
      getListener().OnProgress(0);
    }

    // Add empty points if needed
    if (isFillEmptyPoints() && NumberOfPoints > m_NumberOfPoints) {
      for (int j = 0; j < NumberOfPoints - m_NumberOfPoints; j++) {
        StockData fpoint = new StockData();
        fpoint.setValid(false);
        rawPoints.add(fpoint);
      }
    }

    // Reverse to oldest first
    for (int i = rawPoints.size() - 1; i >= 0; i--) {
      newChartData.getData().add(rawPoints.get(i));
    }

    return newChartData;
  }

  /**
   * Parse Yahoo Finance JSON response
   */
  private ChartData parseYahooFinanceResponse(JSONObject json, ChartData newChartData, int NumberOfPoints) {
    try {
      if (!json.has("chart")) {
        log.warning("No chart data in Yahoo Finance response");
        return null;
      }

      JSONObject chart = json.getJSONObject("chart");
      if (!chart.has("result") || chart.getJSONArray("result").length() == 0) {
        log.warning("No result in Yahoo Finance response");
        return null;
      }

      JSONObject result = chart.getJSONArray("result").getJSONObject(0);

      // Get metadata
      if (result.has("meta")) {
        JSONObject meta = result.getJSONObject("meta");
        if (meta.has("symbol")) {
          String symbol = meta.getString("symbol");
          newChartData.setName(StockInfoStore.getInstance().getStockName(symbol));
        }
      }

      // Get timestamps and indicators
      org.json.JSONArray timestamps = result.getJSONArray("timestamp");
      JSONObject indicators = result.getJSONObject("indicators");
      org.json.JSONArray quotes = indicators.getJSONArray("quote");

      if (quotes.length() == 0) {
        log.warning("No quote data in Yahoo Finance response");
        return null;
      }

      JSONObject quote = quotes.getJSONObject(0);
      org.json.JSONArray opens = quote.getJSONArray("open");
      org.json.JSONArray highs = quote.getJSONArray("high");
      org.json.JSONArray lows = quote.getJSONArray("low");
      org.json.JSONArray closes = quote.getJSONArray("close");
      org.json.JSONArray volumes = quote.getJSONArray("volume");

      List<StockData> rawPoints = new ArrayList<>();
      int m_NumberOfPoints = Math.min(timestamps.length(), NumberOfPoints);

      for (int i = timestamps.length() - m_NumberOfPoints; i < timestamps.length(); i++) {
        StockData fpoint = new StockData();

        long timestamp = timestamps.getLong(i);
        java.util.Date date = new java.util.Date(timestamp * 1000L);

        float open = opens.isNull(i) ? 0 : (float) opens.getDouble(i);
        float high = highs.isNull(i) ? 0 : (float) highs.getDouble(i);
        float low = lows.isNull(i) ? 0 : (float) lows.getDouble(i);
        float close = closes.isNull(i) ? 0 : (float) closes.getDouble(i);
        int volume = volumes.isNull(i) ? 0 : volumes.getInt(i);

        fpoint.setOpen(open);
        fpoint.setClose(close);
        fpoint.setMaximum(high);
        fpoint.setMinimum(low);
        fpoint.setVolume(volume);
        fpoint.setDate(date);

        if (fpoint.getOpen() == 0 && fpoint.getClose() != 0) {
          fpoint.setOpen(fpoint.getClose());
          fpoint.setMaximum(fpoint.getClose());
          fpoint.setMinimum(fpoint.getClose());
        }

        rawPoints.add(fpoint);
        getListener().OnProgress(0);
      }

      // Add empty points if needed
      if (isFillEmptyPoints() && NumberOfPoints > m_NumberOfPoints) {
        for (int j = 0; j < NumberOfPoints - m_NumberOfPoints; j++) {
          StockData fpoint = new StockData();
          fpoint.setValid(false);
          rawPoints.add(0, fpoint); // Add at beginning for Yahoo
        }
      }

      // Add to chart data (already in chronological order from oldest to newest)
      newChartData.getData().addAll(rawPoints);

      return newChartData;

    } catch (Exception e) {
      log.log(Level.SEVERE, "Error parsing Yahoo Finance response", e);
      return null;
    }
  }
}
