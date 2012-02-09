package eu.wisebed.wisedb.model;

import eu.wisebed.wiseml.model.setup.Data;
import org.hibernate.annotations.Entity;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.List;

/**
 * This is a persistant class for the object link that has the
 * properties of a link. In the class there are
 * getter and setter methods for the properties.
 */
@Entity
@Table(name = "links")
public class Link implements Serializable {

    /**
     * Serial Unique Version ID.
     */
    private static final long serialVersionUID = -393203811928650579L;

    /**
     * The link primary key.
     */
    @Id
    private LinkPK linkPK;

    public Link() {
        linkPK = new LinkPK();
        virtual = false;
        encrypted = false;
    }

    /**
     * a boolean value indicating if the link is encrypted or not.
     */
    @Column(name = "encrypted", nullable = false)
    private Boolean encrypted;

    /**
     * a boolean value if the link is virtual or not.
     */
    @Column(name = "virtual", nullable = false)
    private Boolean virtual;

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
        return linkPK.getSource();
    }

    /**
     * this method sets the source of the link.
     *
     * @param source the source of the link.
     */
    public void setSource(final String source) {
        linkPK.setSource(source);
    }

    /**
     * this method returns the target of the link.
     *
     * @return the target of the link.
     */
    public String getTarget() {
        return linkPK.getTarget();
    }

    /**
     * this method sets the target of the link.
     *
     * @param target the target of the link.
     */
    public void setTarget(final String target) {
        linkPK.setTarget(target);

    }

    /**
     * this method returns the boolean value encrypted of the link.
     *
     * @return the encrypted of the link.
     */
    public Boolean isEncrypted() {
        return encrypted;
    }

    /**
     * this method returns the boolean value encrypted of the link.
     *
     * @return the encrypted of the link.
     */
    public Boolean getEncrypted() {
        return isEncrypted();
    }

    /**
     * this method sets the boolean value encrypted of the link.
     *
     * @param encrypted the encrypted of the link.
     */
    public void setEncrypted(final Boolean encrypted) {
        this.encrypted = encrypted;
    }

    /**
     * this method returns the boolean value virtual of the link.
     *
     * @return the virtual of the link.
     */
    public Boolean isVirtual() {
        return virtual;
    }

    /**
     * this method returns the boolean value virtual of the link.
     *
     * @return the virtual of the link.
     */
    public Boolean getVirtual() {
        return isVirtual();
    }

    /**
     * this method sets the boolean value virtual of the link.
     *
     * @param virtual the virtual of the link.
     */
    public void setVirtual(final Boolean virtual) {
        this.virtual = virtual;
    }

//    /**
//     * this method returns a list of capabilities of the link.
//     *
//     * @return the list of capabilities.
//     */
//    public List<Capability> getCapabilities() {
//        return capabilities;
//    }
//
//    /**
//     * this method sets the list of capabilities of the link.
//     *
//     * @param capabilities the list of capabilities.
//     */
//    public void setCapabilities(final List<Capability> capabilities) {
//        this.capabilities = capabilities;
//    }

//    /**
//     * this method returns the rssi of the link.
//     *
//     * @return the rssi.
//     */
//    public Rssi getRssi() {
//        return rssi;
//    }
//
//    /**
//     * this method sets the rssi of the link.
//     *
//     * @param rssi the rssi of the link.
//     */
//    public void setRssi(final Rssi rssi) {
//        this.rssi = rssi;
//    }

    /**
     * Returns data.
     *
     * @return data.
     */
    public List<Data> getData() {
        return data;
    }

    /**
     * Sets data.
     *
     * @param data list of data.
     */
    public void setData(final List<Data> data) {
        this.data = data;
    }

    /**
     * Returns a collection of setups.
     *
     * @return the related setup instance
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

//    /**
//     * Returns all the capability readings for this link.
//     *
//     * @return readings , all capabilities readings for this link.
//     */
//    public Set<LinkReading> getReadings() {
//        return readings;
//    }
//
//    /**
//     * Sets the set of readings for all capability.
//     *
//     * @param readings , a set of capabiliteis readings for this link.
//     */
//    public void setReadings(final Set<LinkReading> readings) {
//        this.readings = readings;
//    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Link)) return false;

        Link link = (Link) o;

        if (!encrypted.equals(link.encrypted)) return false;
        if (!linkPK.equals(link.linkPK)) return false;
        if (!virtual.equals(link.virtual)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = linkPK.hashCode();
        result = 31 * result + encrypted.hashCode();
        result = 31 * result + virtual.hashCode();
        return result;
    }
}
