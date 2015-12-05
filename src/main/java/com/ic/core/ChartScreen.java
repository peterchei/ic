package com.ic.core;

import com.ic.data.ChartData;
import com.ic.data.FLine;
import com.ic.data.FPoint;
import com.ic.data.FTAPoint;
import com.ic.util.FormatUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.text.SimpleDateFormat;
import java.util.Vector;

public class ChartScreen extends JPanel implements MouseListener, MouseMotionListener {

    ///Here is some color variable
    //Color backgroundColor = FConfig.ScreenBackground;
    public static final int NONE = 0;
    public static final int LOADING = 1;
    public static final int STARTED = 2;
    private static final long serialVersionUID = -6984851432299222149L;
    private final String lbArray[][] = {
            {"None", "\u7121"} //0
            , {"Simple Moving Average", "\u7c21\u55ae\u79fb\u52d5\u5e73\u5747\u7dda"} //1
            , {"Weighted Moving Average", "\u52a0\u6b0a\u79fb\u52d5\u5e73\u5747\u7dda"} //2
            , {"Exponential Moving Average", "\u6307\u6578\u79fb\u52d5\u5e73\u5747\u7dda"} //3
            , {"Bollinger Bands", "\u4fdd\u6b77\u52a0\u901a\u9053"} //4
            , {"Open", "\u958b\u5e02"} //5
            , {"Close", "\u6536\u5e02"} //6
            , {"High", "\u6700\u9ad8"} //7
            , {"Low", "\u6700\u4f4e"} //8
            , {"Volume", "\u6210\u4ea4"}//\u91cf"}                    //9
            , {"RSI", "相對強弱指數"} //10
            , {"STC", "STC"} //11
            , {"EMA", "EMA"} //12
            , {"WMA", "WMA"} //13
            , {"SMA", "SMA"} //14
            , {"Date", "\u671f\u9593"} //15
            , {"Time", "\u671f\u9593"} //16
            , {"Relative Strength Index", "\u76f8\u5c0d\ufffd\u5f31\u6307\u6578"} //17
            , {"Stochastics", "\u96a8\u6a5f\u6307\u6578"} //18
            , {"On Balance Volume", "\u6210\u4ea4\u91cf\u5e73 \u6307\u6578"} //19
            , {"Moving Average Convergence Divergence", "\u79fb\u52d5\u5e73\u5747\u7dda\u532f\u805a\u80cc\u99b3\u6307\u6a19"} //20
            //,{"MACD","\u79fb\u52d5\u5e73\u5747\u7dda\u532f\u805a\u80cc\u99b3\u6307\u6a19"}     //20
            , {"William %R", "\u5a01\u5ec9\u6307\u6a19"} //21
            , {"Deviation", "Deviation"} //22
            , {"Date Reference", "Date Reference"} //23
            , {"Time Reference", "Time Reference"} //24
    };
    //Define of Resolution and display information
    private final int minResolution = 3;
    //The screenImage for drawing....
    public Image screenImage = null; //the chart image
    public Image allscreenImage = null; //the full screen image
    public Image loadingBarImage[] = new Image[4];
    //Action Object to record and repersent all the action.
    FAction faction = new FAction();
    private int screenState = STARTED;
    private boolean isUpdatingBaseScreen = false; // check isUpdatingBaseScreen or not.
    private Color gridColor;
    private int language = FConfig.constChinese;
    ///The space of top, left, right and bottom in pixels.
    private int topSpace = 30;
    private int leftSpace = 38;
    private int rightSpace = 38;
    private int bottomSpace = 15;
    //The maximun number of point that the chart have....
    private int maxNumberOfChartPoint = 0;
    private int loadingBarIndex = 0;
    private int resolution = 3; //must be < 4 pixels per point.
    private int startDisplayIndex = 0;
    private int endDisplayIndex = 0;
    private ScreenActionListener screenActionListener = null;
    //A list of add ChartUIObject needed to plot to this screen.....
    private Vector chartObjects = new Vector();
    public ChartScreen(int TOPSpace, int BOTTOMSpace, int LEFTSpace, int RIGHTSpace) {
        topSpace = TOPSpace;
        bottomSpace = BOTTOMSpace;
        leftSpace = LEFTSpace;
        rightSpace = RIGHTSpace;
        try {
            //Enable Mouse Event Listeners.
            addMouseListener(this);
            addMouseMotionListener(this);
            //init the ui component
            jbInit();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    public ChartScreen() {
        try {
            //Enable Mouse Event Listeners.
            addMouseListener(this);
            addMouseMotionListener(this);
            //init the ui component
            jbInit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int getScreenState() {
        return screenState;
    }

    public void setScreenState(int ss) {
        screenState = ss;
        this.repaint();
    }

    public void setLanguage(int tlanguage) {
        language = tlanguage;
    }

    public FAction getFaction() {
        return faction;
    }

    public void setFaction(FAction faction) {
        this.faction = faction;
    }

    //get the chart given the key.
    public ChartItem getChart(String key) {
        for (int i = 0; i < chartObjects.size(); i++) {
            ChartItem pchart = (ChartItem) chartObjects.elementAt(i);
            if (pchart.idKey.equals(key)) {
                return pchart;
            }
        }
        return null;
    }

    public FAction getAction() {
        return faction;
    }

    //add a chart to this screen
    public boolean addChart(ChartItem newChart) {
        int tempNumb = 0;
        for (int i = 0; i < chartObjects.size(); i++) {
            ChartItem cchart = (ChartItem) chartObjects.elementAt(i);
            if (cchart.idKey.equals(newChart.idKey)) {
                chartObjects.removeElement(cchart);
            }
        }
        chartObjects.addElement(newChart);
        // get the max Number Of Chart Point......

        for (int i = 0; i < this.chartObjects.size(); i++) {
            ChartItem cchart = (ChartItem) chartObjects.elementAt(i);
            if (cchart.getChartData().getData().size() > tempNumb) {
                tempNumb = cchart.getChartData().getData().size();
            }
        }
        maxNumberOfChartPoint = tempNumb;

        return true;
    }

    //get the LowerBound and UpperBound of the L or R chart
    private FBound getAxisBound(AxisType axisBarType) {
        double bStockMax = -1000000f;
        double bStockMin = 1000000f;

        float bPercentageMax = 0;
        float bPercentageMin = 100;

        float bMACDMax = -1000000f;
        float bMACDMin = 1000000f;

        float bOBVMax = -10000f;
        float bOBVMin = 100000f;

        double bVolumeMax = 0;
        double bVolumeMin = 0;

        for (int i = 0; i < chartObjects.size(); i++) {
            ChartItem cchart = (ChartItem) chartObjects.elementAt(i);
            if (cchart.getAxisBar() == axisBarType) {
                if (cchart.getChartType() == ChartType.BAR || cchart.getChartType() == ChartType.CANDLE || cchart.getChartType() == ChartType.LINE) {
                    double Max = cchart.getChartData().getMaximum(startDisplayIndex, endDisplayIndex, "STOCK");
                    if (Max > bStockMax) {
                        bStockMax = Max;
                    }
                    double Min = cchart.getChartData().getMinimum(startDisplayIndex, endDisplayIndex, "STOCK");
                    if (Min < bStockMin) {
                        bStockMin = Min;
                    }
                }

                if (cchart.getChartType() == ChartType.VOLUME) {
                    double Max = cchart.getChartData().getMaximumVolume(startDisplayIndex, endDisplayIndex);
                    if (Max > bVolumeMax) {
                        bVolumeMax = Max;
                    }
                }
            }

            if (cchart.getChartType() == ChartType.PERCENTAGE) {
                float Max = (float) cchart.getChartData().getMaximum(startDisplayIndex, endDisplayIndex, "PERCENTAGE");
                if (Max > bPercentageMax) {
                    bPercentageMax = Max;
                }
                float Min = (float) cchart.getChartData().getMinimum(startDisplayIndex, endDisplayIndex, "PERCENTAGE");
                if (Min < bPercentageMin) {
                    bPercentageMin = Min;
                }
            }

            if (cchart.getChartType() == ChartType.SIMPLE_MOVING_AVERAGE || cchart.getChartType() == ChartType.WEIGHTED_MOVING_AVERAGE || cchart.getChartType() == ChartType.EXPONENTIAL_MOVING_AVERAGE) {
                double Max = cchart.getChartData().getMaximum(startDisplayIndex, endDisplayIndex, "STOCK");
                if (Max > bStockMax) {
                    bStockMax = Max;
                }
                double Min = cchart.getChartData().getMinimum(startDisplayIndex, endDisplayIndex, "STOCK");
                if (Min < bStockMin) {
                    bStockMin = Min;
                }
            }

            if (cchart.getChartType() == ChartType.BOLLINGERBAND) {
                double Max = cchart.getChartData().getMaximum(startDisplayIndex, endDisplayIndex, "STOCK");
                if (Max > bStockMax) {
                    bStockMax = Max;
                }
                double Min = cchart.getChartData().getMinimum(startDisplayIndex, endDisplayIndex, "STOCK");
                if (Min < bStockMin) {
                    bStockMin = Min;
                }
            }


            if (cchart.getChartType() == ChartType.MACD) {
                double Max = cchart.getChartData().getMaximum(startDisplayIndex, endDisplayIndex, "MACD");
                if (Max > bStockMax) {
                    bMACDMax = (float) Max;
                }
                double Min = cchart.getChartData().getMinimum(startDisplayIndex, endDisplayIndex, "MACD");
                if (Min < bStockMin) {
                    bMACDMin = (float) Min;
                }
            }

            if (cchart.getChartType() == ChartType.OBV) {
                double Max = cchart.getChartData().getMaximum(startDisplayIndex, endDisplayIndex, "OBV");
                if (Max > bStockMax) {
                    bOBVMax = (float) Max;
                }
                double Min = cchart.getChartData().getMinimum(startDisplayIndex, endDisplayIndex, "OBV");
                if (Min < bStockMin) {
                    bOBVMin = (float) Min;
                }
            }
        }

        FBound newBound = new FBound();

        double ss = (bStockMax - bStockMin) * 0.1f;
        newBound.setLowerStockBound(bStockMin - ss);
        newBound.setUpperStockBound(bStockMax + ss);
        newBound.setLowerVolumeBound(0);
        newBound.setUpperVolumeBound(bVolumeMax + 5);
        ss = (bPercentageMax - bPercentageMin) * 0.1f;
        newBound.setLowerPercentageBound((float) bPercentageMin - (float) ss);
        newBound.setUpperPercentageBound((float) bPercentageMax + (float) ss);
        ss = (bMACDMax - bMACDMin) * 0.1f;
        newBound.setLowerMACDBound((float) bMACDMin - (float) ss);
        newBound.setUpperMACDBound((float) bMACDMax + (float) ss);
        newBound.setLowerOBVBound((int) bOBVMin);
        newBound.setUpperOBVBound((int) bOBVMax);

        for (int i = 0; i < chartObjects.size(); i++) {
            ChartItem cchart = (ChartItem) chartObjects.elementAt(i);
            if (cchart.getAxisBar() == axisBarType) {
                cchart.setChartBound(newBound);
            }
            if (cchart.getChartType() == ChartType.PERCENTAGE && axisBarType == AxisType.LEFTAXIS) {
                cchart.setChartBound(newBound);
            }
        }
        return newBound;
    }

    //function to initScreen, it should be called once in applet.start function in order to fix UI bug.
    public void initScreen() {
        if (getSize().width > 0 && getSize().height > 0) {
            if (screenImage == null) {
                screenImage = createImage(getSize().width, getSize().height);
            }
            if (allscreenImage == null) {
                allscreenImage = createImage(getSize().width, getSize().height);
            }

            if (loadingBarImage[0] == null) {
                loadingBarImage[0] = new ImageIcon(getClass().getResource("/LoadingBar0.png")).getImage();
                loadingBarImage[1] = new ImageIcon(getClass().getResource("/LoadingBar1.png")).getImage();
                loadingBarImage[2] = new ImageIcon(getClass().getResource("/LoadingBar2.png")).getImage();
                loadingBarImage[3] = new ImageIcon(getClass().getResource("/LoadingBar3.png")).getImage();
            }
        }
    }

    public int getStartDisplayIndex() {
        return startDisplayIndex;
    }

    public int getEndDisplayIndex() {
        return endDisplayIndex;
    }

    //remove a chart from this screen given the name
    public boolean removeChart(String key) {
        for (int i = 0; i < chartObjects.size(); i++) {
            ChartItem chartobj = (ChartItem) chartObjects.elementAt(i);
            if (chartobj.idKey.equals(key)) {
                chartObjects.removeElementAt(i);
                return true;
            }
        }
        return false;
    }

    //remove a chart from this screen given the name
    public boolean removeChart(int code) {
        for (int i = 0; i < chartObjects.size(); i++) {
            ChartItem chartobj = (ChartItem) chartObjects.elementAt(i);
            if (chartobj.getChartData().getCode() == code) {
                chartObjects.removeElementAt(i);
            }
        }

        return true;
    }

    public void removeChartsByType(ChartType cType) {
        for (int i = 0; i < chartObjects.size(); i++) {
            ChartItem chartobj = (ChartItem) chartObjects.elementAt(i);
            if (chartobj.getChartType() == cType) {
                chartObjects.removeElementAt(i);
                i = -1;
            }
        }
    }

    // determine the x,y is within the chartRegion or not.
    boolean isWithinChartRegion(int x, int y) {
        if (x >= leftSpace && x <= (getXAxisWidth() + leftSpace + 2)) {
            if (y >= topSpace && y <= (getYAxisWidth() + topSpace)) {
                return true;
            }
        }
        return false;
    }

    // return the width of X Axis in pixels
    int getXAxisWidth() {
        return getSize().width - leftSpace - rightSpace;
    }

    /**
     * return the height of Y Axis in pixels.
     */
    int getYAxisWidth() {
        return getSize().height - topSpace - bottomSpace;
    }

    // jbInit is used to init UI components and is mainly generated by the Jbuilder.
    private void jbInit() throws Exception {
        this.setBackground(FConfig.ScreenBackground);
        this.setLayout(null);
    }

    @Override
    public void setBounds(int x, int y, int w, int h) {

        if (h == 0) {
            return;
        }
        Image newImage1 = null;
        Image newImage2 = null;
        try {
            if (screenImage != null && (screenImage.getHeight(this) != h || screenImage.getWidth(this) != w)) {
                newImage1 = createImage(w, h);
                screenImage = newImage1;
            }
            if (allscreenImage != null && (allscreenImage.getHeight(this) != h || allscreenImage.getWidth(this) != w)) {
                newImage2 = createImage(w, h);
                allscreenImage = newImage2;
            }
            super.setBounds(x, y, w, h);
        } catch (Exception ee) {
            ee.printStackTrace();
            System.out.println("Error when resize, No memory!");

        }


    }

    public boolean zoom(int startIndex, int endIndex) {
        //first, determine the new resolution.....
        int numberOfpoints = endIndex - startIndex + 1;
        float fpixelsperpoint = (float) (getXAxisWidth() - 2) / numberOfpoints;
        resolution = (int) (fpixelsperpoint + 0.01d);

        startDisplayIndex = startIndex;
        endDisplayIndex = endIndex;

        //  update the Screen and notify to listener
        this.updateBaseScreen();

        // remove lines because of when using percentage..........
        ChartItem ccchart = getLeftChart();
        if (ccchart != null && ccchart.getChartType() == ChartType.PERCENTAGE) {
            faction.lineRecords.removeAllElements();
            faction.goldenPartitionLine = null;
        }
        this.repaint();
        if (screenActionListener != null) {
            screenActionListener.OnZoomCompleted(this, startDisplayIndex, endDisplayIndex);
        }
        return true;
    }

    //  The function is used to plot the chart
    @Override
    public void update(Graphics g) {
        paint(g);
    }

    @Override
    public synchronized void paint(Graphics gg) {
        if (isUpdatingBaseScreen) {
            return;
        }
        if (screenImage == null) {
            screenImage = createImage(getSize().width, this.getSize().height);
        }
        if (allscreenImage == null) {
            allscreenImage = createImage(getSize().width, this.getSize().height);
        }

        Graphics g = allscreenImage.getGraphics();
        g.drawImage(screenImage, 0, 0, getSize().width, getSize().height, this);
        g.setColor(Color.black);
        for (int i = 0; i < faction.lineRecords.size(); i++) {
            FLine fline = (FLine) faction.lineRecords.elementAt(i);
            if (fline.isFixedLine()) {
                g.setColor(Color.black);
                g.drawLine(fline.getPoint1().x, fline.getPoint1().y, fline.getPoint2().x, fline.getPoint2().y);
            } else {
                g.setColor(Color.black);
                int x1 = getScreenXPositionFromPoint(fline.getIndex1());
                int x2 = getScreenXPositionFromPoint(fline.getIndex2());
                int y1, y2;
                ChartItem cchart = getLeftChart();
                if (cchart != null) {
                    double Max = cchart.getUpperBound();
                    double Min = cchart.getLowerBound();
                    y1 = getScreenYPosition(fline.getValue1(), Max, Min);
                    y2 = getScreenYPosition(fline.getValue2(), Max, Min);
                    g.drawLine(x1, y1, x2, y2);
                }
            }
        }

        // if we are not process GOLDENPAERTITION and we have one need to draw
        if (faction.currentActionType != FAction.Type.GOLDENPARTITION || faction.actionProcessing == false) {
            if (faction.goldenPartitionLine != null) {
                int MaxY = 0, MinY = 0;
                int x1, x2;
                if (faction.goldenPartitionLine.isFixedLine()) {
                    MaxY = Math.max(faction.goldenPartitionLine.getPoint1().y, faction.goldenPartitionLine.getPoint2().y);
                    MinY = Math.min(faction.goldenPartitionLine.getPoint1().y, faction.goldenPartitionLine.getPoint2().y);
                    x1 = faction.goldenPartitionLine.getPoint1().x;
                    x2 = faction.goldenPartitionLine.getPoint2().x;

                } else {
                    //      g.setColor(Color.black);
                    x1 = getScreenXPositionFromPoint(faction.goldenPartitionLine.getIndex1());
                    x2 = getScreenXPositionFromPoint(faction.goldenPartitionLine.getIndex2());
                    int y1, y2;
                    ChartItem cchart = getLeftChart();
                    if (cchart != null) {
                        double Max = cchart.getUpperBound();
                        double Min = cchart.getLowerBound();
                        y1 = getScreenYPosition(faction.goldenPartitionLine.getValue1(), Max, Min);
                        y2 = getScreenYPosition(faction.goldenPartitionLine.getValue2(), Max, Min);
                        MaxY = Math.max(y1, y2);
                        MinY = Math.min(y1, y2);
                    }
                }

                int Y618 = (int) (MaxY - (MaxY - MinY) * 0.618f);
                int Y50 = (int) (MaxY - (MaxY - MinY) * 0.50f);
                int Y382 = (int) (MaxY - (MaxY - MinY) * 0.382);

                g.drawString("0%", x1 + 2, MaxY - 1);
                g.drawString("100%", x1 + 2, MinY - 1);
                g.drawLine(x1, MaxY, leftSpace + getXAxisWidth(), MaxY);
                g.drawLine(x1, MinY, leftSpace + getXAxisWidth(), MinY);
                g.setColor(Color.red);
                drawDotLine(g, x1, Y618, leftSpace + getXAxisWidth(), Y618);
                drawDotLine(g, x1, Y50, leftSpace + getXAxisWidth(), Y50);
                drawDotLine(g, x1, Y382, leftSpace + getXAxisWidth(), Y382);
                g.setColor(Color.darkGray);
                g.drawString("61.8%", x1 + 2, Y618 - 1);
                g.drawString("50%", x1 + 2, Y50 - 1);
                g.drawString("38.2%", x1 + 2, Y382 - 1);

                ChartItem cchart = (ChartItem) getLeftChart();
                if (cchart != null) {
                    double UpperBound = cchart.getUpperBound();
                    double LowerBound = cchart.getLowerBound();
                    float v100 = (float) getYValueFromScreen(MinY, UpperBound, LowerBound);
                    float v618 = (float) getYValueFromScreen(Y618, UpperBound, LowerBound);
                    float v500 = (float) getYValueFromScreen(Y50, UpperBound, LowerBound);
                    float v382 = (float) getYValueFromScreen(Y382, UpperBound, LowerBound);
                    float v000 = (float) getYValueFromScreen(MaxY, UpperBound, LowerBound);
                    g.setColor(cchart.getFirstColor());
                    if (cchart.getChartType() == ChartType.PERCENTAGE) {
                        g.drawString(FormatUtil.formatData2(v100) + "%", x1 + 40, MinY - 1);
                        g.drawString(FormatUtil.formatData2(v618) + "%", x1 + 40, Y618 - 1);
                        g.drawString(FormatUtil.formatData2(v500) + "%", x1 + 40, Y50 - 1);
                        g.drawString(FormatUtil.formatData2(v382) + "%", x1 + 40, Y382 - 1);
                        g.drawString(FormatUtil.formatData2(v000) + "%", x1 + 40, MaxY - 1);
                    } else {
                        g.drawString(FormatUtil.formatData2(v100), x1 + 40, MinY - 1);
                        g.drawString(FormatUtil.formatData2(v618), x1 + 40, Y618 - 1);
                        g.drawString(FormatUtil.formatData2(v500), x1 + 40, Y50 - 1);
                        g.drawString(FormatUtil.formatData2(v382), x1 + 40, Y382 - 1);
                        g.drawString(FormatUtil.formatData2(v000), x1 + 40, MaxY - 1);
                    }
                }
                cchart = (ChartItem) getRightChart();
                if (cchart != null) {
                    double UpperBound = cchart.getUpperBound();
                    double LowerBound = cchart.getLowerBound();
                    float v100 = (float) getYValueFromScreen(MinY, UpperBound, LowerBound);
                    float v618 = (float) getYValueFromScreen(Y618, UpperBound, LowerBound);
                    float v500 = (float) getYValueFromScreen(Y50, UpperBound, LowerBound);
                    float v382 = (float) getYValueFromScreen(Y382, UpperBound, LowerBound);
                    float v000 = (float) getYValueFromScreen(MaxY, UpperBound, LowerBound);
                    g.setColor(cchart.getFirstColor());

                    g.drawString(FormatUtil.formatData2(v100), x1 + 80, MinY - 1);
                    g.drawString(FormatUtil.formatData2(v618), x1 + 80, Y618 - 1);
                    g.drawString(FormatUtil.formatData2(v500), x1 + 80, Y50 - 1);
                    g.drawString(FormatUtil.formatData2(v382), x1 + 80, Y382 - 1);
                    g.drawString(FormatUtil.formatData2(v000), x1 + 80, MaxY - 1);
                }
            }
        }


/// process current action ..................................
        if (this.screenState != LOADING)
            switch (faction.currentActionType) {
////----------------------Response to Zoom Action ------------------------------------------////////
                case ZOOMIN:
                    if (faction.actionProcessing == true) {
                        Point opoint = new Point();
                        Point epoint = new Point();
                        opoint.x = Math.min(faction.pressMpoint.x, faction.currentMpoint.x);
                        opoint.y = Math.min(faction.pressMpoint.y, faction.currentMpoint.y);
                        epoint.x = Math.max(faction.pressMpoint.x, faction.currentMpoint.x);
                        epoint.y = Math.max(faction.pressMpoint.y, faction.currentMpoint.y);

                        if (opoint.y < topSpace) {
                            opoint.y = topSpace + 1;
                        }
                        if (opoint.x < leftSpace) {
                            opoint.x = leftSpace + 1;
                        }
                        if (epoint.y > topSpace + getYAxisWidth()) {
                            epoint.y = topSpace + getYAxisWidth() - 1;
                        }
                        if (epoint.x > leftSpace + getXAxisWidth()) {
                            epoint.x = leftSpace + getXAxisWidth() - 1;
                        }
                        g.drawRect(opoint.x, opoint.y, Math.abs(epoint.x - opoint.x), Math.abs(epoint.y - opoint.y));
                    }
                    break;

////----------------------Response to Watch Action -----------------------------------------///////
                case WATCH:
                    if (faction.actionProcessing == true && isWithinChartRegion(faction.currentMpoint.x, faction.currentMpoint.y)) {
                        ChartItem cchart = (ChartItem) getLeftChart();
                        if (cchart != null) {
                            drawWatchAction();
                        }
//          if (cchart.chartType == ChartType.LINE ||  cchart.chartType == ChartType.CANDLE || cchart.chartType == ChartType.BAR  || cchart.chartType == ChartType.VOLUME || cchart.chartType == ChartType.MACD)
//          {
                        //      drawWatchAction();
//          }
                    }

                    break;
////----------------------Response to INSERTLINE Action ------------------------------------------////////
                case INSERTLINE:
                    //System.out.println(faction.actionProcessing);
                    if (faction.actionProcessing == true) {
                        g.drawLine(faction.currentMpoint.x, faction.currentMpoint.y, faction.pressMpoint.x, faction.pressMpoint.y);
                    }
                    break;
////----------------------Response to Insert Parallel Line Action ------------------------------------------////////
                case INSERTPARALLELLINE:
//          if (faction.actionProcessing == true &&  isWithinChartRegion(faction.currentMpoint.x,faction.currentMpoint.y))
                    if (faction.actionProcessing == true) {
                        FLine fline = (FLine) faction.lineRecords.lastElement();
                        Point rpoint = new Point(0, 0);
                        if (fline.getPoint1().x < fline.getPoint2().x) {
                            rpoint.x = fline.getPoint1().x;
                            rpoint.y = fline.getPoint1().y;
                        } else {
                            rpoint.x = fline.getPoint2().x;
                            rpoint.y = fline.getPoint2().y;
                        }
                        int dx = +faction.currentMpoint.x - rpoint.x;
                        int dy = +faction.currentMpoint.y - rpoint.y;

                        g.drawLine(fline.getPoint1().x + dx, fline.getPoint1().y + dy, fline.getPoint2().x + dx, fline.getPoint2().y + dy);
                    }
                    break;

////----------------------Response to Golden partition Action ------------------------------------------////////
                case GOLDENPARTITION:
                    //if (faction.actionProcessing == true &&  isWithinChartRegion(faction.currentMpoint.x,faction.currentMpoint.y))
                    if (faction.actionProcessing == true) {
                        int MaxY = Math.max(faction.pressMpoint.y, faction.currentMpoint.y);
                        int MinY = Math.min(faction.pressMpoint.y, faction.currentMpoint.y);

                        int Y618 = (int) (MaxY - (MaxY - MinY) * 0.618f);
                        int Y50 = (int) (MaxY - (MaxY - MinY) * 0.50f);
                        int Y382 = (int) (MaxY - (MaxY - MinY) * 0.382);
                        g.drawString("100%", faction.pressMpoint.x + 2, MinY - 1);
                        g.drawLine(faction.pressMpoint.x, MaxY, leftSpace + getXAxisWidth(), MaxY);
                        g.drawString("0%", faction.pressMpoint.x + 2, MaxY - 1);
                        g.drawLine(faction.pressMpoint.x, MinY, leftSpace + getXAxisWidth(), MinY);
                        g.setColor(Color.red);
                        drawDotLine(g, faction.pressMpoint.x, Y618, leftSpace + getXAxisWidth(), Y618);
                        drawDotLine(g, faction.pressMpoint.x, Y50, leftSpace + getXAxisWidth(), Y50);
                        drawDotLine(g, faction.pressMpoint.x, Y382, leftSpace + getXAxisWidth(), Y382);
                        g.setColor(Color.darkGray);
                        g.drawString("38.2%", faction.pressMpoint.x + 2, Y382 - 1);
                        g.drawString("50%", faction.pressMpoint.x + 2, Y50 - 1);
                        g.drawString("61.8%", faction.pressMpoint.x + 2, Y618 - 1);

                        ChartItem cchart = (ChartItem) getLeftChart();
                        if (cchart != null) {
                            double UpperBound = cchart.getUpperBound();
                            double LowerBound = cchart.getLowerBound();
                            float v100 = (float) getYValueFromScreen(MinY, UpperBound, LowerBound);
                            float v618 = (float) getYValueFromScreen(Y618, UpperBound, LowerBound);
                            float v500 = (float) getYValueFromScreen(Y50, UpperBound, LowerBound);
                            float v382 = (float) getYValueFromScreen(Y382, UpperBound, LowerBound);
                            float v000 = (float) getYValueFromScreen(MaxY, UpperBound, LowerBound);
                            g.setColor(cchart.getFirstColor());
                            if (cchart.getChartType() == ChartType.PERCENTAGE) {
                                g.drawString(FormatUtil.formatData2(v100) + "%", faction.pressMpoint.x + 40, MinY - 1);
                                g.drawString(FormatUtil.formatData2(v618) + "%", faction.pressMpoint.x + 40, Y618 - 1);
                                g.drawString(FormatUtil.formatData2(v500) + "%", faction.pressMpoint.x + 40, Y50 - 1);
                                g.drawString(FormatUtil.formatData2(v382) + "%", faction.pressMpoint.x + 40, Y382 - 1);
                                g.drawString(FormatUtil.formatData2(v000) + "%", faction.pressMpoint.x + 40, MaxY - 1);
                            } else {
                                g.drawString(FormatUtil.formatData2(v100), faction.pressMpoint.x + 40, MinY - 1);
                                g.drawString(FormatUtil.formatData2(v618), faction.pressMpoint.x + 40, Y618 - 1);
                                g.drawString(FormatUtil.formatData2(v500), faction.pressMpoint.x + 40, Y50 - 1);
                                g.drawString(FormatUtil.formatData2(v382), faction.pressMpoint.x + 40, Y382 - 1);
                                g.drawString(FormatUtil.formatData2(v000), faction.pressMpoint.x + 40, MaxY - 1);
                            }
                        }

                        cchart = (ChartItem) getRightChart();
                        if (cchart != null) {
                            double UpperBound = cchart.getUpperBound();
                            double LowerBound = cchart.getLowerBound();
                            float v100 = (float) getYValueFromScreen(MinY, UpperBound, LowerBound);
                            float v618 = (float) getYValueFromScreen(Y618, UpperBound, LowerBound);
                            float v500 = (float) getYValueFromScreen(Y50, UpperBound, LowerBound);
                            float v382 = (float) getYValueFromScreen(Y382, UpperBound, LowerBound);
                            float v000 = (float) getYValueFromScreen(MaxY, UpperBound, LowerBound);
                            g.setColor(cchart.getFirstColor());
                            if (cchart.getChartType() == ChartType.PERCENTAGE) {
                                g.drawString(FormatUtil.formatData2(v100) + "%", faction.pressMpoint.x + 80, MinY - 1);
                                g.drawString(FormatUtil.formatData2(v618) + "%", faction.pressMpoint.x + 80, Y618 - 1);
                                g.drawString(FormatUtil.formatData2(v500) + "%", faction.pressMpoint.x + 80, Y50 - 1);
                                g.drawString(FormatUtil.formatData2(v382) + "%", faction.pressMpoint.x + 80, Y382 - 1);
                                g.drawString(FormatUtil.formatData2(v000) + "%", faction.pressMpoint.x + 80, MaxY - 1);
                            } else {
                                g.drawString(FormatUtil.formatData2(v100), faction.pressMpoint.x + 80, MinY - 1);
                                g.drawString(FormatUtil.formatData2(v618), faction.pressMpoint.x + 80, Y618 - 1);
                                g.drawString(FormatUtil.formatData2(v500), faction.pressMpoint.x + 80, Y50 - 1);
                                g.drawString(FormatUtil.formatData2(v382), faction.pressMpoint.x + 80, Y382 - 1);
                                g.drawString(FormatUtil.formatData2(v000), faction.pressMpoint.x + 80, MaxY - 1);
                            }
                        }
                    }
                    break;
////////////////////////////////////////////////////////////////////////////////////////////

            }
//////plot the Axis/////////////////////////////////////////////////////////////////////////
        plotAxis(true);

        drawCompareTable();
        plotCloseButton();
        // draw the buffered image to the screen /////////////////////////////////////////////////


        if (this.screenState == LOADING) {
            plotLoading();
        }
        gg.drawImage(allscreenImage, 0, 0, getSize().width, getSize().height, this);
    }

    private void plotCloseButton() {
        Graphics g = allscreenImage.getGraphics();
        g.setColor(FConfig.ToolBarColor);
        g.fillRoundRect(getWidth() - 17, 0, 15, 15, 3, 3);
        g.setColor(Color.black);
        g.drawRoundRect(getWidth() - 17, 0, 15, 15, 3, 3);
        g.fillRect(getWidth() - 14, 7, 10, 2);

    }

    private void plotWatchTable(ChartItem cchart, int x, int y) {
        Graphics g = allscreenImage.getGraphics();
        if (cchart != null) {
            int index = getPointIndexFromScreen(faction.currentMpoint.x);
            FPoint fpoint = (FPoint) cchart.getChartData().getData().elementAt(index);
            FTAPoint fTApoint = null;
            if (cchart.getChartData().getTAdata().size() > index) {
                fTApoint = (FTAPoint) cchart.getChartData().getTAdata().elementAt(index);
            }


            String sDate = "";


            if (cchart.getChartData().dataType == ChartData.DAILY) {
                SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yy");
                sDate = sdf.format(fpoint.getDate());
                sDate = lbArray[15][language] + ": " + sDate;
            } else if (cchart.getChartData().dataType == ChartData.WEEKLY || cchart.getChartData().dataType == ChartData.MONTHLY) {
                sDate = fpoint.getDay() + "-" + fpoint.getMonth() + "-" + fpoint.getYear() + " to " + fpoint.getlDay() + "-" + fpoint.getlMonth() + "-" + fpoint.getlYear();
                sDate = lbArray[15][language] + ": " + sDate;
            } else if (cchart.getChartData().dataType == ChartData.INTRADAY) {
                sDate = FormatUtil.formatTime(fpoint.getHour(), fpoint.getMinute());
                sDate = lbArray[16][language] + ": " + sDate;
            }

            switch (cchart.getChartType()) {
                case PERCENTAGE:
                    break;

                case MACD:
                    g.setColor(new Color(240, 240, 240));
                    g.fill3DRect(x, y, 170, 50, true);
                    g.setColor(cchart.getFirstColor());
                    g.drawString("MACD1: " + FormatUtil.formatData3(fTApoint.getMACD1()), x + 1, y + 12);
                    g.drawString("MACD2: " + FormatUtil.formatData3(fTApoint.getMACD2()), x + 1, y + 24);
                    g.drawString("Diff: " + FormatUtil.formatData3(fTApoint.getMACDdiff()), x + 1, y + 36);
                    g.drawString(sDate, x + 1, y + 48);
                    break;

                case RSI:
                    g.setColor(new Color(240, 240, 240));
                    g.fill3DRect(x, y, 170, 28, true);
                    g.setColor(cchart.getFirstColor());
                    g.drawString("RSI: " + FormatUtil.formatData3(fTApoint.getRSI()), x + 1, y + 12);
                    g.drawString(sDate, x + 1, y + 24);
                    break;

                case STC:
                    g.setColor(new Color(240, 240, 240));
                    g.fill3DRect(x, y, 170, 38, true);
                    g.setColor(cchart.getFirstColor());
                    g.drawString("%K: " + FormatUtil.formatData3(fTApoint.getK()), x + 1, y + 12);
                    g.drawString("%D: " + FormatUtil.formatData3(fTApoint.getD()), x + 1, y + 24);
                    g.drawString(sDate, x + 1, y + 36);
                    break;

                case OBV:
                    g.setColor(new Color(240, 240, 240));
                    g.fill3DRect(x, y, 170, 28, true);
                    g.setColor(cchart.getFirstColor());
                    g.drawString("OBV: " + FormatUtil.formatData3(fTApoint.getOBV()), x + 1, y + 12);
                    g.drawString(sDate, x + 1, y + 24);
                    break;

                case WILLIAM_R:
                    g.setColor(new Color(240, 240, 240));
                    g.fill3DRect(x, y, 170, 28, true);
                    g.setColor(cchart.getFirstColor());
                    g.drawString("William %R: " + FormatUtil.formatData3(fTApoint.getR()), x + 1, y + 12);
                    g.drawString(sDate, x + 1, y + 24);
                    break;

                case VOLUME:
                    g.setColor(new Color(240, 240, 240));
                    g.fill3DRect(x, y, 170, 28, true);
                    g.setColor(cchart.getFirstColor());
                    g.drawString(lbArray[9][language] + ": " + fpoint.getVolume(), x + 1, y + 12);
                    g.drawString(sDate, x + 1, y + 24);
                    break;


                case CANDLE:
                case LINE:
                case BAR:
                    g.setColor(new Color(240, 240, 240));
                    g.fill3DRect(x, y, 170, 86, true);
                    g.setColor(Color.blue);
                    //.drawRect(x, y, 160, 86);

                    //g.fill3DRect(x, y, 175, 50, true);
                    g.setColor(cchart.getFirstColor());
                    g.drawString(lbArray[5][language] + ": " + FormatUtil.formatData3(fpoint.getOpen()), x + 4, y + 12);
                    g.drawString(lbArray[6][language] + ": " + FormatUtil.formatData3(fpoint.getClose()), x + 4, y + 24);
                    g.drawString(lbArray[7][language] + ": " + FormatUtil.formatData3(fpoint.getMaximum()), x + 4, y + 36);
                    g.drawString(lbArray[8][language] + ": " + FormatUtil.formatData3(fpoint.getMinimum()), x + 4, y + 48);
                    g.drawString(lbArray[9][language] + ": " + fpoint.getVolume(), x + 4, y + 60);
                    g.drawString(sDate, x + 2, y + 72);
                    break;
            }

        }

    }

    private void drawWatchAction() {

        int indexPoint = getPointIndexFromScreen(faction.currentMpoint.x);
        if (indexPoint < startDisplayIndex || indexPoint > endDisplayIndex) {
            return;
        }

        ChartItem leftChart = getLeftChart();
        ChartItem rightChart = getRightChart();

        // draw the value where the mouse pointed.
        double pointedValue = this.getYValueFromScreen(faction.currentMpoint.y, leftChart.getUpperBound(), leftChart.getLowerBound());
        Graphics g = allscreenImage.getGraphics();
        g.setColor(FConfig.WatchLabelColor);
        g.setFont(new Font("default", 1, 12));
        if (leftChart.getChartType() == ChartType.VOLUME) {
            g.drawString(String.valueOf((int) (pointedValue)), faction.currentMpoint.x, faction.currentMpoint.y);
        } else if (leftChart.getChartType() == ChartType.OBV) {
            //g.drawString(FormatUtil.formatInteger(pointedValue),faction.currentMpoint.x,faction.currentMpoint.y);
        } else if (leftChart.getChartType() == ChartType.PERCENTAGE) {
            g.drawString(FormatUtil.formatData3(pointedValue) + "%", faction.currentMpoint.x, faction.currentMpoint.y);
        } else {
            g.drawString(FormatUtil.formatData3(pointedValue), faction.currentMpoint.x, faction.currentMpoint.y);
        }

        // draw the watch table
        if (leftChart != null && leftChart.isVisable()) {
            if (faction.currentMpoint.x > getSize().width / 2) {
                plotWatchTable(leftChart, leftSpace + 5, topSpace);
            } else {
                plotWatchTable(leftChart, getSize().width - rightSpace - 170, topSpace);
            }
        }
        if (rightChart != null && rightChart.isVisable()) {
            if (faction.currentMpoint.x > getSize().width / 2) {
                plotWatchTable(rightChart, leftSpace + 5, topSpace + 60);
            } else {
                plotWatchTable(rightChart, getSize().width - rightSpace - 180, topSpace + 60);
            }
        }
    }

    // Plot function:::
    public synchronized void updateBaseScreen() {
        if (this.screenImage == null) {
            return;
        }
        if (this.allscreenImage == null) {
            return;
        }
        //System.out.println("Entering updateBaseScreen");
        isUpdatingBaseScreen = true;

        //System.out.println("UpdateBaseScreen: " + startDisplayIndex  + " : " +endDisplayIndex);
        clearScreen();

        //System.out.println("updating % data");
        //update the percentage data
        for (int i = 0; i < chartObjects.size(); i++) {
            ChartItem currentChart = (ChartItem) chartObjects.elementAt(i);
            if (currentChart.isVisable() && currentChart.getChartType() == ChartType.PERCENTAGE) {
                currentChart.getChartData().calculatePercentage(startDisplayIndex);
            }

            if (currentChart.isVisable() && currentChart.getChartType() == ChartType.OBV) {
                currentChart.getChartData().calculateOBV(startDisplayIndex, endDisplayIndex);
            }

        }
        //System.out.println("end updating");

        FBound fb = getAxisBound(AxisType.LEFTAXIS);
        getAxisBound(AxisType.RIGHTAXIS);
        getAxisBound(AxisType.NONE);

        // plot the grid line
        plotAxis(false);
        //  System.out.println("end plotAxis");
        for (int i = 0; i < chartObjects.size(); i++) {
            ChartItem currentChart = (ChartItem) chartObjects.elementAt(i);
            if (currentChart.isVisable()) {
                plotChart(currentChart);
            }
        }
        isUpdatingBaseScreen = false;
    }

    /**
     * plot a loading messaage box in the font of the screen...
     */
    private void plotLoading() {
        Graphics gg = allscreenImage.getGraphics();
        int currentIndex = (loadingBarIndex++) % 4;

        gg.drawImage(loadingBarImage[currentIndex], getSize().width / 2 - loadingBarImage[currentIndex].getWidth(null) / 2, getSize().height / 2, this);
    }

    private void plotPercentageChart(ChartItem currentChart) {
        double Max = currentChart.getChartBound().getUpperPercentageBound();
        double Min = currentChart.getChartBound().getLowerPercentageBound();


        Graphics g = screenImage.getGraphics();
        g.setColor(currentChart.getFirstColor());

        FPoint fpoint1 = null;
        FPoint fpoint2 = null;
        int lastValidPoint = 0;
        for (int i = startDisplayIndex + 1; i <= endDisplayIndex; i++) {
            //System.out.println("pp: " + i);
            fpoint1 = (FPoint) currentChart.getChartData().getData().elementAt(i);
            //System.out.println("fpoint1: " + fpoint1.isValid());
            fpoint2 = (FPoint) currentChart.getChartData().getData().elementAt(i - 1);
            if (fpoint2.isValid()) {
                lastValidPoint = i - 1;
            } else {
                fpoint2 = (FPoint) currentChart.getChartData().getData().elementAt(lastValidPoint);
            }

            if (fpoint1 != null && fpoint2 != null && fpoint1.isValid() && fpoint2.isValid()) {
                try {
                    int x1 = this.getScreenXPositionFromPoint(i);
                    int x2 = getScreenXPositionFromPoint(lastValidPoint);
                    int y1 = this.getScreenYPosition(fpoint1.getPercent(), Max, Min);
                    int y2 = getScreenYPosition(fpoint2.getPercent(), Max, Min);
                    g.drawLine(x1, y1, x2, y2);
                    lastValidPoint = i;
                } catch (Exception ee) {
                    ee.printStackTrace();
                }
            }

        }

    }

    private void plotVolumeChart(ChartItem currentChart) {

        double Max = currentChart.getChartBound().getUpperVolumeBound();// .UpperStockBound;
        double Min = currentChart.getChartBound().getLowerVolumeBound();// .LowerStockBound;
        Max = Math.max(Max, 1);

        Graphics g = screenImage.getGraphics();
        g.setColor(currentChart.getFirstColor());

        int hWidth = 1;
        if (resolution >= 3 && resolution <= 4) {
            hWidth = 1;
        } else if (resolution >= 5 && resolution <= 6) {
            hWidth = 3;
        } else if (resolution >= 7 && resolution <= 8) {
            hWidth = 5;
        } else if (resolution >= 9 && resolution <= 10) {
            hWidth = 7;
        } else if (resolution >= 11) {
            hWidth = resolution / 2;
        }

        for (int i = startDisplayIndex; i <= endDisplayIndex; i++) {
            FPoint fpoint = (FPoint) currentChart.getChartData().getData().elementAt(i);
            g.fill3DRect(getScreenXPositionFromPoint(i), getScreenYPosition(fpoint.getVolume(), Max, Min), hWidth, getScreenYPosition(0, Max, Min) - getScreenYPosition(fpoint.getVolume(), Max, Min), true);
        }
    }

    // plot the CandleChart given the chart
    private void plotCandleChart(ChartItem currentChart) {
        double Max = currentChart.getChartBound().getUpperStockBound();
        double Min = currentChart.getChartBound().getLowerStockBound();

        Graphics g = screenImage.getGraphics();
        g.setColor(currentChart.getFirstColor());


        int CandleWidth = 1;
        if (resolution >= 3 && resolution <= 4) {
            CandleWidth = 1;
        } else if (resolution >= 5 && resolution <= 6) {
            CandleWidth = 2;
        } else if (resolution >= 7 && resolution <= 8) {
            CandleWidth = 3;
        } else if (resolution >= 9 && resolution <= 10) {
            CandleWidth = 4;
        } else if (resolution >= 11) {
            CandleWidth = resolution / 2 - 2;
        }

        for (int i = startDisplayIndex; i <= endDisplayIndex; i++) {
            FPoint fpoint = (FPoint) currentChart.getChartData().getData().elementAt(i);
            if (!fpoint.isIntraDayMarked()) {
                if (fpoint.isValid()) {
                    if (fpoint.getOpen() > fpoint.getClose()) {
                        g.setColor(FConfig.CandleColorDown);
                        g.drawLine(this.getScreenXPositionFromPoint(i), this.getScreenYPosition(fpoint.getMaximum(), Max, Min), getScreenXPositionFromPoint(i), getScreenYPosition(fpoint.getOpen(), Max, Min));
                        g.fillRoundRect(this.getScreenXPositionFromPoint(i) - CandleWidth, this.getScreenYPosition(fpoint.getOpen(), Max, Min), CandleWidth * 2 + 1, getScreenYPosition(fpoint.getClose(), Max, Min) - getScreenYPosition(fpoint.getOpen(), Max, Min), 1, 1);
                        g.drawLine(this.getScreenXPositionFromPoint(i), this.getScreenYPosition(fpoint.getClose(), Max, Min), getScreenXPositionFromPoint(i), getScreenYPosition(fpoint.getMinimum(), Max, Min));
                        if (fpoint.getClose() == fpoint.getOpen()) {
                            g.drawLine(this.getScreenXPositionFromPoint(i) - CandleWidth, this.getScreenYPosition(fpoint.getOpen(), Max, Min), this.getScreenXPositionFromPoint(i) + CandleWidth, getScreenYPosition(fpoint.getClose(), Max, Min));
                        }
                    } else {
                        g.setColor(FConfig.CandleColorUp);
                        g.drawLine(this.getScreenXPositionFromPoint(i), this.getScreenYPosition(fpoint.getMaximum(), Max, Min), getScreenXPositionFromPoint(i), getScreenYPosition(fpoint.getClose(), Max, Min));
                        g.drawRoundRect(this.getScreenXPositionFromPoint(i) - CandleWidth, this.getScreenYPosition(fpoint.getClose(), Max, Min), CandleWidth * 2, getScreenYPosition(fpoint.getOpen(), Max, Min) - getScreenYPosition(fpoint.getClose(), Max, Min), 1, 1);
                        g.drawLine(this.getScreenXPositionFromPoint(i), this.getScreenYPosition(fpoint.getOpen(), Max, Min), getScreenXPositionFromPoint(i), getScreenYPosition(fpoint.getMinimum(), Max, Min));
                        if (fpoint.getClose() == fpoint.getOpen()) {
                            g.drawLine(this.getScreenXPositionFromPoint(i) - CandleWidth, this.getScreenYPosition(fpoint.getOpen(), Max, Min), this.getScreenXPositionFromPoint(i) + CandleWidth, getScreenYPosition(fpoint.getClose(), Max, Min));
                        }
                    }
                }
            }
        }
    }

    private void plotBarChart(ChartItem currentChart) {

        double Max = currentChart.getChartBound().getUpperStockBound();
        double Min = currentChart.getChartBound().getLowerStockBound();

        Graphics g = screenImage.getGraphics();
        g.setColor(FConfig.BarColor);


        int BarWidth = 1;
        if (resolution >= 3 && resolution <= 4) {
            BarWidth = 1;
        } else if (resolution >= 5 && resolution <= 6) {
            BarWidth = 2;
        } else if (resolution >= 7 && resolution <= 8) {
            BarWidth = 3;
        } else if (resolution >= 9 && resolution <= 10) {
            BarWidth = 4;
        } else if (resolution >= 11) {
            BarWidth = 5;
        }


        for (int i = startDisplayIndex; i <= endDisplayIndex; i++) {
            FPoint fpoint = (FPoint) currentChart.getChartData().getData().elementAt(i);
            if (!fpoint.isIntraDayMarked()) {
                if (fpoint.isValid()) {
                    g.drawLine(this.getScreenXPositionFromPoint(i), this.getScreenYPosition(fpoint.getMaximum(), Max, Min), getScreenXPositionFromPoint(i), getScreenYPosition(fpoint.getMinimum(), Max, Min));
                    g.drawLine(this.getScreenXPositionFromPoint(i) - BarWidth, this.getScreenYPosition(fpoint.getOpen(), Max, Min), getScreenXPositionFromPoint(i), getScreenYPosition(fpoint.getOpen(), Max, Min));
                    g.drawLine(this.getScreenXPositionFromPoint(i), this.getScreenYPosition(fpoint.getClose(), Max, Min), getScreenXPositionFromPoint(i) + BarWidth, getScreenYPosition(fpoint.getClose(), Max, Min));
                }
            }

        }
    }

    private void plotBollingerBand(ChartItem currentChart) {

        // System.out.println("Line : " + startDisplayIndex + " : " + endDisplayIndex);
        double Max = currentChart.getChartBound().getUpperStockBound();
        double Min = currentChart.getChartBound().getLowerStockBound();

        int N = currentChart.getChartData().getfTAconfig().bbN;

        Graphics g = screenImage.getGraphics();
        g.setColor(currentChart.getFirstColor());

        FTAPoint fTApoint1 = null;
        FTAPoint fTApoint2 = null;

        for (int i = startDisplayIndex + 1; i <= endDisplayIndex; i++) {
            //System.out.println("pp: " + i);
            fTApoint1 = (FTAPoint) currentChart.getChartData().getTAdata().elementAt(i);
            fTApoint2 = (FTAPoint) currentChart.getChartData().getTAdata().elementAt(i - 1);

            if (fTApoint1 != null && fTApoint2 != null && fTApoint1.isValid() && fTApoint2.isValid()) {
                try {
                    //System.out.println("pp: " + i +" ---------------------");
                    int y1, y2;
                    int x1 = this.getScreenXPositionFromPoint(i);
                    int x2 = this.getScreenXPositionFromPoint(i - 1);

                    //plot UB line
                    g.setColor(FConfig.BollingerBandColor);
                    y1 = this.getScreenYPosition(fTApoint1.getUB(), Max, Min);
                    y2 = getScreenYPosition(fTApoint2.getUB(), Max, Min);
                    //   System.out.println(x1 + " : " + y1 + " : " + x2 + " : " + y2 + " plot");
                    if (i > N) {
                        g.drawLine(x1, y1, x2, y2);
                    }

                    //plot LB  line
                    g.setColor(FConfig.BollingerBandColor);
                    y1 = this.getScreenYPosition(fTApoint1.getLB(), Max, Min);
                    y2 = getScreenYPosition(fTApoint2.getLB(), Max, Min);
                    if (i > N) {
                        g.drawLine(x1, y1, x2, y2);
                    }

                } catch (Exception ee) {
                }
            }

        }

    }

    private void plotOBV(ChartItem currentChart) {

        double Max = currentChart.getChartBound().getUpperOBVBound();
        double Min = currentChart.getChartBound().getLowerOBVBound();

        Graphics g = screenImage.getGraphics();
        g.setColor(currentChart.getFirstColor());

        FTAPoint fTApoint1 = null;
        FTAPoint fTApoint2 = null;
        int lastValidPoint = 0;

        g.setColor(FConfig.OBVColor);
        for (int i = startDisplayIndex + 1; i <= endDisplayIndex; i++) {
            //System.out.println("pp: " + i);
            fTApoint1 = (FTAPoint) currentChart.getChartData().getTAdata().elementAt(i);
            //System.out.println("fpoint1: " + fpoint1.isValid());
            fTApoint2 = (FTAPoint) currentChart.getChartData().getTAdata().elementAt(i - 1);

            if (fTApoint1 != null && fTApoint2 != null && fTApoint1.isValid() && fTApoint2.isValid()) {
                try {

                    int x1 = this.getScreenXPositionFromPoint(i);
                    int x2 = this.getScreenXPositionFromPoint(i - 1);

                    //plot OBV
                    int y1 = this.getScreenYPosition(fTApoint1.getOBV(), Max, Min);
                    int y2 = getScreenYPosition(fTApoint2.getOBV(), Max, Min);
                    //System.out.println("x1:" + x1 + " y1: "+ y1 + " x2: " + x2 + " y2: " + y2);
                    g.drawLine(x1, y1, x2, y2);

                } catch (Exception ee) {
                }
            }
        }
    }

    private void plotMACD(ChartItem currentChart) {
        // System.out.println("Line : " + startDisplayIndex + " : " + endDisplayIndex);
        double Max = currentChart.getChartBound().getUpperMACDBound();
        double Min = currentChart.getChartBound().getLowerMACDBound();

        Graphics g = screenImage.getGraphics();
        g.setColor(currentChart.getFirstColor());

        FTAPoint fTApoint1 = null;
        FTAPoint fTApoint2 = null;
        int lastValidPoint = 0;
        for (int i = startDisplayIndex + 1; i <= endDisplayIndex; i++) {
            //System.out.println("pp: " + i);
            fTApoint1 = (FTAPoint) currentChart.getChartData().getTAdata().elementAt(i);
            //System.out.println("fpoint1: " + fpoint1.isValid());
            fTApoint2 = (FTAPoint) currentChart.getChartData().getTAdata().elementAt(i - 1);

            if (fTApoint1 != null && fTApoint2 != null && fTApoint1.isValid() && fTApoint2.isValid()) {
                try {

                    int x1 = this.getScreenXPositionFromPoint(i);
                    int x2 = this.getScreenXPositionFromPoint(i - 1);

                    //plot MACD1
                    int y1 = this.getScreenYPosition(fTApoint1.getMACD1(), Max, Min);
                    int y2 = getScreenYPosition(fTApoint2.getMACD1(), Max, Min);

                    g.setColor(FConfig.MACDColor1);
                    g.drawLine(x1, y1, x2, y2);

                    //plot MACD2
                    y1 = this.getScreenYPosition(fTApoint1.getMACD2(), Max, Min);
                    y2 = getScreenYPosition(fTApoint2.getMACD2(), Max, Min);

                    g.setColor(FConfig.MACDColor2);
                    g.drawLine(x1, y1, x2, y2);

                    y1 = this.getScreenYPosition(fTApoint1.getMACDdiff(), Max, Min);
                    y2 = this.getScreenYPosition(0, Max, Min);
                    g.setColor(FConfig.MACDColor3);
                    g.drawLine(x1, y1, x1, y2);
                } catch (Exception ee) {
                    ee.printStackTrace();
                }
            }
        }
    }

    private void plotWILLIAM_R(ChartItem currentChart) {

        double Max = currentChart.getChartBound().getUpperWilliamRBound();
        double Min = currentChart.getChartBound().getLowerWilliamRBound();

        Graphics g = screenImage.getGraphics();
        g.setColor(currentChart.getFirstColor());

        FTAPoint fTApoint1 = null;
        FTAPoint fTApoint2 = null;
        int lastValidPoint = 0;
        for (int i = startDisplayIndex + 1; i <= endDisplayIndex; i++) {
            fTApoint1 = (FTAPoint) currentChart.getChartData().getTAdata().elementAt(i);
            fTApoint2 = (FTAPoint) currentChart.getChartData().getTAdata().elementAt(i - 1);

            if (fTApoint1 != null && fTApoint2 != null && fTApoint1.isValid() && fTApoint2.isValid()) {
                try {

                    int x1 = this.getScreenXPositionFromPoint(i);
                    int x2 = this.getScreenXPositionFromPoint(i - 1);

                    //plot %R
                    int y1 = this.getScreenYPosition(fTApoint1.getR(), Max, Min);
                    int y2 = getScreenYPosition(fTApoint2.getR(), Max, Min);
                    g.setColor(FConfig.WilliamRColor);
                    g.drawLine(x1, y1, x2, y2);
                } catch (Exception ee) {
                    ee.printStackTrace();
                }
            }
        }

        g.setColor(Color.red);
        int x1 = this.getScreenXPositionFromPoint(startDisplayIndex);
        int x2 = getScreenXPositionFromPoint(endDisplayIndex);
        int y1 = this.getScreenYPosition(50, Max, Min);
        //this.drawDotLine(g,x1,y1,x2,y1);
        g.drawLine(x1, y1, x2, y1);


    }

    private void plotSTC(ChartItem currentChart) {
        double Max = currentChart.getChartBound().getUpperSTCBound();
        double Min = currentChart.getChartBound().getLowerSTCBound();

        Graphics g = screenImage.getGraphics();
        g.setColor(currentChart.getFirstColor());

        FTAPoint fTApoint1 = null;
        FTAPoint fTApoint2 = null;
        int lastValidPoint = 0;
        for (int i = startDisplayIndex + 1; i <= endDisplayIndex; i++) {
            fTApoint1 = (FTAPoint) currentChart.getChartData().getTAdata().elementAt(i);
            fTApoint2 = (FTAPoint) currentChart.getChartData().getTAdata().elementAt(i - 1);
            if (fTApoint1 != null && fTApoint2 != null && fTApoint1.isValid() && fTApoint2.isValid()) {
                try {
                    int x1 = this.getScreenXPositionFromPoint(i);
                    int x2 = this.getScreenXPositionFromPoint(i - 1);

                    //plot %K
                    int y1 = this.getScreenYPosition(fTApoint1.getK(), Max, Min);
                    int y2 = getScreenYPosition(fTApoint2.getK(), Max, Min);
                    g.setColor(FConfig.STCColorK);
                    g.drawLine(x1, y1, x2, y2);

                    //plot %D
                    y1 = this.getScreenYPosition(fTApoint1.getD(), Max, Min);
                    y2 = getScreenYPosition(fTApoint2.getD(), Max, Min);
                    g.setColor(FConfig.STCColorD);
                    g.drawLine(x1, y1, x2, y2);
                } catch (Exception ee) {
                    ee.printStackTrace();
                }
            }
        }

        g.setColor(Color.red);
        int x1 = this.getScreenXPositionFromPoint(startDisplayIndex);
        int x2 = getScreenXPositionFromPoint(endDisplayIndex);
        int y1 = this.getScreenYPosition(80, Max, Min);
        int y2 = getScreenYPosition(20, Max, Min);
        g.drawLine(x1, y1, x2, y1);
        g.drawLine(x1, y2, x2, y2);

    }

    private void plotRSI(ChartItem currentChart) {
        // System.out.println("Line : " + startDisplayIndex + " : " + endDisplayIndex);
        double Max = currentChart.getChartBound().getUpperRSIBound();
        double Min = currentChart.getChartBound().getLowerRSIBound();

        Graphics g = screenImage.getGraphics();
        g.setColor(currentChart.getFirstColor());

        FTAPoint fTApoint1 = null;
        FTAPoint fTApoint2 = null;
        int lastValidPoint = 0;
        for (int i = startDisplayIndex + 1; i <= endDisplayIndex; i++) {
            fTApoint1 = (FTAPoint) currentChart.getChartData().getTAdata().elementAt(i);
            fTApoint2 = (FTAPoint) currentChart.getChartData().getTAdata().elementAt(i - 1);
            if (fTApoint2.isValid()) {
                lastValidPoint = i - 1;
            } else {
                fTApoint2 = (FTAPoint) currentChart.getChartData().getTAdata().elementAt(lastValidPoint);
            }
            // plot the RSI line
            if (fTApoint1 != null && fTApoint2 != null && fTApoint1.isValid() && fTApoint2.isValid()) {
                try {
                    int x1 = this.getScreenXPositionFromPoint(i);
                    int x2 = getScreenXPositionFromPoint(lastValidPoint);
                    int y1 = this.getScreenYPosition(fTApoint1.getRSI(), Max, Min);
                    int y2 = getScreenYPosition(fTApoint2.getRSI(), Max, Min);
                    g.setColor(FConfig.RSIColor);
                    g.drawLine(x1, y1, x2, y2);
                    lastValidPoint = i;
                } catch (Exception ee) {
                }
            }
        }
        // plot the parellel line.
        g.setColor(Color.red);
        int x1 = this.getScreenXPositionFromPoint(startDisplayIndex);
        int x2 = getScreenXPositionFromPoint(endDisplayIndex);
        int y1 = this.getScreenYPosition(70, Max, Min);
        int y2 = getScreenYPosition(30, Max, Min);
        g.drawLine(x1, y1, x2, y1);
        g.drawLine(x1, y2, x2, y2);

    }

    private void plotMovingAverage(ChartItem currentChart) {

        double Max = currentChart.getChartBound().getUpperStockBound();
        double Min = currentChart.getChartBound().getLowerStockBound();
        int N1, N2, N3;

        if (currentChart.getChartType() == ChartType.SIMPLE_MOVING_AVERAGE) {
            N1 = currentChart.getChartData().getfTAconfig().SMAN1;
            N2 = currentChart.getChartData().getfTAconfig().SMAN2;
            N3 = currentChart.getChartData().getfTAconfig().SMAN3;

        } else if (currentChart.getChartType() == ChartType.WEIGHTED_MOVING_AVERAGE) {
            N1 = currentChart.getChartData().getfTAconfig().WMAN1;
            N2 = currentChart.getChartData().getfTAconfig().WMAN2;
            N3 = currentChart.getChartData().getfTAconfig().WMAN3;
        } else if (currentChart.getChartType() == ChartType.EXPONENTIAL_MOVING_AVERAGE) {
            N1 = 0;
            N2 = 0;
            N3 = 0;
        } else {
            N1 = 0;
            N2 = 0;
            N3 = 0;
        }
        Graphics g = screenImage.getGraphics();
        g.setColor(currentChart.getFirstColor());

        FTAPoint fTApoint1 = null;
        FTAPoint fTApoint2 = null;

        for (int i = startDisplayIndex + 1; i <= endDisplayIndex; i++) {
            fTApoint1 = (FTAPoint) currentChart.getChartData().getTAdata().elementAt(i);
            fTApoint2 = (FTAPoint) currentChart.getChartData().getTAdata().elementAt(i - 1);


            if (fTApoint1 != null && fTApoint2 != null && fTApoint1.isValid() && fTApoint2.isValid()) {
                try {
                    int y1, y2;
                    int x1 = this.getScreenXPositionFromPoint(i);
                    int x2 = this.getScreenXPositionFromPoint(i - 1);

                    //plot MA1 line
                    g.setColor(FConfig.MAColor1);
                    y1 = this.getScreenYPosition(fTApoint1.getMA1(), Max, Min);
                    y2 = getScreenYPosition(fTApoint2.getMA1(), Max, Min);
                    if (i > N1) {
                        g.drawLine(x1, y1, x2, y2);
                    }

                    //plot MA2 line
                    g.setColor(FConfig.MAColor2);
                    y1 = this.getScreenYPosition(fTApoint1.getMA2(), Max, Min);
                    y2 = getScreenYPosition(fTApoint2.getMA2(), Max, Min);
                    if (i > N2) {
                        g.drawLine(x1, y1, x2, y2);
                    }

                    //plot MA3 line
                    g.setColor(FConfig.MAColor3);
                    y1 = this.getScreenYPosition(fTApoint1.getMA3(), Max, Min);
                    y2 = getScreenYPosition(fTApoint2.getMA3(), Max, Min);
                    if (i > N3) {
                        g.drawLine(x1, y1, x2, y2);
                    }

                } catch (Exception ee) {
                    ee.printStackTrace();
                }
            }

        }

    }

    private void plotLineChart(ChartItem currentChart) {

        double Max = currentChart.getChartBound().getUpperStockBound();
        double Min = currentChart.getChartBound().getLowerStockBound();

        Graphics g = screenImage.getGraphics();
        g.setColor(FConfig.LineColor);

        FPoint fpoint1 = null;
        FPoint fpoint2 = null;
        int lastValidPoint = 0;
        for (int i = startDisplayIndex + 1; i <= endDisplayIndex; i++) {
            fpoint1 = (FPoint) currentChart.getChartData().getData().elementAt(i);
            fpoint2 = (FPoint) currentChart.getChartData().getData().elementAt(i - 1);
            if (fpoint2.isValid()) {
                lastValidPoint = i - 1;
            } else {
                fpoint2 = (FPoint) currentChart.getChartData().getData().elementAt(lastValidPoint);
            }
            if (fpoint1 != null && fpoint2 != null && fpoint1.isValid() && fpoint2.isValid()) {
                try {
                    int x1 = this.getScreenXPositionFromPoint(i);
                    int x2 = getScreenXPositionFromPoint(lastValidPoint);
                    int y1 = this.getScreenYPosition(fpoint1.getClose(), Max, Min);
                    int y2 = getScreenYPosition(fpoint2.getClose(), Max, Min);
                    g.drawLine(x1, y1, x2, y2);
                    lastValidPoint = i;
                } catch (Exception ee) {
                    ee.printStackTrace();
                }
            }

        }

    }

    // only parallel or vertical line can be plotted.
    private void drawDotLine(Graphics g, int x1, int y1, int x2, int y2) {

        if (x1 == x2) {
            /* Y axis */
            int p1, p2;
            p1 = Math.min(y1, y2);
            p2 = Math.max(y1, y2);
            for (int i = p1; i <= p2 - 2; i = i + 4) {
                g.drawLine(x1, i, x1, i + 1);
            }
        } else if (y1 == y2) {
            /* X axis */
            int p1, p2;
            p1 = Math.min(x1, x2);
            p2 = Math.max(x1, x2);
            for (int i = p1; i <= p2 - 2; i = i + 4) {
                g.drawLine(i, y1, i + 1, y1);
            }
        }

    }

    private void plotXAxis(ChartItem currentChart, boolean isLabel) {
        if (gridColor == null) {
            gridColor = new Color(200, 200, 200);
        }
        int dpoint = endDisplayIndex;// this.getMaxNumberOfDisplayPointInCurrentResolution()+startDisplayIndex;
        Graphics g = allscreenImage.getGraphics();
        Graphics gg = screenImage.getGraphics();

        // when the resolution is small, set the font size to some.
        if (resolution <= 4) {
            g.setFont(new Font("default", 0, 10));
            gg.setFont(new Font("default", 0, 10));
        }

        if (currentChart.isShowXaxis()) {
            if (currentChart.getChartData().dataType == ChartData.DAILY) {
                for (int i = startDisplayIndex; i < dpoint; i++) {
                    int j = Math.max(i - 1, startDisplayIndex);
                    FPoint fpoint = (FPoint) currentChart.getChartData().getData().elementAt(i);
                    FPoint fpoint2 = (FPoint) currentChart.getChartData().getData().elementAt(j);
                    if (fpoint.getMonth() != fpoint2.getMonth()) {
                        if (!isLabel) {
                            gg.setColor(gridColor);
                            drawDotLine(gg, getScreenXPositionFromPoint(i), topSpace, getScreenXPositionFromPoint(i), topSpace + getYAxisWidth());
                            continue;
                        }

                        g.setColor(Color.black);
                        g.drawLine(getScreenXPositionFromPoint(i), topSpace + getYAxisWidth(), getScreenXPositionFromPoint(i), topSpace + getYAxisWidth() + 2);
//                        String dateMY =  fpoint.Month + "-" + fpoint.Year;
                        String dateMY = FormatUtil.formatDate(fpoint.getDate());
                        if (bottomSpace > 8) {
                            g.drawString(dateMY, getScreenXPositionFromPoint(i) - 10, topSpace + getYAxisWidth() + 12);
                        }
                    }
                }
            } else if (currentChart.getChartData().dataType == ChartData.WEEKLY) {
                for (int i = startDisplayIndex; i < dpoint; i++) {
                    int j = Math.max(i - 1, startDisplayIndex);
                    FPoint fpoint = (FPoint) currentChart.getChartData().getData().elementAt(i);
                    FPoint fpoint2 = (FPoint) currentChart.getChartData().getData().elementAt(j);
                    if (fpoint.getMonth() != fpoint2.getMonth() && fpoint.getMonth() % 3 == 1) {
                        if (!isLabel) {
                            gg.setColor(gridColor);
                            drawDotLine(gg, getScreenXPositionFromPoint(i), topSpace, getScreenXPositionFromPoint(i), topSpace + getYAxisWidth());
                            continue;
                        }

                        g.setColor(Color.black);
                        g.drawLine(getScreenXPositionFromPoint(i), topSpace + getYAxisWidth(), getScreenXPositionFromPoint(i), topSpace + getYAxisWidth() + 4);
                        String dateMY = fpoint.getMonth() + "-" + fpoint.getYear();
                        if (bottomSpace > 8) {
                            g.drawString(dateMY, getScreenXPositionFromPoint(i), topSpace + getYAxisWidth() + 11);
                        }
                    }
                }
            } else if (currentChart.getChartData().dataType == ChartData.MONTHLY) {
                for (int i = startDisplayIndex; i < dpoint; i++) {
                    //System.out.println("ADFA");
                    int j = Math.max(i - 1, startDisplayIndex);
                    FPoint fpoint = (FPoint) currentChart.getChartData().getData().elementAt(i);
                    FPoint fpoint2 = (FPoint) currentChart.getChartData().getData().elementAt(j);
                    if (fpoint.getYear() != fpoint2.getYear()) {
                        if (!isLabel) {
                            gg.setColor(gridColor);
                            drawDotLine(gg, getScreenXPositionFromPoint(i), topSpace, getScreenXPositionFromPoint(i), topSpace + getYAxisWidth());
                            continue;
                        }

                        g.setColor(Color.black);
                        g.drawLine(getScreenXPositionFromPoint(i), topSpace + getYAxisWidth(), getScreenXPositionFromPoint(i), topSpace + getYAxisWidth() + 4);
                        String dateY = String.valueOf(fpoint.getYear());
                        if (bottomSpace > 8) {
                            g.drawString(dateY, getScreenXPositionFromPoint(i), topSpace + getYAxisWidth() + 11);
                        }
                    }
                }
            } else if (currentChart.getChartData().dataType == ChartData.INTRADAY) {
                for (int i = startDisplayIndex; i < dpoint; i++) {
                    int j = Math.max(i - 1, startDisplayIndex);
                    FPoint fpoint = (FPoint) currentChart.getChartData().getData().elementAt(i);
                    if (fpoint.getMinute() == 0 && fpoint.getHour() != 0) {
                        if (!isLabel) {
                            gg.setColor(gridColor);
                            drawDotLine(gg, getScreenXPositionFromPoint(i), topSpace, getScreenXPositionFromPoint(i), topSpace + getYAxisWidth());
                            continue;
                        }

                        g.setColor(Color.black);
                        g.drawLine(getScreenXPositionFromPoint(i), topSpace + getYAxisWidth(), getScreenXPositionFromPoint(i), topSpace + getYAxisWidth() + 4);

                        String dateTime = fpoint.getHour() + ":00";
                        if (bottomSpace > 8) {
                            g.drawString(dateTime, getScreenXPositionFromPoint(i), topSpace + getYAxisWidth() + 11);
                        }

                    }
                }
            }


        }

    }

    private void drawCompareTable() {
        ChartItem lcchart = getLeftChart();
        if (lcchart != null && lcchart.getChartType() == ChartType.PERCENTAGE) {
            //get the number of Percentage chart.....
            int count = 0;
            for (int i = 0; i < chartObjects.size(); i++) {
                ChartItem cchart = (ChartItem) chartObjects.elementAt(i);
                if (cchart.getChartType() == ChartType.PERCENTAGE) {
                    count++;
                }
            }
            // draw the table.
            int x1 = getSize().width - rightSpace - 150;
            int y1 = topSpace + 5;
            int ww = 145;
            int hh = 14 * count + 6;
            if (faction.currentMpoint.x > x1 && faction.currentMpoint.x < (x1 + ww) && faction.currentMpoint.y > y1 && faction.currentMpoint.y < (y1 + hh)) {
                return;
            }
            Graphics gg = allscreenImage.getGraphics();
            gg.setColor(new Color(240, 240, 240));
            gg.fill3DRect(x1, y1, ww, hh, true);
            int lcount = 1;
            for (int i = 0; i < chartObjects.size(); i++) {
                ChartItem cchart = (ChartItem) chartObjects.elementAt(i);
                if (cchart.getChartType() == ChartType.PERCENTAGE) {
                    gg.setColor(cchart.getFirstColor());
                    gg.setFont(new Font("", 0, 10));
                    String Name = cchart.getChartData().getEName();
                    if (language == FConfig.constChinese) {
                        Name = cchart.getChartData().getCName();
                    }
                    String slabel = FormatUtil.getCode(cchart.getChartData().getCode()) + " " + Name;
                    gg.drawString(slabel, getSize().width - rightSpace - 150 + 5, topSpace + 5 + 3 + lcount * 14);
                    lcount++;
                }
            }


        }

    }

    // draw the chart name .
    private void drawLabel(ChartItem currentChart) {

        Graphics g = allscreenImage.getGraphics();
        if (currentChart.getAxisBar() == AxisType.LEFTAXIS) {
            if (currentChart.getChartType() == ChartType.BAR || currentChart.getChartType() == ChartType.LINE || currentChart.getChartType() == ChartType.CANDLE) {
                g.setColor(currentChart.getFirstColor());
                String cname = "";
                if (language == FConfig.constEnglish) {
                    cname = currentChart.getChartData().getEName();
                } else if (language == FConfig.constChinese) {
                    cname = currentChart.getChartData().getCName();
                }
                g.drawString(FormatUtil.getCode(currentChart.getChartData().getCode()) + " " + cname, leftSpace, 18);
            }

            if (currentChart.getChartType() == ChartType.PERCENTAGE) {
                g.setColor(currentChart.getFirstColor());
                FPoint fpoint = (FPoint) currentChart.getChartData().getData().elementAt(startDisplayIndex);
                String sdate = "";
                if (currentChart.getChartData().dataType == ChartData.INTRADAY) {
                    sdate = lbArray[24][language] + " : " + FormatUtil.formatTime(fpoint.getHour(), fpoint.getMinute());
                } else {
                    sdate = lbArray[23][language] + " : " + fpoint.getDay() + "-" + fpoint.getMonth() + "-" + fpoint.getYear();
                }
                g.drawString(sdate, leftSpace, 18);
            }

        }


        if (currentChart.getChartType() == ChartType.VOLUME) {
            g.drawString(FormatUtil.getCode(currentChart.getChartData().getCode()) + " " + lbArray[9][language], leftSpace, 18);
        } else if (currentChart.getChartType() == ChartType.MACD) {
            String tempString;
            tempString = FormatUtil.getCode(currentChart.getChartData().getCode()) + " " + lbArray[20][language];
            tempString = tempString + "(" + currentChart.getChartData().getfTAconfig().MACDLEMA + "-" + currentChart.getChartData().getfTAconfig().MACDSEMA + ")";
            g.setColor(FConfig.MACDColor1);
            g.drawString(tempString, leftSpace, 18);
            tempString = "EMA(" + currentChart.getChartData().getfTAconfig().MACDAEMA + ")";
            g.setColor(FConfig.MACDColor2);
            g.drawString(tempString, leftSpace + 300, 18);
            tempString = "DIFF";
            g.setColor(FConfig.MACDColor3);
            g.drawString(tempString, leftSpace + 350, 18);
        } else if (currentChart.getChartType() == ChartType.EXPONENTIAL_MOVING_AVERAGE) {
            String tempString;
            g.setColor(FConfig.MAColor1);
            tempString = "EMA(" + currentChart.getChartData().getfTAconfig().EMA1 + ")";
            g.drawString(tempString, leftSpace + 160, 18);
            tempString = "EMA(" + currentChart.getChartData().getfTAconfig().EMA2 + ")";
            g.setColor(FConfig.MAColor2);
            g.drawString(tempString, leftSpace + 210, 18);
            tempString = "EMA(" + currentChart.getChartData().getfTAconfig().EMA3 + ")";
            g.setColor(FConfig.MAColor3);
            g.drawString(tempString, leftSpace + 260, 18);
        } else if (currentChart.getChartType() == ChartType.SIMPLE_MOVING_AVERAGE) {
            String tempString;
            g.setColor(FConfig.MAColor1);
            tempString = "SMA(" + currentChart.getChartData().getfTAconfig().SMAN1 + ")";
            g.drawString(tempString, leftSpace + 160, 18);
            tempString = "SMA(" + currentChart.getChartData().getfTAconfig().SMAN2 + ")";
            g.setColor(FConfig.MAColor2);
            g.drawString(tempString, leftSpace + 210, 18);
            tempString = "SMA(" + currentChart.getChartData().getfTAconfig().SMAN3 + ")";
            g.setColor(FConfig.MAColor3);
            g.drawString(tempString, leftSpace + 260, 18);
        } else if (currentChart.getChartType() == ChartType.WEIGHTED_MOVING_AVERAGE) {
            String tempString;
            g.setColor(FConfig.MAColor1);
            tempString = "WMA(" + currentChart.getChartData().getfTAconfig().WMAN1 + ")";
            g.drawString(tempString, leftSpace + 160, 18);
            tempString = "WMA(" + currentChart.getChartData().getfTAconfig().WMAN2 + ")";
            g.setColor(FConfig.MAColor2);
            g.drawString(tempString, leftSpace + 210, 18);
            tempString = "WMA(" + currentChart.getChartData().getfTAconfig().WMAN3 + ")";
            g.setColor(FConfig.MAColor3);
            g.drawString(tempString, leftSpace + 260, 18);
        } else if (currentChart.getChartType() == ChartType.BOLLINGERBAND) {
            String tempString;
            g.setColor(FConfig.BollingerBandColor);
            tempString = "SMA(" + currentChart.getChartData().getfTAconfig().bbN + ")";
            tempString = tempString + "  " + lbArray[22][language] + "(" + currentChart.getChartData().getfTAconfig().bbDevation + ")";
            g.drawString(tempString, leftSpace + 160, 18);
        } else if (currentChart.getChartType() == ChartType.RSI) {
            g.drawString(FormatUtil.getCode(currentChart.getChartData().getCode()) + " " + lbArray[17][language], leftSpace, 18);
            String tempString;
            tempString = "RSI(" + currentChart.getChartData().getfTAconfig().RSIPeriod + ")";
            g.setColor(FConfig.RSIColor);
            g.drawString(tempString, leftSpace + 160, 18);


        } else if (currentChart.getChartType() == ChartType.OBV) {
            g.drawString(FormatUtil.getCode(currentChart.getChartData().getCode()) + " " + lbArray[19][language], leftSpace, 18);
        } else if (currentChart.getChartType() == ChartType.STC) {
            g.drawString(FormatUtil.getCode(currentChart.getChartData().getCode()) + " " + lbArray[18][language], leftSpace, 18);
            String tempString;
            tempString = "%K(" + currentChart.getChartData().getfTAconfig().STCKPeriod + ")";
            g.setColor(FConfig.STCColorK);
            g.drawString(tempString, leftSpace + 100, 18);
            tempString = "%D(" + currentChart.getChartData().getfTAconfig().STCDPeriod + ")";
            g.setColor(FConfig.STCColorD);
            g.drawString(tempString, leftSpace + 150, 18);
        } else if (currentChart.getChartType() == ChartType.WILLIAM_R) {
            String tempString = FormatUtil.getCode(currentChart.getChartData().getCode()) + " " + lbArray[21][language];
            tempString = tempString + "(" + currentChart.getChartData().getfTAconfig().WilliamPeriod + ")";
            g.setColor(FConfig.WilliamRColor);
            g.drawString(tempString, leftSpace, 18);
        }


    }

    //plot the Left YAxis
    private synchronized void plotYAxis(ChartItem currentChart, boolean isLabel) {
        if (currentChart == null) {
            return;
        }
        if (currentChart.getChartBound() == null) {
            return;
        }
        if (gridColor == null) {
            gridColor = new Color(200, 200, 200);
        }
        double Max = 0;
        double Min = 0;

        //calculate the actual bound in the Yaxis.
        Max = currentChart.getUpperBound();
        Min = currentChart.getLowerBound();


        //System.out.println("DD : " + (Max-Min) + " : " + ((int)(Max-Min)*10)/10f);
        float div = (float) (Max - Min) / 3f;
        float ddiv = div;
        if (div < 1 && div >= 0.1) {
            int tempd = Math.round((div * 10));
            ddiv = (tempd) / 10f;
        } else if (div < 0.1 && div >= 0.01) {
            int tempd = Math.round(div * 100);
            ddiv = (tempd) / 100f;
        } else if (div < 10 && div >= 1) {
            int tempd = (int) (div);
            ddiv = tempd;
        } else if (div < 100 && div >= 10) {
            int tempd = (int) (div);
            ddiv = tempd;
        } else if (div < 1000 && div >= 100) {
            int tempd = (int) (div / 10);
            tempd = (int) (tempd * 10);
            ddiv = tempd;
        } else if (div >= 1000) {
            int tempd = (int) (div / 1000);
            tempd = (int) (tempd * 1000);
            ddiv = tempd;
        }


        int tempi = (int) ((float) Min / ddiv);
        float fmin = (tempi * ddiv);
        double sg;
        sg = (tempi) * ddiv; //the first grid line value;

        //if ((Max/ddiv)>100) return;
        // it is used to fix the bug that ddiv == 0, it occur where the server data error.
        if ((ddiv < 0.0001f) || ((sg + 20 * ddiv) < Max)) {
            return;
        }

        //if it is TA chart then 0 to 100 axis
        if (currentChart.getChartType() == ChartType.RSI || currentChart.getChartType() == ChartType.STC || currentChart.getChartType() == ChartType.WILLIAM_R) {
            sg = 0.0f;
            ddiv = 20f;
        }

        Graphics g = allscreenImage.getGraphics();
        Graphics gg = screenImage.getGraphics();

        // if the screen is small, reduce the font size
        if (this.getSize().height < 150) {
            g.setFont(new Font("", 0, 10));
            gg.setFont(new Font("", 0, 10));
        } else {
            g.setFont(new Font("", 0, 12));
            gg.setFont(new Font("", 0, 12));

        }


        if (currentChart.getAxisBar() == AxisType.LEFTAXIS) {
            for (; sg <= Max; sg = sg + ddiv) {
                int ypos = getScreenYPosition(sg, Max, Min);
                if (sg < Min || sg > Max) {
                    continue;
                }
                if (!isLabel) {
                    gg.setColor(gridColor);
                    drawDotLine(gg, leftSpace, ypos, leftSpace + getXAxisWidth(), ypos);
                    continue;
                }

                g.setColor(Color.black);
                g.drawLine(leftSpace, ypos, leftSpace - 3, ypos);
                g.setColor(currentChart.getFirstColor());
                String ss;
                if (currentChart.getChartType() == ChartType.VOLUME) {
                    ss = FormatUtil.formatInteger(sg);
                } else if (currentChart.getChartType() == ChartType.PERCENTAGE) {
                    ss = FormatUtil.formatInteger(sg) + "%";
                } else if (currentChart.getChartType() == ChartType.RSI || currentChart.getChartType() == ChartType.STC || currentChart.getChartType() == ChartType.WILLIAM_R) {
                    ss = String.valueOf((int) (sg + 0.5));
                } else if (currentChart.getChartType() == ChartType.OBV) {
                    ss = FormatUtil.formatOBV(sg);
                } else {
                    if (sg > 1000) {
                        //ss = String.valueOf((int)(sg+0.5));
                        ss = FormatUtil.formatInteger(sg + 0.5);

                    } else {
                        ss = FormatUtil.formatData2(sg);
                    }
                }
                g.setColor(Color.black);
                g.drawString(ss, 2, ypos + 3);
            }
        } else if (currentChart.getAxisBar() == AxisType.RIGHTAXIS) {
            for (; sg < Max; sg = sg + ddiv) {
                if (sg < Min || sg > Max) {
                    continue;
                }
                int ypos = getScreenYPosition(sg, Max, Min);
                g.setColor(Color.black);
                g.drawLine(leftSpace + getXAxisWidth(), ypos, leftSpace + getXAxisWidth() + 3, ypos);
                String ss = FormatUtil.formatData2(sg);
                g.setColor(currentChart.getFirstColor());
                g.drawString(ss, leftSpace + getXAxisWidth() + 6, ypos + 3);
            }
        }
    }

    // switch to different type of chart to plot
    private void plotChart(ChartItem currentChart) {
        switch (currentChart.getChartType()) {
            case LINE:
                plotLineChart(currentChart);
                //plotVolumeChart(currentChart);
                break;

            case BAR:
                plotBarChart(currentChart);
                //plotVolumeChart(currentChart);
                break;

            case CANDLE:
                plotCandleChart(currentChart);
                //plotVolumeChart(currentChart);
                break;

            case VOLUME:
                plotVolumeChart(currentChart);
                break;

            case PERCENTAGE:
                plotPercentageChart(currentChart);
                break;

            case SIMPLE_MOVING_AVERAGE:
                plotMovingAverage(currentChart);
                break;

            case WEIGHTED_MOVING_AVERAGE:
                plotMovingAverage(currentChart);
                break;

            case EXPONENTIAL_MOVING_AVERAGE:
                plotMovingAverage(currentChart);
                break;

            case BOLLINGERBAND:
                plotBollingerBand(currentChart);
                break;

            case RSI:
                plotRSI(currentChart);
                break;

            case MACD:
                plotMACD(currentChart);
                break;

            case WILLIAM_R:
                plotWILLIAM_R(currentChart);
                break;

            case OBV:
                plotOBV(currentChart);
                break;

            case STC:
                plotSTC(currentChart);
                break;


        }

    }

    // clear the Screen
    private boolean clearScreen() {
        //System.out.println("Entering ClearScreen");
        Graphics screenG = screenImage.getGraphics();
        screenG.setColor(FConfig.ChatBackground);
        screenG.fillRect(0, 0, getSize().width, getSize().height);
        return true;
    }

    // remove all charts.
    public void removeAllCharts() {
        this.chartObjects.removeAllElements();
        updateBaseScreen();
    }

    // plot the Axis
    private synchronized boolean plotAxis(boolean isLabel) {
        Graphics screenG = allscreenImage.getGraphics();
        screenG.setColor(Color.black);
        screenG.drawLine(leftSpace, topSpace, leftSpace, topSpace + getYAxisWidth());
        screenG.drawLine(leftSpace, topSpace + getYAxisWidth(), leftSpace + getXAxisWidth(), topSpace + getYAxisWidth());
        screenG.drawLine(leftSpace + getXAxisWidth(), topSpace, leftSpace + getXAxisWidth(), topSpace + getYAxisWidth());
        screenG.setColor(FConfig.ScreenBackground);
        screenG.fillRect(0, 0, leftSpace - 1, getSize().height);
        screenG.fillRect(0, 0, getSize().width, topSpace - 1);
        screenG.fillRect(getSize().width - rightSpace + 1, 0, rightSpace - 1, getSize().height);
        screenG.fillRect(0, getSize().height - bottomSpace + 1, getSize().width, bottomSpace);

        try {
            for (int i = 0; i < chartObjects.size(); i++) {
                ChartItem currentChart = (ChartItem) chartObjects.elementAt(i);
                if (currentChart.isVisable()) {
                    plotXAxis(currentChart, isLabel);
                    plotYAxis(currentChart, isLabel);
                    drawLabel(currentChart);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    // get the Maximum Number of point that the screen can display.
    private int getMaxNumberOfDisplayPoint() {
        //because minimum pixels per point.
        return (getXAxisWidth() - 2) / minResolution - 2;
    }

    // get the Maximum Number of display point that the screen can display in the current resoluation
    private int getMaxNumberOfDisplayPointInCurrentResolution() {
        return (getXAxisWidth() - 2) / resolution;
    }

    // retrieve the (central) point position in the screen
    public int getScreenXPositionFromPoint(int pointIndex) {
        int pos = leftSpace + 1 + (pointIndex - startDisplayIndex) * resolution + resolution / 2;
        return pos;
    }

    // retrieve index from screenPos (Xaxis)
    public int getPointIndexFromScreen(int screenPos) {
        int pos = startDisplayIndex + (screenPos - leftSpace - 1) / resolution;
        return pos;
    }

    // retrieve the Abract Y position given the value and the range.....
    public int getScreenYPosition(double value, double MaxValue, double MinValue) {
        MinValue = MinValue - 0.0001f;
        double dpos = (double) getYAxisWidth() - ((double) (value - MinValue) / ((double) MaxValue - MinValue) * getYAxisWidth()) + topSpace;
        int pos = (int) Math.round(dpos);
        return pos;
    }

    public double getYValueFromScreen(int y, double MaxValue, double MinValue) {
        double dValue = ((double) (getYAxisWidth() - y + topSpace) / getYAxisWidth()) * (MaxValue - MinValue) + MinValue;
        return dValue;
    }

    public void setSpace(int ileftSpace, int irightSpace, int itopSpace, int ibottomSpace) {
        leftSpace = ileftSpace;
        rightSpace = irightSpace;
        topSpace = itopSpace;
        bottomSpace = ibottomSpace;
    }

    public void mouseDragged(MouseEvent e) {
        if (faction.currentActionType == FAction.Type.WATCH || faction.currentActionType == FAction.Type.MOVECHART) {
            if (!isWithinChartRegion(e.getX(), e.getY())) {
                faction.actionProcessing = false;
                repaint();
                return;
            }
        }
        switch (faction.currentActionType) {
            case ZOOMIN:
                faction.currentMpoint.x = e.getPoint().x;
                faction.currentMpoint.y = e.getPoint().y;
                repaint();
                break;

            case INSERTLINE:
                faction.currentMpoint.x = e.getPoint().x;
                faction.currentMpoint.y = e.getPoint().y;
                repaint();
                break;

            case GOLDENPARTITION:
                faction.currentMpoint.x = e.getPoint().x;
                faction.currentMpoint.y = e.getPoint().y;
                repaint();
                break;

            case MOVECHART:
                if (e.getPoint().x > faction.currentMpoint.x + 5) {
                    this.moveLeft();
                    faction.currentMpoint.x = e.getPoint().x;
                    faction.currentMpoint.y = e.getPoint().y;
                } else if (e.getPoint().x < faction.currentMpoint.x - 5) {
                    this.moveRight();
                    faction.currentMpoint.x = e.getPoint().x;
                    faction.currentMpoint.y = e.getPoint().y;
                }
                break;
        }

    }

    public void mouseMoved(MouseEvent e) {
        if (!isWithinChartRegion(e.getX(), e.getY())) {
            faction.actionProcessing = false;

        }
        faction.currentMpoint.x = e.getX();
        faction.currentMpoint.y = e.getY();
        switch (faction.currentActionType) {
            case INSERTPARALLELLINE:
                if (faction.lineRecords.size() == 0) {
                    faction.actionProcessing = false;
                } else {
                    faction.actionProcessing = true;
                    repaint();
                }
                break;

            case WATCH:
                faction.actionProcessing = true;
                repaint();
                break;

            case NONEACTION:
                faction.actionProcessing = false;
                repaint();
                break;

            default:
                faction.actionProcessing = false;
                break;
        }

    }

    public void mousePressed(MouseEvent e) {

        if (!isWithinChartRegion(e.getX(), e.getY())) {
            faction.actionProcessing = false;
            return;
        }

        switch (faction.currentActionType) {
            case ZOOMIN:
                faction.actionProcessing = true;
                faction.pressMpoint.x = e.getPoint().x;
                faction.pressMpoint.y = e.getPoint().y;
                break;

            case INSERTLINE:
                faction.actionProcessing = true;
                faction.pressMpoint.x = e.getPoint().x;
                faction.pressMpoint.y = e.getPoint().y;
                break;

            case GOLDENPARTITION:
                faction.actionProcessing = true;
                faction.pressMpoint.x = e.getPoint().x;
                faction.pressMpoint.y = e.getPoint().y;
                break;

            case MOVECHART:
                faction.actionProcessing = true;
                faction.pressMpoint.x = e.getPoint().x;
                faction.pressMpoint.y = e.getPoint().y;
                faction.currentMpoint.x = e.getPoint().x;
                faction.currentMpoint.y = e.getPoint().y;
                break;


        }

    }

    public void mouseReleased(MouseEvent e) {
        if (faction.currentActionType == FAction.Type.WATCH || faction.currentActionType == FAction.Type.MOVECHART) {
            if (!isWithinChartRegion(e.getX(), e.getY())) {
                faction.actionProcessing = false;
                return;
            }
        }
        FLine fline;
        switch (faction.currentActionType) {

///// Handle Zoom in action ///////////////////////////////////////////////////////////////////
            case ZOOMIN:
                //faction.mouseFlag = false;
                faction.actionProcessing = false;
                faction.releaseMpoint.x = e.getPoint().x;
                faction.releaseMpoint.y = e.getPoint().y;
                int index1 = getPointIndexFromScreen(faction.releaseMpoint.x);
                int index2 = getPointIndexFromScreen(faction.pressMpoint.x);

                if (Math.abs(index1 - index2) > 5) {
                    int startIndex = Math.min(index1, index2);
                    int endIndex = Math.max(index1, index2);
                    if (endIndex > endDisplayIndex) {
                        endIndex = endDisplayIndex;
                    }
                    if (startIndex < startDisplayIndex) {
                        startIndex = startDisplayIndex;
                    }
                    //record the previous position;
                    faction.zoomRecords.addElement(new Point(startDisplayIndex, endDisplayIndex));
                    //change the zoom
                    zoom(startIndex, endIndex);
                } else {
                    repaint();
                }
                break;

///// Handle insert line action //////////////////////////////////////////////////////////////////
            case INSERTLINE:
                faction.actionProcessing = false;
                faction.releaseMpoint.x = e.getPoint().x;
                faction.releaseMpoint.y = e.getPoint().y;
                if (FLine.isFixedLine()) {
                    fline = new FLine(faction.pressMpoint.x, faction.pressMpoint.y, faction.releaseMpoint.x, faction.releaseMpoint.y);
                    faction.lineRecords.addElement(fline);
                } else {
                    int x1 = getPointIndexFromScreen(faction.pressMpoint.x);
                    int x2 = getPointIndexFromScreen(faction.releaseMpoint.x);
                    float y1, y2;
                    ChartItem cchart = this.getLeftChart();
                    if (cchart != null) {
                        float Min = (float) cchart.getLowerBound();
                        float Max = (float) cchart.getUpperBound();
                        y1 = (float) getYValueFromScreen(faction.pressMpoint.y, Max, Min);
                        y2 = (float) getYValueFromScreen(faction.releaseMpoint.y, Max, Min);
                        fline = new FLine(x1, y1, x2, y2);
                        fline.getPoint1().x = faction.pressMpoint.x;
                        fline.getPoint1().y = faction.pressMpoint.y;
                        fline.getPoint2().x = faction.releaseMpoint.x;
                        fline.getPoint2().y = faction.releaseMpoint.y;
                        faction.lineRecords.addElement(fline);
                    }
                }
                repaint();
                break;

            case GOLDENPARTITION:
                faction.actionProcessing = false;
                faction.releaseMpoint.x = e.getPoint().x;
                faction.releaseMpoint.y = e.getPoint().y;

                if (FLine.isFixedLine()) {
                    fline = new FLine(faction.pressMpoint.x, faction.pressMpoint.y, faction.releaseMpoint.x, faction.releaseMpoint.y);
                    faction.goldenPartitionLine = fline;
                } else {
                    int x1 = getPointIndexFromScreen(faction.pressMpoint.x);
                    int x2 = getPointIndexFromScreen(faction.releaseMpoint.x);
                    float y1, y2;
                    ChartItem cchart = this.getLeftChart();
                    if (cchart != null) {
                        float Min = (float) cchart.getLowerBound();
                        float Max = (float) cchart.getUpperBound();
                        y1 = (float) getYValueFromScreen(faction.pressMpoint.y, Max, Min);
                        y2 = (float) getYValueFromScreen(faction.releaseMpoint.y, Max, Min);
                        fline = new FLine(x1, y1, x2, y2);
                        fline.getPoint1().x = faction.pressMpoint.x;
                        fline.getPoint1().y = faction.pressMpoint.y;
                        fline.getPoint2().x = faction.releaseMpoint.x;
                        fline.getPoint2().y = faction.releaseMpoint.y;
                        faction.goldenPartitionLine = fline;
                    }
                }

                if (Math.abs(faction.pressMpoint.y - faction.releaseMpoint.y) < 5) {
                    faction.goldenPartitionLine = null;
                }

                repaint();
                break;

            // handle move chart action -- disable it when release the button .//////////////////////////////
            case MOVECHART:
                faction.actionProcessing = false;
                break;

        }
    }

    public void mouseEntered(MouseEvent e) {
        repaint();
    }

    public void mouseExited(MouseEvent e) {
        switch (faction.currentActionType) {
            // still perform the action.
            case GOLDENPARTITION:
            case INSERTPARALLELLINE:
            case INSERTLINE:
            case ZOOMIN:
                break;

            // cancel the action.
            default:
                faction.actionProcessing = false;
                repaint();
                break;
        }
    }

    protected boolean isWithinCloseButton(int x, int y) {


        System.out.println(x + " : " + y);
        if (x >= (getWidth() - 17) && x <= getWidth() && y >= 0 && y <= 15) {
            return true;
        }

        return false;
    }

    public void mouseClicked(MouseEvent e) {

        if (isWithinCloseButton(e.getX(), e.getY())) {
            this.setVisible(false);
            return;
        }

        if (!isWithinChartRegion(e.getX(), e.getY())) {
            faction.actionProcessing = false;
            return;
        }
        switch (faction.currentActionType) {
            case ZOOMIN:
                String mparamString = e.paramString();
                //if right click mouse
                if (mparamString.indexOf("mods=4") > 0) {
                    this.undoZoom();
                }
                break;

            // when mouse click insert a parallel line into action.lineRecords.///////////////////////////////////
            case INSERTPARALLELLINE:
                if (!isWithinChartRegion(e.getX(), e.getY())) {
                    return;
                }

                FLine fline = (FLine) faction.lineRecords.lastElement();
                Point rpoint = new Point(0, 0);
                if (fline.getPoint1().x < fline.getPoint2().x) {
                    rpoint.x = fline.getPoint1().x;
                    rpoint.y = fline.getPoint1().y;
                } else {
                    rpoint.x = fline.getPoint2().x;
                    rpoint.y = fline.getPoint2().y;
                }
                int dx = +faction.currentMpoint.x - rpoint.x;
                int dy = +faction.currentMpoint.y - rpoint.y;
                if (FLine.isFixedLine()) {
                    FLine newFline = new FLine(fline.getPoint1().x + dx, fline.getPoint1().y + dy, fline.getPoint2().x + dx, fline.getPoint2().y + dy);
                    faction.lineRecords.addElement(newFline);
                } else {

                    int x1 = getPointIndexFromScreen(fline.getPoint1().x + dx);
                    int x2 = getPointIndexFromScreen(fline.getPoint2().x + dx);
                    float y1, y2;
                    ChartItem cchart = this.getLeftChart();
                    if (cchart != null) {
                        float Min = (float) cchart.getLowerBound();
                        float Max = (float) cchart.getUpperBound();
                        y1 = (float) getYValueFromScreen(fline.getPoint1().y + dy, Max, Min);
                        y2 = (float) getYValueFromScreen(fline.getPoint2().y + dy, Max, Min);
                        FLine newfline = new FLine(x1, y1, x2, y2);
                        newfline.getPoint1().x = fline.getPoint1().x + dx;
                        newfline.getPoint1().y = fline.getPoint1().y + dy;
                        newfline.getPoint2().x = fline.getPoint2().x + dx;
                        newfline.getPoint2().y = fline.getPoint2().y + dy;
                        faction.lineRecords.addElement(newfline);
                    }

                }
                repaint();
                break;
        }
    }

    //  End: response of mouse action  .............................................
    //move chart to left
    public void moveLeft() {
        if ((startDisplayIndex - 1) >= 0) {
            int startIndex = startDisplayIndex - 1;
            int endIndex = endDisplayIndex - 1;
            zoom(startIndex, endIndex);
            this.updateBaseScreen();
            this.repaint();
        }
    }

    //move chart to right
    public void moveRight() {
        if ((endDisplayIndex + 1) < maxNumberOfChartPoint) {
            int startIndex = startDisplayIndex + 1;
            int endIndex = endDisplayIndex + 1;
            zoom(startIndex, endIndex);
            this.updateBaseScreen();
            this.repaint();
        }
    }

    public ChartItem getLeftChart() {
        for (int i = 0; i < chartObjects.size(); i++) {
            ChartItem cchart = (ChartItem) chartObjects.elementAt(i);
            if (cchart.getAxisBar() == AxisType.LEFTAXIS) {
                return cchart;
            }
        }
        return null;
    }

    public ChartItem getRightChart() {
        for (int i = 0; i < chartObjects.size(); i++) {
            ChartItem cchart = (ChartItem) chartObjects.elementAt(i);
            if (cchart.getAxisBar() == AxisType.RIGHTAXIS) {
                return cchart;
            }
        }
        return null;
    }

    public void undoInsertLine() {
        if (faction.lineRecords.size() > 0) {
            FLine fline = (FLine) faction.lineRecords.lastElement();
            faction.lineRecords.removeElement(fline);
        }
        repaint();

    }

    // Undo Zoom
    public Point undoZoom() {
        int endIndex = maxNumberOfChartPoint - 1;
        int startIndex = Math.max(0, endIndex - getMaxNumberOfDisplayPoint());
        Point ozoom = new Point(startIndex, endIndex);
        if (faction.zoomRecords.size() > 0) {
            ozoom = (Point) faction.zoomRecords.lastElement();
            faction.zoomRecords.removeElement(ozoom);
            zoom(ozoom.x, ozoom.y);
            return ozoom;
        } else if (faction.zoomRecords.size() == 0) {
            zoom(ozoom.x, ozoom.y);
            return ozoom;
        }
        return ozoom;
    }

    public void addScreenActionListen(ScreenActionListener ss) {
        screenActionListener = ss;
    }

    public void flipLoading() {

        repaint();
    }
}
