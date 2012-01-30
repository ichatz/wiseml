package eu.wisebed.wisedb.controller;

import eu.wisebed.wisedb.model.Testbed;
import eu.wisebed.wiseml.model.setup.Capability;
import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * CRUD operations for Capability entities.
 */
@SuppressWarnings("unchecked")
public class CapabilityController extends AbstractController<Capability> {
    /**
     * static instance(ourInstance) initialized as null.
     */
    private static CapabilityController ourInstance = null;

    /**
     * Capability name literal.
     */
    private static final String CAPABILITY_NAME = "name";

    /**
     * Nodes literal.
     */
    private static final String NODES = "nodes";

    /**
     * Links literal.
     */
    private static final String LINKS = "links";

    /**
     * Logger.
     */
    private static final Logger LOGGER = Logger.getLogger(CapabilityController.class);

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
     * @param entity a Capability object.
     */
    public void add(final Capability entity) {
        LOGGER.info("add(" + entity + ")");
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
     * @param entityID the id of the entity instance.
     * @return an Entity object.
     */
    public Capability getByID(final String entityID) {
        LOGGER.info("getByID(" + entityID + ")");
        return super.getByID(new Capability(), entityID);
    }

    /**
     * Delete the input Capability from the database.
     *
     * @param value the Capability tha we want to delete
     */
    public void delete(final Capability value) {
        LOGGER.info("delete(" + value + ")");
        super.delete(value, value.getName());
    }

    /**
     * Deleting a capability entry from the database.
     *
     * @param entityID the id of the Entity object.
     */
    public void delete(final String entityID) {
        LOGGER.info("delete(" + entityID + ")");
        super.delete(new Capability(), entityID);
    }

    /**
     * Listing all capabilities from the database.
     *
     * @return a list of all capabilities persisted.
     */
    public List<Capability> list() {
        LOGGER.info("list()");
        return super.list(new Capability());
    }


    /**
     * Listing all the capabilities from the database belonging to a selected testbed.
     *
     * @param testbed a selected testbed instance.
     * @return a list of testbed capabilities.
     */
    public List<Capability> list(final Testbed testbed) {
        LOGGER.info("list(" + testbed + ")");
        final List<Capability> capabilities = new ArrayList<Capability>();
        final List<Capability> nodeCapabilities = listNodeCapabilities(testbed);
        final List<Capability> linkCapabilities = listLinkCapabilities(testbed);
        capabilities.addAll(nodeCapabilities);
        capabilities.addAll(linkCapabilities);
        return capabilities;
    }

    public List<String> listNames(final Testbed testbed) {
        LOGGER.info("listNames(" + testbed + ")");
        final List<String> capabilities = new ArrayList<String>();
        final List<String> nodeCapabilities = listNodeCapabilitiesNames(testbed);
        final List<String> linkCapabilities = listLinkCapabilitiesNames(testbed);
        final Map namesMap = new HashMap<String, Boolean>();
        for (String capability : nodeCapabilities) {
            namesMap.put(capability, true);
        }
        for (String capability : linkCapabilities) {
            namesMap.put(capability, true);
        }
        for (Object key : namesMap.keySet()) {
            capabilities.add((String) key);
        }
        return capabilities;
    }

    /**
     * Listing all the capabilities associated with nodes from the database belonging to a selected testbed.
     *
     * @param testbed a selected testbed instance.
     * @return a list of testbed nodes capabilities.
     */
    public List<Capability> listNodeCapabilities(final Testbed testbed) {
        LOGGER.info("listNodeCapabilities(" + testbed + ")");
        final Session session = getSessionFactory().getCurrentSession();
        final Criteria criteria = session.createCriteria(Capability.class);
        criteria.createAlias(NODES, "ns");
        criteria.add(Restrictions.eq("ns.setup", testbed.getSetup()));
        criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        return (List<Capability>) criteria.list();
    }

    public List<String> listNodeCapabilitiesNames(final Testbed testbed) {
        LOGGER.info("listNodeCapabilitiesNames(" + testbed + ")");
        final Session session = getSessionFactory().getCurrentSession();
        final Criteria criteria = session.createCriteria(Capability.class);
        criteria.createAlias(NODES, "ns");
        criteria.add(Restrictions.eq("ns.setup", testbed.getSetup()));
        criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        criteria.setProjection(Projections.property("name"));
        return (List<String>) criteria.list();
    }


    /**
     * Listing all the link capabilities associated with nodes from the database belonging to a selected testbed.
     *
     * @param testbed a selected testbed instance.
     * @return a list of testbed link capabilities.
     */
    public List<Capability> listLinkCapabilities(final Testbed testbed) {
        LOGGER.info("listLinkCapabilities(" + testbed + ")");
        final Session session = getSessionFactory().getCurrentSession();
        final Criteria criteria = session.createCriteria(Capability.class);
        criteria.createAlias(LINKS, "ls");
        criteria.add(Restrictions.eq("ls.setup", testbed.getSetup()));
        criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        return (List<Capability>) criteria.list();
    }

    public List<String> listLinkCapabilitiesNames(final Testbed testbed) {
        LOGGER.info("listLinkCapabilitiesNames(" + testbed + ")");
        final Session session = getSessionFactory().getCurrentSession();
        final Criteria criteria = session.createCriteria(Capability.class);
        criteria.createAlias(LINKS, "ls");
        criteria.add(Restrictions.eq("ls.setup", testbed.getSetup()));
        criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        criteria.setProjection(Projections.property("name"));
        return (List<String>) criteria.list();
    }
}
