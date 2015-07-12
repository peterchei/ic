package teletext.data;

import teletext.apps.ui.FTAConfig;

import java.util.Vector;


public class ChartData {
//the defination of 4 data type, for 1,2,3, data is FPoints.
    public static final int INTRADAY = 0;
    public static final int DAILY = 1;
    public static final int WEEKLY = 2;
    public static final int MONTHLY = 3;

    public int dataType = DAILY;
    // The stock data, price
    public Vector data = new Vector();
    // the TA data............
    public Vector TAdata = new Vector();           // store the TA point
    public FTAConfig fTAconfig = new FTAConfig(); // store the setting of TA chart

    //Stock information
    public int Code = 0;
    public String EName = "";
    public String CName = "";
    public int intradayInterval = 1;//5, or 10;

    public ChartData() {
    }

    //it return the Maximum value of data in that range, getMaximum(0,0) means test all fpoints.
    public double getMaximumVolume(int startIndex, int endIndex) {
        double Maximum = 0;
        if (startIndex == 0 && endIndex == 0) {
            startIndex = 0;
            endIndex = data.size() - 1;
        }
        for (int i = startIndex; i <= endIndex; i++) {
            FPoint fpoint = (FPoint) data.elementAt(i);
            if (Maximum < fpoint.getVolume()) {
                Maximum = fpoint.getVolume();
            }
        }
        return Maximum;
    }


    //it return the Maximum value of data in that range, getMaximum(0,0) means test all fpoints.
    //cType = 0 : value, 1 : percentage
    public double getMaximum(int startIndex, int endIndex, String cType) {
        double Maximum = 0;
        if (startIndex == 0 && endIndex == 0) {
            startIndex = 0;
            endIndex = data.size() - 1;
        }
        for (int i = startIndex; i <= endIndex; i++) {
            FPoint fpoint = (FPoint) data.elementAt(i);


            if (!fpoint.isValid()) continue;
            double Mvalue = 0;
            if (cType == "STOCK") {
                Mvalue = fpoint.getMaximum();
            } else if (cType == "PERCENTAGE") {
                Mvalue = fpoint.getPercent();
            } else if (cType == "MACD") {
                FTAPoint fTApoint = (FTAPoint) TAdata.elementAt(i);
                Mvalue = Math.max(fTApoint.MACD1, fTApoint.MACD2);
                Mvalue = Math.max(Mvalue, fTApoint.MACDdiff);
            } else if (cType == "OBV") {
                FTAPoint fTApoint = (FTAPoint) TAdata.elementAt(i);
                Mvalue = fTApoint.OBV;
            }

            if (Maximum < Mvalue) {
                Maximum = Mvalue;
            }
        }
        return Maximum;
    }

    //it returns the minimum value of data in that range
    public double getMinimum(int startIndex, int endIndex, String cType) {
        double Minimum = 100000;
        if (startIndex == 0 && endIndex == 0) {
            startIndex = 0;
            endIndex = data.size() - 1;
        }

        for (int i = startIndex; i <= endIndex; i++) {
            FPoint fpoint = (FPoint) data.elementAt(i);
            if (!fpoint.isValid()) continue;
            double Mvalue = 0f;
            if (cType == "STOCK") {
                Mvalue = fpoint.getMinimum();
            } else if (cType == "PERCENTAGE") {
                Mvalue = fpoint.getPercent();
            } else if (cType == "MACD") {
                FTAPoint fTApoint = (FTAPoint) TAdata.elementAt(i);
                Mvalue = Math.min(fTApoint.MACD1, fTApoint.MACD2);
                Mvalue = Math.min(Mvalue, fTApoint.MACDdiff);
            } else if (cType == "OBV") {
                FTAPoint fTApoint = (FTAPoint) TAdata.elementAt(i);
                Mvalue = fTApoint.OBV;
            }

            if (Minimum > Mvalue) {
                Minimum = Mvalue;
            }
        }
        return Minimum;
    }


