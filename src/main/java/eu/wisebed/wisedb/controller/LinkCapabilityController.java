package eu.wisebed.wisedb.controller;

import eu.wisebed.wisedb.model.Capability;
import eu.wisebed.wisedb.model.Link;
import eu.wisebed.wisedb.model.LinkCapability;
import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import java.util.ArrayList;
import java.util.List;

/**
 * CRUD operations for LinkCapabilites entities.
 */
@SuppressWarnings("unchecked")
public class LinkCapabilityController extends AbstractController<LinkCapability> {


    /**
     * static instance(ourInstance) initialized as null.
     */
    private static LinkCapabilityController ourInstance = null;

    /**
     * Source literal.
     */
    private static final String SOURCE = "link_source";
    /**
     * Target literal.
     */
    private static final String TARGET = "link_target";

    /**
     * Capabilities literal.
     */
    private static final String CAPABILITY = "capabilityId";
    private static final String ID = "id";

    /**
     * Logger.
     */
    private static final Logger LOGGER = Logger.getLogger(LinkCapabilityController.class);
    private static final String LINK = "link";


    /**
     * Public constructor .
     */
    public LinkCapabilityController() {
        // Does nothing
        super();
    }

    /**
     * LinkController is loaded on the first execution of
     * LinkController.getInstance() or the first access to
     * LinkController.ourInstance, not before.
     *
     * @return ourInstance
     */
    public static LinkCapabilityController getInstance() {
        synchronized (LinkCapabilityController.class) {
            if (ourInstance == null) {
                ourInstance = new LinkCapabilityController();
            }
        }

        return ourInstance;
    }

    public void delete(final Link link, final Capability capability) {

        LOGGER.info("delete(" + link.getSource() + "--" + link.getTarget() + "," + capability.getName() + ")");

        final Session session = this.getSessionFactory().getCurrentSession();
        final LinkCapability linkCapabilities = new LinkCapability();
        linkCapabilities.setCapability(capability);
        linkCapabilities.setSource(link.getSource());
        linkCapabilities.setTarget(link.getTarget());
        session.delete(linkCapabilities);
    }

    public void add(Link link, Capability capability) {

        LOGGER.info("add(" + link.getSource() + "--" + link.getTarget() + "," + capability.getName() + ")");

        final Session session = this.getSessionFactory().getCurrentSession();
        final LinkCapability linkCapabilities = new LinkCapability();
        linkCapabilities.setCapability(capability);
        linkCapabilities.setSource(link.getSource());
        linkCapabilities.setTarget(link.getTarget());
        session.delete(linkCapabilities);
    }

    public int count() {
        LOGGER.info("count()");
        final Session session = getSessionFactory().getCurrentSession();
        final Criteria criteria = session.createCriteria(LinkCapability.class);
        criteria.setProjection(Projections.rowCount());
        return (Integer) criteria.list().get(0);
    }

    public List<Capability> list() {
        LOGGER.info("list()");
        final Session session = getSessionFactory().getCurrentSession();
        final Criteria criteria = session.createCriteria(LinkCapability.class);
        List<Capability> capabilities = new ArrayList<Capability>();
        List list = criteria.list();
        LOGGER.info(list.size());
        for (Object obj : criteria.list()) {
            if (obj instanceof LinkCapability) {
                final LinkCapability cap = (LinkCapability) obj;
                final Capability capability = cap.getCapability();

                if (capability != null) {
                    capabilities.add(capability);
                }
            }
        }
        return capabilities;
    }

    public LinkCapability getByID(int id) {
        LOGGER.info("getByID(" + id + ")");
        try {
            final Session session = getSessionFactory().getCurrentSession();
            final Criteria criteria = session.createCriteria(LinkCapability.class);
            criteria.add(Restrictions.eq(ID, id));
            Object obj = criteria.list().get(0);

            if (obj instanceof LinkCapability) {

                return (LinkCapability) obj;
            }
        } catch (Exception e) {
            LOGGER.error(e);
            e.printStackTrace();
            LOGGER.error(e.getMessage());
            LOGGER.error(e.getStackTrace().toString());
        }
        return null;
    }

    public List<Capability> list(Link link) {
        LOGGER.info("list(" + link.getSource() + "--" + link.getTarget() + ")");
        final Session session = getSessionFactory().getCurrentSession();
        final Criteria criteria = session.createCriteria(LinkCapability.class);
        criteria.add(Restrictions.eq(LINK, link));
        List<Capability> capabilities = new ArrayList<Capability>();
        List list = criteria.list();
        LOGGER.info(list.size());
        for (Object obj : criteria.list()) {
            if (obj instanceof LinkCapability) {
                final LinkCapability cap = (LinkCapability) obj;
                final Capability capability = cap.getCapability();
                if (capability != null) {
                    capabilities.add(capability);
                }
            }
        }
        return capabilities;
    }


}
