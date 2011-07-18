package eu.wisebed.wisedb.model;

import eu.wisebed.wiseml.model.trace.Message;

import java.io.Serializable;

public class LinkReadings extends Message implements Serializable {

    private String linkSource;

    private String linkTarget;

    private String capId;

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
}
