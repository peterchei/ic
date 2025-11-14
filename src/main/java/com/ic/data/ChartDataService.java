package com.ic.data;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Service for managing chart data requests asynchronously.
 * This singleton service runs in a background thread and processes chart data commands
 * in the order they are received.
 *
 * <p>Thread-safe and ensures only one instance exists via singleton pattern.</p>
 *
 * @author IC Team
 * @version 1.0
 * @since 2015
 */
public class ChartDataService extends Thread {

    private static final Logger LOGGER = Logger.getLogger(ChartDataService.class.getName());

    /** Singleton instance */
    private static final ChartDataService INSTANCE = new ChartDataService();

    /** Polling interval in milliseconds for checking real-time quote commands */
    private static final int POLLING_INTERVAL_MS = 100;

    /** Maximum wait time for refresh cycle in milliseconds */
    private static final int REFRESH_CYCLE_MS = 10000; // 100 * 100ms = 10 seconds

    /** Queue for high-priority commands (non-real-time quotes) */
    private final BlockingQueue<RequestCommand> priorityQueue = new LinkedBlockingQueue<>();

    /** Queue for low-priority real-time quote commands */
    private final BlockingQueue<RequestCommand> realtimeQuoteQueue = new LinkedBlockingQueue<>();

    /** Current service state */
    private volatile ServiceState serviceState = ServiceState.STOPPED;

    /** Flag to indicate the service should shut down */
    private volatile boolean shutdownRequested = false;

    /**
     * Private constructor to enforce singleton pattern.
     * The thread is configured as a daemon thread.
     */
    private ChartDataService() {
        super("ChartDataService-Worker");
        setDaemon(true);
    }

    /**
     * Gets the singleton instance of ChartDataService.
     *
     * @return the singleton instance
     */
    public static ChartDataService getInstance() {
        return INSTANCE;
    }

    /**
     * Enables the service and starts processing commands.
     * If the service is already running, this method has no effect.
     */
    public synchronized void enable() {
        if (serviceState == ServiceState.STOPPED) {
            shutdownRequested = false;
            start();
            LOGGER.info("ChartDataService started");
        } else if (serviceState == ServiceState.IDLE) {
            serviceState = ServiceState.STARTED;
            notifyAll();
            LOGGER.fine("ChartDataService resumed from idle state");
        }
    }

    /**
     * Puts the service into idle state.
     * Commands can still be queued but won't be processed until enabled again.
     */
    public synchronized void disable() {
        serviceState = ServiceState.IDLE;
        LOGGER.info("ChartDataService disabled (idle)");
    }

    /**
     * Gracefully shuts down the service.
     * Waits for current command to complete before stopping.
     *
     * @param timeoutMs maximum time to wait for shutdown in milliseconds
     * @return true if shutdown completed within timeout, false otherwise
     */
    public synchronized boolean shutdown(long timeoutMs) {
        LOGGER.info("Shutdown requested for ChartDataService");
        shutdownRequested = true;
        serviceState = ServiceState.STOPPED;
        notifyAll();

        try {
            join(timeoutMs);
            return !isAlive();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            LOGGER.log(Level.WARNING, "Interrupted while waiting for shutdown", e);
            return false;
        }
    }

    /**
     * Main processing loop that handles commands from both queues.
     * Priority queue commands are processed immediately, while real-time quote
     * commands are processed during idle periods.
     */
    @Override
    public void run() {
        serviceState = ServiceState.STARTED;
        LOGGER.info("ChartDataService worker thread started");

        while (!shutdownRequested) {
            try {
                processCommands();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                LOGGER.log(Level.INFO, "ChartDataService interrupted, shutting down", e);
                break;
            } catch (Exception e) {
                LOGGER.log(Level.SEVERE, "Unexpected error in ChartDataService", e);
                // Continue processing despite errors
            }
        }

        serviceState = ServiceState.STOPPED;
        LOGGER.info("ChartDataService worker thread stopped");
    }

