package eu.wisebed.wisedb.model;

import eu.wisebed.wiseml.model.setup.Node;

import java.util.Date;

public class NodeReadingStat {

    private Node node;
    private Date lastTimestamp;
    private Double lastReading;
    private Double maxReading;
    private Double minReading;
    private Long totalCount;

    public NodeReadingStat() {
        //empty constructor
    }

    public NodeReadingStat(final Node node, final Date lastTimestamp, final Double lastReading,
                           final Double maxReading, final Double minReading, final Long totalCount) {
        this.node = node;
        this.lastTimestamp = lastTimestamp;
        this.lastReading = lastReading;
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

    public Date getLastTimestamp() {
        return lastTimestamp;
    }

    public void setLastTimestamp(final Date lastTimestamp) {
        this.lastTimestamp = lastTimestamp;
    }

    public Double getLastReading() {
        return lastReading;
    }

    public void setLastReading(final Double lastReading) {
        this.lastReading = lastReading;
    }

    public String toString() {
        return "[" + node.getId() + "] : " + lastTimestamp + " " + lastReading + " " + maxReading + " "
                + minReading + " " + totalCount;
    }
}
