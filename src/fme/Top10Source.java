/**
 * Title:        <p>
 * Description:  <p>
 * Copyright:    Copyright (c) <p>
 * Company:      <p>
 * @author
 * @version 1.0
 */
package fme;

public class Top10Source {
    public final static int typeTop10Gain = 0;
    public final static int typeTop10Lose = 1;
    public final static int typeTop10Volume = 2;
    public final static int typeTup10Turnover = 3;

    public Item TopGain[] = new Item[10];
    public Item TopLose[] = new Item[10];
    public Item TopVolume[] = new Item[10];
    public Item TopTurnover[] = new Item[10];
    public int Type = typeTup10Turnover;

    public Item[] GetCurrentItem() {
        Item currentItems[] = new Item[10];
        ;
        if (Type == typeTop10Gain) {
            currentItems = TopGain;
        } else if (Type == typeTop10Lose) {
            currentItems = TopLose;
        } else if (Type == typeTop10Volume) {
            currentItems = TopVolume;
        } else if (Type == typeTup10Turnover) {
            currentItems = TopTurnover;
        }
        return currentItems;
    }

    public Top10Source() {
        for (int i = 0; i < 10; i++) {
            TopGain[i] = new Item();
            TopLose[i] = new Item();
            TopVolume[i] = new Item();
            TopTurnover[i] = new Item();
        }

    }

}

class Item {
    public String Code = "";
    public String Name = "";
    public String cName = "";
    public String Open = "";
    public String Last = "";
    public String Change = "";
    public String PCChg = "";
    public String High = "";
    public String Low = "";
    public String Volume = "";
    public String Turnover = "";
    public String prevClose = "";

//LowPrice and HighPrice is for the alert only
    public String AlertHigh = "";
    public String AlertLow = "";
    public boolean Alert = false;

}
