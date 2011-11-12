package eu.wisebed.wisedb.listeners;

import eu.wisebed.wisedb.model.NodeReading;
import org.apache.log4j.Logger;

import java.util.concurrent.BlockingQueue;

/**
 * Created by IntelliJ IDEA.
 * User: akribopo
 * Date: 11/8/11
 * Time: 11:53 PM
 * To change this template use File | Settings | File Templates.
 */
public class NodeReadingDistributer extends Thread {

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
     * <p/>
     * Subclasses of <code>Thread</code> should override this method.
     *
     * @see #start()
     * @see #stop()
     * @see Thread#Thread(ThreadGroup, Runnable, String)
     */
    @Override
    public void run() {
        while (isEnabled) {
            try {
                final NodeReading lastReading = queue.take();
                if (LastNodeReadingConsumer.getInstance().listenersContains(lastReading.getNode().getId(), lastReading.getCapability().getName())) {
                    LastNodeReadingConsumer.getInstance().getListener(lastReading.getNode().getId(), lastReading.getCapability().getName()).update(lastReading);
                }
            } catch (final InterruptedException e) {
                LOGGER.fatal("Interrupted Exception ", e);
            }
        }
    }
}
