package eu.wisebed.wisedb.test;

import eu.wisebed.wisedb.HibernateUtil;
import eu.wisebed.wisedb.controller.NodeController;
import eu.wisebed.wisedb.controller.NodeReadingController;
import eu.wisebed.wisedb.controller.TestbedController;
import eu.wisebed.wisedb.model.NodeReadingStat;
import eu.wisebed.wisedb.model.Testbed;
import eu.wisebed.wiseml.model.setup.Node;
import org.apache.log4j.Logger;
import org.hibernate.Transaction;

import java.util.List;

public class TestHqlReadings {
    /**
     * a log4j logger to print messages.
     */
    private static final Logger LOGGER = org.apache.log4j.Logger.getLogger(TestHqlReadings.class);


    public static void main(String[] args) {

        // Initialize hibernate
        HibernateUtil.connectEntityManagers();
        Transaction tx = HibernateUtil.getInstance().getSession().beginTransaction();

        try {


            // get from first node
            final Node node = NodeController.getInstance().list().iterator().next();
            LOGGER.info("Node [" + node.getId() + "] (1)");
            final NodeReadingStat update = NodeReadingController.getInstance().getNodeReadingStats(node);
            LOGGER.info(update.toString());

            // get from all cti's nodes
            final Testbed testbed = TestbedController.getInstance().getByUrnPrefix("urn:wisebed:ctitestbed:");
            LOGGER.info("Testbed [" + testbed.getId() + "] (" + testbed.getSetup().getId() + "," + testbed.getSetup().getNodes().size() + ")");
            final List<NodeReadingStat> updates = NodeReadingController.getInstance().getNodeReadingStats(testbed);
            for (NodeReadingStat up1 : updates) {
                LOGGER.info(up1.toString());
            }

            // get from all the nodes
            LOGGER.info("All nodes");
            final List<NodeReadingStat> updates1 = NodeReadingController.getInstance().
                    getNodeReadingStats();
            for (NodeReadingStat up1 : updates1) {
                LOGGER.info(up1.toString());
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
