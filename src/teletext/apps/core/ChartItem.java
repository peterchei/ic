package teletext.apps.core;

import teletext.apps.ui.FBound;
import teletext.data.ChartData;

import java.awt.*;

public class ChartItem {
//constant to specify the type of chart
	

	//the Y Axis
    public AxisType axisBar = AxisType.NONE;

    //the UI type of chart
    public ChartType chartType = ChartType.LINE;

    public ChartData chartData = null;
    public Color firstColor = Color.blue;
    public Color secondColor = Color.red;
    public Color thirdColor = Color.green;

    //the key of the chart, it must be an unique ID in the screen.
    public String idKey = "";
    private boolean visable = true;
    private boolean showXaxis = true;


    //the upper bound and lower bound of the bound, this variable is set in the setLeftBound and setRightBound function in the ChartScreen class.
    FBound chartBound;

    //public double upperBound=0.0f;
    //public double lowerBound=10000.0f;
    public double getLowerBound() {
        if (chartBound != null)
            switch (chartType) {
                case BAR:
                    return chartBound.LowerStockBound;
                case CANDLE:
                    return chartBound.LowerStockBound;
                case LINE:
                    return chartBound.LowerStockBound;
                case VOLUME:
                    return chartBound.LowerVolumeBound;
                case PERCENTAGE:
                    return chartBound.LowerPercentageBound;
                case SIMPLE_MOVING_AVERAGE:
                    return chartBound.LowerStockBound;
                case WEIGHTED_MOVING_AVERAGE:
                    return chartBound.LowerStockBound;
                case EXPONENTIAL_MOVING_AVERAGE:
                    return chartBound.LowerStockBound;
                case BOLLINGERBAND:
                    return chartBound.LowerStockBound;
                case RSI:
                    return chartBound.LowerRSIBound;
                case MACD:
                    return chartBound.LowerMACDBound;
                case OBV:
                    return chartBound.LowerOBVBound;
                case WILLIAM_R:
                    return chartBound.LowerWilliamRBound;
                case STC:
                    return chartBound.LowerSTCBound;
                    }
        return 0f;
    }

    public double getUpperBound() {

        if (chartBound != null)
            switch (chartType) {
                case BAR:
                    return chartBound.UpperStockBound;
                case CANDLE:
                    return chartBound.UpperStockBound;
                case LINE:
                    return chartBound.UpperStockBound;
                case VOLUME:
                    return chartBound.UpperVolumeBound;
                case PERCENTAGE:
                    return chartBound.UpperPercentageBound;
                case SIMPLE_MOVING_AVERAGE:
                    return chartBound.UpperStockBound;
                case WEIGHTED_MOVING_AVERAGE:
                    return chartBound.UpperStockBound;
                case EXPONENTIAL_MOVING_AVERAGE:
                    return chartBound.UpperStockBound;
                case BOLLINGERBAND:
                    return chartBound.UpperStockBound;
                case RSI:
                    return chartBound.UpperRSIBound;
                case MACD:
                    return chartBound.UpperMACDBound;
                case OBV:
                    return chartBound.UpperOBVBound;
                case WILLIAM_R:
                    return chartBound.UpperWilliamRBound;
                case STC:
                    return chartBound.UpperSTCBound;


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
        visable = isV;
    }

    public boolean isVisable() {
        return visable;
    }

    public ChartItem(ChartData newChartData, String key) {
        chartData = newChartData;
        idKey = key;
    }


}
