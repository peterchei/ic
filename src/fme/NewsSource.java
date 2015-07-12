/**
 * Title:        <p>
 * Description:  <p>
 * Copyright:    Copyright (c) <p>
 * Company:      <p>
 * @author
 * @version 1.0
 */
package fme;

public class NewsSource {
    public int NumberOfHeaders;

    //public int LastestID;

    private final int MaxHeaders = 100;
    NewsObject newsObjects[] = new NewsObject[MaxHeaders]; //Max 100 news.

    public NewsSource() {
        NumberOfHeaders = 0;
        for (int i = 0; i < MaxHeaders; i++) {
            newsObjects[i] = new NewsObject();
        }
    }

}

class NewsObject {
    public String Date;
    public String Time;
    public int ID;
    public String Type;
    public String Header;
    public String Body;

    public NewsObject() {
//    System.out.println("Create");
        Date = "";
        Time = "";
        ID = -1;
        Type = "X";
        Header = "";
        Body = "";

    }

}