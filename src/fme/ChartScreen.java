package fme;

/* has intraDay chart */

import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.StringTokenizer;
import java.util.Vector;


public class ChartScreen extends Panel implements ItemListener {
////////////////////////////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////////////[
    private final String lbArray[][] = {{"Moving Avg", "\u5e73\u5747\u7dda"}         //0
                                        , {"Volume", "\u6210\u4ea4\u91cf"}                  //1
                                        , {"RSI", "\u76f8\u5c0d\u5f37\u5f31\u6307\u6578"}   //2
                                        , {"Stochastic", "Stochastic"}                      //3
                                        , {"Momentum", "\u52d5\u91cf"}                      //4
                                        , {"Bar", "\u9670\u967d"}                           //5
                                        , {"Candle", "\u68d2\u5f62"}                        //6
                                        , {"Line", "\u7dda\u5f62"}                          //7
                                        , {"Daily", "\u65e5\u7dda"}                         //8
                                        , {"Weekly", "\u661f\u671f\u7dda"}                  //9
                                        , {"Monthly", "\u6708\u7dda"}                       //10
                                        , {"Intraday", "\u5373\u5e02"}                      //11
                                        , {"None", "\u7121"}                                //12
                                        , {"HSI", "\u6046\u6307"}                           //13
                                        , {"Red-chips Index", "\u7d05\u7c4c\u6307\u6578"}   //14
                                        , {"H-shares Index", "\u570b\u4f01\u6307\u6578"}    //15
                                        , {"Open", "\u958b\u5e02"}                          //16
                                        , {"Last", "\u6536\u5e02"}                          //17
                                        , {"High", "\u6700\u9ad8"}                          //18
                                        , {"Low", "\u6700\u4f4e"}                           //19
                                        , {"Volume", "\u6210\u4ea4\u91cf"}                  //20
                                        , {"Lines", "\u7dda"}                               //21
                                        , {"Points", "\u9ede"}                              //22
                                        , {"None", "\u7121"}                                //23
                                        , {"Black", "\u9ed1"}                               //24
                                        , {"Red", "\u7d05"}                                 //25
                                        , {"Blue", "\u85cd"}                                //26
                                        , {"Green", "\u7da0"}                               //27
                                        , {"Pink", "\u7c89\u7d05"}                          //28
                                        , {"Cyan", "\u9752"}                                //29
                                        , {"Orange", "\u6a59"}                              //30
                                        , {"Magenta", "\u7d2b"}                             //31
                                        , {"Clear", "\u6e05\u9664"}                         //32
                                        , {"Undo", "\u5fa9\u539f"}                          //33
                                        , {"Draw Chart", "\u756b\u5716"}                    //34
                                        , {"Days", "\u65e5"}                                //35
                                        , {"Sell", "\u8ce3\u51fa"}                          //36
                                        , {"HSIF", "\u6046\u751f\u671f\u6307"}              //37
                                        , {"GEM Index", "\u5275\u696d\u677f\u6307\u6578"}   //38
                                        , {"Fin. Index", "\u91d1\u878d\u80a1\u6307\u6578"}    //39
                                        , {"Util. Index", "\u516c\u7528\u80a1\u6307\u6578"}   //40
                                        , {"Prop. Index", "\u5730\u7522\u80a1\u6307\u6578"}   //41
                                        , {"Com & Ind Index", "\u516c\u5546\u80a1\u6307\u6578"}    //42
                                        , {"TraHK", "\u76c8\u5bcc\u57fa\u91d1"}    //43
                                        , {"Mid-Cap Index", "\u4e2d\u578b\u80a1\u6307\u6578"}    //44
    };

    private int language;
    int languagenow = language;
///////////////////////////////////////////////////////////////////////////////////////////////////]
////////////////////////////////////////////////////////////////////////////////////////////////////

    public final static int LINES = 0;
    public final static int POINTS = 1;

    Color topBgColor = Color.white;	//new Color(79,79,79);
    Color topFontColor = new Color(255, 255, 255);
    Color grey = new Color(217, 217, 217);
    Color BgColor = Color.white;	//new Color(144,183,212);
    Color FontColor = new Color(119, 102, 1);

    Color lowBgColor = Color.white;	//new Color(75,75,75);
    Color choiceBgColor = Color.white;
    Color MA1Color = new Color(14, 132, 2);
    Color MA2Color = new Color(217, 19, 215);
    Color STO1Color = Color.blue;
    Color STO2Color = Color.magenta;

    CardLayout UfunctionCard = new CardLayout();
    CardLayout LfunctionCard = new CardLayout();
    Panel RSIPanel = new Panel();
    Panel STOPanel = new Panel();
    Panel MOMPanel = new Panel();
    Panel VolPanel = new Panel();
    Panel MAPanel = new Panel();
    Panel NonePanel = new Panel();

//MainScreen mainScreen ;
    String codeID;

    RectangleArea rectanglePanel;
    DrawPanel chartPanel;
    DrawControls controlPanel;

    Panel UfTabPanel = new Panel();
    Panel UfunctionPanel = new Panel();
    Panel functionPanel = new Panel();
    Panel LfTabPanel = new Panel();
    Panel LfunctionPanel = new Panel();
    Panel valuePanel = new Panel();

    Panel buttonPanel = new Panel();
////////////////////////////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////////////[
    Choice chartType;
    Choice Ufunction;
    Choice Lfunction;
    Choice period;
    Choice compare;

    int StateUfunction, StateLfunction, StateChartType, StatePeriod, StateCompare, StateShapes, StateColor;
///////////////////////////////////////////////////////////////////////////////////////////////////]
////////////////////////////////////////////////////////////////////////////////////////////////////

    TextField MAT1, MAT2, RSIT, STOT1, STOT2, MOMT;
    Label MA1, MA2, STO1, STO2, RSI1, MOM1;
    float STOmax, STOmin;
    int compareLine = (int) 0;
    int type = (int) 1;
    int Utype = (int) 0;

//Hung: stored the type of label
//0--Volumn 1--RSI  2--STO  3--MON
    int Ltype = (int) 0;

    int Ptype = (int) 0; // 0 Daily, 1 Weekly, 2 Monthly, 3 Intraday
    int connected = 0;

    int translate;

//Connection DataBase;
    Label dateL, openL, lastL, highL, lowL, volL;

    String table, tableIntra;

    private static String ServerAddress;

    public static void SetServerAddress(String addr) {
        ServerAddress = addr;
    }

