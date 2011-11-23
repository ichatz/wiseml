package eu.wisebed.wisedb.model;

import eu.wisebed.wiseml.model.setup.Capability;
import eu.wisebed.wiseml.model.setup.Node;

import java.io.Serializable;
import java.util.Date;

/**
 * This is a persistent class for the object NodeReading that has the
 * properties of a wisedb entry. In the class there are
 * getter and setter methods for the properties.
 */
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
     * Node reference.
     */
    private Node node;

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
     * Returns the node that indicated this reading.
     *
     * @return node persistent object.
     */
    public Node getNode() {
        return node;
    }

    /**
     * Sets the node that indicated this reading.
     *
     * @param node , must be persistent.
     */
    public void setNode(final Node node) {
        this.node = node;
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
     * @param capability , must be persistent.
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
    public double getReading() {
        return reading;
    }

    /**
     * Sets this reading value.
     *
     * @param reading , this reading value.
     */
    public void setReading(final double reading) {
        this.reading = reading;
    }
}
