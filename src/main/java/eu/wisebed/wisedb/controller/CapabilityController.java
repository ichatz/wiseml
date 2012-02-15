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
     * Link literal.
     */
    private static final String LINK = "link";
    /**
     * Node literal.
     */
    private static final String NODE = "node";
    /**
     * Capability literal.
     */
    private static final String CAPABILITY = "capability";

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
        criteria.add(Restrictions.eq(CAPABILITY_NAME, entityID));

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

    /**
     * Listing all the capabilities from the database belonging to a selected node.
     *
     * @param node a selected node instance.
     * @return a list of capabilities.
     */
    public List<Capability> list(final Node node) {
        LOGGER.info("list(" + node + ")");

        final List<Capability> result = new ArrayList<Capability>();

        final Session session = getSessionFactory().getCurrentSession();
        final Criteria criteria = session.createCriteria(NodeCapability.class);
        criteria.add(Restrictions.eq(NODE, node));
        final List resList = criteria.list();
        for (Object item : resList) {
            if (item instanceof NodeCapability) {
                result.add(((NodeCapability) item).getCapability());
            }
        }
        return result;
    }

    /**
     * Listing all the capabilities from the database belonging to a selected link.
     *
     * @param link a selected link instance.
     * @return a list of capabilities.
     */
    public List<Capability> list(final Link link) {
        LOGGER.info("list(" + link + ")");

        final List<Capability> result = new ArrayList<Capability>();

        final Session session = getSessionFactory().getCurrentSession();
        final Criteria criteria = session.createCriteria(LinkCapability.class);
        criteria.add(Restrictions.eq(LINK, link));
        final List resList = criteria.list();
        for (Object item : resList) {
            if (item instanceof LinkCapability) {
                result.add(((LinkCapability) item).getCapability());
            }
        }
        return result;
    }

    /**
     * Listing all the nodeCapabilities as Capabilities from the database belonging to a selected testbed.
     *
     * @param testbed a selected testbed instance.
     * @return a list of capabilities.
     */
    public List<Capability> listNodeCapabilities(final Testbed testbed) {
        LOGGER.info("listNodeCapabilities(" + testbed + ")");
        List<Node> nodes = NodeController.getInstance().list(testbed.getSetup());
        final Map<Capability, Integer> result = new HashMap<Capability, Integer>();

        if (nodes.size()>0) {
            final Session session = getSessionFactory().getCurrentSession();
            final Criteria criteria = session.createCriteria(NodeCapability.class);
            criteria.add(Restrictions.in(NODE, nodes));
            final List resList = criteria.list();
            for (Object item : resList) {
                if (item instanceof NodeCapability) {
                    result.put(((NodeCapability) item).getCapability(), 1);
                }
            }
        }
        final List<Capability> res = new ArrayList<Capability>();
        for (final Capability item : result.keySet()) {
            res.add(item);
        }
        return res;
    }

    /**
     * Listing all the linkCapabilities as Capabilities from the database belonging to a selected testbed.
     *
     * @param testbed a selected testbed instance.
     * @return a list of capabilities.
     */
    public List<Capability> listLinkCapabilities(final Testbed testbed) {
        LOGGER.info("listLinkCapabilities(" + testbed + ")");
        final List<Link> links = LinkController.getInstance().list(testbed.getSetup());
        final Map<Capability, Integer> result = new HashMap<Capability, Integer>();
        if (links.size()>0) {
            final Session session = getSessionFactory().getCurrentSession();
            final Criteria criteria = session.createCriteria(LinkCapability.class);
            criteria.add(Restrictions.in(LINK, links));
            final List resList = criteria.list();
            for (Object item : resList) {
                if (item instanceof LinkCapability) {

                    result.put(((LinkCapability) item).getCapability(), 1);
                }
            }
        }
        final List<Capability> res = new ArrayList<Capability>();
        for (final Capability item : result.keySet()) {
            res.add(item);
        }
        return res;
    }

    /**
     * Listing all the nodeCapabilities from the database belonging to a selected testbed.
     *
     * @param testbed a selected testbed instance.
     * @return a list of nodeCapabilities.
     */
    public List<NodeCapability> listNodeCapabilities(final Testbed testbed, final Capability capability) {
        LOGGER.info("listNodeCapabilities(" + testbed + "," + capability + ")");
        List<Node> nodes = NodeController.getInstance().list(testbed.getSetup());
        final List<NodeCapability> result = new ArrayList<NodeCapability>();

        if (nodes.size()>0) {
            final Session session = getSessionFactory().getCurrentSession();
            final Criteria criteria = session.createCriteria(NodeCapability.class);
            criteria.add(Restrictions.in(NODE, nodes));
            criteria.add(Restrictions.eq(CAPABILITY, capability));
            final List resList = criteria.list();
            for (Object item : resList) {
                if (item instanceof NodeCapability) {
                    result.add((NodeCapability) item);
                }
            }
        }
        return result;
    }

    /**
     * Listing all the linkCapabilities as Capabilities from the database belonging to a selected testbed.
     *
     * @param testbed a selected testbed instance.
     * @return a list of linkCapabilities.
     */
    public List<LinkCapability> listLinkCapabilities(final Testbed testbed, final Capability capability) {
        LOGGER.info("listLinkCapabilities(" + testbed + "," + capability + ")");
        List<Link> links = LinkController.getInstance().list(testbed.getSetup());
        final List<LinkCapability> result = new ArrayList<LinkCapability>();


        final Session session = getSessionFactory().getCurrentSession();
        final Criteria criteria = session.createCriteria(LinkCapability.class);
        criteria.add(Restrictions.eq(LINK, links));
        criteria.add(Restrictions.eq(CAPABILITY, capability));
        final List resList = criteria.list();
        for (Object item : resList) {
            if (item instanceof LinkCapability) {
                result.add((LinkCapability) item);
            }
        }

        return result;
    }
}
