package eu.wisebed.wisedb.model;

import org.hibernate.annotations.Entity;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * This is a persistant class for the object capability that has the
 * properties of a capability. In the class there are
 * getter and setter methods for the properties.
 */
@Entity
@Table(name = "node_capabilities")
public class NodeCapability implements Serializable {

    /**
     * Serial Unique Version ID.
     */
    private static final long serialVersionUID = -3419203591130581062L;

    @Id
    @Column(name = "id", nullable = false)
    @Basic(fetch = FetchType.LAZY)
    private int id;

    /**
     * the name of the object Capability.
     */
    @Column(name = "capability_id")
    @Basic(fetch = FetchType.LAZY)
    private String capabilityId;
    /**
     * the name of the object node.
     */
    @Column(name = "node_id")
    @ManyToOne(targetEntity = Node.class)
    @Basic(fetch = FetchType.LAZY)
    private String node;

    /**
     * the datatype of the capability.
     */
    @Column(name = "datatype")
    @Basic(fetch = FetchType.LAZY)
    private String datatype;

    /**
     * the unit of the capability.
     */
    @Column(name = "unit")
    @Basic(fetch = FetchType.LAZY)
    private String unit;

    /**
     * the unit of the capability.
     */
    @Column(name = "defaultvalue")
    @Basic(fetch = FetchType.LAZY)
    private String defaultvalue;

    /**
     * Description.
     */
    @Column(name = "description")
    @Basic(fetch = FetchType.LAZY)
    private String description;

    /**
     * this method returns the name of the capability.
     *
     * @return the name of the capability.
     */
    public String getCapabilityId() {
        return capabilityId;
    }

    /**
     * this method sets the name of the capability.
     *
     * @param capabilityId the name of the capability.
     */
    public void setCapabilityId(final String capabilityId) {
        this.capabilityId = capabilityId;
    }

    /**
     * this method returns the datatype of the capability.
     *
     * @return the datatype of the capability.
     */
    public String getDatatype() {
        return datatype;
    }

    /**
     * this method sets the datatype of the capability.
     *
     * @param datatype the datatype of the capability.
     */
    public void setDatatype(final String datatype) {
        this.datatype = datatype;
    }

    /**
     * this method returns the unit of the capability.
     *
     * @return the unit of the capability.
     */
    public String getUnit() {
        return unit;
    }

    /**
     * this method sets the unit of the capability.
     *
     * @param unit the unit of the capability.
     */
    public void setUnit(final String unit) {
        this.unit = unit;
    }

    /**
     * Returns default value.
     *
     * @return default value.
     */
    public String getDefaultvalue() {
        return defaultvalue;
    }

    /**
     * Sets default value.
     *
     * @param defaultvalue default value
     */
    public void setDefaultvalue(final String defaultvalue) {
        this.defaultvalue = defaultvalue;
    }


    /**
     * Returns this capability's description.
     *
     * @return this capability's description.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Set this capability's description.
     *
     * @param description description.
     */
    public void setDescription(final String description) {
        this.description = description;
    }

}
