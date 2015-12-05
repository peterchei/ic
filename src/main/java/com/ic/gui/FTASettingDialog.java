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

public class FTASettingDialog extends JFrame implements WindowListener {

    private static final long serialVersionUID = -9185399744031564931L;
    JLabel JLabel1 = new JLabel();
    JTextField tfRSI = new JTextField();
    JLabel JLabel6 = new JLabel();
    JTextField tfSTCK = new JTextField();
    JLabel JLabel7 = new JLabel();
    JTextField tfSTCD = new JTextField();
    JButton btRSIApply = new JButton();
    JButton btSTCApply = new JButton();
    JLabel JLabel10 = new JLabel();
    JTextField tfELMA = new JTextField();
    JLabel JLabel11 = new JLabel();
    JTextField tfESMA = new JTextField();
    JTextField tfEAMA = new JTextField();
    JButton btMACDApply = new JButton();
    JLabel JLabel12 = new JLabel();
    JLabel lbWilliam = new JLabel();
    JLabel JLabel14 = new JLabel();
    JTextField tfWR = new JTextField();
    JButton btWilliamApply = new JButton();
    JButton btCancel = new JButton();
    JButton btOK = new JButton();
    JPanel jPanel1 = new JPanel();
    JPanel jPanel2 = new JPanel();
    JPanel jPanel3 = new JPanel();
    JPanel jPanel4 = new JPanel();
    JPanel jPanel5 = new JPanel();
    Border border1;
    TitledBorder titledBorder1;
    Border border2;
    Border border3;
    TitledBorder titledBorder2;
    Border border4;
    TitledBorder titledBorder3;
    Border border5;
    TitledBorder titledBorder4;
    JPanel jPanel6 = new JPanel();
    BorderLayout borderLayout1 = new BorderLayout();
    // the reference of chart and the chartscreen
    private String TAChartName = new String("TA1Chart"); // the chart Name (id)
    // that this setting
    // window can
    // control
    private ChartScreen chartScreen = null;
    private int language = FConfig.constEnglish;
    private String lbArray[][] = {
            {"OK", "\u78ba\u5b9a"} // 0
            ,
            {"Apply", "\u5957\u7528"} // 1
            ,
            {"Cancel", "\u95dc\u9589"} // 2
            ,
            {"Help", "\u6307\u5f15"} // 3
            ,
            {"Relative Strength Index", "\u76f8\u5c0d\ufffd\u5f31\u6307\u6578"} // 4
            ,
            {"Stochastics", "\u96a8\u6a5f\u6307\u6578"} // 5
            ,
            {"Moving Average Convergence Divergence",
                    "\u79fb\u52d5\u5e73\u5747\u7dda\u532f\u805a\u80cc\u99b3\u6307\u6a19"} // 6
            , {"William %R", "\u5a01\u5ec9\u6307\u6a19"} // 7

    };

