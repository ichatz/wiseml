package eu.wisebed.wisedb.controller;

import com.googlecode.ehcache.annotations.Cacheable;
import eu.wisebed.wisedb.model.Testbed;
import eu.wisebed.wiseml.model.setup.Capability;
import eu.wisebed.wiseml.model.setup.Node;
import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.classic.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import java.util.List;


/**
 * CRUD operations for Node entities.
 */
@SuppressWarnings("unchecked")
public class NodeController extends AbstractController<Node> {
    /**
     * static instance(ourInstance) initialized as null.
     */
    private static NodeController ourInstance = null;

    /**
     * Setup literal.
     */
    private static final String SETUP = "setup";

    /**
     * Capabilities literal.
     */
    private static final String CAPABILITIES = "capabilities";

    /**
     * Node ID literal.
     */
    private static final String NODE_ID = "id";

    /**
     * Logger.
     */
    private static final Logger LOGGER = Logger.getLogger(NodeController.class);


    /**
     * Public constructor .
     */
    public NodeController() {
        // Does nothing
        super();
    }

    /**
     * NodeController is loaded on the first execution of
     * NodeController.getInstance() or the first access to
     * NodeController.ourInstance, not before.
     *
     * @return ourInstance
     */
    public static NodeController getInstance() {
        synchronized (NodeController.class) {
            if (ourInstance == null) {
                ourInstance = new NodeController();
            }
        }

        return ourInstance;
    }

    /**
     * get the Nodes from the database that corresponds to the input id.
     *
     * @param entityID the id of the Entity object.
     * @return an Entity object.
     */
    public Node getByID(final String entityID) {
        LOGGER.info("getByID(" + entityID + ")");
        return super.getByID(new Node(), entityID);
    }

    /**
     * Delete the input Node from the database.
     *
     * @param node the Node tha we want to delete
     */
    public void delete(final Node node) {
        LOGGER.info("delete(" + node + ")");
        super.delete(node, node.getId());
    }

    /**
     * Delete the input Node from the database.
     *
     * @param nodeId the id of the node tha we want to delete
     */
    public void delete(final String nodeId) {
        LOGGER.info("delete(" + nodeId + ")");
        super.delete(new Node(), nodeId);
    }

    /**
     * Listing all the Nodes from the database.
     *
     * @return a list of all the entries that exist inside the table Node.
     */
    public List<Node> list() {
        LOGGER.info("list()");
        return super.list(new Node());
    }

    /**
     * Listing all the nodes from the database belonging to a selected testbed.
     *
     * @param testbed , a selected testbed.
     * @return a list of testbed links.
     */
    @Cacheable(cacheName = "nodeListsCache")
    public List<Node> list(final Testbed testbed) {
        LOGGER.info("list(" + testbed + ")");
        final Session session = getSessionFactory().getCurrentSession();
        final Criteria criteria = session.createCriteria(Node.class);
        criteria.add(Restrictions.eq(SETUP, testbed.getSetup()));
        criteria.addOrder(Order.asc(NODE_ID));
        return (List<Node>) criteria.list();
    }

    /**
     * Listing all the nodes from the database belonging to a selected testbed.
     *
     * @param testbed , a selected testbed.
     * @return a list of testbed links.
     */
    @Cacheable(cacheName = "nodeListsCache")
    public List<String> listNames(final Testbed testbed) {
        LOGGER.info("list(" + testbed + ")");
        long millis = System.currentTimeMillis();
        final Session session = getSessionFactory().getCurrentSession();
        final Criteria criteria = session.createCriteria(Node.class);
        criteria.add(Restrictions.eq(SETUP, testbed.getSetup()));
        criteria.setProjection(Projections.property("id"));
        final List res = criteria.list();
        LOGGER.info("return @ " + (System.currentTimeMillis() - millis));
        return (List<String>) res;
    }

    /**
     * Listing all the nodes from the database belonging to a selected testbed.
     *
     * @param testbed , a selected testbed.
     * @return a list of testbed links.
     */
    public int count(final Testbed testbed) {
        LOGGER.info("count(" + testbed + ")");
        final Session session = getSessionFactory().getCurrentSession();
        final Criteria criteria = session.createCriteria(Node.class);
        criteria.add(Restrictions.eq(SETUP, testbed.getSetup()));
        criteria.setProjection(Projections.rowCount());
        return (Integer) criteria.list().get(0);
    }

    /**
     * Listing all nodes that have the given capability.
     *
     * @param capability , a capability.
     * @return a list of nodes that share the given capability.
     */
    public List<Node> listCapabilityNodes(final Capability capability) {
        LOGGER.info("listCapabilityNodes(" + capability + ")");
        final Session session = getSessionFactory().getCurrentSession();
        final Criteria criteria = session.createCriteria(Node.class);
        criteria.createAlias(CAPABILITIES, "caps")
                .add(Restrictions.eq("caps.name", capability.getName()));
        criteria.addOrder(Order.asc(NODE_ID));
        return (List<Node>) criteria.list();
    }

    /**
     * Listing all nodes that have the given capability.
     *
     * @param capability a capability.
     * @param testbed    a testbed.
     * @return a list of nodes that share the given capability belonging to the same testbed.
     */
    public List<Node> listCapabilityNodes(final Capability capability, final Testbed testbed) {
        LOGGER.info("listCapabilityNodes(" + capability + "," + testbed + ")");
        final Session session = getSessionFactory().getCurrentSession();
        final Criteria criteria = session.createCriteria(Node.class);
        criteria.add(Restrictions.eq(SETUP, testbed.getSetup()));
        criteria.createAlias(CAPABILITIES, "caps")
                .add(Restrictions.eq("caps.name", capability.getName()));
        criteria.addOrder(Order.asc(NODE_ID));
        return (List<Node>) criteria.list();
    }

    /**
     * Checks if a capability and a node are associated.
     *
     * @param capability , capability.
     * @param testbed    , testbed.
     * @param node       , node .
     * @return
     */
    public boolean isAssociated(final Capability capability, final Testbed testbed, final Node node) {
        final Session session = getSessionFactory().getCurrentSession();
        final Criteria criteria = session.createCriteria(Node.class);
        criteria.add(Restrictions.eq(SETUP, testbed.getSetup()));
        criteria.createAlias(CAPABILITIES, "caps").add(Restrictions.eq("caps.name", capability.getName()));
        criteria.add(Restrictions.eq(NODE_ID, node.getId()));
        return criteria.list().size() > 0;
    }
}
