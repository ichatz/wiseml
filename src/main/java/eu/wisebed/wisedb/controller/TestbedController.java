package eu.wisebed.wisedb.controller;

import eu.wisebed.wisedb.model.Testbed;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import java.util.List;

/**
 * CRUD operations for Testbed objects.
 */
public class TestbedController extends AbstractController<Testbed> {

    /**
     * static instance(ourInstance) initialized as null.
     */
    private static TestbedController ourInstance = null;

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
     * List al the Testbeds from the database that corresponds to the input
     * parameters.
     *
     * @param urnPrefix the Urn prefix of the Testbed object.
     * @return an Entity object.
     */
    @SuppressWarnings("unchecked")
    public List<Testbed> listByUrnPrefix(final String urnPrefix) {
        final Session session = getSessionFactory().getCurrentSession();
        final Criteria criteria = session.createCriteria(Testbed.class);
        criteria.add(Restrictions.like("urnPrefix", urnPrefix, MatchMode.START));
        criteria.addOrder(Order.asc("urnPrefix"));
        List<Testbed> testbedList = criteria.list();
        return testbedList;
    }

}
