
/**
 * Title:        FME Chart Project for E-finet<p>
 * Description:  <p>
 * Copyright:    Copyright (c) <p>
 * Company:      <p>
 * @author
 * @version 1.0
 */
package fmechart;
import java.util.Vector;
import java.awt.Cursor;
import java.awt.Point;
import java.util.Vector;

// record the actions of insert lines, delete lines and so on.....
public class FAction
{

  public static final int NONEACTION = 0;
  public static final int WATCH = 1;
  public static final int INSERTLINE = 2;
  public static final int INSERTPARALLELLINE = 3;
  public static final int GOLDENPARTITION = 4;
  public static final int ZOOMIN = 5;
  public static final int MOVECHART = 6;

  public Cursor watchCursor;

  public Point pressMpoint = new Point();  //the point where the mouse click enter
  public Point currentMpoint = new Point();  //the point of current mouse
  public Point releaseMpoint = new Point();  //the point where the mouse click release
  public boolean actionProcessing = false;



  //Command record
  public Vector zoomRecords = new Vector();
  public Vector lineRecords = new Vector();
  public FLine goldenPartitionLine = null;



//  public int currentActionType = INSERTLINE;
  public int currentActionType = ZOOMIN;
  public FAction()
  {
    //watchCursor = new Cursor(Cursor.CUSTOM_CURSOR);

  }

}