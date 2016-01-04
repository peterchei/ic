package com.ic.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class FormatUtil {

    /* return 0001 given 1 */
    public static String getCode(int code) {
        String cc = "0000" + code;
        cc = cc.substring(cc.length() - 4);
        return cc;
    }

    public static int getHour(String dateTime) {
        int a = dateTime.indexOf(":");
        String tempString = dateTime.substring(0, a);
        int Hour = Integer.parseInt(tempString);
        return Hour;
    }

    public static int getMinute(String dateTime) {
        int a = dateTime.indexOf(":");
        String tempString = dateTime.substring(a + 1, a + 3);
        int Minute = Integer.parseInt(tempString);
        //System.out.println("Minute:" + Minute);
        return Minute;
    }

    public static int getYahooYear(String date) {
        int a = date.indexOf("-");
        int b = date.lastIndexOf("-");
        String tempString = date.substring(b + 1);
        int Year = Integer.parseInt(tempString);
        return Year;
    }

    public static Date getDateFrom(String date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            return sdf.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return new Date();
    }

    public static int getYahooMonth(String date) {
        int a = date.indexOf("-");
        int b = date.lastIndexOf("-");
        String tempString = date.substring(a + 1, b);

        if (tempString.equals("Jan")) {
            return 1;
        } else if (tempString.equals("Feb")) {
            return 2;
        } else if (tempString.equals("Mar")) {
            return 3;
        } else if (tempString.equals("Apr")) {
            return 4;
        } else if (tempString.equals("May")) {
            return 5;
        } else if (tempString.equals("Jun")) {
            return 6;
        } else if (tempString.equals("Jul")) {
            return 7;
        } else if (tempString.equals("Aug")) {
            return 8;
        } else if (tempString.equals("Sep")) {
            return 9;
        } else if (tempString.equals("Oct")) {
            return 10;
        } else if (tempString.equals("Nov")) {
            return 11;
        } else if (tempString.equals("Dec")) {
            return 12;
        }
        return 1;

    }

    public static String formatDate(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("MMM");
        return sdf.format(date);
    }

    public static int getYahooDay(String date) {
        int a = date.indexOf("-");
        int b = date.lastIndexOf("-");
        String tempString = date.substring(0, a);
        int Day = Integer.parseInt(tempString);
        return Day;
    }

    public static int getYear(String date) {
        int a = date.indexOf("-");
        int b = date.lastIndexOf("-");
        String tempString = date.substring(b);
        int Year = Integer.parseInt(tempString);
        return Year;
    }

    public static int getMonth(String date) {
        int a = date.indexOf("-");
        int b = date.lastIndexOf("-");
        String tempString = date.substring(a + 1, b);
        int Month = Integer.parseInt(tempString);
        return Month;
    }

    public static int getDay(String date) {
        int a = date.indexOf("-");
        int b = date.lastIndexOf("-");
        String tempString = date.substring(b + 1);
        int Day = Integer.parseInt(tempString);
        return Day;
    }

    public static String formatOBV(double value) {
        int ivalue = 0;
        double absValue = Math.abs(value);
        if (absValue < 1000 && absValue >= 0) {
            ivalue = Math.round((float) value);
            return String.valueOf(ivalue) + "K";
        } else if (absValue < 1000000 && absValue >= 1000) {
            ivalue = Math.round((float) (value / 1000f));
            return String.valueOf(ivalue) + "M";
        } else if (absValue >= 1000000) {
            ivalue = Math.round((float) (value / 1000000f));
            return String.valueOf(ivalue) + "B";
        }
        return "X";
    }

    public static String formatInteger(double value) {
        int ivalue = 0;
        if (value < 1000 && value >= 0) {
            ivalue = Math.round((float) value);
            return String.valueOf(ivalue);
        } else if (value < 1000000 && value >= 1000) {
            ivalue = Math.round((float) (value / 1000f));
            return String.valueOf(ivalue) + "K";
        } else if (value >= 1000000) {
            ivalue = Math.round((float) (value / 1000000f));
            return String.valueOf(ivalue) + "M";
        }
        return "X";
    }


    // format double to 0.xx format
    public static String formatData3(double value) {
        long ivalue = Math.round((float) value * 1000f);
        float fvalue = ivalue / 1000f;
        String Svalue = String.valueOf(fvalue);
        if (Svalue.indexOf(".") > 0) {
            Svalue = Svalue + "0000000";
            String m_result = Svalue.substring(0, Svalue.indexOf(".") + 4);
            return m_result;
        } else {
            return Svalue + ".000";
        }
    }

    public static boolean isFloat(String ss) {
        try {
            float ivalue = Float.valueOf(ss).floatValue();
            String iss = String.valueOf(ivalue);
            return true;

        } catch (Exception e) {
            return false;
        }
    }

    public static boolean isNumber(String ss) {
        try {
            Integer.parseInt(ss);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    // format double to 0.xx format
    public static String formatData2(double value) {
        long ivalue = Math.round((float) value * 100f);
        float fvalue = ivalue / 100f;
        String Svalue = String.valueOf(fvalue);
        if (Svalue.indexOf(".") > 0) {
            Svalue = Svalue + "0000000";
            String m_result = Svalue.substring(0, Svalue.indexOf(".") + 3);
            return m_result;
        }
        return "XXX";
    }

    public static String formatTime(int mHour, int mMinute) {
        String ho, mi;
        String mtime;
        ho = "000" + mHour;
        mi = "000" + mMinute;
        mtime = ho.substring(ho.length() - 2) + ":" + mi.substring(mi.length() - 2);
        return mtime;
    }

    public static int getNextHour(int currentHour, int currentMinute, int intervals) {
        currentMinute = currentMinute + intervals;
        if (currentMinute >= 60) {
            currentHour = (currentHour + 1) % 24;
        }
        return currentHour;
    }

    public static int getNextMinute(int currentHour, int currentMinute, int intervals) {
        currentMinute = (currentMinute + intervals) % 60;
        return currentMinute;
    }


}
