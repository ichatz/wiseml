package eu.wisebed.wiseml.model.trace;


import java.io.Serializable;

public class Message {

    /**
     * timestamp.
     */
    private long timestamp;

    /**
     * id.
     */
    private String id;

    /**
     * data.
     */
    private String data;

    /**
     * this method returns the timestamp.
     *
     * @return timestamp
     */
    public long getTimestamp() {
        return timestamp;
    }

    /**
     * this method sets the timestamp.
     *
     * @param timestamp
     */
    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    /**
     * this method returns the id.
     *
     * @return id
     */
    public String getId() {
        return id;
    }

    /**
     * this method sets the id.
     *
     * @param id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * this method returns data.
     *
     * @return data
     */
    public String getData() {
        return data;
    }

    /**
     * this method sets data.
     *
     * @param data
     */
    public void setData(String data) {
        this.data = data;
    }
}
