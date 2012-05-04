package eu.wisebed.wiseconfig.model;

import java.util.ArrayList;
import java.util.List;

/**
 * This is a persistent class for the object TestbedConfiguration that has the
 * properties of a testbed xml file. In the class there are
 * getter and setter methods for the properties.
 */
public final class TestbedConfiguration {

    /**
     * List of nodes of the testbed.
     */
    private List<Nodes> nodes;

    /**
     * This method returns the list of nodes of the testbed.
     *
     * @return nodes of the testbed configuration
     */
    public List<Nodes> getNodes() {
        return nodes;
    }

    /**
     * this method sets the list of nodes of the testbed.
     *
     * @param nds of the testbed configuration
     */
    public void setNodes(final List<Nodes> nds) {
        this.nodes = nds;
    }

    /**
     * This method add a node in the configuration.
     *
     * @param nodes of the testbed configuration.
     */
    public void addNodes(final Nodes nodes) {
        if (this.nodes == null) {
            this.nodes = new ArrayList<Nodes>();
        }
        this.findUpdateNode(nodes);
    }

    private void findUpdateNode(final Nodes nodes) {
        if (this.nodes == null) return;
        boolean updated = false;
        for (Object nd : this.nodes.toArray()) {
            if (((Nodes) nd).getId().equals(nodes.getId()) == true) {
                this.nodes.remove(nd);
                this.nodes.add(nodes);
                updated = true;
                break;
            }
        }
        if (updated == false) {
            this.nodes.add(nodes);
        }
    }

    public void deleteNodes(final String node_id) {
        if (this.nodes == null) return;
        for (Object nd : this.nodes.toArray()) {
            if (((Nodes) nd).getId().equals(node_id) == true) {
                this.nodes.remove(nd);
                break;
            }
        }
    }
}
