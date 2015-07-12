/**
 * Title:        <p>
 * Description:  there are some static function used to format datastring
 * Copyright:    Copyright (c) <p>
 * Company:      <p>
 * @author       Hung
 * @version 1.0
 */
package fme;

public class FormatData {

//Given the inString = XXXXX.OOOOO it return XXXXXX
    public static String GetInteger(String inString) throws Exception {
        if (inString.indexOf(".") < 0) {
            return inString;
        }
        try {
            String outString = inString.substring(0, inString.indexOf("."));
            return outString;
        } catch (Exception ee) {
            return "#";
        }
    }

    public static String FormatStringI(String inString, int Slength) throws
            Exception {
        String outString = "000";
        //return inString;
        //System.out.println(inString);

        try {
            int keyIndex = inString.indexOf(".");
            if (keyIndex > 0) {
                inString = inString.substring(0, keyIndex);
            }

            long sValue = (long) Long.parseLong(inString);
            outString = Long.toString(sValue);

            if (sValue > 0 && sValue < 9999) {
                outString = String.valueOf(sValue);
            } else if (sValue >= 9999 && sValue < 1000000) {
                sValue = (long) sValue / 1000;
                outString = String.valueOf(sValue);
                outString = outString + "K";
            } else if (sValue >= 1000000) {
                sValue = sValue / 1000000;
                outString = String.valueOf(sValue);
                outString = outString + "M";
            }
        } catch (Exception ee) {
            return "#";
        }
        return outString;
    }

    public static String FormatPrice(String inString) {
        try {
            float price = Float.valueOf(inString).floatValue();
            if (price < 100) {
                return inString;
            } else if (price < 100) {
//      return Float.toString( ((float) Math.round(price*100)) /  100);
            } else {
//      return Float.toString( ((float) Math.round(price*10)) /  10);
            }

        } catch (NumberFormatException ne) {
        }
        return inString;
    }

    public static String FormatString(String inString, int Slength) throws
            Exception {
        String outString = new String(inString);
        //return inString;

        try {

            Float sValue = Float.valueOf(inString);
            double dvalue = sValue.floatValue();

            if (dvalue > 0 && dvalue < 9999) {
                outString = roundOff((float) dvalue, 1000);
            } else if (dvalue >= 9999 && dvalue < 1000000) {
                dvalue = dvalue / 1000.0;
                outString = String.valueOf(dvalue);
                outString = outString + "00000000000000";
                outString = outString.substring(0, Slength - 1) + "K";
            } else if (dvalue >= 1000000 && dvalue < 1000000000) {
                dvalue = dvalue / 1000000.0;
                outString = String.valueOf(dvalue);
                outString = outString + "00000000000000";
                outString = outString.substring(0, Slength - 1) + "M";
            }
        } catch (Exception ee) {
            return "#";
        }
        return outString;
    }

    public static String FormatString2(String inString, int precision) {
        String outString = "0000";
        try {
            int decimal = (int) Math.pow(10, precision);
            Float sValue = Float.valueOf(inString);
            double dvalue = sValue.floatValue();

            if (dvalue > 0 && dvalue < 999) {
                //  outString = String.valueOf(dvalue);
                outString = roundOff((float) dvalue, decimal);
                //       outString  = inString;
            } else if (dvalue >= 1000 && dvalue < 1000000) {
                dvalue = dvalue / 1000.0;
                //System.out.println(dvalue);
                dvalue = ((double) Math.round(dvalue * decimal) / decimal);
                //System.out.println(dvalue);
                if (precision == 0) {
                    outString = Integer.toString((int) dvalue) + "K";
                } else {
                    outString = Double.toString(dvalue) + "K";

                }
            } else if (dvalue >= 1000000) {
                dvalue = dvalue / 1000000.0;
                dvalue = ((double) Math.round(dvalue * decimal) / decimal);
                if (precision == 0) {
                    outString = Integer.toString((int) dvalue) + "M";
                } else {
                    outString = Double.toString(dvalue) + "M";
                }
            }
        } catch (Exception ee) {
            return "#";
        }
        return outString;
    }

    public float changeUnit(float in, String unit) throws Exception {
        if (unit.equals("K")) {
            in = in / (float) 1000.0;
        } else if (unit.equals("M")) {
            in = in / (float) 1000000.0;

        }
        return in;
    }

    public static String roundOff(float in) throws Exception {
        return Integer.toString(Math.round(in));
    }

    public static String roundOff(String in) {
        try {
            return Integer.toString(Math.round(Float.valueOf(in).floatValue()));
        } catch (Exception ex) {
            return in;
        }
    }

    public static String roundOff(float in, float roundF) throws Exception {
        return Float.toString((float) Math.round(in * roundF) / roundF);
    }
}