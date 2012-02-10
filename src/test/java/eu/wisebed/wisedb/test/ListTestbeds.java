package eu.wisebed.wisedb.test;

import eu.wisebed.wisedb.HibernateUtil;
import eu.wisebed.wisedb.controller.LinkController;
import eu.wisebed.wisedb.controller.NodeController;
import eu.wisebed.wisedb.controller.TestbedController;
import eu.wisebed.wisedb.model.Link;
import eu.wisebed.wisedb.model.Node;
import eu.wisebed.wisedb.model.Setup;
import eu.wisebed.wisedb.model.Testbed;
import org.apache.log4j.Logger;
import org.hibernate.Transaction;

import java.util.List;

public class ListTestbeds {

    /**
     * a log4j logger to print messages.
     */
    private static final Logger LOGGER = Logger.getLogger(ListTestbeds.class);


    public static void main(String[] args) {

        // Initialize hibernate
        HibernateUtil.connectEntityManagers();
        Transaction tx = HibernateUtil.getInstance().getSession().beginTransaction();

        try {
            final List<Testbed> testbeds = TestbedController.getInstance().list();
            LOGGER.info("Total Testbeds: " + testbeds.size());
            for (final Testbed testbed : testbeds) {
                LOGGER.info("Testbed: " + testbed.getName() + ",description: " + testbed.getDescription());
                final Setup setup = testbed.getSetup();
                LOGGER.info("Setup: " + setup.getId() + ", description: " + setup.getDescription());

                final List<Node> nodes = NodeController.getInstance().list(setup);
                for (Node node : nodes) {
                    LOGGER.info("Node : " + node.getId() + ", Desc :" + NodeController.getInstance().getDescription(node));
                }

                final List<Link> links = LinkController.getInstance().list(setup);
                LOGGER.info("Links : " + nodes.size() + ", Links : " + nodes.size());


            }

//
//            List<Setup> setups = SetupController.getInstance().list();
//            LOGGER.info("setup count :" + setups.size());
//            for (final Setup setup : setups) {
//                LOGGER.info("Setup: " + setup.getId() + ", " + setup.getTestbed().getName() + "," + setup.getOrigin().getX());
//            }


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
