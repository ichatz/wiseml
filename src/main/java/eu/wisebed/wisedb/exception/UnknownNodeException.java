package eu.wisebed.wisedb.exception;

public class UnknownNodeException extends Exception {

    public UnknownNodeException(final String id) {
        super("Unknown Node. Cannot find persisted node entity with id : " + id);
    }
}
