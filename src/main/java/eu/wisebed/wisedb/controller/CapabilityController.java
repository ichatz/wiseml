package eu.wisebed.wisedb.controller;

import eu.wisebed.wisedb.model.Capability;
import eu.wisebed.wisedb.model.Link;
import eu.wisebed.wisedb.model.LinkCapability;
import eu.wisebed.wisedb.model.Node;
import eu.wisebed.wisedb.model.NodeCapability;
import eu.wisebed.wisedb.model.Testbed;
import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;
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
     * Unit literal.
     */
    private static final String UNIT = "UNIT";

    /**
     * Datatype literal.
     */
    private static final String DATATYPE = "DATATYPE";

    /**
     * Default value literal.
     */
    private static final String DEFAULT_VALUE = "DEFAULT_VALUE";
    /**
     * static instance(ourInstance) initialized as null.
     */
    private static CapabilityController ourInstance = null;

    /**
     * Capability name literal.
     */
    private static final String CAPABILITY_NAME = "name";

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
     * Prepares and inserts a capability to the persistence with the provided capability name.
     *
     * @param capabilityName , a capability name.
     * @return returns the inserted capability instance.
     */
    Capability prepareInsertCapability(final String capabilityName) {

        LOGGER.info("prepareInsertCapability(" + capabilityName + ")");
        final Capability capability = new Capability();
        capability.setName(capabilityName);
        capability.setDatatype(DATATYPE);
        capability.setDefaultvalue(DEFAULT_VALUE);
        capability.setUnit(UNIT);
        CapabilityController.getInstance().add(capability);

        return capability;
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
        LOGGER.debug("getByID(" + entityID + ")");
        final Session session = getSessionFactory().getCurrentSession();
        final Criteria criteria = session.createCriteria(Capability.class);
        criteria.add(Restrictions.eq("name", entityID));

        final List list = criteria.list();
        if (list.size() > 0) {
            return (Capability) list.get(0);
        }
        return null;
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
//
//    public List<String> listNames(final Testbed testbed) {
//        LOGGER.info("listNames(" + testbed + ")");
//        final List<String> capabilities = new ArrayList<String>();
//        final List<String> nodeCapabilities = listNodeCapabilities(testbed);
//        final List<String> linkCapabilities = listLinkCapabilitiesNames(testbed);
//        final Map namesMap = new HashMap<String, Boolean>();
//        for (String capability : nodeCapabilities) {
//            namesMap.put(capability, true);
//        }
//        for (String capability : linkCapabilities) {
//            namesMap.put(capability, true);
//        }
//        for (Object key : namesMap.keySet()) {
//            capabilities.add((String) key);
//        }
//        return capabilities;
//    }

    public List<Capability> listNodeCapabilities(final Testbed testbed) {
        LOGGER.info("listNodeCapabilities(" + testbed + ")");
        List<Node> nodes = NodeController.getInstance().list(testbed.getSetup());
        final Map<Capability, Integer> result = new HashMap<Capability, Integer>();
        for (final Node node : nodes) {

            final Session session = getSessionFactory().getCurrentSession();
            final Criteria criteria = session.createCriteria(NodeCapability.class);
            criteria.add(Restrictions.eq("node", node));
            final List resList = criteria.list();
            for (Object item : resList) {
                if (item instanceof NodeCapability) {
                    result.put(CapabilityController.getInstance().getByID(((NodeCapability) item).getCapability().getName()), 1);
                }
            }
        }
        final List<Capability> res = new ArrayList<Capability>();
        for (final Capability item : result.keySet()) {
            res.add(item);
        }
        return res;
    }

    public List<Capability> listLinkCapabilities(final Testbed testbed) {
        LOGGER.info("listLinkCapabilities(" + testbed + ")");
        List<Link> links = LinkController.getInstance().list(testbed.getSetup());
        final Map<Capability, Integer> result = new HashMap<Capability, Integer>();
        for (final Link link : links) {

            final Session session = getSessionFactory().getCurrentSession();
            final Criteria criteria = session.createCriteria(LinkCapability.class);
            criteria.add(Restrictions.eq("link", link));
            final List resList = criteria.list();
            for (Object item : resList) {
                if (item instanceof LinkCapability) {
                    result.put(CapabilityController.getInstance().getByID(((LinkCapability) item).getCapability().getName()), 1);
                }
            }
        }
        final List<Capability> res = new ArrayList<Capability>();
        for (final Capability item : result.keySet()) {
            res.add(item);
        }
        return res;
    }

    public List<NodeCapability> listNodeCapabilities(final Testbed testbed, final Capability capability) {
        LOGGER.info("listNodeCapabilities(" + testbed + "," + capability + ")");
        List<Node> nodes = NodeController.getInstance().list(testbed.getSetup());
        final List<NodeCapability> result = new ArrayList<NodeCapability>();
        for (final Node node : nodes) {

            final Session session = getSessionFactory().getCurrentSession();
            final Criteria criteria = session.createCriteria(NodeCapability.class);
            criteria.add(Restrictions.eq("node", node));
            criteria.add(Restrictions.eq("capability", capability));
            final List resList = criteria.list();
            for (Object item : resList) {
                if (item instanceof NodeCapability) {
                    result.add((NodeCapability) item);
                }
            }
        }
        return result;
    }
//
//    /**
//     * Listing all the link capabilities associated with nodes from the database belonging to a selected testbed.
//     *
//     * @param testbed a selected testbed instance.
//     * @return a list of testbed link capabilities.
//     */
//    public List<Capability> listLinkCapabilities(final Testbed testbed) {
//        LOGGER.info("listLinkCapabilities(" + testbed + ")");
//        final Session session = getSessionFactory().getCurrentSession();
//        final Criteria criteria = session.createCriteria(Capability.class);
//        criteria.createAlias(LINKS, "ls");
//        criteria.add(Restrictions.eq("ls.setup", testbed.getSetup()));
//        criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
//        return (List<Capability>) criteria.list();
//    }

//    public List<String> listLinkCapabilitiesNames(final Testbed testbed) {
//        LOGGER.info("listLinkCapabilitiesNames(" + testbed + ")");
//        final Session session = getSessionFactory().getCurrentSession();
//        final Criteria criteria = session.createCriteria(Capability.class);
//        criteria.createAlias(LINKS, "ls");
//        criteria.add(Restrictions.eq("ls.setup", testbed.getSetup()));
//        criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
//        criteria.setProjection(Projections.property("name"));
//        return (List<String>) criteria.list();
//    }

    public List<LinkCapability> listLinkCapabilities(final Testbed testbed, final Capability capability) {
        LOGGER.info("listLinkCapabilities(" + testbed + "," + capability + ")");
        List<Link> links = LinkController.getInstance().list(testbed.getSetup());
        final List<LinkCapability> result = new ArrayList<LinkCapability>();
        for (final Link link : links) {

            final Session session = getSessionFactory().getCurrentSession();
            final Criteria criteria = session.createCriteria(LinkCapability.class);
            criteria.add(Restrictions.eq("link", link));

            criteria.add(Restrictions.eq("capability", capability));
            final List resList = criteria.list();
            for (Object item : resList) {
                if (item instanceof LinkCapability) {
                    result.add((LinkCapability) item);
                }
            }
        }
        return result;
    }


    public List<Capability> list(Link link) {
        return new ArrayList<Capability>();
    }
}
