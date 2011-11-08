package eu.wisebed.wisedb.exception;

public class UnknownCapabilityException extends Exception {

    private static final long serialVersionUID = -7602463742518896601L;

    public UnknownCapabilityException(final String capabilityName) {
        super("Unknown Capability. Cannot find persisted capability entity with id : " + capabilityName);
    }
}
