package eu.wisebed.wisedb.model;

import org.hibernate.annotations.Entity;

import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * This is a persistent class for the object LastNodeReading that has the
 * properties of a wisedb entry. In the class there are
 * getter and setter methods for the properties.
 */
@Entity
@Table(name = "last_node_capability_readings")
public final class LastNodeReading implements Serializable {

    /**
     * Serial Unique Version ID.
     */
    private static final long serialVersionUID = 2824765230014359545L;

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

    private NodeCapability nodeCapability;

    public NodeCapability getNodeCapability() {
        return nodeCapability;
    }

    public void setNodeCapability(NodeCapability nodeCapability) {
        this.nodeCapability = nodeCapability;
    }

    /**
     * Constructor.
     */
    public LastNodeReading() {
        // empty constructor
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
     * @param timestamp last node reading's timestamp.
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
     * @param reading last node reading's reading value.
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

        LastNodeReading that = (LastNodeReading) o;

        if (id != that.id) return false;
        if (reading != null ? !reading.equals(that.reading) : that.reading != null) return false;
        if (stringReading != null ? !stringReading.equals(that.stringReading) : that.stringReading != null)
            return false;
        if (timestamp != null ? !timestamp.equals(that.timestamp) : that.timestamp != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (timestamp != null ? timestamp.hashCode() : 0);
        result = 31 * result + (reading != null ? reading.hashCode() : 0);
        result = 31 * result + (stringReading != null ? stringReading.hashCode() : 0);
        return result;
    }
}
