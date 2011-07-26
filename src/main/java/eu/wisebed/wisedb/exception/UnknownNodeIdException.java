package eu.wisebed.wisedb.exception;

public class UnknownNodeIdException extends Exception {

    /**
     * Constructor
     * @param id
     */
    public UnknownNodeIdException(final String id){
        super("Unknown Node Id. Cannot find persisted node entity with id : " + id);
    }
}
