package com.ic.data;

import com.ic.core.TAConfig;

import java.util.ArrayList;
import java.util.List;

public class ChartData {

  public DataInterval dataInterval = DataInterval.DAILY;
  private List<StockData> data = new ArrayList<StockData>(3000);
  private List<AnalyticalResult> analyticalResults = new ArrayList<AnalyticalResult>(1000);

  private TAConfig fTAconfig = new TAConfig();

  //Stock information
  private int Code = 0;
  private String name = "Unkonwn";
  //private String CName = "Unkonwn";
  private int intradayInterval = 1;//5, or 10;

  public ChartData() {
  }

  //it return the Maximum value of data in that range, getMaximum(0,0) means test all fpoints.
  public double getMaximumVolume(int startIndex, int endIndex) {
    double Maximum = 0;
    if (startIndex == 0 && endIndex == 0) {
      startIndex = 0;
      endIndex = getData().size() - 1;
    }
    for (int i = startIndex; i <= endIndex; i++) {
      StockData fpoint = getData().get(i);
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
      endIndex = getData().size() - 1;
    }
    endIndex = Math.min(endIndex, getData().size() - 1);
    for (int i = startIndex; i <= endIndex; i++) {
      StockData fpoint = getData().get(i);
      if (!fpoint.isValid()) continue;
      double Mvalue = 0;
      if (cType == "STOCK") {
        Mvalue = fpoint.getMaximum();
      } else if (cType == "PERCENTAGE") {
        Mvalue = fpoint.getPercent();
      } else if (cType == "MACD") {
        AnalyticalResult fTApoint = getAnalyticalResults().get(i);
        Mvalue = Math.max(fTApoint.getMACD1(), fTApoint.getMACD2());
        Mvalue = Math.max(Mvalue, fTApoint.getMACDdiff());
      } else if (cType == "OBV") {
        AnalyticalResult fTApoint = getAnalyticalResults().get(i);
        Mvalue = fTApoint.getOBV();
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
      endIndex = getData().size() - 1;
    }
    // startIndex = 0;
    // endIndex = getData().size() - 1;
    endIndex = Math.min(endIndex, getData().size() - 1);
    for (int i = startIndex; i <= endIndex; i++) {
      StockData fpoint = getData().get(i);
      if (!fpoint.isValid()) continue;
      double Mvalue = 0f;
      if (cType == "STOCK") {
        Mvalue = fpoint.getMinimum();
      } else if (cType == "PERCENTAGE") {
        Mvalue = fpoint.getPercent();
      } else if (cType == "MACD") {
        AnalyticalResult fTApoint = getAnalyticalResults().get(i);
        Mvalue = Math.min(fTApoint.getMACD1(), fTApoint.getMACD2());
        Mvalue = Math.min(Mvalue, fTApoint.getMACDdiff());
      } else if (cType == "OBV") {
        AnalyticalResult fTApoint = getAnalyticalResults().get(i);
        Mvalue = fTApoint.getOBV();
      }

      if (Minimum > Mvalue) {
        Minimum = Mvalue;
      }
    }
    return Minimum;
  }


  public void calculatePercentage(int refIndex) {
    StockData refPoint = getData().get(refIndex);
    while (!refPoint.isValid()) {
      refIndex++;
      if (refIndex < this.getData().size()) {
        refPoint = getData().get(refIndex);
      } else {
        break;
      }
    }
    for (int i = 0; i < getData().size(); i++) {
      StockData fpoint = getData().get(i);
      try {
        fpoint.setPercent(fpoint.getClose() / refPoint.getClose() * 100f);
      } catch (Exception ce) {
        fpoint.setValid(false);
      }
    }
  }

  //see. P.19 of the book
  public void calculateExponentialMovingAverage(int n1, int n2, int n3) {
    getAnalyticalResults().clear();
    //create analyticalResults
    for (int i = 0; i < this.getData().size(); i++) {
      AnalyticalResult fTApoint = new AnalyticalResult();
      fTApoint.isValid = false;
      getAnalyticalResults().add(new AnalyticalResult());
    }

    float smoothFactor1 = 2f / (n1 + 1f);
    float smoothFactor2 = 2f / (n2 + 1f);
    float smoothFactor3 = 2f / (n3 + 1f);


    StockData fpoint = getData().get(0);
    AnalyticalResult fTApoint = getAnalyticalResults().get(0);
    fTApoint.setMA1(fpoint.getClose());
    fTApoint.setMA2(fpoint.getClose());
    fTApoint.setMA3(fpoint.getClose());

    for (int k = 1; k < getData().size(); k++) {
      fpoint = getData().get(k);
      AnalyticalResult fTApoint1 = getAnalyticalResults().get(k - 1);
      AnalyticalResult fTApoint2 = getAnalyticalResults().get(k);
      fTApoint2.setMA1((1 - smoothFactor1) * fTApoint1.getMA1() + smoothFactor1 * fpoint.getClose());
      fTApoint2.setMA2((1 - smoothFactor2) * fTApoint1.getMA2() + smoothFactor2 * fpoint.getClose());
      fTApoint2.setMA3((1 - smoothFactor3) * fTApoint1.getMA3() + smoothFactor3 * fpoint.getClose());
      fTApoint2.isValid = true;
    }
  }

  // see p.18 of TA book.......
  public void calculateWeightedMovingAverage(int n1, int n2, int n3) {
    getAnalyticalResults().clear();
    //create analyticalResults
    for (int i = 0; i < this.getData().size(); i++) {
      AnalyticalResult fTApoint = new AnalyticalResult();
      fTApoint.isValid = false;
      getAnalyticalResults().add(new AnalyticalResult());
    }

    if (n1 > 0) {
      if (n1 < getData().size())
        for (int k = n1 - 1; k < getData().size(); k++) {
          float sum = 0f;
          for (int j = 0; j < n1; j++) {
            StockData fpoint = getData().get(k - j);
            sum = sum + (fpoint.getClose() * (n1 - j));
          }
          int W = 0;   //p.18 of TA book
          for (int l = 1; l <= n1; l++) {
            W = W + l;
          }
          AnalyticalResult fTApoint = getAnalyticalResults().get(k);
          fTApoint.setMA1(sum / W);
          fTApoint.isValid = true;
        }
    }

    if (n2 > 0) {
      if (n2 < getData().size())
        for (int k = n2 - 1; k < getData().size(); k++) {
          float sum = 0f;
          for (int j = 0; j < n2; j++) {
            StockData fpoint = getData().get(k - j);
            sum = sum + fpoint.getClose() * (n2 - j);
          }
          int W = 0;   //p.18 of TA book
          for (int l = 1; l <= n2; l++) {
            W = W + l;
          }
          AnalyticalResult fTApoint = getAnalyticalResults().get(k);
          fTApoint.setMA2(sum / W);
          fTApoint.isValid = true;
          // System.out.println("Point " + fTApoint.MA1);
        }
    }

    if (n3 > 0) {
      if (n3 < getData().size())
        for (int k = n3 - 1; k < getData().size(); k++) {
          float sum = 0f;
          for (int j = 0; j < n3; j++) {
            StockData fpoint = getData().get(k - j);
            sum = sum + fpoint.getClose() * (n3 - j);
          }
          int W = 0;   //p.18 of TA book
          for (int l = 1; l <= n3; l++) {
            W = W + l;
          }
          AnalyticalResult fTApoint = getAnalyticalResults().get(k);
          fTApoint.setMA3(sum / W);
          fTApoint.isValid = true;
          // System.out.println("Point " + fTApoint.MA1);
        }
    }
  }

  //p.18
  public void calculateMovingAverage(int n1, int n2, int n3) {
    getAnalyticalResults().clear();
    //create analyticalResults
    for (int i = 0; i < this.getData().size(); i++) {
      AnalyticalResult fTApoint = new AnalyticalResult();
      fTApoint.isValid = false;
      getAnalyticalResults().add(new AnalyticalResult());
    }

    if (n1 > this.getData().size() || n2 > this.getData().size() || n3 > this.getData().size()) return;

    if (n1 > 0) {
      if (n1 < getData().size())
        for (int k = n1 - 1; k < getData().size(); k++) {
          float sum = 0f;
          for (int j = 0; j < n1; j++) {
            StockData fpoint = getData().get(k - j);
            sum = sum + fpoint.getClose();
          }
          AnalyticalResult fTApoint = getAnalyticalResults().get(k);
          fTApoint.setMA1(sum / n1);
          fTApoint.isValid = true;
          // System.out.println("Point " + fTApoint.MA1);
        }
    }

    if (n2 > 0) {
      if (n2 < getData().size())
        for (int k = n2 - 1; k < getData().size(); k++) {
          float sum = 0f;
          for (int j = 0; j < n2; j++) {
            StockData fpoint = getData().get(k - j);
            sum = sum + fpoint.getClose();
          }
          AnalyticalResult fTApoint = getAnalyticalResults().get(k);
          fTApoint.setMA2(sum / n2);
          fTApoint.isValid = true;
        }
    }

    if (n3 > 0) {
      if (n3 < getData().size())
        for (int k = n3 - 1; k < getData().size(); k++) {
          float sum = 0f;
          for (int j = 0; j < n3; j++) {
            StockData fpoint = getData().get(k - j);
            sum = sum + fpoint.getClose();
          }
          AnalyticalResult fTApoint = getAnalyticalResults().get(k);
          fTApoint.setMA3(sum / n3);
          fTApoint.isValid = true;
        }
    }
  }

  //  P.92
  public void calculateWilliamR(int RPeriod) {
    getAnalyticalResults().clear();
    //create analyticalResults

    for (int i = 0; i < this.getData().size(); i++) {
      AnalyticalResult fTApoint = new AnalyticalResult();
      fTApoint.isValid = false;
      getAnalyticalResults().add(new AnalyticalResult());
    }

    if (RPeriod > getData().size()) return;

    // calculate %R value
    for (int k = RPeriod - 1; k < getData().size(); k++) {
      float KMin = 1000000f; //the min value in K day's
      float KMax = 0f;       //the max value in Kday's
      float KValue = 0f;
      StockData fpoint = getData().get(k);
      KValue = fpoint.getClose();
      AnalyticalResult fTApoint = this.getAnalyticalResults().get(k);
      for (int j = 0; j < RPeriod; j++) {
        StockData fcpoint = getData().get(k - j);
        float tempMin = fcpoint.getMinimum();
        float tempMax = fcpoint.getMaximum();
        if (tempMin < KMin) KMin = tempMin;
        if (tempMax > KMax) KMax = tempMax;
      }
      if ((KMax - KMin) != 0f) {
        fTApoint.setR((KMax - KValue) / (KMax - KMin) * 100);
      } else {
        fTApoint.setR(0f);
      }

      fTApoint.isValid = true;
    }

  }


  //  P.92
  public void calculateSTC(int KPeriod, int DPeriod) {


    getAnalyticalResults().clear();
    //create analyticalResults

    for (int i = 0; i < this.getData().size(); i++) {
      AnalyticalResult fTApoint = new AnalyticalResult();
      fTApoint.isValid = false;
      getAnalyticalResults().add(new AnalyticalResult());
    }

    if (KPeriod > getData().size() && DPeriod >= (getData().size() - KPeriod)) return;

    // calculate %K value
    for (int k = KPeriod - 1; k < getData().size(); k++) {
      float KMin = 1000000f; //the min value in K day's
      float KMax = 0f;       //the max value in Kday's
      float KValue = 0f;
      StockData fpoint = getData().get(k);
      KValue = fpoint.getClose();
      AnalyticalResult fTApoint = this.getAnalyticalResults().get(k);
      for (int j = 0; j < KPeriod; j++) {
        StockData fcpoint = getData().get(k - j);
        float tempMin = fcpoint.getMinimum();
        float tempMax = fcpoint.getMaximum();
        if (tempMin < KMin) KMin = tempMin;
        if (tempMax > KMax) KMax = tempMax;
      }
      fTApoint.setK((KValue - KMin) / (KMax - KMin) * 100);
      fTApoint.isValid = true;
    }

    //calculate %D line
    for (int k = KPeriod + DPeriod - 1; k < getData().size(); k++) {
      AnalyticalResult fTApoint = this.getAnalyticalResults().get(k);
      float Sum = 0f;
      for (int j = 0; j < DPeriod; j++) {
        AnalyticalResult cfTApoint = this.getAnalyticalResults().get(k - j);
        Sum = Sum + cfTApoint.getK();
      }
      Sum = Sum / DPeriod;
      fTApoint.setD(Sum);
    }

  }

  /*
    public void calculateRMI(int N)
    {
      if (N > data.size()) return;
      analyticalResults.removeAllElements();
      //create analyticalResults
      for (int i=0;i<this.data.size();i++)
      {
        AnalyticalResult fTApoint = new AnalyticalResult();
        fTApoint.isValid = false;
        analyticalResults.addElement(new AnalyticalResult());
      }

      for (int k = N-1+4; k< data.size(); k++)
      {
         float MU = 0f;
         float MD = 0f;
         AnalyticalResult fTApoint = (AnalyticalResult) this.analyticalResults.elementAt(k);
         for (int j=0;j<N;j++)
         {
            for (int z = 0;z<4;z++)
            {
              StockData fpoint = (StockData) data.elementAt(k-j-z);
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

    getAnalyticalResults().clear();
    //create analyticalResults
    for (int i = 0; i < this.getData().size(); i++) {
      AnalyticalResult fTApoint = new AnalyticalResult();
      fTApoint.isValid = false;
      getAnalyticalResults().add(new AnalyticalResult());
    }
    if (N > getData().size()) return;


    for (int k = N; k < getData().size(); k++) {
      float AU = 0f;
      float AD = 0f;
      AnalyticalResult fTApoint = this.getAnalyticalResults().get(k);
      for (int j = 0; j < N; j++) {
        StockData fpoint1 = getData().get(k - j);
        StockData fpoint2 = getData().get(k - j - 1);
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
        fTApoint.setRSI(AU / (AU + AD) * 100);
      } else {
        fTApoint.setRSI(0);
      }
      fTApoint.isValid = true;
    }
  }

  //p.95
  public void calculateMACD(int LEMA, int SEMA, int MA) {

    this.calculateExponentialMovingAverage(LEMA, SEMA, SEMA);
    if (LEMA > this.getData().size() || SEMA > this.getData().size() || MA > this.getData().size()) return;

    for (int k = 0; k < getData().size(); k++) {
      AnalyticalResult fTApoint = this.getAnalyticalResults().get(k);
      fTApoint.setMACD1(fTApoint.getMA2() - fTApoint.getMA1());

      float sum = 0f;
      //calculate Moving average of MACD1 using MA period.
      if ((k - MA) >= 0)
        for (int j = 0; j < MA; j++) {
          AnalyticalResult fTApoint2 = this.getAnalyticalResults().get(k - j);
          sum = sum + fTApoint2.getMACD1();
        }
      sum = sum / MA;
      fTApoint.setMACD2(sum);
//      calculate diff
      fTApoint.setMACDdiff(fTApoint.getMACD1() - fTApoint.getMACD2());
      fTApoint.isValid = true;
    }


  }


  public void calculateBollingerBand(int N, float dd) {

    calculateMovingAverage(N, N, N);
    if (N > this.getData().size()) return;

    for (int k = N - 1; k < getData().size(); k++) {
      // calculate SD
      float SD = 0f;
      AnalyticalResult fTApoint = this.getAnalyticalResults().get(k);
      for (int j = 0; j < N; j++) {
        StockData fpoint = getData().get(k - j);
        SD = SD + (fpoint.getClose() - fTApoint.getMA1()) * (fpoint.getClose() - fTApoint.getMA1());
      }
      SD = (float) Math.sqrt(SD / N);
      fTApoint.setUB(fTApoint.getMA1() + dd * SD);
      fTApoint.setLB(fTApoint.getMA1() - dd * SD);
    }
  }


  //p.102 pls notice that OBV are down scaler to K unit.
  public void calculateOBV(int startIndex, int endIndex) {
    getAnalyticalResults().clear();
    //create analyticalResults
    for (int i = 0; i < this.getData().size(); i++) {
      AnalyticalResult fTApoint = new AnalyticalResult();
      fTApoint.isValid = false;
      getAnalyticalResults().add(new AnalyticalResult());
    }
    for (int k = startIndex + 1; k <= endIndex; k++) {
      AnalyticalResult fTApoint1 = this.getAnalyticalResults().get(k);
      AnalyticalResult fTApoint2 = this.getAnalyticalResults().get(k - 1);
      StockData fpoint1 = this.getData().get(k);
      StockData fpoint2 = this.getData().get(k - 1);

      if (fpoint1.getClose() > fpoint2.getClose()) {
        fTApoint1.setOBV(fTApoint2.getOBV() + (fpoint1.getVolume() / 1000));
        if (fTApoint2.getOBV() > fTApoint1.getOBV())
          System.out.println("OBV : " + fTApoint2.getOBV() + "ERROR");
      } else if (fpoint1.getClose() < fpoint2.getClose()) {
        fTApoint1.setOBV(fTApoint2.getOBV() - (fpoint1.getVolume() / 1000));
        if (fTApoint2.getOBV() < fTApoint1.getOBV())
          System.out.println("OBV : " + fTApoint2.getOBV() + "ERROR");
      } else {
        fTApoint1.setOBV(fTApoint2.getOBV());
      }
      fTApoint1.isValid = true;
    }
  }


  public synchronized List<StockData> getData() {
    return data;
  }

  public synchronized void setData(List<StockData> data) {
    this.data = data;
  }

  public List<AnalyticalResult> getAnalyticalResults() {
    return analyticalResults;
  }

  public void setAnalyticalResults(List<AnalyticalResult> analyticalResults) {
    this.analyticalResults = analyticalResults;
  }

  public TAConfig getfTAconfig() {
    return fTAconfig;
  }

  public void setfTAconfig(TAConfig fTAconfig) {
    this.fTAconfig = fTAconfig;
  }

  public int getCode() {
    return Code;
  }

  public void setCode(int code) {
    Code = code;
  }

  public String getName() {
    return name;
  }

  public void setName(String CName) {
    this.name = CName;
  }

  public int getIntradayInterval() {
    return intradayInterval;
  }

  public void setIntradayInterval(int intradayInterval) {
    this.intradayInterval = intradayInterval;
  }
}