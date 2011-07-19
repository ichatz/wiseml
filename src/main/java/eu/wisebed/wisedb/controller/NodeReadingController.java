package eu.wisebed.wisedb.controller;

import eu.wisebed.wisedb.model.NodeReading;

public class NodeReadingController extends AbstractController<NodeReading> {

    /**
     * static instance(ourInstance) initialized as null.
     */
    private static NodeReadingController ourInstance = null;

    /**
     * Public constructor .
     */
    public NodeReadingController() {
        // Does nothing
        super();
    }

    /**
     * NodeReadingController is loaded on the first execution of
     * NodeReadingController.getInstance() or the first access to
     * NodeReadingController.ourInstance, not before.
     *
     * @return ourInstance
     */
    public static NodeReadingController getInstance() {
        synchronized (NodeReadingController.class) {
            if (ourInstance == null) {
                ourInstance = new NodeReadingController();
            }
        }

        return ourInstance;
    }

}
