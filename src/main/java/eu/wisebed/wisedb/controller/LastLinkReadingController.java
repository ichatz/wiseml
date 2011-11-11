package eu.wisebed.wisedb.controller;

import eu.wisebed.wisedb.model.LastLinkReading;
import eu.wisebed.wisedb.model.Testbed;
import eu.wisebed.wiseml.model.setup.Capability;
import eu.wisebed.wiseml.model.setup.Link;
import eu.wisebed.wiseml.model.setup.Setup;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;

import java.util.List;

/**
 * CRUD operations for LastLinkReading objects.
 */
public class LastLinkReadingController extends AbstractController<LastLinkReading> {

    /**
     * static instance(ourInstance) initialized as null.
     */
    private static LastLinkReadingController ourInstance = null;

    private final static String LINK = "link";
    private final static String CAPABILITY = "capability";
    private final static String TIMESTAMP = "timestamp";

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
     * Returns the last reading inserted in the persistence for a specific link & capability.
     *
     * @param link       , a link.
     * @param capability , a capability.
     * @return the last node reading.
     */
    public LastLinkReading getByID(final Link link, final Capability capability) {
        final Session session = this.getSessionFactory().getCurrentSession();
        final LastLinkReading lastLinkReading = new LastLinkReading();
        lastLinkReading.setLink(link);
        lastLinkReading.setCapability(capability);
        return (LastLinkReading) session.get(LastLinkReading.class, lastLinkReading);
    }

    /**
     * Returns a list of last reading rows inserted in the persistence for a specific link.
     *
     * @param link , a link.
     * @return a list of last reading rows for each capability.
     */
    public List<LastLinkReading> getByLink(final Link link) {
        final Session session = this.getSessionFactory().getCurrentSession();
        final Criteria criteria = session.createCriteria(LastLinkReading.class);
        criteria.add(Restrictions.eq(LINK, link));
        return (List<LastLinkReading>) criteria.list();
    }

    /**
     * Returns a list of last node reading entries for the nodes of a testbed.
     *
     * @param testbed , a testbed instance.
     * @return a list last node readings from a testbed's nodes
     */
    public List<LastLinkReading> getByTestbed(final Testbed testbed) {

        // retrieve testbed setup
        final Setup setup = SetupController.getInstance().getByID(testbed.getSetup().getId());

        final Session session = this.getSessionFactory().getCurrentSession();
        final Criteria criteria = session.createCriteria(LastLinkReading.class);
        criteria.add(Restrictions.in(LINK, setup.getLink()));
        return (List<LastLinkReading>) criteria.list();
    }

    /**
     * Returns a list of last reading rows inserted in the persistence for a specific capability.
     *
     * @param capability , a capability.
     * @return a list of last reading rows for each capability.
     */
    public List<LastLinkReading> getByCapability(final Capability capability) {
        final Session session = this.getSessionFactory().getCurrentSession();
        final Criteria criteria = session.createCriteria(LastLinkReading.class);
        criteria.add(Restrictions.eq(CAPABILITY, capability));
        return (List<LastLinkReading>) criteria.list();
    }

    /**
     * Returns a list of last reading rows inserted in the persistence for a specific capability.
     *
     * @param testbed    ,  a testbed.
     * @param capability , a capability.
     * @return a list of last reading rows for each capability.
     */
    public List<LastLinkReading> getByCapability(final Testbed testbed, final Capability capability) {

        // retrieve testbed setup
        final Setup setup = SetupController.getInstance().getByID(testbed.getSetup().getId());

        final Session session = this.getSessionFactory().getCurrentSession();
        final Criteria criteria = session.createCriteria(LastLinkReading.class);
        criteria.add(Restrictions.in(LINK, setup.getLink()));
        criteria.add(Restrictions.eq(CAPABILITY, capability));
        return (List<LastLinkReading>) criteria.list();
    }

    /**
     * Returns the latest node reading fo the LastNodeReadings of all capabilities.
     *
     * @param link , a link.
     * @return a LastNodeReading entry
     */
    public LastLinkReading getLatestLinkReading(final Link link) {
        final Session session = this.getSessionFactory().getCurrentSession();

        // a detached criteria for max timestamp for last node readings in testbed and capability
        final DetachedCriteria maxTimestamp = DetachedCriteria.forClass(LastLinkReading.class)
                .add(Restrictions.eq(LINK, link))
                .setProjection(Projections.max(TIMESTAMP));

        // get latest node reading by comparing it with max timestamp
        final Criteria criteria = session.createCriteria(LastLinkReading.class);
        criteria.add(Restrictions.eq(LINK, link));
        criteria.add(Property.forName(TIMESTAMP).eq(maxTimestamp));
        criteria.setMaxResults(1);
        return (LastLinkReading) criteria.uniqueResult();
    }

    /**
     * Returns the latest link reading fo the LastNodeReadings of all capabilities.
     *
     * @param capability , a capability.
     * @return a last reading for a link.
     */
    public LastLinkReading getLatestLinkReading(final Capability capability) {
        final Session session = this.getSessionFactory().getCurrentSession();

        // a detached criteria for max timestamp for last node readings in testbed and capability
        final DetachedCriteria maxTimestamp = DetachedCriteria.forClass(LastLinkReading.class)
                .add(Restrictions.eq(CAPABILITY, capability))
                .setProjection(Projections.max(TIMESTAMP));

        // get latest link reading by comparing it with max timestamp
        final Criteria criteria = session.createCriteria(LastLinkReading.class);
        criteria.add(Restrictions.eq(CAPABILITY, capability));
        criteria.add(Property.forName(TIMESTAMP).eq(maxTimestamp));
        criteria.setMaxResults(1);
        return (LastLinkReading) criteria.uniqueResult();
    }

    /**
     * Returns the latest link reading fo the LastNodeReadings of all capabilities.
     *
     * @param testbed    , a testbed.
     * @param capability , a capability.
     * @return a last reading for a link belonging to a testbed.
     */
    public LastLinkReading getLatestLinkReading(final Testbed testbed, final Capability capability) {

        // retrieve testbed setup
        final Setup setup = SetupController.getInstance().getByID(testbed.getSetup().getId());

        final Session session = this.getSessionFactory().getCurrentSession();

        // a detached criteria for max timestamp for last node readings in testbed and capability
        final DetachedCriteria maxTimestamp = DetachedCriteria.forClass(LastLinkReading.class)
                .add(Restrictions.in(LINK, setup.getLink()))
                .add(Restrictions.eq(CAPABILITY, capability))
                .setProjection(Projections.max(TIMESTAMP));

        // get latest link reading by comparing it with max timestamp
        final Criteria criteria = session.createCriteria(LastLinkReading.class);
        criteria.add(Restrictions.in(LINK, setup.getLink()));
        criteria.add(Restrictions.eq(CAPABILITY, capability));
        criteria.add(Property.forName(TIMESTAMP).eq(maxTimestamp));
        criteria.setMaxResults(1);
        return (LastLinkReading) criteria.uniqueResult();
    }
}
