package eu.wisebed.wisedb.model;

import eu.wisebed.wiseml.model.setup.Capability;
import eu.wisebed.wiseml.model.setup.Link;

import java.io.Serializable;
import java.util.Date;

public class LastLinkReading implements Serializable {

    /**
     * Link reference.
     */
    private Link link;

    public Link getLink() {
        return link;
    }

    public void setLink(Link link) {
        this.link = link;
    }

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
    public LastLinkReading(){
        // empty constructor
    }

    /**
     * Returns the link that indicated this reading.
     * @return link persistent object.
     */
    public Link getNode(){
        return link;
    }

    /**
     * Sets the node that indicated this reading.
     * @param link , must be persistent.
     */
    public void setNode(final Link link){
        this.link = link;
    }

    /**
     * Returns the capability that indicated this reading.
     * @return capability persistent object.
     */
    public Capability getCapability(){
        return capability;
    }

    /**
     * Sets the capability that indicated this reading.
     * @param capability , must be persistent.
     */
    public void setCapability(final Capability capability){
        this.capability = capability;
    }

    /**
     * Returns the timestamp that this reading occured.
     * @return timestamp of the reading.
     */
    public Date getTimestamp() {
        return timestamp;
    }

    /**
     * Sets the timestamp that this reading occured.
     * @param timestamp , timestamp of the reading.
     */
    public void setTimestamp(final Date timestamp) {
        this.timestamp = timestamp;
    }

    /**
     * Returns this reading value.
     * @return this reading value.
     */
    public double getReading() {
        return reading;
    }

    /**
     * Sets this reading value.
     * @param reading , this reading value.
     */
    public void setReading(final double reading) {
        this.reading = reading;
    }
}