    public ChartScreen(String s) {

        this.codeID = "0001";

        setBackground(BgColor);
        setFont(new Font("Arial", Font.PLAIN, 14));
        setSize(500, 240);
        tableName(s);

        setLayout(null);
        chartPanel = new DrawPanel();
        rectanglePanel = new RectangleArea(chartPanel);
//Hung,
//rectanglePanel.setBackground(Color.blue );

        chartPanel.putRectangle(rectanglePanel);
        rectanglePanel.setFont(new Font("Arial", Font.PLAIN, 10));
        chartPanel.setBounds(5, 5, 370, 180);
        rectanglePanel.add(chartPanel);
        rectanglePanel.setBounds(0, 25, 420, 210);
        controlPanel = new DrawControls(chartPanel);
        controlPanel.setBounds(420, 145, 80, 90);
//Hung,
//controlPanel.setBackground(Color.cyan);

//    buttonPanel.setBounds(420,0,80,46);
        buttonPanel.setBounds(420, 0, 80, 60);
        buttonPanel.setLayout(null);

        buttonPanel.setBackground(topBgColor);

//Hung,
//buttonPanel.setBackground(Color.darkGray);
        add(buttonPanel);
        add(rectanglePanel);
        add(controlPanel);
        add(functionPanel);
        add(valuePanel);
        functionPanel.setLayout(null);
        functionPanel.add(UfunctionPanel);
        functionPanel.add(UfTabPanel);
        functionPanel.add(LfunctionPanel);
        functionPanel.add(LfTabPanel);
        functionPanel.setBackground(lowBgColor);
        functionPanel.setBounds(0, 0, 420, 25);
//Hung,
//functionPanel.setBackground(Color.green);

        UfunctionPanel.setLayout(null);
        UfunctionPanel.setBackground(BgColor);

//Hung,
//UfunctionPanel.setBackground(Color.yellow);
        UfunctionPanel.setBounds(2, 3, 191, 20);
        LfunctionPanel.setLayout(null);
        LfunctionPanel.setBackground(BgColor);
        LfunctionPanel.setBounds(197, 3, 231, 20);
//Hung,
//LfunctionPanel.setBackground(Color.pink);
        Ufunction = new Choice();
        Ufunction.addItemListener(this);
        Ufunction.addItem("---");
        Ufunction.addItem("Moving Avg");
        Ufunction.setBackground(Color.lightGray);
        UfunctionPanel.add(Ufunction);
        Ufunction.setBounds(0, 0, 80, 20);
        Ufunction.setFont(new Font("Arial", Font.PLAIN, 12));
        Ufunction.setBackground(choiceBgColor);

        Lfunction = new Choice();
        Lfunction.addItemListener(this);
        Lfunction.addItem("Volume");
        Lfunction.addItem("RSI");
        Lfunction.addItem("Stochastic");
        Lfunction.addItem("Momentum");
        Lfunction.setBackground(Color.lightGray);
        LfunctionPanel.add(Lfunction);
        Lfunction.setBounds(0, 0, 75, 20);
        Lfunction.setFont(new Font("Arial", Font.PLAIN, 12));
        Lfunction.setBackground(choiceBgColor);
//Lfunction.setBackground(Color.black);

        UfTabPanel.setBounds(70, 0, 170, 20);
        UfTabPanel.setBackground(Color.blue);
        UfTabPanel.setLayout(UfunctionCard);
        UfTabPanel.add("UNone", NonePanel);
        UfTabPanel.add("MA", MAPanel);
        UfunctionPanel.add(UfTabPanel);
        LfTabPanel.setBounds(50, 0, 175, 20);
        LfTabPanel.setBackground(Color.blue);
        LfTabPanel.setLayout(LfunctionCard);
        LfTabPanel.add("Vol", VolPanel);
        LfTabPanel.add("RSI", RSIPanel);
        LfTabPanel.add("STO", STOPanel);
        LfTabPanel.add("MOM", MOMPanel);
        LfunctionPanel.add(LfTabPanel);
        RSIPanel.setBackground(BgColor);
        RSIPanel.setLayout(null);
        RSI1 = new Label();
        RSI1.setFont(new Font("Arial", Font.BOLD, 12));
        RSI1.setForeground(FontColor);
        RSI1.setBounds(60, 0, 40, 20);
        RSI1.setText("Days");
        RSIT = new TextField();
        RSIT.setFont(new Font("Arial", Font.PLAIN, 12));
        RSIT.setBackground(Color.white);
        RSIT.setBounds(30, 0, 30, 20);
        RSIPanel.add(RSI1);
        RSIPanel.add(RSIT);

        MOMPanel.setBackground(BgColor);
        MOMPanel.setLayout(null);
        MOM1 = new Label();
        MOM1.setFont(new Font("Arial", Font.BOLD, 12));
        MOM1.setForeground(FontColor);
        MOM1.setBounds(60, 0, 40, 20);
        MOM1.setText("Days");

        MOMT = new TextField();
        MOMT.setFont(new Font("Arial", Font.PLAIN, 12));
        MOMT.setBackground(Color.white);
        MOMT.setBounds(30, 0, 30, 20);
        MOMPanel.add(MOM1);
        MOMPanel.add(MOMT);

        STOPanel.setBackground(BgColor);
        STOPanel.setLayout(null);
        STO1 = new Label();
        STO1.setForeground(STO1Color);
        STO1.setFont(new Font("Arial", Font.PLAIN, 12));
        STO1.setBounds(25, 0, 20, 20);
        STO1.setText("%K");
        STO2 = new Label();
        STO2.setFont(new Font("Arial", Font.PLAIN, 12));
        STO2.setForeground(STO2Color);
        STO2.setBounds(77, 0, 21, 20);
        STO2.setText("%D");
        STOT1 = new TextField();
        STOT1.setFont(new Font("Arial", Font.PLAIN, 12));
        STOT1.setBounds(46, 0, 30, 20);
        STOT1.setBackground(Color.white);
        STOT2 = new TextField();
        STOT2.setFont(new Font("Arial", Font.PLAIN, 12));
        STOT2.setBounds(99, 0, 30, 20);
        STOT2.setBackground(Color.white);
        STOPanel.add(STO1);
        STOPanel.add(STOT1);
        STOPanel.add(STO2);
        STOPanel.add(STOT2);

        VolPanel.setBackground(BgColor);
        NonePanel.setBackground(BgColor);

        MAPanel.setBackground(BgColor);
        MAPanel.setLayout(null);
        MA1 = new Label();
        MA1.setForeground(MA1Color);
        MA1.setFont(new Font("Arial", Font.PLAIN, 12));
        MA1.setBounds(9, 0, 23, 20);
        MA1.setText("MA1");
        MA2 = new Label();
        MA2.setFont(new Font("Arial", Font.PLAIN, 12));
        MA2.setForeground(MA2Color);
        MA2.setBounds(65, 0, 24, 20);
        MA2.setText("MA2");
        MAT1 = new TextField();
        MAT1.setFont(new Font("Arial", Font.PLAIN, 12));
        MAT1.setBounds(34, 0, 30, 20);
        MAT1.setBackground(Color.white);
        MAT2 = new TextField();
        MAT2.setFont(new Font("Arial", Font.PLAIN, 12));
        MAT2.setBounds(91, 0, 30, 20);
        MAT2.setBackground(Color.white);
        MAPanel.add(MA1);
        MAPanel.add(MAT1);
        MAPanel.add(MA2);
        MAPanel.add(MAT2);

        valuePanel.setBounds(420, 46, 80, 100);
        valuePanel.setBackground(BgColor);
        valuePanel.setLayout(null);
        valuePanel.setFont(new Font("Arial", Font.PLAIN, 10));
//Hung
//valuePanel.setBackground(Color.red);

        dateL = new Label();
        dateL.setBounds(new Rectangle(0, 10, 80, 15));
        dateL.setFont(new java.awt.Font("Serif", 1, 10));
        openL = new Label();
        openL.setBounds(new Rectangle(0, 25, 80, 15));
        openL.setFont(new java.awt.Font("Serif", 1, 10));
        lastL = new Label();
        lastL.setBounds(new Rectangle(0, 40, 80, 15));
        lastL.setFont(new java.awt.Font("Serif", 1, 10));
        highL = new Label();
        highL.setBounds(new Rectangle(0, 55, 80, 15));
        highL.setFont(new java.awt.Font("Serif", 1, 10));
        lowL = new Label();
        lowL.setBounds(new Rectangle(0, 70, 80, 15));
        lowL.setFont(new java.awt.Font("Serif", 1, 10));
        volL = new Label();
        volL.setBounds(new Rectangle(0, 85, 80, 15));
        volL.setFont(new java.awt.Font("Serif", 1, 10));
        valuePanel.add(dateL);
        valuePanel.add(openL);
        valuePanel.add(lastL);
        valuePanel.add(highL);
        valuePanel.add(lowL);
        valuePanel.add(volL);

        chartType = new Choice();
        chartType.addItemListener(this);
        chartType.addItem("Bar");
        chartType.addItem("Candle");
        chartType.addItem("Line");

        chartType.setBackground(Color.lightGray);
        buttonPanel.add(chartType);
        chartType.setBounds(2, 2, 70, 20);
        chartType.setFont(new Font("Arial", Font.PLAIN, 10));
        chartType.setBackground(choiceBgColor);

        period = new Choice();
        period.addItemListener(this);
        period.addItem("Daily");
        period.addItem("Weekly");
        period.addItem("Monthly");

//Hung: disable this item because it is not work.....
        period.addItem("Intraday");

        period.setBackground(Color.lightGray);
        buttonPanel.add(period);
//period.setBounds(42,2,36,20);
        period.setBounds(2, 20, 70, 20);
        period.setFont(new Font("Arial", Font.PLAIN, 10));
        period.setBackground(choiceBgColor);

        compare = new Choice();
        compare.addItemListener(this);
        compare.addItem("None");
        compare.addItem("HSI");
        compare.addItem("Red-chips Index");
        compare.addItem("H-shares index");
        compare.addItem("HSIF");
        compare.addItem("GEM Index");
        compare.addItem("Fin. Index");
        compare.addItem("Util. Index");
        compare.addItem("Prop. Index");
        compare.addItem("Com & Ind Index");

        compare.setBackground(Color.lightGray);
        buttonPanel.add(compare);
//              compare.setBounds(2,24,76,20);
        compare.setBounds(2, 38, 70, 20);
        compare.setFont(new Font("Arial", Font.PLAIN, 9));
        compare.setBackground(choiceBgColor);

        updateLabel();
/*
    Thread thread5 = null;
      thread5 = new readThread ();
      thread5.start ();
*/
    }

//Hung: this function should be used to format the correct database Table Name given the stock code
    public void tableName(String s) {
        s = "0000" + s;
        s = s.substring(s.length() - 4);
        table = new String("Day" + s);
        tableIntra = new String("Time" + s);
    }
///////////////////////////////////////////////////////////////////////////////////////////////////////////////


//Hung: this function is used to change the stock code ------------------------------------------->
    public void updateCode(String s) {
        s = s.trim();
        if (s.equals(""))
            return;
        codeID = s;
        tableName(s);
////System.out.println("updateCode" + table + "  " + s );
        if (Ptype < 3)
            chartPanel.getData();
        else
            chartPanel.getIntraData(tableIntra);
////System.out.println("after get Data"+ table + "  " + s );
        chartPanel.plotDraw();
        updateLabel();
        chartPanel.refresh();
    }
///////////////////////////////////////////////////////////////////////////////////////////////////////////////

//Hung: this function is used to update the code information according to the stock code stored in table String
    public void updateLabel() {

        try {
            String srcAddr = table.substring(3) + ".s";
            //String inputLine = RealTimeStock.getRawString(srcAddr, FinetExpress.passcode);
            String[] data = new String[7];

/*      String srcstr = "http://" + ServerAddress + "/stock/" + table.substring (3) + ".s";
      URL index = new URL (srcstr);
//System.out.println(srcstr);
      BufferedReader in =
	new BufferedReader (new InputStreamReader (index.openStream ()));
        String[] data = new String[7];
      String inputLine = in.readLine ();
*/
            //StringTokenizer token = new StringTokenizer (inputLine, ",");
            int i = (int) 0;

            /*while (token.hasMoreTokens ())
            {
          data[i++] = token.nextToken ();
            }*/

        } catch (Exception e) {
        }

    }
//---------------------------------------------------------------------------------------------------------------->


////////////////////////////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////////////[
//Hung: Performs the correct action when the client click the choice ---------------------------------------------->
    public void itemStateChanged(ItemEvent e) {
////System.out.println("itemStateChanged");
        int tmp = 0;

        if (e.getSource() instanceof Choice) {
            String choice = (String) e.getItem();

//Hung: handle the Lchoice: Volume, RSI, Momentum, Stochastic
            if ((choice.equals("Volume")) || (choice.equals(lbArray[1][1]))) {
                LfunctionCard.show(LfTabPanel, "Vol");
                if (Ltype != 0) {
                    Ltype = 0;
                    chartPanel.plotDraw();
                }
            } else if ((choice.equals("RSI")) || (choice.equals(lbArray[2][1]))) {
                LfunctionCard.show(LfTabPanel, "RSI");
                Ltype = 1;
            } else if ((choice.equals("Stochastic")) || (choice.equals(lbArray[3][1]))) {
                LfunctionCard.show(LfTabPanel, "STO");
                Ltype = 2;
            } else if ((choice.equals("Momentum")) || (choice.equals(lbArray[4][1]))) {
                LfunctionCard.show(LfTabPanel, "MOM");
                Ltype = 3;
            }
//Hung: handle the Uchoice: Moving Avg and "---"
            else if ((choice.equals("Moving Avg")) || (choice.equals(lbArray[0][1]))) {
                UfunctionCard.show(UfTabPanel, "MA");
                Utype = 1;
            } else if (choice.equals("---")) {
                UfunctionCard.show(UfTabPanel, "UNone");
                if (Utype != 0) {
                    Utype = 0;
                    chartPanel.plotDraw();
                }
            }
//Hung: handle the period Pchoice: Daily, Weekly, monthly and intraday
            else if ((choice.equals("Daily")) || (choice.equals(lbArray[8][1]))) {
                if (Ptype != 0) {
                    Ptype = 0;
                    chartPanel.dataNum = 185;
                    chartPanel.getData();
                    chartPanel.plotDraw();
                    chartPanel.addLine();
                    chartPanel.refresh();
                }
            } else if ((choice.equals("Weekly")) || (choice.equals(lbArray[9][1]))) {
                if (Ptype != 1) {
                    Ptype = 1;
                    chartPanel.dataNum = 185 * 5;
                    chartPanel.getData();
                    chartPanel.plotDraw();
                    chartPanel.addLine();
                    chartPanel.refresh();
                }
            } else if ((choice.equals("Monthly")) || (choice.equals(lbArray[10][1]))) {
                if (Ptype != 2) {
                    Ptype = 2;
                    chartPanel.dataNum = 185 * 20;
                    chartPanel.getData();
                    chartPanel.plotDraw();
                    chartPanel.addLine();
                    chartPanel.refresh();
                }
            } else if ((choice.equals("Intraday")) || (choice.equals(lbArray[11][1]))) {
                if (Ptype != 3) {
                    Ptype = 3;
                    chartType.select("Line");
                    type = 0;
                    chartPanel.getIntraData(tableIntra);
                    chartPanel.plotDraw();
                    chartPanel.addLine();
                    chartPanel.refresh();
                }
            }
//Hung: handle the plot styles: candle, Line or bar
            else if ((choice.equals("Candle")) || (choice.equals(lbArray[5][1]))) {
                if (type != 2) {
                    if (Ptype != 3) {
                        type = 2;
                        chartPanel.plotType(table);
                        chartPanel.repaint();
                    } else {
                        chartType.select("Line");
                        type = 0;
                    }
                }
            } else if ((choice.equals("Bar")) || (choice.equals(lbArray[6][1]))) {
                if (type != 1) {
                    if (Ptype != 3) {
                        type = 1;
                        chartPanel.plotType(table);
                        chartPanel.repaint();
                    } else {
                        chartType.select("Line");
                        type = 0;
                    }
                }
            } else if ((choice.equals("Line")) || (choice.equals(lbArray[7][1]))) {
                if (type != 0) {
                    type = 0;
                    chartPanel.plotType(table);
                    chartPanel.repaint();
                }
            }

//Hung: handle the compare Line choice -------------------------
            else if ((choice.equals("None")) || (choice.equals(lbArray[12][1]))) {
                tmp = compareLine;
                if (compareLine != 0) {
                    compareLine = 0;
                    chartPanel.updateData();
                    chartPanel.addLine();
                    chartPanel.refresh();
                }

            } else if ((choice.equals("HSI")) || (choice.equals(lbArray[13][1]))) {
                tmp = compareLine;
                if (compareLine != 1) {
                    compareLine = 1;
                    chartPanel.addLine();
//              //System.out.println(tmp);
                    if (tmp == 0)
                        chartPanel.updateData();
                    chartPanel.refresh();
                }
            } else if ((choice.equals("Red-chips Index")) || (choice.equals(lbArray[14][1]))) {
                tmp = compareLine;
                if (compareLine != 2) {
                    compareLine = 2;
                    chartPanel.addLine();
                    if (tmp == 0)
                        chartPanel.updateData();
                    chartPanel.refresh();
                }
            } else if ((choice.equals("H-shares index")) || (choice.equals(lbArray[15][1]))) {
                tmp = compareLine;
                if (compareLine != 3) {
                    compareLine = 3;
                    chartPanel.addLine();
                    if (tmp == 0)
                        chartPanel.updateData();
                    chartPanel.refresh();
                }
            } else if ((choice.equals("H-shares index")) || (choice.equals(lbArray[15][1]))) {
                tmp = compareLine;
                if (compareLine != 3) {
                    compareLine = 3;
                    chartPanel.addLine();
                    if (tmp == 0)
                        chartPanel.updateData();
                    chartPanel.refresh();
                }
            } else if ((choice.equals(lbArray[37][0])) || (choice.equals(lbArray[37][1]))) {
                tmp = compareLine;
                if (compareLine != 4) {
                    compareLine = 4;
                    chartPanel.addLine();
                    if (tmp == 0)
                        chartPanel.updateData();
                    chartPanel.refresh();
                }
            } else if ((choice.equals(lbArray[38][0])) || (choice.equals(lbArray[38][1]))) {
                tmp = compareLine;
                if (compareLine != 5) {
                    compareLine = 5;
                    chartPanel.addLine();
                    if (tmp == 0)
                        chartPanel.updateData();
                    chartPanel.refresh();
                }
            } else if ((choice.equals(lbArray[39][0])) || (choice.equals(lbArray[39][1]))) {
                tmp = compareLine;
                if (compareLine != 6) {
                    compareLine = 6;
                    chartPanel.addLine();
                    if (tmp == 0)
                        chartPanel.updateData();
                    chartPanel.refresh();
                }
            } else if ((choice.equals(lbArray[40][0])) || (choice.equals(lbArray[40][1]))) {
                tmp = compareLine;
                if (compareLine != 7) {
                    compareLine = 7;
                    chartPanel.addLine();
                    if (tmp == 0)
                        chartPanel.updateData();
                    chartPanel.refresh();
                }
            } else if ((choice.equals(lbArray[41][0])) || (choice.equals(lbArray[41][1]))) {
                tmp = compareLine;
                if (compareLine != 8) {
                    compareLine = 8;
                    chartPanel.addLine();
                    if (tmp == 0)
                        chartPanel.updateData();
                    chartPanel.refresh();
                }
            } else if ((choice.equals(lbArray[42][0])) || (choice.equals(lbArray[42][1]))) {
                tmp = compareLine;
                if (compareLine != 9) {
                    compareLine = 9;
                    chartPanel.addLine();
                    if (tmp == 0)
                        chartPanel.updateData();
                    chartPanel.refresh();
                }
            } else if ((choice.equals(lbArray[43][0])) || (choice.equals(lbArray[43][1]))) {
                tmp = compareLine;
                if (compareLine != 10) {
                    compareLine = 10;
                    chartPanel.addLine();
                    if (tmp == 0)
                        chartPanel.updateData();
                    chartPanel.refresh();
                }
            } else if ((choice.equals(lbArray[44][0])) || (choice.equals(lbArray[44][1]))) {
                tmp = compareLine;
                if (compareLine != 11) {
                    compareLine = 11;
                    chartPanel.addLine();
                    if (tmp == 0)
                        chartPanel.updateData();
                    chartPanel.refresh();
                }
            }
        }
///////////////////////////////////////////////////////////////////////////////////////////////////]
////////////////////////////////////////////////////////////////////////////////////////////////////
    }

