package eu.wisebed.wisedb.model;

import eu.wisebed.wiseml.model.setup.Capability;
import eu.wisebed.wiseml.model.setup.Node;

import java.io.Serializable;
import java.util.Date;

public class LastNodeReading implements Serializable {

    private static final long serialVersionUID = 2824765230014359545L;

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
    public LastNodeReading() {
        // empty constructor
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
