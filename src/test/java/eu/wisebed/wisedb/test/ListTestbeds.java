package eu.wisebed.wisedb.test;

import eu.wisebed.wisedb.HibernateUtil;
import eu.wisebed.wisedb.controller.LinkCapabilityController;
import eu.wisebed.wisedb.controller.LinkController;
import eu.wisebed.wisedb.controller.NodeCapabilityController;
import eu.wisebed.wisedb.controller.NodeController;
import eu.wisebed.wisedb.controller.TestbedController;
import eu.wisebed.wisedb.model.Link;
import eu.wisebed.wisedb.model.Node;
import eu.wisebed.wisedb.model.Setup;
import eu.wisebed.wisedb.model.Testbed;
import eu.wisebed.wisedb.model.Capability;
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
            TestbedController.getInstance().countNodes();
            TestbedController.getInstance().countLinks();
            TestbedController.getInstance().countSlses();
            LOGGER.info("Testbeds: " + testbeds.size());
            for (final Testbed testbed : testbeds) {
                LOGGER.info("\t" + testbed.getName() + ",description: " + testbed.getDescription());
                final Setup setup = testbed.getSetup();
                LOGGER.info("\t\t" + setup.getId() + ", description: " + setup.getDescription());

                final List<Node> nodes = NodeController.getInstance().list(setup);
                final List<Link> links = LinkController.getInstance().list(setup);

                for (Node node : nodes) {
                    LOGGER.info("\t\t\t" + node.getId() + " desc: " + NodeController.getInstance().getDescription(node));
                    List<Capability> nodeCapabilities= NodeCapabilityController.getInstance().list(node);
                    LOGGER.info("\t\t\t\t" + node.getId() + " " + nodeCapabilities.size() + " nodeCaps");
                }
                LOGGER.info("\t\t\t" + nodes.size() + " Nodes, " + nodes.size() + " Links");

                for (Link link : links) {
                    List<Capability> linkCapabilities = LinkCapabilityController.getInstance().list(link);
                    LOGGER.info("\t\t\t\t" + link.getSource() + "--" + link.getTarget() + " " + linkCapabilities.size() + " LinkCaps");
                }


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
            e.printStackTrace();
            System.exit(-1);
        } finally {
            // always close session
            HibernateUtil.getInstance().closeSession();
        }
    }
}
