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

public class SettingDialog extends JDialog implements WindowListener {


    private final JLabel JLabelx1 = new JLabel();
    private final JTextField tfRSI = new JTextField();
    private final JLabel jlabelx6 = new JLabel();
    private final JTextField tfSTCK = new JTextField();
    private final JLabel jlabelx7 = new JLabel();
    private final JTextField tfSTCD = new JTextField();
    private final JButton btRSIApply = new JButton();
    private final JButton btSTCApply = new JButton();
    private final JLabel jlabely1 = new JLabel();
    private final JTextField tfELMA = new JTextField();
    private final JLabel jlabel11y = new JLabel();
    private final JTextField tfESMA = new JTextField();
    private final JTextField tfEAMA = new JTextField();
    private final JButton btMACDApply = new JButton();
    private final JLabel jlabelx2 = new JLabel();
    private final JLabel lbWilliam = new JLabel();
    private final JLabel jlabelx4 = new JLabel();
    private final JTextField tfWR = new JTextField();
    private final JButton btWilliamApply = new JButton();
    // JButton btCancel = new JButton();
    // JButton btOK = new JButton();
    private final JPanel jp1 = new JPanel();
    private final JPanel jp2 = new JPanel();
    private final JPanel jp3 = new JPanel();
    private final JPanel jp4 = new JPanel();
    private final JPanel jp5 = new JPanel();
    private Border b1;
    private TitledBorder tb1;
    private Border b2;
    private Border b3;
    private TitledBorder tb2;
    private Border b4;
    private TitledBorder tb3;
    private Border b5;
    private TitledBorder tb4;
    private final JPanel jp6 = new JPanel();
    BorderLayout bl1 = new BorderLayout();
    // the reference of chart and the chartscreen
    //  private String TAChartName = new String("TA1Chart"); // the chart Name (id)
    // that this setting
    // window can
    // control
    private int language = FConfig.constEnglish;


    private final JLabel JLabel1 = new JLabel();
    private final JTextField tfSMA1 = new JTextField();
    private final JLabel JLabel3 = new JLabel();
    private final JTextField tfSMA2 = new JTextField();
    private final JLabel JLabel5 = new JLabel();
    private final JTextField tfSMA3 = new JTextField();
    private final JLabel JLabel6 = new JLabel();
    private final JTextField tfWMA1 = new JTextField();
    private final JLabel JLabel7 = new JLabel();
    private final JTextField tfWMA2 = new JTextField();
    private final JLabel JLabel8 = new JLabel();
    private final JTextField tfWMA3 = new JTextField();
    private final JButton btSMAApply = new JButton();
    private final JButton btWMAApply = new JButton();
    private final JLabel JLabel10 = new JLabel();
    private final JTextField tfEMA1 = new JTextField();
    private final JLabel JLabel11 = new JLabel();
    private final JTextField tfEMA2 = new JTextField();
    private final JTextField tfEMA3 = new JTextField();
    private final JButton btEMAApply = new JButton();
    private final JLabel JLabel12 = new JLabel();
    private final JLabel JLabel14 = new JLabel();
    private final JTextField tfBB = new JTextField();
    private final JLabel JLabel15 = new JLabel();
    private final JTextField tfDevation = new JTextField();
    private final JButton btBollingerApply = new JButton();
    private final JButton btCancel = new JButton();
    private final JButton btOK = new JButton();
    private final JPanel jPanel1 = new JPanel();
    private final BorderLayout borderLayout1 = new BorderLayout();
    private final JPanel jPanel3 = new JPanel();
    private Border border1;
    private TitledBorder titledBorder1;
    private Border border2;
    private final JPanel jPanel4 = new JPanel();
    private Border border3;
    private TitledBorder titledBorder2;
    private final JPanel jPanel5 = new JPanel();
    private Border border4;
    private TitledBorder titledBorder3;
    private final JPanel jPanel6 = new JPanel();
    private Border border5;
    private TitledBorder titledBorder4;
    private Border border6;
    private final JPanel jPanel2 = new JPanel();
    // the reference of chart and the chartscreen
    private String TAChartName = new String("TA1Chart"); // the chart Name (id) that this setting window can control
    private ChartScreen chartScreen = null;
    private ChartScreen chartScreen2 = null;

