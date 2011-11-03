package eu.wisebed.wisedb.controller;

import eu.wisebed.wisedb.exception.UnknownTestbedException;
import eu.wisebed.wisedb.model.*;
import eu.wisebed.wiseml.model.setup.Capability;
import eu.wisebed.wiseml.model.setup.Link;
import eu.wisebed.wiseml.model.setup.Node;
import eu.wisebed.wiseml.model.setup.Setup;
import org.hibernate.Criteria;
import org.hibernate.classic.Session;
import org.hibernate.criterion.*;

import java.util.*;

public class NodeReadingController extends AbstractController<NodeReading> {

    /**
     * static instance(ourInstance) initialized as null.
     */
    private static NodeReadingController ourInstance = null;

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
     * Insert a node's reading from it's capabilities and make the appropriate relations
     * such as Node-Reading , Capability-reading
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
        Testbed testbed = TestbedController.getInstance().getByUrnPrefix(urnPrefix);
        if (testbed == null) {
            throw new UnknownTestbedException(urnPrefix);
        }

        // get node if not found create one
        Node node = NodeController.getInstance().getByID(nodeId);
        if (node == null) {
            // if node not found in db make it and store it
            node = new Node();
            node.setId(nodeId);
            node.setDescription("description"); // todo provide those ?
            node.setGateway("false");
            node.setProgramDetails("program details");
            node.setSetup(testbed.getSetup());
            node.setCapabilities(new ArrayList<Capability>());
            node.setReadings(new HashSet<NodeReading>());
            NodeController.getInstance().add(node);
        }

        // get capability if not found create one
        Capability capability = CapabilityController.getInstance().getByID(capabilityName);
        if (capability == null) {
            // if capability not found in db make it and store it
            capability = new Capability();
            capability.setName(capabilityName);
            capability.setDatatype("datatype"); // todo provide those ?
            capability.setDefaultvalue("default value");
            capability.setUnit("unit");
            capability.setNodes(new HashSet<Node>());
            capability.setNodeReadings(new HashSet<NodeReading>());
            capability.setLinks(new HashSet<Link>());
            capability.setLinkReadings(new HashSet<LinkReading>());
            CapabilityController.getInstance().add(capability);
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
        NodeReading reading = new NodeReading();
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
        Criteria criteria;
        criteria = session.createCriteria(NodeReading.class);
        criteria.add(Restrictions.eq("node", node));
        criteria.add(Restrictions.eq("capability", capability));
        criteria.addOrder(Order.asc("timestamp"));
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
        Criteria criteria = session.createCriteria(NodeReading.class);
        criteria.add(Restrictions.eq("node", node));
        criteria.setProjection(Projections.count("node"));
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
        Criteria criteria = session.createCriteria(NodeReading.class);
        criteria.add(Restrictions.eq("node", node));
        criteria.setProjection(Projections.projectionList()
                .add(Projections.rowCount())
                .add(Projections.property("capability"))
                .add(Projections.groupProperty("capability"))
        );
        HashMap<Capability, Long> resultMap = new HashMap<Capability, Long>();
        List<Object> results = criteria.list();
        for (Object result : results) {
            Object[] row = (Object[]) result;
            resultMap.put((Capability) row[1], (Long) row[0]);
        }
        return resultMap;
    }

    /**
     * Returns the readings count for a capability per node in a testbed.
     *
     * @param capability , a capability .
     * @param testbed    , a testbed.
     * @return a map containing readings of a capability per node.
     */
    public Map<Node, Long> getNodeCapabilityReadingsCountPerNode(final Capability capability, final Testbed testbed) {
        final Session session = getSessionFactory().getCurrentSession();
        Criteria criteria = session.createCriteria(NodeReading.class);
        criteria.createAlias("node", "no");
        criteria.add(Restrictions.eq("no.setup", testbed.getSetup()));
        criteria.add(Restrictions.eq("capability", capability));
        criteria.setProjection(Projections.projectionList()
                .add(Projections.rowCount())
                .add(Projections.property("node"))
                .add(Projections.groupProperty("node"))
        );
        HashMap<Node, Long> resultMap = new HashMap<Node, Long>();
        List results = criteria.list();
        for (Object result : results) {
            Object[] row = (Object[]) result;
            resultMap.put((Node) row[1], (Long) row[0]);
        }
        return resultMap;
    }

