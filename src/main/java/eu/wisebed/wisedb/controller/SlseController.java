package eu.wisebed.wisedb.controller;

import eu.wisebed.wisedb.model.Slse;
import eu.wisebed.wisedb.model.Testbed;
import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.classic.Session;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import java.util.List;


/**
 * CRUD operations for Node entities.
 */
@SuppressWarnings("unchecked")
public class SlseController extends AbstractController<Slse> {
    /**
     * static instance(ourInstance) initialized as null.
     */
    private static SlseController ourInstance = null;

    /**
     * Logger.
     */
    private static final Logger LOGGER = Logger.getLogger(SlseController.class);
    private static final String SETUP = "setup";
    private static final String SLSE_ID = "name";


    /**
     * Public constructor .
     */
    public SlseController() {
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
    public static SlseController getInstance() {
        synchronized (NodeController.class) {
            if (ourInstance == null) {
                ourInstance = new SlseController();
            }
        }

        return ourInstance;
    }

    /**
     * get the Slse from the database that corresponds to the input id.
     *
     * @param entityID the id of the Entity object.
     * @return an Entity object.
     */
    public Slse getByID(final String entityID) {
        LOGGER.info("getByID(" + entityID + ")");
        return super.getByID(new Slse(), entityID);
    }

    /**
     * Delete the input Slse from the database.
     *
     * @param slse the Slse that we want to delete
     */
    public void delete(final Slse slse) {
        LOGGER.info("delete(" + slse + ")");
        super.delete(slse, slse.getName());
    }

    /**
     * Delete the input Node from the database.
     *
     * @param slseName the id of the node tha we want to delete
     */
    public void delete(final String slseName) {
        LOGGER.info("delete(" + slseName + ")");
        super.delete(new Slse(), slseName);
    }

    /**
     * Listing all the Slses from the database.
     *
     * @return a list of all the entries that exist inside the table Slses.
     */
    public List<Slse> list() {
        LOGGER.info("list()");
        return super.list(new Slse());
    }

    /**
     * Listing all the slses from the database belonging to a selected testbed.
     *
     * @param testbed , a selected testbed.
     * @return a list of testbed links.
     */
    public List<Slse> list(final Testbed testbed) {
        LOGGER.info("list(" + testbed + ")");
        final Session session = getSessionFactory().getCurrentSession();
        final Criteria criteria = session.createCriteria(Slse.class);
        criteria.add(Restrictions.eq(SETUP, testbed.getSetup()));
        return (List<Slse>) criteria.list();
    }
    public List<String> listNames(final Testbed testbed) {
        LOGGER.info("list(" + testbed + ")");
        final Session session = getSessionFactory().getCurrentSession();
        final Criteria criteria = session.createCriteria(Slse.class);
        criteria.setProjection(Projections.property("name"));
        criteria.add(Restrictions.eq(SETUP, testbed.getSetup()));
        return (List<String>) criteria.list();
    }


    public int count() {
        LOGGER.info("count()");
        final Session session = getSessionFactory().getCurrentSession();
        final Criteria criteria = session.createCriteria(Slse.class);
        criteria.setProjection(Projections.rowCount());
        return (Integer) criteria.list().get(0);
    }

    public int count(final Testbed testbed) {
        LOGGER.info("count(" + testbed + ")");
        final Session session = getSessionFactory().getCurrentSession();
        final Criteria criteria = session.createCriteria(Slse.class);
        criteria.add(Restrictions.eq(SETUP, testbed.getSetup()));
        criteria.setProjection(Projections.rowCount());
        return (Integer) criteria.list().get(0);
    }
}
