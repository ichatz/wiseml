package eu.wisebed.wisedb.controller;

import eu.wisebed.wisedb.model.Capability;
import eu.wisebed.wisedb.model.Node;
import eu.wisebed.wisedb.model.NodeCapability;
import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import java.util.ArrayList;
import java.util.List;

/**
 * CRUD operations for NodeCapabilities entities.
 */
@SuppressWarnings("unchecked")
public class NodeCapabilityController extends AbstractController<NodeCapability> {


    /**
     * static instance(ourInstance) initialized as null.
     */
    private static NodeCapabilityController ourInstance = null;

    /**
     * Source literal.
     */
    private static final String NODE = "node";

    /**
     * Capabilities literal.
     */
    private static final String CAPABILITY = "capabilityId";
    /**
     * Id literal.
     */
    private static final String ID = "id";

    /**
     * Logger.
     */
    private static final Logger LOGGER = Logger.getLogger(NodeCapabilityController.class);

    /**
     * Public constructor .
     */
    public NodeCapabilityController() {
        // Does nothing
        super();
    }

    /**
     * NodeCapabilityController is loaded on the first execution of
     * NodeCapabilityController.getInstance() or the first access to
     * NodeCapabilityController.ourInstance, not before.
     *
     * @return ourInstance
     */
    public static NodeCapabilityController getInstance() {
        synchronized (NodeCapabilityController.class) {
            if (ourInstance == null) {
                ourInstance = new NodeCapabilityController();
            }
        }

        return ourInstance;
    }


    public long count() {
        LOGGER.info("count()");
        final Session session = getSessionFactory().getCurrentSession();
        final Criteria criteria = session.createCriteria(NodeCapability.class);
        criteria.setProjection(Projections.rowCount());
        return (Long) criteria.list().get(0);
    }

    public List<Capability> list() {
        LOGGER.info("list()");
        final Session session = getSessionFactory().getCurrentSession();
        final Criteria criteria = session.createCriteria(NodeCapability.class);
        List<Capability> capabilities = new ArrayList<Capability>();
        List list = criteria.list();
        for (Object obj : criteria.list()) {
            if (obj instanceof NodeCapability) {
                final NodeCapability cap = (NodeCapability) obj;
                final Capability capability = new Capability();
                capability.setName(cap.getCapability().getName());
                if (capability != null) {
                    capabilities.add(capability);
                }
            }
        }
        return capabilities;
    }

    public NodeCapability getByID(int id) {
        LOGGER.info("getByID(" + id + ")");
        final Session session = getSessionFactory().getCurrentSession();
        final Criteria criteria = session.createCriteria(NodeCapability.class);
        criteria.add(Restrictions.eq(ID, id));
        Object obj = criteria.list().get(0);

        if (obj instanceof NodeCapability) {

            return (NodeCapability) obj;
        }

        return null;
    }

    public NodeCapability getByID(Node node, String capabilityName) {
        final Capability capability = CapabilityController.getInstance().getByID(capabilityName);
        LOGGER.info("getByID(" + node + "," + capabilityName + ")");
        final Session session = getSessionFactory().getCurrentSession();
        final Criteria criteria = session.createCriteria(NodeCapability.class);
        criteria.add(Restrictions.eq(NODE, node));
        criteria.add(Restrictions.eq(CAPABILITY, capability));
        Object obj = criteria.list().get(0);

        if (obj instanceof NodeCapability) {

            return (NodeCapability) obj;
        }

        return null;
    }


    public List<Capability> list(Node node) {
        LOGGER.info("list(" + node.getId() + ")");
        final Session session = getSessionFactory().getCurrentSession();
        final Criteria criteria = session.createCriteria(NodeCapability.class);
        criteria.add(Restrictions.eq(NODE, node));
        List<Capability> capabilities = new ArrayList<Capability>();
        List list = criteria.list();
        for (Object obj : criteria.list()) {
            if (obj instanceof NodeCapability) {
                final NodeCapability cap = (NodeCapability) obj;
                final Capability capability = new Capability();
                capability.setName(cap.getCapability().getName());
                if (capability != null) {
                    capabilities.add(capability);
                }
            }
        }
        return capabilities;
    }


}
