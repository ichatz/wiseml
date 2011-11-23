package eu.wisebed.wiseml.model.setup;

import eu.wisebed.wisedb.model.LinkReading;
import eu.wisebed.wisedb.model.NodeReading;

import java.io.Serializable;
import java.util.Set;

/**
 * This is a persistant class for the object capability that has the
 * properties of a capability. In the class there are
 * getter and setter methods for the properties.
 */
public class Capability implements Serializable {

    /**
     * Serial Unique Version ID.
     */
    private static final long serialVersionUID = -3419203591130581062L;

    /**
     * the name of the object Capability.
     */
    private String name;

    /**
     * the datatype of the capability.
     */
    private String datatype;

    /**
     * the unit of the capability.
     */
    private String unit;

    /**
     * the unit of the capability.
     */
    private String defaultvalue;

    /**
     * set of nodes that have this capability.
     */
    private Set<Node> nodes;

    /**
     * set of links that have this capability.
     */
    private Set<Link> links;

    /**
     * Set of node readings for this capability.
     */
    private Set<NodeReading> nodeReadings;

    /**
     * Set of link readings for this capability.
     */
    private Set<LinkReading> linkReadings;

    /**
     * this method returns the name of the capability.
     *
     * @return the name of the capability.
     */
    public String getName() {
        return name;
    }

    /**
     * this method sets the name of the capability.
     *
     * @param name the name of the capability.
     */
    public void setName(final String name) {
        this.name = name;
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
     * @return default value.
     */
    public String getDefaultvalue() {
        return defaultvalue;
    }

    /**
     * Sets default value.
     * @param defaultvalue default value
     */
    public void setDefaultvalue(final String defaultvalue) {
        this.defaultvalue = defaultvalue;
    }

    /**
     * Returns nodes that have this capability.
     *
     * @return a node collection.
     */
    public Set<Node> getNodes() {
        return nodes;
    }

    /**
     * Sets the node collection that has this capability.
     *
     * @param nodes a node collection.
     */
    public void setNodes(final Set<Node> nodes) {
        this.nodes = nodes;
    }

    /**
     * Returns links that have this capability.
     *
     * @return a link collection.
     */
    public Set<Link> getLinks() {
        return links;
    }

    /**
     * Sets the link collection that has this capability.
     *
     * @param links a link collection.
     */
    public void setLinks(final Set<Link> links) {
        this.links = links;
    }

    /**
     * Returns the set of node readings for this capability.
     *
     * @return nodeReadings
     */
    public Set<NodeReading> getNodeReadings() {
        return nodeReadings;
    }

    /**
     * Sets the set of node readings for this capability.
     *
     * @param nodeReadings , set of node readings
     */
    public void setNodeReadings(final Set<NodeReading> nodeReadings) {
        this.nodeReadings = nodeReadings;
    }

    /**
     * Returns the set of link readings for this capability.
     *
     * @return linkReadings , set of link readings.
     */
    public Set<LinkReading> getLinkReadings() {
        return linkReadings;
    }

    /**
     * Returns the set of link readings for this capability.
     *
     * @param linkReadings , set of link readings.
     */
    public void setLinkReadings(final Set<LinkReading> linkReadings) {
        this.linkReadings = linkReadings;
    }

    /**
     * Override of equals().
     *
     * @param obj , an object
     * @return true if this is equal to obj
     */
    @Override
    public boolean equals(final Object obj) {

        // if null return false
        if (obj == null) {
            return false;
        }

        if (!(obj instanceof Capability)) {
            return false;
        }

        // if same reference return true;
        if (this == obj) {
            return true;
        }

        // equility against name
        final Capability test = (Capability) obj;
        return (test.name == null || test.getName() == null) ? (false) : (this.name.equals(test.getName()));
    }

    /**
     * Override of hashCode().
     *
     * @return identity hashcode
     */
    @Override
    public int hashCode() {
        return (name == null) ? (System.identityHashCode(this)) : (name.hashCode());
    }
}
