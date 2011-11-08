package eu.wisebed.wisedb.listeners;

import eu.wisebed.wisedb.model.NodeReading;

import java.util.ArrayList;
import java.util.HashMap;
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
     * Listeners HashMap<CapabilityID,Listener>
     */
    private final HashMap<String, ArrayList<AbstractNodeReadingListener>> listeners;

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
        listeners = new HashMap<String, ArrayList<AbstractNodeReadingListener>>();
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
     * @param capabilityID the capability ID
     * @param listener     the new Listener.
     */
    public void registerListener(final String capabilityID, final AbstractNodeReadingListener listener) {
        synchronized (lock) {
            if (listeners.containsKey(capabilityID)) {
                listeners.get(capabilityID).add(listener);
            } else {
                final ArrayList<AbstractNodeReadingListener> newArrayListener = new ArrayList<AbstractNodeReadingListener>();
                newArrayListener.add(listener);
                listeners.put(capabilityID, newArrayListener);
            }
        }
    }

    public void removeListener(final String capabilityID, final AbstractNodeReadingListener listener) {
        if (listeners.containsKey(capabilityID)) {
            synchronized (lock) {
                final ArrayList<AbstractNodeReadingListener> thisArray = listeners.get(capabilityID);
                if (thisArray.contains(listener)) {
                    thisArray.remove(listener);
                }
                if (thisArray.isEmpty()) {
                    listeners.remove(capabilityID);
                }
            }
        }
    }

    /**
     * Return if the listeners map contains the specific key.
     *
     * @param capabilityID the key
     * @return true if the map contains the key
     */
    protected boolean listenersContains(final String capabilityID) {
        return listeners.containsKey(capabilityID);
    }

    /**
     * Returns the ArrayLis of listeners of a specific capability.
     *
     * @param capabilityID the key
     * @return an ArrayLis
     */
    protected AbstractNodeReadingListener[] getListener(final String capabilityID) {
        // a temporary array buffer
        return (AbstractNodeReadingListener[]) listeners.get(capabilityID).toArray();
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


