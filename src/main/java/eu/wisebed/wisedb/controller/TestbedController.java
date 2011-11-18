package eu.wisebed.wisedb.controller;

import eu.wisebed.wisedb.model.Testbed;
import eu.wisebed.wiseml.model.setup.Link;
import eu.wisebed.wiseml.model.setup.Node;
import eu.wisebed.wiseml.model.setup.Setup;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import java.util.List;

/**
 * CRUD operations for Testbed entities.
 */
public final class TestbedController extends AbstractController<Testbed> {

    /**
     * static instance(ourInstance) initialized as null.
     */
    private static TestbedController ourInstance = null;

    /**
     * Testbed ID literal.
     */
    private static final String TESTBED_ID = "id";
    /**
     * UrnPrefix literal.
     */
    private static final String URN_PREFIX = "urnPrefix";
    /**
     * Setup literal.
     */
    private static final String SETUP = "setup";
    /**
     * Source literal.
     */
    private static final String SOURCE = "source";

    /**
     * Public constructor .
     */
    public TestbedController() {
        // Does nothing
        super();
    }

    /**
     * TestbedController is loaded on the first execution of
     * TestbedController.getInstance() or the first access to
     * TestbedController.ourInstance, not before.
     *
     * @return ourInstance
     */
    public static TestbedController getInstance() {
        synchronized (TestbedController.class) {
            if (ourInstance == null) {
                ourInstance = new TestbedController();
            }
        }

        return ourInstance;
    }

    /**
     * get the Testbed from the database that corresponds to the input id.
     *
     * @param entityID the id of the Entity object.
     * @return an Entity object.
     */
    public Testbed getByID(final int entityID) {
        return super.getByID(new Testbed(), entityID);
    }

    /**
     * Delete the input Testbed from the database.
     *
     * @param value the Testbed tha we want to delete
     */
    public void delete(final Testbed value) {
        super.delete(value, value.getId());
    }

    /**
     * Listing all the Testbeds from the database.
     *
     * @return a list of all the entries that exist inside the table Testbed.
     */
    public List<Testbed> list() {
        return super.list(new Testbed());
    }

    /**
     * Return the Testbed from the database that has the given urn prefix.
     *
     * @param urnPrefix the Urn prefix of the Testbed object.
     * @return a Testbed object.
     */
    @SuppressWarnings("unchecked")
    public Testbed getByUrnPrefix(final String urnPrefix) {
        final Session session = getSessionFactory().getCurrentSession();
        final Criteria criteria = session.createCriteria(Testbed.class);
        criteria.add(Restrictions.like(URN_PREFIX, urnPrefix, MatchMode.START));
        criteria.addOrder(Order.asc(URN_PREFIX));
        criteria.setMaxResults(1);
        return (Testbed) criteria.uniqueResult();
    }

    /**
     * Listing all the Nodes belonging to a testbed.
     *
     * @param testbed , a testbed instance.
     * @return a list of all the entries that much the criterias
     */
    @SuppressWarnings("unchecked")
    public List<Node> listTestbedNodes(final Testbed testbed) {
        final org.hibernate.classic.Session session = getSessionFactory().getCurrentSession();

        // get testbed only setup
        final Setup setup = testbed.getSetup();

        final Criteria criteria = session.createCriteria(Node.class);
        criteria.add(Restrictions.eq(SETUP, setup));
        criteria.addOrder(Order.asc(TESTBED_ID));
        return (List<Node>) criteria.list();
    }

    /**
     * Listing all the Nodes belonging to a testbed.
     *
     * @param testbed , a testbed instance.
     * @return a list of all the entries that much the criterias
     */
    @SuppressWarnings("unchecked")
    public List<Link> listTestbedLinks(final Testbed testbed) {
        final org.hibernate.classic.Session session = getSessionFactory().getCurrentSession();
        // get testbed only setup
        final Setup setup = testbed.getSetup();

        final Criteria criteria;
        criteria = session.createCriteria(Link.class);
        criteria.add(Restrictions.eq(SETUP, setup));
        criteria.addOrder(Order.asc(SOURCE));
        return (List<Link>) criteria.list();

    }
}
