package com.ic.data;

// the command for the engine to download data,

import com.ic.util.FormatUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.Date;
import java.util.StringTokenizer;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

import static com.ic.data.RequestCommand.CommandType.*;

public class RequestCommand {

    public enum CommandType {
        DAILY, WEEKLY, MONTHLY, INTRADAY, REALTIMEQUOTE
    }
    // Static attributes.
    //public static final int DAILY = 0;
    //public static final int WEEKLY = 1;
    //public static final int MONTHLY = 2;
    //public static final int INTRADAY = 3;
    //public static final int REALTIMEQUOTE = 4;

    public static final int TYPE_DOWNLOAD_RIGHT_CHART = 0;
    public static final int TYPE_DOWNLOAD_LEFT_CHART = 2;


    private final String dailyInterface = "newchart/getDaily.php";
    private final String weeklyInterface = "newchart/getWeekly.php";
    private final String monthlyInterface = "newchart/getMonthly.php";
    private final String intradayInterface = "newchart/getIntra.php";
    private int actionType = TYPE_DOWNLOAD_RIGHT_CHART; // the action of the RequestCommand
    private CommandType dType = DAILY;                          // the type of the chart
    private String sKey = "RMain1";                     // the key of the chart used to id the chart.
    private int code;                                   // the code to download
    private int numberOfPoint = 100;                    // Number of point to download
    private int intradayInterval = 1;                  // for intraday only
    private boolean isFillEmptyPoints = false;
    private ChartDataServiceCallback reference;              // the reference to the object that create this command.

    public RequestCommand(int sCode, int atype, CommandType type, String key, int num, int intervals, boolean fillEmptyPoints, ChartDataServiceCallback re) {
        code = sCode;
        actionType = atype;
        dType = type;
        sKey = key;
        numberOfPoint = num;
        intradayInterval = intervals;
        reference = re;
        isFillEmptyPoints = fillEmptyPoints;
    }

    public RequestCommand(int sCode, CommandType type, ChartDataServiceCallback re) {
        code = sCode;
        dType = type;
        reference = re;
    }