    /**
     * Returns the node readings count for a capability in a testbed.
     *
     * @param capability , a capability .
     * @param testbed    , a testbed .
     * @return total node readings count for a given capability.
     */
    public Long getNodeCapabilityReadingsCount(final Capability capability, final Testbed testbed) {
        final Session session = getSessionFactory().getCurrentSession();
        Criteria criteria = session.createCriteria(NodeReading.class);
        criteria.createAlias("node", "no");
        criteria.add(Restrictions.eq("no.setup", testbed.getSetup()));
        criteria.add(Restrictions.eq("capability", capability));
        criteria.setProjection(Projections.count("capability"));
        criteria.setMaxResults(1);
        return (Long) criteria.uniqueResult();
    }

    /**
     * Returns node reading stats of any node persisted.
     *
     * @return a list of NodeReadingStat objects
     */
    public List<NodeReadingStat> getNodeReadingStats() {
        final Session session = getSessionFactory().getCurrentSession();

        // get max/min reading for a node
        Criteria criteria = session.createCriteria(NodeReading.class);
        criteria.setProjection(Projections.projectionList()
                .add(Projections.groupProperty("node"))
                .add(Projections.max("reading"))
                .add(Projections.min("reading"))
                .add(Projections.rowCount())
        );

        // making the NodeReadingStat list
        List<NodeReadingStat> stats = new ArrayList<NodeReadingStat>();
        for (Object obj : criteria.list()) {
            Object[] row = (Object[]) obj;
            final Node node = (Node) row[0];
            final LastNodeReading lnr = LastNodeReadingController.getInstance().getLatestNodeReading(node);
            final Double maxReading = (Double) row[1];
            final Double minReading = (Double) row[2];
            final Long totalCount = (Long) row[3];

            stats.add(new NodeReadingStat(node, lnr.getTimestamp(), lnr.getReading(), maxReading,
                    minReading, totalCount));
        }
        return stats;
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
        Setup setup = SetupController.getInstance().getByID(testbed.getSetup().getId());

        // get max/min reading for a node
        Criteria criteria = session.createCriteria(NodeReading.class);
        criteria.add(Restrictions.in("node", setup.getNodes()));
        criteria.setProjection(Projections.projectionList()
                .add(Projections.groupProperty("node"))
                .add(Projections.max("reading"))
                .add(Projections.min("reading"))
                .add(Projections.rowCount())
        );

        // making the NodeReadingStat list
        List<NodeReadingStat> stats = new ArrayList<NodeReadingStat>();
        for (Object obj : criteria.list()) {
            Object[] row = (Object[]) obj;
            final Node node = (Node) row[0];
            final LastNodeReading lnr = LastNodeReadingController.getInstance().getLatestNodeReading(node);
            Date lastTimestamp = null;
            Double lastReading = null;
            if (lnr != null) {
                lastTimestamp = lnr.getTimestamp();
                lastReading = lnr.getReading();
            }
            final Double maxReading = (Double) row[1];
            final Double minReading = (Double) row[2];
            final Long totalCount = (Long) row[3];

            stats.add(new NodeReadingStat(node, lastTimestamp, lastReading, maxReading, minReading, totalCount));
        }
        return stats;
    }

    /**
     * Returns node reading stats for the given node.
     *
     * @param node , a node
     * @return a NodeReadingStat instance.
     */
    public NodeReadingStat getNodeReadingStats(final Node node) {
        final Session session = getSessionFactory().getCurrentSession();

        Criteria criteria = session.createCriteria(NodeReading.class);
        criteria.add(Restrictions.eq("node", node));
        criteria.setProjection(Projections.projectionList()
                .add(Projections.groupProperty("node"))
                .add(Projections.max("reading"))
                .add(Projections.min("reading"))
                .add(Projections.rowCount())
        );

        criteria.setMaxResults(1);
        Object[] row = (Object[]) criteria.uniqueResult();
        final Node nodeQ = (Node) row[0];
        final LastNodeReading lnr = LastNodeReadingController.getInstance().getLatestNodeReading(node);
        Date lastTimestamp = null;
        Double lastReading = null;
        if (lnr != null) {
            lastTimestamp = lnr.getTimestamp();
            lastReading = lnr.getReading();
        }
        final Double maxReading = (Double) row[3];
        final Double minReading = (Double) row[4];
        final Long totalCount = (Long) row[5];

        return new NodeReadingStat(nodeQ, lastTimestamp, lastReading, maxReading,minReading, totalCount);
    }
}
