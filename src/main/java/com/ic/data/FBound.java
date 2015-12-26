package com.ic.data;

public class FBound {

    //the bound of the Stock value
    private double LowerStockBound = 0f;
    private double UpperStockBound = 0f;

    // the bound of the Stock value
    private double LowerVolumeBound = 0;
    private double UpperVolumeBound = 0;

    //the boun of percentage value;
    private float LowerPercentageBound = 0;
    private float UpperPercentageBound = 200;

    //thd bound of RSI // it is fixed between 0 to 100
    private float LowerRSIBound = 0f;
    private float UpperRSIBound = 110f;

    //the bound of STC // it is fixed between 0 to 100
    private float LowerSTCBound = 0f;
    private float UpperSTCBound = 110f;

    // for William's %R // it is fixed between 0 to 100
    private float LowerWilliamRBound = 0f;
    private float UpperWilliamRBound = 110f;

    //the bound of OBV
    private float LowerOBVBound = 0f;
    private float UpperOBVBound = 100f;

    //the bound of MACD
    private float LowerMACDBound = 0f;
    private float UpperMACDBound = 100f;


    public double getLowerStockBound() {
        return LowerStockBound;
    }

    public void setLowerStockBound(double lowerStockBound) {
        LowerStockBound = lowerStockBound;
    }

    public double getUpperStockBound() {
        return UpperStockBound;
    }

    public void setUpperStockBound(double upperStockBound) {
        UpperStockBound = upperStockBound;
    }

    public double getLowerVolumeBound() {
        return LowerVolumeBound;
    }

    public void setLowerVolumeBound(double lowerVolumeBound) {
        LowerVolumeBound = lowerVolumeBound;
    }

    public double getUpperVolumeBound() {
        return UpperVolumeBound;
    }

    public void setUpperVolumeBound(double upperVolumeBound) {
        UpperVolumeBound = upperVolumeBound;
    }

    public float getLowerPercentageBound() {
        return LowerPercentageBound;
    }

    public void setLowerPercentageBound(float lowerPercentageBound) {
        LowerPercentageBound = lowerPercentageBound;
    }

    public float getUpperPercentageBound() {
        return UpperPercentageBound;
    }

    public void setUpperPercentageBound(float upperPercentageBound) {
        UpperPercentageBound = upperPercentageBound;
    }

    public float getLowerRSIBound() {
        return LowerRSIBound;
    }

    public void setLowerRSIBound(float lowerRSIBound) {
        LowerRSIBound = lowerRSIBound;
    }

    public float getUpperRSIBound() {
        return UpperRSIBound;
    }

    public void setUpperRSIBound(float upperRSIBound) {
        UpperRSIBound = upperRSIBound;
    }

    public float getLowerSTCBound() {
        return LowerSTCBound;
    }

    public void setLowerSTCBound(float lowerSTCBound) {
        LowerSTCBound = lowerSTCBound;
    }

    public float getUpperSTCBound() {
        return UpperSTCBound;
    }

    public void setUpperSTCBound(float upperSTCBound) {
        UpperSTCBound = upperSTCBound;
    }

    public float getLowerWilliamRBound() {
        return LowerWilliamRBound;
    }

    public void setLowerWilliamRBound(float lowerWilliamRBound) {
        LowerWilliamRBound = lowerWilliamRBound;
    }

    public float getUpperWilliamRBound() {
        return UpperWilliamRBound;
    }

    public void setUpperWilliamRBound(float upperWilliamRBound) {
        UpperWilliamRBound = upperWilliamRBound;
    }

    public float getLowerOBVBound() {
        return LowerOBVBound;
    }

    public void setLowerOBVBound(float lowerOBVBound) {
        LowerOBVBound = lowerOBVBound;
    }

    public float getUpperOBVBound() {
        return UpperOBVBound;
    }

    public void setUpperOBVBound(float upperOBVBound) {
        UpperOBVBound = upperOBVBound;
    }

    public float getLowerMACDBound() {
        return LowerMACDBound;
    }

    public void setLowerMACDBound(float lowerMACDBound) {
        LowerMACDBound = lowerMACDBound;
    }

    public float getUpperMACDBound() {
        return UpperMACDBound;
    }

    public void setUpperMACDBound(float upperMACDBound) {
        UpperMACDBound = upperMACDBound;
    }
}