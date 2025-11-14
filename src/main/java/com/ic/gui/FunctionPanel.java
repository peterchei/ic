package com.ic.gui;

import com.ic.app.Controller;
import com.ic.core.*;
import com.ic.data.FRecord;
import com.ic.util.CopyImageToClipBoard;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * Core function panel
 */
public class FunctionPanel extends JPanel implements ScreenActionListener {

  private final ImageButton btNone = new ImageButton();
  private final ImageButton btWatch = new ImageButton();
  private final ImageButton btZoomIn = new ImageButton();
  private final ImageButton btZoomOut = new ImageButton();
  private final ImageButton btMove = new ImageButton();
  private final ImageButton btInsertLine = new ImageButton();
  private final ImageButton btInsertPLine = new ImageButton();
  private final ImageButton btGPartition = new ImageButton();
  private final ImageButton btRemoveLine = new ImageButton();
  private final ImageButton btClear = new ImageButton();
  private final ImageButton btCompare = new ImageButton();
  private final ImageButton btSetting = new ImageButton();
  private final ArrayList<ChartScreen> screens = new ArrayList<ChartScreen>();
  private ImageButton btCapture = new ImageButton();
  private ImageButton btEdit = new ImageButton();
  private int numberOfButtons = 0;
  // the reference of bars
  private MenuBar fMenuBar = null;
  private CompareBar fCompareBar = null;
  //the reference of chartscreens which this buttonbar can control it.
  private ChartScreen chartScreen1 = null;
  private ChartScreen chartScreen2 = null;
  private ChartScreen chartScreen3 = null;
  private SettingDialog settingWindow1 = null;

  public FunctionPanel() {
    initComponents();
  }

  public void setMenus(CompareBar fc, MenuBar fm) {
    fCompareBar = fc;
    fMenuBar = fm;
  }

  //set the reference of the chartScrenn such that the button bar can control it.
  public void setChartScreen(ChartScreen cs1, ChartScreen cs2, ChartScreen cs3) {
    chartScreen1 = cs1;
    chartScreen2 = cs2;
    chartScreen3 = cs3;
    screens.add(cs1);
    screens.add(cs2);
    screens.add(cs3);
  }

  private void addButton(Component component) {
    component.setBounds(0, (FConfig.BUTTON_SIZE + 1) * numberOfButtons, FConfig.BUTTON_SIZE, FConfig.BUTTON_SIZE);
    this.add(component, null);
    numberOfButtons++;
  }

