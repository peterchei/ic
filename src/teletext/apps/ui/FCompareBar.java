package teletext.apps.ui;

import teletext.apps.core.AxisType;
import teletext.apps.core.ChartScreen;
import teletext.apps.core.ChartItem;
import teletext.apps.core.ChartType;
import teletext.data.ChartData;
import teletext.data.ChartDataService;
import teletext.data.ChartDataServiceListener;
import teletext.data.FCommand;
import teletext.util.FormatUtil;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Vector;

public class FCompareBar extends TabBar implements KeyListener, ChartDataServiceListener {
    /**
	 * 
	 */
	private static final long serialVersionUID = -5350551016952212042L;
	JTextField tfCode = new JTextField();
    JButton btAddChart = new JButton();
    JButton btRemove = new JButton();
    JComboBox<String> chACode = new JComboBox<String>();
    JButton btClose = new JButton();

    private Vector<ChartItem> pcCharts = new Vector<ChartItem>();

    private final Color lineColor[] = {
        new Color(1, 255, 255),
        new Color(1, 255, 37),
        new Color(228, 225, 1),
        new Color(225, 132, 1),
        new Color(225, 132, 1),
        new Color(255, 1, 67),
        new Color(255, 1, 228),
        new Color(191, 1, 255),
        new Color(1, 49, 255),
    };


    private final String lbArray[][] = {
        {"Add", "\u52a0\u5165"},
        {"Remove", "\u79fb\u9664"},
        {"Close", "\u95dc\u9589"}
    };

    final String lbCodeArray[][] = {
        {"0001", "Please Select", "Please Select"},
        {"5001", "Hang seng index", "嚙踝蕭肏嚙踝蕭"},
        {"5051", "HSI Future (current month)", "嚙踝蕭芫嚙踝蕭f嚙踝蕭嚙�嚙磐嚙踝蕭)"},
        {"5052", "HSI Future (next month)", "嚙踝蕭芫嚙踝蕭f嚙踝蕭嚙�嚙磊嚙踝蕭)"},
        {"5002", "HSI finance", "嚙踝蕭耵嚙踝蕭蘆悗嚙踝蕭"},
        {"5003", "HSI-UTILITIES", "嚙踝蕭秅嚙踝蕭峈悗嚙踝蕭"},
        {"5004", "HSI-PROPERTIES", "嚙踝蕭穻a嚙踝蕭嚙踝蕭嚙"},
        {"5005", "HSI-COM & IND", "嚙踝蕭秅u嚙諉股恬蕭"},
        {"5006", "HANG SENG C C I", "嚙踝蕭肮嚙踝蕭w嚙踝蕭"},
        {"5007", "HANG SENG C E I", "嚙踝蕭肭嚙踝蕭嚙踝蕭"},
        {"5008", "HANG SENG MIDCAP", "嚙踝蕭秅嚙踝蕭嚙踝蕭悗嚙踝蕭"},
        {"5009", "HANG SENG 100", "嚙踝蕭秅@嚙褊恬蕭"},
        {"5020", "GEM INDEX", "嚙請業嚙瞌嚙踝蕭"},
        {"2800", "TRACKER FUND", "嚙調富嚙踝蕭"}
    };

    //the reference of chartscreens which this buttonbar can control it.
    private ChartScreen chartScreen1 = null;
    private ChartScreen chartScreen2 = null;
    private ChartScreen chartScreen3 = null;
    private FButtonBar fButtonBar = null;
    private FMenuBar fMenuBar = null;

    private int language = FConfig.constEnglish;


    public void setMenus(FButtonBar fb, FMenuBar fm) {
        fButtonBar = fb;
        fMenuBar = fm;
    }


    public void setLanguage(int tlanguage) {
        language = tlanguage;
        btAddChart.setLabel(lbArray[0][language]);
        btRemove.setLabel(lbArray[1][language]);
        btClose.setLabel(lbArray[2][language]);

    }

    //set the reference of the chartScrenn such that the button bar can control it.
    public void setChartScreen(ChartScreen cs1, ChartScreen cs2, ChartScreen cs3) {
        chartScreen1 = cs1;
        chartScreen2 = cs2;
        chartScreen3 = cs3;
    }


