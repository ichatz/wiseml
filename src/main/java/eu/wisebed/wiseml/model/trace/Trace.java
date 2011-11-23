package eu.wisebed.wiseml.model.trace;

import eu.wisebed.wiseml.model.scenario.Timestamp;

import java.util.LinkedList;
import java.util.List;

/**
 * Represents a trace element with tree of timestamps.
 */
public final class Trace {

    /**
     * id for object scenario.
     */
    private int id;

    /**
     * timestamp for object scenario.
     */
    private List<Timestamp> timestamp;

    /**
     * elements of stream.
     */
    private List children;

    /**
     * construct a new list.
     *
     * @return a new list instance.
     */
    public static List listFactory() {
        return new LinkedList();
    }

    /**
     * returns a LinkedList of objects nested in the trace section.
     *
     * @return a LinkedList of objects nested in the trace section.
     */
    public List getChildren() {
        return children;

    }

    /**
     * sets a LinkedList of objects nested in the trace section.
     *
     * @param list a linked list.
     */
    public void setChildren(final List list) {
        children = list;
    }

    /**
     * this method returns scenario id.
     *
     * @return id scenario
     */
    public int getId() {
        return id;
    }

    /**
     * this method sets the scenario id.
     *
     * @param id scenario id
     */
    public void setId(final int id) {
        this.id = id;
    }

    /**
     * this method returns a list of timestamp.
     *
     * @return list of timestamp.
     */
    public List<Timestamp> getTimestamp() {
        return timestamp;
    }

    /**
     * this method sets a list of timestamp.
     *
     * @param timestamp a timestamp list.
     */
    public void setTimestamp(final List<Timestamp> timestamp) {
        this.timestamp = timestamp;
    }
}
