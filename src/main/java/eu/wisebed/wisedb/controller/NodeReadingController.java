package eu.wisebed.wisedb.controller;

import eu.wisebed.wisedb.exception.UnknownTestbedException;
import eu.wisebed.wisedb.model.LastNodeReading;
import eu.wisebed.wisedb.model.LinkReading;
import eu.wisebed.wisedb.model.NodeReading;
import eu.wisebed.wisedb.model.NodeReadingStat;
import eu.wisebed.wisedb.model.Testbed;
import eu.wisebed.wiseml.model.setup.Capability;
import eu.wisebed.wiseml.model.setup.Link;
import eu.wisebed.wiseml.model.setup.Node;
import eu.wisebed.wiseml.model.setup.Setup;
import org.hibernate.Criteria;
import org.hibernate.classic.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import java.util.ArrayList;
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
    private static final String PROGRAM_DETAILS = "program details";
    /**
     * Unit literal.
     */
    private static final String UNIT = "unit";
    /**
     * Datatype literal.
     */
    private static final String DATATYPE = "datatype";
    /**
     * Default value literal.
     */
    private static final String DEFAULT_VALUE = "default value";
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
     * Reading literal.
     */
    private static final String READING = "reading";

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
        return super.list(new NodeReading());
    }

    /**
     * Delete the node reading from the database.
     *
     * @param readingId the id of the node tha we want to delete
     */
    public void delete(final String readingId) {
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
     * Insert a node's reading from it's capabilities and make the appropriate associations.
     *
     * @param nodeId         , a node id.
     * @param capabilityName , a capability name.
     * @param urnPrefix      , a testbed urn prefix.
     * @param readingValue   , a reading value.
     * @param timestamp      , a timestamp.
     * @throws eu.wisebed.wisedb.exception.UnknownTestbedException
     *          , exception that occurs when the urnPrefix is unknown
     */
    public void insertReading(final String nodeId, final String capabilityName, final String urnPrefix,
                              final double readingValue, final Date timestamp) throws UnknownTestbedException {

        // Retrieve testbed by urn
        final Testbed testbed = TestbedController.getInstance().getByUrnPrefix(urnPrefix);
        if (testbed == null) {
            throw new UnknownTestbedException(urnPrefix);
        }

        // get node if not found create one
        Node node = NodeController.getInstance().getByID(nodeId);
        if (node == null) {
            node = prepareInsertNode(testbed, nodeId);
        }

        // get capability if not found create one
        Capability capability = CapabilityController.getInstance().getByID(capabilityName);
        if (capability == null) {
            capability = prepareInsertCapability(capabilityName);
        }

        // associate capability node
        if (!node.getCapabilities().contains(capability)) {
            node.getCapabilities().add(capability);
            NodeController.getInstance().update(node);
        }
        if (!capability.getNodes().contains(node)) {
            capability.getNodes().add(node);
            CapabilityController.getInstance().update(capability);
        }

        // make a new node reading entity
        final NodeReading reading = new NodeReading();
        reading.setReading(readingValue);
        reading.setTimestamp(timestamp);
        reading.setNode(node);
        reading.setCapability(capability);

        // add reading
        add(reading);

        // get lastNodeReading if not found create one
        LastNodeReading lastNodeReading = LastNodeReadingController.getInstance().getByID(node, capability);
        if (lastNodeReading == null) {
            lastNodeReading = new LastNodeReading();
        }
        lastNodeReading.setReading(readingValue);
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

    /**
     * Returns node reading stats of any node persisted.
     *
     * @return a list of NodeReadingStat objects
     */
    public List<NodeReadingStat> getNodeReadingStats() {
        final Session session = getSessionFactory().getCurrentSession();

        // get max/min reading for a node
        final Criteria criteria = session.createCriteria(NodeReading.class);
        criteria.setProjection(Projections.projectionList()
                .add(Projections.groupProperty(NODE))
                .add(Projections.max(READING))
                .add(Projections.min(READING))
                .add(Projections.rowCount())
        );
        final List<Object> results = (criteria.list() == null) ? (new ArrayList<Object>()) : criteria.list();

        // parsing the result array to a node reading stat array
        final List<NodeReadingStat> nodeReadingStats = new ArrayList<NodeReadingStat>();
        for (Object obj : results) {
            final Object[] row = (Object[]) obj;
            final Node node = (Node) row[0];
            final Double maxReading = (Double) row[1];
            final Double minReading = (Double) row[2];
            final Long totalCount = (Long) row[3];
            final LastNodeReading lnr = LastNodeReadingController.getInstance().getLatestNodeReading(node);
            final Date lastTimestamp = (lnr == null) ? null : lnr.getTimestamp();
            final Double lastReading = (lnr == null) ? null : lnr.getReading();

            NodeReadingStat nodeReadingStat = new NodeReadingStat();
            nodeReadingStat.setNode(node);
            nodeReadingStat.setLastTimestamp(lastTimestamp);
            nodeReadingStat.setLastReading(lastReading);
            nodeReadingStat.setMaxReading(maxReading);
            nodeReadingStat.setMinReading(minReading);
            nodeReadingStat.setTotalCount(totalCount);
            nodeReadingStats.add(nodeReadingStat);
        }
        return nodeReadingStats;
    }

    /**
     * Returns node reading stats of any node belonging to the given testbed.
     *
     * @param testbed , a testbed instance
     * @return a list of NodeReadingStat objects
     */
    public List<NodeReadingStat> getNodeReadingStats(final Testbed testbed) {
        final Session session = getSessionFactory().getCurrentSession();

        // retrieve testbed setup
        final Setup setup = SetupController.getInstance().getByID(testbed.getSetup().getId());

        // get max/min reading for a node
        final Criteria criteria = session.createCriteria(NodeReading.class);
        criteria.add(Restrictions.in(NODE, setup.getNodes()));
        criteria.setProjection(Projections.projectionList()
                .add(Projections.groupProperty(NODE))
                .add(Projections.max(READING))
                .add(Projections.min(READING))
                .add(Projections.rowCount())
        );
        final List<Object> results = (criteria.list() == null) ? (new ArrayList<Object>()) : criteria.list();

        // parsing the result array to a node reading stat array
        final List<NodeReadingStat> nodeReadingStats = new ArrayList<NodeReadingStat>();
        int index = 0;
        for (Object obj : results) {
            final Object[] row = (Object[]) obj;
            final Node node = (Node) row[0];
            final Double maxReading = (Double) row[1];
            final Double minReading = (Double) row[2];
            final Long totalCount = (Long) row[3];
            final LastNodeReading lnr = LastNodeReadingController.getInstance().getLatestNodeReading(node);
            final Date lastTimestamp = (lnr == null) ? null : lnr.getTimestamp();
            final Double lastReading = (lnr == null) ? null : lnr.getReading();
            NodeReadingStat nodeReadingStat = new NodeReadingStat();
            nodeReadingStat.setNode(node);
            nodeReadingStat.setLastTimestamp(lastTimestamp);
            nodeReadingStat.setLastReading(lastReading);
            nodeReadingStat.setMaxReading(maxReading);
            nodeReadingStat.setMinReading(minReading);
            nodeReadingStat.setTotalCount(totalCount);
            nodeReadingStats.add(nodeReadingStat);
        }
        return nodeReadingStats;
    }

    /**
     * Returns node reading stats for the given node.
     *
     * @param node , a node
     * @return a NodeReadingStat instance.
     */
    public NodeReadingStat getNodeReadingStats(final Node node) {
        final Session session = getSessionFactory().getCurrentSession();

        final Criteria criteria = session.createCriteria(NodeReading.class);
        criteria.add(Restrictions.eq(NODE, node));
        criteria.setProjection(Projections.projectionList()
                .add(Projections.groupProperty(NODE))
                .add(Projections.max(READING))
                .add(Projections.min(READING))
                .add(Projections.rowCount())
        );

        criteria.setMaxResults(1);
        final Object[] row = (Object[]) criteria.uniqueResult();
        final Node nodeQ = (Node) row[0];
        final Double maxReading = (Double) row[1];
        final Double minReading = (Double) row[2];
        final Long totalCount = (Long) row[3];
        final LastNodeReading lnr = LastNodeReadingController.getInstance().getLatestNodeReading(node);
        final Date lastTimestamp = (lnr == null) ? null : lnr.getTimestamp();
        final Double lastReading = (lnr == null) ? null : lnr.getReading();

        return new NodeReadingStat(nodeQ, lastTimestamp, lastReading, maxReading, minReading, totalCount);
    }
}
