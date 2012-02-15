package eu.wisebed.wisedb.test;

import eu.wisebed.wisedb.HibernateUtil;
import eu.wisebed.wisedb.controller.LinkCapabilityController;
import eu.wisebed.wisedb.controller.NodeCapabilityController;
import eu.wisebed.wisedb.controller.TestbedController;
import eu.wisebed.wisedb.model.LinkCapability;
import eu.wisebed.wisedb.model.NodeCapability;
import eu.wisebed.wisedb.model.Testbed;
import org.apache.log4j.Logger;
import org.hibernate.Transaction;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Searches for a testbed in the database using its name.
 * Then displays all the testbed related information or an error message.
 */
public class GetTestbedStatus {

    /**
     * a log4j logger to print messages.
     */
    private static final Logger LOGGER = Logger.getLogger(GetTestbedStatus.class);


    public static void main(final String[] args) {

        // Initialize hibernate
        HibernateUtil.connectEntityManagers();
        final Transaction tx = HibernateUtil.getInstance().getSession().beginTransaction();


        try {

            // a specific testbed is requested by testbed Id
            int testbedId;

            testbedId = 1;


            // look up testbed
            final Testbed testbed = TestbedController.getInstance().getByID(3);
            LOGGER.info(testbed);
            // get a list of node last readings from testbed
            final List<NodeCapability> nodeCapabilities = NodeCapabilityController.getInstance().list(testbed);
            LOGGER.info("nodeCapabilities");
            // get a list of link statistics from testbed
            final List<LinkCapability> linkCapabilities = LinkCapabilityController.getInstance().list(testbed);
            LOGGER.info("linkCapabilities");

            // Prepare data to pass to jsp
            final Map<String, Object> refData = new HashMap<String, Object>();
            refData.put("testbed", testbed);
            refData.put("lastNodeReadings", nodeCapabilities);
            refData.put("lastLinkReadings", linkCapabilities);


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
