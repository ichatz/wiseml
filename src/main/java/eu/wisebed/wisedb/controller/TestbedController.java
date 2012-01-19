package eu.wisebed.wisedb.controller;

import eu.wisebed.wisedb.model.Testbed;
import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import java.util.List;

/**
 * CRUD operations for Testbed entities.
 */
public class TestbedController extends AbstractController<Testbed> {

    /**
     * static instance(ourInstance) initialized as null.
     */
    private static TestbedController ourInstance = null;

    /**
     * UrnPrefix literal.
     */
    private static final String URN_PREFIX = "urnPrefix";

    /**
     * Name literal.
     */
    private static final String NAME = "name";

    /**
     * Logger.
     */
    private static final Logger LOGGER = Logger.getLogger(TestbedController.class);

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
        LOGGER.info("getByID(" + entityID + ")");
        return super.getByID(new Testbed(), entityID);
    }

    /**
     * Delete the input Testbed from the database.
     *
     * @param value the Testbed tha we want to delete
     */
    public void delete(final Testbed value) {
        LOGGER.info("delete(" + value + ")");
        super.delete(value, value.getId());
    }

    /**
     * Listing all the Testbeds from the database.
     *
     * @return a list of all the entries that exist inside the table Testbed.
     */
    public List<Testbed> list() {
        LOGGER.info("list()");
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
        LOGGER.info("getByUrnPrefix(" + urnPrefix + ")");
        final Session session = getSessionFactory().getCurrentSession();
        final Criteria criteria = session.createCriteria(Testbed.class);
        criteria.add(Restrictions.like(URN_PREFIX, urnPrefix, MatchMode.START));
        criteria.addOrder(Order.asc(URN_PREFIX));
        criteria.setMaxResults(1);
        return (Testbed) criteria.uniqueResult();
    }


    /**
     * Return the Testbed from the database that has the given urn prefix.
     *
     * @param testbedName the name of a Testbed object.
     * @return a Testbed object.
     */
    @SuppressWarnings("unchecked")
    public Testbed getByName(final String testbedName) {
        LOGGER.info("getByTestbedName(" + testbedName + ")");
        final Session session = getSessionFactory().getCurrentSession();
        final Criteria criteria = session.createCriteria(Testbed.class);
        criteria.add(Restrictions.like(NAME, testbedName, MatchMode.START));
        criteria.addOrder(Order.asc(NAME));
        criteria.setMaxResults(1);
        return (Testbed) criteria.uniqueResult();
    }
}