    /**
     * Processes commands from priority queue and real-time quote queue.
     *
     * @throws InterruptedException if the thread is interrupted while waiting
     */
    private void processCommands() throws InterruptedException {
        // Check for priority commands first
        RequestCommand priorityCommand = priorityQueue.poll(POLLING_INTERVAL_MS, TimeUnit.MILLISECONDS);

        if (priorityCommand != null) {
            executeSafely(priorityCommand);
            serviceState = ServiceState.IDLE;
            return;
        }

        // Process real-time quote commands during idle time
        int cycleCount = 0;
        int maxCycles = REFRESH_CYCLE_MS / POLLING_INTERVAL_MS;

        while (cycleCount < maxCycles && serviceState != ServiceState.STOPPED) {
            // Check if a priority command arrived
            priorityCommand = priorityQueue.peek();
            if (priorityCommand != null) {
                break; // Exit to process priority command
            }

            // Process one real-time quote command
            RequestCommand realtimeCommand = realtimeQuoteQueue.poll(POLLING_INTERVAL_MS, TimeUnit.MILLISECONDS);
            if (realtimeCommand != null) {
                executeSafely(realtimeCommand);
            }

            cycleCount++;
        }

        serviceState = ServiceState.STARTED;
    }

    /**
     * Executes a command with proper error handling.
     *
     * @param command the command to execute
     */
    private void executeSafely(RequestCommand command) {
        if (command == null) {
            return;
        }

        try {
            LOGGER.fine(() -> "Executing command: " + command.getChartType());
            command.execute();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error executing command: " + command.getChartType(), e);
        }
    }

    /**
     * Checks if the service is in idle state.
     *
     * @return true if idle, false otherwise
     */
    public boolean isIdle() {
        return serviceState == ServiceState.IDLE;
    }

    /**
     * Checks if the service is running (started state).
     *
     * @return true if running, false otherwise
     */
    public boolean isRunning() {
        return serviceState == ServiceState.STARTED;
    }

    /**
     * Adds a command to the appropriate queue for processing.
     * Real-time quote commands go to the low-priority queue,
     * all other commands go to the high-priority queue.
     *
     * @param command the command to add, must not be null
     * @throws NullPointerException if command is null
     */
    public void addCommand(RequestCommand command) {
        if (command == null) {
            throw new NullPointerException("Command cannot be null");
        }

        try {
            if (command.getChartType() == CommandType.REALTIMEQUOTE) {
                realtimeQuoteQueue.put(command);
                LOGGER.fine("Added real-time quote command to queue");
            } else {
                priorityQueue.put(command);
                LOGGER.fine(() -> "Added priority command to queue: " + command.getChartType());

                // Wake up the processing thread if it's idle
                synchronized (this) {
                    if (serviceState == ServiceState.IDLE) {
                        serviceState = ServiceState.STARTED;
                        notifyAll();
                    }
                }
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            LOGGER.log(Level.WARNING, "Interrupted while adding command to queue", e);
        }
    }

    /**
     * Gets the number of pending commands in both queues.
     *
     * @return total number of pending commands
     */
    public int getPendingCommandCount() {
        return priorityQueue.size() + realtimeQuoteQueue.size();
    }

    /**
     * Clears all pending commands from both queues.
     * This does not affect currently executing commands.
     */
    public void clearAllCommands() {
        int cleared = priorityQueue.size() + realtimeQuoteQueue.size();
        priorityQueue.clear();
        realtimeQuoteQueue.clear();
        LOGGER.info(() -> "Cleared " + cleared + " pending commands");
    }

    /**
     * Enumeration of possible service states.
     */
    protected enum ServiceState {
        /** Service is stopped and not processing commands */
        STOPPED,

        /** Service is actively processing commands */
        STARTED,

        /** Service is idle and waiting for commands */
        IDLE
    }
}

