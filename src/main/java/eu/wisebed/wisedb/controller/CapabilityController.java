package eu.wisebed.wisedb.controller;

import eu.wisebed.wisedb.model.Testbed;
import eu.wisebed.wiseml.model.setup.Capability;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import java.util.ArrayList;
import java.util.List;


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
     * Listing all the Capabilities from the database.
     *
     * @return a list of all the entries that exist inside the table Node.
     */
    public List<Capability> list() {
        return super.list(new Capability());
    }

    /**
     * Listing all the capabilities from the database belonging to a selected testbed.
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
     * @param testbed , a selected testbed.
     * @return a list of testbed nodes capabilities.
     */
    public List<Capability> listNodeCapabilities(final Testbed testbed) {
        final org.hibernate.classic.Session session = getSessionFactory().getCurrentSession();
        Criteria criteria = session.createCriteria(Capability.class);
        criteria.createAlias("nodes","ns");
        criteria.add(Restrictions.eq("ns.setup",testbed.getSetup()));
        criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        return (List<Capability>) criteria.list();
    }

    /**
     * Listing all the llink capabilities associated with nodes from the database belonging to a selected testbed.
     * @param testbed , a selected testbed.
     * @return a list of testbed link capabilities.
     */
    public List<Capability> listLinkCapabilities(final Testbed testbed) {
        final org.hibernate.classic.Session session = getSessionFactory().getCurrentSession();
        Criteria criteria = session.createCriteria(Capability.class);
        criteria.createAlias("links","ls");
        criteria.add(Restrictions.eq("ls.setup",testbed.getSetup()));
        criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        return (List<Capability>) criteria.list();
    }
}
