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
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

public class SettingDialog
        extends Frame
        implements WindowListener {
    JLabel JLabel1 = new JLabel();
    JTextField tfSMA1 = new JTextField();
    JLabel JLabel3 = new JLabel();
    JTextField tfSMA2 = new JTextField();
    JLabel JLabel5 = new JLabel();
    JTextField tfSMA3 = new JTextField();
    JLabel JLabel6 = new JLabel();
    JTextField tfWMA1 = new JTextField();
    JLabel JLabel7 = new JLabel();
    JTextField tfWMA2 = new JTextField();
    JLabel JLabel8 = new JLabel();
    JTextField tfWMA3 = new JTextField();
    JButton btSMAApply = new JButton();
    JButton btWMAApply = new JButton();
    JLabel JLabel10 = new JLabel();
    JTextField tfEMA1 = new JTextField();
    JLabel JLabel11 = new JLabel();
    JTextField tfEMA2 = new JTextField();
    JTextField tfEMA3 = new JTextField();
    JButton btEMAApply = new JButton();
    JLabel JLabel12 = new JLabel();
    JLabel JLabel14 = new JLabel();
    JTextField tfBB = new JTextField();
    JLabel JLabel15 = new JLabel();
    JTextField tfDevation = new JTextField();
    JButton btBollingerApply = new JButton();
    JButton btCancel = new JButton();
    JButton btOK = new JButton();
    JPanel jPanel1 = new JPanel();
    BorderLayout borderLayout1 = new BorderLayout();
    JPanel jPanel3 = new JPanel();
    Border border1;
    TitledBorder titledBorder1;
    Border border2;
    JPanel jPanel4 = new JPanel();
    Border border3;
    TitledBorder titledBorder2;
    JPanel jPanel5 = new JPanel();
    Border border4;
    TitledBorder titledBorder3;
    JPanel jPanel6 = new JPanel();
    Border border5;
    TitledBorder titledBorder4;
    Border border6;
    JPanel jPanel2 = new JPanel();
    // the reference of chart and the chartscreen
    private String TAChartName = new String("TA1Chart"); // the chart Name (id) that this setting window can control
    private Object chartScreen = null;
    private int language = FConfig.constEnglish;
    private String lbArray[][] = {
            {
                    "OK", "\u78ba\u5b9a"} //0
            , {
            "Apply", "\u5957\u7528"} //1
            , {
            "Cancel", "\u95dc\u9589"} //2
            , {
            "Help", "\u6307\u5f15"} //3
            , {
            "Simple Moving Average", "\u7c21\u55ae\u79fb\u52d5\u5e73\u5747\u7dda"} //4
            , {
            "Weighted Moving Average", "\u52a0\u6b0a\u79fb\u52d5\u5e73\u5747\u7dda"} //5
            , {
            "Exponential Moving Average",
            "\u6307\u6578\u79fb\u52d5\u5e73\u5747\u7dda"} //6
            , {
            "Bollinger Bands", "\u4fdd\u6b77\u52a0\u901a\u9053"} //7
    };

    public SettingDialog() {
        try {
            jbInit();
            addWindowListener(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void jbInit() throws Exception {

        border1 = BorderFactory.createLineBorder(Color.white, 1);
        titledBorder1 = new TitledBorder(BorderFactory.createLineBorder(Color.black, 1), "Simple Moving Average");
        border2 = BorderFactory.createCompoundBorder(BorderFactory.createEtchedBorder(Color.yellow, new Color(178, 140, 0)), BorderFactory.createEmptyBorder(0, 5, 0, 5));
        border3 = BorderFactory.createLineBorder(Color.red, 1);
        titledBorder2 = new TitledBorder(BorderFactory.createLineBorder(Color.black, 1), "Bollinger's Band");
        border4 = BorderFactory.createLineBorder(SystemColor.controlText, 1);
        titledBorder3 = new TitledBorder(border4, "Exponential Moving Average, EMA");
        border5 = BorderFactory.createEmptyBorder();
        titledBorder4 = new TitledBorder(BorderFactory.createLineBorder(Color.black, 1), "Weight Moving Average, WMA");
        border6 = BorderFactory.createLineBorder(SystemColor.controlText, 1);
        JLabel1.setFont(new java.awt.Font("Arial", 0, 11));
        JLabel1.setText("MA1");
        this.setLayout(borderLayout1);

        JLabel3.setFont(new java.awt.Font("Arial", 0, 11));
        JLabel3.setText("MA2");

        JLabel5.setFont(new java.awt.Font("Arial", 0, 11));
        JLabel5.setText("MA3");

        JLabel6.setFont(new java.awt.Font("Arial", 0, 11));
        JLabel6.setText("MA1");
        tfSMA1.setBackground(Color.white);
        tfSMA1.setBorder(BorderFactory.createLineBorder(Color.black));
        tfSMA1.setText("");
        tfSMA1.setColumns(5);
        tfSMA2.setBackground(Color.white);
        tfSMA2.setBorder(BorderFactory.createLineBorder(Color.black));
        tfSMA2.setText("");
        tfSMA2.setColumns(5);
        tfSMA3.setBorder(BorderFactory.createLineBorder(Color.black));
        tfSMA3.setText("");
        tfSMA3.setColumns(5);
        tfWMA1.setBackground(Color.white);
        tfWMA1.setBorder(border6);
        tfWMA1.setMinimumSize(new Dimension(2, 5));
        tfWMA1.setOpaque(true);
        tfWMA1.setCaretColor(Color.black);
        tfWMA1.setCaretPosition(0);
        tfWMA1.setSelectionStart(0);
        tfWMA1.setText("");
        tfWMA1.setColumns(5);

        JLabel7.setFont(new java.awt.Font("Arial", 0, 11));
        JLabel7.setText("MA2");
        tfWMA2.setBackground(Color.white);
        tfWMA2.setBorder(BorderFactory.createLineBorder(Color.black));

        tfWMA2.setText("");
        tfWMA2.setColumns(5);

        JLabel8.setFont(new java.awt.Font("Arial", 0, 11));
        JLabel8.setText("MA3");
        tfWMA3.setBackground(Color.white);
        tfWMA3.setBorder(BorderFactory.createLineBorder(Color.black));
        tfWMA3.setSelectionStart(0);
        tfWMA3.setText("");
        tfWMA3.setColumns(5);
        btSMAApply.setBackground(Color.orange);
        btSMAApply.setFont(new java.awt.Font("Arial", 0, 11));
        btSMAApply.setBorder(border2);
        btSMAApply.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btSMAApply_actionPerformed(e);
            }
        });
        btWMAApply.setBackground(Color.orange);
        btWMAApply.setFont(new java.awt.Font("Arial", 0, 11));
        btWMAApply.setBorder(border2);
        btWMAApply.setBorderPainted(true);
        btWMAApply.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btWMAApply_actionPerformed(e);
            }
        });

        JLabel10.setFont(new java.awt.Font("Arial", 0, 11));
        JLabel10.setText("MA1");
        tfEMA1.setBackground(Color.white);
        tfEMA1.setBorder(BorderFactory.createLineBorder(Color.black));
        tfEMA1.setText("");
        tfEMA1.setColumns(5);

        JLabel11.setFont(new java.awt.Font("Arial", 0, 11));
        JLabel11.setText("MA2");
        tfEMA2.setBackground(Color.white);
        tfEMA2.setBorder(BorderFactory.createLineBorder(Color.black));
        tfEMA2.setText("");
        tfEMA2.setColumns(5);
        tfEMA3.setBackground(Color.white);
        tfEMA3.setBorder(BorderFactory.createLineBorder(Color.black));
        tfEMA3.setText("");
        tfEMA3.setColumns(5);
        btEMAApply.setBackground(Color.orange);
        btEMAApply.setFont(new java.awt.Font("Arial", 0, 11));
        btEMAApply.setBorder(border2);
        btEMAApply.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btEMAApply_actionPerformed(e);
            }
        });

        JLabel12.setFont(new java.awt.Font("Arial", 0, 11));
        JLabel12.setText("MA3");
        this.setBackground(FConfig.DialogColor);
        this.setDropTarget(null);
        JLabel14.setFont(new java.awt.Font("Arial", 0, 11));
        JLabel14.setText("MA1");
        tfBB.setBackground(Color.white);
        tfBB.setBorder(BorderFactory.createLineBorder(Color.black));
        tfBB.setText("");
        tfBB.setColumns(5);

        JLabel15.setFont(new java.awt.Font("Arial", 0, 11));
        JLabel15.setText("Deviation");
        tfDevation.setBackground(Color.white);
        tfDevation.setBorder(BorderFactory.createLineBorder(Color.black));
        tfDevation.setColumns(5);
        btBollingerApply.setBackground(Color.orange);
        btBollingerApply.setFont(new java.awt.Font("Arial", 0, 11));
        btBollingerApply.setBorder(border2);
        btBollingerApply.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btBollingerApply_actionPerformed(e);
            }
        });
        btCancel.setBackground(Color.orange);
        btCancel.setFont(new java.awt.Font("Arial", 0, 11));
        btCancel.setBorder(border2);
        btCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btCancel_actionPerformed(e);
            }
        });
        btOK.setBackground(Color.orange);
        btOK.setFont(new java.awt.Font("Arial", 0, 11));
        btOK.setBorder(border2);
        btOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btOK_actionPerformed(e);
            }
        });

        jPanel1.setBackground(FConfig.ScreenBackground);
        jPanel1.setBorder(BorderFactory.createEtchedBorder());
        jPanel1.setLayout(null);


        jPanel3.setFont(new java.awt.Font("Arial", 0, 11));
        jPanel3.setBorder(titledBorder1);
        jPanel3.setDebugGraphicsOptions(0);
        jPanel3.setBounds(new Rectangle(12, 80, 353, 57));
        jPanel4.setFont(new java.awt.Font("Arial", 0, 11));
        jPanel4.setBorder(titledBorder2);
        jPanel4.setBounds(new Rectangle(13, 204, 352, 60));

        jPanel5.setFont(new java.awt.Font("Arial", 0, 11));
        jPanel5.setBorder(titledBorder3);
        jPanel5.setBounds(new Rectangle(12, 141, 353, 56));
        jPanel6.setFont(new java.awt.Font("Arial", 0, 11));
        jPanel6.setBorder(titledBorder4);
        jPanel6.setBounds(new Rectangle(12, 16, 354, 58));
        jPanel2.setBounds(new Rectangle(74, 268, 235, 34));
        jPanel3.add(JLabel1, null);
        jPanel3.add(tfSMA1, null);
        jPanel3.add(JLabel3, null);
        jPanel3.add(tfSMA2, null);
        jPanel3.add(JLabel5, null);
        jPanel3.add(tfSMA3, null);
        jPanel3.add(btSMAApply, null);
        jPanel1.add(jPanel4, null);
        jPanel4.add(JLabel14, null);
        jPanel4.add(tfBB, null);
        jPanel4.add(JLabel15, null);
        jPanel4.add(tfDevation, null);
        jPanel4.add(btBollingerApply, null);
        jPanel1.add(jPanel5, null);
        jPanel6.add(JLabel6, null);
        jPanel6.add(tfWMA1, null);
        jPanel6.add(JLabel7, null);
        jPanel6.add(tfWMA2, null);
        jPanel6.add(JLabel8, null);
        jPanel6.add(tfWMA3, null);
        jPanel6.add(btWMAApply, null);
        jPanel1.add(jPanel2, null);
        jPanel2.add(btOK, null);
        jPanel2.add(btCancel, null);
        jPanel1.add(jPanel3, null);
        jPanel5.add(JLabel10, null);
        jPanel5.add(tfEMA1, null);
        jPanel5.add(JLabel11, null);
        jPanel5.add(tfEMA2, null);
        jPanel5.add(JLabel12, null);
        jPanel5.add(tfEMA3, null);
        jPanel5.add(btEMAApply, null);
        jPanel1.add(jPanel6, null);
        jPanel2.setBackground(FConfig.ScreenBackground);

        this.add(jPanel1, BorderLayout.CENTER);

    }

    void btWMAApply_actionPerformed(ActionEvent e) {

        if (!(FormatUtil.isNumerical(tfWMA1.getText()) &&
                FormatUtil.isNumerical(tfWMA2.getText()) &&
                FormatUtil.isNumerical(tfWMA3.getText()))) {
            this.updateSetting();
            return;
        }
        ChartItem taChart = ((ChartScreen) chartScreen).getChart(
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
        ((ChartScreen) chartScreen).updateBaseScreen();
        ((ChartScreen) chartScreen).repaint();
    }

    void btOK_actionPerformed(ActionEvent e) {
        this.btBollingerApply_actionPerformed(null);
        this.btEMAApply_actionPerformed(null);
        this.btSMAApply_actionPerformed(null);
        this.btWMAApply_actionPerformed(null);
        this.dispose();
    }

    void btCancel_actionPerformed(ActionEvent e) {

        this.dispose();
    }

    void btSMAApply_actionPerformed(ActionEvent e) {

        if (!(FormatUtil.isNumerical(tfSMA1.getText()) &&
                FormatUtil.isNumerical(tfSMA2.getText()) &&
                FormatUtil.isNumerical(tfSMA3.getText()))) {
            System.out.println("SMAAPPLy ERROR");
            this.updateSetting();
            return;
        }
        ChartItem taChart = ((ChartScreen) chartScreen).getChart(
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
        ((ChartScreen) chartScreen).updateBaseScreen();
        ((ChartScreen) chartScreen).repaint();
    }

    void btEMAApply_actionPerformed(ActionEvent e) {

        if (!(FormatUtil.isFloat(tfEMA1.getText()) &&
                FormatUtil.isFloat(tfEMA2.getText()) &&
                FormatUtil.isFloat(tfEMA3.getText()))) {

            this.updateSetting();
            return;
        }
        ChartItem taChart = ((ChartScreen) chartScreen).getChart(
                TAChartName);
        if (taChart == null) {
            return;
        }
        int N1, N2, N3;
        N1 = Integer.parseInt(tfEMA1.getText());
        N2 = Integer.parseInt(tfEMA2.getText());
        N3 = Integer.parseInt(tfEMA3.getText());

// data vaildation.
        if (N1 <= 0 || N2 <= 0 || N3 <= 0 || N1 > taChart.getChartData().getData().size() ||
                N2 > taChart.getChartData().getData().size() ||
                N3 > taChart.getChartData().getData().size()) {
            this.updateSetting();
            return;
        }
///////
//////////////////////////////////////////////////////////////////////////////////////////////////


        taChart.getChartData().getfTAconfig().EMA1 = N1;
        taChart.getChartData().getfTAconfig().EMA2 = N2;
        taChart.getChartData().getfTAconfig().EMA3 = N3;
        if (taChart.getChartType() == ChartType.EXPONENTIAL_MOVING_AVERAGE) {
            taChart.getChartData().calculateExponentialMovingAverage(N1, N2, N3);
            //  taChart.setVisible(true);
        }
        ((ChartScreen) chartScreen).updateBaseScreen();
        ((ChartScreen) chartScreen).repaint();

    }

    void btBollingerApply_actionPerformed(ActionEvent e) {
        if (!(FormatUtil.isNumerical(tfBB.getText()) &&
                FormatUtil.isFloat(this.tfDevation.getText()))) {
            this.updateSetting();
            return;
        }
        ChartItem taChart = ((ChartScreen) chartScreen).getChart(
                TAChartName);
        if (taChart == null) {
            return;
        }
        int bb;
        float dd;
        bb = Integer.parseInt(tfBB.getText());
        dd = Float.valueOf(tfDevation.getText()).floatValue();

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
        ((ChartScreen) chartScreen).updateBaseScreen();
        ((ChartScreen) chartScreen).repaint();
    }

    void updateSetting() {
        ChartItem currentChart = ((ChartScreen) chartScreen).getChart(
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
    }

    void setChartScreen(ChartScreen cs) {
        chartScreen = cs;
    }

    public void setLanguage(int tlanguage) {
        language = tlanguage;
        this.btOK.setLabel(lbArray[0][language]);
        this.btSMAApply.setLabel(lbArray[1][language]);
        this.btWMAApply.setLabel(lbArray[1][language]);
        this.btEMAApply.setLabel(lbArray[1][language]);
        this.btBollingerApply.setLabel(lbArray[1][language]);
    }

    public void setTAChartName(String tachartName) {
        TAChartName = tachartName;
    }

    public void windowOpened(WindowEvent e) {
        System.out.println("WindowOpened");
    }

    public void windowClosing(WindowEvent e) {
        this.dispose();
    }

    public void windowClosed(WindowEvent e) {
        System.out.println("closed");
    }

    public void windowIconified(WindowEvent e) {
        System.out.println("iconified");
    }

    public void windowDeiconified(WindowEvent e) {
        System.out.println("deiconified");
    }

    public void windowActivated(WindowEvent e) {
        System.out.println("activated");
    }

    public void windowDeactivated(WindowEvent e) {
        System.out.println("deactivated");
    }

}
