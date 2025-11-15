package com.ic.core;

/**
 * Centralized chart label text repository replacing the former lbArray inside ChartScreen.
 * Provides safe static access by index and language.
 */
public final class ChartLabelText {
  private ChartLabelText() {}

  // Original label matrix (index order preserved)
  // 0..24
  private static final String[][] LB_ARRAY = {
      {"None", "無"}, // 0
      {"Simple Moving Average", "簡單移動平均線"}, // 1
      {"Weighted Moving Average", "加權移動平均線"}, // 2
      {"Exponential Moving Average", "指數移動平均線"}, // 3
      {"Bollinger Bands", "保歷加通道"}, // 4
      {"Open", "開市"}, // 5
      {"Close", "收市"}, // 6
      {"High", "最高"}, // 7
      {"Low", "最低"}, // 8
      {"Volume", "成交量"}, // 9
      {"RSI", "RSI"}, // 10
      {"STC", "STC"}, // 11
      {"EMA", "EMA"}, // 12
      {"WMA", "WMA"}, // 13
      {"SMA", "SMA"}, // 14
      {"Date", "期閒"}, // 15 (original encoding retained)
      {"Time", "期閒"}, // 16
      {"Relative Strength Index", "相對強弱指數"}, // 17
      {"Stochastics", "隨機指數"}, // 18
      {"On Balance Volume", "成交量平衡 指數"}, // 19
      {"Moving Average Convergence Divergence", "移動平均匯聚背馳"}, // 20
      {"William %R", "威廉指標"}, // 21
      {"Deviation", "Deviation"}, // 22
      {"Date Reference", "Date Reference"}, // 23
      {"Time Reference", "Time Reference"} // 24
  };

  /**
   * Returns label text at given index for specified language column.
   * @param index label index (0..length-1)
   * @param language language column (typically FConfig.constEnglish == 0)
   * @return label or empty string if out of bounds.
   */
  public static String getLabel(int index, int language) {
    if (index < 0 || index >= LB_ARRAY.length) {
      return "";
    }
    if (language < 0 || language >= LB_ARRAY[index].length) {
      // fallback to first (English)
      return LB_ARRAY[index][0];
    }
    return LB_ARRAY[index][language];
  }

  /**
   * @return number of labels.
   */
  public static int size() { return LB_ARRAY.length; }

  /**
   * Returns the entire row (copy) for a given index.
   */
  public static String[] getLabelRow(int index) {
    if (index < 0 || index >= LB_ARRAY.length) return new String[0];
    String[] src = LB_ARRAY[index];
    String[] copy = new String[src.length];
    System.arraycopy(src, 0, copy, 0, src.length);
    return copy;
  }
}

