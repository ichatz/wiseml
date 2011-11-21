package eu.wisebed.wiseconfig.model;

/**
 * This is a persistent class for the object ProtobufInterface that has the
 * properties of a node in the testbed xml file. In the class there are
 * getter and setter methods for the properties.
 */
public final class ProtobufInterface {

    /**
     * Address Port.
     */
    private String port;

    /**
     * Address IP.
     */
    private String ip;

    /**
     * Return host port.
     * @return host port.
     */
    public String getPort() {
        return port;
    }

    /**
     * Sets address port.
     * @param port address port.
     */
    public void setPort(final String port) {
        this.port = port;
    }

    /**
     * Returns a ip address.
     * @return ip address.
     */
    public String getIp() {
        return ip;
    }

    /**
     * Sets an ip address.
     * @param ip an IP address.
     */
    public void setIp(final String ip) {
        this.ip = ip;
    }
}
