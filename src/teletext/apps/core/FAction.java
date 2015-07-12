package teletext.apps.core;

import teletext.data.FLine;

import java.awt.*;
import java.util.Vector;

// record the actions of insert lines, delete lines and so on.....
public class FAction {

    public enum Type {
        NONEACTION,
        WATCH,
        INSERTLINE,
        INSERTPARALLELLINE,
        GOLDENPARTITION,
        ZOOMIN,
        MOVECHART
    };
    public Cursor watchCursor;
    public Point pressMpoint = new Point();  //the point where the mouse click enter
    public Point currentMpoint = new Point();  //the point of current mouse
    public Point releaseMpoint = new Point();  //the point where the mouse click release
    public boolean actionProcessing = false;
    //Command record
    public Vector zoomRecords = new Vector();
    public Vector lineRecords = new Vector();
    public FLine goldenPartitionLine = null;
    //public int currentActionType = INSERTLINE;
    public Type currentActionType = Type.ZOOMIN;

    public FAction() {
        //watchCursor = new Cursor(Cursor.CUSTOM_CURSOR);
    }
}
