package eu.wisebed.wiseconfig.model;

/**
 * This is a persistent class for the object Nodename that has the
 * properties of a testbed xml file. In the class there are
 * getter and setter methods for the properties.
 */
public final class Nodename {

    /**
     * Node's name.
     */
    private String name;

    /**
     * Node's description.
     */
    private String description;

    /**
     * Returns Node's name.
     * @return Node's name.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets Node's name.
     * @param name Node's name.
     */
    public void setName(final String name) {
        this.name = name;
    }

    /**
     * Returns Node's description.
     * @return Node's description.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets Node's description.
     * @param description Node's description.
     */
    public void setDescription(final String description) {
        this.description = description;
    }
}
