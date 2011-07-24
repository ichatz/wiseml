package eu.wisebed.wisedb.model;

import java.io.Serializable;

import eu.wisebed.wiseml.model.setup.Capability;
import eu.wisebed.wiseml.model.setup.Node;

public class NodeReading implements Serializable{

    /**
     * Reading ID.
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
     * Timestamp
     */
    private long timestamp;

    /**
     * Capability reading value for this node.
     */
    private double reading;

    /**
     * Constructor
     */
    public NodeReading(){}

    public int getId(){
        return id;
    }

    public void setId(final int id){
        this.id = id;
    }

    /**
     *
     * @return
     */
    public Node getNode(){
        return node;
    }

    /**
     *
     * @param node
     */
    public void setNode(final Node node){
        this.node = node;
    }

    /**
     *
     * @return
     */
    public Capability getCapability(){
        return capability;
    }

    /**
     *
     * @param capability
     */
    public void setCapability(final Capability capability){
        this.capability = capability;
    }

    /**
     *
     * @return
     */
    public long getTimestamp() {
        return timestamp;
    }

    /**
     *
     * @param timestamp
     */
    public void setTimestamp(final long timestamp) {
        this.timestamp = timestamp;
    }

    /**
     *
     * @return
     */
    public double getReading() {
        return reading;
    }

    /**
     *
     * @param reading
     */
    public void setReading(final double reading) {
        this.reading = reading;
    }
}
