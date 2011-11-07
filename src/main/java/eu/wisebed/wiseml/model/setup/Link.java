package eu.wisebed.wiseml.model.setup;

import eu.wisebed.wisedb.model.LinkReading;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

/**
 * This is a persistant class for the object link that has the
 * properties of a link. In the class there are
 * getter and setter methods for the properties.
 */
public class Link implements Serializable{

    private static final long serialVersionUID = -393203811928650579L;

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
    private List<Capability> capabilities;
    /**
     * Set of readings for this link.
     */
    private Set<LinkReading> readings;

    /**
     * the Rssi value of the link.
     */
    private Rssi rssi;

    /**
     * list of data.
     */
    private List<Data> data;

    /**
     * this link belongs to a setup.
     */
    private Setup setup;

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
    public List<Capability> getCapabilities() {
        return capabilities;
    }

    /**
     * this method sets the list of capabilities of the link.
     *
     * @param capabilities the list of capabilities.
     */
    public void setCapabilities(List<Capability> capabilities) {
        this.capabilities = capabilities;
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

    /**
     * Returns a collection of setups.
     *
     * @return  the related setup instance
     */
    public Setup getSetup() {
        return setup;
    }

    /**
     * sets the setup this link belongs to setups.
     *
     * @param setup , a setup instance
     */
    public void setSetup(final Setup setup) {
        this.setup = setup;
    }

    /**
     * Returns all the capability readings for this link.
     *
     * @return readings , all capabilities readings for this link.
     */
    public Set<LinkReading> getReadings() {
        return readings;
    }

    /**
     * Sets the set of readings for all capability.
     *
     * @param readings , a set of capabiliteis readings for this link.
     */
    public void setReadings(Set<LinkReading> readings) {
        this.readings = readings;
    }

    /**
     * Override of Object's equals() method
     *
     * @param obj , an object instance
     * @return true or false on whether the objects are equal.
     */
    @Override
    public boolean equals(final Object obj) {

        // if null return false
        if (obj == null){
            return false;
        }

        if (!(obj instanceof Link)){
            return false;
        }

        // if same reference return true;
        if (this == obj){
            return true;
        }

        // equility against name
        Link test = (Link) obj;
        return source.equals(test.getSource()) && target.equals(test.getTarget());
    }

    /**
     * Override of Object's hashCode() method
     *
     * @return hascode value
     */
    @Override
    public int hashCode() {
        return (source == null) ? System.identityHashCode(this) : source.hashCode();
    }
}
