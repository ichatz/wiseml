package eu.wisebed.wisedb.controller;

import eu.wisebed.wiseml.model.setup.Link;
import org.hibernate.Session;

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
     * Listing all the Links from the database.
     *
     * @return a list of all the entries that exist inside the table Link.
     */
    public List<Link> list() {
        return super.list(new Link());
    }

    /**
     * Get the entry from the link that corresponds to the input id, Source & Target node ids.
     *
     * @param sourceId , The node id of the link's source.
     * @param targetId , The node id of the link's target.
     * @return the link object persisted with the specific id
     */
    public Link getByID(final String sourceId , final String targetId) {
        final Session session = this.getSessionFactory().getCurrentSession();
        Link linkWithId = new Link();
        linkWithId.setSource(sourceId);
        linkWithId.setTarget(targetId);
        Link linkById = (Link) session.get(Link.class,linkWithId);
        return linkById;
    }

    /**
     * Deleting a link entry from the database
     * @param sourceId , The node id of the link's source.
     * @param targetId , The node id of the link's target.
     */
    public void delete(final String sourceId,final String targetId) {
        final Session session = this.getSessionFactory().getCurrentSession();
        Link linkWithId = new Link();
        linkWithId.setSource(sourceId);
        linkWithId.setTarget(targetId);
        session.delete(linkWithId);
    }
}
