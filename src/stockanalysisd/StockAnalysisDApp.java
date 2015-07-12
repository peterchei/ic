package stockanalysisd;

import org.jdesktop.application.Application;
import org.jdesktop.application.ApplicationContext;
import org.jdesktop.application.SingleFrameApplication;
import org.jdesktop.application.TaskMonitor;
import org.jdesktop.application.TaskService;

/**
 * The main class of the application.
 */
public class StockAnalysisDApp extends SingleFrameApplication {

    /**
     * At startup create and show the main frame of the application.
     */
    @Override protected void startup() {
        show(new StockAnalysisDView(this));
    }

    /**
     * This method is to initialize the specified window by injecting resources.
     * Windows shown in our application come fully initialized from the GUI
     * builder, so this additional configuration is not needed.
     */
    @Override protected void configureWindow(java.awt.Window root) {
    }

    /**
     * A convenient static getter for the application instance.
     * @return the instance of StockAnalysisDApp
     */
    public static StockAnalysisDApp getApplication() {
        return Application.getInstance(StockAnalysisDApp.class);
    }

    /**
     * Main method launching the application.
     */
    public static void main(String[] args) {
        launch(StockAnalysisDApp.class, args);
    }

    public void monitorStatus() {

       // DummyMonitorTask tx= new DummyMonitorTask(Application.getInstance());
        ApplicationContext appC = Application.getInstance().getContext();
        TaskMonitor tM = appC.getTaskMonitor();
        TaskService tS = appC.getTaskService();
      //  tS.execute(tx);
    }

    /*

     private class DummyMonitorTask extends org.jdesktop.application.Task<Object, Void> {

        DummyMonitorTask(org.jdesktop.application.Application app) {
            super(app);
        }

        protected Object doInBackground() throws Exception {
            //loadResource();
            while (true) {
                Thread.sleep(50);
                if (ChartDataService.getInstance().isIdle()) {
                   // System.out.println("is idle");
                    break;
                } else {
                  //  System.out.println("is not idle");
                    Thread.sleep(50);
                }
            }
            return null;
            //throw new UnsupportedOperationException("Not supported yet.");
        }
    }
    */
    
}
