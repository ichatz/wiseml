package eu.wisebed.wisedb.model;

import eu.wisebed.wiseml.model.setup.Link;
import java.util.Date;

public class LinkReadingStat {

    private Link link;
    private Date lastTimestamp;
    private Double lastReading;
    private Double maxReading;
    private Double minReading;
    private Long totalCount;

    public LinkReadingStat(){
        //empty constructor
    }

    public LinkReadingStat(final Link link, final Date lastTimestamp, final Double lastReading,
                           final Double maxReading, final Double minReading, final Long totalCount) {
        this.link = link;
        this.lastTimestamp = lastTimestamp;
        this.lastReading = lastReading;
        this.maxReading = maxReading;
        this.minReading = minReading;
        this.totalCount = totalCount;
    }

    public Link getLink() {
        return link;
    }

    public void setLink(final Link link) {
        this.link = link;
    }

    public double getMaxReading() {
        return maxReading;
    }

    public void setMaxReading(final double maxReading) {
        this.maxReading = maxReading;
    }

    public double getMinReading() {
        return minReading;
    }

    public void setMinReading(final double minReading) {
        this.minReading = minReading;
    }

    public long getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(final Long totalCount) {
        this.totalCount = totalCount;
    }

    public Date getLastTimestamp(){
        return lastTimestamp;
    }

    public void setLastTimestamp(final Date lastTimestamp){
        this.lastTimestamp = lastTimestamp;
    }

    public Double getLastReading() {
        return lastReading;
    }

    public void setLastReading(final Double lastReading) {
        this.lastReading = lastReading;
    }

    public String toString(){
        return "[" + link.getSource()+ "," + link.getTarget()+ "] : "+ lastTimestamp + " " + lastReading
                + " " + maxReading +" " + minReading + " "+ totalCount;
    }
}
