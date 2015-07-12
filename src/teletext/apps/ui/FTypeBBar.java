package teletext.apps.ui;

import teletext.apps.core.ChartScreen;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FTypeBBar
        extends TabBar {
    //implements ScreenActionListener
    public FImageButton btTypeA = new FImageButton();
    public FImageButton btTypeB = new FImageButton();
    public FImageButton btTypeC = new FImageButton();
    public FImageButton btTypeD = new FImageButton();

    //the reference of chartscreens which this buttonbar can control it.
    private ChartScreen chartScreen1 = null;
    private ChartScreen chartScreen2 = null;
    private ChartScreen chartScreen3 = null;

    private int panelHeight, cs1Height, cs2Height, cs3Height;
    private boolean isInited = true;

    public void initType(int ph, int cs1h, int cs2h, int cs3h) {
        isInited = true;
        panelHeight = ph;
        cs1Height = cs1h;
        cs2Height = cs2h;
        cs3Height = cs3h;
    }

    //set the reference of the chartScrenn such that the button bar can control it.
    public void setChartScreen(ChartScreen cs1, ChartScreen cs2,
                               ChartScreen cs3) {
        chartScreen1 = cs1;
        chartScreen2 = cs2;
        chartScreen3 = cs3;
    }

    public FTypeBBar() {
        try {
            jbInit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void jbInit() throws Exception {

        btTypeA.setBackground(Color.orange);
        btTypeA.setBounds(new Rectangle(78, 2, 22, 22));

        btTypeA.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btTypeA_actionPerformed(e);
            }
        });
        btTypeA.setLabel("FImageButton1");
        btTypeA.addActionListener(new FTypeBBar_btTypeA_actionAdapter(this));
        this.setLayout(null);
        btTypeB.setBackground(Color.orange);
        btTypeB.setBounds(new Rectangle(55, 2, 22, 22));
        btTypeB.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btTypeB_actionPerformed(e);
            }
        });
        btTypeB.setLabel("FImageButton2");
        btTypeB.addActionListener(new FTypeBBar_btTypeB_actionAdapter(this));
        btTypeC.setBackground(Color.orange);
        btTypeC.setBounds(new Rectangle(32, 2, 22, 22));
        btTypeC.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btTypeC_actionPerformed(e);
            }
        });
        btTypeC.setLabel("FImageButton3");
        btTypeC.addActionListener(new FTypeBBar_btTypeC_actionAdapter(this));
        btTypeD.setBackground(Color.orange);
        btTypeD.setBounds(new Rectangle(9, 2, 22, 22));
        btTypeD.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btTypeD_actionPerformed(e);
            }
        });
        btTypeD.setLabel("FImageButton4");
        btTypeD.addActionListener(new FTypeBBar_btTypeD_actionAdapter(this));

        this.setBackground(SystemColor.control);
        this.add(btTypeA, null);
        this.add(btTypeB, null);
        this.add(btTypeC, null);
        this.add(btTypeD, null);
    }

    void btTypeA_actionPerformed(ActionEvent e) {
        System.out.println("D");
        if (!isInited) {
            return;
        }
        chartScreen2.setVisible(true);
        chartScreen2.setVisible(true);
        chartScreen3.setVisible(true);
        chartScreen1.setBounds(new Rectangle(1, panelHeight,
                chartScreen1.getSize().width,
                cs1Height));
        chartScreen2.setBounds(new Rectangle(1, panelHeight + cs1Height,
                chartScreen2.getSize().width,
                cs2Height));
        chartScreen3.setBounds(new Rectangle(1,
                panelHeight + cs1Height + cs2Height,
                chartScreen2.getSize().width,
                cs3Height));
        chartScreen1.updateBaseScreen();
        chartScreen1.repaint();
        chartScreen2.updateBaseScreen();
        chartScreen2.repaint();
        chartScreen3.updateBaseScreen();
        chartScreen3.repaint();

    }

    void btTypeB_actionPerformed(ActionEvent e) {
        if (!isInited) {
            return;
        }
        chartScreen1.setVisible(true);
        chartScreen2.setVisible(false);
        chartScreen3.setVisible(true);
        chartScreen1.setBounds(new Rectangle(1, panelHeight,
                chartScreen1.getSize().width,
                cs1Height + cs2Height));
        chartScreen3.setBounds(new Rectangle(1,
                panelHeight + cs1Height + cs2Height,
                chartScreen3.getSize().width,
                cs3Height));

        chartScreen1.updateBaseScreen();
        chartScreen1.repaint();
        chartScreen3.updateBaseScreen();
        chartScreen3.repaint();

    }

    void btTypeC_actionPerformed(ActionEvent e) {
        if (!isInited) {
            return;
        }
        chartScreen1.setVisible(true);
        chartScreen2.setVisible(true);
        chartScreen3.setVisible(false);

        chartScreen1.setBounds(new Rectangle(1, panelHeight,
                chartScreen1.getSize().width,
                cs1Height + cs2Height));
        chartScreen2.setBounds(new Rectangle(1,
                panelHeight + cs1Height + cs2Height,
                chartScreen3.getSize().width,
                cs3Height));
        chartScreen1.updateBaseScreen();
        chartScreen1.repaint();
        chartScreen2.updateBaseScreen();
        chartScreen2.repaint();


    }

    void btTypeD_actionPerformed(ActionEvent e) {
        if (!isInited) {
            return;
        }
        chartScreen1.setVisible(true);
        chartScreen2.setVisible(false);
        chartScreen3.setVisible(false);
        chartScreen1.setBounds(new Rectangle(1, panelHeight,
                chartScreen1.getSize().width,
                cs1Height + cs2Height + cs3Height));
        chartScreen1.updateBaseScreen();
        chartScreen1.repaint();
    }

}

class FTypeBBar_btTypeD_actionAdapter
        implements java.awt.event.ActionListener {
    FTypeBBar adaptee;

    FTypeBBar_btTypeD_actionAdapter(FTypeBBar adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.btTypeD_actionPerformed(e);
    }
}

class FTypeBBar_btTypeC_actionAdapter
        implements java.awt.event.ActionListener {
    FTypeBBar adaptee;

    FTypeBBar_btTypeC_actionAdapter(FTypeBBar adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.btTypeC_actionPerformed(e);
    }
}

class FTypeBBar_btTypeB_actionAdapter
        implements java.awt.event.ActionListener {
    FTypeBBar adaptee;

    FTypeBBar_btTypeB_actionAdapter(FTypeBBar adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.btTypeB_actionPerformed(e);
    }
}

class FTypeBBar_btTypeA_actionAdapter
        implements java.awt.event.ActionListener {
    FTypeBBar adaptee;

    FTypeBBar_btTypeA_actionAdapter(FTypeBBar adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.btTypeA_actionPerformed(e);
    }
}