    // for test only......
    public static ChartData getTestData(int Code, int NumberOfPoints) {

        ChartData newChartData = new ChartData();

        newChartData.setCode(Code);

        SPoint oldfpoint = new SPoint();
        newChartData.dataType = ChartData.WEEKLY;
        newChartData.setEName("ABC COMPANY");
        newChartData.setCName("Chinese Name");

        for (int i = 0; i < NumberOfPoints; i++) {
            SPoint fpoint = new SPoint();

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
            newChartData.getData().addElement(fpoint);
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

    public RequestCommand.CommandType getChartType() {
        return dType;
    }

    public int getActionType() {
        return actionType;
    }

    public int getCode() {
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
            case REALTIMEQUOTE:
                String price = getReadTimeQuote(currentCommand.getCode());
                getListener().OnReceivedChartData(currentCommand, price);
                break;
        }
    }

    public ChartData getIntradayData(RequestCommand fc) {

        int Code = fc.getCode();
        int NumberOfPoints = fc.getNumberOfPoint();
        int intervals = fc.getIntadayInterval();
        if (intervals != 1 && intervals != 10) {
            intervals = 10;
        }

        //String srcAddr = "http://218.252.190.136/FMEQuoteBase/" + intradayInterface + "?code=" + Code + "&min=" + intervals;
        //String srcAddr = "http://203.161.232.72/FMEQuoteBase/" + intradayInterface + "?code=" + Code + "&min=" + intervals;
        String srcAddr = "http://2103.161.232.72/FMEQuoteBase/" + intradayInterface + "?code=" + Code + "&min=" + intervals;

        ChartData newChartData = new ChartData();
        newChartData.setCode(Code);
        newChartData.dataType = ChartData.INTRADAY;
        newChartData.setIntradayInterval(intervals);  // record the intervals 1,5,or 10

        try {
            URL Finet;
            Finet = new URL(srcAddr);

            URLConnection FinetConnection = Finet.openConnection();
            BufferedReader DS = new BufferedReader(new InputStreamReader(FinetConnection.getInputStream()));

            String RawData = DS.readLine();   //retrieve number Of points it retrieve and its name
            StringTokenizer htokens = new StringTokenizer(RawData);

            int m_NumberOfPoints = Integer.parseInt(htokens.nextToken(";"));
            newChartData.setEName(htokens.nextToken(";"));
            newChartData.setCName(htokens.nextToken(";"));
           // System.out.println("NumberOfPoints: " + m_NumberOfPoints + " Ename " + newChartData.getEName() + " CName " + newChartData.getCName());

            if (m_NumberOfPoints == 0) {
                return null;
            }


            Vector rawPoints = new Vector();
            for (int i = 0; i < m_NumberOfPoints; i++) {
                String tempDateTime;
                String tempHigh;
                String tempLow;
                String tempOpen;
                String tempClose;
                String tempVol;

                SPoint fpoint = new SPoint();
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

                rawPoints.addElement(fpoint);
            }


            //System.out.println("Intraday pass 1");

            // the starting time of each date; 10:00
            int currentHour = 10;
            int currentMinute = 00;
            int tempH, tempM;
            int i = rawPoints.size() - 1;
            while (i >= 0) {
                SPoint fpoint = (SPoint) rawPoints.elementAt(i);
                int timeStamp = fpoint.getHour() * 60 + fpoint.getMinute();
                int currentTimeStamp = currentHour * 60 + currentMinute;
                if (timeStamp < currentTimeStamp) {
                    SPoint pp = (SPoint) rawPoints.elementAt(i);
                    if (!fc.isFillEmptyPoints() || newChartData.getData().size() < NumberOfPoints) {
                        newChartData.getData().addElement(rawPoints.elementAt(i));
                    }
                    i--;
                } else if (timeStamp == currentTimeStamp) {
                    if (!fc.isFillEmptyPoints() || newChartData.getData().size() < NumberOfPoints) {
                        newChartData.getData().addElement(rawPoints.elementAt(i));
                    }
                    tempH = FormatUtil.getNextHour(currentHour, currentMinute, fc.getIntadayInterval());
                    tempM = FormatUtil.getNextMinute(currentHour, currentMinute, fc.getIntadayInterval());
                    currentHour = tempH;
                    currentMinute = tempM;
                    i--;
                } else if (timeStamp > currentTimeStamp) {
                    SPoint newPoint = new SPoint();
                    newPoint.setValid(false);
                    newPoint.setHour(currentHour);
                    newPoint.setMinute(currentMinute);
                    if (currentTimeStamp <= (12 * 60 + 30) || currentTimeStamp >= (14 * 60 + 30)) {
                        if (!fc.isFillEmptyPoints() || newChartData.getData().size() < NumberOfPoints) {
                            newChartData.getData().addElement(newPoint);
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
                        SPoint fpoint = new SPoint();
                        fpoint.setValid(false);
                        newChartData.getData().addElement(fpoint);
                    }
                } else if (NumberOfPoints < newChartData.getData().size()) {
                    int deletecount = NumberOfPoints - newChartData.getData().size();
                    for (int k = 0; k < deletecount; k++) {
                        newChartData.getData().removeElementAt(newChartData.getData().size() - 1);
                    }
                }

            }

            for (int k = 1; k < newChartData.getData().size(); k++) {
                SPoint fpoint1 = (SPoint) newChartData.getData().elementAt(k - 1);
                SPoint fpoint2 = (SPoint) newChartData.getData().elementAt(k);
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
                SPoint fpoint1 = (SPoint) newChartData.getData().elementAt(k + 1);
                SPoint fpoint2 = (SPoint) newChartData.getData().elementAt(k);
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
                SPoint fpoint = (SPoint) newChartData.getData().elementAt(k);
            }

        } catch (Exception exception) {
            //System.out.println("Error when download profile");
            exception.printStackTrace();
            return null;
        }
        return newChartData;
    }

    public ChartData getDailyData(RequestCommand fc) {

        int Code = fc.getCode();
        int NumberOfPoints = fc.getNumberOfPoint();

        //String srcAddr = "http://203.161.232.72/FMEQuoteBase/" + dailyInterface + "?code=" + Code + "&data_num=" + NumberOfPoints;// + "&startdate=2000-11-1";
        String srcAddr = "http://218.252.190.136/FMEQuoteBase/" + dailyInterface + "?code=" + Code + "&data_num=" + NumberOfPoints;// + "&startdate=2000-11-1";

        ChartData newChartData = new ChartData();
        newChartData.setCode(Code);
        newChartData.dataType = ChartData.DAILY;

        try {
            URL Finet;
            Finet = new URL(srcAddr);

            URLConnection FinetConnection = Finet.openConnection();
            BufferedReader DS = new BufferedReader(new InputStreamReader(FinetConnection.getInputStream()));

            String RawData = DS.readLine();   //retrieve number Of points it retrieve and its name
            StringTokenizer htokens = new StringTokenizer(RawData);

            int m_NumberOfPoints = Integer.parseInt(htokens.nextToken(";"));
            newChartData.setEName(htokens.nextToken(";"));
            newChartData.setCName(htokens.nextToken(";"));

            if (m_NumberOfPoints < 10) {
                return null;
            }


            Vector rawPoints = new Vector();
            for (int i = 0; i < m_NumberOfPoints; i++) {
                String tempDate;
                String tempHigh;
                String tempLow;
                String tempOpen;
                String tempClose;
                String tempVol;

                SPoint fpoint = new SPoint();
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

                rawPoints.addElement(fpoint);
            }

            // add "empty" point at the end..
            if (fc.isFillEmptyPoints()) {
                if (NumberOfPoints > m_NumberOfPoints) {
                    for (int j = 0; j < NumberOfPoints - m_NumberOfPoints; j++) {
                        SPoint fpoint = new SPoint();
                        fpoint.setValid(false);
                        rawPoints.addElement(fpoint);
                    }
                }
            }
            for (int i = rawPoints.size() - 1; i >= 0; i--) {
                newChartData.getData().addElement(rawPoints.elementAt(i));
            }

        } catch (Exception exception) {
            exception.printStackTrace();
            return null;
        }

        return newChartData;
    }

    public ChartData getWeeklyData(RequestCommand fc) {
        int Code = fc.getCode();
        int NumberOfPoints = fc.getNumberOfPoint();

        String srcAddr = "http://203.161.232.72/FMEQuoteBase/" + weeklyInterface + "?code=" + Code + "&data_num=" + NumberOfPoints;// + "&startdate=2000-11-1";

        ChartData newChartData = new ChartData();
        newChartData.setCode(Code);
        newChartData.dataType = ChartData.WEEKLY;

        try {
            URL Finet;
            Finet = new URL(srcAddr);

            URLConnection FinetConnection = Finet.openConnection();
            BufferedReader DS = new BufferedReader(new InputStreamReader(FinetConnection.getInputStream()));

            String RawData = DS.readLine();   //retrieve number Of points it retrieve and its name
            StringTokenizer htokens = new StringTokenizer(RawData);

            int m_NumberOfPoints = Integer.parseInt(htokens.nextToken(";"));
            newChartData.setEName(htokens.nextToken(";"));
            newChartData.setCName(htokens.nextToken(";"));

            if (m_NumberOfPoints < 10) {
                return null;
            }


            Vector rawPoints = new Vector();
            for (int i = 0; i < m_NumberOfPoints; i++) {
                String tempfirstDate;
                String templastDate;
                String tempHigh;
                String tempLow;
                String tempOpen;
                String tempClose;
                String tempVol;

                SPoint fpoint = new SPoint();
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

                rawPoints.addElement(fpoint);

            }
            if (fc.isFillEmptyPoints()) {
                if (NumberOfPoints > m_NumberOfPoints) {
                    for (int j = 0; j < NumberOfPoints - m_NumberOfPoints; j++) {
                        SPoint fpoint = new SPoint();
                        fpoint.setValid(false);
                        rawPoints.addElement(fpoint);
                    }
                }
            }

            for (int i = rawPoints.size() - 1; i >= 0; i--) {
                newChartData.getData().addElement(rawPoints.elementAt(i));
            }

        } catch (Exception exception) {
            //System.out.println("Error when download profile" + exception.toString());
            exception.printStackTrace();
            return null;
        }

        return newChartData;
    }

    public ChartData getMonthlyData(RequestCommand fc) {

        int Code = fc.getCode();
        int NumberOfPoints = fc.getNumberOfPoint();

        //String srcAddr = "http://218.252.190.136/FMEQuoteBase/" + monthlyInterface + "?code=" + Code + "&data_num=" + NumberOfPoints;// + "&startdate=2000-11-1";
        String srcAddr = "http://203.161.232.72/FMEQuoteBase/" + monthlyInterface + "?code=" + Code + "&data_num=" + NumberOfPoints;// + "&startdate=2000-11-1";
        ChartData newChartData = new ChartData();
        newChartData.setCode(Code);
        newChartData.dataType = ChartData.MONTHLY;

        try {
            URL Finet;
            Finet = new URL(srcAddr);

            URLConnection FinetConnection = Finet.openConnection();
            BufferedReader DS = new BufferedReader(new InputStreamReader(FinetConnection.getInputStream()));

            String RawData = DS.readLine();   //retrieve number Of points it retrieve and its name
            StringTokenizer htokens = new StringTokenizer(RawData);

            int m_NumberOfPoints = Integer.parseInt(htokens.nextToken(";"));
            newChartData.setEName(htokens.nextToken(";"));
            newChartData.setCName(htokens.nextToken(";"));

            if (m_NumberOfPoints < 10) {
                return null;
            }

            Vector rawPoints = new Vector();
            for (int i = 0; i < m_NumberOfPoints; i++) {
                String tempfirstDate;
                String templastDate;
                String tempHigh;
                String tempLow;
                String tempOpen;
                String tempClose;
                String tempVol;

                SPoint fpoint = new SPoint();
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

                rawPoints.addElement(fpoint);

            }

            // add "empty" point at the end..
            if (fc.isFillEmptyPoints()) {
                if (NumberOfPoints > m_NumberOfPoints) {
                    for (int j = 0; j < NumberOfPoints - m_NumberOfPoints; j++) {
                        SPoint fpoint = new SPoint();
                        fpoint.setValid(false);
                        rawPoints.addElement(fpoint);
                    }
                }
            }

            for (int i = rawPoints.size() - 1; i >= 0; i--) {
                newChartData.getData().addElement(rawPoints.elementAt(i));
            }

        } catch (Exception exception) {
            //System.out.println("Error when download profile" + exception.toString());
            exception.printStackTrace();
            return null;
        }

        return newChartData;
    }

    public String getReadTimeQuote(int code) {
        try {
            String symbol = "00000" + code;
            symbol = symbol.substring(symbol.length() - 4);
            URL url = new URL("http://www.aastocks.com/tc/LTP/RTQuote.aspx?&symbol=" + symbol);
            // System.out.println(url.getRef());
            InputStream in = url.openStream();
            InputStreamReader ii = new InputStreamReader(in, "UTF8");
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

        int Code = getCode();
        int NumberOfPoints = getNumberOfPoint();

        String strCode = "0000" + Code + ".HK";
        strCode = strCode.substring(strCode.length() - 7);

        Date now = new Date();
        //int cday = 31;
        //int cmonth = now.getMonth()+1;
        //int cyear = now.getYear() +2010;
        //String srcAddr = "http://table.finance.yahoo.com/table.csv?a=1&b=2&c=2003&d=" + cday + "&e=" + cmonth + "&f=2004&s=" + strCode + "&y=0&g=d&ignore=.csv";
        //String srcAddr = "http://ichart.yahoo.com/table.csv?a=0&b=2&c=1980&d=" + cday + "&e=" + cmonth + "&f=" + cyear + "&s=" + strCode + "&y=0&g=d&ignore=.csv";

        String srcAddr = "http://ichart.yahoo.com/table.csv?a=5&b=5&c=2010&s=" + strCode;

        System.out.println(srcAddr);
        ChartData newChartData = new ChartData();
        newChartData.setCode(Code);
        newChartData.dataType = ChartData.DAILY;

        try {
            URL Finet;
            Finet = new URL(srcAddr);

            URLConnection FinetConnection = Finet.openConnection();
            BufferedReader DS = new BufferedReader(new InputStreamReader(FinetConnection.getInputStream()));

            String RawData = DS.readLine();   //Header Columns

            StringTokenizer htokens = new StringTokenizer(RawData);

            int m_NumberOfPoints = 0;

            newChartData.setEName(StockInfoStore.getInstance().getStockName(String.valueOf(code)));
            newChartData.setCName(StockInfoStore.getInstance().getStockName(String.valueOf(code)));

            Vector rawPoints = new Vector();
            String line = DS.readLine();
            while (line != null && !line.startsWith("<!--")) {
                m_NumberOfPoints++;
                String tempDate;
                String tempHigh;
                String tempLow;
                String tempOpen;
                String tempClose;
                String tempVol;

                SPoint fpoint = new SPoint();
                RawData = line;//DS.readLine();   //retrieve a point line
                // System.out.println(line);
                StringTokenizer tokens = new StringTokenizer(RawData);
                tempDate = tokens.nextToken(",");
                tempOpen = tokens.nextToken(",");
                tempHigh = tokens.nextToken(",");
                tempLow = tokens.nextToken(",");
                tempClose = tokens.nextToken(",");
                tempVol = tokens.nextToken(",");
                fpoint.setOpen(Float.valueOf(tempOpen).floatValue());
                fpoint.setClose(Float.valueOf(tempClose).floatValue());
                fpoint.setMaximum(Float.valueOf(tempHigh).floatValue());
                fpoint.setMinimum(Float.valueOf(tempLow).floatValue());
                fpoint.setVolume(Integer.parseInt(tempVol));
                //fpoint.Year = FormatUtil.getYahooYear(tempDate);
                //fpoint.Month = FormatUtil.getYahooMonth(tempDate);
                //fpoint.Day = FormatUtil.getYahooDay(tempDate);
                fpoint.setDate(FormatUtil.getDateFrom(tempDate));
                if (fpoint.getOpen() == 0) {
                    fpoint.setOpen(fpoint.getClose());
                    fpoint.setMaximum(fpoint.getClose());
                    fpoint.setMinimum(fpoint.getClose());

                }
                rawPoints.addElement(fpoint);
                line = DS.readLine();
                getListener().OnProgress(0);
            }

            // add "empty" point at the end..
            if (isFillEmptyPoints()) {
                if (NumberOfPoints > m_NumberOfPoints) {
                    for (int j = 0; j < NumberOfPoints - m_NumberOfPoints; j++) {
                        SPoint fpoint = new SPoint();
                        fpoint.setValid(false);
                        rawPoints.addElement(fpoint);
                    }
                }
            }
            for (int i = rawPoints.size() - 1; i >= 0; i--) {
                newChartData.getData().addElement(rawPoints.elementAt(i));
            }

        } catch (Exception exception) {
           // System.out.println("Error when download profile");
            exception.printStackTrace();
            return null;
        }

        return newChartData;
    }
}
