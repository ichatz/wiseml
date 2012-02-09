package eu.wisebed.wisedb.model;

import eu.wisebed.wiseml.model.setup.Defaults;
import org.hibernate.annotations.Entity;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * This is a persistant class for the object setup that has the
 * properties of a setup. In the class there are
 * getter and setter methods for the properties.
 */
@Entity
@Table(name = "setup")
public class Setup implements Serializable {

    /**
     * Serial Version Unique ID.
     */
    private static final long serialVersionUID = -8742610512262219055L;

    /**
     * id of setup.
     */
    private int id;

    /**
     * the origin of node.
     */
    private Origin origin;

    /**
     * the information of time used in the experiment.
     */
    private TimeInfo timeinfo;

    /**
     * the type of interpolation that used for description of mobility.
     */
    private String interpolation;

    /**
     * the description of the experiment.
     */
    private String description;

    /**
     * the type of the coordinate system.
     */
    private String coordinateType;

    /**
     * defaults for object Wiseml.
     */
    private Defaults defaults;

    /**
     * a list of objects Node.
     */
//    private List<Node> nodes;

    /**
     * information about links.
     */
//    private List<Link> link;

    /**
     * The testbed this setup belongs to.
     */
    private Testbed testbed;


    /**
     * this method returns the id of the setup.
     *
     * @return the id of the setup.
     */
    @Id
    @GeneratedValue
    @Column(name = "setup_id", unique = true, nullable = false)
    @Basic(fetch = FetchType.LAZY)
    public int getId() {
        return id;
    }

    /**
     * this method sets the id of the setup.
     *
     * @param id the id.
     */
    public void setId(final int id) {
        this.id = id;
    }

    /**
     * this method returns the origin of nodes.
     *
     * @return the nodes origin
     */
    @Embedded
    public Origin getOrigin() {
        return origin;
    }

    /**
     * this method sets the origin of nodes.
     *
     * @param origin the nodes origin
     */
    public void setOrigin(final Origin origin) {
        this.origin = origin;
    }

    /**
     * this method returns the information of time used in the experiment.
     *
     * @return the information time
     */
    @Embedded
    public TimeInfo getTimeinfo() {
        return timeinfo;
    }

    /**
     * this method sets the information of time used in the experiment.
     *
     * @param timeinfo the time information
     */
    public void setTimeinfo(final TimeInfo timeinfo) {
        this.timeinfo = timeinfo;
    }

    /**
     * this method returns the interpolation that used for description of mobility.
     *
     * @return the interpolation
     */
    public String getInterpolation() {
        return interpolation;
    }

    /**
     * this method sets the interpolation that used for description of mobility.
     *
     * @param interpolation the mobility interpolation
     */
    public void setInterpolation(final String interpolation) {
        this.interpolation = interpolation;
    }

    /**
     * this method returns the description of the experiment.
     *
     * @return the description
     */
    @Column(name = "description", unique = true, nullable = false)
    @Basic(fetch = FetchType.LAZY)
    public String getDescription() {
        return description;
    }

    /**
     * this method sets the description of the experiment.
     *
     * @param description the experiment description
     */
    public void setDescription(final String description) {
        this.description = description;
    }

    /**
     * this method returns the coordinate type used for describing the position of the nodes.
     *
     * @return the coordinate type
     */
    @Column(name = "coordinateType", unique = true, nullable = false)
    @Basic(fetch = FetchType.LAZY)
    public String getCoordinateType() {
        return coordinateType;
    }

    /**
     * this method sets the coordinate type used for describing the position of the nodes.
     *
     * @param description the coordinate type
     */
    public void setCoordinateType(final String description) {
        this.coordinateType = description;
    }

    /**
     * this method returns the defaults of a wiseml.
     *
     * @return the defaults of the wiseml.
     */
    public Defaults getDefaults() {
        return defaults;
    }

    /**
     * this method sets defaults of a wiseml.
     *
     * @param defaults the wiseml defaults.
     */
    public void setDefaults(final Defaults defaults) {
        this.defaults = defaults;
    }

    /**
     * Sets the testbed attribute.
     *
     * @param testbed testbed instance.
     */
    public void setTestbed(final Testbed testbed) {
        this.testbed = testbed;
    }

    /**
     * Returns the testbed this setup belongs to.
     *
     * @return the testbed this setup belongs to.
     */
    public Testbed getTestbed() {
        return testbed;
    }
}