    public FCompareBar() {
        try {
            jbInit();
            setLanguage(language);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void jbInit() throws Exception {
        tfCode.setBackground(Color.white);
        tfCode.setBorder(BorderFactory.createLineBorder(Color.black));
        tfCode.setBounds(new Rectangle(10, 3, 56, 20));
        this.setLayout(null);
        btAddChart.setFont(new Font("Dialog", 0, 10));
        btAddChart.setAlignmentX((float) 0.0);
        btAddChart.setAlignmentY((float) 0.0);
        btAddChart.setBorder(BorderFactory.createLineBorder(Color.black));
        btAddChart.setLabel("Add");
        btAddChart.setBounds(new Rectangle(69, 3, 55, 20));
        btAddChart.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btAddChart_actionPerformed(e);
            }
        });
        btRemove.setFont(new Font("Dialog", 0, 10));
        btRemove.setAlignmentX((float) 0.0);
        btRemove.setAlignmentY((float) 0.0);
        btRemove.setBorder(BorderFactory.createLineBorder(Color.black));
        btRemove.setLabel("Remove");
        btRemove.setBounds(new Rectangle(125, 3, 66, 20));
        btRemove.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btRemove_actionPerformed(e);
            }
        });
        chACode.setFont(new Font("Dialog", 0, 10));
        chACode.setBorder(BorderFactory.createLineBorder(Color.black));
        chACode.setBounds(new Rectangle(192, 3, 76, 19));
        btClose.setFont(new Font("Dialog", 0, 10));
        btClose.setAlignmentY((float) 0.0);
        btClose.setBorder(BorderFactory.createLineBorder(Color.black));
        btClose.setLabel("Close");
        btClose.setBounds(new Rectangle(270, 3, 55, 20));
        btClose.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btClose_actionPerformed(e);
            }
        });
        this.add(tfCode, null);
        this.add(btAddChart, null);
        this.add(btRemove, null);
        this.add(chACode, null);
        this.add(btClose, null);
        tfCode.addKeyListener(this);
        //chACode.setVisible(false);
        //chSpChart.setVisible(false);


    }

    void btClose_actionPerformed(ActionEvent e) {
        if (fMenuBar != null && chartScreen1 != null) {
            ChartItem taChart = chartScreen1.getChart("TA1Chart");
            if (taChart != null) {
                String chTa = (String) fMenuBar.chMA1.getSelectedItem();

                //if TA chart is invisible......
                if (chTa == fMenuBar.lbTA1Array[0][0] || chTa == fMenuBar.lbTA1Array[0][1]) {
                    taChart.setVisible(false);
                } else {
                    taChart.setVisible(true);              // set TA chart to be visible return to normal state.
                }
            }

            this.setVisible(false);
            fMenuBar.setVisible(true);
            chACode.removeAllItems();
            ChartItem cchart = chartScreen1.getLeftChart();
            if (cchart != null) {
            	switch (fMenuBar.chChartType.getSelectedIndex()){
            
            	case 0:
            		cchart.chartType = ChartType.BAR;
            		break;
            	case 1:
            		cchart.chartType = ChartType.CANDLE;
            		break;
            	case 2:
            		cchart.chartType = ChartType.LINE;
            		break;
            	case 3:            		
            		cchart.chartType = ChartType.BAR;
            		break;
            	}
                //fMenuBar.chChartType.getSelectedIndex();
            }
            pcCharts.removeAllElements();
            chartScreen1.getAction().lineRecords.removeAllElements();
            chartScreen1.getAction().goldenPartitionLine = null;

            chartScreen1.removeChartsByType(ChartType.PERCENTAGE);
            chartScreen1.updateBaseScreen();
            chartScreen1.repaint();


        }
    }

    /*** Invoked when a key has been pressed.*/
    public void keyPressed(KeyEvent e) {
        //System.out.println("keyevent" + e);
        //System.out.println("keytyped");
        // System.out.println(e.getKeyCode());

        //e.setKeyChar('a');
        if (e.getKeyCode() == 10) {
            //   System.out.println("Enter");
            btAddChart_actionPerformed(null);
        }

    }

    /*** Invoked when a key has been released.*/
    public void keyReleased(KeyEvent e) {
    }

    public void keyTyped(KeyEvent e) {
    }

    void btAddChart_actionPerformed(ActionEvent e) {
        System.out.println("add action");

        if (fMenuBar == null  || chartScreen1.getLeftChart() == null) return;

        String cc = tfCode.getText();

        if (!FormatUtil.isNumerical(cc)) {
            this.tfCode.setText("");
            return;
        }

        int Code = Integer.parseInt(cc);
        this.tfCode.setText("");
        if (Code != chartScreen1.getLeftChart().chartData.Code) {
            int intervals = chartScreen1.getLeftChart().chartData.intradayInterval;
            int NumberOfPoints = chartScreen1.getLeftChart().chartData.data.size();
            FCommand fc = new FCommand(Code, FCommand.TYPE_DOWNLOAD_LEFT_CHART, fMenuBar.chDuration.getSelectedIndex(), String.valueOf(Code), NumberOfPoints, intervals, true, this);
            ChartDataService.getInstance().addCommand(fc);
            chartScreen1.setScreenState(ChartScreen.LOADING);
        }


    }

    public void OnReceivedError(FCommand fc) {
        System.out.println("Error");
        chartScreen1.setScreenState(ChartScreen.STARTED);
    }

    public void OnReceivedChartData(FCommand fc, Object result) {

        ChartData chartData = (ChartData) result;
        if (chartScreen1.getChart(String.valueOf(fc.getCode())) != null) {
            chartScreen1.setScreenState(ChartScreen.STARTED);
            return;
        }

        ChartData mydata = chartData;


        ChartItem mychart1 = new ChartItem(mydata, String.valueOf(fc.getCode()));
        mychart1.axisBar = AxisType.NONE;
        mychart1.chartType = ChartType.PERCENTAGE;
        mychart1.setShowXaxis(false);
        mychart1.firstColor = lineColor[pcCharts.size() % lineColor.length];


        chartScreen1.addChart(mychart1);
        chartScreen1.getAction().zoomRecords.removeAllElements();
        chartScreen1.getAction().lineRecords.removeAllElements();
        chartScreen1.updateBaseScreen();
        chartScreen1.setScreenState(ChartScreen.STARTED);
        pcCharts.addElement(mychart1);
        chACode.removeAllItems();
        for (int i = 0; i < pcCharts.size(); i++) {
            ChartItem cchart = (ChartItem) pcCharts.elementAt(i);
            chACode.addItem(String.valueOf(cchart.chartData.Code));
        }


    }

    void btRemove_actionPerformed(ActionEvent e) {
        if (chACode.getItemCount() == 0) return;
        pcCharts.removeElementAt(chACode.getSelectedIndex());
        chartScreen1.removeChart(Integer.parseInt((String) chACode.getSelectedItem()));
        chACode.removeAllItems();
        for (int i = 0; i < pcCharts.size(); i++) {
            ChartItem cchart = (ChartItem) pcCharts.elementAt(i);
            chACode.addItem(String.valueOf(cchart.chartData.Code));
        }
        chartScreen1.updateBaseScreen();
        chartScreen1.repaint();

    }


        public void OnProgress(int percent) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
