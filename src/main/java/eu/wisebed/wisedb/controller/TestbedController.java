package eu.wisebed.wisedb.controller;

import eu.wisebed.wisedb.model.Slse;
import eu.wisebed.wisedb.model.Testbed;
import eu.wisebed.wiseml.model.setup.Link;
import eu.wisebed.wiseml.model.setup.Node;
import eu.wisebed.wiseml.model.setup.Setup;
import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

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

    /**
     * Returns the number of nodes in database for each setup
     *
     * @return map containing the setups and node count
     */
    public Map<String, Long> countNodes() {
        LOGGER.info("countNodes()");
        final Session session = getSessionFactory().getCurrentSession();
        final Criteria criteria = session.createCriteria(Node.class);
        ProjectionList projList = Projections.projectionList();
        projList.add(Projections.groupProperty("setup"));
        projList.add(Projections.rowCount());
        criteria.setProjection(projList);

        List results = criteria.list();
        Iterator iter = results.iterator();
        if (!iter.hasNext()) {
            LOGGER.debug("No objects to display.");
            return null;
        }
        Map<String, Long> resultsMap = new HashMap<String, Long>();
        while (iter.hasNext()) {

            final Object[] obj = (Object[]) iter.next();
            final Setup setup = (Setup) obj[0];
            final long count = (Long) obj[1];
            resultsMap.put(setup.getTestbed().getName(), count);

        }

        return resultsMap;
    }

    /**
     * Returns the number of links in database for each setup
     *
     * @return map containing the setups and link count
     */
    public Map<String, Long> countLinks() {
        LOGGER.info("countLinks()");
        final Session session = getSessionFactory().getCurrentSession();
        final Criteria criteria = session.createCriteria(Link.class);
        ProjectionList projList = Projections.projectionList();
        projList.add(Projections.groupProperty("setup"));
        projList.add(Projections.rowCount());
        criteria.setProjection(projList);

        List results = criteria.list();
        Iterator iter = results.iterator();
        if (!iter.hasNext()) {
            LOGGER.debug("No objects to display.");
            return null;
        }
        Map<String, Long> resultsMap = new HashMap<String, Long>();
        while (iter.hasNext()) {

            final Object[] obj = (Object[]) iter.next();
            final Setup setup = (Setup) obj[0];
            final long count = (Long) obj[1];
            resultsMap.put(setup.getTestbed().getName(), count);

        }

        return resultsMap;
    }

    /**
     * Returns the number of slses in database for each setup
     * @return map containing the setups and slse count
     */
    public Map<String, Long> countSlses() {
        LOGGER.info("countSlses()");
        final Session session = getSessionFactory().getCurrentSession();
        final Criteria criteria = session.createCriteria(Slse.class);
        ProjectionList projList = Projections.projectionList();
        projList.add(Projections.groupProperty("setup"));
        projList.add(Projections.rowCount());
        criteria.setProjection(projList);

        List results = criteria.list();
        Iterator iter = results.iterator();
        if (!iter.hasNext()) {
            LOGGER.debug("No objects to display.");
            return null;
        }
        Map<String, Long> resultsMap = new HashMap<String, Long>();
        while (iter.hasNext()) {

            final Object[] obj = (Object[]) iter.next();
            final Setup setup = (Setup) obj[0];
            final long count = (Long) obj[1];
            resultsMap.put(setup.getTestbed().getName(), count);

        }

        return resultsMap;
    }
}
