package eu.wisebed.wisedb.test;

import eu.wisebed.wisedb.HibernateUtil;
import eu.wisebed.wisedb.controller.CapabilityController;
import eu.wisebed.wisedb.controller.LinkCapabilityController;
import eu.wisebed.wisedb.controller.NodeCapabilityController;
import eu.wisebed.wisedb.controller.TestbedController;
import eu.wisebed.wisedb.model.Capability;
import eu.wisebed.wisedb.model.LinkCapability;
import eu.wisebed.wisedb.model.NodeCapability;
import eu.wisebed.wisedb.model.Testbed;
import org.apache.log4j.Logger;
import org.hibernate.Transaction;

import java.util.List;

public class ListTestbedCapabilities {

    /**
     * a log4j logger to print messages.
     */
    private static final Logger LOGGER = org.apache.log4j.Logger.getLogger(ListTestbedCapabilities.class);


    public static void main(String[] args) {

        // Initialize hibernate
        HibernateUtil.connectEntityManagers();
        Transaction tx = HibernateUtil.getInstance().getSession().beginTransaction();

        try {
            {
                long start = System.currentTimeMillis();
                final Testbed testbed = TestbedController.getInstance().getByID(1);
                LOGGER.info("testbed @ " + (System.currentTimeMillis() - start));
                final List<Capability> capabilities = CapabilityController.getInstance().list(testbed);
                LOGGER.info("capabilities @ " + (System.currentTimeMillis() - start));
            }
            {
                long start = System.currentTimeMillis();
                final Testbed testbed = TestbedController.getInstance().getByID(1);
                LOGGER.info("testbed @ " + (System.currentTimeMillis() - start));
                final List<NodeCapability> nodeCapabilities = NodeCapabilityController.getInstance().list(testbed);
                LOGGER.info("nodeCapabilities @ " + (System.currentTimeMillis() - start));
                final List<LinkCapability> linkCapabilities = LinkCapabilityController.getInstance().list(testbed);
                LOGGER.info("linkCapabilities @ " + (System.currentTimeMillis() - start));
            }

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
