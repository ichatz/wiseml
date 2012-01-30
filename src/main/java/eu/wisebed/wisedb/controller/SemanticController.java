package eu.wisebed.wisedb.controller;

import eu.wisebed.wisedb.model.Semantic;
import eu.wisebed.wisedb.model.Testbed;
import eu.wisebed.wiseml.model.setup.Node;
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
public class SemanticController extends AbstractController<Semantic> {
    /**
     * static instance(ourInstance) initialized as null.
     */
    private static SemanticController ourInstance = null;

    /**
     * Logger.
     */
    private static final Logger LOGGER = Logger.getLogger(SemanticController.class);
    private static final String SETUP = "setup";
    private static final String SLSE_ID = "name";
    private static final String NODE = "node";


    /**
     * Public constructor .
     */
    public SemanticController() {
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
    public static SemanticController getInstance() {
        synchronized (NodeController.class) {
            if (ourInstance == null) {
                ourInstance = new SemanticController();
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
    public Semantic getByID(final String entityID) {
        LOGGER.info("getByID(" + entityID + ")");
        return super.getByID(new Semantic(), entityID);
    }


    /**
     * Delete the input Slse from the database.
     *
     * @param semantic the Slse that we want to delete
     */
    public void delete(final Semantic semantic) {
        LOGGER.info("delete(" + semantic + ")");
        super.delete(semantic, semantic.getId());
    }

    /**
     * Delete the input Node from the database.
     *
     * @param semanticName the id of the node tha we want to delete
     */
    public void delete(final String semanticName) {
        LOGGER.info("delete(" + semanticName + ")");
        super.delete(new Semantic(), semanticName);
    }

    /**
     * Listing all the Slses from the database.
     *
     * @return a list of all the entries that exist inside the table Slses.
     */
    public List<Semantic> list() {
        LOGGER.info("list()");
        final Session session = getSessionFactory().getCurrentSession();
        final Criteria criteria = session.createCriteria(Semantic.class);
        return (List<Semantic>) criteria.list();
    }

    /**
     * Listing all the slses from the database belonging to a selected testbed.
     *
     * @param testbed , a selected testbed.
     * @return a list of testbed links.
     */
    public List<Semantic> list(final Testbed testbed) {
        LOGGER.info("list(" + testbed + ")");
        final Session session = getSessionFactory().getCurrentSession();
        final Criteria criteria = session.createCriteria(Semantic.class);
//        criteria.add(Restrictions.eq(SETUP, testbed.getSetup()));
        return (List<Semantic>) criteria.list();
    }

    public List<String> listNames(final Testbed testbed) {
        LOGGER.info("list(" + testbed + ")");
        final Session session = getSessionFactory().getCurrentSession();
        final Criteria criteria = session.createCriteria(Semantic.class);
        criteria.setProjection(Projections.property("id"));
//        criteria.add(Restrictions.eq(SETUP, testbed.getSetup()));
        return (List<String>) criteria.list();
    }


    public List<Semantic> listByNode(final Node node) {
        LOGGER.info("listByNode(" + node.getId() + ")");
        final Session session = getSessionFactory().getCurrentSession();
        final Criteria criteria = session.createCriteria(Semantic.class);
        criteria.add(Restrictions.eq(NODE, node.getId()));
        return (List<Semantic>) criteria.list();
    }

    public int count() {
        LOGGER.info("count()");
        final Session session = getSessionFactory().getCurrentSession();
        final Criteria criteria = session.createCriteria(Semantic.class);
        criteria.setProjection(Projections.rowCount());
        return (Integer) criteria.list().get(0);
    }

    public int count(final Testbed testbed) {
        LOGGER.info("count(" + testbed + ")");
        final Session session = getSessionFactory().getCurrentSession();
        final Criteria criteria = session.createCriteria(Semantic.class);
//        criteria.add(Restrictions.eq(SETUP, testbed.getSetup()));
        criteria.setProjection(Projections.rowCount());
        return (Integer) criteria.list().get(0);
    }
}
