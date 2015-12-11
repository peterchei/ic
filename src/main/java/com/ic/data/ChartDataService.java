package com.ic.data;

public class ChartDataService extends Thread {

    private static ChartDataService chartSource = new ChartDataService();
    private final int refreshSecond = 100;//how long to be refresh for intraday chart.
    private boolean isNewCommand = false;
    private STATE engineState = STATE.STOPPED;

    ;
    private RequestCommand fCommand = null;
    private java.util.LinkedList<RequestCommand> queue = new java.util.LinkedList<RequestCommand>();
    private ChartDataService() {
    }

    public static synchronized ChartDataService getInstance() {
        return chartSource;
    }

    public void enable() {
        if (engineState == STATE.STOPPED) {
            this.start();   // start the thread
        } else if (engineState == STATE.STARTED) {
            this.notify();  // resume the thread
        } else if (engineState == STATE.IDLE) {
            this.notify();
        }
    }

    public void disable() {
        engineState = STATE.IDLE;
    }

    @Override
    public void run() {
        engineState = STATE.STARTED;
        int count = 0;
        while (true) {
            try {
                if (fCommand != null) {
                    //process the RequestCommand the fire result to the ChartDataService Listener
                    RequestCommand currentCommand = fCommand;
                    ChartData cdata = null;
                    fCommand = null;
                    if (isNewCommand) {
                        isNewCommand = false;
                        currentCommand.execute();
                        engineState = STATE.IDLE;
                    }
                }
                synchronized (this) {
                    for (int i = 0; i < refreshSecond; i++) {
                        if (!isNewCommand && queue.size() == 0) {
                            // no request, wait.
                            wait(100);
                        }
                        if (queue.size() > 0) {
                            RequestCommand realQuoteRequest = queue.pollFirst();
                            realQuoteRequest.execute();
                        }
                        if (isNewCommand) {
                            break;
                        }
                    }
                }
                engineState = STATE.STARTED;
            } catch (InterruptedException e) {
                fCommand = null;
                isNewCommand = false;
                e.printStackTrace();
            }
        }
    }

    public boolean isIdle() {
        return (engineState == STATE.IDLE);
    }

    public void addCommand(RequestCommand fC) {

        if (fC.getChartType() != RequestCommand.CommandType.REALTIMEQUOTE) {
            synchronized (this) {
                isNewCommand = true;
                fCommand = fC;
                notify();
            }
        } else {
            queue.add(fC);
        }
    }

    protected enum STATE {
        STOPPED, STARTED, IDLE
    }
}

