package eu.wisebed.wisedb.controller;

import eu.wisebed.wisedb.importer.SetupImporter;
import eu.wisebed.wisedb.model.Testbed;
import eu.wisebed.wiseml.model.setup.Node;
import eu.wisebed.wiseml.model.setup.Setup;
import org.hibernate.Criteria;
import org.hibernate.classic.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import java.util.List;
import java.util.Set;


/**
 * CRUD operations for Node objects.
 */

public class NodeController extends AbstractController<Node> {
    /**
     * static instance(ourInstance) initialized as null.
     */
    private static NodeController ourInstance = null;

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
        super.delete(node,node.getId());
    }

    /**
     * Delete the input Node from the database.
     *
     * @param nodeId the id of the node tha we want to delete
     */
    public void delete(final String nodeId) {
        super.delete(new Node(),nodeId);
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
     * Listing all the Nodes belonging to a testbed.
     * @param testbed
     * @return  a list of all the entries that much the criterias
     */
    public List<Node> listTestbedNodes(final Testbed testbed){
        final Session session = getSessionFactory().getCurrentSession();

        // get testbed only setup
        Set<Setup> setups = testbed.getSetups();

        Criteria criteria;
        criteria = session.createCriteria(Node.class);
        criteria.add(Restrictions.eq("setups",setups));
        criteria.addOrder(Order.asc("id"));
        return (List<Node>) criteria.list();

    }

}
