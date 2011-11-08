package eu.wisebed.wisedb.listeners;

import eu.wisebed.wisedb.model.NodeReading;
import org.apache.log4j.Logger;
import org.hibernate.event.SaveOrUpdateEvent;
import org.hibernate.event.def.DefaultSaveOrUpdateEventListener;

/**
 * Extends the Default SaveOrUpdate Listener used by Hibernate for handling save-update events.
 */
public class LastNodeReadingListener extends DefaultSaveOrUpdateEventListener {

    /**
     * Static logger.
     */
    private static final Logger LOGGER = Logger.getLogger(LastNodeReadingListener.class);

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

            final NodeReading thisReading = (NodeReading) event.getObject();

            LOGGER.info(new StringBuilder().append(thisReading.getNode().getId()).append(": ").append(thisReading.getCapability().getName()).toString());

            //Forward the event to LastNodeReadingObservable
            LastNodeReadingObservable.getInstance().addNodeReading(thisReading);
        }
    }
}
