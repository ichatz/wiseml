package eu.wisebed.wisedb.controller;

import eu.wisebed.wisedb.exception.UnknownCapabilityIdException;
import eu.wisebed.wisedb.exception.UnknownNodeIdException;
import eu.wisebed.wisedb.model.NodeReading;
import eu.wisebed.wiseml.model.setup.Capability;
import eu.wisebed.wiseml.model.setup.Node;
import org.hibernate.Criteria;
import org.hibernate.classic.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import java.util.Date;
import java.util.HashSet;
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
     * @param nodeId       , a node's id.
     * @param capabilityId , a capability's id.
     * @param readingValue , the readings value.
     * @param timestamp    , a timestamp for the time the reading took place
     * @throws UnknownNodeIdException       , exception when an unknown node id occurs.
     * @throws UnknownCapabilityIdException ,  exception when an unknown capability id occurs.
     */
    public void insertReading(final String nodeId, final String capabilityId, final double readingValue, final Date timestamp)
            throws UnknownNodeIdException, UnknownCapabilityIdException {

        // get node if not found throw exception
        Node node = NodeController.getInstance().getByID(nodeId);
        if (node == null) throw new UnknownNodeIdException(nodeId);

        Capability capability = CapabilityController.getInstance().getByID(capabilityId);
        if (capability == null) throw new UnknownCapabilityIdException(capabilityId);

        // make a new node reading entity
        NodeReading reading = new NodeReading();
        reading.setNode(node);
        reading.setCapability(capability);
        reading.setReading(readingValue);
        reading.setTimestamp(timestamp);

        // make association of the reading with the respected node and capability
        if (node.getReadings() == null) {
            node.setReadings(new HashSet<NodeReading>());
        }
        node.getReadings().add(reading);

        if (capability.getNodeReadings() == null) {
            capability.setNodeReadings(new HashSet<NodeReading>());
        }
        capability.getNodeReadings().add(reading);

        // persist node
        add(reading);
    }

    /**
     * Return list of readings
     * @param node
     * @param capability
     * @return
     */
    @SuppressWarnings("unchecked")
    public List<NodeReading> listReadings(final Node node, final Capability capability) {
        final Session session = getSessionFactory().getCurrentSession();
        Criteria criteria;
        criteria = session.createCriteria(NodeReading.class);
        criteria.add(Restrictions.eq("node", node));
        criteria.add(Restrictions.eq("capability", capability));
        criteria.addOrder(Order.asc("timestamp"));
        return criteria.list();
    }

}
