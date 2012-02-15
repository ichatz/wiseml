package eu.wisebed.wisedb.controller;

import eu.wisebed.wisedb.exception.UnknownTestbedException;
import eu.wisebed.wisedb.model.LastLinkReading;
import eu.wisebed.wisedb.model.Link;
import eu.wisebed.wisedb.model.LinkCapability;
import eu.wisebed.wisedb.model.LinkReading;
import eu.wisebed.wisedb.model.Node;
import eu.wisebed.wisedb.model.Testbed;
import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import java.util.Date;
import java.util.List;

/**
 * CRUD operations for LinkReading entities.
 */
@SuppressWarnings("unchecked")
public class LinkReadingController extends AbstractController<LinkReading> {

    /**
     * static instance(ourInstance) initialized as null.
     */
    private static LinkReadingController ourInstance = null;
    /**
     * Description literal.
     */
    private static final String DESCRIPTION = "DESCRIPTION";
    /**
     * Program details literal.
     */
    private static final String PROGRAM_DETAILS = "PROGRAM_DETAILS";
    /**
     * Unit literal.
     */
    private static final String UNIT = "UNIT";
    /**
     * Zero literal.
     */
    private static final String ZERO = "0.0";
    /**
     * Datatype literal.
     */
    private static final String DATATYPE = "DATATYPE";

    /**
     * Default value literal.
     */
    private static final String DEFAULT_VALUE = "DEFAULT_VALUE";

    /**
     * Link value literal.
     */
    private static final String LINK = "link";
    /**
     * Source literal.
     */
    private static final String SOURCE = "link_source";
    /**
     * Target literal.
     */
    private static final String TARGET = "link_target";

    /**
     * Logger.
     */
    private static final Logger LOGGER = Logger.getLogger(LinkReadingController.class);
    private static final String CAPABILITY = "capability";

    /**
     * Public constructor .
     */
    public LinkReadingController() {
        // Does nothing
        super();
    }

    /**
     * LinkReadingController is loaded on the first execution of
     * LinkReadingController.getInstance() or the first access to
     * LinkReadingController.ourInstance, not before.
     *
     * @return ourInstance
     */
    public static LinkReadingController getInstance() {
        synchronized (LinkReadingController.class) {
            if (ourInstance == null) {
                ourInstance = new LinkReadingController();
            }
        }

        return ourInstance;
    }

    /**
     * Listing all the LinkReadings from the database.
     *
     * @return a list of all the entries that exist inside the table LinkReadings.
     */
    public List<LinkReading> list() {
        LOGGER.info("list()");
        return super.list(new LinkReading());
    }

    public List<LinkReading> list(Link link) {
        LOGGER.info("list(" + link.getSource() + "--" + link.getTarget() + ")");
        final org.hibernate.Session session = getSessionFactory().getCurrentSession();
        final Criteria criteria = session.createCriteria(LinkReading.class);
        criteria.add(Restrictions.eq(SOURCE, link.getSource()));
        criteria.add(Restrictions.eq(TARGET, link.getTarget()));
        return criteria.list();

    }

    /**
     * Deleting an entry into the database.
     *
     * @param readingId , id of a reading entry
     */
    public void delete(final int readingId) {
        LOGGER.info("delete(" + readingId + ")");
        super.delete(new LinkReading(), readingId);
    }


