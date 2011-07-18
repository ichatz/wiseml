package eu.wisebed.wiseml.model.setup;

import java.util.Set;

/**
 * This is a persistant class for the object capability that has the
 * properties of a capability. In the class there are
 * getter and setter methods for the properties.
 */

public class Capability {

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
    public void setName(String name) {
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
    public void setDatatype(String datatype) {
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
    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getDefaultvalue() {
        return defaultvalue;
    }

    public void setDefaultvalue(String defaultvalue) {
        this.defaultvalue = defaultvalue;
    }

    /**
     * Returns nodes that have this capability.
     * @return a node collection.
     */
    public Set<Node> getNodes() {
        return nodes;
    }

    /**
     * Sets the node collection that has this capability.
     * @param nodes, a node collection.
     */
    public void setNodes(final Set<Node> nodes) {
        this.nodes = nodes;
    }

    /**
     * Returns links that have this capability.
     * @return a link collection.
     */
    public Set<Link> getLinks() {
        return links;
    }

    /**
     * Sets the link collection that has this capability.
     * @param links, a link collection.
     */
    public void setLinks(final Set<Link> links) {
        this.links = links;
    }
}
