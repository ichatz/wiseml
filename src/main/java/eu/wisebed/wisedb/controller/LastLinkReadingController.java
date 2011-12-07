package eu.wisebed.wisedb.controller;

import eu.wisebed.wisedb.model.LastLinkReading;
import eu.wisebed.wisedb.model.Testbed;
import eu.wisebed.wiseml.model.setup.Capability;
import eu.wisebed.wiseml.model.setup.Link;
import eu.wisebed.wiseml.model.setup.Setup;
import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import java.util.List;

/**
 * CRUD operations for LastLinkReading entities.
 */
@SuppressWarnings("unchecked")
public class LastLinkReadingController extends AbstractController<LastLinkReading> {

    /**
     * static instance(ourInstance) initialized as null.
     */
    private static LastLinkReadingController ourInstance = null;

    /**
     * Link literal.
     */
    private static final  String LINK = "link";

    /**
     * Capability literal.
     */
    private static final String CAPABILITY = "capability";

    /**
     * Logger.
     */
    private static final Logger LOGGER = Logger.getLogger(LastLinkReadingController.class);

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
        LOGGER.info("add(" + link + "," + capability + ")");
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
        LOGGER.info("getByLink(" + link + ")");
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
        LOGGER.info("getByTestbed(" + testbed + ")");
        // retrieve testbed nodes from setup
        final Setup setup = SetupController.getInstance().getByID(testbed.getSetup().getId());
        List<Link> links = setup.getLink();
        if (links == null || links.isEmpty()) {
            return null;
        }

        final Session session = this.getSessionFactory().getCurrentSession();
        final Criteria criteria = session.createCriteria(LastLinkReading.class);
        criteria.add(Restrictions.in(LINK, links));
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
        LOGGER.info("getByCapability(" + testbed + "," + capability +")");
        // retrieve testbed nodes from setup
        final Setup setup = SetupController.getInstance().getByID(testbed.getSetup().getId());
        List<Link> links = setup.getLink();
        if (links == null || links.isEmpty()) {
            return null;
        }

        final Session session = this.getSessionFactory().getCurrentSession();
        final Criteria criteria = session.createCriteria(LastLinkReading.class);
        criteria.add(Restrictions.in(LINK, links));
        criteria.add(Restrictions.eq(CAPABILITY, capability));
        return (List<LastLinkReading>) criteria.list();
    }

}
