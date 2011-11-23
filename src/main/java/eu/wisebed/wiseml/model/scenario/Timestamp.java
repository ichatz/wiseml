package eu.wisebed.wiseml.model.scenario;

import eu.wisebed.wiseml.model.setup.Link;
import eu.wisebed.wiseml.model.setup.Node;
import eu.wisebed.wiseml.model.trace.Message;

import java.util.List;

/**
 * Timestamp class.
 */
public class Timestamp {

    /**
     * timestamp value.
     */
    private String value;

    /**
     * enables a node.
     */
    private EnableNode enableNode;

    /**
     * disables a node.
     */
    private DisableNode disableNode;

    /**
     * enables a link.
     */
    private EnableLink enableLink;

    /**
     * disables a link.
     */
    private DisableLink disableLink;

    /**
     * nodes list.
     */
    private List<Node> node;

    /**
     * messages list.
     */
    private List<Message> message;

    /**
     * nodes link.
     */
    private List<Link> link;

    /**
     * this method returns value.
     *
     * @return value
     */
    public String getValue() {
        return value;
    }

    /**
     * this method sets value.
     *
     * @param value string value.
     */
    public void setValue(final String value) {
        this.value = value;
    }

    /**
     * this method return an enabled node.
     *
     * @return enable node
     */
    public EnableNode getEnableNode() {
        return enableNode;
    }

    /**
     * this method sets an enable node previously disabled.
     *
     * @param enableNode the enabled node
     */
    public void setEnableNode(final EnableNode enableNode) {
        this.enableNode = enableNode;
    }

    /**
     * this method return an disabled node.
     *
     * @return disable node
     */
    public DisableNode getDisableNode() {
        return disableNode;
    }

    /**
     * this method sets a disable node.
     *
     * @param disableNode the disable node
     */
    public void setDisableNode(final DisableNode disableNode) {
        this.disableNode = disableNode;
    }

    /**
     * this method return an enable link.
     *
     * @return enableLink
     */
    public EnableLink getEnableLink() {
        return enableLink;
    }

    /**
     * this method sets an enable link.
     *
     * @param enableLink the enabled link
     */
    public void setEnableLink(final EnableLink enableLink) {
        this.enableLink = enableLink;
    }

    /**
     * this method return a disable link.
     *
     * @return disableLink
     */
    public DisableLink getDisableLink() {
        return disableLink;
    }

    /**
     * this method sets a disablelink.
     *
     * @param disableLink the disabled link
     */
    public void setDisableLink(final DisableLink disableLink) {
        this.disableLink = disableLink;
    }

    /**
     * this method return a node.
     *
     * @return node
     */
    public List<Node> getNode() {
        return node;
    }

    /**
     * this method sets a node.
     *
     * @param node the scenario node
     */
    public void setNode(final List<Node> node) {
        this.node = node;
    }

    /**
     * this method returns a list of message.
     *
     * @return list of message.
     */
    public List<Message> getMessage() {
        return message;
    }

    /**
     * this method sets a list of message.
     *
     * @param message List of messages
     */
    public void setMessage(final List<Message> message) {
        this.message = message;
    }

    public List<Link> getLink() {
        return link;
    }

    public void setLink(final List<Link> link) {
        this.link = link;
    }
}


