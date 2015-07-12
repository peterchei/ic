/**
 * Title:        <p>
 * Description:  <p>
 * Copyright:    Copyright (c) <p>
 * Company:      <p>
 * @author
 * @version 1.0
 */
package fme;

public class CommentarySource {
    public int NumberOfHeaders;

    //public int LastestID;

    private final int MaxHeaders = 100;
    CommentaryObject newsObjects[] = new CommentaryObject[MaxHeaders]; //Max 100 news.

    public CommentarySource() {
        NumberOfHeaders = 0;
        for (int i = 0; i < MaxHeaders; i++) {
            newsObjects[i] = new CommentaryObject();
        }
    }

}

class CommentaryObject {
    public String Date;
    public String Time;
    public String ID;
    public String Type;
    public String Header;
    public String Body;

    public CommentaryObject() {
//    System.out.println("Create");
        Date = "";
        Time = "";
        ID = "";
        Type = "X";
        Header = "";
        Body = "";

    }

}
