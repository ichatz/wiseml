package eu.wisebed.wisedb.model;

import org.hibernate.annotations.Entity;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * This is a persistant class for the object node that has the
 * properties of a node. In the class there are
 * getter and setter methods for the properties.
 */
@Entity
@Table(name = "nodes")
public class Node implements Serializable {

    /**
     * Serial Version Unique ID.
     */
    private static final long serialVersionUID = -5422125775653598399L;

    /**
     * the id of an object Node.
     */
    @Id
    @Column(name = "node_id")
    @Basic(fetch = FetchType.LAZY)
    private String id;

//    /**
//     * information about the type of the device.
//     */
//    @Column(name = "nodeType")
//    @Basic(fetch = FetchType.LAZY)
//    private String nodeType;
//
//    /**
//     * the description of an object node.
//     */
//    @Column(name = "description")
//    @Basic(fetch = FetchType.LAZY)
//    private String description;
//
//    /**
//     * the gateway of an object node.
//     */
//    @Column(name = "gateway")
//    @Basic(fetch = FetchType.LAZY)
//    private String gateway;
//
//    /**
//     * information describing the software image loaded on the node.
//     */
//    @Column(name = "programDetails")
//    @Basic(fetch = FetchType.LAZY)
//    private String programDetails;


    /**
     * this node belongs to a setup.
     */
    @ManyToOne(targetEntity = Setup.class)
    @Column(name = "setup_id")
    @Basic(fetch = FetchType.LAZY)
    private Setup setup;

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
    public void setId(final String id) {
        this.id = id;
    }

//    /**
//     * this method returns the nodetype of a node.
//     *
//     * @return the nodetype of the node.
//     */
//    public String getNodeType() {
//        return nodeType;
//    }
//
//    /**
//     * this method sets the nodetype of a node.
//     *
//     * @param nodeType the nodetype of the node.
//     */
//    public void setNodeType(final String nodeType) {
//        this.nodeType = nodeType;
//    }
//
//    /**
//     * this method returns the program details of a node.
//     *
//     * @return the program details of the node.
//     */
//    public String getProgramDetails() {
//        return programDetails;
//    }
//
//    /**
//     * this method sets the program details of a node.
//     *
//     * @param programDetails the program details of the node.
//     */
//    public void setProgramDetails(final String programDetails) {
//        this.programDetails = programDetails;
//    }
//
//    /**
//     * this method returns the description of a node.
//     *
//     * @return the description of the node.
//     */
//    public String getDescription() {
//        return description;
//    }
//
//    /**
//     * this method sets the description of a node.
//     *
//     * @param description the description of the node.
//     */
//    public void setDescription(final String description) {
//        this.description = description;
//    }
//
//    /**
//     * this method returns the gateway of a node.
//     *
//     * @return gateway the gateway of the node.
//     */
//    public String getGateway() {
//        return gateway;
//    }
//
//    /**
//     * this method sets the gateway of a node.
//     *
//     * @param gateway the gateway of the node.
//     */
//    public void setGateway(final String gateway) {
//        this.gateway = gateway;
//    }


    /**
     * returns the setup this node belongs to.
     *
     * @return the setup this node belongs to.
     */
    public Setup getSetup() {
        return setup;
    }

    /**
     * sets the setup this node belongs to.
     *
     * @param setup setup instance.
     */
    public void setSetup(final Setup setup) {
        this.setup = setup;
    }

}
