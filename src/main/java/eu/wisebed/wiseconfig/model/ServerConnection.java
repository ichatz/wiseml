package eu.wisebed.wiseconfig.model;

/**
 * This is a persistent class for the object ServerConnection that has the
 * properties of a node in the testbed xml file. In the class there are
 * getter and setter methods for the properties.
 */
public final class ServerConnection {

    /**
     * Server host address.
     */
    private String address;

    /**
     * Server type.
     */
    private String type;

    /**
     * Returns server's address.
     * @return server's address.
     */
    public String getAddress() {
        return address;
    }

    /**
     * Sets server's address.
     * @param address server's address.
     */
    public void setAddress(final String address) {
        this.address = address;
    }

    /**
     * Returns server's type.
     * @return server's type.
     */
    public String getType() {
        return type;
    }

    /**
     * Sets server's type.
     * @param type server's type.
     */
    public void setType(final String type) {
        this.type = type;
    }
}
