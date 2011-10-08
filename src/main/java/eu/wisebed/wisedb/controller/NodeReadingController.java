package eu.wisebed.wisedb.controller;

import eu.wisebed.wisedb.model.NodeReading;
import eu.wisebed.wisedb.model.Testbed;
import eu.wisebed.wiseml.model.setup.Capability;
import eu.wisebed.wiseml.model.setup.Node;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.classic.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import java.util.Date;
import java.util.List;

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
     * @param nodeId , a node id.
     * @param capabilityName , a capability name.
     * @param urnPrefix , a testbed urn prefix.
     * @param readingValue , a reading value.
     * @param timestamp , a timestamp.
     */
    public void insertReading(final String nodeId, final String capabilityName, final String urnPrefix,
                              final double readingValue, final Date timestamp) {

        // Retrieve testbed by urn
        Testbed testbed = null;
        if(urnPrefix != null)
        {
             testbed = TestbedController.getInstance().getByUrnPrefix(urnPrefix);
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
            if(testbed != null){
                node.setSetup(testbed.getSetup());
            }
        }

        Capability capability = CapabilityController.getInstance().getByID(capabilityName);
        if (capability == null) {
            // if capability not found in db make it and store it
            capability = new Capability();
            capability.setName(capabilityName);
            capability.setDatatype("datatype"); // todo provide those ?
            capability.setDefaultvalue("default value");
            capability.setUnit("unit");
        }

        // associate capability node
        if(!node.getCapabilities().contains(capability)) {
            node.getCapabilities().add(capability);
        }
        if(!capability.getNodes().contains(node)) {
            capability.getNodes().add(node);
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
     * Returns the latest node reading date for a given node.
     *
     * @param node, a testbed node.
     * @return the latest node reading date
     */
    public Date getLatestNodeReadingDateAKRIBOPO(final Node node) {
        final Session session = getSessionFactory().getCurrentSession();
        String HQL_QUERY = "select max(timestamp) from NodeReading where node=:node";
        Query query = session.createQuery(HQL_QUERY);
        query.setParameter("node", node);
        query.setMaxResults(1);
        return (Date) query.uniqueResult();
    }

    /**
     * Returns the latest node reading date for a given node.
     *
     * @return the latest node reading date
     */
    public List getLatestNodeReadingDateBOUSIS() {
        final Session session = getSessionFactory().getCurrentSession();
        Criteria criteria = session.createCriteria(NodeReading.class);
        criteria.setProjection(Projections.projectionList()
                .add(Projections.max("timestamp"))
                .add(Projections.rowCount())
                .add(Projections.groupProperty("node")));
        return criteria.list();
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
}
