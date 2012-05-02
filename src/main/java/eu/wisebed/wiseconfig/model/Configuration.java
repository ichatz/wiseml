package eu.wisebed.wiseconfig.model;

/**
 * This is a persistent class for the object WsnDevice that has the
 * properties of a node in the testbed xml file. In the class there are
 * getter and setter methods for the properties.
 */
public final class Configuration {

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    /**
     * Unique Resource Name.
     */
    private String key;

    /**
     * Type of WsnDevice.
     */
    private String value;

}
