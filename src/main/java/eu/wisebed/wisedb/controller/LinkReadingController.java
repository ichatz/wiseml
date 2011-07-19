package eu.wisebed.wisedb.controller;

import eu.wisebed.wisedb.model.LinkReading;

public class LinkReadingController extends AbstractController<LinkReading> {

        /**
     * static instance(ourInstance) initialized as null.
     */
    private static LinkReadingController ourInstance = null;

    /**
     * Public constructor .
     */
    public LinkReadingController() {
        // Does nothing
        super();
    }

    /**
     * LinkReadingController is loaded on the first execution of
     * LinkReadingController.getInstance() or the first access to
     * LinkReadingController.ourInstance, not before.
     *
     * @return ourInstance
     */
    public static LinkReadingController getInstance() {
        synchronized (LinkReadingController.class) {
            if (ourInstance == null) {
                ourInstance = new LinkReadingController();
            }
        }

        return ourInstance;
    }
}
