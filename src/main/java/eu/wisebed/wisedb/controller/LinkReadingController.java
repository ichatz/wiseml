package eu.wisebed.wisedb.controller;

import eu.wisebed.wisedb.exception.UnknownTestbedException;
import eu.wisebed.wisedb.model.LastLinkReading;
import eu.wisebed.wisedb.model.LinkReading;
import eu.wisebed.wisedb.model.NodeReading;
import eu.wisebed.wisedb.model.Testbed;
import eu.wisebed.wiseml.model.setup.Capability;
import eu.wisebed.wiseml.model.setup.Link;
import eu.wisebed.wiseml.model.setup.Node;
import eu.wisebed.wiseml.model.setup.Rssi;
import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
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
     * Logger.
     */
    private static final Logger LOGGER = Logger.getLogger(LinkReadingController.class);

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
     * Prepares and inserts a node to the testbed's setup with the id provided.
     *
     * @param testbed , a testbed instance.
     * @param nodeId  , a node id.
     * @return returns the inserted node instance.
     */
    private Node prepareInsertNode(final Testbed testbed, final String nodeId) {
        LOGGER.info("prepareInsertNode(" + testbed + "," + nodeId + ")");
        final Node node = new Node();
        node.setId(nodeId);
        node.setDescription(DESCRIPTION);
        node.setProgramDetails(PROGRAM_DETAILS);
        node.setGateway("false");
        node.setReadings(new HashSet<NodeReading>());
        node.setCapabilities(new ArrayList<Capability>());
        node.setSetup(testbed.getSetup());
        NodeController.getInstance().add(node);

        return node;
    }

    /**
     * Prepares and inserts a link to the testbed setup  with the provided ids as source and target.
     *
     * @param testbed  , a testbed instance.
     * @param sourceId , a source node id.
     * @param targetId , a target node id.
     * @return returns the inserted link instance.
     */
    private Link prepareInsertLink(final Testbed testbed, final String sourceId, final String targetId) {
        LOGGER.info("prepareInsertLink(" + testbed + "," + sourceId + "," + targetId + ")");

        final Rssi rssi = new Rssi();
        rssi.setDatatype(DATATYPE);
        rssi.setUnit(UNIT);
        rssi.setValue(ZERO);

        final Link link = new Link();
        link.setSource(sourceId);
        link.setTarget(targetId);
        link.setEncrypted(false);
        link.setVirtual(false);
        link.setRssi(rssi);
        link.setCapabilities(new ArrayList<Capability>());
        link.setReadings(new HashSet<LinkReading>());
        link.setSetup(testbed.getSetup());
        LinkController.getInstance().add(link);

        return link;
    }

    /**
     * Prepares and inserts a capability to the persistnce with the provided capability name.
     *
     * @param capabilityName , a capability name.
     * @return returns the inserted capability instance.
     */
    private Capability prepareInsertCapability(final String capabilityName) {
        LOGGER.info("prepareInsertCapability(" + capabilityName + ")");

        final Capability capability = new Capability();

        capability.setName(capabilityName);
        capability.setDatatype(DATATYPE);
        capability.setDefaultvalue(DEFAULT_VALUE);
        capability.setUnit(UNIT);
        capability.setNodes(new HashSet<Node>());
        capability.setNodeReadings(new HashSet<NodeReading>());
        capability.setLinks(new HashSet<Link>());
        capability.setLinkReadings(new HashSet<LinkReading>());
        CapabilityController.getInstance().add(capability);

        return capability;
    }


    /**
     * Insert a links's reading from it's capabilities and make the appropriate  associations.
     *
     * @param sourceId       , link's source id.
     * @param targetId       , target's source id.
     * @param capabilityName , capability's id.
     * @param testbedId      , a testbed id.
     * @param rssiValue      , the RSSI value of the link.
     * @param readingValue   , value of a sensor reading.
     * @param timestamp      , a timestamp.
     * @throws UnknownTestbedException exception that occurs when the urnPrefix is unknown
     */
    public void insertReading(final String sourceId, final String targetId, final String capabilityName,
                              final int testbedId, final Double readingValue, final Double rssiValue,
                              final Date timestamp) throws UnknownTestbedException {

        LOGGER.info("insertReading(" + sourceId + "," + targetId + "," + capabilityName + "," + testbedId
                + "," + readingValue + "," + rssiValue + "," + timestamp + ")");

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
            prepareInsertNode(testbed, sourceId);
        }

        // look for source
        final Node target = NodeController.getInstance().getByID(targetId);
        if (target == null) {
            // if target node not found in db make it and store it
            LOGGER.info("Node [" + targetId + "] was not found in db . Storing it");
            prepareInsertNode(testbed, targetId);
        }

        // look for link
        Link link = LinkController.getInstance().getByID(sourceId, targetId);
        if (link == null) {
            // if link was not found in db make it and store it
            LOGGER.info("Link [" + sourceId + "," + targetId + "] was not found in db . Storing it");
            link = prepareInsertLink(testbed, sourceId, targetId);
        }

        // look for capability
        Capability capability = CapabilityController.getInstance().getByID(capabilityName);
        if (capability == null) {
            // if capability not found found in db make it and store it.
            LOGGER.info("Capability [" + sourceId + "," + targetId + "] was not found in db . Storing it");
            capability = prepareInsertCapability(capabilityName);
        }

        // check and make associations with link and capability.
        final boolean isAssociated = LinkController.getInstance().isAssociated(capability, testbed, link);
        if (!isAssociated) {
            LOGGER.info("Associate Link[" + sourceId + "," + targetId + "] Capability [" + capabilityName + "] ");
            // if link and capability are not associated , associate them
            link.getCapabilities().add(capability);
            LinkController.getInstance().update(link);
        }

