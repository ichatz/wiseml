package eu.wisebed.wisedb.listeners;

import eu.uberdust.uberlogger.UberLogger;
import eu.wisebed.wisedb.model.NodeReading;
import org.hibernate.event.SaveOrUpdateEvent;
import org.hibernate.event.def.DefaultSaveOrUpdateEventListener;

/**
 * Extends the Default SaveOrUpdate Listener used by Hibernate for handling save-update events.
 */
public final class LastNodeReadingListener extends DefaultSaveOrUpdateEventListener {

    /**
     * Serial Version Unique ID.
     */
    private static final long serialVersionUID = -7505906126363179257L;

    /**
     * Handle the given update event.
     *
     * @param event The update event to be handled.
     */
    @Override
    public void onSaveOrUpdate(final SaveOrUpdateEvent event) {
        super.onSaveOrUpdate(event);

        //Ignores all events which are not instances of the NodeReading class.
        if (event.getObject() instanceof NodeReading) {

            // Log it using UberLogger
            UberLogger.getInstance().log((NodeReading) event.getObject(), "T3");

            //Forward the event to LastNodeReadingConsumer
            LastNodeReadingConsumer.getInstance().addNodeReading((NodeReading) event.getObject());
        }
    }
}
