package eu.wisebed.wisedb.model;

import eu.wisebed.wiseml.model.trace.Message;

import java.io.Serializable;

public class NodeReadings implements Serializable{

    private String nodeId;

    private String capId;

    private long timestamp;

    private double data;

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

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public double getData() {
        return data;
    }

    public void setData(double data) {
        this.data = data;
    }
}
