package eu.wisebed.wisedb.model;

import eu.wisebed.wisedb.model.Capability;
import org.hibernate.annotations.Entity;

import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * This is a persistent class for the object LastLinkReading that has the
 * properties of a wisedb entry. In the class there are
 * getter and setter methods for the properties.
 */
@Entity
@Table(name="last_link_capability_readings")
public final class LastLinkReading implements Serializable {

    /**
     * Serial Version Unique ID.
     */
    private static final long serialVersionUID = 8748551395278795210L;

    /**
     * Link reference.
     */
    private Link link;

    /**
     * Capability reference.
     */
    private Capability capability;

    /**
     * Timestamp.
     */
    private Date timestamp;

    /**
     * Capability reading value for this node.
     */
    private Double reading;

    /**
     * Numeric value of the current RSSI of the link.
     */
    private Double rssiValue;

    /**
     * String reading value.
     */
    private String stringReading;

    /**
     * Constructor.
     */
    public LastLinkReading() {
        // empty constructor
    }

    /**
     * Returns links.
     *
     * @return last link reading's link
     */
    public Link getLink() {
        return link;
    }

    /**
     * Sets link.
     *
     * @param link last link reading's link.
     */
    public void setLink(final Link link) {
        this.link = link;
    }

    /**
     * Returns the capability that indicated this reading.
     *
     * @return capability persistent object.
     */
    public Capability getCapability() {
        return capability;
    }

    /**
     * Sets the capability that indicated this reading.
     *
     * @param capability last link reading's capability.
     */
    public void setCapability(final Capability capability) {
        this.capability = capability;
    }

    /**
     * Returns the timestamp that this reading occured.
     *
     * @return timestamp of the reading.
     */
    public Date getTimestamp() {
        return timestamp;
    }

    /**
     * Sets the timestamp that this reading occured.
     *
     * @param timestamp last link reading's timestamp.
     */
    public void setTimestamp(final Date timestamp) {
        this.timestamp = timestamp;
    }

    /**
     * Returns this reading value.
     *
     * @return this reading value.
     */
    public Double getReading() {
        return reading;
    }

    /**
     * Sets this reading value.
     *
     * @param reading last link reading's value.
     */
    public void setReading(final Double reading) {
        this.reading = reading;
    }

    /**
     * Returns the rssi value of the link.
     *
     * @return rssi value of the link.
     */
    public Double getRssiValue() {
        return rssiValue;
    }

    /**
     * Sets the rssi value.
     *
     * @param rssiValue , the rssi value
     */
    public void setRssiValue(final Double rssiValue) {
        this.rssiValue = rssiValue;
    }

    /**
     * Returns string reading.
     *
     * @return string reading.
     */
    public String getStringReading() {
        return stringReading;
    }

    /**
     * Sets string reading.
     *
     * @param stringReading string reading.
     */
    public void setStringReading(final String stringReading) {
        this.stringReading = stringReading;
    }

    /**
     * Override of Object's equals() method.
     *
     * @param obj , an object instance
     * @return true or false on whether the objects are equal.
     */
    @Override
    public boolean equals(final Object obj) {

        // if null return false
        if (obj == null) {
            return false;
        }

        if (!(obj instanceof LastLinkReading)) {
            return false;
        }

        // if same reference return true;
        if (this == obj) {
            return true;
        }

        // equility against name
        final LastLinkReading test = (LastLinkReading) obj;
        return link.equals(test.getLink()) && capability.equals(test.getCapability());
    }

    /**
     * Override of Object's hashCode() method.
     *
     * @return hascode value
     */
    @Override
    public int hashCode() {
        return (link == null) ? System.identityHashCode(this) : link.hashCode();
    }
}


