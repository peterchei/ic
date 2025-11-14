package com.ic.data;

/**
 * Enum to indicate Chart Data Interval
 */
public class FText extends FRecord {

  private int xIndex = 0;
  private double yValue = 0f;
  private String text = "Testing";

  public FText(String text, int xAsIndex, double yAsValue) {
    this.setText(text);
    this.setXIndex(xAsIndex);
    this.setYValue(yAsValue);
  }

  public int getXIndex() {
    return xIndex;
  }

  public void setXIndex(int xIndex) {
    this.xIndex = xIndex;
  }

  public double getYValue() {
    return yValue;
  }

  public void setYValue(double yValue) {
    this.yValue = yValue;
  }

  public String getText() {
    return text;
  }

  public void setText(String text) {
    this.text = text;
  }
}
