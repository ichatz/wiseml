package eu.wisebed.wisedb.model;

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
@Table(name = "last_link_capability_readings")
public final class LastLinkReading implements Serializable {

    /**
     * Serial Version Unique ID.
     */
    private static final long serialVersionUID = 8748551395278795210L;

    private int id;

    /**
     * Timestamp.
     */
    private Date timestamp;

    /**
     * Capability reading value for this node.
     */
    private Double reading;

    /**
     * String reading value.
     */
    private String stringReading;

    private LinkCapability linkCapability;

    /**
     * Constructor.
     */
    public LastLinkReading() {
        // empty constructor
    }

    public LinkCapability getLinkCapability() {
        return linkCapability;
    }

    public void setLinkCapability(LinkCapability linkCapability) {
        this.linkCapability = linkCapability;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        LastLinkReading that = (LastLinkReading) o;

        if (id != that.id) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id;
    }
}