    public void stop() {
    }


    class readThread extends Thread {

        public readThread() {
            super();
        }

        public void run() {
            connected = 1;
        }
    }
/* -----------------------------------------------------------------jimmy--------------------------------- */
    class DrawPanel extends Panel implements MouseListener, MouseMotionListener {

//    public final static int LINES = 0;
//    public final static  int POINTS = 1;

        int mode = LINES;
        String lastDate = new String("");
        String[] Year = new String[1900];
        String[] Month = new String[1900];
        String[] Day = new String[1900];
        float[] Open = new float[1900];
        float[] High = new float[1900];
        float[] Low = new float[1900];
        float[] Last = new float[1900];
        int[] Volume = new int[1900];
        Vector lines = new Vector();
        Vector colors = new Vector();
        Vector chart = new Vector();
        Vector chartBg = new Vector();
        Vector chartLine = new Vector();
        Vector chartString = new Vector();
        Vector Lchart = new Vector();
        Vector LchartBg = new Vector();
        Vector LchartLine = new Vector();
        Vector LchartString = new Vector();
        Vector compareChart = new Vector();
        Vector compareBg = new Vector();
        Vector compareString = new Vector();
        Vector candle = new Vector();
        Vector UChart = new Vector();

        int dataNum = (int) 185;
        int x1, y1;
        int x2, y2;
        int xl, yl;
        boolean queryError = false;
        RectangleArea rectangle;
        int arrayLength = (int) 0;
        int newArrayLength = (int) 0;
        float maxA = 0f, minA = 0f, minL = 0f;
        float maxVolA = 5f, maxH = 0f; //, tickA = 0f;
        int moreDataNum = (int) 0;
        float[] value;

        public DrawPanel() {
            setBackground(BgColor);
            addMouseMotionListener(this);
            addMouseListener(this);
        }


//Hung: retrieve the stock data for daily, weekly and monthly
        public void getData() {
            String tableName = table;
            moreDataNum = 0;
            maxA = 0f;
            minA = 1000000f;
            minL = 1000000f;
            maxH = 0f;
            int iCount = (int) 0;
            String[] data = new String[2000];
            try {
                URL index =
                        new URL(FinetExpress.getScriptBase() + "chart/getData.php?dateNum=" + dataNum + "&table=" + tableName + "&type=" + Ptype);
                BufferedReader in =
                        new BufferedReader(new InputStreamReader(index.openStream()));
                String inputLine = in.readLine();
//        System.out.println(index);
//        System.out.println(inputLine);
                StringTokenizer token = new StringTokenizer(inputLine, ";");

                while (token.hasMoreTokens()) {
                    data[iCount++] = token.nextToken();
                }

            } catch (IOException e) {
            }

            int i = 0;
            maxVolA = 5f;
            int j = 0;
            arrayLength = 0;

            while (j < iCount - 4) {
                Last[i] = Float.valueOf(data[j++]).floatValue();
                Open[i] = Float.valueOf(data[j++]).floatValue();
                lastDate = data[j++];
                Year[i] = "," + data[j++];
                Month[i] = data[j++];
                Day[i] = data[j++] + " ";
                High[i] = Float.valueOf(data[j++]).floatValue();
                Low[i] = Float.valueOf(data[j++]).floatValue();
                Volume[i] =
                        Math.round(Float.valueOf(data[j++]).floatValue() / 1000.0f);

                if (Last[i] > maxA)
                    maxA = Last[i];
                if (Last[i] < minA)
                    minA = Last[i];
                if (Volume[i] > maxVolA)
                    maxVolA = Volume[i];
                // smoothening the curve
                if (Low[i] == 0f)
                    Low[i] = Last[i] * 0.95f;
                if (High[i] == 0f)
                    High[i] = Last[i] * 1.05f;
                if (Open[i] == 0f)
                    Open[i] = Last[i];
                // end smoothening the curve
                if (Low[i] < minL && Low[i] > 0f)
                    minL = Low[i];
                if (High[i] > maxH)
                    maxH = High[i];

                i++;
            }				// end while

            arrayLength = i;

            if (type == 0) {
                PlotChart(tableName, maxA, minA);
            } else {
                PlotChart(tableName, maxH, minL);
            }

        }

