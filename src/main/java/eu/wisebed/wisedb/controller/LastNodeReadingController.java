package eu.wisebed.wisedb.controller;

import eu.wisebed.wisedb.model.LastNodeReading;
import eu.wisebed.wiseml.model.setup.Capability;
import eu.wisebed.wiseml.model.setup.Node;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import java.sql.Timestamp;
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
     * Returns the latest node reading fo the LastNodeReadings of all capabilities.
     * @param node , a node.
     * @return a LastNodeReading entry
     */
    public LastNodeReading getLatestNodeReading(final Node node){
        final Session session = this.getSessionFactory().getCurrentSession();

        // get max timestamp
        Criteria criteria = session.createCriteria(LastNodeReading.class);
        criteria.add(Restrictions.eq("node",node));
        criteria.setProjection(Projections.max("timestamp"));
        criteria.setMaxResults(1);
        long maxTimestamp = ((Timestamp) criteria.uniqueResult()).getTime();

        // get latest node reading by comparing it with max timestamp
        criteria = session.createCriteria(LastNodeReading.class);
        criteria.add(Restrictions.eq("node",node));
        criteria.add(Restrictions.eq("timestamp", maxTimestamp));
        criteria.setMaxResults(1);
        return (LastNodeReading) criteria.uniqueResult();
    }

    /**
     * Returns the latest node reading fo the LastNodeReadings of all capabilities.
     * @param capability , a capability.
     * @return a LastNodeReading entry
     */
    public LastNodeReading getLatestNodeReading(final Capability capability){
        final Session session = this.getSessionFactory().getCurrentSession();

        // get max timestamp
        Criteria criteria = session.createCriteria(LastNodeReading.class);
        criteria.add(Restrictions.eq("capability",capability));
        criteria.setProjection(Projections.max("timestamp"));
        criteria.setMaxResults(1);
        long maxTimestamp = ((Timestamp) criteria.uniqueResult()).getTime();

        // get latest node reading by comparing it with max timestamp
        criteria = session.createCriteria(LastNodeReading.class);
        criteria.add(Restrictions.eq("capability",capability));
        criteria.add(Restrictions.eq("timestamp",maxTimestamp));
        criteria.setMaxResults(1);
        return (LastNodeReading) criteria.uniqueResult();
    }
}
