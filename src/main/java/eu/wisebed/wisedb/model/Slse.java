package eu.wisebed.wisedb.model;


import eu.wisebed.wiseml.model.setup.Node;
import eu.wisebed.wiseml.model.setup.Setup;
import org.hibernate.annotations.Entity;

import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: amaxilatis
 * Date: 1/19/12
 * Time: 3:08 PM
 */
@Entity
@Table(name="slses")
public final class Slse implements Serializable {


    private static final long serialVersionUID = -288407547607520275L;

    /**
     * Name of the slse.
     */
    private String name;
    /**
     * Set of Setups belonging in Testbed.
     */
    private Setup setup;
    /**
     * Creation Timestamp.
     */
    private Date creationTime;
    /**
     * Expication Timestamp.
     */

    private Date expirationTime;

    private List<Node> nodes;

    /**
     * getName of slse.
     */
    public String getName() {
        return name;
    }

    /**
     * setName of slse.
     *
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }


    public Setup getSetup() {
        return setup;
    }

    public void setSetup(Setup setup) {
        this.setup = setup;
    }


    public Date getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(Date creationTime) {
        this.creationTime = creationTime;
    }


    public Date getExpirationTime() {
        return expirationTime;
    }

    public void setExpirationTime(Date expirationTime) {
        this.expirationTime = expirationTime;
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
}
