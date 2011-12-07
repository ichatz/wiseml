package eu.wisebed.wiseml.model.setup;

import eu.wisebed.wisedb.model.Testbed;

import java.io.Serializable;
import java.util.List;

/**
 * This is a persistant class for the object setup that has the
 * properties of a setup. In the class there are
 * getter and setter methods for the properties.
 */

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
    private List<Node> nodes;

    /**
     * information about links.
     */
    private List<Link> link;

    /**
     * The testbed this setup belongs to.
     */
    private Testbed testbed;


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
     * this method returns a list of nodes.
     *
     * @return the nodes list
     */
    public List<Node> getNodes() {
        return nodes;
    }

    /**
     * this method sets a list of nodes.
     *
     * @param nodes the nodes list
     */
    public void setNodes(final List<Node> nodes) {
        this.nodes = nodes;
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

    /**
     * this method returns a list of informations about links.
     *
     * @return the links list
     */
    public List<Link> getLink() {
        return link;
    }

    /**
     * this method sets the list of links.
     *
     * @param link the links list information
     */
    public void setLink(final List<Link> link) {
        this.link = link;
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

    /**
     * Override of toString().
     * @return toString().
     */
    @Override
    public String toString() {
        return "Setup{" +
                "id=" + id +
                ", origin=" + origin +
                ", timeinfo=" + timeinfo +
                ", interpolation='" + interpolation + '\'' +
                ", description='" + description + '\'' +
                ", coordinateType='" + coordinateType + '\'' +
                ", defaults=" + defaults +
                ", nodes=" + nodes +
                ", link=" + link +
                ", testbed=" + testbed +
                '}';
    }
}
