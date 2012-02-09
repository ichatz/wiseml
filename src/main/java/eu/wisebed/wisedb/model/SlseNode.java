package eu.wisebed.wisedb.model;

import eu.wisebed.wiseml.model.setup.Node;
import org.hibernate.annotations.Entity;

import javax.persistence.Table;
import java.io.Serializable;

/**
 * This is a persistent class for the object NodeReading that has the
 * properties of a wisedb entry. In the class there are
 * getter and setter methods for the properties.
 */
@Entity
@Table(name="slse_nodes")
public final class SlseNode implements Serializable {


    private static final long serialVersionUID = 3666836684859671319L;

    public Node getNode() {
        return node;
    }

    public void setNode(Node node) {
        this.node = node;
    }

    public Slse getSlse() {
        return slse;
    }

    public void setSlse(Slse slse) {
        this.slse = slse;
    }

    /**
     * Reading id.
     */
    private Slse slse;

    /**
     * Node reference.
     */
    private Node node;
}
