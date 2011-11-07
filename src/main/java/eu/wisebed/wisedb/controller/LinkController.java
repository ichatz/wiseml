package eu.wisebed.wisedb.controller;

import eu.wisebed.wisedb.model.Testbed;
import eu.wisebed.wiseml.model.setup.Capability;
import eu.wisebed.wiseml.model.setup.Link;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import java.util.List;

public class LinkController extends AbstractController<Link> {
    /**
     * static instance(ourInstance) initialized as null.
     */
    private static LinkController ourInstance = null;

    /**
     * Public constructor .
     */
    public LinkController() {
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
    public static LinkController getInstance() {
        synchronized (LinkController.class) {
            if (ourInstance == null) {
                ourInstance = new LinkController();
            }
        }

        return ourInstance;
    }

    /**
     * Get the entry from the link that corresponds to the input id, Source & Target node ids.
     *
     * @param sourceId , The node id of the link's source.
     * @param targetId , The node id of the link's target.
     * @return the link object persisted with the specific id
     */
    public Link getByID(final String sourceId, final String targetId) {
        final Session session = this.getSessionFactory().getCurrentSession();
        Link linkWithId = new Link();
        linkWithId.setSource(sourceId);
        linkWithId.setTarget(targetId);
        return (Link) session.get(Link.class, linkWithId);
    }

    /**
     * Deleting a link entry from the database
     *
     * @param sourceId , The node id of the link's source.
     * @param targetId , The node id of the link's target.
     */
    public void delete(final String sourceId, final String targetId) {
        final Session session = this.getSessionFactory().getCurrentSession();
        Link linkWithId = new Link();
        linkWithId.setSource(sourceId);
        linkWithId.setTarget(targetId);
        session.delete(linkWithId);
    }

    /**
     * Listing all the Links from the database.
     *
     * @return a list of all the entries that exist inside the table Link.
     */
    public List<Link> list() {
        return super.list(new Link());
    }

    /**
     * Listing all the links from the database belonging to a selected testbed.
     *
     * @param testbed , a selected testbed.
     * @return a list of testbed links.
     */
    @SuppressWarnings({"unchecked"})
    public List<Link> list(final Testbed testbed) {
        final Session session = getSessionFactory().getCurrentSession();
        Criteria criteria = session.createCriteria(Link.class);
        criteria.add(Restrictions.eq("setup", testbed.getSetup()));
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
    @SuppressWarnings({"unchecked"})
    public List<Link> listCapabilityLinks(final Capability capability, final Testbed testbed) {
        final Session session = getSessionFactory().getCurrentSession();
        Criteria criteria = session.createCriteria(Link.class);
        criteria.add(Restrictions.eq("setup", testbed.getSetup()));
        criteria.createAlias("capabilities", "caps")
                .add(Restrictions.eq("caps.name", capability.getName()));
        criteria.addOrder(Order.asc("source"));
        return (List<Link>) criteria.list();
    }
}
