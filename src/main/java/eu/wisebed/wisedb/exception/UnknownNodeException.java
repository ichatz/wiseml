package eu.wisebed.wisedb.exception;

public class UnknownNodeException extends Exception {

    private static final long serialVersionUID = -5552569452258829527L;

    public UnknownNodeException(final String nodeId) {
        super("Unknown Node. Cannot find persisted node entity with id : " + nodeId);
    }
}