  private void initComponents() {

    this.setBackground(FConfig.ScreenBackground);
    settingWindow1 = new SettingDialog();
    getBtNone().setButtonImage(new ImageIcon(getClass().getResource("/cursor.png")).getImage());
    getBtWatch().setButtonImage(new ImageIcon(getClass().getResource("/watch.png")).getImage());
    getBtZoomIn().setButtonImage(new ImageIcon(getClass().getResource("/zoomin.png")).getImage());
    getBtZoomOut().setButtonImage(new ImageIcon(getClass().getResource("/zoomout.png")).getImage());
    getBtMove().setButtonImage(new ImageIcon(getClass().getResource("/move.png")).getImage());
    getBtInsertLine().setButtonImage(new ImageIcon(getClass().getResource("/line.png")).getImage());
    getBtInsertPLine().setButtonImage(new ImageIcon(getClass().getResource("/parallelline.png")).getImage());
    getBtGPartition().setButtonImage(new ImageIcon(getClass().getResource("/goldenline.png")).getImage());
    getBtRemoveLine().setButtonImage(new ImageIcon(getClass().getResource("/undo.png")).getImage());
    getBtClear().setButtonImage(new ImageIcon(getClass().getResource("/clean.png")).getImage());
    getBtSetting().setButtonImage(new ImageIcon(getClass().getResource("/setting.png")).getImage());
    getBtCapture().setButtonImage(new ImageIcon(getClass().getResource("/capture.png")).getImage());
    getBtCompare().setButtonImage(new ImageIcon(getClass().getResource("/percentage.png")).getImage());
    getBtEdit().setButtonImage(new ImageIcon(getClass().getResource("/edittext.png")).getImage());


    getBtNone().addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        btNone_actionPerformed(e);
      }
    });
    getBtWatch().addActionListener(new ActionListener() {

      public void actionPerformed(ActionEvent e) {
        btWatch_actionPerformed(e);
      }
    });
    getBtZoomIn().addActionListener(new ActionListener() {

      public void actionPerformed(ActionEvent e) {
        btZoomIn_actionPerformed(e);
      }
    });
    getBtZoomOut().addActionListener(new ActionListener() {

      public void actionPerformed(ActionEvent e) {
        btZoomOut_actionPerformed(e);
      }
    });
    getBtMove().addActionListener(new ActionListener() {

      public void actionPerformed(ActionEvent e) {
        btMove_actionPerformed(e);
      }
    });
    getBtInsertLine().addActionListener(new ActionListener() {

      public void actionPerformed(ActionEvent e) {
        btInsertLine_actionPerformed(e);
      }
    });
    getBtInsertPLine().addActionListener(new ActionListener() {

      public void actionPerformed(ActionEvent e) {
        btInsertPLine_actionPerformed(e);
      }
    });
    getBtGPartition().addActionListener(new ActionListener() {

      public void actionPerformed(ActionEvent e) {
        btGPartition_actionPerformed(e);
      }
    });
    getBtRemoveLine().addActionListener(new ActionListener() {

      public void actionPerformed(ActionEvent e) {
        btRemoveLine_actionPerformed(e);
      }
    });
    getBtClear().addActionListener(new ActionListener() {

      public void actionPerformed(ActionEvent e) {
        btClear_actionPerformed(e);
      }
    });
    getBtCompare().addActionListener(new ActionListener() {

      public void actionPerformed(ActionEvent e) {
        btCompare_actionPerformed(e);
      }
    });
    getBtCapture().addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        btCapture_actionPerformed(e);
      }
    });
    getBtSetting().addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        btSetting_actionPerformed(e);
      }
    });
    getBtEdit().addActionListener(new ActionListener() {

      public void actionPerformed(ActionEvent e) {
        btEdit_actionPerformed(e);
      }
    });

    this.addButton(getBtNone());
    this.addButton(getBtEdit());
    this.addButton(getBtWatch());
    this.addButton(getBtZoomIn());
    this.addButton(getBtZoomOut());
    this.addButton(getBtMove());
    this.addButton(getBtInsertLine());
    this.addButton(getBtInsertPLine());
    this.addButton(getBtGPartition());
    this.addButton(getBtRemoveLine());
    this.addButton(getBtClear());
    this.addButton(getBtCompare());
    this.addButton(getBtCapture());
    this.addButton(getBtSetting());

  }

  void btNone_actionPerformed(ActionEvent e) {
    for (ChartScreen screen : screens) {
      if (screen != null) {
        screen.getAction().setActionType(ActionType.NONE_ACTION);
      }
    }
  }

  void btWatch_actionPerformed(ActionEvent e) {
    for (ChartScreen screen : screens) {
      if (screen != null) {
        screen.getAction().setActionType(ActionType.WATCH);
      }
    }
  }

  void btEdit_actionPerformed(ActionEvent e) {
    for (ChartScreen screen : screens) {
      if (screen != null) {
        screen.getAction().setActionType(ActionType.EDIT_TEXT);
      }
    }
  }

  void btZoomIn_actionPerformed(ActionEvent e) {
    if (chartScreen1 != null) {
      chartScreen1.getAction().setActionType(ActionType.ZOOM_IN);
    }

    if (chartScreen2 != null) {
      chartScreen2.getAction().setActionType(ActionType.NONE_ACTION);
    }

    if (chartScreen3 != null) {
      chartScreen3.getAction().setActionType(ActionType.NONE_ACTION);
    }
  }

  void btZoomOut_actionPerformed(ActionEvent e) {
    if (chartScreen1 != null) {
      chartScreen1.undoZoom();
      int startIndex = chartScreen1.getStartDisplayIndex();
      int endIndex = chartScreen1.getEndDisplayIndex();
      chartScreen2.zoom(startIndex, endIndex);
      chartScreen3.zoom(startIndex, endIndex);

    }
  }

  void btMove_actionPerformed(ActionEvent e) {
    for (ChartScreen screen : screens) {
      if (screen != null) {
        screen.getAction().setActionType(ActionType.MOVE_CHART);
      }
    }
  }

  void btInsertLine_actionPerformed(ActionEvent e) {
    for (ChartScreen screen : screens) {
      if (screen != null) {
        screen.getAction().setActionType(ActionType.INSERT_LINE);
      }
    }
  }

  void btInsertPLine_actionPerformed(ActionEvent e) {
    for (ChartScreen screen : screens) {
      if (screen != null) {
        screen.getAction().setActionType(ActionType.INSERT_PARALLEL_LINE);
      }
    }
  }

  void btGPartition_actionPerformed(ActionEvent e) {
    for (ChartScreen screen : screens) {
      if (screen != null) {
        screen.getAction().setActionType(ActionType.GOLDEN_PARTITION);
      }
    }
  }

  void btRemoveLine_actionPerformed(ActionEvent e) {
    FRecord recordToDelete = null;
    for (ChartScreen screen : screens) {
      if (screen != null) {

        FRecord r1 = screen.getActionCommand().getTextRecords().size() > 0 ? (FRecord) screen.getActionCommand().getTextRecords().lastElement() : null;

        if (r1 != null && (recordToDelete == null || r1.getCreationDateTime().after(recordToDelete.getCreationDateTime()))) {
          recordToDelete = r1;
        }

        FRecord r2 = screen.getActionCommand().getLineRecords().size() > 0 ? (FRecord) screen.getActionCommand().getLineRecords().lastElement() : null;

        if (r2 != null && (recordToDelete == null || r2.getCreationDateTime().after(recordToDelete.getCreationDateTime()))) {
          recordToDelete = r2;
        }
      }
    }

    for (ChartScreen screen : screens) {
      if (screen != null && recordToDelete != null) {

        screen.getActionCommand().getTextRecords().remove(recordToDelete);
        screen.getActionCommand().getLineRecords().remove(recordToDelete);
        screen.repaint();
      }
    }


  }

  void btCompare_actionPerformed(ActionEvent e) {
    if (fCompareBar != null && fMenuBar != null) {
      ChartItem taChart = chartScreen1.getChart("TA1Chart");
      if (taChart != null) {
        taChart.setVisible(false);            // set TA chart to be invisible when in comparing state.
      }
      fCompareBar.setVisible(true);
      fMenuBar.setVisible(false);
      if (chartScreen1 != null) {
        ChartItem leftChart = chartScreen1.getLeftChart();
        if (leftChart != null) {
          leftChart.setChartType(ChartType.PERCENTAGE);
          chartScreen1.getAction().getLineRecords().removeAllElements();
          chartScreen1.getAction().setGoldenPartitionLine(null);
          chartScreen1.updateBaseScreen();
          chartScreen1.repaint();
        }
      }
    }
  }

  void btCapture_actionPerformed(ActionEvent e) {

    EventQueue.invokeLater(new Runnable() {
      @Override
      public void run() {
        int height = 0;
        int width = screens.get(0).getWidth();

        for (ChartScreen screen : screens) {
          if (screen.isVisible()) height = height + screen.getHeight();
        }
        Image image = createImage(width, height);
        Graphics g = image.getGraphics();

        int y = 0;
        for (ChartScreen screen : screens) {

          if (screen.isVisible()) {
            g.drawImage(screen.getAllscreenImage(), 0, y, screen.getWidth(), screen.getHeight(), null);
            y = y + screen.getHeight();
          }
        }
        new CopyImageToClipBoard(image);

      }
    });


  }

  void btSetting_actionPerformed(ActionEvent e) {
    settingWindow1.setChartScreen(chartScreen1, chartScreen2); // tell the setting window that which chartScreen he are using.
    settingWindow1.setTAChartName("TA1Chart");  // tell the setting window that which chart he can control
    settingWindow1.updateSetting();
    settingWindow1.setTitle("IC Technical Analysis Setting");
    settingWindow1.setResizable(false);


    int x = Controller.getInstance().getBounds().x;
    int y = Controller.getInstance().getBounds().y;
    int width = Controller.getInstance().getBounds().width;
    int height = Controller.getInstance().getBounds().height;

    settingWindow1.setBounds(x + width / 2 - 220, y + height / 2 - 290, 400, 600);
//        settingWindow1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Already there
    // settingWindow1.setExtendedState(JFrame.MAXIMIZED_BOTH);
    //settingWindow1.setUndecorated(true);
    settingWindow1.setVisible(true);
  }

  public void OnZoomCompleted(Object actionChartScreen, int startIndex, int endIndex) {
    for (ChartScreen screen : screens) {
      if (screen != null) {
        if (startIndex != screen.getStartDisplayIndex() || endIndex != screen.getEndDisplayIndex()) {
          screen.zoom(startIndex, endIndex);
        }
      }
    }
  }

  @Override
  public void OnWatch(Object actionChartScreen, int watchPoint) {
    for (ChartScreen screen : screens) {
      if (screen != actionChartScreen) {
        screen.watch(watchPoint);
      }
    }
  }

  void btClear_actionPerformed(ActionEvent e) {

    for (ChartScreen screen : screens) {
      if (screen != null) {
        screen.getAction().getLineRecords().removeAllElements();
        screen.getAction().setGoldenPartitionLine(null);
        screen.getAction().getTextRecords().removeAllElements();
        screen.repaint();
      }
    }
  }

  public ImageButton getBtNone() {
    return btNone;
  }

  public ImageButton getBtWatch() {
    return btWatch;
  }

  public ImageButton getBtZoomIn() {
    return btZoomIn;
  }

  public ImageButton getBtZoomOut() {
    return btZoomOut;
  }

  public ImageButton getBtMove() {
    return btMove;
  }

  public ImageButton getBtInsertLine() {
    return btInsertLine;
  }

  public ImageButton getBtInsertPLine() {
    return btInsertPLine;
  }

  public ImageButton getBtGPartition() {
    return btGPartition;
  }

  public ImageButton getBtRemoveLine() {
    return btRemoveLine;
  }

  public ImageButton getBtClear() {
    return btClear;
  }

  public ImageButton getBtCompare() {
    return btCompare;
  }

  public ImageButton getBtSetting() {
    return btSetting;
  }

  public ImageButton getBtCapture() {
    return btCapture;
  }

  public void setBtCapture(ImageButton btCapture) {
    this.btCapture = btCapture;
  }

  public ImageButton getBtEdit() {
    return btEdit;
  }

  public void setBtEdit(ImageButton btEdit) {
    this.btEdit = btEdit;
  }
}
