package com.ic.core;

import com.ic.data.FLine;
import com.ic.data.Point;

import java.util.Vector;

// record the actions of insert lines, delete lines and so on.....
public class ActionCommand {

    public enum Type {
        NONEACTION,
        WATCH,
        INSERTLINE,
        INSERTPARALLELLINE,
        GOLDENPARTITION,
        ZOOMIN,
        MOVECHART,
        EDITTEXT
    }

    private Point startMousePoint = new Point();    //the point where the mouse click enter
    private Point currentMousePoint = new Point();  //the point of current mouse
    private Point releaseMousePoint = new Point();  //the point where the mouse click release
    private boolean isProcessing = false;

    private Vector zoomRecords = new Vector();
    private Vector lineRecords = new Vector();
    private FLine goldenPartitionLine = null;

    private Type actionType = Type.ZOOMIN;

    public Point getStartMousePoint() {
        return startMousePoint;
    }

    public void setStartMousePoint(Point startMousePoint) {
        this.startMousePoint = startMousePoint;
    }

    public Point getCurrentMousePoint() {
        return currentMousePoint;
    }

    public void setCurrentMousePoint(Point currentMousePoint) {
        this.currentMousePoint = currentMousePoint;
    }

    public Point getReleaseMousePoint() {
        return releaseMousePoint;
    }

    public void setReleaseMousePoint(Point releaseMousePoint) {
        this.releaseMousePoint = releaseMousePoint;
    }

    public boolean isProcessing() {
        return isProcessing;
    }

    public void setProcessing(boolean processing) {
        isProcessing = processing;
    }

    public Vector getZoomRecords() {
        return zoomRecords;
    }

    public void setZoomRecords(Vector zoomRecords) {
        this.zoomRecords = zoomRecords;
    }

    public Vector getLineRecords() {
        return lineRecords;
    }

    public void setLineRecords(Vector lineRecords) {
        this.lineRecords = lineRecords;
    }

    public FLine getGoldenPartitionLine() {
        return goldenPartitionLine;
    }

    public void setGoldenPartitionLine(FLine goldenPartitionLine) {
        this.goldenPartitionLine = goldenPartitionLine;
    }

    public Type getActionType() {
        return actionType;
    }

    public void setActionType(Type actionType) {
        this.actionType = actionType;
    }


}
