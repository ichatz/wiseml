package eu.wisebed.wisedb.model;

import java.io.Serializable;

public class LinkReadings implements Serializable {
    /**
     * Source Node of the link.
     */
    private String linkSource;
    /**
     * Target Node of the link.
     */
    private String linkTarget;
    /**
     * Capability id of the reading.
     */
    private String capId;

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
    public LinkReadings(){}

    /**
     * Returns link source.
     * @return the link's source Node.
     */
    public String getLinkSource(){
        return linkSource;
    }

    /**
     * Set link source.
     * @param linkSource sets the link source.
     */
    public void setLinkSource(final String linkSource){
        this.linkSource = linkSource;
    }

    /**
     * Returns link target
     * @return the link's target Node.
     */
    public String getLinkTarget(){
        return linkTarget;
    }

    /**
     * Set link target
     * @return the link's target Node.
     */
    public void setLinkTarget(final String linkTarget){
        this.linkTarget = linkTarget;
    }

    /**
     * Returns link's capability id.
     * @return link's capability id.
     */
    public String getCapId(){
        return capId;
    }

    /**
     * Set link's capability id.
     * @param capId capability id.
     */
    public void setCapId(final String capId){
        this.capId = capId;
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
