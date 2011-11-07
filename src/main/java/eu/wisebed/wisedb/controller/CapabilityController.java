package eu.wisebed.wisedb.controller;

import eu.wisebed.wisedb.model.Testbed;
import eu.wisebed.wiseml.model.setup.Capability;
import eu.wisebed.wiseml.model.setup.Link;
import eu.wisebed.wiseml.model.setup.Node;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;


/**
 * CRUD operations for Capability objects.
 */
public class CapabilityController extends AbstractController<Capability> {
    /**
     * static instance(ourInstance) initialized as null.
     */
    private static CapabilityController ourInstance = null;

    /**
     * Public constructor .
     */
    public CapabilityController() {
        // Does nothing
        super();
    }

    /**
     * CapabilityController is loaded on the first execution of
     * CapabilityController.getInstance() or the first access to
     * CapabilityController.ourInstance, not before.
     *
     * @return ourInstance
     */
    public static CapabilityController getInstance() {
        synchronized (CapabilityController.class) {
            if (ourInstance == null) {
                ourInstance = new CapabilityController();
            }
        }
        return ourInstance;
    }

    /**
     * Stores the capability provided in the parameters.
     *
     * @param entity, a Capability object.
     */
    public void add(final Capability entity) {
        final Session session = getSessionFactory().getCurrentSession();
        final Capability entity2 = (Capability) session.get(Capability.class, entity.getName());
        if (entity2 == null) {
            session.save(entity);
        } else {
            session.merge(entity2);
        }
    }

    /**
     * Retrieve the Capabilities from the database that corresponds to the input id.
     *
     * @param entityID the id of the Entity object.
     * @return an Entity object.
     */
    public Capability getByID(final String entityID) {
        return super.getByID(new Capability(), entityID);
    }

    /**
     * Delete the input Capability from the database.
     *
     * @param value the Capability tha we want to delete
     */
    public void delete(final Capability value) {
        super.delete(value, value.getName());
    }

    /**
     * Deleting a capability entry from the database.
     *
     * @param entityID the id of the Entity object.
     */
    public void delete(final String entityID) {
        super.delete(new Capability(), entityID);
    }

    /**
     * Listing all capabilities from the database.
     *
     * @return a list of all capabilities persisted.
     */
    public List<Capability> list() {
        return super.list(new Capability());
    }

    /**
     * Returns all nodes of a capability.
     *
     * @param capability , a capability
     * @return returns a set of nodes that have this capability
     */
    public Set<Node> listNodes(final Capability capability) {
        final Session session = getSessionFactory().getCurrentSession();
        Capability fetchedCapability = (Capability) session.get(Capability.class, capability.getName());
        return fetchedCapability.getNodes();
    }

    /**
     * Returns all nodes of a capability.
     *
     * @param capability , a capability
     * @return returns a set of links that have this capability
     */
    public Set<Link> listLinks(final Capability capability) {
        final Session session = getSessionFactory().getCurrentSession();
        Capability fetchedCapability = (Capability) session.get(Capability.class, capability.getName());
        return fetchedCapability.getLinks();
    }

    /**
     * Listing all node capabilities from the database.
     *
     * @return a list of all node related capabilities persisted.
     */
    public List<Capability> listNodeCapabilities() {
        final Session session = getSessionFactory().getCurrentSession();
        Criteria criteria = session.createCriteria(Capability.class);
        criteria.add(Restrictions.isNotNull("nodes"));
        criteria.add(Restrictions.isNotEmpty("nodes"));
        criteria.addOrder(Order.asc("name"));
        return (List<Capability>) criteria.list();
    }


    /**
     * Listing all link capabilities from the database.
     *
     * @return a list of all link related capabilities persisted.
     */
    public List<Capability> listLinkCapabilities() {
        final Session session = getSessionFactory().getCurrentSession();
        Criteria criteria = session.createCriteria(Capability.class);
        criteria.add(Restrictions.isNotNull("links"));
        criteria.add(Restrictions.isNotEmpty("links"));
        criteria.addOrder(Order.asc("name"));
        return (List<Capability>) criteria.list();
    }

    /**
     * Listing all the capabilities from the database belonging to a selected testbed.
     *
     * @param testbed , a selected testbed.
     * @return a list of testbed capabilities.
     */
    public List<Capability> list(final Testbed testbed) {
        List<Capability> capabilities = new ArrayList<Capability>();
        List<Capability> nodeCapabilities = listNodeCapabilities(testbed);
        List<Capability> linkCapabilities = listLinkCapabilities(testbed);
        capabilities.addAll(nodeCapabilities);
        capabilities.addAll(linkCapabilities);
        return capabilities;
    }

    /**
     * Listing all the capabilities associated with nodes from the database belonging to a selected testbed.
     *
     * @param testbed , a selected testbed.
     * @return a list of testbed nodes capabilities.
     */
    public List<Capability> listNodeCapabilities(final Testbed testbed) {
        final Session session = getSessionFactory().getCurrentSession();
        Criteria criteria = session.createCriteria(Capability.class);
        criteria.createAlias("nodes", "ns");
        criteria.add(Restrictions.eq("ns.setup", testbed.getSetup()));
        criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        criteria.addOrder(Order.asc("name"));
        return (List<Capability>) criteria.list();
    }

    /**
     * Listing all the link capabilities associated with nodes from the database belonging to a selected testbed.
     *
     * @param testbed , a selected testbed.
     * @return a list of testbed link capabilities.
     */
    public List<Capability> listLinkCapabilities(final Testbed testbed) {
        final Session session = getSessionFactory().getCurrentSession();
        Criteria criteria = session.createCriteria(Capability.class);
        criteria.createAlias("links", "ls");
        criteria.add(Restrictions.eq("ls.setup", testbed.getSetup()));
        criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        criteria.addOrder(Order.asc("name"));
        return (List<Capability>) criteria.list();
    }
}
