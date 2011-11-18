package eu.wisebed.wisedb.exception;

/**
 * Exception class for not stored / unknown capability.
 */
public final class UnknownCapabilityException extends Exception {

    /**
     * Serial version unique ID.
     */
    private static final long serialVersionUID = -7602463742518896601L;

    /**
     * Exception constructor.
     * @param capabilityName not stored / unknown capability name.
     */
    public UnknownCapabilityException(final String capabilityName) {
        super("Unknown Capability. Cannot find persisted capability entity with id : " + capabilityName);
    }
}
