package teletext.data;

import java.awt.*;

public class FLine {

    private static final boolean isFixedLine = true;
    private Point point1 = new Point();
    private Point point2 = new Point();
  //  private Color lineColor;
    private int index1 = 0;
    private double value1 = 0f;
    private int index2 = 0;
    private double value2 = 0f;
/*
    public Color getLineColor() {
        return lineColor;
    }

    public void setLineColor(Color lineColor) {
        this.lineColor = lineColor;
    }
*/
    public static boolean isFixedLine() {
        return isFixedLine;
    }
    public int getIndex1() {
        return index1;
    }

    public void setIndex1(int index1) {
        this.index1 = index1;
    }

    public int getIndex2() {
        return index2;
    }

    public void setIndex2(int index2) {
        this.index2 = index2;
    }

    public Point getPoint1() {
        return point1;
    }

    public void setPoint1(Point point1) {
        this.point1 = point1;
    }

    public Point getPoint2() {
        return point2;
    }

    public void setPoint2(Point point2) {
        this.point2 = point2;
    }

    public double getValue1() {
        return value1;
    }

    public void setValue1(double value1) {
        this.value1 = value1;
    }

    public double getValue2() {
        return value2;
    }

    public void setValue2(double value2) {
        this.value2 = value2;
    }


    public FLine(int x1, float y1, int x2, float y2) {
        index1 = x1;
        value1 = y1;
        index2 = x2;
        value2 = y2;
    }

 
/*
    public FLine(int x1, float y1, int x2, float y2, Color lcolor) {
        index1 = x1;
        value1 = y1;
        index2 = x2;
        value2 = y2;
        lineColor = lcolor;
    }

    public FLine(int x1, int y1, int x2, int y2, Color cc) {
        point1 = new Point(x1, y1);
        point2 = new Point(x2, y2);
        lineColor = cc;
    }
 */


    public FLine(int x1, int y1, int x2, int y2) {
        point1 = new Point(x1, y1);
        point2 = new Point(x2, y2);
    }

}