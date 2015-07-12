
/**
 * Title:        FME Chart Project for E-finet<p>
 * Description:  <p>
 * Copyright:    Copyright (c) <p>
 * Company:      <p>
 * @author
 * @version 1.0
 */
package fmechart;

import java.awt.*;

public class FLine
{
  static final boolean isFixedLine = false;

  Point point1 = new Point();
  Point point2 = new Point();
  Color lineColor;


  int index1=0;
  double value1=0f;
  int index2=0;
  double value2 =0f;




  public FLine(int x1, float y1, int x2, float y2)
  {
    index1 = x1;
    value1 = y1;
    index2 = x2;
    value2 = y2;
  }

  public FLine(int x1, float y1, int x2, float y2,Color lcolor)
  {
    index1 = x1;
    value1 = y1;
    index2 = x2;
    value2 = y2;
    lineColor = lcolor;
  }

  public FLine(int x1,int y1,int x2,int y2,Color cc)
  {
    point1 = new Point(x1,y1);
    point2 = new Point(x2,y2);
    lineColor = cc;
  }

  public FLine(int x1,int y1,int x2,int y2)
  {
    point1 = new Point(x1,y1);
    point2 = new Point(x2,y2);
  }

}