package eu.wisebed.wisedb.controller;

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
     */
    public void insertReading(final String nodeId, final String capabilityName, final String urnPrefix,
                              final double readingValue, final Date timestamp) {

        // Retrieve testbed by urn
        Testbed testbed = TestbedController.getInstance().getByUrnPrefix(urnPrefix);
        if (testbed == null) {
            return; // TODO throw an exception
        }

        // get node if not found throw exception
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
    }

    /**
     * Return list of readings for a selected node and capability.
     *
     * @param node       , node of a testbed.
     * @param capability , capability of a node
     * @return a list with nodereadings for a node/capability combination
     */
    @SuppressWarnings("unchecked")
    public List<NodeReading> listReadings(final Node node, final Capability capability) {
        final Session session = getSessionFactory().getCurrentSession();
        Criteria criteria;
        criteria = session.createCriteria(NodeReading.class);
        criteria.add(Restrictions.eq("node", node));
        criteria.add(Restrictions.eq("capability", capability));
        criteria.addOrder(Order.asc("timestamp"));
        return (List<NodeReading>) criteria.list();
    }

    /**
     * Returns the latest reading update (node,timestamp,row) of any node persisted.
     *
     * @return an object instance list with the rows of the query
     */
    public List<NodeReadingStat> getLatestNodeReadingUpdates() {
        final Session session = getSessionFactory().getCurrentSession();
        Criteria criteria = session.createCriteria(NodeReading.class);
        criteria.setProjection(Projections.projectionList()
                .add(Projections.groupProperty("node"))
                .add(Projections.max("timestamp"))
                .add(Projections.min("timestamp"))
                .add(Projections.max("reading"))
                .add(Projections.min("reading"))
                .add(Projections.rowCount())
        );
        List<NodeReadingStat> updates = new ArrayList<NodeReadingStat>();
        for (Object obj : criteria.list()) {
            Object[] row = (Object[]) obj;
            final Node node = (Node) row[0];
            final Date latestDate = (Date) row[1];
            final Date earliestDate = (Date) row[2];
            final Double maxReading = (Double) row[3];
            final Double minReading = (Double) row[4];
            final Long totalCount = (Long) row[5];

            // reading of latest recording
            Criteria criteria1 = session.createCriteria(NodeReading.class);
            criteria1.setProjection(Projections.projectionList()
                    .add(Projections.property("timestamp"))
                    .add(Projections.property("reading"))
            );
            criteria1.add(Restrictions.eq("node", node));
            criteria1.add(Restrictions.eq("timestamp", latestDate));
            criteria1.setMaxResults(1);
            Object[] row1 = (Object[]) criteria1.uniqueResult();
            final Double latestDateReading = (Double) row1[1];

            // reading of earliest recording
            Criteria criteria2 = session.createCriteria(NodeReading.class);
            criteria2.setProjection(Projections.projectionList()
                    .add(Projections.property("timestamp"))
                    .add(Projections.property("reading"))
            );
            criteria2.add(Restrictions.eq("node", node));
            criteria2.add(Restrictions.eq("timestamp", earliestDate));
            criteria2.setMaxResults(1);
            Object[] row2 = (Object[]) criteria2.uniqueResult();
            final Double earliestDateReading = (Double) row2[1];

            updates.add(new NodeReadingStat(node, latestDate, latestDateReading, earliestDate, earliestDateReading,
                    maxReading, minReading, totalCount));
        }
        return updates;
    }

    /**
     * Returns the latest reading of all nodes of the provided testbed.
     *
     * @param testbed , a testbed instance
     * @return an object instance list with the rows of the query
     */
    public List<NodeReadingStat> getLatestNodeReadingUpdates(final Testbed testbed) {
        final Session session = getSessionFactory().getCurrentSession();

        Criteria criteria = session.createCriteria(NodeReading.class);
        criteria.setProjection(Projections.projectionList()
                .add(Projections.groupProperty("node"))
                .add(Projections.max("timestamp"))
                .add(Projections.min("timestamp"))
                .add(Projections.max("reading"))
                .add(Projections.min("reading"))
                .add(Projections.rowCount())
        );
        Setup setup = SetupController.getInstance().getByID(testbed.getSetup().getId());

        criteria.add(Restrictions.in("node", setup.getNodes()));

        List<NodeReadingStat> updates = new ArrayList<NodeReadingStat>();
        for (Object obj : criteria.list()) {
            Object[] row = (Object[]) obj;

            final Node node = (Node) row[0];
            final Date latestDate = (Date) row[1];
            final Date earliestDate = (Date) row[2];
            final Double maxReading = (Double) row[3];
            final Double minReading = (Double) row[4];
            final Long totalCount = (Long) row[5];

            // reading of latest recording
            Criteria criteria1 = session.createCriteria(NodeReading.class);
            criteria1.setProjection(Projections.projectionList()
                    .add(Projections.property("timestamp"))
                    .add(Projections.property("reading"))
            );
            criteria1.add(Restrictions.eq("node", node));
            criteria1.add(Restrictions.eq("timestamp", latestDate));
            criteria1.setMaxResults(1);
            Object[] row1 = (Object[]) criteria1.uniqueResult();
            final Double latestDateReading = (Double) row1[1];

            // reading of earliest recording
            Criteria criteria2 = session.createCriteria(NodeReading.class);
            criteria2.setProjection(Projections.projectionList()
                    .add(Projections.property("timestamp"))
                    .add(Projections.property("reading"))
            );
            criteria2.add(Restrictions.eq("node", node));
            criteria2.add(Restrictions.eq("timestamp", earliestDate));
            criteria2.setMaxResults(1);
            Object[] row2 = (Object[]) criteria2.uniqueResult();
            final Double earliestDateReading = (Double) row2[1];

            updates.add(new NodeReadingStat(node, latestDate, latestDateReading, earliestDate, earliestDateReading,
                    maxReading, minReading, totalCount));
        }
        return updates;
    }

    /**
     * Returns the latest reading of a specific node
     *
     * @param node , a node
     * @return the latest node reading
     */
    public NodeReadingStat getLatestNodeReadingUpdate(final Node node) {
        final Session session = getSessionFactory().getCurrentSession();
        Criteria criteria = session.createCriteria(NodeReading.class);
        criteria.add(Restrictions.eq("node", node));
        criteria.setProjection(Projections.projectionList()
                .add(Projections.groupProperty("node"))
                .add(Projections.max("timestamp"))
                .add(Projections.min("timestamp"))
                .add(Projections.max("reading"))
                .add(Projections.min("reading"))
                .add(Projections.rowCount())
        );
        criteria.setMaxResults(1);
        Object[] row = (Object[]) criteria.uniqueResult();
        final Node nodeQ = (Node) row[0];
        final Date latestDate = (Date) row[1];
        final Date earliestDate = (Date) row[2];
        final Double maxReading = (Double) row[3];
        final Double minReading = (Double) row[4];
        final Long totalCount = (Long) row[5];

        // reading of latest recording
        Criteria criteria1 = session.createCriteria(NodeReading.class);
        criteria1.setProjection(Projections.projectionList()
                .add(Projections.property("timestamp"))
                .add(Projections.property("reading"))
        );
        criteria1.add(Restrictions.eq("node", nodeQ));
        criteria1.add(Restrictions.eq("timestamp", latestDate));
        criteria1.setMaxResults(1);
        Object[] row1 = (Object[]) criteria1.uniqueResult();
        final Double latestDateReading = (Double) row1[1];

        // reading of earliest recording
        Criteria criteria2 = session.createCriteria(NodeReading.class);
        criteria2.setProjection(Projections.projectionList()
                .add(Projections.property("timestamp"))
                .add(Projections.property("reading"))
        );
        criteria2.add(Restrictions.eq("node", nodeQ));
        criteria2.add(Restrictions.eq("timestamp", earliestDate));
        criteria2.setMaxResults(1);
        Object[] row2 = (Object[]) criteria2.uniqueResult();
        final Double earliestDateReading = (Double) row2[1];

        return new NodeReadingStat(nodeQ, latestDate, latestDateReading, earliestDate, earliestDateReading,
                maxReading, minReading, totalCount);
    }

    /**
     * Returns the latest reading of a specific node
     *
     * @param capability
     * @return
     */
    public NodeReadingStat getLatestNodeReadingUpdate(final Capability capability) {
        final Session session = getSessionFactory().getCurrentSession();
        Criteria criteria = session.createCriteria(NodeReading.class);
        criteria.add(Restrictions.eq("capability", capability));
        criteria.setProjection(Projections.projectionList()
                .add(Projections.groupProperty("node"))
                .add(Projections.max("timestamp"))
                .add(Projections.min("timestamp"))
                .add(Projections.max("reading"))
                .add(Projections.min("reading"))
                .add(Projections.rowCount())
        );
        criteria.setMaxResults(1);
        Object[] row = (Object[]) criteria.uniqueResult();

        final Node node = (Node) row[0];
        final Date latestDate = (Date) row[1];
        final Date earliestDate = (Date) row[2];
        final Double maxReading = (Double) row[3];
        final Double minReading = (Double) row[4];
        final Long totalCount = (Long) row[5];

        // reading of latest recording
        Criteria criteria1 = session.createCriteria(NodeReading.class);
        criteria1.setProjection(Projections.projectionList()
                .add(Projections.property("timestamp"))
                .add(Projections.property("reading"))
        );
        criteria1.add(Restrictions.eq("node", node));
        criteria1.add(Restrictions.eq("timestamp", latestDate));
        criteria1.setMaxResults(1);
        Object[] row1 = (Object[]) criteria1.uniqueResult();
        final Double latestDateReading = (Double) row1[1];

        // reading of earliest recording
        Criteria criteria2 = session.createCriteria(NodeReading.class);
        criteria2.setProjection(Projections.projectionList()
                .add(Projections.property("timestamp"))
                .add(Projections.property("reading"))
        );
        criteria2.add(Restrictions.eq("node", node));
        criteria2.add(Restrictions.eq("timestamp", earliestDate));
        criteria2.setMaxResults(1);
        Object[] row2 = (Object[]) criteria2.uniqueResult();
        final Double earliestDateReading = (Double) row2[1];

        return new NodeReadingStat(node, latestDate, latestDateReading, earliestDate, earliestDateReading,
                maxReading, minReading, totalCount);
    }

    /**
     * Returns the latest reading of a specific node & capability.
     *
     * @param node
     * @param capability
     * @return
     */
    public NodeReadingStat getLatestNodeReadingUpdate(final Node node, final Capability capability) {
        final Session session = getSessionFactory().getCurrentSession();
        Criteria criteria = session.createCriteria(NodeReading.class);
        criteria.add(Restrictions.eq("node", node));
        criteria.add(Restrictions.eq("capability", capability));
        criteria.setProjection(Projections.projectionList()
                .add(Projections.groupProperty("node"))
                .add(Projections.max("timestamp"))
                .add(Projections.min("timestamp"))
                .add(Projections.max("reading"))
                .add(Projections.min("reading"))
                .add(Projections.rowCount())
        );
        criteria.setMaxResults(1);
        Object[] row = (Object[]) criteria.uniqueResult();
        final Node nodeQ = (Node) row[0];
        final Date latestDate = (Date) row[1];
        final Date earliestDate = (Date) row[2];
        final Double maxReading = (Double) row[3];
        final Double minReading = (Double) row[4];
        final Long totalCount = (Long) row[5];

        // reading of latest recording
        Criteria criteria1 = session.createCriteria(NodeReading.class);
        criteria1.setProjection(Projections.projectionList()
                .add(Projections.property("timestamp"))
                .add(Projections.property("reading"))
        );
        criteria1.add(Restrictions.eq("node", nodeQ));
        criteria1.add(Restrictions.eq("timestamp", latestDate));
        criteria1.setMaxResults(1);
        Object[] row1 = (Object[]) criteria1.uniqueResult();
        final Double latestDateReading = (Double) row1[1];

        // reading of earliest recording
        Criteria criteria2 = session.createCriteria(NodeReading.class);
        criteria2.setProjection(Projections.projectionList()
                .add(Projections.property("timestamp"))
                .add(Projections.property("reading"))
        );
        criteria2.add(Restrictions.eq("node", nodeQ));
        criteria2.add(Restrictions.eq("timestamp", earliestDate));
        criteria2.setMaxResults(1);
        Object[] row2 = (Object[]) criteria2.uniqueResult();
        final Double earliestDateReading = (Double) row2[1];

        return new NodeReadingStat(nodeQ, latestDate, latestDateReading, earliestDate, earliestDateReading,
                maxReading, minReading, totalCount);
    }
}