        public void getIntraData(String table) {
            String[] data = new String[10];
            String[] strConstant = new String[10];

            maxA = 0f;
            minA = 1000000f;

            int iCount = (int) 0;
            int[] vol = new int[300];

            try {
                String stringAddr = FinetExpress.getScriptBase() + "chart/getIntra.php?table=" + tableIntra;
                URL index = new URL(stringAddr);
                BufferedReader in =
                        new BufferedReader(new InputStreamReader(index.openStream()));

                String inputLine = in.readLine();
//	  System.out.println (inputLine);

                StringTokenizer token1 = new StringTokenizer(inputLine, ";");
                for (int i = 0; token1.hasMoreTokens(); i++)
                    strConstant[i] = token1.nextToken();

                inputLine = in.readLine();
//        System.out.println (inputLine);

                StringTokenizer token = new StringTokenizer(inputLine, ";");
                data = new String[9 * 130];

                while (token.hasMoreTokens() && iCount < 9 * 130) {
                    data[iCount++] = token.nextToken();
                    //System.out.println("data["+ (a-1) +" ]="+data[a-1]);
                }
            } catch (IOException e) {
                //System.out.println("Error when retrieve the data....." + e);
            }

            int i = 0;
            maxVolA = 5f;
            int j = 0;
            arrayLength = 0;
            lastDate = strConstant[0];
            while (j < iCount - 4 && i < 130) {
                Open[i] = Float.valueOf(strConstant[1]).floatValue();
                High[i] = Float.valueOf(strConstant[2]).floatValue();
                Low[i] = Float.valueOf(strConstant[3]).floatValue();
                Year[i] = ":00";

                Last[i] = Float.valueOf(data[j++]).floatValue();
                Day[i] = data[j].substring(0, 3);
                Month[i] = " " + data[j].substring(3, 6) + " ";
                j++;
                vol[i] = Math.round(Float.valueOf(data[j++]).floatValue() / 1000.0f);
                if (i > 0) {
                    Volume[i - 1] = vol[i - 1] - vol[i];
                    if (Volume[i - 1] > maxVolA)
                        maxVolA = Volume[i - 1];
                }
                if (Last[i] > maxA)
                    maxA = Last[i];
                if (Last[i] < minA)
                    minA = Last[i];

                i++;
            }

            arrayLength = i;

            PlotChart(table, maxA, minA);

        }

        public void getMoreData(int more) {
            String[] data = new String[10];
            int i = arrayLength;
            int iCount = 0;

            try {
                URL index =
                        new URL(FinetExpress.getScriptBase() + "chart/getMoreData.php?lastDate=" + lastDate +
                        "&table=" + table.substring(3) + "&more=" + more + "&type=" + Ptype);

                BufferedReader in = new BufferedReader(new InputStreamReader(index.openStream()));
                String inputLine = in.readLine();
//                //System.out.println(inputLine) ;

                StringTokenizer token = new StringTokenizer(inputLine, ";");
                data = new String[1000];

                while (token.hasMoreTokens() && iCount < 1000) {
                    data[iCount++] = token.nextToken();
                }
            } catch (IOException e) {
            }

            int j = 0;

            while (j < iCount - 1 && i < arrayLength + more) {
                Last[i] = Float.valueOf(data[j++]).floatValue();
                Open[i] = Float.valueOf(data[j++]).floatValue();

                High[i] = Float.valueOf(data[j++]).floatValue();
                Low[i] = Float.valueOf(data[j++]).floatValue();

                if (Low[i] == 0f)
                    Low[i] = Last[i] * 0.95f;
                if (High[i] == 0f)
                    High[i] = Last[i] * 1.05f;
                if (Open[i] == 0f)
                    Open[i] = Last[i - 1];

                i++;
            }

            newArrayLength = i;
        }


        public void PlotChart(String tableName, float max, float min) {
            float tick;
            if (max < 0.25)
                tick = 0.001f;
            else if (max < 0.5)
                tick = 0.005f;
            else if (max < 2.0)
                tick = 0.010f;
            else if (max < 5.0)
                tick = 0.025f;
            else if (max < 30.0)
                tick = 0.050f;
            else if (max < 50.0)
                tick = 0.100f;
            else if (max < 100.0)
                tick = 0.250f;
            else if (max < 200.0)
                tick = 0.500f;
            else if (max < 1000.0)
                tick = 1.000f;
            else if (max < 9995.0)
                tick = 2.500f;
            else
                tick = 5.000f;
            min = min - tick;
            max = max + tick;
/*
+----------+----------+-------+
| start    | end      | tick  |
+----------+----------+-------+
|    0.010 |    0.250 | 0.001 |
|    0.250 |    0.500 | 0.005 |
|    0.500 |    2.000 | 0.010 |
|    2.000 |    5.000 | 0.025 |
|    5.000 |   30.000 | 0.050 |
|   30.000 |   50.000 | 0.100 |
|   50.000 |  100.000 | 0.250 |
|  100.000 |  200.000 | 0.500 |
|  200.000 | 1000.000 | 1.000 |
| 1000.000 | 9995.000 | 2.500 |
+----------+----------+-------+
*/
            chart.removeAllElements();
            chartBg.removeAllElements();
            chartString.removeAllElements();
            chartLine.removeAllElements();
            candle.removeAllElements();

            int num = (int) ((max - min) / tick);
            System.out.println("max: " + max + " min: " + min + " tick: " + tick + " num: " + num);

            min = (float) Math.round(min * 1000.0f) / 1000.0f; // round up min to 3 decimal

            if (num > 6) {
                while (num >= 5) {
                    tick *= 2;
                    num = (int) ((max - min) / tick);
                }
            }

            if (compareLine != 0)
                translate = (int) -40;
            else
                translate = (int) 0;

            int X = (int) 370 + translate;

            int Y1, Y2 = 0;   // 2 Y cordinates for drawing bar and candle
            float yRatio = 140.0f / (max - min);

            String tempYear = new String("");
            for (int i = 0; i < arrayLength; i++) {
                if (type == 0) {
                    if (i != arrayLength - 1) {
                        Y1 = (int) ((max - Last[i + 1]) * yRatio);
                        Y2 = (int) ((max - Last[i]) * yRatio);
                        chart.addElement(new Rectangle(X + 3, Y2, X, Y1));
                    }
                } else if (type == 1) // Bar chart
                {
                    Y1 = (int) ((max - High[i]) * yRatio);
                    Y2 = (int) ((max - Low[i]) * yRatio);
                    chart.addElement(new Rectangle(X, Y2, X, Y1));
                    Y1 = (int) ((max - Last[i]) * yRatio);
                    chart.addElement(new Rectangle(X, Y1, X + 2, Y1));
                } else if (type == 2) // Candle
                {
                    Y1 = (int) ((max - High[i]) * yRatio);
                    Y2 = (int) ((max - Low[i]) * yRatio);
                    chart.addElement(new Rectangle(X + 1, Y2, X + 1, Y1));
                    Y1 = (int) ((max - Last[i]) * yRatio);
                    Y2 = (int) ((max - Open[i]) * yRatio);

                    if (Y1 > Y2) {
                        candle.addElement(new Rectangle(X, Y2, 3, Y1 - Y2));
                        candle.addElement(Color.blue);
                    } else {
                        candle.addElement(new Rectangle(X, Y1, 3, Y2 - Y1));
                        candle.addElement(BgColor);
                    }
                }

                if (i % 15 == 0) {
                    chartBg.addElement(new Rectangle(-1, -1, X - 6, 198));
                    chartString.addElement(new String(Day[i] + Month[i].substring(0, 3)));
                    if (Ptype != 3 && !tempYear.equals(Year[i].substring(1))) {
                        tempYear = Year[i].substring(1);
                        chartBg.addElement(new Rectangle(-1, -1, X - 4, 205));
                        chartString.addElement(new String(Year[i].substring(1)));
                    }
                    chartBg.addElement(new Rectangle(-1, -1, X + 2, 190));
                    chartString.addElement(new String("|"));
                    chartLine.addElement(new Rectangle(X + translate - 3, 0, X + translate - 3, 198));
                }
                X -= 3;
            }
            x2 = x1 = -1;

            int Y;
            for (int i = 0; i <= num; i++) {
                Y = (int) ((i * tick) * yRatio);
                chartBg.addElement(new Rectangle(-1, -1, 379, 148 - Y));
                chartBg.addElement(new Rectangle(-1, -1, 376, 148 - Y));
                chartLine.addElement(new Rectangle(0 + translate, 140 - Y, ((int) 375 + translate), 140 - Y));
                chartString.addElement(new String(Float.toString((float) Math.round((min + i * tick) * 1000.0f) / 1000.0f)));
                chartString.addElement(new String("-"));
            }

        } // end method PlotChart

        public void PlotChart(String tableName, int max) {
            Lchart.removeAllElements();
            LchartBg.removeAllElements();
            LchartString.removeAllElements();
            LchartLine.removeAllElements();

            int maxT = max;
            int maxTT;
            int numI = (int) 0;

            while (maxT >= 10) {
                maxT = Math.round((float) maxT / 10.0f);
                numI++;
            }

            maxTT = maxT;
            maxT = (int) Math.pow((double) 10.0, (double) numI) * maxT;

            while (maxT < max)
                maxT = maxT + (int) Math.pow((double) 10.0, (double) numI - 1);

            int interval;

            interval = (int) Math.pow((double) 10.0, (double) numI);
            while (maxTT >= 4) {
                maxTT = (int) Math.round(maxTT / 2);
                interval = interval * 2;
            }

            if (maxTT == 1) {
                maxTT = maxTT * 2;
                interval = interval / 2;
            }

            if (compareLine != 0)
                translate = (int) -40;
            else
                translate = (int) 0;

            int X = (int) 370 + translate;
            int Y = (int) 0;

            Lchart.addElement(Color.black);
            int i;

            for (i = 0; i < arrayLength; i++) {
                Y = Volume[i];
                Y = Math.round((float) Y * (40f / (float) maxT));
                Lchart.addElement(new Rectangle(X, 180, X, 180 - Y));
                x2 = x1 = -1;
                X = X - 3;
            }

            for (int a = 0; a <= maxTT; a++) {
                Y = Math.round((a * interval) * (40f / maxT));
                LchartBg.addElement(new Rectangle(-1, -1, 379, 188 - Y));
                LchartString.addElement(new String(Integer.toString(Math.round(a * interval))));
                LchartBg.addElement(new Rectangle(-1, -1, 376, 188 - Y));
                LchartString.addElement(new String("-"));
                LchartLine.addElement(new Rectangle(0 + translate, 180 - Y,
                        ((int) 375 + translate), 180 - Y));
            }
//              //System.out.println(maxTT +"   " + interval);
        }


