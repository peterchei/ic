package com.ic.app;

import com.ic.core.ChartScreen;
import com.ic.core.FConfig;
import com.ic.data.ChartDataService;
import com.ic.data.CommandType;
import com.ic.data.RequestCommand;
import com.ic.gui.STVChart;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.logging.Level;
import java.util.logging.Logger;


public class GUI extends JFrame implements WindowListener {

  public static Logger log = Logger.getLogger(GUI.class.getName());

  private final BorderLayout borderLayout1 = new BorderLayout();

  public GUI() {
    try {
      configure();
    } catch (Exception e) {
      log.warning(e.getMessage());
      log.log(Level.WARNING, "Fatal error when launching IC.", e);
      System.exit(1);
    }
  }

  public static void main(String[] args) {
    Controller.getInstance().launch();
  }

  public void launch() {
    setVisible(true);
    setBounds(0, 0, 1280, 960);
    setResizable(true);
    setTitle("IC - Stock Chart");
  }

  public void setChartPanel(STVChart pnChart) {
    JPanel glassPanel = new JPanel();


    ImageIcon icon1 = loadImageIcon("/expand.png");


    JButton expandButton = new JButton();

    expandButton.setIcon(icon1);


    glassPanel.setLayout(new GridLayout(1, 1));
    expandButton.setHorizontalAlignment(SwingConstants.CENTER);
    expandButton.setVerticalAlignment(SwingConstants.CENTER);
    glassPanel.add(expandButton);


    expandButton.setEnabled(false);

    setGlassPane(glassPanel);

    addComponentListener(new ComponentAdapter() {
      @Override
      public void componentResized(ComponentEvent e) {
        super.componentResized(e);
        getGlassPane().setVisible(GUI.this.getBounds().width <= 300 || GUI.this.getBounds().height <= 300);
      }
    });

    this.getContentPane().add(pnChart, BorderLayout.CENTER);
  }

  private ImageIcon loadImageIcon(String path) {
    java.net.URL url = getClass().getResource(path);
    if (url != null) {
      return new ImageIcon(url);
    } else {
      log.warning("Image not found: " + path);
      return null;
    }
  }

  private Image loadImage(String path) {
    ImageIcon icon = loadImageIcon(path);
    return icon != null ? icon.getImage() : null;
  }

  private void configure() {
    this.getContentPane().setLayout(borderLayout1);

    STVChart coreChart = new STVChart();

    coreChart.chartOptionBar1.btTA.setButtonImage(loadImage("/chartTypeA.png"));
    coreChart.chartOptionBar1.btVolume.setButtonImage(loadImage("/chartTypeC.png"));
    coreChart.btFacebookShare.setButtonImage(loadImage("/facebook.png"));
    coreChart.getBtPrinter().setButtonImage(loadImage("/print.png"));
    coreChart.fCompareBar.getCloseButton().setButtonImage(loadImage("/exit.png"));
    coreChart.fCompareBar.getAddButton().setButtonImage(loadImage("/add.png"));
    coreChart.fCompareBar.getRemoveButton().setButtonImage(loadImage("/minus.png"));
    Image iconImage = loadImage("/ICChart.png");
    if (iconImage != null) {
      setIconImage(iconImage);
    }


    // Init the chart screen
    coreChart.chartScreen1.initScreen();
    coreChart.chartScreen2.initScreen();
    coreChart.chartScreen3.initScreen();

    ChartDataService.getInstance().enable();

    // add a chart data
    RequestCommand fc = new RequestCommand("IBM", RequestCommand.TYPE_DOWNLOAD_LEFT_CHART,
      (CommandType) coreChart.fmenuBar.chDuration.getSelectedItem(), "LMain1", 500, 1, false, coreChart.fmenuBar);

    ChartDataService.getInstance().addCommand(fc);
    coreChart.chartScreen1.setScreenState(ChartScreen.LOADING);
    coreChart.chartScreen2.setScreenState(ChartScreen.LOADING);
    coreChart.chartScreen3.setScreenState(ChartScreen.LOADING);

    // change the language by calling set language function
    coreChart.setLanguage(FConfig.constEnglish);
    this.setChartPanel(coreChart);
    this.addWindowListener(this);
  }

  public void windowClosing(WindowEvent e) {
    this.dispose();
    System.exit(0);
  }

  public void windowOpened(WindowEvent e) {
    log.finest("opened");
  }

  public void windowClosed(WindowEvent e) {
    log.finest("closed");
  }

  public void windowIconified(WindowEvent e) {
    log.finest("iconified");
  }

  public void windowDeiconified(WindowEvent e) {
    log.finest("deiconified");
  }

  public void windowActivated(WindowEvent e) {
    log.finest("activated");
  }

  public void windowDeactivated(WindowEvent e) {
    log.finest("deactivated");
  }


}