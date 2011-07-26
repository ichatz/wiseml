package eu.wisebed.wisedb.test;

import eu.wisebed.wisedb.HibernateUtil;
import eu.wisebed.wisedb.controller.CapabilityController;
import eu.wisebed.wisedb.controller.NodeController;
import eu.wisebed.wisedb.controller.NodeReadingController;

import java.util.*;

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

    @SuppressWarnings({"deprecation"})
    public static void main(String args[]){
        try{
            // Initialize hibernate
            HibernateUtil.connectEntityManagers();

            // a valid node id for cti's testbed
            final String nodeId = "urn:wisebed:ctitestbed:0x995d";

            // get that nodes capability name
            final String capabilityName = "urn:wisebed:node:capability:temperature";

            // reading value
            final double readingValue = 10.0;

            // Occured time
            final Date timestamp = new Date();

            LOGGER.debug("Node : " + nodeId);
            LOGGER.debug("Capability : " + capabilityName);
            LOGGER.debug("Reading : " + readingValue);
            LOGGER.debug("Timestamp : " + timestamp.toGMTString());

            // insert reading
            NodeReadingController.getInstance().insertReading(nodeId,capabilityName,readingValue,timestamp);

            // check to see if reading was set correctly
            // NodeReadings table size
            LOGGER.debug("There are " + NodeReadingController.getInstance().list().size() +" node readings in the database ");

            // Node's readings size
            LOGGER.debug("Node " + nodeId + " has " + NodeController.getInstance().getByID(nodeId).getReadings().size()
                    + " readings");

            // Capabilities's readings size
            LOGGER.debug("Capability " + capabilityName + " appears in " + CapabilityController.getInstance()
                    .getByID(capabilityName).getNodeReadings().size()+  " readings");
        }catch(Exception e){
            LOGGER.fatal(e.getMessage());
            System.exit(-1);
        }finally {
            // always close session
            HibernateUtil.getInstance().closeSession();
        }
    }
}
