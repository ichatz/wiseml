package eu.wisebed.wisedb.test;

import eu.wisebed.wisedb.HibernateUtil;
import eu.wisebed.wisedb.controller.NodeController;
import eu.wisebed.wisedb.controller.NodeReadingController;
import eu.wisebed.wisedb.model.NodeReading;
import eu.wisebed.wiseml.model.setup.Capability;
import eu.wisebed.wiseml.model.setup.Node;

import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;

/**
 * Adds a node reading
 */
public class AddNodeReading {

     /**
     * a log4j logger to print messages.
     */
    private static final Logger LOGGER = org.apache.log4j.Logger.getLogger(ImportSetupEntries.class);

    public static void main(String args[]){

        // Initialize hibernate
        HibernateUtil.connectEntityManagers();

        List<Node> nodes = NodeController.getInstance().list();
        Node node = nodes.iterator().next();
        LOGGER.debug("Selected node : " +  node.getId());
        List<Capability> capabilities = node.getCapabilities();
        Capability capability = capabilities.iterator().next();
        LOGGER.debug("Selected Capability : " + capability.getName());
        NodeReading reading = new NodeReading();
        reading.setNode(node);
        reading.setCapability(capability);
        reading.setReading(10.0);
        reading.setTimestamp((long)100);
        NodeReadingController.getInstance().add(reading);
        Set<NodeReading> readingsFromNode = node.getReadings();
        LOGGER.debug("readingsFromNode size() : " + readingsFromNode.size());
        LOGGER.debug(readingsFromNode.iterator().next().getReading());
    }
}