    public void calculatePercentage(int refIndex) {
        FPoint refPoint = (FPoint) data.elementAt(refIndex);
        while (!refPoint.isValid()) {
            refIndex++;
            if (refIndex < this.data.size()) {
                refPoint = (FPoint) data.elementAt(refIndex);
            } else {
                break;
            }
        }

        //System.out.println("reference Point : " + refpoint.getClose());

        for (int i = 0; i < data.size(); i++) {
            FPoint fpoint = (FPoint) data.elementAt(i);
            // FPoint fpoint2 = (FPoint) data.elementAt(i-1);
            try {
                fpoint.setPercent(fpoint.getClose() / refPoint.getClose() * 100f);

            } catch (Exception ce) {
                fpoint.setValid(false);

            }
        }
    }

    //see. P.19 of the book
    public void calculateExponentialMovingAverage(int n1, int n2, int n3) {
        TAdata.removeAllElements();
        //create TAdata
        for (int i = 0; i < this.data.size(); i++) {
            FTAPoint fTApoint = new FTAPoint();
            fTApoint.isValid = false;
            TAdata.addElement(new FTAPoint());
        }

        float smoothFactor1 = 2f / (n1 + 1f);
        float smoothFactor2 = 2f / (n2 + 1f);
        float smoothFactor3 = 2f / (n3 + 1f);


        FPoint fpoint = (FPoint) data.elementAt(0);
        FTAPoint fTApoint = (FTAPoint) TAdata.elementAt(0);
        fTApoint.MA1 = fpoint.getClose();
        fTApoint.MA2 = fpoint.getClose();
        fTApoint.MA3 = fpoint.getClose();

        for (int k = 1; k < data.size(); k++) {
            fpoint = (FPoint) data.elementAt(k);
            FTAPoint fTApoint1 = (FTAPoint) TAdata.elementAt(k - 1);
            FTAPoint fTApoint2 = (FTAPoint) TAdata.elementAt(k);
            fTApoint2.MA1 = (1 - smoothFactor1) * fTApoint1.MA1 + smoothFactor1 * fpoint.getClose();
            fTApoint2.MA2 = (1 - smoothFactor2) * fTApoint1.MA2 + smoothFactor2 * fpoint.getClose();
            fTApoint2.MA3 = (1 - smoothFactor3) * fTApoint1.MA3 + smoothFactor3 * fpoint.getClose();
            fTApoint2.isValid = true;
        }
    }

    // see p.18 of TA book.......
    public void calculateWeightedMovingAverage(int n1, int n2, int n3) {
        TAdata.removeAllElements();
        //create TAdata
        for (int i = 0; i < this.data.size(); i++) {
            FTAPoint fTApoint = new FTAPoint();
            fTApoint.isValid = false;
            TAdata.addElement(new FTAPoint());
        }

        if (n1 > 0) {
            if (n1 < data.size())
                for (int k = n1 - 1; k < data.size(); k++) {
                    float sum = 0f;
                    for (int j = 0; j < n1; j++) {
                        FPoint fpoint = (FPoint) data.elementAt(k - j);
                        sum = sum + (fpoint.getClose() * (n1 - j));
                    }
                    int W = 0;   //p.18 of TA book
                    for (int l = 1; l <= n1; l++) {
                        W = W + l;
                    }
                    FTAPoint fTApoint = (FTAPoint) TAdata.elementAt(k);
                    fTApoint.MA1 = sum / W;
                    fTApoint.isValid = true;
                    //  System.out.println("Point " + fTApoint.MA1);
                }
        }

        if (n2 > 0) {
            if (n2 < data.size())
                for (int k = n2 - 1; k < data.size(); k++) {
                    float sum = 0f;
                    for (int j = 0; j < n2; j++) {
                        FPoint fpoint = (FPoint) data.elementAt(k - j);
                        sum = sum + fpoint.getClose() * (n2 - j);
                    }
                    int W = 0;   //p.18 of TA book
                    for (int l = 1; l <= n2; l++) {
                        W = W + l;
                    }
                    FTAPoint fTApoint = (FTAPoint) TAdata.elementAt(k);
                    fTApoint.MA2 = sum / W;
                    fTApoint.isValid = true;
                    // System.out.println("Point " + fTApoint.MA1);
                }
        }

        if (n3 > 0) {
            if (n3 < data.size())
                for (int k = n3 - 1; k < data.size(); k++) {
                    float sum = 0f;
                    for (int j = 0; j < n3; j++) {
                        FPoint fpoint = (FPoint) data.elementAt(k - j);
                        sum = sum + fpoint.getClose() * (n3 - j);
                    }
                    int W = 0;   //p.18 of TA book
                    for (int l = 1; l <= n3; l++) {
                        W = W + l;
                    }
                    FTAPoint fTApoint = (FTAPoint) TAdata.elementAt(k);
                    fTApoint.MA3 = sum / W;
                    fTApoint.isValid = true;
                    // System.out.println("Point " + fTApoint.MA1);
                }
        }
    }

