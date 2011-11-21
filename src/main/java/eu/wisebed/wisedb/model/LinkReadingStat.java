package eu.wisebed.wisedb.model;

import eu.wisebed.wiseml.model.setup.Link;

import java.io.Serializable;
import java.util.Date;

/**
 * LinkReadingStat wisedb model. Utility class. This class is not persisted.
 */
public final class LinkReadingStat implements Serializable {

    /**
     * Serial Version Unique ID.
     */
    private static final long serialVersionUID = -1984083831602799368L;

    /**
     *
     */
    private Link link;

    /**
     *
     */
    private Date lastTimestamp;

    /**
     *
     */
    private Double lastReading;

    /**
     *
     */
    private Double maxReading;

    /**
     *
     */
    private Double minReading;

    /**
     *
     */
    private Long totalCount;

    /**
     * Constructor.
     */
    public LinkReadingStat() {
        //empty constructor
    }

    /**
     * Constructor.
     * @param link a link instance.
     * @param lastTimestamp the last reading's timestamp.
     * @param lastReading the last reading's reading value.
     * @param maxReading max reading for this link/capability.
     * @param minReading min reading for this link/capability.
     * @param totalCount total count of readings.
     */
    public LinkReadingStat(final Link link, final Date lastTimestamp, final Double lastReading,
                           final Double maxReading, final Double minReading, final Long totalCount) {
        this.link = link;
        this.lastTimestamp = lastTimestamp;
        this.lastReading = lastReading;
        this.maxReading = maxReading;
        this.minReading = minReading;
        this.totalCount = totalCount;
    }

    /**
     *
     * @return
     */
    public Link getLink() {
        return link;
    }

    /**
     *
     * @param link
     */
    public void setLink(final Link link) {
        this.link = link;
    }

    /**
     *
     * @return
     */
    public double getMaxReading() {
        return maxReading;
    }

    /**
     *
     * @param maxReading
     */
    public void setMaxReading(final double maxReading) {
        this.maxReading = maxReading;
    }

    /**
     *
     * @return
     */
    public double getMinReading() {
        return minReading;
    }

    /**
     *
     * @param minReading
     */
    public void setMinReading(final double minReading) {
        this.minReading = minReading;
    }

    /**
     *
     * @return
     */
    public long getTotalCount() {
        return totalCount;
    }

    /**
     *
     * @param totalCount
     */
    public void setTotalCount(final Long totalCount) {
        this.totalCount = totalCount;
    }

    /**
     *
     * @return
     */
    public Date getLastTimestamp() {
        return lastTimestamp;
    }

    /**
     *
     * @param lastTimestamp
     */
    public void setLastTimestamp(final Date lastTimestamp) {
        this.lastTimestamp = lastTimestamp;
    }

    /**
     *
     * @return
     */
    public Double getLastReading() {
        return lastReading;
    }

    /**
     *
     * @param lastReading
     */
    public void setLastReading(final Double lastReading) {
        this.lastReading = lastReading;
    }

    /**
     * Returns string representation of this stat.
     * @return string representation of this stat.
     */
    public String toString() {
        return "[" + link.getSource() + "," + link.getTarget() + "] : " + lastTimestamp + " " + lastReading
                + " " + maxReading + " " + minReading + " " + totalCount;
    }
}
