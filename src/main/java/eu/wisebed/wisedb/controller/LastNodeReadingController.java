package eu.wisebed.wisedb.controller;

import eu.wisebed.wisedb.model.LastNodeReading;
import eu.wisebed.wisedb.model.Testbed;
import eu.wisebed.wiseml.model.setup.Capability;
import eu.wisebed.wiseml.model.setup.Node;
import eu.wisebed.wiseml.model.setup.Setup;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Property;
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
     * Timestamp literal.
     */
    private static final String TIMESTAMP = "timestamp";

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
     * @param node a node instance.
     * @param capability a capability instance.
     * @return the last node reading of a node for a capability.
     */
    public LastNodeReading getByID(final Node node, final Capability capability) {
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

        // retrieve testbed setup
        final Setup setup = SetupController.getInstance().getByID(testbed.getSetup().getId());

        final Session session = this.getSessionFactory().getCurrentSession();
        final Criteria criteria = session.createCriteria(LastNodeReading.class);
        criteria.add(Restrictions.in(NODE, setup.getNodes()));
        return (List<LastNodeReading>) criteria.list();
    }

    /**
     * Returns a list of last reading rows inserted in the persistence for a specific node.
     *
     * @param node a node.
     * @return a list of last reading rows for each capability.
     */
    public List<LastNodeReading> getByNode(final Node node) {
        final Session session = this.getSessionFactory().getCurrentSession();
        final Criteria criteria = session.createCriteria(LastNodeReading.class);
        criteria.add(Restrictions.eq(NODE, node));
        return (List<LastNodeReading>) criteria.list();
    }

    /**
     * Returns a list of last reading rows inserted in the persistence for a specific capability.
     *
     * @param capability , a capability.
     * @return a list of last reading rows for each capability.
     */
    public List<LastNodeReading> getByCapability(final Capability capability) {
        final Session session = this.getSessionFactory().getCurrentSession();
        final Criteria criteria = session.createCriteria(LastNodeReading.class);
        criteria.add(Restrictions.eq(CAPABILITY, capability));
        return (List<LastNodeReading>) criteria.list();
    }

    /**
     * Returns a list of last reading rows inserted in the persistence for a specific capability.
     *
     * @param testbed a testbed
     * @param capability a capability.
     * @return a list of last node reading rows for each capability. Nodes belong to a specific testbed.
     */
    public List<LastNodeReading> getByCapability(final Testbed testbed, final Capability capability) {

        // retrieve testbed setup
        final Setup setup = SetupController.getInstance().getByID(testbed.getSetup().getId());

        final Session session = this.getSessionFactory().getCurrentSession();
        final Criteria criteria = session.createCriteria(LastNodeReading.class);
        criteria.add(Restrictions.in(NODE, setup.getNodes()));
        criteria.add(Restrictions.eq(CAPABILITY, capability));
        return (List<LastNodeReading>) criteria.list();
    }

    /**
     * Returns the latest node reading fo the LastNodeReadings of all capabilities.
     *
     * @param node a node.
     * @return a LastNodeReading entry
     */
    public LastNodeReading getLatestNodeReading(final Node node) {
        final Session session = this.getSessionFactory().getCurrentSession();

        // a detached criteria for max timestamp for last node readings in testbed and capability
        final DetachedCriteria maxTimestamp = DetachedCriteria.forClass(LastNodeReading.class)
                .add(Restrictions.eq(NODE, node))
                .setProjection(Projections.max(TIMESTAMP));

        // get latest node reading by comparing it with max timestamp
        final Criteria criteria = session.createCriteria(LastNodeReading.class);
        criteria.add(Restrictions.eq(NODE, node));
        criteria.add(Property.forName(TIMESTAMP).eq(maxTimestamp));
        criteria.setMaxResults(1);
        return (LastNodeReading) criteria.uniqueResult();
    }

    /**
     * Returns the latest node reading fo the LastNodeReadings of all capabilities.
     *
     * @param capability a capability.
     * @return a last reading for a node.
     */
    public LastNodeReading getLatestNodeReading(final Capability capability) {
        final Session session = this.getSessionFactory().getCurrentSession();

        // a detached criteria for max timestamp for last node readings in testbed and capability
        final DetachedCriteria maxTimestamp = DetachedCriteria.forClass(LastNodeReading.class)
                .add(Restrictions.eq(CAPABILITY, capability))
                .setProjection(Projections.max(TIMESTAMP));

        // get latest node reading by comparing it with max timestamp
        final Criteria criteria = session.createCriteria(LastNodeReading.class);
        criteria.add(Restrictions.eq(CAPABILITY, capability));
        criteria.add(Property.forName(TIMESTAMP).eq(maxTimestamp));
        criteria.setMaxResults(1);
        return (LastNodeReading) criteria.uniqueResult();
    }

    /**
     * Returns the latest node reading fo the LastNodeReadings of all capabilities
     *
     * @param testbed a testbed.
     * @param capability a capability.
     * @return a last reading for a node belonging to a testbed.
     */
    public LastNodeReading getLatestNodeReading(final Testbed testbed, final Capability capability) {

        // retrieve testbed setup
        final Setup setup = SetupController.getInstance().getByID(testbed.getSetup().getId());

        final Session session = this.getSessionFactory().getCurrentSession();

        // a detached criteria for max timestamp for last node readings in testbed and capability
        final DetachedCriteria maxTimestamp = DetachedCriteria.forClass(LastNodeReading.class)
                .add(Restrictions.in(NODE, setup.getNodes()))
                .add(Restrictions.eq(CAPABILITY, capability))
                .setProjection(Projections.max(TIMESTAMP));

        // get latest node reading by comparing it with max timestamp
        final Criteria criteria = session.createCriteria(LastNodeReading.class);
        criteria.add(Restrictions.in(NODE, setup.getNodes()));
        criteria.add(Restrictions.eq(CAPABILITY, capability));
        criteria.add(Property.forName(TIMESTAMP).eq(maxTimestamp));
        criteria.setMaxResults(1);
        return (LastNodeReading) criteria.uniqueResult();
    }
}
