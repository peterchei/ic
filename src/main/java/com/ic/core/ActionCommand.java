package com.ic.core;

import com.ic.data.FLine;

import java.awt.*;
import java.util.Vector;

// record the actions of insert lines, delete lines and so on.....
public class ActionCommand {

    public ActionCommand() {
    }
    public Point pressMpoint = new Point();    //the point where the mouse click enter
    public Point currentMpoint = new Point();  //the point of current mouse
    public Point releaseMpoint = new Point();  //the point where the mouse click release
    public boolean actionProcessing = false;

    public Vector zoomRecords = new Vector();
    public Vector lineRecords = new Vector();
    public FLine goldenPartitionLine = null;
    public Type actionType = Type.ZOOMIN;


    public enum Type {
        NONEACTION,
        WATCH,
        INSERTLINE,
        INSERTPARALLELLINE,
        GOLDENPARTITION,
        ZOOMIN,
        MOVECHART
    }
}
