package eu.wisebed.wisedb.controller;

import eu.wisebed.wisedb.exception.UnknownTestbedException;
import eu.wisebed.wisedb.model.Capability;
import eu.wisebed.wisedb.model.LastNodeReading;
import eu.wisebed.wisedb.model.Node;
import eu.wisebed.wisedb.model.NodeCapability;
import eu.wisebed.wisedb.model.NodeReading;
import eu.wisebed.wisedb.model.Testbed;
import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

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
     * Capability literal.
     */
    private static final String CAPABILITY = "capability";

    /**
     * Timestamp literal.
     */
    private static final String TIMESTAMP = "timestamp";

    private static final String ID = "id";

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

    public Long count() {
        LOGGER.info("count()");
        Criteria criteria = null;
        final Session session = getSessionFactory().getCurrentSession();
        criteria = session.createCriteria(NodeReading.class);
        criteria.setProjection(Projections.rowCount());
        return (Long) criteria.uniqueResult();
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

        Node node = NodeController.getInstance().getByID(nodeId);
        NodeCapability nodeCapability;
        if (node == null) {
            LOGGER.debug("node==null");
            node = NodeController.getInstance().prepareInsertNode(testbed, nodeId);
            nodeCapability = NodeCapabilityController.getInstance().prepareInsertNodeCapability(capabilityName, node.getId());
        } else {
            nodeCapability = NodeCapabilityController.getInstance().getByID(node, capabilityName);
            if (nodeCapability == null) {
                nodeCapability = NodeCapabilityController.getInstance().prepareInsertNodeCapability(capabilityName, node.getId());
                NodeController.getInstance().update(node);
            }
        }

        // make a new node reading entity
        final NodeReading reading = new NodeReading();
        reading.setReading(doubleReading);
        reading.setStringReading(stringReading);
        reading.setTimestamp(timestamp);
        reading.setCapability(nodeCapability);

        // add reading
        add(reading);

        // get lastNodeReading if not found create one
        LastNodeReading lastNodeReading = nodeCapability.getLastNodeReading();
        if (lastNodeReading == null) {
            LOGGER.info("Last node reading for NodeCapability [" + nodeCapability.toString() + "] created");
            lastNodeReading = new LastNodeReading();
        }
        lastNodeReading.setReading(doubleReading);
        lastNodeReading.setStringReading(stringReading);
        lastNodeReading.setTimestamp(timestamp);
        lastNodeReading.setId(nodeCapability.getId());

        nodeCapability.setLastNodeReading(lastNodeReading);

        NodeCapabilityController.getInstance().update(nodeCapability);

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
        final NodeCapability nodeCapability = NodeCapabilityController.getInstance().getByID(node, capability);

        final Session session = getSessionFactory().getCurrentSession();
        final Criteria criteria = session.createCriteria(NodeReading.class);
        criteria.add(Restrictions.eq(CAPABILITY, nodeCapability));
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
        final NodeCapability nodeCapability = NodeCapabilityController.getInstance().getByID(node, capability);

        final Session session = getSessionFactory().getCurrentSession();
        final Criteria criteria = session.createCriteria(NodeReading.class);
        criteria.add(Restrictions.eq(CAPABILITY, nodeCapability));
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
        final List<NodeCapability> nodeCapabilities = NodeCapabilityController.getInstance().list(node);

        Integer result = 0;
        if (nodeCapabilities.size()>0) {
            final Session session = getSessionFactory().getCurrentSession();
            final Criteria criteria = session.createCriteria(NodeReading.class);
            criteria.add(Restrictions.in(CAPABILITY, nodeCapabilities));
            criteria.setProjection(Projections.rowCount());
            final Long count = (Long) criteria.uniqueResult();
            result += count.intValue();
        }
        return result.longValue();
    }

    /**
     * Returns the readings count for a node per capability.
     *
     * @param node , a node .
     * @return a map containing readings of a node per capability
     */
    @SuppressWarnings("unchecked")
    public HashMap<Capability, Integer> getNodeReadingsCountMap(final Node node) {
        LOGGER.info("getNodeReadingsCountMap(" + node + ")");
        final HashMap<Capability, Integer> resultMap = new HashMap<Capability, Integer>();

        final List<NodeCapability> nodeCapabilities = NodeCapabilityController.getInstance().list(node);
        for (NodeCapability nodeCapability : nodeCapabilities) {
            final Session session = getSessionFactory().getCurrentSession();
            final Criteria criteria = session.createCriteria(NodeReading.class);
            criteria.add(Restrictions.eq(CAPABILITY, nodeCapability));
            criteria.setProjection(Projections.projectionList()
                    .add(Projections.rowCount())
            );
            final Long count = (Long) criteria.uniqueResult();
            resultMap.put(nodeCapability.getCapability(), count.intValue());
        }
        return resultMap;
    }

    public NodeReading getByID(final int id) {

        LOGGER.info("getByID(" + id + ")");
        final Session session = getSessionFactory().getCurrentSession();
        final Criteria criteria = session.createCriteria(NodeReading.class);
        criteria.add(Restrictions.eq(ID, id));
        criteria.setMaxResults(1);
        return (NodeReading) criteria.uniqueResult();
    }
}
