package eu.wisebed.wisedb.test;


import eu.wisebed.wisedb.HibernateUtil;
import eu.wisebed.wisedb.controller.NodeController;
import eu.wisebed.wisedb.controller.TestbedController;
import eu.wisebed.wisedb.model.Testbed;
import eu.wisebed.wiseml.model.setup.Node;
import org.apache.log4j.Logger;
import org.hibernate.Transaction;

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
            final Node node = NodeController.getInstance().list(testbed).iterator().next();
            LOGGER.info("Selected Node : " + node.getId());
            Long readingsCount = NodeController.getInstance().getReadingsCount(node);
            LOGGER.info("Selected Node : " + node.getId() + " readings count :" + readingsCount.intValue());
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
