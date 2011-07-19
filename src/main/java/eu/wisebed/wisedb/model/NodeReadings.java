package eu.wisebed.wisedb.model;

import java.io.Serializable;

public class NodeReadings implements Serializable{

    private String nodeId;

    private String capId;

    private long timestamp;

    private double reading;

    public NodeReadings(){}

    public String getNodeId(){
        return nodeId;
    }

    public void setNodeId(final String nodeId){
        this.nodeId = nodeId;
    }

    public String getCapId(){
        return capId;
    }

    public void setCapId(final String capId){
        this.capId = capId;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(final long timestamp) {
        this.timestamp = timestamp;
    }

    public double getReading() {
        return reading;
    }

    public void setReading(final double reading) {
        this.reading = reading;
    }
}