    //p.18
    public void calculateMovingAverage(int n1, int n2, int n3) {
        TAdata.removeAllElements();
        //create TAdata
        for (int i = 0; i < this.data.size(); i++) {
            FTAPoint fTApoint = new FTAPoint();
            fTApoint.isValid = false;
            TAdata.addElement(new FTAPoint());
        }

        if (n1 > this.data.size() || n2 > this.data.size() || n3 > this.data.size()) return;

        if (n1 > 0) {
            if (n1 < data.size())
                for (int k = n1 - 1; k < data.size(); k++) {
                    float sum = 0f;
                    for (int j = 0; j < n1; j++) {
                        FPoint fpoint = (FPoint) data.elementAt(k - j);
                        sum = sum + fpoint.getClose();
                    }
                    FTAPoint fTApoint = (FTAPoint) TAdata.elementAt(k);
                    fTApoint.MA1 = sum / n1;
                    fTApoint.isValid = true;
                    // System.out.println("Point " + fTApoint.MA1);
                }
        }

        if (n2 > 0) {
            if (n2 < data.size())
                for (int k = n2 - 1; k < data.size(); k++) {
                    float sum = 0f;
                    for (int j = 0; j < n2; j++) {
                        FPoint fpoint = (FPoint) data.elementAt(k - j);
                        sum = sum + fpoint.getClose();
                    }
                    FTAPoint fTApoint = (FTAPoint) TAdata.elementAt(k);
                    fTApoint.MA2 = sum / n2;
                    fTApoint.isValid = true;
                }
        }

        if (n3 > 0) {
            if (n3 < data.size())
                for (int k = n3 - 1; k < data.size(); k++) {
                    float sum = 0f;
                    for (int j = 0; j < n3; j++) {
                        FPoint fpoint = (FPoint) data.elementAt(k - j);
                        sum = sum + fpoint.getClose();
                    }
                    FTAPoint fTApoint = (FTAPoint) TAdata.elementAt(k);
                    fTApoint.MA3 = sum / n3;
                    fTApoint.isValid = true;
                }
        }
    }

//  P.92
    public void calculateWilliamR(int RPeriod) {
        TAdata.removeAllElements();
        //create TAdata

        for (int i = 0; i < this.data.size(); i++) {
            FTAPoint fTApoint = new FTAPoint();
            fTApoint.isValid = false;
            TAdata.addElement(new FTAPoint());
        }

        if (RPeriod > data.size()) return;

        // calculate %R value
        for (int k = RPeriod - 1; k < data.size(); k++) {
            float KMin = 1000000f; //the min value in K day's
            float KMax = 0f;       //the max value in Kday's
            float KValue = 0f;
            FPoint fpoint = (FPoint) data.elementAt(k);
            KValue = fpoint.getClose();
            FTAPoint fTApoint = (FTAPoint) this.TAdata.elementAt(k);
            for (int j = 0; j < RPeriod; j++) {
                FPoint fcpoint = (FPoint) data.elementAt(k - j);
                float tempMin = fcpoint.getMinimum();
                float tempMax = fcpoint.getMaximum();
                if (tempMin < KMin) KMin = tempMin;
                if (tempMax > KMax) KMax = tempMax;
            }
            if ((KMax - KMin) != 0f) {
                fTApoint.R = (KMax - KValue) / (KMax - KMin) * 100;
            } else {
                fTApoint.R = 0f;
            }

            fTApoint.isValid = true;
        }

    }



//  P.92
    public void calculateSTC(int KPeriod, int DPeriod) {


        TAdata.removeAllElements();
        //create TAdata

        for (int i = 0; i < this.data.size(); i++) {
            FTAPoint fTApoint = new FTAPoint();
            fTApoint.isValid = false;
            TAdata.addElement(new FTAPoint());
        }

        if (KPeriod > data.size() && DPeriod >= (data.size() - KPeriod)) return;

        // calculate %K value
        for (int k = KPeriod - 1; k < data.size(); k++) {
            float KMin = 1000000f; //the min value in K day's
            float KMax = 0f;       //the max value in Kday's
            float KValue = 0f;
            FPoint fpoint = (FPoint) data.elementAt(k);
            KValue = fpoint.getClose();
            FTAPoint fTApoint = (FTAPoint) this.TAdata.elementAt(k);
            for (int j = 0; j < KPeriod; j++) {
                FPoint fcpoint = (FPoint) data.elementAt(k - j);
                float tempMin = fcpoint.getMinimum();
                float tempMax = fcpoint.getMaximum();
                if (tempMin < KMin) KMin = tempMin;
                if (tempMax > KMax) KMax = tempMax;
            }
            fTApoint.K = (KValue - KMin) / (KMax - KMin) * 100;
            fTApoint.isValid = true;
        }

        //calculate %D line
        for (int k = KPeriod + DPeriod - 1; k < data.size(); k++) {
            FTAPoint fTApoint = (FTAPoint) this.TAdata.elementAt(k);
            float Sum = 0f;
            for (int j = 0; j < DPeriod; j++) {
                FTAPoint cfTApoint = (FTAPoint) this.TAdata.elementAt(k - j);
                Sum = Sum + cfTApoint.K;
            }
            Sum = Sum / DPeriod;
            fTApoint.D = Sum;
        }

    }

/*
  public void calculateRMI(int N)
  {
    if (N > data.size()) return;
    TAdata.removeAllElements();
    //create TAdata
    for (int i=0;i<this.data.size();i++)
    {
      FTAPoint fTApoint = new FTAPoint();
      fTApoint.isValid = false;
      TAdata.addElement(new FTAPoint());
    }

    for (int k = N-1+4; k< data.size(); k++)
    {
       float MU = 0f;
       float MD = 0f;
       FTAPoint fTApoint = (FTAPoint) this.TAdata.elementAt(k);
       for (int j=0;j<N;j++)
       {
          for (int z = 0;z<4;z++)
          {
            FPoint fpoint = (FPoint) data.elementAt(k-j-z);
            float diff = fpoint.getClose() - fpoint.Open;
            if (diff >0)
            {
              MU = MU + Math.abs(diff);
            }
            else
            {
              MD = MD + Math.abs(diff);
            }
          }
     }
     fTApoint.RMI = MU/(MU+MD) * 100;
     fTApoint.isValid = true;
    }
  }
*/
    public void calculateRSI(int N) {

        TAdata.removeAllElements();
        //create TAdata
        for (int i = 0; i < this.data.size(); i++) {
            FTAPoint fTApoint = new FTAPoint();
            fTApoint.isValid = false;
            TAdata.addElement(new FTAPoint());
        }
        if (N > data.size()) return;


        for (int k = N; k < data.size(); k++) {
            float AU = 0f;
            float AD = 0f;
            FTAPoint fTApoint = (FTAPoint) this.TAdata.elementAt(k);
            for (int j = 0; j < N; j++) {
                FPoint fpoint1 = (FPoint) data.elementAt(k - j);
                FPoint fpoint2 = (FPoint) data.elementAt(k - j - 1);
//          float diff = fpoint1.Close - fpoint1.Open;
                float diff = fpoint1.getClose() - fpoint2.getClose();
                //float diff2 = fpoint1.Close - fpoint1.Open;

                if (diff > 0) {
                    AU = AU + Math.abs(diff);
                } else if (diff < 0) {
                    AD = AD + Math.abs(diff);
                }
            }

            if ((AU + AD) > 0) {
                fTApoint.RSI = AU / (AU + AD) * 100;
            } else {
                fTApoint.RSI = 0;
            }
            fTApoint.isValid = true;
        }
    }

