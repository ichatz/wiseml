package eu.wisebed.wisedb.test;

import eu.wisebed.wisedb.HibernateUtil;
import eu.wisebed.wisedb.controller.NodeReadingController;
import org.apache.log4j.Logger;
import org.hibernate.Transaction;

import java.util.Date;

/**
 * Adds a node reading
 */
public class AddNodeReading {

    /**
     * a log4j logger to print messages.
     */
    private static final Logger LOGGER = org.apache.log4j.Logger.getLogger(AddNodeReading.class);

    @SuppressWarnings({"deprecation"})
    public static void main(String args[]) {

        // Initialize hibernate
        HibernateUtil.connectEntityManagers();
        Transaction tx = HibernateUtil.getInstance().getSession().beginTransaction();
        try {

            // a valid urnPrefix for CTI's testbed
            final String urnPrefix = "urn:wisebed:ctitestbed:";

            // valid id.
            final int testbedId = 1;

            // a valid node id for cti's testbed
            final String nodeId = urnPrefix + "NODETEST1";

            // get that nodes capability name
            final String capabilityName = urnPrefix + "CAPATEST";

            // reading value
            final double readingValue = -1337.0;

            // Occured time
            final Date timestamp = new Date(10);

            LOGGER.debug("Node : " + nodeId);
            LOGGER.debug("Capability : " + capabilityName);
            LOGGER.debug("Reading : " + readingValue);
            LOGGER.debug("Timestamp : " + timestamp.toGMTString());


            // insert reading
            NodeReadingController.getInstance().insertReading(nodeId, capabilityName, testbedId, readingValue,null, timestamp);

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            LOGGER.fatal(e);
            System.exit(-1);
        } finally {
            // always close session
            HibernateUtil.getInstance().closeSession();
        }
    }
}
