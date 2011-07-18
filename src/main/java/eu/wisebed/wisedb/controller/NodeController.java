package eu.wisebed.wisedb.controller;

import eu.wisebed.wiseml.model.setup.Node;

import java.util.List;


/**
 * CRUD operations for Node objects.
 */

public class NodeController extends AbstractController<Node> {
    /**
     * static instance(ourInstance) initialized as null.
     */
    private static NodeController ourInstance = null;

    /**
     * Public constructor .
     */
    public NodeController() {
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
    public static NodeController getInstance() {
        synchronized (NodeController.class) {
            if (ourInstance == null) {
                ourInstance = new NodeController();
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
    public Node getByID(final String entityID) {
        return super.getByID(new Node(), entityID);
    }

    /**
     * Delete the input Node from the database.
     *
     * @param value the Node tha we want to delete
     */
    public void delete(final Node value) {
        super.delete(value,value.getId());
    }

    /**
     * Listing all the Nodes from the database.
     *
     * @return a list of all the entries that exist inside the table Node.
     */
    public List<Node> list() {
        return super.list(new Node());
    }

}
