package eu.wisebed.wisedb.exception;

public class UnknownTestbedException extends Exception{
    public UnknownTestbedException(final String id) {
        super("Unknown Testbed. Cannot find persisted testbed entity with id or urnPrefix : " + id);
    }
}
