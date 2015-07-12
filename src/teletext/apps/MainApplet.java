package teletext.apps;

import teletext.apps.core.ChartScreen;
import teletext.apps.core.MainPanel;
import teletext.apps.ui.FConfig;
import teletext.apps.ui.FMEChartA;
import teletext.apps.ui.LoginPanel;
import teletext.data.FCommand;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import teletext.data.ChartDataService;

public class MainApplet extends JApplet {
	boolean isStandalone = false;

	JPanel tempPanel = new JPanel();

	FMEChartA fmeChartA = null; // new FMEChartA();

	MainPanel mPanel = new MainPanel();

	BorderLayout borderLayout2 = new BorderLayout();

	LoginPanel loginPanel = new LoginPanel();

	BorderLayout borderLayout1 = new BorderLayout();

	CardLayout cardLayout1 = new CardLayout();

	// Get a parameter value
	public String getParameter(String key, String def) {
		return isStandalone ? System.getProperty(key, def)
				: (getParameter(key) != null ? getParameter(key) : def);
	}

	// Construct the applet
	public MainApplet() {
	}

	// Initialize the applet
	public void init() {
		try {
			jbInit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// Component initialization
	private void jbInit() throws Exception {
		// add the chart panel to the Applet.....
		// this.getContentPane().setLayout(cardLayout1);
		tempPanel.setLayout(cardLayout1);
		tempPanel.setBackground(Color.white);

		loginPanel.jbLogin.addActionListener(new MainApplet_jbLogin_actionAdapter(this));
		this.getContentPane().setLayout(borderLayout1);
		// this.getContentPane().add(loginPanel, BorderLayout.CENTER);
		// tempPanel.add(mPanel, BorderLayout.CENTER);
		tempPanel.add(loginPanel, "loginPanel");
		tempPanel.add(mPanel, "mainPanel");

		this.getContentPane().add(tempPanel, BorderLayout.CENTER);
		fmeChartA = mPanel.fMEChartA1;
	}

	// Start the applet
	public void start() {

		// load image from URL
		fmeChartA.fbuttonBar.btNone.setButtonImage( getImage(getDocumentBase(), "mouse.gif"));
		fmeChartA.fbuttonBar.btWatch.setButtonImage( getImage(getDocumentBase(), "watch.gif"));
		fmeChartA.fbuttonBar.btZoomIn.setButtonImage( getImage(getDocumentBase(), "zoomin.gif"));
		fmeChartA.fbuttonBar.btZoomOut.setButtonImage( getImage(getDocumentBase(), "zoomout.gif"));
		fmeChartA.fbuttonBar.btMove.setButtonImage( getImage(getDocumentBase(), "move.gif"));
		fmeChartA.fbuttonBar.btInsertLine.setButtonImage( getImage(getDocumentBase(), "insertline.gif"));
		fmeChartA.fbuttonBar.btInsertPLine.setButtonImage( getImage(getDocumentBase(), "parallelline.gif"));
		fmeChartA.fbuttonBar.btGPartition.setButtonImage( getImage(getDocumentBase(), "goldenpartition.gif"));
		fmeChartA.fbuttonBar.btRemoveLine.setButtonImage( getImage(getDocumentBase(), "removeline.gif"));
		fmeChartA.fbuttonBar.btClear.setButtonImage( getImage(getDocumentBase(),"clear.gif"));
		fmeChartA.fbuttonBar.btCompare.setButtonImage( getImage(getDocumentBase(),"compare.gif"));
		fmeChartA.fbuttonBar.btSetting.setButtonImage( getImage(getDocumentBase(),"setting.gif"));
		fmeChartA.fTAMenu1.btSetting.setButtonImage( getImage(getDocumentBase(),	"setting.gif"));
		fmeChartA.chartOptionBar1.btStock.setButtonImage( getImage(getDocumentBase(), "SS.gif"));
		fmeChartA.chartOptionBar1.btTA.setButtonImage( getImage(getDocumentBase(),"ST.gif"));
		fmeChartA.chartOptionBar1.btVolume.setButtonImage( getImage(getDocumentBase(), "SV.gif"));
		fmeChartA.btOpenClose.setButtonImage( getImage(getDocumentBase(), "open.gif"));
		fmeChartA.btPrinter.setButtonImage( getImage(getDocumentBase(),	"printer.gif"));
		//loginPanel.setButtonImage( getImage(getDocumentBase(), "screen.gif");
		loginPanel.offImage = ( getImage(getDocumentBase(), "screen.gif"));
		loginPanel.repaint();

		// Init the chart screen
		this.fmeChartA.chartScreen1.initScreen();
		this.fmeChartA.chartScreen2.initScreen();
		this.fmeChartA.chartScreen3.initScreen();

		ChartDataService.getInstance().enable();

		// add a chart data
		FCommand fc = new FCommand(0001, FCommand.TYPE_DOWNLOAD_LEFT_CHART,	fmeChartA.fmenuBar.chDuration.getSelectedIndex(), "LMain1", 500, 1, false, fmeChartA.fmenuBar);
		ChartDataService.getInstance().addCommand(fc);
		fmeChartA.chartScreen1.setScreenState(ChartScreen.LOADING);
		fmeChartA.chartScreen2.setScreenState(ChartScreen.LOADING);
		fmeChartA.chartScreen3.setScreenState(ChartScreen.LOADING);

		// change the language by calling set language function
		fmeChartA.SetLanguage(FConfig.constChinese);


	}

	// Stop the applet
	public void stop() {
	}

	// Destroy the applet
	public void destroy() {
	}

	// Get Applet information
	public String getAppletInfo() {
		return "Applet Information";
	}

	// Get parameter info
	public String[][] getParameterInfo() {
		return null;
	}

	void jbLogin_actionPerformed(ActionEvent e) {
		
		if (loginPanel.isValidUser())
		cardLayout1.show(tempPanel, "mainPanel");
	}
}

class MainApplet_jbLogin_actionAdapter implements java.awt.event.ActionListener {
	MainApplet adaptee;

	MainApplet_jbLogin_actionAdapter(MainApplet adaptee) {
		this.adaptee = adaptee;
	}

	public void actionPerformed(ActionEvent e) {
		adaptee.jbLogin_actionPerformed(e);
	}
}