    public FTASettingDialog() {
        try {
            jbInit();
            addWindowListener(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setLanguage(int tlanguage) {
        language = tlanguage;
        this.btOK.setLabel(lbArray[0][language]);
        this.btMACDApply.setLabel(lbArray[1][language]);
        this.btRSIApply.setLabel(lbArray[1][language]);
        this.btWilliamApply.setLabel(lbArray[1][language]);
        this.btSTCApply.setLabel(lbArray[1][language]);
        this.btCancel.setLabel(lbArray[2][language]);
        // this.btHelp.setLabel(lbArray[3][language]);

        // this.lbRSI.setText(lbArray[4][language] + "(RSI)");
        // this.lbSTC.setText(lbArray[5][language] + "(STC)");
        // this.lbMACD.setText(lbArray[6][language] + "(MACD)");
        // this.lbWilliam.setText(lbArray[7][language]);
    }

    private void jbInit() throws Exception {
        border1 = BorderFactory.createLineBorder(SystemColor.controlText, 1);
        titledBorder1 = new TitledBorder(border1,
                "Relative Strength Index (RSI)");

        border2 = BorderFactory.createCompoundBorder(BorderFactory
                        .createEtchedBorder(Color.yellow, new Color(178, 140, 0)),
                BorderFactory.createEmptyBorder(0, 5, 0, 5));
        border3 = BorderFactory.createLineBorder(SystemColor.controlText, 1);
        titledBorder2 = new TitledBorder(border3, "William's %R");
        border4 = BorderFactory.createLineBorder(SystemColor.controlText, 1);
        titledBorder3 = new TitledBorder(border4, "Stochastic (STC)");
        border5 = BorderFactory.createLineBorder(SystemColor.controlText, 1);
        titledBorder4 = new TitledBorder(BorderFactory.createLineBorder(
                SystemColor.controlText, 1),
                "Moving Average Convergence Divergence (MACD)");
        JLabel1.setText("MA1");
        // this.setLayout(borderLayout1);

        JLabel6.setText("%K");
        tfRSI.setBackground(Color.white);
        tfRSI.setFont(new java.awt.Font("Arial", 0, 11));
        tfRSI.setBorder(BorderFactory.createLineBorder(Color.black));
        tfRSI.setText("");
        tfRSI.setColumns(5);
        tfSTCK.setBackground(Color.white);
        tfSTCK.setFont(new java.awt.Font("Arial", 0, 11));
        tfSTCK.setBorder(BorderFactory.createLineBorder(Color.black));
        tfSTCK.setText("");
        tfSTCK.setColumns(5);

        JLabel7.setText("%D");
        tfSTCD.setBackground(Color.white);
        tfSTCD.setFont(new java.awt.Font("Arial", 0, 11));
        tfSTCD.setBorder(BorderFactory.createLineBorder(Color.black));
        tfSTCD.setText("");
        tfSTCD.setColumns(5);
        btRSIApply.setBackground(Color.orange);
        btRSIApply.setFont(new java.awt.Font("Arial", 0, 11));
        btRSIApply.setBorder(border2);
        btRSIApply.setLabel("Apply");
        btRSIApply.addActionListener(new java.awt.event.ActionListener() {

            public void actionPerformed(ActionEvent e) {
                btRSIApply_actionPerformed(e);
            }
        });
        btRSIApply.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btRSIApply_actionPerformed(e);
            }
        });
        btSTCApply.setBackground(Color.orange);
        btSTCApply.setFont(new java.awt.Font("Arial", 0, 11));
        btSTCApply.setBorder(border2);
        btSTCApply.setLabel("Apply");
        btSTCApply.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btSTCApply_actionPerformed(e);
            }
        });

        JLabel10.setText("MA1");
        tfELMA.setBackground(Color.white);
        tfELMA.setFont(new java.awt.Font("Arial", 0, 11));
        tfELMA.setBorder(BorderFactory.createLineBorder(Color.black));
        tfELMA.setText("");
        tfELMA.setColumns(5);

        JLabel11.setText("MA2");
        tfESMA.setBackground(Color.white);
        tfESMA.setFont(new java.awt.Font("Arial", 0, 11));
        tfESMA.setBorder(BorderFactory.createLineBorder(Color.black));
        tfESMA.setText("");
        tfESMA.setColumns(5);
        tfEAMA.setBackground(Color.white);
        tfEAMA.setFont(new java.awt.Font("Arial", 0, 11));
        tfEAMA.setBorder(BorderFactory.createLineBorder(Color.black));
        tfEAMA.setToolTipText("");
        tfEAMA.setText("");
        tfEAMA.setColumns(5);
        btMACDApply.setBackground(Color.orange);
        btMACDApply.setFont(new java.awt.Font("Arial", 0, 11));
        btMACDApply.setBorder(border2);
        btMACDApply.setLabel("Apply");
        btMACDApply.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btMACDApply_actionPerformed(e);
            }
        });

        JLabel12.setText("MA3");
        this.setBackground(FConfig.DialogColor);
        lbWilliam.setText("William\'s %R");

        JLabel14.setText("%R");
        tfWR.setBackground(Color.white);
        tfWR.setFont(new java.awt.Font("Arial", 0, 11));
        tfWR.setBorder(BorderFactory.createLineBorder(Color.black));
        tfWR.setText("");
        tfWR.setColumns(5);
        btWilliamApply.setBackground(Color.orange);
        btWilliamApply.setFont(new java.awt.Font("Arial", 0, 11));
        btWilliamApply.setBorder(border2);
        btWilliamApply.setLabel("Apply");
        btWilliamApply.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btWilliamApply_actionPerformed(e);
            }
        });
        btCancel.setBackground(Color.orange);
        btCancel.setFont(new java.awt.Font("Arial", 0, 11));
        btCancel.setBorder(border2);
        btCancel.setLabel("Cancel");
        btCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btCancel_actionPerformed(e);
            }
        });
        btOK.setBackground(Color.orange);
        btOK.setFont(new java.awt.Font("Arial", 0, 11));
        btOK.setBorder(border2);
        btOK.setLabel("OK");
        btOK.addActionListener(new java.awt.event.ActionListener() {

            public void actionPerformed(ActionEvent e) {
                btOK_actionPerformed(e);
            }
        });
        btOK.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btOK_actionPerformed(e);
            }
        });
        jPanel1.setFont(new java.awt.Font("Arial", 0, 11));
        jPanel1.setBorder(titledBorder1);
        jPanel1.setBounds(new Rectangle(9, 10, 295, 56));
        jPanel2.setFont(new java.awt.Font("Arial", 0, 11));
        jPanel2.setBorder(titledBorder3);
        jPanel2.setBounds(new Rectangle(10, 73, 294, 56));
        jPanel3.setFont(new java.awt.Font("Arial", 0, 11));
        jPanel3.setBorder(titledBorder4);
        jPanel3.setBounds(new Rectangle(10, 196, 292, 56));
        jPanel4.setFont(new java.awt.Font("Arial", 0, 11));
        jPanel4.setBorder(titledBorder2);
        jPanel4.setBounds(new Rectangle(10, 134, 293, 56));
        jPanel6.setLayout(null);
        jPanel5.setBounds(new Rectangle(89, 254, 131, 31));
        jPanel6.setFont(new java.awt.Font("Arial", 0, 11));
        jPanel1.add(JLabel1, null);
        jPanel1.add(tfRSI, null);
        jPanel1.add(btRSIApply, null);
        jPanel6.add(jPanel2, null);
        this.getContentPane().add(jPanel6, BorderLayout.CENTER);
        jPanel2.add(JLabel6, null);
        jPanel2.add(tfSTCK, null);
        jPanel2.add(JLabel7, null);
        jPanel2.add(tfSTCD, null);
        jPanel2.add(btSTCApply, null);
        jPanel6.add(jPanel4, null);
        jPanel3.add(JLabel10, null);
        jPanel3.add(tfELMA, null);
        jPanel3.add(JLabel11, null);
        jPanel3.add(tfESMA, null);
        jPanel3.add(JLabel12, null);
        jPanel3.add(tfEAMA, null);
        jPanel3.add(btMACDApply, null);
        jPanel6.add(jPanel5, null);
        jPanel4.add(lbWilliam, null);
        jPanel4.add(JLabel14, null);
        jPanel4.add(tfWR, null);
        jPanel4.add(btWilliamApply, null);
        jPanel6.add(jPanel3, null);
        jPanel5.add(btOK, null);
        jPanel5.add(btCancel, null);
        jPanel6.add(jPanel1, null);
        jPanel5.setBackground(FConfig.ScreenBackground);
        jPanel6.setBackground(FConfig.ScreenBackground);
    }

    void btOK_actionPerformed(ActionEvent e) {
        this.btWilliamApply_actionPerformed(null);
        this.btMACDApply_actionPerformed(null);
        this.btRSIApply_actionPerformed(null);
        this.btSTCApply_actionPerformed(null);
        this.dispose();
    }

    void btCancel_actionPerformed(ActionEvent e) {

        this.dispose();
    }

    void btRSIApply_actionPerformed(ActionEvent e) {
        if (!FormatUtil.isNumerical(tfRSI.getText())) {
            updateSetting();
            return;
        }
        ChartItem taChart = chartScreen.getChart(TAChartName);
        if (taChart == null) {
            return;
        }

        int RSI = Integer.parseInt(tfRSI.getText());

        taChart.getChartData().getfTAconfig().RSIPeriod = RSI;
        if (taChart.getChartType() == ChartType.RSI) {
            taChart.getChartData()
                    .calculateRSI(taChart.getChartData().getfTAconfig().RSIPeriod);
            chartScreen.updateBaseScreen();
            chartScreen.repaint();
        }

    }

    void btMACDApply_actionPerformed(ActionEvent e) {
        if (!FormatUtil.isNumerical(tfEAMA.getText())
                || !FormatUtil.isNumerical(this.tfESMA.getText())
                || !FormatUtil.isNumerical(this.tfELMA.getText())) {
            updateSetting();
            return;
        }
        ChartItem taChart = chartScreen.getChart(TAChartName);
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
            chartScreen.updateBaseScreen();
            chartScreen.repaint();
        }

    }

    void btSTCApply_actionPerformed(ActionEvent e) {
        if (!FormatUtil.isNumerical(tfSTCK.getText())
                || !FormatUtil.isNumerical(this.tfSTCD.getText())) {
            updateSetting();
            return;
        }
        ChartItem taChart = chartScreen.getChart(TAChartName);
        if (taChart == null) {
            return;
        }

        int K = Integer.parseInt(this.tfSTCK.getText());
        int D = Integer.parseInt(this.tfSTCD.getText());

        taChart.getChartData().getfTAconfig().STCDPeriod = D;
        taChart.getChartData().getfTAconfig().STCKPeriod = K;
        if (taChart.getChartType() == ChartType.STC) {
            taChart.getChartData().calculateSTC(K, D);
            chartScreen.updateBaseScreen();
            chartScreen.repaint();
        }
    }

    void btWilliamApply_actionPerformed(ActionEvent e) {
        if (!FormatUtil.isNumerical(tfWR.getText())) {
            updateSetting();
            return;
        }
        ChartItem taChart = chartScreen.getChart(TAChartName);
        if (taChart == null) {
            return;
        }

        int R = Integer.parseInt(tfWR.getText());

        taChart.getChartData().getfTAconfig().WilliamPeriod = R;
        if (taChart.getChartType() == ChartType.WILLIAM_R) {
            taChart.getChartData().calculateWilliamR(R);
            chartScreen.updateBaseScreen();
            chartScreen.repaint();
        }
    }

    void updateSetting() {
        ChartItem currentChart = chartScreen.getChart(TAChartName);
        if (currentChart == null) {
            System.out.println("NO chart found in setting");
            return;
        }

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

    void setChartScreen(ChartScreen cs) {
        chartScreen = cs;
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
    }

    public void windowIconified(WindowEvent e) {
    }

    public void windowDeiconified(WindowEvent e) {
    }

    public void windowActivated(WindowEvent e) {
    }

    public void windowDeactivated(WindowEvent e) {
    }

}