//        if (link == null) {
//            link = prepareInsertLink(testbed, sourceId, targetId);
//            if (capability == null) {
//                capability = prepareInsertCapability(capabilityName);
//                link.getCapabilities().add(capability);
//                LinkController.getInstance().update(link);
//            } else {
//                link.getCapabilities().add(capability);
//                LinkController.getInstance().update(link);
//            }
//        } else {
//            if (capability == null) {
//                capability = prepareInsertCapability(capabilityName);
//                link.getCapabilities().add(capability);
//                LinkController.getInstance().update(link);
//            } else {
//                if (!LinkController.getInstance().isAssociated(capability, testbed, link)) {
//                    link.getCapabilities().add(capability);
//                    LinkController.getInstance().update(link);
//                }
//            }
//        }
//
//        // associate Link with Capability
//        if (!link.getCapabilities().contains(capability)) {
//            // if link does not contain this capability add it
//            link.getCapabilities().add(capability);
//            LinkController.getInstance().update(link);
//        }
//        if (!capability.getLinks().contains(link)) {
//            // if capability contains this link add it
//            capability.getLinks().add(link);
//            CapabilityController.getInstance().update(capability);
//        }


        // make a new link reading entity
        final LinkReading reading = new LinkReading();
        reading.setLink(link);
        reading.setCapability(capability);
        reading.setReading(readingValue);
        reading.setRssiValue(rssiValue);
        reading.setTimestamp(timestamp);

        // add reading
        add(reading);

        // get last link reading for link and capability if not found create one
        LastLinkReading lastLinkReading = LastLinkReadingController.getInstance().getByID(link, capability);
        if (lastLinkReading == null) {
            // if last link reading was not found
            LOGGER.info("Last link reading for Link[" + sourceId + "," + targetId
                    + "] Capability [" + capabilityName + "] created");
            lastLinkReading = new LastLinkReading();
        }
        lastLinkReading.setReading(readingValue);
        lastLinkReading.setTimestamp(timestamp);
        lastLinkReading.setLink(link);
        lastLinkReading.setCapability(capability);
        lastLinkReading.setRssiValue(rssiValue);
        LastLinkReadingController.getInstance().add(lastLinkReading);
    }

    /**
     * Returns the readings count for a link.
     *
     * @param link , a link .
     * @return the count of this link.
     */
    public Long getLinkReadingsCount(final Link link) {
        LOGGER.info("getLinkReadingsCount(" + link + ")");
        final org.hibernate.Session session = getSessionFactory().getCurrentSession();
        final Criteria criteria = session.createCriteria(LinkReading.class);
        criteria.createAlias(LINK, "id");
        criteria.add(Restrictions.eq("id.source", link.getSource()));
        criteria.add(Restrictions.eq("id.target", link.getTarget()));
        criteria.setProjection(Projections.count("id"));
        criteria.setMaxResults(1);
        return (Long) criteria.uniqueResult();
    }
}
