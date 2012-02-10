package eu.wisebed.wisedb.model;

import org.hibernate.annotations.Entity;

import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * This is a persistent class for the object NodeReading that has the
 * properties of a wisedb entry. In the class there are
 * getter and setter methods for the properties.
 */
@Entity
@Table(name = "nodeReadings")
public final class NodeReading implements Serializable {

    /**
     * Serial Version Unique ID.
     */
    private static final long serialVersionUID = -1984083831602799368L;

    /**
     * Reading id.
     */
    private int id;

    /**
     * Capability reference.
     */
    private NodeCapability capability;

    /**
     * Timestamp.
     */
    private Date timestamp;

    /**
     * Capability reading value for this node.
     */
    private Double reading;

    /**
     * Capability string reading value for this node.
     */
    private String stringReading;

    /**
     * Constructor.
     */
    public NodeReading() {
        // empty constructor
    }

    /**
     * Returns NodeReading's id.
     *
     * @return NodeReading's id.
     */
    public int getId() {
        return id;
    }

    /**
     * Sets NodeReading's id.
     *
     * @param id , nodereading's id.
     */
    public void setId(final int id) {
        this.id = id;
    }

    /**
     * Returns the capability that indicated this reading.
     *
     * @return capability persistent object.
     */
    public NodeCapability getCapability() {
        return capability;
    }

    /**
     * Sets the capability that indicated this reading.
     *
     * @param capability , must be persistent.
     */
    public void setCapability(final NodeCapability capability) {
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
     * @param timestamp , timestamp of the reading.
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
     * @param reading , this reading value.
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
}
