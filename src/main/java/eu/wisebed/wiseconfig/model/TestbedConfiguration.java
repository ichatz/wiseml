package eu.wisebed.wiseconfig.model;

import java.util.ArrayList;
import java.util.List;

/**
 * This is a persistent class for the object TestbedConfiguration that has the
 * properties of a TestbedConfiguration xml file. In the class there are
 * getter and setter methods for the properties.
 */
public class TestbedConfiguration {

    /**
     * list of nodes of the testbed
     */
    private List<Nodes> nodes;

    /**
     * this method returns the list of nodes of the testbed
     *
     * @return nodes of the testbed configuration
     */
    public final List<Nodes> getNodes() {
        return nodes;
    }

    /**
     * this method sets the list of nodes of the testbed
     *
     * @param nds of the testbed configuration
     */
    public final void setNodes(final List<Nodes> nds) {
        this.nodes = nds;
    }

    /**
     * this method add a node in the configuration
     *
     * @param nodes of the testbed configuration
     */

    public final void addNodes(final Nodes nodes) {
        if (this.nodes == null) {
            this.nodes = new ArrayList<Nodes>();
        }
        this.nodes.add(nodes);
    }
}
