package eu.wisebed.wisedb.model;

import eu.wisebed.wiseml.model.setup.Link;
import java.util.Date;

public class LinkReadingStat {

    private Link link;
    private Date latestTimestamp;
    private Double latestReading;
    private Date firstTimestamp;
    private Double firstReading;
    private Double maxReading;
    private Double minReading;
    private Long totalCount;

    public LinkReadingStat(){
        //empty constructor
    }

    public LinkReadingStat(final Link link, final Date latestTimestamp, final Double latestReading,
                           final Date firstTimestamp, final Double firstReading,
                           final Double maxReading, final Double minReading, final Long totalCount) {
        this.link = link;
        this.latestTimestamp = latestTimestamp;
        this.latestReading = latestReading;
        this.firstTimestamp = firstTimestamp;
        this.firstReading = firstReading;
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

    public Date getLatestTimestamp() {
        return latestTimestamp;
    }

    public void setLatestTimestamp(final Date latestTimestamp) {
        this.latestTimestamp = latestTimestamp;
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

        public Double getLatestReading() {
        return latestReading;
    }

    public void setLatestReading(Double latestReading) {
        this.latestReading = latestReading;
    }

    public Date getFirstTimestamp() {
        return firstTimestamp;
    }

    public void setFirstTimestamp(Date firstTimestamp) {
        this.firstTimestamp = firstTimestamp;
    }

    public Double getFirstReading() {
        return firstReading;
    }

    public void setFirstReading(Double firstReading) {
        this.firstReading = firstReading;
    }


    public String toString(){
        return "[" + link.getSource()+ "," + link.getTarget()+ "] : "+ latestTimestamp + " " + latestReading + " " + firstTimestamp + " " + firstReading
                + " " + maxReading +" " + minReading + " "+ totalCount;
    }
}
