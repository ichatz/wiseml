package eu.wisebed.wisedb.model;

import eu.wisebed.wiseml.model.trace.Message;

import java.io.Serializable;

public class LinkReadings implements Serializable {

    private String linkSource;

    private String linkTarget;

    private String capId;

    private long timestamp;

    private double data;

    public LinkReadings(){}

    public String getLinkSource(){
        return linkSource;
    }

    public void setLinkSource(final String linkSource){
        this.linkSource = linkSource;
    }

    public String getLinkTarget(){
        return linkTarget;
    }

    public void setLinkTarget(final String linkTarget){
        this.linkTarget = linkTarget;
    }

    public String getCapId(){
        return capId;
    }

    public void setCapId(final String capId){
        this.capId = capId;
    }

    public double getData(){
        return data;
    }

    public void setData(final double data){
        this.data = data;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}
