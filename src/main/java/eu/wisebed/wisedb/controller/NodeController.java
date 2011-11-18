package eu.wisebed.wisedb.controller;

import eu.wisebed.wisedb.model.Testbed;
import eu.wisebed.wiseml.model.setup.Capability;
import eu.wisebed.wiseml.model.setup.Node;
import org.hibernate.Criteria;
import org.hibernate.classic.Session;
import org.hibernate.criterion.Order;
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
        return super.getByID(new Node(), entityID);
    }

    /**
     * Delete the input Node from the database.
     *
     * @param node the Node tha we want to delete
     */
    public void delete(final Node node) {
        super.delete(node, node.getId());
    }

    /**
     * Delete the input Node from the database.
     *
     * @param nodeId the id of the node tha we want to delete
     */
    public void delete(final String nodeId) {
        super.delete(new Node(), nodeId);
    }

    /**
     * Listing all the Nodes from the database.
     *
     * @return a list of all the entries that exist inside the table Node.
     */
    public List<Node> list() {
        return super.list(new Node());
    }

    /**
     * Listing all the nodes from the database belonging to a selected testbed.
     *
     * @param testbed , a selected testbed.
     * @return a list of testbed links.
     */
    public List<Node> list(final Testbed testbed) {
        final Session session = getSessionFactory().getCurrentSession();
        final Criteria criteria = session.createCriteria(Node.class);
        criteria.add(Restrictions.eq(SETUP, testbed.getSetup()));
        criteria.addOrder(Order.asc(NODE_ID));
        return (List<Node>) criteria.list();
    }

    /**
     * Listing all nodes that have the given capability.
     *
     * @param capability , a capability.
     * @return a list of nodes that share the given capability.
     */
    public List<Node> listCapabilityNodes(final Capability capability) {
        final org.hibernate.Session session = getSessionFactory().getCurrentSession();
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
     * @param testbed a testbed.
     * @return a list of nodes that share the given capability belonging to the same testbed.
     */
    public List<Node> listCapabilityNodes(final Capability capability, final Testbed testbed) {
        final org.hibernate.Session session = getSessionFactory().getCurrentSession();
        final Criteria criteria = session.createCriteria(Node.class);
        criteria.add(Restrictions.eq(SETUP, testbed.getSetup()));
        criteria.createAlias(CAPABILITIES, "caps")
                .add(Restrictions.eq("caps.name", capability.getName()));
        criteria.addOrder(Order.asc(NODE_ID));
        return (List<Node>) criteria.list();
    }
}
