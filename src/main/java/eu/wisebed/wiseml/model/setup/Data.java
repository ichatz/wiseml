package eu.wisebed.wiseml.model.setup;

import eu.wisebed.wiseml.model.trace.Message;

import java.io.Serializable;

/**
 * This is a persistant class for the object data that has the
 * properties of a data. In the class there are
 * getter and setter methods for the properties.
 */
public final class Data implements Serializable {

    /**
     * Serial Unique Version ID.
     */
    private static final long serialVersionUID = 8036404050506823239L;

    /**
     * key id.
     */
    private String key;

    /**
     * value.
     */
    private String value;

    /**
     * node message.
     */
    private Message message;

    /**
     * this method returns key.
     *
     * @return data key
     */

    public String getKey() {
        return key;
    }

    /**
     * this method sets the key.
     *
     * @param key data key
     */

    public void setKey(final String key) {
        this.key = key;
    }

    /**
     * this method returns node message.
     *
     * @return message
     */
    public Message getMessage() {
        return message;
    }

    /**
     * This method sets node message.
     *
     * @param message a message.
     */
    public void setMessage(final Message message) {
        this.message = message;
    }

    /**
     * this method returns data value.
     *
     * @return value
     */
    public String getValue() {
        return value;
    }

    /**
     * this method sets data value.
     *
     * @param value a value.
     */
    public void setValue(final String value) {
        this.value = value;
    }
}
