package eu.wisebed.wisedb.model;

import java.io.Serializable;

public class NodeReading implements Serializable{
    /**
     * Node id.
     */
    private String nodeId;

    /**
     * Capability id.
     */
    private String capId;

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

    /**
     *
     * @return
     */
    public String getNodeId(){
        return nodeId;
    }

    /**
     *
     * @param nodeId
     */
    public void setNodeId(final String nodeId){
        this.nodeId = nodeId;
    }

    /**
     *
     * @return
     */
    public String getCapId(){
        return capId;
    }

    /**
     *
     * @param capId
     */
    public void setCapId(final String capId){
        this.capId = capId;
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
