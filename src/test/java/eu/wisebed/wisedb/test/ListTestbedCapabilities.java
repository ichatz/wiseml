package eu.wisebed.wisedb.test;

import eu.wisebed.wisedb.HibernateUtil;
import eu.wisebed.wisedb.controller.LinkCapabilityController;
import eu.wisebed.wisedb.controller.NodeCapabilityController;
import eu.wisebed.wisedb.controller.NodeController;
import eu.wisebed.wisedb.model.Node;
import org.apache.log4j.Logger;
import org.hibernate.Transaction;

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
//            List<Capability> nodeCapabilities = NodeCapabilityController.getInstance().list();
            LOGGER.info("Total Node Capabilities :" + NodeCapabilityController.getInstance().count());
            LOGGER.info("Node Capability 1 :" + NodeCapabilityController.getInstance().getByID(1).getCapability().getName());
            LOGGER.info("Total Node Capabilities :" + NodeCapabilityController.getInstance().list().size());
            Node node = NodeController.getInstance().getByID("urn:wisebed:ctitestbed:0x9979");
            LOGGER.info("Node "+node.getId() + " Capabilities :" + NodeCapabilityController.getInstance().list(node).size());
//            List<Capability> linkCapabilities = LinkCapabilityController.getInstance().list();
            LOGGER.info("Total Link Capabilities :" + LinkCapabilityController.getInstance().count());


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
