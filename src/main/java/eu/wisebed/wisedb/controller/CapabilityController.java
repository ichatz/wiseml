package eu.wisebed.wisedb.controller;

import com.hp.hpl.jena.ontology.Restriction;
import eu.wisebed.wisedb.model.LinkReading;
import eu.wisebed.wisedb.model.NodeReading;
import eu.wisebed.wisedb.model.Testbed;
import eu.wisebed.wiseml.model.setup.Capability;
import eu.wisebed.wiseml.model.setup.Link;
import eu.wisebed.wiseml.model.setup.Node;
import org.apache.commons.collections.map.LinkedMap;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.*;

import java.util.*;


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

    public void add(final Capability entity) {
        final Session session = getSessionFactory().getCurrentSession();
        final Capability entity2 = (Capability) session.get(Capability.class, entity.getName());
        if (entity2 == null)
            session.save(entity);
        else
            session.merge(entity2);
    }

    /**
     * get the Capabilities from the database that corresponds to the input id.
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

    /**
     * Listing all nodes that have the given capability.
     *
     * @param capability , a capability.
     * @return a list of nodes that share the given capability.
     */
    public List<Node> listCapabilityNodes(final Capability capability) {
        final Session session = getSessionFactory().getCurrentSession();
        Criteria criteria = session.createCriteria(Node.class);
        criteria.createAlias("capabilities", "caps")
                .add(Restrictions.eq("caps.name", capability.getName()));
        criteria.addOrder(Order.asc("id"));
        return (List<Node>) criteria.list();
    }

    /**
     * Listing all nodes that have the given capability.
     *
     * @param capability, a capability.
     * @param testbed     , a testbed.
     * @return a list of nodes that share the given capability belonging to the same testbed.
     */
    public List<Node> listCapabilityNodes(final Capability capability, final Testbed testbed) {
        final Session session = getSessionFactory().getCurrentSession();
        Criteria criteria = session.createCriteria(Node.class);
        criteria.add(Restrictions.eq("setup", testbed.getSetup()));
        criteria.createAlias("capabilities", "caps")
                .add(Restrictions.eq("caps.name", capability.getName()));
        criteria.addOrder(Order.asc("id"));
        return (List<Node>) criteria.list();
    }

    /**
     * Listing all links that have the given capability.
     *
     * @param capability , a capability.
     * @return a list of links that share the given capability.
     */
    public List<Link> listCapabilityLinks(final Capability capability) {
        final Session session = getSessionFactory().getCurrentSession();
        Criteria criteria = session.createCriteria(Link.class);
        criteria.createAlias("capabilities", "caps")
                .add(Restrictions.eq("caps.name", capability.getName()));
        criteria.addOrder(Order.asc("source"));
        return (List<Link>) criteria.list();
    }

    /**
     * Listing all links that have the given capability.
     *
     * @param capability, a capability.
     * @param testbed     , a testbed.
     * @return a list of links that share the given capability belonging to the same testbed.
     */
    public List<Link> listCapabilityLinks(final Capability capability, final Testbed testbed) {
        final Session session = getSessionFactory().getCurrentSession();
        Criteria criteria = session.createCriteria(Link.class);
        criteria.add(Restrictions.eq("setup", testbed.getSetup()));
        criteria.createAlias("capabilities", "caps")
                .add(Restrictions.eq("caps.name", capability.getName()));
        criteria.addOrder(Order.asc("source"));
        return (List<Link>) criteria.list();
    }

    /**
     * Returns the node readings count for a capability.
     *
     * @param capability , a capability .
     * @return total node readings count for a given capability.
     */
    public Long getNodeReadingsCount(final Capability capability) {
        final Session session = getSessionFactory().getCurrentSession();
        Criteria criteria = session.createCriteria(NodeReading.class);
        criteria.add(Restrictions.eq("capability", capability));
        criteria.setProjection(Projections.count("capability"));
        criteria.setMaxResults(1);
        return (Long) criteria.uniqueResult();
    }

    /**
     * Returns the node readings count for a capability in a testbed.
     *
     * @param capability , a capability .
     * @param testbed    , a testbed .
     * @return total node readings count for a given capability.
     */
    public Long getNodeReadingsCount(final Capability capability, final Testbed testbed) {
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
     * Returns the node readings count for a capability.
     *
     * @param capability , a capability .
     * @return total node readings count for a given capability.
     */
    public Long getLinkReadingsCount(final Capability capability) {
        final Session session = getSessionFactory().getCurrentSession();
        Criteria criteria = session.createCriteria(LinkReading.class);
        criteria.add(Restrictions.eq("capability", capability));
        criteria.setProjection(Projections.count("capability"));
        criteria.setMaxResults(1);
        return (Long) criteria.uniqueResult();
    }

    /**
     * Returns the node readings count for a capability in a testbed.
     *
     * @param capability , a capability.
     * @param testbed    , a testbed .
     * @return total node readings count for a given capability.
     */
    public Long getLinkReadingsCount(final Capability capability, final Testbed testbed) {
        final Session session = getSessionFactory().getCurrentSession();
        Criteria criteria = session.createCriteria(LinkReading.class);
        criteria.createAlias("link", "li");
        criteria.add(Restrictions.eq("li.setup", testbed.getSetup()));
        criteria.add(Restrictions.eq("capability", capability));
        criteria.setProjection(Projections.count("capability"));
        criteria.setMaxResults(1);
        return (Long) criteria.uniqueResult();
    }

    /**
     * Returns the readings count for a capability per node.
     *
     * @param capability , a capability .
     * @return a map containing readings of a capability per node.
     */
    public Map<Node, Long> getReadingsCountPerNode(final Capability capability) {
        final Session session = getSessionFactory().getCurrentSession();
        Criteria criteria = session.createCriteria(NodeReading.class);
        criteria.add(Restrictions.eq("capability", capability));
        criteria.setProjection(Projections.projectionList()
                .add(Projections.rowCount())
                .add(Projections.property("node"))
                .add(Projections.groupProperty("node"))
        );
        HashMap<Node, Long> resultMap = new HashMap<Node, Long>();
        List<Object> results = criteria.list();
        for (Object result : results) {
            Object[] row = (Object[]) result;
            resultMap.put((Node) row[1], (Long) row[0]);
        }
        return resultMap;
    }

    /**
     * Returns the readings count for a capability per link.
     *
     * @param capability , a capability .
     * @return a map containing readings of a capability per link.
     */
    public Map<Link, Long> getReadingsCountPerLink(final Capability capability) {
        final Session session = getSessionFactory().getCurrentSession();
        Criteria criteria = session.createCriteria(LinkReading.class);
        criteria.add(Restrictions.eq("capability", capability));
        criteria.setProjection(Projections.projectionList()
                .add(Projections.rowCount())
                .add(Projections.property("link"))
                .add(Projections.groupProperty("link"))
        );
        HashMap<Link, Long> resultMap = new HashMap<Link, Long>();
        List<Object> results = criteria.list();
        for (Object result : results) {
            Object[] row = (Object[]) result;
            resultMap.put((Link) row[1], (Long) row[0]);
        }
        return resultMap;
    }

    /**
     * Returns the readings count for a capability per node in a testbed.
     *
     * @param capability , a capability .
     * @param testbed , a testbed.
     * @return a map containing readings of a capability per node.
     */
    public Map<Node, Long> getReadingsCountPerNode(final Capability capability, final Testbed testbed) {
        final Session session = getSessionFactory().getCurrentSession();
        Criteria criteria = session.createCriteria(NodeReading.class);
        criteria.createAlias("node","no");
        criteria.add(Restrictions.eq("no.setup",testbed.getSetup()));
        criteria.add(Restrictions.eq("capability", capability));
        criteria.setProjection(Projections.projectionList()
                .add(Projections.rowCount())
                .add(Projections.property("node"))
                .add(Projections.groupProperty("node"))
        );
        HashMap<Node, Long> resultMap = new HashMap<Node, Long>();
        List<Object> results = criteria.list();
        for (Object result : results) {
            Object[] row = (Object[]) result;
            resultMap.put((Node) row[1], (Long) row[0]);
        }
        return resultMap;
    }

    /**
     * Returns the readings count for a capability per link in a testbed.
     *
     * @param capability , a capability .
     * @param testbed , a testbed.
     * @return a map containing readings of a capability per link.
     */
    public Map<Link, Long> getReadingsCountPerLink(final Capability capability, final Testbed testbed) {
        final Session session = getSessionFactory().getCurrentSession();
        Criteria criteria = session.createCriteria(LinkReading.class);
        criteria.createAlias("link","li");
        criteria.add(Restrictions.eq("li.setup",testbed.getSetup()));
        criteria.add(Restrictions.eq("capability", capability));
        criteria.setProjection(Projections.projectionList()
                .add(Projections.rowCount())
                .add(Projections.property("link"))
                .add(Projections.groupProperty("link"))
        );
        HashMap<Link, Long> resultMap = new HashMap<Link, Long>();
        List<Object> results = criteria.list();
        for (Object result : results) {
            Object[] row = (Object[]) result;
            resultMap.put((Link) row[1], (Long) row[0]);
        }
        return resultMap;
    }
}