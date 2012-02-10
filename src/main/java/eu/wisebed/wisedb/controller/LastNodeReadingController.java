package eu.wisebed.wisedb.controller;

import eu.wisebed.wisedb.model.LastNodeReading;
import eu.wisebed.wisedb.model.Testbed;
import eu.wisebed.wiseml.model.setup.Capability;
import eu.wisebed.wisedb.model.Node;
import eu.wisebed.wisedb.model.Setup;
import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import java.util.List;

/**
 * CRUD operations for LastNodeReading entities.
 */
@SuppressWarnings("unchecked")
public class LastNodeReadingController extends AbstractController<LastNodeReading> {

    /**
     * static instance(ourInstance) initialized as null.
     */
    private static LastNodeReadingController ourInstance = null;
    /**
     * Node literal.
     */
    private static final String NODE = "node";
    /**
     * Capability literal.
     */
    private static final String CAPABILITY = "capability";

    /**
     * Logger.
     */
    private static final Logger LOGGER = Logger.getLogger(LastNodeReadingController.class);


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
     * Returns the last reading row inserted in the persistence for a specific node & capability.
     *
     * @param node       a node instance.
     * @param capability a capability instance.
     * @return the last node reading of a node for a capability.
     */
    public LastNodeReading getByID(final Node node, final Capability capability) {

        LOGGER.info("getByID(" + node + "," + capability + ")");

        final Session session = this.getSessionFactory().getCurrentSession();
        final LastNodeReading lastNodeReading = new LastNodeReading();
        lastNodeReading.setNode(node);
        lastNodeReading.setCapability(capability);
        return (LastNodeReading) session.get(LastNodeReading.class, lastNodeReading);
    }

    /**
     * Returns a list of last node reading entries for the nodes of a testbed.
     *
     * @param testbed , a testbed.
     * @return a list last node readings from a testbed's nodes
     */
    public List<LastNodeReading> getByTestbed(final Testbed testbed) {

        LOGGER.info("getByTestbed(" + testbed + ")");

        // retrieve testbed nodes from setup
        final Setup setup = SetupController.getInstance().getByID(testbed.getSetup().getId());
        List<Node> nodes = NodeController.getInstance().list(TestbedController.getInstance().getBySetup(setup));
        if (nodes == null || nodes.isEmpty()) {
            return null;
        }

        final Session session = this.getSessionFactory().getCurrentSession();
        final Criteria criteria = session.createCriteria(LastNodeReading.class);
        criteria.add(Restrictions.in(NODE, nodes));
        return (List<LastNodeReading>) criteria.list();
    }

    /**
     * Returns a list of last reading rows inserted in the persistence for a specific capability.
     *
     * @param testbed    a testbed
     * @param capability a capability.
     * @return a list of last node reading rows for each capability. Nodes belong to a specific testbed.
     */
    public List<LastNodeReading> getByCapability(final Testbed testbed, final Capability capability) {

        LOGGER.info("getByCapability(" + testbed + "," + capability + ")");

        // retrieve testbed nodes from setup
        final Setup setup = SetupController.getInstance().getByID(testbed.getSetup().getId());
        List<Node> nodes = NodeController.getInstance().list(TestbedController.getInstance().getBySetup(setup));
        if (nodes == null || nodes.isEmpty()) {
            return null;
        }

        final Session session = this.getSessionFactory().getCurrentSession();
        final Criteria criteria = session.createCriteria(LastNodeReading.class);
        criteria.add(Restrictions.in(NODE, nodes));
        criteria.add(Restrictions.eq(CAPABILITY, capability));
        return (List<LastNodeReading>) criteria.list();
    }

}
