package eu.wisebed.wisedb.exception;

public class UnknownCapabilityException extends Exception {

    public UnknownCapabilityException(final String id){
        super("Unknown Capability. Cannot find persisted capability entity with id : " + id);
    }
}
