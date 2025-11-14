package com.ic.gui;

import com.ic.core.ChartItem;
import com.ic.core.ChartScreen;
import com.ic.core.ChartType;
import com.ic.core.FConfig;
import com.ic.util.FormatUtil;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

public class SettingDialog extends JDialog implements WindowListener {


    private class SimpleTextField extends JTextField {
        public SimpleTextField() {
            setText("");
            setColumns(5);
            setBackground(Color.white);
        }
    }

    private final JLabel JLabelx1 = new JLabel();
    private final JLabel jlabelx6 = new JLabel();
    private final JLabel jlabely1 = new JLabel();
    private final JLabel jlabel11y = new JLabel();
    private final JLabel jlabelx2 = new JLabel();
    private final JLabel lbWilliam = new JLabel();
    private final JLabel jlabelx4 = new JLabel();
    private final JLabel jlabelx7 = new JLabel();
    private final JLabel JLabel1 = new JLabel();
    private final JLabel JLabel3 = new JLabel();
    private final JLabel JLabel5 = new JLabel();
    private final JLabel JLabel6 = new JLabel();
    private final JLabel JLabel7 = new JLabel();
    private final JLabel JLabel8 = new JLabel();
    private final JLabel JLabel10 = new JLabel();
    private final JLabel JLabel11 = new JLabel();
    private final JLabel JLabel12 = new JLabel();
    private final JLabel JLabel14 = new JLabel();
    private final JLabel JLabel15 = new JLabel();


    private final SimpleTextField tfSTCD = new SimpleTextField();
    private final SimpleTextField tfSTCK = new SimpleTextField();
    private final SimpleTextField tfELMA = new SimpleTextField();
    private final SimpleTextField tfRSI = new SimpleTextField();
    private final SimpleTextField tfESMA = new SimpleTextField();
    private final SimpleTextField tfEAMA = new SimpleTextField();
    private final SimpleTextField tfWR = new SimpleTextField();
    private final SimpleTextField tfSMA1 = new SimpleTextField();
    private final SimpleTextField tfSMA2 = new SimpleTextField();
    private final SimpleTextField tfSMA3 = new SimpleTextField();
    private final SimpleTextField tfWMA1 = new SimpleTextField();
    private final SimpleTextField tfWMA2 = new SimpleTextField();
    private final SimpleTextField tfWMA3 = new SimpleTextField();
    private final SimpleTextField tfEMA1 = new SimpleTextField();
    private final SimpleTextField tfEMA2 = new SimpleTextField();
    private final SimpleTextField tfEMA3 = new SimpleTextField();
    private final SimpleTextField tfBB = new SimpleTextField();
    private final SimpleTextField tfDevation = new SimpleTextField();

    private final JPanel jpRSI = new JPanel();
    private final JPanel jpSTC = new JPanel();
    private final JPanel jpMACD = new JPanel();
    private final JPanel jpWilliamR = new JPanel();
    private final JPanel jpButtons = new JPanel();
    private final JPanel jp6 = new JPanel();
    private final JPanel jpMain = new JPanel();
    private final JPanel jpSMA = new JPanel();
    private final JPanel jpBollingerBand = new JPanel();
    private final JPanel jpEMA = new JPanel();
    private final JPanel jpWMA = new JPanel();
    private final JPanel jPanel2 = new JPanel();

    private final BorderLayout borderLayout1 = new BorderLayout();

    private final JButton btCancel = new JButton();
    private final JButton btOK = new JButton();

    private String TAChartName = "TA1Chart"; // the chart Name (id) that this setting window can control
    private ChartScreen chartScreen = null;
    private ChartScreen chartScreen2 = null;

    public SettingDialog() {
        super();
        jbInit();
        addWindowListener(this);
    }

