package eu.wisebed.wisedb.test;

import eu.wisebed.wisedb.HibernateUtil;
import eu.wisebed.wisedb.controller.NodeController;
import eu.wisebed.wisedb.model.Node;
import org.apache.log4j.Logger;
import org.hibernate.Transaction;

/**
 * Searches for a testbed in the database using its name.
 * Then displays all the testbed related information or an error message.
 */
public class GetNode {

    /**
     * a log4j logger to print messages.
     */
    private static final Logger LOGGER = Logger.getLogger(GetNode.class);


    public static void main(final String[] args) {

        // Initialize hibernate
        HibernateUtil.connectEntityManagers();
        final Transaction tx = HibernateUtil.getInstance().getSession().beginTransaction();
        final int id = 1;
        try {
            final Node node = NodeController.getInstance().getByID("urn:wisebed:ctitestbed:0x9979");
            if (node != null) {
                LOGGER.info("id: " + node.getId());
                LOGGER.info("description: " + NodeController.getInstance().getDescription(node));
                LOGGER.info("Position :" + NodeController.getInstance().getPosition(node));
                LOGGER.info("Origin :" + NodeController.getInstance().getOrigin(node));
            } else {
                LOGGER.error("testbed " + id + " does not exist!");
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
