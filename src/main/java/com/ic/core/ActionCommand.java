package com.ic.core;

import com.ic.data.FLine;
import com.ic.data.FRecord;
import com.ic.data.Point;

import java.util.Vector;

/**
 * Action Command stores the actions that performed on the Screen
 * <p/>
 * It records the actions of insert lines, delete lines and so on.....
 */
public class ActionCommand {

  private Point startMousePoint = new Point();    //the point where the mouse click enter
  private Point currentMousePoint = new Point();  //the point of current mouse
  private Point releaseMousePoint = new Point();  //the point where the mouse click release
  private boolean isProcessing = false;

  private Vector zoomRecords = new Vector();
  private Vector<FRecord> lineRecords = new Vector<FRecord>();
  private Vector<FRecord> textRecords = new Vector<FRecord>();
  private FLine goldenPartitionLine = null;

  private ActionType actionType = ActionType.ZOOM_IN;

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

  public ActionType getActionType() {
    return actionType;
  }

  public void setActionType(ActionType actionType) {
    this.actionType = actionType;
  }


  public Vector getTextRecords() {
    return textRecords;
  }

  public void setTextRecords(Vector textRecords) {
    this.textRecords = textRecords;
  }
}
