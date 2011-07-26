package eu.wisebed.wisedb.controller;

import eu.wisebed.wiseml.model.setup.Link;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.LikeExpression;

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
     * NodeController is loaded on the first execution of
     * NodeController.getInstance() or the first access to
     * NodeController.ourInstance, not before.
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
     * @param linkSource , The node id of the link's source.
     * @param linkTarget , The node id of the link's target.
     * @return the link object persisted with the specific id
     */
    public Link getByID(final String linkSource , final String linkTarget) {
        final Session session = this.getSessionFactory().getCurrentSession();
        Transaction tx = session.beginTransaction();
        Link linkWithId = new Link();
        linkWithId.setSource(linkSource);
        linkWithId.setTarget(linkTarget);
        Link linkById = (Link) session.get(Link.class,linkWithId);
        tx.commit();
        return linkById;
    }

    /**
     * Deleting a link entry from the database
     * @param sourceId
     * @param targetId
     */
    public void delete (final String sourceId,final String targetId) {
        final Session session = this.getSessionFactory().getCurrentSession();
        Transaction tx = session.beginTransaction();
        Link linkWithId = new Link();
        linkWithId.setSource(sourceId);
        linkWithId.setTarget(targetId);
        session.delete(linkWithId);
        tx.commit();
    }
}