        public void addLine() {
//System.out.println("addLine called");

            compareChart.removeAllElements();
            compareBg.removeAllElements();
            compareString.removeAllElements();

            String tableName = new String("");
            if (compareLine == 0)
                tableName = new String("None");
            else if (compareLine == 1)
                tableName = new String("Day5001");
            else if (compareLine == 2)
                tableName = new String("Day5006");
            else if (compareLine == 3)
                tableName = new String("Day5007");
            else if (compareLine == 4)
                tableName = new String("Day5051");
            else if (compareLine == 5)
                tableName = new String("Day5020");
            else if (compareLine == 6)
                tableName = new String("Day5002");
            else if (compareLine == 7)
                tableName = new String("Day5003");
            else if (compareLine == 8)
                tableName = new String("Day5004");
            else if (compareLine == 9)
                tableName = new String("Day5005");
            else if (compareLine == 10)
                tableName = new String("Day2800");
            else if (compareLine == 11)
                tableName = new String("Day5008");

            if (tableName.equals("None")) {
                setBounds(5, 5, 370, 180);
                return;
            }

            if (Ptype == 3)
                tableName = new String("Time" + tableName.substring(3));

            chartPanel.setBounds(45, 5, 330, 180);
            rectangle.repaint();

            float max = (float) 0.0, min = (float) 100000.0;
            float tick = (float) 100.0;

            compareChart.removeAllElements();
            compareBg.removeAllElements();
            compareString.removeAllElements();

            String[] data = new String[10];

            int iCount = 0;
            URL index;

            try {
                if (Ptype != 3) {
//	  index = new URL (FinetExpress.getScriptBase() + "chart/getIndex.php?dateNum=" + dataNum +
//		     "&table=" + EncodeSpace (tableName) + "&type=" + Ptype);
                    index = new URL(FinetExpress.getScriptBase() + "chart/getIndex.php?dateNum=" + dataNum +
                            "&table=" + tableName + "&type=" + Ptype);
//	    System.out.println(index.toString());
                } else {

                    if (compareLine == 1)
                        tableName = new String("Time5001");
                    else if (compareLine == 2)
                        tableName = new String("Time5006");
                    else if (compareLine == 3)
                        tableName = new String("Time5007");
                    else if (compareLine == 4)
                        tableName = new String("Time5051");
                    else if (compareLine == 5)
                        tableName = new String("Time5020");
                    else if (compareLine == 6)
                        tableName = new String("Time5002");
                    else if (compareLine == 7)
                        tableName = new String("Time5003");
                    else if (compareLine == 8)
                        tableName = new String("Time5004");
                    else if (compareLine == 9)
                        tableName = new String("Time5005");
                    else if (compareLine == 10)
                        tableName = new String("Time2800");
                    else if (compareLine == 11)
                        tableName = new String("Time5008");



//      index = new URL (FinetExpress.getScriptBase() + "chart/getIndex.php?table=" +
//		     EncodeSpace (tableName) + "&type=" + Ptype);
                    index = new URL(FinetExpress.getScriptBase() + "chart/getIndex.php?table=" +
                            tableName + "&type=" + Ptype);
                }

                BufferedReader in = new BufferedReader(new InputStreamReader(index.openStream()));
                String inputLine = in.readLine();
//                //System.out.println(inputLine) ;

                StringTokenizer token = new StringTokenizer(inputLine, ";");
                data = new String[1000];
                int end = (int) 0;
                iCount = (int) 0;
                float tmp;
                while (token.hasMoreTokens() && iCount < 1000) {
                    data[iCount++] = token.nextToken();
                }
            } catch (IOException e) {
            }

            if (compareLine != 0)
                translate = (int) -40;
            else
                translate = (int) 0;

            int XX = 370 + translate - 3;
            int i = 0;
            int j = 0;
            float[] LastC = new float[1900];
            int ilength;

            while (j < iCount - 1 && XX >= 0) {
                LastC[i] = Float.valueOf(data[j++]).floatValue();

                if (LastC[i] > max)
                    max = LastC[i];
                if (LastC[i] < min)
                    min = LastC[i];

                XX = XX - 3;
                i++;

            }
            ilength = i;

            Float temp = new Float((max - min) / tick);
            int num = temp.intValue();
//      //System.out.println(max+"   " + min );
            min = (float) Math.round(min * 1000.0f) / 1000.0f;

            if (num == 1) {
                num = 2;
                tick = tick / 2;
            } else if (num > 6) {
                while (num >= 5) {
                    tick = tick * (float) 2;
                    temp = new Float((max - min) / tick);
                    num = temp.intValue();
                }
            }

            Float Y = new Float(0.0);

            int pX = (int) 370 + translate;
            int X = pX;

            Float pY = new Float(0.0);

            pY = new Float(LastC[0]);

            pY = new Float((pY.floatValue() - min) * ((float) 140.0 / (max - min)));

            for (i = 1; i < ilength; i++) {
                X = pX - 3;
                Y = new Float(LastC[i]);
                Y = new Float((Y.floatValue() - min) * ((float) 140.0 / (max - min)));
                compareChart.addElement(new Rectangle(pX, 140 - pY.intValue(), X,
                        140 - Y.intValue()));
                x2 = x1 = -1;
                pY = Y;
                pX = X;
            }

////System.out.println(num);
            for (i = 0; i <= num; i++) {
                Y = new Float((i * tick) * ((float) 140.0 / (max - min)));
                compareBg.addElement(new Rectangle(-1, -1, 6, 148 - Y.intValue()));
                compareString.addElement(new String(Float.toString((float) Math.
                        round((min + i * tick) * 1000.0f) /
                        1000.0f)));
                compareBg.addElement(new Rectangle(-1, -1, 42, 148 - Y.intValue()));
                compareString.addElement(new String("-"));
            }
        }

        public void paint(Graphics g) {
/*      if (connected == 0)
	drawString (g, "Loading Data........", 50, 50);
      else if (connected == 2)
	drawString (g, "Please Reload Again ...... ", 50, 50);
*/
            // Plotting chartLine
            int iCount = chartLine.size();
            for (int i = 0; i < iCount; i++) {
                Rectangle pp = (Rectangle) chartLine.elementAt(i);
                g.setColor(grey);
                g.drawLine(pp.x, pp.y, pp.width, pp.height);
            }

            //  Plotting LchartLine
            iCount = LchartLine.size();
            for (int i = 0; i < iCount; i++) {
                Rectangle pp = (Rectangle) LchartLine.elementAt(i);
                if (i == 0)
                    g.setColor(FontColor);
                else
                    g.setColor(grey);
                g.drawLine(pp.x, pp.y, pp.width, pp.height);
            }

            // draw the current lines
            // Plotting lines
            g.setColor(getForeground());
            g.setPaintMode();
            iCount = lines.size();
            for (int i = 0; i < iCount; i++) {
                Rectangle p = (Rectangle) lines.elementAt(i);
                g.setColor((Color) colors.elementAt(i));
                if (p.width != -1)
                    g.drawLine(p.x, p.y, p.width, p.height);
                else
                    g.drawLine(p.x, p.y, p.x, p.y);
            }

            // Plotting chart
            int e = (int) 0;
            iCount = chart.size();
            for (int i = 0; i < iCount; i++) {
                Rectangle p1 = (Rectangle) chart.elementAt(i);
                g.setColor(Color.blue);
                if (p1.width != -1)
                    g.drawLine(p1.x, p1.y, p1.width, p1.height);
                else
                    g.drawLine(p1.x, p1.y, p1.x, p1.y);
                if (type == 2) {
                    Rectangle pa = (Rectangle) candle.elementAt(e++);
                    Color ca = (Color) candle.elementAt(e++);
                    g.setColor(ca);
                    g.fillRect(pa.x, pa.y, pa.width, pa.height);
                    g.setColor(Color.blue);
                    g.drawRect(pa.x, pa.y, pa.width, pa.height);
                }
            }

            // Plotting the compareChart
            Rectangle p1;
            iCount = compareChart.size();
            for (int i = 0; i < iCount; i++) {
                p1 = (Rectangle) compareChart.elementAt(i);
                if (i == 0)
                    g.setColor(Color.red);
                g.drawLine(p1.x, p1.y, p1.width, p1.height);
            }

            // Plotting the Lchart
            iCount = Lchart.size();
            if (iCount != 0)
                g.setColor((Color) Lchart.elementAt(0));
            for (int i = 1; i < iCount; i++) {
                p1 = (Rectangle) Lchart.elementAt(i);
                if (p1.x == -1 && p1.y == -1 && p1.width == -1 && p1.height == -1)
                    g.setColor(Color.red);
                else
                    g.drawLine(p1.x, p1.y, p1.width, p1.height);
            }

            g.setColor(MA1.getForeground());

            // Plotting the UChart
            iCount = UChart.size();
            for (int i = 0; i < iCount; i++) {
                Rectangle pU = (Rectangle) UChart.elementAt(i);
                if (pU.x == -1 && pU.y == -1 && pU.width == -1 && pU.height == -1)
                    g.setColor(MA2.getForeground());
                else
                    g.drawLine(pU.x, pU.y, pU.width, pU.height);
            }

            if (mode != -1 && mode == LINES) {
                g.setXORMode(getBackground());
                if (xl != -1) {
/* erase the last line. */
                    g.drawLine(x1, y1, xl, yl);
                }
                g.setColor(getForeground());
                g.setPaintMode();
                if (x2 != -1) {
                    g.drawLine(x1, y1, x2, y2);
                }
            }

        }

