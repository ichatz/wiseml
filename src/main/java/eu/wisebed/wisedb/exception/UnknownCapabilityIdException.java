package eu.wisebed.wisedb.exception;

public class UnknownCapabilityIdException extends Exception {

    public UnknownCapabilityIdException(final String id){
        super("Unknown Capability Id. Cannot find persisted capability entity with id : " + id);
    }
}
