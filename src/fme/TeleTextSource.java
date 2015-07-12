/**
 * Title:        <p>
 * Description:  <p>
 * Copyright:    Copyright (c) <p>
 * Company:      <p>
 * @author
 * @version 1.0
 */
package fme;

import java.util.Vector;

public class TeleTextSource {

//Store the broker information (company name)
    public Vector BrokerQueue = new Vector();
    public Vector CBrokerQueue = new Vector();

// Generate Information
    public String Name; //s1
    public String CName; //s2
    public String numshare; //s5
    public String Code; //i0
    public String date; //i1
    public String time; //i2
    public String currbid; //i3
    public String currask; //i4
    public String open; //i5
    public String premium; //i15
    public String Wration; //i16
    public String yrhigh; //i17
    public String yrlow; //i18

// Left Table
    public String Nominal; //i8
    public String High; //i6
    public String Low; //i7
    public String PrevClose; //s3
    public String AvgPrice;
    public String Volume; //i11
    public String Turnover; //i12
    public String LowerSpread; //i63
    public String UpperSpread; //i64
    public String LotSize; //s4
    public String Chg; //i9
    public String PecentChg; //i10
    public String HH;

//the Top right Data
    public String PE; //i14
    public String Yield; //i13
    public String HSI;
    public String HSIF;

// Sell Summary    //i19 to i33
    public String ask[] = new String[5];
    public String nask[] = new String[5];
    public String iask[] = new String[5];

// Buy Summary    //i34 to i48
    public String bid[] = new String[5];
    public String nbid[] = new String[5];
    public String ibid[] = new String[5];

// trade activity summary //i49 to i62
    public String act[] = new String[4];
    public String nact[] = new String[4];
    public String tact[] = new String[4];

// Buy Brokers //all the q data
    public String BuyBroker[][] = new String[5][10];

// Sell Brokers
    public String SellBroker[][] = new String[5][10];

// The corresping Flags
    public int Flag_Nominal;
    public int Flag_High;
    public int Flag_Low;
    public int Flag_PrevClose;
    public int Flag_AvgPrice;
    public int Flag_Volume;
    public int Flag_Turnover;
    public int Flag_LowerSpread;
    public int Flag_UpperSpread;
    public int Flag_LotSize;
    public int Flag_Chg;
    public int Flag_PecentChg;
    public int Flag_HH;

//the Top right Data
    public int Flag_PE;
    public int Flag_Yield;
    public int Flag_HSI;
    public int Flag_HSIF;

// Sell Summary
    public int Flag_ask[] = new int[5];
    public int Flag_nask[] = new int[5];
    public int Flag_iask[] = new int[5];

// Buy Summary
    public int Flag_bid[] = new int[5];
    public int Flag_nbid[] = new int[5];
    public int Flag_ibid[] = new int[5];

// trade activity summary
    public int Flag_act[] = new int[4];
    public int Flag_nact[] = new int[4];
    public int Flag_tact[] = new int[4];

// Buy Brokers
    public int Flag_BuyBroker[][] = new int[4][10];

// Sell Brokers
    public int Flag_SellBroker[][] = new int[4][10];

    public TeleTextSource() {
        // Generate Information
        Name = "XXX"; //s1
        CName = "XXX"; //s2
        numshare = "XXX"; //s5
        Code = "0001"; //i0
        date = "XXX"; //i1
        time = "XXX"; //i2
        currbid = "XXX"; //i3
        currask = "XXX"; //i4
        open = "XXX"; //i5
        premium = "XXX"; //i15
        Wration = "XXX"; //i16
        yrhigh = "XXX"; //i17
        yrlow = "XXX"; //i18

        // Left Table
        Nominal = "XXX";
        High = "XXX";
        Low = "XXX";
        PrevClose = "XXX";
        AvgPrice = "XXX";
        Volume = "XXX";
        Turnover = "XXX";
        LowerSpread = "XXX";
        UpperSpread = "XXX";
        LotSize = "XXX";
        Chg = "XXX";
        PecentChg = "XXX";
        HH = "XXX";

        Flag_Nominal = 5;
        Flag_High = 5;
        Flag_Low = 5;
        Flag_PrevClose = 5;
        Flag_AvgPrice = 5;
        Flag_Volume = 5;
        Flag_Turnover = 5;
        Flag_LowerSpread = 5;
        Flag_UpperSpread = 5;
        Flag_LotSize = 5;
        Flag_Chg = 5;
        Flag_PecentChg = 5;
        Flag_HH = 5;

        //the Top right Data
        PE = "XXX";
        Yield = "XXX";
        HSI = "XXX";
        HSIF = "XXX";

        Flag_PE = 5;
        Flag_Yield = 5;
        Flag_HSI = 5;
        Flag_HSIF = 5;

        // Sell Summary
        for (int i = 0; i < 5; i++) {
            ask[i] = "XXX";
            nask[i] = "XXX";
            iask[i] = "XXX";
            Flag_ask[i] = 5;
            Flag_nask[i] = 5;
            Flag_iask[i] = 5;
        }

        // Buy Summary
        for (int i = 0; i < 5; i++) {
            bid[i] = "XXX";
            nbid[i] = "XXX";
            ibid[i] = "XXX";
            Flag_bid[i] = 5;
            Flag_nbid[i] = 5;
            Flag_ibid[i] = 5;

        }

        // trade activity summary
        for (int i = 0; i < 4; i++) {
            act[i] = "XXX";
            nact[i] = "XXX";
            tact[i] = "XXX";
            Flag_act[i] = 5;
            Flag_nact[i] = 5;
            Flag_tact[i] = 5;
        }

        // Buy and Sell Brokers
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 10; j++) {
                BuyBroker[i][j] = "XXXX";
                SellBroker[i][j] = "XXXX";
                Flag_BuyBroker[i][j] = 5;
                Flag_SellBroker[i][j] = 5;
            }
        }
    }

}