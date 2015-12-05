package com.ic.app;

import com.ic.core.ChartScreen;
import com.ic.core.FConfig;
import com.ic.data.ChartDataService;
import com.ic.data.FCommand;
import com.ic.gui.FMEChartA;
import javafx.application.Application;
import javafx.embed.swing.SwingNode;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import javax.swing.*;
import java.awt.*;

public class Main extends Application {

//  @Override
//  public void start(Stage primaryStage) throws Exception{
//      //Parent
//      StackPane  root =  new StackPane(); ;//FXMLLoader.load(getClass().getResource("IC.fxml"));
//      primaryStage.setTitle("IC");
//      primaryStage.setScene(new Scene(root, 300, 275));
//
//      FMEChartA panel = new FMEChartA();
//      boolean result = root.getChildren().add(panel);
//      primaryStage.show();
//  }


    @Override
    public void start(Stage stage) {
        final SwingNode swingNode = new SwingNode();
        createAndSetSwingContent(swingNode);
        Pane pane = new Pane();
        pane.getChildren().add(swingNode); // Adding swing node
        stage.setScene(new Scene(pane, 1000, 500));
        stage.show();
    }

    private void createAndSetSwingContent(final SwingNode swingNode) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {

                JPanel panel = new JPanel();




                FMEChartA fMEChartA1 = new FMEChartA();


                fMEChartA1.fbuttonBar.getBtWatch().setButtonImage(new ImageIcon(getClass().getResource("/watch.gif")).getImage());
                // load image from URL
                fMEChartA1.fbuttonBar.getBtNone().setButtonImage(new ImageIcon(getClass().getResource("/mouse.gif")).getImage());
                fMEChartA1.fbuttonBar.getBtWatch().setButtonImage(new ImageIcon(getClass().getResource("/watch.gif")).getImage());
                fMEChartA1.fbuttonBar.getBtZoomIn().setButtonImage(new ImageIcon(getClass().getResource("/zoomin.gif")).getImage());
                fMEChartA1.fbuttonBar.getBtZoomOut().setButtonImage(new ImageIcon(getClass().getResource("/zoomout.gif")).getImage());
                fMEChartA1.fbuttonBar.getBtMove().setButtonImage(new ImageIcon(getClass().getResource("/move.gif")).getImage());
                fMEChartA1.fbuttonBar.getBtInsertLine().setButtonImage(new ImageIcon(getClass().getResource("/insertline.gif")).getImage());
                fMEChartA1.fbuttonBar.getBtInsertPLine().setButtonImage(new ImageIcon(getClass().getResource("/parallelline.gif")).getImage());
                fMEChartA1.fbuttonBar.getBtGPartition().setButtonImage(new ImageIcon(getClass().getResource("/goldenpartition.gif")).getImage());
                fMEChartA1.fbuttonBar.getBtRemoveLine().setButtonImage(new ImageIcon(getClass().getResource("/removeline.gif")).getImage());
                fMEChartA1.fbuttonBar.getBtClear().setButtonImage(new ImageIcon(getClass().getResource("/clear.gif")).getImage());
                fMEChartA1.fbuttonBar.getBtCompare().setButtonImage(new ImageIcon(getClass().getResource("/compare.gif")).getImage());
                fMEChartA1.fbuttonBar.getBtSetting().setButtonImage(new ImageIcon(getClass().getResource("/setting.png")).getImage());
                fMEChartA1.fTAMenu1.btSetting.setButtonImage(new ImageIcon(getClass().getResource("/setting.png")).getImage());
                fMEChartA1.chartOptionBar1.btStock.setButtonImage(new ImageIcon(getClass().getResource("/SS.gif")).getImage());
                fMEChartA1.chartOptionBar1.btTA.setButtonImage(new ImageIcon(getClass().getResource("/ST.gif")).getImage());
                fMEChartA1.chartOptionBar1.btVolume.setButtonImage(new ImageIcon(getClass().getResource("/SV.gif")).getImage());
                fMEChartA1.btOpenClose.setButtonImage(new ImageIcon(getClass().getResource("/open.gif")).getImage());
                fMEChartA1.btPrinter.setButtonImage(new ImageIcon(getClass().getResource("/printer.gif")).getImage());

                // Init the chart screen
                fMEChartA1.chartScreen1.initScreen();
                fMEChartA1.chartScreen2.initScreen();
                fMEChartA1.chartScreen3.initScreen();

                fMEChartA1.setBackground(Color.blue);
                ChartDataService.getInstance().enable();
                panel.setBackground(Color.yellow);

                // add a chart data
                FCommand fc = new FCommand(0001, FCommand.TYPE_DOWNLOAD_LEFT_CHART, fMEChartA1.fmenuBar.chDuration.getSelectedIndex(), "LMain1", 500, 1, false, fMEChartA1.fmenuBar);
                ChartDataService.getInstance().addCommand(fc);
                fMEChartA1.chartScreen1.setScreenState(ChartScreen.LOADING);
                fMEChartA1.chartScreen2.setScreenState(ChartScreen.LOADING);
                fMEChartA1.chartScreen3.setScreenState(ChartScreen.LOADING);

                // change the language by calling set language function
                fMEChartA1.SetLanguage(FConfig.constChinese);

                fMEChartA1.setSize(2000,1000);


                swingNode.setVisible(true);
                swingNode.setContent(fMEChartA1);
            }
        });
    }

    public static void main(String[] args) {
        launch(args);
    }
}
