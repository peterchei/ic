package pfinance.client.apps.ui;

import teletext.apps.core.ChartScreen;
import teletext.apps.ui.FMEChartA;
import teletext.apps.ui.FConfig;
import teletext.data.FCommand;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import teletext.data.ChartDataService;

/**
 * <p>Title: FME Chart Project for E-finet</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) </p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class MDIFrame
        extends JFrame
        implements ActionListener {
    JDesktopPane desktop;

    public MDIFrame() {
        super("MDIFrame");

        //Make the big window be indented 50 pixels from each edge
        //of the screen.
        int inset = 50;
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setBounds(inset, inset,
                screenSize.width - inset * 2,
                screenSize.height - inset * 2);

        //Set up the GUI.
        desktop = new JDesktopPane(); //a specialized layered pane
        createFrame(); //create first "window"
        setContentPane(desktop);
        setJMenuBar(createMenuBar());

        //Make dragging a little faster but perhaps uglier.
        desktop.setDragMode(JDesktopPane.OUTLINE_DRAG_MODE);
    }

    protected JMenuBar createMenuBar() {
        JMenuBar menuBar = new JMenuBar();

        //Set up the lone menu.
        JMenu menu = new JMenu("Document");
        menu.setMnemonic(KeyEvent.VK_D);
        menuBar.add(menu);

        //Set up the first menu item.
        JMenuItem menuItem = new JMenuItem("New");
        menuItem.setMnemonic(KeyEvent.VK_N);
        menuItem.setAccelerator(KeyStroke.getKeyStroke(
                KeyEvent.VK_N, ActionEvent.ALT_MASK));
        menuItem.setActionCommand("new");
        menuItem.addActionListener(this);
        menu.add(menuItem);

        //Set up the second menu item.
        menuItem = new JMenuItem("Quit");
        menuItem.setMnemonic(KeyEvent.VK_Q);
        menuItem.setAccelerator(KeyStroke.getKeyStroke(
                KeyEvent.VK_Q, ActionEvent.ALT_MASK));
        menuItem.setActionCommand("quit");
        menuItem.addActionListener(this);
        menu.add(menuItem);

        return menuBar;
    }

    //React to menu selections.
    public void actionPerformed(ActionEvent e) {
        if ("new".equals(e.getActionCommand())) { //new
            createFrame();
        } else { //quit
            quit();
        }
    }

    //Create a new internal frame.
    protected void createFrame() {
        System.out.println("D");
        JInternalFrame frame = new JInternalFrame();
        frame.setVisible(true); //necessary as of 1.3
        FMEChartA fmeChartA = new FMEChartA();
        frame.getContentPane().add(fmeChartA);
        frame.setSize(600, 400);
        desktop.add(frame);

        // load image from URL
        /*
        fmeChartA.fbuttonBar.btNone.offImage = getImage(getDocumentBase(),            "mouse.gif");
        fmeChartA.fbuttonBar.btWatch.offImage = getImage(getDocumentBase(),            "watch.gif");
        fmeChartA.fbuttonBar.btZoomIn.offImage = getImage(getDocumentBase(),            "zoomin.gif");
        fmeChartA.fbuttonBar.btZoomOut.offImage = getImage(getDocumentBase(),            "zoomout.gif");
        fmeChartA.fbuttonBar.btMove.offImage = getImage(getDocumentBase(),            "move.gif");
        fmeChartA.fbuttonBar.btInsertLine.offImage = getImage(getDocumentBase(),            "insertline.gif");
        fmeChartA.fbuttonBar.btInsertPLine.offImage = getImage(getDocumentBase(),            "parallelline.gif");
        fmeChartA.fbuttonBar.btGPartition.offImage = getImage(getDocumentBase(),            "goldenpartition.gif");
        fmeChartA.fbuttonBar.btRemoveLine.offImage = getImage(getDocumentBase(),            "removeline.gif");
        fmeChartA.fbuttonBar.btClear.offImage = getImage(getDocumentBase(),            "clear.gif");
        fmeChartA.fbuttonBar.btCompare.offImage = getImage(getDocumentBase(),            "compare.gif");
        fmeChartA.fbuttonBar.btSetting.offImage = getImage(getDocumentBase(),            "setting.gif");
        fmeChartA.fTAMenu1.btSetting.offImage = getImage(getDocumentBase(),            "setting.gif");
        fmeChartA.fTypeBBar.btTypeA.offImage = getImage(getDocumentBase(),            "TypeA.gif");
        fmeChartA.fTypeBBar.btTypeB.offImage = getImage(getDocumentBase(),            "TypeB.gif");
        fmeChartA.fTypeBBar.btTypeC.offImage = getImage(getDocumentBase(),            "TypeC.gif");
        fmeChartA.fTypeBBar.btTypeD.offImage = getImage(getDocumentBase(),            "TypeD.gif");
        fmeChartA.btOpenClose.offImage = getImage(getDocumentBase(), "open.gif");
        fmeChartA.btPrinter.offImage = getImage(getDocumentBase(),                                                "printer.gif");
*/
        // Init the chart screen
        fmeChartA.chartScreen1.initScreen();
        fmeChartA.chartScreen2.initScreen();
        fmeChartA.chartScreen3.initScreen();

        ChartDataService.getInstance().enable();

        // add a chart data
        FCommand fc = new FCommand(0001, FCommand.TYPE_DOWNLOAD_LEFT_CHART,
                fmeChartA.fmenuBar.chDuration.
                getSelectedIndex(), "LMain1", 500, 1, false,
                fmeChartA.fmenuBar);
        ChartDataService.getInstance().addCommand(fc);
        fmeChartA.chartScreen1.setScreenState(ChartScreen.LOADING);
        fmeChartA.chartScreen2.setScreenState(ChartScreen.LOADING);
        fmeChartA.chartScreen3.setScreenState(ChartScreen.LOADING);

        // change the language by calling set language function
        fmeChartA.SetLanguage(FConfig.constChinese);
        //fmeChartA.SetLanguage(FConfig.constChinese);

        try {
            frame.setSelected(true);
        } catch (java.beans.PropertyVetoException e) {
        }
    }

    //Quit the application.
    protected void quit() {
        System.exit(0);
    }

    /**
     * Create the GUI and show it.  For thread safety,
     * this method should be invoked from the
     * event-dispatching thread.
     */
    private static void createAndShowGUI() {
        //Make sure we have nice window decorations.


        //Create and set up the window.
        MDIFrame frame = new MDIFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Display the window.
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }
}
