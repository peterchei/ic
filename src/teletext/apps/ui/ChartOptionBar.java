package teletext.apps.ui;

import teletext.apps.core.ChartScreen;

import java.awt.*;
import java.awt.event.ActionEvent;

public class ChartOptionBar extends TabBar {
    public FImageButton btStock = new FImageButton();
    public FImageButton btTA = new FImageButton();
    public FImageButton btVolume = new FImageButton();

    public ChartOptionBar() {
        try {
            jbInit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void jbInit() throws Exception {
        btStock.setLabel("fImageButton1");
        btStock.setBounds(new Rectangle(9, 2, 22, 22));
        btStock.addActionListener(new ChartOptionBar_btStock_actionAdapter(this));
        this.setLayout(null);
        btTA.setLabel("fImageButton2");
        btTA.setBounds(new Rectangle(33, 2, 22, 22));
        btTA.addActionListener(new ChartOptionBar_btTA_actionAdapter(this));
        btVolume.setLabel("fImageButton3");
        btVolume.setBounds(new Rectangle(57, 2, 22, 22));
        btVolume.addActionListener(new ChartOptionBar_btVolume_actionAdapter(this));
        this.add(btStock, null);
        this.add(btTA, null);
        this.add(btVolume, null);
    }

    void btStock_actionPerformed(ActionEvent e) {
        chartScreen1.setVisible(!chartScreen1.isVisible());

    }

    void btTA_actionPerformed(ActionEvent e) {
        chartScreen2.setVisible(!chartScreen2.isVisible());
    }

    void btVolume_actionPerformed(ActionEvent e) {
        chartScreen3.setVisible(!chartScreen3.isVisible());

    }

    //the reference of chartscreens which this buttonbar can control it.
    private ChartScreen chartScreen1 = null;
    private ChartScreen chartScreen2 = null;
    private ChartScreen chartScreen3 = null;

    //set the reference of the chartScrenn such that the button bar can control it.
    public void setChartScreen(ChartScreen cs1, ChartScreen cs2,
                               ChartScreen cs3) {
        chartScreen1 = cs1;
        chartScreen2 = cs2;
        chartScreen3 = cs3;
    }


}

class ChartOptionBar_btStock_actionAdapter implements java.awt.event.ActionListener {
    ChartOptionBar adaptee;

    ChartOptionBar_btStock_actionAdapter(ChartOptionBar adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.btStock_actionPerformed(e);
    }
}

class ChartOptionBar_btTA_actionAdapter implements java.awt.event.ActionListener {
    ChartOptionBar adaptee;

    ChartOptionBar_btTA_actionAdapter(ChartOptionBar adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.btTA_actionPerformed(e);
    }
}

class ChartOptionBar_btVolume_actionAdapter implements java.awt.event.ActionListener {
    ChartOptionBar adaptee;

    ChartOptionBar_btVolume_actionAdapter(ChartOptionBar adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.btVolume_actionPerformed(e);
    }
}
