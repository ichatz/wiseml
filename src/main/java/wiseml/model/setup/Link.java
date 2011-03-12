package wiseml.model.setup;

import java.util.List;

/**
 * This is a persistant class for the object link that has the
 * properties of a link. In the class there are
 * getter and setter methods for the properties.
 */

public class Link {

    /**
     * the source of an object Link.
     */
    private String source;

    /**
     * the target of an object Link.
     */
    private String target;

    /**
     * a boolean value indicating if the link is encrypted or not.
     */
    private boolean encrypted;

    /**
     * a boolean value if the link is virtual or not.
     */
    private boolean virtual;

    /**
     * a list of capabilities to add to the link.
     */
    private List<Capability> capability;

    /**
     * the Rssi value of the link.
     */
    private Rssi rssi;

    private List<Data> data;

    /**
     * this method returns the source of the link.
     *
     * @return the source of the link.
     */
    public String getSource() {
        return source;
    }

    /**
     * this method sets the source of the link.
     *
     * @param source the source of the link.
     */
    public void setSource(String source) {
        this.source = source;
    }

    /**
     * this method returns the target of the link.
     *
     * @return the target of the link.
     */
    public String getTarget() {
        return target;
    }

    /**
     * this method sets the target of the link.
     *
     * @param target the target of the link.
     */
    public void setTarget(String target) {
        this.target = target;
    }

    /**
     * this method returns the boolean value encrypted of the link.
     *
     * @return the encrypted of the link.
     */
    public boolean isEncrypted() {
        return encrypted;
    }

    /**
     * this method sets the boolean value encrypted of the link.
     *
     * @param encrypted the encrypted of the link.
     */
    public void setEncrypted(boolean encrypted) {
        this.encrypted = encrypted;
    }

    /**
     * this method returns the boolean value virtual of the link.
     *
     * @return the virtual of the link.
     */
    public boolean isVirtual() {
        return virtual;
    }

    /**
     * this method sets the boolean value virtual of the link.
     *
     * @param virtual the virtual of the link.
     */
    public void setVirtual(boolean virtual) {
        this.virtual = virtual;
    }

    /**
     * this method returns a list of capabilities of the link.
     *
     * @return the list of capabilities.
     */
    public List<Capability> getCapability() {
        return capability;
    }

    /**
     * this method sets the list of capabilities of the link.
     *
     * @param capability the list of capabilities.
     */
    public void setCapability(List<Capability> capability) {
        this.capability = capability;
    }

    /**
     * this method returns the rssi of the link.
     *
     * @return the rssi.
     */
    public Rssi getRssi() {
        return rssi;
    }

    /**
     * this method sets the rssi of the link.
     *
     * @param rssi the rssi of the link.
     */
    public void setRssi(Rssi rssi) {
        this.rssi = rssi;
    }

    public List<Data> getData() {
        return data;
    }

    public void setData(List<Data> data) {
        this.data = data;
    }
}
