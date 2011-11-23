package eu.wisebed.wiseml.model.setup;

import java.io.Serializable;

/**
 * This is a persistant class for the object defaults that has the
 * properties of defaults. In the class there are
 * getter and setter methods for the properties.
 */
public final class Defaults implements Serializable {

    /**
     * Serial Unique Version ID.
     */
    private static final long serialVersionUID = -4369100001687471041L;

    /**
     * defaults for object Node.
     */
    private Node node;

    /**
     * defaults for object Link.
     */
    private Link link;

    /**
     * this method returns the defaults for node.
     *
     * @return defaults.
     */
    public Node getNode() {
        return node;
    }

    /**
     * this method sets defaults for node.
     *
     * @param node nodes defaults.
     */
    public void setNode(final Node node) {
        this.node = node;
    }

    /**
     * this method returns the defaults for link.
     *
     * @return defaults.
     */
    public Link getLink() {
        return link;
    }

    /**
     * this method sets defaults for link.
     *
     * @param link links defaults.
     */
    public void setLink(final Link link) {
        this.link = link;
    }
}
