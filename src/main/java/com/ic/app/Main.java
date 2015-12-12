package com.ic.app;

import com.ic.core.ChartScreen;
import com.ic.core.FConfig;
import com.ic.data.ChartDataService;
import com.ic.data.RequestCommand;
import com.ic.gui.STVChart;
import javafx.application.Application;
import javafx.embed.swing.JFXPanel;
import javafx.embed.swing.SwingNode;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import javax.swing.*;

public class Main extends Application {

//  @Override
//  public void start(Stage primaryStage) throws Exception{
//      //Parent
//      StackPane  root =  new StackPane(); ;//FXMLLoader.load(getClass().getResource("IC.fxml"));
//      primaryStage.setTitle("IC");
//      primaryStage.setScene(new Scene(root, 300, 275));
//
//      STVChart panel = new STVChart();
//      boolean result = root.getChildren().add(panel);
//      primaryStage.show();
//  }

    private Pane pane;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        final SwingNode swingNode = new SwingNode();
        createAndSetSwingContent(swingNode);
        pane = new Pane();
        pane.getChildren().add(swingNode); // Adding swing node
        stage.setScene(new Scene(pane, 1000, 500));
        stage.show();
    }

    private void createAndSetSwingContent(final SwingNode swingNode) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {

                JFXPanel panel = new JFXPanel();


                STVChart coreChart = new STVChart();
                // load image from URL

                coreChart.functionPanel.getBtNone().setButtonImage(new ImageIcon(getClass().getResource("/cursor.png")).getImage());
                coreChart.functionPanel.getBtWatch().setButtonImage(new ImageIcon(getClass().getResource("/watch.png")).getImage());
                coreChart.functionPanel.getBtZoomIn().setButtonImage(new ImageIcon(getClass().getResource("/zoomin.png")).getImage());
                coreChart.functionPanel.getBtZoomOut().setButtonImage(new ImageIcon(getClass().getResource("/zoomout.png")).getImage());
                coreChart.functionPanel.getBtMove().setButtonImage(new ImageIcon(getClass().getResource("/move.png")).getImage());
                coreChart.functionPanel.getBtInsertLine().setButtonImage(new ImageIcon(getClass().getResource("/line.png")).getImage());
                coreChart.functionPanel.getBtInsertPLine().setButtonImage(new ImageIcon(getClass().getResource("/parallelline.png")).getImage());
                coreChart.functionPanel.getBtGPartition().setButtonImage(new ImageIcon(getClass().getResource("/goldenline.png")).getImage());
                coreChart.functionPanel.getBtRemoveLine().setButtonImage(new ImageIcon(getClass().getResource("/undo.png")).getImage());
                coreChart.functionPanel.getBtClear().setButtonImage(new ImageIcon(getClass().getResource("/clean.png")).getImage());
                coreChart.functionPanel.getBtCompare().setButtonImage(new ImageIcon(getClass().getResource("/percentage.png")).getImage());
                coreChart.functionPanel.getBtSetting().setButtonImage(new ImageIcon(getClass().getResource("/setting.png")).getImage());

                coreChart.fbuttonBar.getBtNone().setButtonImage(new ImageIcon(getClass().getResource("/cursor.png")).getImage());
                coreChart.fbuttonBar.getBtWatch().setButtonImage(new ImageIcon(getClass().getResource("/watch.png")).getImage());
                coreChart.fbuttonBar.getBtZoomIn().setButtonImage(new ImageIcon(getClass().getResource("/zoomin.png")).getImage());
                coreChart.fbuttonBar.getBtZoomOut().setButtonImage(new ImageIcon(getClass().getResource("/zoomout.png")).getImage());
                coreChart.fbuttonBar.getBtMove().setButtonImage(new ImageIcon(getClass().getResource("/move.png")).getImage());
                coreChart.fbuttonBar.getBtInsertLine().setButtonImage(new ImageIcon(getClass().getResource("/line.png")).getImage());
                coreChart.fbuttonBar.getBtInsertPLine().setButtonImage(new ImageIcon(getClass().getResource("/parallelline.png")).getImage());
                coreChart.fbuttonBar.getBtGPartition().setButtonImage(new ImageIcon(getClass().getResource("/goldenline.png")).getImage());
                coreChart.fbuttonBar.getBtRemoveLine().setButtonImage(new ImageIcon(getClass().getResource("/undo.png")).getImage());
                coreChart.fbuttonBar.getBtClear().setButtonImage(new ImageIcon(getClass().getResource("/clean.png")).getImage());
                coreChart.fbuttonBar.getBtCompare().setButtonImage(new ImageIcon(getClass().getResource("/percentage.png")).getImage());
                coreChart.fbuttonBar.getBtSetting().setButtonImage(new ImageIcon(getClass().getResource("/setting.png")).getImage());
                coreChart.fTAMenu1.btSetting.setButtonImage(new ImageIcon(getClass().getResource("/setting.png")).getImage());
                //coreChart.chartOptionBar1.btStock.setButtonImage(new ImageIcon(getClass().getResource("/chartTypeA.png")).getImage());
                coreChart.chartOptionBar1.btTA.setButtonImage(new ImageIcon(getClass().getResource("/chartTypeA.png")).getImage());
                coreChart.chartOptionBar1.btVolume.setButtonImage(new ImageIcon(getClass().getResource("/chartTypeC.png")).getImage());
                coreChart.btOpenClose.setButtonImage(new ImageIcon(getClass().getResource("/close.png")).getImage());
                coreChart.btPrinter.setButtonImage(new ImageIcon(getClass().getResource("/print.png")).getImage());
                coreChart.fCompareBar.getCloseButton().setButtonImage(new ImageIcon(getClass().getResource("/close.png")).getImage());
                coreChart.fCompareBar.getAddButton().setButtonImage(new ImageIcon(getClass().getResource("/add.png")).getImage());
                coreChart.fCompareBar.getRemoveButton().setButtonImage(new ImageIcon(getClass().getResource("/minus.png")).getImage());

                // Init the chart screen
                coreChart.chartScreen1.initScreen();
                coreChart.chartScreen2.initScreen();
                coreChart.chartScreen3.initScreen();

                //  fMEChartA1.setBackground(Color.blue);
                ChartDataService.getInstance().enable();
                //panel.setBackground(Color.yellow);

                // add a chart data
                RequestCommand fc = new RequestCommand(0001, RequestCommand.TYPE_DOWNLOAD_LEFT_CHART, (RequestCommand.CommandType) coreChart.fmenuBar.chDuration.getSelectedItem(), "LMain1", 500, 1, false, coreChart.fmenuBar);
                ChartDataService.getInstance().addCommand(fc);
                coreChart.chartScreen1.setScreenState(ChartScreen.LOADING);
                coreChart.chartScreen2.setScreenState(ChartScreen.LOADING);
                coreChart.chartScreen3.setScreenState(ChartScreen.LOADING);

                // change the language by calling set language function
                coreChart.setLanguage(FConfig.constEnglish);

                coreChart.setSize(2000, 1000);

                swingNode.setContent(coreChart);

                swingNode.setVisible(true);

                //swingNode.isResizable();
                //pane.addEventHandler();
            }
        });
    }
}
