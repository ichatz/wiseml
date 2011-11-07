package eu.wisebed.wisedb.test;

import eu.wisebed.wisedb.HibernateUtil;
import eu.wisebed.wisedb.controller.CapabilityController;
import eu.wisebed.wisedb.controller.LinkController;
import eu.wisebed.wisedb.controller.NodeController;
import eu.wisebed.wisedb.controller.TestbedController;
import eu.wisebed.wisedb.model.Testbed;
import eu.wisebed.wiseml.model.setup.Capability;
import eu.wisebed.wiseml.model.setup.Link;
import eu.wisebed.wiseml.model.setup.Node;
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
            final Testbed testbedCTI = TestbedController.getInstance().getByUrnPrefix("urn:wisebed:ctitestbed:");
            final Testbed testbedSAN = TestbedController.getInstance().getByUrnPrefix("urn:wisebed:santander:");
            final Capability capability1 = CapabilityController.getInstance().getByID("urn:wisebed:node:capability:temperature");
            final Capability capability2 = CapabilityController.getInstance().getByID("urn:wisebed:node:capability:battery");
            final Capability capability3 = CapabilityController.getInstance().getByID("status");

            // 1st test
            List<Capability> capabilities1 = CapabilityController.getInstance().list(testbedCTI);
            for (Capability capability : capabilities1) {
                LOGGER.info("CTI : " + capability.getName());
            }
            List<Capability> capabilities2 = CapabilityController.getInstance().list(testbedSAN);
            for (Capability capability : capabilities2) {
                LOGGER.info("SAN : " + capability.getName());
            }

            // 2nd test
            List<Node> nodes = NodeController.getInstance().listCapabilityNodes(capability1);
            for (Node node : nodes) {
                LOGGER.info(node.getId());
            }

            // 3rd test
            List<Node> nodes1 = NodeController.getInstance().listCapabilityNodes(capability1, testbedCTI);
            for (Node node : nodes1) {
                LOGGER.info("1.CTI " + node.getId());
            }
            List<Node> nodes2 = NodeController.getInstance().listCapabilityNodes(capability2, testbedCTI);
            for (Node node : nodes2) {
                LOGGER.info("1.SAN " + node.getId()); // shouldnt be printed
            }

            nodes1 = NodeController.getInstance().listCapabilityNodes(capability1, testbedSAN);
            for (Node node : nodes1) {
                LOGGER.info("2.CTI " + node.getId());// shouldnt be printed
            }
            nodes2 = NodeController.getInstance().listCapabilityNodes(capability2, testbedSAN);
            for (Node node : nodes2) {
                LOGGER.info("2.SAN " + node.getId());
            }

            //4th test
            List<Link> links = LinkController.getInstance().listCapabilityLinks(capability3, testbedCTI);
            for (Link link : links) {
                LOGGER.info(link.getSource() + " -> " + link.getTarget());
            }
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            e.printStackTrace();
            LOGGER.fatal(e);
            System.exit(-1);
        } finally {
            // always close session
            HibernateUtil.getInstance().closeSession();
        }
    }
}
