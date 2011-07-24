package eu.wisebed.wisedb.model;

import java.io.Serializable;

import eu.wisebed.wiseml.model.setup.Capability;
import eu.wisebed.wiseml.model.setup.Link;

public class LinkReading implements Serializable {

    private int id;
    public int getId(){
        return id;
    }
    public void setId(final int id){
        this.id = id;
    }

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

    public Capability getCapability() {
        return capability;
    }

    public void setCapability(Capability capability) {
        this.capability = capability;
    }

    /**
     * Timestamp
     */
    private long timestamp;

    /**
     * Numeric value of a reading.
     */
    private double reading;

    /**
     * Numeric value of the current RSSI of the link.
     */
    private double rssiValue;

    /**
     * Constructor.
     */
    public LinkReading(){}

    /**
     * Returns capability reading.
     * @return Link's capability reading
     */
    public double getReading(){
        return reading;
    }

    /**
     * Set capability reading.
     * @param reading
     */
    public void setReading(final double reading){
        this.reading = reading;
    }

    /**
     * Returns timestamp value.
     * @return Link's reading timestamp
     */
    public long getTimestamp() {
        return timestamp;
    }

    /**
     * Set timestamp value.
     * @param timestamp
     */
    public void setTimestamp(final long timestamp) {
        this.timestamp = timestamp;
    }

    /**
     * Return the rssi value of the link.
      * @return rssi value of the link
     */
    public double getRssiValue(){
       return rssiValue;
    }

    public void setRssiValue(final double rssiValue) {
        this.rssiValue = rssiValue;
    }
}
