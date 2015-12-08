package com.ic.gui;

import com.ic.core.ChartItem;
import com.ic.core.ChartScreen;
import com.ic.core.ChartType;
import com.ic.core.FConfig;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;


public class TAMenu extends JPanel {


    static final String lbArray[][] = {//{"None","\u7121"}     //0
            {"RSI", "相對強弱指數"}       //0
            , {"STC", "隨機指數"}       //1
            , {"OBV", "\u6210\u4ea4\u91cf\u5e73 \u6307\u6578"}       //2
            , {"MACD", "\u79fb\u52d5\u5e73\u5747\u7dda\u532f\u805a\u80cc\u99b3\u6307\u6a19"}     //3
            , {"William %R", "\u5a01\u5ec9\u6307\u6a19"}  //4
    };
    private static final long serialVersionUID = -4589991589319998628L;
    public JComboBox chChartType = new JComboBox();
    public ImageButton btSetting = new ImageButton();

    private int language = FConfig.constEnglish;
    private ChartScreen chartScreen = null;
    private TASettingDialog TASetting = new TASettingDialog();

    public TAMenu() {
        try {
            jbInit();
            this.chChartType.addItemListener(new ItemListener() {
                public void itemStateChanged(ItemEvent e) {
                    chChartType_itemStateChanged(e);
                }
            });
            updateMenu();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setChartScreen(ChartScreen cs) {
        chartScreen = cs;
    }

    private void jbInit() throws Exception {
        this.setLayout(null);
        chChartType.setFont(new Font("Dialog", 0, 10));
        chChartType.setBorder(BorderFactory.createLineBorder(Color.black));
        chChartType.setBounds(new Rectangle(8, 3, 82, FConfig.BUTTON_SIZE));
        btSetting.setBounds(new Rectangle(91, 1, FConfig.BUTTON_SIZE, FConfig.BUTTON_SIZE));
        btSetting.setLabel("fImageButton1");
        btSetting.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btSetting_actionPerformed(e);
            }
        });
        //  btSetting.addActionListener(new FTAMenu_btSetting_actionAdapter(this));
        btSetting.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                btSetting_actionPerformed(e);
            }
        });
        btSetting.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                btSetting_actionPerformed(e);
            }
        });
        this.add(chChartType, null);
        this.add(btSetting, null);
        chChartType.setBackground(Color.white);
    }

    public void updateMenu() {
        setLanguage(language);
    }

    //change language
    public void setLanguage(int tlanguage) {
        language = tlanguage;

        int selectedIndex;

        selectedIndex = chChartType.getSelectedIndex();
        //   System.out.println("Select " + select);
        chChartType.removeAllItems();
        for (int i = 0; i < lbArray.length; i++)
            chChartType.addItem(lbArray[i][language]);

        if (selectedIndex >= 0) chChartType.setSelectedIndex(selectedIndex);
        TASetting.setLanguage(language);

    }

    public void chChartType_itemStateChanged(ItemEvent e) {

        if (chartScreen == null) return;
        ChartItem lchart = chartScreen.getLeftChart(); // The TA chart....
        if (lchart == null) return;

        String cType = (String) chChartType.getSelectedItem(); // get the ITEM we select.

        //below : process the ITEM
        if (cType == lbArray[0][0] || cType == lbArray[0][1]) {
            //System.out.println("RSI Selected");
            lchart.setChartType(ChartType.RSI);
            lchart.getChartData().calculateRSI(lchart.getChartData().getfTAconfig().RSIPeriod);
        } else if (cType == lbArray[1][0] || cType == lbArray[1][1]) {
            //System.out.println("STC selected");
            lchart.setChartType(ChartType.STC);
            lchart.getChartData().calculateSTC(lchart.getChartData().getfTAconfig().STCKPeriod, lchart.getChartData().getfTAconfig().STCDPeriod);
        } else if (cType == lbArray[2][0] || cType == lbArray[2][1]) {
            lchart.setChartType(ChartType.OBV);
            // lchart.chartData.calculateOBV();
        } else if (cType == lbArray[3][0] || cType == lbArray[3][1]) {
            lchart.setChartType(ChartType.MACD);
            lchart.getChartData().calculateMACD(lchart.getChartData().getfTAconfig().MACDLEMA, lchart.getChartData().getfTAconfig().MACDSEMA, lchart.getChartData().getfTAconfig().MACDAEMA);
        } else if (cType == lbArray[4][0] || cType == lbArray[4][1]) {
            lchart.setChartType(ChartType.WILLIAM_R);
            lchart.getChartData().calculateWilliamR(lchart.getChartData().getfTAconfig().WilliamPeriod);
        }
        chartScreen.updateBaseScreen();
        chartScreen.repaint();

    }

    void btSetting_actionPerformed(ActionEvent e) {
        TASetting.setChartScreen(chartScreen);
        TASetting.setTAChartName("TA1Chart");  // tell the setting window that which chart he can control
        TASetting.updateSetting();
        TASetting.setTitle("FME TA Chart Setting Window");
        TASetting.setResizable(false);
        TASetting.setBounds(0, 0, 320, 320);
        TASetting.show();
        //TASetting.pack();
    }
}

