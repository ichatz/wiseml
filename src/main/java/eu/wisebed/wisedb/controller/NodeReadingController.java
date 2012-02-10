package eu.wisebed.wisedb.controller;

import eu.wisebed.wisedb.exception.UnknownTestbedException;
import eu.wisebed.wisedb.model.LastNodeReading;
import eu.wisebed.wisedb.model.LinkReading;
import eu.wisebed.wisedb.model.Node;
import eu.wisebed.wisedb.model.NodeReading;
import eu.wisebed.wisedb.model.Testbed;
import eu.wisebed.wiseml.model.setup.Capability;
import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.classic.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

/**
 * CRUD operations for NodeReadings entities.
 */
@SuppressWarnings("unchecked")
public class NodeReadingController extends AbstractController<NodeReading> {

    /**
     * static instance(ourInstance) initialized as null.
     */
    private static NodeReadingController ourInstance = null;

    /**
     * Descriptions literal.
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
     * Datatype literal.
     */
    private static final String DATATYPE = "DATATYPE";

    /**
     * Default value literal.
     */
    private static final String DEFAULT_VALUE = "DEFAULT_VALUE";

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
     * Logger.
     */
    private static final Logger LOGGER = Logger.getLogger(NodeReadingController.class);

    /**
     * Public constructor .
     */
    public NodeReadingController() {
        // Does nothing
        super();
    }

    /**
     * NodeReadingController is loaded on the first execution of
     * NodeReadingController.getInstance() or the first access to
     * NodeReadingController.ourInstance, not before.
     *
     * @return ourInstance
     */
    public static NodeReadingController getInstance() {
        synchronized (NodeReadingController.class) {
            if (ourInstance == null) {
                ourInstance = new NodeReadingController();
            }
        }

        return ourInstance;
    }

    /**
     * Listing all the NodeReadings from the database.
     *
     * @return a list of all the entries that exist inside the table NodeReadings.
     */
    public List<NodeReading> list() {
        LOGGER.info("list()");
        return super.list(new NodeReading());
    }

    /**
     * Delete the node reading from the database.
     *
     * @param readingId the id of the node tha we want to delete
     */
    public void delete(final String readingId) {
        LOGGER.info("delete(" + readingId + ")");
        super.delete(new NodeReading(), readingId);
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
        //TODO
//        node.setDescription(DESCRIPTION);
//        node.setProgramDetails(PROGRAM_DETAILS);
//        node.setGateway("false");
        node.setSetup(testbed.getSetup());
        NodeController.getInstance().add(node);

        return node;
    }

    /**
     * Prepares and inserts a capability to the persistence with the provided capability name.
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
        capability.setNodeReadings(new HashSet<NodeReading>());
        capability.setLinkReadings(new HashSet<LinkReading>());
        CapabilityController.getInstance().add(capability);

        return capability;
    }

    /**
     * Insert a node's reading from it's capabilities and make the appropriate associations.
     *
     * @param nodeId         , a node id.
     * @param capabilityName , a capability name.
     * @param testbedId      , a testbed Id.
     * @param doubleReading  , a reading value (double).
     * @param stringReading  , a reading value (string).
     * @param timestamp      , a timestamp.
     * @throws UnknownTestbedException exception that occurs when the testbedId is unknown.
     */
    public void insertReading(final String nodeId, final String capabilityName, final int testbedId,
                              final Double doubleReading, final String stringReading, final Date timestamp)
            throws UnknownTestbedException {
        LOGGER.info("insertReading(" + nodeId + "," + capabilityName + "," + testbedId + "," + doubleReading + ","
                + stringReading + "," + timestamp + ")");

        // Retrieve testbed by urn
        final Testbed testbed = TestbedController.getInstance().getByID(testbedId);
        if (testbed == null) {
            throw new UnknownTestbedException(Integer.toString(testbedId));
        }

//        // get node if not found create one
//        Node node = NodeController.getInstance().getByID(nodeId);
//        if (node == null) {
//            LOGGER.info("Node [" + nodeId + "] was not found in db . Storing it");
//            node = prepareInsertNode(testbed, nodeId);
//        }
//
//        // get capability if not found create one
//        Capability capability = CapabilityController.getInstance().getByID(capabilityName);
//        if (capability == null) {
//            LOGGER.info("Capability [" + capabilityName + "] was not found in db. Storing it");
//            capability = prepareInsertCapability(capabilityName);
//        }
//
//        // if the given node and capability are not associated
//        final boolean isAssociated = NodeController.getInstance().isAssociated(capability, testbed, node);
//        if (!isAssociated) {
//            LOGGER.info("Associate Node[" + nodeId + "] Capability[" + capabilityName + "] ");
//            node.getCapabilities().add(capability);
//            NodeController.getInstance().update(node);
//        }

        Node node = NodeController.getInstance().getByID(nodeId);
        Capability capability = CapabilityController.getInstance().getByID(capabilityName);
        if (node == null) {
            node = prepareInsertNode(testbed, nodeId);
            if (capability == null) {
                capability = prepareInsertCapability(capabilityName);
                NodeController.getInstance().update(node);
            } else {
                NodeController.getInstance().update(node);
            }
//            throw new UnknownNodeException(nodeId);
        } else {
            if (capability == null) {
                capability = prepareInsertCapability(capabilityName);
                NodeController.getInstance().update(node);
//            throw new UnknownCapabilityException(capabilityName);
            } else {
                LOGGER.info("isAssociated " + NodeController.getInstance().isAssociated(capability, testbed, node));
                if (!NodeController.getInstance().isAssociated(capability, testbed, node)) {
                    NodeController.getInstance().update(node);
                }
            }
        }

        // make a new node reading entity
        final NodeReading reading = new NodeReading();
        reading.setReading(doubleReading);
        reading.setStringReading(stringReading);
        reading.setTimestamp(timestamp);
        reading.setNode(node);
        reading.setCapability(capability);

        // add reading
        add(reading);

        // get lastNodeReading if not found create one
        LastNodeReading lastNodeReading = LastNodeReadingController.getInstance().getByID(node, capability);
        if (lastNodeReading == null) {
            LOGGER.info("Last node reading for Node [" + nodeId + "] Capability [" + capabilityName + "] created");
            lastNodeReading = new LastNodeReading();
        }
        lastNodeReading.setReading(doubleReading);
        lastNodeReading.setStringReading(stringReading);
        lastNodeReading.setTimestamp(timestamp);
        lastNodeReading.setNode(node);
        lastNodeReading.setCapability(capability);
        LastNodeReadingController.getInstance().add(lastNodeReading);
    }

