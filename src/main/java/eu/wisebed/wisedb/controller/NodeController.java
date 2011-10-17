package eu.wisebed.wisedb.controller;

import com.hp.hpl.jena.ontology.Restriction;
import eu.wisebed.wisedb.importer.SetupImporter;
import eu.wisebed.wisedb.model.NodeReading;
import eu.wisebed.wisedb.model.Testbed;
import eu.wisebed.wiseml.model.setup.Capability;
import eu.wisebed.wiseml.model.setup.Link;
import eu.wisebed.wiseml.model.setup.Node;
import eu.wisebed.wiseml.model.setup.Setup;
import org.hibernate.Criteria;
import org.hibernate.classic.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projection;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.dialect.Oracle10gDialect;

import java.util.Date;
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
     * Listing all the nodes from the database belonging to a selected testbed.
     *
     * @param testbed , a selected testbed.
     * @return a list of testbed links.
     */
    public List<Node> list(final Testbed testbed) {
        final Session session = getSessionFactory().getCurrentSession();
        Criteria criteria = session.createCriteria(Node.class);
        criteria.add(Restrictions.eq("setup", testbed.getSetup()));
        return (List<Node>) criteria.list();
    }

    /**
     * Returns the readings count for a node.
     *
     * @param node , a node .
     * @return the count of this node.
     */
    public Long getReadingsCount(final Node node){
        final Session session = getSessionFactory().getCurrentSession();
        Criteria criteria = session.createCriteria(NodeReading.class);
        criteria.add(Restrictions.eq("node",node));
        criteria.setProjection(Projections.count("node"));
        criteria.setMaxResults(1);
        return (Long) criteria.uniqueResult();
    }

    /**
     * Returns the readings count for a node and a capability.
     *
     * @param node  , a node.
     * @param capability , a capability
     * @return the count of readings for this node and capability.
     */
    public Long getReadingsCount(final Node node,final Capability capability){
        final Session session = getSessionFactory().getCurrentSession();
        Criteria criteria = session.createCriteria(NodeReading.class);
        criteria.add(Restrictions.eq("node",node));
        criteria.add(Restrictions.eq("capability",capability));
        criteria.setProjection(Projections.count("node"));
        criteria.setMaxResults(1);
        return (Long) criteria.uniqueResult();
    }
}
