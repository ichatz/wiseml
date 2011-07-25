package eu.wisebed.wisedb.model;

import java.io.Serializable;
import java.util.Date;

import eu.wisebed.wiseml.model.setup.Capability;
import eu.wisebed.wiseml.model.setup.Link;

public class LinkReading implements Serializable {

    /**
     * Reading id.
     */
    private int id;

    /**
     * Link reference.
     */
    private Link link;

    /**
     * Capability reference.
     */
    private Capability capability;

    /**
     * Timestamp
     */
    private Date timestamp;

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
     * Returns this reading id.
     * @return this reading id.
     */
    public int getId(){
        return id;
    }

    /**
     * Sets this reading id.
     * @param id
     */
    public void setId(final int id){
        this.id = id;
    }

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
    public Date getTimestamp() {
        return timestamp;
    }

    /**
     * Set timestamp value.
     * @param timestamp
     */
    public void setTimestamp(final Date timestamp) {
        this.timestamp = timestamp;
    }

    /**
     * Returns the rssi value of the link.
     * @return rssi value of the link.
     */
    public double getRssiValue(){
       return rssiValue;
    }

    /**
     * Sets the rssi value of the link.
     * @return rssi value of the link.
     */
    public void setRssiValue(final double rssiValue) {
        this.rssiValue = rssiValue;
    }

    public Link getLink() {
        return link;
    }

    public void setLink(Link link) {
        this.link = link;
    }

    public Capability getCapability() {
        return capability;
    }

    public void setCapability(Capability capability) {
        this.capability = capability;
    }

}
