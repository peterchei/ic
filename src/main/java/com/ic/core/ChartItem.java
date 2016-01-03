package com.ic.core;

import com.ic.data.ChartData;
import com.ic.data.FBound;

import java.awt.Color;


public class ChartItem {

    //the key of the chart, it must be an unique ID in the screen.
    private String key = "";
    //the Y Axis
    private AxisType axisBar = AxisType.NONE;
    //the UI type of chart
    private ChartType chartType = ChartType.LINE;
    private ChartData chartData = null;
    private Color firstColor = Color.blue;
    private Color secondColor = Color.red;
    private Color thirdColor = Color.green;
    private boolean visible = true;
    private boolean showXaxis = true;


    //the upper bound and lower bound of the bound, this variable is set in the setLeftBound and setRightBound function in the ChartScreen class.
    private FBound chartBound;

    public ChartItem(ChartData newChartData, String key) {
        setChartData(newChartData);
        this.setKey(key);
    }

    public double getLowerBound() {
        if (getChartBound() != null)
            switch (getChartType()) {
                case BAR:
                    return getChartBound().getLowerStockBound();
                case CANDLE:
                    return getChartBound().getLowerStockBound();
                case LINE:
                    return getChartBound().getLowerStockBound();
                case VOLUME:
                    return getChartBound().getLowerVolumeBound();
                case PERCENTAGE:
                    return getChartBound().getLowerPercentageBound();
                case SIMPLE_MOVING_AVERAGE:
                    return getChartBound().getLowerStockBound();
                case WEIGHTED_MOVING_AVERAGE:
                    return getChartBound().getLowerStockBound();
                case EXPONENTIAL_MOVING_AVERAGE:
                    return getChartBound().getLowerStockBound();
                case BOLLINGERBAND:
                    return getChartBound().getLowerStockBound();
                case RSI:
                    return getChartBound().getLowerRSIBound();
                case MACD:
                    return getChartBound().getLowerMACDBound();
                case OBV:
                    return getChartBound().getLowerOBVBound();
                case WILLIAM_R:
                    return getChartBound().getLowerWilliamRBound();
                case STC:
                    return getChartBound().getLowerSTCBound();
            }
        return 0f;
    }

    public double getUpperBound() {

        if (getChartBound() != null)
            switch (getChartType()) {
                case BAR:
                    return getChartBound().getUpperStockBound();
                case CANDLE:
                    return getChartBound().getUpperStockBound();
                case LINE:
                    return getChartBound().getUpperStockBound();
                case VOLUME:
                    return getChartBound().getUpperVolumeBound();
                case PERCENTAGE:
                    return getChartBound().getUpperPercentageBound();
                case SIMPLE_MOVING_AVERAGE:
                    return getChartBound().getUpperStockBound();
                case WEIGHTED_MOVING_AVERAGE:
                    return getChartBound().getUpperStockBound();
                case EXPONENTIAL_MOVING_AVERAGE:
                    return getChartBound().getUpperStockBound();
                case BOLLINGERBAND:
                    return getChartBound().getUpperStockBound();
                case RSI:
                    return getChartBound().getUpperRSIBound();
                case MACD:
                    return getChartBound().getUpperMACDBound();
                case OBV:
                    return getChartBound().getUpperOBVBound();
                case WILLIAM_R:
                    return getChartBound().getUpperWilliamRBound();
                case STC:
                    return getChartBound().getUpperSTCBound();


            }
        return 0f;
    }

    public boolean isShowXaxis() {
        return showXaxis;
    }

    public void setShowXaxis(boolean ss) {
        showXaxis = ss;
    }

    public void setVisible(boolean isV) {
        visible = isV;
    }

    public boolean isVisible() {
        return visible;
    }

    public AxisType getAxisBar() {
        return axisBar;
    }

    public void setAxisBar(AxisType axisBar) {
        this.axisBar = axisBar;
    }

    public ChartType getChartType() {
        return chartType;
    }

    public void setChartType(ChartType chartType) {
        this.chartType = chartType;
    }

    public ChartData getChartData() {
        return chartData;
    }

    public void setChartData(ChartData chartData) {
        this.chartData = chartData;
    }

    public Color getFirstColor() {
        return firstColor;
    }

    public void setFirstColor(Color firstColor) {
        this.firstColor = firstColor;
    }

    public Color getSecondColor() {
        return secondColor;
    }

    public void setSecondColor(Color secondColor) {
        this.secondColor = secondColor;
    }

    public Color getThirdColor() {
        return thirdColor;
    }

    public void setThirdColor(Color thirdColor) {
        this.thirdColor = thirdColor;
    }

    public FBound getChartBound() {
        return chartBound;
    }

    public void setChartBound(FBound chartBound) {
        this.chartBound = chartBound;
    }

    public String getKey() { return key; }

    public void setKey(String key) {
        this.key = key;
    }
}