        public void drawIndex(Graphics g) {
            int iCount = chartBg.size();
            for (int i = 0; i < iCount; i++) {
                Rectangle p1 = (Rectangle) chartBg.elementAt(i);
                g.setColor(Color.black);
                if (p1.x == -1 && p1.y == -1) {
                    String sp = (String) chartString.elementAt(i);
                    if (!(compareLine != 0 && p1.width < 40))
                        g.drawString(sp, p1.width, p1.height);
                }
            }

            iCount = LchartBg.size();
            for (int i = 0; i < iCount; i++) {
                Rectangle p1 = (Rectangle) LchartBg.elementAt(i);
                g.setColor(Color.black);
                if (p1.x == -1 && p1.y == -1) {
                    String sp = (String) LchartString.elementAt(i);
                    g.drawString(sp, p1.width, p1.height);
                }
            }

            iCount = compareBg.size();
            for (int i = 0; i < iCount; i++) {
                Rectangle p1 = (Rectangle) compareBg.elementAt(i);
                g.setColor(Color.black);
                if (p1.x == -1 && p1.y == -1) {
                    String sp = (String) compareString.elementAt(i);
                    g.drawString(sp, p1.width, p1.height);
                }
            }

        }

        public void plotType(String tableName) {

            chart.removeAllElements();
            chartBg.removeAllElements();
            chartString.removeAllElements();
            chartLine.removeAllElements();
            candle.removeAllElements();
////System.out.println(maxA+"  "+ minA+"  " + tickA+"  "+maxH);
            if (type == 0) {
//	PlotChart (tableName, maxA, minA - tickA, tickA);
                PlotChart(tableName, maxA, minA);
            } else {
//	PlotChart (tableName, maxH, minA - tickA, tickA);
                PlotChart(tableName, maxH, minA);
            }

        }

        public void updateData() {
            Vector chart1;
            Rectangle pp;
            int b, c;

            if (compareLine != 0)
                translate = (int) -40;
            else
                translate = (int) +40;

            chart1 = new Vector();
            b = chart.size();
            for (c = 0; c < b; c++) {
                pp = (Rectangle) chart.elementAt(c);
                chart1.addElement(new Rectangle(pp.x + translate, pp.y, pp.width + translate, pp.height));
            }
            chart = chart1;

            chart1 = new Vector();
            b = chartLine.size();
            for (c = 0; c < b; c++) {
                pp = (Rectangle) chartLine.elementAt(c);
                chart1.addElement(new Rectangle(pp.x + translate, pp.y, pp.width + translate, pp.height));
            }
            chartLine = chart1;

            chart1 = new Vector();
            b = Lchart.size();
            if (b != 0)
                chart1.addElement((Color) Lchart.elementAt(0));
            for (c = 1; c < b; c++) {
                pp = (Rectangle) Lchart.elementAt(c);
                chart1.addElement(new Rectangle(pp.x + translate, pp.y, pp.width + translate,
                        pp.height));
            }
            Lchart = chart1;

            chart1 = new Vector();
            b = LchartLine.size();
            for (c = 0; c < b; c++) {
                pp = (Rectangle) LchartLine.elementAt(c);
                chart1.addElement(new Rectangle(pp.x + translate, pp.y, pp.width + translate,
                        pp.height));
            }
            LchartLine = chart1;

            chart1 = new Vector();
            Color ca;
            b = candle.size();

            for (c = 0; c < b; c++) {
                pp = (Rectangle) candle.elementAt(c++);
                ca = (Color) candle.elementAt(c);
                chart1.addElement(new Rectangle(pp.x + translate, pp.y, pp.width,
                        pp.height));
                chart1.addElement(ca);
            }
            candle = chart1;

            chart1 = new Vector();
            b = UChart.size();
            for (c = 0; c < b; c++) {
                pp = (Rectangle) UChart.elementAt(c);
                if (pp.x != -1 && pp.y != -1 && pp.width != -1)
                    chart1.addElement(new Rectangle(pp.x + translate, pp.y,
                            pp.width + translate, pp.height));
                else
                    chart1.addElement(new Rectangle(pp.x, pp.y, pp.width, pp.height));
            }
            UChart = chart1;

        }

        public void PlotFChart(float max, float min, int more, int mode) {
            Float Y = new Float(0.0);

            if (compareLine != 0)
                translate = (int) -40;
            else
                translate = (int) 0;

            int pX = (int) 370 + translate;
            int X = pX;
            int tmpY = 0;

            Float pY = new Float(0.0);

            if (mode == 0) {
                pY = new Float(getMA(0, more));
                tmpY = (int) 140;
            } else if (mode == 1) {
                value = new float[arrayLength];
                Lchart.removeAllElements();
                LchartBg.removeAllElements();
                LchartString.removeAllElements();
                LchartLine.removeAllElements();

                max = 0f;
                min = 1000f;

                for (int i = 0; i < arrayLength && Last[i + more] != 0; i++) {
                    value[i] = getRSI(i, more);
                    if (value[i] > max)
                        max = value[i];
                    if (value[i] < min)
                        min = value[i];
                }
                pY = new Float(value[0]);
                tmpY = (int) 40;
                Lchart.addElement((Color) RSI1.getForeground());
            } else if (mode == 2) {
//         value = new float[arrayLength + (int)max + 2];
                value = new float[1000];
                Lchart.removeAllElements();
                LchartBg.removeAllElements();
                LchartString.removeAllElements();
                LchartLine.removeAllElements();

////System.out.println(max);
                int max1 = (int) max;
                max = 0f;
                min = 1000f;

                for (int i = 0; i < arrayLength + max1 && Last[i + more] != 0; i++) {
                    value[i] = getSTO(i, more);
                    if (value[i] > max)
                        max = value[i];
                    if (value[i] < min)
                        min = value[i];
                }

                pY = new Float(value[0]);
                tmpY = (int) 40;
                Lchart.addElement(RSI1.getForeground());

                STOmax = max;
                STOmin = min;

            } else if (mode == 3) {
                float tmp1 = 0f;
                int g;
                for (g = 0; g < arrayLength && value[g + more] != 0; g++) {
                    tmp1 = 0f;
                    for (int j = g; j < g + more; j++) {
                        tmp1 = tmp1 + value[j];
                    }
                    value[g] = tmp1 / (float) more;
                    //System.out.println(g+"  " +more+"  " +value[g]);
                }

                //System.out.println(g+"  " +value[1]);

                pY = new Float(value[0]);
                tmpY = (int) 40;
                max = STOmax;
                min = STOmin;
            } else if (mode == 4) {

                value = new float[arrayLength];
                Lchart.removeAllElements();
                LchartBg.removeAllElements();
                LchartString.removeAllElements();
                LchartLine.removeAllElements();

                max = 0f;
                min = 1000f;

                for (int i = 0; i < arrayLength && Last[i + more] != 0; i++) {
                    value[i] = getMOM(i, more);
                    if (value[i] > max)
                        max = value[i];
                    if (value[i] < min)
                        min = value[i];
                }
                pY = new Float(value[0]);
                tmpY = (int) 40;
                Lchart.addElement((Color) MOM1.getForeground());
            }

            pY = new Float((pY.floatValue() - min) * ((float) tmpY / (max - min)));
////System.out.println(tmpY);
            for (int i = 1; i < arrayLength && (i + more) < newArrayLength; i++) {
                X = pX - 3;
                if (mode == 0)
                    Y = new Float(getMA(i, more));
                else if (mode >= 1)
                    Y = new Float(value[i]);

                Y = new Float((Y.floatValue() - min) * (tmpY / (max - min)));

                if (mode == 0)
                    UChart.addElement(new Rectangle(pX, tmpY - pY.intValue(), X,
                            tmpY - Y.intValue()));
                else if (mode >= 1)
                    Lchart.addElement(new Rectangle(pX, 180 - pY.intValue(), X,
                            180 - Y.intValue()));

                pX = X;
                pY = Y;

//              //System.out.println(pX+"   "+pY);
            }

            if (mode == 1 || mode == 2 || mode == 4) {
                float tick = 0.05f;

                Float temp = new Float((max - min) / tick);
                int num = temp.intValue();


//min = (float) Math.round(min * 1000.0f) / 1000.0f ;
                min = (float) Math.round(min * 1000000.0f) / 1000000.0f;
                if (num == 1) {
                    num = 2;
                } else if (num > 4) {
                    while (num >= 3) {
                        tick = tick * (float) 2;
                        temp = new Float((max - min) / tick);
                        num = temp.intValue();
                    }
                }

                for (int a = 0; a <= num; a++) {
                    Y = new Float((a * tick) * (40f / (max - min)));
                    LchartBg.addElement(new Rectangle(-1, -1, 379, 188 - Y.intValue()));
                    LchartString.addElement(new String(Float.toString((float) Math.
                            round((min + a * tick) * 100.0f) / 100.0f)));
                    LchartBg.addElement(new Rectangle(-1, -1, 376, 188 - Y.intValue()));
                    LchartString.addElement(new String("-"));
                    LchartLine.addElement(new Rectangle(0 + translate, 180 - Y.intValue(),
                            375 + translate, 180 - Y.intValue()));
                }
            }
        }

        public float getMA(int a, int MA) {
            float avg = 0f;
            for (int i = 0; i < MA; i++) {
                avg = avg + Last[a + i];
            }
            return (avg / (float) MA);
        }

        public float getRSI(int a, int RSI) {

            float avg = 0f;
            float up = 0f;
            float down = 0f;

            for (int i = 0; i < RSI; i++) {
                if (Last[a + i + 1] > Last[a + i])
                    down = down + Last[a + i + 1] - Last[a + i];
                else
                    up = up + Last[a + i] - Last[a + i + 1];
            }

            up = up / (float) RSI;
            down = down / (float) RSI;
            return (100 * up / (up + down));
        }

        public float getSTO(int a, int STO) {
            float high = 0f;
            float low = 100000f;

            for (int i = 0; i < STO; i++) {
                if (High[a + i] > high)
                    high = High[a + i];
                if (Low[a + i] < low)
                    low = Low[a + i];
            }

            return (100 * ((Last[a] - low) / (high - low)));
        }

        public float getMOM(int a, int MOM) {

            return (Last[a] - Last[a + MOM]);

        }