    private void jbInit() {

        setLayout(borderLayout1);
        Border border = BorderFactory.createLineBorder(Color.black, 1);
        TitledBorder tb1 = new TitledBorder(border, "Relative Strength Index (RSI)");
        TitledBorder tb2 = new TitledBorder(border, "William's %R");
        TitledBorder tb3 = new TitledBorder(border, "Stochastic (STC)");
        TitledBorder tb4 = new TitledBorder(border, "Moving Average Convergence Divergence (MACD)");
        TitledBorder tb5 = new TitledBorder(border, "Simple Moving Average");
        TitledBorder tb6 = new TitledBorder(border, "Bollinger's Band");
        TitledBorder tb7 = new TitledBorder(border, "Exponential Moving Average, EMA");
        TitledBorder tb8 = new TitledBorder(border, "Weight Moving Average, WMA");


        jpSMA.setBorder(tb5);
        jpBollingerBand.setBorder(tb6);
        jpEMA.setBorder(tb7);
        jpWMA.setBorder(tb8);
        jpRSI.setBorder(tb1);
        jpSTC.setBorder(tb3);
        jpMACD.setBorder(tb4);
        jpWilliamR.setBorder(tb2);

        JLabel1.setText("MA1");
        JLabel3.setText("MA2");
        JLabel5.setText("MA3");

        JLabel6.setText("MA1");
        JLabel7.setText("MA2");
        JLabel8.setText("MA3");

        JLabel10.setText("MA1");
        JLabel11.setText("MA2");
        JLabel12.setText("MA3");

        JLabel14.setText("MA1");
        JLabel15.setText("Deviation");


        //jpMain.setBorder(BorderFactory.createEtchedBorder());

        jp6.setLayout(null);

        jpWMA.setBounds(new Rectangle(10, 20, 380, 56));
        jpSMA.setBounds(new Rectangle(10, 80, 380, 56));
        jpBollingerBand.setBounds(new Rectangle(10, 200, 380, 56));
        jpEMA.setBounds(new Rectangle(10, 140, 380, 56));
        jpRSI.setBounds(new Rectangle(10, 260, 380, 56));
        jpSTC.setBounds(new Rectangle(10, 320, 380, 56));
        jpMACD.setBounds(new Rectangle(10, 380, 380, 56));
        jpWilliamR.setBounds(new Rectangle(10, 440, 380, 56));
        jpButtons.setBounds(new Rectangle(10, 500, 380, 56));

        jpSMA.add(JLabel1, null);
        jpSMA.add(tfSMA1, null);
        jpSMA.add(JLabel3, null);
        jpSMA.add(tfSMA2, null);
        jpSMA.add(JLabel5, null);
        jpSMA.add(tfSMA3, null);


        jpBollingerBand.add(JLabel14, null);
        jpBollingerBand.add(tfBB, null);
        jpBollingerBand.add(JLabel15, null);
        jpBollingerBand.add(tfDevation, null);

        jpMain.setBackground(FConfig.ScreenBackground);
        jpMain.setLayout(null);
        jpMain.add(jpBollingerBand, null);
        jpMain.add(jpEMA, null);
        jpMain.add(jPanel2, null);
        jpMain.add(jpSMA, null);
        jpMain.add(jpWMA, null);

        jpWMA.add(JLabel6, null);
        jpWMA.add(tfWMA1, null);
        jpWMA.add(JLabel7, null);
        jpWMA.add(tfWMA2, null);
        jpWMA.add(JLabel8, null);
        jpWMA.add(tfWMA3, null);


        //  jPanel2.add(btOK, null);
        //  jPanel2.add(btCancel, null);

        jpEMA.add(JLabel10, null);
        jpEMA.add(tfEMA1, null);
        jpEMA.add(JLabel11, null);
        jpEMA.add(tfEMA2, null);
        jpEMA.add(JLabel12, null);
        jpEMA.add(tfEMA3, null);


        JLabelx1.setText("MA1");
        jlabelx6.setText("%K");
        jlabelx7.setText("%D");
        jlabely1.setText("MA1");
        jlabel11y.setText("MA2");
        jlabelx2.setText("MA3");
        lbWilliam.setText("William's %R");
        jlabelx4.setText("%R");


        jpRSI.add(JLabelx1, null);
        jpRSI.add(tfRSI, null);

        jpMain.add(jpSTC, null);

        jpSTC.add(jlabelx6, null);
        jpSTC.add(tfSTCK, null);
        jpSTC.add(jlabelx7, null);
        jpSTC.add(tfSTCD, null);

        jpMain.add(jpWilliamR, null);
        jpMACD.add(jlabely1, null);
        jpMACD.add(tfELMA, null);
        jpMACD.add(jlabel11y, null);
        jpMACD.add(tfESMA, null);
        jpMACD.add(jlabelx2, null);
        jpMACD.add(tfEAMA, null);

        jpMain.add(jpButtons, null);
        jpWilliamR.add(lbWilliam, null);
        jpWilliamR.add(jlabelx4, null);
        jpWilliamR.add(tfWR, null);

        jpMain.add(jpMACD, null);
        jpButtons.add(btOK, null);
        jpButtons.add(btCancel, null);
        jpMain.add(jpRSI, null);

        this.add(jpMain, BorderLayout.CENTER);


        btCancel.setText("Cancel");
        btCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btCancel_actionPerformed(e);
            }
        });
        btOK.setText("OK");
        btOK.addActionListener(new java.awt.event.ActionListener() {

            public void actionPerformed(ActionEvent e) {
                btOK_actionPerformed(e);
            }
        });

    }


    private void btOK_actionPerformed(ActionEvent e) {
        this.btWilliamApply_actionPerformed(null);
        this.btMACDApply_actionPerformed(null);
        this.btRSIApply_actionPerformed(null);
        this.btSTCApply_actionPerformed(null);

        this.btBollingerApply_actionPerformed(null);
        this.btEMAApply_actionPerformed(null);
        this.btSMAApply_actionPerformed(null);
        this.btWMAApply_actionPerformed(null);

        this.dispose();
    }


    private void btRSIApply_actionPerformed(ActionEvent e) {
        if (!FormatUtil.isNumber(tfRSI.getText())) {
            updateSetting();
            return;
        }
        ChartItem taChart = chartScreen2.getChart(TAChartName);
        if (taChart == null) {
            return;
        }

        taChart.getChartData().getfTAconfig().RSIPeriod = Integer.parseInt(tfRSI.getText());
        if (taChart.getChartType() == ChartType.RSI) {
            taChart.getChartData()
                    .calculateRSI(taChart.getChartData().getfTAconfig().RSIPeriod);
            chartScreen2.updateBaseScreen();
            chartScreen2.repaint();
        }

    }

    private void btMACDApply_actionPerformed(ActionEvent e) {
        if (!FormatUtil.isNumber(tfEAMA.getText())
                || !FormatUtil.isNumber(this.tfESMA.getText())
                || !FormatUtil.isNumber(this.tfELMA.getText())) {
            updateSetting();
            return;
        }
        ChartItem taChart = chartScreen2.getChart(TAChartName);
        if (taChart == null) {
            return;
        }

        int LMA = Integer.parseInt(this.tfELMA.getText());
        int SMA = Integer.parseInt(this.tfESMA.getText());
        int AMA = Integer.parseInt(this.tfEAMA.getText());

        taChart.getChartData().getfTAconfig().MACDAEMA = AMA;
        taChart.getChartData().getfTAconfig().MACDLEMA = LMA;
        taChart.getChartData().getfTAconfig().MACDSEMA = SMA;

        if (taChart.getChartType() == ChartType.MACD) {
            taChart.getChartData().calculateMACD(LMA, SMA, AMA);
            chartScreen2.updateBaseScreen();
            chartScreen2.repaint();
        }

    }

    private void btSTCApply_actionPerformed(ActionEvent e) {
        if (!FormatUtil.isNumber(tfSTCK.getText())
                || !FormatUtil.isNumber(this.tfSTCD.getText())) {
            updateSetting();
            return;
        }
        ChartItem taChart = chartScreen2.getChart(TAChartName);
        if (taChart == null) {
            return;
        }

        int K = Integer.parseInt(this.tfSTCK.getText());
        int D = Integer.parseInt(this.tfSTCD.getText());

        taChart.getChartData().getfTAconfig().STCDPeriod = D;
        taChart.getChartData().getfTAconfig().STCKPeriod = K;
        if (taChart.getChartType() == ChartType.STC) {
            taChart.getChartData().calculateSTC(K, D);
            chartScreen2.updateBaseScreen();
            chartScreen2.repaint();
        }
    }

    private void btWilliamApply_actionPerformed(ActionEvent e) {
        if (!FormatUtil.isNumber(tfWR.getText())) {
            updateSetting();
            return;
        }
        ChartItem taChart = chartScreen2.getChart(TAChartName);
        if (taChart == null) {
            return;
        }

        int R = Integer.parseInt(tfWR.getText());

        taChart.getChartData().getfTAconfig().WilliamPeriod = R;
        if (taChart.getChartType() == ChartType.WILLIAM_R) {
            taChart.getChartData().calculateWilliamR(R);
            chartScreen2.updateBaseScreen();
            chartScreen2.repaint();
        }
    }

    private void btWMAApply_actionPerformed(ActionEvent e) {

        if (!(FormatUtil.isNumber(tfWMA1.getText()) &&
                FormatUtil.isNumber(tfWMA2.getText()) &&
                FormatUtil.isNumber(tfWMA3.getText()))) {
            this.updateSetting();
            return;
        }
        ChartItem taChart = chartScreen.getChart(
                TAChartName);
        if (taChart == null) {
            return;
        }
        int N1, N2, N3;
        N1 = Integer.parseInt(tfWMA1.getText());
        N2 = Integer.parseInt(tfWMA2.getText());
        N3 = Integer.parseInt(tfWMA3.getText());

// data vaildation.
        if (N1 <= 0 || N2 <= 0 || N3 <= 0 || N1 > taChart.getChartData().getData().size() ||
                N2 > taChart.getChartData().getData().size() ||
                N3 > taChart.getChartData().getData().size()) {
            this.updateSetting();
            return;
        }
//////////////////////////////////////////////////////////////////////////////////////////////////

        taChart.getChartData().getfTAconfig().WMAN1 = N1;
        taChart.getChartData().getfTAconfig().WMAN2 = N2;
        taChart.getChartData().getfTAconfig().WMAN3 = N3;
        if (taChart.getChartType() == ChartType.WEIGHTED_MOVING_AVERAGE) {
            taChart.getChartData().calculateWeightedMovingAverage(N1, N2, N3);
        }
        chartScreen.updateBaseScreen();
        chartScreen.repaint();
    }


    private void btCancel_actionPerformed(ActionEvent e) {

        this.dispose();
    }

    private void btSMAApply_actionPerformed(ActionEvent e) {

        if (!(FormatUtil.isNumber(tfSMA1.getText()) &&
                FormatUtil.isNumber(tfSMA2.getText()) &&
                FormatUtil.isNumber(tfSMA3.getText()))) {
            System.out.println("SMAAPPLy ERROR");
            this.updateSetting();
            return;
        }
        ChartItem taChart = chartScreen.getChart(
                TAChartName);
        if (taChart == null) {
            return;
        }
        int N1, N2, N3;
        N1 = Integer.parseInt(tfSMA1.getText());
        N2 = Integer.parseInt(tfSMA2.getText());
        N3 = Integer.parseInt(tfSMA3.getText());

// data vaildation.
        if (N1 <= 0 || N2 <= 0 || N3 <= 0 || N1 > taChart.getChartData().getData().size() ||
                N2 > taChart.getChartData().getData().size() ||
                N3 > taChart.getChartData().getData().size()) {
            this.updateSetting();
            return;
        }
//////////////////////////////////////////////////////////////////////////////////////////////////

        taChart.getChartData().getfTAconfig().SMAN1 = N1;
        taChart.getChartData().getfTAconfig().SMAN2 = N2;
        taChart.getChartData().getfTAconfig().SMAN3 = N3;
        if (taChart.getChartType() == ChartType.SIMPLE_MOVING_AVERAGE) {
            taChart.getChartData().calculateMovingAverage(N1, N2, N3);
        }
        chartScreen.updateBaseScreen();
        chartScreen.repaint();
    }

    private void btEMAApply_actionPerformed(ActionEvent e) {

        if (!(FormatUtil.isFloat(tfEMA1.getText()) &&
                FormatUtil.isFloat(tfEMA2.getText()) &&
                FormatUtil.isFloat(tfEMA3.getText()))) {

            this.updateSetting();
            return;
        }
        ChartItem taChart = chartScreen.getChart(
                TAChartName);
        if (taChart == null) {
            return;
        }
        int N1, N2, N3;
        N1 = Integer.parseInt(tfEMA1.getText());
        N2 = Integer.parseInt(tfEMA2.getText());
        N3 = Integer.parseInt(tfEMA3.getText());

        if (N1 <= 0 || N2 <= 0 || N3 <= 0 || N1 > taChart.getChartData().getData().size() ||
                N2 > taChart.getChartData().getData().size() ||
                N3 > taChart.getChartData().getData().size()) {
            this.updateSetting();
            return;
        }

        taChart.getChartData().getfTAconfig().EMA1 = N1;
        taChart.getChartData().getfTAconfig().EMA2 = N2;
        taChart.getChartData().getfTAconfig().EMA3 = N3;
        if (taChart.getChartType() == ChartType.EXPONENTIAL_MOVING_AVERAGE) {
            taChart.getChartData().calculateExponentialMovingAverage(N1, N2, N3);
            //  taChart.setVisible(true);
        }
        chartScreen.updateBaseScreen();
        chartScreen.repaint();

    }

    private void btBollingerApply_actionPerformed(ActionEvent e) {
        if (!(FormatUtil.isNumber(tfBB.getText()) &&
                FormatUtil.isFloat(this.tfDevation.getText()))) {
            this.updateSetting();
            return;
        }
        ChartItem taChart = chartScreen.getChart(
                TAChartName);
        if (taChart == null) {
            return;
        }
        int bb;
        float dd;
        bb = Integer.parseInt(tfBB.getText());
        dd = Float.valueOf(tfDevation.getText());

        // data vaildation.
        if (bb <= 0 || bb > taChart.getChartData().getData().size() || dd <= 0 ||
                dd >= 100) {
            this.updateSetting();
            return;
        }

        taChart.getChartData().getfTAconfig().bbN = bb;
        taChart.getChartData().getfTAconfig().bbDevation = dd;
        if (taChart.getChartType() == ChartType.BOLLINGERBAND) {
            taChart.getChartData().calculateBollingerBand(bb, dd);
        }
        chartScreen.updateBaseScreen();
        chartScreen.repaint();
    }

    void updateSetting() {
        ChartItem currentChart = chartScreen.getChart(
                TAChartName);
        if (currentChart == null) {
            System.out.println("NO chart found in setting");
            return;
        }

        this.tfSMA1.setText(String.valueOf(currentChart.getChartData().getfTAconfig().SMAN1));
        this.tfSMA2.setText(String.valueOf(currentChart.getChartData().getfTAconfig().SMAN2));
        this.tfSMA3.setText(String.valueOf(currentChart.getChartData().getfTAconfig().SMAN3));
        this.tfWMA1.setText(String.valueOf(currentChart.getChartData().getfTAconfig().WMAN1));
        this.tfWMA2.setText(String.valueOf(currentChart.getChartData().getfTAconfig().WMAN2));
        this.tfWMA3.setText(String.valueOf(currentChart.getChartData().getfTAconfig().WMAN3));
        this.tfEMA1.setText(String.valueOf(currentChart.getChartData().getfTAconfig().EMA1));
        this.tfEMA2.setText(String.valueOf(currentChart.getChartData().getfTAconfig().EMA2));
        this.tfEMA3.setText(String.valueOf(currentChart.getChartData().getfTAconfig().EMA3));
        this.tfBB.setText(String.valueOf(currentChart.getChartData().getfTAconfig().bbN));
        this.tfDevation.setText(String.valueOf(currentChart.getChartData().getfTAconfig().bbDevation));


        this.tfRSI.setText(String
                .valueOf(currentChart.getChartData().getfTAconfig().RSIPeriod));
        this.tfSTCK.setText(String
                .valueOf(currentChart.getChartData().getfTAconfig().STCKPeriod));
        this.tfSTCD.setText(String
                .valueOf(currentChart.getChartData().getfTAconfig().STCDPeriod));
        this.tfWR.setText(String
                .valueOf(currentChart.getChartData().getfTAconfig().WilliamPeriod));
        this.tfEAMA.setText(String
                .valueOf(currentChart.getChartData().getfTAconfig().MACDAEMA));
        this.tfELMA.setText(String
                .valueOf(currentChart.getChartData().getfTAconfig().MACDLEMA));
        this.tfESMA.setText(String
                .valueOf(currentChart.getChartData().getfTAconfig().MACDSEMA));
    }

    void setChartScreen(ChartScreen cs, ChartScreen cs2) {
        chartScreen = cs;
        chartScreen2 = cs2;
    }

    public void setTAChartName(String tachartName) {
        TAChartName = tachartName;
    }

    public void windowOpened(WindowEvent e) {
        //  System.out.println("WindowOpened");
    }

    public void windowClosing(WindowEvent e) {
        this.dispose();
    }

    public void windowClosed(WindowEvent e) {
        // System.out.println("closed");
    }

    public void windowIconified(WindowEvent e) {
        // System.out.println("iconified");
    }

    public void windowDeiconified(WindowEvent e) {
        //System.out.println("deiconified");
    }

    public void windowActivated(WindowEvent e) {
        //System.out.println("activated");
    }

    public void windowDeactivated(WindowEvent e) {
        //System.out.println("deactivated");
    }

}
