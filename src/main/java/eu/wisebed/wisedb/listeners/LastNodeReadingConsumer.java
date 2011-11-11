package eu.wisebed.wisedb.listeners;

import eu.wisebed.wisedb.model.NodeReading;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Notifies observers with the last NodeReading.
 */
public final class LastNodeReadingConsumer {

    /**
     * static instance(ourInstance) initialized as null.
     */
    private static LastNodeReadingConsumer ourInstance = null;

    /**
     * The queue.
     */
    private static final BlockingQueue<NodeReading> queue = new LinkedBlockingQueue<NodeReading>();

    /**
     * The Distributer Thread.
     */
    private final NodeReadingDistributer nodeReadingDistributer;

    /**
     * Listeners double HashMap<NodeID, <CapabilityID, Listeners>>.
     */
    private final HashMap<String, HashMap<String, ArrayList<AbstractNodeReadingListener>>> listeners;

    /**
     * Used to lock specific block.
     */
    private final Object lock = new Object();

    /**
     * Private constructor suppresses generation of a (public) default constructor.
     */
    private LastNodeReadingConsumer() {
        nodeReadingDistributer = new NodeReadingDistributer(queue);
        nodeReadingDistributer.start();
        listeners = new HashMap<String, HashMap<String, ArrayList<AbstractNodeReadingListener>>>();
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
    public void registerListener(final String nodeId, final String capabilityID, final AbstractNodeReadingListener listener) {
        synchronized (lock) {
            if (listeners.containsKey(nodeId)) {
                if (listeners.get(nodeId).containsKey(capabilityID)) {
                    listeners.get(nodeId).get(capabilityID).add(listener);
                } else {
                    final ArrayList<AbstractNodeReadingListener> newArrayListener = new ArrayList<AbstractNodeReadingListener>();
                    newArrayListener.add(listener);
                    listeners.get(nodeId).put(capabilityID, newArrayListener);
                }
            } else {
                final ArrayList<AbstractNodeReadingListener> newArrayListener = new ArrayList<AbstractNodeReadingListener>();
                newArrayListener.add(listener);
                final HashMap<String, ArrayList<AbstractNodeReadingListener>> newCapability = new HashMap<String, ArrayList<AbstractNodeReadingListener>>();
                newCapability.put(capabilityID, newArrayListener);
                listeners.put(nodeId, newCapability);
            }
        }
    }

    /**
     * Remove the specified listener.
     *
     * @param nodeId       the Node ID
     * @param capabilityID the Capability ID
     * @param listener     the listener
     */
    public void removeListener(final String nodeId, final String capabilityID, final AbstractNodeReadingListener listener) {
        synchronized (lock) {
            final ArrayList<AbstractNodeReadingListener> thisList = listeners.get(nodeId).get(capabilityID);
            if (thisList != null) {
                thisList.remove(listener);
                if (thisList.isEmpty()) {
                    listeners.get(nodeId).remove(capabilityID);
                }
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
        return listeners.containsKey(nodeID) ? listeners.get(nodeID).containsKey(capabilityID) : false;

    }

    /**
     * Returns the ArrayLis of listeners of a specific capability.
     *
     * @param nodeID       the node id
     * @param capabilityID the key
     * @return an ArrayLis
     */
    protected Object[] getListener(final String nodeID, final String capabilityID) {
        // a temporary array buffer
        return listeners.get(nodeID).get(capabilityID).toArray();
    }

    /**
     * Forwards the NodeReading to the Distributer Thread.
     *
     * @param thisReading the NodeReading
     */
    public void addNodeReading(final NodeReading thisReading) {
        queue.offer(thisReading);
    }
}


