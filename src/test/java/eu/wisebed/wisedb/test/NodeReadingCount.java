package eu.wisebed.wisedb.test;


import eu.wisebed.wisedb.HibernateUtil;
import eu.wisebed.wisedb.controller.NodeController;
import eu.wisebed.wisedb.controller.NodeReadingController;
import eu.wisebed.wisedb.controller.TestbedController;
import eu.wisebed.wisedb.model.Testbed;
import eu.wisebed.wiseml.model.setup.Capability;
import eu.wisebed.wisedb.model.Node;
import org.apache.log4j.Logger;
import org.hibernate.Transaction;

import java.util.Map;

public class NodeReadingCount {
    /**
     * a log4j logger to print messages.
     */
    private static final Logger LOGGER = org.apache.log4j.Logger.getLogger(NodeReadingCount.class);

    public static void main(String args[]) {

        // Initialize hibernate
        HibernateUtil.connectEntityManagers();
        Transaction tx = HibernateUtil.getInstance().getSession().beginTransaction();
        try {
            final String urnPrefix = "urn:wisebed:ctitestbed:";
            final Testbed testbed = TestbedController.getInstance().getByUrnPrefix(urnPrefix);
            final Node node = NodeController.getInstance().getByID(urnPrefix + "0x99c");
            LOGGER.info("Selected Node : " + node.getId());
            long now1 = System.currentTimeMillis();
            Long readingsCount = NodeReadingController.getInstance().getNodeReadingsCount(node);
            long now2 = System.currentTimeMillis();
            LOGGER.info("Selected Node : " + node.getId() + " readings count :" + readingsCount.intValue() + " (" + (now2 - now1) + ")");
            now1 = System.currentTimeMillis();
            Map<Capability, Long> map = NodeReadingController.getInstance().getNodeReadingsCountMap(node);
            now2 = System.currentTimeMillis();
            for (Capability key : map.keySet()) {
                LOGGER.info("Selected Node : " + node.getId() + " Selected Capability : " + key.getName() + " readings count :" + map.get(key));
            }
            LOGGER.info("it took (" + (now2 - now1) + ").");
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
