package eu.wisebed.wisedb.controller;

import eu.wisebed.wisedb.model.Setup;
import org.apache.log4j.Logger;

import java.util.List;

/**
 * CRUD operations for Setup entities.
 */
public class SetupController extends AbstractController<Setup> {

    /**
     * static instance(ourInstance) initialized as null.
     */
    private static SetupController ourInstance = null;

    /**
     * Logger.
     */
    private static final Logger LOGGER = Logger.getLogger(SetupController.class);

    /**
     * Public constructor .
     */
    public SetupController() {
        // Does nothing
        super();
    }

    /**
     * SetupController is loaded on the first execution of
     * SetupController.getInstance() or the first access to
     * SetupController.ourInstance, not before.
     *
     * @return ourInstance
     */
    public static SetupController getInstance() {
        synchronized (SetupController.class) {
            if (ourInstance == null) {
                ourInstance = new SetupController();
            }
        }

        return ourInstance;
    }

    /**
     * get the Nodes from the database that corresponds to the input id.
     *
     * @param entityID the id of the Entity object.
     * @return an Entity object.
     */
    public Setup getByID(final int entityID) {
        LOGGER.info("getByID(" + entityID + ")");
        return super.getByID(new Setup(), entityID);
    }

    /**
     * Delete the input Node from the database.
     *
     * @param value the Node tha we want to delete
     */
    public void delete(final Setup value) {
        LOGGER.info("delete(" + value + ")");
        super.delete(value, value.getId());
    }


    /**
     * Delete the input Node from the database.
     *
     * @param setupId the Node tha we want to delete
     */
    public void delete(final int setupId) {
        LOGGER.info("delete(" + setupId + ")");
        super.delete(new Setup(), setupId);
    }

    /**
     * Listing all the Setups from the database.
     *
     * @return a list of all the entries that exist inside the table Setup.
     */
    public List<Setup> list() {
        LOGGER.info("list()");
        return super.list(new Setup());
    }
}