    //p.95
    public void calculateMACD(int LEMA, int SEMA, int MA) {

        this.calculateExponentialMovingAverage(LEMA, SEMA, SEMA);
        if (LEMA > this.data.size() || SEMA > this.data.size() || MA > this.data.size()) return;

        for (int k = 0; k < data.size(); k++) {
            FTAPoint fTApoint = (FTAPoint) this.TAdata.elementAt(k);
            fTApoint.MACD1 = fTApoint.MA2 - fTApoint.MA1;

            float sum = 0f;
            //calculate Moving average of MACD1 using MA period.
            if ((k - MA) >= 0)
                for (int j = 0; j < MA; j++) {
                    FTAPoint fTApoint2 = (FTAPoint) this.TAdata.elementAt(k - j);
                    sum = sum + fTApoint2.MACD1;
                }
            sum = sum / MA;
            fTApoint.MACD2 = sum;
//      calculate diff
            fTApoint.MACDdiff = fTApoint.MACD1 - fTApoint.MACD2;
            fTApoint.isValid = true;
        }


    }


    public void calculateBollingerBand(int N, float dd) {

        calculateMovingAverage(N, N, N);
        if (N > this.data.size()) return;

        for (int k = N - 1; k < data.size(); k++) {
            // calculate SD
            float SD = 0f;
            FTAPoint fTApoint = (FTAPoint) this.TAdata.elementAt(k);
            for (int j = 0; j < N; j++) {
                FPoint fpoint = (FPoint) data.elementAt(k - j);
                SD = SD + (fpoint.getClose() - fTApoint.MA1) * (fpoint.getClose() - fTApoint.MA1);
            }
            SD = (float) Math.sqrt(SD / N);
            fTApoint.UB = fTApoint.MA1 + dd * SD;
            fTApoint.LB = fTApoint.MA1 - dd * SD;
        }
    }


