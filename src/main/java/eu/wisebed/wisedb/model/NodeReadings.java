package eu.wisebed.wisedb.model;

import eu.wisebed.wiseml.model.trace.Message;

import java.io.Serializable;

public class NodeReadings extends Message implements Serializable{

    private String nodeId;

    private String capId;

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
}
