package com.ic.data;


public class FTAPoint {


    public boolean isValid = false;
    // for SMA and WMA, EMA
    private float MA1 = 0f;
    private float MA2 = 0f;
    private float MA3 = 0f;
    // for Bollinger's Band
    private float UB = 0f;
    private float LB = 0f;

    // for EMA
    // float EMA = 0f;
    // for RSI
    private float RSI = 0f;
    // for STC
    private float K = 0f;
    private float D = 0f;
    // for William's %R
    private float R = 0f;
    // for OBV
    private double OBV = 0f; //in K unit
    // for MACD
    private float MACD1 = 0f;
    private float MACD2 = 0f;
    private float MACDdiff = 0f;
    // for RMI
    private float RMI = 0f;

    public boolean isValid() {
        return isValid;
    }

    public void setValid(boolean isValid) {
        this.isValid = isValid;
    }

    public float getMA1() {
        return MA1;
    }

    public void setMA1(float MA1) {
        this.MA1 = MA1;
    }

    public float getMA2() {
        return MA2;
    }

    public void setMA2(float MA2) {
        this.MA2 = MA2;
    }

    public float getMA3() {
        return MA3;
    }

    public void setMA3(float MA3) {
        this.MA3 = MA3;
    }

    public float getUB() {
        return UB;
    }

    public void setUB(float UB) {
        this.UB = UB;
    }

    public float getLB() {
        return LB;
    }

    public void setLB(float LB) {
        this.LB = LB;
    }

    public float getRSI() {
        return RSI;
    }

    public void setRSI(float RSI) {
        this.RSI = RSI;
    }

    public float getK() {
        return K;
    }

    public void setK(float k) {
        K = k;
    }

    public float getD() {
        return D;
    }

    public void setD(float d) {
        D = d;
    }

    public float getR() {
        return R;
    }

    public void setR(float r) {
        R = r;
    }

    public double getOBV() {
        return OBV;
    }

    public void setOBV(double OBV) {
        this.OBV = OBV;
    }

    public float getMACD1() {
        return MACD1;
    }

    public void setMACD1(float MACD1) {
        this.MACD1 = MACD1;
    }

    public float getMACD2() {
        return MACD2;
    }

    public void setMACD2(float MACD2) {
        this.MACD2 = MACD2;
    }

    public float getMACDdiff() {
        return MACDdiff;
    }

    public void setMACDdiff(float MACDdiff) {
        this.MACDdiff = MACDdiff;
    }

    public float getRMI() {
        return RMI;
    }

    public void setRMI(float RMI) {
        this.RMI = RMI;
    }
}