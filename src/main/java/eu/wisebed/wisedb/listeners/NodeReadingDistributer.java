package eu.wisebed.wisedb.listeners;

import eu.uberdust.uberlogger.UberLogger;
import eu.wisebed.wisedb.model.NodeReading;
import org.apache.log4j.Logger;

import java.util.concurrent.BlockingQueue;

/**
 * NodeReadingDistributer class.
 */
public final class NodeReadingDistributer extends Thread {

    /**
     * Static logger.
     */
    private static final Logger LOGGER = Logger.getLogger(NodeReadingDistributer.class);

    /**
     * The queue.
     */
    private final BlockingQueue<NodeReading> queue;

    /**
     * Enables/Disables this Thread.
     */
    private boolean isEnabled;

    /**
     * Default Constructor.
     *
     * @param thisQueue a BlockingQueue
     */
    public NodeReadingDistributer(final BlockingQueue<NodeReading> thisQueue) {
        this.queue = thisQueue;
        isEnabled = true;
    }

    /**
     * If this thread was constructed using a separate
     * <code>Runnable</code> run object, then that
     * <code>Runnable</code> object's <code>run</code> method is called;
     * otherwise, this method does nothing and returns.
     * Subclasses of <code>Thread</code> should override this method.
     *
     * @see #start()
     * @see #stop()
     * @see Thread#Thread(ThreadGroup, Runnable, String)
     */
    public void run() {
        while (isEnabled) {
            try {
                final NodeReading lastReading = queue.take();
                // LOGGER.info("New Reading: " + lastReading.toString());

                if (LastNodeReadingConsumer.getInstance().listenersContains(lastReading.getCapability().getNode().getId(),
                        lastReading.getCapability().getCapability().getName())) {
                    UberLogger.getInstance().log(lastReading, "T4");
                    LastNodeReadingConsumer.getInstance().getListener(lastReading.getCapability().getNode().getId(),
                            lastReading.getCapability().getCapability().getName()).update(lastReading);
                    LOGGER.info("Updating.... : " + lastReading.toString());
                    UberLogger.getInstance().log(lastReading, "T41");
                }
            } catch (final InterruptedException e) {
                LOGGER.fatal("Interrupted Exception ", e);
            }
        }
    }
}
