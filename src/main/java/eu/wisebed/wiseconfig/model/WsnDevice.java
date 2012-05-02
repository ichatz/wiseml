package eu.wisebed.wiseconfig.model;

import java.util.ArrayList;
import java.util.List;

/**
 * This is a persistent class for the object WsnDevice that has the
 * properties of a node in the testbed xml file. In the class there are
 * getter and setter methods for the properties.
 */
public final class WsnDevice {

    /**
     * Unique Resource Name.
     */
    private String urn;

    /**
     * Type of WsnDevice.
     */
    private String type;

    /**
     * Serial interface.
     */
    private String serialinterface;

    /**
     * USB chip ID.
     */
    private String usbchipid;


    /**
     * configurationValues Interface.
     */
    private List<Configuration> configurationValues = new ArrayList<Configuration>();

    /**
     * Returns URN.
     *
     * @return urn.
     */
    public String getUrn() {
        return urn;
    }

    /**
     * Sets URN.
     *
     * @param urn a urn instances.
     */
    public void setUrn(final String urn) {
        this.urn = urn;
    }

    /**
     * Returns type.
     *
     * @return type.
     */
    public String getType() {
        return type;
    }

    /**
     * Sets wsn device type.
     *
     * @param type a wsn device type.
     */
    public void setType(final String type) {
        this.type = type;
    }

    /**
     * Returns serial interface.
     *
     * @return serial interface.
     */
    public String getSerialinterface() {
        return serialinterface;
    }

    /**
     * Sets serial interface.
     *
     * @param serialinterface a serial interface instance.
     */
    public void setSerialinterface(final String serialinterface) {
        this.serialinterface = serialinterface;
    }

    /**
     * Returns USB chip ID.
     *
     * @return USB chip ID.
     */
    public String getUsbchipid() {
        return usbchipid;
    }

    /**
     * Sets USB chip ID.
     *
     * @param usbchipid USB chip ID.
     */
    public void setUsbchipid(final String usbchipid) {
        this.usbchipid = usbchipid;
    }

    public List<Configuration> getConfigurationValues() {
        return configurationValues;
    }

    public void setConfigurationValues(List<Configuration> configurationValues) {
        this.configurationValues = configurationValues;
    }

    public void addConfiguration(String key, String value) {
        if (this.configurationValues == null) {
            this.configurationValues = new ArrayList<Configuration>();
        }
        Configuration c = new Configuration();
        c.setKey(key);
        c.setValue(value);
        this.configurationValues.add(c);
    }
}
