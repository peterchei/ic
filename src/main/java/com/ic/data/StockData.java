package com.ic.data;

import java.util.Date;

// a unit of each chart point,
public class StockData {

  private float Open = 0f;
  private float Close = 0f;
  private float Maximum = 0f;
  private float Minimum = 0f;
  private float Current = 0f; // current price
  private int Volume = 0;
  private float percent = 0.0f;
  private int Hour = 0;
  private int Minute = 0;
  private int lYear = 0;
  private int lMonth = 0;
  private int lDay = 0;
  private boolean isValid = true;
  private boolean isIntraDayMarked = false;
  private Date date = null;

  public float getOpen() {
    return Open;
  }

  public void setOpen(float open) {
    Open = open;
  }

  public float getClose() {
    return Close;
  }

  public void setClose(float close) {
    Close = close;
  }

  public float getMaximum() {
    return Maximum;
  }

  public void setMaximum(float maximum) {
    Maximum = maximum;
  }

  public float getMinimum() {
    return Minimum;
  }

  public void setMinimum(float minimum) {
    Minimum = minimum;
  }

  public float getCurrent() {
    return Current;
  }

  public void setCurrent(float current) {
    Current = current;
  }

  public int getVolume() {
    return Volume;
  }

  public void setVolume(int volume) {
    Volume = volume;
  }

  public float getPercent() {
    return percent;
  }

  public void setPercent(float percent) {
    this.percent = percent;
  }

  public int getHour() {
    return Hour;
  }

  public void setHour(int hour) {
    Hour = hour;
  }

  public int getMinute() {
    return Minute;
  }

  public void setMinute(int minute) {
    Minute = minute;
  }

  public int getlYear() {
    return lYear;
  }

  public void setlYear(int lYear) {
    this.lYear = lYear;
  }

  public int getlMonth() {
    return lMonth;
  }

  public void setlMonth(int lMonth) {
    this.lMonth = lMonth;
  }

  public int getlDay() {
    return lDay;
  }

  public void setlDay(int lDay) {
    this.lDay = lDay;
  }

  public boolean isValid() {
    return isValid;
  }

  public void setValid(boolean isValid) {
    this.isValid = isValid;
  }

  public boolean isIntraDayMarked() {
    return isIntraDayMarked;
  }

  public void setIntraDayMarked(boolean isIntraDayMarked) {
    this.isIntraDayMarked = isIntraDayMarked;
  }

  public Date getDate() {
    return date;
  }

  public void setDate(Date date) {
    this.date = date;
  }

  public int getMonth() {
    return date.getMonth() + 1;
  }

  public int getYear() {
    return date.getYear() + 1900;
  }

  public int getDay() {
    return date.getDate();
  }
}
