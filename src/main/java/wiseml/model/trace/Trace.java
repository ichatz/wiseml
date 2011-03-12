package wiseml.model.trace;

import wiseml.model.scenario.Timestamp;

import java.util.List;

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
