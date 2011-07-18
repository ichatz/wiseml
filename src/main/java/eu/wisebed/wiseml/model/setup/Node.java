package eu.wisebed.wiseml.model.setup;

import eu.wisebed.wiseml.model.trace.Message;

import java.util.List;
import java.util.Set;
import java.util.HashMap;

/**
 * This is a persistant class for the object node that has the
 * properties of a node. In the class there are
 * getter and setter methods for the properties.
 */

public class Node {

    /**
     * the id of an object Node.
     */
    private String id;

    /**
     * a list of node capabilities.
     */
    private List<Capability> capabilities;

    /**
     * the positiom of an object node.
     */
    private Position position;

    /**
     * information about the type of the device.
     */
    private String nodeType;

    /**
     * the description of an object node.
     */
    private String description;

    /**
     * the gateway of an object node.
     */
    private String gateway;

    /**
     * information describing the software image loaded on the node.
     */
    private String programDetails;

    /**
     * a list of node data.
     */

    private List<Data> data;

    /**
     * node message.
     */
    private Message message;

    /**
     * this node belongs to a collection of setups
     */
    private Set<Setup> setups;

    /**
     * this method returns the id of a node.
     *
     * @return the id of the node.
     */
    public String getId() {
        return id;
    }

    /**
     * this method sets a number at the id of a node.
     *
     * @param id the id of the node.
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * this method returns the nodetype of a node.
     *
     * @return the nodetype of the node.
     */
    public String getNodeType() {
        return nodeType;
    }

    /**
     * this method sets the nodetype of a node.
     *
     * @param nodeType the nodetype of the node.
     */
    public void setNodeType(String nodeType) {
        this.nodeType = nodeType;
    }

    /**
     * this method returns the program details of a node.
     *
     * @return the program details of the node.
     */
    public String getProgramDetails() {
        return programDetails;
    }

    /**
     * this method sets the program details of a node.
     *
     * @param programDetails the program details of the node.
     */
    public void setProgramDetails(String programDetails) {
        this.programDetails = programDetails;
    }

    /**
     * this method returns the description of a node.
     *
     * @return the description of the node.
     */
    public String getDescription() {
        return description;
    }

    /**
     * this method sets the description of a node.
     *
     * @param description the description of the node.
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * this method returns the capabilities of a node.
     *
     * @return the capabilities of the node.
     */
    public List<Capability> getCapabilities() {
        return capabilities;
    }

    /**
     * this method sets the capabilities of a node.
     *
     * @param capabilities the capabilities of the node.
     */
    public void setCapabilities(List<Capability> capabilities) {
        this.capabilities = capabilities;
    }

    /**
     * this method returns the position of a node.
     *
     * @return the position of the node.
     */
    public Position getPosition() {
        return position;
    }

    /**
     * this method sets the position of a node.
     *
     * @param position the position of the node.
     */
    public void setPosition(Position position) {
        this.position = position;
    }

    /**
     * this method returns the gateway of a node.
     *
     * @return gateway the gateway of the node.
     */
    public String getGateway() {
        return gateway;
    }

    /**
     * this method sets the gateway of a node.
     *
     * @param gateway the gateway of the node.
     */
    public void setGateway(String gateway) {
        this.gateway = gateway;
    }

    /**
     * this method returns nodes data.
     *
     * @return data node data
     */
    public List<Data> getData() {
        return data;
    }

    /**
     * this method sets the data of a node.
     *
     * @param data the data of the node.
     */
    public void setData(List<Data> data) {
        this.data = data;
    }

    /**
     * this method returns the node message.
     *
     * @return message
     */
    public Message getMessage() {
        return message;
    }

    /**
     * this method sets the node message.
     *
     * @param message
     */
    public void setMessage(Message message) {
        this.message = message;
    }

    /**
     * returns a collection of setups.
     * @return
     */
    public Set<Setup> getSetups() {
        return setups;
    }

    /**
     * sets a collection of setups
     * @param setups
     */
    public void setSetups(final Set<Setup> setups) {
        this.setups = setups;
    }
}
