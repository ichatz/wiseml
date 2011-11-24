package eu.wisebed.wisedb.listeners;

import eu.wisebed.wisedb.model.NodeReading;
import org.apache.log4j.Logger;

import java.util.HashMap;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Notifies observers with the last NodeReading.
 */
public final class LastNodeReadingConsumer {

    /**
     * Static logger.
     */
    private static final Logger LOGGER = Logger.getLogger(LastNodeReadingConsumer.class);

    /**
     * static instance(ourInstance) initialized as null.
     */
    private static LastNodeReadingConsumer ourInstance = null;

    /**
     * The QUEUE.
     */
    private static final BlockingQueue<NodeReading> QUEUE = new LinkedBlockingQueue<NodeReading>();

    /**
     * Listeners double HashMap<NodeID, <CapabilityID, Listener>>.
     */
    private final HashMap<String, HashMap<String, AbstractNodeReadingListener>> listeners;

    /**
     * Used to lock specific block.
     */
    private final Object lock = new Object();

    /**
     * Private constructor suppresses generation of a (public) default constructor.
     */
    private LastNodeReadingConsumer() {
        final NodeReadingDistributer nodeReadingDistributer = new NodeReadingDistributer(QUEUE);
        nodeReadingDistributer.start();
        listeners = new HashMap<String, HashMap<String, AbstractNodeReadingListener>>();
    }

    /**
     * LastNodeReadingConsumer is loaded on the first execution of LastNodeReadingConsumer.getInstance()
     * or the first access to LastNodeReadingConsumer.ourInstance, not before.
     *
     * @return ourInstance
     */
    public static LastNodeReadingConsumer getInstance() {
        synchronized (LastNodeReadingConsumer.class) {
            if (ourInstance == null) {
                ourInstance = new LastNodeReadingConsumer();
            }
        }
        return ourInstance;
    }

    /**
     * Register a new Listener for a specific capability.
     *
     * @param nodeId       the node ID
     * @param capabilityID the capability ID
     * @param listener     the new Listener
     */
    public void registerListener(final String nodeId, final String capabilityID,
                                 final AbstractNodeReadingListener listener) {
        synchronized (lock) {
            if (listeners.containsKey(nodeId)) {
                if (!listeners.get(nodeId).containsKey(capabilityID)) {
                    listeners.get(nodeId).put(capabilityID, listener);
                }
            } else {
                final HashMap<String, AbstractNodeReadingListener> newCapability;
                newCapability = new HashMap<String, AbstractNodeReadingListener>();
                newCapability.put(capabilityID, listener);
                listeners.put(nodeId, newCapability);
                LOGGER.info(listenersContains(nodeId, capabilityID));
            }
        }
    }

    /**
     * Remove the specified listener.
     *
     * @param nodeId       the Node ID
     * @param capabilityID the Capability ID
     */
    public void removeListener(final String nodeId, final String capabilityID) {
        synchronized (lock) {
            if (listeners.containsKey(nodeId)) {
                listeners.get(nodeId).remove(capabilityID);
            }
        }
    }

    /**
     * Return an ArrayList if the listeners map contains the specific key.
     *
     * @param nodeID       the node id
     * @param capabilityID the key
     * @return true if the map contains the key
     */
    protected boolean listenersContains(final String nodeID, final String capabilityID) {
        return listeners.containsKey(nodeID) && listeners.get(nodeID).containsKey(capabilityID);

    }

    /**
     * Returns the listeners of a specific node and capability .
     *
     * @param nodeID       the node id
     * @param capabilityID the key
     * @return an AbstractNodeReadingListener
     */
    protected AbstractNodeReadingListener getListener(final String nodeID, final String capabilityID) {
        // a temporary array buffer
        return listeners.get(nodeID).get(capabilityID);
    }

    /**
     * Forwards the NodeReading to the Distributer Thread.
     *
     * @param thisReading the NodeReading
     */
    public void addNodeReading(final NodeReading thisReading) {
        QUEUE.offer(thisReading);
    }
}


