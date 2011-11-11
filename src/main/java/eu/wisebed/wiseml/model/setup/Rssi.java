package eu.wisebed.wiseml.model.setup;

import java.io.Serializable;

/**
 * This is a persistant class for the object rssi that has the
 * properties of a rssi. In the class there are
 * getter and setter methods for the properties.
 */

public class Rssi implements Serializable{

    private static final long serialVersionUID = -756196992494151059L;

    /**
     * the datatype of the object Rssi.
     */
    private String datatype;

    /**
     * the unit of the object Rssi.
     */
    private String unit;

    /**
     *
     * @return
     */
    public String getValue() {
        return value;
    }

    /**
     *
     * @param value
     */
    public void setValue(final String value) {
        this.value = value;
    }

    /**
     * the unit of the object Rssi.
     */
    private String value;


    /**
     * this method returns the value of the rssi.
     *
     * @return the value of the rssi.
     */
    public String getDatatype() {
        return datatype;
    }

    /**
     * this method sets the datatype of the rssi.
     *
     * @param datatype the datatype of the rssi.
     */
    public void setDatatype(final String datatype) {
        this.datatype = datatype;
    }

    /**
     * this method returns the unit of the rssi.
     *
     * @return the unit of the rssi.
     */
    public String getUnit() {
        return unit;
    }

    /**
     * this method sets the unit of the rssi.
     *
     * @param unit the unit of the rssi.
     */
    public void setUnit(final String unit) {
        this.unit = unit;
    }
}