    public SettingDialog(Frame frame) {
        super(frame);
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
        btSMAApply.setText("Apply");
        btWMAApply.setBackground(Color.orange);
        btWMAApply.setFont(new java.awt.Font("Arial", 0, 11));
        btWMAApply.setBorder(border2);
        btWMAApply.setBorderPainted(true);
        btWMAApply.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btWMAApply_actionPerformed(e);
            }
        });
        btWMAApply.setText("Apply");

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
        btEMAApply.setText("Apply");
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
        btBollingerApply.setText("Apply");
        btCancel.setBackground(Color.orange);
        btCancel.setFont(new java.awt.Font("Arial", 0, 11));
        btCancel.setBorder(border2);
        btCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btCancel_actionPerformed(e);
            }
        });
        btCancel.setText("Cancel");
        btOK.setBackground(Color.orange);
        btOK.setFont(new java.awt.Font("Arial", 0, 11));
        btOK.setBorder(border2);
        btOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btOK_actionPerformed(e);
            }
        });
        btOK.setText("OK");

        jPanel1.setBackground(FConfig.ScreenBackground);
        jPanel1.setBorder(BorderFactory.createEtchedBorder());
        jPanel1.setLayout(null);


        jPanel3.setFont(new java.awt.Font("Arial", 0, 11));
        jPanel3.setBorder(titledBorder1);
       
        jPanel3.setBounds(new Rectangle(10, 80, 350, 56));
        jPanel4.setFont(new java.awt.Font("Arial", 0, 11));
        jPanel4.setBorder(titledBorder2);
        jPanel4.setBounds(new Rectangle(10, 200, 350, 56));

        jPanel5.setFont(new java.awt.Font("Arial", 0, 11));
        jPanel5.setBorder(titledBorder3);
        jPanel5.setBounds(new Rectangle(10, 140, 350, 56));
        jPanel6.setFont(new java.awt.Font("Arial", 0, 11));
        jPanel6.setBorder(titledBorder4);
        jPanel6.setBounds(new Rectangle(10, 20, 350, 56));

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


        b1 = BorderFactory.createLineBorder(SystemColor.controlText, 1);
        tb1 = new TitledBorder(b1,
                "Relative Strength Index (RSI)");

        b2 = BorderFactory.createCompoundBorder(BorderFactory
                        .createEtchedBorder(Color.yellow, new Color(178, 140, 0)),
                BorderFactory.createEmptyBorder(0, 5, 0, 5));
        b3 = BorderFactory.createLineBorder(SystemColor.controlText, 1);
        tb2 = new TitledBorder(b3, "William's %R");
        b4 = BorderFactory.createLineBorder(SystemColor.controlText, 1);
        tb3 = new TitledBorder(b4, "Stochastic (STC)");
        b5 = BorderFactory.createLineBorder(SystemColor.controlText, 1);
        tb4 = new TitledBorder(BorderFactory.createLineBorder(
                SystemColor.controlText, 1),
                "Moving Average Convergence Divergence (MACD)");
        JLabelx1.setText("MA1");
        // this.setLayout(bl1);

        jlabelx6.setText("%K");
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

        jlabelx7.setText("%D");
        tfSTCD.setBackground(Color.white);
        tfSTCD.setFont(new java.awt.Font("Arial", 0, 11));
        tfSTCD.setBorder(BorderFactory.createLineBorder(Color.black));
        tfSTCD.setText("");
        tfSTCD.setColumns(5);
        btRSIApply.setBackground(Color.orange);
        btRSIApply.setFont(new java.awt.Font("Arial", 0, 11));
        btRSIApply.setBorder(b2);
        btRSIApply.setText("Apply");
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
        btSTCApply.setBorder(b2);
        btSTCApply.setText("Apply");
        btSTCApply.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btSTCApply_actionPerformed(e);
            }
        });

        jlabely1.setText("MA1");
        tfELMA.setBackground(Color.white);
        tfELMA.setFont(new java.awt.Font("Arial", 0, 11));
        tfELMA.setBorder(BorderFactory.createLineBorder(Color.black));
        tfELMA.setText("");
        tfELMA.setColumns(5);

        jlabel11y.setText("MA2");
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
        btMACDApply.setBorder(b2);
        btMACDApply.setText("Apply");
        btMACDApply.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btMACDApply_actionPerformed(e);
            }
        });

        jlabelx2.setText("MA3");
        this.setBackground(FConfig.DialogColor);
        lbWilliam.setText("William\'s %R");

        jlabelx4.setText("%R");
        tfWR.setBackground(Color.white);
        tfWR.setFont(new java.awt.Font("Arial", 0, 11));
        tfWR.setBorder(BorderFactory.createLineBorder(Color.black));
        tfWR.setText("");
        tfWR.setColumns(5);
        btWilliamApply.setBackground(Color.orange);
        btWilliamApply.setFont(new java.awt.Font("Arial", 0, 11));
        btWilliamApply.setBorder(b2);
        btWilliamApply.setText("Apply");
        btWilliamApply.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btWilliamApply_actionPerformed(e);
            }
        });
        btCancel.setBackground(Color.orange);
        btCancel.setFont(new java.awt.Font("Arial", 0, 11));
        btCancel.setBorder(b2);
        btCancel.setText("Cancel");
        btCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btCancel_actionPerformed(e);
            }
        });
        btOK.setBackground(Color.orange);
        btOK.setFont(new java.awt.Font("Arial", 0, 11));
        btOK.setBorder(b2);
        btOK.setText("OK");
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
        jp1.setFont(new java.awt.Font("Arial", 0, 11));
        jp1.setBorder(tb1);
        jp1.setBounds(new Rectangle(10, 260, 350, 56));
        jp2.setFont(new java.awt.Font("Arial", 0, 11));
        jp2.setBorder(tb3);
        jp2.setBounds(new Rectangle(10, 320, 350, 56));
        jp3.setFont(new java.awt.Font("Arial", 0, 11));
        jp3.setBorder(tb4);
        jp3.setBounds(new Rectangle(10, 380, 350, 56));
        jp4.setFont(new java.awt.Font("Arial", 0, 11));
        jp4.setBorder(tb2);
        jp4.setBounds(new Rectangle(10, 440, 350, 56));
        jp6.setLayout(null);
        jp5.setBounds(new Rectangle(10, 500, 350, 56));
        jPanel1.setFont(new java.awt.Font("Arial", 0, 11));
        jp1.add(JLabelx1, null);
        jp1.add(tfRSI, null);
        jp1.add(btRSIApply, null);
        jPanel1.add(jp2, null);

        // this.getContentPane().add(jp6, BorderLayout.CENTER);
        jp2.add(jlabelx6, null);
        jp2.add(tfSTCK, null);
        jp2.add(jlabelx7, null);
        jp2.add(tfSTCD, null);
        jp2.add(btSTCApply, null);
        jPanel1.add(jp4, null);
        jp3.add(jlabely1, null);
        jp3.add(tfELMA, null);
        jp3.add(jlabel11y, null);
        jp3.add(tfESMA, null);
        jp3.add(jlabelx2, null);
        jp3.add(tfEAMA, null);
        jp3.add(btMACDApply, null);
        jPanel1.add(jp5, null);
        jp4.add(lbWilliam, null);
        jp4.add(jlabelx4, null);
        jp4.add(tfWR, null);
        jp4.add(btWilliamApply, null);
        jPanel1.add(jp3, null);
        jp5.add(btOK, null);
        jp5.add(btCancel, null);
        jPanel1.add(jp1, null);

        this.add(jPanel1, BorderLayout.CENTER);

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
        if (!FormatUtil.isNumerical(tfRSI.getText())) {
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
        if (!FormatUtil.isNumerical(tfEAMA.getText())
                || !FormatUtil.isNumerical(this.tfESMA.getText())
                || !FormatUtil.isNumerical(this.tfELMA.getText())) {
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
        if (!FormatUtil.isNumerical(tfSTCK.getText())
                || !FormatUtil.isNumerical(this.tfSTCD.getText())) {
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
        if (!FormatUtil.isNumerical(tfWR.getText())) {
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


    private void btCancel_actionPerformed(ActionEvent e) {

        this.dispose();
    }

    private void btSMAApply_actionPerformed(ActionEvent e) {

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

    private void btEMAApply_actionPerformed(ActionEvent e) {

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
        ((ChartScreen) chartScreen).updateBaseScreen();
        ((ChartScreen) chartScreen).repaint();

    }

    private void btBollingerApply_actionPerformed(ActionEvent e) {
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
        chartScreen2=cs2;
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
