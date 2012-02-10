package eu.wisebed.wisedb.model;

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
    @Id
    @GeneratedValue
    @Column(name = "setup_id", unique = true, nullable = false)
    private int id;

    /**
     * the origin of node.
     */
    @Embedded
    private Origin origin;

    /**
     * the information of time used in the experiment.
     */
    @Embedded
    private TimeInfo timeinfo;

    /**
     * the description of the experiment.
     */
    @Column(name = "description", unique = true, nullable = false)
    @Basic(fetch = FetchType.LAZY)
    private String description;

    /**
     * the type of the coordinate system.
     */
    @Column(name = "coordinateType", unique = true, nullable = false)
    @Basic(fetch = FetchType.LAZY)
    private String coordinateType;

    private Testbed testbed;

    public Testbed getTestbed() {
        return testbed;
    }

    public void setTestbed(Testbed testbed) {
        this.testbed = testbed;
    }
    /**
     * a list of objects Node.
     */
//    private List<Node> nodes;

    /**
     * information about links.
     */
//    private List<Link> link;

    /**
     * this method returns the id of the setup.
     *
     * @return the id of the setup.
     */
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
     * this method returns the description of the experiment.
     *
     * @return the description
     */
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
}
