package eu.wisebed.wisedb.model;

import eu.wisebed.wiseml.model.setup.Capability;
import eu.wisebed.wiseml.model.setup.Link;

import java.io.Serializable;
import java.util.Date;

/**
 * LastLinkReading wisedb model.
 */
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
    private double reading;

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
    public double getReading() {
        return reading;
    }

    /**
     * Sets this reading value.
     *
     * @param reading last link reading's value.
     */
    public void setReading(final double reading) {
        this.reading = reading;
    }
}


