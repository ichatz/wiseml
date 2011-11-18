package eu.wisebed.wisedb.exception;

/**
 * Exception class for not stored / unknown capability.
 */
public final class UnknownNodeException extends Exception {

    /**
     * Serial version unique ID.
     */
    private static final long serialVersionUID = -5552569452258829527L;

    /**
     * Exception constructor.
     * @param nodeId , node id .
     */
    public UnknownNodeException(final String nodeId) {
        super("Unknown Node. Cannot find persisted node entity with id : " + nodeId);
    }
}
