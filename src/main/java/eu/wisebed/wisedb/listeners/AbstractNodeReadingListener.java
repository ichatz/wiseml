package eu.wisebed.wisedb.listeners;

import eu.wisebed.wisedb.model.NodeReading;

/**
 * Abstract NodeReading Listener.
 */
public interface AbstractNodeReadingListener {

    void update(final NodeReading nodeReading);
}
