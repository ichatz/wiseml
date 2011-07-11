package eu.wisebed.wiseml.model.trace;

import eu.wisebed.wiseml.model.scenario.Timestamp;

import java.util.LinkedList;
import java.util.List;

/**
 * Represents a trace element with tree of timestamps.
 */
public class Trace {

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
     * @return a new list instance.
     */
    private static List listFactory() {
        return new LinkedList();
    }

    /**
     * returns a LinkedList of objects nested in the trace section.
     * @return
     */
    public List getChildren() {
        return children;

    }

    /**
         * sets a LinkedList of objects nested in the trace section.
         * @return
         */
        public void setChildren(List l) {
            children=l;
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
    public void setId(int id) {
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
     * @param timestamp
     */
    public void setTimestamp(List<Timestamp> timestamp) {
        this.timestamp = timestamp;
    }

}