        public void plotDraw() {
            if (Utype == 0) {
                UChart.removeAllElements();
                MAT1.setText("");
                MAT2.setText("");
            }

            if (Utype == 1) {
                int MAnum1, MAnum2, RSInum;
                try {
                    MAnum1 = Integer.valueOf(MAT1.getText()).intValue();
                } catch (NumberFormatException a) {
                    MAnum1 = (int) 0;
                }

                try {
                    MAnum2 = Integer.valueOf(MAT2.getText()).intValue();
                } catch (NumberFormatException a) {
                    MAnum2 = (int) 0;
                }

                UChart.removeAllElements();
                if (MAnum1 == 0 && MAnum2 == 0) {
                    repaint();
                    return;
                }

                if (MAnum1 > moreDataNum || MAnum2 > moreDataNum)
                    if (MAnum1 > MAnum2) {
                        moreDataNum = MAnum1;
                        getMoreData(MAnum1);
                    } else {
                        moreDataNum = MAnum2;
                        getMoreData(MAnum2);
                    }

                if (MAnum1 > 0 && MAnum1 <= 999)
                    PlotFChart(maxA, minA, MAnum1, 0);
                if (MAnum2 > 0 && MAnum2 <= 999) {
                    UChart.addElement(new Rectangle(-1, -1, -1, -1));
                    PlotFChart(maxA, minA, MAnum2, 0);
                }
            }

            if (Ltype == 0) {
                Lchart.removeAllElements();
                PlotChart(table, Math.round(maxVolA));
                RSIT.setText("");
                STOT1.setText("");
                STOT2.setText("");
                MOMT.setText("");

            }

            if (Ltype == 1) {
                int RSInum;
                try {
                    RSInum = Integer.valueOf(RSIT.getText()).intValue();
                } catch (NumberFormatException a) {
                    RSInum = (int) 0;
                }

                if (RSInum == 0) {
                    repaint();
                    return;
                }
                Lchart.removeAllElements();

                if (RSInum > moreDataNum) {
                    moreDataNum = RSInum;
                    getMoreData(RSInum);
                }

                if (RSInum > 0 && RSInum <= 999)
                    PlotFChart(0, 0, RSInum, 1);

            }

            if (Ltype == 2) {
                int STOnum1, STOnum2;
                try {
                    STOnum1 = Integer.valueOf(STOT1.getText()).intValue();
                } catch (NumberFormatException a) {
                    STOnum1 = (int) 0;
                }

                try {
                    STOnum2 = Integer.valueOf(STOT2.getText()).intValue();
                } catch (NumberFormatException a) {
                    STOnum2 = (int) 0;
                }

                if (STOnum1 == 0) {
                    repaint();
                    return;
                }

                Lchart.removeAllElements();
                if (STOnum1 + STOnum1 * STOnum2 > moreDataNum) {
                    moreDataNum = STOnum1 + STOnum1 * STOnum2;
                    getMoreData(STOnum1 + STOnum1 * STOnum2);
                }

//              //System.out.println("STO" + STOnum1 + STOnum2);
                if (STOnum1 > 0 && STOnum1 <= 999) {
                    PlotFChart((float) STOnum2, 0, STOnum1, 2);
                }
                if (STOnum2 > 0 && STOnum2 <= 999) {
                    Lchart.addElement(new Rectangle(-1, -1, -1, -1));
                    PlotFChart(0, 0, STOnum2, 3);
                }
            }

            if (Ltype == 3) {
                int MOMnum;
                try {
                    MOMnum = Integer.valueOf(MOMT.getText()).intValue();
                } catch (NumberFormatException a) {
                    MOMnum = (int) 0;
                }

                Lchart.removeAllElements();
                if (MOMnum == 0) {
                    repaint();
                    return;
                }

                if (MOMnum > moreDataNum) {
                    moreDataNum = MOMnum;
                    getMoreData(MOMnum);
                }

                if (MOMnum > 0 && MOMnum <= 400)
                    PlotFChart(0, 0, MOMnum, 4);
            }

            refresh();
        }

        public void displayInfo(int ex) {

            if (compareLine != 0)
                translate = (int) -40;
            else
                translate = (int) 0;

            double factor = Utilities.GetFactor();
            int x = (int) Math.round(((370 * factor + translate * factor - ex)) / factor / 3);

            if (x >= 0 && x < arrayLength && ex > 0
                    && (ex <= (370 * factor + translate * factor))) {
                dateL.setText(Day[x] + Month[x].substring(0, 3) + Year[x]);
                /////////////////////////////////////////////////////////////////////////////////////////////////
                ////////////////////////////////////////////////////////////////////////////////////////////////[
                if (languagenow == FinetExpress.constEnglish) {
                    openL.setText(lbArray[16][0] + " = " + (float) Math.round(Open[x] * 1000.0f) / 1000.0f);
                    lastL.setText(lbArray[17][0] + " = " + (float) Math.round(Last[x] * 1000.0f) / 1000.0f);
                    highL.setText(lbArray[18][0] + " = " + (float) Math.round(High[x] * 1000.0f) / 1000.0f);
                    lowL.setText(lbArray[19][0] + " = " + (float) Math.round(Low[x] * 1000.0f) / 1000.0f);
                    volL.setText(lbArray[20][0] + " = " + Volume[x] + " K");
                } else if (languagenow == FinetExpress.constChinese) {
                    openL.setText(lbArray[16][1] + " = " + (float) Math.round(Open[x] * 1000.0f) / 1000.0f);
                    lastL.setText(lbArray[17][1] + " = " + (float) Math.round(Last[x] * 1000.0f) / 1000.0f);
                    highL.setText(lbArray[18][1] + " = " + (float) Math.round(High[x] * 1000.0f) / 1000.0f);
                    lowL.setText(lbArray[19][1] + " = " + (float) Math.round(Low[x] * 1000.0f) / 1000.0f);
                    volL.setText(lbArray[20][1] + " = " + Volume[x] + " K");
                }
                ////////////////////////////////////////////////////////////////////////////////////////////////]
                /////////////////////////////////////////////////////////////////////////////////////////////////
            }
        }

        public void mouseDragged(MouseEvent e) {
            e.consume();
            double factor = Utilities.GetFactor();
            displayInfo(e.getX());
            switch (mode) {
                case LINES:
                    xl = x2;
                    yl = y2;
                    x2 = (int) Math.round(e.getX() / factor);
                    y2 = (int) Math.round(e.getY() / factor);
                    break;
                case -1:
                    break;
                case POINTS:
                default:
                    colors.addElement(getForeground());
                    lines.addElement(new Rectangle(x1, y1, (int) Math.round(e.getX() / factor),
                            (int) Math.round(e.getY() / factor)));
                    x1 = (int) Math.round(e.getX() / factor);
                    y1 = (int) Math.round(e.getY() / factor);
                    break;
            }
            repaint();
        }

        public void mouseMoved(MouseEvent e) {
            e.consume();
            displayInfo(e.getX());
        }

        public void mousePressed(MouseEvent e) {
            e.consume();
            double factor = Utilities.GetFactor();
            switch (mode) {
                case LINES:
                    x1 = (int) Math.round(e.getX() / factor);
                    y1 = (int) Math.round(e.getY() / factor);
                    x2 = -1;
                    break;

                case -1:
                    break;

                case POINTS:
                default:
                    colors.addElement(getForeground());
                    lines.addElement(new Rectangle((int) Math.round(e.getX() / factor),
                            (int) Math.round(e.getY() / factor), -1, -1));
                    x1 = (int) Math.round(e.getX() / factor);
                    y1 = (int) Math.round(e.getY() / factor);
                    repaint();
                    break;
            }
        }

        public void mouseReleased(MouseEvent e) {
            e.consume();
            double factor = Utilities.GetFactor();
            switch (mode) {
                case LINES:
                    colors.addElement(getForeground());
                    lines.addElement(new Rectangle(x1, y1, (int) Math.round(e.getX() / factor),
                            (int) Math.round(e.getY() / factor)));
                    x2 = xl = -1;
                    break;

                case -1:
                    break;

                case POINTS:
                default:
                    break;
            }
            repaint();
        }

        public void mouseEntered(MouseEvent e) {
        }

        /////////////////////////////////////////////////////////////////////////////////////////////////
        ////////////////////////////////////////////////////////////////////////////////////////////////[
        public void mouseExited(MouseEvent e) {
            dateL.setText("");
            if (languagenow == FinetExpress.constEnglish) {
                openL.setText(lbArray[16][0] + " = ");
                lastL.setText(lbArray[17][0] + " = ");
                highL.setText(lbArray[18][0] + " = ");
                lowL.setText(lbArray[19][0] + " = ");
                volL.setText(lbArray[20][0] + " = ");
            } else if (languagenow == FinetExpress.constChinese) {
                openL.setText(lbArray[16][1] + " = ");
                lastL.setText(lbArray[17][1] + " = ");
                highL.setText(lbArray[18][1] + " = ");
                lowL.setText(lbArray[19][1] + " = ");
                volL.setText(lbArray[20][1] + " = ");
            }
        }
        ////////////////////////////////////////////////////////////////////////////////////////////////]
        /////////////////////////////////////////////////////////////////////////////////////////////////

        public void mouseClicked(MouseEvent e) {
        }

        public void refresh() {
            repaint();
            rectangle.repaint();
        }

        public void putRectangle(RectangleArea rectanglePanel) {
            this.rectangle = rectanglePanel;
        }

        public void setDrawMode(int mode) {
            switch (mode) {
                case LINES:
                case POINTS:
                case -1:
                    this.mode = mode;
                    break;
                default:
                    throw new IllegalArgumentException();
            }
        }

        public void clearAll() {
            lines.removeAllElements();
            colors.removeAllElements();
            repaint();
        }

        public void removeOneElement() {
            if (lines.isEmpty() || colors.isEmpty())
                return;
            lines.removeElement(lines.lastElement());
            colors.removeElement(colors.lastElement());
            repaint();
        }
    }

    class DrawControls extends Panel implements ActionListener, ItemListener {
        DrawPanel target;
        /////////////////////////////////////////////////////////////////////////////////////////////////
        ////////////////////////////////////////////////////////////////////////////////////////////////[
        Button drawButton;
        Button undoButton;
        Button clearButton;
        Choice shapes;
        Choice color;
        ////////////////////////////////////////////////////////////////////////////////////////////////]
        /////////////////////////////////////////////////////////////////////////////////////////////////

