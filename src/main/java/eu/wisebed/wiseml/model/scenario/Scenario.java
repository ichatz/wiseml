package eu.wisebed.wiseml.model.scenario;

import eu.wisebed.wiseml.model.setup.Data;

import java.util.LinkedList;
import java.util.List;

/**
 * This is a persistant class for the object scenario that has the
 * properties of a scenario. In the class there are
 * getter and setter methods for the properties.
 */
public final class Scenario {

    /**
     * id for object scenario.
     */
    private int id;

    /**
     * timestamp for object scenario.
     */
    private List<Timestamp> timestamp;

    /**
     * node data.
     */
    private Data data;

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
     * Sets the children list.
     * @param children a list.
     */
    public void setChildren(final List children) {
        this.children = children;
    }

    /**
     * returns a LinkedList of objects nested in the scenario section.
     *
     * @return linkedlist.
     */
    public List getChildren() {
        return children;
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
     * this method returns a list of timestamps.
     *
     * @return list of timestamps
     */
    public List<Timestamp> getTimestamp() {
        return timestamp;
    }

    /**
     * this method sets a list of timestamps.
     *
     * @param timestamp timestamp.
     */
    public void setTimestamp(final List<Timestamp> timestamp) {
        this.timestamp = timestamp;
    }

    /**
     * this method returns data.
     *
     * @return data
     */
    public Data getData() {
        return data;
    }

    /**
     * this method sets data.
     *
     * @param data data.
     */
    public void setData(final Data data) {
        this.data = data;
    }
}
