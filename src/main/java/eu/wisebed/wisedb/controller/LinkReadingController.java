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
import org.hibernate.Criteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

public class LinkReadingController extends AbstractController<LinkReading> {

    /**
     * static instance(ourInstance) initialized as null.
     */
    private static LinkReadingController ourInstance = null;

    private static final String DESCRIPTION = "DESCRIPTION"; // todo provide those ?
    private static final String PROGRAM_DETAILS = "program details"; // todo provide those ?
    private static final String UNIT = "unit"; // todo provide those ?
    private static final String ZERO = "0.0"; // todo provide those ?
    private static final String DATATYPE = "datatype"; // todo provide those ?
    private static final String DEFAULT_VALUE = "default value"; // todo provide those ?
    private static final String LINK ="link";

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
        return super.list(new LinkReading());
    }

    /**
     * Deleting an entry into the database.
     *
     * @param readingId , id of a reading entry
     */
    public void delete(final int readingId) {
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
     * Insert a links's reading from it's capabilities and make the appropriate relations
     * such as Link-Reading , Capability-reading
     *
     * @param sourceId       , link's source id.
     * @param targetId       , target's source id.
     * @param capabilityName , capability's id.
     * @param urnPrefix      , a testbed urn prefix.
     * @param rssiValue      , the RSSI value of the link.
     * @param readingValue   , value of a sensor reading.
     * @param timestamp      , a timestamp.
     * @throws eu.wisebed.wisedb.exception.UnknownTestbedException
     *          , exception that occurs when the urnPrefix is unknown
     */
    public void insertReading(final String sourceId, final String targetId, final String capabilityName,
                              final String urnPrefix, final double readingValue, final double rssiValue,
                              final Date timestamp) throws UnknownTestbedException {

        // look for testbed
        final Testbed testbed = TestbedController.getInstance().getByUrnPrefix(urnPrefix);
        if (testbed == null) {
            throw new UnknownTestbedException(urnPrefix);
        }
        // look for node and target
        final Node source = NodeController.getInstance().getByID(sourceId);
        if (source == null) {
            // if source node not found in db make it and store it
            prepareInsertNode(testbed, sourceId);
        }
        final Node target = NodeController.getInstance().getByID(targetId);
        if (target == null) {
            // if target node not found in db make it and store it
            prepareInsertNode(testbed, targetId);
        }

        // look for link
        Link link = LinkController.getInstance().getByID(sourceId, targetId);
        if (link == null) {
            // if link not found in db make it and store it
            link = prepareInsertLink(testbed, sourceId, targetId);
        }

        // look for capability
        Capability capability = CapabilityController.getInstance().getByID(capabilityName);
        if (capability == null) {
            capability = prepareInsertCapability(capabilityName);
        }

        // associate Link with Capability
        if (!link.getCapabilities().contains(capability)) {
            // if link does not contain this capability add it
            link.getCapabilities().add(capability);
            LinkController.getInstance().update(link);
        }
        if (!capability.getLinks().contains(link)) {
            // if capability contains this link add it
            capability.getLinks().add(link);
            CapabilityController.getInstance().update(capability);
        }


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
            lastLinkReading = new LastLinkReading();
        }
        lastLinkReading.setReading(readingValue);
        lastLinkReading.setTimestamp(timestamp);
        lastLinkReading.setLink(link);
        lastLinkReading.setCapability(capability);
        LastLinkReadingController.getInstance().add(lastLinkReading);
    }

    /**
     * Returns the readings count for a link.
     *
     * @param link , a link .
     * @return the count of this link.
     */
    public Long getLinkReadingsCount(final Link link) {
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