    /**
     * Return list of readings for a selected node and capability.
     *
     * @param node       , node of a testbed.
     * @param capability , capability of a node
     * @return a list with nodereadings for a node/capability combination
     */
    @SuppressWarnings("unchecked")
    public List<NodeReading> listNodeReadings(final Node node, final Capability capability) {
        LOGGER.info("listNodeReadings(" + node + "," + capability + ")");
        final Session session = getSessionFactory().getCurrentSession();
        final Criteria criteria = session.createCriteria(NodeReading.class);
        criteria.add(Restrictions.eq(NODE, node));
        criteria.add(Restrictions.eq(CAPABILITY, capability));
        criteria.addOrder(Order.desc(TIMESTAMP));
        return (List<NodeReading>) criteria.list();
    }

    /**
     * Return a limited list of readings for a selected node and capability.
     *
     * @param node       , node of a testbed.
     * @param capability , capability of a node
     * @param limit      , an integer that express the number of records to be returned.
     * @return a list with nodereadings for a node/capability combination
     */
    @SuppressWarnings("unchecked")
    public List<NodeReading> listNodeReadings(final Node node, final Capability capability, final int limit) {
        LOGGER.info("listNodeReadings(" + node + "," + capability + "," + limit + ")");
        final Session session = getSessionFactory().getCurrentSession();
        final Criteria criteria = session.createCriteria(NodeReading.class);
        criteria.add(Restrictions.eq(NODE, node));
        criteria.add(Restrictions.eq(CAPABILITY, capability));
        criteria.addOrder(Order.desc(TIMESTAMP));
        criteria.setMaxResults(limit);
        return (List<NodeReading>) criteria.list();
    }

    /**
     * Returns the readings count for a node.
     *
     * @param node , a node .
     * @return the count of this node.
     */
    public Long getNodeReadingsCount(final Node node) {
        LOGGER.info("getNodeReadingsCount(" + node + ")");
        final Session session = getSessionFactory().getCurrentSession();
        final Criteria criteria = session.createCriteria(NodeReading.class);
        criteria.add(Restrictions.eq(NODE, node));
        criteria.setProjection(Projections.count(NODE));
        criteria.setMaxResults(1);
        return (Long) criteria.uniqueResult();
    }

    /**
     * Returns the readings count for a node per capability.
     *
     * @param node , a node .
     * @return a map containing readings of a node per capability
     */
    @SuppressWarnings("unchecked")
    public Map<Capability, Long> getNodeReadingsCountMap(final Node node) {
        LOGGER.info("getNodeReadingsCountMap(" + node + ")");
        final Session session = getSessionFactory().getCurrentSession();
        final Criteria criteria = session.createCriteria(NodeReading.class);
        criteria.add(Restrictions.eq(NODE, node));
        criteria.setProjection(Projections.projectionList()
                .add(Projections.rowCount())
                .add(Projections.property(CAPABILITY))
                .add(Projections.groupProperty(CAPABILITY))
        );
        final HashMap<Capability, Long> resultMap = new HashMap<Capability, Long>();
        final List<Object> results = criteria.list();
        for (Object result : results) {
            final Object[] row = (Object[]) result;
            resultMap.put((Capability) row[1], (Long) row[0]);
        }
        return resultMap;
    }
}
