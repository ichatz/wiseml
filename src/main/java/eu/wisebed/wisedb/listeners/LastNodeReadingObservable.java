package eu.wisebed.wisedb.listeners;

import eu.wisebed.wisedb.model.NodeReading;

import java.util.Observable;

/**
 * Notifies observers with the last NodeReading.
 */
public final class LastNodeReadingObservable extends Observable {

    /**
     * static instance(ourInstance) initialized as null.
     */
    private static LastNodeReadingObservable ourInstance = null;

    /**
     * Private constructor suppresses generation of a (public) default constructor.
     */
    private LastNodeReadingObservable() {
    }

    /**
     * LastNodeReadingObservable is loaded on the first execution of LastNodeReadingObservable.getInstance()
     * or the first access to LastNodeReadingObservable.ourInstance, not before.
     *
     * @return ourInstance
     */
    public static LastNodeReadingObservable getInstance() {
        synchronized (LastNodeReadingObservable.class) {
            if (ourInstance == null) {
                ourInstance = new LastNodeReadingObservable();
            }
        }
        return ourInstance;
    }

    /**
     * Notifies all of its observers.
     *
     * @param nodeReading the Last NodeReading
     */
    public void addNodeReading(final NodeReading nodeReading) {
        LastNodeReadingObservable.getInstance().setChanged();
        LastNodeReadingObservable.getInstance().notifyObservers(nodeReading);
    }
}

