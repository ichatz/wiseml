package eu.wisebed.wisedb.test;

import eu.wisebed.wisedb.HibernateUtil;
import eu.wisebed.wisedb.controller.NodeController;
import eu.wisebed.wisedb.controller.NodeReadingController;
import eu.wisebed.wisedb.model.NodeReading;
import eu.wisebed.wiseml.model.setup.Capability;
import eu.wisebed.wiseml.model.setup.Node;

import java.util.Date;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.hibernate.Hibernate;

/**
 * Adds a node reading
 */
public class AddNodeReading {

    /**
     * a log4j logger to print messages.
     */
    private static final Logger LOGGER = org.apache.log4j.Logger.getLogger(AddNodeReading.class);

    public static void main(String args[]){
        try{
            // Initialize hibernate
            HibernateUtil.connectEntityManagers();

            // List all nodes. get first one
            List<Node> nodes = NodeController.getInstance().list();
            if(nodes == null || nodes.isEmpty()) return; // no nodes exit
            Node node = nodes.iterator().next();
            LOGGER.debug("Selected node : " +  node.getId());

            // List all capabilities of this node
            List<Capability> capabilities = node.getCapabilities();
            Capability capability = capabilities.iterator().next();
            LOGGER.debug("Selected Capability : " + capability.getName());

            // make a new node reading entity
            NodeReading reading = new NodeReading();
            reading.setNode(node);
            reading.setCapability(capability);
            reading.setReading(10.0);
            reading.setTimestamp(new Date());
            NodeReadingController.getInstance().add(reading);

            // check to see if reading was put
            Set<NodeReading> readingsFromNode = node.getReadings();
            LOGGER.debug("readingsFromNode size() : " + readingsFromNode.size());
            LOGGER.debug(readingsFromNode.iterator().next().getReading());
        }finally {
            // always close session
            HibernateUtil.getInstance().closeSession();
        }
    }
}
