package eu.wisebed.wisedb.controller;

import eu.wisebed.wisedb.model.LastLinkReading;
import eu.wisebed.wisedb.model.LastNodeReading;
import eu.wisebed.wiseml.model.setup.Capability;
import eu.wisebed.wiseml.model.setup.Link;
import eu.wisebed.wiseml.model.setup.Node;
import org.hibernate.Session;

public class LastLinkReadingController extends AbstractController<LastLinkReading> {

    /**
     * static instance(ourInstance) initialized as null.
     */
    private static LastLinkReadingController ourInstance = null;

    /**
     * Public constructor .
     */
    public LastLinkReadingController() {
        // Does nothing
        super();
    }

    /**
     * LastNodeReadingController is loaded on the first execution of
     * LastNodeReadingController.getInstance() or the first access to
     * LastNodeReadingController.ourInstance, not before.
     *
     * @return ourInstance
     */
    public static LastLinkReadingController getInstance() {
        synchronized (LastLinkReadingController.class) {
            if (ourInstance == null) {
                ourInstance = new LastLinkReadingController();
            }
        }

        return ourInstance;
    }

    /**
     * Returns the last reading inserted in the persistence for a specific link & capability
     *
     * @param link       , a link.
     * @param capability , a capability.
     * @return the last node reading.
     */
    public LastLinkReading getByID(final Link link, final Capability capability) {
        final Session session = this.getSessionFactory().getCurrentSession();
        LastLinkReading lastLinkReading = new LastLinkReading();
        lastLinkReading.setNode(link);
        lastLinkReading.setCapability(capability);
        return (LastLinkReading) session.get(LastLinkReading.class, lastLinkReading);
    }
}
