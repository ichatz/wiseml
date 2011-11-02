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
     * Returns the last reading row inserted in the persistence for a specific node & capability
     *
     * @param node       , a node.
     * @param capability , a capability.
     * @return the last node reading.
     */
    public LastNodeReading getByID(final Node node, final Capability capability){
        final Session session = this.getSessionFactory().getCurrentSession();
        LastNodeReading lastNodeReading = new LastNodeReading();
        lastNodeReading.setNode(node);
        lastNodeReading.setCapability(capability);
        return (LastNodeReading) session.get(LastNodeReading.class, lastNodeReading);
    }

    /**
     * Returns a list of last reading rows inserted in the persistence for a specific node
     * @param node , a node.
     * @return a list of last reading rows for each capability.
     */
    public List<LastNodeReading> getByNode(final Node node){
        final Session session = this.getSessionFactory().getCurrentSession();
        Criteria criteria = session.createCriteria(LastNodeReading.class);
        criteria.add(Restrictions.eq("node",node));
        return (List<LastNodeReading>) criteria.list();
    }

    /**
     * Returns a list of last reading rows inserted in the persistence for a specific capability.
     * @param capability , a capability.
     * @return a list of last reading rows for each capability.
     */
    public List<LastNodeReading> getByCapability(final Capability capability){
        final Session session = this.getSessionFactory().getCurrentSession();
        Criteria criteria = session.createCriteria(LastNodeReading.class);
        criteria.add(Restrictions.eq("capability",capability));
        return (List<LastNodeReading>) criteria.list();
    }

    /**
     * Returns a list of last reading rows inserted in the persistence for a specific capability.
     * @param testbed , a testbed
     * @param capability , a capability.
     * @return a list of last node reading rows for each capability. Nodes belong to a specific testbed.
     */
    public List<LastNodeReading> getByCapability(final Testbed testbed, final Capability capability){

        // retrieve testbed setup
        Setup setup = SetupController.getInstance().getByID(testbed.getSetup().getId());

        final Session session = this.getSessionFactory().getCurrentSession();
        Criteria criteria = session.createCriteria(LastNodeReading.class);
        criteria.add(Restrictions.in("node", setup.getNodes()));
        criteria.add(Restrictions.eq("capability",capability));
        return (List<LastNodeReading>) criteria.list();
    }

    /**
     * Returns the latest node reading fo the LastNodeReadings of all capabilities.
     * @param node , a node.
     * @return a LastNodeReading entry
     */
    public LastNodeReading getLatestNodeReading(final Node node){
        final Session session = this.getSessionFactory().getCurrentSession();

        // a detached criteria for max timestamp for last node readings in testbed and capability
        DetachedCriteria maxTimestamp = DetachedCriteria.forClass(LastNodeReading.class)
                .add(Restrictions.eq("node",node))
                .setProjection(Projections.max("timestamp"));

        // get latest node reading by comparing it with max timestamp
        Criteria criteria = session.createCriteria(LastNodeReading.class);
        criteria.add(Restrictions.eq("node",node));
        criteria.add(Property.forName("timestamp").eq(maxTimestamp));
        criteria.setMaxResults(1);
        return (LastNodeReading) criteria.uniqueResult();
    }

    /**
     * Returns the latest node reading fo the LastNodeReadings of all capabilities.
     * @param capability , a capability.
     * @return a last reading for a node.
     */
    public LastNodeReading getLatestNodeReading(final Capability capability){
        final Session session = this.getSessionFactory().getCurrentSession();

        // a detached criteria for max timestamp for last node readings in testbed and capability
        DetachedCriteria maxTimestamp = DetachedCriteria.forClass(LastNodeReading.class)
                .add(Restrictions.eq("capability",capability))
                .setProjection(Projections.max("timestamp"));

        // get latest node reading by comparing it with max timestamp
        Criteria criteria = session.createCriteria(LastNodeReading.class);
        criteria.add(Restrictions.eq("capability",capability));
        criteria.add(Property.forName("timestamp").eq(maxTimestamp));
        criteria.setMaxResults(1);
        return (LastNodeReading) criteria.uniqueResult();
    }

    /**
     * Returns the latest node reading fo the LastNodeReadings of all capabilities
     * @param testbed , a testbed.
     * @param capability , a capability.
     * @return a last reading for a node belonging to a testbed.
     */
    public LastNodeReading getLatestNodeReading(final Testbed testbed,final Capability capability){

        // retrieve testbed setup
        Setup setup = SetupController.getInstance().getByID(testbed.getSetup().getId());

        final Session session = this.getSessionFactory().getCurrentSession();

        // a detached criteria for max timestamp for last node readings in testbed and capability
        DetachedCriteria maxTimestamp = DetachedCriteria.forClass(LastNodeReading.class)
                .add(Restrictions.in("node",setup.getNodes()))
                .add(Restrictions.eq("capability",capability))
                .setProjection(Projections.max("timestamp"));

        // get latest node reading by comparing it with max timestamp
        Criteria criteria = session.createCriteria(LastNodeReading.class);
        criteria.add(Restrictions.in("node", setup.getNodes()));
        criteria.add(Restrictions.eq("capability",capability));
        criteria.add(Property.forName("timestamp").eq(maxTimestamp));
        criteria.setMaxResults(1);

        return (LastNodeReading) criteria.uniqueResult();
    }
}