    /**
     * Insert a links's reading from it's capabilities and make the appropriate  associations.
     *
     * @param sourceId       , link's source id.
     * @param targetId       , target's source id.
     * @param capabilityName , capability's id.
     * @param testbedId      , a testbed id.
     * @param stringReading  , value of a string reading.
     * @param doubleReading  , value of a sensor reading.
     * @param timestamp      , a timestamp.
     * @throws UnknownTestbedException exception that occurs when the urnPrefix is unknown
     */
    public void insertReading(final String sourceId, final String targetId, final String capabilityName,
                              final int testbedId, final Double doubleReading, final String stringReading,
                              final Date timestamp) throws UnknownTestbedException {

        LOGGER.info("insertReading(" + sourceId + "," + targetId + "," + capabilityName + "," + testbedId
                + "," + doubleReading + "," + stringReading + "," + timestamp + ")");

        // look for testbed
        final Testbed testbed = TestbedController.getInstance().getByID(testbedId);
        if (testbed == null) {
            throw new UnknownTestbedException(Integer.toString(testbedId));
        }

        // look for source
        final Node source = NodeController.getInstance().getByID(sourceId);
        if (source == null) {
            // if source node not found in db make it and store it
            LOGGER.info("Node [" + sourceId + "] was not found in db . Storing it");
            NodeController.getInstance().prepareInsertNode(testbed, sourceId);
        }

        // look for target
        final Node target = NodeController.getInstance().getByID(targetId);
        if (target == null) {
            // if target node not found in db make it and store it
            LOGGER.info("Node [" + targetId + "] was not found in db . Storing it");
            NodeController.getInstance().prepareInsertNode(testbed, targetId);
        }
//
//        // look for link
//        Link link = LinkController.getInstance().getByID(sourceId, targetId);
//        if (link == null) {
//            // if link was not found in db make it and store it
//            LOGGER.info("Link [" + sourceId + "," + targetId + "] was not found in db . Storing it");
//            link = prepareInsertLink(testbed, sourceId, targetId);
//        }
//
//        // look for capability
//        Capability capability = CapabilityController.getInstance().getByID(capabilityName);
//        if (capability == null) {
//            // if capability not found found in db make it and store it.
//            LOGGER.info("Capability [" + sourceId + "," + targetId + "] was not found in db . Storing it");
//            capability = prepareInsertCapability(capabilityName);
//        }
//
//        // check and make associations with link and capability.
//        final boolean isAssociated = LinkController.getInstance().isAssociated(capability, testbed, link);
//        if (!isAssociated) {
//            LOGGER.info("Associate Link[" + sourceId + "," + targetId + "] Capability [" + capabilityName + "] ");
//            // if link and capability are not associated , associate them
//            link.getCapabilities().add(capability);
//            LinkController.getInstance().update(link);
//        }

        Link link = LinkController.getInstance().getByID(sourceId, targetId);
        LinkCapability linkCapability;
        if (link == null) {
            LOGGER.debug("link==null");
            link = LinkController.getInstance().prepareInsertLink(testbed, sourceId, targetId);
            linkCapability = LinkCapabilityController.getInstance().prepareInsertLinkCapability(link, capabilityName);
        } else {
            linkCapability = LinkCapabilityController.getInstance().getByID(link, capabilityName);
            if (linkCapability == null) {
                linkCapability = LinkCapabilityController.getInstance().prepareInsertLinkCapability(link, capabilityName);
                LinkController.getInstance().update(link);
            }
        }

        // make a new link reading entity
        final LinkReading reading = new LinkReading();
        reading.setCapability(linkCapability);
        reading.setReading(doubleReading);
        reading.setStringReading(stringReading);
        reading.setTimestamp(timestamp);

        // add reading
        add(reading);


        // get last link reading for link and capability if not found create one
        LastLinkReading lastLinkReading = linkCapability.getLastLinkReading();
        if (lastLinkReading == null) {
            // if last link reading was not found
            LOGGER.info("Last link reading for LinkCapability [" + linkCapability.toString() + "] created");
            lastLinkReading = new LastLinkReading();
        }
        lastLinkReading.setReading(doubleReading);
        lastLinkReading.setStringReading(stringReading);
        lastLinkReading.setTimestamp(timestamp);
        lastLinkReading.setId(linkCapability.getId());

        linkCapability.setLastLinkReading(lastLinkReading);

        LinkCapabilityController.getInstance().update(linkCapability);
    }

    /**
     * Returns the readings count for a link.
     *
     * @param link , a link .
     * @return the count of this link.
     */
    public int getLinkReadingsCount(final Link link) {
        LOGGER.info("getLinkReadingsCount(" + link + ")");
        final List<LinkCapability> linkCapabilities = LinkCapabilityController.getInstance().list(link);
        Integer result = 0;
        if (linkCapabilities.size()>0) {
            final org.hibernate.Session session = getSessionFactory().getCurrentSession();
            final Criteria criteria = session.createCriteria(LinkReading.class);
            criteria.add(Restrictions.in(CAPABILITY, linkCapabilities));
            criteria.setProjection(Projections.rowCount());
            final Long count = (Long) criteria.uniqueResult();
            result += count.intValue();
        }

        return result.intValue();

    }

    public Long count() {
        LOGGER.info("count()");
        Criteria criteria = null;
        final Session session = getSessionFactory().getCurrentSession();
        criteria = session.createCriteria(LinkReading.class);
        criteria.setProjection(Projections.rowCount());
        return (Long) criteria.uniqueResult();
    }
}
