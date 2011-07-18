package eu.wisebed.wisedb.controller;

import eu.wisebed.wiseml.model.setup.Node;
import eu.wisebed.wiseml.model.setup.Setup;

import java.util.List;

public class SetupController extends AbstractController<Setup> {

    private static SetupController ourInstance = null;

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
        return super.getByID(new Setup(), entityID);
    }

    /**
     * Delete the input Node from the database.
     *
     * @param value the Node tha we want to delete
     */
    public void delete(final Setup value) {
        super.delete(value,value.getId());
    }

    /**
     * Listing all the Setups from the database.
     *
     * @return a list of all the entries that exist inside the table Setup.
     */
    public List<Setup> list() {
        return super.list(new Setup());
    }

}
