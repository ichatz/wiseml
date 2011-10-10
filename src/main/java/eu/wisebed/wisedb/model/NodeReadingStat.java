package eu.wisebed.wisedb.model;

import eu.wisebed.wiseml.model.setup.Node;

import java.util.Date;

public class NodeReadingStat {

    private Node node;
    private Date latestTimestamp;
    private Double latestReading;
    private Date firstTimestamp;
    private Double firstReading;
    private Double maxReading;
    private Double minReading;
    private Long totalCount;

    public NodeReadingStat(){
        //empty constructor
    }

    public NodeReadingStat(final Node node, final Date latestTimestamp, final Double latestReading,
                           final Date firstTimestamp, final Double firstReading,
                           final Double maxReading, final Double minReading, final Long totalCount) {
        this.node = node;
        this.latestTimestamp = latestTimestamp;
        this.latestReading = latestReading;
        this.firstTimestamp = firstTimestamp;
        this.firstReading = firstReading;
        this.maxReading = maxReading;
        this.minReading = minReading;
        this.totalCount = totalCount;
    }

    public Node getNode() {
        return node;
    }

    public void setNode(Node node) {
        this.node = node;
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
        return node.getId()+ " : "+ latestTimestamp + " " + latestReading + " " + firstTimestamp + " " + firstReading
                + " " + maxReading +" " + minReading + " "+ totalCount;
    }
}
