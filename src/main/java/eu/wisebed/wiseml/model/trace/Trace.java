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
     * elements of stream.
     */
    private List children;

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
     * sets the list for the objects of the trace.
     * @param children the new list instance.
     */
    public void setChildren(List children) {
        this.children = children;
    }

}
