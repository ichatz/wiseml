package wiseml.model.setup;

/**
 * This is a persistant class for the object rssi that has the
 * properties of a rssi. In the class there are
 * getter and setter methods for the properties.
 */

public class Rssi {

    /**
     * the datatype of the object Rssi.
     */
    private String datatype;

    /**
     * the unit of the object Rssi.
     */
    private String unit;

    /**
     * this method returns the datatype of the rssi.
     *
     * @return the datatype of the rssi.
     */
    public String getDatatype() {
        return datatype;
    }

    /**
     * this method sets the datatype of the rssi.
     *
     * @param datatype the datatype of the rssi.
     */
    public void setDatatype(String datatype) {
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
    public void setUnit(String unit) {
        this.unit = unit;
    }
}