        public DrawControls(DrawPanel target) {
            this.target = target;
            setLayout(null);
            setBackground(lowBgColor);
            setFont(new Font("Arial", Font.PLAIN, 10));

            shapes = new Choice();
            shapes.addItemListener(this);
            shapes.addItem("Lines");
            shapes.addItem("Points");
            shapes.addItem("None");
            shapes.setBackground(choiceBgColor);
            shapes.setBounds(3, 4, 70, 20);
//shapes.setBounds(20 , 4, 55 , 20);
            shapes.setFont(new Font("Arial", Font.PLAIN, 10));
            color = new Choice();
            color.addItemListener(this);
            color.addItem("Black");
            color.addItem("Red");
            color.addItem("Blue");
            color.addItem("Green");
            color.addItem("Pink");
            color.addItem("Cyan");
            color.addItem("Orange");
            color.addItem("Magenta");
            color.setBackground(choiceBgColor);
            color.setBounds(3, 27, 70, 20);
            color.setFont(new Font("Arial", Font.PLAIN, 10));
            add(color);
            add(shapes);

            drawButton = new Button();
            drawButton.setLabel("Draw Chart");
            drawButton.setEnabled(true);
            drawButton.setActionCommand("DRAW");
            add(drawButton);
            drawButton.setBackground(Color.lightGray);
            drawButton.setFont(new Font("Arial", Font.PLAIN, 10));
            drawButton.setBounds(2, 70, 70, 20);
            drawButton.addActionListener(this);

            undoButton = new Button();
            undoButton.setLabel("Undo");
            undoButton.setEnabled(true);
            undoButton.setActionCommand("UNDO");
            add(undoButton);
            undoButton.setBackground(Color.lightGray);
            undoButton.setFont(new Font("Arial", Font.PLAIN, 10));
            undoButton.setBounds(39, 48, 33, 20);
            undoButton.addActionListener(this);

            clearButton = new Button();
            clearButton.setLabel("Clear");
            clearButton.setEnabled(true);
            clearButton.setActionCommand("CLEAR");
            add(clearButton);
            clearButton.setBackground(Color.lightGray);
            clearButton.setFont(new Font("Arial", Font.PLAIN, 10));
            clearButton.setBounds(2, 48, 32, 20);
            clearButton.addActionListener(this);
        }

        /////////////////////////////////////////////////////////////////////////////////////////////////
        ////////////////////////////////////////////////////////////////////////////////////////////////[
        public void actionPerformed(ActionEvent e) {
            String command = e.getActionCommand();

            if ((command == "CLEAR") || (command == (lbArray[33][1])))
                target.clearAll();
            else if ((command == "UNDO") || (command == (lbArray[34][1])))
                target.removeOneElement();
            else if ((command == "DRAW") || (command == (lbArray[35][1])))
                target.plotDraw();
        }

        public void itemStateChanged(ItemEvent e) {
            String choice = (String) e.getItem();

            if (e.getSource() instanceof Choice) {
                if ((choice.equals("Lines")) || (choice.equals(lbArray[21][1])))
                    target.setDrawMode(ChartScreen.LINES);	//target.setDrawMode(DrawPanel.LINES);
                else if ((choice.equals("Points")) || (choice.equals(lbArray[22][1])))
                    target.setDrawMode(ChartScreen.POINTS);	//target.setDrawMode(DrawPanel.POINTS);
                else if ((choice.equals("None")) || (choice.equals(lbArray[23][1])))
                    target.setDrawMode(-1);
                else if ((choice.equals("Black")) || (choice.equals(lbArray[24][1])))
                    target.setForeground(Color.black);
                else if ((choice.equals("Red")) || (choice.equals(lbArray[25][1])))
                    target.setForeground(Color.red);
                else if ((choice.equals("Blue")) || (choice.equals(lbArray[26][1])))
                    target.setForeground(Color.blue);
                else if ((choice.equals("Green")) || (choice.equals(lbArray[27][1])))
                    target.setForeground(Color.green);
                else if ((choice.equals("Pink")) || (choice.equals(lbArray[28][1])))
                    target.setForeground(Color.pink);
                else if ((choice.equals("Cyan")) || (choice.equals(lbArray[29][1])))
                    target.setForeground(Color.cyan);
                else if ((choice.equals("Orange")) || (choice.equals(lbArray[30][1])))
                    target.setForeground(Color.orange);
                else if ((choice.equals("Magenta")) || (choice.equals(lbArray[31][1])))
                    target.setForeground(Color.magenta);
            }
        }
    }

    //Due to after click the ¤¤¤å button, SetLanguage must remove all item in the Choice
    // and then additem to them. It must change to the top item. So use this function to
    // get the current item and then through SetLanguage, use PutItemState to set back.
    public void GetItemState() {
        StateUfunction = Ufunction.getSelectedIndex();
        StateLfunction = Lfunction.getSelectedIndex();
        StateChartType = chartType.getSelectedIndex();
        StatePeriod = period.getSelectedIndex();
        StateCompare = compare.getSelectedIndex();
        StateShapes = controlPanel.shapes.getSelectedIndex();
        StateColor = controlPanel.color.getSelectedIndex();
    }

    public void PutItemState() {
        Ufunction.select(StateUfunction);
        Lfunction.select(StateLfunction);
        chartType.select(StateChartType);
        period.select(StatePeriod);
        compare.select(StateCompare);
        controlPanel.shapes.select(StateShapes);
        controlPanel.color.select(StateColor);
    }

    public void SetItemEnable() {
        Ufunction.setVisible(true);
        Lfunction.setVisible(true);
        chartType.setVisible(true);
        period.setVisible(true);
        compare.setVisible(true);
        controlPanel.shapes.setVisible(true);
        controlPanel.color.setVisible(true);
    }

    public void SetItemDisable() {
        Ufunction.setVisible(false);
        Lfunction.setVisible(false);
        chartType.setVisible(false);
        period.setVisible(false);
        compare.setVisible(false);
        ;
        controlPanel.shapes.setVisible(false);
        controlPanel.color.setVisible(false);
    }

    public void SetLanguage(int tlanguage) throws Exception {
        language = tlanguage;
        languagenow = language;
        SetItemDisable();
        GetItemState();

        this.Ufunction.removeAll();
        this.Ufunction.addItem("---");

        if (language == FinetExpress.constEnglish) {
//       this.Ufunction.remove(lbArray[0][1]);
            this.Ufunction.addItem(lbArray[0][0]);
            this.Lfunction.removeAll();
            for (int i = 1; i <= 4; i++)
                this.Lfunction.addItem(lbArray[i][0]);
            this.chartType.removeAll();
            for (int i = 5; i <= 7; i++)
                this.chartType.addItem(lbArray[i][0]);
            this.period.removeAll();
            for (int i = 8; i <= 11; i++)
                this.period.addItem(lbArray[i][0]);
            this.compare.removeAll();
            for (int i = 12; i <= 15; i++)
                this.compare.addItem(lbArray[i][0]);
            this.openL.setText(lbArray[16][0]);
            this.lastL.setText(lbArray[17][0]);
            this.highL.setText(lbArray[18][0]);
            this.lowL.setText(lbArray[19][0]);
            this.volL.setText(lbArray[20][0]);
            this.controlPanel.shapes.removeAll();
            for (int i = 21; i <= 23; i++)
                this.controlPanel.shapes.addItem(lbArray[22][0]);
            this.controlPanel.color.removeAll();
            for (int i = 24; i <= 31; i++)
                this.controlPanel.color.addItem(lbArray[i][0]);
            this.controlPanel.clearButton.setLabel(lbArray[32][0]);
            this.controlPanel.undoButton.setLabel(lbArray[33][0]);
            this.controlPanel.drawButton.setLabel(lbArray[34][0]);
            this.RSI1.setText(lbArray[35][0]);
            this.MOM1.setText(lbArray[35][0]);
            for (int i = 37; i <= 44; i++)
                this.compare.addItem(lbArray[i][0]);
        } else if (language == FinetExpress.constChinese) {
//       this.Ufunction.remove(lbArray[0][0]);
            this.Ufunction.addItem(lbArray[0][1]);
            this.Lfunction.removeAll();
            for (int i = 1; i <= 4; i++)
                this.Lfunction.addItem(lbArray[i][1]);
            this.chartType.removeAll();
            for (int i = 5; i <= 7; i++)
                this.chartType.addItem(lbArray[i][1]);
            this.period.removeAll();
            for (int i = 8; i <= 11; i++)
                this.period.addItem(lbArray[i][1]);
            this.compare.removeAll();
            for (int i = 12; i <= 15; i++)
                this.compare.addItem(lbArray[i][1]);
            this.openL.setText(lbArray[16][1]);
            this.lastL.setText(lbArray[17][1]);
            this.highL.setText(lbArray[18][1]);
            this.lowL.setText(lbArray[19][1]);
            this.volL.setText(lbArray[20][1]);
            this.controlPanel.shapes.removeAll();
            for (int i = 21; i <= 23; i++)
                this.controlPanel.shapes.addItem(lbArray[i][1]);
            this.controlPanel.color.removeAll();
            for (int i = 24; i <= 31; i++)
                this.controlPanel.color.addItem(lbArray[i][1]);
            this.controlPanel.clearButton.setLabel(lbArray[32][1]);
            this.controlPanel.undoButton.setLabel(lbArray[33][1]);
            this.controlPanel.drawButton.setLabel(lbArray[34][1]);
            this.RSI1.setText(lbArray[35][1]);
            this.MOM1.setText(lbArray[35][1]);
            for (int i = 37; i <= 44; i++)
                this.compare.addItem(lbArray[i][1]);
        }
        SetItemEnable();
        PutItemState();
    }
//22/8/2000 by edmund
///////////////////////////////////////////////////////////////////////////////////////////////////]
////////////////////////////////////////////////////////////////////////////////////////////////////
    class RectangleArea extends Panel {
        DrawPanel controller;

        public RectangleArea(DrawPanel drawPanel) {
            super();
            setLayout(null);
            this.controller = drawPanel;
        }

        public void paint(Graphics g) {
//  //System.out.println("RectangleAreaPaint");
            Dimension d = getSize();
            g.draw3DRect(0, 0, d.width - 1, d.height - 1, true);
            g.draw3DRect(3, 3, d.width - 7, d.height - 7, false);

            g.drawLine(375, 5, 375, 185);
            if (compareLine == 0) {
                g.drawLine(5, 185, 375, 185);
            } else {
                g.drawLine(45, 185, 375, 185);
                g.drawLine(44, 5, 44, 185);
            }
            controller.drawIndex(g);
        }
    }
}
