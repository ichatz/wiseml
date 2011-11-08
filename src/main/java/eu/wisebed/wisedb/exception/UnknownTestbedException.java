package eu.wisebed.wisedb.exception;

public class UnknownTestbedException extends Exception {

    private static final long serialVersionUID = -4712962905078216005L;

    public UnknownTestbedException(final String testbedId) {
        super("Unknown Testbed. Cannot find persisted testbed entity with id or urnPrefix : " + testbedId);
    }
}
