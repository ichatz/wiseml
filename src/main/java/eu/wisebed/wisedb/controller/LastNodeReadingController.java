package eu.wisebed.wisedb.controller;

import eu.wisebed.wisedb.model.LastNodeReading;
import eu.wisebed.wiseml.model.setup.Capability;
import eu.wisebed.wiseml.model.setup.Link;
import eu.wisebed.wiseml.model.setup.Node;
import org.hibernate.Session;

public class LastNodeReadingController extends AbstractController<LastNodeReading> {

    /**
     * static instance(ourInstance) initialized as null.
     */
    private static LastNodeReadingController ourInstance = null;

    /**
     * Public constructor .
     */
    public LastNodeReadingController() {
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
    public static LastNodeReadingController getInstance() {
        synchronized (LastNodeReadingController.class) {
            if (ourInstance == null) {
                ourInstance = new LastNodeReadingController();
            }
        }

        return ourInstance;
    }


    /**
     * Returns the last reading inserted in the persistence for a specific node & capability
     *
     * @param node       , a node.
     * @param capability , a capability.
     * @return the last node reading.
     */
    public LastNodeReading getByID(final Node node, final Capability capability) {
        final Session session = this.getSessionFactory().getCurrentSession();
        LastNodeReading lastNodeReading = new LastNodeReading();
        lastNodeReading.setNode(node);
        lastNodeReading.setCapability(capability);
        return (LastNodeReading) session.get(LastNodeReading.class, lastNodeReading);
    }
}
