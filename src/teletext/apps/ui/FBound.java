package teletext.apps.ui;

public class FBound {
    //the bound of the Stock value
    public double LowerStockBound = 0f;
    public double UpperStockBound = 0f;

    // the bound of the Stock value
    public double LowerVolumeBound = 0;
    public double UpperVolumeBound = 0;

    //the boun of percentage value;
    public float LowerPercentageBound = 0;
    public float UpperPercentageBound = 200;

    //thd bound of RSI // it is fixed between 0 to 100
    public float LowerRSIBound = 0f;
    public float UpperRSIBound = 110f;

    //the bound of STC // it is fixed between 0 to 100
    public float LowerSTCBound = 0f;
    public float UpperSTCBound = 110f;

    // for William's %R // it is fixed between 0 to 100
    public float LowerWilliamRBound = 0f;
    public float UpperWilliamRBound = 110f;


    //the bound of OBV
    public float LowerOBVBound = 0f;
    public float UpperOBVBound = 100f;

    //the bound of MACD
    public float LowerMACDBound = 0f;
    public float UpperMACDBound = 100f;


}