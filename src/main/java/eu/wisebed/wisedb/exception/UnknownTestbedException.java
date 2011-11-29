package eu.wisebed.wisedb.exception;

/**
 * Exception class for not stored / unknown testbed.
 */
public class UnknownTestbedException extends Exception {

    /**
     * Serial version unique ID.
     */
    private static final long serialVersionUID = -4712962905078216005L;

    /**
     * Exception constructor.
     * @param testbedId a testbed id.
     */
    public UnknownTestbedException(final String testbedId) {
        super("Unknown Testbed. Cannot find persisted testbed entity with id " + testbedId);
    }
}