    //p.102 pls notice that OBV are down scaler to K unit.
    public void calculateOBV(int startIndex, int endIndex) {
        TAdata.removeAllElements();
        //create TAdata
        for (int i = 0; i < this.data.size(); i++) {
            FTAPoint fTApoint = new FTAPoint();
            fTApoint.isValid = false;
            TAdata.addElement(new FTAPoint());
        }
        for (int k = startIndex + 1; k <= endIndex; k++) {
            FTAPoint fTApoint1 = (FTAPoint) this.TAdata.elementAt(k);
            FTAPoint fTApoint2 = (FTAPoint) this.TAdata.elementAt(k - 1);
            FPoint fpoint1 = (FPoint) this.data.elementAt(k);
            FPoint fpoint2 = (FPoint) this.data.elementAt(k - 1);

            if (fpoint1.getClose() > fpoint2.getClose()) {
                fTApoint1.OBV = fTApoint2.OBV + (fpoint1.getVolume() / 1000);
                if (fTApoint2.OBV > fTApoint1.OBV)
                    System.out.println("OBV : " + fTApoint2.OBV + "ERROR");
            } else if (fpoint1.getClose() < fpoint2.getClose()) {
                fTApoint1.OBV = fTApoint2.OBV - (fpoint1.getVolume() / 1000);
                if (fTApoint2.OBV < fTApoint1.OBV)
                    System.out.println("OBV : " + fTApoint2.OBV + "ERROR");
            } else {
                fTApoint1.OBV = fTApoint2.OBV;
            }
            fTApoint1.isValid = true;
        }
    }


}