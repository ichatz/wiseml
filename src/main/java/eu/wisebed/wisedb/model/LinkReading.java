package eu.wisebed.wisedb.model;

import eu.wisebed.wiseml.model.setup.Capability;
import eu.wisebed.wiseml.model.setup.Link;

import java.io.Serializable;
import java.util.Date;

/**
 * This is a persistent class for the object LinkReading that has the
 * properties of a wisedb entry. In the class there are
 * getter and setter methods for the properties.
 */
public final class LinkReading implements Serializable {

    /**
     * Serial verison unique ID.
     */
    private static final long serialVersionUID = -8617855105682730969L;

    /**
     * Reading id.
     */
    private int id;

    /**
     * Link.
     */
    private Link link;

    /**
     * Capability.
     */
    private Capability capability;

    /**
     * Timestamp of the reading.
     */
    private Date timestamp;

    /**
     * Numeric value of the reading.
     */
    private Double reading;

    /**
     * Numeric value of the current RSSI of the link.
     */
    private Double rssiValue;

    /**
     * String value of the reading.
     */
    private String stringReading;

    /**
     * Constructor.
     */
    public LinkReading() {
        // empty constructor
    }

    /**
     * Returns this reading id.
     *
     * @return this reading id.
     */
    public int getId() {
        return id;
    }

    /**
     * Sets this reading id.
     *
     * @param id , reading id
     */
    public void setId(final int id) {
        this.id = id;
    }

    /**
     * Returns capability reading.
     *
     * @return Link's capability reading
     */
    public Double getReading() {
        return reading;
    }

    /**
     * Set capability reading.
     *
     * @param reading , reading value.
     */
    public void setReading(final Double reading) {
        this.reading = reading;
    }

    /**
     * Returns timestamp value.
     *
     * @return Link's reading timestamp
     */
    public Date getTimestamp() {
        return timestamp;
    }

    /**
     * Sets the reading timestamp.
     *
     * @param timestamp , a Date instance.
     */
    public void setTimestamp(final Date timestamp) {
        this.timestamp = timestamp;
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
     * Returns the link associated with this LinkReading.
     *
     * @return the link associated with this LinkReading.
     */
    public Link getLink() {
        return link;
    }

    /**
     * Sets the link associated with this LinkReading.
     *
     * @param link the associated link.
     */
    public void setLink(final Link link) {
        this.link = link;
    }

    /**
     * Returns the capability associated with this LinkReading.
     *
     * @return the capability associated with this LinkReading.
     */
    public Capability getCapability() {
        return capability;
    }

    /**
     * Sets the capability associated with this LinkReading.
     *
     * @param capability the associated capability.
     */
    public void setCapability(final Capability capability) {
        this.capability = capability;
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
}
